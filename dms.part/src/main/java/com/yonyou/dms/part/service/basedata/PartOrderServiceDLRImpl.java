package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtPtDeliverDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtDeliverDetailDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtOrderDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtOrderDetailDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtPartBaseDcsPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.dao.PartOrderDLRDao;
import com.yonyou.dms.part.domains.DTO.basedata.OrderRegisterDTO;
import com.yonyou.dms.part.domains.DTO.partOrder.TtPtOrderDetailDcsDTO;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TmpPtOrderDetailDcsPO;

/**
 * 配件订货计划
 * @author ZhaoZ
 *@date 2017年3月22日
 */
@Service
public class PartOrderServiceDLRImpl implements PartOrderDLRService{

	@Autowired
	private  PartOrderDLRDao partDao;

	/**
	 * 配件订货计划查询信
	 */
	@Override
	public PageInfoDto queryOrderPlanInfo(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.getOrderPlanInfo(queryParams);
	}

	/**
	 * 删除
	 */
	@Override
	public void delete(BigDecimal id) throws ServiceBizException {
		boolean flag = false; 
		if(!StringUtils.isNullOrEmpty(id)){ 
			 TtPtOrderDcsPO ttPtOrderPO = TtPtOrderDcsPO.findById(id);
			 if(ttPtOrderPO!=null){ 
				 ttPtOrderPO.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_01);
				 flag = ttPtOrderPO.saveIt();
				 if(flag){			
				 }else{
					 throw new ServiceBizException("删除失败!");
				 }
			 }
		 }
		
	}

	/**
	 * 查询配件清单
	 */
	@Override
	public PageInfoDto queryOrderList(Map<String, String> queryParams,String partCodes) throws ServiceBizException {
		return partDao.getOrderList(queryParams,partCodes);
	}

	/**
	 * 配件订单补登记查询
	 */
	@Override
	public PageInfoDto queryInvoiceList(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.getInvoice(queryParams);
	}

	/**
	 * 配件订单补登记 
	 */
	@Override
	public void register(OrderRegisterDTO dto) throws ServiceBizException {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	long time= System.currentTimeMillis();
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String orderType = dto.getOpAuart();
		String sapOrderNo = dto.getOpVbeln();//SAP订单编号
		Map<String, Object> dealerInfo = partDao.getDealerInfoByDealerID(logonUser.getDealerId());//经销基本信息 dealer_id 小区id 大区id
		TtPtOrderDcsPO ttPtOrderPO = new TtPtOrderDcsPO();
		ttPtOrderPO.setLong("BIG_ORG_ID",new Long(dealerInfo.get("BIG_ORG_ID").toString()));//大区
		ttPtOrderPO.setLong("ORG_ID",new Long(dealerInfo.get("SMALL_ORG_ID").toString()));//小区
		Long dealerId = new Long(dealerInfo.get("DEALER_ID").toString());
		ttPtOrderPO.setLong("DEALER_ID",dealerId);//经销id
		ttPtOrderPO.setInteger("ORDER_TYPE",new Integer(orderType));
		ttPtOrderPO.setInteger("ORDER_STATUS",OemDictCodeConstants.PART_ORDER_STATUS_04);
		String orderNo = partDao.getOrderNoByRe(logonUser.getDealerCode());//订单号 
		ttPtOrderPO.setString("ORDER_NO", orderNo);
	
		ttPtOrderPO.setLong("CREATE_BY",logonUser.getUserId());//新增人
		try {
    		Date date = sdf.parse(sdf.format(new Date(time)));
    		java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
    		ttPtOrderPO.setTimestamp("CREATE_DATE",st);//新增时间
    	} catch (ParseException e) {
    		e.printStackTrace();
    	} 	
		ttPtOrderPO.setInteger("IS_DCS_SEND",OemDictCodeConstants.IF_TYPE_YES);
		ttPtOrderPO.setString("SAP_ORDER_NO",sapOrderNo);
		boolean flag = false;
		double orderBalance=0;
		List<TtPtOrderDetailDcsPO> listDetailpo = new ArrayList<TtPtOrderDetailDcsPO>();
		String[] partCode = null;
		String[] orderNum =  null;
		String[] noTaxAmount = null;
		String[] discount = null;
		String[] opDelCharge = null;
		for(Map map:dto.getDms_table()){
			int i = 0;
			partCode = new String[dto.getDms_table().size()];
			orderNum = new String[dto.getDms_table().size()];
			noTaxAmount = new String[dto.getDms_table().size()];
			discount = new String[dto.getDms_table().size()];
			opDelCharge = new String[dto.getDms_table().size()];
			partCode[i] = (String) map.get("opMabnr");
			orderNum[i] = (String) map.get("opFklmg");
			noTaxAmount[i] = (String) map.get("opRetailprice");
			discount[i] = (String) map.get("opDiscount");
			opDelCharge[i] = (String) map.get("opDelCharge");
			i++;
		}
		if (null != partCode && partCode.length > 0) {
			for (int i=0;i<partCode.length;i++) {
				
			
				TtPtOrderDetailDcsPO po = new TtPtOrderDetailDcsPO();
				po.setLong("ORDER_ID",ttPtOrderPO.getLong("ORDER_ID"));
				LazyList<TtPtPartBaseDcsPO> listBasePO = TtPtPartBaseDcsPO.find("PART_CODE = ?", partCode[i]);
				
				if (null != listBasePO && listBasePO.size() > 0) {
					TtPtPartBaseDcsPO sPO = listBasePO.get(0);
					po.setLong("PART_ID", sPO.getId());
					po.setString("PART_CODE",sPO.getString("PART_CODE"));
					po.setString("PART_NAME",sPO.getString("PART_NAME"));
					po.setInteger("PART_MDOEL",sPO.getInteger("PART_MDOEL"));
					po.setInteger("REPORT_TYPE",sPO.getInteger("REPORT_TYPE"));
					po.setInteger("IS_MJV",sPO.getInteger("IS_MJV"));
					po.setInteger("IS_MOP",sPO.getInteger("IS_MOP"));
				}
				if (!StringUtils.isNullOrEmpty(orderNum[i])) {
					if (orderNum[i].equals("0")) {
						po.setDouble("ORDER_NUM",0L);
					} else {
						po.setDouble("ORDER_NUM",new BigDecimal(orderNum[i]).longValue());
					}
				}
				if (!StringUtils.isNullOrEmpty(noTaxAmount[i])) {
					po.setDouble("NO_TAX_AMOUNT",new BigDecimal(noTaxAmount[i]).doubleValue());
					po.setDouble("NO_TAX_PRICE",po.getDouble("NO_TAX_AMOUNT")/po.getDouble("ORDER_NUM"));
					orderBalance+=po.getDouble("NO_TAX_AMOUNT");
				}
				if (!StringUtils.isNullOrEmpty(discount[i])) {
					po.setDouble("DISCOUNT",new BigDecimal(discount[i]).doubleValue());
				}
				po.setLong("CREATE_BY",logonUser.getUserId());
				try {
		    		Date date = sdf.parse(sdf.format(new Date(time)));
		    		java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
		    		po.setTimestamp("CREATE_DATE",st);//新增时间
		    	} catch (ParseException e) {
		    		e.printStackTrace();
		    	} 	
				listDetailpo.add(po);
				flag = po.insert();
				 if(flag){			
				 }else{
					 throw new ServiceBizException("登记失败!");
				 }
			}
		}
		ttPtOrderPO.setDouble("ORDER_BALANCE", orderBalance);
		flag = ttPtOrderPO.insert();
		 if(flag){			
		 }else{
			 throw new ServiceBizException("登记失败!");
		 }
		 if (OemDictCodeConstants.PART_ORDER_TYPE_05.toString().equals(ttPtOrderPO.getInteger("ORDER_TYPE").toString())) {//T.O.
			 TtPtDeliverDcsPO ttPtDeliverPO =  new TtPtDeliverDcsPO();
			 ttPtDeliverPO.setLong("DEALER_ID",ttPtOrderPO.getLong("DEALER_ID") );
			 ttPtDeliverPO.setString("DELIVER_NO",partDao.getDeliverNo());//交货单号
			 ttPtDeliverPO.setString("DMS_ORDER_NO",ttPtOrderPO.getString("ORDER_NO"));//DMS订单编号
			 ttPtDeliverPO.setString("SAP_ORDER_NO",ttPtOrderPO.getString("SAP_ORDER_NO"));//SAP订单编号
			 DecimalFormat  df   = new DecimalFormat("######0.00");//double数据转换  只保留两位
			 Double amount = ttPtOrderPO.getDouble("ORDER_BALANCE")*OemDictCodeConstants.PARTBASEPRICE_TAXRATE;
			 ttPtDeliverPO.setDouble("AMOUNT",Utility.getDouble(df.format(amount))); //总价
			 ttPtDeliverPO.setDouble("NET_PRICE",ttPtOrderPO.getDouble("ORDER_BALANCE"));//净价
			 Double taxAmount = ttPtOrderPO.getDouble("ORDER_BALANCE")*(OemDictCodeConstants.PARTBASEPRICE_TAXRATE-0.1);
			 ttPtDeliverPO.setDouble("TAX_AMOUNT",Utility.getDouble(df.format(taxAmount)));//税额
			 ttPtDeliverPO.setDouble("TRANS_AMOUNT",0d);//运费   第三方是0
			 ttPtDeliverPO.setInteger("IS_DCS_SEND",OemDictCodeConstants.IF_TYPE_NO);//未下发
			 try {
		    		Date date = sdf.parse(sdf.format(new Date(time)));
		    		java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
		    		ttPtDeliverPO.setTimestamp("TRANS_DATE",st);//运单日期 
		    		ttPtDeliverPO.setTimestamp("ARRIVED_DATE",st);//预计到店日期 
		    		ttPtDeliverPO.setTimestamp("DELIVER_DATE",st);//交货单创建时间
		    	} catch (ParseException e) {
		    		e.printStackTrace();
		    	} 	
			
			LazyList<TtPtDeliverDcsPO> listTtPtDeliverPO = TtPtDeliverDcsPO.find("SAP_ORDER_NO = ?", ttPtOrderPO.getString("SAP_ORDER_NO"));
			 
			 Long deliverId = null;
			 //是否存在
			 if (null != listTtPtDeliverPO && listTtPtDeliverPO.size() > 0) {//修改
				deliverId = listTtPtDeliverPO.get(0).getLong("DELIVER_ID");
				
				ttPtDeliverPO.setLong("UPDATE_BY",99999999L);//修改人
				 try {
			    		Date date = sdf.parse(sdf.format(new Date(time)));
			    		java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
			    		ttPtDeliverPO.setTimestamp("UPDATE_DATE",st);
			    	} catch (ParseException e) {
			    		e.printStackTrace();
			    	} 	
				flag = ttPtDeliverPO.saveIt();
				 if(flag){			
				 }else{
					 throw new ServiceBizException("登记失败!");
				 }
			} else {//新增
				 try {
			    		Date date = sdf.parse(sdf.format(new Date(time)));
			    		java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
			    		ttPtDeliverPO.setTimestamp("UPDATE_DATE",st);
			    		ttPtDeliverPO.setDate("CREATE_DATE",st);//创建人
			    	} catch (ParseException e) {
			    		e.printStackTrace();
			    	} 	
				ttPtDeliverPO.setLong("CREATE_BY",99999999L);//创建人
				ttPtDeliverPO.setLong("UPDATE_BY",99999999L);//修改人
				ttPtDeliverPO.setInteger("DELIVER_STATUS", OemDictCodeConstants.PART_DELIVER_STATUS_01);
				flag = ttPtDeliverPO.insert();
				 if(flag){			
				 }else{
					 throw new ServiceBizException("登记失败!");
				 }
			}
			 TtPtDeliverDetailDcsPO deletePtDeliverDetailPO = TtPtDeliverDetailDcsPO.findFirst("DELIVER_ID = ?", deliverId);
			 deletePtDeliverDetailPO.deleteCascadeShallow();
			//明细
				if (null != listDetailpo && listDetailpo.size() > 0) {
					//新增明细
					for (int j=0;j<listDetailpo.size();j++) {
						TtPtOrderDetailDcsPO orderDetailPo = listDetailpo.get(j);
						String pCode = orderDetailPo.getString("PART_CODE");
						TtPtDeliverDetailDcsPO dPo = new TtPtDeliverDetailDcsPO();
						dPo.setLong("DELIVER_ID",deliverId);
						dPo.setBigDecimal("PART_ID",orderDetailPo.getBigDecimal("PART_ID"));//保存配件主键
						dPo.setString("PART_NAME",orderDetailPo.getString("PART_NAME"));//配件名称
						dPo.setString("PART_CODE",pCode);
						dPo.setLong("INIT_PLAN_NUM",orderDetailPo.getLong("ORDER_NUM"));//原计划量
						dPo.setLong("PLAN_NUM",orderDetailPo.getLong("ORDER_NUM"));//计划量
						dPo.setDouble("DISCOUNT",orderDetailPo.getDouble("DISCOUNT"));//单个折扣
						dPo.setLong("CREATE_BY",99999999L);//创建人
						 try {
					    		Date date = sdf.parse(sdf.format(new Date(time)));
					    		java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
					    		dPo.setDate("CREATE_DATE",st);//创建人
					    	} catch (ParseException e) {
					    		e.printStackTrace();
					    	} 	
						dPo.setString("ORDER_NO",ttPtOrderPO.getString("SAP_ORDER_NO"));
						dPo.setDouble("NET_PRICE",orderDetailPo.getDouble("NO_TAX_PRICE"));//净价
						dPo.setDouble("INSTOR_PRICE",orderDetailPo.getDouble("NO_TAX_PRICE"));//入库单价
						dPo.setDouble("DELIVER_AMOUNT",orderDetailPo.getDouble("NO_TAX_AMOUNT"));//运单总额
						flag = dPo.insert();
						
						 if(flag){			
						 }else{
							 throw new ServiceBizException("登记失败!");
						 }
					}
					
				}
		 }
		 
	}

	//导入临时表
	@Override
	public void insertTmpPtOrderDetailDcs(TtPtOrderDetailDcsDTO rowDto) throws ServiceBizException {
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	long time= System.currentTimeMillis();
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TmpPtOrderDetailDcsPO po = new TmpPtOrderDetailDcsPO();
		po.setString("LINE_NO",rowDto.getRowNO());
		po.setString("PART_CODE",rowDto.getPartCode());
		po.setString("PART_NAME",rowDto.getPartName());
		po.setBigDecimal("ORDER_NUM",new BigDecimal(rowDto.getOrderNum()).longValue());
		po.setLong("CREATE_BY", logonUser.getUserId());
		try {
    		Date date = sdf.parse(sdf.format(new Date(time)));
    		java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
    		po.setDate("CREATE_DATE",st);//创建人
    	} catch (ParseException e) {
    		e.printStackTrace();
    	} 	
		po.insert();
	}

	//数据校验
	@SuppressWarnings("unchecked")
	@Override
	public List<TtPtOrderDetailDcsDTO> checkData(LazyList<TmpPtOrderDetailDcsPO> poList, String partModel,String partCodes) throws ServiceBizException {
		ArrayList<TtPtOrderDetailDcsDTO> trvdDTOList = new ArrayList<TtPtOrderDetailDcsDTO>();
		ImportResultDto<TtPtOrderDetailDcsDTO> importResult = new ImportResultDto<TtPtOrderDetailDcsDTO>();
		
		
		//数据正确性校验
		List<Map> trvdList = partDao.checkData2(poList,partModel,partCodes);		
		if(trvdList.size()>0){
			for(Map<String, Object> p:trvdList){
				TtPtOrderDetailDcsDTO rowDto = new TtPtOrderDetailDcsDTO();
				 rowDto.setRowNO(Integer.valueOf(p.get("ROW_NUM").toString()));
				 rowDto.setErrorMsg(p.get("ERROR").toString());
				 trvdDTOList.add(rowDto);
				 
			}
			importResult.setErrorList(trvdDTOList);
		}

		return trvdDTOList;
	}

	
}
