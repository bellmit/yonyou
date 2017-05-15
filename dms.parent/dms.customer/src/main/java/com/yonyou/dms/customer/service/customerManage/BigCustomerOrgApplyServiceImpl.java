package com.yonyou.dms.customer.service.customerManage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmBigCustomerOrgApplyPO;
import com.yonyou.dms.customer.domains.DTO.customerManage.BigCustomerOrgApplyDTO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class BigCustomerOrgApplyServiceImpl implements BigCustomerOrgApplyService {
    @Autowired
    private CommonNoService commonNoService;
	
    /* (non-Javadoc)
	 * @see com.yonyou.dms.customer.service.customerManage.BigCustomerOrgApplyService#querybigCusApply(java.util.Map)
	 */
    @Override
	public PageInfoDto querybigCusApply(Map<String, String> queryParam) throws ServiceBizException {
    	StringBuilder sb = new StringBuilder();
        sb.append(" Select a.* from TM_BIG_CUSTOMER_ORG_APPLY A  LEFT JOIN   tm_user b ON a.`USER_CODE` = b.`USER_CODE` AND a.`EMPLOYEE_NO` = b.`EMPLOYEE_NO`   where 1=1 ");
        this.sqlToDate(sb, queryParam.get("startTime"), queryParam.get("endTime"), "APPLY_DATE", "A");
        this.sqlToEquals(sb, queryParam.get("userName"), "USER_ID", "B");
        sb.append(" order by APPLY_NO desc ");
		
        List<Object> queryList = new ArrayList<Object>();
        System.out.println("SQL------------------:   "+sb.toString());
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }
    
    public void setBigCustomerOrgApply(TmBigCustomerOrgApplyPO bigCustomerOrgApplyPo, BigCustomerOrgApplyDTO bigCustomerOrgApplyDto) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String date =   sdf.format(new Date()); 
    	
    	bigCustomerOrgApplyPo.setString("USER_CODE", bigCustomerOrgApplyDto.getUserCode().toString());
    	bigCustomerOrgApplyPo.setString("EMPLOYEE_NO", bigCustomerOrgApplyDto.getEmployeeCode().toString());
    	bigCustomerOrgApplyPo.setString("USER_NAME", bigCustomerOrgApplyDto.getUserName());
    	bigCustomerOrgApplyPo.setString("ORIGINAL_STATION", bigCustomerOrgApplyDto.getOriginalStation());
    	bigCustomerOrgApplyPo.setString("IS_PARTTIME", bigCustomerOrgApplyDto.getIsParttime());
    	bigCustomerOrgApplyPo.setString("PARTTIME_STATION", bigCustomerOrgApplyDto.getParttimeStation());
    	bigCustomerOrgApplyPo.setString("CONTACTOR_MOBILE", bigCustomerOrgApplyDto.getContactorMobile());
    	bigCustomerOrgApplyPo.setString("BRAND_YEAR", bigCustomerOrgApplyDto.getBrandYear());
    	bigCustomerOrgApplyPo.setString("REMARK", bigCustomerOrgApplyDto.getRemark());
    	bigCustomerOrgApplyPo.setString("SELF_EVALUTION", bigCustomerOrgApplyDto.getSelfEvalution());
    	bigCustomerOrgApplyPo.setString("APPLY_STATUS", 47771001);
    	bigCustomerOrgApplyPo.setString("APPLY_DATE", date);    		
    }
    
    
    @Override
    public void addBigCustomerOrg(BigCustomerOrgApplyDTO bigCustomerOrgApplyDto) throws ServiceBizException {
    	String userCode = bigCustomerOrgApplyDto.getUserCode().toString();
    	List<Map> waList = this.queryByWaiting(userCode);
    	for(int i=0;i<waList.size();i++){
    		if("1".equals(waList.get(i).get("STATUS_COUNT"))){
    			throw new ServiceBizException("总部还未审核批，请等待！");
    		}    		 
    	}
    	List<Map> okList = this.queryByOk(userCode);
    	for(int i=0;i<okList.size();i++){
    		if(Integer.parseInt(okList.get(i).get("STATUS_COUNT").toString()) == 1){
    			throw new ServiceBizException("大客户组织架构权限申请审批已经通过，不能再次申请！");
    		}    		    	
    		if(Integer.parseInt(okList.get(i).get("STATUS_COUNT").toString()) != 0 ){
    			throw new ServiceBizException("大客户组织架构权限申请审批已经通过，不能再次申请！");
    		}    
    	}
    	
    	TmBigCustomerOrgApplyPO bigCustomerOrgApplyPo = new TmBigCustomerOrgApplyPO();
        List<Map> result = this.queryBigCustomer();
        for(int i=0;i<result.size();i++){
        	Map mm = result.get(i);
        	String sumorg =mm.get("SUMORG").toString();		
        	String sumapp = mm.get("SUMAPP").toString();	
        	int j = Integer.parseInt(sumorg)+Integer.parseInt(sumapp);
        		if(j>2){
        			throw new ServiceBizException("DMS中大客户经理数量已有3个，不能再次申请！");
        		}
        	}
        String applyNo =commonNoService.getSystemOrderNo(CommonConstants.APPLY_ORG_NO);
        bigCustomerOrgApplyPo.setString("APPLY_NO", applyNo);
        this.setBigCustomerOrgApply(bigCustomerOrgApplyPo, bigCustomerOrgApplyDto);
        bigCustomerOrgApplyPo.saveIt();
        
    }
    
    
    public List<Map> queryBigCustomer() {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT  SUM(c.SUMORG) SUMORG,SUM(c.SUMAPP) SUMAPP FROM ( SELECT COUNT(1) AS SUMORG ,0 AS SUMAPP FROM tm_user_ctrl a WHERE 1=1   AND a.CTRL_CODE='80900000'  UNION ALL  SELECT 0 AS SUMORG ,COUNT(1) AS SUMAPP FROM Tm_Big_Customer_Org_Apply b WHERE 1=1  AND b.APPLY_STATUS=47771001 ) c");
        List<Object> queryList = new ArrayList<Object>();
        return Base.findAll(sb.toString(), queryList.toArray());
    }
    
    /**
     * 查询大客户组织架构权限待审批是否存在数据
     * @param userCode
     * @return
     */
    public List<Map> queryByWaiting(String userCode) {
        StringBuffer sb = new StringBuffer();
		 sb.append("SELECT COUNT(*) AS STATUS_COUNT FROM (SELECT * FROM TM_BIG_CUSTOMER_ORG_APPLY A where 1=1");
		if(userCode!=null&&!userCode.trim().equals("")){
		sb.append(	" AND A.USER_CODE='"+userCode+"'" );
		}
		sb.append(" order by created_at desc limit 1 ) a ");
		sb.append(" where a.APPLY_STATUS=47771001 ");
        List<Object> queryList = new ArrayList<Object>();
        return Base.findAll(sb.toString(), queryList.toArray());
    }
    
    /**
     * 查询大客户组织架构权限审批通过是否存在数据
     * @param userCode
     * @return
     */
    public List<Map> queryByOk(String userCode) {
        StringBuffer sb = new StringBuffer();
        sb.append( "SELECT COUNT(*) AS STATUS_COUNT FROM (SELECT * FROM TM_BIG_CUSTOMER_ORG_APPLY A where 1=1");
		if(userCode!=null&&!userCode.trim().equals("")){
			sb.append(" AND A.USER_CODE='"+userCode+"'");
		}	
		sb.append(" order by created_at desc limit 1) a ");
		sb.append(" where a.APPLY_STATUS=47771002");
        List<Object> queryList = new ArrayList<Object>();
        return Base.findAll(sb.toString(), queryList.toArray());
    }
    
    
    @SuppressWarnings("unchecked")
	@Override
    public List<Map> querySafeToExport(Map<String, String> queryParam) throws ServiceBizException {

    	 LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		 List<Map> list  = null;
		 List<Object> queryList = new ArrayList<Object>();
		 
		 StringBuilder sb = new StringBuilder();
	        sb.append(" Select * from TM_BIG_CUSTOMER_ORG_APPLY A where 1=1 ");
	        this.sqlToDate(sb, queryParam.get("startTime"), queryParam.get("endTime"), "APPLY_DATE", "A");
	        this.sqlToEquals(sb, queryParam.get("userName"), "USER_NAME", "A");
	        sb.append(" order by APPLY_NO desc ");	

			       
	       list = DAOUtil.findAll(sb.toString(), queryList);
	       for (Map map : list) {
	            if (map.get("APPLY_STATUS") != null && map.get("APPLY_STATUS") != "") {
	                if (Integer.parseInt(map.get("APPLY_STATUS").toString()) == 47771001) {
	                    map.put("APPLY_STATUS", "提交待审批");
	                } else if (Integer.parseInt(map.get("APPLY_STATUS").toString()) == 47771002) {
	                    map.put("APPLY_STATUS", "审批通过");
	                } else if (Integer.parseInt(map.get("APPLY_STATUS").toString()) == 47771003) {
	                    map.put("APPLY_STATUS", "审批拒绝");
	                }
	            }
	            
	            if (map.get("REMARK") != null && map.get("REMARK") != "") {
	                if (Integer.parseInt(map.get("REMARK").toString()) == 57821001) {
	                    map.put("REMARK", "原大客户经理离职");
	                } else if (Integer.parseInt(map.get("REMARK").toString()) == 57821002) {
	                    map.put("REMARK", "原大客户经理升职");
	                } else if (Integer.parseInt(map.get("REMARK").toString()) == 57821003) {
	                    map.put("REMARK", "原大客户经理调岗");
	                } else if (Integer.parseInt(map.get("REMARK").toString()) == 57821004) {
	                    map.put("REMARK", "增配培养，原申请理由去除");
	                }
	            }
	            
	            
	       }
	       
	       
	       
	       return list;
		       
    }


    /**
     * TODO 拼接sql语句模糊查询
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @param param 查询条件
     * @param field 查询的字段
     * @param alias 表的别名
     * @return
     */
    public void sqlToLike(StringBuilder sb, String param, String field, String alias) {
        if (StringUtils.isNotBlank(param)) {
            sb.append("AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" LIKE '%" + param + "%' ");
        }
    }

    /**
     * TODO 拼接sql语句等量查询
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @param param 查询条件
     * @param field 查询的字段
     * @param alias 表的别名
     * @return
     */
    public void sqlToEquals(StringBuilder sb, String param, String field, String alias) {
        if (StringUtils.isNotBlank(param)) {
            sb.append(" AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" = '" + param + "' ");
        }
    }
    
    /**
     * TODO 拼接sql语句时间查询(单个字段)
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @param begin 开始时间
     * @param end 结束时间
     * @param field 查询的字段
     * @param alias 表的别名
     * @return
     */
    public void sqlToDate(StringBuilder sb, String begin, String end, String field, String alias) {
        if (StringUtils.isNotBlank(begin) && StringUtils.isNotBlank(end)) {
            sb.append("AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" between '" + begin + "' AND '" + end + "' ");
        } else if (StringUtils.isNotBlank(begin) && !StringUtils.isNotBlank(end)) {
            sb.append("AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" >= '" + begin + "' ");
        } else if (!StringUtils.isNotBlank(begin) && StringUtils.isNotBlank(end)) {
            sb.append("AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" <= '" + end + "' ");
        }
    }
    
}
