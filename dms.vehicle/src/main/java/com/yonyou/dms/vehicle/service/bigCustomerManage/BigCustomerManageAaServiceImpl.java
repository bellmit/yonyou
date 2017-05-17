package com.yonyou.dms.vehicle.service.bigCustomerManage;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yonyou.dcs.gacfca.SADCS015Cloud;
import com.yonyou.dms.common.domains.DTO.basedata.TtBigCustomerReportApprovalHisPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerAuthorityApprovalPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerRebateApprovalHisPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerReportApprovalPO;
import com.yonyou.dms.common.domains.PO.customer.TtBigCustomerRebateApprovalPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.dao.bigCustomerManage.BigCustomerManageAaDao;
import com.yonyou.dms.vehicle.domains.DTO.bigCustomer.TtBigCustomerAuthorityApprovalDTO;
import com.yonyou.dms.vehicle.domains.DTO.bigCustomer.TtBigCustomerReportApprovalDTO;
import com.yonyou.dms.vehicle.domains.PO.bigCustomer.TtBigCustomerAuthorityApprovalHistoryPO;
import com.yonyou.dms.vehicle.domains.PO.bigCustomer.TtBigCustomerPolicyFilePO;

import com.yonyou.f4.filestore.FileStore;

/**
 * 大客户管理
 * 
 * @author ZhaoZ
 * @date 2017年3月10日 updateBy zhengzengiang
 */
@SuppressWarnings("rawtypes")
@Service
public class BigCustomerManageAaServiceImpl implements BigCustomerManageAaService {

	@Autowired
	BigCustomerManageAaDao customerdao;

	@Autowired
	private FileStoreService fileStoreService;
	@Autowired
	SADCS015Cloud sadcs015cloud;
	// 定义文件存储的实现类
	@Autowired
	FileStore fileStore;

	/**
	 * 根据状态查询大客户
	 */
	@Override
	public PageInfoDto QueryCustomerByStatus(Map<String, String> queryParams, int flag) throws ServiceBizException {

		return customerdao.QueryCustomer(queryParams, flag);
	}

	/**
	 * 大客户报备审批信息下载
	 */
	@Override
	public List<Map> dlrFilingInfoExport(Map<String, String> queryParams, int flag) throws ServiceBizException {
		return customerdao.dlrFilingInfoExport(queryParams, flag);
	}

	/**
	 * 大客户报备审批明细信息下载
	 */
	@Override
	public List<Map> dlrFilingInfoDetailExport(Map<String, String> queryParams, int flag) throws ServiceBizException {
		return customerdao.dlrFilingInfoDetailExport(queryParams, flag);
	}

	@Override
	public PageInfoDto getCustomerBatchVhclInfoS(String wsno, TtBigCustomerReportApprovalPO tbcraPO)
			throws ServiceBizException {
		return customerdao.customerBatchVhclInfoS(wsno, tbcraPO);
	}

	@Override
	public PageInfoDto QueryapplyforQuery(Map<String, String> queryParams, int type) throws ServiceBizException {

		return customerdao.applyforQuerys(queryParams, type);
	}

	/**
	 * 大客户申请审批信息下载
	 */
	@Override
	public List<Map> dlrRebateApprovalInfoExport(Map<String, String> queryParams, int flag) throws ServiceBizException {
		return customerdao.rebateApprovalInfoExport(queryParams, flag);
	}

	/**
	 * 大客户申请明细下载
	 */
	@Override
	public List<Map> reBateApprovalInfoDownLoadDetail(Map<String, String> queryParams) {
		return customerdao.rebateApprovalInfoDetailExport(queryParams);
	}

	@Override
	public Map<String, Object> QueryCustomerByStatus(String wsno, int flag) throws ServiceBizException {

		return customerdao.QueryCustomer(wsno, flag);
	}

	/**
	 * 查询客户相关信息
	 */
	@Override
	public Map<String, Object> getCustomerInfo(String customerCode, String dealerCode, String wsno)
			throws ServiceBizException {
		return customerdao.getCustomerInfos(customerCode, dealerCode, wsno);
	}

	/**
	 * 查询审批信息
	 */
	@Override
	public PageInfoDto getApprovalHisInfos(String wsno, String customerCode) throws ServiceBizException {
		return customerdao.approvalHisInfos(wsno, customerCode);
	}
	
	
	/**
	 * 查询审批信息
	 */
	@Override
	public PageInfoDto getApprovalHisInfos1(String wsno, String customerCode) throws ServiceBizException {
		return customerdao.approvalHisInfos1(wsno, customerCode);
	}

	/**
	 * 功能描述：保存大客户报备审批信息
	 */
	@Override
	public void saveApprovalInfos(TtBigCustomerReportApprovalDTO dto, String wsno) throws ServiceBizException {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtBigCustomerReportApprovalPO tbcraPO = TtBigCustomerReportApprovalPO.findFirst("WS_NO LIKE ?", wsno);
		if (StringUtils.isNullOrEmpty(tbcraPO.getString("CUSTOMER_COMPANY_CODE"))) {
			throw new ServiceBizException("大客户代码为空,请联系管理员！");
		}
		if (StringUtils.isNullOrEmpty(wsno)) {
			throw new ServiceBizException("报备单号为空,请联系管理员!");
		}
		if (StringUtils.isNullOrEmpty(dto.getReportApprovalStatus())) {
			throw new ServiceBizException("审批状态为空,请联系管理员!");
		}

		if ("15980002".equals(dto.getReportApprovalStatus().toString())) {
			tbcraPO.setInteger("REPORT_APPROVAL_STATUS", 15980002);
		}
		if ("15980003".equals(dto.getReportApprovalStatus().toString())) {
			tbcraPO.setInteger("REPORT_APPROVAL_STATUS", 15980003);
		}
		if ("15980004".equals(dto.getReportApprovalStatus().toString())) {
			tbcraPO.setInteger("REPORT_APPROVAL_STATUS", 15980004);
		}
		tbcraPO.setString("REPORT_APPROVAL_REMARK", dto.getReportApprovalRemark());
		tbcraPO.setDate("REPORT_APPROVAL_DATE", new Date(System.currentTimeMillis()));
		tbcraPO.setBigDecimal("REPORT_APPROVAL_USER_ID", loginUser.getUserId());
		tbcraPO.setDate("UPDATE_DATE", new Date(System.currentTimeMillis()));
		tbcraPO.setBigDecimal("UPDATE_BY", loginUser.getUserId());
		boolean flag = false;
		flag = tbcraPO.saveIt();
		if (!flag) {
			throw new ServiceBizException("审批失败,请联系管理员!");
		}
		TtBigCustomerReportApprovalHisPO bigCustomerApprovalHisPo = new TtBigCustomerReportApprovalHisPO();
		bigCustomerApprovalHisPo.setId(tbcraPO.getBigDecimal("REPORT_APPROVAL_ID"));
		bigCustomerApprovalHisPo.setString("CUSTOMER_COMPANY_CODE", tbcraPO.getString("CUSTOMER_COMPANY_CODE"));
		bigCustomerApprovalHisPo.setString("WS_NO", wsno);
		bigCustomerApprovalHisPo.setInteger("REPORT_APPROVAL_STATUS", dto.getReportApprovalStatus());
		bigCustomerApprovalHisPo.setString("REPORT_APPROVAL_REMARK", dto.getReportApprovalRemark());
		bigCustomerApprovalHisPo.setDate("REPORT_APPROVAL_DATE", new Date(System.currentTimeMillis()));
		bigCustomerApprovalHisPo.setDate("CREATE_DATE", new Date(System.currentTimeMillis()));
		System.out.println(loginUser.getUserId());
		bigCustomerApprovalHisPo.setBigDecimal("REPORT_APPROVAL_USER_ID", loginUser.getUserId());
		bigCustomerApprovalHisPo.setBigDecimal("CREATE_BY", loginUser.getUserId());
		
		flag = bigCustomerApprovalHisPo.saveIt();	

		if (!flag) {
			throw new ServiceBizException("审批历史插入失败!");
		}
	}
	
	
	/**
	 * 功能描述：保存大客户申请审批信息(申请未审批)
	 */
	
	@Override
	public void saveApprovalInfos1(TtBigCustomerReportApprovalDTO dto, String wsno) throws ServiceBizException {
				LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				TtBigCustomerReportApprovalPO tbcraPO = TtBigCustomerReportApprovalPO.findFirst("WS_NO LIKE ?", wsno);
				if (StringUtils.isNullOrEmpty(tbcraPO.getString("CUSTOMER_COMPANY_CODE"))) {
					throw new ServiceBizException("大客户代码为空,请联系管理员！");
				}
				if (StringUtils.isNullOrEmpty(wsno)) {
					throw new ServiceBizException("报备单号为空,请联系管理员!");
				}
				if (StringUtils.isNullOrEmpty(dto.getReportApprovalStatus())) {
					throw new ServiceBizException("审批状态为空,请联系管理员!");
				}

	          /*  TtBigCustomerRebateApprovalPO bigCustomerApprovalKey = new TtBigCustomerRebateApprovalPO(); 
	            bigCustomerApprovalKey.setString("BIG_CUSTOMER_CODE",tbcraPO.getString("CUSTOMER_COMPANY_CODE")); //大客户代码
	            bigCustomerApprovalKey.setString("WS_NO", wsno);//报备单号
	            //未审批
	            if ("15950001".equals(dto.getReportApprovalStatus().toString())) {
	            	bigCustomerApprovalKey.setInteger("REPORT_APPROVAL_STATUS", 15950001);
	    		}*/
				
				String bigCustomerCode = tbcraPO.getString("CUSTOMER_COMPANY_CODE");
				String dealerCode = dto.getDealerCode();
				 List<TtBigCustomerRebateApprovalPO> rebateApproval  = null;
				 //查询出申请未审批的数据
				if(!StringUtils.isNullOrEmpty(dealerCode)) {
					  rebateApproval  = TtBigCustomerRebateApprovalPO.findBySQL("SELECT * FROM tt_big_customer_rebate_approval WHERE BIG_CUSTOMER_CODE ='"+bigCustomerCode+"' AND WS_NO= '"+wsno+"' AND REBATE_APPROVAL_STATUS='"+15950001+"' AND ENABLE = '"+10011001+"' AND DealerCode= '"+dealerCode+"' ", null);
				}else{
					  rebateApproval  = TtBigCustomerRebateApprovalPO.findBySQL("SELECT * FROM tt_big_customer_rebate_approval WHERE BIG_CUSTOMER_CODE ='"+bigCustomerCode+"' AND WS_NO= '"+wsno+"' AND REBATE_APPROVAL_STATUS='"+15950001+"' AND ENABLE = '"+10011001+"'  ", null);
				}
	           
	          
	            
	            /************************************** 防止同时审批 *************************************************/
	            //List<TtBigCustomerRebateApprovalPO> rebateApproval = factory.select(bigCustomerApprovalKey);
	            if(null != rebateApproval && rebateApproval.size() > 0 ){
	            	/************************************** 更新大客户返利审批表 *************************************************/
	               
	                
	                TtBigCustomerRebateApprovalPO updateBigCustomerPo = new TtBigCustomerRebateApprovalPO();
	                 //上传附件
	                /*if(approvalStatus.equals(Constant.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_PASS.toString())){
	                	FileObject fo = (FileObject)request.getParamObject("approval_file");
	                	if(null!=fo && fo.getLength()>0){
	                    	FileStore fs = FileStore.getInstance();
	        				fidStr = fs.write(fo.getFileName(), fo.getContent()); //上传到服务器并返回地址
	                    }
	                	//else{
	                    	//throw new Exception("审批通过附件为空,请检查！");
	                    //}
	                	//updateBigCustomerPo.setApprovalFile(FileStore.getInstance().getDomainURL(fidStr));
	                	updateBigCustomerPo.setApprovalFile(fidStr);
	                }*/
	                
	                // 第二次驳回时直接将“驳回转拒绝”的状态下发给经销商
	                int overNumber = 0;
	                if(dto.getReportApprovalStatus().equals(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER.toString())){
	                	overNumber = rebateApproval.get(0).getInteger("OVER_NUMBER");
	                    updateBigCustomerPo.setInteger("OVER_NUMBER", overNumber+1);
	                }
	                if(overNumber >= 2){
	                	updateBigCustomerPo.setString("REBATE_APPROVAL_STATUS", OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER_RUS);
	                	updateBigCustomerPo.setString("REBATE_APPROVAL_REMARK", OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER_RUS_REMARK);
	                }else{
	                	updateBigCustomerPo.setString("REBATE_APPROVAL_STATUS", dto.getReportApprovalStatus());
	                	updateBigCustomerPo.setString("REBATE_APPROVAL_REMARK", dto.getReportApprovalRemark());
	                }
	                //end
	                updateBigCustomerPo.setDate("REBATE_APPROVAL_DATE", new Date(System.currentTimeMillis()));
	                updateBigCustomerPo.setBigDecimal("REBATE_APPROVAL_USER_ID", loginUser.getUserId());
	                updateBigCustomerPo.setBigDecimal("UPDATE_BY", loginUser.getUserId());
	                updateBigCustomerPo.setDate("UPDATE_DATE", new Date(System.currentTimeMillis()));
	                boolean flag = false;
	        		flag = updateBigCustomerPo.saveIt();
	        		if (!flag) {
	        			throw new ServiceBizException("审批失败,更新大客户按失败！");
	        		}
	                
	                /************************************** 写入大客户返利审批历史表 *************************************************/
	                TtBigCustomerRebateApprovalHisPO bigCustomerApprovalHisPo = new TtBigCustomerRebateApprovalHisPO();
	                //bigCustomerApprovalHisPo.setApprovalHisId(factory.getLongPK(bigCustomerApprovalHisPo));
	                bigCustomerApprovalHisPo.setString("BIG_CUSTOMER_CODE", bigCustomerCode);
	                bigCustomerApprovalHisPo.setString("WS_NO", wsno);
	                if(overNumber >= 2){
	                	//驳回次数超过二次状态自动改为驳回转拒绝
	                	bigCustomerApprovalHisPo.setString("REBATE_APPROVAL_STATUS", OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER_RUS);
	                	bigCustomerApprovalHisPo.setString("REBATE_APPROVAL_REMARK", OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER_RUS_REMARK);
	                }else{
	                	bigCustomerApprovalHisPo.setString("REBATE_APPROVAL_STATUS", dto.getReportApprovalStatus());
	                	bigCustomerApprovalHisPo.setString("REBATE_APPROVAL_REMARK", dto.getReportApprovalRemark());
	                }
	                bigCustomerApprovalHisPo.setDate("REBATE_APPROVAL_DATE", new Date(System.currentTimeMillis()));
	                bigCustomerApprovalHisPo.setBigDecimal("REBATE_APPROVAL_USER_ID", loginUser.getUserId());
	                bigCustomerApprovalHisPo.setString("ENABLE", "10011001");
	                bigCustomerApprovalHisPo.setBigDecimal("UPDATE_BY", loginUser.getUserId());
	                bigCustomerApprovalHisPo.setDate("UPDATE_DATE", new Date(System.currentTimeMillis()));
	        		flag = bigCustomerApprovalHisPo.saveIt();
	        		if (!flag) {
	        			throw new ServiceBizException("审批失败,更新大客户审批历史失败！");
	        		}
	            }else{
	            	  throw new ServiceBizException("无法进行审批,请确认是否已被审批过!");
	            }
	}
	
	
	/**
	 * 功能描述：保存大客户申请审批信息(资料完整待签字)
	 */
	
	@Override
	public void saveApprovalInfos2(TtBigCustomerReportApprovalDTO dto, String wsno) throws ServiceBizException {
		
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtBigCustomerReportApprovalPO tbcraPO = TtBigCustomerReportApprovalPO.findFirst("WS_NO LIKE ?", wsno);
		if (StringUtils.isNullOrEmpty(tbcraPO.getString("CUSTOMER_COMPANY_CODE"))) {
			throw new ServiceBizException("大客户代码为空,请联系管理员！");
		}
		if (StringUtils.isNullOrEmpty(wsno)) {
			throw new ServiceBizException("报备单号为空,请联系管理员!");
		}
		if (StringUtils.isNullOrEmpty(dto.getReportApprovalStatus())) {
			throw new ServiceBizException("审批状态为空,请联系管理员!");
		}	
		String bigCustomerCode = tbcraPO.getString("CUSTOMER_COMPANY_CODE");
		String dealerCode = dto.getDealerCode();
		 List<TtBigCustomerRebateApprovalPO> rebateApproval  = null;
		//查询出资料完成待签字的数据
		if(!StringUtils.isNullOrEmpty(dealerCode)) {
			  rebateApproval  = TtBigCustomerRebateApprovalPO.findBySQL("SELECT * FROM tt_big_customer_rebate_approval WHERE BIG_CUSTOMER_CODE ='"+bigCustomerCode+"' AND WS_NO= '"+wsno+"' AND REBATE_APPROVAL_STATUS='"+15950005+"' AND ENABLE = '"+10011001+"' AND Dealer_Code= '"+dealerCode+"' ", null);
		}else{
			  rebateApproval  = TtBigCustomerRebateApprovalPO.findBySQL("SELECT * FROM tt_big_customer_rebate_approval WHERE BIG_CUSTOMER_CODE ='"+bigCustomerCode+"' AND WS_NO= '"+wsno+"' AND REBATE_APPROVAL_STATUS='"+15950005+"' AND ENABLE = '"+10011001+"'  ", null);
		}        
        /************************************** 防止同时审批 *************************************************/
        if(null != rebateApproval && rebateApproval.size() > 0 ){
            /************************************** 更新大客户返利审批表 *************************************************/ 
            TtBigCustomerRebateApprovalPO updateBigCustomerPo = rebateApproval.get(0);
           /*  //上传附件
            if(approvalStatus.equals(Constant.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_PASS.toString())){
            	FileObject fo = (FileObject)request.getParamObject("approval_file");
            	if(null!=fo && fo.getLength()>0){
                	FileStore fs = FileStore.getInstance();
    				fidStr = fs.write(fo.getFileName(), fo.getContent()); //上传到服务器并返回地址
                }
            	//else{
                	//throw new Exception("审批通过附件为空,请检查！");
                //}
            	//updateBigCustomerPo.setApprovalFile(FileStore.getInstance().getDomainURL(fidStr));
            	updateBigCustomerPo.setApprovalFile(fidStr);
            }*/
            
            // 第二次驳回时直接将“驳回转拒绝”的状态下发给经销商
            int overNumber = 0;
            if(dto.getReportApprovalStatus().toString().equals(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER.toString())){
            	overNumber = rebateApproval.get(0).getInteger("OVER_NUMBER");
                updateBigCustomerPo.setInteger("OVER_NUMBER", overNumber+1);
            }
            //驳回次数超过二次状态自动改为驳回转拒绝
            if(overNumber >= 2){
            	updateBigCustomerPo.setString("REBATE_APPROVAL_STATUS", OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER_RUS);
            	updateBigCustomerPo.setString("REBATE_APPROVAL_REMARK", OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER_RUS_REMARK);
            }else{
            	updateBigCustomerPo.setString("REBATE_APPROVAL_STATUS", dto.getReportApprovalStatus());
            	updateBigCustomerPo.setString("REBATE_APPROVAL_REMARK", dto.getReportApprovalRemark());
            }
            //end
            updateBigCustomerPo.setDate("REBATE_APPROVAL_DATE", new Date(System.currentTimeMillis()));
            updateBigCustomerPo.setBigDecimal("REBATE_APPROVAL_USER_ID", loginUser.getUserId());
            updateBigCustomerPo.setBigDecimal("UPDATE_BY", loginUser.getUserId());
            updateBigCustomerPo.setDate("UPDATE_DATE", new Date(System.currentTimeMillis()));
            boolean flag = false;
    		flag = updateBigCustomerPo.saveIt();
    		if (!flag) {
    			throw new ServiceBizException("审批失败,更新大客户按失败！");
    		}
            
            /************************************** 写入大客户返利审批历史表 *************************************************/
            TtBigCustomerRebateApprovalHisPO bigCustomerApprovalHisPo = new TtBigCustomerRebateApprovalHisPO();
            bigCustomerApprovalHisPo.setString("BIG_CUSTOMER_CODE", bigCustomerCode);
            bigCustomerApprovalHisPo.setString("WS_NO", wsno);
            //驳回次数超过二次状态自动改为驳回转拒绝
            if(overNumber >= 2){
            	bigCustomerApprovalHisPo.setString("REBATE_APPROVAL_STATUS", OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER_RUS);
            	bigCustomerApprovalHisPo.setString("REBATE_APPROVAL_REMARK", OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER_RUS_REMARK);
            }else{
            	bigCustomerApprovalHisPo.setString("REBATE_APPROVAL_STATUS", dto.getReportApprovalStatus());
            	bigCustomerApprovalHisPo.setString("REBATE_APPROVAL_REMARK", dto.getReportApprovalRemark());
            }
            bigCustomerApprovalHisPo.setDate("REBATE_APPROVAL_DATE", new Date(System.currentTimeMillis()));
            bigCustomerApprovalHisPo.setBigDecimal("REBATE_APPROVAL_USER_ID", loginUser.getUserId());
            bigCustomerApprovalHisPo.setString("ENABLE", "10011001");
            bigCustomerApprovalHisPo.setBigDecimal("CREATE_BY", loginUser.getUserId());
            bigCustomerApprovalHisPo.setDate("CREATE_DATE", new Date(System.currentTimeMillis()));
    		flag = bigCustomerApprovalHisPo.saveIt();
    		if (!flag) {
    			throw new ServiceBizException("审批失败,更新大客户审批历史失败！");
    		}
        }else{
        	  throw new ServiceBizException("无法进行审批,请确认是否已被审批过!");
        }
	}
	
	/**
	 * 大客户激活
	 * @param dto
	 * @param wsno
	 */
	@Override
	public void activationApprovalInfos(TtBigCustomerReportApprovalDTO dto, String wsno) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtBigCustomerRebateApprovalPO updateBigCustomerPo = TtBigCustomerRebateApprovalPO.findFirst("WS_NO LIKE ?",
				wsno);

		if (StringUtils.isNullOrEmpty(updateBigCustomerPo.getString("BIG_CUSTOMER_CODE"))) {
			throw new ServiceBizException("大客户代码为空,请联系管理员！");
		}
		if (StringUtils.isNullOrEmpty(wsno)) {
			throw new ServiceBizException("报备单号为空,请联系管理员!");
		}

		if (updateBigCustomerPo.getInteger("REBATE_APPROVAL_STATUS")
				.equals(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER_RUS.toString())) {
			updateBigCustomerPo.setBigDecimal("OVER_NUMBER", 0);// 重置驳回次数
		}

		updateBigCustomerPo.setInteger("REBATE_APPROVAL_STATUS",
				OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER);
		updateBigCustomerPo.setString("REBATE_APPROVAL_REMARK",
				OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_SYSTEM_OVER_REMARK);
		updateBigCustomerPo.setDate("ACTIVITY_DATE", new Date(System.currentTimeMillis()));
		updateBigCustomerPo.setBigDecimal("REBATE_APPROVAL_USER_ID", loginUser.getUserId());
		updateBigCustomerPo.setDate("REBATE_APPROVAL_DATE", new Date(System.currentTimeMillis()));
		updateBigCustomerPo.setBigDecimal("UPDATE_BY", loginUser.getUserId());
		updateBigCustomerPo.setDate("UPDATE_DATE", new Date(System.currentTimeMillis()));
		boolean flag = false;
		flag = updateBigCustomerPo.saveIt();
		if (!flag) {
			throw new ServiceBizException("审批失败,请联系管理员!");
		}
		TtBigCustomerRebateApprovalHisPO bigCustomerApprovalHisPo = new TtBigCustomerRebateApprovalHisPO();
		bigCustomerApprovalHisPo.setId(updateBigCustomerPo.getId());
		bigCustomerApprovalHisPo.setString("BIG_CUSTOMER_CODE", updateBigCustomerPo.getString("BIG_CUSTOMER_CODE"));
		bigCustomerApprovalHisPo.setDate("ACTIVITY_DATE", new Date(System.currentTimeMillis()));
		bigCustomerApprovalHisPo.setString("WS_NO", wsno);
		bigCustomerApprovalHisPo.setInteger("REBATE_APPROVAL_STATUS",
				OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER);
		bigCustomerApprovalHisPo.setString("REBATE_APPROVAL_REMARK",
				OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_SYSTEM_OVER_REMARK);
		bigCustomerApprovalHisPo.setDate("REBATE_APPROVAL_DATE", new Date(System.currentTimeMillis()));
		bigCustomerApprovalHisPo.setBigDecimal("REBATE_APPROVAL_USER_ID", loginUser.getUserId());
		bigCustomerApprovalHisPo.setInteger("ENABLE", OemDictCodeConstants.STATUS_ENABLE);
		bigCustomerApprovalHisPo.setBigDecimal("CREATE_BY", loginUser.getUserId());
		bigCustomerApprovalHisPo.setDate("CREATE_DATE", new Date(System.currentTimeMillis()));
		flag = bigCustomerApprovalHisPo.saveIt();
		if (!flag) {
			throw new ServiceBizException("审批历史插入失败!");
		}
	}

	/**
	 * 查询大客户的信息
	 */
	@Override
	public PageInfoDto queryBigCustomerPolicy(Map<String, String> queryParams, LoginInfoDto loginUser)
			throws ServiceBizException {

		return customerdao.queryBigCustomer(queryParams, loginUser);
	}

	/**
	 * 功能描述：查询大客户组织架构权限审批信息
	 */
	@Override
	public PageInfoDto queryBigCustomerAuthorityApproval(Map<String, String> queryParams) throws ServiceBizException {
		return customerdao.findAuthorityApproval(queryParams);
	}

	/**
	 * 大客户组织架构权限审批明细页面
	 */
	@Override
	public Map<String, Object> approvalDetailPre(BigDecimal id) throws ServiceBizException {
		return customerdao.getAuthorityApprovalInfo(id);
	}

	/**
	 * 审核历史
	 */
	@Override
	public PageInfoDto approvalHis(BigDecimal id) throws ServiceBizException {
		return customerdao.getApprovalHisInfo(id);
	}

	public void saveApprovalInfo(BigDecimal id, TtBigCustomerAuthorityApprovalDTO dto) throws ServiceBizException {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtBigCustomerAuthorityApprovalPO updateBigCustomerPo = TtBigCustomerAuthorityApprovalPO.findById(id);
		TtBigCustomerAuthorityApprovalHistoryPO bigCustomerApprovalHisPo = new TtBigCustomerAuthorityApprovalHistoryPO();
		if (dto.getAuthorityApprovalStatus() == null) {
			throw new ServiceBizException("审批状态为空,请联系管理员!");
		}
		updateBigCustomerPo.setInteger("AUTHORITY_APPROVAL_STATUS", Integer.parseInt(dto.getAuthorityApprovalStatus()));
		updateBigCustomerPo.setBigDecimal("AUTHORITY_APPROVAL_USER_ID", loginUser.getUserId());
		updateBigCustomerPo.setBigDecimal("UPDATE_BY", loginUser.getUserId());
		updateBigCustomerPo.setDate("UPDATE_DATE", new Date(System.currentTimeMillis()));
		boolean flag = false;
		flag = updateBigCustomerPo.saveIt();
		if (!flag) {
			throw new ServiceBizException("审批失败!");
		}
		bigCustomerApprovalHisPo.setBigDecimal("AUTHORITY_APPROVAL_ID", id);
		bigCustomerApprovalHisPo.setInteger("AUTHORITY_APPROVAL_STATUS",
				Integer.parseInt(dto.getAuthorityApprovalStatus()));
		bigCustomerApprovalHisPo.setString("AUTHORITY_APPROVAL_REMARK", dto.getApprovalRemark());
		bigCustomerApprovalHisPo.setDate("AUTHORITY_APPROVAL_DATE", new Date(System.currentTimeMillis()));
		bigCustomerApprovalHisPo.setBigDecimal("AUTHORITY_APPROVAL_USER_ID", loginUser.getUserId());
		bigCustomerApprovalHisPo.setBigDecimal("CREATE_BY", loginUser.getUserId());
		bigCustomerApprovalHisPo.setDate("CREATE_DATE", new Date(System.currentTimeMillis()));
		flag = bigCustomerApprovalHisPo.saveIt();
		// 这个方法有问题调用接口事需要wsNo号暂时写死
		String wsNo = "";
		sadcs015cloud.execute(wsNo, dto.getDealerCode());
		if (!flag) {
			throw new ServiceBizException("插入审批历史失败!");
		}
	}

	@Override
	public void downBigCustomerPolicy(BigDecimal policyFileId) throws ServiceBizException {
		// 文件名称 待完善，功能未实现
		// String fileName =
		// customerdao.findFileNameByPolicyFileId(policyFileId);
		/*
		 * act.getResponse().setContentType("text/html; charset=utf-8");
		 * act.getResponse().addHeader("Content-disposition",
		 * "attachment;filename="+URLEncoder.encode("文件:"+fileName, "UTF-8"));
		 * byte[] inst=FileStore.getInstance().read(fileId); OutputStream out =
		 * act.getResponse().getOutputStream(); out.write(inst); out.flush();
		 */
	}

	@Override
	public void uploadFiles(MultipartFile importFile) throws ServiceBizException {
		String fidStr = fileStoreService.writeFile(importFile);
		TtBigCustomerPolicyFilePO po = new TtBigCustomerPolicyFilePO();
		po.setString("POLICY_FILE_NAME", importFile.getOriginalFilename().substring(
				importFile.getOriginalFilename().lastIndexOf("\\") + 1, importFile.getOriginalFilename().length()));// 文件名称
		po.setString("POLICY_FILE_ID", fidStr);// 文件服务器的文件ID
		try {
			po.setString("POLICY_FILE_URL", fileStore.getInputStream(fidStr));
		} catch (Exception e) {
			throw new ServiceBizException("设置文件URL失败", e);
		}
		po.setInteger("FILE_ENABLE", OemDictCodeConstants.STATUS_ENABLE);// 有效
		po.setLong("CREATE_BY", new Long(111111));
		po.setTimestamp("CREATE_DATE", new Date());
		po.saveIt();
	}

	@Override
	public Map<String, Object> QueryCustomerByStatus1(String wsno, int flag) {
		return customerdao.QueryCustomer1(wsno, flag);
	}

}
