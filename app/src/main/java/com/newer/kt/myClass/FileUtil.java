package com.newer.kt.myClass;

import java.io.File;

/**
 * Created by ww on 2016/3/14.
 */
public class FileUtil {
    //创建文件夹
    public static File createFile(File nowPath,String folderName) {
        File f = new File(nowPath,folderName);
        f.mkdirs();
        return f;
    }

    //删除文件
    public static void rm(File file){
        if (file.isFile()){
            file.delete();
        } else {
            //获得目录中的内容
            File[] files = file.listFiles();
            //遍历
            for (File f : files){
                if (f.isFile()){
                    f.delete();
                } else {
                    rm(f);
                }
            }
            //删除该目录
            file.delete();
        }
    }
}
