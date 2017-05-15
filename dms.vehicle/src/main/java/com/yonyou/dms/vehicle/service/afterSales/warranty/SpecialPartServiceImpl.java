package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.dao.afterSales.warranty.SpecialPartDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrSpecialPartDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TtWrSpecialPartDcsPO;

@Service
public class SpecialPartServiceImpl implements SpecialPartService {
	@Autowired
	SpecialPartDao specialPartDao;
	
	@Autowired
	private ExcelGenerator excelService;
	
	@Override
	public PageInfoDto query(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		PageInfoDto pageInfoDto = specialPartDao.query(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 新增
	 */
	@Override
	public Long add(TtWrSpecialPartDTO dto) throws  ServiceBizException {
		TtWrSpecialPartDcsPO po = new TtWrSpecialPartDcsPO();
		setSpecialPartPo(po, dto);
		return po.getLongId();
	}
	
	private void setSpecialPartPo(TtWrSpecialPartDcsPO po, TtWrSpecialPartDTO dto) {
		boolean flag = false;
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if (!StringUtils.isNullOrEmpty(dto.getMvs()) && !StringUtils.isNullOrEmpty(dto.getPtCode())) {
			LazyList<TtWrSpecialPartDcsPO> listPO = TtWrSpecialPartDcsPO.find("MVS = ? and PT_CODE = ?", dto.getMvs(),dto.getPtCode());
			if (listPO.size()>0) {
				throw new ServiceBizException("该MVS与零部件已维护！新增失败！");
			}
			LazyList<TtWrSpecialPartDcsPO> listPart = TtWrSpecialPartDcsPO.find("PT_CODE = ?", dto.getPtCode());
			if (listPart.size()>0) {
				if(!listPart.get(0).getString("PT_NAME").equals(dto.getPtName())){
					throw new ServiceBizException("该零部件名称与零部件编号不对应！重新输入!");
				}
			}
			po.setString("MVS", dto.getMvs());
			po.setString("PT_CODE", dto.getPtCode());
			po.setString("PT_NAME", dto.getPtName());
			po.setDouble("WR_PRICE", dto.getWrPrice());
			po.setInteger("IS_CAN_MOD", dto.getIsCanMod());
			po.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
			po.setLong("CREATE_BY", loginInfo.getUserId());
			po.setDate("CREATE_DATE", new Date());
			po.setLong("UPDATE_BY", loginInfo.getUserId());
			po.setDate("UPDATE_DATE", new Date());
			flag=po.saveIt();
		} else {
			throw new ServiceBizException("未填写完整重要信息，请输入！");
		}
		if(flag){
		}else{
			throw new ServiceBizException("新增失败!");
		}
	}
	
	/**
	 * 修改
	 */
	@Override
	public void update(TtWrSpecialPartDTO dto) throws  ServiceBizException {
		boolean flag = false;
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if(!StringUtils.isNullOrEmpty(dto.getId())){
			TtWrSpecialPartDcsPO po = TtWrSpecialPartDcsPO.findFirst("ID = ?", dto.getId());
			if(po!=null){
				LazyList<TtWrSpecialPartDcsPO> listPO = TtWrSpecialPartDcsPO.find("MVS = ? and PT_CODE = ? and ID <> ?", dto.getMvs(),dto.getPtCode(),dto.getId());
				if (listPO.size()>0) {
					throw new ServiceBizException("该MVS与零部件已维护！新增失败！");
				}
				po.setString("MVS", dto.getMvs());
				po.setDouble("WR_PRICE", dto.getWrPrice());
				po.setInteger("IS_CAN_MOD", dto.getIsCanMod());
				po.setInteger("STATUS", dto.getStatus());
				po.setLong("UPDATE_BY", loginUser.getUserId());
				po.setDate("UPDATE_DATE", new Date());
				flag=po.saveIt();
			}
			if(flag){
			}else{
				throw new ServiceBizException("更新失败!");
			}
		}
	}
	
	/**
	 * 查询MVS
	 */
	@Override
	public List<Map> getMVS(Map<String, String> queryParams) throws ServiceBizException {
		// TODO Auto-generated method stub
		return specialPartDao.getMVS(queryParams);
	}
	
	/**
	 * 下载
	 */
	@Override
	public void download(Map<String, String> queryParam, HttpServletRequest request,
		HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = specialPartDao.getDownloadList(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("特殊零部件维护", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("PT_CODE", "零部件编号"));
		exportColumnList.add(new ExcelExportColumn("PT_NAME", "零部件名称"));
		exportColumnList.add(new ExcelExportColumn("WR_PRICE", "保修价格"));
		exportColumnList.add(new ExcelExportColumn("MVS", "MVS"));
		exportColumnList.add(new ExcelExportColumn("STATUS", "状态" ));
		excelService.generateExcel(excelData, exportColumnList, "特殊零部件维护.xls", request, response);
	}
}
