package com.yonyou.dms.vehicle.service.afterSales.workWeek;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.dao.afterSales.workWeek.WorkWeekManageDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.workWeek.TmpWeekDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.workWeek.TmpWrClaimmonthDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.workWeek.TmWeekPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.workWeek.TmpWeekPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.workWeek.TmpWrClaimmonthPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.workWeek.TtWrClaimmonthPO;

/**
 * 工作周查询
 * @author Administrator
 *
 */
@Service
public class WorkWeekManageServiceImpl implements WorkWeekManageService{
	@Autowired
	WorkWeekManageDao workWeekManageDao;

	//周查询
	@Override
	public PageInfoDto WorkWeekManageQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return workWeekManageDao.WorkWeekManageQuery(queryParam);
	}
	//删除临时表数据
	@Override
	public void deleteTmpRecallVehicleDcs() {
		TmpWeekPO trvdPo = new TmpWeekPO();
		trvdPo.deleteAll();
	}
	//插入临时表数据
	@Override
	public void saveTmpRecallVehicleDcs(TmpWeekDTO rowDto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		//保存
		TmpWeekPO savePo = new TmpWeekPO();
	//	savePo.set("ID",rowDto.getRowNO());
		savePo.set("YEAR_CODE", rowDto.getYear_code());
		savePo.set("WEEK_CODE", rowDto.getWeek_code());
		if(rowDto.getYear_code()!=null&& rowDto.getWeek_code()!=null){
			int year = Integer.parseInt(rowDto.getYear_code());
			int week = Integer.parseInt(rowDto.getWeek_code());
			
			// 得到某年某周的第一天  
			Date firstDayOfWeek = getFirstDayOfWeek(year,week);
			
			savePo.set("MONTH_CODE",(firstDayOfWeek.getMonth()+1)+"");
			savePo.set("START_DATE",new Timestamp(firstDayOfWeek.getTime()).toString());
			
			//得到某年某周的最后一天  
			Date lastDayOfWeek = getLastDayOfWeek(year,week);
			savePo.set("END_DATE",new Timestamp(lastDayOfWeek.getTime()).toString());
		}
		savePo.set("CREATE_BY", loginInfo.getUserId());
		savePo.set("CREATE_DATE", new Date());
		savePo.saveIt();
	}
	
	public static Date getFirstDayOfWeek(int year, int week) {  
        Calendar c = Calendar.getInstance();  
        c.set(Calendar.YEAR, year);  
        c.set(Calendar.WEEK_OF_YEAR, week);  
        c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);//设置周一  
        c.setFirstDayOfWeek(Calendar.MONDAY);  
  
        return c.getTime();  
    }  
	
	public static Date getLastDayOfWeek(int year, int week) {  
        Calendar c = Calendar.getInstance();  
        c.set(Calendar.YEAR, year);  
        c.set(Calendar.WEEK_OF_YEAR, week+1);  
        c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY-1);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        return c.getTime();  
    }  
	
	
	
	
	
	//检查临时表数据
	@Override
	public List<TmpWeekDTO> checkData() {
		ArrayList<TmpWeekDTO> resultDTOList = new ArrayList<TmpWeekDTO>();
		ImportResultDto<TmpWeekDTO> importResult = new ImportResultDto<TmpWeekDTO>();
		String errorSb = "";
		//查询临时表的数据
		List<Map>   temList = findTmpList();
	    if(temList.size()>0){
		       for(Map row : temList){
		    	   
		    	   
		    	   if(StringUtils.isNullOrEmpty(row.get("YEAR_CODE"))){
					    TmpWeekDTO rowDto = new TmpWeekDTO();
					    rowDto.setRowNO(Integer.valueOf(row.get("ID").toString()));
					    rowDto.setErrorMsg("年份不能为空！");
					    resultDTOList.add(rowDto);				 
				   }else
					if(row.get("YEAR_CODE").toString().length()!=4){
						TmpWeekDTO rowDto = new TmpWeekDTO();
						rowDto.setRowNO(Integer.valueOf(row.get("ID").toString()));
					    rowDto.setErrorMsg("年份4个字节！");
						resultDTOList.add(rowDto);				 	
					}
				
				  if(StringUtils.isNullOrEmpty(row.get("WEEK_CODE"))){
					  TmpWeekDTO rowDto = new TmpWeekDTO();
						 rowDto.setRowNO(Integer.valueOf(row.get("ID").toString()));
						 rowDto.setErrorMsg("导入周不能为空!");
						 resultDTOList.add(rowDto);				 
						}
		    	   
		    	   if(row.get("WEEK_CODE")!=null){
		    		   
		    	  
		    	   
		    		String year =row.get("YEAR_CODE")+"";
		    		String month =row.get("MONTH_CODE")+"";
		    		int week = Integer.parseInt(row.get("WEEK_CODE")+"");
		    		
		    		
		    		Calendar c = Calendar.getInstance();
		    		c.set(Calendar.YEAR, Integer.parseInt(year)); 
		            c.set(Calendar.MONTH, Integer.parseInt(month)-1); 
		            int maxWeek = c.getActualMaximum(Calendar.WEEK_OF_MONTH);
		    		
		        	if(week>maxWeek || week<1){
		        		TmpWeekDTO rowDto = new TmpWeekDTO();
		   			 rowDto.setRowNO(Integer.valueOf(row.get("ID").toString()));
		   			 rowDto.setErrorMsg(year+"年"+month+"月第"+week+"周信息填写有误！");
		   			 resultDTOList.add(rowDto);				 
		    		} 
		        	
		    	   }
		        	
		  
				}
		
	    }
		
		return resultDTOList;
	}
	//查询临时表数据
	@Override
	public List<Map> findTmpList() {
		List<Map> list = workWeekManageDao.findWorkWeek();
		return list;
	}
	//导入业务表数据
	@Override
	public void saveAndDeleteData(Map<String, String> queryParam) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> list = workWeekManageDao.findWorkWeek();
		//将数据插入业务表
		for(Map map : list){
			TmWeekPO savePo = new TmWeekPO();
			savePo.set("YEAR_CODE", map.get("YEAR_CODE"));
			savePo.set("MONTH_CODE", map.get("MONTH_CODE"));
			savePo.set("WEEK_CODE", map.get("WEEK_CODE"));
			savePo.set("START_DATE", map.get("START_DATE"));
			savePo.set("END_DATE",map.get("END_DATE"));
			savePo.set("CREATE_BY", loginInfo.getUserId());
			savePo.set("CREATE_DATE", new Date());
			savePo.set("UPDATE_BY", loginInfo.getUserId());
			savePo.set("UPDATE_DATE", new Date());
			savePo.saveIt();
		}
		
	}
	
	
	
	
	
	
}
