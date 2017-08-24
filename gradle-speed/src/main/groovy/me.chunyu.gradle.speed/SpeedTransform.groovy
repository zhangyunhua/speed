package me.chunyu.gradle.speed

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import javassist.ClassPool
import me.chunyu.gradle.speed.api.FileLogUtil
import me.chunyu.gradle.speed.data.LogSwitch
import me.chunyu.gradle.speed.javaassist.JavaAssistInsertImpl
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.logging.Logger

import java.util.zip.GZIPOutputStream

/**
 * Created by zhangyunhua on 17/8/21.
 *
 * insert code
 *
 */

class SpeedTransform extends Transform implements Plugin<Project> {
    Project project
    static Logger logger
    private static List<String> includePackageList = new ArrayList<>();
    private static List<String> includeMethodList = new ArrayList<>();
    private static List<String> exceptPackageList = new ArrayList<>();
    private static List<String> exceptMethodList = new ArrayList<>();

    private static LogSwitch logSwitch = new LogSwitch();

    private static boolean isSpeedOn = false;
//    private static boolean isExceptMethodLevel = false;
//    private static boolean isForceInsert = true;
    private static boolean isForceInsert = false;
//    private static boolean useASM = false;
    private static boolean useASM = true;
    def cyspeed
    InsertcodeStrategy insertcodeStrategy;

    @Override
    void apply(Project target) {
        project = target
        cyspeed = new XmlSlurper().parse(new File("${project.projectDir}/${Constants.SPEED_XML}"))
        if (null != cyspeed.switch.turnOnRobust && !"true".equals(String.valueOf(cyspeed.switch.turnOnSpeed))) {
            return;
        }
        logger = project.logger
        initConfig()
        //turnOnDevelopModel 是true的话，则强制执行插入
        if (!isForceInsert) {
            def taskNames = project.gradle.startParameter.taskNames
            def isDebugTask = false;
            for (int index = 0; index < taskNames.size(); ++index) {
                def taskName = taskNames[index]
                logger.debug "input start parameter task is ${taskName}"
                //FIXME: assembleRelease下屏蔽Prepare，这里因为还没有执行Task，没法直接通过当前的BuildType来判断，所以直接分析当前的startParameter中的taskname，
                //另外这里有一个小坑task的名字不能是缩写必须是全称 例如assembleDebug不能是任何形式的缩写输入
                if (taskName.endsWith("Debug") && taskName.contains("Debug")) {
//                    logger.warn " Don't register speed transform for debug model !!! task is：${taskName}"
                    isDebugTask = true
                    break;
                }
            }
            if (isDebugTask) {
                project.android.registerTransform(this)
//                project.afterEvaluate(new RobustApkHashAction())
                logger.quiet "Register cyspeed transform successful !!!"
            }
        } else {
            project.android.registerTransform(this)
//            project.afterEvaluate(new RobustApkHashAction())
        }
    }

    def initConfig() {
        includePackageList = new ArrayList<>()
        includeMethodList = new ArrayList<>()
        exceptPackageList = new ArrayList<>()
        exceptMethodList = new ArrayList<>()
//        isHotfixMethodLevel = false;
//        isExceptMethodLevel = false;
        /*对文件进行解析*/
        for (name in cyspeed.packname.name) {
            includePackageList.add(name.text());
        }
        for (name in cyspeed.exceptPackname.name) {
            exceptPackageList.add(name.text());
        }
        for (name in cyspeed.includeMethod.name) {
            includeMethodList.add(name.text());
        }
        for (name in cyspeed.exceptMethod.name) {
            exceptMethodList.add(name.text());
        }

        if (cyspeed.switch.forceInsert != null && "true".equals(String.valueOf(cyspeed.switch.forceInsert.text())))
            isForceInsert = true
        else
            isForceInsert = false

        if ("".equals(String.valueOf(cyspeed.log.mode.text()))) {
            logSwitch.logMode = LogSwitch.LogMode.CONSOLE
        } else {
            logSwitch.logMode = LogSwitch.LogMode.valueOf(String.valueOf(cyspeed.log.mode.text()).toUpperCase())
        }

        if ("".equals(String.valueOf(cyspeed.log.path.text()))) {
            logSwitch.logPath = "/sdcard/cyspeed"
        } else {
            logSwitch.logPath = cyspeed.log.path
        }
        FileLogUtil.setFilePath(logSwitch.logPath);

        if (!"false".equals(String.valueOf(cyspeed.log.isSimple.text()))) {
            logSwitch.isSimple = true
        }

        if (!"".equals(String.valueOf(cyspeed.log.speedThreshold.text()))) {
            logSwitch.speedThreshold = Long.parseLong(cyspeed.log.speedThreshold.text())
        }

        //  <!-- log打印形式 %st 代表起始时间  %et代表结束时间 %cost代表花费时间 %lname代表调用的完整方法名 %class代表类名 %method方法名
        if ("".equals(String.valueOf(cyspeed.log.startExpression.text()))) {
            logSwitch.startExpression = "%lname start in %st"
        } else {
            logSwitch.startExpression = cyspeed.log.startExpression.text()
        }

        if ("".equals(String.valueOf(cyspeed.log.endExpression.text()))) {
            logSwitch.endExpression = "%lname end in %et  cost: %costms"
        } else {
            logSwitch.endExpression = cyspeed.log.endExpression.text()
        }
    }

    @Override
    String getName() {
        return "cyspeed"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }


    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput> referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws IOException, TransformException, InterruptedException {
        logger.quiet '================cyspeed start================'
        def startTime = System.currentTimeMillis()
        outputProvider.deleteAll()
        File jarFile = outputProvider.getContentLocation("main", getOutputTypes(), getScopes(),
                Format.JAR);
        if(!jarFile.getParentFile().exists()){
            jarFile.getParentFile().mkdirs();
        }
        if(jarFile.exists()){
            jarFile.delete();
        }

        ClassPool classPool = new ClassPool()
        project.android.bootClasspath.each {
            classPool.appendClassPath((String) it.absolutePath)
        }

        def box = ConvertUtils.toCtClasses(inputs, classPool)

        synchronized (SpeedTransform.class) {
            if (Constants.ClassSpeedAnnotation == null) {
                Constants.ClassSpeedAnnotation = box.get(0).getClassPool().get(Constants.SPEED_CLASS_ANNOTATION).toClass();
            }

            if (Constants.ClassMethodSpeedAnnotation == null) {
                Constants.ClassMethodSpeedAnnotation = box.get(0).getClassPool().get(Constants.SPEED_CLASS_METHOD_ANNOTATION).toClass();
            }

            if (Constants.MethodSpeedAnnotation == null) {
                Constants.MethodSpeedAnnotation = box.get(0).getClassPool().get(Constants.SPEED_METHOD_ANNOTATION).toClass();
            }
        }

        def cost = (System.currentTimeMillis() - startTime) / 1000
//        logger.quiet "check all class cost $cost second, class count: ${box.size()}"
//        if(useASM){
//            insertcodeStrategy=new AsmInsertImpl(includePackageList,includeMethodList,exceptPackageList,exceptMethodList,isHotfixMethodLevel,isExceptMethodLevel);
//        }else {
            insertcodeStrategy=new JavaAssistInsertImpl(includePackageList, includeMethodList, exceptPackageList, exceptMethodList, logSwitch);
//        }
        insertcodeStrategy.insertCode(box, jarFile);
//        writeMap2File(insertcodeStrategy.methodMap, Constants.METHOD_MAP_OUT_PATH)

        cost = (System.currentTimeMillis() - startTime) / 1000
        logger.quiet "cyspeed cost $cost second"
        logger.quiet '================cyspeed   end================'
    }

//    private void writeMap2File(Map map, String path) {
//        File file = new File(project.buildDir.path + path);
//        if (!file.exists() && (!file.parentFile.mkdirs() || !file.createNewFile())) {
////            logger.error(path + " file create error!!")
//        }
//        FileOutputStream fileOut = new FileOutputStream(file);
//
//        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//        ObjectOutputStream objOut = new ObjectOutputStream(byteOut);
//        objOut.writeObject(map)
//        //gzip压缩
//        GZIPOutputStream gzip = new GZIPOutputStream(fileOut);
//        gzip.write(byteOut.toByteArray())
//        objOut.close();
//        gzip.flush();
//        gzip.close();
//        fileOut.flush()
//        fileOut.close()
//
//    }

}