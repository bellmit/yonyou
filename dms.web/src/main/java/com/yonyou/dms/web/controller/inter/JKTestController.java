package com.yonyou.dms.web.controller.inter;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.DTO.SiTestDto;
import com.yonyou.dms.common.domains.DTO.basedata.InvoiceRefundDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerVehicleListPO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.gacfca.DMSTODCS004;
import com.yonyou.dms.gacfca.DSO0302;
import com.yonyou.dms.gacfca.DSO0401;
import com.yonyou.dms.gacfca.SADMS002;
import com.yonyou.dms.gacfca.SADMS008;
import com.yonyou.dms.gacfca.SADMS008add;
import com.yonyou.dms.gacfca.SADMS049;
import com.yonyou.dms.gacfca.SADMS095;
import com.yonyou.dms.gacfca.SADMS096;
import com.yonyou.dms.gacfca.SEDMS058;
import com.yonyou.dms.gacfca.SalesReturnUpload;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 接口调试Controller
 * @author Benzc
 * @date 2017年5月4日
 */
@Controller
@TxnConn
@RequestMapping("/jkTest")
public class JKTestController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(JKTestController.class);
	
	@Autowired SEDMS058 SEDMS058;
	
	@Autowired SADMS095 SADMS095;
	
	@Autowired DMSTODCS004 DMSTODCS004;
	
	@Autowired SADMS096 SADMS096;
	
	@Autowired SADMS049 SADMS049;
	
	@Autowired DSO0302 DSO0302;
	
	@Autowired SalesReturnUpload SalesReturnUpload;
	
	@Autowired SADMS002 SADMS002;
	
	@Autowired SADMS008add SADMS008add;
	
	@Autowired SADMS008 SADMS008;
	
	@Autowired DSO0401 DSO0401;
	
	@RequestMapping(value = "/{jkName}", method = RequestMethod.PUT)
	public ResponseEntity<SiTestDto> jkTest(@PathVariable("jkName") String jkName, @RequestBody @Valid SiTestDto dto,
			UriComponentsBuilder uriCB) {
		try {
			
			if ("SEDMS058".equals(jkName)) {
				logger.info("================SEDMS058====================");
				try {
					SEDMS058.getSEDMS058("SN1511120004", "1C4BJWBG2DL690194");
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
				try {
					// sadcs013colud.sendData(null, null);
				} catch (Exception e) {
					System.out.println("DE接口调用失败！" + e);
				}
			}
			
			if ("SADMS095".equals(jkName)) {
				logger.info("================SADMS095====================");
				try {
					InvoiceRefundDTO dto1 = new InvoiceRefundDTO();
					dto1.setCustomerNo("PU1308287584");
					dto1.setVin("LWVCAFM000726");
					dto1.setSoNo("SN1607040003");
					dto1.setGender("10061001");
					dto1.setInvoiceAmount("100000");
					dto1.setInvoiceWriter(9833L);
					dto1.setPermutedVin("1C4PJMCB7EW092404");
					SADMS095.getSADMS095(dto1);
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
				try {
					// sadcs013colud.sendData(null, null);
				} catch (Exception e) {
					System.out.println("DE接口调用失败！" + e);
				}
			}
			
			if ("DMSTODCS004".equals(jkName)) {
				logger.info("================DMSTODCS004====================");
				try {
					DMSTODCS004.getDMSTODCS004("SN1607040003", "16001002");
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
				try {
					// sadcs013colud.sendData(null, null);
				} catch (Exception e) {
					System.out.println("DE接口调用失败！" + e);
				}
			}
			
			if ("SADMS096".equals(jkName)) {
				logger.info("================SADMS096====================");
				try {
					SADMS096.getSADMS096("ZO1703150001", "张德江", "57821001");
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			
			if ("SADMS049".equals(jkName)) {
				logger.info("================SADMS049====================");
				try {
					List<TtCustomerVehicleListPO> listPo = 
							TtCustomerVehicleListPO.findBySQL("SELECT * FROM tt_customer_vehicle_list WHERE ITEM_ID=?", "10000000000000");
					SADMS049.getSADMS049(listPo, null, "PU1308287365");
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
				
			if ("DSO0302".equals(jkName)) {
				logger.info("================DSO0302====================");
				try {
					DSO0302.getDSO0302("1C4BJWBG2DL690194", "SN1511120004", "12781002", "12781001");
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			
			if ("SalesReturnUpload".equals(jkName)) {
				logger.info("================SalesReturnUpload====================");
				try {
					String[] vin = {"1C4BJWBG6DL659448","3C4PDCFB5FT744802"};
					SalesReturnUpload.getSalesReturnUpload("13071005", vin);
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			
			if ("SADMS002".equals(jkName)) {
				logger.info("================SADMS002====================");
				try {
					String vin[] = {"FDWFEWTWEFDFGJUKJ"};
					String[] isSelected = {"12781001"};
					String[] productCode = {"13/3.6L 300S 1PAUDLX92013"};
 					//SADMS002.getSADMS002("VD1604060001", vin, isSelected, productCode);
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			
			if ("SADMS008add".equals(jkName)) {
				logger.info("================SADMS008add====================");
				try {
					String customerNo = "CO1408210007";
					String vin = "0010604ZXCVBNMASD";
					Date invoiceDate = new Date();
					String isDms = "Y";
					SADMS008add.getSADMS008add(customerNo, vin, invoiceDate, isDms);
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			
			if ("SADMS008".equals(jkName)) {
				logger.info("================SADMS008====================");
				try {
					SADMS008.getSADMS008("0010604ZXCVBNMASD", "CO1604130001");
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			
			if ("DSO0401".equals(jkName)) {
				logger.info("================DSO0401====================");
				try {
					DSO0401.getDSO0401("WS1017012102");
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			
			
		} catch (Exception e) {
			throw new ServiceBizException("接口调用失败");
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/jkTest/{jkName}").buildAndExpand(jkName).toUriString());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

}
