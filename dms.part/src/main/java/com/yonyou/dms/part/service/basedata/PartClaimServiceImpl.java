package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtPtClaimDcsPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.dao.PartClaimManageDao;
import com.yonyou.dms.part.domains.DTO.basedata.TtptClaimDcsDTO;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TtPtClaimHistoryDcsPO;

/**
 * 直发交货单
 * @author ZhaoZ
 *@date 2017年3月27日
 */
@Service
public class PartClaimServiceImpl implements PartClaimService{

	@Autowired
	private  PartClaimManageDao partDao;
	
	/**
	 * 到货索赔审核查询
	 */
	@Override
	public PageInfoDto checkClaim(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.QuerycheckClaim(queryParams);
	}

	/**
	 * 索赔基本信息
	 */
	@Override
	public Map<String, Object> checkDetail(BigDecimal id) throws ServiceBizException {
		return partDao.getCheckDetail(id);
	}

	/**
	 * 索赔订单详细信息
	 */
	@Override
	public Map<String, Object> findClaimDetailInfoByClaimId(BigDecimal claimId) throws ServiceBizException {
		return partDao.getClaimDetailInfo(claimId);
	}

	/**
	 * 查询附件
	 */
	@Override
	public PageInfoDto findCheckInfoByClaimId(BigDecimal claimId) throws ServiceBizException {
		return partDao.checkInfoByClaimId(claimId);
	}

	/**
	 * 补发交货单录入
	 */
	@Override
	public void saveReissueTransNo(TtptClaimDcsDTO dto, BigDecimal id) throws ServiceBizException {
		boolean flag1 = false;
		if(StringUtils.isNullOrEmpty(id)){
			throw new ServiceBizException("补发交货单录入失败");
		}
		TtPtClaimDcsPO po = TtPtClaimDcsPO.findById(id);
		if(po!=null){
			if(!StringUtils.isNullOrEmpty(dto.getReissueTransNo())){
				po.setString("REISSUE_TRANS_NO",dto.getReissueTransNo());
			}
			po.setInteger("CLAIM_STATUS", OemDictCodeConstants.PART_CLAIM_STATUS_08);
			po.setInteger("IS_REISSUE_SEND", 10041002);
			flag1 = po.saveIt();
			if(flag1){			
			}else{
				throw new ServiceBizException("补发交货单录入失败！");
			}
		}
		
	}

	/**
	 * 同意   拒绝  驳回
	 */
	@Override
	public void checkAgree(TtptClaimDcsDTO dto, BigDecimal id, String type) throws ServiceBizException {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	long time= System.currentTimeMillis();
		boolean flag1 = false;
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String dutyBy = dto.getDutyBy();
		if(StringUtils.isNullOrEmpty(id)){
			throw new ServiceBizException("审核失败");
		}
		TtPtClaimDcsPO Po = TtPtClaimDcsPO.findById(id);
		if(Po!=null){
			if (type.equals("1")) {//审核通过
				Po.setInteger("CLAIM_STATUS",OemDictCodeConstants.PART_CLAIM_STATUS_04);
			} else if (type.equals("2")) {//拒绝
				Po.setInteger("CLAIM_STATUS",OemDictCodeConstants.PART_CLAIM_STATUS_06);
			} else if (type.equals("3")) {//驳回
				Po.setInteger("CLAIM_STATUS",OemDictCodeConstants.PART_CLAIM_STATUS_05);
			}
			if(!StringUtils.isNullOrEmpty(dutyBy)){
				if (dutyBy.equals("1")) {//供应商
					Po.setInteger("DUTY_BY",OemDictCodeConstants.PART_CLAIM_BUSINESS_01);
				} else if (dutyBy.equals("2")) {//运营商
					Po.setInteger("DUTY_BY",OemDictCodeConstants.PART_CLAIM_BUSINESS_02);
				} else if (dutyBy.equals("3")) {//广菲克
					Po.setInteger("DUTY_BY",OemDictCodeConstants.PART_CLAIM_BUSINESS_03);
				}else if (dutyBy.equals("4")) {//仓库运营方
					Po.setInteger("DUTY_BY",OemDictCodeConstants.PART_CLAIM_BUSINESS_04);
				}
			}
			
			Po.setLong("UPDATE_BY", loginInfo.getUserId());
			if(!StringUtils.isNullOrEmpty(dto.getAuditOptions())){
				Po.setInteger("AUDIT_OPTIONS", new Integer(dto.getAuditOptions()));
			}
			if(!StringUtils.isNullOrEmpty(dto.getCheckOpinion())){
				Po.setString("AUDIT_REMARK", dto.getCheckOpinion());
			}
			try {
	    		Date date = sdf.parse(sdf.format(new Date(time)));
	    		java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
	    		Po.setTimestamp("UPDATE_DATE", st);
	    	} catch (ParseException e) {
	    		e.printStackTrace();
	    	} 	
			Po.setInteger("IS_DCS_SEND", OemDictCodeConstants.IF_TYPE_NO);
			flag1 = Po.saveIt();
			if(flag1){			
			}else{
				throw new ServiceBizException("审核失败！");
			}
		}
		
		
		TtPtClaimHistoryDcsPO historyPo = new TtPtClaimHistoryDcsPO();
		historyPo.setBigDecimal("CLAIM_ID", id);
		if (type.equals("1")) {//审核通过
			historyPo.setInteger("CHECK_STATUS",OemDictCodeConstants.PART_CLAIM_STATUS_04);
		} else if (type.equals("2")) {//拒绝
			historyPo.setInteger("CHECK_STATUS",OemDictCodeConstants.PART_CLAIM_STATUS_06);
		} else if (type.equals("3")) {//驳回
			historyPo.setInteger("CHECK_STATUS",OemDictCodeConstants.PART_CLAIM_STATUS_05);
		}
		if(!StringUtils.isNullOrEmpty(dto.getCheckOpinion())){
			historyPo.setString("CHECK_OPINION", dto.getCheckOpinion());
		}
    	try {
    		Date date = sdf.parse(sdf.format(new Date(time)));
    		java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
    		historyPo.setTimestamp("CHECK_DATE", st);
    		historyPo.setTimestamp("CREATE_DATE", st);
    	} catch (ParseException e) {
    		e.printStackTrace();
    	} 	
		
		historyPo.setLong("CREATE_BY", loginInfo.getUserId());
		if(!StringUtils.isNullOrEmpty(dto.getAuditOptions())){
			historyPo.setInteger("AUDIT_OPTIONS", new Integer(dto.getAuditOptions()));
		}
		if(!StringUtils.isNullOrEmpty(dutyBy)){
			if (dutyBy.equals("1")) {//供应商
				historyPo.setInteger("DUTY_BY",OemDictCodeConstants.PART_CLAIM_BUSINESS_01);
			} else if (dutyBy.equals("2")) {//运营商
				historyPo.setInteger("DUTY_BY",OemDictCodeConstants.PART_CLAIM_BUSINESS_02);
			} else if (dutyBy.equals("3")) {//广菲克
				historyPo.setInteger("DUTY_BY",OemDictCodeConstants.PART_CLAIM_BUSINESS_03);
			}else if (dutyBy.equals("4")) {//仓库运营方
				historyPo.setInteger("DUTY_BY",OemDictCodeConstants.PART_CLAIM_BUSINESS_04);
			}
		}
		flag1 = historyPo.insert();
		if(flag1){			
		}else{
			throw new ServiceBizException("审核历史插入失败！");
		}
	}

	/**
	 * 到货索赔查询
	 */
	@Override
	public PageInfoDto queryClaim(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.QueryqueryClaim(queryParams);
	}

	/**
	 * 导出查询
	 */
	@Override
	public List<Map> queryDownLoad(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.downLoad(queryParams);
	}

	/**
	 * 索赔订单详细信息
	 */
	@Override
	public Map<String, Object> findClaimInfoByClaimId(BigDecimal id) throws ServiceBizException {
		return partDao.getCheckDetail(id);
	}

}
