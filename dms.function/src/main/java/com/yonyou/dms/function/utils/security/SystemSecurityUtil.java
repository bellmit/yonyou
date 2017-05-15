package com.yonyou.dms.function.utils.security;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.yonyou.dms.function.exception.UtilException;

public class SystemSecurityUtil {
    /**
     * 16进制数字
     */
    private static final String HEX_NUMS_STR="0123456789abcdef";
    
    /** 加密算法,可用 DES,DESede,Blowfish. */
	public  static final  String ALGORITHM = "DES";
	
	/**
	 * 
	* 测试方法
	* @author zhangxc
	* @date 2017年1月13日
	* @param args
	* @throws Exception
	 */
    public static void main(String[] args) throws Exception {
        String testUrl = "/demo/booking/bookingTime/11/11/11";
        System.out.println("原文：" + testUrl);
        String str = encrypt(testUrl,"ddddddsfdsfsaf");
        System.out.println("加密: " + str);
        str = decrypt(str,"ddddddsfdsfsaf");
        System.out.println("解密: " + str);
    }
    
    /**
     * 对数据进行DES解密.
     * @param data 待进行DES加密的数据
     * @return 返回经过DES加密后的数据
     * @throws Exception
     * @author <a href="mailto:xiexingxing1121@126.com" mce_href="mailto:xiexingxing1121@126.com">AmigoXie</a>
     * Creation date: 2007-7-31 - 下午12:06:24
     */
    public final static String decrypt(String data,String cryptKey) {
    	if (data == null) {
    		data = "";
    	}
        return new String(decrypt(hexStringToByte(data),cryptKey.getBytes()));
    }
    /**
     * 对用DES加密过的数据进行加密.
     * @param data DES加密数据
     * @return 返回解密后的数据
     * @throws Exception
     * @author <a href="mailto:xiexingxing1121@126.com" mce_href="mailto:xiexingxing1121@126.com">AmigoXie</a>
     * Creation date: 2007-7-31 - 下午12:07:54
     */
    public final static String encrypt(String data,String cryptKey) {
    	if (data == null) {
    		data = "";
    	}
        return byteToHexString(encrypt(data.getBytes(), cryptKey.getBytes()));
    }
    
    /**
     * 用指定的key对数据进行DES加密.
     * @param data 待加密的数据
     * @param key DES加密的key
     * @return 返回DES加密后的数据
     * @throws Exception
     * @author <a href="mailto:xiexingxing1121@126.com" mce_href="mailto:xiexingxing1121@126.com">AmigoXie</a>
     * Creation date: 2007-7-31 - 下午12:09:03
     */
    private static byte[] encrypt(byte[] data, byte[] key) {
        try{
         // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            // 从原始密匙数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SystemSecurityUtil.ALGORITHM);
            SecretKey securekey = keyFactory.generateSecret(dks);
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance(SystemSecurityUtil.ALGORITHM);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
            // 现在，获取数据并加密
            // 正式执行加密操作
            return cipher.doFinal(data);
        }catch(Exception e){
            throw new UtilException("数据加密失败",e);
        }
        
    }
    /**
     * 用指定的key对数据进行DES解密.
     * @param data 待解密的数据
     * @param key DES解密的key
     * @return 返回DES解密后的数据
     * @throws Exception
     * @author <a href="mailto:xiexingxing1121@126.com" mce_href="mailto:xiexingxing1121@126.com">AmigoXie</a>
     * Creation date: 2007-7-31 - 下午12:10:34
     */
    private static byte[] decrypt(byte[] data, byte[] key) {
        try{
         // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            // 从原始密匙数据创建一个DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);
            // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SystemSecurityUtil.ALGORITHM);
            SecretKey securekey = keyFactory.generateSecret(dks);
            // Cipher对象实际完成解密操作
            Cipher cipher = Cipher.getInstance(SystemSecurityUtil.ALGORITHM);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
            // 现在，获取数据并解密
            // 正式执行解密操作
            return cipher.doFinal(data);
        }catch(Exception e){
            throw new UtilException("数据解密失败",e);
        }
        
    }
    
    /** 
     * 将16进制字符串转换成字节数组(大写)
     * @param hex 
     * @return 
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4
                    | HEX_NUMS_STR.indexOf(hexChars[pos + 1]));
        }
        return result;
    }


    /**
     * 将指定byte数组转换成16进制字符串（大写）
     * @param b
     * @return
     */
    public static String byteToHexString(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        //把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
             digital = bytes[i];
            if(digital < 0) {
                digital += 256;
            }
            if(digital < 16){
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString();
    }
    
    /**
     * 将指定byte数组转换成16进制字符串(小写)
     * @param b
     * @return
     */
    public static String byteToHex(byte[] bytes) {
        StringBuffer md5str = new StringBuffer();
        //把数组每一字节换成16进制连成md5字符串
        int digital;
        for (int i = 0; i < bytes.length; i++) {
             digital = bytes[i];
            if(digital < 0) {
                digital += 256;
            }
            if(digital < 16){
                md5str.append("0");
            }
            md5str.append(Integer.toHexString(digital));
        }
        return md5str.toString();
    }
}