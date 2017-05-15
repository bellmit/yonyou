package com.yonyou.dms.retail.service.basedata;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.dao.basedata.RetailDiscountDao;
import com.yonyou.dms.retail.domains.DTO.basedata.TmRetalDiscountImportTempDTO;
import com.yonyou.dms.retail.domains.PO.basedata.TmRetalDiscountImportTempPO;

@SuppressWarnings("rawtypes")
@Service
public class RetailUimportServiceImpl implements RetailUimportService{
	@Autowired
	RetailDiscountDao dao;

	@Override
	public void insertTmpVsYearlyPlan(TmRetalDiscountImportTempDTO rowDto) {
		dao.insertTmpVsYearlyPlan(rowDto);
	}

	/**
	 * 检查数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ImportResultDto<TmRetalDiscountImportTempDTO> checkData() {
		ImportResultDto<TmRetalDiscountImportTempDTO> importResult = new ImportResultDto<TmRetalDiscountImportTempDTO>();
		ArrayList<TmRetalDiscountImportTempDTO> tvypDTOList = new ArrayList<TmRetalDiscountImportTempDTO>();
		//检查数据是否合法
		List<TmRetalDiscountImportTempPO> list = TmRetalDiscountImportTempPO.findAll();
		tvypDTOList = check(list);
		//检查数据是否正确
		if(tvypDTOList.size()==0){
			List<Map> li = checkData3();
			for(Map<String,Object> m:li){
				TmRetalDiscountImportTempDTO err = new TmRetalDiscountImportTempDTO();
				err.setRowNO(Integer.valueOf(m.get("ROW_NO").toString()));
				String er = "";
				if(m.get("VIN").toString().equals("")){
					er+="车架号："+m.get("VIN")+" 不存在。";
				}else {
					er+="车架号："+m.get("VIN")+" 不存在。";
				}
				err.setErrorMsg(er);
				tvypDTOList.add(err);
			}
		}
		importResult.setErrorList(tvypDTOList);
		return importResult;
	}
	
	private ArrayList<TmRetalDiscountImportTempDTO> check(List<TmRetalDiscountImportTempPO> list) {
		ArrayList<TmRetalDiscountImportTempDTO> err = new ArrayList<TmRetalDiscountImportTempDTO>();
		for(TmRetalDiscountImportTempPO po:list){
		if(!StringUtils.isNullOrEmpty(po.getString("MSRP"))){
			if(!isNumber(po.getString("MSRP"))){
				TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
				rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
				rowDto.setErrorMsg("MSRP: 不是合法数字。");
				err.add(rowDto);
			}
		}
		if(!StringUtils.isNullOrEmpty(po.getString("NET_PRICE"))){
			if(!isNumber(po.getString("NET_PRICE"))){
				TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();				
				rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
				rowDto.setErrorMsg("成交价：不是合法数字。");
				err.add(rowDto);
			}
		}else if(!StringUtils.isNullOrEmpty(po.getString("DEAL_AMOUNT"))){
			if(!isNumber(po.getString("DEAL_AMOUNT"))){
				TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();				
				rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
				rowDto.setErrorMsg("贷款金额：不是合法数字。");
				err.add(rowDto);
				
			}
			if(isNumber(po.getString("DEAL_AMOUNT"))&&isNumber(po.getString("NET_PRICE"))){
				if(Double.valueOf(po.getString("DEAL_AMOUNT"))>=Double.valueOf(po.getString("NET_PRICE"))){
					TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
					rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
					rowDto.setErrorMsg("贷款金额【"+po.getString("DEAL_AMOUNT")+"】必须小于成交价【"+po.getString("NET_PRICE")+"】。");
					err.add(rowDto);
				}
			}
		}
		if(!StringUtils.isNullOrEmpty(po.getString("APPLY_DATE"))){
			if(!isDate(po.getString("APPLY_DATE"))){
				TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
				rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
				rowDto.setErrorMsg("申请时间格式错误。");
				err.add(rowDto);
			}
		}
		if(!StringUtils.isNullOrEmpty(po.getString("FIRST_PERMENT_RATIO"))){
			String numStr = isPercent(po.getString("FIRST_PERMENT_RATIO").trim());
			if(po.getString("FIRST_PERMENT_RATIO").indexOf(".")>0){//如果是小数			
				if (numStr.split("\\.")[1].length() > 2 || numStr.split("\\.")[1].length()< 1){
					TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
					rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
					rowDto.setErrorMsg("首付比例：  数据不是一位小数或两位小数格式。");
					err.add(rowDto);
				}else{
					if(!isNumber(numStr)){
						TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
						rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
						rowDto.setErrorMsg("首付比例：  不是有效数字。");
						err.add(rowDto);
					}
				}			
			}else{//如果是整数
				if(!isNumber(numStr)){//如果不数字
					TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
					rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
					rowDto.setErrorMsg("首付比例：  数字无效。");
					err.add(rowDto);
				}else{

				}
			}
			
		}
		if(!StringUtils.isNullOrEmpty(po.getString("INTEREST_RATE"))){
			String numStr = isPercent(po.getString("INTEREST_RATE").trim());
			if(po.getString("INTEREST_RATE").indexOf(".")>0){//如果是小数			
				if (numStr.split("\\.")[1].length()>2 || numStr.split("\\.")[1].length()<1){
					TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
					rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
					rowDto.setErrorMsg("原利率：  数据不是一位小数或两位小数格式。");
					err.add(rowDto);
				}else{
					if(!isNumber(numStr)){
						TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
						rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
						rowDto.setErrorMsg("原利率：  不是有效数字。");
						err.add(rowDto);
					}
				}			
			}else{//如果是整数
				if(!isNumber(numStr)){//如果不数字
					TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
					rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
					rowDto.setErrorMsg("原利率：  不是数字。");
					err.add(rowDto);
				}else{
				}
			}
			
		}

		if(!StringUtils.isNullOrEmpty(po.getString("DEAL_DATE"))){
			if(!isDate(po.getString("DEAL_DATE"))){
				if(!CommonUtils.checkNull(po.getString("DEAL_DATE")).equals("")){
					TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
					rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
					rowDto.setErrorMsg("银行放款时间：格式不合法。");
					err.add(rowDto);
				}
			}
		}
		if(!StringUtils.isNullOrEmpty(po.getString("INSTALL_MENT_NUM"))){
			if(!isInteger(po.getString("INSTALL_MENT_NUM"))){
				TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
				rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
				rowDto.setErrorMsg("分期期数：不是合法数字。");
				err.add(rowDto);
			}
		}
		if(CommonUtils.checkNull(po.getString("MERCHAN_FEES_RATE")).equals("")){
		
		}else{
			String numStr = isPercent(po.getString("MERCHAN_FEES_RATE").trim());
			if(po.getString("MERCHAN_FEES_RATE").indexOf(".")>0){//如果是小数			
				if (numStr.split("\\.")[1].length()>2 || numStr.split("\\.")[1].length()< 1){
					TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
					rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
					rowDto.setErrorMsg("商户手续费率：  数据不是一位小数或两位小数格式。");
					err.add(rowDto);
				}else{
					if(!isNumber(numStr)){
						TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
						rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
						rowDto.setErrorMsg("商户手续费率：  数据不是有效数字。");
						err.add(rowDto);
//						}
					}
				}			
			}else{//如果是整数
				if(!isNumber(numStr)){//如果不数字
					TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
					rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
					rowDto.setErrorMsg("商户手续费率：  数据无效。");
					err.add(rowDto);
				}else{

				}
			}
		}
		

		if(CommonUtils.checkNull(po.getString("POLOCY_RATE")).equals("")){
			
		}else{
			String numStr = isPercent(po.getString("POLOCY_RATE").trim());
			if(po.getString("POLOCY_RATE").indexOf(".")>0){//如果是小数				
				if (numStr.split("\\.")[1].length()> 2 || numStr.split("\\.")[1].length()< 1){
					TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
					rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
					rowDto.setErrorMsg("政策费率： 数据不是一位小数或两位小数格式。");
					err.add(rowDto);
				}else{
					if(!isNumber(numStr)){

						TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
						rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
						rowDto.setErrorMsg("政策费率： 不是有效数字。");
						err.add(rowDto);
					}
				}			
			}else{
				if (!isNumber(numStr)) {//如果是整数，校验是否是是数字
					TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
					rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
					rowDto.setErrorMsg("政策费率： 不是数字。");
					err.add(rowDto);
				}else{

				}
			}
			
		}
		if(!StringUtils.isNullOrEmpty(po.getString("ALLOWANCED_SUM_TAX"))){
			if(!isNumber(po.getString("ALLOWANCED_SUM_TAX"))){
				TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
				rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
				rowDto.setErrorMsg("贴息金额（含税）： 不是合法数字。");
				err.add(rowDto);
			}
		}
		if(!StringUtils.isNullOrEmpty(po.getString("ALLOWANCED_SUM_TAX"))){
			if(!isNumber(po.getString("ALLOWANCED_SUM_TAX"))){
				TmRetalDiscountImportTempDTO rowDto=new TmRetalDiscountImportTempDTO();
				rowDto.setRowNO(Integer.parseInt(po.getLong("ROW_NO").toString()));
				rowDto.setErrorMsg("贴息金额（含税）： 不是数字。");
				err.add(rowDto);
			}
		}

		}
	
		return err;
	}

	@Override
	public boolean save() {
		return dao.save();
	}

	/**  * 判断字符串是否是数字  */
	public static boolean isNumber(String value) {
		if(!StringUtils.isNullOrEmpty(value)){
			return isInteger(value) || isDouble(value); 
		}
		return false;
	}
	
	/** 字符比例转换**/
	public static String isPercent(String value){
		if(!StringUtils.isNullOrEmpty(value)){
			Double dou = Double.valueOf(value);
			Double dou2 = 100.0;
			BigDecimal b1 = new BigDecimal(Double.toString(dou.doubleValue()));  
			BigDecimal b2 = new BigDecimal(Double.toString(dou2.doubleValue()));
			Double bou3 = b1.multiply(b2).doubleValue();
			String valu = String.valueOf(bou3);
			return valu;
		}
		return value;
	}

	/**  * 判断字符串是否是整数  */ 
	public static boolean isInteger(String value) {  
		try {  
			Integer.parseInt(value); 
			if(Integer.parseInt(value)>=0){
				return true;
			}else{
				return false;
			}
		} catch (NumberFormatException e) { 
			return false; 
		}
	} 
	/**  * 判断字符串是否是浮点数  */ 
	public static boolean isDouble(String value) { 
		try {  
			Double.parseDouble(value);  
			if (value.contains(".")){
				if(Double.parseDouble(value)>=0){
					return true;
				}else{
					return false;
				}
			}
			return false; 
		} catch (NumberFormatException e) { 
			return false;  
		} 
	} 
	/**  * 判断字符串是否是Long类型  */
	public static boolean isLong(String value) {
		if(!StringUtils.isNullOrEmpty(value)){
			try {
				Long.parseLong(value);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}
	
	/** * 是否是合法日期格式 */
	@SuppressWarnings("unused")
	public static boolean isDate(String value){
		boolean flag = false;
		if(!!StringUtils.isNullOrEmpty(value)){
			return flag;
		}
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat df3 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		SimpleDateFormat df4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date d = null;
		try {
			d = df1.parse(value);
			flag = true;
		} catch (ParseException e) {
			
		}
		try {
			if(!flag){
				d = df2.parse(value);
				flag = true;
			}
		} catch (ParseException e) {
			
		}
		try {
			if(!flag){
				d = df3.parse(value);
				flag = true;
			}
		} catch (ParseException e) {
			
		}
		try {
			if(!flag){
				d = df4.parse(value);
				flag = true;
			}
		} catch (ParseException e) {
			
		}
		return flag;
	}
	
	private List<Map> checkData3() {
		List<Object> params = new ArrayList<Object>();	
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from (");
		sql.append("  select trd.ROW_NO,trd.VIN,");
		sql.append(" (case when  ifnull(tv.VIN,'')!='' then 1 else 0 end) flag \n");
		sql.append("   from TM_RETAIL_DISCOUNT_IMPORT_TEMP trd \n");
		sql.append("  LEFT JOIN TM_VEHICLE_DEC tv on tv.VIN=trd.VIN ) temp\n");
		sql.append(" where flag=0");
		 List<Map> resultList = OemDAOUtil.findAll(sql.toString(), params);
		return resultList;		
	}


}
