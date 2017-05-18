package com.yonyou.dms.gacfca;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.PartStockDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 特约店配件存库库存信息上报（计划任务)
 * @author Administrator
 *
 */
@Service
public class SEDMS008CoudImpl implements SEDMS008Coud{

	final Logger logger = Logger.getLogger(SEDMS008CoudImpl.class);

	@Override
	public LinkedList<PartStockDTO> getSEDMS008(String dealerCode) {
		logger.info("==========SEDMS008Impl执行===========");
		LinkedList<PartStockDTO> result = new LinkedList<PartStockDTO>();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			Calendar calm = Calendar.getInstance();
			calm.setTime(date); 
			int uploadYear= calm.get(Calendar.YEAR);//当前年份
			int uploadMonth= (calm.get(Calendar.MONTH))+1;//当前月份

			Calendar calmLast = Calendar.getInstance();
			calmLast.set(Calendar.DAY_OF_MONTH, calmLast.getActualMaximum(Calendar.DAY_OF_MONTH));
			String strIsEndMonth = CommonConstants.DICT_IS_NO;
			if (!format.format(calmLast.getTime()).equals(format.format(calm.getTime()))) {//判断当前日期是否是本月最后一天
				if (calm.get(Calendar.DAY_OF_WEEK)!=5){//5是周四
					return null;
				}
			} else {
				strIsEndMonth = CommonConstants.DICT_IS_YES;
			}
			if(strIsEndMonth!=null && strIsEndMonth.equals(CommonConstants.DICT_IS_YES)){
				List<Map> partStockList = queryPartStock(dealerCode);
				//		    	每月月底执行此计划任务 并上报总部
				PartStockDTO partStockDTO =null;
				if (partStockList != null && !partStockList.isEmpty()) {
					for (Map<String,Object> bean : partStockList) {
						String partNo = bean.get("PART_NO").toString();
						String partName = bean.get("PART_NAME").toString();
						String storageCode = bean.get("STORAGE_CODE").toString();
						String storageName = bean.get("STORAGE_NAME").toString();						
						Float stockQuantity = Float.parseFloat(bean.get("STOCK_QUANTITY").toString());						
						Double salesPrice = Double.parseDouble(bean.get("INSTRUCT_PRICE").toString());
						Double limitPrice = Double.parseDouble(bean.get("LIMIT_PRICE").toString());
						Double claimPrice = Double.parseDouble(bean.get("CLAIM_PRICE").toString());
						String limitNo=bean.get("LIMIT_NO").toString();
						partStockDTO = new PartStockDTO();
						partStockDTO.setEntityCode(dealerCode);
						partStockDTO.setStorageCode(storageCode);
						partStockDTO.setStorageName(storageName);
						partStockDTO.setPartNo(partNo);
						partStockDTO.setPartName(partName);
						partStockDTO.setPaperQuantity(stockQuantity);
						partStockDTO.setSalesPrice(salesPrice);//含税MSRP
						partStockDTO.setDnp(claimPrice);//含税DNP
						if(Utility.testString(limitPrice)){
							partStockDTO.setTaxCostPrice(limitPrice*1.17);	//含税成本价
						}else{
							partStockDTO.setTaxCostPrice(0D);
						}
						partStockDTO.setUploadYear(uploadYear);
						partStockDTO.setUploadMonth(uploadMonth);
						if(limitNo!=null){
							partStockDTO.setLimitNo(limitNo);
						}
						result.add(partStockDTO);
					}
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			return null;
		}finally{
			logger.info("==========SEDMS008Impl结束===========");
		}
	}

	/**
	 * @description 查询配件库存
	 * @param dealerCode
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryPartStock(String dealerCode) throws Exception {
		String sql= " select distinct a.DEALER_CODE,a.PART_NO,a.PART_NAME,a.STOCK_QUANTITY,c.UNIT_CODE,c.UNIT_NAME," +
				" a.SALES_PRICE,a.COST_PRICE as LIMIT_PRICE,a.STORAGE_POSITION_CODE,a.SLOW_MOVING_DATE ," +
				" d.CLAIM_PRICE,a.COST_AMOUNT,a.BORROW_QUANTITY,a.LEND_QUANTITY,a.LOCKED_QUANTITY,b.STORAGE_CODE,b.STORAGE_NAME,d.LIMIT_NO,d.INSTRUCT_PRICE" +
				"  from tm_part_stock a" +
				" inner join tm_storage b on a.dealer_code=b.dealer_code and a.storage_code=b.storage_code" +
				" left join tm_unit c on a.dealer_code=c.dealer_code and  a.UNIT_CODE=c.UNIT_CODE " +
				" left join tm_part_info d on a.dealer_code=d.dealer_code and  a.PART_NO=d.PART_NO " +
				" where a.dealer_code = '"+dealerCode+"' and a.STOCK_QUANTITY>0 and " +
				"  a.COST_PRICE>0  and  a.PART_STATUS="+CommonConstants.DICT_IS_NO+" ";

		logger.debug(sql);
		return DAOUtil.findAll(sql, null);
	}
}
