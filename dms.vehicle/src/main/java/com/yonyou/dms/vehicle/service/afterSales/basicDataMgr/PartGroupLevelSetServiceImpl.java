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
import com.yonyou.dms.function.exception.ServiceBizException;
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
		
		/**
		 * 导入业务表，删除临时表数据
		 */
		@Override
		public void importSaveAndDelete() throws ServiceBizException {
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			List<Map> list = partGroupLevelSetDao.findTmpRecallVehicleDcsList();
			for(Map map : list){
				//更新数据
				TtPartGroupLevelSetPO trvdPo = new TtPartGroupLevelSetPO();
				trvdPo.set("FAMIGLIA_FAMIGLIA", map.get("FAMIGLIA_FAMIGLIA"));
				trvdPo.set("FAMIGLIA_FAMIGLIA_DESC", map.get("FAMIGLIA_FAMIGLIA_DESC"));
				trvdPo.set("ITEM_NO", map.get("ITEM_NO"));
				trvdPo.set("UPDATE_BY", loginInfo.getUserId());
				trvdPo.set("UPDATE_DATE", new Date());
				trvdPo.set("CREATE_BY", loginInfo.getUserId());
				trvdPo.set("CREATE_DATE", new Date());
				trvdPo.set("DATA_STATUS","INSERT");
				trvdPo.saveIt();
			}
			TtPartGroupLevelSetTempPO deletePo = new TtPartGroupLevelSetTempPO();
			deletePo.deleteAll();
			
		}

		/**
		 * 查询临时表的所有数据
		 */
		@Override
		public List<Map> findTmpPartGroupLevelSetList(Map<String, String> queryParam) {
			List<Map> list = partGroupLevelSetDao.findTmpRecallVehicleDcsList();
			return list;
		}


		
		
		/**
		 * 检查临时表数据
		 */
		@Override
			public ImportResultDto<TtPartGroupLevelSetTempDTO> checkData(TtPartGroupLevelSetTempDTO list) throws Exception {
				ImportResultDto<TtPartGroupLevelSetTempDTO> imp = new ImportResultDto<TtPartGroupLevelSetTempDTO>();
				boolean isError = false;
				ArrayList<TtPartGroupLevelSetTempDTO> err = new ArrayList<TtPartGroupLevelSetTempDTO>();

				// 写入表
				TtPartGroupLevelSetTempDTO trmPO = new TtPartGroupLevelSetTempDTO();
				// 校验
				if (!StringUtils.isNullOrEmpty(list.getFamigliaFamiglia())) {
					if (list.getFamigliaFamiglia().length()<8) {
						TtPartGroupLevelSetTempDTO rowDto = new TtPartGroupLevelSetTempDTO();
						rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
						rowDto.setErrorMsg("组别代码不能大于或小于8字段!");
						err.add(rowDto);
					} 
				} else {
					TtPartGroupLevelSetTempDTO rowDto = new TtPartGroupLevelSetTempDTO();
					rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
					rowDto.setErrorMsg("组别代码不能为空!");
					err.add(rowDto);
				}
				if (StringUtils.isNullOrEmpty(list.getFamigliaFamigliaDesc())) {
					TtPartGroupLevelSetTempDTO rowDto = new TtPartGroupLevelSetTempDTO();
					rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
					rowDto.setErrorMsg("组别描述不能为空!");
					err.add(rowDto);
				}
				if (!StringUtils.isNullOrEmpty(list.getItemNo())) {
					TtPartGroupLevelSetTempDTO rowDto = new TtPartGroupLevelSetTempDTO();
					rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
					rowDto.setErrorMsg("预警级别不能为空!");
					err.add(rowDto);
				}
				if (StringUtils.isNullOrEmpty(list.getCreateDate())) {
					TtPartGroupLevelSetTempDTO rowDto = new TtPartGroupLevelSetTempDTO();
					rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
					rowDto.setErrorMsg("创建日期不能为空!");
					err.add(rowDto);
				}
				List<Object> queryParam = new ArrayList<Object>();
				StringBuffer sql = new StringBuffer();
				sql.append("SELECT   tt.FAMIGLIA_FAMIGLIA   FROM TT_PART_GROUP_LEVEL_SET_DCS  tt   WHERE  tt.FAMIGLIA_FAMIGLIA= ?");
				queryParam.add(list.getFamigliaFamiglia());
				List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
				if (resultList != null && resultList.size() > 0) {
					TtPartGroupLevelSetTempDTO rowDto = new TtPartGroupLevelSetTempDTO();
					rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
					rowDto.setErrorMsg("该组别信息已经存在!");
					err.add(rowDto);
				} else {
				
				}
				imp.setErrorList(err);
				logger.info("*****************校验完成!************************");
				if (isError) {
					return imp;
				} else {
					return null;
				}
			}
		/**
		 * 向临时表中插入数据
		 */
		@Override
		public void insertTmpRecallVehicleDcs(TtPartGroupLevelSetTempDTO rowDto) throws ServiceBizException {
			//获取当前用户
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			TtPartGroupLevelSetTempPO savePo = new TtPartGroupLevelSetTempPO();
			savePo.set("FAMIGLIA_FAMIGLIA", rowDto.getFamigliaFamiglia());
			savePo.set("FAMIGLIA_FAMIGLIA_DESC", rowDto.getFamigliaFamigliaDesc());
			savePo.set("ITEM_NO", rowDto.getItemNo());
			savePo.set("DATA_STATUS", "insert");
			savePo.set("CREATE_BY", loginInfo.getUserId());
			savePo.set("CREATE_DATE", new Date(System.currentTimeMillis()));
			savePo.set("UPDATE_BY", loginInfo.getUserId());
			savePo.set("UPDATE_DATE", new Date(System.currentTimeMillis()));
			savePo.saveIt();
			
		}
		
}
