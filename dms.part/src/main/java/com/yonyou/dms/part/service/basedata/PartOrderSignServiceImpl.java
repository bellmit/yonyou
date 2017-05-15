/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : LabourPriceService1.java
*
 * @Author : zhengcong
 *
 * @Date : 2017年3月30日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月30日    zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtPtDeliverPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.PartOrderSignDTO;

/**
 * 
*签收货运单serviceimpl 
 * @author zhengcong
 * @date 2017年3月30日
 */
@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class PartOrderSignServiceImpl implements PartOrderSignService {
    

//    @Autowired
//    private SystemParamService systemParamService;
	private List<Map> dataDetail;

    /**
     * 根据传入信息查询
 * @author zhengcong
 * @date 2017年3月30日
     */
    @Override
   public PageInfoDto queryPartOrder(Map<String, String> queryParam)throws ServiceBizException {
        StringBuffer sqlsb = new StringBuffer("SELECT A.DEALER_CODE,");
        sqlsb.append(" A.ORDER_REGEDIT_NO,A.DELIVERY_ORDER_NO,A.IS_SIGNED,A.DELIVERY_STATUS,  ");
        sqlsb.append(" A.IS_VERIFICATION,A.IS_VALID,A.DMS_ORDER_NO,A.SAP_ORDER_NO,A.TRANS_NO,  ");
        sqlsb.append(" A.ARRIVED_DATE,B.STOCK_IN_NO,A.DELIVERY_PDC,A.DELIVERY_TIME,  ");
        sqlsb.append(" A.DELIVERY_COMPANY,A.IS_TEMPORARY_STORAGE,  ");
        sqlsb.append(" ifnull(A.RECEIVABLE_CASES,0) RECEIVABLE_CASES,ifnull(A.FACT_CASES,0) FACT_CASES,A.CASE_ID,A.QUANTITY,A.REMARK FROM TT_PT_DELIVER  ");
        sqlsb.append(" A LEFT JOIN TT_PART_BUY B ON A.DEALER_CODE = B.DEALER_CODE AND  ");
        sqlsb.append(" A.ORDER_REGEDIT_NO = B.DELVIERY_NO AND A.DELIVERY_TIME = B.DELIVERY_TIME ");
        sqlsb.append(" WHERE A.D_KEY = 0 ");
        
        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("ORDER_REGEDIT_NO"))) {
            sqlsb.append(" and A.ORDER_REGEDIT_NO like ?");
            params.add("%"+queryParam.get("ORDER_REGEDIT_NO")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("DELIVERY_ORDER_NO"))) {
            sqlsb.append(" and A.DELIVERY_ORDER_NO like ?");
            params.add("%"+queryParam.get("DELIVERY_ORDER_NO")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("CASE_ID"))) {
            sqlsb.append(" and A.CASE_ID like ?");
            params.add("%"+queryParam.get("CASE_ID")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("IS_SIGNED"))) {
            sqlsb.append(" and A.IS_SIGNED = ?");
            params.add(queryParam.get("IS_SIGNED"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("IS_FIAT"))) {
            sqlsb.append(" and A.IS_FIAT = ?");
            params.add(queryParam.get("IS_FIAT"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("IS_VALID"))) {
        	if(queryParam.get("IS_VALID").equals("12781002")) {
                sqlsb.append(" and A.IS_VALID = 12781002");
        	}else if(queryParam.get("IS_VALID").equals("12781001")){
        		sqlsb.append(" and (A.IS_VALID IS NULL or A.IS_VALID = 12781001 or A.IS_VALID = 0) ");
        	}

        }
              
        if(!StringUtils.isNullOrEmpty(queryParam.get("deliveryTimeFrom")) 
           && !StringUtils.isNullOrEmpty(queryParam.get("deliveryTimeTo"))) {
        	long day=0;
        	  try {
          		Date startdate = new SimpleDateFormat("yyyy-MM-dd").parse(queryParam.get("deliveryTimeFrom"));
          		Date enddate = new SimpleDateFormat("yyyy-MM-dd").parse(queryParam.get("deliveryTimeTo"));
          		//获取相减后天数

          		day= (enddate.getTime()-startdate.getTime())/(24*60*60*1000);
          		}catch(Exception e) {
          			e.printStackTrace();
          		}
        	  
        	  if (day<=90) {
        		  Utility.sqlToDate(sqlsb, queryParam.get("deliveryTimeFrom"), queryParam.get("deliveryTimeTo"), "DELIVERY_TIME", "A");
              	
                  PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(),params);
                  return pageInfoDto;
        	  }else {
        		  throw new ServiceBizException("发运日期可调整的最大时间跨度为90天");  
        	  }
        	
        }else {
        	throw new ServiceBizException("必须选择发运日期，且可调整的最大时间跨度为90天");
        }


    }    
 
    /**
     * 根据CODE查询核销信息
 * @author zhengcong
 * @date 2017年4月5日
     */
    @Override
   public PageInfoDto queryVerification(String ORDER_REGEDIT_NO, String DELIVERY_ORDER_NO)throws ServiceBizException {
    	List<Object> params = new ArrayList<Object>();
        StringBuffer sqlsb = new StringBuffer("SELECT A.DEALER_CODE,A.ORDER_REGEDIT_NO, A.DELIVERY_ORDER_NO,A.PART_NO,A.PART_NAME,A.SIGN_SUPPLY_QTY, ");
        sqlsb.append(" COALESCE(B.IN_QUANTITY,0) AS IN_QUANTITY,  COALESCE(C.SUPPLY_QTY,0)AS SUPPLY_QTY, ");
        sqlsb.append(" (COALESCE(B.IN_QUANTITY,0)-COALESCE(C.SUPPLY_QTY,0)) AS CHECK_QTY  ");
        sqlsb.append(" FROM  (select de.DEALER_CODE,de.ORDER_REGEDIT_NO,de.DELIVERY_ORDER_NO,di.part_no,di.part_name, ");
        sqlsb.append(" COALESCE(sum(di.SUPPLY_QTY),0) as SIGN_SUPPLY_QTY  from tt_pt_deliver de  ");
        sqlsb.append(" left join tt_pt_deliver_item di on de.DEALER_CODE = di.DEALER_CODE and de.ORDER_REGEDIT_NO=di.ORDER_REGEDIT_NO  ");    
        sqlsb.append("   and de.DELIVERY_TIME = di.DELIVERY_TIME where  ");
        sqlsb.append(" de.DEALER_CODE ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
 
        sqlsb.append(" and de.ORDER_REGEDIT_NO='" + ORDER_REGEDIT_NO + "'   and de.DELIVERY_ORDER_NO='" + DELIVERY_ORDER_NO + "'  ");
        
        sqlsb.append("  group by de.DEALER_CODE,de.ORDER_REGEDIT_NO,de.DELIVERY_ORDER_NO,di.part_no,di.part_name) A    ");
        sqlsb.append(" left join (select tb.DEALER_CODE,tb.OEM_ORDER_NO,pm.part_no,COALESCE(sum(pm.IN_QUANTITY),0) as IN_QUANTITY ");
        sqlsb.append("  from tt_part_buy tb left join tt_part_buy_item pm  on tb.DEALER_CODE = pm.DEALER_CODE  ");
        sqlsb.append(" and tb.STOCK_IN_NO= pm.STOCK_IN_NO  where tb.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "'   ");
        
        sqlsb.append(" and tb.OEM_ORDER_NO='" + DELIVERY_ORDER_NO + "'   and tb.IS_FINISHED='12781001' group by tb.DEALER_CODE, ");
        
        sqlsb.append(" tb.OEM_ORDER_NO,pm.part_no) B   ON A.DEALER_CODE=B.DEALER_CODE AND ");
        sqlsb.append("  A.DELIVERY_ORDER_NO=B.OEM_ORDER_NO AND A.PART_NO=B.PART_NO left join  ");
        sqlsb.append("  ( select de.DEALER_CODE,de.DELIVERY_ORDER_NO,di.part_no,COALESCE(sum(di.SUPPLY_QTY),0) as SUPPLY_QTY ");
        sqlsb.append("  from tt_pt_deliver de   left join tt_pt_deliver_item di on de.DEALER_CODE = di.DEALER_CODE  ");
        sqlsb.append(" and de.ORDER_REGEDIT_NO=di.ORDER_REGEDIT_NO   and de.DELIVERY_TIME = di.DELIVERY_TIME ");
        
        sqlsb.append("  where de.DEALER_CODE ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
        sqlsb.append("  and de.DELIVERY_ORDER_NO='" + DELIVERY_ORDER_NO + "'   ");
        sqlsb.append(" and de.IS_VALID='12781001' and de.IS_VERIFICATION='12781001' group by de.DEALER_CODE, ");
        sqlsb.append(" de.DELIVERY_ORDER_NO,di.part_no  )C ON B.DEALER_CODE=C.DEALER_CODE AND  ");
        sqlsb.append(" B.OEM_ORDER_NO=C.DELIVERY_ORDER_NO AND B.PART_NO=C.PART_NO   ");
        sqlsb.append(" WHERE A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "'   ");
        sqlsb.append("  AND A.ORDER_REGEDIT_NO='" + ORDER_REGEDIT_NO + "'   ");
        sqlsb.append("  and A.DELIVERY_ORDER_NO='" + DELIVERY_ORDER_NO + "'  ");
        

                  PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(),params);
                  return pageInfoDto;
        	

    }      
    
    
    /**
     * 根据CODE查询
     * @author zhengcong
     * @date 2017年3月24日
     */
	@Override
	public Map<String, String> findByCode(String ORDER_REGEDIT_NO,String DELIVERY_TIME) throws ServiceBizException {	
		
    StringBuilder sqlsb = new StringBuilder(" SELECT DEALER_CODE,ORDER_REGEDIT_NO,DELIVERY_COMPANY,ifnull(RECEIVABLE_CASES,0) RECEIVABLE_CASES,ifnull(FACT_CASES,0) FACT_CASES "); 
    sqlsb.append(" FROM TT_PT_DELIVER  where 1=1 and d_key= 0 ");
    sqlsb.append("and dealer_code ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
    List queryParam = new ArrayList();
    sqlsb.append(" and ORDER_REGEDIT_NO = ? ");   
    queryParam.add(ORDER_REGEDIT_NO);
    sqlsb.append(" and DELIVERY_TIME = ? ");
    queryParam.add(this.stampToDate(DELIVERY_TIME));
    return DAOUtil.findFirst(sqlsb.toString(), queryParam);
}    
    

	/**
	 * 根据CODE签收修改
  * @author zhengcong
  * @date 2017年4月5日
	 */

	@Override
	public void signByCode(String ORDER_REGEDIT_NO,String DELIVERY_TIME,PartOrderSignDTO ctdto) throws ServiceBizException{
		TtPtDeliverPO ctpo=TtPtDeliverPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),ORDER_REGEDIT_NO,this.stampToDate(DELIVERY_TIME));
		if(ctdto.getFactCases()>0) {
			ctpo.setDouble("FACT_CASES",ctdto.getFactCases() );  
			ctpo.setDouble("IS_SIGNED",DictCodeConstants.STATUS_IS_YES);  
			ctpo.setDouble("DELIVERY_STATUS",DictCodeConstants.DELIVERY_STATUS_SIGNED);
			ctpo.saveIt();
			}else {
			    throw new ServiceBizException("签收箱数必须大于0");
			}

	} 
    
    
  /**
  * 货运单明细界面查询
 * @author zhengcong
 * @date 2017年3月30日
 */
 	
 @Override
  public List<Map> queryPartOrderDetail(String ORDER_REGEDIT_NO,String DELIVERY_TIME) throws ServiceBizException {
	 StringBuffer sqlsb = new StringBuffer("SELECT '' AS STORAGE_POSITION_CODE,A.SAP_ORDER_NO,B.UNIT_CODE,A.COUNT,");
	 sqlsb.append("     a.DEALER_CODE,a.D_KEY, ");
	 sqlsb.append("     a.VER,a.ITEM_ID,a.PART_NO,b.PART_NAME,b.UNIT_CODE AS UNIT_NAME,a.AMOUNT, ");
	 sqlsb.append("     a.REMARK,a.PLAN_PRICE,a.INSTRUCT_PRICE, a.TAX,a.TAX_AMOUNT, ");
	 sqlsb.append("     a.ORDER_REGEDIT_NO,a.ORDER_NO,a.OEM_ORDER_NO,a.SORT, a.CASE_NO,a.BO,a.EXBO ");
	 sqlsb.append("     ,a.SUPPLY_QTY,a.NEED_QUANTITY,a.IS_FIAT,a.TAXED_AMOUNT, ");
	 sqlsb.append("     CASE ");
	 sqlsb.append("       WHEN b.CLAIM_PRICE IS NULL ");
	 sqlsb.append("         THEN 0 ");
	 sqlsb.append("       ELSE b.CLAIM_PRICE ");
	 sqlsb.append("     END AS CLAIM_PRICE ");
	 sqlsb.append("   from Tt_Pt_Deliver_Item a ");
	 sqlsb.append("     left join ("+CommonConstants.VM_PART_INFO+") b");
	 sqlsb.append("     on a.part_no=b.part_no ");
	 sqlsb.append("     and a.dealer_code=b.dealer_code ");
	 sqlsb.append("   where 1=1 ");
	 sqlsb.append("   and a.D_Key=0 ");
	 
	 List<Object> params = new ArrayList<Object>();


     sqlsb.append(" and a.ORDER_REGEDIT_NO =?");
     params.add(ORDER_REGEDIT_NO);
     sqlsb.append(" and (a.DELIVERY_TIME = ? or a.DELIVERY_TIME is null ) ");
     params.add(this.stampToDate(DELIVERY_TIME));
     	
     dataDetail=DAOUtil.findAll(sqlsb.toString(),params);
      return dataDetail;
  }   
 
 //将时间戳转换为日期格式
 public  String stampToDate(String s){
     String res;
     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     long lt = new Long(s);
     Date date = new Date(lt);
     res = simpleDateFormat.format(date);
     return res;
   
 }
 
 
 /**
  * excel导出方法
  * 
* @author zhengcong
* @date 2017年4月5日
  */
 @Override
 public List<Map> queryToExport(Map<String, String> queryParam) throws ServiceBizException {

	 return dataDetail;
     
 } 
 
 
	/**
	 * 根据CODE作废
  * @author zhengcong
  * @date 2017年4月5日
	 */

	@Override
	public void cancelByCode(String ORDER_REGEDIT_NO,String DELIVERY_TIME,PartOrderSignDTO ctdto) throws ServiceBizException{
		TtPtDeliverPO ctpo=TtPtDeliverPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),ORDER_REGEDIT_NO,this.stampToDate(DELIVERY_TIME));
		ctpo.setInteger("IS_VALID",DictCodeConstants.STATUS_IS_NOT );   
		ctpo.saveIt();
	} 

//public String getExcelSql(List<Object> partOrderSignSql,Long id){
//	 StringBuilder sb=new StringBuilder("select tpdi.ITEM_ID,tpdi.DELIVER_ID,tpdi.DEALER_CODE,tpdi.OEM_ORDER_NO,tpdi.PART_NO,tpdi.PART_NAME,tpdi.COUNT,tpdi.SUPPLY_QTY,tpdi.LJ_SIGN_COUNT,");
//   sb.append("tpdi.THIS_TIME_SIGN_COUNT,tpdi.IN_QUANTITY_HAVE,tpdi.AMOUNT,tpdi.TAXED_AMOUNT,tpdi.REMARK,tpdi.PLAN_PRICE  ");
//   sb.append( " from tt_pt_deliver_item tpdi where 1=1  and tpdi.DELIVER_ID = ?"); 
//
//   partOrderSignSql.add(id);
//   return sb.toString();
//}     
    
//    /**
//     * 查询
//    * @author zhongsw
//    * @date 2016年8月29日
//    * @param queryParam
//    * @return
//    * @throws ServiceBizException(non-Javadoc)
//    * @see com.yonyou.dms.part.service.basedata.PartOrderSignService#partOrderSignSQL(java.util.Map)
//    */
//    @Override
//    public PageInfoDto partOrderSignSQL(Map<String, String> queryParam) throws ServiceBizException{
//        StringBuilder sb=new StringBuilder("SELECT tpd.DELIVER_ID,tpd.DEALER_CODE,tpd.DELIVERY_NO,tpd.DELIVERY_TIME,tpd.DELIVERY_STATUS as DELIVERY_STATUS_SHOW,tpd.IS_VERIFICATION, tpd.IS_VALID as IS_VALID_SHOW,tpd.STOCK_IN_NO,tpd.RECEIVABLE_CASES,tpd.FACT_CASES,tpd.REMARK,tpd.DELIVERY_PDC,tpd.DELIVERY_COMPANY,tpd.RECORD_VERSION FROM tt_pt_deliver tpd WHERE  1 = 1");      
//        
//        List<Object> partOrderSignSql=new ArrayList<Object>();
//                
//        if(!StringUtils.isNullOrEmpty(queryParam.get("delivery_no"))){
//            sb.append("  and DELIVERY_NO like ?");
//            partOrderSignSql.add("%"+queryParam.get("delivery_no")+"%");
//        }
//        if(!StringUtils.isNullOrEmpty(queryParam.get("delivery_status"))){
//            sb.append("  and DELIVERY_STATUS = ?");
//            partOrderSignSql.add(Integer.parseInt(queryParam.get("delivery_status")));
//        }
//        if(!StringUtils.isNullOrEmpty(queryParam.get("is_valid"))){
//            sb.append("  and IS_VALID = ?");
//            partOrderSignSql.add(Integer.parseInt(queryParam.get("is_valid")));
//        }
//        if(!StringUtils.isNullOrEmpty(queryParam.get("is_verification"))){
//            sb.append("  and IS_VERIFICATION = ?");
//            partOrderSignSql.add(Integer.parseInt(queryParam.get("is_verification")));
//        }
//        if(!StringUtils.isNullOrEmpty(queryParam.get("delivery_time_from"))){
//            sb.append("  and DELIVERY_TIME >= ?");
//            partOrderSignSql.add(DateUtil.parseDefaultDate(queryParam.get("delivery_time_from")));
//        }
//        if(!StringUtils.isNullOrEmpty(queryParam.get("delivery_time_to"))){
//            sb.append("  and DELIVERY_TIME < ?");
//            partOrderSignSql.add(DateUtil.addOneDay(queryParam.get("delivery_time_to")));
//        }
//        PageInfoDto id=DAOUtil.pageQuery(sb.toString(), partOrderSignSql);
//        return id;
//    }
//    
//    /**
//     * 修改签收数量
//    * @author zhongsw
//    * @date 2016年8月2日
//    * @param id
//    * @param partOrderSignDTO
//    * @param partOrderSignDTO(non-Javadoc)
//    * @see com.yonyou.dms.part.service.basedata.PartOrderSignService#updatePartOrderSign(java.lang.Long, com.yonyou.dms.part.domains.DTO.basedata.PartOrderSignDTO)
//    */
//    @Override
//    public void updatePartOrderSign(Long id, PartOrderSignDTO partOrderSignDTO) throws ServiceBizException{
//      //修改子表字段信息重新赋值
//        PartOrderSignPO wtp=PartOrderSignPO.findById(id);
//        wtp.setString("delivery_no", partOrderSignDTO.getDelivery_no());
//        wtp.setString("delivery_company", partOrderSignDTO.getDelivery_company());
//        wtp.setDouble("receivable_cases", partOrderSignDTO.getReceivable_cases());
//        wtp.setDouble("fact_cases", partOrderSignDTO.getFact_cases());
//        wtp.setString("delivery_pdc", partOrderSignDTO.getDelivery_pdc());
//        //wtp.saveIt();
//        for (PartOrderSignItemDTO dto : partOrderSignDTO.getPartordersignitemlist()) {
//            PartOrderSignItemPO dpp =PartOrderSignItemPO.findById(dto.getItem_id());
//            int i= NumberUtil.compareTo(NumberUtil.add2Double(dto.getThis_time_sign_count(),dto.getLj_sign_count()),dto.getSupply_qty());
//            if(i!=1){
//               dpp.setDouble("LJ_SIGN_COUNT", dto.getThis_time_sign_count()+dto.getLj_sign_count());
//            }else {
//                throw new ServiceBizException("本次签收数量+累计签收数量不能大于供应数量！");
//            }
//            dpp.saveIt();
//        }
//        //更新发运单状态（部分签收、全部签收）
//        StringBuilder sb=new StringBuilder("SELECT t.DEALER_CODE,t.SUPPLY_QTY,t.LJ_SIGN_COUNT from TT_PT_DELIVER_ITEM t where t.DELIVER_ID=?");
//        List<Object> param=new ArrayList<Object>();
//        param.add(id);
//        List<Map> result=DAOUtil.findAll(sb.toString(), param);
//        boolean isOk=true;
//        if(!CommonUtils.isNullOrEmpty(result)){
//        	for(int i=0;i<result.size();i++){
//        		Map resultMap=result.get(i);
//        		int j=NumberUtil.compareTo(Double.parseDouble(resultMap.get("SUPPLY_QTY")+""),Double.parseDouble(resultMap.get("LJ_SIGN_COUNT")+""));
//        		if(j!=0){
//        			isOk=false;
//        			break;
//        		}
//        	}
//        }
//        //全部签收
//        if(isOk){
//        	wtp.setInteger("DELIVERY_STATUS", DictCodeConstants.DELIVERY_STATUS_ALL);
//        //部分签收	
//        }else{
//        	wtp.setInteger("DELIVERY_STATUS", DictCodeConstants.DELIVERY_STATUS_PORTION);
//        }
//        wtp.saveIt();
//    }
//    
//    /**
//     * 修改发运单状态
//     * @author zhongsw
//     * @date 2016年8月2日
//     * @param id
//     * @param partOrderSignDTO
//     * @param partOrderSignDTO(non-Javadoc)
//     * @see com.yonyou.dms.part.service.basedata.PartOrderSignService#updatePartOrderSign(java.lang.Long, com.yonyou.dms.part.domains.DTO.basedata.PartOrderSignDTO)
//     */
//     @Override
//     public void updateOrderStatus(Long id, PartOrderSignDTO partOrderSignDTO) throws ServiceBizException{
//         PartOrderSignPO wtp=PartOrderSignPO.findById(id);
//         wtp.setInteger("is_valid", DictCodeConstants.STATUS_IS_NOT);
//         wtp.saveIt();
//     }
//     
//    /**
//     *  修改核销状态
//    * @author zhongsw
//    * @date 2016年8月29日
//    * @param id
//    * @param partOrderSignDTO
//    * @throws ServiceBizException(non-Javadoc)
//    * @see com.yonyou.dms.part.service.basedata.PartOrderSignService#updateHXStatu(java.lang.Long, com.yonyou.dms.part.domains.DTO.basedata.PartOrderSignDTO)
//    */
//    public void updateHXStatu(Long id, PartOrderSignDTO partOrderSignDTO) throws ServiceBizException{
//          PartOrderSignPO wtp=PartOrderSignPO.findById(id);
//          updateHXStatus(wtp,partOrderSignDTO);
//          wtp.saveIt();
//      }
//    
//    /**
//     * 根据ID查询
//    * @author zhongsw
//    * @date 2016年8月29日
//    * @param id
//    * @return
//    * @throws ServiceBizException
//    * (non-Javadoc)
//    * @see com.yonyou.dms.part.service.basedata.PartOrderSignService#findById(java.lang.Long)
//    */
//    @Override
//    public Map findById(Long id) throws ServiceBizException{//查询字段不能用*
//        String sql="select DELIVER_ID,DEALER_CODE,DELIVERY_NO,DELIVERY_TIME,DELIVERY_STATUS,IS_VERIFICATION,IS_VALID,STOCK_IN_NO,RECEIVABLE_CASES,FACT_CASES,REMARK,DELIVERY_PDC,DELIVERY_COMPANY from TT_PT_DELIVER where DELIVER_ID = ? ";
//        List<Object> param=new ArrayList<Object>();
//        param.add(id);
//        return DAOUtil.findFirst(sql, param);
//    }
//
///**
// * 导出货运单明细界面信息
// * @author zhongsw
// * @date 2016年7月20日
// * @param queryParam
// * @return
// * @throws ServiceBizException(non-Javadoc)
// * @see com.yonyou.dms.manage.service.sample.UserService#queryUsersForExport(java.util.Map)
// */
// @Override
// public List<Map> queryUsersForExport(Long id,Map<String, String> queryParam) throws ServiceBizException {
//     List<Object> params = new ArrayList<Object>();
//     String sql = getExcelSql(params,id);
//     List<Map> resultList = DAOUtil.findAll(sql,params);
//     return resultList;
// }
// 
// /**
//  * 导出
// * @author zhongsw
// * @date 2016年7月20日
// * @param queryParam
// * @param params
// * @return
//  */
// private String getQuerySql(Map<String,String> queryParam,List<Object> params) throws ServiceBizException{
//     StringBuilder sqlSb = new StringBuilder("select tpdi.ITEM_ID,tpdi.DELIVER_ID,tpdi.DEALER_CODE,tpdi.OEM_ORDER_NO,tpdi.PART_NO,tpdi.PART_NAME,tpdi.COUNT,tpdi.SUPPLY_QTY,tpdi.THIS_TIME_SIGN_COUNT,tpdi.IN_QUANTITY_HAVE,tpdi.AMOUNT,tpdi.TAXED_AMOUNT,tpdi.REMARK from TT_PT_DELIVER_ITEM tpdi where 1=1 ");
//     return sqlSb.toString();
// }
//
///**
// * 货运单签收界面查询
//* @author zhongsw
//* @date 2016年8月2日
//* @param id
//* @return
//* @throws ServiceBizException(non-Javadoc)
//* @see com.yonyou.dms.part.service.basedata.PartOrderSignService#partOrderSignItem(java.util.Map)
//*/
//	
// @Override
// public PageInfoDto partOrderSignItem(Long id) throws ServiceBizException {
//     StringBuilder sb=new StringBuilder("select ITEM_ID,DELIVER_ID,DEALER_CODE,OEM_ORDER_NO as oem_order_noShow,PART_NO as part_noShow,PART_NAME as part_nameShow,COUNT as countShow,SUPPLY_QTY as supply_qtyShow,LJ_SIGN_COUNT as lj_sign_countShow,");
//             sb.append("IN_QUANTITY_HAVE as in_quantity_haveShow,THIS_TIME_SIGN_COUNT as this_time_sign_countShow,AMOUNT as amountShow,TAXED_AMOUNT as taxed_amountShow,REMARK ");
//             sb.append( " from tt_pt_deliver_item where 1=1  and DELIVER_ID = ?"); 
//     List<Object> partOrderSignSql=new ArrayList<Object>();
//     partOrderSignSql.add(id);
//     return DAOUtil.pageQuery(sb.toString(), partOrderSignSql);
// }
// 
///**
// * 货运单明细界面查询
//* @author zhongsw
//* @date 2016年8月10日
//* @param id
//* @return
//* @throws ServiceBizException (non-Javadoc)
//* @see com.yonyou.dms.part.service.basedata.PartOrderSignService#partOrderSignItem2(java.lang.Long)
//*/
//	
//@Override
// public List<Map> partOrderSignItem2(Long id) throws ServiceBizException {
//	List<Object> partOrderSignSql=new ArrayList<Object>();
//     List<Map> pageInfo=DAOUtil.findAll(getExcelSql(partOrderSignSql,id), partOrderSignSql);
//     return pageInfo;
// }
//
///**
// * 导出和明细sql
// * @author zhongsw
// * @date 2016年8月10日
// * @param partOrderSignSql
// * @param id
// * @return
// */
// public String getExcelSql(List<Object> partOrderSignSql,Long id){
//	 StringBuilder sb=new StringBuilder("select tpdi.ITEM_ID,tpdi.DELIVER_ID,tpdi.DEALER_CODE,tpdi.OEM_ORDER_NO,tpdi.PART_NO,tpdi.PART_NAME,tpdi.COUNT,tpdi.SUPPLY_QTY,tpdi.LJ_SIGN_COUNT,");
//     sb.append("tpdi.THIS_TIME_SIGN_COUNT,tpdi.IN_QUANTITY_HAVE,tpdi.AMOUNT,tpdi.TAXED_AMOUNT,tpdi.REMARK,tpdi.PLAN_PRICE  ");
//     sb.append( " from tt_pt_deliver_item tpdi where 1=1  and tpdi.DELIVER_ID = ?"); 
//
//     partOrderSignSql.add(id);
//     return sb.toString();
// } 
//
///**
// * 采购入库查询货运单
//*  @author xukl
//* @date 2016年8月8日
//* @param queryParam
//* @return
//* @throws ServiceBizException(non-Javadoc)
//* @see com.yonyou.dms.part.service.basedata.PartOrderSignService#qryPartOrderSign(java.util.Map)
//*/
//	
//@Override
// public PageInfoDto qryPartOrderSign(Map<String, String> queryParam) throws ServiceBizException{
//        StringBuilder sb = new StringBuilder("SELECT\n")
//        .append("  tt.DELIVER_ID,\n")
//        .append("  tt.DEALER_CODE,\n")
//        .append("  tt.DELIVERY_NO,\n")
//        .append("  tt.DELIVERY_STATUS,\n")
//        .append("  tt.DELIVERY_TIME,\n")
//        .append("  tt.RECEIVABLE_CASES,\n")
//        .append("  tt.FACT_CASES,\n")
//        .append("  tt.REMARK\n")
//        .append("FROM\n")
//        .append("  tt_pt_deliver tt\n")
//        .append("WHERE\n")
//        .append("  tt.IS_VERIFICATION = '10041002'\n")
//        .append("AND tt.IS_VALID = '10041001'\n")
//        .append("AND (tt.DELIVERY_STATUS = '13061001' or  tt.DELIVERY_STATUS = '13061003') \n");
//     List<Object> partOrderSignSql=new ArrayList<Object>();
//     
//     if(!StringUtils.isNullOrEmpty(queryParam.get("delivery_no"))){
//         sb.append("  and tt.DELIVERY_NO like ?");
//         partOrderSignSql.add("%"+queryParam.get("delivery_no")+"%");
//     }
//     if(!StringUtils.isNullOrEmpty(queryParam.get("delivery_status"))){
//         sb.append("  and tt.DELIVERY_STATUS = ?");
//         partOrderSignSql.add(queryParam.get("delivery_status"));
//     }
//     if(!StringUtils.isNullOrEmpty(queryParam.get("delivery_time_from"))){
//         sb.append("  and tt.DELIVERY_TIME >= ?");
//         partOrderSignSql.add("%"+queryParam.get("delivery_time_from")+"%");
//     }
//     if(!StringUtils.isNullOrEmpty(queryParam.get("delivery_time_to"))){
//         sb.append("  and tt.DELIVERY_TIME < ?");
//         partOrderSignSql.add(DateUtil.addOneDay(queryParam.get("delivery_time_to")));
//     }
//     PageInfoDto pageInfoDto=DAOUtil.pageQuery(sb.toString(), partOrderSignSql);
//     return pageInfoDto;
// }
///**
// * （采购入库用）查询货运单明细
//* @author xukl
//* @date 2016年8月4日
//* @param id
//* @return
//* @throws ServiceBizException(non-Javadoc)
//* @see com.yonyou.dms.part.service.basedata.PartOrderSignService#qryOrderSignItemBuy(java.lang.Long)
//*/
//@Override
// public List<Map> qryOrderSignItemBuy(Long id) throws ServiceBizException {
//	 BasicParametersDTO bpDto=systemParamService.queryBasicParameterByTypeandCode(SystemParamConstants.PARAM_TYPE_BASICOM, SystemParamConstants.BASICOM_CODE);
//        StringBuilder sb = new StringBuilder("SELECT\n")
//        .append("tpdi.ITEM_ID,\n")
//        .append("tpdi.DELIVER_ID,\n")
//        .append("tpdi.DEALER_CODE,\n")
//        .append("tpdi.PART_NO as PART_CODE,\n")
//        .append("tpdi.PART_NAME,\n")
//        .append("tpdi.PLAN_PRICE as PRICE,\n")
//        .append("tpi.UNIT,\n")
//        .append("tpdi.LJ_SIGN_COUNT-tpdi.IN_QUANTITY_HAVE as INQUANTITY,\n")
//        .append("tpdi.PLAN_PRICE*(tpdi.LJ_SIGN_COUNT-tpdi.IN_QUANTITY_HAVE) AMOUNT,\n")
//        .append("tpdi.PLAN_PRICE*(1+"+new Double(bpDto.getParamValue())+")*(tpdi.LJ_SIGN_COUNT-tpdi.IN_QUANTITY_HAVE) as TAXEDAMOUNT,\n")
//        .append("'13051001' as FROMTYPE,\n")
//        .append("(tpdi.PLAN_PRICE*(1+"+new Double(bpDto.getParamValue())+")) as PRICETAXED\n")
//        .append("FROM\n")
//        .append("tt_pt_deliver_item tpdi\n")
//        .append("left join tm_part_info tpi on tpdi.PART_NO=tpi.PART_CODE and tpdi.DEALER_CODE=tpi.DEALER_CODE\n")
//        .append("where 1=1 and tpdi.DELIVER_ID = ? and tpdi.LJ_SIGN_COUNT-tpdi.IN_QUANTITY_HAVE>0\n");
//     List<Object> partOrderSignSql=new ArrayList<Object>();
//     partOrderSignSql.add(id);
//     List<Map> list=DAOUtil.findAll(sb.toString(), partOrderSignSql);
//     return list;
// }
//
//
///**
// * 货运单作废功能
//* @author zhongsw
//* @date 2016年8月10日
//* @param lap
//* @param partOrderSigndto
//* @throws ServiceBizException
//*/
//	
//public void updateOrderStatus(PartOrderSignPO lap,PartOrderSignDTO partOrderSigndto) throws ServiceBizException {
//    //if(DictCodeConstants.STATUS_IS_VALID==Integer.parseInt(String.valueOf(lap.get("is_valid").toString()))){
//        lap.setInteger("is_valid", 10011002);
//    //}else{
//       // throw new ServiceBizException("操作错误！");
//    //}
//}
//
///**
// * 货运单核销功能
//* @author zhongsw
//* @date 2016年8月10日
//* @param lap
//* @param partOrderSigndto
//* @throws ServiceBizException
//*/
//public void updateHXStatus(PartOrderSignPO lap, PartOrderSignDTO partOrderSigndto) throws ServiceBizException {
//    //if(DictCodeConstants.STATUS_IS_NOT==Integer.parseInt(String.valueOf(lap.get("is_verification")))){
//        lap.setInteger("is_verification", 10041001);
//   // }else{
//        //throw new ServiceBizException("操作错误！");
//    //}
//    
//}


}