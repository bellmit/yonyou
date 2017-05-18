package com.yonyou.dcs.gacfca;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.SEDCSP14DTO;
import com.yonyou.dms.common.Util.DateUtil;
import com.yonyou.dms.common.domains.PO.basedata.TtPtDeliverPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtOrderTracePO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Service
public class SEDCSP14CloudImpl  extends BaseCloudImpl implements SEDCSP14Cloud {

	private static final Logger logger = LoggerFactory.getLogger(SEDCSP14CloudImpl.class);
	
	@Override
	public String handleExecutor(List<SEDCSP14DTO> dtos) throws Exception {
		logger.info("***************************SEDCSP14Cloud 同步运单收货管理回执信息开始******************************");
		beginDbService();
		try {
			for (SEDCSP14DTO dto : dtos) {
				
				logger.info("====Ivbeln==============="+dto.getIvbeln());
				logger.info("====dealerCredentials===="+dto.getDealerCredentials().toString());
				logger.info("====badRemark============"+dto.getBadRemark());
				
				String Ivbeln = dto.getIvbeln();
				
				//参数
				List<Object> params = new ArrayList<Object>();
				params.add(OemDictCodeConstants.PART_DELIVER_STATUS_02);//已签收货
				params.add(99999999L);
				params.add(new Date(System.currentTimeMillis()));
				params.add(Ivbeln);
				TtPtDeliverPO.update("DELIVER_STATUS = ? AND UPDATE_BY = ? AND UPDATE_DATE = ?", "DELIVER_NO = ?", params.toArray());
				//更新运单物流跟踪表为已签收
				Date nowDate = new Date();
				Integer isSignDelay = OemDictCodeConstants.IF_TYPE_NO; 
				Date actualArriveDate = getActualArriveDate(Ivbeln);//实际到货时间
				if (actualArriveDate != null) {
					int signDay = DateUtil.daysBetweenDate(actualArriveDate, nowDate);//计算签收延误天数
					if (signDay>0) {
						isSignDelay = OemDictCodeConstants.IF_TYPE_YES;
					}
				 }
			
				//参数
				List<Object> utparams = new ArrayList<Object>();
				utparams.add(OemDictCodeConstants.IF_TYPE_YES);//已签收
				utparams.add(new Date());//签收时间
				utparams.add(isSignDelay);//是否签收延误
				utparams.add(getAssess(dto.getDealerCredentials(),dto.getBadRemark()));
				utparams.add(dto.getBadRemark());
				utparams.add(Ivbeln);
				utparams.add(OemDictCodeConstants.IF_TYPE_NO);
				
				TtPtOrderTracePO.update("IS_SING = ? AND SING_DATE = ? AND IS_SING_DELAY = ? AND ASSESS = ? AND BADREMARK", "DELIVER_NO = ? AND IS_SING = ?", params.toArray());
				if (!Ivbeln.startsWith("007")) {//如果不是007就发送给sap
					//发送数据到sap
					querySEDCSP14DTO(dto);
					logger.info("====同步查询运单收货管理回执信息结束====");
				}
			}
			dbService.endTxn(true);
		}catch (Exception e) {
			logger.error("运单收货管理回执执行失败", e);
			dbService.endTxn(false);
		}finally {
			Base.detach();
			dbService.clean();
		}

		logger.info("***************************SEDCSP14Cloud  运单收货管理回执执行成功 ******************************");
		return "1";
	}
	public void handleExeDer(SEDCSP14DTO dto) throws Exception{
		logger.info("====同步运单收货管理回执信息开始====");
		try  {
			querySEDCSP14DTO(dto);
		logger.info("====同步查询运单收货管理回执信息结束====");
		} catch (Exception e) {
			logger.info("同步查询运单收货管理回执信息失败", e);
			throw new ServiceBizException(e);
		}
	}
	/**
	 * 到sap查询是否有数据
	 * @param vo
	 * @return
	 */
	private void querySEDCSP14DTO(SEDCSP14DTO dto) throws Exception{
		com.infoeai.eai.wsClient.parts.podReceiveData.ZRSD_PODRECEIVE_BindingStub stub = 
				new com.infoeai.eai.wsClient.parts.podReceiveData.ZRSD_PODRECEIVE_BindingStub();
		com.infoeai.eai.wsClient.parts.podReceiveData.ZRSD_PODRECEIVE_Service lmsService = 
				new com.infoeai.eai.wsClient.parts.podReceiveData.ZRSD_PODRECEIVE_ServiceLocator();
		stub = (com.infoeai.eai.wsClient.parts.podReceiveData.ZRSD_PODRECEIVE_BindingStub)lmsService.getZRSD_PODRECEIVE();
		logger.info("----------------调用对方服务地址："+lmsService.getZRSD_PODRECEIVEAddress()+"----------------");
		try {
			com.infoeai.eai.wsClient.parts.podReceiveData.ZrsdPod pod = 
					new com.infoeai.eai.wsClient.parts.podReceiveData.ZrsdPod();
			pod.setMandt(OemDictCodeConstants.Mandt);
			pod.setVbeln(dto.getIvbeln());
			pod.setPosnr(dto.getIposnr()+"");
			pod.setZtype(OemDictCodeConstants.Iztype);////类型
			pod.setSernr(dto.getIsernr());
			pod.setKunnr(dto.getIkunnr()+"");
			pod.setMatnr(dto.getImatnr());
			pod.setMaktx(dto.getImaktx());
			pod.setLfimg(new BigDecimal(dto.getIlfimg()));
			pod.setLfdat(DateUtil.yyyy_MM_dd2Str(dto.getIlfdat()));
			pod.setVbelnVa(dto.getIvbelnva());
			String iuname = dto.getIuname();
			if (CommonUtils.notNull(iuname)) {
				if (iuname.length()>6) {
					iuname = iuname.substring(0,6);
				}
			}
			pod.setUname(iuname);
			pod.setDatum(DateUtil.yyyy_MM_dd2Str(dto.getIdatum()));
			pod.setUzeit(dto.getIuzeit());
			pod.setPodQty(new BigDecimal(dto.getIpodqty()));
			pod.setRemark(dto.getIremark());
			logger.info("====SEDCSP14 Mandt===="+pod.getMandt());
			logger.info("====SEDCSP14 Vbeln===="+pod.getVbeln());
			logger.info("====SEDCSP14 Posnr===="+pod.getPosnr());
			logger.info("====SEDCSP14 Ztype===="+pod.getZtype());
			logger.info("====SEDCSP14 Sernr===="+pod.getSernr());
			logger.info("====SEDCSP14 Kunnr===="+pod.getKunnr());
			logger.info("====SEDCSP14 Matnr===="+pod.getMatnr());
			logger.info("====SEDCSP14 Maktx===="+pod.getMaktx());
			logger.info("====SEDCSP14 Lfimg===="+pod.getLfimg());
			logger.info("====SEDCSP14 Lfdat===="+pod.getLfdat());
			logger.info("====SEDCSP14 VbelnVa===="+pod.getVbelnVa());
			logger.info("====SEDCSP14 Uname===="+pod.getUname());
			logger.info("====SEDCSP14 Datum===="+pod.getDatum());
			logger.info("====SEDCSP14 Uzeit===="+pod.getUzeit());
			logger.info("====SEDCSP14 PodQty===="+pod.getPodQty());
			logger.info("====SEDCSP14 Remark===="+pod.getRemark());
			stub.zrsdPodreceive(pod);
		} catch (Exception e) {
			logger.info("====SEDCSP14 Exception info"+e.getMessage());
			logger.error("====SEDCSP14 Exception error"+e.getMessage());
			e.printStackTrace();
		}
	}


	private Date getActualArriveDate(String DeliverNo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT ACTUAL_ARRIVE_DATE FROM TT_PT_ORDER_TRACE_DCS \n");
		sql.append(" WHERE DELIVER_NO = "+DeliverNo+" \n");
		sql.append(" WITH UR \n");
		Map map=OemDAOUtil.findFirst(sql.toString(), null);
		if(map!=null){
			return CommonUtils.parseDate(CommonUtils.checkNull(map.get("ACTUAL_ARRIVE_DATE")));
		}else{
			return null;
		}
	}
	
	private String getAssess(Integer dealerCredentials,String badRemark) {
		String assess="";
		if(dealerCredentials==70131001){
			assess="非常满意";
		}else if(dealerCredentials==70131002){
			assess="满意";
		}else if(dealerCredentials==70131003){
			assess="一般";
		}else if(dealerCredentials==70131004){
			if(badRemark!=null && !badRemark.equals("")){
				assess="很差--说明："+badRemark;
			}else{
				assess="很差";
			}
		}
		return assess;
	}

}
