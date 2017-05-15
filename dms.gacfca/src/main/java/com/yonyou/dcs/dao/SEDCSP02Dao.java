package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.SEDCSP02DTO;
import com.yonyou.dms.DTO.gacfca.sedcsP01PartInfoDTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.PartCommonDao;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SEDCSP02Dao extends OemBaseDAO {
	private static final Logger logger = LoggerFactory.getLogger(SEDCSP02Dao.class);

	@Autowired
	PartCommonDao commonDao;
	
	/**
	 * 到sap查询是否有数据
	 * @param vo
	 * @return
	 */
	public List<SEDCSP02DTO> querySedcsP02DTO(SEDCSP02DTO vo) throws Exception {
		List<SEDCSP02DTO> list = null;
		SEDCSP02DTO voDCS = null;
		String ICreditConArea = OemDictCodeConstants.ICreditConArea;//信用控制范围
		// 因为接口现在IDealerUsr长度为15位，所以截取掉前面2位'20'
		String IDealerUsr = vo.getIdealerUsr();//特约店用户
		if (IDealerUsr !=null && IDealerUsr.length() > 15){
			IDealerUsr = IDealerUsr.substring(2);
		}
		String IMarca = OemDictCodeConstants.IMARCA;//品牌
		String IWerks = OemDictCodeConstants.IWERKS;//Plant (Own or External)
		String IZzcliente = 
				commonDao.getFCACodeByEntityCode(vo.getIzzcliente()); //特约店7位编码    entityCode转换成FACCode
		if (CommonUtils.checkIsNull(IZzcliente)) {
			list = new ArrayList<SEDCSP02DTO>();
			voDCS = new SEDCSP02DTO();
			logger.info("====IZzcliente 特约店7位编码 不能为空====");
			voDCS.setResultInfo("特约店7位编码不能为空");
			list.add(voDCS);
			return list;
		} 
		if (CommonUtils.checkIsNull(IDealerUsr)) {
			list = new ArrayList<SEDCSP02DTO>();
			voDCS = new SEDCSP02DTO();
			logger.info("====IDealerUsr 特约店用户 不能为空====");
			voDCS.setResultInfo("特约店用户 不能为空");
			list.add(voDCS);
			return list;
		} 
		//获取sap信息然后在信息
//		com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.holders.TableOfZlvsdwsGf01OutHolder tbOutput = new com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.holders.TableOfZlvsdwsGf01OutHolder();
//		com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.holders.TableOfBapiret1Holder tbReturn = new com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.holders.TableOfBapiret1Holder();
//		com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZLVSDWS_GF01_OUT_BindingStub stub = new com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZLVSDWS_GF01_OUT_BindingStub();
//		com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZLVSDWS_GF01_OUT_Service lmsService = new com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZLVSDWS_GF01_OUT_ServiceLocator();
//		stub = (com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZLVSDWS_GF01_OUT_BindingStub) lmsService
//				.getZLVSDWS_GF01_OUT();
//		logger.info("----------------调用对方服务地址：" + lmsService.getZLVSDWS_GF01_OUTAddress() + "----------------");
//		// DMS获得数据 然后传给SAP 然后在给DMS
//		logger.info("====SEDCSP02 ICreditConArea ====:" + ICreditConArea);
//		logger.info("====SEDCSP02 IDealerUsr ====:" + IDealerUsr);
//		logger.info("====SEDCSP02 IMarca ====:" + IMarca);
//		logger.info("====SEDCSP02 IWerks ====:" + IWerks);
//		logger.info("====SEDCSP02 IZzcliente ====:" + IZzcliente);
//		stub.zlvsdwsGf01Out(ICreditConArea, IDealerUsr, IMarca, IWerks, IZzcliente, tbOutput, tbReturn);
//		// 把返回的sap值封装到list中 然后返回给下端
//		if (tbOutput != null && tbOutput.value.length > 0) {
//			list = new ArrayList<SEDCSP02VO>();
//			com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZlvsdwsGf01Out[] outs = tbOutput.value;
//			for (int i = 0; i < outs.length; i++) {
//				com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZlvsdwsGf01Out out = outs[i];
//				voDCS = new SEDCSP02VO();
//				voDCS.setIwerks(out.getWerks());
//				// facCode转换成entityCode
//				String entityCode = commonDao.getDealerEntityCodeByFCACode(out.getZzcliente());
//				// 如果对应经销商没找到，则跳过
//				if (!Utility.testString(entityCode)) {
//					continue;
//				}
//				voDCS.setZzcliente(entityCode);
//				voDCS.setMarca(out.getMarca());
//				voDCS.setDealerUsr(out.getDealerUsr());
//				voDCS.setCreditLimit(out.getCreditLimit() + "");
//				voDCS.setReceivable(out.getReceivable() + "");
//				voDCS.setSalesValue(out.getSalesValue() + "");
//				voDCS.setCreditExposure(out.getCreditExposure() + "");
//				list.add(voDCS);
//			}
//		} else {
//			com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.Bapiret1[] bapiret1s = tbReturn.value;
//			if (bapiret1s != null && bapiret1s.length > 0) {
//				list = new ArrayList<SEDCSP02VO>();
//				for (int i = 0; i < bapiret1s.length; i++) {
//					voDCS = new SEDCSP02VO();
//					com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.Bapiret1 bapiret1 = bapiret1s[i];
//					// 错误信息返回
//					String errorInfo = bapiret1.getMessage();
//					voDCS.setResultInfo(errorInfo);
//					logger.info("sap 返回结果失败 :" + errorInfo);
//					list.add(voDCS);
//				}
//				list.add(voDCS);
//			}
//		}
		return list;
	}
	/**
	 * 到sap查询是否有数据,DCS账户余额查询专用
	 * @param vo
	 * @return
	 */
	public List<SEDCSP02DTO> querySedcsP02DTOByDCS(SEDCSP02DTO vo) throws Exception{
		List<SEDCSP02DTO> list = null;
		SEDCSP02DTO voDCS = null;
		String ICreditConArea = OemDictCodeConstants.ICreditConArea;//信用控制范围
		// 因为接口现在IDealerUsr长度为15位，所以截取掉前面2位'20'
		String IDealerUsr = vo.getIdealerUsr();//特约店用户
		if (IDealerUsr !=null && IDealerUsr.length() > 15){
			IDealerUsr = IDealerUsr.substring(2);
		}
		String IMarca = OemDictCodeConstants.IMARCA;//品牌
		String IWerks = OemDictCodeConstants.IWERKS;//Plant (Own or External)
		String IZzcliente = 
				commonDao.getFCACodeByCompanyId(Long.parseLong(vo.getIzzcliente())); //特约店7位编码    CompanyId转换成FACCode
		if (CommonUtils.checkIsNull(IZzcliente)) {
			list = new ArrayList<SEDCSP02DTO>();
			voDCS = new SEDCSP02DTO();
			logger.info("====IZzcliente 特约店7位编码 不能为空====");
			voDCS.setResultInfo("特约店7位编码不能为空");
			list.add(voDCS);
			return list;
		} 
		if (CommonUtils.checkIsNull(IDealerUsr)) {
			list = new ArrayList<SEDCSP02DTO>();
			voDCS = new SEDCSP02DTO();
			logger.info("====IDealerUsr 特约店用户 不能为空====");
			voDCS.setResultInfo("特约店用户 不能为空");
			list.add(voDCS);
			return list;
		} 
//		//获取sap信息然后在信息
//		com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.holders.TableOfZlvsdwsGf01OutHolder tbOutput = new 
//				com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.holders.TableOfZlvsdwsGf01OutHolder();
//		com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.holders.TableOfBapiret1Holder tbReturn = new 
//				com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.holders.TableOfBapiret1Holder();
//		com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZLVSDWS_GF01_OUT_BindingStub stub = 
//				new com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZLVSDWS_GF01_OUT_BindingStub();
//		com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZLVSDWS_GF01_OUT_Service lmsService = 
//				new com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZLVSDWS_GF01_OUT_ServiceLocator();
//		stub = (com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZLVSDWS_GF01_OUT_BindingStub)lmsService.getZLVSDWS_GF01_OUT();
//		logger.info("----------------调用对方服务地址："+lmsService.getZLVSDWS_GF01_OUTAddress()+"----------------");
//		//DMS获得数据 然后传给SAP 然后在给DMS
//		logger.info("====SEDCSP02 ICreditConArea ====:"+ICreditConArea);
//		logger.info("====SEDCSP02 IDealerUsr ====:"+IDealerUsr);
//		logger.info("====SEDCSP02 IMarca ====:"+IMarca);
//		logger.info("====SEDCSP02 IWerks ====:"+IWerks);
//		logger.info("====SEDCSP02 IZzcliente ====:"+IZzcliente);
//		stub.zlvsdwsGf01Out(
//	    		ICreditConArea, 
//	    		IDealerUsr,
//	    		IMarca, 
//	    		IWerks, 
//	    		IZzcliente, 
//	    		tbOutput,
//	    		tbReturn
//	    		);
//		//把返回的sap值封装到list中 然后返回给下端
//		if (tbOutput != null && tbOutput.value.length > 0) {
//			list = new ArrayList<SEDCSP02VO>();
//			com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZlvsdwsGf01Out[] outs = tbOutput.value;
//			for (int i=0;i<outs.length;i++) {
//				com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.ZlvsdwsGf01Out out = outs[i];
//				voDCS = new SEDCSP02VO();
//				voDCS.setIwerks(out.getWerks());
//				//facCode转换成entityCode
//			//	String entityCode = commonDao.getDealerEntityCodeByFCACode(out.getZzcliente());
//				// 如果对应经销商没找到，则跳过
//			//	if (!Utility.testString(entityCode)){
//			//		continue;
//			//	}
//			//	voDCS.setZzcliente(entityCode);
//				voDCS.setZzcliente(out.getZzcliente());
//				voDCS.setMarca(out.getMarca());
//				voDCS.setDealerUsr(out.getDealerUsr());
//				voDCS.setCreditLimit(out.getCreditLimit()+"");
//				voDCS.setReceivable(out.getReceivable()+"");
//				voDCS.setSalesValue(out.getSalesValue()+"");
//				voDCS.setCreditExposure(out.getCreditExposure()+"");
//				list.add(voDCS);
//			}
//		} else {
//			com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.Bapiret1[] bapiret1s = tbReturn.value;
//			if (bapiret1s != null && bapiret1s.length > 0) {
//				list = new ArrayList<SEDCSP02VO>();
//				for (int i=0;i<bapiret1s.length;i++) {
//					voDCS = new SEDCSP02VO();
//					com.infoeai.eai.wsClient.parts.dealerCreditExpoureInquiry.Bapiret1 bapiret1 = bapiret1s[i];
//					//错误信息返回
//					String errorInfo = bapiret1.getMessage();
//					voDCS.setResultInfo(errorInfo);
//					logger.info("sap 返回结果失败 :"+errorInfo);
//					list.add(voDCS);
//				}
//				list.add(voDCS);
//			}
//		}
		return list;
	}
}
