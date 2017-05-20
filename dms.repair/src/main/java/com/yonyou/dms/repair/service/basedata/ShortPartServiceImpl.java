package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.MaintainWorkTypePO;
import com.yonyou.dms.common.domains.PO.basedata.TtShortPartPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.TtShortPartDTO;

/**
 * 配件缺料
 * 
 * @author chenwei
 * @date 2017年4月18日
 */
@Service
public class ShortPartServiceImpl implements ShortPartService {

    @Override
    public MaintainWorkTypePO findByPrimaryKey(String labourPositionCode) throws ServiceBizException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PageInfoDto searchMaintainWorkType(Map<String, String> queryParam) throws ServiceBizException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String insertShortPartPo(TtShortPartDTO shortPartto) throws ServiceBizException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateShortPart(String shortId, TtShortPartDTO shortPartto) throws ServiceBizException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteShortPartById(Long id) throws ServiceBizException {
        // TODO Auto-generated method stub
        TtShortPartPO.delete(" SHORT_ID = ? ", id);
    }

    @Override
    public List<Map> queryShortPart(Map<String, Object> queryParams) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder("SELECT A.DEALER_CODE,A.STORAGE_CODE as storageCode,A.PART_NO as partNo,A.STORAGE_POSITION_CODE as storagePositionCode,A.SHEET_NO as sheetNo,A.D_KEY as dKey,A.IS_BO as isBo FROM TT_SHORT_PART A where 1=1 ");
        if(!StringUtils.isNullOrEmpty(queryParams.get("storageCode"))){
            sqlSb.append(" AND A.storage_code = ? ");
            params.add(queryParams.get("storageCode"));
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("partNo"))){
            sqlSb.append(" AND A.part_no = ? ");
            params.add(queryParams.get("partNo"));
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("storagePositionCode"))){
            sqlSb.append(" AND A.STORAGE_POSITION_CODE = ? ");
            params.add(queryParams.get("storagePositionCode"));
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("sheetNo"))){
            sqlSb.append(" AND A.sheet_no = ? ");
            params.add(queryParams.get("sheetNo"));
        }
        return DAOUtil.findAll(sqlSb.toString(), params);
    }

}
