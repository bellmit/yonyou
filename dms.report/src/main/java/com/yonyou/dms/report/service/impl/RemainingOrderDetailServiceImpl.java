package com.yonyou.dms.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DefinedRowProcessor;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class RemainingOrderDetailServiceImpl implements RemainingOrderDetailService {

    /**
     * 按*查询
     * 
     * @author zhanshiwei
     * @date 2017年2月10日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.RemainingOrderDetailService#queryRemainingOrderDetail(java.util.Map)
     */

    @Override
    public PageInfoDto queryRemainingOrderDetail(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> queryList = new ArrayList<Object>();
        StringBuilder sb = this.getQuerySql(queryParam);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }
    
    
    /**
     * 导出
    * @author zhanshiwei
    * @date 2017年2月14日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.report.service.impl.RemainingOrderDetailService#queryRemainingOrderDetailList(java.util.Map)
    */
    	
    @Override
    public List<Map> queryRemainingOrderDetailList(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> queryList = new ArrayList<Object>();
        StringBuilder sb = this.getQuerySql(queryParam);
        return DAOUtil.findAll(sb.toString(), queryList);
    }

    /**
     * 查询sql拼接
     * 
     * @author zhanshiwei
     * @date 2017年2月10日
     * @param queryParam
     * @return
     */

    public StringBuilder getQuerySql(Map<String, String> queryParam) {
        final StringBuilder sb = new StringBuilder();
        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        Integer dKey = CommonConstants.D_KEY;
        if (StringUtils.isEquals(queryParam.get("isCheck"), "50331001")) {
            sb.append(" SELECT A.SOLD_BY,A.DEALER_CODE,tu.USER_NAME");
            List<Object> params = new ArrayList<>();
            StringBuilder sb1 = new StringBuilder("");
            this.getQueryColuSql(queryParam, sb1);
            DAOUtil.findAll(sb1.toString(), params, new DefinedRowProcessor() {

                @Override
                protected void process(Map<String, Object> row) {
                    sb.append(" ,COUNT((case when B.SERIES_CODE='").append(row.get("CODE")).append("'  then  ").append("'"
                                                                                                                       + row.get("CODE")
                                                                                                                       + "'").append(" end )) as ").append("'"
                                                                                                                                                           + row.get("CODE")
                                                                                                                                                           + "'").append(" \n");
                }
            });
            
            sb.append(" ,count(A.SOLD_BY) as countSOLD  ");
            sb.append("FROM TT_SALES_ORDER A \n");
            sb.append("LEFT JOIN TM_USER tu ON A.SOLD_BY=tu.USER_ID AND A.DEALER_CODE=tu.DEALER_CODE\n");
            sb.append(" LEFT JOIN TM_VS_PRODUCT B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE  ");
            sb.append(" WHERE A.BUSINESS_TYPE = 13001001 AND A.SO_STATUS IN (13011010,13011015,13011020,13011025,13011030,13011040,13011045,13011050) ");
            sb.append(" AND A.PRODUCT_CODE IS NOT NULL  " + " AND A.DEALER_CODE = '" + dealerCode + "' ");
            sb.append(" AND A.D_KEY = '" + dKey + "'");
            if (!StringUtils.isNullOrEmpty(queryParam.get("SOLD_BY"))) {
                sb.append(" AND A.SOLD_BY=" + queryParam.get("SOLD_BY") + "");
            }
            sb.append(" GROUP BY A.SOLD_BY,A.DEALER_CODE ORDER BY A.SOLD_BY ");
        } else if (StringUtils.isEquals(queryParam.get("isCheck"), "50331002")) {
            sb.append(" SELECT A.SOLD_BY,A.DEALER_CODE,tu.USER_NAME").append("\n");
            List<Object> params = new ArrayList<>();
            StringBuilder sb1 = new StringBuilder("");
            this.getQueryColuSql(queryParam, sb1);
            DAOUtil.findAll(sb1.toString(), params, new DefinedRowProcessor() {

                @Override
                protected void process(Map<String, Object> row) {
                    sb.append(" ,COUNT((case when B.MODEL_CODE='").append(row.get("CODE")).append("'  then  ").append("'"
                                                                                                                      + row.get("CODE")
                                                                                                                      + "'").append(" end )) as '").append(row.get("CODE")).append("' \n");
                }
            });
            sb.append(" ,count(A.SOLD_BY) as countSOLD  ");
            sb.append(" FROM TT_SALES_ORDER A ");
            sb.append(" LEFT JOIN TM_USER tu ON A.SOLD_BY=tu.USER_ID AND A.DEALER_CODE=tu.DEALER_CODE\n");
            sb.append(" LEFT JOIN TM_VS_PRODUCT B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE  ").append(" \n");
            sb.append(" WHERE A.BUSINESS_TYPE = 13001001 AND A.SO_STATUS IN (13011010,13011015,13011020,13011025,13011030,13011040,13011045,13011050) ").append(" \n");
            sb.append(" AND A.PRODUCT_CODE IS NOT NULL  " + " AND A.DEALER_CODE = '" + dealerCode + "' ").append(" \n");
            sb.append(" AND A.D_KEY = '" + dKey + "'").append(" \n");
            if (!StringUtils.isNullOrEmpty(queryParam.get("SOLD_BY"))) {
                sb.append(" AND A.SOLD_BY=" + queryParam.get("SOLD_BY") + "");
            }
            sb.append(" GROUP BY A.SOLD_BY,A.DEALER_CODE ORDER BY A.SOLD_BY ");

        }
        return sb;
    }

    /**
     * 表格列sql
     * 
     * @author zhanshiwei
     * @date 2017年2月10日
     * @param queryParam
     */

    public void getQueryColuSql(Map<String, String> queryParam, StringBuilder sb) {
        if (StringUtils.isEquals(queryParam.get("isCheck"), "50331001")) {
            sb.append(" select distinct DEALER_CODE,A.SERIES_CODE AS CODE,A.SERIES_NAME AS NAME from TM_SERIES A ");
            sb.append(" where A.OEM_TAG = " + DictCodeConstants.DICT_IS_YES + "  " + " and A.IS_VALID= "
                      + DictCodeConstants.DICT_IS_YES + "  ");
        } else if (StringUtils.isEquals(queryParam.get("isCheck"), "50331002")) {
            sb.append(" select distinct DEALER_CODE,A.MODEL_CODE AS CODE,A.MODEL_NAME AS NAME from TM_MODEL A ");
            sb.append(" where  A.OEM_TAG = " + DictCodeConstants.DICT_IS_YES + " " + " and A.IS_VALID= "
                      + DictCodeConstants.DICT_IS_YES + "  ");
        }
    }

    /**
     * 表格列查询
     * 
     * @author zhanshiwei
     * @date 2017年2月10日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.RemainingOrderDetailService#queryModel(java.util.Map)
     */

    @Override
    public List<Map> queryModel(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("");
        this.getQueryColuSql(queryParam, sb);
        return DAOUtil.findAll(sb.toString(), new ArrayList<Object>());
    }

    /**
     * 库存查询
     * 
     * @author zhanshiwei
     * @date 2017年2月10日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.RemainingOrderDetailService#queryOrderDetail(java.util.Map)
     */

    @Override
    public PageInfoDto queryOrderDetail(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> queryList = new ArrayList<Object>();
        StringBuilder sb = this.getDetailQuerySql(queryParam);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

    /**
     * @author zhanshiwei
     * @date 2017年2月10日
     * @param queryParam
     * @return
     */

    public StringBuilder getDetailQuerySql(Map<String, String> queryParam) {
        final StringBuilder sb = new StringBuilder();
        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        if (StringUtils.isEquals(queryParam.get("isCheck"), "50331001")) {
            sb.append(" select s.DEALER_CODE  ");
            List<Object> params = new ArrayList<>();
            StringBuilder sb1 = new StringBuilder("");
            this.getQueryColuSql(queryParam, sb1);
            DAOUtil.findAll(sb1.toString(), params, new DefinedRowProcessor() {
                @Override
                protected void process(Map<String, Object> row) {
                    sb.append(" ,COUNT((case when p.SERIES_CODE='").append(row.get("CODE")).append("'  then  ").append("'"
                                                                                                                       + row.get("CODE")
                                                                                                                       + "'").append(" end )) as ").append("'"+
                                                                                                                                row.get("CODE") + "'").append(" \n");
                }
            });
            sb.append(" ,count(1) as countSOLD  ");
            sb.append("from tm_vs_stock s \n");
            sb.append(" inner join    tm_vs_product p on s.DEALER_CODE=p.DEALER_CODE and s.product_code=p.product_code");
            sb.append(" where s.DEALER_CODE='" + dealerCode + "' and s.STOCK_STATUS=13041002  ");
        } else if (StringUtils.isEquals(queryParam.get("isCheck"), "50331002")) {
            sb.append(" select s.DEALER_CODE ");
            List<Object> params = new ArrayList<>();
            StringBuilder sb1 = new StringBuilder("");
            this.getQueryColuSql(queryParam, sb1);
            DAOUtil.findAll(sb1.toString(), params, new DefinedRowProcessor() {

                @Override
                protected void process(Map<String, Object> row) {
                    sb.append(" ,COUNT((case when p.MODEL_CODE='").append(row.get("CODE")).append("'  then  ").append("'"
                                                                                                                      + row.get("CODE")
                                                                                                                      + "'").append(" end )) as '").append(row.get("CODE")).append("' \n");
                }
            });
            sb.append(" ,count(1) as countSOLD  ");
            sb.append(" from tm_vs_stock s \n");
            sb.append(" inner join    tm_vs_product p on s.DEALER_CODE=p.DEALER_CODE and s.product_code=p.product_code");
            sb.append(" where s.DEALER_CODE='" + dealerCode + "' and s.STOCK_STATUS=13041002  ");
        }

        return sb;
    }
}
