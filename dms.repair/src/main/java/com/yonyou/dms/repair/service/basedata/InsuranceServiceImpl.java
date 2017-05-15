package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.PO.basedata.InsurancePo;

/**
 * 保险接口实现
 * @author zhongshiwei
 * @date 2016年6月30日
 */
@Service
public class InsuranceServiceImpl implements InsuranceService {

    /**
     * 前台界面查询方法
     * @author zhongshiwei
     * @date 2016年6月30日
     * @param insuranceSQL
     * @return 查询
     * @throws ServiceBizException(non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.InsuranceService#InsuranceSQL(java.util.Map)
     */
    @Override
    public PageInfoDto InsuranceSQL(Map<String, String> queryParam) throws ServiceBizException{
      	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	String groupCode = Utility.getGroupEntity(loginInfo.getDealerCode(), "TM_INSURANCE");
        StringBuilder sb=new StringBuilder();      
        sb.append(" select DEALER_CODE,UPDATED_BY,UPDATED_AT,CREATED_BY,CREATED_AT,ZIP_CODE,FAX,INSURATION_CODE,INSURATION_NAME," );			
        sb.append(" INSURATION_SHORT_NAME,BANK,BANK_ACCOUNT,CONTACTOR_NAME,CONTACTOR_PHONE,CONTACTOR_MOBILE,INSURATION_LEVEL, ");			
        sb.append(" INSURATION_HIGHER,INSURATION_MEDIUM,INCREASE_RATE,BUSI_INSURANCE_REBATE_NUM,COM_INSURANCE_REBATE_NUM,BUSI_INSURANCE_REBATE_NUMX," );			
        sb.append(" COM_INSURANCE_REBATE_NUMX,BUSI_INSURANCE_REBATE_NUMD,COM_INSURANCE_REBATE_NUMD,BUSI_INSURANCE_REBATE_NUMC,");			
    	sb.append(" COM_INSURANCE_REBATE_NUMC,CASE WHEN OEM_TAG = 12781001 THEN 10571001 END AS OEM_TAG,CASE WHEN IS_APPLY = 12781001 THEN 10571001 END AS IS_APPLY,IS_UPDATE,ADDRESS from TM_INSURANCE where 1=1  and DEALER_CODE='"+groupCode+"'   ");			
      //	sb.append(" order by SORT ");		
        List<Object> insuranceSql=new ArrayList<Object>();
        PageInfoDto id=DAOUtil.pageQuery(sb.toString(), insuranceSql);
        return id;
    }


    /**
     * 根据ID 查找
     * @author zhongsw
     * @date 2016年8月5日
     * @param id
     * @return
     * @throws ServiceBizException(non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.InsuranceService#findById(java.lang.Long)
     */

    @Override
    public InsurancePo findByCode(String id) throws ServiceBizException{
      	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
      	String groupCode = Utility.getGroupEntity(loginInfo.getDealerCode(), "tm_insurance");
      	InsurancePo wtpo=  InsurancePo.findByCompositeKeys(groupCode, id);
        return wtpo; 
    }


  

    /**
     * 保险公司下拉框 
     * @author ZhengHe
     * @date 2016年8月10日
     * @return
     * @throws ServiceBizException(non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.InsuranceService#selectInsurance()
     */
    @Override
    public List<Map> selectInsurance() throws ServiceBizException {
        StringBuffer sqlsb=new StringBuffer("SELECT DEALER_CODE,INSURATION_CODE,INSURATION_NAME,INSURATION_SHORT_NAME,RECORD_VERSION FROM tm_insurance WHERE 1=1");
        sqlsb.append(" and IS_VALID="+DictCodeConstants.STATUS_IS_YES);
        List<Object> queryParam=new ArrayList<Object>();
        List<Map> result=DAOUtil.findAll(sqlsb.toString(), queryParam);
        return result;
    }


}
