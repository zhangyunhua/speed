
##测速插件

###集成方式

```
根build.gradle
buildscript {    
    dependencies {
        classpath 'me.chunyu.gradle.plugin:speed:0.0.1'
    }
}


主工程build.gradle

apply plugin: 'me.chunyu.speed'
    android {
    dependencies {
       compile 'me.chunyu.gradle.plugin:speed-api:1.0'
    }
}

```


###配置方式

提供了配置文件的配置方式，和注解的配置方式， 注解的优先级高于配置文件的优先级。

***a.配置文件***

    将cyspeed.xml文件放置于主工程下（配置文件名须为cyspeed.xml）
    
    ```
    <?xml version="1.0" encoding="utf-8"?>
    <resources>
    
        <switch>
            <!--true代表打开Cyspeed，请注意即使这个值为true，Cyspeed也默认只在Release模式下开启-->
            <!--false代表关闭Cyspeed，无论是Debug还是Release模式都不会运行Cyspeed-->
            <turnOnSpeed>false</turnOnSpeed>
    
            <!--是否开启手动模式，手动模式会去寻找配置项patchPackname包名下的所有类，自动的处理混淆，然后把patchPackname包名下的所有类制作成补丁-->
            <!--这个开关只是把配置项patchPackname包名下的所有类制作成补丁，适用于特殊情况，一般不会遇到-->
            <manual>false</manual>
    
            <!--项目是否支持ASM进行插桩，默认使用ASM，推荐使用ASM，Javaassist在容易和其他字节码工具相互干扰-->
            <useAsm>false</useAsm>
            <!--<useAsm>false</useAsm>-->
    
            <forceInsert>true</forceInsert>
        </switch>
    
        <!--需要热补的包名或者类名，这些包名下的所有类都被会插入代码-->
        <!--这个配置项是各个APP需要自行配置，就是你们App里面你们自己代码的包名，
        这些包名下的类会被Cyspeed插入代码，没有被Cyspeed插入代码的类Cyspeed是无法修复的-->
        <packname name="includePackage">
            <name>me.chunyu</name>
        </packname>
    
        <!--不需要Cyspeed插入代码的包名，Cyspeed库不需要插入代码，如下的配置项请保留，还可以根据各个APP的情况执行添加-->
        <exceptPackname name="exceptPackage">
            <name>me.chunyu.g7anno</name>
        </exceptPackname>
    
        <!-- 包括的方法名称 -->
        <includeMethod></includeMethod>
    
        <!-- 排除的方法名称 -->
        <exceptMethod></exceptMethod>
    
        <log>
            <!-- log模式 枚举体 file标示写入文件, 默认文件写入路径为/sdcard/cyspeed，可以通过FileLogUtil.setFilePath设置任意路径。console标示在控制台打印，all全部  默认为console-->
            <mode>all</mode>
    
            <!-- log打印模式， true为只打印方法执行的时间， false会打印方法的起始调用时间与结束调用时间以及执行时间， 默认为false-->
            <isSimple>false</isSimple>
    
            <!-- 速度阈值，只有在log-is-simple设置为true时生效， 单位为ms， 只有超过指定时长的才会打印log-->
            <speedThreshold>20</speedThreshold>
    
            <!-- log打印形式 %st 代表起始时间  %et代表结束时间 %cost代表花费时间 %lname代表调用的完整方法名 %class代表类名 %method方法名
                 startExpression标示起始log表达式， endExpression标示结束log表达式， 如果isSimple为true则只用endExpression表达式
             -->
            <startExpression>【%class %method】 start: %st  end: %et    cost: %costms</startExpression>
            <endExpression>【%class %method】 start: %st  end: %et    cost: %costms</endExpression>
        </log>
    
    </resources>
    
    ```
    
***b.注解配置***

ClassSpeed 在类上进行修饰，如果类上添加了该注解，则该类的方法都会打印执行时间

ClassMethodSpeed 在类上进行修饰， 与MethodSpeed配合使用， 是MethodSpeed的开关。 设置之后该类中用MethodSpeed修饰的方法才会打印执行时间的log

MethodSpeed 在方法上进行修饰，添加该注解的方法，会打印执行时间的log （前提是该方法的类声明了ClassMethodSpeed注解）


###查看方式
1.如果是log， 则查看TAG为SpeedPlugin的android日志
2.如果是file， 则默认路径会保存在磁盘 /sdcard/cyspeed/ 下
 对于写文件， 提供了一下api，用于设置速度日志文件保存路径，以及日志的打印频率（超过多少条打印，超过设定时长打印）
 
 ```
 public class me.chunyu.gradle.speed.api.FileLogUtil {
    //设置文件保存路径
    public static void setFilePath(String filePath)
    //设置文件最大日志条目
    public static void setCountThresold(int count)
    //设置log最长间隔的写入时间，单位ms
    public static void setTimeThresold(int time) 
 }
 ```