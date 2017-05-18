package com.infoeai.eai.wsClient.parts.eai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.yonyou.dms.function.exception.ServiceBizException;


public class P12Return {
	private static Logger logger = Logger.getLogger(P12Return.class);
	
	public List<P12ReturnVO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception {
		List<P12ReturnVO> voList = null;
		try {
			logger.info("==========P12Return getXMLToVO() is START==========");
			logger.info("==========XML赋值到VO==========");
			logger.info("==========XMLSIZE:==========" + xmlList.size());
			if (xmlList != null && xmlList.size() > 0) {
				voList = new ArrayList<P12ReturnVO>();
				for (int i = 0; i < xmlList.size(); i++) {
					Map<String, String> map = xmlList.get(i);
					if (map != null && map.size() > 0) {
						P12ReturnVO outVo = new P12ReturnVO();
						outVo.setMandt(map.get("mandt"));
						outVo.setMatnr(map.get("matnr"));
						outVo.setVkorg(map.get("vkorg"));
						outVo.setVtweg(map.get("vtweg"));
						outVo.setMaktx(map.get("maktx"));
						outVo.setMatkl(map.get("matkl"));
						outVo.setMtpos(map.get("mtpos"));
						outVo.setMeins(map.get("meins"));
						outVo.setMsehl(map.get("msehl"));
						outVo.setVormg(getBigDecimalByStr(map.get("vormg")));
						outVo.setKbetr(getBigDecimalByStr(map.get("kbetr")));
						outVo.setKonwa(map.get("konwa"));
						outVo.setKpein(getBigDecimalByStr(map.get("kpein")));
						outVo.setKmein(map.get("kmein"));
						outVo.setZflag(map.get("zflag"));
						outVo.setZprice(getBigDecimalByStr(map.get("zprice")));
						voList.add(outVo);
						logger.info("==========outVo:==========" + outVo);
					}
				}
			}
			logger.info("==========P12Return getXMLToVO() is END==========");
		} catch (Throwable e) {
			logger.info("==========XML赋值VO失败==========");
			logger.error(e.getMessage(), e);
			throw new ServiceBizException("P12Return XML转换处理异常！"+e);
		} finally {
			logger.info("==========P12Return getXMLToVO() is finish==========");
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
