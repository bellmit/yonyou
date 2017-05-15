package com.yonyou.dms.vehicle.controller.afterSales.warranty;

import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.service.afterSales.warranty.WarrantyQueryOEMService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 保修单管理OEM
 * @author zhanghongyi
 */
@Controller
@TxnConn
@RequestMapping("/warrantyQueryOEM")
public class WarrantyQueryOEMController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	WarrantyQueryOEMService  warrantyQueryOEMService;
	
	/**
	 * 保修单查询
	 */
	@RequestMapping(value = "/warrantySearch", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto warrantySearch(@RequestParam Map<String, String> queryParam) {
		logger.info("==========保修单查询==========");
		PageInfoDto pageInfoDto = warrantyQueryOEMService.WarrantyQuery(queryParam);
		return pageInfoDto;
	}

 	 /**
	  * 保修单明细
	  */
	@RequestMapping(value="/warrantyDetailInfo/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> warrantyDetailInfo(@PathVariable(value = "id") BigDecimal id) {
		logger.info("==========保修单明细==========");
		Map<String, Object> mapA = warrantyQueryOEMService.WarrantyDetailInfoById(id);
		return mapA;
	}
	
 	/**
	 * 保修单故障清单
	 */
	@RequestMapping(value="/queryWarrantyDetail/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryWarrantyDetail(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====保修单故障清单=====");
		 StringBuilder sql = new StringBuilder();
		 sql.append("select t.ID,t.WT_CODE,t.BUG_CODE,t.BUG_NAME,t.ALL_WORK_HOURS,t.ALL_WORK_FEE,t.ALL_MATERIAL_FEE, \n");
		 sql.append("t.ALL_WORK_FEE+t.ALL_MATERIAL_FEE ALL_FEE \n");
		 sql.append("from tt_wr_wc_detail_dcs t where t.WC_ID = "+id+" \n");
		 return OemDAOUtil.pageQuery(sql.toString(), null);
	}
	
 	/**
	 * 保修单审核历史
	 */
	@RequestMapping(value="/queryWarrantyConfirm/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryWarrantyConfirm(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====保修单审核历史=====");
		 StringBuilder sql = new StringBuilder();
		 sql.append("select t.ID,t.CONFIRM_DATE,t.CONFIRM_BY,t.CONFIRM_STATUS,t.ERR_CODE,t.ERR_NAME \n");
		 sql.append("from tt_wr_wc_confirm_his_dcs t where t.WC_ID = "+id+" \n");
		 return OemDAOUtil.pageQuery(sql.toString(), null);
	}
	
	/**
	 * 保修单附件
	 */
	@RequestMapping(value="/queryWarrantyAtt/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryPartFuJian(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====附件信息=====");
		String sql = "SELECT * FROM tt_wr_wc_att_dcs TA WHERE TA.WC_ID = "+id+"";
		return OemDAOUtil.pageQuery(sql, null);
	}
	
	 /**
	  * 保修单故障明细
	  */
	@RequestMapping(value="/warrantyFaultInfo/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> warrantyFaultInfo(@PathVariable(value = "id") BigDecimal id) {
		logger.info("==========保修单故障明细==========");
		Map<String, Object> mapA = warrantyQueryOEMService.WarrantyFaultInfoById(id);
		return mapA;
	}
	
 	/**
	 * 保修单操作清单
	 */
	@RequestMapping(value="/queryWarrantyOperate/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryWarrantyOperate(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====保修单操作清单=====");
		 StringBuilder sql = new StringBuilder();
		 sql.append("select t.ID,t.OPT_CODE,t.OPT_NAME_CN,t.WORK_HOUR, \n");
		 sql.append("case when t.IS_CAN_MOD=10041001 then '开放性费用' else '非开放性费用' end IS_CAN_MOD, \n");
		 sql.append("t.WORK_PRICE,t.INVOICE_CODE,t.WORK_HOUR*t.WORK_PRICE WORK_FEE \n");
		 sql.append("from tt_wr_wc_detail_operate_dcs t where t.DETAIL_ID = "+id+" \n");
		 return OemDAOUtil.pageQuery(sql.toString(), null);
	}
	
 	/**
	 * 保修单材料清单
	 */
	@RequestMapping(value="/queryWarrantyPart/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryWarrantyPart(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====保修单材料清单=====");
		 StringBuilder sql = new StringBuilder();
		 sql.append("select t.ID,t.PT_CODE,t.PT_NAME,t.QTY,t.PT_PRICE, \n");
		 sql.append("case when t.IS_CAN_MOD=10041001 then '开放性费用' else '非开放性费用' end IS_CAN_MOD, \n");
		 sql.append("t.QTY*t.PT_PRICE PT_FEE,t.IS_MAIN \n");
		 sql.append("from tt_wr_wc_detail_part_dcs t where t.DETAIL_ID = "+id+" \n");
		 return OemDAOUtil.pageQuery(sql.toString(), null);
	}
	
	/**
	 * 作废
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/delete/{wcCode}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> reset(@RequestParam Map<String, String> queryParams,@PathVariable("wcCode") String wcCode,UriComponentsBuilder uriCB){
		logger.info("==========作废==========");
		warrantyQueryOEMService.delete(wcCode);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/delete").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
}
