package com.yonyou.dms.framework.domain;

import java.util.Map;

import org.javalite.activejdbc.ColumnMetadata;
import org.javalite.activejdbc.MetaModel;
import org.javalite.activejdbc.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.DALException;

public abstract class BaseModel extends Model {

    private static final Logger logger         = LoggerFactory.getLogger(BaseModel.class);
    public static final String Dealer_Code    = CommonConstants.PUBLIC_DEALER_CODE_NAME;
    private static final String ORGANIZATIONID = CommonConstants.PUBLIC_ORGANIZATION_NAME;

    /**
     * 定义保存之前处理方案
     * 
     * @author zhangxc
     * @date 2016年7月7日 (non-Javadoc)
     * @see org.javalite.activejdbc.CallbackSupport#beforeSave()
     */
    @Override
    public void beforeSave() {
        // 得到当前用户的DealerCode
        LoginInfoDto currentUserInfo = getSessionUserInfo();

        if (this.getId() == null) {
            //设置创建人
            set("created_by", "-1");
            //如果当前用户信息存在
            if(currentUserInfo != null){
                if (isExistsDealerCodeColumn()) {
                    if (this.getLong(Dealer_Code) == null) {
                        set(Dealer_Code, currentUserInfo.getDealerCode());
                    }
                }
                if (isExistsOrganiztionIdColumn()) {
                    if (this.getLong(ORGANIZATIONID) == null) {
                        set(ORGANIZATIONID, currentUserInfo.getOrgId());
                    }
                }
                // 如果不为空
                set("created_by", currentUserInfo.getUserId());
            }
        } else {
            if (isExistsDealerCodeColumn()) {
                String dealerCode = this.getString(Dealer_Code);
                if (currentUserInfo != null && currentUserInfo.getDealerCode() != null && dealerCode != null
                    && !dealerCode.equals(currentUserInfo.getDealerCode())) {
                    logger.info("Dealer_code:" + dealerCode + ";Info:" + currentUserInfo.getDealerCode());
                    throw new DALException("数据操作异常");
                }
            }
        }
        // 在修改的情况下，只修改update_by 值
        if (currentUserInfo != null && currentUserInfo.getUserAccount() != null) {
            set("updated_by", currentUserInfo.getUserId());
        }else{
            set("updated_by", "-1");
        }

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
        // 如果有经销商信息，检查经销商代码是否与当前session 中代码一致
        if (isExistsDealerCodeColumn()) {
            // 得到当前用户的DealerCode
            String currentDealerCode = getSessionDealerCode();
            String dealerCode = this.getString(Dealer_Code);
            if (currentDealerCode != null && dealerCode != null && !"-1".equals(dealerCode)
                && !dealerCode.equals(currentDealerCode)) {
                logger.info("Dealer_code:" + dealerCode + ";Info:" + currentDealerCode);
                throw new DALException("数据操作异常");
            }
        }
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
        // 如果有经销商信息，检查经销商代码是否与当前session 中代码一致
        if (isExistsDealerCodeColumn()) {
            // 得到当前用户的DealerCode
            String currentDealerCode = getSessionDealerCode();
            String dealerCode = this.getString(Dealer_Code);
            if (currentDealerCode != null && dealerCode != null && !dealerCode.equals(currentDealerCode)) {
                logger.info("Dealer_code:" + dealerCode + ";Info:" + currentDealerCode);
                throw new DALException("数据操作异常");
            }
        }
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
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
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
    private boolean isExistsDealerCodeColumn() {
        return isExistsDefineColumn(Dealer_Code);
    }

    /**
     * 判断是否存在ORGNAIZTION_ID
     * 
     * @author zhangxc
     * @date 2016年11月13日
     * @return
     */
    private boolean isExistsOrganiztionIdColumn() {
        return isExistsDefineColumn(ORGANIZATIONID);
    }

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
