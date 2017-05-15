package com.yonyou.dms.gacfca;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.PoCusWholeClryslerDto;
import com.yonyou.dms.common.domains.PO.basedata.BigCustomerHistoryPO;
import com.yonyou.dms.common.domains.PO.basedata.PoCusWholesalePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：OEM下发批售客户审批状态
 * @date 2017年1月10日
 * @author Benzc
 *
 */
@Service
public class OSD0401Impl implements OSD0401{
	
	@Autowired
    private CommonNoService     commonNoService;

	@Override
	public int getOSD0401(List<PoCusWholeClryslerDto> dtList) throws ServiceBizException {
		try {

			String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			//此处需要定义一个DTO列表获取上端数据
			//LinkedList dtList = new LinkedList();
			if(dtList != null && dtList.size()>0){
			
				for(int i = 0;i<dtList.size();i++){
					PoCusWholeClryslerDto dt = new PoCusWholeClryslerDto();
					
					BigCustomerHistoryPO poh = new BigCustomerHistoryPO();
					dt=(PoCusWholeClryslerDto) dtList.get(i);
					dealerCode = dt.getDealerCode();
					if(!StringUtils.isEmpty(dt.getDealerCode()) && !StringUtils.isEmpty(dt.getWsNo()) && !StringUtils.isEmpty(dt.getWsStatus().toString())){

						PoCusWholesalePO.update("WS_STATUS=? AND AUDITING_DATE=? AND WS_AUDITOR=? AND WS_AUDITING_REMARK=? AND UPDATED_BY=? AND UPDATED_AT=? ",
								"D_KEY=? AND WS_NO=? AND DEALER_CODE=?", dt.getWsStatus(),dt.getAuditingDate(),dt.getWsAuditor(),dt.getWsAuditingRemark(),1L,System.currentTimeMillis(),0,dt.getWsNo(),dealerCode);

						
						try {
							Long id = commonNoService.getId("ID");
					        System.out.println(id);
					        poh.setLong("ITEM_ID", id);
							poh.setInteger("WS_STATUS", dt.getWsStatus());
							System.out.println(dt.getWsStatus());
							poh.setDate("AUDITING_DATE", dt.getAuditingDate());
							System.out.println(dt.getAuditingDate());
							poh.setString("WS_AUDITING_REMARK",dt.getWsAuditingRemark());
							poh.setString("WS_AUDITOR",dt.getWsAuditor());
							poh.setInteger("D_KEY", 0);
							poh.setString("WS_NO", dt.getWsNo());
							poh.setString("DEALER_CODE", dt.getDealerCode());
							poh.setLong("CREATED_BY", 1L);
							//poh.setDate("CREATED_AT", System.currentTimeMillis());					
							poh.insert();
							poh.getString("DEALER_CODE");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.err.println(poh.getString("DEALER_CODE"));
					}
				}
			}
			return 1;
			
		
		} catch (Exception e) {
			return 0;
		}
	}
	
	

}
