
/** 
 *Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.part
 *
 * @File name : PartBuyServiceImpl.java
 *
* @Author : zhengcong
*
* @Date : 2017年4月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月7日   zhengcong    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.part.service.stockmanage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * PartBuyServiceImpl
 * 
 * @author xukl
 * @date 2016年7月26日
 */
@Service
@SuppressWarnings("rawtypes")
public class PartBuyServiceImpl implements PartBuyService {
	
	
	   /**
     * 根据No查询出采购入库单
 * @author zhengcong
 * @date 2017年4月5日
     */
    @Override
    public PageInfoDto partBuyFindByNo(String stockInNo)throws ServiceBizException {
    	List<Object> params = new ArrayList<Object>();
        StringBuffer sqlsb = new StringBuffer("SELECT A.REMARK, A.ORDER_REGEDIT_NO, A.CASE_ID, A.DEALER_CODE, A.STOCK_IN_NO, ");
        sqlsb.append(" A.DELVIERY_NO, A.BILLING_NO, A.CUSTOMER_CODE, A.CUSTOMER_NAME, A.ARRIVE_DATE,  ");
        sqlsb.append(" A.STOCK_IN_VOUCHER, A.SHEET_CREATE_DATE, A. HANDLER, TAX, A.TAX_AMOUNT,  ");
        sqlsb.append(" A.BEFORE_TAX_AMOUNT, A.TOTAL_AMOUNT, A.IS_FIAT, A.IS_FINISHED, A.FINISHED_DATE,  ");
        sqlsb.append(" A.IS_PAYOFF, A.IS_BACK, A.ORDER_STATUS, A.CREDENCE, A.LOCK_USER,  ");
        sqlsb.append(" B.PRICE_ADD_RATE, A.DELIVERY_TIME, A.STOCK_IN_TYPE, A.OEM_ORDER_NO FROM  ");
        sqlsb.append(" TT_PART_BUY A LEFT JOIN ("+ CommonConstants.VM_PART_CUSTOMER +") b ON ( a.CUSTOMER_CODE =  ");
        sqlsb.append(" b.CUSTOMER_CODE AND a.DEALER_CODE = b.DEALER_CODE )   ");
        sqlsb.append("  WHERE a.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "' "); 
        sqlsb.append("  AND a.IS_FINISHED =" + DictCodeConstants.DICT_IS_NO);
        sqlsb.append("  AND a.D_KEY ="+ DictCodeConstants.D_KEY);
        if(!StringUtils.isNullOrEmpty(stockInNo)){
          sqlsb.append(" AND A.STOCK_IN_NO LIKE '%" + stockInNo + "%' ");
        }

                  PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(),params);
                  return pageInfoDto;

    }     
    
    
/**
  * 根据查询出的入库单，选择后将信息带到主界面后，根据NO查询出详细信息
* @author zhengcong
* @date 2017年4月10日
  */
 @Override
 public PageInfoDto partBuyItemFindByNo(String stockInNo)throws ServiceBizException {
 	List<Object> params = new ArrayList<Object>();
     StringBuffer sqlsb = new StringBuffer("SELECT '12781002' AS IS_SELECTED, B.STOCK_QUANTITY, ");
     sqlsb.append(" (cast(a.IN_PRICE AS decimal(14,2))) AS IN_PRICE,(cast(a.IN_PRICE_TAXED AS decimal(14,2))) AS IN_PRICE_TAXED, ");
     sqlsb.append(" A.OLD_STOCK_IN_NO,  A.ITEM_ID,A.DEALER_CODE,A.STOCK_IN_NO,A.STORAGE_CODE,D.STORAGE_NAME,A.STORAGE_POSITION_CODE, ");
     sqlsb.append(" A.PART_BATCH_NO,A.PART_NO,A.PART_NAME,A.UNIT_CODE,A.UNIT_NAME,A.IN_QUANTITY,A.NEED_QUANTITY, ");
     sqlsb.append(" A.INBOUND_QUANTITY,A.IS_FIAT,A.IN_AMOUNT,A.IN_AMOUNT_TAXED,A.COST_PRICE,A.COST_AMOUNT,A.D_KEY, ");
     sqlsb.append(" A.CREATED_BY,A.CREATED_AT,A.UPDATED_BY,A.UPDATED_AT,A.VER,A.FROM_TYPE,A.RECEIVE_REMARK,A.OTHER_PART_COST_PRICE, ");
     sqlsb.append(" A.OTHER_PART_COST_AMOUNT,  B.MAX_STOCK,B.MIN_STOCK, ");
     sqlsb.append(" (B.STOCK_QUANTITY+B.BORROW_QUANTITY-B.LEND_QUANTITY-B.LOCKED_QUANTITY) AS USEABLE_QUANTITY, ");
     sqlsb.append(" C.DOWN_TAG,C.CLAIM_PRICE FROM TT_PART_BUY_ITEM A  ");
     sqlsb.append(" LEFT JOIN TM_PART_STOCK B  ");
     sqlsb.append(" ON A.PART_NO=B.PART_NO AND A.STORAGE_CODE=B.STORAGE_CODE AND A.DEALER_CODE=B.DEALER_CODE ");
     sqlsb.append("  LEFT JOIN TM_PART_INFO C ON A.DEALER_CODE = C.DEALER_CODE AND A.PART_NO = C.PART_NO  ");
     sqlsb.append("  LEFT JOIN TM_STORAGE D ON A.DEALER_CODE = D.DEALER_CODE AND A.STORAGE_CODE=D.STORAGE_CODE  ");
     sqlsb.append(" WHERE A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
     sqlsb.append(" AND A.D_KEY="+ DictCodeConstants.D_KEY  );
     sqlsb.append("  AND A.STOCK_IN_NO='" + stockInNo + "'  ");
     
     

               PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(),params);
               return pageInfoDto;

 }     

 
 /**
  * 点修改，查询出非退货单配件信息
* @author zhengcong
* @date 2017年4月10日
  */
 @Override
 public PageInfoDto findNotBackItem(String STORAGE_CODE, String PART_NO)throws ServiceBizException {
 	List<Object> params = new ArrayList<Object>();
     StringBuffer sqlsb = new StringBuffer("SELECT '' as POS_CODE,'' as POS_NAME,A.PART_MODEL_GROUP_CODE_SET, 0.0 as PART_QUANTITY,  ");
     sqlsb.append("  A.DEALER_CODE, A.PART_NO, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME,  ");
     sqlsb.append("  A.SPELL_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE,  ");
     sqlsb.append(" A.PART_GROUP_CODE,  B.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE AS COST_PRICE, ");
     sqlsb.append("  A.COST_AMOUNT, A.MAX_STOCK,B.DOWN_TAG,  A.BORROW_QUANTITY, A.LEND_QUANTITY,  ");
     sqlsb.append(" A.LOCKED_QUANTITY, A.LAST_STOCK_IN, B.INSTRUCT_PRICE, A.PART_STATUS,  ");
     sqlsb.append("  A.LAST_STOCK_OUT, A.REMARK, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, ");
     sqlsb.append("  A.UPDATED_AT, A.VER, A.MIN_STOCK,  B.OPTION_NO, B.PART_MODEL_GROUP_CODE_SET INFO_PART_MODEL_GROUP_CODE_SET, ");
     sqlsb.append("  A.NODE_PRICE, B.PLAN_PRICE, B.OEM_LIMIT_PRICE, B.URGENT_PRICE,  ");
     sqlsb.append("  B.INSTRUCT_PRICE INFO_INSTRUCT_PRICE,A.INSURANCE_PRICE, D.OPTION_STOCK,  ");
     sqlsb.append(" (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK, ");
     sqlsb.append(" B.IS_BACK,B.PART_INFIX,  B.MIN_LIMIT_PRICE  ");
     sqlsb.append("  FROM TM_PART_STOCK A   ");
     sqlsb.append(" LEFT OUTER JOIN ("+ CommonConstants.VM_PART_INFO +") B  ");
     sqlsb.append(" ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO )  ");
     sqlsb.append("  LEFT OUTER JOIN  ");
     sqlsb.append(" (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK  ");
     sqlsb.append(" FROM TM_PART_STOCK C GROUP BY DEALER_CODE,PART_NO ) D  ");
     sqlsb.append("  ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.OPTION_NO = D.PART_NO ) ");
     sqlsb.append("  WHERE A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "'   ");
     sqlsb.append(" AND A.D_KEY ="+ DictCodeConstants.D_KEY);
     sqlsb.append(" and A.PART_NO =  '"+ PART_NO +"'   ");
     sqlsb.append(" and A.STORAGE_CODE = '"+ STORAGE_CODE +"'    ");


               PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(),params);
               return pageInfoDto;

 }     
 

 /**
  * 点修改，查询出退货单配件信息
* @author zhengcong
* @date 2017年4月10日
  */
 @Override
 public PageInfoDto findBackItem(String PART_NO, String OLD_STOCK_IN_NO)throws ServiceBizException {
 	List<Object> params = new ArrayList<Object>();
     StringBuffer sqlsb = new StringBuffer("select 12781002 as IS_SELECTED,   ");
     sqlsb.append(" b.DEALER_CODE,b.PART_NO,b.PART_NAME,b.IN_QUANTITY,b.IN_PRICE,b.IN_AMOUNT,b.IN_PRICE_TAXED,b.IN_AMOUNT_TAXED,b.COST_PRICE,b.COST_AMOUNT,  ");
     sqlsb.append(" b.STORAGE_CODE,b.STORAGE_POSITION_CODE,b.STOCK_IN_NO,a.DELVIERY_NO,a.CUSTOMER_NAME,a.SHEET_CREATE_DATE,a.FINISHED_DATE,  ");
     sqlsb.append(" greatest(least((SELECT  COALESCE(STOCK_QUANTITY,0) + COALESCE(BORROW_QUANTITY,0) - COALESCE(LEND_QUANTITY,0) + COALESCE(LOCKED_QUANTITY,0)  ");
     sqlsb.append(" FROM TM_PART_STOCK C  ");
     sqlsb.append(" WHERE C.DEALER_CODE = B.DEALER_CODE  AND C.PART_NO = B.PART_NO AND C.STORAGE_CODE = B.STORAGE_CODE) , ");
     sqlsb.append(" B.IN_QUANTITY + (SELECT COALESCE(SUM(IN_QUANTITY),0)  ");
     sqlsb.append(" FROM TT_PART_BUY_ITEM D LEFT JOIN TT_PART_BUY E  ");
     sqlsb.append(" ON D.DEALER_CODE = E.DEALER_CODE AND D.STOCK_IN_NO = E.STOCK_IN_NO ");
     sqlsb.append(" WHERE E.DEALER_CODE = A.DEALER_CODE  ");
     sqlsb.append(" AND E.IS_BACK ="+ DictCodeConstants.DICT_IS_YES );
     sqlsb.append(" AND D.PART_NO = B.PART_NO AND D.STORAGE_CODE = B.STORAGE_CODE  ");
     sqlsb.append(" AND D.STOCK_IN_NO <> '' AND D.OLD_STOCK_IN_NO = B.STOCK_IN_NO)),0) AS MAX_QUANTITY, ");
     sqlsb.append(" greatest(least(least((SELECT  COALESCE(STOCK_QUANTITY,0) + COALESCE(BORROW_QUANTITY,0) - COALESCE(LEND_QUANTITY,0)  + COALESCE(LOCKED_QUANTITY,0)  ");
     sqlsb.append(" FROM TM_PART_STOCK C WHERE C.DEALER_CODE = B.DEALER_CODE  ");
     sqlsb.append(" AND C.PART_NO = B.PART_NO AND C.STORAGE_CODE = B.STORAGE_CODE), ");
     sqlsb.append(" B.IN_QUANTITY + (SELECT COALESCE(SUM(IN_QUANTITY),0)  ");
     sqlsb.append(" FROM TT_PART_BUY_ITEM D LEFT JOIN TT_PART_BUY E  ");
     sqlsb.append(" ON D.DEALER_CODE = E.DEALER_CODE AND D.STOCK_IN_NO = E.STOCK_IN_NO ");
     sqlsb.append(" WHERE E.DEALER_CODE = A.DEALER_CODE ");
     sqlsb.append(" AND E.IS_BACK ="+ DictCodeConstants.DICT_IS_YES );
     sqlsb.append(" AND D.PART_NO = B.PART_NO AND D.STORAGE_CODE = B.STORAGE_CODE ");
     sqlsb.append(" AND D.STOCK_IN_NO <> '' AND D.OLD_STOCK_IN_NO = B.STOCK_IN_NO)),B.IN_QUANTITY),0) AS OUT_QUANTITY  ");
     sqlsb.append(" from tt_part_buy a left join tt_part_buy_item b  ");
     sqlsb.append(" on a.DEALER_CODE=b.DEALER_CODE and a.stock_in_no=b.stock_in_no  ");
     sqlsb.append(" where a.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "'  ");
     sqlsb.append(" and a.d_key="+ DictCodeConstants.D_KEY);
     sqlsb.append(" and b.IN_QUANTITY>=0   ");
     sqlsb.append(" AND A.STOCK_IN_NO LIKE '%"+ OLD_STOCK_IN_NO +"%'  ");
     sqlsb.append(" AND B.PART_NO LIKE '%"+ PART_NO +"%' ");

               PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(),params);
               return pageInfoDto;

 }     
 
//	@Autowired
//	private OperateLogService operateLogService;
//
//	/**
//	 * 通过id查询
//	 * 
//	 * @author xukl
//	 * @date 2016年8月7日
//	 * @param id
//	 * @return
//	 * @throws ServiceBizException (non-Javadoc)
//	 * @see com.yonyou.dms.part.service.stockmanage.PartBuyService#getPartBuyById(java.lang.Long)
//	 */
//
//	@Override
//	public PartBuyPO getPartBuyById(Long id) throws ServiceBizException {
//		return PartBuyPO.findById(id);
//	}
//
//
//	/**
//	 * 
//	 * 查询明细
//	 *  @author xukl
//	 * @date 2016年8月8日
//	 * @param masterid
//	 * @return
//	 * @throws ServiceBizException
//	 * (non-Javadoc)
//	 * @see com.yonyou.dms.part.service.stockmanage.PartBuyService#getPartBuyItemsById(java.lang.Long)
//	 */
//
//	@Override
//
//	public List<Map> getPartBuyItemsById(Long masterid) throws ServiceBizException {
//		StringBuilder sb = new StringBuilder("SELECT\n")
//				.append("tpbi.IS_FINISHED,\n")
//				.append("tpbi.PART_NO as PART_CODE,\n")
//				.append("tpbi.PART_NAME,\n")
//				.append("tpbi.STORAGE_CODE,\n")
//				.append("tpbi.STORAGE_POSITION_CODE,\n")
//				.append("tpbi.UNIT,\n")
//				.append("tpbi.IN_QUANTITY as INQUANTITY,\n")
//				.append("tpbi.IN_PRICE as PRICE,\n")
//				.append("tpbi.IN_AMOUNT as AMOUNT,\n")
//				.append("tpbi.IN_AMOUNT_TAXED as TAXEDAMOUNT,\n")
//				.append("tpbi.IN_PRICE_TAXED as PRICETAXED,\n")
//				.append("tpbi.FROM_TYPE as FROMTYPE,\n")
//				.append("tpbi.DEALER_CODE,\n")
//				.append("tpbi.PART_BUY_ID,\n")
//				.append("tpbi.ITEM_ID\n")
//				.append("FROM\n")
//				.append("  tt_part_buy_item tpbi\n")
//				.append("WHERE\n")
//				.append("  tpbi.PART_BUY_ID =?\n");
//		List<Object> param = new ArrayList<Object>();
//		param.add(masterid);
//		List<Map> list = DAOUtil.findAll(sb.toString(), param);
//		return list;
//	}
//
//
//	/**
//	 * 采购入库单打印查询
//	 * @author DuPengXin
//	 * @date 2016年12月7日
//	 * @param id
//	 * @return
//	 * @throws ServiceBizException
//	 * (non-Javadoc)
//	 * @see com.yonyou.dms.part.service.stockmanage.PartBuyService#getPartBuyPrintById(java.lang.Long)
//	 */
//
//	@Override
//	public List<Map> getPartBuyPrintById(Long id) throws ServiceBizException {
//		StringBuilder sb = new StringBuilder("select tpb.PART_BUY_ID,tpbi.DEALER_CODE,tpb.STOCK_IN_NO,tpb.ORDER_DATE,ts.STORAGE_CODE,ts.STORAGE_NAME,tpbi.STORAGE_POSITION_CODE,tpbi.PART_NO,tpbi.PART_NAME,tpbi.UNIT,tpbi.IN_QUANTITY,tpbi.IN_PRICE,tpbi.IN_PRICE_TAXED,tpbi.IN_AMOUNT,tpbi.IN_AMOUNT_TAXED,tpc.CUSTOMER_NAME from  tt_part_buy tpb LEFT JOIN tt_part_buy_item tpbi ON tpbi.PART_BUY_ID=tpb.PART_BUY_ID and tpbi.DEALER_CODE=tpb.DEALER_CODE LEFT JOIN tm_store ts ON tpbi.STORAGE_CODE=ts.STORAGE_CODE and tpbi.DEALER_CODE=ts.DEALER_CODE LEFT JOIN tm_part_customer tpc ON tpb.CUSTOMER_CODE=tpc.CUSTOMER_CODE and tpb.DEALER_CODE=tpc.DEALER_CODE  where 1=1 and  tpb.PART_BUY_ID=?");
//		List<Object> param = new ArrayList<Object>();
//		param.add(id);
//		List<Map> list = DAOUtil.findAll(sb.toString(), param);
//		return list;
//	}
//	/**
//	 * 查询
//	 * 
//	 * @author xukl
//	 * @date 2016年8月3日
//	 * @param queryParam
//	 * @return (non-Javadoc)
//	 * @see com.yonyou.dms.part.service.stockmanage.PartBuyService#queryPurchaseOrders(java.util.Map)
//	 */
//
//	@Override
//	public PageInfoDto queryPurchaseOrders(Map<String, String> queryParam) throws ServiceBizException {
//		List<Object> params = new ArrayList<Object>();
//		StringBuilder sqlSb = new StringBuilder("SELECT tpb.PART_BUY_ID,tpb.DEALER_CODE,tpb.STOCK_IN_NO,tpb.ORDER_STATUS,tpb.DELIVERY_NO,tpb.ORDER_DATE,tpb.CUSTOMER_CODE,tpc.CUSTOMER_NAME,tpb.BEFORE_TAX_AMOUNT FROM  tt_part_buy tpb LEFT JOIN tm_part_customer tpc ON tpb.CUSTOMER_CODE = tpc.CUSTOMER_CODE and tpc.DEALER_CODE = tpb.DEALER_CODE where 1=1 ");
//		if (!StringUtils.isNullOrEmpty(queryParam.get("STOCK_IN_NO"))) {
//			sqlSb.append(" and tpb.STOCK_IN_NO like ?");
//			params.add("%" + queryParam.get("STOCK_IN_NO") + "%");
//		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("DELVIERY_NO"))) {
//			sqlSb.append(" and tpb.DELVIERY_NO like ?");
//			params.add("%" + queryParam.get("DELVIERY_NO") + "%");
//		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("customerCode"))) {
//			sqlSb.append(" and tpb.CUSTOMER_CODE = ? ");
//			params.add(queryParam.get("customerCode"));
//		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("ORDER_DATEFrom"))) {
//			sqlSb.append(" and ORDER_DATE>=? ");
//			params.add(DateUtil.parseDefaultDate(queryParam.get("ORDER_DATEFrom")));
//		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("ORDER_DATETo"))) {
//			sqlSb.append(" and ORDER_DATE<? ");
//			params.add(DateUtil.addOneDay(queryParam.get("ORDER_DATETo")));
//		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("ORDER_STATUS"))) {
//			sqlSb.append(" and tpb.ORDER_STATUS = ? ");
//			params.add(Integer.parseInt(queryParam.get("ORDER_STATUS")));
//		}
//		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(), params);
//
//		return pageInfoDto;
//	}
//
//	/**
//	 * 新增
//	 * 
//	 * @author xukl
//	 * @date 2016年8月3日
//	 * @param partBuyDto
//	 * @return (non-Javadoc)
//	 * @see com.yonyou.dms.part.service.stockmanage.PartBuyService#addPartBuy(com.yonyou.dms.part.domains.DTO.stockmanage.PartBuyDTO)
//	 */
//
//	@Override
//	public PartBuyPO addPartBuy(String stockinno, PartBuyDTO partBuyDto) throws ServiceBizException {
//		PartBuyPO partBuyPO = new PartBuyPO();
//		// 设置对象属性
//		setPartBuyPO(partBuyPO, partBuyDto);
//		partBuyPO.setString("STOCK_IN_NO", stockinno);
//		partBuyPO.setInteger("ORDER_STATUS", DictCodeConstants.IN_ORDER_NOSTATUS);
//		partBuyPO.saveIt();
//		if (partBuyDto.getPartBuyItemList() != null) {
//			for (PartBuyItemDTO partBuyItemDto : partBuyDto.getPartBuyItemList()) {
//				PartBuyItemPO partBuyItemPo = setPartBuyItemPO(partBuyItemDto);
//				if (!StringUtils.isNullOrEmpty(partBuyDto.getDeliveryNo())) {
//					PartOrderSignItemPO posi=qryDeliverItem(partBuyDto.getDeliveryNo(), partBuyItemDto.getPartNo(), partBuyItemDto.getInPrice());
//					partBuyItemPo.set("DELIVER_ITEM_ID",  posi.get("ITEM_ID"));//货运单明细id
//				}
//				partBuyPO.add(partBuyItemPo);
//			}
//		}
//		return partBuyPO;
//	}
//
//	/**
//	 * 修改
//	 * 
//	 * @author xukl
//	 * @date 2016年8月7日
//	 * @param id
//	 * @param partBuyDto
//	 * @throws ServiceBizException
//	 */
//
//	@Override
//	public void updatePartBuy(Long id, PartBuyDTO partBuyDto) throws ServiceBizException {
//		PartBuyPO partBuyPO = PartBuyPO.findById(id);
//		int order_status = (Integer) partBuyPO.get("ORDER_STATUS");
//		if(order_status==DictCodeConstants.IN_ORDER_ISSTATUS){
//			throw new ServiceBizException("此配件已入库不能修改!");
//		}
//		//设置对象属性
//		setPartBuyPO(partBuyPO, partBuyDto);
//		partBuyPO.setInteger("ORDER_STATUS", DictCodeConstants.IN_ORDER_NOSTATUS);
//		partBuyPO.saveIt();
//
//		//删除原采购入库明细
//		PartBuyItemPO.delete("PART_BUY_ID = ?", id);
//
//		for (PartBuyItemDTO partBuyItemDto : partBuyDto.getPartBuyItemList()) {
//			PartBuyItemPO partBuyItemPo = setPartBuyItemPO(partBuyItemDto);
//			if(DictCodeConstants.SOURCE_TYPE_ORDER==partBuyItemDto.getFromType()){
//				PartOrderSignItemPO posi=qryDeliverItem(partBuyDto.getDeliveryNo(), partBuyItemDto.getPartNo(), partBuyItemDto.getInPrice());
//				partBuyItemPo.set("DELIVER_ITEM_ID",  posi.get("ITEM_ID"));//货运单明细id
//			}
//			partBuyPO.add(partBuyItemPo);
//		}
//	}
//
//	/**
//	 * 入账
//	 * 
//	 * @author xukl
//	 * @date 2016年8月5日
//	 * @param id
//	 * @throws ServiceBizException (non-Javadoc)
//	 * @see com.yonyou.dms.part.service.stockmanage.PartBuyService#doInWarehouse(java.lang.Long)
//	 */
//
//	@Override
//	public void doInWarehouse(Long id) throws ServiceBizException {
//		PartBuyPO pbpo = PartBuyPO.findById(id);
//		// 查询入库明细表信息
//		List<Map> result = qryPartBuyItems(id);
//		if (null == result || result.size() == 0) {
//			throw new ServiceBizException("未找到相关数据");
//		}
//		// 更新库存and记录配件流水and更新货运单
//		pbpo= updateStock(result,pbpo);
//		// 更新采购入库明细表入库状态为已入库
//		PartBuyItemPO.update("IS_FINISHED=?,FINISHED_DATE=?", "PART_BUY_ID=?", DictCodeConstants.STATUS_IS_YES,new Date(), id);
//		// 更新采购入库主表入库状态为已入库
//		pbpo.setInteger("ORDER_STATUS", DictCodeConstants.IN_ORDER_ISSTATUS);
//		pbpo.saveIt();
//	}
//
//	/**
//	 * 更新库存
//	 * 
//	 * @author xukl
//	 * @date 2016年8月5日
//	 * @param result
//	 * @throws ServiceBizException
//	 * @throws ParseException 
//	 */
//	private PartBuyPO updateStock(List<Map> result,PartBuyPO pbpo) throws ServiceBizException {
//		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
//		Double sumInAmountTaxed=0.0;//含税总金额
//		Double sumInAmount=0.0;//不含税总金额
//		String customerCode=(String) pbpo.get("CUSTOMER_CODE");
//		for (int i = 0; i < result.size(); i++) {
//			Map map = result.get(i);
//			String storageCode = (String) map.get("storage_code"); // 仓库代码
//			String storagePositionCode = (String) map.get("STORAGE_POSITION_CODE"); // 仓库代码
//			String partNo = (String) map.get("part_no"); // 配件代码
//			String partName=(String) map.get("part_name");//配件名称
//			Double inPrice=new Double("0");
//			if(!StringUtils.isNullOrEmpty(map.get("in_price"))){
//				inPrice=Double.valueOf(map.get("in_price").toString());// 不含税单价
//			}
//			Double inQuantity = Double.valueOf(map.get("in_quantity").toString());// 入库数量
//			Double inAmount = Double.valueOf(map.get("in_amount").toString());// 不含税金额
//			Double inPriceTaxed = Double.valueOf(map.get("in_price_taxed").toString());// 含税单价
//			Double inAmountTaxed= Double.valueOf(map.get("in_amount_taxed").toString());// 含税金额
//			sumInAmountTaxed = sumInAmountTaxed+inAmountTaxed;
//			sumInAmount = sumInAmount+inAmount;
//			//更新货运单已入库数量
//			if(!StringUtils.isNullOrEmpty(pbpo.get("DELIVERY_NO"))){
//				PartOrderSignItemPO posi=qryDeliverItem(pbpo.get("DELIVERY_NO").toString(), partNo, inPrice);
//				Double inQuantityHave=(Double) posi.get("IN_QUANTITY_HAVE");//已入库数量
//				posi.setDouble("IN_QUANTITY_HAVE", inQuantityHave+inQuantity);//已入库数量-退货数量
//				posi.saveIt();
//			}
//			// 仓库代码+配件代码 查询配件库存信息
//			String strSql = "SELECT t.PART_STOCK_ID,t.STOCK_QUANTITY,t.COST_AMOUNT,t.DEALER_CODE from tt_part_stock t where STORAGE_CODE=? and PART_CODE=?";
//			List<Object> queryParam = new ArrayList<Object>();
//			queryParam.add(storageCode);
//			queryParam.add(partNo);
//			List<Map> stockList = DAOUtil.findAll(strSql, queryParam);
//			if(CommonUtils.isNullOrEmpty(stockList)){
//				PartStockPO partStock=new PartStockPO();
//				partStock.setString("storage_code",storageCode);
//				partStock.setString("STORAGE_POSITION_CODE",storagePositionCode);
//				partStock.setString("part_code",partNo);
//				partStock.setString("part_name",partName);
//				partStock.setDate("last_stock_in",new Date());
//				partStock.setDouble("stock_quantity",inQuantity);
//				partStock.setTimestamp("LAST_STOCK_IN", new Date());
//				partStock.setDouble("TAX_LATEST_PRICE", inPrice);
//				Map mappart=qryPartByCode(partNo);
//				if(!CommonUtils.isNullOrEmpty(mappart)){
//					partStock.setInteger("part_status", mappart.get("part_status"));//是否停用
//					partStock.setDouble("limit_price", mappart.get("limit_price"));//销售限价
//					partStock.setDouble("advice_sale_price", mappart.get("advice_sale_price"));//建议销售价
//					partStock.setDouble("sales_price", mappart.get("advice_sale_price"));//销售价
//					partStock.setDouble("claim_price", mappart.get("claim_price"));//索赔价
//				}
//				partStock.setDouble("cost_price", inAmount/inQuantity);
//				partStock.setDouble("cost_amount", inAmount);
//				//新增库存
//				partStock.saveIt();
//
//				//记录配件流水账
//				PartFlowPO pfpo= new PartFlowPO();
//				pfpo.setInteger("IN_OUT_TYPE", DictCodeConstants.PART_BUY_IN);//出入库类型
//				pfpo.setTimestamp("OPERATE_DATE", new Date());//发生日期
//				pfpo.setDouble("STOCK_IN_QUANTITY", inQuantity);//入库数量
//				pfpo.setDouble("STOCK_QUANTITY", inQuantity);//库存数量
//				pfpo.setDouble("IN_OUT_NET_PRICE", inPrice);//入库不含税单价
//				pfpo.setDouble("IN_OUT_NET_AMOUNT", inAmount);//入库不含税金额
//				pfpo.setDouble("cost_price", inPrice);
//				pfpo.setDouble("cost_amount", inAmount);
//				pfpo.setDouble("IN_OUT_TAXED_PRICE", inPriceTaxed);
//				pfpo.setDouble("IN_OUT_TAXED_AMOUNT", inAmountTaxed);
//				pfpo.setString("SHEET_NO", pbpo.get("STOCK_IN_NO"));//入库单号
//				pfpo.setString("STORAGE_CODE", storageCode);//仓库代码
//				pfpo.setString("PART_NO", partNo);//配件代码
//				pfpo.setString("PART_NAME", partName);//配件名称
//				pfpo.setInteger("CUSTOMER_TYPE", DictCodeConstants.CUSTOMER_CONTACT);//客户类型
//				pfpo.setString("CUSTOMER_CODE", customerCode);//客户代码
//				pfpo.setString("OPERATOR", loginInfo.getEmployeeNo());
//				pfpo.saveIt();
//			}else{
//				Map stockMap = stockList.get(0);
//				Integer id = Integer.parseInt(stockMap.get("PART_STOCK_ID").toString());//库存id
//				Double stockQuantity = Double.valueOf(stockMap.get("stock_quantity").toString()); // 账面库存数量
//				Double costAmount = Double.valueOf(stockMap.get("cost_amount").toString()); // 成本金额
//
//				// 计算最新库存数量 和 成本金额
//				BigDecimal newStockQuantity = NumberUtil.add(doubleToBigDecimal(inQuantity), doubleToBigDecimal(stockQuantity)); // 入库后库存数量
//				BigDecimal newcostAmount = NumberUtil.add(doubleToBigDecimal(inAmount), doubleToBigDecimal(costAmount)); // 入库后成本金额
//				BigDecimal newcostPrice = NumberUtil.add(newcostAmount, newStockQuantity);
//				//更新库存
//				PartStockPO ptpo=PartStockPO.findById(id);
//				ptpo.setDouble("COST_PRICE", newcostPrice.doubleValue());
//				ptpo.setDouble("COST_AMOUNT", newcostAmount.doubleValue());
//				ptpo.setDouble("STOCK_QUANTITY", newStockQuantity.doubleValue());
//				ptpo.setDouble("TAX_LATEST_PRICE", inPrice);
//				ptpo.setString("STORAGE_POSITION_CODE",storagePositionCode);
//				ptpo.setTimestamp("LAST_STOCK_IN", new Date());
//				if(newStockQuantity.doubleValue()!=0){
//					ptpo.setDouble("COST_PRICE", NumberUtil.div(newcostAmount, newStockQuantity, 2).doubleValue());
//				}
//				ptpo.saveIt();
//
//				//记录配件流水账
//				PartFlowPO pfpo= new PartFlowPO();
//				pfpo.setInteger("IN_OUT_TYPE", DictCodeConstants.PART_BUY_IN);//出入库类型
//				pfpo.setTimestamp("OPERATE_DATE", new Date());//发生日期
//				pfpo.setDouble("STOCK_IN_QUANTITY", inQuantity);//入库数量
//				pfpo.setDouble("STOCK_QUANTITY", newStockQuantity.doubleValue());//库存数量
//				pfpo.setDouble("IN_OUT_NET_PRICE", inPrice);//入库不含税单价
//				pfpo.setDouble("IN_OUT_NET_AMOUNT", inAmount);//入库不含税金额
//				pfpo.setDouble("IN_OUT_TAXED_PRICE", inPriceTaxed);//入库含税单价
//				pfpo.setDouble("IN_OUT_TAXED_AMOUNT", inAmountTaxed);//入库含税金额
//				if(newStockQuantity.doubleValue()!=0){
//					pfpo.setDouble("COST_PRICE", NumberUtil.div(newcostAmount, newStockQuantity, 2).doubleValue());//成本单价
//				}
//				pfpo.setDouble("COST_AMOUNT", newcostAmount);//成本金额
//				pfpo.setString("SHEET_NO", pbpo.get("STOCK_IN_NO"));//入库单号
//				pfpo.setString("STORAGE_CODE", storageCode);//仓库代码
//				pfpo.setString("PART_NO", partNo);//配件代码
//				pfpo.setString("PART_NAME", partName);//配件名称
//				pfpo.setInteger("CUSTOMER_TYPE", DictCodeConstants.CUSTOMER_CONTACT);//客户类型
//				pfpo.setString("CUSTOMER_CODE", customerCode);//客户代码
//				pfpo.setString("OPERATOR", loginInfo.getEmployeeNo());
//				pfpo.saveIt();
//			}
//		}
//		pbpo.setDouble("BEFORE_TAX_AMOUNT", sumInAmountTaxed);//设置税前入库总金额
//		pbpo.setDouble("TOTAL_AMOUNT", sumInAmount);//设置税后总金额
//		return pbpo;
//	}
//	/**
//	 * 查配件基础信息
//	 * @author xukl
//	 * @date 2016年8月25日
//	 * @param partNo
//	 * @return
//	 */
//	private Map qryPartByCode(String partNo){
//		StringBuilder str=new StringBuilder("select * from tm_part_info where PART_CODE=?");
//		List<Object> queryParam = new ArrayList<Object>();
//		queryParam.add(partNo);
//		Map map = DAOUtil.findFirst(str.toString(), queryParam);
//		return map;
//	}
//	/**
//	 * 查询配件明细信息
//	 * 
//	 * @author xukl
//	 * @date 2016年8月5日
//	 * @param id
//	 * @return result
//	 * @throws ServiceBizException
//	 */
//
//	private List<Map> qryPartBuyItems(Long id) throws ServiceBizException {
//		List<Object> param = new ArrayList<Object>();
//		StringBuilder sb = new StringBuilder("SELECT\n")
//				.append("  tpbi.DEALER_CODE,\n")
//				.append("  tpbi.IN_QUANTITY,\n")
//				.append("  tpbi.STORAGE_CODE,tpbi.STORAGE_POSITION_CODE,\n")
//				.append("  tpbi.PART_NO,tpbi.PART_NAME,\n")
//				.append("  tpbi.IN_AMOUNT,tpbi.IN_PRICE,\n")
//				.append("  tpbi.IN_AMOUNT_TAXED,\n")
//				.append("  tpbi.IN_PRICE_TAXED\n")
//				.append("FROM\n")
//				.append("  tt_part_buy_item tpbi\n")
//				.append("WHERE\n")
//				.append("  tpbi.PART_BUY_ID = ?\n");
//		param.add(id);
//		List<Map> result = DAOUtil.findAll(sb.toString(), param);
//		return result;
//	}
//
//	/**
//	 * 设置采购入库单明细
//	 * 
//	 * @author xukl
//	 * @date 2016年8月3日
//	 * @param partBuyItemDto
//	 * @return partBuyItemPo
//	 */
//	private PartBuyItemPO setPartBuyItemPO(PartBuyItemDTO partBuyItemDto) throws ServiceBizException {
//		PartBuyItemPO partBuyItemPo = new PartBuyItemPO();
//		partBuyItemPo.setInteger("DELIVER_ITEM_ID", partBuyItemDto.getDeliverItemId());
//		partBuyItemPo.setString("STORAGE_CODE", partBuyItemDto.getStorageCode());
//		partBuyItemPo.setString("STORAGE_POSITION_CODE", partBuyItemDto.getStoragePositionCode());
//		partBuyItemPo.setString("PART_NO", partBuyItemDto.getPartNo());
//		partBuyItemPo.setString("PART_NAME", partBuyItemDto.getPartName());
//		partBuyItemPo.setString("UNIT", partBuyItemDto.getUnit());
//		partBuyItemPo.setDouble("IN_QUANTITY", partBuyItemDto.getInQuantity());
//		partBuyItemPo.setDouble("IN_AMOUNT", partBuyItemDto.getInAmount());
//		partBuyItemPo.setDouble("IN_PRICE", partBuyItemDto.getInPrice());
//		partBuyItemPo.setDouble("IN_PRICE_TAXED", partBuyItemDto.getInPriceTaxed());
//		partBuyItemPo.setDouble("IN_AMOUNT_TAXED", partBuyItemDto.getInAmountTaxed());
//		partBuyItemPo.setInteger("FROM_TYPE", partBuyItemDto.getFromType());
//		partBuyItemPo.setInteger("IS_FINISHED", partBuyItemDto.getIsFinished());
//		return partBuyItemPo;
//	}
//
//	/**
//	 * 设置采购入库
//	 * 
//	 * @author xukl
//	 * @date 2016年8月3日
//	 * @param partBuyPO
//	 * @param partBuyDto
//	 */
//
//	private void setPartBuyPO(PartBuyPO partBuyPO, PartBuyDTO partBuyDto) throws ServiceBizException {
//		partBuyPO.setString("STOCK_IN_NO", partBuyDto.getStockInNo());
//		partBuyPO.setString("CUSTOMER_CODE", partBuyDto.getCustomerCode());
//		partBuyPO.setString("CUSTOMER_NAME", partBuyDto.getCustomerName());
//		partBuyPO.setString("DELIVERY_NO", partBuyDto.getDeliveryNo());
//		partBuyPO.setDate("ORDER_DATE", partBuyDto.getOrderDate());
//		partBuyPO.setString("REMARK", partBuyDto.getRemark());
//	}
//
//	/**
//	 * 退货时查询采购明细
//	 * @author xukl
//	 * @date 2016年8月8日
//	 * @param id
//	 * @return
//	 * @throws ServiceBizException
//	 */
//
//	@Override
//	public List<Map> qryPartBuyReturnItems(Long id) throws ServiceBizException {
//		PartBuyPO partBuyPO = PartBuyPO.findById(id);
//		if((Integer)partBuyPO.get("ORDER_STATUS")==DictCodeConstants.IN_ORDER_NOSTATUS){
//			throw new ServiceBizException("此配件未入库不能退货!");
//		}
//		StringBuilder sb = new StringBuilder("SELECT\n")
//				.append("  tpbi.DEALER_CODE,\n")
//				.append("  sum(tpbi.IN_QUANTITY) AS RETURN_QUANTITY,\n")
//				.append("  sum(tpbi.IN_AMOUNT) AS IN_AMOUNT,\n")
//				.append("  sum(tpbi.IN_AMOUNT_TAXED) AS IN_AMOUNT_TAXED,\n")
//				.append("  tpbi.IN_PRICE,\n")
//				.append("  tpbi.STORAGE_POSITION_CODE,\n")
//				.append("  tpbi.PART_NO AS PART_CODE,\n")
//				.append("  tpbi.PART_NAME,\n")
//				.append("  tpbi.STORAGE_CODE,\n")
//				.append("  tpbi.IN_PRICE_TAXED,\n")
//				.append("  ( SELECT\n")
//				.append("      sum(ti.IN_QUANTITY)\n")
//				.append("    FROM\n")
//				.append("      tt_part_buy_item ti\n")
//				.append("    WHERE\n")
//				.append("      ti.PART_BUY_ID = tpbi.PART_BUY_ID\n")
//				.append("    AND ti.PART_NO = tpbi.PART_NO\n")
//				.append("    AND ti.STORAGE_CODE = tpbi.STORAGE_CODE\n")
//				.append("    AND ti.IN_PRICE = tpbi.IN_PRICE\n")
//				.append("    AND ti.IN_QUANTITY > 0\n")
//				.append("    group by ti.PART_NO,\n")
//				.append("             ti.STORAGE_CODE,\n")
//				.append("             ti.IN_PRICE\n")
//				.append("  ) AS IN_QUANTITY\n")
//				.append("FROM\n")
//				.append("  tt_part_buy_item tpbi\n")
//				.append("WHERE\n")
//				.append("  tpbi.PART_BUY_ID = ?\n")
//				.append("GROUP BY\n")
//				.append("  tpbi.PART_NO,\n")
//				.append("  tpbi.STORAGE_CODE,\n")
//				.append("  tpbi.IN_PRICE\n");
//		List<Object> param = new ArrayList<Object>();
//		param.add(id);
//		List<Map> result = DAOUtil.findAll(sb.toString(), param);
//		return result;
//	}
//
//	/**
//	 * 退货
//	 * @date 2016年8月8日
//	 * @param partBuyDto
//	 * @throws ServiceBizException
//	 * (non-Javadoc)
//	 * @see com.yonyou.dms.part.service.stockmanage.PartBuyService#backGoods(com.yonyou.dms.part.domains.DTO.stockmanage.PartBuyDTO)
//	 */
//
//	@Override
//	public void backGoods(PartBuyDTO partBuyDto,Long id) throws ServiceBizException{
//		PartBuyPO partBuyPO = PartBuyPO.findById(id);
//		LoginInfoDto loginInfo= ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
//		int order_status = (Integer) partBuyPO.get("ORDER_STATUS");
//		if(order_status==DictCodeConstants.IN_ORDER_NOSTATUS){
//			throw new ServiceBizException("此配件未入库不能退货!");
//		}
//		if (partBuyDto.getPartBuyItemList() != null) {
//			Double sumInAmount=0.0;//退货不含税总金额
//			Double sumInAmountTaxed=0.0;//含税总金额
//			for (PartBuyItemDTO partBuyItemDto : partBuyDto.getPartBuyItemList()) {
//				if(!StringUtils.isNullOrEmpty(partBuyItemDto.getInQuantity())){
//					int result=NumberUtil.compareTo(partBuyItemDto.getInQuantity(),partBuyItemDto.getReturnQuantity());
//					if(result==1){
//						throw new ServiceBizException("本次退货数量不能超过可退货数量 ");
//					}
//					Double inQuantity=partBuyItemDto.getInQuantity()!=null?-partBuyItemDto.getInQuantity():0;//退货数量
//					Double inprice=partBuyItemDto.getInPrice();//不含税单价
//					if(inQuantity!=0){
//						Double inPriceTaxed= partBuyItemDto.getInPriceTaxed();//含税单价
//						String storageCode=partBuyItemDto.getStorageCode();//仓库代码
//						String partNo=partBuyItemDto.getPartNo();//配件代码
//						String partName=partBuyItemDto.getPartName();//配件名称
//						sumInAmount+=inprice*inQuantity;//计算退货总金额
//						sumInAmountTaxed+=inPriceTaxed*inQuantity;
//						//查来源方式
//						int fromType = qryFromType(id, storageCode, partNo, inprice);
//						//当来源方式为发运单的时候更新发运单入库数量
//						if(fromType==DictCodeConstants.SOURCE_TYPE_ORDER){   
//							PartOrderSignItemPO posi=qryDeliverItem(partBuyPO.get("DELIVERY_NO").toString(), partBuyItemDto.getPartNo(), partBuyItemDto.getInPrice());
//							Double inQuantityHave=(Double) posi.get("IN_QUANTITY_HAVE");//已入库数量
//							posi.setDouble("IN_QUANTITY_HAVE", inQuantityHave+inQuantity);//已入库数量-退货数量
//							posi.setInteger("IS_VERIFICATION",DictCodeConstants.STATUS_IS_NOT);
//							posi.saveIt();
//						}
//						//退货更新库存+记流水账
//						String strSql = "SELECT t.STOCK_QUANTITY,t.COST_AMOUNT,t.DEALER_CODE from tt_part_stock t where STORAGE_CODE=? and PART_CODE=?";
//						List<Object> queryParam = new ArrayList<Object>();
//						queryParam.add(partBuyItemDto.getStorageCode());
//						queryParam.add(partBuyItemDto.getPartNo());
//						List<Map> stockList = DAOUtil.findAll(strSql, queryParam);
//						if(null==stockList||stockList.size()==0){
//							throw new ServiceBizException("未查询到相关库存信息！");
//						}else{
//							Map stockMap = stockList.get(0);
//							Double stockQuantity = Double.valueOf(stockMap.get("stock_quantity").toString()); // 账面库存数量
//							Double costAmount = Double.valueOf(stockMap.get("cost_amount").toString()); // 成本金额
//
//							// 计算最新库存数量 和 成本金额
//							Double newStockQuantity = inQuantity + stockQuantity; // 入库后库存数量
//							Double newcostAmount = inprice*inQuantity + costAmount; // 入库后成本金额
//
//							// 更新库存
//							PartStockPO.update("COST_AMOUNT=?,STOCK_QUANTITY=?,COST_PRICE=?",
//									"STORAGE_CODE=? and PART_CODE=? and DEALER_CODE = ?", newcostAmount, newStockQuantity,
//									(newcostAmount / newStockQuantity), storageCode, partNo,loginInfo.getDealerCode());
//							//记录配件流水账
//							PartFlowPO pfpo= new PartFlowPO();
//							pfpo.setInteger("IN_OUT_TYPE", DictCodeConstants.PART_BUY_IN);//出入库类型
//							pfpo.setTimestamp("OPERATE_DATE",new Date());
//
//
//							pfpo.setDouble("STOCK_IN_QUANTITY", inQuantity);//入库数量
//							pfpo.setDouble("STOCK_QUANTITY", newStockQuantity);//库存数量
//							pfpo.setDouble("STOCK_QUANTITY", inQuantity);//库存数量
//							pfpo.setDouble("IN_OUT_NET_PRICE", inprice);//入库不含税单价
//							pfpo.setDouble("IN_OUT_NET_AMOUNT", inprice*inQuantity);//入库不含税金额
//							pfpo.setDouble("cost_price", newcostAmount/newStockQuantity);
//							pfpo.setDouble("cost_amount", newcostAmount);
//							pfpo.setDouble("IN_OUT_TAXED_PRICE", inPriceTaxed);
//							pfpo.setDouble("IN_OUT_TAXED_AMOUNT", inQuantity*inPriceTaxed);
//							pfpo.setString("SHEET_NO", partBuyPO.get("STOCK_IN_NO"));//入库单号
//							pfpo.setString("STORAGE_CODE", storageCode);//仓库代码
//							pfpo.setString("PART_NO", partNo);//配件代码
//							pfpo.setString("PART_NAME", partName);//配件名称
//							pfpo.setString("CUSTOMER_TYPE", DictCodeConstants.CUSTOMER_CONTACT);//客户类型
//							pfpo.setString("CUSTOMER_CODE", partBuyPO.get("CUSTOMER_CODE"));//客户代码
//
//							pfpo.saveIt();
//						}
//
//						partBuyItemDto.setInQuantity(inQuantity);//设置退货数量
//						partBuyItemDto.setInAmount(inprice*inQuantity);//设置退货不含税金额
//						partBuyItemDto.setInAmountTaxed(inPriceTaxed*inQuantity);//设置退货含税金额
//						PartBuyItemPO partBuyItemPo = setPartBuyItemPO(partBuyItemDto);
//						partBuyItemPo.setInteger("FROM_TYPE", fromType);
//						partBuyItemPo.setString("PART_BUY_ID", id);
//						partBuyItemPo.saveIt();
//					}
//					partBuyPO.setDouble("TOTAL_AMOUNT", partBuyPO.getDouble("TOTAL_AMOUNT")+sumInAmount);//更新税后总金额
//					partBuyPO.setDouble("BEFORE_TAX_AMOUNT", partBuyPO.getDouble("BEFORE_TAX_AMOUNT")+sumInAmountTaxed);//更新税前总金额
//					partBuyPO.saveIt();
//				}
//
//			}
//		}
//	}
//
//
//	/**
//	 * 查询来源方式
//	 * @author xukl
//	 * @date 2016年8月10日
//	 * @param id
//	 * @param storageCode
//	 * @param partCode
//	 * @param price
//	 * @return
//	 */
//
//	private int qryFromType(Long id,String storageCode,String partCode,Double price)throws ServiceBizException{
//		String sql="select tb.DEALER_CODE,tb.FROM_TYPE from tt_part_buy_item tb where tb.PART_BUY_ID = ? and  tb.STORAGE_CODE = ? and tb.PART_NO = ? and tb.IN_PRICE= ? and tb.IN_QUANTITY>0";
//		List<Object> param = new ArrayList<Object>();
//		param.add(id);
//		param.add(storageCode);
//		param.add(partCode);
//		param.add(price);
//		Map map=DAOUtil.findFirst(sql, param);
//		int fromType = Integer.parseInt(map.get("FROM_TYPE").toString());
//		return fromType ;
//	}
//	/**
//	 * 查货运单明细
//	 * @author xukl
//	 * @date 2016年8月9日
//	 * @param deliverNo
//	 * @param partCode
//	 * @param planprice
//	 * @return
//	 */
//
//	private PartOrderSignItemPO qryDeliverItem(String deliverNo,String partCode,Double planprice)throws ServiceBizException{
//		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
//		StringBuilder strb=new StringBuilder("SELECT tpdi.ITEM_ID, tpdi.DEALER_CODE FROM tt_pt_deliver_item tpdi INNER JOIN tt_pt_deliver tpd on tpd.DELIVER_ID=tpdi.DELIVER_ID and tpd.DEALER_CODE=tpdi.DEALER_CODE WHERE tpd.DELIVERY_NO =? AND tpdi.DEALER_CODE =? AND tpdi.PART_NO = ? AND tpdi.PLAN_PRICE =?");
//		List<Object> param = new ArrayList<Object>();
//		param.add(deliverNo);
//		param.add(loginInfo.getDealerCode());
//		param.add(partCode);
//		param.add(planprice);
//		Map map = DAOUtil.findFirst(strb.toString(), param);
//		Long id= Long.valueOf(map.get("ITEM_ID").toString());
//		PartOrderSignItemPO posi=PartOrderSignItemPO.findById(id);
//		return posi;
//	}
//	/**
//	 * 删除
//	 *  @author xukl
//	 * @date 2016年8月9日
//	 * @param id
//	 * @throws ServiceBizException
//	 * (non-Javadoc)
//	 * @see com.yonyou.dms.part.service.stockmanage.PartBuyService#deletePartBuybyId(java.lang.Long)
//	 */
//	@Override
//	public void deletePartBuybyId(Long id) throws ServiceBizException{
//		PartBuyPO partBuy = PartBuyPO.findById(id);
//		int order_status = (Integer) partBuy.get("ORDER_STATUS");
//		if(order_status==13041002){
//			throw new ServiceBizException("此配件已入库不能删除!");
//		}
//		//记录操作日志
//		OperateLogDto operateLogDto=new OperateLogDto();
//		operateLogDto.setOperateContent("配件采购入库单删除：采购入库单号【"+partBuy.getString("STOCK_IN_NO")+"】");
//		operateLogDto.setOperateType(DictCodeConstants.LOG_SYSTEM_PART);
//		operateLogService.writeOperateLog(operateLogDto);
//		partBuy.deleteCascadeShallow();
//
//
//
//	}
//
//	/**
//	 * double转bigdecimal
//	 * @author xukl
//	 * @date 2016年9月7日
//	 * @param db
//	 * @return
//	 */
//
//	private BigDecimal doubleToBigDecimal(Double db){
//		return new  BigDecimal(db.toString());
//	}



}
