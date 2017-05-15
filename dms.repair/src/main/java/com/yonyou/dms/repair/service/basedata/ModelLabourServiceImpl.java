package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmModelLabourPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author zhl
 * @date 2017年3月27日
 **/
@Service
@SuppressWarnings("rawtypes")
public class ModelLabourServiceImpl implements ModelLabourService {

	@Override
	public PageInfoDto QueryModelLabour(Map<String, String> queryParam) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	String groupCode = Utility.getGroupEntity(loginInfo.getDealerCode(), "TM_MODEL_LABOUR");
        StringBuilder sql = new StringBuilder("SELECT MODEL_LABOUR_CODE,MODEL_LABOUR_NAME, CASE WHEN DOWN_TAG = 12781001 THEN 10571001 END AS DOWN_TAG,DEALER_CODE FROM TM_MODEL_LABOUR where 1=1  and DEALER_CODE='"+groupCode+"' ");
        List<Object> params = new ArrayList<Object>();
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(),params);
        return pageInfoDto;
	}

	@Override
	public void addCarTypes(Map<String, String> map) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	String groupCode = Utility.getGroupEntity(loginInfo.getDealerCode(), "TM_MODEL_LABOUR");
    	TmModelLabourPO mlPo=new TmModelLabourPO();
      	if (!StringUtils.isNullOrEmpty(map.get("MODEL_LABOUR_CODE"))) {
      		mlPo.setString("MODEL_LABOUR_CODE", map.get("MODEL_LABOUR_CODE"));}
	    if (!StringUtils.isNullOrEmpty(map.get("MODEL_LABOUR_NAME"))) {
	    	mlPo.setString("MODEL_LABOUR_NAME", map.get("MODEL_LABOUR_NAME"));}
	    	mlPo.setInteger("DOWN_TAG", CommonConstants.DICT_IS_NO);
	    		mlPo.saveIt();
	    }

	@Override
	public void updateCarType(Map<String, String> map) throws ServiceBizException {
	 	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
      	String groupCode = Utility.getGroupEntity(loginInfo.getDealerCode(), "TM_REPAIR_TYPE");
		String name  =	FrameworkUtil.getLoginInfo().getUserName();
		System.out.println(groupCode+"----"+name);
    	TmModelLabourPO labourPo = TmModelLabourPO.findByCompositeKeys(map.get("MODEL_LABOUR_CODE"),groupCode);
	    if (labourPo != null) {
	    	TmModelLabourPO sto = TmModelLabourPO.findByCompositeKeys(map.get("MODEL_LABOUR_CODE"),groupCode);
	    	if (!StringUtils.isNullOrEmpty(map.get("MODEL_LABOUR_NAME"))) {
	    	 sto.setString("MODEL_LABOUR_NAME", map.get("MODEL_LABOUR_NAME"));}
	    	 sto.saveIt();
	    }
		
	}

	@Override
	public TmModelLabourPO findByCode(String id) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
      	String groupCode = Utility.getGroupEntity(loginInfo.getDealerCode(), "TM_MODEL_LABOUR");
      	TmModelLabourPO PO=  TmModelLabourPO.findByCompositeKeys(id,groupCode );
        return PO; 
	}

}
