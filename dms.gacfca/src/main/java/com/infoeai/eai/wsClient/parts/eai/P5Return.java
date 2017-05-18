package com.infoeai.eai.wsClient.parts.eai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.yonyou.dms.function.exception.ServiceBizException;


public class P5Return {
	private static Logger logger = Logger.getLogger(P5Return.class);
	
	public List<P5ReturnHeaderVO> setXMLToHeaderVO(List<Map<String, String>> xmlList) throws Exception {
		List<P5ReturnHeaderVO> voList = null;
		try {
			logger.info("==========P5ReturnHeaderVO getXMLToVO() is START==========");
			logger.info("==========XML赋值到VO==========");
			logger.info("==========XMLSIZE:==========" + xmlList.size());
			if (xmlList != null && xmlList.size() > 0) {
				voList = new ArrayList<P5ReturnHeaderVO>();
				for (int i = 0; i < xmlList.size(); i++) {
					Map<String, String> map = xmlList.get(i);
					if (map != null && map.size() > 0) {
						P5ReturnHeaderVO outVo = new P5ReturnHeaderVO();
						outVo.setWerks(map.get("werks"));
						outVo.setZzcliente(map.get("zzcliente"));
						outVo.setMarca(map.get("marca"));
						outVo.setDealerUsr(map.get("dealerUsr"));
						outVo.setVbeln(map.get("vbeln"));
						outVo.setErdat(map.get("erdat"));
						outVo.setErzet(map.get("erzet"));
						outVo.setTknum(map.get("tknum"));
						outVo.setTknum(map.get("bezei"));
						outVo.setDtabf(map.get("dtabf"));
						outVo.setUzabf(map.get("uzabf"));
						outVo.setVbelnDn(map.get("vbelnDn"));
						outVo.setBstnk(map.get("bstnk"));
						outVo.setVhvin(map.get("vhvin"));
						outVo.setTdlnr(map.get("tdlnr"));
						outVo.setName1(map.get("name1"));
						outVo.setFtabf(map.get("ftabf"));
						outVo.setPtabf(map.get("ptabf"));
						outVo.setUzeit(map.get("uzeit"));;
						outVo.setPodStatus(map.get("podStatus"));
						outVo.setOpInvnetwr(getBigDecimalByStr(map.get("opInvnetwr")));
						outVo.setOpInvmwsbk(getBigDecimalByStr(map.get("opInvmwsbk")));
						outVo.setOpDelcharge(getBigDecimalByStr(map.get("opDelcharge")));
						outVo.setOpTotalamout(getBigDecimalByStr(map.get("opTotalamout")));
						voList.add(outVo);
						logger.info("==========outVo:==========" + outVo);
					}
				}
			}
			logger.info("==========P5ReturnHeaderVO getXMLToVO() is END==========");
		} catch (Throwable e) {
			logger.info("==========XML赋值VO失败==========");
			logger.error(e.getMessage(), e);
			throw new ServiceBizException("P5ReturnHeaderVO XML转换处理异常！"+e);
		} finally {
			logger.info("==========P5ReturnHeaderVO getXMLToVO() is finish==========");
		}
		return voList;
	}
	
	public List<P5ReturnPositionVO> setXMLToPositionVO(List<Map<String, String>> xmlList) throws Exception {
		List<P5ReturnPositionVO> voList = null;
		try {
			logger.info("==========P5ReturnPositionVO getXMLToVO() is START==========");
			logger.info("==========XML赋值到VO==========");
			logger.info("==========XMLSIZE:==========" + xmlList.size());
			if (xmlList != null && xmlList.size() > 0) {
				voList = new ArrayList<P5ReturnPositionVO>();
				for (int i = 0; i < xmlList.size(); i++) {
					Map<String, String> map = xmlList.get(i);
					if (map != null && map.size() > 0) {
						P5ReturnPositionVO outVo = new P5ReturnPositionVO();
						outVo.setOpDelvbeln(map.get("opDelvbeln"));
						outVo.setOpOrdvbeln(map.get("opOrdvbeln"));
						outVo.setOpMabnr(map.get("opMabnr"));
						outVo.setOpArktx(map.get("opArktx"));
						outVo.setOpTotalamout(getBigDecimalByStr(map.get("opTotalamout")));
						outVo.setOpFklmg(getBigDecimalByStr(map.get("opFklmg")));
						outVo.setOpRetailprice(getBigDecimalByStr(map.get("opRetailprice")));
						outVo.setOpDiscount(getBigDecimalByStr(map.get("opDiscount")));
						voList.add(outVo);
						logger.info("==========outVo:==========" + outVo);
					}
				}
			}
			logger.info("==========P5ReturnPositionVO getXMLToVO() is END==========");
		} catch (Throwable e) {
			logger.info("==========XML赋值VO失败==========");
			logger.error(e.getMessage(), e);
			throw new ServiceBizException("P5ReturnPositionVO XML转换处理异常！"+e);
		} finally {
			logger.info("==========P5ReturnPositionVO getXMLToVO() is finish==========");
		}
		return voList;
	}
	
	
	/**
	 * str to BigDecimal 
	 * by wujinbiao
	 */
	public java.math.BigDecimal getBigDecimalByStr(String str) {
		if (null == str ||
				"null".equals(str) ||
				"".equals(str)) {
			return new java.math.BigDecimal("0");
		} else {
			return new java.math.BigDecimal(str);
		}
	}


}
