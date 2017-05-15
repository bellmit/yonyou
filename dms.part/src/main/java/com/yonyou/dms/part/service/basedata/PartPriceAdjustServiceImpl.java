
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartPriceAdjustServiceImpl.java
*
* @Author : zhongshiwei
*
* @Date : 2016年7月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月17日    zhongshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmPartStockItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.PartPriceAdjustDTO;

/**
 * 配件价格调整
 * 
 * @author zhongshiwei
 * @date 2016年7月17日
 */
@SuppressWarnings("rawtypes")
@Service
public class PartPriceAdjustServiceImpl implements PartPriceAdjustService {

    /**
     * 查询界面信息
     * 
     * @author zhongsw
     * @date 2016年8月29日
     * @param queryParam
     * @return
     * @throws ServiceBizException(non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartPriceAdjustService#partPriceAdjustSQL(java.util.Map)
     */
    @Override
    public PageInfoDto partPriceAdjustSQL(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> part = new ArrayList<Object>();
        String a = "";
        String b = "";
        String c = "";
        String d = "";
        String e = "";
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isNullOrEmpty(queryParam.get("BASE_COST_TYPE"))) {
            if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_COST)) {
                d = "COST_PRICE";//成本价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE)) {
                d = "INSTRUCT_PRICE";//建议销售价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INDEMNITY)) {
                d = "CLAIM_PRICE";//索赔价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT)) {
                d = "NODE_PRICE";//网点价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATEST)) {
                d = "LATEST_PRICE";//最新进货价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE_PRICE)) {
                d = "INSURANCE_PRICE";//保险价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE)) {
                d = "SALES_PRICE";//销售价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LIMIT_PRICE)) {
                d = "LIMIT_PRICE";//销售限价
            }
            if (d != null && !"".equals(d)) {
                if (d.equals("INSTRUCT_PRICE") || d.equals("NODE_PRICE")) {
                    a = " B."+d; 
                }else{
                    a = " A."+d; 
                }
            }
         }
        if (!StringUtils.isNullOrEmpty(queryParam.get("IS_COST_TYPE"))) {
            if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT)) {
                e = "NODE_PRICE";//网点价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE_PRICE)) {
                e = "INSURANCE_PRICE";//保险价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE)) {
                e = "SALES_PRICE";//销售价
            }
            if (e != null && !"".equals(e)) {
                if (e.equals("INSTRUCT_PRICE")|| e.equals("NODE_PRICE")) {
                    b = " B."+e; 
                }else{
                    b = " A."+e; 
                }
            }
         }
        if (a != null && !"".equals(a) && b != null && !"".equals(b)) {
            c = " CASE WHEN "+a+"=0 THEN 0 ELSE ("+b+"-"+a+")/"+a+"  END ";
        }
        if (c != null && !"".equals(c)) {
            sb.append("SELECT "+c+" AS RATE, ");
        }else{
            sb.append("SELECT 0 AS RATE,");
        }
        sb.append(" ROUND(CASE WHEN  A.LATEST_PRICE = 0  THEN  0 ELSE  (A.SALES_PRICE - ");
        sb.append(" IFNULL(A.LATEST_PRICE,0))/A.LATEST_PRICE END,4) AS ADDS, A.DEALER_CODE, A.PART_NO, A.STORAGE_CODE, A.PART_BATCH_NO,");
        sb.append(" A.STORAGE_POSITION_CODE, A.PART_NAME, A.SPELL_CODE, A.PART_GROUP_CODE, A.UNIT_CODE,");
        sb.append(" A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK,  A.STOCK_QUANTITY, A.SALES_PRICE,A.NODE_PRICE,");
        sb.append(" A.CLAIM_PRICE, B.LIMIT_PRICE, A.LATEST_PRICE,  A.COST_PRICE, A.COST_AMOUNT,");
        sb.append(" A.BORROW_QUANTITY, A.PART_STATUS, A.LEND_QUANTITY,");
        sb.append(" B.OPTION_NO, B.PRODUCTING_AREA, B.BRAND, B.MIN_PACKAGE,");
        sb.append(" B.INSTRUCT_PRICE,A.INSURANCE_PRICE,FROM_ENTITY,DOWN_TAG,D.PART_MODEL_GROUP_CODE_SET,G.STORAGE_NAME ");
        sb.append(" FROM TM_PART_STOCK_ITEM A ");
        sb.append(" LEFT JOIN tm_storage G ON G.DEALER_CODE=A.DEALER_CODE AND G.STORAGE_CODE=A.STORAGE_CODE");
        sb.append(" LEFT OUTER JOIN ("+CommonConstants.VM_PART_INFO+") B ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO)  left join TM_PART_STOCK D ON D.DEALER_CODE=A.DEALER_CODE");
        sb.append(" AND A.PART_NO=D.PART_NO AND A.STORAGE_CODE=D.STORAGE_CODE");
        sb.append(" WHERE A.DEALER_CODE = ?");
        part.add(FrameworkUtil.getLoginInfo().getDealerCode());
        sb.append(" AND A.D_KEY =?");
        part.add(CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(queryParam.get("STORAGE_CODE"))) {
           sb.append(" AND A.STORAGE_CODE=?");
           part.add(queryParam.get("STORAGE_CODE"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("PART_NO"))) {
            sb.append(" AND A.PART_NO=?");
            part.add(queryParam.get("PART_NO"));
         }
        if (!StringUtils.isNullOrEmpty(queryParam.get("PART_NAME"))) {
            sb.append(" AND A.PART_NAME=?");
            part.add(queryParam.get("PART_NAME"));
         }
        if (!StringUtils.isNullOrEmpty(queryParam.get("PART_GROUP_CODE"))) {
            sb.append(" AND A.PART_GROUP_CODE=?");
            part.add(queryParam.get("PART_GROUP_CODE"));
         }
        
        if (!StringUtils.isNullOrEmpty(queryParam.get("RATE_END"))) {
            sb.append(" AND " + c + "<" + queryParam.get("RATE_END"));
         }
        if (!StringUtils.isNullOrEmpty(queryParam.get("RATE_BEGIN"))) {
            sb.append("  AND " + c + ">" + queryParam.get("RATE_BEGIN"));
         }
        
        if (!StringUtils.isNullOrEmpty(queryParam.get("partModelGroupCodeSet"))) {
            sb.append(" AND D.PART_MODEL_GROUP_CODE_SET=?");
            part.add(queryParam.get("partModelGroupCodeSet"));
         }
        if (!StringUtils.isNullOrEmpty(queryParam.get("SALES_COST"))) {
            sb.append(" AND A.SALES_PRICE<A.COST_PRICE");
         }
        if (!StringUtils.isNullOrEmpty(queryParam.get("SALES_ADVICE"))) {
            sb.append(" AND A.SALES_PRICE<B.INSTRUCT_PRICE");
         }
        if (!StringUtils.isNullOrEmpty(queryParam.get("SALES_LIMIT"))) {
            sb.append(" AND A.SALES_PRICE > IFNULL(B.LIMIT_PRICE, 0)");
         }
        if (!StringUtils.isNullOrEmpty(queryParam.get("REMARK"))) {
            sb.append(" AND A.REMARK=?");
            part.add(queryParam.get("REMARK"));
         }
        if (!StringUtils.isNullOrEmpty(queryParam.get("PRICE_LOW"))) {
            sb.append(" AND A.LATEST_PRICE >=?");
            part.add(queryParam.get("PRICE_LOW"));
         }
        if (!StringUtils.isNullOrEmpty(queryParam.get("PRICE_HIGH"))) {
            sb.append(" AND A.LATEST_PRICE <=?");
            part.add(queryParam.get("PRICE_HIGH"));
         }
        return DAOUtil.pageQuery(sb.toString(),part);
    }
    
    public List<Map> querySql(Map<String, String> queryParam) throws ServiceBizException{
        List<Object> part = new ArrayList<Object>();
        String a = "";
        String b = "";
        String c = "";
        String d = "";
        String e = "";
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isNullOrEmpty(queryParam.get("BASE_COST_TYPE"))) {
            if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_COST)) {
                d = "COST_PRICE";//成本价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE)) {
                d = "INSTRUCT_PRICE";//建议销售价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INDEMNITY)) {
                d = "CLAIM_PRICE";//索赔价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT)) {
                d = "NODE_PRICE";//网点价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATEST)) {
                d = "LATEST_PRICE";//最新进货价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE_PRICE)) {
                d = "INSURANCE_PRICE";//保险价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE)) {
                d = "SALES_PRICE";//销售价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LIMIT_PRICE)) {
                d = "LIMIT_PRICE";//销售限价
            }
            if (d != null && !"".equals(d)) {
                if (d.equals("INSTRUCT_PRICE") || d.equals("NODE_PRICE")) {
                    a = " B."+d; 
                }else{
                    a = " A."+d; 
                }
            }
         }
        if (!StringUtils.isNullOrEmpty(queryParam.get("IS_COST_TYPE"))) {
            if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT)) {
                e = "NODE_PRICE";//网点价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE_PRICE)) {
                e = "INSURANCE_PRICE";//保险价
            }else if (queryParam.get("BASE_COST_TYPE").equals(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE)) {
                e = "SALES_PRICE";//销售价
            }
            if (e != null && !"".equals(e)) {
                if (e.equals("INSTRUCT_PRICE")|| e.equals("NODE_PRICE")) {
                    b = " B."+e; 
                }else{
                    b = " A."+e; 
                }
            }
         }
        if (a != null && !"".equals(a) && b != null && !"".equals(b)) {
            c = " CASE WHEN "+a+"=0 THEN 0 ELSE ("+b+"-"+a+")/"+a+"  END ";
        }
        if (c != null && !"".equals(c)) {
            sb.append("SELECT "+c+" AS RATE, ");
        }else{
            sb.append("SELECT 0 AS RATE,");
        }
        sb.append(" ROUND(CASE WHEN  A.LATEST_PRICE = 0  THEN  0 ELSE  (A.SALES_PRICE - ");
        sb.append(" IFNULL(A.LATEST_PRICE,0))/A.LATEST_PRICE END,4) AS ADDS, A.DEALER_CODE, A.PART_NO, A.STORAGE_CODE, A.PART_BATCH_NO,");
        sb.append(" A.STORAGE_POSITION_CODE, A.PART_NAME, A.SPELL_CODE, A.PART_GROUP_CODE, A.UNIT_CODE,");
        sb.append(" A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK,  A.STOCK_QUANTITY, A.SALES_PRICE,A.NODE_PRICE,");
        sb.append(" A.CLAIM_PRICE, B.LIMIT_PRICE, A.LATEST_PRICE,  A.COST_PRICE, A.COST_AMOUNT,");
        sb.append(" A.BORROW_QUANTITY, A.PART_STATUS, A.LEND_QUANTITY,");
        sb.append(" B.OPTION_NO, B.PRODUCTING_AREA, B.BRAND, B.MIN_PACKAGE,");
        sb.append(" B.INSTRUCT_PRICE,A.INSURANCE_PRICE,FROM_ENTITY,DOWN_TAG,D.PART_MODEL_GROUP_CODE_SET ");
        sb.append(" FROM TM_PART_STOCK_ITEM A ");
        sb.append(" LEFT OUTER JOIN ("+CommonConstants.VM_PART_INFO+") B ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO)  left join TM_PART_STOCK D ON D.DEALER_CODE=A.DEALER_CODE");
        sb.append(" AND A.PART_NO=D.PART_NO AND A.STORAGE_CODE=D.STORAGE_CODE");
        sb.append(" WHERE A.DEALER_CODE = ?");
        part.add(FrameworkUtil.getLoginInfo().getDealerCode());
        sb.append(" AND A.D_KEY =?");
        part.add(CommonConstants.D_KEY);
        return DAOUtil.findAll(sb.toString(), part);
    }

    /**
     * PartPriceAdjustPO对象赋值
     * 
     * @author zhongsw
     * @date 2016年8月29日
     * @param wtp
     * @param partPriceAdjustdto
     * @throws ServiceBizException
     */
    public void updataPPo(TmPartStockPO partStockPO,TmPartStockItemPO partStockItemPO, Map<String, String> param) throws ServiceBizException {
        if (!StringUtils.isNullOrEmpty(param.get("ROND"))) {
            if (DictCodeConstants.DICT_YUAN.equals(param.get("ROND"))) {
                if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_COST)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                        partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INDEMNITY)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                        partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATEST)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                        partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE_PRICE)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                        partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LIMIT_PRICE)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                        partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    }
                }
            } else if (DictCodeConstants.DICT_JIAO.equals(param.get("ROND"))) {
                if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_COST)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                        partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INDEMNITY)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                        partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATEST)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                        partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE_PRICE)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                        partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LIMIT_PRICE)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                        partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*10)/10);
                    }
                }
            } else if (DictCodeConstants.DICT_FEN.equals(param.get("ROND"))) {
                if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_COST)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                        partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INDEMNITY)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                        partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATEST)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                        partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE_PRICE)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                        partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    }
                } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LIMIT_PRICE)) {
                    if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                        partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                        partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                        partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))*100)/100);
                    }
                }
            }
        } else {
            if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_COST)) {
                if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                    partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                    partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                    partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                }
            } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE)) {
                if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                    partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                }
            } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INDEMNITY)) {
                if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                    partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                    partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                }
            } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT)) {
                if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                    partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                    partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                    partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                }
            } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATEST)) {
                if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                    partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                    partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                    partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                }
            } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE_PRICE)) {
                if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                    partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                    partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                    partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                }
            } else if (Integer.parseInt(param.get("BASE_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LIMIT_PRICE)) {
                if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE )) {
                    partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT )) {
                    partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                } else if (Integer.parseInt(param.get("IS_COST_TYPE"))==(DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE )) {
                    partStockPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                    partStockItemPO.setDouble("INSURANCE_PRICE", Math.round(Double.parseDouble(param.get("SALES_PRICES"))));
                }
            }
        }
        partStockPO.saveIt();
        partStockItemPO.saveIt();
    }

    /**
     * 根据ID修改价格
     * 
     * @author zhongsw
     * @date 2016年8月2日
     * @param id
     * @param partPriceAdjustDTO
     * @throws ServiceBizException(non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartPriceAdjustService#updatePartPriceAdjust(java.lang.Long,
     * com.yonyou.dms.part.domains.DTO.basedata.PartPriceAdjustDTO)
     */
    @Override
    public void updatePartPriceAdjust(String id,String ids,String idd,Map<String, String> param) throws ServiceBizException {
    	TmPartStockPO partStockPO = TmPartStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id,ids);
    	TmPartStockItemPO partStockItemPO = TmPartStockItemPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id,ids,idd);
    	updataPPo(partStockPO,partStockItemPO, param);
    }

    /**
     * 根据ID查找
     * 
     * @author zhongsw
     * @date 2016年8月2日
     * @param id
     * @return
     * @throws ServiceBizException(non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartPriceAdjustService#findById(java.lang.Long)
     */

    @Override
    public List<Map> findById(String id,String ids) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        params.add(ids);
        return DAOUtil.findAll("select * from tm_part_stock where  PART_NO =? AND STORAGE_CODE=?", params);
    }

    
    
    /**
     * 导出
     * 
     * @author zhongsw
     * @date 2016年7月20日
     * @param queryParam
     * @return
     * @throws ServiceBizException(non-Javadoc)
     * @see com.yonyou.dms.manage.service.sample.UserService#queryUsersForExport(java.util.Map)
     */

    @Override
    public List<Map> queryExport(Map<String, String> queryParam) throws ServiceBizException {
        return querySql(queryParam);
    }

    /**
     * 封装SQL 语句
     * 
     * @author zhongsw
     * @date 2016年7月20日
     * @param queryParam
     * @param params
     * @return
     */

//    private String getQuerySql(Map<String, String> queryParam, List<Object> partPriceAdjustSql) throws ServiceBizException {
//        StringBuilder sb = new StringBuilder("select tps.part_stock_id,tps.dealer_code,ts.storage_name,tps.storage_position_code,");
//        sb.append("tps.part_code,tps.part_name,tps.limit_price,tps.advice_sale_price,tps.sales_price,tps.cost_price,tps.claim_price,");
//        sb.append("(tps.SALES_PRICE-tps.COST_PRICE)/tps.COST_PRICE as markup_percentage  ");
//        sb.append("from tt_part_stock tps left join tm_store ts on tps.STORAGE_CODE=ts.STORAGE_CODE and tps.dealer_code=ts.dealer_code where 1=1 ");
//        if (!StringUtils.isNullOrEmpty(queryParam.get("storage_code"))) {
//            sb.append("  and tps.STORAGE_CODE = ?");
//            partPriceAdjustSql.add(queryParam.get("storage_code"));
//        }
//        if (!StringUtils.isNullOrEmpty(queryParam.get("part_code"))) {
//            sb.append("  and tps.PART_CODE like ?");
//            partPriceAdjustSql.add("%" + queryParam.get("part_code") + "%");
//        }
//        if (!StringUtils.isNullOrEmpty(queryParam.get("part_name"))) {
//            sb.append("  and tps.PART_NAME like ?");
//            partPriceAdjustSql.add("%" + queryParam.get("part_name") + "%");
//        }
//        if (!StringUtils.isNullOrEmpty(queryParam.get("part_group_code"))) {
//            sb.append("  and tpi.PART_GROUP_CODE like ?");
//            partPriceAdjustSql.add("%" + queryParam.get("part_group_code") + "%");
//        }
//        if ((Integer.toString(DictCodeConstants.SALES_COST)).equals(queryParam.get("sales_cost"))) {
//            sb.append(" and tps.SALES_PRICE < tps.COST_PRICE");
//        }
//        if ((Integer.toString(DictCodeConstants.SALES_ADVICE)).equals(queryParam.get("sales_advice"))) {
//            sb.append(" and tps.SALES_PRICE < tps.ADVICE_SALE_PRICE");
//        }
//        if ((Integer.toString(DictCodeConstants.SALES_LIMIT)).equals(queryParam.get("sales_limit"))) {
//            sb.append(" and tps.SALES_PRICE > tps.LIMIT_PRICE");
//        }
//        if (!StringUtils.isNullOrEmpty(queryParam.get("min_markup_percentage"))) {
//
//            if (DictCodeConstants.SALES_PRICE == (Integer.parseInt(String.valueOf(queryParam.get("benchmark_type"))))) {
//                sb.append(" and (tps.SALES_PRICE-t.COST_PRICE)/tps.COST_PRICE < ?");
//            }
//            if (DictCodeConstants.ADVICE_SALE_PRICE == (Integer.parseInt(String.valueOf(queryParam.get("benchmark_type"))))) {
//                sb.append(" and (tps.SALES_PRICE-t.COST_PRICE)/tps.COST_PRICE < ?");
//            }
//            if (DictCodeConstants.CLAIM_PRICE == (Integer.parseInt(String.valueOf(queryParam.get("benchmark_type"))))) {
//                sb.append(" and (tps.SALES_PRICE-t.COST_PRICE)/tps.COST_PRICE < ?");
//            }
//            if (DictCodeConstants.LIMIT_PRICE == (Integer.parseInt(String.valueOf(queryParam.get("benchmark_type"))))) {
//                sb.append(" and (tps.SALES_PRICE-t.COST_PRICE)/tps.COST_PRICE < ?");
//            }
//            if (DictCodeConstants.COST_PRICE == (Integer.parseInt(String.valueOf(queryParam.get("benchmark_type"))))) {
//                sb.append(" and (tps.SALES_PRICE-t.COST_PRICE)/tps.COST_PRICE < ?");
//            }
//            if (null == queryParam.get("benchmark_type")) {
//                throw new ServiceBizException("基准价类型不能为空！");
//            }
//            partPriceAdjustSql.add(queryParam.get("min_markup_percentage"));
//        }
//        if (!StringUtils.isNullOrEmpty(queryParam.get("max_markup_percentage"))) {
//
//            if (DictCodeConstants.SALES_PRICE == (Integer.parseInt(String.valueOf(queryParam.get("benchmark_type"))))) {
//                sb.append(" and (tps.SALES_PRICE-t.COST_PRICE)/tps.COST_PRICE > ?");
//            }
//            if (DictCodeConstants.ADVICE_SALE_PRICE == (Integer.parseInt(String.valueOf(queryParam.get("benchmark_type"))))) {
//                sb.append(" and (tps.SALES_PRICE-t.COST_PRICE)/tps.COST_PRICE > ?");
//            }
//            if (DictCodeConstants.CLAIM_PRICE == (Integer.parseInt(String.valueOf(queryParam.get("benchmark_type"))))) {
//                sb.append(" and (tps.SALES_PRICE-t.COST_PRICE)/tps.COST_PRICE > ?");
//            }
//            if (DictCodeConstants.LIMIT_PRICE == (Integer.parseInt(String.valueOf(queryParam.get("benchmark_type"))))) {
//                sb.append(" and (tps.SALES_PRICE-t.COST_PRICE)/tps.COST_PRICE > ?");
//            }
//            if (DictCodeConstants.COST_PRICE == (Integer.parseInt(String.valueOf(queryParam.get("benchmark_type"))))) {
//                sb.append(" and (tps.SALES_PRICE-t.COST_PRICE)/tps.COST_PRICE > ?");
//            }
//            if (null == queryParam.get("benchmark_type")) {
//                throw new ServiceBizException("基准价类型不能为空！");
//            }
//            partPriceAdjustSql.add(queryParam.get("max_markup_percentage"));
//        }
//        sb.append(" group by tps.PART_STOCK_ID");
//        return sb.toString();
//    }

    /**
     * 批量修改信息
     * 
     * @author dingchaoyu 
     * @date 2016年8月3日
     * @param partPriceAdjustDTO
     * @throws ServiceBizException(non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartPriceAdjustService#modifyUser(com.yonyou.dms.part.domains.DTO.basedata.PartPriceAdjustDTO)
     */
    @Override
    public void modifyPrice(Map queryParam) throws ServiceBizException {
        String[] id = queryParam.get("PART_NO").toString().split(",");
        String[] ids = queryParam.get("STORAGE_CODE").toString().split(",");
        String[] idd = queryParam.get("PART_BATCH_NO").toString().split(",");
        for(int i=0;i<id.length;i++){
            TmPartStockPO partStockPO = TmPartStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id[i],ids[i]);
            TmPartStockItemPO partStockItemPO = TmPartStockItemPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id[i],ids[i],idd[i]);
            updataPPos(partStockPO,partStockItemPO, queryParam);
        }
    }

    public void updataPPos(TmPartStockPO partStockPO,TmPartStockItemPO partStockItemPO, Map param) throws ServiceBizException {
        if (!StringUtils.isNullOrEmpty(param.get("SALES_PRICE"))) {
            String[] s = param.get("SALES_PRICE").toString().split(",");
            for(int i=0;i<s.length;i++){
                if (!StringUtils.isNullOrEmpty(param.get("Price"))) {
                    if (param.get("Price").toString().equals("12921001")) {
                        partStockPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(s[i])));
                        partStockItemPO.setDouble("SALES_PRICE", Math.round(Double.parseDouble(s[i])));
                    }else if(param.get("Price").toString().equals("12921002")){
                        partStockPO.setDouble("SALES_PRICE", (Math.round((Double.parseDouble(s[i])*10))/10));
                        partStockItemPO.setDouble("SALES_PRICE", (Math.round((Double.parseDouble(s[i])*10))/10));
                    }else if(param.get("Price").toString().equals("12921003")){
                        partStockPO.setDouble("SALES_PRICE", (Math.round((Double.parseDouble(s[i])*100))/100));
                        partStockItemPO.setDouble("SALES_PRICE", (Math.round((Double.parseDouble(s[i])*100))/100));
                    }
                }else{
                    partStockPO.setDouble("SALES_PRICE", Double.parseDouble(s[i]));
                    partStockItemPO.setDouble("SALES_PRICE", Double.parseDouble(s[i])); 
                }
            }
        }else if(!StringUtils.isNullOrEmpty(param.get("NODE_PRICE"))) {
            String[] n = param.get("NODE_PRICE").toString().split(",");
            for(int i=0;i<n.length;i++){
                if (!StringUtils.isNullOrEmpty(param.get("Price"))) {
                    if (param.get("Price").toString().equals("12921001")) {
                        partStockPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(n[i])));
                        partStockItemPO.setDouble("NODE_PRICE", Math.round(Double.parseDouble(n[i])));
                    }else if(param.get("Price").toString().equals("12921002")){
                        partStockPO.setDouble("NODE_PRICE", (Math.round((Double.parseDouble(n[i])*10))/10));
                        partStockItemPO.setDouble("NODE_PRICE", (Math.round((Double.parseDouble(n[i])*10))/10));
                    }else if(param.get("Price").toString().equals("12921003")){
                        partStockPO.setDouble("NODE_PRICE", (Math.round((Double.parseDouble(n[i])*100))/100));
                        partStockItemPO.setDouble("NODE_PRICE", (Math.round((Double.parseDouble(n[i])*100))/100));
                    }
                }else{
                    partStockPO.setDouble("SALES_PRICE", Double.parseDouble(n[i]));
                    partStockItemPO.setDouble("SALES_PRICE", Double.parseDouble(n[i])); 
                }
            }
        }else if(!StringUtils.isNullOrEmpty(param.get("INSTRUCT_PRICE"))) {
            String[] a = param.get("INSTRUCT_PRICE").toString().split(",");
            for(int i=0;i<a.length;i++){
                if (!StringUtils.isNullOrEmpty(param.get("Price"))) {
                    if (param.get("Price").toString().equals("12921001")) {
                        partStockPO.setDouble("INSTRUCT_PRICE", Math.round(Double.parseDouble(a[i])));
                        partStockItemPO.setDouble("INSTRUCT_PRICE", Math.round(Double.parseDouble(a[i])));
                    }else if(param.get("Price").toString().equals("12921002")){
                        partStockPO.setDouble("INSTRUCT_PRICE", (Math.round((Double.parseDouble(a[i])*10))/10));
                        partStockItemPO.setDouble("INSTRUCT_PRICE", (Math.round((Double.parseDouble(a[i])*10))/10));
                    }else if(param.get("Price").toString().equals("12921003")){
                        partStockPO.setDouble("INSTRUCT_PRICE", (Math.round((Double.parseDouble(a[i])*100))/100));
                        partStockItemPO.setDouble("INSTRUCT_PRICE", (Math.round((Double.parseDouble(a[i])*100))/100));
                    }
                }else{
                    partStockPO.setDouble("SALES_PRICE", Double.parseDouble(a[i]));
                    partStockItemPO.setDouble("SALES_PRICE", Double.parseDouble(a[i])); 
                }
            }
        }
        partStockPO.saveIt();
        partStockItemPO.saveIt();
    }
   
    /**
     * 批量设置对象属性
     * 
     * @author zhongsw
     * @date 2016年7月6日
     * @param partInfoPO
     * @param PartPriceAdjustDto
     */

    private void setPartUpdatePO(TmPartStockPO partInfoPO, PartPriceAdjustDTO PartPriceAdjustDto) {
//        // 公式：调整价=基准价*(1+加价率)
//        if (DictCodeConstants.COST_PRICE == Integer.parseInt(String.valueOf(PartPriceAdjustDto.getBenchmark_type()))) {// 成本价
//            partInfoPO.setDouble("sales_price",
//                                 partInfoPO.getDouble("cost_price") * (1 + PartPriceAdjustDto.getMarkup_percentage()));
//        }
//        if (DictCodeConstants.LIMIT_PRICE == Integer.parseInt(String.valueOf(PartPriceAdjustDto.getBenchmark_type()))) {// 销售限价
//            partInfoPO.setDouble("sales_price",
//                                 partInfoPO.getDouble("limit_price") * (1 + PartPriceAdjustDto.getMarkup_percentage()));
//        }
//        if (DictCodeConstants.CLAIM_PRICE == Integer.parseInt(String.valueOf(PartPriceAdjustDto.getBenchmark_type()))) {// 索赔价
//            partInfoPO.setDouble("sales_price",
//                                 partInfoPO.getDouble("claim_price") * (1 + PartPriceAdjustDto.getMarkup_percentage()));
//        }
//        if (DictCodeConstants.ADVICE_SALE_PRICE == Integer.parseInt(String.valueOf(PartPriceAdjustDto.getBenchmark_type()))) {// 建议销售价
//            partInfoPO.setDouble("sales_price", partInfoPO.getDouble("advice_sale_price")
//                                                * (1 + PartPriceAdjustDto.getMarkup_percentage()));
//        }
//        if (DictCodeConstants.SALES_PRICE == Integer.parseInt(String.valueOf(PartPriceAdjustDto.getBenchmark_type()))) {// 销售价
//            partInfoPO.setDouble("sales_price",
//                                 partInfoPO.getDouble("sales_price") * (1 + PartPriceAdjustDto.getMarkup_percentage()));
//        }
//        if (StringUtils.isNullOrEmpty(partInfoPO) || StringUtils.isNullOrEmpty(PartPriceAdjustDto)) {
//            throw new ServiceBizException("选项不能为空！");
//        }
    }
    
//    /**
//     * 校验记录是否存在
//     * @author xukl
//     * @date 2016年8月30日
//     * @param table
//     * @param whereSql
//     * @param condition
//     * @return
//     */
//     private boolean verifyExist(String whereSql,Object... condition){
//         List<Object> queryParam=new ArrayList<Object>();
//         for (int i = 0; i < condition.length; i++) {
//             queryParam.add(condition[i]);
//         }
//         List<Map> list = DAOUtil.findAll(whereSql, queryParam);
//         if(CommonUtils.isNullOrEmpty(list)){
//             return false;
//         }else{
//             return true;
//         }
//     }
     
     /**
      * 校验 配件号 或者 仓库代码是否存在
      * @author dingchaoyu
      * @date 
      * @param partNo
      * @param logo
      * @return
       */ 
      public boolean partNoOrStorageExist(String partNo,String logo) throws ServiceBizException{ 
          List<Object> param = new ArrayList<Object>();
          List<Object> params = new ArrayList<Object>();
          param.add(partNo);
          params.add(logo);
          String sql = "SELECT * from tm_part_stock  where PART_NO=? ";
          String sqls = "SELECT * from tm_storage  where STORAGE_CODE=? ";
          List<Map> findAll = DAOUtil.findAll(sql, param);
          List<Map> findAlls = DAOUtil.findAll(sqls, params);
          if (CommonUtils.isNullOrEmpty(findAll)&&CommonUtils.isNullOrEmpty(findAlls)) {
            return false;
        }else{
            return true;
        }
      }

    /**
     * 导入
    * @author dingchaoyu
    * @date 2017年4月14日
    * @param rowDto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartPriceAdjustService#imports(com.yonyou.dms.part.domains.DTO.basedata.PartPriceAdjustDTO)
     */
    @Override
    public void imports(PartPriceAdjustDTO rowDto) throws ServiceBizException {
        
        String part_NO = rowDto.getPART_NO();
        String storage_CODE = rowDto.getSTORAGE_CODE();
        boolean b = partNoOrStorageExist(part_NO,storage_CODE);
        if (b) {
            TmPartStockPO partStockPO = TmPartStockPO.findFirst("PART_NO=? and STORAGE_CODE=?", part_NO,storage_CODE);
            TmPartStockItemPO partStockItemPO = TmPartStockItemPO.findFirst("PART_NO=? and STORAGE_CODE=?", part_NO,storage_CODE);
            setData(partStockPO,partStockItemPO,rowDto);
        }else{
            throw new ServiceBizException("仓库或件信息不对，不能导入！");
        }
    }
    
    public void setData(TmPartStockPO partStockPO,TmPartStockItemPO partStockItemPO,PartPriceAdjustDTO rowDto){
        partStockPO.setString("SALES_PRICE", rowDto.getSALES_PRICE());   
        partStockItemPO.setString("SALES_PRICE", rowDto.getSALES_PRICE());
        partStockPO.saveIt();
        partStockItemPO.saveIt();
    }

}
