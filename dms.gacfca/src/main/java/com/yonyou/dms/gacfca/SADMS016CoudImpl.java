package com.yonyou.dms.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS014Cloud;
import com.yonyou.dcs.gacfca.SADCS016Cloud;
import com.yonyou.dms.DTO.gacfca.UcReplaceRebateApplyDto;
import com.yonyou.dms.DTO.gacfca.UcReplaceRebateOwnerDto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.ReplaceRepayPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 业务描述：二手车返利申请上报
 * 
 * @author Benzc
 * @date 2017年1月12日
 *
 */
@Service
public class SADMS016CoudImpl implements SADMS016Coud{
	 @SuppressWarnings("null")
	@Autowired
	 SADCS016Cloud sadcs016Cloud;
	
	@Override
	public int getSADMS016(String soNo)throws ServiceBizException {
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String msg="1";
		 
		try {
			List<UcReplaceRebateApplyDto> resultList = new ArrayList<UcReplaceRebateApplyDto>();
			String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			Long userId = FrameworkUtil.getLoginInfo().getUserId();
			
			if(soNo!=null && !"".equals(soNo)){
				//ReplaceRepayPO po = new ReplaceRepayPO();
				List<Map> listpo = Base.findAll("select *  from tt_replace_repay where DEALER_CODE='"+dealerCode+"' and SO_NO='"+soNo+"' and D_KEY='"+0+"'");
				if(listpo == null && listpo.size()==0){
					throw new ServiceBizException("没找到申请单，请重新查询后再试");
				}else{
					Map po=listpo.get(0);
					if ( String.valueOf(po.get("SO_STATUS")) !=null && !String.valueOf(po.get("SO_STATUS")).equals("15951001") && !String.valueOf(po.get("SO_STATUS")).equals("15951004") ){
						throw new ServiceBizException("只能上报未上报或审核修改的申请单,请重新查询后再试！");
					}
					UcReplaceRebateApplyDto dto = new UcReplaceRebateApplyDto();
					dto.setDealerCode(dealerCode);
					dto.setReplaceWay((Integer)po.get("REPLACE_TYPE"));
					dto.setReplaceApplyNo(po.get("SO_NO")+"");
					dto.setReplaceSubmitApplyDate(new Date(System.currentTimeMillis()));
					dto.setReplaceDate((Date)po.get("REPLACE_DATE"));
					if(!StringUtils.isNullOrEmpty(po.get("REPLACE_PRICE"))){
						dto.setOldVhclTransactionPrice(((Double)po.get("REPLACE_PRICE")).toString());
					}
					dto.setOldVhclBrand(po.get("OLD_BRAND_CODE")+"");
					dto.setOldVhclModel(po.get("OLD_MODEL_CODE")+"");
					dto.setOldVin(po.get("OLD_VIN")+"");

					dto.setNewVhclInvoicePic(po.get("FILE_URL_A")+"");
					dto.setOldVhclTravelCardPic(po.get("FILE_URL_B")+"");
					dto.setOldVhclTransferMembershipPic(po.get("FILE_URL_C")+"");
					dto.setCardPic(po.get("FILE_URL_D")+"");
					dto.setOldVhclCertificatesOfTitlePic(po.get("FILE_URL_E")+"");
					dto.setMarriageCertificatesPic(po.get("FILE_URL_F")+"");
					dto.setFinaPic(po.get("FILE_URL_G")+"");
					dto.setOtherPic(po.get("FILE_URL_H")+"");
					
					LinkedList<UcReplaceRebateOwnerDto> ownerList = new LinkedList<UcReplaceRebateOwnerDto>();
					UcReplaceRebateOwnerDto ownerdto= new UcReplaceRebateOwnerDto();
					//PotentialCusPO cuspo = new PotentialCusPO();
					List<Map> list=Base.findAll("select *  from TM_POTENTIAL_CUSTOMER where DEALER_CODE=? and D_KEY=? and CUSTOMER_NO=?", new Object[]{dealerCode,0,po.get("CUSTOMER_NO")+""});
					if(list!=null&&list.size()>0){
						Map cuspo =list.get(0);
						ownerdto.setCustomerType((Integer)cuspo.get("CUSTOMER_TYPE"));
						ownerdto.setOwnerName(cuspo.get("OWNER_NAME")+"");
						ownerdto.setCardType(Integer.parseInt(String.valueOf(cuspo.get("CT_CODE"))));
						ownerdto.setCardNo(cuspo.get("CERTIFICATE_NO")+"");
						ownerdto.setTelephone(cuspo.get("CONTACTOR_PHONE")+"");
						ownerdto.setCellphone(cuspo.get("CONTACTOR_MOBILE")+"");
						ownerdto.setIsNewOwner(1);
					}
					UcReplaceRebateOwnerDto ownerolddto= new UcReplaceRebateOwnerDto();
					ownerolddto.setOwnerName(po.get("OLD_CUSTOMER_NAME")+"");
					ownerolddto.setCardType((Integer)po.get("CT_COLD"));
					ownerolddto.setCardNo(po.get("CERTIFICATE_NO")+"");
					ownerolddto.setTelephone(po.get("OLD_PHONE")+"");
					ownerolddto.setCellphone(po.get("OLD_MOBILE")+"");
					ownerolddto.setIsNewOwner(2);
					
					ownerList.add(ownerdto);
					ownerList.add(ownerolddto);
					dto.setOwnerList(ownerList);
					resultList.add(dto);
					String num=sadcs016Cloud.handleExecutor(resultList);
					if(num.equals("1")){
						String sql="UPDATE tt_replace_repay SET SUBMIT_BY='"+userId+"', SUBMIT_DATE='"+df.format(new Date())+"',SO_STATUS='"+15951002+"',UPDATED_BY='"+userId+"',UPDATED_AT='"+df.format(new Date())+"' WHERE DEALER_CODE='"+dealerCode+"' AND SO_NO='"+soNo+"'";
						Base.exec(sql);
					}else{
						throw new ServiceBizException("上报未成功，请稍后重试！");
					}
					
					
					
				}
				
			}
			return Integer.parseInt(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}

}
