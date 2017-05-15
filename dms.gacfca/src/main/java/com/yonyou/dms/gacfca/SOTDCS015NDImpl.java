package com.yonyou.dms.gacfca;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SOTDCS015Cloud;
import com.yonyou.dms.DTO.gacfca.TiDmsUCustomerStatusDto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 更新客户信息(休眠，订单，交车客户状态数据传递)DMS更新 接口实现类
 * 交车 ND
 * @author wangliang
 * @date 2017年2月24日
 */
@Service
public class SOTDCS015NDImpl implements SOTDCS015ND{
    private static final Logger logger = LoggerFactory.getLogger(SOTDCS015NDImpl.class);
    @Autowired
    SOTDCS015Cloud SOTDCS015Cloud;
	@Override
	public int getSOTDCS015ND(String soNo,Date invoiceDate) throws ServiceBizException{

		String msg="1";
		try {
		    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			Long userId = FrameworkUtil.getLoginInfo().getUserId();
			logger.info("=================开始上报SOTDCS015ND====================1");
			//根据售中工具开关来判断是否执行上报
			if (DictCodeConstants.DICT_IS_YES.equals(Utility.getDefaultValue("5434"))){
			    logger.info("=================开始上报SOTDCS015ND====================2");
			    if(soNo != null || !"".equals(soNo)) {
			        logger.info("=================开始上报SOTDCS015ND====================3");
			        SalesOrderPO salespo =SalesOrderPO.findByCompositeKeys(dealerCode,soNo);
			        logger.info("=================开始上报SOTDCS015ND====================4");
                    if(!StringUtils.isNullOrEmpty(salespo.getString("CUSTOMER_NO"))) {
                        logger.info("=================开始上报SOTDCS015ND====================5");
                        PotentialCusPO po =PotentialCusPO.findByCompositeKeys(dealerCode,salespo.getString("CUSTOMER_NO"));
                        logger.info("=================开始上报SOTDCS015ND====================6");
                        if(po != null) {
                            logger.info("=================开始上报SOTDCS015ND====================7");
                            LinkedList<TiDmsUCustomerStatusDto> resultList = new LinkedList<TiDmsUCustomerStatusDto>();
                            TiDmsUCustomerStatusDto dto = new TiDmsUCustomerStatusDto();
                            dto.setDealerCode(dealerCode);
                            dto.setUniquenessID(salespo.getString("CUSTOMER_NO"));
                            if(!StringUtils.isNullOrEmpty(po.getLong("SPAD_CUSTOMER_ID"))) {
                                dto.setFCAID(po.getLong("SPAD_CUSTOMER_ID"));
                            }
                            if(!StringUtils.isNullOrEmpty(po.getString("INTENT_LEVEL"))) {
                                dto.setOppLevelID(po.getString("INTENT_LEVEL"));
                            }
                            logger.info("=================开始上报SOTDCS015ND====================8");
                            if(!StringUtils.isNullOrEmpty(salespo.getString("SHEET_CREATE_DATE"))) {
                                dto.setOrderDate(format1.parse(salespo.getString("SHEET_CREATE_DATE")));
                            }
                            logger.info("=================开始上报SOTDCS015ND====================9");
                            System.out.println(invoiceDate);
                            if(!StringUtils.isNullOrEmpty(invoiceDate)) {
                                dto.setBuyCarDate(invoiceDate);
                            }
                            logger.info("=================开始上报SOTDCS015ND====================91");
                            if(!StringUtils.isNullOrEmpty(po.getString("SOLD_BY"))) {
                                dto.setDealerUserID(po.getString("SOLD_BY"));
                            }
                            logger.info("=================开始上报SOTDCS015ND====================10");
                           
                            resultList.add(dto);
                            logger.info("=================开始上报SOTDCS015ND====================");
                            msg = SOTDCS015Cloud.handleExecutor(resultList);
                            logger.info("=================结束上报SOTDCS015ND====================");
                            List<PotentialCusPO> list3 = PotentialCusPO.findBySQL("SELECT * FROM TM_POTENTIAL_CUSTOMER WHERE DEALER_CODE = ? AND CUSTOMER_NO = ? AND D_KEY = ? ", new Object[]{dealerCode,salespo.getString("CUSTOMER_NO"),CommonConstants.D_KEY});
                            PotentialCusPO po1 = list3.get(0);
                            po1.setInteger("IS_UPLOAD", Utility.getInt(CommonConstants.DICT_IS_YES));
                            po1.setLong("UPDATED_BY", userId);
                            po1.setDate("UPDATED_AT", new Date());
                            po1.saveIt();
                           
                        }
                    }
	            }
			
			}
			 return Integer.parseInt(msg);
	
		} catch (Exception e) {
		    logger.info("=================上报SOTDCS015ND失败====================");
			return 0;
		}
	}

}
