/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.function
*
* @File name : IOUtils.java
*
* @Author : zhangxianchao
*
* @Date : 2016年2月29日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年2月29日    zhangxianchao    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.function.utils.io;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.function.exception.UtilException;
import com.yonyou.dms.function.utils.common.StringUtils;

/*
*
* @author zhangxianchao
* IOUtils
* @date 2016年2月29日
*/

public class IOUtils {

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(IOUtils.class);

    public static String readFile(String filePath) throws UtilException {
        if (!isFile(filePath)) {
            throw new UtilException("file " + filePath + " not exist");
        }
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] buf = new byte[1024];
            StringBuffer sb = new StringBuffer();
            while ((fis.read(buf)) != -1) {
                sb.append(new String(buf));
                buf = new byte[1024];
            }
            return sb.toString();
        } catch (Exception e) {
            throw new UtilException(e);
        }
    }

    public static void writeFile(String filePath, String data) throws UtilException {
        File file = new File(filePath);
        try (FileOutputStream fop = new FileOutputStream(file)) {
            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] contentInBytes = data.getBytes();
            fop.write(contentInBytes);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            throw new UtilException("write file error", e);
        }
    }

    /**
     * get file extension
     */
    public static String getFileExtension(String fileName) {
        if (StringUtils.isNullOrEmpty(fileName)) {
            return StringUtils.EMPTY_STRING;
        }
        int index = fileName.lastIndexOf(".");
        if (index == -1 || index == fileName.length() - 1) {
            return StringUtils.EMPTY_STRING;
        }
        return fileName.substring(index + 1);
    }

    /**
     * get file name without extension
     */
    public static String getFileNameWithoutExtension(String fileName) {
        if (StringUtils.isNullOrEmpty(fileName)) {
            return StringUtils.EMPTY_STRING;
        }
        int index = fileName.lastIndexOf(".");
        if (index == -1) {
            return fileName;
        }
        return fileName.substring(0, index);
    }

    /**
     * exist file or dir
     */
    public static boolean exist(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * is dir
     */
    public static boolean isDirectory(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isDirectory();
    }

    /**
     * is file
     */
    public static boolean isFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && !file.isDirectory();
    }

    /**
     * remove file
     * 
     * @throws UtilException
     */
    public static boolean delete(String filePath) throws UtilException {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        try {
            return file.delete();
        } catch (Exception e) {
            throw new UtilException(e);
        }
    }

    /**
     * rename file
     */
    public static boolean renameFile(String filePath, String descFilePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        File descFile = new File(descFilePath);
        if (descFile.exists()) {
            return false;
        }
        return file.renameTo(descFile);
    }
    /**
     * copy file
     */
    public static void copyFile(String oldPath, String newPath) throws UtilException {
        int bytesum = 0;
        int byteread = 0;
        File oldfile = new File(oldPath);
        if (oldfile.exists()) {
            try (InputStream inStream = new FileInputStream(oldPath);
                    FileOutputStream fs = new FileOutputStream(newPath)) {
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
            } catch (Exception e) {
                throw new UtilException("copy file error", e);
            }
        }
    }

    /**
     * get file by last modified date asc
     */
    public static List<File> getFilesByLastModifiedASC(String filePath) throws UtilException {
        File root = new File(filePath);
        if (!root.exists()) {
            throw new UtilException(filePath + " not exist");
        }
        File[] files = root.listFiles();
        Arrays.sort(files, new ComparatorByLastModifiedASC());
        List<File> list = new ArrayList<>(files.length);
        for (File file : files) {
            if (!file.isDirectory()) {
                list.add(file);
            }
        }
        return list;
    }
    
    /**
     * close stream
     */
    public static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (Exception e) {
                logger.warn("close stream " + stream.getClass() + " error", e);
            }
        }
    }

    private static class ComparatorByLastModifiedASC implements Comparator<File> {

        public int compare(File f1, File f2) {
            long diff = f1.lastModified() - f2.lastModified();
            return diff > 0 ? 1 : (diff == 0 ? 0 : -1);
        }
    }
    
    
    /**
     * 获得inputstream 的body 体内容
     * 
     * @author zhangxc
     * @date 2016年12月8日
     * @return
     * @throws IOException 
     */
    public static String getInputStreamBody(InputStream is) throws IOException {
        ByteArrayOutputStream bao = null;
        try{
            return new String(getInputStreamBodyByte(is));
        }catch(IOException e){
            throw e;
        }finally{
            IOUtils.closeStream(bao);
        }
    }
    
    /**
     * 获得inputstream 的body 体内容
     * 
     * @author zhangxc
     * @date 2016年12月8日
     * @return
     * @throws IOException 
     */
    public static byte[] getInputStreamBodyByte(InputStream is) throws IOException {
        ByteArrayOutputStream bao = null;
        try{
            BufferedInputStream httpIS = new BufferedInputStream(is);
            bao = new ByteArrayOutputStream();
            byte[] bytes = new byte[4096];
            int i = 0;
            while((i = httpIS.read(bytes))!=-1){
                bao.write(bytes, 0, i);
            }
            return bao.toByteArray();
        }catch(IOException e){
            throw e;
        }finally{
            IOUtils.closeStream(bao);
        }
    }
}
