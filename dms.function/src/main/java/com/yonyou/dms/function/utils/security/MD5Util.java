
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.function
 *
 * @File name : MD5Util.java
 *
 * @Author : zhangxc
 *
 * @Date : 2016年9月14日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月14日    zhangxc    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.function.utils.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import com.yonyou.dms.function.exception.UtilException;

/**
 * 密码加密生成器
 * @author zhangxc
 * @date 2016年9月14日
 */

public class MD5Util {
    private static final Integer SALT_LENGTH = 12;
    
    /**
     * 验证口令是否合法
     * @param password
     * @param passwordInDb
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static boolean validPassword(String password, String passwordInDb) {
        try{
          //将16进制字符串格式口令转换成字节数组
            byte[] pwdInDb = SystemSecurityUtil.hexStringToByte(passwordInDb);
            //声明盐变量
            byte[] salt = new byte[SALT_LENGTH];
            //将盐从数据库中保存的口令字节数组中提取出来
            System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH);
            
            //获得加密的数据
            byte[] digest = encrypte(salt,password);
            
            //声明一个保存数据库中口令消息摘要的变量
            byte[] digestInDb = new byte[pwdInDb.length - SALT_LENGTH];
            //取得数据库中口令的消息摘要
            System.arraycopy(pwdInDb, SALT_LENGTH, digestInDb, 0, digestInDb.length);
            //比较根据输入口令生成的消息摘要和数据库中消息摘要是否相同
            if (Arrays.equals(digest, digestInDb) ) {
                //口令正确返回口令匹配消息
                return true;
            } else if(passwordInDb.equals(MD5Encryption(password))){
            	 //32位加密校验
                return true;
            }else{
                //口令不正确返回口令不匹配消息
                return false;
            }
        }catch(Exception e){
            throw new UtilException("密码验证失败",e);
        }
    }


    /**
     * 获得加密后的16进制形式口令
     * @param password
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getEncryptedPwd(String password){
        try{
            //声明加密后的口令数组变量
            byte[] pwd = null;
            //随机数生成器
            SecureRandom random = new SecureRandom();
            //声明盐数组变量
            byte[] salt = new byte[SALT_LENGTH];
            //将随机数放入盐变量中
            random.nextBytes(salt);
            
            //获得加密的数据
            byte[] digest = encrypte(salt,password);

            //因为要在口令的字节数组中存放盐，所以加上盐的字节长度
            pwd = new byte[digest.length + SALT_LENGTH];
            //将盐的字节拷贝到生成的加密口令字节数组的前12个字节，以便在验证口令时取出盐
            System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);
            //将消息摘要拷贝到加密口令字节数组从第13个字节开始的字节
            System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);
            //将字节数组格式加密后的口令转化为16进制字符串格式的口令
            return SystemSecurityUtil.byteToHexString(pwd); 
        }catch(Exception e){
            throw new UtilException("获取加密密码失败",e);
        }
        
    }
    
    /**
     * 
    * 根据盐生成密码
    * @author zhangxc
    * @date 2016年9月14日
    * @param salt
    * @param passwrod
    * @return
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */
    public static byte[] encrypte(byte[] salt,String passwrod){
        try{
            //声明消息摘要对象
            MessageDigest md = null;
            //创建消息摘要
            md = MessageDigest.getInstance("MD5");
            //将盐数据传入消息摘要对象
            md.update(salt);
            //将口令的数据传给消息摘要对象
            md.update(passwrod.getBytes("UTF-8"));
            //获得消息摘要的字节数组
            return md.digest();
        }catch(Exception e){
            throw new UtilException("Md5解密失败",e);
        }
    }
    
    /**
     * 
    * 根据盐生成密码
    * @author zhangxc
    * @date 2016年9月14日
    * @param salt
    * @param passwrod
    * @return
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */
    public static String encrypte(String salt,String content){
        try{
          //声明消息摘要对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            //将盐数据传入消息摘要对象
            md.update(salt.getBytes("UTF-8"));
            //将口令的数据传给消息摘要对象
            md.update(content.getBytes("UTF-8"));
            return new BigInteger(1, md.digest()).toString(16).toUpperCase();
        }catch(Exception e){
            throw new UtilException("Md5加密失败",e);
        }
        
    }
    // 3代框架的加密方法
    public static String MD5Encryption(String password) {   
        StringBuffer hexString = new StringBuffer();   
        if (password != null && password.trim().length() != 0) {   
            try {   
                MessageDigest md = MessageDigest.getInstance("MD5");   
                md.update(password.getBytes());   
                byte[] hash = md.digest();   
                for (int i = 0; i < hash.length; i++) {   
                    if ((0xff & hash[i]) < 0x10) {   
                        hexString.append("0" + Integer.toHexString((0xFF & hash[i])));   
                    } else {   
                        hexString.append(Integer.toHexString(0xFF & hash[i]));   
                    }   
                }   
            } catch (NoSuchAlgorithmException e) {   
                e.printStackTrace();   
            }   
  
        }   
        return hexString.toString();   
    }

}
