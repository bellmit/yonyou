package com.yonyou.dms.part.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TmpPtOrderDetailDcsPO;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TmpPtPartWbpDcsPO;

/**
 * 配件订货计划Dao
 * @author ZhaoZ
 * @date 2017年3月24日
 */
@Repository
public class PartOrderDLRDao  extends OemBaseDAO{

	/**
	 * 配件订货计划查询信
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto getOrderPlanInfo(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOrderPlanInfoSql(queryParams, params);
		return OemDAOUtil.pageQuery(sql, params);
	}
	
	/**
	 * 配件订货计划查询信SQL
	 * @param queryParams
	 * @return
	 */
	private String getOrderPlanInfoSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		System.out.println(loginUser.getDealerId());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT   \n");
		sql.append("      TPO.ORDER_ID,    \n");
		sql.append("      TPO.ORDER_NO,    \n");
		sql.append("      TPO.ORDER_TYPE,  \n");
		sql.append("      TPO.VIN,   \n");
		sql.append("      DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') CREATE_DATE,   \n");
		sql.append("      DATE_FORMAT(TPO.UPDATE_DATE,'%Y-%m-%d') UPDATE_DATE,   \n");
		sql.append("      TPO.ORDER_STATUS \n");
		sql.append("FROM TT_PT_ORDER_dcs TPO   \n");
		sql.append(" WHERE TPO.DEALER_ID = "+loginUser.getDealerId()+" \n");
		sql.append("      AND TPO.ORDER_STATUS in ("+OemDictCodeConstants.PART_ORDER_STATUS_02+","+OemDictCodeConstants.PART_ORDER_STATUS_05+") \n");
		sql.append("      AND TPO.IS_DEL = "+OemDictCodeConstants.IS_DEL_00+" \n");
		//订单号
        if(!StringUtils.isNullOrEmpty(queryParams.get("orderNo"))){ 
        	sql.append(" 					AND TPO.ORDER_NO LIKE ? \n");
       		params.add("%"+queryParams.get("orderNo")+"%");
        }
       //订单类型
       if(!StringUtils.isNullOrEmpty(queryParams.get("orderType"))){ 
    	    sql.append(" 					AND TPO.ORDER_TYPE = ? \n");
        	params.add(queryParams.get("orderType"));
       }
       //订单状态
       if(!StringUtils.isNullOrEmpty(queryParams.get("orderStatus"))){ 
    	    sql.append(" 					AND TPO.ORDER_STATUS = ? \n");
        	params.add(queryParams.get("orderStatus"));
       }
	return sql.toString();
		
		
	}

	/**
	 * 查询配件清单
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto getOrderList(Map<String, String> queryParams,String partCodes) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TPPB.PART_CODE,TPPB.PART_NAME,t.ORDER_NUM,TPPB.PART_MDOEL,TPPB.REPORT_TYPE,TPPB.IS_MJV,TPPB.IS_MOP FROM TT_PT_PART_BASE_dcs TPPB,TT_PT_ORDER_DETAIL_DCS T  \n");
		sql.append(" WHERE  \n");
		sql.append(" 1=1  \n");
		sql.append(" AND TPPB.PART_CODE = T.PART_CODE \n");
		sql.append(" AND TPPB.PART_STATUS ="+OemDictCodeConstants.STATUS_ENABLE+" \n");
		 if(!StringUtils.isNullOrEmpty(queryParams.get("partCode"))){ 
			 sql.append("AND TPPB.PART_CODE LIKE ? \n");
	         params.add("%"+queryParams.get("partCode")+"%");
	       }
		 if(!StringUtils.isNullOrEmpty(partCodes)){ 
			 String[] partArr = partCodes.split(",");
				String strParts = "'";
				for (String str : partArr) {
					strParts += str + "','";
				}
				strParts = strParts.substring(0, strParts.length()-2);
				sql.append("AND TPPB.PART_CODE NOT IN (?) \n");
				params.add(strParts);
	       }
		 if(!StringUtils.isNullOrEmpty(queryParams.get("partName"))){ 
			 sql.append("AND TPPB.PART_NAME LIKE ? \n");
	         params.add("%"+queryParams.get("partName")+"%");
	       }
		 String partModel = "";
		 if ("".equals(queryParams.get("partModel"))) {//全部选择，就选择自动带出来的
				partModel = CommonUtils.checkNull(queryParams.get("partModels"));
			}else{
				partModel  = queryParams.get("partModel");
			}
		 if(!StringUtils.isNullOrEmpty(partModel)){ 
			 sql.append("AND TPPB.PART_MDOEL IN (?) \n");
	         params.add(partModel);
	       }
		
		if (!StringUtils.isNullOrEmpty(queryParams.get("sjv"))) {
			sql.append(" AND (TPPB.PART_MDOEL IN ("+partModel+") \n");
			sql.append(" OR (TPPB.IS_MJV = "+OemDictCodeConstants.IF_TYPE_NO+" \n");
			sql.append(" AND TPPB.IS_MOP = "+OemDictCodeConstants.IF_TYPE_YES+") )\n");
		}
		return OemDAOUtil.pageQuery(sql.toString(), params);

	}

	/**
	 * 配件订单补登记查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto getInvoice(Map<String, String> queryParams) {
		return new PageInfoDto();
//		try {
//			act.getResponse().setContentType("text/html; charset=utf-8");
//			String status = CommonUtils.checkNull(request.getParamValue("status"));//登记状态
//			String Status = "";
//			if(status.equals(Constant.IF_TYPE_YES.toString())){
//				Status="已登记";
//			}else if(status.equals(Constant.IF_TYPE_NO.toString())){
//				Status="未登记";
//			}
//			String ivbeln = request.getParamValue("ivbeln");//SAP订单号
//			String IVbeln = ivbeln;
//			String iAuart = CommonUtils.checkNull(request.getParamValue("orderType"));//订单类型
//			String IAuart = commonDao.getOrderTypeByIauart(iAuart);
//			String iOrdstatus = request.getParamValue("orderStatus");//开票状态
//			String IOrdstatus = iOrdstatus;
//			String invoiceStartDate = request.getParamValue("invoiceStartDate");//开票日期   开始
//			String invoiceEndDate = request.getParamValue("invoiceEndDate");//开票日期   结束
//			SEDCSP07 p07=new SEDCSP07();
//			SEDCSP07VO vo=new SEDCSP07VO();
//			vo.setIzzcliente(logonUser.getCompanyId().toString());
//			vo.setIdealerUsr(logonUser.getUserId().toString());
//			vo.setIvbeln(IVbeln);
//			vo.setLow(DateUtil.yyyy_MM_dd2Date(invoiceStartDate));
//			vo.setHigh(DateUtil.yyyy_MM_dd2Date(invoiceEndDate));
//			vo.setIauart(IAuart);
//			vo.setIordstatus(IOrdstatus);
//			List<SEDCSP07VO> listVo= p07.querySedcsP07VOByDCS(vo);
//			PageResult<Map<String, Object>> ps = new PageResult<Map<String, Object>>();
//			if (listVo != null && listVo.size() > 0) {
//				LinkedList<Map<String,Object>> t1 = new LinkedList<Map<String,Object>>();
//				for (int i = 0;i<listVo.size();i++) {
//					SEDCSP07VO out = listVo.get(i);
//					if(out.getVbeln()==null || out.getVbeln().equals("")){
//						continue;
//					}
//					Map<String, Object> map = new HashMap<String, Object>();
//					map.put("OP_SAP_ORDER_NO", out.getVbeln());//订单编号
//					map.put("OP_INVOICE_STATUS", commonDao.getInvoiceStatusBySap(out.getOrderStatus()));//订单开票状态
//					map.put("OP_ORDER_TYPE", commonDao.getOrderTypeBySap(out.getAuart()));//订单类型
//					map.put("OP_ORDER_DATE", out.getAudat());//订单日期
//					String sapOrderNO=dao.getOrderBySAPOrderNO(out.getVbeln());
//					logger.info("====sapOrderNO====:"+sapOrderNO);
//					if(sapOrderNO.equals("")){
//						map.put("OP_STATUS", "未登记");//登记状态
//					}else{
//						map.put("OP_STATUS", "已登记");//登记状态
//					}
//					if(Status!=null && !"".equals(Status)){
//						if(Status.equals(map.get("OP_STATUS"))){
//							t1.addLast(map);
//						}
//					}else{
//						t1.addLast(map);
//					}
//				}
//				ps.setRecords(t1);	
//			}
//			
//			/*PageResult<Map<String, Object>> ps = new PageResult<Map<String, Object>>();
//			LinkedList<Map<String,Object>> t1 = new LinkedList<Map<String,Object>>();
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("OP_SAP_ORDER_NO", "123");//订单编号
//			map.put("OP_INVOICE_STATUS", "123");//订单开票状态
//			map.put("OP_ORDER_TYPE", "123");//订单类型
//			map.put("OP_ORDER_DATE", "123");//订单日期
//			map.put("OP_STATUS", "未登记");//登记状态
//			t1.addLast(map);
//			ps.setRecords(t1);*/
//			
//			act.setOutData("ps", ps);
//		} catch (Exception e) {
//			BizException e1 = new BizException(act, e, "配件订单补登记查询异常", "配件订单补登记查询异常");
//			logger.error(logonUser, e);
//			act.setException(e1);
//		}
	}

	/**
	 * (根据经销商ID取国产车dealer_id 小区id 大区id)
	 * @param dealerId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getDealerInfoByDealerID(Long dealerId) {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TM.DEALER_ID,TM.DEALER_CODE, \n");
		sql.append("       TM.DEALER_SHORTNAME,TOR2.ORG_DESC BIG_ORG_NAME,TOR3.ORG_DESC ORG_NAME, \n");
		sql.append("	   TOR3.PARENT_ORG_ID AS BIG_ORG_ID ,TOR3.ORG_ID AS SMALL_ORG_ID \n");
		sql.append("FROM TM_DEALER TM ,TM_DEALER_ORG_RELATION TDOR ,TM_ORG  TOR3 ,TM_ORG TOR2 \n");
		sql.append("WHERE TDOR.DEALER_ID = TM.DEALER_ID \n");
		sql.append("AND (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3 ) \n");
		sql.append("AND (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 ) \n");
		sql.append("AND TOR3.BUSS_TYPE ="+OemDictCodeConstants.ORG_BUSS_TYPE_02+" \n");
		sql.append("AND TOR2.BUSS_TYPE ="+OemDictCodeConstants.ORG_BUSS_TYPE_02+" \n");
		sql.append("AND TM.DEALER_ID = "+dealerId+" \n");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}

	/**
	 * 得到补登记订单号
	 * @param dealerCode
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getOrderNoByRe(String dealerCode) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ifnull(MAX(SUBSTR(TPO.ORDER_NO,9,4)),0000) ORDER_NO from TT_PT_ORDER_dcs TPO ");
		sql.append(" WHERE TPO.ORDER_NO LIKE 'RO%'  ");
		sql.append(" AND DATE_FORMAT(TPO.CREATE_DATE,'%Y-%m-%d') >='"+DateUtil.formatDefaultDate(new Date())+"'\n");
		Map<String,Object> map = OemDAOUtil.findAll(sql.toString(), null).get(0);
		String lastFour = (new Long(map.get("ORDER_NO").toString()) + 1 ) + "";
		String orderNo = "RO";
		Calendar calendar = Calendar.getInstance();	// 得到日历
		String year = (calendar.get(Calendar.YEAR)+"").substring(2, 4);
		String month = (calendar.get(Calendar.MONTH) + 1)+"";
		if (month.length()==1) {
			month = "0"+month;
		}
		String day = calendar.get(Calendar.DAY_OF_MONTH)+"";
		if (day.length()==1) {
			day = "0"+day;
		}
		orderNo = orderNo + year + month + day;
		if (lastFour.length()==1) {
			orderNo = orderNo + "000" + lastFour;
		} else if (lastFour.length()==2) {
			orderNo = orderNo + "00" + lastFour;
		} else if (lastFour.length()==3) {
			orderNo = orderNo + "0" + lastFour;
		} else {
			orderNo = orderNo + lastFour;
		}
		return orderNo + dealerCode;
	}

	public String getDeliverNo() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ifnull(MAX(TPD.DELIVER_NO),'0070000000') DELIVER_NO from TT_PT_DELIVER_dcs TPD ");
		sb.append(" WHERE TPD.DELIVER_NO like '007%'");
		Map map = OemDAOUtil.findFirst(sb.toString(),null);
		long maxDeliverNo = new Long(map.get("DELIVER_NO").toString())+1;
		return "00"+maxDeliverNo;
	}

	/**
	 * 数据校验
	 * @param poList
	 * @return
	 */
	public List<Map> checkData2(LazyList<TmpPtOrderDetailDcsPO> poList, String partModel,String partCodes) {
		List<Map> errList = new ArrayList<Map>();
		List<String> partCodeList = new ArrayList<String>();
		for(TmpPtOrderDetailDcsPO po:poList){
			//判断计划是否是数字，如果不是默认为1
			if(!StringUtils.isNullOrEmpty(po.getString("ORDER_NUM"))){
				if(!po.getString("ORDER_NUM").matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")) {
					po.setString("ORDER_NUM","1");
					po.saveIt();
				}
			}
				List<Object> params = new ArrayList<Object>();
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT TPPB.PART_CODE FROM TT_PT_PART_BASE_dcs TPPB \n");
				sql.append(" WHERE  \n");
				sql.append(" 1=1  \n");
				sql.append(" AND TPPB.PART_STATUS ="+OemDictCodeConstants.STATUS_ENABLE+" \n");
				if (!StringUtils.isNullOrEmpty(po.getString("PART_CODE"))) {
					sql.append("AND TPPB.PART_CODE LIKE ? \n");
					params.add("%"+po.getString("PART_CODE")+"%");
				}
				List<Map> partList = OemDAOUtil.findAll(sql.toString(), params);
				boolean flagPartCode = true;
				if (null == partList||partList.size() == 0 || partList.size() < 0) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("ROW_NUM", po.getString("LINE_NO"));
					map.put("ERROR", "该配件在系统中没有找到!");
					errList.add(map);
					flagPartCode = false;
				}
				if(flagPartCode){
					StringBuffer sql1 = new StringBuffer();
					sql1.append("SELECT TPPB.PART_CODE FROM TT_PT_PART_BASE_dcs TPPB \n");
					sql1.append(" WHERE  \n");
					sql1.append(" 1=1  \n");
					sql1.append(" AND TPPB.PART_STATUS ="+OemDictCodeConstants.STATUS_ENABLE+" \n");
					if (!StringUtils.isNullOrEmpty(po.getString("PART_CODE"))) {
						sql1.append("AND TPPB.PART_CODE LIKE '%"+po.getString("PART_CODE")+"%' \n");
					}
					if (!StringUtils.isNullOrEmpty(partModel)) {
						sql1.append("AND TPPB.PART_MDOEL IN ("+partModel+") \n");
					}
					List<Map> partList1 = OemDAOUtil.findAll(sql1.toString(), null);
					if (null == partList1||partList1.size() == 0 || partList1.size() < 0) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("ROW_NUM", po.getString("LINE_NO"));
						map.put("ERROR", "该配件不符合该订单类型!");
						errList.add(map);
						flagPartCode = false;
					}
					
				} 
			
				if (flagPartCode) {
					if(partCodeList.contains(po.getString("PART_CODE"))) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("ROW_NUM", po.getString("LINE_NO"));
						map.put("ERROR", "导入配件有重复!");
						errList.add(map);
						flagPartCode = false;
					}
				}
				
				if (flagPartCode && partCodes!=null && partCodes!="") {
					if (partCodes.contains(po.getString("PART_CODE"))) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("ROW_NUM", po.getString("LINE_NO"));
						map.put("ERROR", "导入配件已经在配件显示列表中!");
						errList.add(map);
						flagPartCode = false;
					}
				}
			partCodeList.add(po.getString("PART_CODE"));
		}
		return errList;
	}
}
