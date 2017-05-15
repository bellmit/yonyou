package com.yonyou.dms.vehicle.service.afterSales.workWeek;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.Util.DateTimeUtil;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.afterSales.workWeek.ClaimWorkMonthLifeDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.workWeek.TmpWrClaimmonthDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.workWeek.TmpWrClaimmonthPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.workWeek.TtWrClaimmonthPO;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 索赔工作越维护
 * @author Administrator
 *
 */
@Service
public class ClaimWorkMonthLifeServiceImpl implements ClaimWorkMonthLifeService{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	ClaimWorkMonthLifeDao  claimWorkMonthLifeDao;
	//删除临时表数据
	@Override
	public void deleteTmpRecallVehicleDcs() {
		TmpWrClaimmonthPO trvdPo = new TmpWrClaimmonthPO();
		trvdPo.deleteAll();
	}
	//保存数据到临时表中
	@Override
	public void saveTmpRecallVehicleDcs(TmpWrClaimmonthDTO rowDto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		//保存
		TmpWrClaimmonthPO savePo = new TmpWrClaimmonthPO();
		//savePo.set("ID", );
		savePo.set("WORK_YEAR", rowDto.getWorkYear());
		savePo.set("WORK_NONTH", rowDto.getWorkNonth());
		savePo.set("WORK_WEEK", rowDto.getWorkWeek());
		savePo.set("START_DATE", rowDto.getStartDate());
		savePo.set("END_DATE",rowDto.getEndDate());
		savePo.set("CREATE_BY", loginInfo.getUserId());
		savePo.setTimestamp("CREATE_DATE", new Date(System.currentTimeMillis()));
		savePo.set("VER",rowDto.getRowNO());
		savePo.set("IS_DEL",0);
		savePo.saveIt();
	}
	@Override
	public List<TmpWrClaimmonthDTO> checkData() {
		ArrayList<TmpWrClaimmonthDTO> resultDTOList = new ArrayList<TmpWrClaimmonthDTO>();
		ImportResultDto<TmpWrClaimmonthDTO> importResult = new ImportResultDto<TmpWrClaimmonthDTO>();
		//查询临时表的数据
		List<Map>   temList = findTmpList();
		if(temList.size()>0){
			for(Map row : temList){
		if(StringUtils.isNullOrEmpty(row.get("WORK_YEAR"))){
			TmpWrClaimmonthDTO rowDto = new TmpWrClaimmonthDTO();
			 rowDto.setRowNO(Integer.valueOf(row.get("VER").toString()));
			 rowDto.setErrorMsg("年份不能为空！");
			 resultDTOList.add(rowDto);				 
			}else
			if(row.get("WORK_YEAR").toString().length()!=4){
				TmpWrClaimmonthDTO rowDto = new TmpWrClaimmonthDTO();
				rowDto.setRowNO(Integer.valueOf(row.get("VER").toString()));
			    rowDto.setErrorMsg("年份4个字节！");
				resultDTOList.add(rowDto);				 	
			}
			if(StringUtils.isNullOrEmpty(row.get("WORK_NONTH"))){
				TmpWrClaimmonthDTO rowDto = new TmpWrClaimmonthDTO();
				 rowDto.setRowNO(Integer.valueOf(row.get("VER").toString()));
				 rowDto.setErrorMsg("导入月份不能为空!");
				 resultDTOList.add(rowDto);				 
				}else
			
			if(row.get("WORK_NONTH").toString().length()>2){
				TmpWrClaimmonthDTO rowDto = new TmpWrClaimmonthDTO();
				rowDto.setRowNO(Integer.valueOf(row.get("VER").toString()));
			    rowDto.setErrorMsg(" 导入月份不能超过2个字节!");
				resultDTOList.add(rowDto);				 	
			}
			
             if(StringUtils.isNullOrEmpty(row.get("WORK_WEEK"))){
				TmpWrClaimmonthDTO rowDto = new TmpWrClaimmonthDTO();
				 rowDto.setRowNO(Integer.valueOf(row.get("VER").toString()));
				 rowDto.setErrorMsg("导入周不能为空!");
				 resultDTOList.add(rowDto);				 
				}else
			
			if(Integer.parseInt(row.get("WORK_WEEK").toString())<0 || Integer.parseInt(row.get("WORK_WEEK").toString())>5){
				TmpWrClaimmonthDTO rowDto = new TmpWrClaimmonthDTO();
				rowDto.setRowNO(Integer.valueOf(row.get("VER").toString()));
			    rowDto.setErrorMsg(" 1个月只能存1-4或者1-5这样的周次 ！");
				resultDTOList.add(rowDto);				 	
        	} else{
    			List<Object> queryParam = new ArrayList<Object>();
    			StringBuffer sql = new StringBuffer();
    			sql.append("SELECT * FROM TT_Wr_Claimmonth_dcs WHERE 1=1  AND Is_Del=0  AND Work_Nonth='").append(row.get("WORK_NONTH")).append("'  AND Work_Year='").append(row.get("WORK_YEAR")).append("'  AND Work_Week='").append(row.get("WORK_WEEK")).append("' \n");
    			System.out.println(sql);
    			List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
    			if (resultList != null && resultList.size() > 0) {
    				TmpWrClaimmonthDTO rowDto = new TmpWrClaimmonthDTO();
    				rowDto.setRowNO(Integer.valueOf(row.get("VER").toString()));
    				rowDto.setErrorMsg(" 该组别信息已经存在! ");
    				resultDTOList.add(rowDto);				 	
                                 }	
                             }
             try {
				if(DateTimeUtil.dateBetween(DateTimeUtil.increaseDays(DateTimeUtil.parseDateToDate(DateTimeUtil.stringToDateByPattern(row.get("START_DATE").toString(),"yy-MM-dd")), 6), DateTimeUtil.parseDateToDate(DateTimeUtil.stringToDateByPattern(row.get("END_DATE").toString(),"yy-MM-dd")))!=0){	
					TmpWrClaimmonthDTO rowDto = new TmpWrClaimmonthDTO();
					rowDto.setRowNO(Integer.valueOf(row.get("VER").toString()));
				    rowDto.setErrorMsg(" 开始时间与结束时间相差必须是7天！");
					resultDTOList.add(rowDto);				 	
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
             catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
         	if(row.get("VER").toString().equals("2")){
    			String endDate =getClaimmonthMaxEenDate();
    			if(!"".equals(endDate)){
    				double d;
					try {
						d = DateTimeUtil.dateBetween(DateTimeUtil.increaseDays(endDate, 1), DateTimeUtil.parseDateToDate(DateTimeUtil.stringToDateByPattern(row.get("START_DATE").toString(),"yy-MM-dd")));
						if(d!=0){
	    					TmpWrClaimmonthDTO rowDto = new TmpWrClaimmonthDTO();
	    					rowDto.setRowNO(Integer.valueOf(row.get("VER").toString()));
	    				    rowDto.setErrorMsg(" 导入开始日期+1天与上一次结束时间不匹配！");
	    					resultDTOList.add(rowDto);				 	
	    				}
					
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				
    			}
    		}
             
             
             
             
			          }
			}
		
		
		return resultDTOList;
	}
	
	/**
	 * 得到最大结束时间
	 * @return
	 */
	public String getClaimmonthMaxEenDate(){
		StringBuffer sql = new StringBuffer();
		sql.append("select max(end_date) end_date from tt_Wr_Claimmonth_dcs ");
		List<Map> map = OemDAOUtil.findAll(sql.toString(), null);
		if(null==map){
			return "";
		}else{
			return CommonUtils.checkNull(map.get(0).get("END_DATE"));
		}
	}
	
	
	//查询临时表里面的数据
			public List<Map> findTmpList() {
				List<Map> list = claimWorkMonthLifeDao.findclaimWorkMonthLife();
				return list;
		}
			
			
			//临时表数据导入业务表
			@Override
			public void saveAndDeleteData(Map<String, String> queryParam) throws ServiceBizException {
				LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				List<Map> list = claimWorkMonthLifeDao.findclaimWorkMonthLife();
				//将数据插入业务表
				for(Map map : list){
					TtWrClaimmonthPO savePo = new TtWrClaimmonthPO();
					savePo.set("WORK_YEAR", map.get("WORK_YEAR"));
					savePo.set("WORK_NONTH", map.get("WORK_NONTH"));
					savePo.set("WORK_WEEK", map.get("WORK_WEEK"));
					savePo.set("START_DATE", map.get("START_DATE"));
					savePo.set("END_DATE",map.get("END_DATE"));
					savePo.set("CREATE_BY", loginInfo.getUserId());
					savePo.setTimestamp("CREATE_DATE", new Date(System.currentTimeMillis()));
					savePo.set("VER",0);
					savePo.set("IS_DEL",0);
					savePo.saveIt();
				}
			}
}
