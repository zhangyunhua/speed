package me.chunyu.gradle.speed;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mivanzhang on 16/11/3.
 */

public class Constants {

    public final static String SPEED_XML = "cyspeed.xml";

//    public static final String ORIGINCLASS = "originClass";
//    public static final String SPEED_ANNOTATION = "me.chunyu.gradle.speed.api.annotation.Speed";

    public static final String SPEED_CLASS_ANNOTATION = "me.chunyu.gradle.speed.api.annotation.ClassSpeed";

    public static final String SPEED_CLASS_METHOD_ANNOTATION = "me.chunyu.gradle.speed.api.annotation.ClassMethodSpeed";

    public static final String SPEED_METHOD_ANNOTATION = "me.chunyu.gradle.speed.api.annotation.MethodSpeed";

    public static Class ClassMethodSpeedAnnotation = null;

    public static Class ClassSpeedAnnotation = null;

    public static Class MethodSpeedAnnotation = null;

}
