package com.yonyou.dms.common.Util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangxianchao
 * 定义客户端通用校验方法，开发人员可根据需要添加。
 *
 */
public class CheckUtil {
	/**
	 * 只能由字母和数字组成
	 */
	private static final String SEQUENCE_FORMAT_STR = "[a-zA-Z0-9]*[a-zA-Z]{1,}[a-zA-Z0-9]*";
	private static final String SEQUENCE_FORMAT_STR5 = "[a-zA-Z0-9]*[0-9]{1,}[a-zA-Z0-9]*";;
	/**
	 * 不能含有特殊字符
	 */
	private static final String SEQUENCE_FORMAT_STR2 = "[#%&+=\\/?<>'\"]";
	/**
	 * 只能为数字
	 */
	//private static final String SEQUECNE_FORMAT_STR3="^[1-9]+\\d+$";
	private static final String SEQUECNE_FORMAT_STR3="\\d*";
	/**
	 * 只能为数字和横线组成：如：021-62128308
	 */
	//private static final String SEQUECNE_FORMAT_STR3="^[1-9]+\\d+$";
	private static final String SEQUECNE_FORMAT_STR4="^[+]{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$";
//	private static final String MessageService = null;
	/**
	 * 手工生成编号的正确格式：只能由数字、字母开头，可以带下划线和点组成
	 * 
	 * @param source
	 * @return 如果符合要求则返回true否则返回false
	 */
	public static boolean checkVINFormat(String source){
		Pattern pattern1 = Pattern.compile(SEQUENCE_FORMAT_STR);
		Matcher matcher1 = pattern1.matcher(source);
		Pattern pattern2 = Pattern.compile(SEQUENCE_FORMAT_STR5);
		Matcher matcher2 = pattern2.matcher(source);
		return matcher1.matches()&&matcher2.matches();
	}
	/**
	 * 名称：不要输入如下字符：#\"%&+=\/?<>'
	 * 
	 * @param source
	 * @return 如果是返回true；否则返回false
	 */
	public static boolean checkDescFormat(String source){
		Pattern pattern = Pattern.compile(SEQUENCE_FORMAT_STR2);
		Matcher matcher = pattern.matcher(source);
		return !matcher.find();
	}
	/**
	 * 判断输入的字符必须是数字, 而且第一位不能为0；
	 * @param source
	 * @return 如果符合规则则返回true,否则返回false
	 */
	public static boolean checkFormatNumber(String source){
		Pattern pattern =Pattern.compile(SEQUECNE_FORMAT_STR3);
		Matcher matcher =pattern.matcher(source);
		return matcher.matches();
	}
	/**
	 * 判断输入的字符必须数字和横线组成，符合电话格式
	 * @param source
	 * @return 如果符合规则则返回true,否则返回false
	 */
	public static boolean checkFormatTelephone(String source){
		Pattern pattern =Pattern.compile(SEQUECNE_FORMAT_STR4);
		Matcher matcher =pattern.matcher(source);
		return matcher.matches();
	}
	/**
	 * 检查是不是为空
	 * @param source
	 * @return
	 */
	public static boolean checkNull(String source){
		if(source==null||source.trim().equals("")){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 验证VIN的合法性
	 * @param source 需要校验的字符串
	 * @param flag  标识是否需要验证非空
	 * @return 如果验证通过，则返回null,否则返回错误信息。
	 */
	public static String checkVINAll(String source,boolean flag){
		StringBuffer sb = new StringBuffer();
		if(flag ==true){
			if(checkNull(source)){
				sb.append("不能为空");
			}
		}
		if(!checkNull(source)){
			if(source.length()!=17){
				if(sb.length()>0){
					sb.append(",长度必须为17位");
				}else{
					sb.append("长度必须为17位");
				}
			}
			if(!ValidateUtil.isVin(source)){
				if(sb.length()>0){
					sb.append(",必须为大写字母,数字组成");
				}else{
					sb.append("必须为大写字母,数字组成");
				}
			}
		}
		StringBuffer sb2 = new StringBuffer();
		if(sb.length()>0){
			sb2.append("VIN:").append(sb.toString());
		}
		return sb2.toString();
	}
	/**
	 * 对一般输入框进行校验
	 * @param source  需要校验的字符串
	 * @param flag  标识是否需要验证非空
	 * @param name 需要验证的标识
	 * @return 如果验证通过，则返回null,否则返回错误信息。
	 */
	public static String checkDescAll(String source,int length,String name,boolean flag){
		StringBuffer sb = new StringBuffer();
		if(flag ==true){
			if(checkNull(source)){
				sb.append("不能为空");
			}
		}
		if(!checkNull(source)){
			if(source.length()>length){
				if(sb.length()>0){
					sb.append(",长度不能超过"+length);
				}else{
					sb.append("长度不能超过"+length);
				}
			}
			if(!ValidateUtil.isNoquotation(source,0,100)){
				if(sb.length()>0){
					sb.append(",不能含有如下字符:%'");
				}else{
					sb.append("不能含有如下字符:%'");
				}
			}
		}
		StringBuffer sb2 = new StringBuffer();
		if(sb.length()>0){
			sb2.append(name+":").append(sb.toString());
		}
		return sb2.toString();
	}
	/**
	 * 检查所检查的值是否在这某几个值之间
	 * @param souce
	 * @param checkValue
	 * @param name
	 * @param flag
	 * @return
	 */
	public static String checkValue(String source,String[] checkValue,String name,boolean flag){
		StringBuffer sb = new StringBuffer();
		if(flag){
			if(checkNull(source)){
				sb.append("不能为空");
			}
		}
		if(!checkNull(source)){
			boolean isHave = false;
			for(String str:checkValue){
				if(str.equals(source)){
					isHave = true;
					break;
				}
			}
			if(!isHave){
				if(sb.length()>0){
					sb.append(",只能为：").append(getArrayString(checkValue)).append("之一");
				}else{
					sb.append("只能为：").append(getArrayString(checkValue)).append("之一");
				}
			}
		}
		StringBuffer sb2 = new StringBuffer();
		if(sb.length()>0){
			sb2.append(name+":").append(sb.toString());
		}
		return sb2.toString();
	}
	/**
	 * 将检查值组织成字符串
	 * @param checkValue
	 * @return
	 */
	private static String getArrayString(String[] checkValue){
		StringBuffer sb = new StringBuffer();
		for(String str:checkValue){
			sb.append(str+",");
		}
		return sb.substring(0, sb.length()-1);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//System.out.println(checkVINAll("dddddd",true));
	}
}
