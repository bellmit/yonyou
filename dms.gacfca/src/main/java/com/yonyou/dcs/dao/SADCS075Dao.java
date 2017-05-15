package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.VoucherDTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SADCS075Dao extends OemBaseDAO {
	

	/**
	 * 查询下发数据
	 * @return
	 */
	public List<VoucherDTO> queryVoucherDTO(String actId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  \n");
		sql.append("  TA.ACTIVITY_CODE, \n");//活动编号
		sql.append("  TA.ACTIVITY_NAME, \n");//活动名称
		sql.append("  NVL(TO_CHAR(TA.ACTIVITY_START_DATE,'YYYY-MM-DD'),'') AS ACTIVITY_START_DATE , \n");//活动开始日期
		sql.append("  NVL(TO_CHAR(TA.ACTIVITY_END_DATE,'YYYY-MM-DD'),'') AS ACTIVITY_END_DATE , \n");//活动结束日期
		sql.append("  TA.ACTIVITY_ISSUE_NUMBER, \n");//活动发放卡券数量上限
		sql.append("  TA.RELEASE_STATUS, \n");//发布状态
		sql.append("  TA.INSURANCE_COMPANY_CODE, \n");//保险公司代码
		sql.append("  TICM.INSURANCE_COMPANY_NAME,  \n");//保险公司名称
		sql.append("  TV.VOUCHER_NO,\n"); //卡券类型ID
		sql.append("  TV.VOUCHER_NAME, \n"); //卡券类型名称
		sql.append("  TV.VOUCHER_TYPE,\n"); //卡券类型
		sql.append("  TV.SINGLE_AMOUNT,  \n");//单张抵扣金额
		sql.append("  TV.ISSUE_NUMBER_LIMIT, \n");//单次最多发放数量
		sql.append("  NVL(TO_CHAR(TV.START_DATE,'YYYY-MM-DD'),'') AS START_DATE, \n");//开始日期
		sql.append("  NVL(TO_CHAR(TV.END_DATE,'YYYY-MM-DD'),'') AS END_DATE,\n"); // 结束日期
		sql.append("  TV.VOUCHER_STANDARD, \n");// 发券标准
		sql.append("  TV.USE_RANGE, \n");// 使用范围
		sql.append("  TV.REPAIR_TYPE, \n");//维修类型代码
		sql.append("  TV.REPAIR_TYPE_NAME \n");//维修类型名称
		sql.append("FROM TM_INSURANCE_ACTIVITY TA  \n");
		sql.append("LEFT JOIN  TM_VOUCHER TV ON TV.ACTIVITY_CODE=TA.ACTIVITY_CODE \n");
		sql.append("LEFT JOIN  TT_INSURANCE_COMPANY_MAIN TICM ON TICM.INSURANCE_COMPANY_CODE=TA.INSURANCE_COMPANY_CODE \n");
		sql.append("WHERE TA.IS_DEL <>'1' AND TV.IS_DEL <>'1'\n");
		sql.append(" AND TA.ID='"+actId+"' \n");//活动主键
		sql.append(" AND TA.RELEASE_STATUS in('"+OemDictCodeConstants.RELEASE_STATUS_01+"','"+OemDictCodeConstants.RELEASE_STATUS_02+"') OR TA.RELEASE_STATUS IS NULL \n");//未下发、已发布
		
		List<Map> listmap=OemDAOUtil.findAll(sql.toString(), null);
		List<VoucherDTO> dtolist=null;
		if(null!=listmap&&listmap.size()>0){
			dtolist=new ArrayList<>();
			for(int i=0;i<dtolist.size();i++){
				VoucherDTO dto = new VoucherDTO();
				dto.setActivityCode(CommonUtils.checkNull(listmap.get(i).get("ACTIVITY_CODE")));//活动编号
				dto.setActivityName(CommonUtils.checkNull(listmap.get(i).get("ACTIVITY_NAME")));//活动名称
				dto.setActivityStartDate(CommonUtils.parseDate(CommonUtils.checkNull(listmap.get(i).get("ACTIVITY_START_DATE"))));//活动开始日期
				dto.setActivityEndDate(CommonUtils.parseDate(CommonUtils.checkNull(listmap.get(i).get("ACTIVITY_END_DATE"))));//活动结束日期
				dto.setActivityIssueNumber(Integer.parseInt(CommonUtils.checkNull(listmap.get(i).get("ACTIVITY_ISSUE_NUMBER"))));//活动发放卡券数量上限
				dto.setReleaseStatus(Integer.parseInt(CommonUtils.checkNull(listmap.get(i).get("RELEASE_STATUS")))==OemDictCodeConstants.RELEASE_STATUS_01?OemDictCodeConstants.RELEASE_STATUS_02:OemDictCodeConstants.RELEASE_STATUS_03);//发布状态
				dto.setInsuranceCompanyCode(CommonUtils.checkNull(listmap.get(i).get("INSURANCE_COMPANY_CODE")));//保险公司code
				dto.setInsuranceCompanyName(CommonUtils.checkNull(listmap.get(i).get("INSURANCE_COMPANY_NAME")));//保险公司简称
				
				dto.setVoucherNo(CommonUtils.checkNull(listmap.get(i).get("VOUCHER_NO")));//卡券类型ID
				dto.setVoucherName(CommonUtils.checkNull(listmap.get(i).get("VOUCHER_NAME")));//卡券类型名称
				dto.setVoucherType(Integer.parseInt(CommonUtils.checkNull(listmap.get(i).get("VOUCHER_TYPE"))));//卡券类型
				dto.setSingleAmount(Float.parseFloat(CommonUtils.checkNull(listmap.get(i).get("SINGLE_AMOUNT"))));//单张抵扣金额
				dto.setIssueNumberLimit(Integer.parseInt(CommonUtils.checkNull(listmap.get(i).get("ISSUE_NUMBER_LIMIT"))));//单次最多发放数量
				dto.setStartDate(CommonUtils.parseDate(CommonUtils.checkNull(listmap.get(i).get("START_DATE"))));//开始日期
				dto.setEndDate(CommonUtils.parseDate(CommonUtils.checkNull(listmap.get(i).get("END_DATE"))));//结束日期
				dto.setVoucherStandard(Integer.parseInt(CommonUtils.checkNull(listmap.get(i).get("VOUCHER_STANDARD"))));//发券标准
				dto.setUseRange(Integer.parseInt(CommonUtils.checkNull(listmap.get(i).get("USE_RANGE"))));//使用范围
				dto.setRepairType(CommonUtils.checkNull(listmap.get(i).get("REPAIR_TYPE")));//维修类型代码
				dto.setRepairTypeName(CommonUtils.checkNull(listmap.get(i).get("REPAIR_TYPE_NAME")));//维修类型名称
				dtolist.add(dto);
			}
		}
		
		return dtolist;
	}
}
