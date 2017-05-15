package com.yonyou.dms.vehicle.service.allot;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.Util.MailSender;
import com.yonyou.dms.common.domains.PO.basedata.TmAllotGrandPO;
import com.yonyou.dms.common.domains.PO.basedata.TmAllotGroupTempPO;
import com.yonyou.dms.common.domains.PO.basedata.TmAllotResourceLogPO;
import com.yonyou.dms.common.domains.PO.basedata.TmAllotResourceOrgbagPO;
import com.yonyou.dms.common.domains.PO.basedata.TmAllotResourcePO;
import com.yonyou.dms.common.domains.PO.basedata.TmAllotResourcePortPO;
import com.yonyou.dms.common.domains.PO.basedata.TmAllotResourceTotalPO;
import com.yonyou.dms.common.domains.PO.basedata.TmAllotSupportPO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TmLatelyMonthPO;
import com.yonyou.dms.common.domains.PO.basedata.TmResourcePortAllotPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TmpUploadResourcePO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.allot.ResourceAllotAreaBigDao;
import com.yonyou.dms.vehicle.dao.allot.ResourceAllotAreaSmallDao;
import com.yonyou.dms.vehicle.dao.allot.ResourceAllotPortDao;
import com.yonyou.dms.vehicle.domains.DTO.allot.ResourceAllotImport2Dto;
import com.yonyou.dms.vehicle.domains.DTO.allot.ResourceAllotImportDto;
import com.yonyou.dms.vehicle.domains.DTO.allot.ResourceAllotPortDto;
import com.yonyou.dms.vehicle.domains.PO.allot.TmAllotPortPO;

@SuppressWarnings("all")
@Service
public class ResourceAllotPortServiceImpl implements ResourceAllotPortService {
	private static final Logger logger = LoggerFactory.getLogger(ResourceAllotPortServiceImpl.class);
	@Autowired
	ResourceAllotPortDao dao;
	
	@Autowired
	ResourceAllotAreaSmallDao smallDao;
	
	@Autowired
	ResourceAllotAreaBigDao bigDao;

	@Override
	public List<Map> resourceallotportInt() {

		return dao.resourceallotportInt();
	}

	@Override
	public Map findById(Long id) {

		return dao.findById(id);
	}

	@Override
	public void editAllotPort(ResourceAllotPortDto dto)  throws Exception{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = formatter.format(currentTime);
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String vpcPort = CommonUtils.checkNull(dto.getVpcPort());
		String tmAllotPortId = CommonUtils.checkNull(dto.getTmAllotPortId());
		String portLevel = CommonUtils.checkNull(dto.getPortLevel());
		String returnValue = "";
		if ((vpcPort == null || vpcPort.equals("")) && (portLevel == null || portLevel.equals(""))) {
			returnValue = "";
		} else if (vpcPort == null || vpcPort.equals("")) {

			List<TmAllotPortPO> list1 = TmAllotPortPO.find(" PORT_LEVEL=?", portLevel);
			if (list1.size() > 0) {
				throw new ServiceBizException("港口级别已存在!");
			} else {
				returnValue = "";
			}
		} else {

			List<TmAllotPortPO> list = TmAllotPortPO.find("VPC_PORT=?", vpcPort);
			if (list.size() > 0) {
				throw new ServiceBizException("港口类型已存在!");
			}
			if (returnValue == "" || returnValue.equals("")) {
				List<TmAllotPortPO> list3 = TmAllotPortPO.find("PORT_LEVEL=?", portLevel);
				if (list3.size() > 0) {
					throw new ServiceBizException("港口级别已存在!");

				}
			}
		}
		TmAllotPortPO valuePO = TmAllotPortPO.findById(tmAllotPortId);
		valuePO.setInteger("VPC_PORT", vpcPort);
		valuePO.setInteger("PORT_LEVEL", portLevel);
		valuePO.setInteger("UPDATE_BY", loginInfo.getUserId());
		valuePO.setTimestamp("UPDATE_DATE", date);
		valuePO.saveIt();

	}

	// 新增
	@Override
	public void addResourceallotport(ResourceAllotPortDto dto) throws Exception{
		String vpcPort = CommonUtils.checkNull(dto.getVpcPort());
		String portLevel = CommonUtils.checkNull(dto.getPortLevel());
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = formatter.format(currentTime);
		String returnValue = "";

		if ((vpcPort == null || vpcPort.equals("")) && (portLevel == null || portLevel.equals(""))) {
			returnValue = "";
		} else if (vpcPort == null || vpcPort.equals("")) {

			List<TmAllotPortPO> list1 = TmAllotPortPO.find("PORT_LEVEL=?", portLevel);
			if (list1.size() > 0) {
				throw new ServiceBizException("港口级别已存在!");
			} else {
				returnValue = "";
			}
		} else {

			List<TmAllotPortPO> list = TmAllotPortPO.find("VPC_PORT=?", vpcPort);
			if (list.size() > 0) {
				throw new ServiceBizException("港口类型已存在!");
			}
			if (returnValue == "" || returnValue.equals("")) {
				List<TmAllotPortPO> list3 = TmAllotPortPO.find("PORT_LEVEL=?", portLevel);
				if (list3.size() > 0) {
					throw new ServiceBizException("港口级别已存在!");
					// returnValue="2";
				}
			}
		}

		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TmAllotPortPO valuePO = new TmAllotPortPO();
		valuePO.setInteger("VPC_PORT", vpcPort);
		valuePO.setInteger("PORT_LEVEL", portLevel);
		valuePO.setInteger("UPDATE_BY", loginInfo.getUserId());
		valuePO.setTimestamp("CREATE_DATE", date);
		valuePO.saveIt();
	}

	@Override
	public void delOrderRepeal(Long id) throws Exception{
		TmAllotPortPO.delete("TM_ALLOT_PORT_ID=?", id);
	}

	@Override
	public void insertTmp(ResourceAllotImportDto rowDto) throws Exception{
		TmpUploadResourcePO po = new TmpUploadResourcePO();
		setTmpPO(po,rowDto);
		boolean flag = po.saveIt();
	}
	
	/**
	 * 临时表对象映射
	 * @param po
	 * @param rowDto
	 */
	public void setTmpPO(TmpUploadResourcePO po ,ResourceAllotImportDto rowDto){
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TmVehiclePO tv = TmVehiclePO.findFirst(" VIN = ? ", rowDto.getVin());
		po.setInteger("ROW_NO",rowDto.getRowNO());
		po.setString("VIN", rowDto.getVin());
		po.setInteger("DEAL", 0);
		po.setInteger("ALLOT", 0);
		po.setLong("CREATE_BY", loginInfo.getUserId());
		po.setTimestamp("CREATE_DATE", new Date());
		po.setInteger("VPC_PORT", tv==null?0:tv.getInteger("VPC_PORT"));
		
		
	}
	
	/**
	 * 资源分配上传校验
	 */
	@Override
	public List<Map> checkData() {
		Set eSet = new HashSet();
		List<Map> errorList = new LinkedList<Map>(); 
		List<Map> rList = dao.findRepeatData();				
		if(rList.size()>0) {
			Set ts = new TreeSet();
			for(int i=0;i<rList.size();i++){
				ts.add(rList.get(i).get("VIN"));
			}			
			Iterator it = ts.iterator();
			while(it.hasNext()){
				String s = it.next().toString();
				Set set2 = new TreeSet();
				for(int i=0;i<rList.size();i++){
					if((rList.get(i).get("VIN")).equals(s)){
						set2.add(rList.get(i).get("ROW_NO"));
					}					
				}
				List l = new ArrayList<>(set2);	
				Map err = new HashMap<String,Object>();
				err.put("ROW_NO",Integer.parseInt(l.get(l.size()-1).toString())+1);
				l.remove(l.size()-1);
				err.put("ERROR_DESC","与第"+l.toString()+"行VIN重复!");
				errorList.add(err);
			}						
		}
		//1.判断是否在车辆表中存在
		List<Map> vehList = dao.findNoExistsVin();
		if(vehList.size()>0){		
			for(int i=0;i<vehList.size();i++){
				Map err = new HashMap<String,Object>();
				err.put("ROW_NO",Integer.parseInt(vehList.get(i).get("ROW_NO").toString())+1);
				err.put("ERROR_DESC","VIN号:"+vehList.get(i).get("VIN")+" 不存在");
				if(!eSet.contains(CommonUtils.checkNull(err.get("ROW_NO")))){
					errorList.add(err);
					eSet.add(CommonUtils.checkNull(err.get("ROW_NO")));
				}			
			}	
			return errorList;
		}
		//2.是否在车厂库存
		List<Map> istList = dao.checkVinIsStore();
		if(istList.size()>0){
			for(int i=0;i<istList.size();i++){
				Map err = new HashMap<String,Object>();
				err.put("ROW_NO",Integer.parseInt(istList.get(i).get("ROW_NO").toString())+1);
				err.put("ERROR_DESC","vin号:"+istList.get(i).get("VIN")+" 不在车厂库存");
				if(!eSet.contains(CommonUtils.checkNull(err.get("ROW_NO")))){
					errorList.add(err);
					eSet.add(CommonUtils.checkNull(err.get("ROW_NO")));
				}
			}	
		}
		//3.车辆在车厂库存，而且zbildate是否为空(在车厂库存且为空则报错)
		//4.判断该VIN是否锁定(资源备注)
		//5.是否生成有效订单
		List<Map> vinList = dao.findIsOrder();
		if(null!= vinList && vinList.size()>0){
			if(vinList.size()>0){
				for(int i=0;i<vinList.size();i++){
					Map<String,Object> map = vinList.get(i);
					Map err = new HashMap<String,Object>();
					err.put("ROW_NO",Integer.parseInt(vinList.get(i).get("ROW_NO").toString())+1);
					err.put("ERROR_DESC","vin:"+map.get("VIN")+" 已存在订单");
					if(!eSet.contains(CommonUtils.checkNull(err.get("ROW_NO")))){
						errorList.add(err);
						eSet.add(CommonUtils.checkNull(err.get("ROW_NO")));
					}
				}
			}		
		}
		//6.是否存在公共资源池中
		List<Map> comList = dao.findIsCommon();
		if(null!= comList && comList.size()>0){
			if(comList.size()>0){
				for(int i=0;i<comList.size();i++){
					Map<String,Object> map = comList.get(i);
					Map err = new HashMap<String,Object>();
					err.put("ROW_NO",Integer.parseInt(map.get("ROW_NO").toString())+1);
					err.put("ERROR_DESC","vin:"+map.get("VIN")+" 已存在公共资源");
					if(!eSet.contains(CommonUtils.checkNull(err.get("ROW_NO")))){
						errorList.add(err);
						eSet.add(CommonUtils.checkNull(err.get("ROW_NO")));
					}
				}
			}		
		}
		//7.月度目标是否存在
		List<Map> cList = dao.findMonthPlanNotExists();
		if(cList.size()>0){
			for(int i=0;i<cList.size();i++){
				Map<String,Object> map = cList.get(i);
				Map err = new HashMap<String,Object>();
				err.put("ROW_NO",Integer.parseInt(map.get("ROW_NO").toString())+1);
				err.put("ERROR_DESC","vin:"+map.get("VIN")+"的车系为"+map.get("GROUP_NAME")+" 不存在月度任务");
				if(!eSet.contains(CommonUtils.checkNull(err.get("ROW_NO")))){
					errorList.add(err);
					eSet.add(CommonUtils.checkNull(err.get("ROW_NO")));
				}
			}
		}
		
		//7.月度目标是否为0
		List<Map> cList2 = dao.findMonthPlanNotExists2();
		if(cList2.size()>0){
			for(int i=0;i<cList2.size();i++){
				Map<String,Object> map = cList2.get(i);
				Map err = new HashMap<String,Object>();
				err.put("ROW_NO",Integer.parseInt(map.get("ROW_NO").toString())+1);
				err.put("ERROR_DESC","vin:"+map.get("VIN")+"的车系是"+map.get("GROUP_NAME")+" 月度任务为0");
				if(!eSet.contains(CommonUtils.checkNull(err.get("ROW_NO")))){
					errorList.add(err);
					eSet.add(CommonUtils.checkNull(err.get("ROW_NO")));
				}
			}
		}
		//8.判断是否在资源上传临时表
		List<Map> cmList = dao.findExistsCommon();
		if(cmList.size()>0){
			for(int i=0;i<cmList.size();i++){
				Map err = new HashMap<String,Object>();
				err.put("ROW_NO",Integer.parseInt(cmList.get(i).get("ROW_NO").toString())+1);
				err.put("ERROR_DESC","VIN："+cmList.get(i).get("VIN")+" 在【资源上传】中待分配");
				errorList.add(err);				
			}		
			return errorList;
		}	
		//9.港口是否为空
		List<Map> vpcPortList = dao.findVpcPortIsNull();
		if(vpcPortList.size()>0){
			for(int i=0;i<vpcPortList.size();i++){
				Map<String,Object> map = vpcPortList.get(i);
				Map err = new HashMap<String,Object>();
				err.put("ROW_NO",Integer.parseInt(map.get("ROW_NO").toString())+1);
				err.put("ERROR_DESC","vin:"+map.get("VIN")+"没有港口信息，请核实！");
				if(!eSet.contains(CommonUtils.checkNull(err.get("ROW_NO")))){
					errorList.add(err);
					eSet.add(CommonUtils.checkNull(err.get("ROW_NO")));
				}
			}
		}
		//10.港口是否维护了相应区域
		List<Map> vpcPortList1 = dao.getVpcPort();
		if(vpcPortList1.size()>0){
			for(int i=0;i<vpcPortList1.size();i++){
				Map<String,Object> map = vpcPortList1.get(i);
				Map err = new HashMap<String,Object>();
				List<Map> vpcPortOrgList =new ArrayList<Map>();;
				if(map.get("VPC_PORT")!=null){
					vpcPortOrgList =dao.getVpcPortOrgId(CommonUtils.checkNull(map.get("VPC_PORT")));
				}
				if(vpcPortOrgList.size()==0){
					if(map.get("VPC_PORT")!=null){
						List<Map> vpcPortDescList=dao.getVpcPortDesc(CommonUtils.checkNull(map.get("VPC_PORT")));
						if(vpcPortDescList.size()>0){
							for (int j = 0; j < vpcPortDescList.size(); j++) {
								Map<String,Object> bean= vpcPortDescList.get(j);
								err.put("ERROR_DESC",bean.get("CODE_DESC")+"港未定义分配大区，请核实！");	
								errorList.add(err);
							}
							
						}
			
					}
				}			
			}
		}
		//11.判断是否是进口车
		List<Map> importList = dao.findIsNotImport();
		if(null!=importList && importList.size()>0){
			for (int i = 0; i < importList.size(); i++) {
				Map err = new HashMap<String,Object>();
				Map map = importList.get(i);
				err.put("ROW_NO",Integer.parseInt(map.get("ROW_NO").toString())+1);
				err.put("ERROR_DESC","VIN号:"+map.get("VIN")+" 不是进口车!");
				if(!eSet.contains(CommonUtils.checkNull(err.get("ROW_NO")))){
					errorList.add(err);
					eSet.add(CommonUtils.checkNull(err.get("ROW_NO")));
				}
			}
		}	
//		Collections.sort((errorList);排序
		return errorList;
	}
	
	/**
	 * 
	 */
	@Override
	public List<Map> findTime() {
		return dao.findTime();
	}

	@Override
	public void update(String id) throws Exception{
		dao.update(id);
	}

	@Override
	public void updateStatus() throws Exception{
		dao.updateStatus();
	}

	@Override
	public List<Map> findOrderAllot() {
		return dao.findOrderAllot();
	}

	@Override
	public void updateDeal() throws Exception{
		dao.updateDeal();
	}
	
	/**
	 * 月初结转存储过程转代码
	 */
	@Override
	public void callProcedureMonth(List<Object> inParameter) throws Exception{
		Date nowDate = new Date();
		Calendar c = Calendar.getInstance();
		Integer year = c.get(Calendar.YEAR);
		Integer month = c.get(Calendar.MONTH)+1;
		Integer day = c.get(Calendar.DAY_OF_MONTH)+1;
		
		
		String month_day_start = year+"-"+month+"-01";
		String month_day_end = year+"-"+(month+1)+"-01";
		if(month==12){
			month_day_end = (year+1)+"-01-01";
		}
		if(month==1){
			month = 12;
			year = year - 1;
		}else{
			month = month-1;
		}
		String lately_month_day_start = year+"-"+month+"-01";
		String lately_month_day_end = year+"-"+(month+1)+"-01";
		
		List<Map> list = dao.callProcedureMonth(month_day_start,month_day_end,lately_month_day_start,lately_month_day_end);
		if(null!=list && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Map map = list.get(i);
				TmLatelyMonthPO po = new TmLatelyMonthPO();
				po.setLong("DEALER_ID",Long.parseLong("".equals(CommonUtils.checkNull(map.get("DEALER_ID")))?"0":CommonUtils.checkNull(map.get("DEALER_ID"))));
				po.setString("VIN", CommonUtils.checkNull(map.get("VIN")));
				po.setInteger("R_TYPE", OemDictCodeConstants.DUTY_TYPE_DEALER);
				po.setString("ALLOT_DATE", inParameter.get(1).toString());
				po.setLong("CREATE_BY", Long.parseLong(inParameter.get(0).toString()));
				po.setTimestamp("CREATE_DATE", nowDate);
				po.saveIt();
			}
		}
		TmAllotResourceLogPO logPO = new TmAllotResourceLogPO();
		logPO.setString("PRO_NAME", "MONLY_LATELY_ORDER");
		logPO.setInteger("ALLOT_TYPE", 123);
		logPO.setInteger("STATUS", 1);
		logPO.setString("ERROR_INFO", "");
		logPO.setLong("CREATE_BY", Long.parseLong(inParameter.get(0).toString()));
		logPO.setTimestamp("CREATE_DATE", nowDate);
		logPO.saveIt();
	}
	
	/**
	 * 资源分配大区存储过程转代码
	 */
	@Override
	public void callProcedureInfo(List<Object> inParameter) throws Exception{
		Date nowDate = new Date();
		Long[] org_id_array = {};
		double[] v_color_array = {};
		double[] r_color_array = {};
		double[] group_id_array = {};
		int temp = 0;
		int temp_num = 0;
		Long temp_arr = 0L;
		double temp_arr2 = 0;
		double temp_arr3 = 0;
		int m = 0;
		int gap_total = 0;
		int temp_gap_total = 0;
		int org_count = 0;
		int temp_count = 0;
		int o_count = 0;
		int temp_num1 = 0;
		int gap_org = 0;
		int gap_temp = 0;
		int over_gap_num = 0;
		int target_num = 0;
		int gap_temp_flag = 0;
		int t_temp_num = 0;
		int loop_count = 0;
		int playVer = 0;
		int total_gap_num = 0;
		int total_allot_num = 0;
		long s_series_id = 0L;
		String s_series_code = "";
		int s_num = 0;
		int s36_flag = 0;
		String SQLSTATE = "00000";
		long org_bag_id = 0L;
		List<Map> mycursor = dao.getMycursor();
		List<Map> mycursor1 = dao.getMycursor1();
		
		Calendar c = Calendar.getInstance();
		Integer year = c.get(Calendar.YEAR);
		Integer month = c.get(Calendar.MONTH)+1;
		Integer day = c.get(Calendar.DAY_OF_MONTH)+1;
		
		String month_day_start = year+"-"+month+"-01";
		String month_day_end = year+"-"+(month+1)+"-01";
		
		if(month==12){
			month_day_end = (year+1)+"-01-01";
		}
		//获取最大月计划任务版本
		playVer = dao.getMaxPalyVer(year,month);
		Long userId = Long.parseLong(inParameter.get(0).toString());
		String allotDate = inParameter.get(1).toString();
		//游标1循环
		for (int i = 0; i < mycursor.size(); i++) {
			Map map = mycursor.get(i);
			s_series_id = Long.parseLong(CommonUtils.checkNull(map.get("GROUP_ID")));
			dao.save(month_day_start,month_day_end,year,month,s_series_id,inParameter,playVer);
		}
		//游标2循环
		for (int i = 0; i < mycursor1.size(); i++) {
			Map map = mycursor1.get(i);
			s_series_id = Long.parseLong(CommonUtils.checkNull(map.get("GROUP_ID")));
			
			dao.exceBatch(s_series_id,userId,allotDate);
		}
		//tmp  1转
		List<Map> temp1List = dao.getTempList1(userId,allotDate);
		for(Map map : temp1List){
			Integer SALE_AMOUNT = Integer.parseInt(CommonUtils.checkNull(map.get("SALE_AMOUNT"),"0"));
			Long TARGET_ID = Long.parseLong(CommonUtils.checkNull(map.get("TARGET_ID"),"0"));
			dao.exceBatchTemp1(SALE_AMOUNT,TARGET_ID,userId,allotDate);
		}
		
		//tmp  2转
		List<Map> temp2List = dao.getTempList2(userId,allotDate);
		for(Map map : temp1List){
			Integer SALE_AMOUNT = Integer.parseInt(CommonUtils.checkNull(map.get("SALE_AMOUNT"),"0"));
			Long TARGET_ID = Long.parseLong(CommonUtils.checkNull(map.get("TARGET_ID"),"0"));
			dao.exceBatchTemp2(map,userId,allotDate);
		}
		//
		List<Map> flagList = dao.checkList();
		if(null!=flagList && flagList.size()>0){
			s36_flag = 1;
		}
		if(0==s36_flag){
			List<Map> list = dao.getFlagList(userId,allotDate);
			for(Map map : temp1List){
				Integer SALE_AMOUNT = Integer.parseInt(CommonUtils.checkNull(map.get("SALE_AMOUNT"),"0"));
				Long TARGET_ID = Long.parseLong(CommonUtils.checkNull(map.get("TARGET_ID"),"0"));
				dao.exceBatchTemp3(map,userId,allotDate);
			}
		}
		
		//tmp  3转
		List<Map> temp3List = dao.getTempList3(userId,allotDate);
		for (Map map : temp3List) {
			dao.exceBatchTemp4(map,userId,allotDate);
		}
		
		dao.exceBatchTemp5(allotDate);
		
		//tmp  4转
		List<Map> temp4List = dao.getTempList4(userId,allotDate);
		for (Map map : temp3List) {
			dao.exceBatchTemp6(map,userId,allotDate);
		}
		
		//游标1 2次循环
		for(Map map:mycursor){
			s_series_id = Long.parseLong(CommonUtils.checkNull(map.get("GROUP_ID")));
			s_series_code = CommonUtils.checkNull(map.get("GROUP_CODE"));
			s_num = Integer.parseInt(CommonUtils.checkNull(map.get("NUM")));
			
			//获取VPC
			List<Map> listVpc = dao.getListVpc();
			if(null!= listVpc && listVpc.size()>0){
				for(Map vpcMap : listVpc){
					Integer VPC_PORT = Integer.parseInt(CommonUtils.checkNull(vpcMap.get("VPC_PORT")));
					List<Map> listMap = dao.getTempList5(s_series_code,VPC_PORT);
					for (Map map2 : listMap) {
						int num = Integer.parseInt(CommonUtils.checkNull(map2.get("NUM"),"0"));
						Long groupId = Long.parseLong(CommonUtils.checkNull(map2.get("GROUP_ID"),"0"));
						String colorName = CommonUtils.checkNull(map2.get("COLOR_NAME"));
						over_gap_num = 0;
						gap_temp_flag = 1;
						temp_count = dao.getTempCount(VPC_PORT);
						loop_count = 0 ;
						List<Map> listMap2 = dao.getTempList6(VPC_PORT);
						for (Map map3 : listMap2) {
							Integer org_level = Integer.parseInt(CommonUtils.checkNull(map3.get("ORG_LEVEL")));
							temp_num = 0;
							gap_total = 0;
							gap_temp = 0;
							loop_count = loop_count + 1;
							gap_temp_flag = 1;
							if(loop_count > 1 || loop_count == 1){
								o_count = dao.getOCount(VPC_PORT,org_level);
								List<Map> listMap3 = dao.getTempList7(VPC_PORT,org_level);
								//1次循环
								for (Map map4 : listMap3) {
									Long org_id = Long.parseLong(CommonUtils.checkNull(map4.get("ORG_ID"),"0"));
									org_count = dao.getOrgCount(s_series_id,userId,allotDate,org_id);
									if(org_count!=0){
										temp_gap_total = dao.getGAP(s_series_id,userId,allotDate,org_id);
										target_num = dao.getTargetNum(s_series_id,userId,allotDate,org_id);
										if(target_num < temp_gap_total){
											gap_total = gap_total + temp_gap_total - target_num;
										}
									}
								}
								//2次循环
								v_color_array = new double[listMap3.size()+1];
								r_color_array = new double[listMap3.size()+1];
								group_id_array = new double[listMap3.size()+1];
								for (Map map4 : listMap3) {
									gap_temp_flag = 1;
									Long org_id = Long.parseLong(CommonUtils.checkNull(map4.get("ORG_ID"),"0"));
									org_count = dao.getOrgCount(s_series_id,userId,allotDate,org_id);
									if(org_count!=0){
										gap_org = dao.getGAP(s_series_id,userId,allotDate,org_id);
										target_num = dao.getTargetNum(s_series_id,userId,allotDate,org_id);
										if(target_num == gap_org ||target_num > gap_org){
											gap_org = 0;
										}else{
											gap_org = gap_org - target_num;
										}
										temp_num=temp_num+1;
										if(gap_total==0){
											v_color_array[temp_num] = 0;
											r_color_array[temp_num] = 0;
											group_id_array[temp_num] = org_id;
											gap_temp = 0; 
											gap_temp_flag = 0;
										}
										if( gap_total>0 ){
											if(gap_org == 0){
												v_color_array[temp_num] = 0;
												r_color_array[temp_num] = 0;
											}else if(loop_count == 1){
												v_color_array[temp_num] = gap_org*num/gap_total;
												r_color_array[temp_num] = gap_org*num/gap_total-v_color_array[temp_num];
											}else{
												v_color_array[temp_num] = gap_org*over_gap_num/gap_total;
												r_color_array[temp_num] = gap_org*over_gap_num/gap_total-v_color_array[temp_num];
											}
										}
										group_id_array[temp_num] = org_id;
										gap_temp = gap_temp+(int) v_color_array[temp_num];  
									}
								}
								temp = 0;
								while(temp<temp_num){
									temp = temp+1;  
									m = 0; 
									while(m<temp_num-1){
										m = m+1;
										if(r_color_array[m]<r_color_array[m+1]){
											temp_arr = (long) r_color_array[m+1];
											r_color_array[m+1] = r_color_array[m];
											r_color_array[m] = temp_arr;
											temp_arr2 = v_color_array[m+1];
											v_color_array[m+1] = v_color_array[m];
											v_color_array[m] = temp_arr2;
											temp_arr3 = group_id_array[m+1];
											group_id_array[m+1] = group_id_array[m];
											group_id_array[m] = temp_arr3;
										}
									}
								}
								
								temp = 0;
								if(gap_temp_flag>0){
									if(loop_count == 1){
										gap_temp = num-gap_temp;
									}else{
										gap_temp = over_gap_num-gap_temp;
									}
									if(gap_temp >0 ){
										while (temp<gap_temp){
											temp = temp+1; 
											v_color_array[temp] = v_color_array[temp]+1;
										}
										while (temp<temp_num){
											temp = temp+1; 
										}
									}
								}
								
								temp = 0;
								while (temp<temp_num){
									temp = temp+1;  
									List<Map> listMap5 = dao.getTempList8(userId,allotDate,s_series_id,group_id_array[temp]);
									if(null!= listMap5 && listMap5.size()>0 ){
										total_gap_num = Integer.parseInt(CommonUtils.checkNull(listMap5.get(0).get("GAP"),"0"));
										total_allot_num = Integer.parseInt(CommonUtils.checkNull(listMap5.get(0).get("TOTAL_ALLOT_NUM"),"0"));
									}else{
										total_gap_num = 0;
										total_allot_num = 0;
									}
									
									
									if(temp_count == 1){
										if(gap_temp_flag == 0){
											TmAllotGroupTempPO po = new TmAllotGroupTempPO();
											po.setInteger("ALLOT_TYPE", 10431003);
											po.setLong("TARGET_ID", group_id_array[temp]);
											po.setLong("SERIES_ID", s_series_id);
											po.setLong("GROUP_ID", groupId);
											po.setString("COLOR_NAME", colorName);
											po.setInteger("NUM", num);
											po.setString("ALLOT_DATE", allotDate);
											po.setLong("CREATE_BY", userId);
											po.setTimestamp("CREATE_DATE", nowDate);
											po.setInteger("VPC_PORT", vpcMap);
											po.saveIt();
											temp = temp_num;
										}
										if (total_allot_num+v_color_array[temp] > total_gap_num ) {
											t_temp_num = (int) v_color_array[temp];
											v_color_array[temp] = total_gap_num-total_allot_num;
											t_temp_num = t_temp_num-(total_gap_num-total_allot_num);   
											TmAllotGroupTempPO po = new TmAllotGroupTempPO();
											po.setInteger("ALLOT_TYPE", 10431003);
											po.setLong("TARGET_ID", group_id_array[temp]);
											po.setLong("SERIES_ID", s_series_id);
											po.setLong("GROUP_ID", groupId);
											po.setString("COLOR_NAME", colorName);
											po.setString("NUM", num);
											po.setString("ALLOT_DATE", allotDate);
											po.setLong("CREATE_BY", userId);
											po.setTimestamp("CREATE_DATE", nowDate);
											po.setInteger("VPC_PORT", VPC_PORT);
											po.saveIt();
										}
										TmAllotResourcePortPO portPO = new TmAllotResourcePortPO();
										portPO.setLong("ORG_ID", group_id_array[temp]);
										portPO.setLong("TM_PORT_ID", VPC_PORT);
										portPO.setInteger("TARGET_NUM", (int) v_color_array[temp]);
										portPO.setInteger("HAS_NUM", 0);
										portPO.setString("ALLOT_DATE", allotDate);
										portPO.setLong("CREATE_BY", userId);
										portPO.setTimestamp("CREATE_DATE", nowDate);
										portPO.setLong("GROUP_ID", groupId);
										portPO.setString("COLOR_NAME",colorName);
										portPO.setLong("SERIES_ID",s_series_id);
										portPO.saveIt();
									}else{
										if(total_allot_num+v_color_array[temp] > total_gap_num){
											over_gap_num = (int)(total_allot_num+v_color_array[temp] - total_gap_num+over_gap_num);
											t_temp_num = total_gap_num-total_allot_num; 
										}else{
											t_temp_num = (int) v_color_array[temp]; 
											if(v_color_array[temp] == 0 && over_gap_num==0 && gap_total ==0){
												over_gap_num = num;
												t_temp_num = 0;
											}
										}
										TmAllotResourcePortPO portPO = new TmAllotResourcePortPO();
										portPO.setLong("ORG_ID", group_id_array[temp]);
										portPO.setLong("TM_PORT_ID", VPC_PORT);
										portPO.setInteger("TARGET_NUM", t_temp_num);
										portPO.setInteger("HAS_NUM", 0);
										portPO.setString("ALLOT_DATE", allotDate);
										portPO.setLong("CREATE_BY", userId);
										portPO.setTimestamp("CREATE_DATE", nowDate);
										portPO.setLong("GROUP_ID", groupId);
										portPO.setString("COLOR_NAME",colorName);
										portPO.setLong("SERIES_ID",s_series_id);
										portPO.saveIt();
										o_count = o_count - 1;
										if( o_count == 0){
											temp_count = temp_count - 1;
										}
									}
								}
							}
						}
					}
					List<Map> temp9 = dao.getTempList9(s_series_code);
					for (Map map2 : temp9) {
						Long groupId = Long.parseLong(CommonUtils.checkNull(map2.get("GROUP_ID"),"0"));
						String colorName = CommonUtils.checkNull(map2.get("COLOR_NAME"));
						List<Map> temp10 = dao.getTempList10(VPC_PORT);
						for (Map map3 : temp10) {
							Long orgId = Long.parseLong(CommonUtils.checkNull(map3.get("ORG_ID"),"0"));
							temp_arr = dao.getPortId(orgId,VPC_PORT,allotDate,userId,nowDate,groupId,colorName);
							if(temp_arr == 0){
								TmAllotResourcePortPO portPO = new TmAllotResourcePortPO();
								portPO.setLong("ORG_ID", orgId);
								portPO.setLong("TM_PORT_ID", VPC_PORT);
								portPO.setInteger("TARGET_NUM", 0);
								portPO.setInteger("HAS_NUM", 0);
								portPO.setString("ALLOT_DATE", allotDate);
								portPO.setLong("CREATE_BY", userId);
								portPO.setTimestamp("CREATE_DATE", nowDate);
								portPO.setLong("GROUP_ID", groupId);
								portPO.setString("COLOR_NAME",colorName);
								portPO.setLong("SERIES_ID",s_series_id);
								portPO.saveIt();
							}
						}
					}
				}
			}
		}
		List<Map> temp11 = dao.getTmppList11(userId, allotDate);
		for (Map map : temp11) {
			Integer VPC_PORT = Integer.parseInt(CommonUtils.checkNull(map.get("VPC_PORT"),"0"));
			Integer num = Integer.parseInt(CommonUtils.checkNull(map.get("NUM"),"0"));
			Long groupId = Long.parseLong(CommonUtils.checkNull(map.get("SERIES_ID"),"0"));
			String colorName = CommonUtils.checkNull(map.get("colorName"));
			gap_temp = 0;
			gap_total = 0;
			temp_num = 0;
			List<Map> temp10 = dao.getTempList10(VPC_PORT);
			for (Map map2 : temp10) {
				Long orgId = Long.parseLong(CommonUtils.checkNull(map2.get("ORG_ID"),"0"));
				gap_org = dao.getGAP2(userId,allotDate,groupId,orgId);
				gap_total = gap_total + gap_org;
			}
			for (Map map3 : temp10) {
				Long orgId = Long.parseLong(CommonUtils.checkNull(map3.get("ORG_ID"),"0"));
				List<Map> temp12 = dao.getTempList12(userId,allotDate,groupId,orgId);
				if(temp_num==0){
					v_color_array = new double[(temp12.size()*temp10.size())];
					r_color_array = new double[(temp12.size()*temp10.size())];
					group_id_array = new double[(temp12.size()*temp10.size())];
				}
				for (Map map4 : temp12) {
					  Integer gap = Integer.parseInt(CommonUtils.checkNull(map3.get("GAP"),"0"));
					  temp_num=temp_num+1;
	                  v_color_array[temp_num] = gap*num/gap_total;
	                  r_color_array[temp_num] = gap*num /gap_total-v_color_array[temp_num];
	                  group_id_array[temp_num] = orgId;
	                  gap_temp =(int) (gap_temp+v_color_array[temp_num]); 
				}
			}
			temp = 0;
			 while (temp<temp_num) {
				 temp = temp+1;  
				 m = 0;   
				 while(m<temp_num-1){
					 m = m+1;
					 if (r_color_array[m]<r_color_array[m+1]){
						 temp_arr = (long) r_color_array[m+1];
						 r_color_array[m+1] = r_color_array[m];
						 r_color_array[m] = temp_arr;
						 temp_arr2 = v_color_array[m+1];
						 v_color_array[m+1] = v_color_array[m];
						 v_color_array[m] = temp_arr2;
						 temp_arr3 = group_id_array[m+1];
						 group_id_array[m+1] = group_id_array[m];
						 group_id_array[m] = temp_arr3;
					 }
				 }
			 }
			 temp = 0;
			 if(num-gap_temp>0){
				 while (temp<num-gap_temp){
					 temp = temp+1; 
					 v_color_array[temp] = v_color_array[temp]+1; 
				 }
				 temp = 0;
				 while (temp<temp_num) {
					 temp = temp+1; 
				 }
			 }
			 temp = 0;
			 while (temp<temp_num){
				 temp = temp+1; 
				 temp_arr = 0L;     
				 temp_arr = dao.getTempList13(group_id_array[temp],VPC_PORT,groupId,colorName,userId,allotDate);
				 if(temp_arr>0){
					 dao.exceBatchTemp7((long)v_color_array[temp],group_id_array[temp],VPC_PORT,groupId,colorName,userId,allotDate);
				 }
				 if(temp_arr==0 ){
					 TmAllotResourcePortPO portPO = new TmAllotResourcePortPO();
						portPO.setLong("ORG_ID", (long) group_id_array[temp]);
						portPO.setLong("TM_PORT_ID", VPC_PORT);
						portPO.setInteger("TARGET_NUM", v_color_array[temp]);
						portPO.setInteger("HAS_NUM", 0);
						portPO.setString("ALLOT_DATE", allotDate);
						portPO.setLong("CREATE_BY", userId);
						portPO.setTimestamp("CREATE_DATE", nowDate);
						portPO.setLong("GROUP_ID", groupId);
						portPO.setString("COLOR_NAME",colorName);
						portPO.setLong("SERIES_ID",s_series_id);
						portPO.saveIt();
				 }
			 }
		}
		List<Map> temp14 = dao.getTempList14(allotDate);
		for (Map map : temp14) {
			Long orgId =  Long.parseLong(CommonUtils.checkNull(map.get("ORG_ID"),"0"));
			Long groupId =  Long.parseLong(CommonUtils.checkNull(map.get("GROUP_ID"),"0"));
			String colorName =  CommonUtils.checkNull(map.get("COLOR_NAME"),"0");
			Integer targetNum = Integer.parseInt(CommonUtils.checkNull(map.get("TARGET_NUM"),"0"));
			TmAllotResourcePO po = new TmAllotResourcePO();
			po.setInteger("ALLOT_TYPE",10431003);
			po.setLong("ALLOT_TARGET_ID",orgId);
			po.setLong("GROUP_ID",groupId);
			po.setString("COLOR_NAME",colorName);
			po.setInteger("ALLOT_NUM",targetNum);
			po.setInteger("ADJUST_NUM",targetNum);
			po.setInteger("ALLOT_STATUS",0);
			po.setInteger("AUDIT_TYPE",12302);
			po.setInteger("AUDIT_STATUS",0);
			po.setInteger("IS_ORDER",0);
			po.setString("ALLOT_DATE",allotDate);
			po.setLong("CREATE_BY",userId);
			po.setTimestamp("CREATE_DATE", nowDate);
			po.saveIt();
		}
		List<Map> temp15 = dao.getTempList15(allotDate);
		for (Map map : temp15) {
			Long orgId =  Long.parseLong(CommonUtils.checkNull(map.get("ORG_ID"),"0"));
			Integer VPC_PORT = Integer.parseInt(CommonUtils.checkNull(map.get("TM_PORT_ID"),"0"));
			Integer targetNum = Integer.parseInt(CommonUtils.checkNull(map.get("TARGET_NUM"),"0"));
			TmResourcePortAllotPO po = new TmResourcePortAllotPO();
			po.setInteger("VPC_PORT",VPC_PORT);
			po.setLong("ORG_ID",orgId);
			po.setInteger("ALLOT_NUM",targetNum);
			po.setInteger("HAS_NUM",0);
			po.setString("ALLOT_DATE",allotDate);
			po.setLong("CREATE_BY",userId);
			po.setTimestamp("CREATE_DATE", nowDate);
			po.saveIt();
		}
		List<Map> temp16 = dao.getTempList16(allotDate);
		for (Map map : temp16) {
			Long allotNum =  Long.parseLong(CommonUtils.checkNull(map.get("ALLOT_NUM"),"0"));
			Integer VPC_PORT = Integer.parseInt(CommonUtils.checkNull(map.get("TM_PORT_ID"),"0"));
			Integer orgLevel = Integer.parseInt(CommonUtils.checkNull(map.get("ORG_LEVEL"),"0"));
			TmAllotResourceTotalPO po = new TmAllotResourceTotalPO();
			po.setInteger("ORG_LEVEL",orgLevel);
			po.setLong("VPC_PORT",VPC_PORT);
			po.setInteger("ALLOT_NUM",allotNum);
			po.setInteger("HAS_NUM",0);
			po.setString("ALLOT_DATE",allotDate);
			po.setLong("CREATE_BY",userId);
			po.setTimestamp("CREATE_DATE", nowDate);
			po.saveIt();
			List<Map> temp17 = dao.getTempList17(allotDate,VPC_PORT,orgLevel);
			for (Map map2 : temp17) {
				TmAllotResourceOrgbagPO orgbagPO = new TmAllotResourceOrgbagPO();
				orgbagPO.setLong("TOTAL_ID", po.getLongId());
				orgbagPO.setLong("ORG_ID", Long.parseLong(CommonUtils.checkNull(map2.get("ORG_ID"),"0")));
				orgbagPO.setInteger("ORG_LEVEL", Integer.parseInt(CommonUtils.checkNull(map2.get("ORG_LEVEL"),"0")));
				orgbagPO.setInteger("VPC_PORT", Integer.parseInt(CommonUtils.checkNull(map2.get("TM_PORT_ID"),"0")));
				orgbagPO.setInteger("ALLOT_NUM", Integer.parseInt(CommonUtils.checkNull(map2.get("ALLOT_NUM"),"0")));
				orgbagPO.setInteger("HAS_NUM",0);
				orgbagPO.setString("ALLOT_DATE",allotDate);
				orgbagPO.setLong("CREATE_BY",userId);
				orgbagPO.setTimestamp("CREATE_DATE", nowDate);
				orgbagPO.saveIt();
			}
		}
		
		TmAllotResourceLogPO logPO = new TmAllotResourceLogPO();
		logPO.setString("PRO_NAME", "AREA_TOTAL_GAP_INFO");
		logPO.setInteger("ALLOT_TYPE", 10431003);
		logPO.setInteger("STATUS", 1);
		logPO.setString("ERROR_INFO", "");
		logPO.setLong("CREATE_BY", Long.parseLong(inParameter.get(0).toString()));
		logPO.setTimestamp("CREATE_DATE", nowDate);
		logPO.saveIt();
	}
	
	/**
	 * 校验二
	 * @return
	 */
	@Override
	public List<Map> checkData2() {
		List<Map> errorList = new LinkedList<Map>(); 
		List<Map> rList = dao.findRepeatData2();				
		if(rList.size()>0) {
			Set ts = new TreeSet();
			for(int i=0;i<rList.size();i++){
				ts.add(rList.get(i).get("VIN")+"_"+rList.get(i).get("DEALER_CODE"));
			}			
			Iterator it = ts.iterator();
			while(it.hasNext()){
				String s = it.next().toString();
				Set set2 = new TreeSet();
				for(int i=0;i<rList.size();i++){
					if((rList.get(i).get("VIN")+"_"+rList.get(i).get("DEALER_CODE")).equals(s)){
						set2.add(rList.get(i).get("ROW_NO"));
					}					
				}
				List l = new ArrayList(set2);	
				Map err = new HashMap();
				err.put("ROW_NO",Integer.parseInt(l.get(l.size()-1).toString())+1);
				l.remove(l.size()-1);
				err.put("ERROR_DESC","与第"+l.toString()+"行数据重复!");
				errorList.add(err);
			}					
			return errorList;
		}
		//1.判断是否在车辆表中存在
		List<Map> vehList = dao.findNoExistsVin2();
		if(vehList.size()>0){		
			for(int i=0;i<vehList.size();i++){
				Map err = new HashMap();
				err.put("ROW_NO",Integer.parseInt(vehList.get(i).get("ROW_NO").toString())+1);
				err.put("ERROR_DESC","VIN:"+vehList.get(i).get("VIN")+" 不存在");
				errorList.add(err);
									
			}	
			return errorList;
		}
		//2.判断资源范围是否存在
		List<Map> isResList = dao.findNoExistsResource2();
		if(isResList.size()>0){
			for(int i=0;i<isResList.size();i++){
				Map err = new HashMap();
				err.put("ROW_NO",Integer.parseInt(isResList.get(i).get("ROW_NO").toString())+1);
				err.put("ERROR_DESC","资源范围:"+isResList.get(i).get("DEALER_CODE")+" 不存在");
				errorList.add(err);				
			}		
			return errorList;
		}			
		return errorList;
	}
	
	/**
	 * 不是本周的导入
	 */
	@Override
	public void insertTmp2(ResourceAllotImport2Dto rowDto) throws Exception{
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Integer rType = 0;
		List<TmDealerPO> tdList = TmDealerPO.find(" DEALER_CODE = ? ", rowDto.getDealerCode());
		if(tdList.size()>0){
			rType = OemDictCodeConstants.DUTY_TYPE_DEALER;
		}else{
			List<Map> oList = dao.findOrg(rowDto.getDealerCode());
			if(oList.size()>0){
				if(CommonUtils.checkNull(oList.get(0).get("ORG_ID")).equals(OemDictCodeConstants.OEM_ACTIVITIES)){
					//rType = Constant.DUTY_TYPE_COMPANY;//月初结转 全国池 待定
				}else{
					rType = OemDictCodeConstants.DUTY_TYPE_LARGEREGION;
				}
			}
		}
		List<TmAllotSupportPO> tList = TmAllotSupportPO.find(" vin = ? ", rowDto.getVin());
		if(tList.size()>0){
			OemDAOUtil.execBatchPreparement("update TM_ALLOT_SUPPORT set IS_SUPPORT="+rowDto.getIsSupport()+" where vin='"+rowDto.getVin()+"'", null);
		}else{
			OemDAOUtil.execBatchPreparement("insert into TM_ALLOT_SUPPORT(VIN,IS_SUPPORT,CREATE_BY,CREATE_DATE) values('"+rowDto.getVin()+"',"+rowDto.getIsSupport()+","+loginInfo.getUserId()+",now())", null);
		}
		OemDAOUtil.execBatchPreparement("insert into TMP_UPLOAD_LATELY_MONTH(ROW_NO,VIN,DEALER_CODE,R_TYPE,CREATE_BY,CREATE_DATE) values("+rowDto.getRowNO()+",'"+rowDto.getVin()+"','"+rowDto.getDealerCode()+"',"+rType+","+loginInfo.getUserId()+",now())", null);
	}
	
	/**
	 * 
	 */
	@Override
	public void insertTempToTm() throws Exception{
		dao.insertTempToTm();
	}
	
	/**
	 * 获取分配下载数据
	 */
	@Override
	public Map<String, List<Map>> getExcelData(Map<String, String> queryParam) {
		Map<String, List<Map>> excelData = new HashMap<>();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		try {
		
			String actionType = CommonUtils.checkNull(queryParam.get("actionType"));
			String groupId = CommonUtils.checkNull(queryParam.get("groupId"));
			String atype = CommonUtils.checkNull(queryParam.get("atype"));
			String initType = CommonUtils.checkNull(queryParam.get("initType"));
			
			String grand30="0";
			String grand36="0";
			List<TmAllotGrandPO> taList = TmAllotGrandPO.findAll(); 
			for(int i=0;i<taList.size();i++){
				TmAllotGrandPO PO = taList.get(i);
				if(PO.getInteger("GRAND_TYPE")==30){
					grand30 = CommonUtils.checkNull(PO.getLong("GRAND_ID"),"0");
				}
				if(PO.getInteger("GRAND_TYPE")==36){
					grand36 = CommonUtils.checkNull(PO.getLong("GRAND_ID"),"0");
				}
			}
			if(groupId.equals(grand36)){
				groupId=grand30+","+grand36;
			}
			if(atype.equals("1")){
				groupId="";
			}	
			List<Map> List = dao.findOrderIsAllot(loginInfo);
			if(List.size()>0){
	//			act.setOutData("result", "资源分配上传数据正在审核中...");
	//			act.setForword(ALLOT_RES_IMP_INI_TURL2);
			}else{
				List<Map> tList = dao.findOrderAllot(loginInfo);
				if(tList.size()>0){
					String allotDate = CommonUtils.checkNull(tList.get(0).get("CREATE_DATE"));
					String allotMonthDate=allotDate.substring(0, allotDate.length()-2);
					List<Map> list = dao.findResourceAllotList(groupId,allotDate,loginInfo);
					List<Map> gapList = dao.findTotalGapListBySeries(groupId,allotDate,allotMonthDate,loginInfo);					
					List<Map> serGroup = dao.findResourceAllotListBySeries(groupId,allotDate,loginInfo);
					List<Map> coloGroup = dao.findResourceAllotListByColor(groupId,allotDate,loginInfo);
					List<Map> gapList_30 = new ArrayList<Map>();
					int total=0;//
				    List<Map> list30=new ArrayList<Map>();
				    List<Map> list36=new ArrayList<Map>();
				    List<Map> tList30=new ArrayList<Map>();
				    List<Map> tList36=new ArrayList<Map>();
					for(int i=0;i<gapList.size();i++){
						Map bean = gapList.get(i);	
						if(CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand30)){
							gapList_30.add(bean);
							Map<String,Object> map30=new HashMap<String,Object>();
							Map<String,Object> tMap30=new HashMap<String,Object>();
							if(!CommonUtils.checkNull(bean.get("SERIES_NAME")).equals("TOTAL")){															
								map30.put("ALLOT_NUM", Integer.parseInt(CommonUtils.checkNull(bean.get("ALLOT_NUM"))));
								map30.put("ORG_ID", bean.get("ORG_ID"));
								map30.put("ALLOT_MONTH_NUM", bean.get("ALLOT_MONTH_NUM"));
								list30.add(map30);
							}else{
								tMap30.put("ORG_ID", bean.get("ORG_ID"));
								tMap30.put("ALLOT_MONTH_NUM", Integer.parseInt(CommonUtils.checkNull(bean.get("ALLOT_MONTH_NUM"))));
								tMap30.put("ALLOT_NUM", Integer.parseInt(CommonUtils.checkNull(bean.get("ALLOT_NUM"))));
								tList30.add(tMap30);
							}
						}
						if(CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand36)){
							Map<String,Object> map36=new HashMap<String,Object>();
							Map<String,Object> tMap36=new HashMap<String,Object>();
							if(!CommonUtils.checkNull(bean.get("SERIES_NAME")).equals("TOTAL")){
								map36.put("ORG_ID",bean.get("ORG_ID"));
								map36.put("ALLOT_NUM", bean.get("ALLOT_NUM"));
								map36.put("ALLOT_MONTH_NUM", bean.get("ALLOT_MONTH_NUM"));
								list36.add(map36);
							}else{
								tMap36.put("ORG_ID", bean.get("ORG_ID"));
								tMap36.put("ALLOT_MONTH_NUM", bean.get("ALLOT_MONTH_NUM"));
								tMap36.put("ALLOT_NUM", Integer.parseInt(CommonUtils.checkNull(bean.get("ALLOT_NUM"),"0")));
								tList36.add(tMap36);
							}
						}				
						
					}
					for(int i=0;i<list30.size();i++){
						Map<String,Object> map30=list30.get(i);
						for(int k=0;k<list36.size();k++){
							Map<String,Object> map36=list36.get(k);
							if(CommonUtils.checkNull(map30.get("ORG_ID")).equals(CommonUtils.checkNull(map36.get("ORG_ID")))){
								total=Integer.parseInt(CommonUtils.checkNull(map30.get("ALLOT_MONTH_NUM"),"0"))+Integer.parseInt(CommonUtils.checkNull(map36.get("ALLOT_MONTH_NUM"),"0"));
								for(int j=0;j<gapList.size();j++){
									Map bean1 = gapList.get(j);
									if(CommonUtils.checkNull(bean1.get("SERIES_ID")).equals(grand36)&&CommonUtils.checkNull(map36.get("ORG_ID")).equals(CommonUtils.checkNull(bean1.get("ORG_ID")))&&!(CommonUtils.checkNull(map36.get("ORG_ID")).equals("0"))){																					
										bean1.put("ALLOT_MONTH_NUM", total);	
										bean1.put("ALLOT_NUM", (Integer.parseInt(CommonUtils.checkNull(map30.get("ALLOT_NUM"),"0"))+Integer.parseInt(CommonUtils.checkNull(map36.get("ALLOT_NUM"),"0"))));
	
									if(CommonUtils.checkNull(bean1.get("SERIES_ID")).equals(grand30)){
										bean1.remove("SERIES_ID");
									}
									}
								}
							}
						}
					}
	
					for(int i=0;i<tList30.size();i++){
						Map<String,Object> tMap30=tList30.get(i);
						for(int k=0;k<tList36.size();k++){
							Map<String,Object> tMap36=tList36.get(k);
							if(CommonUtils.checkNull(tMap30.get("ORG_ID")).equals(CommonUtils.checkNull(tMap36.get("ORG_ID")))){
								total=Integer.parseInt(CommonUtils.checkNull(tMap30.get("ALLOT_MONTH_NUM"),"0"))+Integer.parseInt(CommonUtils.checkNull(tMap36.get("ALLOT_MONTH_NUM"),"0"));
								for(int j=0;j<gapList.size();j++){
									Map bean1 = gapList.get(j);
									if(CommonUtils.checkNull(bean1.get("SERIES_ID")).equals(grand36)&&CommonUtils.checkNull(tMap36.get("ORG_ID")).equals(CommonUtils.checkNull(bean1.get("ORG_ID")))&&(CommonUtils.checkNull(tMap36.get("ORG_ID")).equals("0"))){
											bean1.put("ALLOT_MONTH_NUM", total);
											bean1.put("ALLOT_NUM", (Integer.parseInt(CommonUtils.checkNull(tMap30.get("ALLOT_NUM"),"0"))+Integer.parseInt(CommonUtils.checkNull(tMap36.get("ALLOT_NUM"),"0"))));
									if(CommonUtils.checkNull(bean1.get("SERIES_ID")).equals(grand30)){
											bean1.remove("SERIES_ID");
										}
									}
								}
							}
						}
					}
					
					//将大切诺基3.0的数据加到3.6上
					for(int i=0;i<gapList.size();i++){
						Map bean36 = gapList.get(i);
						if(CommonUtils.checkNull(bean36.get("SERIES_ID")).equals(grand36)){
							int rateNum = 0;
							for(int j=0;j<gapList.size();j++){
								Map bean30 = gapList.get(j);
								if(CommonUtils.checkNull(bean30.get("SERIES_ID")).equals(grand30)&&CommonUtils.checkNull(bean30.get("ORG_ID")).equals(CommonUtils.checkNull(bean36.get("ORG_ID")))){
									gapList.get(i).put("NUM1", new Integer(CommonUtils.checkNull(bean36.get("NUM1"),"0"))+new Integer(CommonUtils.checkNull(bean30.get("NUM1"),"0")));
									gapList.get(i).put("NUM2", new Integer(CommonUtils.checkNull(bean36.get("NUM2"),"0"))+new Integer(CommonUtils.checkNull(bean30.get("NUM2"),"0")));
									gapList.get(i).put("NUM3", new Integer(CommonUtils.checkNull(bean36.get("NUM3"),"0"))+new Integer(CommonUtils.checkNull(bean30.get("NUM3"),"0")));
									gapList.get(i).put("NUM4", new Integer(CommonUtils.checkNull(bean36.get("NUM4"),"0"))+new Integer(CommonUtils.checkNull(bean30.get("NUM4"),"0")));
									gapList.get(i).put("NUM5", new Integer(CommonUtils.checkNull(bean36.get("NUM5"),"0"))+new Integer(CommonUtils.checkNull(bean30.get("NUM5"),"0")));
								}
							}
						}
					}
					//资源满足率
					for(int i=0;i<gapList.size();i++){
						Map<String,Object> bean=gapList.get(i);
						if(CommonUtils.checkNull(bean.get("SALE_AMOUNT")).equals("0")){
							gapList.get(i).put("RATE",0);			
						}else{		
							float num = new Float(CommonUtils.checkNull(bean.get("NUM1"),"0"))+new Float(CommonUtils.checkNull(bean.get("NUM2"),"0"))
									+new Float(CommonUtils.checkNull(bean.get("NUM3"),"0"))+new Float(CommonUtils.checkNull(bean.get("NUM33"),"0"))
									+new Float(CommonUtils.checkNull(bean.get("NUM4"),"0"))+new Float(CommonUtils.checkNull(bean.get("NUM5"),"0"))
											+new Float(CommonUtils.checkNull(bean.get("ALLOT_NUM"),"0"));
							gapList.get(i).put("RATE", String.format("%.0f",num*100/new Float(CommonUtils.checkNull(bean.get("SALE_AMOUNT"),"0"))));							
						}						
					}
					//大切诺基-分配比例
					for(int i=0;i<serGroup.size();i++){
						Map bean36 = serGroup.get(i);
						if(CommonUtils.checkNull(bean36.get("SERIES_ID")).equals(grand36)){
							for(int j=0;j<serGroup.size();j++){
								Map bean30 = serGroup.get(j);
								if(CommonUtils.checkNull(bean30.get("SERIES_ID")).equals(grand30)){
									serGroup.get(i).put("SER_NUM", new Integer(CommonUtils.checkNull(bean36.get("SER_NUM"),"0"))
											+new Integer(CommonUtils.checkNull(bean30.get("SER_NUM"),"0")));
								}
							}
						}
					}
					List<Map> list_30 = new ArrayList<Map>();	
					
					List<Map> serGroup_30 = new ArrayList<Map>();
					List<Map> coloGroup_30 = new ArrayList<Map>();
					
					DecimalFormat df = new DecimalFormat("");
					Long startTime = System.currentTimeMillis();
					List dqTotalList = new ArrayList();	
					for(int i=0;i<list.size();i++){
						Map bean = list.get(i);	
						if(CommonUtils.checkNull(list.get(i).get("SERIES_NAME")).contains("大切诺基")){
							list.get(i).put("SERIES_NAME", "大切诺基");
						}
						
						if(CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand30)){						
							list_30.add(bean);
						}
					}
					
					for(int i=0;i<serGroup.size();i++){
						Map bean = serGroup.get(i);	
						if(CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand30)){
							serGroup_30.add(bean);
						}
					}
					for(int i=0;i<coloGroup.size();i++){
						Map bean = coloGroup.get(i);	
						if(CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand30)){
							coloGroup_30.add(bean);
						}
					}
					for(int i=0;i<gapList.size();i++){
						Map bean = gapList.get(i);	
						if(!dqTotalList.contains(CommonUtils.checkNull(bean.get("ORG_NAME")))){
							dqTotalList.add(CommonUtils.checkNull(bean.get("ORG_NAME")));
						}		
						for(int j=0;j<serGroup.size();j++){
							Map sBean = serGroup.get(j);											
							if(CommonUtils.checkNull(sBean.get("SERIES_NAME")).equals(CommonUtils.checkNull(bean.get("SERIES_NAME")))
									||CommonUtils.checkNull(bean.get("SERIES_NAME")).equals("ORG_TOTAL")
									||CommonUtils.checkNull(bean.get("SERIES_NAME")).equals("TOTAL")){
								int allotNum=new Integer(CommonUtils.checkNull(bean.get("ALLOT_NUM"),"0"));	
								int totalNum=0;
								if(CommonUtils.checkNull(bean.get("SERIES_NAME"),"0").equals("ORG_TOTAL")){
									totalNum=new Integer(CommonUtils.checkNull(bean.get("GAP"),"0"));
								}else{
									if(CommonUtils.checkNull(bean.get("SERIES_NAME")).equals("TOTAL")){
										totalNum=new Integer(CommonUtils.checkNull(bean.get("GAP"),"0"));
									}else{
										totalNum=new Integer(CommonUtils.checkNull(sBean.get("SER_NUM"),"0"));
									}
								}
								if(totalNum>0){
									gapList.get(i).put("ALLOT_RATE", df.format(allotNum*100/totalNum)+"%");
								}else{
									gapList.get(i).put("ALLOT_RATE", "0%");
								}		
								break;
							}						
						}
					}					
					List<Map> sList = new ArrayList<Map>();		
					List<Map> sList_temmp = new ArrayList<Map>();	
					List<Map> sList_temmp1 = new ArrayList<Map>();		
					List<Map> dqList = new ArrayList<Map>();
					List<Map> gList = new ArrayList<Map>();
					List<Map> cList = new ArrayList<Map>();
					List<Map> sList_30 = new ArrayList<Map>();			
					List<Map> dqList_30 = new ArrayList<Map>();
					List<Map> gList_30 = new ArrayList<Map>();
					List<Map> cList_30 = new ArrayList<Map>();
					for(int i=0;i<list.size();i++){
						Map bean =  list.get(i);	
						Map sDB = new HashMap();
						sDB.put("SERIES_ID", bean.get("SERIES_ID"));
						sDB.put("SERIES_CODE", bean.get("SERIES_CODE"));
						sDB.put("SERIES_NAME", bean.get("SERIES_NAME"));
						sDB.put("STATUS", bean.get("STATUS"));
						if(CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand30)){						
							if(!sList_30.contains(sDB)){
								if(CommonUtils.checkNull(bean.get("STATUS")).equals("1")){
									sList_temmp.add(sDB);
								}else{
									sList_30.add(sDB);
								}
							}	
						}else{
							if(!sList_temmp.contains(sDB)){
								if(CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand36)&&CommonUtils.checkNull(bean.get("STATUS")).equals("1")){
									sList_temmp.add(sDB);
								}else if(!CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand36)){
									sList_temmp.add(sDB);
								}
							}	
						}							
						
						Map map = new HashMap();
						map.put("SERIES_ID", bean.get("SERIES_ID"));
						map.put("SERIES_NAME", bean.get("SERIES_NAME"));
						map.put("ORG_NAME", bean.get("ORG_NAME"));
						map.put("STATUS", bean.get("STATUS"));
						if(!dqList.contains(map)){
							dqList.add(map);
						}
											
						Map gMap = new HashMap();
						gMap.put("SERIES_ID", bean.get("SERIES_ID"));
						gMap.put("SERIES_NAME", bean.get("SERIES_NAME"));
						gMap.put("GROUP_ID", bean.get("GROUP_ID"));
						gMap.put("GROUP_NAME", bean.get("GROUP_NAME"));
						gMap.put("STANDARD_OPTION", bean.get("STANDARD_OPTION"));
						gMap.put("FACTORY_OPTIONS", bean.get("FACTORY_OPTIONS"));
						gMap.put("LOCAL_OPTION", bean.get("LOCAL_OPTION"));
						gMap.put("MODEL_YEAR", bean.get("MODEL_YEAR"));
						if(CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand30)){						
							if(!gList_30.contains(gMap)){
								gList_30.add(gMap);
							}	
						}else{
							if(!gList.contains(gMap)){
								gList.add(gMap);
							}
						}	
	
						Map cMap = new HashMap();
						cMap.put("SERIES_ID", bean.get("SERIES_ID"));
						cMap.put("SERIES_NAME", bean.get("SERIES_NAME"));
						cMap.put("GROUP_ID", bean.get("GROUP_ID"));
						cMap.put("GROUP_NAME", bean.get("GROUP_NAME"));
						cMap.put("COLOR_NAME", bean.get("COLOR_NAME"));
						if(CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand30)){						
							if(!cList_30.contains(cMap)){
								cList_30.add(cMap);
							}	
						}else{
							if(!cList.contains(cMap)){
								cList.add(cMap);
							}
						}						
					}	
					List colorList = new ArrayList();
					for(int i=0;i<gList.size();i++){
						Map gMap = gList.get(i);
						int colorNum = 0;
						for(int j=0;j<cList.size();j++){
							Map cMap = cList.get(j);
							if(cMap.get("SERIES_ID").equals(gMap.get("SERIES_ID"))&&
									cMap.get("GROUP_ID").equals(gMap.get("GROUP_ID"))&&
									cMap.get("GROUP_NAME").equals(gMap.get("GROUP_NAME"))){
								if(!colorList.contains(cMap.get("COLOR_NAME"))){
									colorList.add(cMap.get("COLOR_NAME"));
								}
							}
						}
						gMap.put("colorNum", colorList.size());
						colorList.clear();
					}		
					//大切诺基3.0需要单独处理
					for(int i=0;i<gList_30.size();i++){
						Map gMap = gList_30.get(i);
						int colorNum = 0;
						for(int j=0;j<cList_30.size();j++){
							Map cMap = cList_30.get(j);
							if(cMap.get("SERIES_ID").equals(gMap.get("SERIES_ID"))&&
									cMap.get("GROUP_ID").equals(gMap.get("GROUP_ID"))&&
									cMap.get("GROUP_NAME").equals(gMap.get("GROUP_NAME"))){
								if(!colorList.contains(cMap.get("COLOR_NAME"))){
									colorList.add(cMap.get("COLOR_NAME"));
								}
							}
						}
						gMap.put("colorNum", colorList.size());
						colorList.clear();
					}	
					for(int i=0;i<cList.size();i++){
						Map bean = cList.get(i);	
						for(int j=0;j<coloGroup.size();j++){
							Map sBean = coloGroup.get(j);	
							if(CommonUtils.checkNull(sBean.get("SERIES_ID")).equals(CommonUtils.checkNull(bean.get("SERIES_ID")))&&
									CommonUtils.checkNull(sBean.get("GROUP_ID")).equals(CommonUtils.checkNull(bean.get("GROUP_ID")))&&
									CommonUtils.checkNull(sBean.get("GROUP_NAME")).equals(CommonUtils.checkNull(bean.get("GROUP_NAME")))&&
									CommonUtils.checkNull(sBean.get("COLOR_NAME")).equals(CommonUtils.checkNull(bean.get("COLOR_NAME")))){
								cList.get(i).put("COLOR_TOTAL", sBean.get("COLOR_NUM"));
								break;
							}
						}
					}
					//大切诺基3.0需要单独处理
					for(int i=0;i<cList_30.size();i++){
						Map bean = cList_30.get(i);	
						for(int j=0;j<coloGroup_30.size();j++){
							Map sBean = coloGroup_30.get(j);	
							if(CommonUtils.checkNull(sBean.get("SERIES_ID")).equals(CommonUtils.checkNull(bean.get("SERIES_ID")))&&
									CommonUtils.checkNull(sBean.get("GROUP_ID")).equals(CommonUtils.checkNull(bean.get("GROUP_ID")))&&
									sBean.get("GROUP_NAME").equals(bean.get("GROUP_NAME"))&&
									sBean.get("COLOR_NAME").equals(bean.get("COLOR_NAME"))){
								cList_30.get(i).put("COLOR_TOTAL", sBean.get("COLOR_NUM"));
								break;
							}
						}
					}
					int grand30Flag = 0;
					int grand36Flag = 0;
					for(int i=0;i<sList_temmp.size();i++){
						Map bean = sList_temmp.get(i);
						Map sDB = new HashMap();
						sDB.put("SERIES_ID", bean.get("SERIES_ID"));
						sDB.put("SERIES_CODE", bean.get("SERIES_CODE"));
						sDB.put("SERIES_NAME", bean.get("SERIES_NAME"));
						sDB.put("STATUS", bean.get("STATUS"));
						if(!CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand30)){						
							if(!sList.contains(sDB)){
								if(!CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand30)&&CommonUtils.checkNull(bean.get("STATUS")).equals("1")){
									sList_temmp1.add(sDB);
		
								}else if(!CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand30)&&!CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand36)){
									sList_temmp1.add(sDB);
								}
							}
						}
						if(CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand30)){
							if(CommonUtils.checkNull(bean.get("STATUS")).equals("1")){
								sList_temmp1.add(sDB);
								grand30Flag = 1;
							}
							
						}
						if(CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand36)){
							if(CommonUtils.checkNull(bean.get("STATUS")).equals("1")){
	
								grand36Flag = 1;
							}
							
						}
						
					}
				
						for(int i=0;i<sList_temmp1.size();i++){
							Map bean = sList_temmp1.get(i);
							Map sDB = new HashMap();
							if(!CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand30)&&
									!CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand36)){
								if(!sList.contains(sDB)){
									sDB.put("SERIES_ID", bean.get("SERIES_ID"));
									sDB.put("SERIES_CODE", bean.get("SERIES_CODE"));
									sDB.put("SERIES_NAME", bean.get("SERIES_NAME"));
									sDB.put("STATUS", bean.get("STATUS"));
									sList.add(sDB);
								}
							}
						}
			
					if(grand30Flag==1&&grand36Flag==0){
						Map sDB = new HashMap();
						sDB.put("SERIES_ID", grand30);
						sDB.put("SERIES_CODE", "");
						sDB.put("SERIES_NAME", "大切诺基");
						sDB.put("STATUS", 1);
						sList.add(sDB);
					}
					if(grand36Flag==1&&grand30Flag==0){
						Map sDB = new HashMap();
						sDB.put("SERIES_ID", grand36);
						sDB.put("SERIES_CODE", "");
						sDB.put("SERIES_NAME", "大切诺基");
						sDB.put("STATUS", 1);
						sList.add(sDB);
					}
					if(grand36Flag==1&&grand30Flag==1){
						Map sDB = new HashMap();
						sDB.put("SERIES_ID", grand36);
						sDB.put("SERIES_CODE", "");
						sDB.put("SERIES_NAME", "大切诺基");
						sDB.put("STATUS", 1);
						sList.add(sDB);
					}
					if(grand36Flag==0&&grand30Flag==0){
						Map sDB = new HashMap();
						sDB.put("SERIES_ID", grand36);
						sDB.put("SERIES_CODE", "");
						sDB.put("SERIES_NAME", "大切诺基");
						sDB.put("STATUS", 0);
						sList.add(sDB);
					}
					List<Map> sList_status1 = new ArrayList<Map>();	
					List<Map> sList_status0 = new ArrayList<Map>();	
	                 for (int i = 0; i < sList.size(); i++) {
	                	 Map bean =sList.get(i);
						if(CommonUtils.checkNull(bean.get("STATUS")).equals("0")){
							sList_status0.add(bean);
						}
						if(CommonUtils.checkNull(bean.get("STATUS")).equals("1")){
							sList_status1.add(bean);
						}
					}
	                 sList.clear();
	                 if(sList_status1.size()>0){
	                	 for (int i = 0; i <sList_status1.size(); i++) {
							sList.add(sList_status1.get(i));
						}
	                 }
	                 if(sList_status0.size()>0){
	                	 for (int i = 0; i <sList_status0.size(); i++) {
							sList.add(sList_status0.get(i));
						}
	                 }
	                
	                excelData  =toExcelAllotSmall(dqTotalList,sList,dqList,gapList,gList,cList,serGroup,gList_30, cList_30,list);
	                excelData.put("Slist", sList);
					if(actionType.equals("download")){
//						ToExcel.toExcelAllotSmall(ActionContext.getContext().getResponse(), request, dqTotalList,sList,dqList,gapList,gList,cList,serGroup,gList_30, cList_30,list, "资源分配(OTD)下载" + Utility.getCurrentTime(10));
					}else{						
//						act.setOutData("dqTotalSize", dqTotalList.size());
//						act.setOutData("sList", sList);
//						act.setOutData("dqList", dqList);
//						act.setOutData("gapList", gapList);
//						act.setOutData("gList", gList);
//						act.setOutData("cList", cList);
//						act.setOutData("list", list);	
//						act.setOutData("allotDate", allotDate);
//						act.setOutData("groupId", groupId);	
//						act.setOutData("gList_30", gList_30);
//						act.setOutData("cList_30", cList_30);
						if(actionType.equals("modify")){
							List<Map> sList1 = new ArrayList<Map>();	
							List<Map> sLis1_temmp = new ArrayList<Map>();	
							for(int i=0;i<list.size();i++){
								Map bean = list.get(i);	
								if(CommonUtils.checkNull(list.get(i).get("STATUS")).equals("1")){
									Map sDB = new HashMap();
									sDB.put("SERIES_ID", bean.get("SERIES_ID"));
									sDB.put("SERIES_CODE", bean.get("SERIES_CODE"));
									sDB.put("SERIES_NAME", bean.get("SERIES_NAME"));
									sDB.put("STATUS", bean.get("STATUS"));
									if(!sList1.contains(sDB)){										
										sList1.add(sDB);
									}
								}
								
							}
							int grandCount=0;
							for (int i = 0; i < sList1.size(); i++) {
								Map bean = list.get(i);	
								if(CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand30)){
									grandCount++;
								}
								if(CommonUtils.checkNull(bean.get("SERIES_ID")).equals(grand36)){
									grandCount++;
								}
							}
							if(grandCount>0){
								if(grand30Flag==1&&grand36Flag==0){
									Map sDB = new HashMap();
									sDB.put("SERIES_ID", grand30);
									sDB.put("SERIES_CODE", null);
									sDB.put("SERIES_NAME","大切诺基");
									sDB.put("STATUS", 1);
									sLis1_temmp.add(sDB);
								}
								if(grand36Flag==1&&grand30Flag==0){
									Map sDB = new HashMap();
									sDB.put("SERIES_ID", grand36);
									sDB.put("SERIES_CODE", null);
									sDB.put("SERIES_NAME","大切诺基");
									sDB.put("STATUS", 1);
									sLis1_temmp.add(sDB);
								}
								if(grand36Flag==1&&grand30Flag==1){
									Map sDB = new HashMap();
									sDB.put("SERIES_ID", grand36);
									sDB.put("SERIES_CODE", null);
									sDB.put("SERIES_NAME","大切诺基");
									sDB.put("STATUS", 1);
									sLis1_temmp.add(sDB);
	
								}
//								act.setOutData("sList1", sLis1_temmp);
//								act.setOutData("series", sLis1_temmp.get(0).get("SERIES_NAME").toString());
							}else if(grandCount==0){
//								act.setOutData("sList1", sList1);
//								act.setOutData("series", sList1.get(0).get("SERIES_NAME").toString());
							}
							
							
//							act.setForword(ALLOT_RES_IMP_MODIFY);	
						}else{
//							excelData.put("result", list);
//							act.setForword(ALLOT_RES_IMP_SUC_URL);
						}
					}														
				}else{
//					act.setForword(ALLOT_RES_IMP_INI_TURL);
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException("未知错误!");
		}
		return excelData;
	}
	
	/**
	 * 转换成F4框架可导出的数据结构
	 * @param dqTotalList
	 * @param sList
	 * @param dqList
	 * @param gapList
	 * @param gList
	 * @param cList
	 * @param serGroup
	 * @param gList_30
	 * @param cList_30
	 * @param list
	 * @return
	 */
	private Map<String, List<Map>> toExcelAllotSmall(List dqTotalList, List<Map> sList,
			List<Map> dqList, List<Map> gapList, List<Map> gList, List<Map> cList, List<Map> serGroup,
			List<Map> gList_30, List<Map> cList_30, List<Map> list) {
		Map<String, List<Map>> resultMap = new HashMap<String, List<Map>>();
		int m=1;
	    int n=14;
	    int a=4;
	    int num=2;
	    for(int i=0;i<sList.size();i++){
	    	Map sMap = sList.get(i);
	    	List<Map> listMap = new ArrayList<>();
	    	String seriesName = CommonUtils.checkNull(sMap.get("SERIES_NAME"));
	    	//设置每一个sheet名称
		    a=4;
		    //车款
//		    for(int j=0;j<gList.size();j++){
//		    	Map gMap = gList.get(j);
//		    	if(CommonUtils.checkNull(gMap.get("SERIES_ID")).equals(CommonUtils.checkNull(sMap.get("SERIES_ID")))&&!gMap.get("GROUP_ID").toString().equals("0")){
//		    		String groupName = CommonUtils.checkNull(gMap.get("GROUP_NAME"));
//		    		int cols = new Integer(CommonUtils.checkNull(gMap.get("colorNum")));
//		    		wsheet.setColumnView(m, groupName.length()*2+2);
//		    		wsheet.mergeCells(m,0,m+cols-1,0);			    		
//					wsheet.addCell(new  Label(m, 0, groupName, wcf));	
//					m+=cols;
//		    	}
//		    }
		   //大切诺基3.0需要单独处理
//		   for(int j=0;j<gList_30.size();j++){
//		    	Map gMap = gList_30.get(j);
//		    	if(gMap.get("SERIES_NAME").equals(sMap.get("SERIES_NAME"))&&!gMap.get("GROUP_ID").toString().equals("0")){
//		    		String groupName = CommonUtils.checkNull(gMap.get("GROUP_NAME"));
//		    		int cols = new Integer(CommonUtils.checkNull(gMap.get("colorNum")));
//		    		wsheet.setColumnView(m, groupName.length()*2+2);
//		    		wsheet.mergeCells(m,0,m+cols-1,0);			    		
//					wsheet.addCell(new  Label(m, 0, groupName, wcf));	
//					m+=cols;
//		    	}
//		    }
		
		   for(int j=0;j<gList.size();j++){
		    	Map gMap = gList.get(j);
		    	if(CommonUtils.checkNull(gMap.get("SERIES_ID")).equals(CommonUtils.checkNull(sMap.get("SERIES_ID")))){
		    		for(int k=0;k<cList.size();k++){
		    			Map cMap = cList.get(k);
		    			if(CommonUtils.checkNull(cMap.get("SERIES_ID")).equals(CommonUtils.checkNull(gMap.get("SERIES_ID")))
		    					&&CommonUtils.checkNull(cMap.get("GROUP_ID")).equals(CommonUtils.checkNull(gMap.get("GROUP_ID")))&&
		    					CommonUtils.checkNull(cMap.get("GROUP_NAME")).equals(CommonUtils.checkNull(gMap.get("GROUP_NAME")))
		    					&&!CommonUtils.checkNull(cMap.get("COLOR_NAME")).equals("0")){
//		    				wsheet.addCell(new  Label(n++, 1, CommonUtils.checkNull(cMap.get("COLOR_NAME")), wcf));
		    			}
		    		}	
		    	}
		    }
		    //大切诺基3.0需要单独处理
		    for(int j=0;j<gList_30.size();j++){
		    	Map gMap = gList_30.get(j);
		    	if(CommonUtils.checkNull(gMap.get("SERIES_NAME")).equals(CommonUtils.checkNull(sMap.get("SERIES_NAME")))){
		    		for(int k=0;k<cList_30.size();k++){
		    			Map cMap = cList_30.get(k);
		    			if(CommonUtils.checkNull(cMap.get("SERIES_ID")).equals(CommonUtils.checkNull(gMap.get("SERIES_ID")))
		    					&&CommonUtils.checkNull(cMap.get("GROUP_ID")).equals(CommonUtils.checkNull(gMap.get("GROUP_ID")))&&
		    					CommonUtils.checkNull(cMap.get("GROUP_NAME")).equals(CommonUtils.checkNull(gMap.get("GROUP_NAME")))&&
		    					!CommonUtils.checkNull(cMap.get("COLOR_NAME")).equals("0")){
//		    				wsheet.addCell(new  Label(n++, 1, CommonUtils.checkNull(cMap.get("COLOR_NAME")), wcf));
		    			}
		    		}	
		    	}
		    }
		    n=2;
		    //数据
		    for(int j=0;j<dqList.size();j++){
		    	Map dqMap = dqList.get(j);
		    	if(CommonUtils.checkNull(dqMap.get("SERIES_ID")).equals(CommonUtils.checkNull(sMap.get("SERIES_ID")))){
				    for(int k=0;k<gapList.size();k++){
				    	Map gapMap = gapList.get(k);
				    	if(CommonUtils.checkNull(gapMap.get("SERIES_ID")).equals(dqMap.get("SERIES_ID").toString())&&gapMap.get("ORG_NAME").equals(dqMap.get("ORG_NAME"))){
				    		Map map = new HashMap<>();
				    		map.put("SERIES_NAME" , seriesName);
				    		map.put("SERIES_ID" , CommonUtils.checkNull(gapMap.get("SERIES_ID")));
				    		map.put("ORG_NAME" , CommonUtils.checkNull(gapMap.get("ORG_NAME")));
				    		map.put("SALE_AMOUNT" , CommonUtils.checkNull(gapMap.get("SALE_AMOUNT")));
				    		map.put("NUM1" , CommonUtils.checkNull(gapMap.get("NUM1")));
				    		map.put("NUM2" , CommonUtils.checkNull(gapMap.get("NUM2")));
				    		map.put("NUM22" , CommonUtils.checkNull(gapMap.get("NUM22")));
				    		map.put("NUM3" , CommonUtils.checkNull(gapMap.get("NUM3")));
				    		map.put("NUM33" , CommonUtils.checkNull(gapMap.get("NUM33")));
				    		map.put("NUM4" , CommonUtils.checkNull(gapMap.get("NUM4")));
				    		map.put("NUM5" , CommonUtils.checkNull(gapMap.get("NUM5")));
				    		map.put("GAP" , CommonUtils.checkNull(gapMap.get("GAP")));
				    		map.put("ALLOT_NUM" , CommonUtils.checkNull(gapMap.get("ALLOT_NUM")));
				    		map.put("ALLOT_MONTH_NUM" , CommonUtils.checkNull(gapMap.get("ALLOT_MONTH_NUM")));
				    		map.put("ALLOT_RATE" , CommonUtils.checkNull(gapMap.get("ALLOT_RATE")));
				    		map.put("RATE" , CommonUtils.checkNull(gapMap.get("RATE")));
				    		listMap.add(map);
				    	}
				    }			
				    for(int k=0;k<gList.size();k++){
				    	Map gMap = gList.get(k);
				    	if(CommonUtils.checkNull(gMap.get("SERIES_ID")).equals(CommonUtils.checkNull(dqMap.get("SERIES_ID"))) &&
				    			!CommonUtils.checkNull(gMap.get("GROUP_ID")).equals("0")){
				    		for(int l=0;l<list.size();l++){
				    			Map rMap = list.get(l);
				    			if(CommonUtils.checkNull(rMap.get("SERIES_ID")).equals(CommonUtils.checkNull(dqMap.get("SERIES_ID")))
				    					&&CommonUtils.checkNull(rMap.get("ORG_NAME")).equals(CommonUtils.checkNull(dqMap.get("ORG_NAME")))
				    			   &&CommonUtils.checkNull(rMap.get("GROUP_ID")).equals(CommonUtils.checkNull(gMap.get("GROUP_ID")))&&
				    			   CommonUtils.checkNull(rMap.get("GROUP_NAME")).equals(CommonUtils.checkNull(gMap.get("GROUP_NAME")))&&
				    			   !CommonUtils.checkNull(gMap.get("GROUP_NAME")).equals("0")){
//				    				 wsheet.addCell(new  Label(num++, n, rMap.get("NUM").toString(), wcf1));
				    			}
				    		}
				    	}
				    }
				    //大切诺基3.0需要单独处理
				    for(int k=0;k<gList_30.size();k++){
				    	Map gMap = gList_30.get(k);
				    	if(CommonUtils.checkNull(gMap.get("SERIES_NAME")).equals(CommonUtils.checkNull(dqMap.get("SERIES_NAME")))&&!CommonUtils.checkNull(gMap.get("GROUP_ID")).equals("0")){
				    		for(int l=0;l<list.size();l++){
				    			Map rMap = list.get(l);
				    			if(CommonUtils.checkNull(rMap.get("SERIES_NAME")).equals(CommonUtils.checkNull(dqMap.get("SERIES_NAME")))&&
				    					CommonUtils.checkNull(rMap.get("ORG_NAME")).equals(CommonUtils.checkNull(dqMap.get("ORG_NAME")))
				    			   &&CommonUtils.checkNull(rMap.get("GROUP_ID")).equals(CommonUtils.checkNull(gMap.get("GROUP_ID")))&&
				    			   CommonUtils.checkNull(rMap.get("GROUP_NAME")).equals(CommonUtils.checkNull(gMap.get("GROUP_NAME")))&&
				    			   !CommonUtils.checkNull(gMap.get("GROUP_ID")).equals("0")){
//				    				 wsheet.addCell(new  Label(num++, n, rMap.get("NUM").toString(), wcf1));
				    			}
				    		}
				    	}
				    }
		    	}
		    }
		    for(int j=0;j<gapList.size();j++){
		    	Map gapMap = gapList.get(j);
		    	if(CommonUtils.checkNull(gapMap.get("SERIES_ID")).equals(CommonUtils.checkNull(sMap.get("SERIES_ID")))
		    			&&CommonUtils.checkNull(gapMap.get("SERIES_NAME")).equals("TOTAL")){
		    		Map map = new HashMap<>();
		    		map.put("SERIES_NAME" , seriesName);
		    		map.put("SERIES_ID" , CommonUtils.checkNull(gapMap.get("SERIES_ID")));
		    		map.put("ORG_NAME" , CommonUtils.checkNull(gapMap.get("ORG_NAME")));
		    		map.put("SALE_AMOUNT" , CommonUtils.checkNull(gapMap.get("SALE_AMOUNT")));
		    		map.put("NUM1" , CommonUtils.checkNull(gapMap.get("NUM1")));
		    		map.put("NUM2" , CommonUtils.checkNull(gapMap.get("NUM2")));
		    		map.put("NUM22" , CommonUtils.checkNull(gapMap.get("NUM22")));
		    		map.put("NUM3" , CommonUtils.checkNull(gapMap.get("NUM3")));
		    		map.put("NUM33" , CommonUtils.checkNull(gapMap.get("NUM33")));
		    		map.put("NUM4" , CommonUtils.checkNull(gapMap.get("NUM4")));
		    		map.put("NUM5" , CommonUtils.checkNull(gapMap.get("NUM5")));
		    		map.put("GAP" , CommonUtils.checkNull(gapMap.get("GAP")));
		    		map.put("ALLOT_NUM" , CommonUtils.checkNull(gapMap.get("ALLOT_NUM")));
		    		map.put("ALLOT_MONTH_NUM" , CommonUtils.checkNull(gapMap.get("ALLOT_MONTH_NUM")));
		    		map.put("ALLOT_RATE" , CommonUtils.checkNull(gapMap.get("ALLOT_RATE")));
		    		map.put("RATE" , CommonUtils.checkNull(gapMap.get("RATE")));
		    		listMap.add(map);
		    	}
		    }
		    //颜色汇总
		    for(int j=0;j<gList.size();j++){
		    	Map gMap = gList.get(j);
		    	if(CommonUtils.checkNull(gMap.get("SERIES_ID")).equals(CommonUtils.checkNull(sMap.get("SERIES_ID")))&&
		    			!CommonUtils.checkNull(gMap.get("GROUP_NAME")).equals("0")){
		    		for(int k=0;k<cList.size();k++){
		    			Map cMap = cList.get(k);
		    			if(CommonUtils.checkNull(cMap.get("SERIES_ID")).equals(CommonUtils.checkNull(gMap.get("SERIES_ID")))&&cMap.get("GROUP_ID").equals(gMap.get("GROUP_ID"))&&
		    					cMap.get("GROUP_NAME").equals(gMap.get("GROUP_NAME"))&&!CommonUtils.checkNull(cMap.get("GROUP_ID")).equals("0")){
//		    				wsheet.addCell(new  Label(num++, n, cMap.get("COLOR_TOTAL").toString(), wcf1));
		    			}
		    		}	
		    	}
		    }
		    //大切诺基3.0需要单独处理
		    for(int j=0;j<gList_30.size();j++){
		    	Map gMap = gList_30.get(j);
		    	if(CommonUtils.checkNull(gMap.get("SERIES_NAME")).equals(CommonUtils.checkNull(sMap.get("SERIES_NAME")))&&
		    			!CommonUtils.checkNull(gMap.get("GROUP_NAME")).equals("0")){
		    		for(int k=0;k<cList_30.size();k++){
		    			Map cMap = cList_30.get(k);
		    			if(CommonUtils.checkNull(cMap.get("SERIES_NAME")).equals(CommonUtils.checkNull(gMap.get("SERIES_NAME")))
		    					&&CommonUtils.checkNull(cMap.get("GROUP_ID")).equals(CommonUtils.checkNull(gMap.get("GROUP_ID")))&&
		    					CommonUtils.checkNull(cMap.get("GROUP_NAME")).equals(CommonUtils.checkNull(gMap.get("GROUP_NAME")))&&
		    					!CommonUtils.checkNull(cMap.get("GROUP_ID")).equals("0")){
//		    				wsheet.addCell(new  Label(num++, n, cMap.get("COLOR_TOTAL").toString(), wcf1));
		    			}
		    		}	
		    	}
		    }
		    resultMap.put(seriesName, listMap);
	    }
	    return resultMap;
	}
	
	/**
	 * 资源分配执行
	 */
	@Override
	public Map<String, Object> allot(Map<String, String> queryParam) throws Exception{
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		List<Map> tList = dao.findOrderAllot(loginInfo);
		String allotDate = "";
		if(tList.size()>0){
			allotDate = CommonUtils.checkNull(tList.get(0).get("CREATE_DATE"));
		}
		
		long startTime=System.currentTimeMillis();
		try{		
			/*************************开始事务,标识数据正在导入中**************************/
			//dao.update("insert into TMP_IMPORT_STATUS(ID,ITYPE,STATUS,IMPORT_BY,CREATE_DATE) values(F_GETID,1,"+Constant.TMP_IMPORT_STATUS_01+","+logonUser.getUserId()+",CURRENT TIMESTAMP)", null);
		
			String userId = "";
			List<TmpUploadResourcePO> list = TmpUploadResourcePO.findAll();
			if(list.size()>0){
				userId = CommonUtils.checkNull(list.get(0).get("CREATE_BY"));
			}else{
				userId=loginInfo.getUserId().toString();
			}
            List<Object> inParameter = new ArrayList<Object>();// 输入参数
			inParameter.add(userId);
			inParameter.add(allotDate);
			startTime=System.currentTimeMillis();
			
			logger.info("------资源分配 调用存储过程(AREA_SMALL_GAP_INFO) 开始------");       
			OemDAOUtil.execBatchPreparement("update TMP_UPLOAD_RESOURCE set DEAL=2", new ArrayList<>());
			
        	//dao.callProcedure("AREA_SMALL_GAP_INFO",inParameter,null);
			doCallPrcedureAreaSmallGapInfo(inParameter);
        	
			//dao.update("update TMP_IMPORT_STATUS set STATUS="+Constant.TMP_IMPORT_STATUS_02+",UPDATE_DATE=CURRENT TIMESTAMP where ITYPE=1 and STATUS="+Constant.TMP_IMPORT_STATUS_01+" and IMPORT_BY="+logonUser.getUserId(), null);			
           
            logger.info("------资源分配 调用存储过程(AREA_SMALL_GAP_INFO) 结束 用时："+String.format("%.2f",(System.currentTimeMillis()-startTime)*0.001)+"秒------");			
           
           /* dao.update("update TM_ALLOT_RESOURCE set AUTHOR_TYPE=10431003,AUDIT_TYPE=12302 where AUTHOR_TYPE=10431002 and ALLOT_DATE='"+allotDate+"'", null);
			dao.update("update TM_TOTAL_GAP set AUTHOR_TYPE=10431003 where AUTHOR_TYPE=10431002 and ALLOT_DATE='"+allotDate+"'", null);*/
            //如果上传分配时未分配到大区，则需要将该大区的数据置为审核通过
            List<Map> nlist = dao.findNoAllotNums(allotDate);
            for(int n = 0;n<nlist.size();n++){
            	Map<String,Object> map = nlist.get(n);
            	Long bigOrgId = new Long(map.get("ORG_ID").toString());
            	
            	//执行存储过程将数据分给小区下的经销商
 	            List<Object> inParam = new ArrayList<Object>();// 输入参数
 	            inParam.add(bigOrgId);
 	            inParam.add(allotDate);
 				//dao.callProcedure("DEALER_GAP_INFO",inParam,null);
 				doCallProcedureDealerGapInfo(inParam);
 				//将大区下所有的小区提交				
 				List<Map> slist=dao.findNoAllotNumsSmallArea(allotDate,bigOrgId);
 				for(int m = 0;m<slist.size();m++){
 					map = slist.get(m);
 					Long smallOrgId = new Long(map.get("ORG_ID").toString());
 					smallDao.updateAllot(allotDate,smallOrgId.toString());
 				}
 				
 				//将大区审核通过
 				bigDao.resourceAuditAgree(allotDate,bigOrgId.toString());
            }
           //add by huyu start
            logger.info("------资源分配 OTD发送邮件给大区------");			
    		Long org = loginInfo.getOrgId() ; 
    		String manageMail = "xw_yonyou@163.com";// test邮箱
    		String email = "";
    		String name = "";
    		String dealerName = "";
    		String orgId = "";
    		String filename [] = {};

    		List<Map> emailList = dao.findOTDAndAreaBigByEmail(org,allotDate);
    		/*List<Map<String,Object>> seriesList = dao.findSeriesName(allotDate);
    		for(Map<String,Object> map :seriesList){
    			seriesName += map.get("SERIES_NAME") == null ? "" : map.get("SERIES_NAME").toString()+',';
    		}
    		seriesName = seriesName.substring(0, seriesName.length()-1);*/
    		for(Map<String,Object> map :emailList){
        		String seriesName = "";
    			name = map.get("NAME") == null ? "" : map.get("NAME").toString();
    			email = manageMail;//map.get("EMAIL") == null ? manageMail : map.get("EMAIL").toString();
    			dealerName = map.get("BIGORG") == null ? "" : map.get("BIGORG").toString();
    			orgId = map.get("ORG_ID") == null ? "" : map.get("ORG_ID").toString();
    			List<Map> seriesList = dao.findSeriesName(allotDate,orgId);
    			if(seriesList.size()>0){
	        		for(Map<String,Object> map1 :seriesList){
	        			seriesName += map1.get("SERIES_NAME") == null ? "" : map1.get("SERIES_NAME").toString()+',';
	        		}
	        		seriesName = seriesName.substring(0, seriesName.length()-1);
//	    			mailToOTDAndAreaBig(manageMail, email, filename,dealerName,name,seriesName);
    			}
    		}
    		//end by huyu
//			act.setOutData("returnValue", 1);
		} catch (Exception e) {			
			e.printStackTrace();
			ServiceBizException e1 = new ServiceBizException("OTD资源分配");
//			e1.setMessage("OTD资源分配出错,请联系管理员");
			logger.error(e1.toString());
//			act.setException(e1);
		} finally{           
        	//POContext.beginTxn(DBService.getInstance().getDefTxnManager(), -1);      	
			//dao.update("update TMP_IMPORT_STATUS set STATUS="+Constant.TMP_IMPORT_STATUS_02+",UPDATE_DATE=CURRENT TIMESTAMP where ITYPE=1 and STATUS="+Constant.TMP_IMPORT_STATUS_01+" and IMPORT_BY="+logonUser.getUserId(), null);			
			//POContext.endTxn(true);
           // POContext.cleanTxn();         
		}
		return null;
	}
	/**
	 * 执行存储过程将数据分给小区下的经销商
	 * @param inParam
	 */
	private void doCallProcedureDealerGapInfo(List<Object> inParam) throws Exception{
		Long orgId = Long.parseLong(CommonUtils.checkNull(inParam.get(0),"0"));
		String allotDate = CommonUtils.checkNull(inParam.get(1));
		int org_total_gap =1;
		int[] t_array = null;
		int[] a_array = null;
		int[] v_array = null;
		int[] d_array = null;
		int[] r_array = null;
		int[] v_series_array = null;
		int[] r_series_array = null;
		int[] v_group_array = null;
		int[] r_group_array = null;
		int[] v_color_array = null;
		int[] r_color_array = null;
		Long[] group_id_array = null;
		float a_rate = 0;
		int area_num = 0;
		int temp = 0;
		int temp_num = 0;
		int gap_total_num = 0;
		int gap_allot_num = 0;
		int gap_temp_num = 0;
		int dealer_gap_num = 0;
		double max_arr = 0;
		double temp_arr = 0;
		double temp_arr2 = 0;
		double temp_arr3 = 0;
		int m = 0;
		int gap_total = 0;
		int gap_temp = 0;
		int gap_temp_flag = 0;
		Long s_org_id = 0L;
		Long s_series_id = 0L;
		int series_total_num = 0;
		int series_temp_num = 0;
		int t_temp_num = 0;
		int temp_allot_num = 0;
		String month_day_start = "";
		String month_day_end = "";
		Date nowDate = new Date();
		Calendar c = Calendar.getInstance();
		Integer year = c.get(Calendar.YEAR);
		Integer month = c.get(Calendar.MONTH)+1;
		Integer day = c.get(Calendar.DAY_OF_MONTH)+1;
		int playVer = 0;
		double t_org_id = 0;
		int org_allot_num = 0;
		int allot_num = 0;
		int total_gap_num = 0;
		int total_allot_num = 0;
		int temp_gap_min_num = 0;
		int temp_dealer_num = 0;
		String s_series_name = "";
		String s_color_code = "";
		int s_num = 0;
		int s36_flag = 0;
		List<Map> cur_total = dao.getCursorTotal1(inParam);
		List<Map> cur_series = dao.getCursorSeries1(inParam);
		List<Map> cur_series2 = dao.getCursorSeries2(inParam);
	    month_day_start=year+"-"+month+"-01";
	    month_day_end=year+"-"+(month+1)+"-01"; 
	    if (month==12) {
	    	month_day_end=(year+1)+"-01-01";
	    }
	  //获取最大月计划任务版本
	  playVer = dao.getMaxPalyVer(year, month);
	  for (Map curTotal: cur_total) {//循环第一个游标
		t_org_id = Double.parseDouble(CommonUtils.checkNull(curTotal.get("ALLOT_TARGET_ID"),"0"));
		org_allot_num = Integer.parseInt(CommonUtils.checkNull(curTotal.get("ALLOT_NUM"),"0"));
		try {
			for (Map series : cur_series) {//循环第二个游标
				s_org_id = Long.parseLong(CommonUtils.checkNull(series.get("ALLOT_TARGET_ID"),"0"));
				s_series_id= Long.parseLong(CommonUtils.checkNull(series.get("GROUP_ID"),"0"));
				s_series_name = CommonUtils.checkNull(series.get("GROUP_NAME"));
				s_num = Integer.parseInt(CommonUtils.checkNull(series.get("NUM"),"0"));
				try {
					if(s_org_id==(long)t_org_id){
						temp_num = 0;
						gap_total = 0;
						dao.save2(month_day_start, month_day_end, year, month,s_org_id, s_series_id, inParam, playVer);
					}
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			for (Map series2 : cur_series2) {//循环第三个游标
				s_org_id = Long.parseLong(CommonUtils.checkNull(series2.get("ALLOT_TARGET_ID"),"0"));
				s_series_id= Long.parseLong(CommonUtils.checkNull(series2.get("GROUP_ID"),"0"));
				s_series_name = CommonUtils.checkNull(series2.get("GROUP_NAME"));
				s_num = Integer.parseInt(CommonUtils.checkNull(series2.get("NUM"),"0"));
				try {
					List<Map> temp1 = dao.getTemp1(allotDate,s_series_id);
					for (Map map : temp1) {
						dao.exceBatch1(allotDate,s_series_id);
					}
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			List<Map> temp2 = dao.getTemp2(year,month, playVer,t_org_id);
			for (Map map : temp2) {
				Integer salesAmount = Integer.parseInt(CommonUtils.checkNull(map.get("SALE_AMOUNT"),"0"));
				Long dealerId = Long.parseLong(CommonUtils.checkNull(map.get("DEALER_ID"),"0"));
				dao.exceBatch2(dealerId,salesAmount,orgId,allotDate);
			}
			List<Map> temp3 = dao.getTemp3(orgId,allotDate);
			for (Map map : temp3) {
				dao.exceBatch3(map,orgId,allotDate);
			}
			List<Map> temp4 = dao.getTemp4(orgId,allotDate);
			for (Map map : temp4) {
				Long groupId = Long.parseLong(CommonUtils.checkNull(map.get("GROUP_ID"),"0"));
				Long org_Id = Long.parseLong(CommonUtils.checkNull(map.get("ORG_ID"),"0"));
				List<Map> temp5 = dao.getTemp5(org_Id,orgId,groupId,allotDate);
				for (Map map2 : temp5) {
					Long tmGapId = Long.parseLong(CommonUtils.checkNull(map.get("TM_GAP_ID"),"0"));
					dao.exceBatch4(tmGapId);
				}
			}
			for (Map series : cur_series) {//第二次循环第二个游标
				s_org_id = Long.parseLong(CommonUtils.checkNull(series.get("ALLOT_TARGET_ID"),"0"));
				s_series_id= Long.parseLong(CommonUtils.checkNull(series.get("GROUP_ID"),"0"));
				s_series_name = CommonUtils.checkNull(series.get("GROUP_NAME"));
				s_num = Integer.parseInt(CommonUtils.checkNull(series.get("NUM"),"0"));
				try {
					if(s_org_id==(long)t_org_id){
						List<Map> temp6 = dao.getTemp6(t_org_id,s_series_id,allotDate);
						for (Map map : temp6) {
							Long groupId = Long.parseLong(CommonUtils.checkNull(map.get("GROUP_ID"),"0"));
							Integer num = Integer.parseInt(CommonUtils.checkNull(map.get("NUM"),"0"));
							List<Map> temp7 =dao.getTemp7(t_org_id,s_series_id,groupId,allotDate);
							for (Map map2 : temp7) {
								Long groupId2 = Long.parseLong(CommonUtils.checkNull(map.get("GROUP_ID"),"0"));
								String colorName = CommonUtils.checkNull(map.get("COLOR_NAME"));
								//删除状态为10431005的数据
								dao.exceBatch5();
								dao.exceBatch6(map2,s_org_id,orgId,s_series_id,s_series_name,groupId,allotDate);
								temp_num = 0;
								gap_temp = 0;
								gap_temp_flag = 1;
								List<Map> temp8 = dao.getTemp8(s_series_id,s_org_id,allotDate,orgId);
								v_color_array =  new int[temp8.size()+1];
								r_color_array =  new int[temp8.size()+1];
								group_id_array =  new Long[temp8.size()+1];
								for (Map map3 : temp8) {
									Long targetId = Long.parseLong(CommonUtils.checkNull(series.get("TARGET_ID"),"0"));
									Integer gap = Integer.parseInt(CommonUtils.checkNull(series.get("GAP"),"0"));
									gap_total = dao.getGapTotal3(orgId, targetId,s_series_id, s_org_id, allotDate);
									temp_num=temp_num+1; 
									if(gap_total==0){
										v_color_array[temp_num] = 0;
										r_color_array[temp_num] = 0;
										group_id_array[temp_num] = targetId;
										gap_temp = 0;
										gap_temp_flag = 0; 
									}
									if(gap_total>0){
										dealer_gap_num = gap;
										v_color_array[temp_num] = dealer_gap_num*num/gap_total; 
										r_color_array[temp_num] = dealer_gap_num*num/gap_total-v_color_array[temp_num];
										group_id_array[temp_num] = targetId;
										gap_temp = gap_temp+v_color_array[temp_num];
									}
								}
								temp = 0;
								while (temp<temp_num){
									temp = temp+1; 
									m = 0; 
									while(m<temp_num-1){
										m = m+1;
										if(r_color_array[m]<r_color_array[m+1]){
											temp_arr = r_color_array[m+1];
                                            r_color_array[m+1] = r_color_array[m];
                                            r_color_array[m] = (int) temp_arr;

                                            temp_arr2 = v_color_array[m+1];
                                            v_color_array[m+1] = v_color_array[m];
                                            v_color_array[m] = (int) temp_arr2;

                                            temp_arr3 = group_id_array[m+1];
                                            group_id_array[m+1] = group_id_array[m];
                                            group_id_array[m] = (long) temp_arr3;
										}
									}
								}
								temp = 0;
								if(gap_temp_flag>0){
									if(num-gap_temp >0){
										while(temp<num-gap_temp){
											temp = temp+1; 
											v_color_array[temp] = v_color_array[temp]+1;
										}
										temp = 0;
										while(temp<temp_num){
											temp = temp+1; 
										}
									}
								}
								temp = 0;
								while(temp<temp_num){
									temp = temp+1;
									List<Map> temp9 = dao.getTemp9(group_id_array[temp],s_org_id,s_series_id,allotDate,orgId);
									if(total_allot_num+v_color_array[temp] > total_gap_num ){
										t_temp_num = v_color_array[temp];
										v_color_array[temp] = total_gap_num-total_allot_num;
										t_temp_num = t_temp_num-(total_gap_num-total_allot_num); 
										TmAllotGroupTempPO po = new TmAllotGroupTempPO();
										po.setInteger("ALLOT_TYPE", 10431005);
										po.setLong("TARGET_ID", group_id_array[temp]);
										po.setLong("SERIES_ID", s_series_id);
										po.setLong("GROUP_ID", groupId2);
										po.setString("COLOR_NAME", colorName);
										po.setInteger("NUM", t_temp_num);
										po.setString("ALLOT_DATE", allotDate);
										po.setLong("CREATE_BY", orgId);
										po.setTimestamp("CREATE_DATE", nowDate);
										po.saveIt();
									}
									TmAllotResourcePO po = new TmAllotResourcePO();
									po.setInteger("ALLOT_TYPE", 10431005);
									po.setLong("TARGET_ID", group_id_array[temp]);
									po.setLong("GROUP_ID", groupId2);
									po.setString("COLOR_NAME", colorName);
									po.setInteger("ALLOT_NUM", v_color_array[temp]);
									po.setInteger("ADJUST_NUM", v_color_array[temp]);
									po.setInteger("ALLOT_STATUS", 0);
									po.setInteger("AUDIT_TYPE", 12303);
									po.setInteger("AUDIT_STATUS", 0);
									po.setInteger("IS_ORDER", 0);
									po.setString("ALLOT_DATE", allotDate);
									po.setInteger("AUTHOR_TYPE", 10431005);
									po.setLong("CREATE_BY", orgId);
									po.setTimestamp("CREATE_DATE", nowDate);
									po.saveIt();
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			break;
		}
	  }
	  List<Map> temp10 = dao.getTemp10(orgId,allotDate);
	  for (Map map : temp10) {
		String seriesId = CommonUtils.checkNull(map.get("SERIES_ID"));
		String colorName = CommonUtils.checkNull(map.get("COLOR_NAME"));
		Long org_id = Long.parseLong(CommonUtils.checkNull(map.get("ORG_ID"),"0"));
		Integer num = Integer.parseInt(CommonUtils.checkNull(map.get("NUM"), "0"));
		temp_num = 0;
		gap_temp = 0;
		List<Map> temp11 =dao.getTemp11(seriesId,orgId,allotDate,org_id);
		v_color_array =  new int[temp11.size()+1];
		r_color_array =  new int[temp11.size()+1];
		group_id_array =  new Long[temp11.size()+1];
		for (Map map2 : temp11) {
			Integer gap = Integer.parseInt(CommonUtils.checkNull(map.get("GAP"), "0"));
			Long targetId = Long.parseLong(CommonUtils.checkNull(map.get("TARGET_ID"), "0"));
			temp_num=temp_num+1;
			gap_total = dao.getGapTotal4(org_id, orgId,allotDate, seriesId);
			v_color_array[temp_num] = gap*num/gap_total;
			r_color_array[temp_num] = gap*num/gap_total-v_color_array[temp_num];
			group_id_array[temp_num] = targetId;
			gap_temp = gap_temp+v_color_array[temp_num];   
		}
		temp = 0;
		while (temp<temp_num) {
			temp = temp+1;
			m = 0; 
			while (m<temp_num-1) {
				m = m+1;
				if(r_color_array[m]<r_color_array[m+1]){
					temp_arr = r_color_array[m+1];
                    r_color_array[m+1] = r_color_array[m];
                    r_color_array[m] = (int) temp_arr;

                    temp_arr2 = v_color_array[m+1];
                    v_color_array[m+1] = v_color_array[m];
                    v_color_array[m] = (int) temp_arr2;

                    temp_arr3 = group_id_array[m+1];
                    group_id_array[m+1] = group_id_array[m];
                    group_id_array[m] = (long) temp_arr3;
				}
			}
		}
		temp = 0;
		if(num-gap_temp >0){
			while(temp<num-gap_temp){
				temp = temp+1; 
				v_color_array[temp] = v_color_array[temp]+1; 
			}
			temp = 0;
			while (temp<temp_num) {
				temp = temp+1; 
			}
		}
		temp = 0;
		while(temp<temp_num){
			temp = temp+1; 
			temp_arr = 0;
			temp_arr = dao.getTempArr(group_id_array[temp],seriesId,colorName,orgId,allotDate);
			if(temp_arr>0){
				dao.exceBatch7(v_color_array[temp],group_id_array[temp],seriesId,colorName,orgId,allotDate);
			}
			if(temp_arr==0){
				TmAllotResourcePO po = new TmAllotResourcePO();
				po.setInteger("ALLOT_TYPE", 10431005);
				po.setLong("TARGET_ID", group_id_array[temp]);
				po.setLong("GROUP_ID", seriesId);
				po.setString("COLOR_NAME", colorName);
				po.setInteger("ALLOT_NUM", v_color_array[temp]);
				po.setInteger("ADJUST_NUM", v_color_array[temp]);
				po.setInteger("ALLOT_STATUS", 0);
				po.setInteger("AUDIT_TYPE", 12303);
				po.setInteger("AUDIT_STATUS", 0);
				po.setInteger("IS_ORDER", 0);
				po.setString("ALLOT_DATE", allotDate);
				po.setInteger("AUTHOR_TYPE", 10431005);
				po.setLong("CREATE_BY", orgId);
				po.setTimestamp("CREATE_DATE", nowDate);
				po.saveIt();
			}
		}
	 }
	 dao.exceBatch8(orgId,allotDate);
	 
	 List<Map> temp12 = dao.getTemp12();
	 for (Map map : temp12) {
		 s36_flag = 1;
	 }
	 if(s36_flag==0){
		 List<Map> temp13 = dao.getTemp13(orgId,allotDate);
		 for (Map map : temp13) {
			dao.exceBatch9(map,allotDate);
		}
	 }
	 dao.exceBatch10(allotDate);
	 dao.exceBatch11(allotDate);
	 TmAllotResourceLogPO logPO = new TmAllotResourceLogPO();
	 logPO.setString("PRO_NAME", "DEALER_GAP_INFO");
	 logPO.setInteger("ALLOT_TYPE", 10431005);
	 logPO.setInteger("STATUS", 1);
	 logPO.setString("ERROR_INFO", "");
	 logPO.setLong("CREATE_BY", orgId);
	 logPO.setTimestamp("CREATE_DATE", nowDate);
	 logPO.saveIt();
		
	}

	/**
	 * 资源分配 调用存储过程(AREA_SMALL_GAP_INFO)
	 * @param inParameter
	 */
	private void doCallPrcedureAreaSmallGapInfo(List<Object> inParameter) throws Exception{
		int org_total_gap =1 ;
		int[] t_array = null;
		int[] o_array = null;
		int[] a_array = null;
		int[] v_array = null;
		int[] d_array = null;
		int[] r_array = null;
		int[] v_series_array = null;
		int[] r_series_array = null;
		int[] v_group_array = null;
		int[] r_group_array = null;
		double[] v_color_array = null;
		double[] r_color_array = null;
		double[] group_id_array = null;
		float a_rate = 0;
		int temp = 0;
		int temp_num = 0;
		int temp_allot_num = 0;
		int gap_total_num = 0;
		int gap_temp_num = 0;
		double max_arr = 0;
		double temp_arr = 0;
		double temp_arr2 = 0;
		double temp_arr3 = 0;
		int m = 0;
		int gap_total = 0;
		int gap_temp = 0;
		int gap_temp_flag = 0;
		Long s_org_id = 0L;
		Long s_series_id = 0L;
		int series_total_num = 0;
		int series_temp_num = 0;
		int t_temp_num = 0;
		String month_day_start = "";
		String month_day_end = "";
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		Integer year = c.get(Calendar.YEAR);
		Integer month = c.get(Calendar.MONTH)+1;
		Integer day = c.get(Calendar.DAY_OF_MONTH)+1;
		int playVer = 0;
		double t_org_id = 0;
		int org_allot_num = 0;
		int allot_num = 0;
		int total_gap_num = 0;
		int total_allot_num = 0;
		String s_series_code = "";
		String s_color_code = "";
		int s_num = 0;
		int s_status = 0;
		int s36_flag = 0;
		List<Map> cur_total = dao.getCursorTotal(inParameter);
		List<Map> cur_series = dao.getCursorSeries(inParameter);
		
		month_day_start=year+"-"+month+"-01";
		month_day_end=year+"-"+(month+1)+"-01"; 
		if(month==12){
			month_day_end=(year+1)+"-01-01";
		}
		//获取最大月计划任务版本
		playVer = dao.getMaxPalyVer(year, month);
		Long userId = Long.parseLong(CommonUtils.checkNull(inParameter.get(0)));
		String allotDate = CommonUtils.checkNull(inParameter.get(1));
		
		for(Map totalMap:cur_total){
			t_org_id = Double.parseDouble(CommonUtils.checkNull(totalMap.get("ALLOT_TARGET_ID"),"0"));
			org_allot_num = Integer.parseInt(CommonUtils.checkNull(totalMap.get("ALLOT_NUM"),"0"));
			try {
				for(Map series:cur_series){
					s_org_id = Long.parseLong(CommonUtils.checkNull(series.get("ALLOT_TARGET_ID"),"0"));
					s_series_id= Long.parseLong(CommonUtils.checkNull(series.get("SERIES_ID"),"0"));
					s_num = Integer.parseInt(CommonUtils.checkNull(series.get("NUM"),"0"));
					s_status =Integer.parseInt(CommonUtils.checkNull(series.get("STATUS"),"0"));
					try {
						if(s_org_id==t_org_id){
							temp_num = 0 ;
							gap_total = 0;
							dao.save1(s_status,month_day_start, month_day_end, year, month, s_series_id,s_org_id, inParameter, playVer);
						}
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
				//一转
				List<Map> temp1List = dao.getTemp1List(userId,allotDate);
				for (Map map : temp1List) {
					Integer SALE_AMOUNT = Integer.parseInt(CommonUtils.checkNull(map.get("SALE_AMOUNT"),"0"));
					Long TARGET_ID = Long.parseLong(CommonUtils.checkNull(map.get("TARGET_ID"),"0"));
					dao.exceBatch1Temp(SALE_AMOUNT,TARGET_ID,userId,allotDate);
				}
				//tmp  2转
				List<Map> temp2List = dao.getTemp2List(userId,allotDate);
				for(Map map : temp1List){
					Integer SALE_AMOUNT = Integer.parseInt(CommonUtils.checkNull(map.get("SALE_AMOUNT"),"0"));
					Long TARGET_ID = Long.parseLong(CommonUtils.checkNull(map.get("TARGET_ID"),"0"));
					dao.exceBatch2Temp(map,userId,allotDate);
				}
				//temp3
				List<Map> temp3List = dao.getTemp3list(userId,allotDate);
				for (Map map : temp3List) {
					Long groupId = Long.parseLong(CommonUtils.checkNull(map.get("GROUP_ID"),"0"));
					Long parentOrgId = Long.parseLong(CommonUtils.checkNull(map.get("PARENT_ORG_ID"),"0"));
					List<Map> temp4List = dao.getTemp4List(allotDate,groupId,parentOrgId);
					for (Map map2 : temp4List) {
						Long tmpGapId = Long.parseLong(CommonUtils.checkNull(map.get("TM_GAP_ID"),"0"));
						dao.exceBatch1Temp(tmpGapId);
					}
				}
				//二开游标series
				for(Map series2:cur_series){
					s_org_id = Long.parseLong(CommonUtils.checkNull(series2.get("ALLOT_TARGET_ID"),"0"));
					s_series_id= Long.parseLong(CommonUtils.checkNull(series2.get("SERIES_ID"),"0"));
					s_num = Integer.parseInt(CommonUtils.checkNull(series2.get("NUM"),"0"));
					s_status =Integer.parseInt(CommonUtils.checkNull(series2.get("STATUS"),"0"));
					try {
						if(s_org_id==t_org_id && s_status==1){
							List<Map> temp5List = dao.getTemp5List(userId,allotDate,s_org_id,s_series_id);
							//嵌套循环1
							for (Map map : temp5List) {
								String groupId = CommonUtils.checkNull(map.get("GROUP_ID"));
								List<Map> temp6List = dao.getTemp6List(groupId,userId,allotDate,s_org_id,s_series_id);
								//嵌套循环2
								for (Map map2 : temp6List) {
									String groupId2 = CommonUtils.checkNull(map.get("GROUP_ID"));
									String colorName = CommonUtils.checkNull(map.get("COLOR_NAME"));
									Integer num = Integer.parseInt(CommonUtils.checkNull(map2.get("NUM"),"0"));
									temp_num = 0;
									gap_temp = 0;
									gap_temp_flag = 1;
									List<Map> temp7List = dao.getTemp7List(month_day_start,month_day_end,userId,allotDate,s_org_id,s_series_id);
									v_color_array =  new double[temp7List.size()+1];
									r_color_array =  new double[temp7List.size()+1];
									group_id_array =  new double[temp7List.size()+1];
									//嵌套循环3
									for (Map map3 : temp7List) {
										Long targetId = Long.parseLong(CommonUtils.checkNull(series2.get("TARGET_ID"),"0"));
										Integer gap  = Integer.parseInt(CommonUtils.checkNull(series2.get("GAP"),"0"));
										gap_total = dao.getGapTotal(userId,allotDate,s_org_id,s_series_id);
										
										temp_num=temp_num+1;
										if(gap_total==0){
											v_color_array[temp] = 0;
											r_color_array[temp_num] = 0;
											group_id_array[temp_num] = targetId;
											gap_temp_flag = 0;
										}else if(gap_total >0){
											v_color_array[temp_num] = gap*num/gap_total;
											r_color_array[temp_num] = gap*num/gap_total-v_color_array[temp_num];
											group_id_array[temp_num] = targetId;
											gap_temp = (int) (gap_temp+v_color_array[temp_num]); 
										}
									}
									temp = 0;
									while (temp<temp_num){
										temp = temp+1;   
										m = 0 ;
										while(m<temp_num-1){
											m = m+1;
											 if (r_color_array[m]<r_color_array[m+1]) {
												  temp_arr = r_color_array[m+1];
												  r_color_array[m+1] = r_color_array[m];
												  r_color_array[m] = temp_arr;
												 
												  temp_arr2 = v_color_array[m+1];
												  v_color_array[m+1] = v_color_array[m];
												  v_color_array[m] = temp_arr2;
												 
												  temp_arr3 = group_id_array[m+1];
												  group_id_array[m+1] = group_id_array[m];
												  group_id_array[m] = temp_arr3;
											 }
										}
									}
									temp = 0;
									if(gap_temp_flag>0){
										if(num-gap_temp >0){
											while(temp<num-gap_temp){
												temp = temp+1; 
												v_color_array[temp] = v_color_array[temp]+1; 
											}
											while(temp<temp_num){
												temp = temp+1; 
											}
										}
									}
									temp = 0;
									while(temp<temp_num){
										temp = temp+1;  
										double group_Id = group_id_array[temp];
										List<Map> temp8List = dao.getTemp8List(group_Id,userId,allotDate,s_series_id);
										if(total_allot_num+v_color_array[temp] > total_gap_num){
											t_temp_num = (int) v_color_array[temp];
											v_color_array[temp] = total_gap_num-total_allot_num;
											t_temp_num = t_temp_num-(total_gap_num-total_allot_num); 
											TmAllotGroupTempPO po  = new TmAllotGroupTempPO();
											po.setInteger("ALLOT_TYPE", 10431004);
											po.setLong("TARGET_ID", (long) group_id_array[temp]);
											po.setLong("SERIES_ID", s_series_id);
											po.setLong("GROUP_ID",Long.parseLong(groupId2));
											po.setString("COLOR_NAME", colorName);
											po.setInteger("NUM", t_temp_num);
											po.setString("ALLOT_DATE", allotDate);
											po.setLong("CREATE_BY", userId);
											po.setTimestamp("CREATE_DATE", new Date());
											po.saveIt();
										}
										TmAllotResourcePO po = new TmAllotResourcePO();
										po.setInteger("ALLOT_TYPE",10431003);
										po.setLong("ALLOT_TARGET_ID",group_id_array[temp]);
										po.setLong("GROUP_ID",Long.parseLong(groupId2));
										po.setString("COLOR_NAME",colorName);
										po.setInteger("ALLOT_NUM",v_color_array[temp]);
										po.setInteger("ADJUST_NUM",v_color_array[temp]);
										po.setInteger("ALLOT_STATUS",0);
										po.setInteger("AUDIT_TYPE",12302);
										po.setInteger("AUDIT_STATUS",0);
										po.setInteger("IS_ORDER",0);
										po.setString("ALLOT_DATE",allotDate);
										po.setInteger("AUTHOR_TYPE",10431004);
										po.setLong("CREATE_BY",userId);
										po.setTimestamp("CREATE_DATE", now);
										po.saveIt();
									}
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		//游标结束
		List<Map> temp9List = dao.getTemp9List(userId,allotDate);
		for (Map map : temp9List) {
			temp_num = 0;
			gap_temp = 0;
			Long seriesId = Long.parseLong(CommonUtils.checkNull(map.get("SERIES_ID"),"0"));
			Long parentOrgId = Long.parseLong(CommonUtils.checkNull(map.get("PARENT_ORG_ID"),"0"));
			Integer num = Integer.parseInt(CommonUtils.checkNull(map.get("NUM"),"0"));
			String colorName = CommonUtils.checkNull(map.get("COLOR_NAME"));
			List<Map> temp10List = dao.getTemp10List(userId,allotDate,seriesId,parentOrgId);
			v_color_array =  new double[temp10List.size()+1];
			r_color_array =  new double[temp10List.size()+1];
			group_id_array =  new double[temp10List.size()+1];
			for (Map map2 : temp10List) {
				Integer gap = Integer.parseInt(CommonUtils.checkNull(map.get("GAP"),"0"));
				Long target = Long.parseLong(CommonUtils.checkNull(map.get("TARGET_ID"),"0"));
				temp_num=temp_num+1;
				gap_total = dao.getGapTotal2(userId, allotDate, seriesId, parentOrgId);
				v_color_array[temp_num] = gap*num/gap_total;
				r_color_array[temp_num] = gap*num/gap_total-v_color_array[temp_num];
				group_id_array[temp_num] = target;
				gap_temp = (int) (gap_temp+v_color_array[temp_num]);  
			}
			temp = 0;
			while(temp<temp_num){
				temp = temp+1;
				m = 0 ;
				while (m<temp_num-1){
					m = m+1;
					if(r_color_array[m]<r_color_array[m+1]){
						temp_arr = r_color_array[m+1];
						r_color_array[m+1] = r_color_array[m];
						r_color_array[m] = temp_arr;
						
						temp_arr2 = v_color_array[m+1];
						v_color_array[m+1] = v_color_array[m];
						v_color_array[m] = temp_arr2;
						
						temp_arr3 = group_id_array[m+1];
						group_id_array[m+1] = group_id_array[m];
						group_id_array[m] = temp_arr3;
					}
				}
			}
			temp = 0;
			if(num-gap_temp >0){
				while(temp<num-gap_temp){
					temp = temp+1; 
					v_color_array[temp] = v_color_array[temp]+1;
				}
				temp = 0;
				while(temp<temp_num){
					temp = temp+1;
				}
			}
			temp = 0;
			while(temp<temp_num){
				temp = temp+1;
				temp_arr = 0;  
				temp_arr = dao.getTempArr(group_id_array[temp],userId, allotDate, seriesId, parentOrgId,colorName);
				if(temp_arr>0){
					dao.excebatch2Temp(v_color_array[temp],group_id_array[temp],seriesId,colorName,userId, allotDate);
				}else if(temp_arr==0){
					TmAllotResourcePO po = new TmAllotResourcePO();
					po.setInteger("ALLOT_TYPE",10431003);
					po.setLong("ALLOT_TARGET_ID",group_id_array[temp]);
					po.setLong("GROUP_ID",seriesId);
					po.setString("COLOR_NAME",colorName);
					po.setInteger("ALLOT_NUM",v_color_array[temp]);
					po.setInteger("ADJUST_NUM",v_color_array[temp]);
					po.setInteger("ALLOT_STATUS",0);
					po.setInteger("AUDIT_TYPE",12302);
					po.setInteger("AUDIT_STATUS",0);
					po.setInteger("IS_ORDER",0);
					po.setString("ALLOT_DATE",allotDate);
					po.setInteger("AUTHOR_TYPE",10431004);
					po.setLong("CREATE_BY",userId);
					po.setTimestamp("CREATE_DATE", now);
					po.saveIt();
				}
			}
		}
		List<Map> temp11List = dao.getTemp11List();
		for (Map map : temp11List) {
			s36_flag = 1;
		}
		if(s36_flag==0){
			List<Map> temp12List = dao.getTemp12List(userId,allotDate);
			for (Map map : temp12List) {
				dao.exceBatch3Temp(map,userId,allotDate);
			}
		}
		dao.exceBatch4Temp(allotDate);
		dao.exceBatch5Temp(allotDate);
		TmAllotResourceLogPO logPO = new TmAllotResourceLogPO();
		logPO.setString("PRO_NAME", "AREA_SMALL_GAP_INFO");
		logPO.setInteger("ALLOT_TYPE", 10431004);
		logPO.setInteger("STATUS", 1);
		logPO.setString("ERROR_INFO", "");
		logPO.setLong("CREATE_BY", Long.parseLong(inParameter.get(0).toString()));
		logPO.setTimestamp("CREATE_DATE", now);
		logPO.saveIt();
	}

	/**
	 * 资源分配邮件
	 * @param manageMail
	 * @param email
	 * @param filename[]
	 * @user : huyu
	 * @date : 2014-12-16
	 */
	public void mailToOTDAndAreaBig(String manageMail,String email,String filename [],String dealerName,String name,String seriesName){
		//OTD发送邮件给大区
//		String dealerName = "sales";
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		String nowDate = df.format(new Date());// new Date()为获取当前系统时间
		String[] emails = email.split(",");
		String subject = nowDate+" 资源分配提醒";
		StringBuffer body = new StringBuffer("");
		body.append(name).append(":").append(dealerName).append("有未分配的资源，请使用系统分配！(时间:").append(nowDate).append(",车系:").append(seriesName).append(")");
        logger.info("------资源分配 OTD发送邮件给大区------"+name+":"+dealerName);	
		if (email == null || email.trim().equals("")) {
			logger.error(dealerName + " 邮箱地址为空，邮件发送失败！");
		}else if (!MailSender.sendMail(emails, subject,
				body.toString(), filename)) {
			try {
				logger.error(dealerName + " 邮箱地址：" + email + "    邮件发送失败！");
				MailSender.sendMail(manageMail, subject + "  错误：" + dealerName + " 邮箱地址："
						+ email + "    邮件发送失败！", body.toString(),
						null);
				throw new ServiceBizException(dealerName + "    " + email
						+ "邮件发送失败！");
			} catch (ServiceBizException ee) {
				logger.error("邮件发送失败！");
			}
		}else
			logger.debug(dealerName + " 邮箱地址：" + email+ "    邮件发送成功！");
	}

}
