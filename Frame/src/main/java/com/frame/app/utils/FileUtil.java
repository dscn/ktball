package com.frame.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/*
 * 一、方法介绍：       
 每个Android应用程序都可以通过Context来获取与应用程序相关的目录，这些目录的功能各异，每一个目录都有自己的特点，有时候可能会搞混淆，本文结合android源码注释和实际操作，详细介绍一下每个方法：
 方法：getFilesDir 
 释义：返回通过Context.openFileOutput()创建和存储的文件系统的绝对路径，应用程序文件，这些文件会在程序被卸载的时候全部删掉。

 方法：getCacheDir
 释义：返回应用程序指定的缓存目录，这些文件在设备内存不足时会优先被删除掉，所以存放在这里的文件是没有任何保障的，可能会随时丢掉。

 方法：getDir
 释义：这是一个可以存放你自己应用程序自定义的文件，你可以通过该方法返回的File实例来创建或者访问这个目录，注意该目录下的文件只有你自己的程序可以访问。

 方法：getExternalCacheDir
 释义：使用这个方法需要写外部存储的权限“<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />”，调用该方法会返回应用程序的外部文件系统（Environment.getExternalStorageDirectory()）目录的绝对路径，它是用来存放应用的缓存文件，它和getCacheDir目录一样，目录下的文件都会在程序被卸载的时候被清除掉。 

 方法：getExternalFilesDir
 释义：使用这个方法需要写外部存储的权限“<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />”，这个目录是与应用程序相关的外部文件系统，它和getExternalCacheDir不一样的是只要应用程序存在它就会一直存在，这些文件只属于你的应用，不能被其它人访问。同样，这个目录下的文件在程序被卸载时也会被一同删除。

 方法：getExternalFilesDir
 释义：和上面的方法一样，只是返回的是其目录下某一类型的文件，这些类型可以是：     Environment#DIRECTORY_MUSIC 音乐     Environment#DIRECTORY_PODCASTS 音频     Environment#DIRECTORY_RINGTONES 铃声     Environment#DIRECTORY_ALARMS 闹铃     Environment#DIRECTORY_NOTIFICATIONS 通知铃声     Environment#DIRECTORY_PICTURES 图片     Environment#DIRECTORY_MOVIES 视频

 方法：getDatabasePath
 释义：保存通过Context.openOrCreateDatabase 创建的数据库文件

 方法：getPackageCodePath
 释义：返回android 安装包的完整路径，这个包是一个zip的压缩文件，它包括应用程序的代码和assets文件。

 方法：getPackageResourcePath
 释义：返回android 安装包的完整路径，这个包是一个ZIP的要锁文件，它包括应用程序的私有资源。

 方法：getObbDir
 释义：返回应用程序的OBB文件目录（如果有的话），注意如果该应用程序没有任何OBB文件，这个目录是不存在的。
 */
public class FileUtil {
	
	public static String downloadDir = "KTFootBallBate/";// 安装目录
	
	public static boolean isSDCardAvailable() {
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			return true;
		} else {
			return false;
		}
	}

	public static String getDownloadDir(Context context){
		return getDir(context,"download");
	}

	public static String getDecompressionDir(Context context){
		return getDir(context,"decompression");
	}

	public static String getCacheDir(Context context){
		return getDir(context,"cache");
	}

	public static String getIconDir(Context context){
		return getDir(context,"icon");
	}
	
	/**
	 * 获得文件夹路径
	 * @param context
	 * @param name
	 * @return
	 */
	public static String getDir(Context context,String name) {
		String path;
		if (isSDCardAvailable()) {
			path = getExternalStoragePath();
		} else {
			path = getCachePath(context);
		}
		path = path + name + "/";
		if (createDirs(path)) {
			return path;
		} else {
			return path;
		}
	}
	
	/**
	 * 获取缓存路径
	 * @param context
	 * @return
	 */
	public static String getCachePath(Context context) {
		File f = context.getCacheDir();
		if (null == f) {
			return null;
		} else {
			return f.getAbsolutePath() + "/";
		}
	}
	
	/**
	 * 获取SD卡路径
	 * @return
	 */
	public static String getExternalStoragePath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + downloadDir;
	}

	/**
	 * 创建文件夹
	 * @param dirPath
	 * @return
	 */
	public static boolean createDirs(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists() || !file.isDirectory()) {
			return file.mkdirs();
		}
		return true;
	}
	/**
	 * 创建文件
	 * @param dirPath
	 * @return
	 */
	public static boolean createNewFile(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists()) {
			try {
				return file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 从Assets目录下读取字符串
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String getFromAssets(Context context,String fileName) {
		try {
			InputStreamReader inputReader = new InputStreamReader(
					context.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String Result = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 得到SD卡根目录.
	 */
	public static File getRootPath() {
		File path = null;
		if (FileUtil.sdCardIsAvailable()) {
			path = Environment.getExternalStorageDirectory(); // 取得sdcard文件路径
		} else {
			path = Environment.getDataDirectory();
		}
		return path;
	}

	/**
	 * SD卡是否可用.
	 */
	public static boolean sdCardIsAvailable() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File sd = new File(Environment.getExternalStorageDirectory().getPath());
			return sd.canWrite();
		} else
			return false;
	}

	/**
	 * 文件或者文件夹是否存在.
	 */
	public static boolean fileExists(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	/**
	 * 删除指定文件夹下所有文件, 保留文件夹.
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (file.isFile()) {
			file.delete();
			return true;
		}
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			File exeFile = files[i];
			if (exeFile.isDirectory()) {
				delAllFile(exeFile.getAbsolutePath());
			} else {
				exeFile.delete();
			}
		}
		return flag;
	}

	/**
	 * 文件复制.
	 */
	public static boolean copy(String srcFile, String destFile) {
		try {
			FileInputStream in = new FileInputStream(srcFile);
			FileOutputStream out = new FileOutputStream(destFile);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = in.read(bytes)) != -1) {
				out.write(bytes, 0, c);
			}
			in.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 复制整个文件夹内.
	 *
	 * @param oldPath string 原文件路径如：c:/fqf.
	 * @param newPath string 复制后路径如：f:/fqf/ff.
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (NullPointerException e) {
		} catch (Exception e) {
		}
	}

	/**
	 * 重命名文件.
	 */
	public static boolean renameFile(String resFilePath, String newFilePath) {
		File resFile = new File(resFilePath);
		File newFile = new File(newFilePath);
		return resFile.renameTo(newFile);
	}

	/**
	 * 获取磁盘可用空间.
	 */
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	public static long getSDCardAvailaleSize() {
		File path = getRootPath();
		StatFs stat = new StatFs(path.getPath());
		long blockSize, availableBlocks;
		if (Build.VERSION.SDK_INT >= 18) {
			blockSize = stat.getBlockSizeLong();
			availableBlocks = stat.getAvailableBlocksLong();
		} else {
			blockSize = stat.getBlockSize();
			availableBlocks = stat.getAvailableBlocks();
		}
		return availableBlocks * blockSize;
	}

	/**
	 * 获取某个目录可用大小.
	 */
	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static long getDirSize(String path) {
		StatFs stat = new StatFs(path);
		long blockSize, availableBlocks;
		if (Build.VERSION.SDK_INT >= 18) {
			blockSize = stat.getBlockSizeLong();
			availableBlocks = stat.getAvailableBlocksLong();
		} else {
			blockSize = stat.getBlockSize();
			availableBlocks = stat.getAvailableBlocks();
		}
		return availableBlocks * blockSize;
	}

	/**
	 * 获取文件或者文件夹大小.
	 */
	public static long getFileAllSize(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] childrens = file.listFiles();
				long size = 0;
				for (File f : childrens) {
					size += getFileAllSize(f.getPath());
				}
				return size;
			} else {
				return file.length();
			}
		} else {
			return 0;
		}
	}

	/**
	 * 创建一个文件.
	 */
	public static boolean initFile(String path) {
		boolean result = false;
		try {
			File file = new File(path);
			if (!file.exists()) {
				result = file.createNewFile();
			} else if (file.isDirectory()) {
				file.delete();
				result = file.createNewFile();
			} else if (file.exists()) {
				result = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 创建一个文件夹.
	 */
	public static boolean initDirectory(String path) {
		boolean result = false;
		File file = new File(path);
		if (!file.exists()) {
			result = file.mkdir();
		} else if (!file.isDirectory()) {
			file.delete();
			result = file.mkdir();
		} else if (file.exists()) {
			result = true;
		}
		return result;
	}

	/**
	 * 复制文件.
	 */
	public static void copyFile(File from, File to) throws IOException {
		if (!from.exists()) {
			throw new IOException("The source file not exist: " + from.getAbsolutePath());
		}
		FileInputStream fis = new FileInputStream(from);
		try {
			copyFile(fis, to);
		} finally {
			fis.close();
		}
	}

	/**
	 * 复制文件.
	 */
	public static long copyFile(InputStream from, File to) throws IOException {
		long totalBytes = 0;
		FileOutputStream fos = new FileOutputStream(to, false);
		try {
			byte[] data = new byte[1024];
			int len;
			while ((len = from.read(data)) > -1) {
				fos.write(data, 0, len);
				totalBytes += len;
			}
			fos.flush();
		} finally {
			fos.close();
		}
		return totalBytes;
	}

	/**
	 * 保存流到文件.
	 */
	public static void saveFile(InputStream inputStream, String filePath) {
		try {
			OutputStream outputStream = new FileOutputStream(new File(filePath), false);
			int len;
			byte[] buffer = new byte[1024];
			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用UTF8保存一个文件.
	 */
	public static void saveFileUTF8(String path, String content, Boolean append) throws IOException {
		FileOutputStream fos = new FileOutputStream(path, append);
		Writer out = new OutputStreamWriter(fos, "UTF-8");
		out.write(content);
		out.flush();
		out.close();
		fos.flush();
		fos.close();
	}

	/**
	 * 用UTF8读取一个文件.
	 */
	public static String getFileUTF8(String path) {
		String result = "";
		InputStream fin = null;
		try {
			fin = new FileInputStream(path);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			fin.close();
			result = new String(buffer, "UTF-8");
		} catch (Exception e) {
		}
		return result;
	}

	/**
	 * 得到一个文件Intent.
	 */
	public static Intent getFileIntent(String path, String mimeType) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(path)), mimeType);
		return intent;
	}

	public static boolean deleteFile(File file) {
		if (file.exists()) {//判断文件是否存在
			if (file.isFile()) {//判断是否是文件
				file.delete();//删除文件
			} else if (file.isDirectory()) {//否则如果它是一个目录
				File[] files = file.listFiles();//声明目录下所有的文件 files[];
				for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件
					deleteFile(files[i]);//把每个文件用这个方法进行迭代
				}
				file.delete();//删除文件夹
			}
			return true;
		} else {
			System.out.println("所删除的文件不存在");
			return false;
		}
	}
}
