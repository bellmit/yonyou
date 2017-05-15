package com.yonyou.dms.vehicle.controller.afterSales.warranty;

import java.math.BigDecimal;
import java.util.List;
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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWtParmaterDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWtBugDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWtOperateDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWtPartDTO;
import com.yonyou.dms.vehicle.service.afterSales.warranty.WarrantyTypeNotActService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 保修类型（非活动）
 * @author zhanghongyi
 */
@Controller
@TxnConn
@RequestMapping("/warrantyTypeNotAct")
public class WarrantyTypeNotActController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	WarrantyTypeNotActService  warrantyTypeNotActService;
	
	/**
	 * 保修类型查询
	 */
	@RequestMapping(value = "/warrantyTypeSearch", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto warrantyTypeSearch(@RequestParam Map<String, String> queryParam) {
		logger.info("==========保修类型查询==========");
		PageInfoDto pageInfoDto = warrantyTypeNotActService.warrantyTypeQuery(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 保修类型新增
	 */
	@RequestMapping(value = "/addWarrantyType", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtWrWarrantyTypeDTO> addWarrantyType(@RequestBody TtWrWarrantyTypeDTO dto, UriComponentsBuilder uriCB) {
		logger.info("=====保修类型新增=====");
		Long id = warrantyTypeNotActService.addWarrantyType(dto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/warrantyTypeNotAct/addWarrantyType").buildAndExpand(id).toUriString());
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
		 sql.append("from tt_wr_warranty_type_dcs t, tt_wr_wt_activity_dcs a where a.WT_ID=t.ID and t.ID = "+id+" \n");
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
		warrantyTypeNotActService.updateWarrantyType(dto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
	    headers.set("Location", uriCB.path("/updateWarrantyType").buildAndExpand().toUriString());
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
		 sql.append("from tt_wr_warranty_type_dcs t, tt_wr_wt_activity_dcs a, tt_wr_wt_parmater_dcs p \n");
		 sql.append("where a.WT_ID=t.ID and p.ACT_ID=a.ID and t.ID = "+id+" \n");
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
		 sql.append("select b.ID BID,b.BUG_CODE,b.BUG_NAME,b.MVS \n");
		 sql.append("from tt_wr_warranty_type_dcs t, tt_wr_wt_activity_dcs a, tt_wr_wt_bug_dcs b \n");
		 sql.append("where a.WT_ID=t.ID and b.ACT_ID=a.ID and t.ID = "+id+" \n");
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
		 sql.append("select o.ID OID,o.OPT_CODE,o.OPT_NAME_CN,o.MVS \n");
		 sql.append("from tt_wr_warranty_type_dcs t, tt_wr_wt_activity_dcs a, tt_wr_wt_operate_dcs o \n");
		 sql.append("where a.WT_ID=t.ID and o.ACT_ID=a.ID and t.ID = "+id+" \n");
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
		 sql.append("select p.ID PID,p.PT_CODE,pt.PT_NAME,p.MVS,p.QTY,p.OPT_CODE ");
		 sql.append("from tt_wr_warranty_type_dcs t inner join tt_wr_wt_activity_dcs a on a.WT_ID=t.ID inner join tt_wr_wt_part_dcs p on p.ACT_ID=a.ID \n");
		 sql.append("left join (select distinct TPPB.PART_CODE PT_CODE,TPPB.PART_NAME PT_NAME,10041002 PT_TYPE from TT_PT_PART_BASE_DCS TPPB union all \n");
		 sql.append("select distinct TWSP.PT_CODE PT_CODE,TWSP.PT_NAME PT_NAME,10041001 PT_TYPE from TT_WR_SPECIAL_PART_DCS TWSP) pt on p.PT_CODE=pt.PT_CODE and p.PT_TYPE=pt.PT_TYPE \n");
		 sql.append("where t.ID = "+id+" \n");
		 return OemDAOUtil.pageQuery(sql.toString(), null);
	}
	
	/**
	 * 删除保修参数
	 */
	@RequestMapping(value = "/deleteParm/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deleteParm(@RequestParam Map<String, String> queryParams,@PathVariable(value = "id") BigDecimal id,UriComponentsBuilder uriCB){
		logger.info("==========删除保修参数==========");
		warrantyTypeNotActService.deleteParm(id);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/deleteParm").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	/**
	 * 删除保修故障
	 */
	@RequestMapping(value = "/deleteBug/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deleteBug(@RequestParam Map<String, String> queryParams,@PathVariable(value = "id") BigDecimal id,UriComponentsBuilder uriCB){
		logger.info("==========删除保修故障==========");
		warrantyTypeNotActService.deleteBug(id);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/deleteBug").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	/**
	 * 删除保修操作
	 */
	@RequestMapping(value = "/deleteOper/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deleteOper(@RequestParam Map<String, String> queryParams,@PathVariable(value = "id") BigDecimal id,UriComponentsBuilder uriCB){
		logger.info("==========删除保修故障==========");
		warrantyTypeNotActService.deleteOper(id);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/deleteOper").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	/**
	 * 删除保修零部件
	 */
	@RequestMapping(value = "/deletePart/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?> deletePart(@RequestParam Map<String, String> queryParams,@PathVariable(value = "id") BigDecimal id,UriComponentsBuilder uriCB){
		logger.info("==========删除保修零部件==========");
		warrantyTypeNotActService.deletePart(id);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/deletePart").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	/**
	* 获取MVS
	*/
    @RequestMapping(value="/getMvs/{mvsType}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getMvs(@RequestParam Map<String, String> queryParams,@PathVariable("mvsType") String mvsType){
    	logger.info("==========获取MVS==========");
    	logger.info("==========mvsType=========="+mvsType);
    	List<Map> mapping = warrantyTypeNotActService.getMvs(queryParams,mvsType);
        return mapping;
    }
    
	/**
	 * 保修参数新增
	 */
	@RequestMapping(value = "/addParm", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtWrWtParmaterDTO> addParm(@RequestBody TtWrWtParmaterDTO dto, UriComponentsBuilder uriCB) {
		logger.info("=====保修参数新增=====");
		Long id = warrantyTypeNotActService.addParm(dto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/warrantyTypeNotAct/addParm").buildAndExpand(id).toUriString());
		return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
	}
	
	/**
	 * 保修故障新增
	 */
	@RequestMapping(value = "/addBug", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtWrWtBugDTO> addBug(@RequestBody TtWrWtBugDTO dto, UriComponentsBuilder uriCB) {
		logger.info("=====保修故障新增=====");
		Long id = warrantyTypeNotActService.addBug(dto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/warrantyTypeNotAct/addParm").buildAndExpand(id).toUriString());
		return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
	}
	
	/**
	 * 保修操作新增
	 */
	@RequestMapping(value = "/addOper", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtWrWtOperateDTO> addOper(@RequestBody TtWrWtOperateDTO dto, UriComponentsBuilder uriCB) {
		logger.info("=====保修操作新增=====");
		Long id = warrantyTypeNotActService.addOper(dto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/warrantyTypeNotAct/addParm").buildAndExpand(id).toUriString());
		return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
	}
	
	/**
	 * 保修零部件新增
	 */
	@RequestMapping(value = "/addPart", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtWrWtPartDTO> addPart(@RequestBody TtWrWtPartDTO dto, UriComponentsBuilder uriCB) {
		logger.info("=====保修零部件新增=====");
		Long id = warrantyTypeNotActService.addPart(dto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/warrantyTypeNotAct/addParm").buildAndExpand(id).toUriString());
		return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
	}
	
	/**
	 * 操作代码查询
	 */
	@RequestMapping(value = "/operSearch", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto operSearch(@RequestParam Map<String, String> queryParam) {
		logger.info("==========操作代码查询==========");
		PageInfoDto pageInfoDto = warrantyTypeNotActService.operSearch(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 零部件查询
	 */
	@RequestMapping(value = "/partSearch", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto partSearch(@RequestParam Map<String, String> queryParam) {
		logger.info("==========零部件查询==========");
		PageInfoDto pageInfoDto = warrantyTypeNotActService.partSearch(queryParam);
		return pageInfoDto;
	}

}
