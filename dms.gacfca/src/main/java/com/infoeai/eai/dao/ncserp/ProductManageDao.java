/**
 * @Title: ProductManageDao.java 
 * @Copyright: Copyright (c) 2012
 * @Company: http://autosoft.ufida.com
 * @Date: 2012-5-9
 * @author lichuang
 * @version 1.0
 */
package com.infoeai.eai.dao.ncserp;

import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * @author lichuang
 * 
 */
@Repository
public class ProductManageDao extends OemBaseDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductManageDao.class);
	
	/**
	 * 获得物料组的树代码
	 * @param parentTreeCode
	 * @return
	 */
	public String getTreeCode(String parentTreeCode){
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT MAX(TREE_CODE) AS TREE_CODE,COUNT(*) AS COUNT FROM TM_VHCL_MATERIAL_GROUP");
		sql.append(" WHERE TREE_CODE LIKE '"+parentTreeCode+"___'");
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		
		String count=String.valueOf(map.get("COUNT"));
		String str=String.valueOf(map.get("TREE_CODE"));
		String treeCode="";
		if(count==null || count=="") {
			return "";
		}
		//如果count值为0说明上级物料组没有子节点
		if("0".equals(count)){
			treeCode=parentTreeCode+="A01";
		//如果count不为0说明已经有子节点
		}else {
			if(null!=str && !"".equals(str)){
				String t1=str.substring(str.length()-2, str.length());
				String t2 = Integer.parseInt(t1)+1+"";
				String t3=str.substring(0,str.length()-2);
				if(t2.length()==1){
				treeCode=t3+"0"+t2;
				}else{
					treeCode=t3+t2;
				}
			}
		}
		return treeCode;
	}
	
	/**
	 * 字符串排序
	 * @return
	 */
	public String orderByStr(String str){
//		StringBuffer sql = new StringBuffer();
//		sql.append("select ordertext('"+str+"') val from sysibm.sysdummy1");
//		Map<String, Object> map = dao.pageQueryMap(sql.toString(), null, getFunName());
//		String rs = "";
//		if(null!=map){
//			rs = CommonUtils.checkNull(map.get("VAL"));
//		}
		String result = "";
		String[] strNames = str.split(",");
		Arrays.sort(strNames);
		for (String string : strNames) {
			if("".equals(result)){
				result = string;
			}else{
				result =result +","+ string;
				
			}
		}
		return result;
	}
	
	
}
