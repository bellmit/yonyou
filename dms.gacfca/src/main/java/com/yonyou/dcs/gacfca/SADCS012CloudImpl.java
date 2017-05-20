package com.yonyou.dcs.gacfca;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dcs.dao.SADCS012CloudDao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.PoCusWholeClryslerDto;
import com.yonyou.dms.DTO.gacfca.WsConfigInfoClryslerDto;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerBatchSaleInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerFilingBaseInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerReportApprovalPO;
import com.yonyou.dms.common.domains.PO.basedata.TtUcCustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.TtUcLinkmanPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: SADCS012CloudImpl 
* @Description: 【异步】大客户报备数据上报
* @author zhengzengliang 
* @date 2017年4月13日 下午3:52:56 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class SADCS012CloudImpl extends BaseCloudImpl implements SADCS012Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS012CloudImpl.class);
	
	@Autowired
	DeCommonDao  deCommonDao;
	
	@Autowired
	SADCS012CloudDao sadcs012CloudDao;

	@Override
	public String receiveDate(List<PoCusWholeClryslerDto> dtoList) throws Exception {
	    String msg="1";
		logger.info("*************************** 开始获取大客户报备上传数据 ******************************");
		try {
		    
			dealUpBigCustomerData(dtoList);
		} catch (Exception e) {
		    msg="0";
			e.printStackTrace();
			throw new ServiceBizException(e);
		}
		logger.info("*************************** 成功获取大客户报备上传数据 ******************************");
		return msg;
	}
	
	//处理上报数据
		private  void dealUpBigCustomerData(List<PoCusWholeClryslerDto> bodys) throws Exception{
		   
			logger.info("##################### 开始处理大客户报备上传数据 ############################");
			System.out.println( bodys.size());
			Calendar cal = Calendar.getInstance();
			for (PoCusWholeClryslerDto wholeVo : bodys) {
		//		PoCusWholeClryslerVO wholeVo = (PoCusWholeClryslerVO)entry.getValue();
				Map map = deCommonDao.getSaDcsDealerCode(wholeVo.getDealerCode());
				String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
				//验证是否存在报备单(用于判断是否首次报备)
				TtBigCustomerFilingBaseInfoPO keyFilingBasePO = new TtBigCustomerFilingBaseInfoPO();
				keyFilingBasePO.setString("WS_NO", wholeVo.getWsNo().trim()); //报备单号
				/***业务更改---报备单号+经销商确定唯一***/
				keyFilingBasePO.setString("DEALER_CODE", dealerCode); 
				keyFilingBasePO.setInteger("ENABLE", OemDictCodeConstants.STATUS_ENABLE); //是否有效
				List<TtBigCustomerFilingBaseInfoPO> baseInfoList = TtBigCustomerFilingBaseInfoPO.find("WS_NO = ? AND DEALER_CODE = ? AND ENABLE = ? "
						, wholeVo.getWsNo().trim(),dealerCode,OemDictCodeConstants.STATUS_ENABLE);
				
				//大客户信息
				TtUcCustomerPO ucCustomerPo = new TtUcCustomerPO();
				ucCustomerPo.setString("DEALER_CODE", dealerCode);
				ucCustomerPo.setInteger("CUSTOMER_TYPE", OemDictCodeConstants.CTM_TYPE_02); //公司客户
				ucCustomerPo.setInteger("CUSTOMER_BUSINESS_TYPE", OemDictCodeConstants.IF_TYPE_YES); //是大客户
				ucCustomerPo.setString("CUSTOMER_NAME", wholeVo.getCustomerName()); //客户名称
				ucCustomerPo.setString("CUSTOMER_PHONE", wholeVo.getMobile()); //客户电话
				ucCustomerPo.setString("CUSTOMER_COMPANY_NAME", wholeVo.getCustomerName()); //大客户名称
				ucCustomerPo.setInteger("CUSTOMER_COMPANY_NATURE", wholeVo.getCustomerCharacter()); //大客户公司性质
				ucCustomerPo.setInteger("CUSTOMER_COMPANY_TYPE", wholeVo.getCustomerKind()); //大客户类型 61201001战略大客户 61201002一般大客户
				//companyName  by wujinbiao  start
				ucCustomerPo.setString("COMPANY_NAME", wholeVo.getCompanyName());//公司的名字
				//end
				if(null != wholeVo.getIsSecondReport() && !"".equals(wholeVo.getIsSecondReport())){
					if(wholeVo.getIsSecondReport()== 12781001){
						ucCustomerPo.setInteger("CUSTOMER_COMPANY_APPLY_SOURCE", OemDictCodeConstants.IF_TYPE_YES); //是否资源申请
					}else{
						ucCustomerPo.setInteger("CUSTOMER_COMPANY_APPLY_SOURCE", OemDictCodeConstants.IF_TYPE_NO); //是否资源申请
					}
				}
				
				
				//联系人信息
				TtUcLinkmanPO ucLinkManPo = new TtUcLinkmanPO();
				ucLinkManPo.setString("DEALER_CODE", dealerCode); //经销商代码
				ucLinkManPo.setInteger("LM_TYPE", OemDictCodeConstants.IF_TYPE_YES);  //是否车主
				ucLinkManPo.setString("LM_NAME", wholeVo.getContactorName()); //联系人名称
				ucLinkManPo.setInteger("LM_CARD_TYPE", wholeVo.getCtCode()); //证件类型
				ucLinkManPo.setString("LM_CARD_NO", wholeVo.getCertificateNo()); //证件号码
				ucLinkManPo.setDate("LM_BIRTH_DAY", wholeVo.getLmbirthDay()); //生日
				ucLinkManPo.setInteger("LM_SEX", wholeVo.getLmSex()); //性别
				ucLinkManPo.setString("LM_CELLPHONE", wholeVo.getMobile()); //手机
				ucLinkManPo.setString("LM_TELEPHONE", wholeVo.getPhone()); //电话
				ucLinkManPo.setString("LM_EMAIL", wholeVo.getLmEmail()); //邮箱
				ucLinkManPo.setString("LM_FAX", wholeVo.getFax()); //传真
				if(Utility.testIsNotNull(wholeVo.getPositionName())){
					ucLinkManPo.setString("LM_POST", wholeVo.getPositionName()); //职位
				}else{
					ucLinkManPo.setString("LM_POST", "."); //职位
				}
				
				//报备基础信息
				TtBigCustomerFilingBaseInfoPO filingBasePO = new TtBigCustomerFilingBaseInfoPO();
				filingBasePO.setString("DEALER_CODE", dealerCode); //经销商代码
				filingBasePO.setString("WS_NO", wholeVo.getWsNo()); //报备单号 
				filingBasePO.setString("DLR_HEAD", wholeVo.getDlrPrincipal()); //经销商负责人
				filingBasePO.setString("DLR_HEAD_PHONE", wholeVo.getDlrPrincipalPhone()); //负责人电话
				filingBasePO.setInteger("PS_TYPE", wholeVo.getWsType()); //批售类型
				filingBasePO.setTimestamp("REPORT_DATE", wholeVo.getSubmitTime()); //报备日期
				filingBasePO.setString("REAMRK", wholeVo.getProjectRemark()); //报备备注
				String applyPic = wholeVo.getApplyPic();//申请表
				String applyPic1 = "";
				if(Utility.testIsNotNull(applyPic)){
					filingBasePO.setString("APPLY_PIC", wholeVo.getApplyPic()); //申请表原始图片
					applyPic1 = applyPic.substring(applyPic.lastIndexOf("/")+1, applyPic.length());
			//		applyPic1 = ImageResizer.resizeImage(applyPic1, 800, 800);  //弹出图片,  未完成
					filingBasePO.setString("APPLY_PIC1", applyPic1); //申请表压缩后图片
				}
				filingBasePO.setString("SALE_CONTRACT_PIC", wholeVo.getSaleContractPic()); //销售合同
				filingBasePO.setString("SALE_CONTRACT_PIC1", wholeVo.getContractFileAurl()); //销售合同A URL
				filingBasePO.setString("SALE_CONTRACT_PIC2", wholeVo.getContractFileBurl()); //销售合同B URL
				filingBasePO.setString("CUSTOMER_CARD_PIC", wholeVo.getCustomerCardPic()); //客户身份证明
				filingBasePO.setTimestamp("ESTIMATE_APPLY_TIME", wholeVo.getEstimateApplyTime());//预计申请时间
				//add by huyu 2015-6-01 start
				// 企业员工分类    15961001:一般大客户;15961002:知名企业 ；15961003：平安集团；  15961004：公务员；15961005：知名院校、医院；15961006：合作银行
				if(wholeVo.getWsType().toString().equals(OemDictCodeConstants.BIG_CUSTOMER_BATCH_SALE_TYPE_GROUP_BUY.toString())){//团购
					if(null != wholeVo.getCustomerKind()){
						switch(wholeVo.getCustomerKind()){
						    case 61201002:
						    	filingBasePO.setInteger("EMPLOYEE_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE_02);//知名企业
						    	break;
						    case 61201005:
						    	filingBasePO.setInteger("EMPLOYEE_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE_01);//一般大客户
						    	break;
						    case 61201008:
						    	filingBasePO.setInteger("EMPLOYEE_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE_05);//知名院校、医院
						    	break;
						    case 61201009:
						    	filingBasePO.setInteger("EMPLOYEE_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE_06);//合作银行
						    	break;
						    case 61201010:
						    	filingBasePO.setInteger("EMPLOYEE_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE_03);//平安集团
						    	break;
						    case 61201011:
						    	filingBasePO.setInteger("EMPLOYEE_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE_04);//公务员
						    	break;
						}
					}
				}else if(wholeVo.getWsType().toString().equals(OemDictCodeConstants.BIG_CUSTOMER_BATCH_SALE_TYPE_GROUP.toString())){//集团销售
					if(null != wholeVo.getCustomerKind()){
						switch(wholeVo.getCustomerKind()){
						    case 61201002:
						    	filingBasePO.setInteger("EMPLOYEE_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE1_01);//知名企业
						    	break;
						    case 61201003:
						    	filingBasePO.setInteger("EMPLOYEE_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE1_02);//酒店/航空
						    	break;
						    case 61201004:
						    	filingBasePO.setInteger("EMPLOYEE_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE1_03);//租赁
						    	break;
						    case 61201012:
						    	filingBasePO.setInteger("EMPLOYEE_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE1_04);//一般企业
						    	break;
						    case 61201013:
						    	filingBasePO.setInteger("EMPLOYEE_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE1_05);//政府
						    	break;
						}
					}
				}
				
				//客户细分类别
				if(null != wholeVo.getWsthreeType()){
					switch(wholeVo.getWsthreeType()){
						case 61301001:
							filingBasePO.setInteger("CUSTOMER_SUB_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE2_01);//腾讯集团
							break;
						case 61301002:
							filingBasePO.setInteger("CUSTOMER_SUB_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE2_02);//中石油中石化
							break;
						case 61301003:
							filingBasePO.setInteger("CUSTOMER_SUB_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE2_03);//平安集团
							break;
						case 61301004:
							filingBasePO.setInteger("CUSTOMER_SUB_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE2_04);//其他
							break;
						case 61301005:
							filingBasePO.setInteger("CUSTOMER_SUB_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE2_05);//建设银行
							break;
						case 61301006:
							filingBasePO.setInteger("CUSTOMER_SUB_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE2_06);//中国银行
							break;
						case 61301008:
							filingBasePO.setInteger("CUSTOMER_SUB_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE2_08);//中信银行
							break;
						case 61301009:
							filingBasePO.setInteger("CUSTOMER_SUB_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE2_09);//世界五百强
							break;
						case 61301010:
							filingBasePO.setInteger("CUSTOMER_SUB_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE2_10);//中国五百强
							break;
						case 61301011:
							filingBasePO.setInteger("CUSTOMER_SUB_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE2_11);//万科
							break;
						case 61301012:
							filingBasePO.setInteger("CUSTOMER_SUB_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE2_12);//华为
							break;
						case 61301013:
							filingBasePO.setInteger("CUSTOMER_SUB_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE2_13);//海尔
							break;
						case 61301014:
							filingBasePO.setInteger("CUSTOMER_SUB_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE2_14);//蓝色光标
							break;
						case 61301015:
							filingBasePO.setInteger("CUSTOMER_SUB_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE2_15);//中欧商学院
							break;
						case 61301016:
							filingBasePO.setInteger("CUSTOMER_SUB_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE2_16);//长江商学院
							break;
						case 61301017:
							filingBasePO.setInteger("CUSTOMER_SUB_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE2_17);//211院校
							break;	
						case 61301018:
							filingBasePO.setInteger("CUSTOMER_SUB_TYPE", OemDictCodeConstants.EMPLOYEE_TYPE2_18);//三甲医院
							break;	
					}
				}
				//end by huyu
				
				//审批信息
				TtBigCustomerReportApprovalPO reportApprovalPo = new TtBigCustomerReportApprovalPO();
				reportApprovalPo.setString("DEALER_CODE", dealerCode); //经销商代码
				reportApprovalPo.setTimestamp("REPORT_DATE", wholeVo.getSubmitTime()); //报备日期
				reportApprovalPo.setInteger("REPORT_APPROVAL_STATUS", OemDictCodeConstants.BIG_CUSTOMER_FILING_APPROVAL_TYPE_UNAPPROVED); //总部未审批
				
				String bigCustomerCode = "" ; //大客户代码
				if(null != baseInfoList && baseInfoList.size() > 0){
					bigCustomerCode = baseInfoList.get(0).get("CUSTOMER_COMPANY_CODE").toString(); //大客户代码
						
					//更新大客户信息
					TtUcCustomerPO keyUcCustomerPo = new TtUcCustomerPO();
					keyUcCustomerPo.setLong("CUSTOMER_ID", baseInfoList.get(0).get("CUSTOMER_ID")); //客户ID
					keyUcCustomerPo.setInteger("ENABLE", OemDictCodeConstants.STATUS_ENABLE);
					/***业务更改---报备单号+经销商确定唯一***/
					keyUcCustomerPo.setString("DEALER_CODE", dealerCode);
					
					ucCustomerPo.setLong("UPDATE_BY", DEConstant.DE_CREATE_BY);
					ucCustomerPo.setTimestamp("UPDATE_DATE", cal.getTime());
					
					int updateCustomerCount = sadcs012CloudDao.updateTtUcCustomer(keyUcCustomerPo, ucCustomerPo);
					
					logger.info("************* 成功更新报备单号("+wholeVo.getWsNo()+")的客户信息,影响行数 "+updateCustomerCount+"*******************");
					
					//更新联系人信息
					TtUcLinkmanPO keyUcLinkManPo = new TtUcLinkmanPO();
					keyUcLinkManPo.setLong("CUSTOMER_ID", baseInfoList.get(0).get("CUSTOMER_ID")); //客户ID
					keyUcLinkManPo.setInteger("ENABLE", OemDictCodeConstants.STATUS_ENABLE);
					/***业务更改---报备单号+经销商确定唯一***/
					keyUcLinkManPo.setString("DEALER_CODE", dealerCode);
					
					ucLinkManPo.setLong("UPDATE_BY", DEConstant.DE_CREATE_BY);
					ucLinkManPo.setTimestamp("UPDATE_DATE", cal.getTime()); 
					int updateUcLinkManCount = sadcs012CloudDao.updateTtUcLinkman(keyUcLinkManPo, ucLinkManPo);
					logger.info("************* 成功更新报备单号("+wholeVo.getWsNo()+")的联系人信息,影响行数 "+updateUcLinkManCount+"*******************");
					
					//更新报备基础信息
					filingBasePO.setLong("UPDATE_BY", DEConstant.DE_CREATE_BY);
					filingBasePO.setTimestamp("UPDATE_DATE", cal.getTime());
					int updateFilingCount = sadcs012CloudDao.updateTtBigCustomerFilingBaseInfo(keyFilingBasePO,filingBasePO);
					logger.info("************* 成功更新报备单号("+wholeVo.getWsNo()+")的报备基础信息,影响行数 "+updateFilingCount+"*******************");
					
					//更新审批信息
					TtBigCustomerReportApprovalPO keyReportApprovalPo = new TtBigCustomerReportApprovalPO();
					keyReportApprovalPo.setString("WS_NO", wholeVo.getWsNo()); //大客户ID
					keyReportApprovalPo.setInteger("ENABLE", OemDictCodeConstants.STATUS_ENABLE);
					/***业务更改---报备单号+经销商确定唯一***/
					keyReportApprovalPo.setString("DEALER_CODE", dealerCode);
					
					reportApprovalPo.setLong("UPDATE_BY", DEConstant.DE_CREATE_BY);
					reportApprovalPo.setTimestamp("UPDATE_DATE", cal.getTime());
					int upApprovalCount = sadcs012CloudDao.updateTtBigCustomerReportApproval(keyReportApprovalPo, reportApprovalPo);
					logger.info("************* 成功更新报备单号("+wholeVo.getWsNo()+")的审批信息,影响行数 "+upApprovalCount+"*******************");
				}else{
					long customerId = sadcs012CloudDao.getCurrentTableMaxPK("CUSTOMER_ID", "tt_uc_customer");
					customerId += 1;
					bigCustomerCode = String.valueOf(customerId);
					//写入大客户信息
					ucCustomerPo.setLong("CUSTOMER_ID", customerId); //主键
					ucCustomerPo.setString("CUSTOMER_COMPANY_CODE", bigCustomerCode); //大客户代码
					ucCustomerPo.setString("DEALER_CODE", dealerCode);
					ucCustomerPo.setInteger("ENABLE", OemDictCodeConstants.STATUS_ENABLE); //是否启用 -是
					ucCustomerPo.setLong("CREATE_BY", DEConstant.DE_CREATE_BY); //创建人
					ucCustomerPo.setTimestamp("CREATE_DATE", cal.getTime()); //创建时间
					ucCustomerPo.saveIt();
					logger.info("*************************** 成功写入大客户信息表(TT_UC_CUSTOMER) ******************************");
					
					//写入大客户联系人信息表(TT_UC_LINKMAN)
					/*long lmId = sadcs012CloudDao.getCurrentTableMaxPK("LM_ID", "tt_uc_linkman");  //联系人主键
					lmId += 1;*/
			//		ucLinkManPo.setLong("LM_ID", lmId); //联系人主键
					ucLinkManPo.setLong("CUSTOMER_ID", customerId); //客户ID
					ucLinkManPo.setString("DEALER_CODE", dealerCode); //经销商代码
					ucLinkManPo.setInteger("ENABLE", OemDictCodeConstants.STATUS_ENABLE); //是否启用-是
					ucLinkManPo.setLong("CREATE_BY", DEConstant.DE_CREATE_BY); //创建人
					ucLinkManPo.setTimestamp("CREATE_DATE", cal.getTime()); //创建时间
					ucLinkManPo.saveIt();
					logger.info("***************************成功写入大客户联系人信息表(TT_UC_LINKMAN) ******************************");
					
					//写入大客户报备基础信息表(TT_BIG_CUSTOMER_FILING_BASE_INFO)
				//	filingBasePO.setLong("BASE_INFO_ID", factory.getLongPK(filingBasePO));
					filingBasePO.setString("CUSTOMER_COMPANY_CODE", bigCustomerCode); //大客户代码
					filingBasePO.setLong("CUSTOMER_ID", customerId); //大客户ID
					filingBasePO.setInteger("ENABLE", OemDictCodeConstants.STATUS_ENABLE);
					filingBasePO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY); //创建人
					filingBasePO.setTimestamp("CREATE_DATE", cal.getTime()); //创建时间
					filingBasePO.saveIt();
					logger.info("*************************** 写入大客户报备基础信息表(TT_BIG_CUSTOMER_FILING_BASE_INFO)************");
					
					//写入大客户报备审批信息表(tt_big_customer_report_approval)
			//		reportApprovalPo.setLong("REPORT_APPROVAL_ID", factory.getLongPK(reportApprovalPo)); //主键
					reportApprovalPo.setString("CUSTOMER_COMPANY_CODE", bigCustomerCode); //大客户代码
					reportApprovalPo.setString("WS_NO", wholeVo.getWsNo()); //报备单号
					reportApprovalPo.setInteger("ENABLE", OemDictCodeConstants.STATUS_ENABLE); 
					reportApprovalPo.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);
					reportApprovalPo.setTimestamp("CREATE_DATE", cal.getTime());
					reportApprovalPo.saveIt();
					logger.info("*************************** END * 成功写入大客户报备信息表(TT_BIG_CUSTOMER_FILING_BASE_INFO)************");
					
				}
				
				logger.info("*************************** START * 写入大客户批售车辆信息表(TT_BIG_CUSTOMER_FILING_BASE_INFO)************");
				LinkedList<WsConfigInfoClryslerDto> configList = wholeVo.getConfigList();
		//		List<TtBigCustomerBatchSaleInfoPO> salesList = new ArrayList<TtBigCustomerBatchSaleInfoPO>();
				TtBigCustomerBatchSaleInfoPO batchSalePo = null;
				if (null != configList && configList.size() > 0){
					//删除批售车辆信息(说明再次上报时候,车辆信息有变更,先删后写)
					TtBigCustomerBatchSaleInfoPO keyBatchSalePo = new TtBigCustomerBatchSaleInfoPO();
					keyBatchSalePo.setString("WS_NO", wholeVo.getWsNo());
					keyBatchSalePo.setInteger("ENABLE", OemDictCodeConstants.STATUS_ENABLE);
					/***业务更改---报备单号+经销商确定唯一***/
					keyBatchSalePo.setString("DEALER_CODE", dealerCode);
					
					int delBatchSaleCount = TtBigCustomerBatchSaleInfoPO.delete(" WS_NO=? AND ENABLE=? AND DEALER_CODE=? ", wholeVo.getWsNo(),OemDictCodeConstants.STATUS_ENABLE,dealerCode);
					logger.info("************* 成功删除报备单号("+wholeVo.getWsNo()+")的批售车辆信息,影响行数 "+delBatchSaleCount+"*******************");
					
					int psSourceApplyNumCount = 0;
					for(WsConfigInfoClryslerDto clryslerVO : configList){
						batchSalePo = new TtBigCustomerBatchSaleInfoPO();
				//		batchSalePo.setLong("BATCH_SALE_VHCL_ID", factory.getLongPK(batchSalePo)); //主键
						batchSalePo.setString("DEALER_CODE", dealerCode); //经销商代码
						batchSalePo.setString("CUSTOMER_COMPANY_CODE", bigCustomerCode); //大客户代码
						batchSalePo.setString("WS_NO", wholeVo.getWsNo()); //报备单号
						batchSalePo.setString("SALE_VHCL_BRAND", clryslerVO.getBrandCode()); //品牌
						batchSalePo.setString("SALE_VHCL_SERIES", clryslerVO.getSeriesCode()); //车系
						batchSalePo.setString("SALE_VHCL_MODEL", clryslerVO.getModelCode()); //车型
						batchSalePo.setString("SALE_VHCL_PACKAGE", clryslerVO.getConfigCode()); // 配置
						batchSalePo.setString("SALE_VHCL_PACKAGE_NAME", clryslerVO.getConfigName()); // 配置名称
						batchSalePo.setString("SALE_VHCL_COLOR", clryslerVO.getColorCode()); //颜色
						batchSalePo.setInteger("PS_SOURCE_APPLY_NUM", clryslerVO.getPurchaseCount()); //批售资源数-报备数量
						if(clryslerVO.getPurchaseCount()!= null){
							psSourceApplyNumCount += clryslerVO.getPurchaseCount();//总报备数
						}else{
							psSourceApplyNumCount += 1;
						}
						batchSalePo.setInteger("ENABLE", OemDictCodeConstants.STATUS_ENABLE); //是否启用-是
						batchSalePo.setLong("CREATE_BY", DEConstant.DE_CREATE_BY); //创建人
						batchSalePo.setTimestamp("CREATE_DATE", cal.getTime()); //创建时间
						if (clryslerVO.getIsResApply() != null) { // 是否资源申请
							if (clryslerVO.getIsResApply().equals(12781001)) {
								batchSalePo.setInteger("IS_RES_APPLY", OemDictCodeConstants.IF_TYPE_YES);//是
							} else if(clryslerVO.getIsResApply().equals(12781002)){
								batchSalePo.setInteger("IS_RES_APPLY", OemDictCodeConstants.IF_TYPE_NO);//否
							}
						}
				//		salesList.add(batchSalePo);
						batchSalePo.saveIt();
					}
			//		factory.insert(salesList);
					//更新审批信息-报备总数 modify by huyu
					TtBigCustomerReportApprovalPO keyPo = new TtBigCustomerReportApprovalPO();
					keyPo.setString("WS_NO", wholeVo.getWsNo());
					keyPo.setInteger("ENABLE", OemDictCodeConstants.STATUS_ENABLE);
					/***业务更改---报备单号+经销商确定唯一***/
					keyPo.setString("DEALER_CODE", dealerCode);
					TtBigCustomerReportApprovalPO po = new TtBigCustomerReportApprovalPO();
					po.setInteger("PS_SOURCE_APPLY_NUM_COUNT", psSourceApplyNumCount);//总报备数
			//		factory.update(keyPo, po);
					sadcs012CloudDao.updateTtBigCustomerReportApproval(keyPo, po);
					//end
				}
				logger.info("*************************** END * 成功写入大客户批售车辆信息表(TT_BIG_CUSTOMER_FILING_BASE_INFO)************");

			}
			logger.info("######################## 成功处理大客户报备上传数据 ######################## ");
		}
		
	

}
