package com.yonyou.dms.part.controller.stockmanage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.DTO.basedata.TtcampaignPlanDTO;
import com.yonyou.dms.common.domains.DTO.stockmanage.TmPartStockDTO;
import com.yonyou.dms.common.domains.DTO.stockmanage.TmPartStockItemDTO;
import com.yonyou.dms.common.domains.DTO.stockmanage.TtPartFlowDTO;
import com.yonyou.dms.common.domains.DTO.stockmanage.TtpartLendDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.service.stockmanage.CheckOutService;
import com.yonyou.f4.mvc.annotation.TxnConn;

@Controller
@TxnConn
@RequestMapping("/part/lendOrderChoose")
public class CheckOutController {

	@Autowired
	private CheckOutService checkoutService;

	/**
	 * 查询借出登记单TODO description
	 * 
	 * @param queryParam
	 * @return
	 * @throws SerialException
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto lendOrderChoose(@RequestParam Map<String, String> queryParam) throws SerialException {
		return checkoutService.findOrderChoose(queryParam);
	}

	/**
	 * 查询借出明细
	 * 
	 * @param id
	 * @return
	 * @throws SerialException
	 */
	@RequestMapping(value = "/{lendNo}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findAllDetails(@PathVariable(value = "lendNo") String id,
			@RequestParam Map<String, String> queryParam) throws ServiceBizException, SerialException {
		PageInfoDto pageInfoDto = checkoutService.lendDetail(id, queryParam);
		if (StringUtils.isNullOrEmpty(pageInfoDto)) {
			return new PageInfoDto();
		} else {
			return pageInfoDto;// 页面关闭时解锁
		}
	}

	/**
	 * 新增查询借出登记单明细TODO description
	 * 
	 * @param queryParam
	 * @return
	 * @throws SerialException
	 */
	@RequestMapping(value = "/registeritem", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto lendRegisterItem(@RequestParam Map<String, String> queryParam) throws SerialException {
		return checkoutService.findLendRegisterItem(queryParam);
	}

	/**
	 * 新增查询销售单明细TODO description
	 * 
	 * @param queryParam
	 * @return
	 * @throws SerialException
	 */
	@RequestMapping(value = "/{partNo}/{storageCode}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto lendPrice(@PathVariable(value = "partNo") String partNo,
			@PathVariable(value = "storageCode") String storageCode, @RequestParam Map<String, String> queryParam)
			throws SerialException {
		return checkoutService.findLendPrice(partNo, storageCode, queryParam);
	}

	/**
	 * 新增借记登记单/借记登记明细
	 * 
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/btnSave", method = RequestMethod.POST)
	public ResponseEntity<String> btnSave(@RequestBody TtpartLendDTO ttpartLendDTO, UriComponentsBuilder uriCB) {
		String btnSave = checkoutService.btnSave(ttpartLendDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/part/lendOrderChoose/btnSave").buildAndExpand().toUriString());
		return new ResponseEntity<String>(btnSave, headers, HttpStatus.CREATED);
	}

	/**
	 * 借记登记单/借记登记明细出库
	 * 
	 * @param uriCB
	 * @return
	 * @throws SerialException
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/{partNo2}/{storageCode}/{outQuantity}", method = RequestMethod.POST)
	public ResponseEntity<Map> btnOutter(@PathVariable(value = "partNo2") String partNo2,
			@PathVariable(value = "storageCode") String storageCode,
			@PathVariable(value = "outQuantity") String outQuantity, Float tag, TmPartStockItemDTO tmPartStockItemDTO,
			UriComponentsBuilder uriCB) throws Exception {
		String[] split = partNo2.split(",");// 用来储存出库时选择的partNo2号
		String[] split2 = storageCode.split(",");// 用来储存出库时选择的storageCode号
		String[] split3 = outQuantity.split(",");// 用来储存出库时选择的outQuantity号
		String partNo2s = "";
		String storageCodes = "";
		String outQuantitys = "";
		List<Map> list = new ArrayList<Map>();
		if (split.length > 1 && split2.length > 1 && split3.length > 1) {// 表示批量出库
			for (int i = 0; i < split.length; i++) {
				if (i == split.length - 1 && i == split2.length - 1 && i == split3.length - 1) {
					partNo2s += "'" + split[i] + "'";
					storageCodes += "'" + split[i] + "'";
					outQuantitys += "'" + split[i] + "'";
				} else {
					partNo2s += "'" + split[i] + "',";
					storageCodes += "'" + split[i] + "',";
					outQuantitys += "'" + split[i] + "',";
				}
			}
			list = checkoutService.btnOutter(list, partNo2s, storageCodes, outQuantitys, tag, tmPartStockItemDTO);
		} else {// 表示单个出库
			if (split.length != 0 && split2.length != 0 & split3.length != 0) {
				list = checkoutService.btnOutter2(list, split[0], split2[0], split3[0], tag, tmPartStockItemDTO);
			}
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/vehicleStock/stockIn").buildAndExpand().toUriString());
		return new ResponseEntity<Map>(list.get(0), headers, HttpStatus.CREATED);
	}

	/**
	 * 查询库存是否为零或金额为零
	 * 
	 * @param queryParam
	 * @return
	 * @throws SerialException
	 */
	@RequestMapping(value = "/partNo2/storageCode", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto quaryAccount(@RequestParam Map<String, String> queryParam) throws SerialException {
		return checkoutService.findAccount(queryParam);
	}

	/**
	 * 入账,出库操作
	 * 
	 * @param dto
	 * @param uriCB
	 * @return
	 * @throws SerialException
	 */
	@RequestMapping(value = "/{lendNo}", method = RequestMethod.POST)
	public ResponseEntity<Integer> btnOut(@PathVariable(value = "lendNo") String lendNo, UriComponentsBuilder uriCB)
			throws Exception {
		List<Map> lendDetail2 = checkoutService.lendDetail2(lendNo);

		checkoutService.btnOut(lendNo, lendDetail2);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/part/lendOrderChoose/btnSave").buildAndExpand().toUriString());
		return new ResponseEntity<Integer>(headers, HttpStatus.CREATED);
	}

	/**
	 * 作废 TODO description
	 * 
	 * @param id
	 * @param uriCB
	 * @param ptdto
	 */
	@RequestMapping(value = "/delete/{lendNo}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAllocateIn(@PathVariable(value = "lendNo") String lendNo, UriComponentsBuilder uriCB) {
		checkoutService.deleteAllocateIn(lendNo);
	}

}
