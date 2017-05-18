package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.SEDCSP15DTO;
import com.yonyou.dms.DTO.gacfca.SEDCSP15InfoDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtClaimAttDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtClaimDcsPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SEDCSP15CloudImpl extends BaseCloudImpl implements SEDCSP15Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SEDCSP15CloudImpl.class);

	@Override
	public String handleExecutor(LinkedList<SEDCSP15DTO> dtoList) throws Exception {
		String msg = "1";
		logger.info("====到货索赔上报DCS上报开始====");
		beginDbService();
		try {
			for(SEDCSP15DTO dto:dtoList){
				try {
					boolean flag = dottPtClaimData(dto);
					if(!flag){
						logger.error("====到货索赔上报DCS上报失败====");
						msg = "0";
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("====到货索赔上报DCS上报接收异常====", e);
					msg = "0";
				}
			}
			logger.info("====到货索赔上报DCS上报失败结束===="); 
			dbService.endTxn(true);
		} catch (Exception e) {
			dbService.endTxn(false);
		}finally {
			Base.detach();
			dbService.clean();
		}
		return msg;
		
	}
	
	/**
	 * 处理下端上报的数据
	 * @param vo
	 * @throws Exception 
	 */
	private boolean dottPtClaimData(SEDCSP15DTO vo) throws Exception {
		boolean flag=false;
		if (vo == null) {
			throw new ServiceBizException("SEDCSP15VO信息为空！");
		}
		Map<String, Object> dcsInfoMap = getSeDcsDealerCode(vo.getEntityCode());
		if (dcsInfoMap == null) {
			throw new ServiceBizException("DCS没有【" + vo.getEntityCode() + "】经销商信息！");
		}
		String claimNo = vo.getClaimNo();//索赔单号
		Long claimId = null;//主键
		
		TtPtClaimDcsPO listTtPtClaimPO=TtPtClaimDcsPO.findFirst("DEALER_ID = ? AND CLAIM_NO = ?", dcsInfoMap.get("DEALER_ID")+"",claimNo);
		
		if (null != listTtPtClaimPO && listTtPtClaimPO.getLong("CLAIM_ID")!=null) {//修改
			
			claimId = listTtPtClaimPO.getLong("CLAIM_ID");
			//更新结果
			StringBuffer upSqlV=new StringBuffer();
			upSqlV.append("DEALER_ID = ?");
			upSqlV.append("CLAIM_NO = ?");
			upSqlV.append("CLAIM_DATE = ?");
			upSqlV.append("CLAIM_PROPERTY = ?");
			upSqlV.append("CLAIM_REQUIRE = ?");
			upSqlV.append("DELIVER_NO = ?");
			upSqlV.append("ORDER_NO = ?");
			upSqlV.append("TRANS_COMPANY = ?");
			upSqlV.append("TRANS_NO = ?");
			upSqlV.append("TRANS_TYPE = ?");
			upSqlV.append("BOX_NO = ?");
			upSqlV.append("ARRIVED_DATE = ?");
			upSqlV.append("CHECKED_DATE = ?");
			upSqlV.append("CHECKED_BY = ?");
			upSqlV.append("CHECKED_PHONE = ?");
			upSqlV.append("PART_CODE = ?");
			upSqlV.append("PART_NAME = ?");
			upSqlV.append("TRANS_NUM = ?");
			upSqlV.append("CLAIM_NUM = ?");
			upSqlV.append("PART_PRICE = ?");
			upSqlV.append("AMOUNT = ?");
			upSqlV.append("PART_MDOEL = ?");
			upSqlV.append("REPORT_TIMES = ?");	
			upSqlV.append("REPORT_DATE = ?");
			upSqlV.append("TRANS_DATE = ?");
			upSqlV.append("LEAVE_TRANS_DAYS = ?");
			upSqlV.append("CLAIM_OPTIONS = ?");
			upSqlV.append("DMS_SEND_DATE = ?");
			upSqlV.append("CLAIM_STATUS = ?");
			upSqlV.append("REISSUE_TRANS_NO = ?");
			upSqlV.append("DELIVERY_ERROR_PART = ?");
			upSqlV.append("TRANS_SYSTEM_DATE = ?");
			upSqlV.append("TRANS_STOCK = ?");
			upSqlV.append("UPDATE_BY = ?");
			upSqlV.append("UPDATE_DATE = ?");
			//更新条件
			StringBuffer upSqlC=new StringBuffer();
			upSqlV.append("CLAIM_ID = ?");
			//参数
			List<Object> params = new ArrayList<Object>();
			params.add(OemDictCodeConstants.IF_TYPE_NO);
			params.add(new Long(dcsInfoMap.get("DEALER_ID")+""));//经销商id
			params.add(vo.getClaimNo());
			params.add(vo.getClaimDate());
			params.add(vo.getClaimProperty());
			params.add(vo.getClaimRequire());
			params.add(vo.getDeliverNo());
			params.add(vo.getOrderNo());
			params.add(vo.getTransCompany());
			params.add(vo.getTransNo());
			params.add(vo.getTransType());
			params.add(vo.getBoxNo());
			params.add(vo.getArrivedDate());
			params.add(vo.getCheckedDate());
			params.add(vo.getCheckedBy());
			params.add(vo.getCheckedPhone());
			params.add(vo.getPartCode());
			params.add(vo.getPartName());
			params.add(vo.getTransNum());
			params.add(vo.getClaimNum());
			params.add(vo.getPartPrice());
			params.add(vo.getAmount());
			params.add(vo.getPartMdoel());
			params.add(vo.getReportTimes());
			params.add(vo.getReportDate());
			params.add(vo.getTransDate());
			params.add(vo.getLeaveTransDays());
			params.add(vo.getClaimOptions());
			params.add(new Date(System.currentTimeMillis()));
			params.add(OemDictCodeConstants.PART_CLAIM_STATUS_03);
			params.add(vo.getReissueTransNo());
			params.add(vo.getDeliveryErrorPart());
			params.add(vo.getTransSystemDate());
			params.add(vo.getTransStock());
			params.add(DEConstant.DE_UPDATE_BY);
			params.add(new Date(System.currentTimeMillis()));
			params.add(claimId);
			int f=TtPtClaimDcsPO.update(upSqlV.toString(), upSqlC.toString(), params.toArray());
			flag=f==1?true:false;
		} else {//新增
			TtPtClaimDcsPO po = new TtPtClaimDcsPO();
			po.setLong("DEALER_ID",new Long(dcsInfoMap.get("DEALER_ID")+""));//经销商id
			po.setString("CLAIM_NO",vo.getClaimNo());
			po.setTimestamp("CLAIM_DATE",vo.getClaimDate());
			po.setInteger("CLAIM_PROPERTY",vo.getClaimProperty());
			po.setInteger("CLAIM_REQUIRE",vo.getClaimRequire());
			po.setString("DELIVER_NO",vo.getDeliverNo());
			po.setString("ORDER_NO",vo.getOrderNo());
			po.setString("TRANS_COMPANY",vo.getTransCompany());
			po.setString("TRANS_NO",vo.getTransNo());
			po.setString("TRANS_TYPE",vo.getTransType());
			po.setString("BOX_NO",vo.getBoxNo());
			po.setTimestamp("ARRIVED_DATE",vo.getArrivedDate());
			po.setTimestamp("CHECKED_DATE",vo.getCheckedDate());
			po.setString("CHECKED_BY",vo.getCheckedBy());
			po.setString("CHECKED_PHONE",vo.getCheckedPhone());
			po.setString("PART_CODE",vo.getPartCode());
			po.setString("PART_NAME",vo.getPartName());
			po.setInteger("TRANS_NUM",vo.getTransNum());
			po.setInteger("CLAIM_NUM",vo.getClaimNum());
			po.setDouble("PART_PRICE",vo.getPartPrice());
			po.setDouble("AMOUNT",vo.getAmount());
			po.setInteger("PART_MDOEL",vo.getPartMdoel());
			po.setInteger("REPORT_TIMES",vo.getReportTimes());
			po.setTimestamp("REPORT_DATE",vo.getReportDate());
			po.setTimestamp("TRANS_DATE",vo.getTransDate());
			po.setInteger("LEAVE_TRANS_DAYS",vo.getLeaveTransDays());
			po.setString("CLAIM_OPTIONS",vo.getClaimOptions());
			po.setTimestamp("DMS_SEND_DATE",new Date(System.currentTimeMillis()));
			po.setInteger("CLAIM_STATUS",OemDictCodeConstants.PART_CLAIM_STATUS_03);
			po.setString("REISSUE_TRANS_NO",vo.getReissueTransNo());
			po.setString("DELIVERY_ERROR_PART",vo.getDeliveryErrorPart());
			po.setTimestamp("TRANS_SYSTEM_DATE",vo.getTransSystemDate());
			po.setString("TRANS_STOCK",vo.getTransStock());
			po.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
			po.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
			flag =po.insert();
			claimId=po.getLong("CLAIM_ID");
		}
		//附件信息
		LinkedList<SEDCSP15InfoDTO> attachList = vo.getAttachList();
		if (null != attachList && attachList.size() > 0) {
			//删除
			TtPtClaimAttDcsPO.delete("CLAIM_ID", claimId);
			//新增
			for (int i=0;i<attachList.size();i++) {
				SEDCSP15InfoDTO pVo = attachList.get(i);
				TtPtClaimAttDcsPO tPo = new TtPtClaimAttDcsPO();
				tPo.setLong("CLAIM_ID",claimId);
				tPo.setString("ATT_PATH",pVo.getAttpath());
				tPo.setInteger("ATT_TYPE",pVo.getAttType());
				tPo.setString("ATT_NAME",pVo.getAttName());
				tPo.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
				tPo.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
				flag=tPo.insert();
			}
		}
		return flag;
	}

}
