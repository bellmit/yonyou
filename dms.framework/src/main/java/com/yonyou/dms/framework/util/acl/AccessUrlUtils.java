package com.yonyou.dms.framework.util.acl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.common.FrameworkConstants;
import com.yonyou.dms.framework.domain.ApplicationAccessUrlDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.utils.common.StringUtils;



/**
*集合遍历工具
* @author zhangxc
* @IteratorUtils
* @date 2016年11月23日
*/

public class AccessUrlUtils {

	
	 /**
     * 将权限url的List<Map〉集合元素转化为特定数组形式
     * @author zhangxc
     * @date 2016年11月23日
     * @param list
     * @return 字符串数组
     */
	@SuppressWarnings("rawtypes")
	public static String[]  iteratorToArray(List<Map> list){
		Iterator<Map> urlIterator = list.iterator();
		String[] urlString = new String[list.size()];
		int i = 0;  
		while(urlIterator.hasNext()){
			Map urlMap = urlIterator.next();
			String method = (String)urlMap.get("method");
			String model = (String) urlMap.get("model");
			String resultUrl = (String) urlMap.get("code");
		    resultUrl = resultUrl.replaceAll("\\{[^/]+\\}", ".+");
		    StringBuilder url = new StringBuilder(method);
		    url.append(FrameworkConstants.ACL_RESOUCCE_SPLIT).append(model).append(FrameworkConstants.ACL_RESOUCCE_SPLIT).append(resultUrl);
		    urlString[i]=url.toString();
		    i++;
		}
	    return urlString;
	}	
	 /**
     * 将权限url的List<Map〉集合元素转化为特定字符串形式
     * @author zhangxc
     * @date 2016年11月23日
     * @param list
     * @return 字符串
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String  iteratorToString(List powerList){
		Iterator<Map> powerIterator = powerList.iterator();
		StringBuilder powerResult = new StringBuilder();
		if(powerList.size()==0){
		    powerResult.append("' ',");
		}else if(powerList.size()==1){
			powerResult.append("'").append(powerList.get(0)).append("',");
		}else{
			while(powerIterator.hasNext()){
			   powerResult.append("'").append(powerIterator.next()).append("',");
			}
		}
		String result = powerResult.toString();
		result = result.substring(0, result.length()-1);
		return result;
	}
	
	
	/**
	* 将维修和配件数据权限转为Map集合
	* @author Administrator
	* @date 2016年11月29日
	* @param powerList
	* @return
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map iteratorToMap(List powerList,List<Map> typeList){
	    StringBuilder sb = new StringBuilder();
	    Iterator<Map> ti = typeList.iterator();
	    while(ti.hasNext()){
	        sb.append(ti.next().get("CODE_ID")).append(",");
	    }
	    String[] types = sb.toString().split(",");
	    Map powerResult = new HashMap();
	    for(String type: types){
	        powerResult.put(type, false);
	    }
	    Iterator powerIterator = powerList.iterator();
	    while(powerIterator.hasNext()){
	        powerResult.put(powerIterator.next(), true);
	    }
	    return powerResult;
	}
	
	/**
	 * 
	* 判断是否是系统默认的URL,只要登录都可以访问的URL
	* @author zhangxc
	* @date 2016年12月10日
	* @param url
	* @param method
	* @return
	 */
	public static boolean isApplicationDefaultUrl(String url,String method){
	    ApplicationAccessUrlDto userAuthDto = ApplicationContextHelper.getBeanByType(ApplicationAccessUrlDto.class);
	    String[] defaultUrl = userAuthDto.getDafaultUrl();
	    String accessUrl = method+FrameworkConstants.ACL_RESOUCCE_SPLIT+url;
	    for(int i=0;i<defaultUrl.length;i++){
	        String[] splitStr = defaultUrl[i].split(FrameworkConstants.ACL_RESOUCCE_SPLIT);
	        if(isValidUrl(accessUrl,splitStr[0],splitStr[1],splitStr[2])){
	            return true;
	        }
	    }
	    return false;
	}
	
	/**
	 * 
	* 判断是否是合法的URL
	* @author zhangxc
	* @date 2016年12月10日
	* @param url
	* @param method
	* @param module
	* @param actionurl
	* @return
	 */
	public static boolean isValidUrl(String url,String method,String module,String actionurl){
	    String pattern = method+FrameworkConstants.ACL_RESOUCCE_SPLIT+"/[^/]+/"+module+"/[^/]+"+actionurl;
        return StringUtils.isMatcherPatten(pattern, url);
	}
}
