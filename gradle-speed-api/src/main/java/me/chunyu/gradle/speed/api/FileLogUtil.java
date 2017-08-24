package me.chunyu.gradle.speed.api;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.chunyu.gradle.speed.api.data.SpeedData;


/**
 * Created by zhangyunhua on 17/8/23.
 */

public class FileLogUtil {

    private static final String TAG = "SpeedUtil";

    private static int sCount;

    private static long sTime = System.currentTimeMillis();

    private static File sPath = new File("/sdcard/speed/");

    static {
        if (!sPath.exists()) {
            sPath.mkdirs();
        }
    }

    private static int COUNT_MOD = 20;

    private static long TIME_THRESOLD = 5 * 1000;

    private static Map<String, SpeedData> sMap = new ConcurrentHashMap<>(COUNT_MOD);

    public synchronized static void addStartLog(String key, String data) {
        sMap.put(key, new SpeedData(data, null));
    }

    public synchronized static void addEndLog(String key, String data) {
        SpeedData value = sMap.get(key);
        if (value == null) {
            sMap.put(key,  new SpeedData(null, data));
        } else {
            value.end = data;
        }

        if (++sCount > COUNT_MOD || Math.abs(sTime - System.currentTimeMillis()) > TIME_THRESOLD) {
            Map<String, SpeedData> sTempMap = sMap;
            sMap = new ConcurrentHashMap<>(COUNT_MOD);
            sCount = 0;
            writeFile(sTempMap.values());
        }
    }

    private static void writeFile(final Collection<SpeedData> values) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SpeedData data;
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    BufferedWriter fw = new BufferedWriter(new FileWriter(new File(sPath, sf.format(new Date()))));
                    Iterator<SpeedData> it = values.iterator();
                    while (it.hasNext()) {
                        data = it.next();
                        if (data.start != null) {
                            fw.write(data.start);
                        }
                        if (data.end != null) {
                            fw.write(data.end);
                        }
                    }
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void setFilePath(String filePath) {
        File file = new File("/sdcard/" + filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        sPath = file;
    }

    public static void setCountThresold(int count) {
        COUNT_MOD = count;
    }

    public static void setTimeThresold(int time) {
        TIME_THRESOLD = time;
    }

}
