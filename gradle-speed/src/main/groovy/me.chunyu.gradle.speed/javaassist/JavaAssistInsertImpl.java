package me.chunyu.gradle.speed.javaassist;

import com.android.tools.lint.detector.api.Speed;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipOutputStream;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.AccessFlag;
import javassist.expr.Cast;
import javassist.expr.ConstructorCall;
import javassist.expr.ExprEditor;
import javassist.expr.Handler;
import javassist.expr.Instanceof;
import javassist.expr.MethodCall;
import javassist.expr.NewArray;
import javassist.expr.NewExpr;
import me.chunyu.gradle.speed.Constants;
import me.chunyu.gradle.speed.InsertcodeStrategy;
import me.chunyu.gradle.speed.api.FileLogUtil;
import me.chunyu.gradle.speed.api.annotation.ClassMethodSpeed;
import me.chunyu.gradle.speed.api.annotation.ClassSpeed;
import me.chunyu.gradle.speed.data.LogSwitch;


/**
 * * Created by zhangyunhua on 17/8/21.
 */

public class JavaAssistInsertImpl extends InsertcodeStrategy {

    public JavaAssistInsertImpl(List<String> includePackageList, List<String> includeMethodList, List<String> exceptPackageList, List<String> exceptMethodList, LogSwitch logSwitch) {
        super(includePackageList, includeMethodList, exceptPackageList, exceptMethodList, logSwitch);
    }

    @Override
    protected void insertCode(List<CtClass> box, File jarFile) throws CannotCompileException, IOException, NotFoundException {
        ZipOutputStream outStream=new JarOutputStream(new FileOutputStream(jarFile));
//        new ForkJoinPool().submit {
        for(CtClass ctClass:box) {
            boolean isClassMethodPass  = getClassMethodAnnotation(ctClass) != null;
            boolean isClassPass = getClassAnnotation(ctClass) != null;
            if (isClassMethodPass || isClassPass || isNeedInsertClass(ctClass.getName())) {
                ctClass.setModifiers(AccessFlag.setPublic(ctClass.getModifiers()));
                if (ctClass.isInterface() || ctClass.getDeclaredMethods().length < 1) {
                    zipFile(ctClass.toBytecode(), outStream, ctClass.getName().replaceAll("\\.", "/") + ".class");
                    continue;
                }
//                boolean addIncrementalChange = false;
                for (CtBehavior ctBehavior : ctClass.getDeclaredBehaviors()) {
//                    if (!addIncrementalChange) {
//                        addIncrementalChange = true;
//                        ClassPool classPool = ctBehavior.getDeclaringClass().getClassPool();
//
//
//
//                        CtClass type = classPool.getOrNull(Constants.INTERFACE_NAME);
//                        CtField ctField = new CtField(type, Constants.INSERT_FIELD_NAME, ctClass);
//                        ctField.setModifiers(AccessFlag.PUBLIC | AccessFlag.STATIC);
//                        ctClass.addField(ctField);
//                    }
                    if (!isClassPass && !isQualifiedMethod(ctBehavior, isClassMethodPass)) {
                        continue;
                    }
                    //here comes the method will be inserted code
                    methodMap.put(ctBehavior.getLongName(), insertMethodCount.incrementAndGet());
                    try {
                        if (ctBehavior.getMethodInfo().isMethod()) {
                            insertCode(ctClass, ctBehavior);
                        }
                    } catch (Throwable t ) {
                        t.printStackTrace();
                        System.out.println("ctClass: " + ctClass.getName() + " error: " + t.getMessage());
                    }
                }
            }
            zipFile(ctClass.toBytecode(), outStream, ctClass.getName().replaceAll("\\.", "/") + ".class");
        }
        outStream.close();
    }

    //  <!-- log打印形式 %st 代表起始时间  %et代表结束时间 %cost代表花费时间 %lname代表调用的完整方法名 %class代表类名 %method方法名
    private void insertCode(CtClass ctClass, CtBehavior ctBehavior) throws CannotCompileException {

        ctBehavior.addLocalVariable("gradleSpeedStartTime", CtClass.longType);
        ctBehavior.addLocalVariable("gradleSpeedEndTime", CtClass.longType);
        StringBuffer body = new StringBuffer();

        boolean isFileMode = logSwitch.logMode == LogSwitch.LogMode.FILE || logSwitch.logMode == LogSwitch.LogMode.ALL;
        boolean isConsoleMode = logSwitch.logMode == LogSwitch.LogMode.CONSOLE || logSwitch.logMode == LogSwitch.LogMode.ALL;
        String id = "";
        if (isFileMode) {
            id = UUID.randomUUID().toString();
        }


        body.append("gradleSpeedStartTime = System.currentTimeMillis();");

        if (logSwitch.isSimple) {
            StringBuffer value = convertLogExpression(logSwitch.startExpression.replace("%lname", ctBehavior.getLongName())
                    .replace("%class", ctClass.getName()).replace("%method", ctBehavior.getName()));

            if (isConsoleMode) {
                body.append("android.util.Log.d(\"SpeedPlugin\", ");
                body.append(value);
                body.append(");");
            }

            if (isFileMode){
                body.append("me.chunyu.gradle.speed.api.FileLogUtil.addStartLog(\"");
                body.append(id);
                body.append("\",");
                body.append(value);
                body.append(");");
            }
//                    String expression = logSwitch.startExpression.replace("%lname", ctBehavior.getLongName())
//                            .replace("%class", ctClass.getName()).replace("%method", ctBehavior.getName());
//                    body.append(convertLogExpression(expression));
        }
        ctBehavior.insertBefore(body.toString());

        body = new StringBuffer();
        body.append("gradleSpeedEndTime = System.currentTimeMillis();");
        if (!logSwitch.isSimple) {
            body.append("if((gradleSpeedEndTime - gradleSpeedStartTime) > ");
            body.append(logSwitch.speedThreshold);
            body.append(") {");
        }

        StringBuffer value = convertLogExpression(logSwitch.endExpression.replace("%lname", ctBehavior.getLongName())
                .replace("%class", ctClass.getName()).replace("%method", ctBehavior.getName())) ;
        if (isConsoleMode) {
            body.append("android.util.Log.d(\"SpeedPlugin\", ");
            body.append(value);
            body.append(");");
        }
        if (isFileMode) {
            body.append("me.chunyu.gradle.speed.api.FileLogUtil.addEndLog(\"");
            body.append(id);
            body.append("\",");
            body.append(value);
            body.append(");");
        }

        if (!logSwitch.isSimple) {
            body.append("}");
        }
        ctBehavior.insertAfter(body.toString());

//            body.append("gradleSpeedStartTime = System.currentTimeMillis();");
//
//            if (logSwitch.isSimple) {
//                body.append("android.util.Log.d(\"SpeedPlugin\", \"");
//
//
//                String expression = logSwitch.startExpression;
//                body.append(expression.replace("%st", "\"+gradleSpeedStartTime+")
//                        .replace("\"+%et\"+", "0").replace("%cost", "0").replace("%lname", ctBehavior.getLongName())
//                        .replace("%class", ctClass.getName()).replace("%method", ctBehavior.getName()));
//                body.append("\");");
//            }
//            ctBehavior.insertBefore(body.toString());
//
//            body = new StringBuffer();
//            body.append("gradleSpeedEndTime = System.currentTimeMillis();");
//            if (!logSwitch.isSimple) {
//                body.append("if((gradleSpeedEndTime - gradleSpeedStartTime) > ");
//                body.append(logSwitch.speedThreshold);
//                body.append(") {");
//            }
//            body.append("android.util.Log.d(\"SpeedPlugin\", \"");
//            String expression = logSwitch.endExpression;
//            body.append(expression.replace("%st", "\"+gradleSpeedStartTime\"+")
//                    .replace("%et", "\"+gradleSpeedEndTime\"+").replace("%cost", "\"+(gradleSpeedEndTime - gradleSpeedStartTime)+\"").replace("%lname", ctBehavior.getLongName())
//                    .replace("%class", ctClass.getName()).replace("%method", ctBehavior.getName()));
//            body.append("\");");
//            if (!logSwitch.isSimple) {
//                body.append("}");
//            }
//            ctBehavior.insertAfter(body.toString());

    }

    private StringBuffer convertLogExpression(String str) {

//        String str = "abcde%st nnn%costmesa %et".trim();

        StringBuffer sb = new StringBuffer();
        char ch;
        String cost = "(gradleSpeedEndTime - gradleSpeedStartTime)";
        String st = "gradleSpeedStartTime";
        String et = "gradleSpeedEndTime";
        StringBuffer blank = new StringBuffer();
        int length = str.length();
        int index = 0;
        int status = 1;
        while (index < length) {
            ch = str.charAt(index);
            if (status == 1) {
                if (ch == '%') {
                    status = 2;
                } else {
                    status = 3;
                    sb.append("\"");
                    sb.append(ch);
                }
            } else if (status == 2) {
                if (ch == 'c') {
                    status = 4;
                } else if (ch == 's') {
                    status = 5;
                } else if (ch == 'e') {
                    status = 6;
                } else {
                    sb.append(ch);
                    status = 3;
                }
            } else if (status == 3) {
                if (ch == '%') {
                    status = 2;
                } else {
                    sb.append(ch);
                    status = 3;
                }
            } else if (status == 4) {
                if (ch == 'o') {
                    status = 7;
                } else {
                    status = 3;
                    sb.append(ch);
                }
            } else if (status == 5) {
                if (ch == 't') {
                    if (sb.length() > 0 && sb.toString().trim().length() != 0) {
                        sb.append("\" + ");
                    }
                    sb.append(st);
                    status = 9;
                } else {
                    sb.append(ch);
                    status= 3;
                }
            } else if (status == 6) {
                if (ch == 't') {
                    if (sb.length() > 0 && sb.toString().trim().length() != 0) {
                        sb.append("\" + ");
                    }
                    sb.append(et);
                    status = 9;
                } else {
                    sb.append(ch);
                    status = 3;
                }
            } else if (status == 7) {
                if (ch == 's') {
                    status = 8;
                } else {
                    sb.append(ch);
                    status = 3;
                }
            } else if (status == 8) {
                if (ch == 't') {
                    status = 9;
                    if (sb.length() > 0 && sb.toString().trim().length() != 0) {
                        sb.append("\" + ");
                    }
                    sb.append(cost);
                } else {
                    sb.append(ch);
                    status = 3;
                }
            } else if (status == 9) {
                if (Character.isSpaceChar(ch)) {
//					sb.append(ch);
                    blank.append(ch);
                    status = 9;
                } else if (ch == '%'){
                    sb.append(blank);
                    blank = new StringBuffer();
                    status = 2;
                } else {
                    sb.append(" + \"");
                    sb.append(blank);
                    sb.append(ch);
                    blank = new StringBuffer();
                    status = 3;
                }
            }
            index++;
        }
        if (status != 9) {
            sb.append("\"");
        }
        return sb;
    }

    private boolean isQualifiedMethod(CtBehavior it, boolean isClassMethodPass) throws CannotCompileException {
        if (it.getMethodInfo().isStaticInitializer()) {
            return false;
        }

        // synthetic 方法暂时不aop 比如AsyncTask 会生成一些同名 synthetic方法,对synthetic 以及private的方法也插入的代码，主要是针对lambda表达式
        if ((it.getModifiers() & AccessFlag.SYNTHETIC) != 0 && !AccessFlag.isPrivate(it.getModifiers())) {
            return false;
        }
        if (it.getMethodInfo().isConstructor()) {
            return false;
        }

        if ((it.getModifiers() & AccessFlag.ABSTRACT) != 0) {
            return false;
        }
        if ((it.getModifiers() & AccessFlag.NATIVE) != 0) {
            return false;
        }
        if ((it.getModifiers() & AccessFlag.INTERFACE) != 0) {
            return false;
        }

        if (it.getMethodInfo().isMethod()) {
            if (AccessFlag.isPackage(it.getModifiers())) {
                it.setModifiers(AccessFlag.setPublic(it.getModifiers()));
            }
            boolean flag = isMethodWithExpression((CtMethod) it);
            if (!flag) {
                return false;
            }
        }

        try {
            if (isClassMethodPass) {
                return (it.getAnnotation(Constants.MethodSpeedAnnotation) != null);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //方法过滤
        if (exceptMethodList != null) {
            for (String exceptMethod : exceptMethodList) {
                if (it.getName().matches(exceptMethod)) {
                    return false;
                }
            }
        }

        if (includeMethodList != null) {
            for (String name : includeMethodList) {
                if (it.getName().matches(name)) {
                    return true;
                }
            }
        }
        return true;
    }

//    private String getParametersClassType(CtMethod method) throws NotFoundException {
//        if(method.getParameterTypes().length==0){
//            return " null ";
//        }
//        StringBuilder parameterType=new StringBuilder();
//        parameterType.append("new Class[]{");
//        for(CtClass paramterClass:method.getParameterTypes()){
//            parameterType.append(paramterClass.getName()).append(".class,");
//        }
//        //remove last ','
//        if(','==parameterType.charAt(parameterType.length()-1))
//            parameterType.deleteCharAt(parameterType.length()-1);
//        parameterType.append("}");
//        return parameterType.toString();
//    }
    //判断代码中是否有方法调用
    private boolean isCallMethod = false;
    /**
     * 判断是否有方法调用
     * @return 是否插桩
     */
    private boolean isMethodWithExpression(CtMethod ctMethod) throws CannotCompileException {
        isCallMethod = false;
        if (ctMethod == null) {
            return false;
        }

        ctMethod.instrument(new ExprEditor() {
            /**
             * Edits a <tt>new</tt> expression (overridable).
             * The default implementation performs nothing.
             *
             * @param e the <tt>new</tt> expression creating an object.
             */
//            public void edit(NewExpr e) throws CannotCompileException { isCallMethod = true; }

            /**
             * Edits an expression for array creation (overridable).
             * The default implementation performs nothing.
             *
             * @param a the <tt>new</tt> expression for creating an array.
             * @throws CannotCompileException
             */
            public void edit(NewArray a) throws CannotCompileException { isCallMethod = true; }

            /**
             * Edits a method call (overridable).
             *
             * The default implementation performs nothing.
             */
            public void edit(MethodCall m) throws CannotCompileException { isCallMethod = true; }

            /**
             * Edits a constructor call (overridable).
             * The constructor call is either
             * <code>super()</code> or <code>this()</code>
             * included in a constructor body.
             *
             * The default implementation performs nothing.
             *
             * @see #edit(NewExpr)
             */
            public void edit(ConstructorCall c) throws CannotCompileException {
                isCallMethod = true;
            }

            /**
             * Edits an instanceof expression (overridable).
             * The default implementation performs nothing.
             */
            public void edit(Instanceof i) throws CannotCompileException { isCallMethod = true; }

            /**
             * Edits an expression for explicit type casting (overridable).
             * The default implementation performs nothing.
             */
            public void edit(Cast c) throws CannotCompileException { isCallMethod = true; }

            /**
             * Edits a catch clause (overridable).
             * The default implementation performs nothing.
             */
            public void edit(Handler h) throws CannotCompileException { isCallMethod = true; }
        });
        return isCallMethod;
    }

    private ClassMethodSpeed getClassMethodAnnotation(CtClass ctClass) {
        try {
            Object obj = ctClass.getAnnotation(Constants.ClassMethodSpeedAnnotation);
            if (obj != null && obj instanceof ClassMethodSpeed) {
                return (ClassMethodSpeed) obj;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ClassSpeed getClassAnnotation(CtClass ctClass) {
        try {
            Object obj = ctClass.getAnnotation(Constants.ClassSpeedAnnotation);
            if (obj != null && obj instanceof ClassSpeed) {
                return (ClassSpeed) obj;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
