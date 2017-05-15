package com.yonyou.dms.vehicle.dao.afterSales.warranty;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TmpVsYearlyPlanPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsYearlyPlanPO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TmpWrOperateDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TmpWrOperatePO;

/**
 * @author xuqinqin
 * @date 2017年04月25日
 */
@Repository
public class WarrantyOptCodeDao extends OemBaseDAO {

	/**
	 * 操作代码查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getOptCodeQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOptCodeQuerySql(queryParam, params,"query");
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 操作代码下载
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getOptCodeDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOptCodeQuerySql(queryParam, params,"down");
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * SQL组装操作代码
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getOptCodeQuerySql(Map<String, String> queryParam, List<Object> params,String operate) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TWO.ID,TWO.OPT_CODE,TWO.OPT_NAME_EN,TWO.OPT_NAME_CN,TC.CODE_CN_DESC AS STATUS,TU1.NAME AS CREATE_BY,TU2.NAME AS UPDATE_BY, \n  ");
		sql.append("  DATE_FORMAT(TWO.CREATE_DATE,'%Y-%m-%d') CREATE_DATE,DATE_FORMAT(TWO.UPDATE_DATE,'%Y-%m-%d') UPDATE_DATE   \n");
		sql.append(" FROM TT_WR_OPERATE_DCS TWO  \n");
		sql.append(" LEFT JOIN TC_CODE TC ON TC.CODE_ID =TWO.STATUS  \n");
		sql.append(" LEFT OUTER JOIN Tc_user TU1 ON TU1.USER_ID=TWO.CREATE_BY	 \n");
		sql.append(" LEFT OUTER JOIN Tc_user TU2 ON TU2.USER_ID=TWO.UPDATE_BY  WHERE 1=1	\n  ");
        //操作代码
        if(!StringUtils.isNullOrEmpty(queryParam.get("optCode"))){ 
        	sql.append(" 	AND TWO.OPT_CODE LIKE '%" + queryParam.get("optCode") + "%' \n");
        }  
        if(operate.equals("down")){//下载排序
        	sql.append(" 	ORDER BY TWO.ID DESC,TWO.UPDATE_DATE DESC \n");
        }
		System.out.println(sql.toString());
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: insertTmpOptCode 
	* @Description: 操作代码(导入临时表)
	* @param @param rowDto    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void insertTmpOptCode(TmpWrOperateDTO twDTO) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TmpWrOperatePO twPO = new TmpWrOperatePO();
		//设置对象属性
		twPO.setString("LINE_NO", twDTO.getRowNO());
		twPO.setString("OPT_CODE", twDTO.getOptCode());
		twPO.setString("OPT_NAME_CN", twDTO.getOptNameCn());
		twPO.setLong("CREATE_BY", loginInfo.getUserId().toString());
		twPO.setDate("CREATE_DATE", new Date());
		twPO.saveIt();
	}
	/**
	 * 
	* @Title: checkData 
	* @Description: 操作代码导入（ 校验临时表数据） 
	* @param @param rowDto
	* @param @return    设定文件 
	* @return List<ImportResultDto>    返回类型 
	* @throws
	 */
	public ImportResultDto<TmpWrOperateDTO> checkData(TmpWrOperateDTO rowDto) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuilder fullQuery = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		fullQuery.append("SELECT * FROM tmp_wr_operate_dcs tw WHERE 1=1 AND tw.CREATE_BY = " + loginInfo.getUserId());
		List<Map> twList = OemDAOUtil.findAll(fullQuery.toString(), params);
		if(twList == null || twList.size()<0){
			twList = new ArrayList<Map>();
		}
		Map<String,Object> twMap = new HashMap<String,Object>();
		StringBuffer errorInfo = new StringBuffer("");
		boolean isError = false;
		ArrayList<TmpWrOperateDTO> twDTOList = new ArrayList<TmpWrOperateDTO>();
		ImportResultDto<TmpWrOperateDTO> importResult = new ImportResultDto<TmpWrOperateDTO>();
		for(int i=0; i < twList.size(); i++){
			twMap = twList.get(i);
			// 取得行号
			Integer rowNum = Integer.valueOf((String) twMap.get("LINE_NO"));
			String optCode=(String) twMap.get("OPT_CODE");
			String optNameCn=(String) twMap.get("OPT_NAME_CN");
			
			if (optCode.trim().length() == 0) {
				isError = true;
				rowDto.setRowNO(new Integer(rowNum));
				rowDto.setErrorMsg("操作代码不能为空");
				twDTOList.add(rowDto);
				importResult.setErrorList(twDTOList);
			}else if (optCode.trim().length() != 7) {
				isError = true;
				rowDto.setRowNO(new Integer(rowNum));
				rowDto.setErrorMsg("操作代码长度有误(应为7个字符)");
				twDTOList.add(rowDto);
				importResult.setErrorList(twDTOList);
			}else if (!optCode.trim().matches("[0-9a-zA-Z]{7}")) {
				isError = true;
				rowDto.setRowNO(new Integer(rowNum));
				rowDto.setErrorMsg("操作代码是7位可以是字母，数字或者字母和数字的组合");
				twDTOList.add(rowDto);
				importResult.setErrorList(twDTOList);
			}
			if (optNameCn.trim().length() > 30) {
				isError = true;
				rowDto.setRowNO(new Integer(rowNum));
				rowDto.setErrorMsg("操作描述(中文)过长(不得超过30个字)");
				twDTOList.add(rowDto);
				importResult.setErrorList(twDTOList);
			}
			if (errorInfo.length() > 0) {
				String info = errorInfo.substring(0, errorInfo.length() - 1);
				rowDto.setRowNO(new Integer(rowNum));
				rowDto.setErrorMsg(info);
				twDTOList.add(rowDto);
				importResult.setErrorList(twDTOList);
			}
		}
		// 临时表校验重复数据
		List<Object> params4 = new ArrayList<Object>();
		String fullQuery4 = getOemTalbeCheckDumpSql(loginInfo);
		List<Map> dumpList = OemDAOUtil.findAll(fullQuery4, params4);
		String errorSb = "";
		if (null != dumpList && dumpList.size() > 0) {
			isError = true;
			for (int i = 0; i < dumpList.size(); i++) {
				Map<String, Object> map = dumpList.get(i);
				errorSb+=map.get("ROW_NUMBER").toString();
				if(i<dumpList.size()-2){
					errorSb+=",";
				}
			}
			Map<String, Object> map2 = dumpList.get(dumpList.size()-1);
			TmpWrOperateDTO rowDto2 = new TmpWrOperateDTO();
			rowDto.setRowNO(Integer.parseInt(map2.get("ROW_NUMBER").toString()));
			rowDto.setErrorMsg("与第"+errorSb+"行数据重复");
			twDTOList.add(rowDto);
			importResult.setErrorList(twDTOList);
		}
		if (isError) {
			return importResult;
		} else {
			return null;
		}
	}
	// 临时表校验重复数据
	private String getOemTalbeCheckDumpSql( LoginInfoDto loginInfo) {
		StringBuilder sql = new StringBuilder();
		sql.append("select p1.LINE_NO\n");
		sql.append("  from tmp_wr_operate_dcs p1\n");
		sql.append(" where exists (\n");
		sql.append("   select 1\n");
		sql.append("   from tmp_wr_operate_dcs p2\n");
		sql.append("   where p1.opt_code = p2.opt_code\n");
		sql.append("   and p1.opt_name_cn = p2.opt_name_cn\n");
		sql.append("   and p1.LINE_NO <> p2.LINE_NO\n");
		sql.append("   and p1.create_by =" + loginInfo.getUserId() + ")\n");
		System.out.println(sql.toString());
		return sql.toString();
	}
}
