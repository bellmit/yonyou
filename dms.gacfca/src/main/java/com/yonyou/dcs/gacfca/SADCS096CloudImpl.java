package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SaleVehicleSaleDao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.BigCustomerAuthorityApprovalDto;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerAuthorityApprovalPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
 * Title:SADCS096CloudImpl
 * Description: 大客户组织架构权限审批数据接收
 * @author DC
 * @date 2017年4月10日 下午6:29:31
 * result msg 1：成功 0：失败
 */
@Service
public class SADCS096CloudImpl extends BaseCloudImpl implements SADCS096Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS096CloudImpl.class);
	
	@Autowired
	SaleVehicleSaleDao saleDao;

	@Override
	public String handleExecutor(List<BigCustomerAuthorityApprovalDto> dtoList) throws Exception {
		String msg = "1";
		logger.info("*************************** 开始获取大客户组织架构权限上传数据 ******************************");
		for (BigCustomerAuthorityApprovalDto entry : dtoList) {
			try {
				dealUpBigCustomerData(entry);
			} catch (Exception e) {
				logger.error("经销商零售信息变更上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
			logger.info("*************************** 成功获取大客户组织架构权限上传数据 **结束*************************");
		}
		return msg;
	}

	@SuppressWarnings("unchecked")
	private void dealUpBigCustomerData(BigCustomerAuthorityApprovalDto authorityVo) throws Exception{
		logger.info("##################### 开始处理大客户组织架构权限审批上传数据 ############################");
		if(null == authorityVo.getEntityCode() || "".equals(authorityVo.getEntityCode())){
			throw new Exception("DE消息中EntityCode为空！");
		}
		Map<String, Object> map = saleDao.getSaDcsDealerCode(authorityVo.getEntityCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商Code 
		String dealerName = String.valueOf(map.get("DEALER_NAME"));// 上报经销商名称 
		//验证是否存在报备单(用于判断是否首次报备)
		TtBigCustomerAuthorityApprovalPO keyFilingAuthorityPO = new TtBigCustomerAuthorityApprovalPO();
		/***报备单号+经销商确定唯一***/
		List<TtBigCustomerAuthorityApprovalPO> baseInfoList = TtBigCustomerAuthorityApprovalPO.find("APPLY_NO = ? AND DEALER_CODE = ? ",authorityVo.getApplyNo().trim(), dealerCode);
		if(null != baseInfoList && baseInfoList.size() > 0){
			keyFilingAuthorityPO = baseInfoList.get(0);
			keyFilingAuthorityPO.setString("APPLY_REMARK", authorityVo.getApplyRemark());//申请理由
			keyFilingAuthorityPO.setDate("AUTHORITY_APPLY_DATE", authorityVo.getAuthorityApplyDate());//权限申请日期
			keyFilingAuthorityPO.setInteger("AUTHORITY_APPROVAL_STATUS", OemDictCodeConstants.BIG_CUSTOMER_AUTHORITY_APPROVAL_TYPE_UNAPPROVED);//审批状态
			keyFilingAuthorityPO.setString("EMP_CODE", authorityVo.getEmpCode());//申请员工代码(为哪个员工申请这个权限)
			keyFilingAuthorityPO.setString("USER_CODE", authorityVo.getUserCode());//申请用户(操作人)
			keyFilingAuthorityPO.setString("USER_NAME", authorityVo.getUserName());//申请用户名称
			keyFilingAuthorityPO.setString("ORIGINAL_STATION", authorityVo.getOriginalStation());//原岗位
			keyFilingAuthorityPO.setString("PARTTIME_STATION", authorityVo.getParttimeStation());//兼职岗位
			keyFilingAuthorityPO.setString("CONTACTOR_MOBILE", authorityVo.getContactorMobile());//手机号
			//是否兼职
			if(authorityVo.getIsParttime().equals("12781001")){
				keyFilingAuthorityPO.setInteger("IS_PARTTIME", OemDictCodeConstants.IF_TYPE_YES);//是
			}else{
				keyFilingAuthorityPO.setInteger("IS_PARTTIME", OemDictCodeConstants.IF_TYPE_NO);//否
			}
			keyFilingAuthorityPO.setString("SELF_EVALUTION", authorityVo.getSelfEvalution());//自我评价
			keyFilingAuthorityPO.setFloat("BRAND_YEAR", Float.parseFloat(authorityVo.getBrandYear().toString()));//本品牌工作年限
			keyFilingAuthorityPO.setLong("UPDATE_BY", DEConstant.DE_CREATE_BY);
			keyFilingAuthorityPO.setTimestamp("UPDATE_DATE", new Date());
			keyFilingAuthorityPO.saveIt();
			logger.info("************* 成功更新申请单号("+authorityVo.getApplyNo()+")*******************");
		}else{
			keyFilingAuthorityPO.setString("DEALER_NAME", dealerName);//经销商名称
			keyFilingAuthorityPO.setString("APPLY_REMARK", authorityVo.getApplyRemark());//申请理由
			keyFilingAuthorityPO.setDate("AUTHORITY_APPLY_DATE", authorityVo.getAuthorityApplyDate());//权限申请日期
			keyFilingAuthorityPO.setInteger("AUTHORITY_APPROVAL_STATUS", OemDictCodeConstants.BIG_CUSTOMER_AUTHORITY_APPROVAL_TYPE_UNAPPROVED);//审批状态
			keyFilingAuthorityPO.setString("EMP_CODE", authorityVo.getEmpCode());//申请员工代码(为哪个员工申请这个权限)
			keyFilingAuthorityPO.setString("USER_CODE", authorityVo.getUserCode());//申请用户(操作人)
			keyFilingAuthorityPO.setString("USER_NAME", authorityVo.getUserName());//申请用户名称
			keyFilingAuthorityPO.setString("ORIGINAL_STATION", authorityVo.getOriginalStation());//原岗位
			keyFilingAuthorityPO.setString("PARTTIME_STATION", authorityVo.getParttimeStation());//兼职岗位
			keyFilingAuthorityPO.setString("CONTACTOR_MOBILE", authorityVo.getContactorMobile());//手机号
			//是否兼职
			if(authorityVo.getIsParttime().equals("12781001")){
				keyFilingAuthorityPO.setInteger("IS_PARTTIME", OemDictCodeConstants.IF_TYPE_YES);//是
			}else{
				keyFilingAuthorityPO.setInteger("IS_PARTTIME", OemDictCodeConstants.IF_TYPE_NO);//否
			}
			keyFilingAuthorityPO.setString("SELF_EVALUTION", authorityVo.getSelfEvalution());//自我评价
			keyFilingAuthorityPO.setFloat("BRAND_YEAR", Float.parseFloat(authorityVo.getBrandYear().toString()));//本品牌工作年限
			keyFilingAuthorityPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);
			keyFilingAuthorityPO.setTimestamp("CREATE_DATE", new Date());
			keyFilingAuthorityPO.insert();
			logger.info("*************************** 成功写入组织架构权限表(TT_BIG_CUSTOMER_AUTHORITY_APPROVAL) ******************************");
		}
		
	}

}
