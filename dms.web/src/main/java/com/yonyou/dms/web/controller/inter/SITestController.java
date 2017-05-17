
package com.yonyou.dms.web.controller.inter;

import java.util.ArrayList;
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

import com.infoeai.eai.action.boldseas.ob.SendOwnerMsgTask;
import com.infoeai.eai.action.bsuv.dccmApp.DCCMTODCS001;
import com.infoeai.eai.action.bsuv.dccmApp.DCCMTODCS002;
import com.infoeai.eai.action.bsuv.lms.LMSTODCS001;
import com.infoeai.eai.action.ctcai.SI02;
import com.infoeai.eai.action.ctcai.SI03;
import com.infoeai.eai.action.ctcai.SI04;
import com.infoeai.eai.action.ctcai.SI05;
import com.infoeai.eai.action.ctcai.SI25;
import com.infoeai.eai.action.ctcai.SI27;
import com.infoeai.eai.action.ctcai.SI28;
import com.infoeai.eai.action.ctcai.SI31;
import com.infoeai.eai.action.ctcai.SI32;
import com.infoeai.eai.action.ctcai.SI33;
import com.infoeai.eai.action.ctcai.SI34;
import com.infoeai.eai.action.ctcai.SI35;
import com.infoeai.eai.action.ctcai.SI36;
import com.infoeai.eai.action.ctcai.SI37;
import com.infoeai.eai.action.ctcai.SI39;
import com.infoeai.eai.action.lms.SI09;
import com.infoeai.eai.action.lms.SI10;
import com.infoeai.eai.action.lms.SI11;
import com.infoeai.eai.action.lms.SI30;
import com.infoeai.eai.action.wx.WX01;
import com.infoeai.eai.action.wx.WX02;
import com.infoeai.eai.action.wx.WX02u;
import com.infoeai.eai.action.wx.WX03;
import com.infoeai.eai.action.wx.WX03u;
import com.infoeai.eai.action.wx.WX04;
import com.infoeai.eai.action.wx.WX04U;
import com.infoeai.eai.action.wx.WX05;
import com.infoeai.eai.action.wx.WxHiringTaxies;
import com.infoeai.eai.action.wx.WxResaleChange;
import com.yonyou.dcs.de.CLDCS002;
import com.yonyou.dcs.de.CLDCS003;
import com.yonyou.dcs.de.CLDCS004;
import com.yonyou.dcs.gacfca.CLDCS002Cloud;
import com.yonyou.dcs.gacfca.CLDCS003Cloud;
import com.yonyou.dcs.gacfca.CLDCS004Cloud;
import com.yonyou.dcs.gacfca.SADCS001Cloud;
import com.yonyou.dcs.gacfca.SADCS003Cloud;
import com.yonyou.dcs.gacfca.SADCS003ForeCloud;
import com.yonyou.dcs.gacfca.SADCS007Cloud;
import com.yonyou.dcs.gacfca.SADCS013Colud;
import com.yonyou.dcs.gacfca.SADCS015Cloud;
import com.yonyou.dcs.gacfca.SADCS017Cloud;
import com.yonyou.dcs.gacfca.SADCS020Cloud;
import com.yonyou.dcs.gacfca.SADCS021Cloud;
import com.yonyou.dcs.gacfca.SADCS031Cluod;
import com.yonyou.dcs.gacfca.SADCS032Cloud;
import com.yonyou.dcs.gacfca.SADCS055Cloud;
import com.yonyou.dcs.gacfca.SADMS065Cloud;
import com.yonyou.dcs.gacfca.SEDCS054Cloud;
import com.yonyou.dcs.gacfca.SEDCS063Cloud;
import com.yonyou.dcs.gacfca.SEDCS065Cloud;
import com.yonyou.dcs.gacfca.SEDCSP6Cloud;
import com.yonyou.dcs.gacfca.SEDMS066Cloud;
import com.yonyou.dcs.gacfca.SEDMS068Cloud;
import com.yonyou.dcs.gacfca.SOTDMS001Cloud;
import com.yonyou.dcs.gacfca.SOTDMS002Cloud;
import com.yonyou.dcs.gacfca.SOTDMS003Cloud;
import com.yonyou.dcs.gacfca.SOTDMS004Cloud;
import com.yonyou.dcs.gacfca.SOTDMS005Cloud;
import com.yonyou.dcs.gacfca.SOTDMS006Cloud;
import com.yonyou.dcs.gacfca.SOTDMS007Cloud;
import com.yonyou.dcs.gacfca.SOTDMS008Cloud;
import com.yonyou.dcs.gacfca.SOTDMS012Cloud;
import com.yonyou.dcs.gacfca.SOTDMS013Cloud;
import com.yonyou.dcs.gacfca.SOTDMS014Cloud;
import com.yonyou.dcs.gacfca.SOTDMS015Cloud;
import com.yonyou.dcs.gacfca.SOTDMS016Cloud;
import com.yonyou.dcs.gacfca.SOTDMS017Cloud;
import com.yonyou.dcs.gacfca.SendBoldMsgToDmsByWXCloud;
import com.yonyou.dcs.gacfca.SendBoldMsgToDmsCloud;
import com.yonyou.dms.common.domains.DTO.SiTestDto;
import com.yonyou.dms.common.domains.DTO.basedata.OutBoundReturnDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.gacfca.SADMS016Coud;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * SItest手工触发页面
 * 
 * @author 夏威 2017-03-28
 */
@Controller
@TxnConn
@RequestMapping("/siTest")
public class SITestController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(SITestController.class);

	@Autowired
	CLDCS002Cloud cldcs002;

	@Autowired
	CLDCS002 dcs002;

	@Autowired
	CLDCS003Cloud cldcs003;

	@Autowired
	CLDCS003 dcs003;

	@Autowired
	CLDCS004Cloud cldcs004;

	@Autowired
	CLDCS004 dcs004;

	@Autowired
	SOTDMS001Cloud sotdms001cloud;

	@Autowired
	SOTDMS002Cloud sotdms002cloud;

	@Autowired
	SOTDMS003Cloud sotdms003cloud;

	@Autowired
	SOTDMS004Cloud sotdms004cloud;

	@Autowired
	SOTDMS005Cloud sotdms005cloud;

	@Autowired
	SOTDMS006Cloud sotdms006cloud;

	@Autowired
	SOTDMS007Cloud sotdms007cloud;

	@Autowired
	SOTDMS008Cloud sotdms008cloud;

	@Autowired
	SOTDMS012Cloud sotdms012cloud;

	@Autowired
	SOTDMS013Cloud sotdms013cloud;

	@Autowired
	SOTDMS014Cloud sotdms014cloud;

	@Autowired
	SOTDMS015Cloud sotdms015cloud;

	@Autowired
	SOTDMS016Cloud sotdms016cloud;

	@Autowired
	SOTDMS017Cloud sotdms017cloud;
	@Autowired
	SADCS020Cloud sadcs020cloud;
	@Autowired
	SADCS031Cluod sadcs031cluod;

	@Autowired
	SADMS065Cloud sadms065cloud;

	@Autowired
	SEDCS063Cloud sedcs063cloud;

	@Autowired
	SEDCS065Cloud sedcs065cloud;

	@Autowired
	SEDMS066Cloud sedms066cloud;

	@Autowired
	SEDMS068Cloud sedms068cloud;

	@Autowired
	SEDCSP6Cloud sedcsp6cloud;

	@Autowired
	LMSTODCS001 lmstodcs001;

	@Autowired
	SendOwnerMsgTask sendowner;

	@Autowired
	SI02 si02;
	@Autowired
	SI03 si03;

	@Autowired
	SI04 si04;
	@Autowired
	SI05 si05;
	@Autowired
	SI25 si25;

	@Autowired
	SI37 si37;

	@Autowired
	SI27 si27;

	@Autowired
	SI28 si28;

	@Autowired
	SI31 si31;

	@Autowired
	SI32 si32;

	@Autowired
	SI33 si33;

	@Autowired
	SI34 si34;
	@Autowired
	SI35 si35;
	@Autowired
	SI36 si36;
	@Autowired
	SI39 si39;
	@Autowired
	SADCS013Colud sadcs013colud;
	@Autowired
	SADCS015Cloud sadcs015cloud;
	@Autowired
	SADCS021Cloud sadcs021cluod;
	@Autowired
	SADCS032Cloud sadcs032clud;
	@Autowired
	SADCS055Cloud sadcs055cloud;

	@Autowired
	SEDCS054Cloud sedcs054c;
	@Autowired
	SADCS017Cloud sadcs017cloud;

	@Autowired
	SendBoldMsgToDmsCloud sbmtdc;

	@Autowired
	SendBoldMsgToDmsByWXCloud wx;

	@Autowired
	SADMS016Coud sadms016;

	@Autowired
	SADCS003ForeCloud SADCS003Fore;

	@Autowired
	SADCS003Cloud SADCS003Cloud;
	
	@Autowired
	SADCS001Cloud sadcs001;

	@Autowired
	SADCS007Cloud SADCS007Cloud;
	
	@Autowired
	WX01 wx01;

	@Autowired
	WX02 wx02;

	@Autowired
	WX02u wx02u;

	@Autowired
	WX03 wx03;

	@Autowired
	WX03u wx03u;

	@Autowired
	WX04 wx04;

	@Autowired
	WX04U wx04u;

	@Autowired
	WX05 wx05;

	@Autowired
	WxHiringTaxies wxHiringTaxies;

	@Autowired
	WxResaleChange wxResaleChange;
	
	@Autowired
	SI09 si09;
	
	@Autowired
	SI10 si10;
	
	@Autowired
	SI11 si11;
	
	@Autowired
	SI30 si30;
	
	@Autowired
	DCCMTODCS001 dccmtodcs001;
	
	@Autowired
	DCCMTODCS002 dccmtodcs002;
	
	@RequestMapping(value = "/{siName}", method = RequestMethod.PUT)
	public ResponseEntity<SiTestDto> siTest(@PathVariable("siName") String siName, @RequestBody @Valid SiTestDto dto,
			UriComponentsBuilder uriCB) {
		try {

			if ("SADCS020".equals(siName)) {
				logger.info("================微信车主信息下发===================");
				try {
					sadcs020cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
				try {
					// sadcs013colud.sendData(null, null);
				} catch (Exception e) {
					System.out.println("DE接口调用失败！" + e);
				}
			}

			if ("SADCS020".equals(siName)) {
				logger.info("================微信车主信息下发===================");
				try {
					sadcs020cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
				try {
					// sadcs013colud.sendData(null, null);
				} catch (Exception e) {
					System.out.println("DE接口调用失败！" + e);
				}
			}
			if ("SADCS055".equals(siName)) {
				logger.info("================微信车主信息下发===================");
				try {
					sadcs055cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
				try {
					// sadcs013colud.sendData(null, null);
				} catch (Exception e) {
					System.out.println("DE接口调用失败！" + e);
				}
			}
			if ("SADCS032".equals(siName)) {
				logger.info("================大客户政策车系下发===================");
				try {
					sadcs032clud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
				try {
					// sadcs013colud.sendData(null, null);
				} catch (Exception e) {
					System.out.println("DE接口调用失败！" + e);
				}
			}

			if ("SADCS031".equals(siName)) {
				logger.info("================大客户政策车系下发===================");
				try {
					sadcs031cluod.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
				try {
					// sadcs013colud.sendData(null, null);
				} catch (Exception e) {
					System.out.println("DE接口调用失败！" + e);
				}
			}
			if ("SADCS017".equals(siName)) {
				logger.info("================一对一客户经理绑定修改下发====================");
				try {
					sadcs017cloud.execute("SN1403250001");
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
				try {
					// sadcs013colud.sendData(null, null);
				} catch (Exception e) {
					System.out.println("DE接口调用失败！" + e);
				}
			}
			if ("SADCS021".equals(siName)) {
				logger.info("================一对一客户经理绑定修改下发====================");
				try {
					sadcs021cluod.handleExecute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
				try {
					// sadcs013colud.sendData(null, null);
				} catch (Exception e) {
					System.out.println("DE接口调用失败！" + e);
				}
			}

			if ("SADCS015".equals(siName)) {
				logger.info("================大客户报备审批数据下发（SADCS013）====================");
				try {
					sadcs015cloud.execute("WS1403260001", "33102");
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
				try {
					// sadcs013colud.sendData(null, null);
				} catch (Exception e) {
					System.out.println("DE接口调用失败！" + e);
				}
			}
			if ("SADCS013".equals(siName)) {
				logger.info("================大客户报备审批数据下发（SADCS013）====================");
				try {
					sadcs013colud.execute("WS1017012102", "33102");
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
				try {
					// sadcs013colud.sendData(null, null);
				} catch (Exception e) {
					System.out.println("DE接口调用失败！" + e);
				}
			}
			if ("CLDCS002".equals(siName)) {
				logger.info("================车型组主数据下发手工触发（CLDCS002）====================");
				try {
					cldcs002.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
				try {
					dcs002.sendData(null, null);
				} catch (Exception e) {
					System.out.println("DE接口调用失败！" + e);
				}
			}

			// 车型价格信息下发
			if ("CLDCS003".equals(siName)) {
				logger.info("================车型价格信息下发手工触发（CLDCS003）====================");
				try {
					cldcs003.execute(null, null);
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
				try {
					dcs003.sendData(null, null);
				} catch (Exception e) {
					System.out.println("DE接口调用失败！" + e);
				}
			}

			// 市场活动（活动主单、车型清单）
			if ("CLDCS004".equals(siName)) {
				logger.info("================车型价格信息下发手工触发（CLDCS004）====================");
				try {
					cldcs004.execute(null, null);
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
				try {
					dcs004.sendData(null, null);
				} catch (Exception e) {
					System.out.println("DE接口调用失败！" + e);
				}
			}

			if ("SOTDMS001".equals(siName)) {
				logger.info("================DMS产品、销售人员、经销商信息验证接口手工触发（SOTDMS001）====================");
				try {
					sotdms001cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SOTDMS002".equals(siName)) {
				logger.info("================数据同步验证接口手工触发（SOTDMS002）====================");
				try {
					sotdms002cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SOTDMS003".equals(siName)) {
				logger.info("================创建客户信息（客户接待信息/需求分析）APP新增下发手工触发（SOTDMS003）====================");
				try {
					sotdms003cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SOTDMS004".equals(siName)) {
				logger.info("================创建客户信息（试乘试驾）APP新增下发手工触发（SOTDMS004）====================");
				try {
					sotdms004cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SOTDMS005".equals(siName)) {
				logger.info("================创建客户信息（置换需求）APP新增手工触发（SOTDMS005）====================");
				try {
					sotdms005cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SOTDMS006".equals(siName)) {
				logger.info("================创建客户信息（金融报价）APP新增手工触发（SOTDMS006）====================");
				try {
					sotdms006cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SOTDMS007".equals(siName)) {
				logger.info("================创建客户信息（客户培育）APP新增手工触发（SOTDMS007）====================");
				try {
					sotdms007cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SOTDMS008".equals(siName)) {
				logger.info("================创建客户信息（试驾车辆信息）APP新增手工触发（SOTDMS008）====================");
				try {
					sotdms008cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SOTDMS012".equals(siName)) {
				logger.info("================更新客户信息（客户接待信息/需求分析）APP更新手工触发（SOTDMS012）====================");
				try {
					sotdms012cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SOTDMS013".equals(siName)) {
				logger.info("================更新客户信息（试乘试驾）APP更新手工触发（SOTDMS013）====================");
				try {
					sotdms013cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SOTDMS014".equals(siName)) {
				logger.info("================更新客户信息（置换需求）APP更新手工触发（SOTDMS014）====================");
				try {
					sotdms014cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SOTDMS015".equals(siName)) {
				logger.info("================更新客户信息（金融报价）APP更新手工触发（SOTDMS015）====================");
				try {
					sotdms015cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SOTDMS016".equals(siName)) {
				logger.info("================更新客户信息（客户培育）APP更新手工触发（SOTDMS016）====================");
				try {
					sotdms016cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SOTDMS017".equals(siName)) {
				logger.info("================销售人员分配信息APP更新手工触发（SOTDMS017）====================");
				try {
					sotdms017cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SADMS065".equals(siName)) {
				logger.info("================销售人员分配信息APP更新手工触发（SOTDMS017）====================");
				try {
					sadms065cloud.execute(dto.getVinList());
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SEDCS063".equals(siName)) {
				logger.info("================大客户直销生成售后资料手工触发（SADMS065）====================");
				try {
					sedcs063cloud.execute(dto.getId());
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SEDCS065".equals(siName)) {
				logger.info("================呆滞件下架or取消下发接口手工触发（SEDCS065）====================");
				try {
					sedcs065cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SEDMS066".equals(siName)) {
				logger.info("================调拨出库单下发接口手工触发（SEDMS066）====================");
				try {
					sedms066cloud.execute(dto.getOutWarehousNos());
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SEDMS068".equals(siName)) {
				logger.info("================调拨入库单下发接口手工触发（SEDMS068）====================");
				try {
					sedms068cloud.execute(dto.getAllocateOutNo());
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}

			if ("SEDCSP6".equals(siName)) {
				logger.info("================经销商交货单发送接口手工触发（SEDCSP6）====================");
				try {
					sedcsp6cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("LMSTODCS001".equals(siName)) {
				logger.info("================DCS线索校验(Cue Check)）====================");
				try {
					// lmstodcs001.receiveBSUVCueCheck("2015-01-01 00:00:00",
					// "2017-04-17 00:00:00");
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SendOwnerMsgTask".equals(siName)) {
				logger.info("================DCS线索校验(Cue Check)）====================");
				try {
					sendowner.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SI02".equals(siName)) {
				logger.info("================销售订单信息导入-ZDRR====================");
				try {
					si02.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SI03".equals(siName)) {
				logger.info("================经销商返利明细导入====================");
				try {
					si03.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SI04".equals(siName)) {
				logger.info("================车辆返利使用结果回传====================");
				try {
					si04.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SI05".equals(siName)) {
				logger.info("================红票返利使用结果回传(SI05 CTCAI->DCS)====================");
				try {
					si05.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SI25".equals(siName)) {
				logger.info("================ZDRQ-订单取消同步接口(DCS->CTCAI)====================");
				try {
					si25.doCtcaiMethod(dto.getVin());
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SI37".equals(siName)) {
				logger.info("================中进撤单结果处理(CTCAI->DCS)====================");
				try {
					si37.execute(dto.getIn());
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SI27".equals(siName)) {
				logger.info("================QHDD-期货订单同步至CATC系统(DCS->CTCAI)====================");
				try {
					si27.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SI28".equals(siName)) {
				logger.info("================DJZF-经销商定金支付状态同步至DCS系统(CTCAI->DCS)====================");
				try {
					si28.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SI31".equals(siName)) {
				logger.info("================撤销未支付全款的期货订单(DCS->CTCAI)====================");
				try {
					si31.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SI32".equals(siName)) {
				logger.info("================DZXX-CTCAI将单证信息发送给DCS(CTCAI->DCS)====================");
				try {
					si32.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SI33".equals(siName)) {
				logger.info("================ZCXX-CTCAI将整车信息发送给DCS(CTCAI->DCS)====================");
				try {
					si33.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SI34".equals(siName)) {
				logger.info("================FPXX--CTCAI将发票信息发送给DCS(CTCAI->DCS)====================");
				try {
					si34.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SI35".equals(siName)) {
				logger.info("================CZXX-CTCAI将出证信息发送给DCS(CTCAI->DCS)====================");
				try {
					si35.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SI36".equals(siName)) {
				logger.info("================ZJXX-CTCAI将资金信息发送给DCS(CTCAI->DCS)====================");
				try {
					si36.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SI39".equals(siName)) {
				logger.info("================生产订单(DCS->CTCAI)====================");
				try {
					si39.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SEDCS054Cloud".equals(siName)) {
				logger.info("================克莱斯勒明检和神秘客业务需求BRD文档  下发====================");
				try {
					sedcs054c.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SBMSTDC".equals(siName)) {
				logger.info("================车主核实结果下发====================");
				try {
					OutBoundReturnDTO obr = new OutBoundReturnDTO();
					obr.setDealerCode("33123");
					obr.setObFlag("1");
					obr.setDmsOwnerId("PU1308287584");// IS_BINDING
					obr.setIsBinding("1");
					sbmtdc.sendData(obr);
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("WX".equals(siName)) {
				logger.info("================微信绑定结果下发====================");
				try {
					wx.sendDate(null, null);
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SI01v2".equals(siName)) {
				logger.info("================DDU读取文件====================");
				try {
					// si01v2.testSi01v2(dto);
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SADMS016".equals(siName)) {
				logger.info("================二手车置换返利====================");
				try {
					sadms016.getSADMS016("SN1403250001");
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SADCS003Fore".equals(siName)) {
				logger.info("================DCC建档客户信息下发====================");
				try {
					SADCS003Fore.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SADCS003".equals(siName)) {
				logger.info("================SADCS003Cloud --> DCC建档客户信息下发====================");
				try {
					SADCS003Cloud.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("SADCS007".equals(siName)) {
				logger.info("================SADCS007Cloud --> 经销商之间车辆调拨下发====================");
				try {
					List<Long> dealerIds = new ArrayList<>();
					dealerIds.add(201308217685191L);
					Long vehicleId = 201308228463532L;
					Long inDealerId = 201308217685191L;
					Long outDealerId = 201308217685182L;
					SADCS007Cloud.execute(dealerIds, vehicleId, inDealerId, outDealerId);
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");// SADCS007Cloud
				}

			}
			
			if ("SADCS001".equals(siName)) {
				logger.info("================车辆发运信息下发====================");
				try {
					String dealerCode="33151";
					String dealerId="201308217685211";
					String vehicleId="201308228463532";
					sadcs001.execute(dealerCode,dealerId,vehicleId);
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}

			}
			if ("WX01".equals(siName)) {
				logger.info("================WX01 --> 车主信息====================");
				try {
					wx01.handleExecute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			if ("WX02".equals(siName)) {
				logger.info("================WX02 --> 一对一客户经理====================");
				try {
					wx02.handleExecute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			if ("WX02U".equals(siName)) {
				logger.info("================WX02u --> 交车客户、客户经理重绑 ====================");
				try {
					wx02u.handleExecute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			if ("WX03".equals(siName)) {
				logger.info("================WX03 --> 新增车辆信息====================");
				try {
					wx03.handleExecute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			if ("WX03U".equals(siName)) {
				logger.info("================WX03u --> 更新车辆信息====================");
				try {
					wx03u.handleExecute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			if ("WX04".equals(siName)) {
				logger.info("================WX04 --> 默认客户经理====================");
				try {
					wx04.handleExecute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			if ("WX04U".equals(siName)) {
				logger.info("================WX04u --> 离职客户经理修改接口====================");
				try {
					wx04u.handleExecute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			if ("WX05".equals(siName)) {
				logger.info("================WX05 --> 保养信息====================");
				try {
					wx05.handleExecute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			if ("WxHiringTaxies".equals(siName)) {
				logger.info("================WxHiringTaxies --> 订车信息发送====================");
				try {
					wxHiringTaxies.handleExecute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			if ("WxResaleChange".equals(siName)) {
				logger.info("================WxResaleChange --> 经销商零售信息变更====================");
				try {
					wxResaleChange.handleExecute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			if ("SI09".equals(siName)) {
				logger.info("================SI09 --> 经销商零售信息变更====================");
				try {
					si09.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			if ("SI10".equals(siName)) {
				logger.info("================SI10 --> 经销商零售信息变更====================");
				try {
					si10.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			if ("SI11".equals(siName)) {
				logger.info("================SI11 --> 经销商零售信息变更====================");
				try {
					si11.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			if ("SI30".equals(siName)) {
				logger.info("================SI30 --> 经销商零售信息变更====================");
				try {
					si30.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			if ("DCCMTODCS001".equals(siName)) {
				logger.info("================DCCMTODCS001 --> 潜客信息数据交互接口（app新增）====================");
				try {
					dccmtodcs001.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
			if ("DCCMTODCS002".equals(siName)) {
				logger.info("================DCCMTODCS002 --> 客户接待/沟通记录数据交互接口（app新增）====================");
				try {
					dccmtodcs002.execute();
				} catch (Exception e) {
					System.out.println("Function接口调用失败！");
				}
			}
		} catch (Exception e) {
			throw new ServiceBizException("接口调用失败");
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/siTest/{siName}").buildAndExpand(siName).toUriString());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

}
