package com.yonyou.dms.gacfca;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS012Cloud;
import com.yonyou.dms.DTO.gacfca.PoCusWholeClryslerDto;
import com.yonyou.dms.DTO.gacfca.WsConfigInfoClryslerDto;
import com.yonyou.dms.common.domains.PO.basedata.ConfigurationPO;
import com.yonyou.dms.common.domains.PO.basedata.PoCusWholesalePO;
import com.yonyou.dms.common.domains.PO.basedata.TtWsConfigInfoPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 业务描述：经销商上报批售审批单
 * 
 * @author Benzc
 * @date 2017年1月12日
 *
 */
@Service
public class DSO0401CoudImpl implements DSO0401Coud{
	private static final Logger logger = LoggerFactory.getLogger(DSO0401CoudImpl.class);
    @Autowired
    SADCS012Cloud SADCS012Cloud;
	@Override
	public int getDSO0401(String wsNo)throws ServiceBizException {
	    try {
	        String msg="1";
	        logger.info("======================DSO0401开始=========================");
	        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
	        Long userId = FrameworkUtil.getLoginInfo().getUserId();
	        List<PoCusWholeClryslerDto> WholeClryslerList = new LinkedList<PoCusWholeClryslerDto>();        
	        if(!StringUtils.isNullOrEmpty(wsNo) && !StringUtils.isNullOrEmpty(dealerCode)){     
	            PoCusWholeClryslerDto dto = new PoCusWholeClryslerDto();
	            List<PoCusWholesalePO> cListSame = PoCusWholesalePO.findBySQL("SELECT * FROM tt_po_cus_wholesale WHERE DEALER_CODE=? and WS_NO=? and D_KEY=?", new Object[]{dealerCode,wsNo,0});
	            if (cListSame==null){
	                throw new ServiceBizException("没找到申请单，请重新查询后再试");
	            }
	            if(cListSame!=null && cListSame.size()>0){
	                PoCusWholesalePO po = (PoCusWholesalePO) cListSame.get(0);
	                if(!StringUtils.isNullOrEmpty(po.getInteger("WS_STATUS").toString()) && 
	                        po.getInteger("WS_STATUS").equals(15981002)||po.getInteger("WS_STATUS").equals(15981003)){
	                    throw new ServiceBizException("该批售单号"+wsNo+"已经上报");
	                }
	                dto.setCompanyName(po.getString("COMPANY_NAME"));
	                dto.setSubmitTime(new Date(System.currentTimeMillis()));
	                dto.setWsType(po.getInteger("WS_APP_TYPE"));//批售类型      NUMERIC(8)
	                dto.setDealerCode(po.getString("DEALER_CODE"));//经销商代码  CHAR(8) 
	                dto.setDlrPrincipalPhone(po.getString("DLR_PRINCE"));// 负责人电话   VARCHAR(30)
	                dto.setFax(po.getString("FAX"));    //传真 VARCHAR(30)
	                dto.setPhone(po.getString("PHONE"));//  电话  VARCHAR(120)
	                dto.setWsNo(po.getString("WS_NO"));//   批售审批单号  CHAR(12)
	                dto.setDlrPrincipal(po.getString("DLR_PRINCEPAL"));//经销商业务负责人   VARCHAR(30)
	                dto.setCustomerName(po.getString("CUSTOMER_NAME"));//客户名称 VARCHAR(120)
	                dto.setMobile(po.getString("MOBILE"));//手机      VARCHAR(30)
	                dto.setProjectRemark(po.getString("PROJECT_REMARK"));//项目注释 VARCHAR(100)
	                dto.setContactorName(po.getString("CONTACTOR_NAME"));//联系人   VARCHAR(30)
	                dto.setPositionName(po.getString("POSITION_NAME"));//   职务名称    VARCHAR(30)
	                dto.setCustomerCharacter(po.getInteger("CUSTOMER_CHARACTER"));//客户性质    NUMERIC(8)
	                dto.setWsStatus(po.getInteger("WS_STATUS"));//批售状态  NUMERIC(8)
	                dto.setIsSecondReport(po.getInteger("IS_SECOND_REPORT"));//是否申请     NUMERIC(8)
	                dto.setLargeCustomerNo(po.getString("LARGE_CUSTOMER_NO"));//大客户代码   CHAR(20)
	                dto.setCustomerKind(po.getInteger("CUSTOMER_KIND"));//大客户类别
	                dto.setApplyPicId(po.getString("APPLY_APPLY_ID"));//申请表ID
	                dto.setApplyPic(po.getString("FILE_APPLY_URL"));//申请表url
	                dto.setSaleContractPicId(po.getString("FILE_CONTRACT_ID"));//销售合同id
	                dto.setSaleContractPic(po.getString("FILE_CONTACT_URL"));//销售合同url
	                dto.setContractFileAid(po.getString("CONTRACT_FILE_AID"));//销售合同a附件ID
	                dto.setContractFileAurl(po.getString("CONTRACT_FILE_URL"));//销售合同a附件URL
	                dto.setContractFileBid(po.getString("CONTRACT_FILE_BID"));//销售合同b附件ID
	                dto.setContractFileBurl(po.getString("CONTRACT_FILE_URL"));//销售合同b附件URL
	                dto.setEstimateApplyTime(po.getDate("ESTIMATE_APPLY_TIME"));//预计申请时间
	                dto.setWsthreeType(po.getInteger("WSTHREE_TYPE"));//客户细分类别
	                //获取配置
	                TtWsConfigInfoPO vehiclePO = new TtWsConfigInfoPO();
	                LinkedList<WsConfigInfoClryslerDto> vehicleList = new LinkedList<WsConfigInfoClryslerDto>();
	                List<TtWsConfigInfoPO> vinList=TtWsConfigInfoPO.findBySQL("SELECT * FROM tt_ws_config_info WHERE DEALER_CODE=? and WS_NO=? and D_KEY=?", new Object[]{dealerCode,wsNo,0});
	                if(vinList!=null && vinList.size()>0){
	                    for(int i=0;i<vinList.size();i++){
	                        vehiclePO=(TtWsConfigInfoPO)vinList.get(i);
	                        WsConfigInfoClryslerDto vehicleDto = new WsConfigInfoClryslerDto();
	                        BeanUtils.copyProperties(vehicleDto, vehiclePO);
	                        ConfigurationPO configPO =new ConfigurationPO();
	                        List<ConfigurationPO> vinList2 = ConfigurationPO.findBySQL("SELECT * FROM tm_configuration WHERE BRAND_CODE=? and CONFIG_CODE=? and SERIES_CODE=? and MODEL_CODE=? and DEALER_CODE=?"
	                                , new Object[]{
	                                        vehiclePO.getString("BRAND_CODE"),
	                                        vehiclePO.getString("CONFIG_CODE"),
	                                        vehiclePO.getString("SERIES_CODE"),
	                                        vehiclePO.getString("MODEL_CODE"),
	                                        dealerCode});
	                        if(vinList2 != null && vinList2.size()>0){
	                            configPO=(ConfigurationPO)vinList2.get(0);
	                            vehicleDto.setConfigName(configPO.getString("CONFIG_NAME"));
	                        }
	                        vehicleList.add(vehicleDto);
	                    }
	                }
	                dto.setConfigList(vehicleList);
	            }
                PoCusWholesalePO poT = PoCusWholesalePO.findByCompositeKeys(dealerCode,wsNo);
                if(!StringUtils.isNullOrEmpty(poT.getDate("FIRST_SUBMIT_TIME"))){
                    poT.setDate("FIRST_SUBMIT_TIME", new Date(System.currentTimeMillis()));
                }
               
	            WholeClryslerList.add(dto);
	            //调用厂端代码
	            logger.info("=====================即将进入SADCS012Cloud========================");
	            msg =SADCS012Cloud.receiveDate(WholeClryslerList);
	            logger.info("=====================走出SADCS012Cloud,没出错!========================");
	            poT.setInteger("WS_STATUS", 15981002);
                poT.setDate("SUBMIT_TIME", new Date(System.currentTimeMillis()));
                poT.setLong("D_KEY", 0);
                poT.setLong("UPDATED_BY", userId);
                poT.setDate("UPDATED_AT", new Date(System.currentTimeMillis()));
                poT.saveIt();
	  
	                
	        }

	        return Integer.parseInt(msg);
	    
        } catch (Exception e) {
        	logger.info("==========================DSO0401出异常啦!!!===========================");
           e.printStackTrace();
           return 0;
        }
	}

}
