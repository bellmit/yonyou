package com.infoeai.eai.action.ncserp.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.common.EAIConstant;
import com.infoeai.eai.dao.ctcai.SICommonDao;
import com.infoeai.eai.po.TiColoriEstemiVeicoloPO;
import com.infoeai.eai.po.TiCommercialModelsPO;
import com.infoeai.eai.po.TiCoppieColoriVeicoloPO;
import com.infoeai.eai.po.TiOptIncompatibiliPO;
import com.infoeai.eai.po.TiOptVeicoloPO;
import com.infoeai.eai.po.TiOptVincolAlteSellPO;
import com.infoeai.eai.po.TiOptVincolatiPO;
import com.infoeai.eai.po.TiOptVincolatoColoreEstemoPO;
import com.infoeai.eai.po.TiOptVincolatoSelleriaPO;
import com.infoeai.eai.po.TiOptionalPackPO;
import com.infoeai.eai.po.TiSellerieVeicoloPO;
import com.infoeai.eai.po.TiVehicleTypePO;
import com.infoeai.eai.po.TiVeicoliPO;
import com.infoeai.eai.po.TiVincolAltColEstPO;

@Service
public class PCExecutorImpl extends BaseService {
	
	private static Logger logger = LoggerFactory.getLogger(PCExecutorImpl.class);
	Calendar sysDate = Calendar.getInstance();

	@Autowired
	SICommonDao siComDAO;
	
	public String storePcData(String[] pcData,Long batchId) throws Exception {
		String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
		try {
			String codRow = pcData[0].trim();
			if ("13".equals(codRow)) {
				returnResult = insertTiVehicleType(pcData,batchId);
			} else if ("15".equals(codRow)) {
				returnResult = insertCommercialModels(pcData,batchId);
			} else if ("16".equals(codRow)) {
				returnResult = insertTiVeicoliPo(pcData,batchId);
			} else if ("19".equals(codRow)) {
				returnResult = insertOptVeicolo(pcData,batchId);
			} else if ("22".equals(codRow)) {
				returnResult = insertCoppieColoriVeicolo(pcData,batchId);
			} else if ("23".equals(codRow)) {
				returnResult = insertoptvincolati(pcData,batchId);
			} else if ("24".equals(codRow)) {
				returnResult = insertOptIncompatibili(pcData,batchId);
			} else if ("25".equals(codRow)) {
				returnResult = insertOptVincolatoColoreEsterno(pcData,batchId);
			} else if ("26".equals(codRow)) {
				returnResult = insertOptVincolatoSelleria(pcData,batchId);
			} else if ("30".equals(codRow)) {
				returnResult = insertColoriEstemiVeicolo(pcData,batchId);
			} else if ("31".equals(codRow)) {
				returnResult = insertSellerieVeicolo(pcData,batchId);
			} else if ("33".equals(codRow)) {
				returnResult = insertOptionalPack(pcData,batchId);
			} else if ("35".equals(codRow)) {
				returnResult = insertOptVincolatoAlternativoColoreEsterno(pcData,batchId);
			} else if ("36".equals(codRow)) {
				returnResult = insertOptVincolAlteSell(pcData,batchId);
			}

		} catch (Throwable e) {
			returnResult = EAIConstant.DEAL_FAIL;
			logger.error("=============PC 插入接口表处理异常============" + e.getMessage(), e);
			throw new Exception("============PC 插入接口表处理异常==================" + e);
		}
		returnResult = EAIConstant.DEAL_SUCCESS;
		return returnResult;
	}

	/**
	 * 插入数据表"TI_COLORI_ESTEMI_VEICOLO_DCS"调用 codRow：30
	 * 
	 * @param pcData
	 *            解析出txt文档里的具体一行数据
	 * @param batchId 
	 * @return
	 * @throws Exception
	 */
	public String insertColoriEstemiVeicolo(String[] pcData, Long batchId) throws Exception {
		String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
		TiColoriEstemiVeicoloPO cev = new TiColoriEstemiVeicoloPO();
		try {
			// 根据txt文档与表结构先后顺序插入TI_VEHICLE_TYPE，pcData是解析出txt文档里的具体一行数据。
			cev.setLong("BATCH_ID",batchId);
			cev.setString("COD_TIPO_RECORD",pcData[0].trim());
			cev.setString("COD_MARCHET",pcData[1].trim());
			cev.setString("COD_LANGUAGE",pcData[2].trim());
			cev.setString("COD_VEHICLE_BRA",pcData[3].trim());
			cev.setInteger("ID_VEHICLE",Integer.parseInt(pcData[4].trim()));
			cev.setString("COD_VEHICLE",pcData[5].trim());
			cev.setString("COD_SPECIAL_SERIE",pcData[6].trim());
			cev.setString("COD_EXTEMAL_COLOR",pcData[7].trim());
			cev.setString("EXTEMAL_COLOR_DES",pcData[8].trim());
			cev.setString("IS_MAPPING","0");
			cev.setTimestamp("CREATE_DATE", new Date());
			// cev.setRemark(remark);
			// cev.setUpdateDate(updateDate);
			cev.saveIt();
		} catch (Throwable e) {
			returnResult = EAIConstant.DEAL_FAIL;
			logger.error("=============PC 插入TI_COLORI_ESTEMI_VEICOLO_DCS表处理异常============"+e.getMessage(), e);
			throw new Exception("============PC 插入TI_COLORI_ESTEMI_VEICOLO_DCS表处理异常==================" + e);
		}
		returnResult = EAIConstant.DEAL_SUCCESS;
		return returnResult;
	}

	/**
	 * 插入数据表"TI_COMMERCIAL_MODELS_DCS"调用 codRow：15
	 * 
	 * @param pcData
	 *            是解析出txt文档里的具体一行数据。
	 * @param batchId 
	 * @return
	 * @throws Exception
	 */
	public String insertCommercialModels(String[] pcData, Long batchId) throws Exception {
		String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
		TiCommercialModelsPO tcm = new TiCommercialModelsPO();
		try {
			// 根据txt文档与表结构先后顺序插入TI_VEHICLE_TYPE，pcData是解析出txt文档里的具体一行数据。
			tcm.setString("COD_TIPO_RECORD",pcData[0].trim());
			tcm.setString("COD_MARCHET",pcData[1].trim());
			tcm.setString("COD_LANGUAGE",pcData[2].trim());
			tcm.setString("COD_VEHICLE_BRA",pcData[3].trim());
			tcm.setString("COD_COMMERCIAL_MODEL",pcData[4].trim());
			tcm.setString("COMMERCIAL_MODEL_DES",pcData[5].trim());
			tcm.setString("COD_VEHICLE_TYPE",pcData[6].trim());
			tcm.setInteger("INDEX_CODE",Integer.parseInt(pcData[7].trim()));
			tcm.setInteger("FLAG_SERIE_LIMITED",Integer.parseInt(pcData[8].trim()));
			tcm.setTimestamp("CREATE_DATE", new Date());
			tcm.setString("IS_MAPPING","0");
			tcm.setLong("BATCH_ID",batchId);
			tcm.saveIt();
		} catch (Throwable e) {
			returnResult = EAIConstant.DEAL_FAIL;
			logger.error("=============PC 插入TI_COMMERCIAL_MODELS_DCS表处理异常============" +e.getMessage(), e);
			throw new Exception("============PC 插入TI_COMMERCIAL_MODELS_DCS表处理异常==================" + e);
		}
		returnResult = EAIConstant.DEAL_SUCCESS;
		return returnResult;
	}

	/**
	 * 插入数据表"TI_COPPIE_COLORI_VEICOLO_DCS"调用 codRow：22
	 * 
	 * @param pcData
	 *            是解析出txt文档里的具体一行数据。
	 * @param batchId 
	 * @return
	 * @throws Exception
	 */
	public String insertCoppieColoriVeicolo(String[] pcData, Long batchId) throws Exception {
		String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
		TiCoppieColoriVeicoloPO ccv = new TiCoppieColoriVeicoloPO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			// 根据txt文档与表结构先后顺序插入TI_VEHICLE_TYPE，pcData是解析出txt文档里的具体一行数据。
			ccv.setLong("BATCH_ID",batchId);
			ccv.setString("COD_TIPO_RECORD",pcData[0].trim());
			ccv.setString("COD_MARCHET",pcData[1].trim());
			ccv.setString("COD_VEHICLE_BRA",pcData[2].trim());
			ccv.setInteger("ID_VEHICLE",Integer.parseInt(pcData[3].trim()));
			ccv.setString("COD_VEHICLE",pcData[4].trim());
			ccv.setString("COD_SPECIAL_SERIE",pcData[5].trim());
			ccv.setString("COD_EXTEMAL_COLOR",pcData[6].trim());
			ccv.setString("COD_TRIM",pcData[7].trim());
			ccv.setString("COD_SECONDARY_COLOR",pcData[8].trim());
			ccv.setString("COD_TONE",pcData[9].trim());
			ccv.setString("COD_PACK",pcData[10].trim());
			ccv.setString("COD_ROOF",pcData[11].trim());
			if (pcData[12] != null && !pcData[12].trim().equals("")) {
				ccv.setTimestamp("ORD_START_DATE",sdf.parse(pcData[12].trim()));
			}
			if (pcData[13] != null && !pcData[13].trim().equals("")) {
				ccv.setTimestamp("ORD_END_DATE",sdf.parse(pcData[13].trim()));
			}
			if (pcData[14] != null && !pcData[14].trim().equals("")) {
				ccv.setTimestamp("SALES_START_DATE",sdf.parse(pcData[14].trim()));
			}
			if (pcData[15] != null && !pcData[15].trim().equals("")) {
				ccv.setTimestamp("SALES_END_DATE",sdf.parse(pcData[15].trim()));
			}
			if (pcData[16] != null && !pcData[16].trim().equals("")) {
				ccv.setTimestamp("START_DATE_VISIBILITY",sdf.parse(pcData[16].trim()));
			}
			if (pcData[17] != null && !pcData[17].trim().equals("")) {
				ccv.setTimestamp("END_DATE_VISIBILITY",sdf.parse(pcData[17].trim()));
			}
			ccv.setTimestamp("CREATE_DATE",new Date());
			ccv.setString("IS_MAPPING","0");
			ccv.saveIt();
		} catch (Throwable e) {
			returnResult = EAIConstant.DEAL_FAIL;
			logger.error("=============PC 插入TI_COPPIE_COLORI_VEICOLO_DCS表处理异常============" + e.getMessage(), e);
			throw new Exception("============PC 插入TI_COPPIE_COLORI_VEICOLO_DCS表处理异常==================" + e);
		}
		returnResult = EAIConstant.DEAL_SUCCESS;
		return returnResult;
	}

	/**
	 * 插入数据表"TI_OPT_INCOMPATIBILI_DCS"调用 codRow：24
	 * 
	 * @param pcData
	 *            是解析出txt文档里的具体一行数据。
	 * @param batchId 
	 * @return
	 * @throws Exception
	 */
	public String insertOptIncompatibili(String[] pcData, Long batchId) throws Exception {
		String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
		TiOptIncompatibiliPO oil = new TiOptIncompatibiliPO();
		try {
			// 根据txt文档与表结构先后顺序插入TI_VEHICLE_TYPE，pcData是解析出txt文档里的具体一行数据。
			oil.setLong("BATCH_ID",batchId);
			oil.setString("COD_TIPO_RECORD",pcData[0].trim());
			oil.setString("COD_MARCHET",pcData[1].trim());
			oil.setString("COD_VEHICLE_BRA",pcData[2].trim());
			oil.setInteger("ID_VEHICLE",Integer.parseInt(pcData[3].trim()));
			oil.setString("COD_VEHICLE",pcData[4].trim());
			oil.setString("COD_SPECIAL_SERIE",pcData[5].trim());
			oil.setString("COD_OPTIONAL",pcData[6].trim());
			oil.setString("COD_OPTIONAL_INCOMPATIBLE",pcData[7].trim());
			oil.setString("IS_MAPPING","0");
			oil.setTimestamp("CREATE_DATE",new Date());
			oil.saveIt();
		} catch (Throwable e) {
			returnResult = EAIConstant.DEAL_FAIL;
			logger.error("=============PC 插入TI_OPT_INCOMPATIBILI_DCS表处理异常============" + e.getMessage(), e);
			throw new Exception("============PC 插入TI_OPT_INCOMPATIBILI_DCS表处理异常==================" + e);
		}
		returnResult = EAIConstant.DEAL_SUCCESS;
		return returnResult;
	}

	/**
	 * 插入数据表"TI_OPTIONAL_PACK_DCS"调用 codRow：33
	 * 
	 * @param pcData
	 * @param batchId 
	 * @return
	 * @throws Exception
	 */
	public String insertOptionalPack(String[] pcData, Long batchId) throws Exception {
		String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
		TiOptionalPackPO opp = new TiOptionalPackPO();
		try {
			// 根据txt文档与表结构先后顺序插入TI_VEHICLE_TYPE，pcData是解析出txt文档里的具体一行数据。
			opp.setLong("BATCH_ID",batchId);
			opp.setString("COD_TIPO_RECORD",pcData[0].trim());
			opp.setString("COD_MARCHET",pcData[1].trim());
			opp.setString("COD_OPTIONAL_PACK",pcData[2].trim());
			opp.setString("COD_OPTIONAL_COMPONENT",pcData[3].trim());
			opp.setString("IS_MAPPING","0");
			opp.setTimestamp("CREATE_DATE",new Date());
			opp.saveIt();
		} catch (Throwable e) {
			returnResult = EAIConstant.DEAL_FAIL;
			logger.error("=============PC 插入TI_OPTIONAL_PACK_DCS表处理异常============" + e.getMessage(), e);
			throw new Exception("============PC 插入TI_OPTIONAL_PACK_DCS表处理异常==================" + e);
		}
		returnResult = EAIConstant.DEAL_SUCCESS;
		return returnResult;
	}

	/**
	 * 插入数据表"TI_OPT_VEICOLO_DCS"调用 codRow：19
	 * @param batchId 
	 * 
	 * @param pcData是解析出txt文档里的具体一行数据
	 * @return
	 * @throws Exception
	 */
	public String insertOptVeicolo(String[] pcData, Long batchId) throws Exception {
		String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
		TiOptVeicoloPO ov = new TiOptVeicoloPO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			// 根据txt文档与表结构先后顺序插入TI_VEHICLE_TYPE，pcData是解析出txt文档里的具体一行数据。
			ov.setLong("BATCH_ID",batchId);
			ov.setString("COD_TIPO_RECORD",pcData[0].trim());
			ov.setString("COD_MARCHET",pcData[1].trim());
			ov.setString("COD_LANGUAGE",pcData[2].trim());
			ov.setString("COD_VEHICLE_BRA",pcData[3].trim());
			ov.setInteger("ID_VEHICLE",Integer.parseInt(pcData[4].trim()));
			ov.setString("COD_VEHICLE",pcData[5].trim());
			ov.setString("COD_SPECIAL_SERIE",pcData[6].trim());
			ov.setString("COD_OPTIONAL",pcData[7].trim());
			ov.setString("TYPE_AVAILABILITY",pcData[8].trim());
			ov.setInteger("FLAG_MANDATORY",Integer.parseInt(pcData[9].trim()));
			ov.setString("OPTIONAL_DES",pcData[10].trim());
			ov.setString("COD_CATEGORIA_OPTIONAL",pcData[11].trim());
			if (pcData[12] != null && !pcData[12].equals("")) {
				ov.setTimestamp("ORD_START_DATE",sdf.parse(pcData[12].trim()));
			}
			if (pcData[13] != null && !pcData[13].trim().equals("")) {
				ov.setTimestamp("ORD_END_DATE",sdf.parse(pcData[13].trim()));
			}
			if (pcData[14] != null && !pcData[14].trim().equals("")) {
				ov.setTimestamp("SALES_START_DATE",sdf.parse(pcData[14].trim()));
			}
			if (pcData[15] != null && !pcData[15].trim().equals("")) {
				ov.setTimestamp("SALES_END_DATE",sdf.parse(pcData[15].trim()));
			}
			ov.setInteger("TLAG_SHOWTO_DEALER",Integer.parseInt(pcData[16].trim()));
			ov.setString("FAM_ALTEMATIVITY",pcData[17].trim());
			ov.setInteger("FG_GRIDS",Integer.parseInt(pcData[18].trim()));
			ov.setInteger("FG_SPECIALSERIE",Integer.parseInt(pcData[19].trim()));
			if(pcData[20]!=null&&!pcData[20].trim().equals("")){
				ov.setTimestamp("START_DATE_VISIBILITY",sdf.parse(pcData[20].trim()));
			}
			if(pcData[21]!=null&&!pcData[21].trim().equals("")){
				ov.setTimestamp("END_DATE_VISIBILITY",sdf.parse(pcData[21].trim()));
			}
			ov.setInteger("FG_MANDARY_LOCAL",Integer.parseInt(pcData[22].trim()));
			ov.setString("LONG_DESCRIPTION",pcData[23].trim());
			ov.setString("IS_MAPPING","0");
			ov.setTimestamp("CREATE_DATE",new Date());
			ov.saveIt();
		} catch (Throwable e) {
			returnResult = EAIConstant.DEAL_FAIL;
			logger.error("=============PC 插入TI_OPT_VEICOLO_DCS表处理异常============" + e.getMessage(), e);
			throw new Exception("============PC 插入TI_OPT_VEICOLO_DCS表处理异常==================" + e);
		}
		returnResult = EAIConstant.DEAL_SUCCESS;
		return returnResult;
	}

	/**
	 * 插入数据表"TI_OPT_VINCOL_ALTE_SELL_DCS"调用 codRow：36
	 * @param batchId 
	 * 
	 * @param pcData是解析出txt文档里的具体一行数据
	 * @return
	 * @throws Exception
	 */
	public String insertOptVincolAlteSell(String[] pcData, Long batchId) throws Exception {
		String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
		TiOptVincolAlteSellPO ovas = new TiOptVincolAlteSellPO();
		try {
			// 根据txt文档与表结构先后顺序插入TI_VEHICLE_TYPE，pcData是解析出txt文档里的具体一行数据。
			ovas.setLong("BATCH_ID",batchId);
			ovas.setString("COD_TIPO_RECORD",pcData[0].trim());
			ovas.setString("COD_MARCHET",pcData[1].trim());
			ovas.setString("COD_VEHICLE_BRA",pcData[2].trim());
			ovas.setInteger("ID_VEHICLE",Integer.parseInt(pcData[3].trim()));
			ovas.setString("COD_VEHICLE",pcData[4].trim());
			ovas.setString("COD_SPECIAL_SERIE",pcData[5].trim());
			ovas.setString("COD_TRIM",pcData[6].trim());
			ovas.setString("COD_OPTIONAL_LINKED",pcData[7].trim());
			ovas.setString("COD_ALTEMATIVE_OPTION",pcData[8].trim());
			ovas.setString("IS_MAPPING","0");
			ovas.setTimestamp("CREATE_DATE",new Date());
			ovas.saveIt();
		} catch (Throwable e) {
			returnResult = EAIConstant.DEAL_FAIL;
			logger.error("=============PC 插入TI_OPT_VINCOL_ALTE_SELL_DCS表处理异常============"+e.getMessage(), e);
			throw new Exception("============PC 插入TI_OPT_VINCOL_ALTE_SELL_DCS表处理异常=================="+ e);
		}
		returnResult = EAIConstant.DEAL_SUCCESS;
		return returnResult;
	}

	/**
	 * 插入数据表"TI_OPT_VINCOLATI_DCS"调用 codRow：23
	 * 
	 * @param pcData
	 * @param batchId 
	 * @return
	 * @throws Exception
	 */
	public String insertoptvincolati(String[] pcData, Long batchId) throws Exception {
		String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
		TiOptVincolatiPO tovp = new TiOptVincolatiPO();
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		try {
			tovp.setLong("BATCH_ID",batchId);
			tovp.setString("COD_TIPO_RECORD",pcData[0].trim());
			tovp.setString("COD_MARCHET",pcData[1].trim());
			tovp.setString("COD_VEHICLE_BRA",pcData[2].trim());
			tovp.setInteger("ID_VEHICLE",Integer.parseInt(pcData[3].trim()));
			tovp.setString("COD_VEHICLE",pcData[4].trim());
			tovp.setString("COD_SPECIAL_SERIE",pcData[5].trim());
			tovp.setString("COD_OPTIONAL",pcData[6].trim());
			tovp.setString("COD_OPTIONAL_LINKED",pcData[7].trim());
			tovp.setString("COD_PROGRESSIVE",pcData[8].trim());
			tovp.setString("IS_MAPPING","0");
			tovp.setTimestamp("CREATE_DATE",new Date());
			tovp.saveIt();

		} catch (Throwable e) {
			returnResult = EAIConstant.DEAL_FAIL;
			logger.error("=============PC 插入TI_OPT_VINCOLATI_DCS表处理异常============" + e.getMessage(), e);
			throw new Exception("============PC 插入TI_OPT_VINCOLATI_DCS表处理异常==================" + e);
		}
		returnResult = EAIConstant.DEAL_SUCCESS;
		return returnResult;
	}

	/**
	 * 插入数据表"TI_SELLERIE_VEICOLO_DCS"调用 31
	 * 
	 * @param pcData
	 * @param batchId 
	 * @return
	 * @throws Exception
	 */
	public String insertSellerieVeicolo(String[] pcData, Long batchId) throws Exception {
		String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
		TiSellerieVeicoloPO tsvp = new TiSellerieVeicoloPO();
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		try {
			tsvp.setLong("BATCH_ID",batchId);
			tsvp.setString("COD_TIPO_RECORD",pcData[0].trim());
			tsvp.setString("COD_MARCHET",pcData[1].trim());
			tsvp.setString("COD_LANGUAGE",pcData[2].trim());
			tsvp.setString("COD_VEHICLE_BRA",pcData[3].trim());
			tsvp.setInteger("ID_VEHICLE",Integer.parseInt(pcData[4].trim()));
			tsvp.setString("COD_VEHICLE",pcData[5].trim());
			tsvp.setString("COD_SPECIAL_SERIE",pcData[6].trim());
			tsvp.setString("COD_TRIM",pcData[7].trim());
			tsvp.setString("TRIM_DES",pcData[8].trim());
			tsvp.setString("IS_MAPPING","0");
			tsvp.setTimestamp("CREATE_DATE",new Date());
			tsvp.saveIt();
		} catch (Throwable e) {
			returnResult = EAIConstant.DEAL_FAIL;
			logger.error("=============PC 插入TI_SELLERIE_VEICOLO_DCS表处理异常============" + e.getMessage(), e);
			throw new Exception("============PC 插入TI_SELLERIE_VEICOLO_DCS表处理异常==================" + e);
		}
		returnResult = EAIConstant.DEAL_SUCCESS;
		return returnResult;
	}

	/**
	 * 插入数据表"TI_OPT_VINCOLATO_COLORE_ESTEMO_DCS"调用 codRow：25
	 * 
	 * @param pcData
	 * @param batchId 
	 * @return
	 * @throws Exception
	 */
	public String insertOptVincolatoColoreEsterno(String[] pcData, Long batchId)
			throws Exception {
		String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
		TiOptVincolatoColoreEstemoPO tovce = new TiOptVincolatoColoreEstemoPO();
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		try {
			tovce.setLong("BATCH_ID",batchId);
			tovce.setString("COD_TIPO_RECORD",pcData[0].trim());
			tovce.setString("COD_MARCHET",pcData[1].trim());
			tovce.setString("COD_VEHICLE_BRA",pcData[2].trim());
			tovce.setString("YEAR_CODE",pcData[3].trim());
			tovce.setString("COD_VEHICLE_FAMILY",pcData[4].trim());
			tovce.setString("COD_BODY_MODEL",pcData[5].trim());
			tovce.setString("COD_CPOS",pcData[6].trim());
			tovce.setString("COD_MODEL_YEAR",pcData[7].trim());
			tovce.setString("COD_SPECIAL_SERIE",pcData[8].trim());
			tovce.setString("COD_EXTEMAL_COLOR",pcData[9].trim());
			tovce.setString("COD_OPTIONAL_LINKED",pcData[10].trim());
			tovce.setTimestamp("CREATE_DATE",new Date());
			tovce.setString("IS_MAPPING","0");
			tovce.saveIt();

		} catch (Throwable e) {
			returnResult = EAIConstant.DEAL_FAIL;
			logger.error("=============PC 插入TI_OPT_VINCOLATO_COLORE_ESTEMO_DCS表处理异常============" + e.getMessage(), e);
			throw new Exception("============PC 插入TI_OPT_VINCOLATO_COLORE_ESTEMO_DCS表处理异常=================="	+ e);
		}
		returnResult = EAIConstant.DEAL_SUCCESS;
		return returnResult;
	}

	/**
	 * 插入数据表"TI_OPT_VINCOLATO_SELLERIA_DCS"调用 codRow：26
	 * 
	 * @param pcData
	 * @param batchId 
	 * @return
	 * @throws Exception
	 */
	public String insertOptVincolatoSelleria(String[] pcData, Long batchId) throws Exception {
		String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
		TiOptVincolatoSelleriaPO tovsp = new TiOptVincolatoSelleriaPO();
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		try {
			tovsp.setLong("BATCH_ID",batchId);
			tovsp.setString("COD_TIPO_RECORD",pcData[0].trim());
			tovsp.setString("COD_MARCHET",pcData[1].trim());
			tovsp.setString("COD_VEHICLE_BRA",pcData[2].trim());
			tovsp.setInteger("ID_VEHICLE",Integer.parseInt(pcData[3].trim().trim()));
			tovsp.setString("COD_VEHICLE",pcData[4].trim());
			tovsp.setString("COD_SPECIAL_SERIE",pcData[5].trim());
			tovsp.setString("COD_TRIM",pcData[6].trim());
			tovsp.setString("COD_OPTIONAL_LINKED",pcData[7].trim());
			tovsp.setTimestamp("CREATE_DATE",new Date());
			tovsp.setString("IS_MAPPING","0");
			tovsp.saveIt();
		} catch (Throwable e) {
			returnResult = EAIConstant.DEAL_FAIL;
			logger.error("=============PC 插入TI_OPT_VINCOLATO_SELLERIA_DCS表处理异常============"+ e.getMessage(), e);
			throw new Exception("============PC 插入TI_OPT_VINCOLATO_SELLERIA_DCS表处理异常=================="+ e);
		}
		returnResult = EAIConstant.DEAL_SUCCESS;
		return returnResult;
	}

	/**
	 * 插入数据表"TI_VEHICLE_TYPE_DCS"调用 codRow：13
	 * 
	 * @param pcData
	 * @return
	 * @throws Exception
	 */
	public String insertTiVehicleType(String[] pcData,Long batchId) throws Exception {
		String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
		TiVehicleTypePO topp = new TiVehicleTypePO();
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		try {
			topp.setString("COD_TIPO_RECORD",pcData[0].trim());
			topp.setString("COD_MARCHET",pcData[1].trim());
			topp.setString("COD_LANGUAGE",pcData[2].trim());
			topp.setString("COD_VEHICLE_TYPE",pcData[3].trim());
			topp.setString("VEHICL_TYPE_DES",pcData[4].trim());
			topp.setTimestamp("CREATE_DATE",new Date());
			topp.setString("IS_MAPPING","0");
			topp.setLong("BATCH_ID",batchId);
			topp.saveIt();
		} catch (Throwable e) {
			returnResult = EAIConstant.DEAL_FAIL;
			logger.error("=============PC 插入TI_VEHICLE_TYPE_DCS表处理异常============"+e.getMessage(), e);
			throw new Exception("============PC 插入TI_VEHICLE_TYPE_DCS表处理异常=================="+ e);
		}
		returnResult = EAIConstant.DEAL_SUCCESS;
		return returnResult;
	}

	/**
	 * 插入数据表"TI_VINCOL_ALT_COL_EST_DCS"调用 35
	 * 
	 * @param pcData
	 *            是解析出txt文档里的具体一行数据
	 * @param batchId 
	 * @return
	 * @throws Exception
	 */
	public String insertOptVincolatoAlternativoColoreEsterno(String[] pcData, Long batchId)
			throws Exception {
		String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
		TiVincolAltColEstPO tvacp = new TiVincolAltColEstPO();
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		try {
			tvacp.setLong("BATCH_ID",batchId);
			tvacp.setString("COD_TIPO_RECORD",pcData[0].trim());
			tvacp.setString("COD_MARCHET",pcData[1].trim());
			tvacp.setString("COD_VEHICLE_BRA",pcData[2].trim());
			tvacp.setInteger("ID_VEHICLE",Integer.parseInt(pcData[3].trim()));
			tvacp.setString("COD_VEHICLE",pcData[4].trim());
			tvacp.setString("COD_SPECIAL_SERIE",pcData[5].trim());
			tvacp.setString("COD_EXTEMAL_COLOR",pcData[6].trim());
			tvacp.setString("COD_OPTION_LINKED",pcData[7].trim());
			tvacp.setString("COD_ALTEMATIVE_OPTION",pcData[8].trim());
			tvacp.setTimestamp("CREATE_DATE",new Date());
			tvacp.setString("IS_MAPPING","0");
			tvacp.saveIt();
		} catch (Throwable e) {
			returnResult = EAIConstant.DEAL_FAIL;
			logger.error("=============PC 插入TI_VINCOL_ALT_COL_EST_DCS表处理异常============"+e.getMessage(), e);
			throw new Exception("============PC 插入TI_VINCOL_ALT_COL_EST_DCS表处理异常==================" + e);
		}
		returnResult = EAIConstant.DEAL_SUCCESS;
		return returnResult;
	}

	/**
	 * 插入数据表"TI_VEICOLI_DCS"调用 16
	 * 
	 * @param pcData
	 *            是解析出txt文档里的具体一行数据
	 * @param batchId 
	 * @return
	 * @throws Exception
	 */
	public String insertTiVeicoliPo(String[] pcData, Long batchId) throws Exception {
		String returnResult = EAIConstant.DEAL_FAIL; // 返回02表示成功,01表示失败
		TiVeicoliPO tovsp = new TiVeicoliPO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		try {
			tovsp.setLong("BATCH_ID",batchId);
			tovsp.setString("COD_TIPO_RECORD",pcData[0].trim());
			tovsp.setString("COD_MARCHET",pcData[1].trim());
			tovsp.setString("COD_LANGUAGE",pcData[2].trim());
			tovsp.setString("COD_VEHICLE_BRA",pcData[3].trim());
			tovsp.setInteger("ID_VEHICLE",Integer.parseInt(pcData[4].trim()));
			tovsp.setString("COD_VEHICLE",pcData[5].trim());
			tovsp.setString("COD_SPECIAL_SERIE",pcData[6].trim());
			tovsp.setString("COD_LOW_CPOS",pcData[7].trim());
			tovsp.setString("VEHICLE_DES",pcData[8].trim());
			tovsp.setString("COD_COMMERCIAL_MODEL",pcData[9].trim());
			tovsp.setString("COD_COMMERCIAL_VERSION",pcData[10].trim());
			tovsp.setString("COD_VEHICLE_TYPE",pcData[11].trim());
			tovsp.setInteger("INDEXT",Integer.parseInt(pcData[12].trim()));
			tovsp.setString("ENGINEE",pcData[13].trim());
			tovsp.setString("TRASMISSION",pcData[14].trim());
			tovsp.setString("GEAR",pcData[15].trim());
			tovsp.setString("ENGINE_DES",pcData[16].trim());
			tovsp.setString("TRASMISSION_DES",pcData[17].trim());
			if (pcData[18] != null && !pcData[18].trim().equals("")) {
				tovsp.setTimestamp("ORD_START_DATE",sdf.parse(pcData[18].trim()));
			}
			if (pcData[19] != null && !pcData[19].trim().equals("")) {
				tovsp.setTimestamp("ORD_END_DATE",sdf.parse(pcData[19].trim()));
			}
			if (pcData[20] != null && !pcData[20].trim().equals("")) {
				tovsp.setTimestamp("SALES_START_DATE",sdf.parse(pcData[20].trim()));
			}
			if (pcData[21] != null && !pcData[21].trim().equals("")) {
				tovsp.setTimestamp("SALES_END_DATE",sdf.parse(pcData[21].trim()));
			}
			tovsp.setString("TLAG_SHOWTO_DEALER",pcData[22].trim());
			tovsp.setString("FLAG_COD_TYPE",pcData[23].trim());
			if (pcData[24] != null && !pcData[24].trim().equals("")) {
				tovsp.setTimestamp("START_DATE_VISIBILITY",sdf.parse(pcData[24].trim()));
			}
			if (pcData[25] != null && !pcData[25].trim().equals("")) {
				tovsp.setTimestamp("END_DATE_VISIBILITY",sdf.parse(pcData[25].trim()));
			}
			tovsp.setTimestamp("CREATE_DATE",new Date());
			tovsp.setString("IS_MAPPING","0");
			tovsp.saveIt();
		} catch (Throwable e) {
			returnResult = EAIConstant.DEAL_FAIL;
			logger.error("=============PC 插入TI_VEICOLI_DCS表处理异常============"+ e.getMessage(), e);
			throw new Exception("============PC 插入TI_VEICOLI_DCS表处理异常==================" + e);
		}
		returnResult = EAIConstant.DEAL_SUCCESS;
		return returnResult;
	}

	

//	public static void main(String[] args) throws IOException {
//		ContextUtil.loadConf();
//		File txtFile = new File("E:\\Interface\\pcTestData\\test\\U-EAI_PRODUCT_20150402_03.txt");
//		BufferedReader reader = null;
//		String tempString = null;
//		PCExecutorImpl pcImpl = new PCExecutorImpl();
//		try {
//			System.out.println("以行为单位读取文件内容，一次读一整行：");
//			reader = new BufferedReader(new FileReader(txtFile));
//			POContext.beginTxn(DBService.getInstance().getDefTxnManager(),
//					-1);
//			logger.info("====================事物创建===================");
//			Long batchId = Long.parseLong(SequenceManager.getSequence(null));
//			while ((tempString = reader.readLine()) != null) {
//				String[] rowData = tempString.replace("|", " @ ").split("@");
//				
//				pcImpl.storePcData(rowData,batchId);
//				
//				logger.info("===========解析每行传输数据： " + rowData[0]);
//			}
//			
//			POContext.endTxn(true);
//			logger.info("====================事物提交===================");
//			reader.close();
//			logger.info("===========txt文件解析完成================= ");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			logger.info("====事物结束====");
//			POContext.cleanTxn();
//		}
//		
//		Thread pcMappingThread = new PCMappingThread("PCMappingThread");
//		pcMappingThread.start();
//		
//	}
}
