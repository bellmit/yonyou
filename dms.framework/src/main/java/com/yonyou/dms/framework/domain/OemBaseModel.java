package com.yonyou.dms.framework.domain;

import java.util.Map;

import org.javalite.activejdbc.ColumnMetadata;
import org.javalite.activejdbc.MetaModel;
import org.javalite.activejdbc.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;

public abstract class OemBaseModel extends Model {

    private static final Logger logger         = LoggerFactory.getLogger(OemBaseModel.class);
//    private static final String Dealer_Code    = CommonConstants.PUBLIC_DEALER_CODE_NAME;
//    private static final String ORGANIZATIONID = CommonConstants.PUBLIC_ORGANIZATION_NAME;

    /**
     * 定义保存之前处理方案
     * 
     * @author zhangxc
     * @date 2016年7月7日 (non-Javadoc)
     * @see org.javalite.activejdbc.CallbackSupport#beforeSave()
     */
    @Override
    public void beforeSave() {

    }

    /**
     * 如果有经销商信息，检查经销商代码是否与当前session 中代码一致
     * 
     * @author zhangxc
     * @date 2016年6月30日 (non-Javadoc)
     * @see org.javalite.activejdbc.CallbackSupport#afterLoad()
     */
    @Override
    public void afterLoad() {
    }

    /**
     * 在删除之前进行检查
     * 
     * @author zhangxc
     * @date 2016年7月7日 (non-Javadoc)
     * @see org.javalite.activejdbc.CallbackSupport#beforeDelete()
     */
    @Override
    public void beforeDelete() {
    	
    }

    /**
     * 获得当前用户的经销商代码
     * 
     * @author zhangxc
     * @date 2016年11月9日
     * @return
     */
    private String getSessionDealerCode() {
        try {
            LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
            return loginInfo.getDealerCode();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        };
        return null;
    }

    /**
     * 获得当前用户的经销商代码
     * 
     * @author zhangxc
     * @date 2016年11月9日
     * @return
     */
    private LoginInfoDto getSessionUserInfo() {
        try {
            LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
            return loginInfo;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        };
        return null;
    }

    /**
     * 判断是否包含经销商代码
     * 
     * @author zhangxc
     * @date 2016年6月30日
     * @return 如果包含，返回true,否则返回false
     */
//    private boolean isExistsDealerCodeColumn() {
//        return isExistsDefineColumn(Dealer_Code);
//    }

    /**
     * 判断是否存在ORGNAIZTION_ID
     * 
     * @author zhangxc
     * @date 2016年11月13日
     * @return
     */
//    private boolean isExistsOrganiztionIdColumn() {
//        return isExistsDefineColumn(ORGANIZATIONID);
//    }

    /**
     * 判断是否存在某个字段
     * 
     * @author zhangxc
     * @date 2016年11月13日
     * @param columnName
     * @return
     */
    private boolean isExistsDefineColumn(String columnName) {
        MetaModel metaModel = this.getMetaModelLocal();
        Map<String, ColumnMetadata> columnMetas = metaModel.getColumnMetadata();

        for (Map.Entry<String, ColumnMetadata> entry : columnMetas.entrySet()) {
            if (columnName.equalsIgnoreCase(entry.getKey())) {
                return true;
            }
        }
        return false;
    }

}
