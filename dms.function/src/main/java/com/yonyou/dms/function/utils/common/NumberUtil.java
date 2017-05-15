/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.function
*
* @File name : NumberUtil.java
*
* @Author : zhangxc
*
* @Date : 2016年3月29日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年3月29日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.function.utils.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/*
*
* @author zhangxc
* 数据值操作的Util 方法
* @date 2016年3月29日
*/

public class NumberUtil {
    /** 
     * double 相加 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static Double add2Double(Double d1,Double d2){
        d1 = d1==null?0:d1;
        d2 = d2==null?0:d2;
        BigDecimal bd1 = BigDecimal.valueOf(d1); 
        BigDecimal bd2 = BigDecimal.valueOf(d2); 
        return bd1.add(bd2).doubleValue(); 
    } 


    /** 
     * double 相减 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static Double sub2Double(Double d1,Double d2){ 
        d1 = d1==null?0:d1;
        d2 = d2==null?0:d2;
        BigDecimal bd1 = BigDecimal.valueOf(d1); 
        BigDecimal bd2 = BigDecimal.valueOf(d2); 
        return bd1.subtract(bd2).doubleValue(); 
    } 

    /** 
     * double 乘法 
     * @param d1 
     * @param d2 
     * @return 
     */ 
    public static Double mul2Double(Double d1,Double d2){ 
        d1 = d1==null?0:d1;
        d2 = d2==null?0:d2;
        BigDecimal bd1 = BigDecimal.valueOf(d1); 
        BigDecimal bd2 = BigDecimal.valueOf(d2); 
        return bd1.multiply(bd2).doubleValue(); 
    } 


    /** 
     * double 除法 
     * @param d1 
     * @param d2 
     * @param scale 四舍五入 小数点位数  这里精确到2位
     * @return 
     */ 
    public static Double div2Double(Double d1,Double d2,int scale){ 
        d1 = d1==null?0:d1;
        if(d2==null||isZero(d2)){
            return null;
        }
        BigDecimal bd1 = BigDecimal.valueOf(d1); 
        BigDecimal bd2 = BigDecimal.valueOf(d2); 
        return bd1.divide (bd2,scale,BigDecimal.ROUND_CEILING).doubleValue();
    } 
    
    /** 
     * double 比较大小 
     * @param d1 
     * @param d2 
     * @return 返回的结果是int类型,-1表示小于,0是等于,1是大于.
     */ 
    public static int compareTo(Double d1,Double d2){ 
        d1 = d1==null?0:d1;
        d2 = d2==null?0:d2;
        BigDecimal bd1 = BigDecimal.valueOf(d1); 
        BigDecimal bd2 = BigDecimal.valueOf(d2); 
        return bd1.compareTo(bd2); 
    } 
    /** 
     * 提供精确的加法运算
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(BigDecimal v1,BigDecimal v2){
        
        v1 = v1==null?BigDecimal.ZERO:v1;
        v2 = v2==null?BigDecimal.ZERO:v2;
        return v1.add(v2);
    }
    
    /**
     * 提供精确的减法运算
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static BigDecimal sub(BigDecimal v1,BigDecimal v2){
        v1 = v1==null?BigDecimal.ZERO:v1;
        v2 = v2==null?BigDecimal.ZERO:v2;
        return v1.subtract(v2);
    }
    
    /**
     * 提供精确的乘法运算
     * @param v1  被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static BigDecimal mul(BigDecimal v1,BigDecimal v2){
        v1 = v1==null?BigDecimal.ZERO:v1;
        v2 = v2==null?BigDecimal.ZERO:v2;        
        return v1.multiply(v2);
    }
    
   
    /**
     * 提供(相对)精确的除法运算.当发生除不尽的情况时,由scale参数指
     * 定精度,以后的数字四舍五入
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位
     * @return 两个参数的商
     */
    public static BigDecimal div(BigDecimal v1,BigDecimal v2,int scale){
        v1 = v1==null?BigDecimal.ZERO:v1;
        if(v2==null||isZero(v2.doubleValue())){
            return null;
        }
        return v1.divide(v2,scale,BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * 
    * 获得数字的最短字符串表示
    * @author zhangxc
    * @date 2016年8月31日
    * @param value
    * @return
     */
    public static String getShortString(Number value){
        DecimalFormat df = new DecimalFormat("0.####");
        String formatValue = df.format(value);
        return formatValue;
    }
    /**
     * 
    *  对数字进行四舍五入
    * @author zhangxc
    * @date 2016年12月29日
    * @param v
    * @param scale
    * @return
     */
    public static double roundHalfUp(double v,int scale){
        BigDecimal b = BigDecimal.valueOf(v);
        return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    
    /**
     * 
    * 判断一个符点类型是否等于0
    * @author zhangxc
    * @date 2017年1月14日
    * @param v
    * @return
     */
    public static boolean isZero(Double v){
        return new Double(0d).compareTo(v) ==0;
    }
    
}
