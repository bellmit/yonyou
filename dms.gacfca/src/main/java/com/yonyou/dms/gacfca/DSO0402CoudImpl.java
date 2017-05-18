package com.yonyou.dms.gacfca;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.javalite.activejdbc.Base;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS014Cloud;
import com.yonyou.dms.DTO.gacfca.PoCusWholeRepayClryslerDto;
import com.yonyou.dms.DTO.gacfca.WsConfigInfoRepayClryslerDto;
import com.yonyou.dms.common.domains.PO.basedata.WholesaleRepayDteailPO;
import com.yonyou.dms.common.domains.PO.basedata.WholesaleRepayPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：经销商上报批售审批单返利
 * 
 * @author Benzc
 * @date 2017年1月12日
 *
 */
@Service
public class DSO0402CoudImpl implements DSO0402Coud{
    @Autowired
    SADCS014Cloud SADCS014Cloud;
    
	@SuppressWarnings("rawtypes")
	@Override
	public int getDSO0402(String wsNo)throws ServiceBizException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
	    String msg="1";
	    try {

	        
	        LinkedList<PoCusWholeRepayClryslerDto>  WholeClryslerList = new LinkedList<PoCusWholeRepayClryslerDto>();
	        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
	        Long userId = FrameworkUtil.getLoginInfo().getUserId();

	        if(!StringUtils.isEmpty(wsNo) && !StringUtils.isEmpty(dealerCode)){
	            PoCusWholeRepayClryslerDto dto = new PoCusWholeRepayClryslerDto();
	            List<Map> cListSame = Base.findAll("SELECT *  FROM tt_wholesale_repay WHERE DEALER_CODE=? and WS_NO=? and D_KEY=?", new Object[]{dealerCode,wsNo,0});
	            if(cListSame==null){
	                throw new ServiceBizException("没找到申请单，请重新查询后再试");
	            }
	            if(cListSame!=null && cListSame.size()>0){
	                Map  po=  cListSame.get(0); 
	                
	                if (!StringUtils.isEmpty(String.valueOf(po.get("SO_STATUS"))) &&
	                        (String.valueOf(po.get("SO_STATUS"))).equals("16101002")||(String.valueOf(po.get("SO_STATUS"))).equals("16101003")){
	                    throw new ServiceBizException("该批售返利单号"+wsNo+"已经上报");
	                }
	                dto.setDealerCode((String)po.get("DEALER_CODE"));
	                dto.setWsNo((String)po.get("WS_NO"));//   报备单号    CHAR(12)
	                dto.setWsAuditor(po.get("WS_AUDITOR")+"");//批返利审核人       VARCHAR(60)
	                dto.setSoStatus(Integer.parseInt(String.valueOf(po.get("SO_STATUS"))));//批售返利申请状态  NUMERIC(8)
	                dto.setAuditingDate((Date)po.get("AUDITING_DATE"));//审核日期 TIMESTAMP
	                dto.setWsAuditingRemark(po.get("WS_AUDITING_DATE")+"");//    审核备注        VARCHAR(255)
	                dto.setCompanyName(po.get("COMPANY_NAME")+"");//公司名称
	                dto.setSubmitTime(new Date(System.currentTimeMillis()));//上报日期  TIMESTAMP
	                dto.setSubmitBy(userId);//上报人
	                dto.setAmount(Integer.parseInt(String.valueOf(po.get("AMOUNT"))));//成交数量
	                dto.setRemark(po.get("REMARK")+""); //备注
	                dto.setWsAppType(Integer.parseInt(String.valueOf(po.get("WS_APP_TYPE"))));//批售类型 ： 集团销售 影响力人物 团购 可修改
	                dto.setWsEmployeeType(Integer.parseInt(String.valueOf(po.get("WS_EMPLOYEE_TYPE"))));// 政策类型 为 企业员工的  员工分类
	                dto.setWsthreeType(Integer.parseInt(String.valueOf(po.get("WSTHREE_TYPE"))));//客户细分
	                if(!StringUtils.isEmpty(String.valueOf(po.get("WS_APP_TYPE")).toString())){
	                    if((String.valueOf(po.get("WS_APP_TYPE"))).equals("15971001")){
	                        //集团销售材料
	                        dto.setFileUrlAId(po.get("FILE_ID_A")+"");//集团销售申请表
	                        dto.setFileUrlBId(po.get("FILE_ID_B")+"");//客户单位组织机构代码证
	                        dto.setFileUrlCId(po.get("FILE_ID_C")+"");//租赁类企业营业执照
	                        dto.setFileUrlDId(po.get("FILE_ID_D")+"");//集团销售明细表
	                        dto.setFileUrlEId(po.get("FILE_ID_E")+"");//销售合同
	                        
	                        dto.setFileUrlA(po.get("FILE_URL_A")+"");//集团销售申请表
	                        dto.setFileUrlB(po.get("FILE_URL_B")+"");//客户单位组织机构代码证
	                        dto.setFileUrlC(po.get("FILE_URL_C")+"");//租赁类企业营业执照
	                        dto.setFileUrlD(po.get("FILE_URL_D")+"");//集团销售明细表
	                        dto.setFileUrlE(po.get("FILE_URL_E")+"");//销售合同
	                        dto.setFileUrlNId(po.get("FILE_ID_N")+"");
	                        dto.setFileUrlOId(po.get("FILE_ID_O")+"");
	                        dto.setFileUrlPId(po.get("FILE_ID_P")+"");
	                        dto.setFileUrlQId(po.get("FILE_ID_Q")+"");
	                        dto.setFileUrlRId(po.get("FILE_ID_R")+"");
	                        dto.setFileUrlN(po.get("FILE_URL_N")+"");
	                        dto.setFileUrlO(po.get("FILE_URL_O")+"");
	                        dto.setFileUrlP(po.get("FILE_URL_P")+"");
	                        dto.setFileUrlQ(po.get("FILE_URL_Q")+"");
	                        dto.setFileUrlR(po.get("FILE_URL_R")+"");
	                        //销售合同补充资料上传
	                        dto.setContractFileAid(po.get("CONTRACT_FILE_AID")+"");
	                        dto.setContractFileAurl(po.get("CONTRACT_FILE_AURL")+"");
	                        dto.setContractFileBid(po.get("CONTRACT_FILE_BID")+"");
	                        dto.setContractFileBurl(po.get("CONTRACT_FILE_BURL")+"");
	                    }else if(((Integer)po.get("WS_APP_TYPE")).equals(15971002)){
	                        //影响力人物
	                        dto.setFileUrlFId(po.get("FILE_ID_A")+"");//影响力人物申请表
	                        dto.setFileUrlGId(po.get("FILE_URL_B")+"");//客户身份证明
	                        dto.setFileUrlHId(po.get("FILE_URL_C")+"");//身份证
	                        dto.setFileUrlIId(po.get("FILE_URL_D")+"");//销售合同
	                        
	                        dto.setFileUrlF(po.get("FILE_URL_A")+"");//影响力人物申请表
	                        dto.setFileUrlG(po.get("FILE_URL_B")+"");//客户身份证明
	                        dto.setFileUrlH(po.get("FILE_URL_C")+"");//身份证
	                        dto.setFileUrlI(po.get("FILE_URL_D")+"");//销售合同
	                    }else if(((Integer)po.get("WS_APP_TYPE")).equals(15971003)){
	                        //团购
	                        dto.setFileUrlJId(po.get("FILE_ID_A")+"");//团体客户申请表
	                        dto.setFileUrlMId(po.get("FILE_ID_B")+"");//营业执照
	                        dto.setFileUrlNId(po.get("FILE_ID_N")+"");
	                        dto.setFileUrlOId(po.get("FILE_ID_O")+"");
	                        dto.setFileUrlPId(po.get("FILE_ID_P")+"");
	                        dto.setFileUrlQId(po.get("FILE_ID_Q")+"");
	                        dto.setFileUrlRId(po.get("FILE_ID_R")+"");
	                        dto.setFileUrlN(po.get("FILE_URL_N")+"");
	                        dto.setFileUrlO(po.get("FILE_URL_O")+"");
	                        dto.setFileUrlP(po.get("FILE_URL_P")+"");
	                        dto.setFileUrlQ(po.get("FILE_URL_Q")+"");
	                        dto.setFileUrlR(po.get("FILE_URL_R")+"");
	                        dto.setFileUrlJ(po.get("FILE_URL_J")+"");//团体客户申请表
	                        dto.setFileUrlM(po.get("FILE_URL_M")+"");//营业执照
	                        dto.setContractFileAid(po.get("CONTRACT_FILE_AID")+"");
	                        dto.setContractFileAurl(po.get("CONTRACT_FILE_AURL")+"");
	                        dto.setContractFileBid(po.get("CONTRACT_FILE_BID")+"");
	                        dto.setContractFileBurl(po.get("CONTRACT_FILE_BURL")+"");
	                    }
	                }
	                
	                //获取配置
	               // WholesaleRepayDteailPO vehiclePO=new WholesaleRepayDteailPO();
	                LinkedList<WsConfigInfoRepayClryslerDto> vehicleList=new LinkedList<WsConfigInfoRepayClryslerDto>();
	                List<Map> vinList = Base.findAll("SELECT *  FROM tt_wholesale_repay_dteail WHERE DEALER_CODE=? and WS_NO=? and D_KEY=?", new Object[]{dealerCode,wsNo,0});
	                if(vinList!=null && vinList.size()>0){
	                    for(int i=0;i<vinList.size();i++){
	                        Map vehiclePO=vinList.get(i);
	                        WsConfigInfoRepayClryslerDto vehicleDto=new WsConfigInfoRepayClryslerDto();
	                       // BeanUtils.copyProperties(vehicleDto, vehiclePO);
	                        vehicleDto.setBrandCode(vehiclePO.get("BRAND_CODE")+"");
	                        vehicleDto.setConfigCode(vehiclePO.get("CONFIG_CODE")+"");
	                        vehicleDto.setDealerCode(vehiclePO.get("DEALER_CODE")+"");
	                        vehicleDto.setModelCode(vehiclePO.get("MODEL_CODE")+"");
	                        vehicleDto.setSeriesCode(vehiclePO.get("SERIES_CODE")+"");
	                        vehicleDto.setSoNo(vehiclePO.get("SO_NO")+"");
	                        vehicleDto.setVehiclePrice(((BigDecimal)vehiclePO.get("Vehicle_Price")).doubleValue());
	                        vehicleDto.setVin(vehiclePO.get("VIN")+"");
	                        
	                        vehicleDto.setFileCustomerCardId(vehiclePO.get("FILE_SFZ_ID")+"");
	                        vehicleDto.setFileCustomerCardUrl(vehiclePO.get("FILE_SFZ_URL")+"");//身份证
	                        vehicleDto.setFileSaleContractId(vehiclePO.get("FILE_SB_ID")+"");//社保
	                        vehicleDto.setFileSaleContractUrl(vehiclePO.get("FILE_SB_URL")+"");
	                        vehicleDto.setFileSaleContractId1(vehiclePO.get("FILE_SB1_ID")+"");//社保
	                        vehicleDto.setFileSaleContractUrl1(vehiclePO.get("FILE_SB1_URL")+"");
	                        vehicleDto.setFileUrlEmpGId(vehiclePO.get("FILE_G_ID")+"");//员工在职证明
	                        vehicleDto.setFileUrlEmpG(vehiclePO.get("FILE_G_URL")+"");
	                        vehicleDto.setFileUrlEmpHId(vehiclePO.get("FILE_H_ID")+"");//公务员专用销售合同
	                        vehicleDto.setFileUrlEmpH(vehiclePO.get("FILE_H_URL")+"");
	                        vehicleDto.setFileUrlEmpH2Id(vehiclePO.get("FILE_H2_ID")+"");//公务员专用销售合同
	                        vehicleDto.setFileUrlEmpH2(vehiclePO.get("FILE_H2_URL")+"");
	                        vehicleDto.setFileUrlEmpH3Id(vehiclePO.get("FILE_H3_ID")+"");//公务员专用销售合同
	                        vehicleDto.setFileUrlEmpH3(vehiclePO.get("FILE_H3_URL")+"");
	                        vehicleDto.setFileUrlEmpH4Id(vehiclePO.get("FILE_H4_ID")+"");//公务员专用销售合同
	                        vehicleDto.setFileUrlEmpH4(vehiclePO.get("FILE_H4_URL")+"");
	                        vehicleDto.setFileUrlEmpIId(vehiclePO.get("FILE_I_ID")+"");//公务员工作证明
	                        vehicleDto.setFileUrlEmpI(vehiclePO.get("FILE_I_URL")+"");
	                        vehicleList.add(vehicleDto);
	                    }
	                }
	                dto.setConfigList(vehicleList);
	            }
	            WholeClryslerList.add(dto);
	            SADCS014Cloud.handleExecutor(WholeClryslerList);
	            //更新主表信息
//	            WholesaleRepayPO poT = WholesaleRepayPO.findByCompositeKeys(dealerCode,wsNo);           
//	            
//	            poT.setInteger("SO_STATUS", 16101002);
//	            poT.setDate("SUBMIT_DATE", new Date(System.currentTimeMillis()));
//	            poT.setLong("SUBMIT_BY", userId);
//	            poT.setLong("D_KEY", 0);
//	            poT.setLong("UPDATED_AT",userId);
//	            poT.setDate("UPDATED_BY", new Date(System.currentTimeMillis()));
//	            poT.saveIt();
	            
	            String sql="update tt_wholesale_repay set SO_STATUS='"+16101002+"',SUBMIT_DATE='"+format+"',SUBMIT_BY='"+userId+"',D_KEY='"+0+"',UPDATED_AT='"+format+"',UPDATED_BY='"+userId+"' where DEALER_CODE='"+dealerCode+"' AND WS_NO='"+wsNo+"'";
	            
	            Base.exec(sql);
	            
	            
	            
	        }
	      
	       // WholeClryslerList
	        return Integer.parseInt(msg);
	    
        } catch (Exception e) {
            return 0;
        }
	}

}
