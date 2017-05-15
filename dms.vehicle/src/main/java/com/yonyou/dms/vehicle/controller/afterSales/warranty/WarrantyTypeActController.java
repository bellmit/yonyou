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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWarrantyTypeDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWtActivityDTO;
import com.yonyou.dms.vehicle.service.afterSales.warranty.WarrantyTypeActService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 保修类型（活动）
 * @author zhanghongyi
 */
@Controller
@TxnConn
@RequestMapping("/warrantyTypeAct")
public class WarrantyTypeActController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	WarrantyTypeActService  warrantyTypeActService;
	
	/**
	 * 保修类型查询
	 */
	@RequestMapping(value = "/warrantyTypeSearch", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto warrantyTypeSearch(@RequestParam Map<String, String> queryParam) {
		logger.info("==========保修类型查询==========");
		PageInfoDto pageInfoDto = warrantyTypeActService.warrantyTypeQuery(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 保修类型新增
	 */
	@RequestMapping(value = "/addWarrantyType", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtWrWarrantyTypeDTO> addWarrantyType(@RequestBody TtWrWarrantyTypeDTO dto, UriComponentsBuilder uriCB) {
		logger.info("=====保修类型新增=====");
		Long id = warrantyTypeActService.addWarrantyType(dto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/warrantyTypeAct/addWarrantyType").buildAndExpand(id).toUriString());
		return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
	}
	
 	/**
	 * 保修类型修改初始化
	 */
	@RequestMapping(value="/queryWarrantyType/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryWarrantyType(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====保修类型修改=====");
		 StringBuilder sql = new StringBuilder();
		 sql.append("select case when t.IS_NO_CCF=10041001 then 99011001 else (case when t.IS_PRESALE=10041001 then 99011002 else \n");
		 sql.append("(case when t.IS_NO_REP=10041001 then 99011003 else null end) end) end WT_TYPE_PARM,a.ID ACT_ID,t.* \n");
		 sql.append("from tt_wr_warranty_type_dcs t, tt_wr_wt_activity_dcs a where a.WT_ID=t.ID and a.ACT_CODE is null and t.ID = "+id+" \n");
		 Map<String, Object> mapA = OemDAOUtil.findFirst(sql.toString(), null);
		 return mapA;
	}
	
	/**
	 * 保修类型修改保存
	 */
	@RequestMapping(value = "/updateWarrantyType", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtWrWarrantyTypeDTO> updateWarrantyType(@RequestBody TtWrWarrantyTypeDTO dto, UriComponentsBuilder uriCB) {
		logger.info("=====保修类型修改保存=====");
		warrantyTypeActService.updateWarrantyType(dto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
	    headers.set("Location", uriCB.path("/updateWarrantyType").buildAndExpand().toUriString());
	    return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
 	/**
	 * 活动查询
	 */
	@RequestMapping(value="/queryAct/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryAct(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====活动查询=====");
		 StringBuilder sql = new StringBuilder();
		 sql.append("select a.ID AID,a.ACT_CODE,a.ACT_NAME,a.BEGIN_TIME,a.END_TIME \n");
		 sql.append("from tt_wr_wt_activity_dcs a \n");
		 sql.append("where a.ACT_CODE is not null and a.WT_ID = "+id+" \n");
		 return OemDAOUtil.pageQuery(sql.toString(), null);
	}
	
 	/**
	 * 活动修改初始化
	 */
	@RequestMapping(value="/queryActivity/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryActivity(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====活动修改=====");
		 StringBuilder sql = new StringBuilder();
		 sql.append("select a.* \n");
		 sql.append("from tt_wr_wt_activity_dcs a \n");
		 sql.append("where a.ID = "+id+" \n");
		 Map<String, Object> mapA = OemDAOUtil.findFirst(sql.toString(), null);
		 return mapA;
	}
	
	/**
	 * 活动新增
	 */
	@RequestMapping(value = "/addAct", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtWrWtActivityDTO> addAct(@RequestBody TtWrWtActivityDTO dto, UriComponentsBuilder uriCB) {
		logger.info("=====活动新增=====");
		warrantyTypeActService.addAct(dto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
	    headers.set("Location", uriCB.path("/addAct").buildAndExpand().toUriString());
	    return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	/**
	 * 活动修改保存
	 */
	@RequestMapping(value = "/updateAct", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtWrWtActivityDTO> updateAct(@RequestBody TtWrWtActivityDTO dto, UriComponentsBuilder uriCB) {
		logger.info("=====活动修改保存=====");
		warrantyTypeActService.updateAct(dto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
	    headers.set("Location", uriCB.path("/updateAct").buildAndExpand().toUriString());
	    return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	/**
	 * 删除活动
	 */
	@RequestMapping(value = "/deleteAct/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deleteAct(@RequestParam Map<String, String> queryParams,@PathVariable(value = "id") BigDecimal id,UriComponentsBuilder uriCB){
		logger.info("==========删除活动==========");
		warrantyTypeActService.deleteAct(id);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/deleteAct").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
 	/**
	 * 保修参数查询
	 */
	@RequestMapping(value="/queryWarrantyParm/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryWarrantyParm(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====保修参数查询=====");
		 StringBuilder sql = new StringBuilder();
		 sql.append("select p.ID PID,p.MVS,p.WR_DAYS,p.WR_RANGE,p.WR_NUM \n");
		 sql.append("from tt_wr_wt_parmater_dcs p \n");
		 sql.append("where p.ACT_ID = "+id+" \n");
		 return OemDAOUtil.pageQuery(sql.toString(), null);
	}
	
 	/**
	 * 故障清单查询
	 */
	@RequestMapping(value="/queryWarrantyBug/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryWarrantyBug(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====故障清单查询=====");
		 StringBuilder sql = new StringBuilder();
		 sql.append("select b.ID BID,b.BUG_CODE,b.BUG_NAME,b.MVS,b.TAG \n");
		 sql.append("from tt_wr_wt_bug_dcs b \n");
		 sql.append("where b.ACT_ID = "+id+" \n");
		 return OemDAOUtil.pageQuery(sql.toString(), null);
	}
	
	/**
	 * 操作清单查询
	 */
	@RequestMapping(value="/queryWarrantyOper/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryWarrantyOper(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====操作清单查询=====");
		 StringBuilder sql = new StringBuilder();
		 sql.append("select o.ID OID,o.OPT_CODE,o.OPT_NAME_CN,o.MVS,o.TAG \n");
		 sql.append("from tt_wr_wt_operate_dcs o \n");
		 sql.append("where o.ACT_ID = "+id+" \n");
		 return OemDAOUtil.pageQuery(sql.toString(), null);
	}
	
	/**
	 * 零部件清单查询
	 */
	@RequestMapping(value="/queryWarrantyPart/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryWarrantyPart(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====零部件清单查询=====");
		 StringBuilder sql = new StringBuilder();
		 sql.append("select p.ID PID,p.PT_CODE,pt.PT_NAME,p.MVS,p.QTY,p.OPT_CODE,p.TAG ");
		 sql.append("from tt_wr_wt_part_dcs p \n");
		 sql.append("left join (select distinct TPPB.PART_CODE PT_CODE,TPPB.PART_NAME PT_NAME,10041002 PT_TYPE from TT_PT_PART_BASE_DCS TPPB union all \n");
		 sql.append("select distinct TWSP.PT_CODE PT_CODE,TWSP.PT_NAME PT_NAME,10041001 PT_TYPE from TT_WR_SPECIAL_PART_DCS TWSP) pt on p.PT_CODE=pt.PT_CODE and p.PT_TYPE=pt.PT_TYPE \n");
		 sql.append("where p.ACT_ID = "+id+" \n");
		 return OemDAOUtil.pageQuery(sql.toString(), null);
	}
	
}
