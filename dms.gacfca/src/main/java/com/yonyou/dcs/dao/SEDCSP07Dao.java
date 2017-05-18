package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.infoservice.dms.cgcsl.vo.SEDCSP07VO;
import com.yonyou.dms.DTO.gacfca.SEDCSP07DTO;
import com.yonyou.dms.common.Util.DateUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.PartCommonDao;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SEDCSP07Dao extends OemBaseDAO {
	private static final Logger logger = LoggerFactory.getLogger(SEDCSP07Dao.class);

	@Autowired
	PartCommonDao commonDao;
	

	/**
	 * 到sap查询是否有数据VO(供DE接口调用)
	 * @param vo
	 * @return
	 */
	public List<SEDCSP07VO> querySedcsP07VOByDCS(SEDCSP07VO vo) throws Exception{
		List<SEDCSP07VO> list = null;
		SEDCSP07VO voDCS = null;
		String IZzcliente = commonDao.getFCACodeByCompanyId(Long.parseLong(vo.getIzzcliente())); //特约店7位编码    CompanyId转换成FACCode
		// 因为接口现在IDealerUsr长度为15位，所以截取掉前面2位'20'
		String IDealerUsr = vo.getIdealerUsr();//特约店用户
		if (IDealerUsr !=null && IDealerUsr.length() > 15){
			IDealerUsr = IDealerUsr.substring(2);
		}
		if (CommonUtils.isNullString(IZzcliente)) {
			list = new ArrayList<SEDCSP07VO>();
			voDCS = new SEDCSP07VO();
			logger.info("====IZzcliente 特约店7位编码 不能为空====");
			voDCS.setResultInfo("特约店7位编码不能为空");
			list.add(voDCS);
			return list;
		} 
		if (CommonUtils.isNullString(IDealerUsr)) {
			list = new ArrayList<SEDCSP07VO>();
			voDCS = new SEDCSP07VO();
			logger.info("====IDealerUsr 特约店用户 不能为空====");
			voDCS.setResultInfo("特约店用户 不能为空");
			list.add(voDCS);
			return list;
		}
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_BindingStub stub =
				new com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_BindingStub();
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_Service lmsServices = 
				new com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_ServiceLocator();
		stub = (com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_BindingStub)lmsServices.getZLVSDWS_L25_OUT();
		logger.info("----------------调用对方服务地址："+lmsServices.getZLVSDWS_L25_OUTAddress()+"----------------");
		String IAuart = vo.getIauart();
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.TpmsRDate date = 
				new com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.TpmsRDate();
		date.setHigh(DateUtil.yyyyMMdd2Str(vo.getHigh()));
		date.setLow(DateUtil.yyyyMMdd2Str(vo.getLow()));
		logger.info("====SEDCSP07 High ====:"+DateUtil.yyyyMMdd2Str(vo.getHigh()));
		logger.info("====SEDCSP07 Low ====:"+DateUtil.yyyyMMdd2Str(vo.getLow()));
		date.setOption(OemDictCodeConstants.OPTION);
		date.setSign(OemDictCodeConstants.SIGN);
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.TpmsRDate[] IAudat = {date};
		String IMarca = OemDictCodeConstants.IMARCA;//品牌
		String IOrdstatus = vo.getIordstatus();//订单状态
		String IVbeln = vo.getIvbeln(); //vin
		String IWerks = OemDictCodeConstants.IWERKS;//Plant (Own or External)
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.holders.TableOfZlvsdwsL25OutHolder tbOutput = new 
				com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.holders.TableOfZlvsdwsL25OutHolder();
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.holders.TableOfBapiret1Holder tbReturn = new 
				com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.holders.TableOfBapiret1Holder();
		//DMS获得数据 然后传给SAP 然后在给DMS
		logger.info("====SEDCSP07 IAuart ====:"+IAuart);
		logger.info("====SEDCSP07 IAudat ====:"+IAudat);
		logger.info("====SEDCSP07 IDealerUsr ====:"+IDealerUsr);
		logger.info("====SEDCSP07 IMarca ====:"+IMarca);
		logger.info("====SEDCSP07 IOrdstatus ====:"+IOrdstatus);
		logger.info("====SEDCSP07 IVbeln ====:"+IVbeln);
		logger.info("====SEDCSP07 IWerks ====:"+IWerks);
		logger.info("====SEDCSP07 IZzcliente ====:"+IZzcliente);
		stub.zlvsdwsL25Out(
				IAuart,
				IAudat, 
				IDealerUsr, 
				IMarca, 
				IOrdstatus, 
				IVbeln, 
				IWerks, 
				IZzcliente, 
				tbOutput, 
				tbReturn);
		logger.info("====SEDCSP07 length ====:"+tbOutput.value.length);
		//把返回的sap值封装到list中 然后返回给下端
		if (tbOutput != null && tbOutput.value.length > 0) {
				list = new ArrayList<SEDCSP07VO>();
				com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZlvsdwsL25Out[] outs =  tbOutput.value;
				for (int i=0;i<outs.length;i++) {
					com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZlvsdwsL25Out out = outs[i];
					voDCS = new SEDCSP07VO();
					voDCS.setIwerks(out.getWerks());
					//facCode转换成entityCode
					voDCS.setZzcliente(out.getZzcliente());
					voDCS.setMarca(out.getMarca());
					voDCS.setDealerUsr(out.getDealerUsr());
					voDCS.setVbeln(out.getVbeln());
					voDCS.setAudat(out.getAudat());
					voDCS.setAuart(out.getAuart());
					voDCS.setOrderStatus(out.getOrderStatus());
					voDCS.setDelvryAddr(out.getDelvryAddr());
					voDCS.setInvoiceAddr(out.getInvoiceAddr());
					voDCS.setRespStaff(out.getRespStaff());
					voDCS.setNote(out.getNote());
					voDCS.setDelCharge(out.getDelCharge().doubleValue());
					voDCS.setAllDate(out.getAllDate());
					list.add(voDCS);
				}
		} else {
			com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.Bapiret1[] bapiret1s = tbReturn.value;
			if (bapiret1s != null && bapiret1s.length > 0) {
				list = new ArrayList<SEDCSP07VO>();
				for (int i=0;i<bapiret1s.length;i++) {
					voDCS = new SEDCSP07VO();
					com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.Bapiret1 bapiret1 = bapiret1s[i];
					//错误信息返回
					String errorInfo = bapiret1.getMessage();
					voDCS.setResultInfo(errorInfo);
					logger.info("sap 返回结果失败 :"+errorInfo);
					list.add(voDCS);
				}
				list.add(voDCS);
			}
		}
		return list;
	}
	
	/**
	 * 到sap查询是否有数据VO(供DE接口调用)
	 * @param vo
	 * @return
	 */
	public List<SEDCSP07VO> querySedcsP07VO(SEDCSP07VO vo) throws Exception{
		List<SEDCSP07VO> list = null;
		SEDCSP07VO voDCS = null;
		String IZzcliente = 
				commonDao.getFCACodeByEntityCode(vo.getIzzcliente()); //特约店7位编码    entityCode转换成FACCode
		// 因为接口现在IDealerUsr长度为15位，所以截取掉前面2位'20'
		String IDealerUsr = vo.getIdealerUsr();//特约店用户
		if (IDealerUsr !=null && IDealerUsr.length() > 15){
			IDealerUsr = IDealerUsr.substring(2);
		}
		if (CommonUtils.isNullString(IZzcliente)) {
			list = new ArrayList<SEDCSP07VO>();
			voDCS = new SEDCSP07VO();
			logger.info("====IZzcliente 特约店7位编码 不能为空====");
			voDCS.setResultInfo("特约店7位编码不能为空");
			list.add(voDCS);
			return list;
		} 
		if (CommonUtils.isNullString(IDealerUsr)) {
			list = new ArrayList<SEDCSP07VO>();
			voDCS = new SEDCSP07VO();
			logger.info("====IDealerUsr 特约店用户 不能为空====");
			voDCS.setResultInfo("特约店用户 不能为空");
			list.add(voDCS);
			return list;
		}
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_BindingStub stub =
				new com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_BindingStub();
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_Service lmsServices = 
				new com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_ServiceLocator();
		stub = (com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_BindingStub)lmsServices.getZLVSDWS_L25_OUT();
		logger.info("----------------调用对方服务地址："+lmsServices.getZLVSDWS_L25_OUTAddress()+"----------------");
		String IAuart = vo.getIauart();
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.TpmsRDate date = 
				new com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.TpmsRDate();
		date.setHigh(DateUtil.yyyyMMdd2Str(vo.getHigh()));
		date.setLow(DateUtil.yyyyMMdd2Str(vo.getLow()));
		logger.info("====SEDCSP07 High ====:"+DateUtil.yyyyMMdd2Str(vo.getHigh()));
		logger.info("====SEDCSP07 Low ====:"+DateUtil.yyyyMMdd2Str(vo.getLow()));
		date.setOption(OemDictCodeConstants.OPTION);
		date.setSign(OemDictCodeConstants.SIGN);
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.TpmsRDate[] IAudat = {date};
		String IMarca = OemDictCodeConstants.IMARCA;//品牌
		String IOrdstatus = vo.getIordstatus();//订单状态
		String IVbeln = vo.getIvbeln(); //vin
		String IWerks = OemDictCodeConstants.IWERKS;//Plant (Own or External)
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.holders.TableOfZlvsdwsL25OutHolder tbOutput = new 
				com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.holders.TableOfZlvsdwsL25OutHolder();
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.holders.TableOfBapiret1Holder tbReturn = new 
				com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.holders.TableOfBapiret1Holder();
		//DMS获得数据 然后传给SAP 然后在给DMS
		logger.info("====SEDCSP07 IAuart ====:"+IAuart);
		logger.info("====SEDCSP07 IAudat ====:"+IAudat);
		logger.info("====SEDCSP07 IDealerUsr ====:"+IDealerUsr);
		logger.info("====SEDCSP07 IMarca ====:"+IMarca);
		logger.info("====SEDCSP07 IOrdstatus ====:"+IOrdstatus);
		logger.info("====SEDCSP07 IVbeln ====:"+IVbeln);
		logger.info("====SEDCSP07 IWerks ====:"+IWerks);
		logger.info("====SEDCSP07 IZzcliente ====:"+IZzcliente);
		stub.zlvsdwsL25Out(
				IAuart,
				IAudat, 
				IDealerUsr, 
				IMarca, 
				IOrdstatus, 
				IVbeln, 
				IWerks, 
				IZzcliente, 
				tbOutput, 
				tbReturn);
		logger.info("====SEDCSP07 length ====:"+tbOutput.value.length);
		//把返回的sap值封装到list中 然后返回给下端
		if (tbOutput != null && tbOutput.value.length > 0) {
				list = new ArrayList<SEDCSP07VO>();
				com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZlvsdwsL25Out[] outs =  tbOutput.value;
				for (int i=0;i<outs.length;i++) {
					com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZlvsdwsL25Out out = outs[i];
					voDCS = new SEDCSP07VO();
					voDCS.setIwerks(out.getWerks());
					//facCode转换成entityCode
					voDCS.setZzcliente(
							commonDao.getDealerEntityCodeByFCACode(out.getZzcliente())
							);
					voDCS.setMarca(out.getMarca());
					voDCS.setDealerUsr(out.getDealerUsr());
					voDCS.setVbeln(out.getVbeln());
					voDCS.setAudat(out.getAudat());
					voDCS.setAuart(out.getAuart());
					voDCS.setOrderStatus(out.getOrderStatus());
					voDCS.setDelvryAddr(out.getDelvryAddr());
					voDCS.setInvoiceAddr(out.getInvoiceAddr());
					voDCS.setRespStaff(out.getRespStaff());
					voDCS.setNote(out.getNote());
					voDCS.setDelCharge(out.getDelCharge().doubleValue());
					voDCS.setAllDate(out.getAllDate());
					list.add(voDCS);
				}
		} else {
			com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.Bapiret1[] bapiret1s = tbReturn.value;
			if (bapiret1s != null && bapiret1s.length > 0) {
				list = new ArrayList<SEDCSP07VO>();
				for (int i=0;i<bapiret1s.length;i++) {
					voDCS = new SEDCSP07VO();
					com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.Bapiret1 bapiret1 = bapiret1s[i];
					//错误信息返回
					String errorInfo = bapiret1.getMessage();
					voDCS.setResultInfo(errorInfo);
					logger.info("sap 返回结果失败 :"+errorInfo);
					list.add(voDCS);
				}
				list.add(voDCS);
			}
		}
		return list;
	}
	/**
	 * 到sap查询是否有数据VO(供DE接口调用)
	 * @param vo
	 * @return
	 */
	public List<SEDCSP07DTO> querySedcsP07DTO(SEDCSP07DTO vo) throws Exception{
		List<SEDCSP07DTO> list = null;
		SEDCSP07DTO voDCS = null;
		String IZzcliente = 
				commonDao.getFCACodeByEntityCode(vo.getIzzcliente()); //特约店7位编码    entityCode转换成FACCode
		// 因为接口现在IDealerUsr长度为15位，所以截取掉前面2位'20'
		String IDealerUsr = vo.getIdealerUsr();//特约店用户
		if (IDealerUsr !=null && IDealerUsr.length() > 15){
			IDealerUsr = IDealerUsr.substring(2);
		}
		if (CommonUtils.isNullString(IZzcliente)) {
			list = new ArrayList<SEDCSP07DTO>();
			voDCS = new SEDCSP07DTO();
			logger.info("====IZzcliente 特约店7位编码 不能为空====");
			voDCS.setResultInfo("特约店7位编码不能为空");
			list.add(voDCS);
			return list;
		} 
		if (CommonUtils.isNullString(IDealerUsr)) {
			list = new ArrayList<SEDCSP07DTO>();
			voDCS = new SEDCSP07DTO();
			logger.info("====IDealerUsr 特约店用户 不能为空====");
			voDCS.setResultInfo("特约店用户 不能为空");
			list.add(voDCS);
			return list;
		}
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_BindingStub stub =
				new com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_BindingStub();
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_Service lmsServices = 
				new com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_ServiceLocator();
		stub = (com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZLVSDWS_L25_OUT_BindingStub)lmsServices.getZLVSDWS_L25_OUT();
		logger.info("----------------调用对方服务地址："+lmsServices.getZLVSDWS_L25_OUTAddress()+"----------------");
		String IAuart = vo.getIauart();
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.TpmsRDate date = 
				new com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.TpmsRDate();
		date.setHigh(DateUtil.yyyyMMdd2Str(vo.getHigh()));
		date.setLow(DateUtil.yyyyMMdd2Str(vo.getLow()));
		logger.info("====SEDCSP07 High ====:"+DateUtil.yyyyMMdd2Str(vo.getHigh()));
		logger.info("====SEDCSP07 Low ====:"+DateUtil.yyyyMMdd2Str(vo.getLow()));
		date.setOption(OemDictCodeConstants.OPTION);
		date.setSign(OemDictCodeConstants.SIGN);
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.TpmsRDate[] IAudat = {date};
		String IMarca = OemDictCodeConstants.IMARCA;//品牌
		String IOrdstatus = vo.getIordstatus();//订单状态
		String IVbeln = vo.getIvbeln(); //vin
		String IWerks = OemDictCodeConstants.IWERKS;//Plant (Own or External)
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.holders.TableOfZlvsdwsL25OutHolder tbOutput = new 
				com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.holders.TableOfZlvsdwsL25OutHolder();
		com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.holders.TableOfBapiret1Holder tbReturn = new 
				com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.holders.TableOfBapiret1Holder();
		//DMS获得数据 然后传给SAP 然后在给DMS
		logger.info("====SEDCSP07 IAuart ====:"+IAuart);
		logger.info("====SEDCSP07 IAudat ====:"+IAudat);
		logger.info("====SEDCSP07 IDealerUsr ====:"+IDealerUsr);
		logger.info("====SEDCSP07 IMarca ====:"+IMarca);
		logger.info("====SEDCSP07 IOrdstatus ====:"+IOrdstatus);
		logger.info("====SEDCSP07 IVbeln ====:"+IVbeln);
		logger.info("====SEDCSP07 IWerks ====:"+IWerks);
		logger.info("====SEDCSP07 IZzcliente ====:"+IZzcliente);
		stub.zlvsdwsL25Out(
				IAuart,
				IAudat, 
				IDealerUsr, 
				IMarca, 
				IOrdstatus, 
				IVbeln, 
				IWerks, 
				IZzcliente, 
				tbOutput, 
				tbReturn);
		logger.info("====SEDCSP07 length ====:"+tbOutput.value.length);
		//把返回的sap值封装到list中 然后返回给下端
		if (tbOutput != null && tbOutput.value.length > 0) {
				list = new ArrayList<SEDCSP07DTO>();
				com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZlvsdwsL25Out[] outs =  tbOutput.value;
				for (int i=0;i<outs.length;i++) {
					com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.ZlvsdwsL25Out out = outs[i];
					voDCS = new SEDCSP07DTO();
					voDCS.setIwerks(out.getWerks());
					//facCode转换成entityCode
					voDCS.setZzcliente(
							commonDao.getDealerEntityCodeByFCACode(out.getZzcliente())
							);
					voDCS.setMarca(out.getMarca());
					voDCS.setDealerUsr(out.getDealerUsr());
					voDCS.setVbeln(out.getVbeln());
					voDCS.setAudat(out.getAudat());
					voDCS.setAuart(out.getAuart());
					voDCS.setOrderStatus(out.getOrderStatus());
					voDCS.setDelvryAddr(out.getDelvryAddr());
					voDCS.setInvoiceAddr(out.getInvoiceAddr());
					voDCS.setRespStaff(out.getRespStaff());
					voDCS.setNote(out.getNote());
					voDCS.setDelCharge(out.getDelCharge().doubleValue());
					voDCS.setAllDate(out.getAllDate());
					list.add(voDCS);
				}
		} else {
			com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.Bapiret1[] bapiret1s = tbReturn.value;
			if (bapiret1s != null && bapiret1s.length > 0) {
				list = new ArrayList<SEDCSP07DTO>();
				for (int i=0;i<bapiret1s.length;i++) {
					voDCS = new SEDCSP07DTO();
					com.infoeai.eai.wsClient.parts.lvmPartsSearchOrdersList.Bapiret1 bapiret1 = bapiret1s[i];
					//错误信息返回
					String errorInfo = bapiret1.getMessage();
					voDCS.setResultInfo(errorInfo);
					logger.info("sap 返回结果失败 :"+errorInfo);
					list.add(voDCS);
				}
				list.add(voDCS);
			}
		}
		return list;
	}
}
