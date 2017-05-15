package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.SADMS003ForeDTO;
import com.yonyou.dms.common.domains.PO.basedata.TiSalesLeadsCustomerCreateDcsPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
/**
 * 
* @ClassName: SalesLeadsCustomerCreateDao 
* @Description: DCC建档客户信息下发
* @author zhengzengliang 
* @date 2017年4月12日 下午6:02:24 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class SalesLeadsCustomerCreateDao extends OemBaseDAO{
	
	/**
	 * 
	* @Title: querySalesLeadsCustomerPOInfo 
	* @Description: TODO()查询待下发的DCC建档客户信息数据 
	* @return List<dccVO>    返回类型 
	* @throws
	 */
	public List<SADMS003ForeDTO> querySalesLeadsCustomerCreatePOInfo() {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT SLC.NID,\n" );
		sql.append("       SLC.ID ,\n" );
		sql.append("       SLC.PHONE,\n" );
		sql.append("       SLC.IS_SCAN,\n" );
		sql.append("       SLC.CREATE_BY,\n" );
		sql.append("       SLC.CREATE_DATE,\n" );
		sql.append("       SLC.UPDATE_BY,\n" );
		sql.append("       SLC.UPDATE_DATE,\n" );
		sql.append("       SLC.DEALER_CODE, \n" );
		sql.append("       C.DMS_CODE \n" );
		sql.append("          FROM TI_SALES_LEADS_CUSTOMER_CREATE_DCS SLC," );
		sql.append("          TM_DEALER A, TM_COMPANY B, TI_DEALER_RELATION C " );
		sql.append("          WHERE  1=1   \n" );
		sql.append("          AND  A.COMPANY_ID = B.COMPANY_ID AND B.COMPANY_CODE = C.DCS_CODE" );
		sql.append("          AND A.DEALER_CODE = SLC.DEALER_CODE" );
		sql.append("          AND ( SLC.IS_SCAN = 0 OR SLC.IS_SCAN is null)" );
		//wjs 2015-04-09
		sql.append("          AND C.STATUS="+OemDictCodeConstants.STATUS_ENABLE+" \n");
		//测试时TI_SALES_LEADS_CUSTOMER中的CAR_STYLE_ID字段应该是TM_VHCL_MATERIAL_GROUP表中GROUP_LEVEL为4的字段，
		//MODEL_ID应该是TM_VHCL_MATERIAL_GROUP表中该条记录CAR_STYLE_ID的父级的父级（GROUP_LEVEL为2的字段）
		//BRAND_ID应该是对应的MODEL_ID在TM_VHCL_MATERIAL_GROUP表中的父级（GROUP_LEVEL为1的字段）
		List<SADMS003ForeDTO> list = wrapperVO(OemDAOUtil.findAll(sql.toString(),null));
		return list;
	}
	protected List<SADMS003ForeDTO> wrapperVO(List<Map> rs) {
		List<SADMS003ForeDTO> resultList = new ArrayList<SADMS003ForeDTO>();
		try {
			if(null!=rs && rs.size()>0){
				for (int i = 0; i < rs.size(); i++) {
					SADMS003ForeDTO vo = new SADMS003ForeDTO();
					vo.setNid(Long.valueOf(CommonUtils.checkNull(rs.get(i).get("NID"))) );
					vo.setPhone(CommonUtils.checkNull(rs.get(i).get("PHONE")) );
					vo.setDealerCode(CommonUtils.checkNull(rs.get(i).get("DMS_CODE")) );
					resultList.add(vo);
				}
			}
		} catch (Exception e) {
			throw new ServiceBizException(e);
		}
		return resultList;
	}
	
	//根据NID将 TI_SALES_LEADS_CUSTOMER;中 IS_SCAN=0或者null的字段更新为1
	public int finishSalesLeadsCustomerSCANStatus(Long nid) {
		return TiSalesLeadsCustomerCreateDcsPO.update("IS_SCAN =1 , update_by= ? , update_date= ? ", " IS_SCAN = 0 OR IS_SCAN is null and nid = ? ", DEConstant.DE_UPDATE_BY, new Date(),nid);
	}

}
