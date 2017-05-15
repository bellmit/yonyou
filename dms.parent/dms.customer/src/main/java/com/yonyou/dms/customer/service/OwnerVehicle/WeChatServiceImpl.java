package com.yonyou.dms.customer.service.OwnerVehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
@Service
public class WeChatServiceImpl implements WeChatService {

	@Override
	public PageInfoDto queryWeChatInfo(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		sb.append("SELECT OWNER_NO, OWNER_NAME,VIN,MOBILE,GENDER,ADDRESS,E_MAIL,ZIP_CODE,PHONE,COALESCE(UPDATE_DATE, CREATE_DATE) CREATE_DATE ,DEALER_CODE FROM TM_WX_OWNER_CHANGE ");
		sb.append("  WHERE 1=1 ");
		this.setWhere(sb, queryParam, queryList);
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), queryList);
		return pageInfoDto;
	}

	private void setWhere(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {

		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			sb.append(" AND OWNER_NO like ?");
			queryList.add("%"+queryParam.get("ownerNo")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerName"))) {
			sb.append(" AND OWNER_NAME like ? ");
			queryList.add("%"+queryParam.get("ownerName")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sb.append(" AND VIN like ?");
			queryList.add("%"+queryParam.get("vin")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("mobile"))) {
			sb.append(" AND MOBILE like ? ");
			queryList.add("%"+queryParam.get("mobile")+"%");
		}
			
	}

}
