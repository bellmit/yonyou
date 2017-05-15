package com.yonyou.dcs.dao;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.UrlFunctionDTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class UrlFunctionDao extends OemBaseDAO {
	
	/**
	 * 
	* @Title: queryUrlFunction 
	* @Description: TODO(根据id列表查询url功能列表) 
	* @param @param ids 
	* @param @return  FUNC_ID, PAR_FUNC_ID, FUNC_CODE, FUNC_NAME, SORT_ORDER
	* @return List<UrlFunctionDTO>    返回类型 
	* @throws
	 */
	public LinkedList<UrlFunctionDTO> queryUrlFunction() {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT FUNC_ID, PAR_FUNC_ID, FUNC_CODE, FUNC_NAME, SORT_ORDER \n");
		sql.append(" FROM TC_URL_FUNC \n");
		LinkedList<UrlFunctionDTO> list = wrapperVO(OemDAOUtil.findAll(sql.toString(), null));
		return list;
	}
	
	protected LinkedList<UrlFunctionDTO> wrapperVO(List<Map> rs) {
		LinkedList<UrlFunctionDTO> resultList = new LinkedList<UrlFunctionDTO>();
		try {
			if(null!=rs && rs.size()>0){
				for (int i = 0; i < rs.size(); i++) {
					UrlFunctionDTO dto = new UrlFunctionDTO();
					dto.setFunctionName(CommonUtils.checkNull(rs.get(i).get("FUNC_NAME")));
					dto.setUrl(CommonUtils.checkNull(rs.get(i).get("FUNC_CODE")));
					dto.setParentFunctionCode(CommonUtils.checkNull(rs.get(i).get(("PAR_FUNC_ID"))));
					dto.setDcsFunctionId(CommonUtils.checkNull(rs.get(i).get(("FUNC_ID"))));
					dto.setSort(Integer.parseInt(CommonUtils.checkNull(rs.get(i).get("SORT_ORDER"), "0")));
					resultList.add(dto);
				}
			}
			
		} catch (Exception e) {
			throw new ServiceBizException(e);
		}
		return resultList;
	}
}