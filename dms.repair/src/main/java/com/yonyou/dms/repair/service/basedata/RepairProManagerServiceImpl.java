
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : RepairProManagerServiceImpl.java
*
* @Author : rongzoujie
*
* @Date : 2016年8月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月11日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.RepairPartsDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.RepairProManagerDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.SingleCopyDTO;
import com.yonyou.dms.repair.domains.PO.basedata.RepairPartPO;
import com.yonyou.dms.repair.domains.PO.basedata.RepairProManagerPO;



/**
* TODO description
* @author rongzoujie
* @date 2016年8月11日
*/
@Service
public class RepairProManagerServiceImpl implements RepairProManagerService {

    /**维修页面查询
    * @author rongzoujie
    * @date 2016年8月11日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.RepairProManagerService#queryRepairPros(java.util.Map)
    */
    @Override
    public PageInfoDto queryRepairPros(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = getQuerySql(queryParam,params);
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql,params);
        return pageInfoDto;
    }

    /**
     * 设置页面查询维修项目 
    * @author rongzoujie
    * @date 2016年9月22日
    * @param queryParam
    * @param params
    * @return
     */
    private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
        StringBuilder sql = new StringBuilder("SELECT mc.MAIN_GROUP_NAME,sc.SUB_GROUP_NAME,trt.REPAIR_TYPE_CODE,twt.WORKER_TYPE_CODE,tml.LABOUR_ID,tml.DEALER_CODE,tml.MODEL_LABOUR_CODE,tml.LABOUR_CODE,tml.LABOUR_NAME,");
        sql.append("trt.REPAIR_TYPE_NAME,tml.SPELL_CODE,tml.STD_LABOUR_HOUR,tml.ASSIGN_LABOUR_HOUR,tml.CLAIM_LABOUR,tml.OPERATION_CODE,");
        sql.append("tml.MAIN_GROUP_CODE,tml.SUB_GROUP_CODE,tml.OEM_TAG,twt.WORKER_TYPE_NAME,tml.LOCAL_LABOUR_CODE,tml.LOCAL_LABOUR_NAME ");
        sql.append("FROM tm_labour tml LEFT JOIN tm_repair_type trt on trt.REPAIR_TYPE_CODE = tml.REPAIR_TYPE_CODE and tml.DEALER_CODE = trt.DEALER_CODE");
        sql.append(" LEFT JOIN tm_worker_type twt on twt.WORKER_TYPE_CODE = tml.WORKER_TYPE_CODE and tml.DEALER_CODE = twt.DEALER_CODE");
        sql.append(" LEFT JOIN tm_labour_group mc ON tml.MAIN_GROUP_CODE = mc.MAIN_GROUP_CODE and tml.DEALER_CODE = mc.DEALER_CODE");
        sql.append(" LEFT JOIN tm_labour_subgroup sc on tml.SUB_GROUP_CODE = sc.SUB_GROUP_CODE and tml.DEALER_CODE = sc.DEALER_CODE");
        if(!StringUtils.isNullOrEmpty(queryParam.get("modeLabourCode"))){
            String modeLabourCodeStr = queryParam.get("modeLabourCode").toString();
            String[] modeLabourCodeAttr = modeLabourCodeStr.split(",");
            sql.append(" and tml.MODEL_LABOUR_CODE in (");
            for(int i=0;i<modeLabourCodeAttr.length;i++){
                sql.append("?");
                if((i+1) < modeLabourCodeAttr.length){
                    sql.append(",");
                }
                params.add(modeLabourCodeAttr[i]);
            }
            sql.append(")");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("primaryGroupName"))){
            sql.append(" and tml.MAIN_GROUP_CODE = ?");
            params.add(queryParam.get("primaryGroupName"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("subGroupName"))){
            sql.append(" and tml.SUB_GROUP_CODE = ?");
            params.add(queryParam.get("subGroupName"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("repairCode"))){
            sql.append(" and tml.LABOUR_CODE like ?");
            params.add("%"+queryParam.get("repairCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("repairName"))){
            sql.append(" and tml.LABOUR_NAME like ?");
            params.add("%"+queryParam.get("repairName")+"%");
        }
        
        if(!StringUtils.isNullOrEmpty(queryParam.get("repairSpell"))){
            sql.append(" and tml.SPELL_CODE like ?");
            params.add("%"+queryParam.get("repairSpell")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("isOem"))){
            int isOem = Integer.parseInt((String)queryParam.get("isOem").toString());
            sql.append(" and tml.OEM_TAG = ?");
            params.add(isOem);
        }
        
        if(!StringUtils.isNullOrEmpty(queryParam.get("localCode"))){
            sql.append(" and tml.LOCAL_LABOUR_CODE like ?");
            params.add("%"+queryParam.get("localCode")+"%");
        }
        
        if(!StringUtils.isNullOrEmpty(queryParam.get("localName"))){
            sql.append(" and tml.LOCAL_LABOUR_NAME like ?");
            params.add("%"+queryParam.get("localName")+"%");
        }
        sql.append(" ORDER BY tml.LABOUR_CODE");
        return sql.toString();
    }

    /**
     * 获取车型code
    * @author rongzoujie
    * @date 2016年8月15日
    * @param modeLabourCode
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.RepairProManagerService#getModelCode(java.lang.String)
     */
    @Override
    public List<Map> getModelCode(String modeLabourCode) throws ServiceBizException{
//        StringBuilder sql = new StringBuilder("select MODEL_CODE,DEALER_CODE from tm_model where 1=1 ");
        StringBuilder sql = new StringBuilder("SELECT t1.DEALER_CODE,t1.MODEL_CODE FROM tm_model_group_item t1 ");
        sql.append("LEFT JOIN tm_model_group t2 ON t1.DEALER_CODE = t2.DEALER_CODE ");
        sql.append("AND t1.MODEL_GROUP_ID = t2.MODEL_GROUP_ID where 1=1");
        List<Object> queryParam = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(modeLabourCode)){
            sql.append(" and t2.MODEL_LABOUR_CODE = ?");
            queryParam.add(modeLabourCode);
        }
        
        List<Map> modeCode = DAOUtil.findAll(sql.toString(), queryParam);
        return modeCode;
    }

    
    /**
     * 添加维修项目
    * @author rongzoujie
    * @date 2016年8月15日
    * @param repairProManagerDTO
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.RepairProManagerService#addRepairPro(com.yonyou.dms.repair.domains.DTO.basedata.RepairProManagerDTO)
     */
    @Override
    public Long addRepairPro(RepairProManagerDTO repairProManagerDTO) throws ServiceBizException {
//        if(StringUtils.isNullOrEmpty(repairProManagerDTO.getRepairTypeCode())){
//            throw new ServiceBizException("维修类型必须选择");
//        }
//        if(StringUtils.isNullOrEmpty(repairProManagerDTO.getWorkTypeCode())){
//            throw new ServiceBizException("工种必须选择");
//        }
        
        RepairProManagerPO repairProManagerPO = new RepairProManagerPO();
        repairProManagerDTO.setOmeTag(10041002);
        setRepairPro(repairProManagerPO, repairProManagerDTO,false);
        repairProManagerPO.saveIt();
        return repairProManagerPO.getLongId();
    }
    
    /**
     * 设置添加维修项目代码 
    * @author rongzoujie
    * @date 2016年9月22日
    * @param repairProManagerPO
    * @param repairProManagerDTO
     */
    public void setRepairPro(RepairProManagerPO repairProManagerPO,RepairProManagerDTO repairProManagerDTO,boolean copyFlag){
    	if(!copyFlag){
    		repairProManagerPO.setString("MODEL_LABOUR_CODE", repairProManagerDTO.getModelLabourCode());
            repairProManagerPO.setString("LABOUR_CODE",repairProManagerDTO.getLabourCode());
    	}
        repairProManagerPO.setString("LABOUR_NAME",repairProManagerDTO.getLabourName());
        repairProManagerPO.setString("SPELL_CODE",repairProManagerDTO.getSpellCode());
        repairProManagerPO.setString("REPAIR_GROUP_CODE", repairProManagerDTO.getRepairGroupCode());
        repairProManagerPO.setString("LOCAL_LABOUR_CODE", repairProManagerDTO.getLocalLabourCode());
        repairProManagerPO.setString("LOCAL_LABOUR_NAME", repairProManagerDTO.getLocalLabourName());
        repairProManagerPO.setInteger("REPAIR_TYPE_CODE", repairProManagerDTO.getRepairTypeCode());
        repairProManagerPO.setString("WORKER_TYPE_CODE", repairProManagerDTO.getWorkTypeCode());
        repairProManagerPO.setDouble("STD_LABOUR_HOUR", repairProManagerDTO.getStdLabourHour());
        repairProManagerPO.setDouble("ASSIGN_LABOUR_HOUR", repairProManagerDTO.getAssignLabourHour());
        repairProManagerPO.setDouble("CLAIM_LABOUR", repairProManagerDTO.getClaimLabHour());
        repairProManagerPO.setString("OPERATION_CODE", repairProManagerDTO.getOperationCode());
        repairProManagerPO.setString("MAIN_GROUP_CODE", repairProManagerDTO.getMainGroupCode());
        if(!StringUtils.isNullOrEmpty(repairProManagerDTO.getSubGroupCode())){
            repairProManagerPO.setString("SUB_GROUP_CODE", repairProManagerDTO.getSubGroupCode());
        }else{
            repairProManagerPO.setString("SUB_GROUP_CODE", "");
        }
        repairProManagerPO.setInteger("OEM_TAG", repairProManagerDTO.getOmeTag());
        
    }
    
    /**
     * 修改维修项目管理
    * @author rongzoujie
    * @date 2016年8月16日
    * @param id
    * @param repairProManagerDTO
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.RepairProManagerService#modifyRepairProMng(java.lang.Long, com.yonyou.dms.repair.domains.DTO.basedata.RepairProManagerDTO)
     */
    @Override
    public void modifyRepairProMng(Long id, RepairProManagerDTO repairProManagerDTO) {
        RepairProManagerPO repairProManagerPO = RepairProManagerPO.findById(id);
        setRepairPro(repairProManagerPO,repairProManagerDTO,false);
        repairProManagerPO.saveIt();
    }

    /**
     * 更具id查找维修项目
    * @author rongzoujie
    * @date 2016年8月16日
    * @param id
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.RepairProManagerService#getRepairProMngById(java.lang.Long)
     */
    @Override
    public RepairProManagerPO getRepairProMngById(Long id) {
        RepairProManagerPO repairProManagerPO = RepairProManagerPO.findById(id);
        return repairProManagerPO;
    }
    
    private void checkRepairProOem(Long id){
        StringBuilder sql = new StringBuilder("select OEM_TAG,DEALER_CODE from tm_labour where 1=1 and LABOUR_ID = ?");
        List<Object> param = new ArrayList<Object>();
        param.add(id);
        Map result = DAOUtil.findFirst(sql.toString(), param);
        Integer oemTag = Integer.parseInt(result.get("OEM_TAG").toString());
        if(oemTag == DictCodeConstants.STATUS_IS_YES){
            throw new ServiceBizException("OEM为：是，不能删除");
        }
    }

    /**
     * 删除维修项目
    * @author rongzoujie
    * @date 2016年9月2日
    * @param id
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.RepairProManagerService#deleteRepairMng(java.lang.Long)
     */
    @Override
    public void deleteRepairMng(Long id) {
        checkRepairProOem(id);
        List<Object> param = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("select PART_NO,DEALER_CODE,LABOUR_PART_ID from tm_labour_part where LABOUR_ID = ?");
        param.add(id);
        List<Map> partResult = DAOUtil.findAll(sql.toString(), param);
        
        boolean deleteFlag = true;
        if(partResult.size()>0){
            for(int i=0;i<partResult.size();i++){
                String partNO = partResult.get(i).get("PART_NO").toString();
                if(checkPartOutStorage(partNO,id)){
                    deleteFlag = true;
                }else{
                    deleteFlag = false;
                    break;
                }
            }
        }
        
        if(deleteFlag){
            for(int i=0;i<partResult.size();i++){
                Long labourPartId = Long.parseLong(partResult.get(i).get("LABOUR_PART_ID").toString());
                RepairPartPO repairPartPO = RepairPartPO.findById(labourPartId);
                repairPartPO.deleteCascadeShallow();
            }
        }else{
            throw new ServiceBizException("当前维修项目下有未出库的配件无法删除");
        }
        
        RepairProManagerPO repairProManagerPO = RepairProManagerPO.findById(id);
        repairProManagerPO.deleteCascadeShallow();
     
        
    }

    /**
    * 更具车型组查询维修项目
    * @author rongzoujie
    * @date 2016年9月2日
    * @param repairCode
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.RepairProManagerService#queryModeGroupByRepairProMngCode(java.lang.String)
     */
    @Override
    public PageInfoDto queryModeGroupByRepairProMngCode(String repairCode) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("SELECT DISTINCT t1.DEALER_CODE,t1.MODEL_GROUP_ID,t1.MODEL_LABOUR_CODE,t1.MODEL_LABOUR_NAME,(CASE WHEN t2.LABOUR_ID IS NOT NULL THEN 10041001 ELSE 10041002 END) as isRe FROM TM_MODEL_GROUP t1");
        sql.append(" LEFT JOIN TM_LABOUR t2 ON t1.DEALER_CODE = t2.DEALER_CODE");
        sql.append(" AND t1.MODEL_LABOUR_CODE = t2.MODEL_LABOUR_CODE where 1=1");
        if(!StringUtils.isNullOrEmpty(repairCode)){
            sql.append(" AND t1.MODEL_LABOUR_CODE !=?");
            params.add(repairCode);
        }
        sql.append(" AND t1.IS_VALID = ?");
        params.add(DictCodeConstants.STATUS_IS_YES);
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(),params);
        return pageInfoDto;
    }

    /**
    * 维修项目单项复制
    * @author rongzoujie
    * @date 2016年9月2日
    * @param singleCopyDTO
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.RepairProManagerService#singleCopy(com.yonyou.dms.repair.domains.DTO.basedata.SingleCopyDTO)
     */
    @Override
    public void singleCopy(SingleCopyDTO singleCopyDTO) {
        String[] ids = singleCopyDTO.getModelGroupIds().split(",");
        List<Object> params = new ArrayList<Object>();
        for(int i=0;i<ids.length;i++){
            Long modelGroupid = Long.parseLong(ids[i]);
            StringBuilder sqlsb = new StringBuilder("SELECT tl.LABOUR_NAME,tl.LABOUR_CODE,tl.DEALER_CODE,tl.LABOUR_ID FROM tm_labour tl WHERE tl.MODEL_LABOUR_CODE = (SELECT tm.MODEL_LABOUR_CODE FROM tm_model_group tm WHERE tm.MODEL_GROUP_id = ? )");
            params = new ArrayList<Object>();
            params.add(modelGroupid);
            List<Map> repairProMng = DAOUtil.findAll(sqlsb.toString(), params);
//            RepairProManagerPO repairProManagerPO = null;
            
            sqlsb = new StringBuilder("select t1.dealer_code,t1.model_labour_code from tm_model_group t1 where t1.MODEL_GROUP_id = ? ");
            params = new ArrayList<Object>();
            params.add(ids[i]);
            Map modelLabourCodeMap = DAOUtil.findFirst(sqlsb.toString(), params);
            String modelLabourCode = (String)modelLabourCodeMap.get("MODEL_LABOUR_CODE").toString();
            singleCopyDTO.setModeLabourCode(modelLabourCode);
            boolean createNew = false;
            //判断是否存在
            if (repairProMng.size() > 0) {
                for (int j = 0; j < repairProMng.size(); j++) {
                    if ((repairProMng.get(j).get("LABOUR_CODE").toString()).equals(singleCopyDTO.getLabourCode())) {
                    	Long labourId = Long.parseLong(repairProMng.get(j).get("LABOUR_ID").toString());
                        RepairProManagerPO repairProManagerPO = RepairProManagerPO.findById(labourId);
                        setSingleCopy(repairProManagerPO, singleCopyDTO,false);
                        repairProManagerPO.saveIt();
                        createNew = false;
                        break;
                    } else {
                    	createNew = true;
                    }
                }
                if(createNew){
                	RepairProManagerPO repairProManagerPO = new RepairProManagerPO();
                    setSingleCopy(repairProManagerPO, singleCopyDTO,true);
                    repairProManagerPO.saveIt();
                }
            }else{
                RepairProManagerPO repairProManagerPO = new RepairProManagerPO();
                setSingleCopy(repairProManagerPO, singleCopyDTO,true);
                repairProManagerPO.saveIt();
            }
        }
    }

    /**
     * 维修项目单项复制
    * TODO description
    * @author rongzoujie
    * @date 2016年8月22日
    * @param repairProManagerPO
    * @param singleCopyDTO
     */
    private void setSingleCopy(RepairProManagerPO repairProManagerPO, SingleCopyDTO singleCopyDTO,boolean createNew) {
        repairProManagerPO.setInteger("OEM_TAG",singleCopyDTO.getOmgTag());
        if(createNew){
            repairProManagerPO.setString("LABOUR_CODE",singleCopyDTO.getLabourCode());
            repairProManagerPO.setString("MODEL_LABOUR_CODE", singleCopyDTO.getModeLabourCode());
        }
        repairProManagerPO.setString("LABOUR_NAME",singleCopyDTO.getLabourName());
        repairProManagerPO.setString("SPELL_CODE",singleCopyDTO.getSpellCode());
        repairProManagerPO.setString("LOCAL_LABOUR_CODE", singleCopyDTO.getLocalLabourCode());
        repairProManagerPO.setString("LOCAL_LABOUR_NAME", singleCopyDTO.getLocalLabourName());
        repairProManagerPO.setDouble("STD_LABOUR_HOUR", singleCopyDTO.getStdLabourHour());
        repairProManagerPO.setDouble("ASSIGN_LABOUR_HOUR", singleCopyDTO.getAssignLabourHour());
        repairProManagerPO.setDouble("CLAIM_LABOUR", singleCopyDTO.getClaimLabHour());
        repairProManagerPO.setString("OPERATION_CODE", singleCopyDTO.getOperationCode());
        repairProManagerPO.setString("MAIN_GROUP_CODE", singleCopyDTO.getMainGroupCode());
        repairProManagerPO.setString("WORKER_TYPE_CODE",singleCopyDTO.getWorkTypeCode());
        
        if(!StringUtils.isNullOrEmpty(singleCopyDTO.getSubGroupCode())){
            repairProManagerPO.setString("SUB_GROUP_CODE", singleCopyDTO.getSubGroupCode());
        }else{
            repairProManagerPO.setString("SUB_GROUP_CODE", "");
        }
        
        //维修类型
        if(!StringUtils.isNullOrEmpty(singleCopyDTO.getRepairTypeName())){
            List<Object> repairTypeParams = new ArrayList<Object>();
            StringBuilder repairTypesql = new StringBuilder("select DEALER_CODE,REPAIR_TYPE_CODE from tm_repair_type where 1=1");
            repairTypesql.append(" and REPAIR_TYPE_NAME = ?");
            repairTypeParams.add(singleCopyDTO.getRepairTypeName());
            Map repairType = DAOUtil.findFirst(repairTypesql.toString(), repairTypeParams);
            repairProManagerPO.setInteger("REPAIR_TYPE_CODE", Integer.parseInt(repairType.get("REPAIR_TYPE_CODE").toString()));
        }
        //工种判断
//        if(!StringUtils.isNullOrEmpty(singleCopyDTO.getRepairTypeName())){
//            List<Object> workTypeParams = new ArrayList<Object>();
//            StringBuilder workTypesql = new StringBuilder("select DEALER_CODE,WORKER_TYPE_CODE from tm_worker_type where 1=1");
//            workTypesql.append(" and WORKER_TYPE_NAME = ?");
//            workTypeParams.add(singleCopyDTO.getWorkTypeName());
//            Map workType = DAOUtil.findFirst(workTypesql.toString(), workTypeParams);
//            repairProManagerPO.setString("WORKER_TYPE_CODE", singleCopyDTO.getWorkTypeCode());
//        }
        
//        if(repairType.size()>0 && workType.size()>0){
//            singleCopyDTO.setRepairTypeCode(Integer.parseInt((String)repairType.get("REPAIR_TYPE_CODE").toString()));
//            singleCopyDTO.setWorkTypeCode((String)workType.get("WORKER_TYPE_CODE").toString());
//            repairProManagerPO.setInteger("REPAIR_TYPE_CODE", singleCopyDTO.getRepairTypeCode());
//            repairProManagerPO.setString("WORKER_TYPE_CODE", singleCopyDTO.getWorkTypeCode());
//        }else{
//            throw new ServiceBizException("维修类型和工种不能为空");
//        }
    }

    /**
    * 全部拷贝
    * @author rongzoujie
    * @date 2016年8月23日
    * @param srcLabourGroupCode
    * @param desLabourGroupCode
     */
    @Override
    public void totalCopy(String srcLabourGroupCode, String desLabourGroupCode) {
        List<Object> srcParams = new ArrayList<Object>();
        StringBuilder srcSql = new StringBuilder("SELECT LABOUR_ID,DEALER_CODE,MODEL_LABOUR_CODE,LABOUR_CODE,LABOUR_NAME,SPELL_CODE,REPAIR_GROUP_CODE,LOCAL_LABOUR_CODE,LOCAL_LABOUR_NAME,REPAIR_TYPE_CODE,WORKER_TYPE_CODE,STD_LABOUR_HOUR,ASSIGN_LABOUR_HOUR,CLAIM_LABOUR,OPERATION_CODE,MAIN_GROUP_CODE,SUB_GROUP_CODE,OEM_TAG FROM tm_labour WHERE 1=1"); 
        srcSql.append(" AND MODEL_LABOUR_CODE = ?");    
        srcParams.add(srcLabourGroupCode);
        List<Map> srcRepairPro = DAOUtil.findAll(srcSql.toString(), srcParams);
        
        List<Object> desParams = new ArrayList<Object>();
        StringBuilder desSql = new StringBuilder("SELECT LABOUR_ID,DEALER_CODE,MODEL_LABOUR_CODE,LABOUR_CODE,LABOUR_NAME,SPELL_CODE,REPAIR_GROUP_CODE,LOCAL_LABOUR_CODE,LOCAL_LABOUR_NAME,REPAIR_TYPE_CODE,WORKER_TYPE_CODE,STD_LABOUR_HOUR,ASSIGN_LABOUR_HOUR,CLAIM_LABOUR,OPERATION_CODE,MAIN_GROUP_CODE,SUB_GROUP_CODE,OEM_TAG FROM tm_labour WHERE 1=1"); 
        desSql.append(" AND MODEL_LABOUR_CODE = ?");    
        desParams.add(desLabourGroupCode);
        List<Map> desRepairPro = DAOUtil.findAll(desSql.toString(), desParams);
        
        RepairProManagerPO repairProManagerPO = null;
        boolean createNew = false;
        for (int i = 0; i < srcRepairPro.size(); i++) {
            RepairProManagerDTO repairProManagerDTO = setDtoForAllCopy(srcRepairPro, desLabourGroupCode, i);
            if(desRepairPro.size() > 0){
                for (int j = 0; j < desRepairPro.size(); j++) {
                    if ((desRepairPro.get(j).get("LABOUR_CODE").toString()).equals(repairProManagerDTO.getLabourCode())) {
                    	Long labourId = Long.parseLong(desRepairPro.get(j).get("LABOUR_ID").toString());
                    	repairProManagerPO = RepairProManagerPO.findById(labourId);
                    	setRepairPro(repairProManagerPO, repairProManagerDTO,true);
                    	repairProManagerPO.saveIt();
                    	createNew = false;
                        break;
                    }else{
                    	createNew = true;
                    }
                }
                if(createNew){
                	 repairProManagerPO = new RepairProManagerPO();
                     setRepairPro(repairProManagerPO, repairProManagerDTO,false);
                     repairProManagerPO.saveIt();
                }
            }else{
            	 repairProManagerPO = new RepairProManagerPO();
                 setRepairPro(repairProManagerPO, repairProManagerDTO,false);
                 repairProManagerPO.saveIt();
            }
            
        }
        
    }
    
    /**
    * 删除已经存在的特定车型组的维修项目
    * @author rongzoujie
    * @date 2016年8月23日
    * @param sqlsb
    * @param params
    * @param desLabourGroupCode
     */
    private void delExistRepirPro(String sqlsb,List<Object> params,String desLabourGroupCode){
        params = new ArrayList<Object>();
        params.add(desLabourGroupCode);
        List<Map> desRepairPro = DAOUtil.findAll(sqlsb.toString(), params);
        if(desRepairPro.size() > 0){
            for(int i=0;i<desRepairPro.size();i++){
                RepairProManagerPO repairProManagerPO = RepairProManagerPO.findById(desRepairPro.get(i).get("LABOUR_ID"));
                repairProManagerPO.deleteCascadeShallow();
            }
        }
    }
    
    /**
     * 设置全部拷贝维修项目DTO
    * TODO description
    * @author rongzoujie
    * @date 2016年8月23日
    * @param srcRepairPro
    * @param desLabourGroupCode
    * @param index
    * @return
     */
    private RepairProManagerDTO setDtoForAllCopy(List<Map> srcRepairPro,String desLabourGroupCode,int index) {
        RepairProManagerDTO repairProManagerDTO = new RepairProManagerDTO();
        repairProManagerDTO.setDealerCode((String) srcRepairPro.get(index).get("DEALER_CODE").toString());
        repairProManagerDTO.setModelLabourCode(desLabourGroupCode);
        repairProManagerDTO.setLabourCode((String) srcRepairPro.get(index).get("LABOUR_CODE").toString());
        repairProManagerDTO.setLabourName((String) srcRepairPro.get(index).get("LABOUR_NAME").toString());
        if(!StringUtils.isNullOrEmpty(srcRepairPro.get(index).get("SPELL_CODE"))){
            repairProManagerDTO.setLocalLabourCode((String) srcRepairPro.get(index).get("SPELL_CODE").toString());
        }
//        repairProManagerDTO.setSpellCode((String) srcRepairPro.get(index).get("SPELL_CODE").toString());
        String repairGroupCode = "";
        if (!StringUtils.isNullOrEmpty(srcRepairPro.get(index).get("REPAIR_GROUP_CODE"))) {
            repairGroupCode = (String) srcRepairPro.get(index).get("REPAIR_GROUP_CODE").toString();
        }
        repairProManagerDTO.setRepairGroupCode(repairGroupCode);
        if(!StringUtils.isNullOrEmpty(srcRepairPro.get(index).get("LOCAL_LABOUR_CODE"))){
            repairProManagerDTO.setLocalLabourCode((String) srcRepairPro.get(index).get("LOCAL_LABOUR_CODE").toString());
        }
        if(!StringUtils.isNullOrEmpty(srcRepairPro.get(index).get("LOCAL_LABOUR_NAME"))){
            repairProManagerDTO.setLocalLabourName((String) srcRepairPro.get(index).get("LOCAL_LABOUR_NAME").toString());
        }
        if(!StringUtils.isNullOrEmpty(srcRepairPro.get(index).get("REPAIR_TYPE_CODE"))){
            repairProManagerDTO.setRepairTypeCode(Integer.parseInt(srcRepairPro.get(index).get("REPAIR_TYPE_CODE").toString()));
        }
        if(!StringUtils.isNullOrEmpty(srcRepairPro.get(index).get("WORKER_TYPE_CODE"))){
            repairProManagerDTO.setWorkTypeCode((String) srcRepairPro.get(index).get("WORKER_TYPE_CODE").toString());
        }
        
        if(!StringUtils.isNullOrEmpty(srcRepairPro.get(index).get("STD_LABOUR_HOUR"))){
        	 repairProManagerDTO.setStdLabourHour(Double.parseDouble(srcRepairPro.get(index).get("STD_LABOUR_HOUR").toString()));
        }
		if (!StringUtils.isNullOrEmpty(srcRepairPro.get(index).get("ASSIGN_LABOUR_HOUR"))) {
			repairProManagerDTO.setAssignLabourHour(Double.parseDouble(srcRepairPro.get(index).get("ASSIGN_LABOUR_HOUR").toString()));
		}
		if (!StringUtils.isNullOrEmpty(srcRepairPro.get(index).get("CLAIM_LABOUR"))) {
			 repairProManagerDTO.setClaimLabour(Double.parseDouble(srcRepairPro.get(index).get("CLAIM_LABOUR").toString()));
		}
        if(!StringUtils.isNullOrEmpty(srcRepairPro.get(index).get("OPERATION_CODE"))){
            repairProManagerDTO.setOperationCode((String) srcRepairPro.get(index).get("OPERATION_CODE").toString());
        }
        
        if(!StringUtils.isNullOrEmpty(srcRepairPro.get(index).get("MAIN_GROUP_CODE"))){
            repairProManagerDTO.setMainGroupCode((String) srcRepairPro.get(index).get("MAIN_GROUP_CODE").toString());
        }
        
        if(!StringUtils.isNullOrEmpty(srcRepairPro.get(index).get("SUB_GROUP_CODE"))){
            repairProManagerDTO.setSubGroupCode((String) srcRepairPro.get(index).get("SUB_GROUP_CODE").toString());
        }
        
        if(!StringUtils.isNullOrEmpty(srcRepairPro.get(index).get("OEM_TAG"))){
            repairProManagerDTO.setOmeTag(Integer.parseInt( srcRepairPro.get(index).get("OEM_TAG").toString()));
        }
        
        return repairProManagerDTO;
    }

    /**
     * 树节点查询
    * @author rongzoujie
    * @date 2016年9月14日
    * @param groupCode
    * @param modelGroupCode
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.RepairProManagerService#queryRepairProsByTree(java.lang.String, java.lang.String)
     */
    @Override
    public PageInfoDto queryRepairProsByTree(String groupCode,String modelGroupCode) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlsb = new StringBuilder("SELECT tml.LABOUR_ID,tml.DEALER_CODE,tml.MODEL_LABOUR_CODE,tml.LABOUR_CODE,tml.LABOUR_NAME,trt.REPAIR_TYPE_NAME,");
        sqlsb.append("tml.SPELL_CODE,tml.STD_LABOUR_HOUR,tml.ASSIGN_LABOUR_HOUR,tml.CLAIM_LABOUR,tml.OPERATION_CODE,tml.MAIN_GROUP_CODE,tml.SUB_GROUP_CODE,");
        sqlsb.append("tml.OEM_TAG,twt.WORKER_TYPE_NAME,tml.LOCAL_LABOUR_CODE,tml.LOCAL_LABOUR_NAME FROM tm_labour tml INNER JOIN tm_repair_type trt ON 1 = 1 ");
        sqlsb.append("AND trt.REPAIR_TYPE_CODE = tml.REPAIR_TYPE_CODE INNER JOIN tm_worker_type twt ON twt.WORKER_TYPE_CODE = tml.WORKER_TYPE_CODE ");
        if(!StringUtils.isNullOrEmpty(groupCode)){
            sqlsb.append("AND (MAIN_GROUP_CODE = ?");
            params.add(groupCode);
            sqlsb.append(" OR SUB_GROUP_CODE = ?)");
            params.add(groupCode);
        }
        if(!"none".equals(modelGroupCode)){
            sqlsb.append(" AND MODEL_LABOUR_CODE in ("+modelGroupCode+")");
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(),params);
        return pageInfoDto;
    }

    @Override
    public List<Map> queryRepairProForExport(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = getQuerySql(queryParam,params);
        List<Map> resultList = DAOUtil.findAll(sql, params);
        return resultList;
    }

    @Override
    public void setRepairParts(RepairPartsDTO repairPartsDTO) throws ServiceBizException{
//        Long partId = repairPartsDTO.getPartId();
        Double partQuantity = repairPartsDTO.getPartQuantity();
//        Long labourId = repairPartsDTO.getLabourId(); 
        if(partQuantity == 0){
            throw new ServiceBizException("数量不能为0");
        }
        checkPartsDup(repairPartsDTO.getLabourId(), repairPartsDTO.getPartNo());
        
        RepairPartPO repairPartPO = new RepairPartPO();
//        RepairPartsDTO repairPartsDTO = new RepairPartsDTO();
//        repairPartsDTO.setStoreCode(partInfoPO.getString("STORAGE_CODE"));
//        repairPartsDTO.setPartNo(partInfoPO.getString("PART_CODE"));
//        repairPartsDTO.setPartName(partInfoPO.getString("PART_NAME"));
//        repairPartsDTO.setPartQuantity(partQuantity);
//        repairPartsDTO.setSalesPrice(partInfoPO.getDouble("SALES_PRICE"));
//        repairPartsDTO.setLabourId(labourId);

        setAddParts(repairPartPO, repairPartsDTO);
        repairPartPO.saveIt();
    }
    
    private void setAddParts(RepairPartPO repairPartPO,RepairPartsDTO repairPartsDTO){
        repairPartPO.setInteger("LABOUR_ID", repairPartsDTO.getLabourId());
        repairPartPO.setString("STORAGE_CODE",repairPartsDTO.getStoreCode());
        repairPartPO.setString("PART_NO", repairPartsDTO.getPartNo());
        repairPartPO.setDouble("PART_QUANTITY", repairPartsDTO.getPartQuantity());
        repairPartPO.setDouble("PART_SALES_PRICE", repairPartsDTO.getSalesPrice());
        repairPartPO.setString("PART_NAME", repairPartsDTO.getPartName());
    }
    
    /**
     * 导入维修项目配件
     * @param rpDto
     */
	private void importRepairParts(RepairPartsDTO rpDto) {
		RepairPartPO repairPartPO = new RepairPartPO();
		// 获取labourId
		StringBuilder sql = new StringBuilder("select DEALER_CODE,LABOUR_ID from tm_labour_part where 1=1");
		sql.append(" and labour_code = ?");
		List<Object> param = new ArrayList<Object>();
		param.add(rpDto.getLabourCode());
		List<Map> result = DAOUtil.findAll(sql.toString(), param);
		if (result.size() > 0) {
			Long labourId = Long.parseLong(result.get(0).get("LABOUR_ID").toString());
			repairPartPO.setInteger("LABOUR_ID", labourId);
		} else {
			throw new ServiceBizException("请填写正确的维修项目代码");
		}
		repairPartPO.setString("PART_NO", rpDto.getPartNo());
		repairPartPO.setDouble("PART_QUANTITY", rpDto.getPartQuantity());
		repairPartPO.setDouble("PART_SALES_PRICE", rpDto.getSalesPrice());
		repairPartPO.setString("PART_NAME", rpDto.getPartName());
		
		

	}

    /**
     * 更具维修项目查询维修配件
    * @author rongzoujie
    * @date 2016年9月6日
    * @param labourId
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.RepairProManagerService#queryPartsByLabour(java.lang.Long)
     */
    @Override
    public PageInfoDto queryPartsByLabour(Long labourId) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlsb = new StringBuilder("SELECT t4.MODEL_LABOUR_NAME,t1.LABOUR_PART_ID,t1.DEALER_CODE,t1.PART_NAME,t1.PART_NO,t1.PART_QUANTITY AS PART_QUAUTITY_NUM,t2.STOCK_QUANTITY,(t2.STOCK_QUANTITY+t2.BORROW_QUANTITY-t2.LEND_QUANTITY-t2.LOCKED_QUANTITY) as AVAILABLE,t3.MODEL_LABOUR_CODE,t1.PART_SALES_PRICE,t3.LABOUR_CODE,t5.STORAGE_NAME,t1.STORAGE_CODE FROM tm_labour_part t1");
        sqlsb.append(" LEFT JOIN tt_part_stock t2 ON t1.PART_NO = t2.PART_CODE and t1.STORAGE_CODE = t2.STORAGE_CODE and t1.DEALER_CODE = t2.DEALER_CODE");
        sqlsb.append(" LEFT JOIN tm_labour t3 on t3.LABOUR_id = t1.LABOUR_ID and t1.DEALER_CODE = t3.DEALER_CODE");
        sqlsb.append(" LEFT JOIN tm_model_group t4 on t4.MODEL_LABOUR_CODE = t3.MODEL_LABOUR_CODE and t4.DEALER_CODE = t1.DEALER_CODE LEFT JOIN tm_store t5 on t5.STORAGE_CODE = t1.STORAGE_CODE and t1.DEALER_CODE = t5.DEALER_CODE");
        sqlsb.append(" where 1=1");
        sqlsb.append(" and t1.LABOUR_ID = ?");
        params.add(labourId);
        
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(),params);
        return pageInfoDto;
    }
    
    /**
     *检查添加配件是否重复 
    * @author rongzoujie
    * @date 2016年11月16日
    * @param labourId
    * @param roNO
    * @throws ServiceBizException
     */
    public void checkPartsDup(Long labourId,String roNO) throws ServiceBizException{
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlsb = new StringBuilder("select DEALER_CODE from tm_labour_part where 1=1");
        sqlsb.append(" and LABOUR_ID = ?");
        params.add(labourId);
        sqlsb.append(" and PART_NO = ?");
        params.add(roNO);
        
        List<Map> result = DAOUtil.findAll(sqlsb.toString(), params);
        if(result.size()>0){
            throw new ServiceBizException("请不要添加相同的配件");
        }
    }

    /**
     * 删除配件
    * @author rongzoujie
    * @date 2016年11月7日
    * @param id
    * @throws ServiceBizException
     */
    @Override
    public void deleteRepairPart(Long id) throws ServiceBizException {
        StringBuilder sql = new StringBuilder("select PART_NO,DEALER_CODE,LABOUR_ID from TM_LABOUR_PART WHERE LABOUR_PART_ID = ?");
        List<Object> param = new ArrayList<Object>();
        param.add(id);
        Map result = DAOUtil.findFirst(sql.toString(), param);
        String partNo = result.get("PART_NO").toString();
        Long labourId = Long.parseLong(result.get("LABOUR_ID").toString());
        
        if(checkPartOutStorage(partNo,labourId)){
            RepairPartPO repairPartPO = RepairPartPO.findById(id);
            repairPartPO.deleteCascadeShallow();
        }else{
            throw new ServiceBizException("配件已出库不能删除");
        }
    }
    
    /**
     *检查配件是否出库 
    * @author rongzoujie
    * @date 2016年11月7日
    * @param id
    * @return
     */
    private boolean checkPartOutStorage(String partNo,Long labourId)throws ServiceBizException {
        List<Object> param = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("select DEALER_CODE,IS_FINISHED from TT_RO_PART WHERE 1=1");
        sql.append(" and PART_NO = ?");
        param.add(partNo);
        sql.append(" and RO_LABOUR_ID = ?");
        param.add(labourId);
        
        List<Map> result = DAOUtil.findAll(sql.toString(), param);
        if(result.size() > 0){
            Integer isFinish = Integer.parseInt(result.get(0).get("IS_FINISHED").toString());
            if(isFinish == DictCodeConstants.STATUS_IS_YES){
                return false;
            }
        }
        return true;
    }

    /**
     * 导出维修配件
    * @author rongzoujie
    * @date 2016年9月6日
    * @param labourId
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.RepairProManagerService#exportRepairParts(java.lang.Long)
     */
    @Override
    public List<Map> exportRepairParts(Long labourId) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = getPartsByLabourIdSql(labourId,params);
        List<Map> result = DAOUtil.findAll(sql, params);
        return result;
    }
    
    /**
     * 更具维修项目查询维修配件(导出)
    * @author rongzoujie
    * @date 2016年9月6日
    * @param labourId
    * @param params
    * @return
     */
    private String getPartsByLabourIdSql(Long labourId,List<Object> params)throws ServiceBizException {
//        StringBuilder sqlsb = new StringBuilder("SELECT t3.MODEL_LABOUR_CODE,t1.LABOUR_PART_ID,t1.DEALER_CODE,t1.PART_NAME,t1.PART_NO,t1.PART_QUANTITY,t2.STOCK_QUANTITY,t1.PART_SALES_PRICE,t3.LABOUR_CODE FROM tm_labour_part t1");
//        sqlsb.append(" LEFT JOIN tt_part_stock t2 ON t1.PART_NO = t2.PART_CODE");
//        sqlsb.append(" LEFT JOIN tm_labour t3 on t3.LABOUR_id = t1.LABOUR_ID where 1=1");
//        sqlsb.append(" and t1.LABOUR_ID = ?");
        StringBuilder sqlsb = new StringBuilder("SELECT t1.DEALER_CODE,t1.PART_NO,t1.PART_QUANTITY as PART_QUAUTITY_NUM,t1.PART_NAME,(t2.STOCK_QUANTITY+t2.BORROW_QUANTITY-t2.LEND_QUANTITY+t2.LOCKED_QUANTITY) as AVAILABLE,t1.PART_SALES_PRICE,t3.LABOUR_CODE,t3.MODEL_LABOUR_CODE FROM tm_labour_part t1");
        sqlsb.append(" LEFT JOIN tt_part_stock t2 ON t1.PART_NO = t2.PART_CODE and t1.STORAGE_CODE = t2.STORAGE_CODE and t1.DEALER_CODE = t2.DEALER_CODE");
        sqlsb.append(" LEFT JOIN tm_labour t3 on t3.LABOUR_id = t1.LABOUR_ID and t1.DEALER_CODE = t3.DEALER_CODE");
        sqlsb.append(" LEFT JOIN tm_model_group t4 on t4.MODEL_LABOUR_CODE = t3.MODEL_LABOUR_CODE and t4.DEALER_CODE = t1.DEALER_CODE LEFT JOIN tm_store t5 on t5.STORAGE_CODE = t1.STORAGE_CODE and t1.DEALER_CODE = t5.DEALER_CODE");
        sqlsb.append(" where 1=1");
        sqlsb.append(" and t1.LABOUR_ID = ?");
        params.add(labourId);
        return sqlsb.toString();
    }

    /**
     * 添加维修配件
    * @author rongzoujie
    * @date 2016年9月6日
    * @param repairPartsDTO
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.RepairProManagerService#addRepairPart(com.yonyou.dms.repair.domains.DTO.basedata.RepairPartsDTO)
     */
    @Override
    public void addRepairPart(RepairPartsDTO repairPartsDTO)throws ServiceBizException  {
        RepairPartPO repairPartPO = new RepairPartPO();
        setAddParts(repairPartPO,repairPartsDTO);
        repairPartPO.saveIt();
    }
    
    /**
     * 倒入维修配件
    * @author rongzoujie
    * @date 2016年11月28日
    * @param repairPartsDTO
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.RepairProManagerService#addRepairPart(com.yonyou.dms.repair.domains.DTO.basedata.RepairPartsDTO)
     */
    public void importAddRepairPart(RepairPartsDTO repairPartsDTO)throws ServiceBizException {
    	RepairPartPO repairPartPO = new RepairPartPO();
    	List<Object> param = new ArrayList<Object>();
    	String labourCode = repairPartsDTO.getLabourCodeDesc();
    	String modelLabourCode = repairPartsDTO.getModelLabourCode();
    	StringBuilder sql = new StringBuilder("select DEALER_CODE,LABOUR_ID from tm_labour where 1=1");
    	sql.append(" and labour_code = ?");
    	param.add(labourCode);
    	sql.append(" and MODEL_LABOUR_CODE = ?");
    	param.add(modelLabourCode);
    	List<Map> result = DAOUtil.findAll(sql.toString(), param);
    	if(result.size()>0){
    		String partCode=repairPartsDTO.getPartNo();
        	StringBuilder sqlSb =new StringBuilder("select DEALER_CODE,PART_ID,PART_CODE from tm_part_info where 1=1 ");
        	sqlSb.append(" and PART_CODE=?");
        	List<Object> params = new ArrayList<Object>();
        	params.add(partCode);
        	List<Map> results = DAOUtil.findAll(sqlSb.toString(), params);
        	if(results.size()>0){
        		if(StringUtils.isNullOrEmpty(repairPartsDTO.getPartQuantity())){
        			throw new ServiceBizException("数量不能为空!");
        		}
        		Long labourId = Long.parseLong(result.get(0).get("LABOUR_ID").toString());
        		StringBuilder sqlsb=new StringBuilder("select DEALER_CODE,LABOUR_PART_ID,LABOUR_ID from tm_labour_part where 1=1 ");
            	sqlsb.append(" and LABOUR_ID=? and PART_NO=?");
            	List<Object> paramS = new ArrayList<Object>();
            	paramS.add(labourId);
            	paramS.add(partCode);
            	List<Map> resultS = DAOUtil.findAll(sqlsb.toString(), paramS);
            	if(resultS.size()>0){
            		throw new ServiceBizException("该配件已存在，不允许再次导入!");
            	}else{
            	
            		repairPartPO.setDouble("PART_QUANTITY",repairPartsDTO.getPartQuantity());
            		repairPartPO.setDouble("PART_SALES_PRICE", repairPartsDTO.getSalesPrice());
            		repairPartPO.setLong("LABOUR_ID", labourId);
            		repairPartPO.setString("PART_NO",repairPartsDTO.getPartNo());
            		repairPartPO.setString("PART_NAME",repairPartsDTO.getPartName());
            		repairPartPO.saveIt();
            	}
        	}else{
        		throw new ServiceBizException("没有匹配的配件!");
        	}
    	}else{
    		throw new ServiceBizException("没有匹配的维修项目或车型组!");
    	}

    	
    }

    /**
     * 根据工单id查询维修项目
    * @author jcsi
    * @date 2016年10月11日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.RepairProManagerService#findByRoLabourByRoId(java.lang.Long)
     */
    @Override
    public List<Map> findByRoLabourByRoId(Long id) throws ServiceBizException {
        String sql="SELECT t.RO_LABOUR_ID,t.DEALER_CODE,t.LABOUR_CODE,t.LABOUR_NAME from TT_RO_LABOUR t where RO_ID=?";
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(id);
        return DAOUtil.findAll(sql, queryParam);
    }

    /**
     * 导入维修项目 
    * @author rongzoujie
    * @date 2016年11月3日
    * @param rowDto
    * @throws ServiceBizException
     */
    @Override
    public void importRepairPro(RepairProManagerDTO rowDto) throws ServiceBizException {
//        RepairProManagerPO repairProManagerPO = new RepairProManagerPO();
        
        rowDto.setOmeTag(DictCodeConstants.STATUS_IS_NOT);
        String checkYes = new String("是");
        if(checkYes.equals(rowDto.getOmeTagDesc())){
            throw new ServiceBizException("OEM为是不能导入");
        }
        
        //判断导入的维修项目的车型组是否存在 
        checkModel(rowDto.getModelLabourCode());
        //删除维修项目代码和项目车型组相同的数据
        List<Map> result = checkDupCode(rowDto.getModelLabourCode(),rowDto.getLabourCode());
        if(result.size()>0){
            Long labourId = Long.parseLong(result.get(0).get("LABOUR_ID").toString());
            RepairProManagerPO repairProManagerPO = RepairProManagerPO.findById(labourId);
            extendImportFelid(rowDto,repairProManagerPO);
            repairProManagerPO.saveIt();
        }else{
            RepairProManagerPO repairProManagerPO = new RepairProManagerPO();
            repairProManagerPO.setString("MODEL_LABOUR_CODE", rowDto.getModelLabourCode());
            repairProManagerPO.setString("LABOUR_CODE",rowDto.getLabourCode());
            extendImportFelid(rowDto,repairProManagerPO);
            repairProManagerPO.saveIt();
        }
        
       
//        repairProManagerPO.setString("LABOUR_NAME",rowDto.getLabourName());
//        repairProManagerPO.setString("SPELL_CODE",rowDto.getSpellCode());
//        repairProManagerPO.setString("REPAIR_GROUP_CODE", rowDto.getRepairGroupCode());
//        repairProManagerPO.setString("LOCAL_LABOUR_CODE", rowDto.getLocalLabourCode());
//        repairProManagerPO.setString("LOCAL_LABOUR_NAME", rowDto.getLocalLabourName());
//        repairProManagerPO.setInteger("OEM_TAG", rowDto.getOmeTag());
//        Integer repairCode = getRepairTypeInt(rowDto.getRepairTypeDesc());
//        repairProManagerPO.setInteger("REPAIR_TYPE_CODE",repairCode);
//        String workerTypeCode = getWorkTypeCode(rowDto.getWorkTypeDesc());
//        repairProManagerPO.setString("WORKER_TYPE_CODE",workerTypeCode);
//        repairProManagerPO.setDouble("STD_LABOUR_HOUR", rowDto.getStdLabourHour());
//        repairProManagerPO.setDouble("ASSIGN_LABOUR_HOUR", rowDto.getAssignLabourHour());
//        repairProManagerPO.setDouble("CLAIM_LABOUR", rowDto.getClaimLabHour());
//        repairProManagerPO.setString("OPERATION_CODE", rowDto.getOperationCode());
//        repairProManagerPO.setString("MAIN_GROUP_CODE", rowDto.getMainGroupCode());
//        if(!StringUtils.isNullOrEmpty(rowDto.getSubGroupCode())){
//            repairProManagerPO.setString("SUB_GROUP_CODE", rowDto.getSubGroupCode());
//        }else{
//            repairProManagerPO.setString("SUB_GROUP_CODE", "");
//        }
    }
    /**
     * 判断导入的维修项目的车型组是否存在 
    * @author rongzoujie
    * @date 2016年11月16日
    * @param modelLabourCode
     */
    private void checkModel(String modelLabourCode){
        List<Object> param = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("select model_labour_code,dealer_code from tm_model_group where 1=1");
        sql.append(" and IS_VALID = ?");
        param.add(DictCodeConstants.STATUS_IS_YES);
        sql.append(" and model_labour_code = ?");
        param.add(modelLabourCode);
        List<Map> result = DAOUtil.findAll(sql.toString(), param);
        if(result.size()<=0){
            throw new ServiceBizException("无效或不存在的车型代码无法导入");
        }
    }
    
    /**
     *删除维修项目代码和项目车型组相同的数据 
    * @author rongzoujie
    * @date 2016年11月16日
    * @param modelLabourCode
    * @param labourCode
     */
    public List<Map> checkDupCode(String modelLabourCode,String labourCode){
        List<Object> param = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("select LABOUR_ID,DEALER_CODE from tm_labour where 1=1");
        sql.append(" and MODEL_LABOUR_CODE = ?");
        param.add(modelLabourCode);
        sql.append(" and LABOUR_CODE = ?");
        param.add(labourCode);
        
        List<Map> result = DAOUtil.findAll(sql.toString(),param);
        return result;
    }
    
    public void extendImportFelid(RepairProManagerDTO rowDto,RepairProManagerPO repairProManagerPO){
        repairProManagerPO.setString("LABOUR_NAME",rowDto.getLabourName());
        repairProManagerPO.setString("SPELL_CODE",rowDto.getSpellCode());
        repairProManagerPO.setString("REPAIR_GROUP_CODE", rowDto.getRepairGroupCode());
        repairProManagerPO.setString("LOCAL_LABOUR_CODE", rowDto.getLocalLabourCode());
        repairProManagerPO.setString("LOCAL_LABOUR_NAME", rowDto.getLocalLabourName());
        repairProManagerPO.setInteger("OEM_TAG", rowDto.getOmeTag());
        Integer repairCode = getRepairTypeInt(rowDto.getRepairTypeDesc());
        if(repairCode == 0){
            repairProManagerPO.setInteger("REPAIR_TYPE_CODE",null);
        }else{
            repairProManagerPO.setInteger("REPAIR_TYPE_CODE",repairCode);
        }
        String workerTypeCode = getWorkTypeCode(rowDto.getWorkTypeDesc());
        repairProManagerPO.setString("WORKER_TYPE_CODE",workerTypeCode);
        repairProManagerPO.setDouble("STD_LABOUR_HOUR", rowDto.getStdLabourHour());
        repairProManagerPO.setDouble("ASSIGN_LABOUR_HOUR", rowDto.getAssignLabourHour());
        repairProManagerPO.setDouble("CLAIM_LABOUR", rowDto.getClaimLabHour());
        repairProManagerPO.setString("OPERATION_CODE", rowDto.getOperationCode());
        if(checkMainGroup(rowDto.getMainGroupCode())){
            repairProManagerPO.setString("MAIN_GROUP_CODE", rowDto.getMainGroupCode());
        }else{
            repairProManagerPO.setString("MAIN_GROUP_CODE", "");
        }
        if(!StringUtils.isNullOrEmpty(rowDto.getSubGroupCode())){
            if(checkSubGroup(rowDto.getSubGroupCode())){
                repairProManagerPO.setString("SUB_GROUP_CODE", rowDto.getSubGroupCode());
            }else{
                repairProManagerPO.setString("SUB_GROUP_CODE", "");
            }
        }else{
            repairProManagerPO.setString("SUB_GROUP_CODE", "");
        }
    }
    
    private boolean checkMainGroup(String mainGroupCode){
        List<Object> param = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("select MAIN_GROUP_NAME,MAIN_GROUP_CODE,DEALER_CODE from tm_labour_group where 1=1 ");
        sql.append("and MAIN_GROUP_CODE = ?");
        param.add(mainGroupCode);
        List<Map> result = DAOUtil.findAll(sql.toString(), param);
        if(result.size() > 0){
            return true;
        }else{
            return false;
        }
    }
    
    private boolean checkSubGroup(String subGroupCode){
//        select SUB_GROUP_NAME,SUB_GROUP_CODE,DEALER_CODE from tm_labour_subgroup where 1=1 and SUB_GROUP_CODE = 100101
        List<Object> param = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("select SUB_GROUP_NAME,SUB_GROUP_CODE,DEALER_CODE from tm_labour_subgroup where 1=1 ");
        sql.append("and SUB_GROUP_CODE = ?");
        param.add(subGroupCode);
        List<Map> result = DAOUtil.findAll(sql.toString(), param);
        if(result.size() > 0){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 更具维修类型名转换成维修类型code 
    * @author rongzoujie
    * @date 2016年11月3日
    * @param repairTypeName
    * @return
     */
    private Integer getRepairTypeInt(String repairTypeName){
        StringBuilder sql = new StringBuilder("select DEALER_CODE,REPAIR_TYPE_CODE from tm_repair_type where REPAIR_TYPE_NAME = ?");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(repairTypeName);
        List<Map> repairTypeCodes = DAOUtil.findAll(sql.toString(), queryParam);
        if(repairTypeCodes.size() > 0){
            String repairTypeCodeStr = repairTypeCodes.get(0).get("REPAIR_TYPE_CODE").toString();
            Integer repairTypeInt = Integer.parseInt(repairTypeCodeStr);
            return repairTypeInt;
        }else{
            return 0;
        }
       
    }
    
    /**
    *  更具工种类型名称查询工种类型code
    * @author rongzoujie
    * @date 2016年11月3日
    * @param workTypeName
    * @return
     */
    private String getWorkTypeCode(String workTypeName){
        StringBuilder sql = new StringBuilder("select DEALER_CODE,WORKER_TYPE_CODE from tm_worker_type where WORKER_TYPE_NAME = ?");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(workTypeName);
        List<Map> workerTypeCodes = DAOUtil.findAll(sql.toString(), queryParam);
        if(workerTypeCodes.size() > 0){
            String workerTypeCodeStr = workerTypeCodes.get(0).get("WORKER_TYPE_CODE").toString();
            return workerTypeCodeStr;
        }else{
            return "";
        }
        
        
    }
    
}
