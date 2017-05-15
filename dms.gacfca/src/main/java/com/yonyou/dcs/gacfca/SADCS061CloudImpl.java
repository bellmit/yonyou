package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dcs.dao.SaleVehicleSaleDao;
import com.yonyou.dms.DTO.gacfca.OrderCarDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtHiringTaxiesPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domains.DTO.baseData.VehicleInfoDTO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
 * Title:SADCS061CloudImpl
 * Description: 订车接口  接收
 * @author DC
 * @date 2017年4月10日 下午12:11:47
 * result msg 1：成功 0：失败
 */
@Service
@SuppressWarnings("all")
public class SADCS061CloudImpl extends BaseCloudImpl implements SADCS061Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS061CloudImpl.class);
	private static List<Map> vehicleInfoList = null;
	
	@Autowired
	SaleVehicleSaleDao saleDao;

	@Override
	public String handleExecutor(List<OrderCarDTO> dtoList) throws Exception {
		String msg = "1";
		logger.info("====订车接口接收开始====");
		for (OrderCarDTO vo : dtoList) {
			try {
				insertData(vo);
			} catch (Exception e) {
				logger.error("订车接口接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
			logger.info("====订车接口接收结束====");
		}
		return msg;
	}

	private void insertData(OrderCarDTO vo) throws Exception {
		try {
			Map<String, Object> map = saleDao.getSaDcsDealerCode(vo.getEntityCode());
			String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息

			TtHiringTaxiesPO thtPO = new TtHiringTaxiesPO();
			
			if(null!=findById(vo,dealerCode)){
				Long updatedocumentId = findById(vo,dealerCode);
				thtPO = TtHiringTaxiesPO.findById(updatedocumentId);
				thtPO = getPO(vo,dealerCode,thtPO);
				thtPO.setString("RESULT_VALUE", null);
				thtPO.setString("IS_SCAN", "0");
				thtPO.setString("IS_UPDATE", "1");
				thtPO.setLong("UPDATE_BY", 999999999L);
				thtPO.setTimestamp("UPDATE_DATE", new Date());
				thtPO.saveIt();
			} else {
				thtPO = getPO(vo,dealerCode,thtPO);
				thtPO.setString("IS_SCAN", "0");
				thtPO.setLong("CREATE_BY", 999999999L);
				thtPO.setTimestamp("CREATE_DATE", new Date());
				thtPO.setString("IS_UPDATE", "0");
				thtPO.insert();
				logger.info("====订车接口接收成功====");
			}
			
		} catch (Exception e) {
			logger.error("订车接口接收失败", e);
			throw new Exception(e);
		}
	}

	private TtHiringTaxiesPO getPO(OrderCarDTO vo,String dealerCode,TtHiringTaxiesPO thtPO) {
		VehicleInfoDTO infoVo = null;
		thtPO.setString("DEALER_CODE", dealerCode);//经销商代码
		thtPO.setString("SO_NO", vo.getSoNo());//销售订单号
		thtPO.setString("CUSTOMER_NAME", vo.getCustomerName());//车主姓名
		thtPO.setString("CUSTOMER_MOBILE", vo.getCustomerMobile());//车主手机号
		thtPO.setString("SOLD_BY", vo.getSoldBy());//销售顾问
		thtPO.setString("PHONE", vo.getPhone());//联系电话
		thtPO.setInteger("CARD_TYPE", vo.getCardType());//证件类型
		thtPO.setString("CARD_NO", vo.getCardNo());//证件号码
		
		//品牌，车系，车型集合
		infoVo = validatorPoCode(vo.getBrand()+"@"+vo.getCarModel());
		logger.info("====订车SS===="+vo.getBrand()+"=========="+vo.getCarModel());
		if(null != infoVo){
			logger.info("====订车SS===="+infoVo.getBrandId()+"=========="+infoVo.getCarModle());
			thtPO.setString("BRAND", infoVo.getBrandId());//汽车品牌
			thtPO.setString("CAR_MOBILE", infoVo.getCarModle());//车款
		}
		
		thtPO.setString("FIRST_COLOR", vo.getFirstColor());//车身颜色（首选）
		thtPO.setInteger("DELIVER_MODE", vo.getDeliverMode());//交车方式
		thtPO.setDate("APP_DELIVER_DATE", vo.getAppDeliverDate());//预约交车时间
		thtPO.setTimestamp("STOCK_DATE", vo.getStockDate());//车辆到库时间
		thtPO.setTimestamp("DEAL_DATE", vo.getDealDate());//订车成功时间
		thtPO.setString("VIN", vo.getVin());//车辆代码
		return thtPO;
		
		
	}

	private Long findById(OrderCarDTO vo, String dealerCode) throws Exception {
		Long documentId = 0L;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT HIRING_TAXIES_ID  \n");
			sql.append(" From TT_HIRING_TAXIES   ");
			sql.append(" WHERE 1=1 ");
			sql.append(" AND SO_NO = '"+vo.getSoNo()+ "' \n");
			if(!StringUtils.isNullOrEmpty(vo.getVin())){
				 sql.append(" AND VIN ='"+vo.getVin()+"' \n");
			 }
			sql.append(" AND DEALER_CODE ='").append(dealerCode).append("' \n");
			sql.append(" ORDER BY HIRING_TAXIES_ID DESC");
			List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
			if(null!=list && list.size()>0){
				documentId = (Long) list.get(0).get("HIRING_TAXIES_ID");
				return documentId;
			}
		} catch (Exception e) {
			logger.error("订车校验数据失败", e);
			throw new Exception(e);
		}
		return null;
	}

	private VehicleInfoDTO validatorPoCode(String code) {
		VehicleInfoDTO infoVo = null;
		vehicleInfoList = getVehicle();
		for(Map map : vehicleInfoList){
			infoVo =  (VehicleInfoDTO) map.get(code);
			logger.info("====订车CODE===="+code);
			if(null != infoVo){
				break;
			}
		}
		return infoVo;
	}

	private List<Map> getVehicle() {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(" SELECT DISTINCT ");
		sql.append(" TVMG1.GROUP_CODE AS BRAND_CODE, TVMG2.GROUP_CODE AS SERIES_CODE, TVMG3.GROUP_CODE AS MODEL_CODE,TVMG4.GROUP_CODE AS CAR_MODEL_CODE ");
		sql.append(" ,TVMG1.TREE_CODE AS BRAND_ID ,TVMG2.TREE_CODE AS SERIES_ID ,TVMG3.TREE_CODE AS MODEL_ID ,TVMG4.TREE_CODE AS CAR_MODEL_ID");
        //sql.append(" ,TVM.COLOR_CODE ") ;
        sql.append(" FROM TM_VHCL_MATERIAL TVM " );
        sql.append(" INNER JOIN TM_VHCL_MATERIAL_GROUP_R TVMGR ON TVM.MATERIAL_ID = TVMGR.MATERIAL_ID "); //车辆物料组关系表
        sql.append(" INNER JOIN TM_VHCL_MATERIAL_GROUP TVMG4 ON TVMGR.GROUP_ID = TVMG4.GROUP_ID " ) ;   
        sql.append(" INNER JOIN TM_VHCL_MATERIAL_GROUP TVMG3 ON TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID " ); 
        sql.append(" INNER JOIN TM_VHCL_MATERIAL_GROUP TVMG2 ON TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID " ); 
        sql.append(" INNER JOIN TM_VHCL_MATERIAL_GROUP TVMG1 ON TVMG2.PARENT_GROUP_ID = TVMG1.GROUP_ID " ); 
        sql.append(" WHERE TVMG1.STATUS = ? AND  TVMG2.STATUS = ? " );
        sql.append(" AND TVMG3.STATUS = ? AND TVMG4.STATUS = ? " );
        params.add(OemDictCodeConstants.STATUS_ENABLE);
        params.add(OemDictCodeConstants.STATUS_ENABLE);
        params.add(OemDictCodeConstants.STATUS_ENABLE);
        params.add(OemDictCodeConstants.STATUS_ENABLE);
		List<Map> ps = OemDAOUtil.findAll(sql.toString(), params);
        return ps;
	}

}
