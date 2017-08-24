package me.chunyu.gradle.speed;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;
import me.chunyu.gradle.speed.data.LogSwitch;

/**
 * Created by zhangmeng on 2017/5/10.
 */

public abstract class InsertcodeStrategy {
    protected  List<String> includePackageList = new ArrayList<>();
    protected  List<String> includeMethodList = new ArrayList<>();
    protected  List<String> exceptPackageList = new ArrayList<>();
    protected  List<String> exceptMethodList = new ArrayList<>();
    protected AtomicInteger insertMethodCount = new AtomicInteger(0);
    protected LogSwitch logSwitch = new LogSwitch();
    public HashMap<String, Integer> methodMap = new HashMap();


    public InsertcodeStrategy(List<String> hotfixPackageList, List<String> hotfixMethodList, List<String> exceptPackageList, List<String> exceptMethodList, LogSwitch logSwitch) {
        this.includePackageList = hotfixPackageList;
        this.includeMethodList = hotfixMethodList;
        this.exceptPackageList = exceptPackageList;
        this.exceptMethodList = exceptMethodList;
        this.logSwitch = logSwitch;
        insertMethodCount.set(0);
    }

    protected abstract void insertCode(List<CtClass> box, File jarFile) throws CannotCompileException, IOException, NotFoundException;
    protected  boolean isNeedInsertClass(String className) {

        //这样子可以在需要埋点的剔除指定的类
        for (String exceptName : exceptPackageList) {
            if (className.startsWith(exceptName)) {
                return false;
            }
        }
        for (String name : includePackageList) {
            if (className.startsWith(name)) {
                return true;
            }
        }
        return false;
    }

    protected void zipFile(byte[] classBytesArray, ZipOutputStream zos, String entryName){
        try {
            ZipEntry entry = new ZipEntry(entryName);
            zos.putNextEntry(entry);
            zos.write(classBytesArray, 0, classBytesArray.length);
            zos.closeEntry();
            zos.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
