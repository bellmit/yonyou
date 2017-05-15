package com.infoeai.eai.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ReadFileTxt {
/**
 * 公共
 * txt解析方法
 * **/
	public  String readFile(String curfile, String encoding) {
		File f = new File(curfile);
		if (!f.exists())
			return null;
		try {
			FileReader cf = new FileReader(curfile);
			BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(curfile), encoding));
			String filecontent = "";
			String str = is.readLine();
			while (str != null){
				filecontent += str;
				str = is.readLine();
				if (str != null)
					filecontent += "\n";
			}
			is.close();
			cf.close();
			return filecontent;
		}
		catch (Exception e) {
			System.err.println("不能读属性文件: "+curfile+" \n"+e.getMessage());
			return null;
		}
	}
	public String DelFile(String curfile){
		File fil = new File(curfile);
		if(fil.exists())
			fil.delete();
      return null;		
	}
	/**
	 * 写文件方法, 追加方式
	 * @param path 文件路径名
	 * @param fileName 文件名
	 * @param content 需要写入的文件内容
	 * @param encoding 字符编码格式
	 * @return
	 */
	public  boolean append(String path, String fileName, String content, String encoding){
		return append(path+File.separator+fileName, content, encoding);
	}
	
	/**
	 * 写文件方法, 追加方式
	 * @param fileFullName 文件全名, 包括全路径
	 * @param content 需要写入的文件内容
	 * @param encoding 字符编码格式
	 * @return
	 */
	public  boolean append(String fileFullName, String content, String encoding){
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileFullName, true), encoding));
			writer.write(content+"\r\n");
			writer.flush();
			writer.close();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (writer != null){
				try {
					writer.close();
				}
				catch (IOException e) {
					//TODO:
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 拷贝文件到指定目录
	 * @param srcPath 源文件路径
	 * @param destPath 目标文件路径
	 * @return true:拷贝成功 false:拷贝失败
	 */
	public  boolean copyFile(String srcPath,String destPath){
		try
		{
			File fl = new File(srcPath);
			int length = (int)fl.length();
		  	FileInputStream is = new FileInputStream(srcPath);
		  	FileOutputStream os = new FileOutputStream(destPath);
			byte[] b = new byte[length];
		  	is.read(b);
		  	os.write(b);
		  	is.close();
		  	os.close();
		  	return true;
	  	} 
	  	catch (Exception e)
	  	{
	  		return false;
	  	}
	}

}
