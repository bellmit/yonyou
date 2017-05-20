package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr.TtWrLabourDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrLabourDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrLabourPO;

/**
 * 索赔工时维护
 * @author Administrator
 *
 */
@Service
public class TtWrLabourServiceImpl  implements TtWrLabourService{
	@Autowired
	TtWrLabourDao  ttWrLabourDao;
	
	/**
	 * 查询所有车系集合
	 */
	
	@Override
	public List<Map> getlabour(Map<String, String> queryParams) {
		// TODO Auto-generated method stub
		return ttWrLabourDao.getLabour(queryParams);
	}
   /**
    * 索赔工时维护查询
    */
	@Override
	public PageInfoDto LabourQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return ttWrLabourDao.LabourQuery(queryParam);
	}
	
	/**
	 * 删除索赔工时维护
	 */
		@Override
		public void delete(Long id) {
			TtWrLabourPO ptPo = TtWrLabourPO.findById(id);
			   	if(ptPo!=null){
				   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
				   ptPo.saveIt();	
	}
		}
	/**
	 * 通过id修改索赔工时维护时的回显信息
	 */

	@Override
	public TtWrLabourPO getLabourById(Long id) {
		// TODO Auto-generated method stub
		return TtWrLabourPO.findById(id);
	}
	/**
	 * 通过id进行修改索赔工时维护
	 */
	@Override
	public void edit(Long id, TtWrLabourDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtWrLabourPO   po=TtWrLabourPO.findById(id);
		 po.setString("SKILL_CATEGORY", dto.getSkillCategory());
		 po.setDouble("RAW_SUBSIDY", dto.getRawSubsidy());
		 po.setString("LABOUR_TYPE", dto.getLabourType());
		 po.setDouble("LABOUR_NUM", dto.getLabourNum());
		 //得到groupCode
		 po.setString("GROUP_CODE", dto.getGroupCode1());
		 po.setLong("UPDATE_BY", loginInfo.getUserId());
		 po.setDate("UPDATE_DATE", new Date());
		 po.setInteger("IS_DOWN",0);
		 po.setInteger("VER",0);
		 po.setInteger("IS_DEL",0);		 
		 List<Map> list2=getGroupId(dto.getGroupCode1());
		  Map  m= list2.get(0);
		  Long group_id = (Long) m.get("group_id");
		  po.setLong("GROUP_ID", group_id);	
		  po.saveIt();

	}
	@Override
	public List<Map> getGroupId(String group_code) throws ServiceBizException {
		// TODO Auto-generated method stub
		return ttWrLabourDao.getGroupId(group_code);
	}

	
	
	  public void addLabour(TtWrLabourDTO ptdto) {
		   if(ptdto.getLabourCode()!=null&&ptdto.getLabourName()!=null&&ptdto.getLabourNum()!=null){
			   //判断工时代码和车系代码是否存在
		   if(!CommonUtils.isNullOrEmpty(getBy(ptdto))){
			   
		              throw new ServiceBizException("工时代码:"+ptdto.getLabourCode()+"和车系代码:"+ptdto.getGroupCode()+"已经存在！");
		          }else{
				Long id = 0l;
				TtWrLabourPO.delete(" ID = ? ", ptdto.getId());
	        	List<String> list = ptdto.getGroupCode();
	        	if(null!=list && list.size()>0){
	        		for(int i =0;i<list.size();i++){	
		        TtWrLabourPO ptPo=new TtWrLabourPO();
		        ptPo.setString("GROUP_CODE", list.get(i));
			    setApplyPo(ptPo,ptdto);
	        		}
	        	}else{
	        		  throw new ServiceBizException("请选择车系！");
	        	}
	        	}
		   }
	}
		private void setApplyPo(TtWrLabourPO ptPo, TtWrLabourDTO ptdto) {
			   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				   ptPo.setString("LABOUR_CODE", ptdto.getLabourCode());
				   ptPo.setString("LABOUR_NAME", ptdto.getLabourName()); 
				   ptPo.setString("SKILL_CATEGORY", ptdto.getSkillCategory());
				   ptPo.setDouble("RAW_SUBSIDY", ptdto.getRawSubsidy());
				   ptPo.setString("LABOUR_TYPE", ptdto.getLabourType());
				   ptPo.setDouble("LABOUR_NUM", ptdto.getLabourNum());
				   List<Map> list2=getGroupId(ptdto.getGroupCode().get(0));
				   Map m= list2.get(0);
				   ptPo.setLong("GROUP_ID", m.get("GROUP_ID"));
				   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
				   ptPo.setDate("UPDATE_DATE", new Date());
				   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
				   ptPo.setDate("CREATE_DATE", new Date());  
				   ptPo.setInteger("VER",0);
				   ptPo.setInteger("IS_DEL",0);
				   ptPo.setInteger("IS_DOWN",0);
				   ptPo.saveIt(); 
		}
		
	    public List<Map> getBy(TtWrLabourDTO ptdto) throws ServiceBizException {
	    	List<Map> applyList=null;
	        List<String> list = ptdto.getGroupCode();
        	if(null!=list && list.size()>0){
        		for(int i =0;i<list.size();i++){	
        	    StringBuilder sqlSb = new StringBuilder(" 	SELECT labour_code,group_code FROM TT_WR_LABOUR_DCS WHERE 1=1 ");
                List<Object> params = new ArrayList<>();
	           sqlSb.append("  AND group_code= ? ");
	           params.add(list.get(i));
	            sqlSb.append("  and labour_code= ? ");
		        params.add(ptdto.getLabourCode());
	            applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
	            System.out.println(sqlSb);
	            if(applyList != null && applyList.size() > 0){
	            	return applyList;
	            }
	            }
        	    	}
	        return null;
	    }
	    
	    
	    
	    
	
	    
	    
	    
	    //导入数据校验
		//Long groupId = 0l;
		public List<TtWrLabourDTO> checkData(TtWrLabourDTO rowDto,String flag) {
			
			ArrayList<TtWrLabourDTO> resultDTOList = new ArrayList<TtWrLabourDTO>();
			ImportResultDto<TtWrLabourDTO> importResult = new ImportResultDto<TtWrLabourDTO>();
		
			
			 if(StringUtils.isNullOrEmpty(rowDto.getGroupCode1())){
				    TtWrLabourDTO Dto = new TtWrLabourDTO();
				    Dto.setRowNO(Integer.valueOf(rowDto.getRowNO().toString()));
				    Dto.setErrorMsg("车系代码不能为空！");
				    resultDTOList.add(Dto);			 
			   }else{
				   List<Map> list =  ttWrLabourDao.getGroupByCode(rowDto.getGroupCode1().toString());
				   if(list.size() < 1){
					   TtWrLabourDTO Dto = new TtWrLabourDTO();
					    Dto.setRowNO(Integer.valueOf(rowDto.getRowNO().toString()));
					    Dto.setErrorMsg("车系代码不存在，请重新输入！");
					    resultDTOList.add(Dto);				    
				   }else{
					
						Long groupId = Long.parseLong(list.get(0).get("GROUP_ID").toString());
						rowDto.setGroupId(groupId);
				   }
			   }
			 if(StringUtils.isNullOrEmpty(rowDto.getLabourCode())){
				 
				 TtWrLabourDTO Dto = new TtWrLabourDTO();
				    Dto.setRowNO(Integer.valueOf(rowDto.getRowNO().toString()));
				    Dto.setErrorMsg("工时代码不能为空！");
				    resultDTOList.add(Dto);			
				 
			 }else{
					if("1".equals(flag)){
						String str1 = "[a-zA-Z0-9]{12}";
						if(!rowDto.getLabourCode().matches(str1)){
							TtWrLabourDTO Dto = new TtWrLabourDTO();
							    Dto.setRowNO(Integer.valueOf(rowDto.getRowNO().toString()));
							    Dto.setErrorMsg("工时代码必须为12位数字和字母");
							    resultDTOList.add(Dto);				
							
						}else if("2".equals(flag)){		
						}	
					} 
			 }
			 if(StringUtils.isNullOrEmpty(rowDto.getLabourName())){
				 TtWrLabourDTO Dto = new TtWrLabourDTO();
					    Dto.setRowNO(Integer.valueOf(rowDto.getRowNO().toString()));
					    Dto.setErrorMsg("工时名称不能为空!");
					    resultDTOList.add(Dto);		
				}else{
					if(rowDto.getLabourName().length()>100){
						TtWrLabourDTO Dto = new TtWrLabourDTO();
						    Dto.setRowNO(Integer.valueOf(rowDto.getRowNO().toString()));
						    Dto.setErrorMsg("工时名称不能大于100个字节！");
						    resultDTOList.add(Dto);		
					}
				}
				if(rowDto.getSkillCategory()!=null&&rowDto.getSkillCategory().length()>10){
					TtWrLabourDTO Dto = new TtWrLabourDTO();
					    Dto.setRowNO(Integer.valueOf(rowDto.getRowNO().toString()));
					    Dto.setErrorMsg("技能类别不能大于10个字节！");
					    resultDTOList.add(Dto);	
				}
				Float rawSubsidy = null;
				if(rowDto.getRawSubsidy()!=null&&rowDto.getRawSubsidy().toString().length()>4){
					TtWrLabourDTO Dto = new TtWrLabourDTO();
					    Dto.setRowNO(Integer.valueOf(rowDto.getRowNO().toString()));
					    Dto.setErrorMsg("原料补贴不能大于4个字节！");
					    resultDTOList.add(Dto);	
				}else{
					try{
						 if(!StringUtils.isNullOrEmpty(rowDto.getRawSubsidy())){
						   rawSubsidy = Float.valueOf(rowDto.getRawSubsidy());
						}
					}catch (Exception e) {
						TtWrLabourDTO Dto = new TtWrLabourDTO();
						    Dto.setRowNO(Integer.valueOf(rowDto.getRowNO().toString()));
						    Dto.setErrorMsg(" 原料补贴必须是整数或浮点数！");
						    resultDTOList.add(Dto);	
					}
				}
				if(rowDto.getLabourType()!=null&&rowDto.getLabourType().length()>20){
					TtWrLabourDTO Dto = new TtWrLabourDTO();
					    Dto.setRowNO(Integer.valueOf(rowDto.getRowNO().toString()));
					    Dto.setErrorMsg(" 类型不能大于20个字节！");
					    resultDTOList.add(Dto);	
				}
			 if(StringUtils.isNullOrEmpty(rowDto.getLabourNum())){
					TtWrLabourDTO Dto = new TtWrLabourDTO();
				    Dto.setRowNO(Integer.valueOf(rowDto.getRowNO().toString()));
				    Dto.setErrorMsg(" 索赔工时数不能为空！");
				    resultDTOList.add(Dto);			
				}else{
					Float labourNum = null;
					if("1".equals(flag)){
						if(rowDto.getLabourNum().toString().indexOf(".")>0 && rowDto.getLabourNum().toString().length()> rowDto.getLabourNum().toString().indexOf(".")+2){
							TtWrLabourDTO Dto = new TtWrLabourDTO();
						    Dto.setRowNO(Integer.valueOf(rowDto.getRowNO().toString()));
						    Dto.setErrorMsg(" 索赔工时数只能输入整数或者带一位小数的数字！");
						    resultDTOList.add(Dto);	
						}else{
							try{
								labourNum = Float.valueOf(rowDto.getLabourNum());
								if(labourNum>=100){
									TtWrLabourDTO Dto = new TtWrLabourDTO();
								    Dto.setRowNO(Integer.valueOf(rowDto.getRowNO().toString()));
								    Dto.setErrorMsg("索赔工时数不能大于等于100！");
								    resultDTOList.add(Dto);	
								}
							}catch (Exception e) {
								TtWrLabourDTO Dto = new TtWrLabourDTO();
							    Dto.setRowNO(Integer.valueOf(rowDto.getRowNO().toString()));
							    Dto.setErrorMsg("索赔工时数只能输入整数或者带一位小数的数字！");
							    resultDTOList.add(Dto);	
							}
						}
					}
					if("2".equals(flag)){
						if(rowDto.getLabourNum().toString().trim().matches("[0](\\.\\d{1,2})?") || rowDto.getLabourNum().toString().trim().matches("[1-9]+[0-9]*(\\.\\d{1,2})?")){
							labourNum = Float.valueOf(rowDto.getLabourNum());
						}else{
							TtWrLabourDTO Dto = new TtWrLabourDTO();
						    Dto.setRowNO(Integer.valueOf(rowDto.getRowNO().toString()));
						    Dto.setErrorMsg("索赔工时数只能输入整数或者带最多2位小数！");
						    resultDTOList.add(Dto);
						}
					}
				}
				 //通过工时代码和车系代码检查是否已经存在
				 List<Map> listPo=getApplyDataBy(rowDto);	 
				 if(listPo != null && listPo.size() > 0){
						TtWrLabourDTO Dto = new TtWrLabourDTO();
					    Dto.setRowNO(Integer.valueOf(rowDto.getRowNO().toString()));
					    Dto.setErrorMsg("工时代码: "+listPo.get(0).get("LABOUR_CODE")+ ",车系代码："+listPo.get(0).get("GROUP_CODE")+" 已经存在!");
					    resultDTOList.add(Dto);	
					}	 
			return resultDTOList;
			
			
		}
	
		
		//通过工时代码和车系代码检查是否已经存在
		  public List<Map> getApplyDataBy(TtWrLabourDTO rowDto) throws ServiceBizException {
		        StringBuilder sqlSb = new StringBuilder("  SELECT  * from TT_WR_LABOUR_DCS where 1=1  and  IS_DEL="+OemDictCodeConstants.IS_DEL_00+" \n");
		        List<Object> params = new ArrayList<>();
		        sqlSb.append(" and  labour_Code=?  and group_Code=? ");
		        params.add(rowDto.getLabourCode());
		        params.add(rowDto.getGroupCode1());
		        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
		        return applyList;
		    }
		  
		  
		//将数据导入到业务表中
		@Override
		public void insertTtWrLabourDcs(TtWrLabourDTO rowDto) {
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			TtWrLabourPO savePo = new TtWrLabourPO();
			savePo.set("GROUP_CODE", rowDto.getGroupCode1());
			  savePo.setLong("GROUP_ID", rowDto.getGroupId());	
			savePo.set("LABOUR_CODE", rowDto.getLabourCode());
			savePo.set("LABOUR_NAME", rowDto.getLabourName());
			savePo.set("SKILL_CATEGORY", rowDto.getSkillCategory());
			savePo.set("RAW_SUBSIDY", rowDto.getRawSubsidy());
			
			
			savePo.set("TYPE", rowDto.getType());
			savePo.set("LABOUR_NUM", rowDto.getLabourNum());
			
			
			savePo.set("VER", 0);
			savePo.set("IS_DEL", 0);
			savePo.set("IS_DOWN", 0);
			
			savePo.set("CREATE_BY", loginInfo.getUserId());
			savePo.set("CREATE_DATE", new Date(System.currentTimeMillis()));
			savePo.set("UPDATE_BY", loginInfo.getUserId());
			savePo.set("UPDATE_DATE", new Date(System.currentTimeMillis()));
			savePo.saveIt();
		//	 }
		}
}
