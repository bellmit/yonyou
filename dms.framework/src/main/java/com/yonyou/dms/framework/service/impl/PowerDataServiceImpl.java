package com.yonyou.dms.framework.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.domain.UserAccessInfoDto;
import com.yonyou.dms.framework.service.PowerDataService;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* 数据权限Service
* @author Administrator
* @date 2016年12月5日
 */

@Service
public class PowerDataServiceImpl implements PowerDataService{
    
    /**
     * 获取用户的数据权限范围
    * @author Administrator
    * @date 2016年12月5日
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.framework.service.PowerDataService#getDataPower()
     */
    
    @SuppressWarnings({ "rawtypes", "unchecked"})
    @Override
    public void getDataPower(Long userId ,String orgCodes) throws ServiceBizException {
        
       UserAccessInfoDto userAccessInfoDto = ApplicationContextHelper.getBeanByType(UserAccessInfoDto.class);
       Map<String,Map<String,Object>> powerDataRange = new HashMap<>();
       List paramList = new ArrayList();
       StringBuilder sb = new StringBuilder();
       int i = 0;
       sb.append(" SELECT tmr.MENU_ID ,tmr.RANGE_CODE,tmr.IS_DEFAULT,tum.DEALER_CODE  FROM  tc_menu_range tmr ,tm_user_menu_range tumr,tm_user_menu  tum");
       sb.append(" WHERE  tumr.USER_MENU_ID = tum.USER_MENU_ID AND tumr.MENU_RANGE_ID=tmr.MENU_RANGE_ID AND tum.USER_ID = ?");
       paramList.add(userId);
       List<Map> powerDataList = DAOUtil.findAll(sb.toString(), paramList);
       Iterator<Map> pdlIterator = powerDataList.iterator();
       //String[] ranges ={"10371001","10371002","10371003","10371004"}; 
       while(pdlIterator.hasNext()){
           Map dr = pdlIterator.next();
           String mapKey = dr.get("MENU_ID").toString();
           if(!powerDataRange.containsKey(mapKey)){
               powerDataRange.put(mapKey, new HashMap());
           }
           Map dataRange = powerDataRange.get(mapKey);
           String rangeCode = dr.get("RANGE_CODE").toString();
           Integer isDefault =  (Integer) dr.get("IS_DEFAULT");
           if(isDefault==10041001){
               if(rangeCode.equals("10371003")){
                   //判断是否有本组织及以下数据范围权限
                   i++;
               }
               dataRange.put(rangeCode, true);
           }else{
               dataRange.put(rangeCode, false);
           }
           powerDataRange.put(mapKey, dataRange);
       }
       userAccessInfoDto.setDataRange(powerDataRange);
       if(i>0&&orgCodes!=null){
           userAccessInfoDto.setChildDepts(getChildsDepts(orgCodes));
       }
    }

   /**
    * 获取本部门及下属部门的organization_id 
   * @author Administrator
   * @date 2016年12月9日
   * @param orgCode
   * @return
   * @throws ServiceBizException
   * (non-Javadoc)
   * @see com.yonyou.dms.framework.service.PowerDataService#getChildsDepts(java.lang.String)
    */
    @Override
    public String getChildsDepts(String orgCode) throws ServiceBizException {
        StringBuilder childDeptId = new StringBuilder();
        String rootParentOrg = orgCode;
        
        String sql = "SELECT ORGDEPT_ID,dealer_code,ORG_CODE ,PARENT_ORG_CODE FROM tm_organization";
        List<Map> orgCodes = DAOUtil.findAll(sql, null);
        Iterator orgsI = orgCodes.iterator();
        //获取全部含有子部门的部门的信息
        Map<String,Object> deptIds = new HashMap<>();
        Map<String,List> parentChild = new HashMap<>();
        while(orgsI.hasNext()){
            Map<String,Object> map = (Map<String,Object>) orgsI.next();
            String parentOrgCode = (String) map.get("PARENT_ORG_CODE");
            if(parentOrgCode!=null){
                if(!parentChild.containsKey(parentOrgCode)){
                    parentChild.put(parentOrgCode, new ArrayList());
                }
                parentChild.get(parentOrgCode).add(map.get("ORG_CODE"));
            }
            deptIds.put((String) map.get("ORG_CODE"), map.get("ORGDEPT_ID"));
        }
        //逐级查找出含有子部门的子部门的orgCode
       StringBuilder childOrgs = new StringBuilder();
       List parentOrgs = new ArrayList();
       childOrgs.append(rootParentOrg);
       parentOrgs.add(rootParentOrg);
       List recordList = new ArrayList();
       recordList.add(rootParentOrg);
       
       while(recordList.size()>0){
           recordList.clear();
           Iterator parentOrgsIterator = parentOrgs.iterator();
           while(parentOrgsIterator.hasNext()){
               String parentCode = (String) parentOrgsIterator.next();
               if(parentChild.containsKey(parentCode)){
                   for(int i=0;i<parentChild.get(parentCode).size();i++){
                       childOrgs.append(",").append(parentChild.get(parentCode).get(i)); 
                       recordList.add(parentChild.get(parentCode).get(i));
                   }
               }
           }
           parentOrgs.clear();
           Iterator recordIterator = recordList.iterator();
           while(recordIterator.hasNext()){
               parentOrgs.add((String) recordIterator.next());
           }
       }
       
       //根据部门的ORG_CODE获取部门的DEPT_CODE_ID
      String[] orgsArray = childOrgs.toString().split(",");
      for(String deptId : orgsArray ){
          childDeptId.append(deptIds.get(deptId)).append(",");
      }
      String result = childDeptId.toString();
      result = result.substring(0, result.length()-1);
      return result;
    }
    
}
