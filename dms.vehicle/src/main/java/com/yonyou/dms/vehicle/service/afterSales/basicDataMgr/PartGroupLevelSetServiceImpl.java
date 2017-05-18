package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr.PartGroupLevelSetDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtPartGroupLevelSetTempDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtPartGroupLevelSetPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtPartGroupLevelSetTempPO;
/**
 * 配件分组级别设定
 * @author Administrator
 *
 */
@Service
public class PartGroupLevelSetServiceImpl  implements PartGroupLevelSetService{
		@Autowired
		PartGroupLevelSetDao    partGroupLevelSetDao;
		private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
		/**
		 * 分组级别设定查询
		 */
		@Override
		public PageInfoDto LevelSetQuery(Map<String, String> queryParam) {
			// TODO Auto-generated method stub
			return partGroupLevelSetDao.LevelSetQuery(queryParam);
		}
		
		//导入数据到业务表
		@Override
		public void importSaveAndDelete() {
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			List<Map> list = partGroupLevelSetDao.findTmpRecallVehicleDcsList();
		   for(Map map : list){
			if (map.get("DATA_STATUS").equals("insert")) {
				TtPartGroupLevelSetPO trvdPo = new TtPartGroupLevelSetPO();
				trvdPo.set("FAMIGLIA_FAMIGLIA", map.get("FAMIGLIA_FAMIGLIA"));
				trvdPo.set("FAMIGLIA_FAMIGLIA_DESC", map.get("FAMIGLIA_FAMIGLIA_DESC"));
				trvdPo.set("ITEM_NO", map.get("ITEM_NO"));
				trvdPo.set("DATA_STATUS",0);    //0说明这个组别需要更新预警信息
				trvdPo.set("CREATE_BY", loginInfo.getUserId());
				trvdPo.set("CREATE_DATE", new Date(System.currentTimeMillis()));
				trvdPo.saveIt();
			}else if (map.get("DATA_STATUS").equals("update")) {
				//通过组别查找业务表po
				TtPartGroupLevelSetPO savePo =TtPartGroupLevelSetPO.findFirst("FAMIGLIA_FAMIGLIA=?", map.get("FAMIGLIA_FAMIGLIA"));
				if (savePo.get("FAMIGLIA_FAMIGLIA").equals(map.get("FAMIGLIA_FAMIGLIA")) &&
						savePo.get("FAMIGLIA_FAMIGLIA_DESC").equals(map.get("FAMIGLIA_FAMIGLIA_DESC"))&&
						savePo.get("ITEM_NO").equals(map.get("ITEM_NO"))) {//组别  组别描述   级别   主表和临时表一样   不进行修改
					continue;
				}	
				TtPartGroupLevelSetPO trvd = new TtPartGroupLevelSetPO();
				trvd.set("FAMIGLIA_FAMIGLIA", map.get("FAMIGLIA_FAMIGLIA"));
				trvd.set("FAMIGLIA_FAMIGLIA_DESC", map.get("FAMIGLIA_FAMIGLIA_DESC"));
				trvd.set("ITEM_NO", map.get("ITEM_NO"));
				trvd.set("DATA_STATUS",0);    //0说明这个组别需要更新预警信息
				trvd.set("UPDATE_BY", loginInfo.getUserId());
				trvd.set("UPDATE_DATE", new Date(System.currentTimeMillis()));
				trvd.saveIt();
			}
			}
		}
		//查询临时表的数据
		@Override
		public List<Map> findTmpPartGroupLevelSetList() {
			List<Map> list = partGroupLevelSetDao.findTmpRecallVehicleDcsList();
			return list;
		}
		//删除临时表的数据
		@Override
		public void deleteTmpRecallVehicleDcs() {
			TtPartGroupLevelSetTempPO trvdPo = new TtPartGroupLevelSetTempPO();
			trvdPo.deleteAll();
		}
		
		//将数据保存到临时表中
		@Override
		public void saveTmpRecallVehicleDcs(TtPartGroupLevelSetTempDTO rowDto) {
			//获取当前用户
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			List<Map> updatePartGroupLevelSet = partGroupLevelSetDao.findUpdatePartGroupLevelSet(rowDto.getFamigliaFamiglia());
			//更新数据
			if (null != updatePartGroupLevelSet && updatePartGroupLevelSet.size()> 0){
				//找出要修改的数据
			for(Map listMap: updatePartGroupLevelSet){
		    String famiglia=listMap.get("FAMIGLIA_FAMIGLIA").toString();
			TtPartGroupLevelSetTempPO savePo = new TtPartGroupLevelSetTempPO();
		    TtPartGroupLevelSetPO Po =TtPartGroupLevelSetPO.findFirst("FAMIGLIA_FAMIGLIA=?", famiglia);
			savePo.set("FAMIGLIA_FAMIGLIA", Po.get("FAMIGLIA_FAMIGLIA"));
			savePo.set("FAMIGLIA_FAMIGLIA_DESC", Po.get("FAMIGLIA_FAMIGLIA_DESC"));
			savePo.set("ITEM_NO", Po.get("ITEM_NO"));
			savePo.set("CREATE_BY", loginInfo.getUserId());
			savePo.set("CREATE_DATE",Po.get("CREATE_DATE"));
			savePo.set("DATA_STATUS", "update");
			savePo.saveIt();
			}
		}else{
			TtPartGroupLevelSetTempPO save = new TtPartGroupLevelSetTempPO();
			save.set("FAMIGLIA_FAMIGLIA", rowDto.getFamigliaFamiglia());
			save.set("FAMIGLIA_FAMIGLIA_DESC", rowDto.getFamigliaFamigliaDesc());
			save.set("ITEM_NO", rowDto.getItemNo());
			save.set("CREATE_BY", loginInfo.getUserId());
			save.set("CREATE_DATE",rowDto.getCreateDate());
			save.set("DATA_STATUS", "insert");
			save.saveIt();
		}
		
		}
		
		
		
		
		//进行数据校验
		@Override
		public List<TtPartGroupLevelSetTempDTO> checkData() {
		
			ArrayList<TtPartGroupLevelSetTempDTO> resultDTOList = new ArrayList<TtPartGroupLevelSetTempDTO>();
			ImportResultDto<TtPartGroupLevelSetTempDTO> importResult = new ImportResultDto<TtPartGroupLevelSetTempDTO>();
			//查询临时表的数据
			List<Map>   temList = findTmpPartGroupLevelSetList();
			if(temList.size()>0){
				for(Map row : temList){
			if(StringUtils.isNullOrEmpty(row.get("FAMIGLIA_FAMIGLIA"))){
				TtPartGroupLevelSetTempDTO rowDto = new TtPartGroupLevelSetTempDTO();
				 rowDto.setRowNO(Integer.valueOf(row.get("ID").toString()));
				 rowDto.setErrorMsg("组别代码不能为空！");
				 resultDTOList.add(rowDto);				 
				}else
				if(row.get("FAMIGLIA_FAMIGLIA").toString().length()<8){
					TtPartGroupLevelSetTempDTO rowDto = new TtPartGroupLevelSetTempDTO();
					rowDto.setRowNO(Integer.valueOf(row.get("ID").toString()));
				    rowDto.setErrorMsg("组别代码不能大于或小于8字段!");
					resultDTOList.add(rowDto);				 	
				}
			if(StringUtils.isNullOrEmpty(row.get("FAMIGLIA_FAMIGLIA_DESC"))){
				TtPartGroupLevelSetTempDTO rowDto = new TtPartGroupLevelSetTempDTO();
				 rowDto.setRowNO(Integer.valueOf(row.get("ID").toString()));
				 rowDto.setErrorMsg(" 组别描述不能为空!");
				 resultDTOList.add(rowDto);				 
				}
			if(StringUtils.isNullOrEmpty(row.get("ITEM_NO"))){
				TtPartGroupLevelSetTempDTO rowDto = new TtPartGroupLevelSetTempDTO();
				 rowDto.setRowNO(Integer.valueOf(row.get("ID").toString()));
				 rowDto.setErrorMsg(" 预警级别不能为空!");
				 resultDTOList.add(rowDto);				 
				}
			if(StringUtils.isNullOrEmpty(row.get("CREATE_DATE"))){
				TtPartGroupLevelSetTempDTO rowDto = new TtPartGroupLevelSetTempDTO();
				 rowDto.setRowNO(Integer.valueOf(row.get("ID").toString()));
				 rowDto.setErrorMsg(" 创建日期不能为空!");
				 resultDTOList.add(rowDto);				 
				}
				}
			}		
			return resultDTOList;
		}
}
