/**
 * 
 */
package com.yonyou.dms.customer.service.vehicleTransfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.ChargeDeratePO;
import com.yonyou.dms.common.domains.PO.basedata.ReceiveMoneyPO;
import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TTPreReceiveMoneyPO;
import com.yonyou.dms.common.domains.PO.basedata.TmMemberVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehicleSubclassPO;
import com.yonyou.dms.common.domains.PO.customer.InsProposalPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @author sqh
 *
 */

@Service
public class VehicleTransferServiceImpl implements VehicleTransferService{
	
	@Override
	public List<Map> queryOwnerNoByid(String id) {
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(" SELECT  A.*,ve.*,ve.ENGINE_NO,A.OWNER_NAME FROM (" + CommonConstants.VM_OWNER + ") A "
				+ " LEFT JOIN TM_OWNER_MEMO B ON B.DEALER_CODE = A.DEALER_CODE AND B.OWNER_NO = A.OWNER_NO "
				+ " LEFT JOIN ("+CommonConstants.VM_VEHICLE+") ve ON ve.DEALER_CODE= A.DEALER_CODE AND ve.OWNER_NO=A.OWNER_NO "
				+ " WHERE A.DEALER_CODE = '" + dealerCode + "'  AND  ve.VIN = '" + id + "' ");
		List<Map> resultList = Base.findAll(sql.toString());
		return resultList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	@Override
	public List<Map> ModifyOwnerById(Map<String, String> queryParam)  {
		
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
	
		if(queryParam.get("proposalCode")!=null && !"".equals(queryParam.get("proposalCode"))){
			String sql="select * from TM_INS_PROPOSAL where VIN='"+queryParam.get("vin")+"' and DEALER_CODE='"+dealerCode+"'";
			List<Map> resultList = Base.findAll(sql);
			System.out.print(resultList.get(0).get("FORM_STATUS"));
			if(resultList==null || resultList.size()==0){
				throw new ServiceBizException("该车主没有投保信息，不可过户！");
			}else if(resultList.get(0).get("FORM_STATUS") != "12291006"){
				throw new ServiceBizException("投保单必须已结案才可过户！");
			}else if(!queryParam.get("proposalCode").equals(resultList.get(0).get("PROPOSAL_CODE"))){
				throw new ServiceBizException("输入的投保单号错误！");
			}
			InsProposalPO insProposalPO = InsProposalPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),queryParam.get("proposalCode"));
			insProposalPO.setInteger("IS_TRANSFER", queryParam.get("isTransfer"));
			insProposalPO.setString("TRANSFER_OWNER_NO", queryParam.get("ownerNo2"));
			insProposalPO.saveIt();
		}
			StringBuffer sb = new StringBuffer();
			StringBuffer sbw = new StringBuffer();
			StringBuffer sbc = new StringBuffer();
			List list = new ArrayList<>();
			sb.append(" OWNER_NO = ?  ");
			sbw.append(" VIN = ? AND MAIN_ENTITY = ? ");
			sbc.append(" VIN = ? AND DEALER_CODE = ? ");
			list.add(queryParam.get("ownerNo2"));
			list.add(queryParam.get("vin"));
			list.add(dealerCode);
			TmVehicleSubclassPO.update(sb.toString(), sbw.toString(), list.toArray());
			VehiclePO.update(sb.toString(), sbc.toString(), list.toArray());
			if(queryParam.get("status") != null && queryParam.get("status") != ""){
			if (Integer.parseInt(queryParam.get("status")) == 10571001) {
				StringBuffer st = new StringBuffer();
				StringBuffer str = new StringBuffer();
				st.append(" OWNER_NO = ? , owner_name= ? ");
				str.append(" VIN = ? AND DEALER_CODE = ? ");
				List list1 = new ArrayList<>();
				list1.add(queryParam.get("ownerNo2"));
				list1.add(queryParam.get("ownerName2"));
				list1.add(queryParam.get("vin"));
				list1.add(dealerCode);
				ChargeDeratePO.update(st.toString(), str.toString(), list1.toArray());
				ReceiveMoneyPO.update(st.toString(), str.toString(), list1.toArray());
				TTPreReceiveMoneyPO.update(st.toString(), str.toString(), list1.toArray());
				StringBuffer buffer = new StringBuffer();
				List list2 = new ArrayList<>();
				buffer.append("OWNER_NO = ? , owner_name= ? , owner_property= ? ");
				list2.add(queryParam.get("ownerNo2"));
				list2.add(queryParam.get("ownerName2"));
				list2.add(queryParam.get("ownerProperty2"));
				list2.add(queryParam.get("vin"));
				list2.add(dealerCode);
				RepairOrderPO.update(buffer.toString(), str.toString(), list2.toArray());
			}
			}
			List list3 = new ArrayList<>();
			list3.add(queryParam.get("vin"));
			list3.add(dealerCode);
			TmMemberVehiclePO.delete(" VIN = ? AND DEALER_CODE = ? ", list3.toArray());
			Map<String, String> queryParam1 = new HashMap<String, String>();
			queryParam1.put("vin", queryParam.get("vin"));
			List<Map> result = queryByLinsence(queryParam1);
			return result;
	}

	@Override
	public List<Map> queryByLinsence(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append(
				" SELECT A.*,B.OWNER_PROPERTY,B.OWNER_NAME,B.CONTACTOR_ZIP_CODE,B.CONTACTOR_EMAIL,B.CONTACTOR_ADDRESS,B.ADDRESS,B.PHONE,B.MOBILE,tb.brand_name,ts.series_name,tm.MODEL_NAME, ");
		sql.append(
				" (case when B.CONTACTOR_NAME IS NULL or B.CONTACTOR_NAME='' then B.OWNER_NAME else B.CONTACTOR_NAME end) AS CONTACTOR_NAME, ");
		sql.append(
				" (case when B.CONTACTOR_PHONE IS NULL or B.CONTACTOR_PHONE='' then B.PHONE else B.CONTACTOR_PHONE end) AS CONTACTOR_PHONE, ");
		sql.append(
				" (case when B.CONTACTOR_MOBILE IS NULL or B.CONTACTOR_MOBILE='' then B.MOBILE else B.CONTACTOR_MOBILE end) AS CONTACTOR_MOBILE, ");
		sql.append(
				" B.GENDER,B.CT_CODE,B.CERTIFICATE_NO,B.CONTACTOR_GENDER,B.E_MAIL,B.BIRTHDAY,'' as IS_MILEAGE_MODIFY,'' as sell_date ");
		sql.append(" ,'' as DELIVERER_DDD_CODE,'' as CHANGE_MILEAGE FROM (" + CommonConstants.VM_VEHICLE
				+ ") A left join (" + CommonConstants.VM_OWNER
				+ ") B on A.OWNER_NO = B.OWNER_NO and A.DEALER_CODE = B.DEALER_CODE ");
		// if (DictDataConstant.DICT_IS_YES.equals(vinflag))
		// {
		// sql += " AND (A.VIN LIKE '%" + license + "%' OR A.LICENSE LIKE '%" +
		// license
		// + "%') ";
		// }
		sql.append(
				"  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON A.DEALER_CODE=tb.DEALER_CODE AND A.BRAND=tb.BRAND_CODE ");
		sql.append(
				" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON A.DEALER_CODE = ts.DEALER_CODE  AND A.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code");
		sql.append(
				" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON A.DEALER_CODE = tm.DEALER_CODE  AND A.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code WHERE  ");
		sql.append(" A.DEALER_CODE = ? ");
		params.add(FrameworkUtil.getLoginInfo().getDealerCode());
		sql.append(Utility.getLikeCond("A", "LICENSE", queryParam.get("license"), "AND"));
		sql.append(Utility.getLikeCond("A", "VIN", queryParam.get("vin"), "AND"));
		sql.append(Utility.getLikeCond("B", "OWNER_NAME", queryParam.get("ownerName"), "AND"));
		System.out.print(sql.toString());
		List<Map> list = DAOUtil.findAll(sql.toString(), params);
		return list;
	}
	
}
