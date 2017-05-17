package com.yonyou.dcs.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.infoservice.dms.cgcsl.vo.SEDCSP01VO;
import com.infoservice.dms.cgcsl.vo.sedcsP01PartInfoVO;
import com.yonyou.dms.DTO.gacfca.SEDCSP01DTO;
import com.yonyou.dms.DTO.gacfca.sedcsP01PartInfoDTO;
import com.yonyou.dms.common.Util.DateUtil;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.PartCommonDao;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SEDCSP01Dao extends OemBaseDAO {
	private static final Logger logger = LoggerFactory.getLogger(SEDCSP01Dao.class);

	@Autowired
	PartCommonDao commonDao;
	
	/**
	 * 到sap查询是否有数据 DTO
	 * @param vo
	 * @return
	 */
	public List<SEDCSP01DTO> querySedcsP01DTO(SEDCSP01DTO vo) throws Exception {
		List<SEDCSP01DTO> list = null;
		SEDCSP01DTO voDCS = null;
		String IZzcliente = 
				commonDao.getFCACodeByEntityCode(vo.getIzzcliente()); //特约店7位编码    entityCode转换成FACCode
		// 因为接口现在IDealerUsr长度为15位，所以截取掉前面2位'20'
		String IDealerUsr = vo.getIdealerUsr();//特约店用户
		if (IDealerUsr !=null && IDealerUsr.length() > 15){
			IDealerUsr = IDealerUsr.substring(2);
		}
		String Iauart = commonDao.getOrderTypeByIauart(vo.getIauart());//订单类型
		String ISubstitute = vo.getIsubstitute();//是否获取替代件
		LinkedList<sedcsP01PartInfoDTO> partInfoList = vo.getPartBaseInfoList();//配件信息
		//验收格式是否正确
		if (CommonUtils.checkIsNull(IZzcliente)) {
			list = new ArrayList<SEDCSP01DTO>();
			voDCS = new SEDCSP01DTO();
			logger.info("====IZzcliente 特约店7位编码 不能为空====");
			voDCS.setResultInfo("特约店7位编码不能为空");
			list.add(voDCS);
			return list;
		} 
		if (CommonUtils.checkIsNull(IDealerUsr)) {
			list = new ArrayList<SEDCSP01DTO>();
			voDCS = new SEDCSP01DTO();
			logger.info("====IDealerUsr 特约店用户 不能为空====");
			voDCS.setResultInfo("特约店用户 不能为空");
			list.add(voDCS);
			return list;
		} 
		if (CommonUtils.checkIsNull(Iauart)) {
			list = new ArrayList<SEDCSP01DTO>();
			voDCS = new SEDCSP01DTO();
			logger.info("====Iauart 订单类型 不能为空====");
			voDCS.setResultInfo("订单类型 不能为空");
			list.add(voDCS);
			return list;
		} 
		if (partInfoList!=null && partInfoList.size()<0) {
			list = new ArrayList<SEDCSP01DTO>();
			voDCS = new SEDCSP01DTO();
			logger.info("====partInfoList 配件信息不能为空====");
			voDCS.setResultInfo("配件信息不能为空");
			list.add(voDCS);
			return list;
		}
		//获取sap信息然后在信息
		com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_BindingStub stub = 
				new com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_BindingStub();
		com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_Service lmsService = 
				new com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_ServiceLocator();
		stub = (com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_BindingStub)lmsService.getZLVSDWS_L31_OUT();
		logger.info("----------------调用对方服务地址："+lmsService.getZLVSDWS_L31_OUTAddress()+"----------------");
		String IMarca = OemDictCodeConstants.IMARCA;//品牌
		String IElcode = vo.getIelcode();//电子编码
		String IMecode = vo.getImecode();//机械代码
		String IVhvin = vo.getIvhvin();//VIN
		String IWerks = OemDictCodeConstants.IWERKS;//Plant (Own or External)
		com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31MatnrHolder tbInput = new 
				com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31MatnrHolder();
		//配件信息
		com.infoeai.eai.wsClient.parts.getSparePartsList.ZlvsdwsL31Matnr[] matnrs = 
				new com.infoeai.eai.wsClient.parts.getSparePartsList.ZlvsdwsL31Matnr[partInfoList.size()];
		for (int i=0;i<partInfoList.size();i++) {
			sedcsP01PartInfoDTO partInfoVo = partInfoList.get(i);
			com.infoeai.eai.wsClient.parts.getSparePartsList.ZlvsdwsL31Matnr matnr = 
					new com.infoeai.eai.wsClient.parts.getSparePartsList.ZlvsdwsL31Matnr();
			matnr.setIMatnr(partInfoVo.getImatnr());//物料号
			Double iqtyreq = partInfoVo.getIqtyreq();//需求量
			matnr.setIQtyreq(new BigDecimal(iqtyreq==null?"":iqtyreq+""));
			matnrs[i] = matnr;
			logger.info("====SEDCSP01 IMatnr ====:"+partInfoVo.getImatnr());
			logger.info("====SEDCSP01 IQtyreq ====:"+new BigDecimal(iqtyreq==null?"":iqtyreq+""));
		}
		tbInput.value = matnrs;
		com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31OutHolder tbOutput = new
				com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31OutHolder();
		com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfBapiret1Holder tbReturn = new 
				com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfBapiret1Holder();
		com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31MatHolder tbSubs = new 
				com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31MatHolder();
		logger.info("====SEDCSP01 Iauart ====:"+Iauart);
		logger.info("====SEDCSP01 IDealerUsr ====:"+IDealerUsr);
		logger.info("====SEDCSP01 IElcode ====:"+IElcode);
		logger.info("====SEDCSP01 IMecode ====:"+IMecode);
		logger.info("====SEDCSP01 ISubstitute ====:"+ISubstitute);
		logger.info("====SEDCSP01 IVhvin ====:"+IVhvin);
		logger.info("====SEDCSP01 IWerks ====:"+IWerks);
		logger.info("====SEDCSP01 IZzcliente ====:"+IZzcliente);
		logger.info("====SEDCSP01 tbInput ====:"+tbInput);
		//把数据发送到SAP然后返回值给DCS 在output中
		stub.zlvsdwsL31Out(
				Iauart, 
				IDealerUsr, 
				IElcode,
				IMarca,
				IMecode, 
				ISubstitute, 
				IVhvin, 
				IWerks,
				IZzcliente, 
				tbInput, 
				tbOutput, 
				tbReturn, 
				tbSubs);
		Map<String,String> partMap = null;
		//配件信息
		logger.info("====SEDCSP01 tbSubs.value.length ====:"+tbSubs.value.length);
		if (null != tbSubs && tbSubs.value.length > 0) {
			partMap = new HashMap<String,String>();
			com.infoeai.eai.wsClient.parts.getSparePartsList.ZlvsdwsL31Mat[] mats = tbSubs.value;
			for (int i=0;i<mats.length;i++) {
				com.infoeai.eai.wsClient.parts.getSparePartsList.ZlvsdwsL31Mat mat = mats[i];
				logger.info("====SEDCSP01 MatnrDms ====:"+mat.getMatnrDms());
				logger.info("====SEDCSP01 MatnrSap ====:"+mat.getMatnrSap());
				partMap.put(mat.getMatnrDms(), mat.getMatnrSap());
			}
		}
		//把返回的sap值封装到list中 然后返回给下端
		logger.info("====SEDCSP01 tbOutput.value.length ====:"+tbOutput.value.length);
		if (null != tbOutput && tbOutput.value.length > 0) {
			list = new ArrayList<SEDCSP01DTO>();
			com.infoeai.eai.wsClient.parts.getSparePartsList.ZlvsdwsL31Out[] outs = tbOutput.value;
			for (int i=0;i<outs.length;i++) {
				com.infoeai.eai.wsClient.parts.getSparePartsList.ZlvsdwsL31Out out = outs[i];
				voDCS = new SEDCSP01DTO();
				voDCS.setIwerks(out.getWerks());//工厂
				String entityCode = commonDao.getDealerEntityCodeByFCACode(out.getZzcliente());
				logger.info("====SEDCSP01 tbOutput.getMaktx ====:"+out.getMaktx());
				logger.info("====SEDCSP01 tbOutput.getQtyreq ====:"+out.getQtyreq());
				logger.info("====SEDCSP01 tbOutput.getIsstock ====:"+out.getIsstock());
				logger.info("====SEDCSP01 tbOutput.getOpnetwr ====:"+out.getOpnetwr());
				logger.info("====SEDCSP01 tbOutput.getNetwr ====:"+out.getNetwr());
				logger.info("====SEDCSP01 tbOutput.getTotal ====:"+out.getTotal());
				logger.info("====SEDCSP01 tbOutput.getOptotal ====:"+out.getOptotal());
				logger.info("====SEDCSP01 tbOutput.getHaschange ====:"+out.getHaschange());
				logger.info("====SEDCSP01 tbOutput.getDiscount ====:"+out.getDiscount());
				logger.info("====SEDCSP01 tbOutput.getVormg ====:"+out.getVormg());
				logger.info("====SEDCSP01 tbOutput.getPrice ====:"+out.getPrice());
				// 如果对应经销商没找到，则跳过
				if (!Utility.testString(entityCode)){
					continue;
				}
				voDCS.setZzcliente(entityCode);//特约店7位编码
				voDCS.setMarca(out.getMarca());//品牌
				voDCS.setDealerUsr(out.getDealerUsr());//特约店用户
				voDCS.setAuart(commonDao.getIauartByOrderType(out.getAuart()));//订单类型
				voDCS.setVhvin(out.getVhvin());//VIN
				voDCS.setElcode(out.getElcode());//电子编码
				voDCS.setMecode(out.getMecode());//机械代码
				voDCS.setSubstitute(out.getSubstitute());//获取替代件
				voDCS.setMatnr(out.getMatnr());//Material Number
				voDCS.setMaktx(out.getMaktx());//Material Description (Short Text)
				voDCS.setArktx(out.getArktx());//零部件名称
				voDCS.setVormg(out.getVormg()+"");//最小订货批量
				voDCS.setQtyin(out.getQtyin()+"");//传入参数中的需求量
				voDCS.setQtyreq(out.getQtyreq()+"");//按照最小订货批量折算后的计划量
				voDCS.setQtycom(out.getQtycom()+"");//SAP中库存量
				voDCS.setIsStock(out.getIsstock());//是否有库存
				voDCS.setHasChange(out.getHaschange());//是否有替换件
				voDCS.setDtest(DateUtil.yyyy_MM_dd2Date(out.getDtest()));//预估日期
				voDCS.setPrice(out.getPrice()+"");//不含税终端销售价
				voDCS.setDiscount(out.getDiscount()+"");//单个折扣
				voDCS.setOpNetwr(out.getOpnetwr()+"");//不含税单价
				voDCS.setOpTotal(out.getOptotal()+"");//不含税总额
				voDCS.setNetwr(out.getNetwr()+"");//含税单价
				voDCS.setTotal(out.getTotal()+"");//含税总额
				if (null != partMap) {
					voDCS.setMatnrSap(partMap.get(out.getMatnr()));// 强制替换的时候用   替换前的物料代码
					voDCS.setMatnrDms(out.getMatnr());// 强制替换的时候用   替换后的物料代码
				}
				list.add(voDCS);
			}
		}
		logger.info("====SEDCSP01 tbReturn.value.length ====:"+tbReturn.value.length);
		com.infoeai.eai.wsClient.parts.getSparePartsList.Bapiret1[] bapiret1s = tbReturn.value;
		String errorInfo="";
		if (bapiret1s != null && bapiret1s.length > 0) {
			//list = new ArrayList<SEDCSP01DTO>();
			for (int i=0;i<bapiret1s.length;i++) {
				//voDCS = new SEDCSP01DTO();
				com.infoeai.eai.wsClient.parts.getSparePartsList.Bapiret1 bapiret1 = bapiret1s[i];
				//错误信息返回
				if(!bapiret1.getType().equals("S")){
					errorInfo += bapiret1.getNumber()+":"+bapiret1.getMessage()+"\n";
				}
				logger.info("====SEDCSP01 bapiret1.getMessage() ====:"+bapiret1.getMessage());
				logger.info("====SEDCSP01 bapiret1.getType() ====:"+bapiret1.getType());
				//voDCS.setResultInfo(errorInfo);
				logger.info("sap 返回结果失败 :"+errorInfo);
				//list.add(voDCS);
			}
			//list.add(voDCS);
		}
		if(list.size()==0){
			SEDCSP01DTO vo1 = new SEDCSP01DTO();
			list.add(vo1);
		}
		list.get(0).setResultInfo(errorInfo);
		return list;
	}

	/**
	 * 到sap查询是否有数据 VO(供DE接口调用)
	 * @param vo
	 * @return
	 */
	public List<SEDCSP01VO> querySedcsP01VO(SEDCSP01VO vo) throws Exception {
		List<SEDCSP01VO> list = null;
		SEDCSP01VO voDCS = null;
		String IZzcliente = 
				commonDao.getFCACodeByEntityCode(vo.getIzzcliente()); //特约店7位编码    entityCode转换成FACCode
		// 因为接口现在IDealerUsr长度为15位，所以截取掉前面2位'20'
		String IDealerUsr = vo.getIdealerUsr();//特约店用户
		if (IDealerUsr !=null && IDealerUsr.length() > 15){
			IDealerUsr = IDealerUsr.substring(2);
		}
		String Iauart = commonDao.getOrderTypeByIauart(vo.getIauart());//订单类型
		String ISubstitute = vo.getIsubstitute();//是否获取替代件
		LinkedList<sedcsP01PartInfoVO> partInfoList = vo.getPartBaseInfoList();//配件信息
		//验收格式是否正确
		if (CommonUtils.checkIsNull(IZzcliente)) {
			list = new ArrayList<SEDCSP01VO>();
			voDCS = new SEDCSP01VO();
			logger.info("====IZzcliente 特约店7位编码 不能为空====");
			voDCS.setResultInfo("特约店7位编码不能为空");
			list.add(voDCS);
			return list;
		} 
		if (CommonUtils.checkIsNull(IDealerUsr)) {
			list = new ArrayList<SEDCSP01VO>();
			voDCS = new SEDCSP01VO();
			logger.info("====IDealerUsr 特约店用户 不能为空====");
			voDCS.setResultInfo("特约店用户 不能为空");
			list.add(voDCS);
			return list;
		} 
		if (CommonUtils.checkIsNull(Iauart)) {
			list = new ArrayList<SEDCSP01VO>();
			voDCS = new SEDCSP01VO();
			logger.info("====Iauart 订单类型 不能为空====");
			voDCS.setResultInfo("订单类型 不能为空");
			list.add(voDCS);
			return list;
		} 
		if (partInfoList!=null && partInfoList.size()<0) {
			list = new ArrayList<SEDCSP01VO>();
			voDCS = new SEDCSP01VO();
			logger.info("====partInfoList 配件信息不能为空====");
			voDCS.setResultInfo("配件信息不能为空");
			list.add(voDCS);
			return list;
		}
		//获取sap信息然后在信息
		com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_BindingStub stub = 
				new com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_BindingStub();
		com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_Service lmsService = 
				new com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_ServiceLocator();
		stub = (com.infoeai.eai.wsClient.parts.getSparePartsList.ZLVSDWS_L31_OUT_BindingStub)lmsService.getZLVSDWS_L31_OUT();
		logger.info("----------------调用对方服务地址："+lmsService.getZLVSDWS_L31_OUTAddress()+"----------------");
		String IMarca = OemDictCodeConstants.IMARCA;//品牌
		String IElcode = vo.getIelcode();//电子编码
		String IMecode = vo.getImecode();//机械代码
		String IVhvin = vo.getIvhvin();//VIN
		String IWerks = OemDictCodeConstants.IWERKS;//Plant (Own or External)
		com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31MatnrHolder tbInput = new 
				com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31MatnrHolder();
		//配件信息
		com.infoeai.eai.wsClient.parts.getSparePartsList.ZlvsdwsL31Matnr[] matnrs = 
				new com.infoeai.eai.wsClient.parts.getSparePartsList.ZlvsdwsL31Matnr[partInfoList.size()];
		for (int i=0;i<partInfoList.size();i++) {
			sedcsP01PartInfoVO partInfoVo = partInfoList.get(i);
			com.infoeai.eai.wsClient.parts.getSparePartsList.ZlvsdwsL31Matnr matnr = 
					new com.infoeai.eai.wsClient.parts.getSparePartsList.ZlvsdwsL31Matnr();
			matnr.setIMatnr(partInfoVo.getImatnr());//物料号
			Double iqtyreq = partInfoVo.getIqtyreq();//需求量
			matnr.setIQtyreq(new BigDecimal(iqtyreq==null?"":iqtyreq+""));
			matnrs[i] = matnr;
			logger.info("====SEDCSP01 IMatnr ====:"+partInfoVo.getImatnr());
			logger.info("====SEDCSP01 IQtyreq ====:"+new BigDecimal(iqtyreq==null?"":iqtyreq+""));
		}
		tbInput.value = matnrs;
		com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31OutHolder tbOutput = new
				com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31OutHolder();
		com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfBapiret1Holder tbReturn = new 
				com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfBapiret1Holder();
		com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31MatHolder tbSubs = new 
				com.infoeai.eai.wsClient.parts.getSparePartsList.holders.TableOfZlvsdwsL31MatHolder();
		logger.info("====SEDCSP01 Iauart ====:"+Iauart);
		logger.info("====SEDCSP01 IDealerUsr ====:"+IDealerUsr);
		logger.info("====SEDCSP01 IElcode ====:"+IElcode);
		logger.info("====SEDCSP01 IMecode ====:"+IMecode);
		logger.info("====SEDCSP01 ISubstitute ====:"+ISubstitute);
		logger.info("====SEDCSP01 IVhvin ====:"+IVhvin);
		logger.info("====SEDCSP01 IWerks ====:"+IWerks);
		logger.info("====SEDCSP01 IZzcliente ====:"+IZzcliente);
		logger.info("====SEDCSP01 tbInput ====:"+tbInput);
		//把数据发送到SAP然后返回值给DCS 在output中
		stub.zlvsdwsL31Out(
				Iauart, 
				IDealerUsr, 
				IElcode,
				IMarca,
				IMecode, 
				ISubstitute, 
				IVhvin, 
				IWerks,
				IZzcliente, 
				tbInput, 
				tbOutput, 
				tbReturn, 
				tbSubs);
		Map<String,String> partMap = null;
		//配件信息
		logger.info("====SEDCSP01 tbSubs.value.length ====:"+tbSubs.value.length);
		if (null != tbSubs && tbSubs.value.length > 0) {
			partMap = new HashMap<String,String>();
			com.infoeai.eai.wsClient.parts.getSparePartsList.ZlvsdwsL31Mat[] mats = tbSubs.value;
			for (int i=0;i<mats.length;i++) {
				com.infoeai.eai.wsClient.parts.getSparePartsList.ZlvsdwsL31Mat mat = mats[i];
				logger.info("====SEDCSP01 MatnrDms ====:"+mat.getMatnrDms());
				logger.info("====SEDCSP01 MatnrSap ====:"+mat.getMatnrSap());
				partMap.put(mat.getMatnrDms(), mat.getMatnrSap());
			}
		}
		//把返回的sap值封装到list中 然后返回给下端
		logger.info("====SEDCSP01 tbOutput.value.length ====:"+tbOutput.value.length);
		if (null != tbOutput && tbOutput.value.length > 0) {
			list = new ArrayList<SEDCSP01VO>();
			com.infoeai.eai.wsClient.parts.getSparePartsList.ZlvsdwsL31Out[] outs = tbOutput.value;
			for (int i=0;i<outs.length;i++) {
				com.infoeai.eai.wsClient.parts.getSparePartsList.ZlvsdwsL31Out out = outs[i];
				voDCS = new SEDCSP01VO();
				voDCS.setIwerks(out.getWerks());//工厂
				String entityCode = commonDao.getDealerEntityCodeByFCACode(out.getZzcliente());
				logger.info("====SEDCSP01 tbOutput.getMaktx ====:"+out.getMaktx());
				logger.info("====SEDCSP01 tbOutput.getQtyreq ====:"+out.getQtyreq());
				logger.info("====SEDCSP01 tbOutput.getIsstock ====:"+out.getIsstock());
				logger.info("====SEDCSP01 tbOutput.getOpnetwr ====:"+out.getOpnetwr());
				logger.info("====SEDCSP01 tbOutput.getNetwr ====:"+out.getNetwr());
				logger.info("====SEDCSP01 tbOutput.getTotal ====:"+out.getTotal());
				logger.info("====SEDCSP01 tbOutput.getOptotal ====:"+out.getOptotal());
				logger.info("====SEDCSP01 tbOutput.getHaschange ====:"+out.getHaschange());
				logger.info("====SEDCSP01 tbOutput.getDiscount ====:"+out.getDiscount());
				logger.info("====SEDCSP01 tbOutput.getVormg ====:"+out.getVormg());
				logger.info("====SEDCSP01 tbOutput.getPrice ====:"+out.getPrice());
				// 如果对应经销商没找到，则跳过
				if (!Utility.testString(entityCode)){
					continue;
				}
				voDCS.setZzcliente(entityCode);//特约店7位编码
				voDCS.setMarca(out.getMarca());//品牌
				voDCS.setDealerUsr(out.getDealerUsr());//特约店用户
				voDCS.setAuart(commonDao.getIauartByOrderType(out.getAuart()));//订单类型
				voDCS.setVhvin(out.getVhvin());//VIN
				voDCS.setElcode(out.getElcode());//电子编码
				voDCS.setMecode(out.getMecode());//机械代码
				voDCS.setSubstitute(out.getSubstitute());//获取替代件
				voDCS.setMatnr(out.getMatnr());//Material Number
				voDCS.setMaktx(out.getMaktx());//Material Description (Short Text)
				voDCS.setArktx(out.getArktx());//零部件名称
				voDCS.setVormg(out.getVormg()+"");//最小订货批量
				voDCS.setQtyin(out.getQtyin()+"");//传入参数中的需求量
				voDCS.setQtyreq(out.getQtyreq()+"");//按照最小订货批量折算后的计划量
				voDCS.setQtycom(out.getQtycom()+"");//SAP中库存量
				voDCS.setIsStock(out.getIsstock());//是否有库存
				voDCS.setHasChange(out.getHaschange());//是否有替换件
				voDCS.setDtest(DateUtil.yyyy_MM_dd2Date(out.getDtest()));//预估日期
				voDCS.setPrice(out.getPrice()+"");//不含税终端销售价
				voDCS.setDiscount(out.getDiscount()+"");//单个折扣
				voDCS.setOpNetwr(out.getOpnetwr()+"");//不含税单价
				voDCS.setOpTotal(out.getOptotal()+"");//不含税总额
				voDCS.setNetwr(out.getNetwr()+"");//含税单价
				voDCS.setTotal(out.getTotal()+"");//含税总额
				if (null != partMap) {
					voDCS.setMatnrSap(partMap.get(out.getMatnr()));// 强制替换的时候用   替换前的物料代码
					voDCS.setMatnrDms(out.getMatnr());// 强制替换的时候用   替换后的物料代码
				}
				list.add(voDCS);
			}
		}
		logger.info("====SEDCSP01 tbReturn.value.length ====:"+tbReturn.value.length);
		com.infoeai.eai.wsClient.parts.getSparePartsList.Bapiret1[] bapiret1s = tbReturn.value;
		String errorInfo="";
		if (bapiret1s != null && bapiret1s.length > 0) {
			//list = new ArrayList<SEDCSP01DTO>();
			for (int i=0;i<bapiret1s.length;i++) {
				//voDCS = new SEDCSP01DTO();
				com.infoeai.eai.wsClient.parts.getSparePartsList.Bapiret1 bapiret1 = bapiret1s[i];
				//错误信息返回
				if(!bapiret1.getType().equals("S")){
					errorInfo += bapiret1.getNumber()+":"+bapiret1.getMessage()+"\n";
				}
				logger.info("====SEDCSP01 bapiret1.getMessage() ====:"+bapiret1.getMessage());
				logger.info("====SEDCSP01 bapiret1.getType() ====:"+bapiret1.getType());
				//voDCS.setResultInfo(errorInfo);
				logger.info("sap 返回结果失败 :"+errorInfo);
				//list.add(voDCS);
			}
			//list.add(voDCS);
		}
		if(list.size()==0){
			SEDCSP01VO vo1 = new SEDCSP01VO();
			list.add(vo1);
		}
		list.get(0).setResultInfo(errorInfo);
		return list;
	}
	
}
