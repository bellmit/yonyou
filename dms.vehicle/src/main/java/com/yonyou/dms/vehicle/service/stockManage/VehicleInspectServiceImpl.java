
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : VehicleInspectServiceImpl.java
*
* @Author : zhanshiwei
*
* @Date : 2016年12月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月6日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.vehicle.service.stockManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.stockmanage.VehicleInspectPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VinspectionMarPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DefinedRowProcessor;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.InspectPdiItemDTO;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.VehicleInspectDTO;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.VinspectionMarDTO;
import com.yonyou.dms.vehicle.domains.PO.stockManage.InspectPdiItemPO;
import com.yonyou.dms.vehicle.domains.PO.stockManage.InspectionPdiPO;
import com.yonyou.dms.vehicle.domains.PO.stockManage.StockInOutPO;

/**
 * 车辆验收
 * 
 * @author zhanshiwei
 * @date 2016年12月6日
 */
@Service
public class VehicleInspectServiceImpl implements VehicleInspectService {

    @Autowired
    FileStoreService        fileStoreService;
    @Autowired
    private CommonNoService commonNoService;

    /**
     * 车辆验收查询
     * 
     * @author zhanshiwei
     * @date 2016年12月6日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.VehicleInspectService#queryVehicleInspect(java.util.Map)
     */

    @Override
    public PageInfoDto queryVehicleInspect(Map<String, String> queryParam) throws ServiceBizException {

        List<Object> queryList = new ArrayList<Object>();
        String sql = this.getSQlVehicleInspect();
        StringBuffer sb = new StringBuffer(sql);
        this.setWhere(queryParam, queryList, sb);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

    /**
     * 设置查询条件
     * 
     * @author zhanshiwei
     * @date 2016年12月6日
     * @param queryParam
     * @param queryList
     */

    public void setWhere(Map<String, String> queryParam, List<Object> queryList, StringBuffer sb) {
        if (!StringUtils.isNullOrEmpty(queryParam.get("vbTyp"))) {
            sb.append(" and veinspect.VB_TYPE = ?");
            queryList.add(queryParam.get("vbTyp"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("bussinessNo"))) {
            sb.append(" and veinspect.BUSSINESS_NO like ?");
            queryList.add("%" + queryParam.get("bussinessNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("bussiness_stardate"))) {
            sb.append(" and veinspect.BUSSINESS_DATE >= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("bussiness_stardate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("bussiness_endate"))) {
            sb.append(" and veinspect.BUSSINESS_DATE < ?");
            queryList.add(DateUtil.addOneDay(queryParam.get("bussiness_endate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("inspectionStatus"))) {
            sb.append(" and veinspect.INSPECT_STATUS in (?)");
            queryList.add(queryParam.get("inspectionStatus"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("marType"))) {
            sb.append(" and veinspect.MAR_STATUS =?");
            queryList.add(queryParam.get("marType"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and veinspect.VIN like ?");
            queryList.add("%" + queryParam.get("vin") + "%");
        }
    }

    /**
     * 车辆验收SQl
     * 
     * @author zhanshiwei
     * @date 2016年12月6日
     * @param queryParam
     * @param queryList
     * @return
     */

    public String getSQlVehicleInspect() {
        StringBuffer sb = new StringBuffer("select veinspect.VEHICLE_INSPECT_ID,veinspect.DEALER_CODE,stock.VIN,\n");
        sb.append("veinspect.BUSSINESS_NO,veinspect.VB_TYPE,veinspect.BUSSINESS_DATE,veinspect.INSPECT_STATUS,veinspect.IS_IN_OUT_STOCK,\n");
        sb.append("veinspect.INSPECT_PERSON,veinspect.MAR_STATUS,stock.ENGINE_NO,stock.EXHAUST_QUANTITY,\n");
        sb.append("stock.PRODUCT_DATE,stock.FACTORY_DATE,stock.KEY_NO,stock.CERTIFICATE_NUMBER,stock.DISCHARGE_STANDARD,stock.STORAGE_POSITION_CODE,stock.STORAGE_CODE,\n");
        sb.append("vs_product.PRODUCT_CODE,vs_product.PRODUCT_NAME,em.EMPLOYEE_NAME\n");
        sb.append("from TM_VEHICLE_INSPECT veinspect\n");
        sb.append("left join TM_VS_STOCK stock  on  veinspect.VS_STOCK_ID=stock.VS_STOCK_ID and veinspect.DEALER_CODE=stock.DEALER_CODE\n");
        sb.append("left join TM_VS_PRODUCT vs_product  on  stock.PRODUCT_ID=vs_product.PRODUCT_ID\n");
        sb.append("left join TM_EMPLOYEE  em  on   veinspect.INSPECT_PERSON=em.EMPLOYEE_NO and veinspect.DEALER_CODE=em.DEALER_CODE\n");

        sb.append("where 1=1\n");
        return sb.toString();

    }

    /**
     * 根据ID查询车辆验收信息
     * 
     * @author zhanshiwei
     * @date 2016年12月6日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.VehicleInspectService#queryVehicleInspectById(java.lang.Long)
     */

    @Override
    public Map<String, Object> queryVehicleInspectById(Long id) throws ServiceBizException {
        List<Object> queryList = new ArrayList<Object>();
        String sql = getSQlVehicleInspect();
        StringBuffer sb = new StringBuffer(sql);
        sb.append(" and veinspect.VEHICLE_INSPECT_ID=?");
        queryList.add(id);
        return DAOUtil.findFirst(sb.toString(), queryList);
    }

    /**
     * 验收数据查询
     * 
     * @author zhanshiwei
     * @date 2016年12月6日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.VehicleInspectService#queryVinspectionMar(java.lang.Long)
     */

    @SuppressWarnings("rawtypes")
    @Override
    public List<Map> queryVinspectionMar(Long id) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("select VEHICLE_INSPECT_ID,MAR_POSITION,MAR_DEGREE,MAR_TYPE,MAR_REMARK from TT_VINSPECTION_MAR where 1=1 and VEHICLE_INSPECT_ID=?");
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        List<Map> map = Base.findAll(sb.toString(), params.toArray());
        ;
        return map;
    }

    /**
     * @author zhanshiwei
     * @date 2016年12月7日
     * @param vin
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.VehicleInspectService#QueryStockInVin(java.lang.String)
     */

    @SuppressWarnings("rawtypes")
    @Override
    public List<Map> queryPdiInspection(int inspectType, String bussinessNo) throws ServiceBizException {
        // 看看这个vin下有没有pdi检查过，检查过应该有数据
        StringBuilder sqlSb = new StringBuilder("select Ipc_t.INSPECT_PDI_ID,Ipc_t.DEALER_CODE,Ipc_m.INSPECT_CATEGORY,Ipc_m.INSPECT_ITEM,Ipc_m.IS_PROBLEM,Ipc_m.INSPECT_DESCRIBE,tc_1.TYPE_NAME,tc_1.CODE_CN_DESC from TT_INSPECT_PDI Ipc_t,TT_INSPECT_PDI_ITEM Ipc_m,tc_code tc_1 where  tc_1.TYPE=Ipc_m.INSPECT_CATEGORY and tc_1.CODE_ID=Ipc_m.INSPECT_ITEM and  Ipc_t.INSPECT_PDI_ID=Ipc_m.INSPECT_PDI_ID and INSPECT_TYPE=? and BUSSINESS_NO=?");
        List<Object> params = new ArrayList<Object>();
        params.add(inspectType);
        params.add(bussinessNo);
        List<Map> map = DAOUtil.findAll(sqlSb.toString(), params);
        if (!CommonUtils.isNullOrEmpty(map)) {
            return map;
        } else {
            StringBuilder sqlSb2 = new StringBuilder("SELECT CODE_ID as INSPECT_ITEM ,TYPE as INSPECT_CATEGORY ,TYPE_NAME,CODE_CN_DESC FROM tc_code where 1=1 and TYPE in (1409,1410,1411)");
            List<Object> params2 = new ArrayList<Object>();
            List<Map> map2 = Base.findAll(sqlSb2.toString(), params2.toArray());
            return map2;
        }
    }

    /**
     * 车辆验收更新
     * 
     * @author zhanshiwei
     * @date 2016年12月8日
     * @param id
     * @param veInspDto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.VehicleInspectService#modifyVehicleInspect(java.lang.Long,
     * com.yonyou.dms.vehicle.domains.DTO.stockManage.VehicleInspectDTO)
     */

    @Override
    public void modifyVehicleInspect(Long id, VehicleInspectDTO veInspDto) throws ServiceBizException {
        VehicleInspectPO veInspPo = VehicleInspectPO.findById(id);
        veInspPo.setInteger("MAR_STATUS", veInspDto.getMarStatus());
        veInspPo.setString("INSPECT_PERSON", veInspDto.getInspectPerson());
        veInspPo.setInteger("INSPECT_STATUS", veInspDto.getInspectStatus());
        veInspPo.saveIt();
        VinspectionMarPO.delete("VEHICLE_INSPECT_ID=?", id);

        if (veInspDto.getInspectMarList() != null && veInspDto.getInspectMarList().size() > 0) {
            for (VinspectionMarDTO inspectionMarDto : veInspDto.getInspectMarList()) {
                veInspPo.add(getVinspectionMarPO(inspectionMarDto));
            }
        }
        modifyInsppectPdiInfo(14121008, veInspDto);
        if (!StringUtils.isNullOrEmpty(veInspDto.getInspectStatus())
            && veInspDto.getInspectStatus() == DictCodeConstants.INSPECTION_RESULT_SUCCESS) {
            insertStockInOutInfo(veInspPo.getLong("VS_STOCK_ID"), veInspDto);
        }
        // 更新附件的信息
        fileStoreService.updateFileUploadInfo(veInspDto.getInspectDmsFileIds(), id.toString(),
                                              DictCodeConstants.FILE_TYPE_CAR_INFO_INSPECTION);

    }

    @SuppressWarnings("rawtypes")
    public void modifyInsppectPdiInfo(int inspectType, VehicleInspectDTO veInspDto) {
        StringBuilder sqlSb = new StringBuilder("select INSPECT_PDI_ID,DEALER_CODE from TT_INSPECT_PDI where INSPECT_TYPE=? and BUSSINESS_NO=?");
        List<Object> params = new ArrayList<Object>();
        params.add(inspectType);
        params.add(veInspDto.getBussinessNo());
        List<Map> result = DAOUtil.findAll(sqlSb.toString(), params);
        if (result != null && result.size() > 0) {
            InspectionPdiPO inspectPdiPo = InspectionPdiPO.findById(result.get(0).get("INSPECT_PDI_ID"));
            modifyInsppectPdietmInfo(inspectPdiPo, veInspDto.getInspenctItem());
        } else {
            InspectionPdiPO inspectPdiPo = new InspectionPdiPO();
            inspectPdiPo.setString("BUSSINESS_NO", veInspDto.getBussinessNo());
            inspectPdiPo.setString("INSPECT_PERSON", veInspDto.getInspectPerson());
            inspectPdiPo.setInteger("INSPECT_TYPE", "14121008");
            inspectPdiPo.saveIt();
            modifyInsppectPdietmInfo(inspectPdiPo, veInspDto.getInspenctItem());
        }
    }

    /**
     * PDI检查明细修改
     * 
     * @author zhanshiwei
     * @date 2016年12月8日
     * @param inspenctItem
     */

    public void modifyInsppectPdietmInfo(InspectionPdiPO inspectPdiPo, List<InspectPdiItemDTO> inspenctItem) {
        InspectPdiItemPO.delete("inspect_pdi_id=?", inspectPdiPo.getId());
        if (inspenctItem.size() > 0 && inspenctItem != null) {
            for (InspectPdiItemDTO inspectItemDto : inspenctItem) {
                inspectPdiPo.add(getInspenctItemPo(inspectItemDto));
            }
        }
    }

    /**
     * PDI检查明细PO
     * 
     * @author zhanshiwei
     * @date 2016年12月8日
     * @param inspectItemDto
     * @return
     */

    public InspectPdiItemPO getInspenctItemPo(InspectPdiItemDTO inspectItemDto) {
        InspectPdiItemPO inspectIntemPo = new InspectPdiItemPO();
        inspectIntemPo.setInteger("INSPECT_CATEGORY", inspectItemDto.getInspectCategory());
        inspectIntemPo.setInteger("INSPECT_ITEM", inspectItemDto.getInspectItem());
        inspectIntemPo.setInteger("IS_PROBLEM", inspectItemDto.getIsProblem());
        inspectIntemPo.setString("INSPECT_DESCRIBE", inspectItemDto.getInspectDescribe());
        inspectIntemPo.setInteger("IS_PROBLEM", inspectItemDto.getIsProblem());
        inspectIntemPo.setString("INSPECT_DESCRIBE", inspectItemDto.getInspectDescribe());
        return inspectIntemPo;
    }

    /**
     * 车辆验收质损明细
     * 
     * @author zhanshiwei
     * @date 2016年12月8日
     * @param inspectionMarDto
     * @return
     */

    public VinspectionMarPO getVinspectionMarPO(VinspectionMarDTO inspectionMarDto) {
        VinspectionMarPO inspectionMarPo = new VinspectionMarPO();
        inspectionMarPo.setInteger("MAR_POSITION", inspectionMarDto.getMarPosition());
        inspectionMarPo.setInteger("MAR_DEGREE", inspectionMarDto.getMarDegree());
        inspectionMarPo.setInteger("MAR_TYPE", inspectionMarDto.getMarType());
        inspectionMarPo.setString("MAR_REMARK", inspectionMarDto.getMarRemark());
        return inspectionMarPo;
    }

    /**
     * 生成出入库单
     * 
     * @author zhanshiwei
     * @date 2016年12月8日
     * @param veInspDto
     */

    public void insertStockInOutInfo(Long vsid, VehicleInspectDTO veInspDto) {
        StockInOutPO stockPo = new StockInOutPO();
        stockPo.setInteger("IS_IN_OUT_STOCK", DictCodeConstants.STATUS_IS_NOT);
        stockPo.setInteger("IN_DELIVERY_TYPE", veInspDto.getVbType());
       
        stockPo.setString("IN_OUT_STOCK_NO", commonNoService.getSystemOrderNo(CommonConstants.SD_NO));  
        stockPo.setString("BUSSINESS_NO", veInspDto.getBussinessNo());
        stockPo.setTimestamp("BUSSINESS_DATE", veInspDto.getBussinessDate());
        stockPo.setLong("VS_STOCK_ID", vsid);
        stockPo.setString("STORAGE_POSITION_CODE", veInspDto.getStoragePositionCode());
        stockPo.setString("STORAGE_CODE", veInspDto.getStorageCode());
        stockPo.saveIt();
    }

    /**
     * 批量验收
     * 
     * @author zhanshiwei
     * @date 2016年12月8日
     * @param veInspDto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.VehicleInspectService#modifyVehicleInspectFinishSel(com.yonyou.dms.vehicle.domains.DTO.stockManage.VehicleInspectDTO)
     */

    @Override
    public void modifyVehicleInspectFinishSel(VehicleInspectDTO veInspDto) throws ServiceBizException {
        String[] ids = veInspDto.getVeInspectIds().split(",");
        for (int i = 0; i < ids.length; i++) {
            long id = Long.parseLong(ids[i]);
            VehicleInspectPO veInspPo = VehicleInspectPO.findById(id);
            veInspPo.setString("INSPECT_PERSON", veInspDto.getInspectPerson());
            veInspPo.setInteger("INSPECT_STATUS", veInspDto.getInspectStatus());
            veInspPo.saveIt();
            if (!StringUtils.isNullOrEmpty(veInspDto.getInspectStatus())
                && veInspDto.getInspectStatus() == DictCodeConstants.INSPECTION_RESULT_SUCCESS) {
                queryVehicleInspectDtoById(id, veInspDto);
                insertStockInOutInfo(veInspPo.getLong("VS_STOCK_ID"), veInspDto);
            }
        }
    }

    /**
     * 查询车辆验收复合DTO
     * 
     * @author zhanshiwei
     * @date 2016年12月8日
     * @param id
     * @param veInspDto
     * @return
     */

    public VehicleInspectDTO queryVehicleInspectDtoById(Long id, final VehicleInspectDTO veInspDto) {
        List<Object> queryList = new ArrayList<Object>();
        String sql = getSQlVehicleInspect();
        StringBuffer sb = new StringBuffer(sql);
        sb.append(" and veinspect.VEHICLE_INSPECT_ID=?");
        queryList.add(id);
        DAOUtil.findAll(sb.toString(), queryList, new DefinedRowProcessor() {

            @Override
            protected void process(Map<String, Object> row) {
                veInspDto.setBussinessDate(row.get("BUSSINESS_DATE") != null ? DateUtil.parseDefaultDateTime(row.get("BUSSINESS_DATE").toString()) : null);
                veInspDto.setBussinessNo(row.get("BUSSINESS_NO") != null ? row.get("BUSSINESS_NO").toString() : "");
                veInspDto.setStorageCode(row.get("STORAGE_CODE") != null ? row.get("STORAGE_CODE").toString() : "");
                veInspDto.setStoragePositionCode(row.get("STORAGE_POSITION_CODE") != null ? row.get("STORAGE_POSITION_CODE").toString() : "");
                veInspDto.setVbType(row.get("VB_TYPE") != null ? Integer.parseInt(row.get("VB_TYPE").toString()) : null);
            }
        });
        return veInspDto;

    }

    
    /**
     * 导出
    * @author zhanshiwei
    * @date 2016年12月8日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.vehicle.service.stockManage.VehicleInspectService#exportVehicleInspectInfo(java.util.Map)
    */
    	
    @Override
    public List<Map> exportVehicleInspectInfo(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> queryList = new ArrayList<Object>();
        String sql = this.getSQlVehicleInspect();
        StringBuffer sb = new StringBuffer(sql);
        this.setWhere(queryParam, queryList, sb);
        List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
        return resultList;
    }
}
