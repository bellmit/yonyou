package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtPtPartBaseDcsPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.dao.PartInfoManageAaDao;
import com.yonyou.dms.part.domains.DTO.basedata.TtPtPartBaseDcsDTO;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TmpPtPartWbpDcsPO;


/**
 * 配件管理
 * @author ZhaoZ
 *@date 2017年3月22日
 */
@SuppressWarnings("all")
@Service
public class PartInfoManageServiceAaImpl implements PartInfoManageAaService{

	@Autowired
	private  PartInfoManageAaDao partDao;
	
	/**
	 * 配件信息维护查询
	 */
	@Override
	public PageInfoDto findPartList(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.findList(queryParams);
	}
	
	/**
	 * 替换件信息
	 */
	@Override
	public List<Map> selectPart(String oldPartCode) throws ServiceBizException {
		return partDao.selectParts(oldPartCode);
	}

	/**
	 * 车型信息
	 */
	@Override
	public List<Map> vhclMaterial(String code) throws ServiceBizException {
		return partDao.vhclMaterialinfos(code);
	}

	@Override
	public PageInfoDto selectPart(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.selectParts(queryParams);
	}

	@Override
	public PageInfoDto addTmVhclMaterialGroupQuery(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.addTmVhclMaterial(queryParams);
	}

	/**
	 * 更新配件基础信息(保存)
	 */
	@Override
	public void saveDeliveryOrder(TtPtPartBaseDcsDTO dto, BigDecimal id,LoginInfoDto loginUser) throws ServiceBizException {
		TtPtPartBaseDcsPO updatePo = TtPtPartBaseDcsPO.findById(id);
		
		updatePo.setString("PART_NAME", dto.getPartName());
		if(!StringUtils.isNullOrEmpty(dto.getIsPurchase())){
			updatePo.setInteger("IS_PURCHASE", Integer.parseInt(dto.getIsPurchase()));
		}
		if(!StringUtils.isNullOrEmpty( dto.getPartPrice())){
			updatePo.setDouble("PART_PRICE", dto.getPartPrice());
		}
		if(!StringUtils.isNullOrEmpty(dto.getIsSales())){
			updatePo.setInteger("IS_SALES", Integer.parseInt(dto.getIsSales()));
		}
		if(!StringUtils.isNullOrEmpty(dto.getPackageNum())){
			updatePo.setInteger("PACKAGE_NUM", dto.getPackageNum());
		}
		if(!StringUtils.isNullOrEmpty(dto.getDnpPrice())){
			updatePo.setDouble("DNP_PRICE", dto.getDnpPrice());
		}
		if(!StringUtils.isNullOrEmpty(dto.getWbp())){
			updatePo.setDouble("WBP", Double.parseDouble(dto.getWbp()));
		}
		if(!StringUtils.isNullOrEmpty(dto.getIsNormal())){
			updatePo.setInteger("IS_NORMAL",Integer.parseInt(dto.getIsNormal()) );
		}
		
		updatePo.setBigDecimal("UPDATE_BY", loginUser.getUserId());
		updatePo.setString("PART_PROPERTY",  dto.getPartProperty());
		updatePo.setString("REMARK",  dto.getRemark());
		updatePo.setString("PART_PROPERTY",  dto.getPartProperty());
		updatePo.setString("PART_NUIT",  dto.getPartNuit());
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time= System.currentTimeMillis();
		try {
			Date date = sdf.parse(sdf.format(new Date(time)));
			java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
			updatePo.setTimestamp("UPDATE_DATE",st);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		updatePo.setInteger("DOWN_STATUS", 0);
		updatePo.setString("MODEL_CODES",  dto.getModelCodes());
		updatePo.setString("OLD_PART_CODE",  dto.getOldPartCode());
		boolean flag = false;
		flag = updatePo.saveIt();
		if(flag){			
		}else{
			throw new ServiceBizException("修改配件失败");
		}
	}

	

	/**
	 * 导入数据校验
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  List<TtPtPartBaseDcsDTO>  checkData(LazyList<TmpPtPartWbpDcsPO> poList) throws ServiceBizException {
		ArrayList<TtPtPartBaseDcsDTO> trvdDTOList = new ArrayList<TtPtPartBaseDcsDTO>();
		ImportResultDto<TtPtPartBaseDcsDTO> importResult = new ImportResultDto<TtPtPartBaseDcsDTO>();
		
		
		//数据正确性校验
		List<Map> trvdList = partDao.checkData2(poList);		
		if(trvdList.size()>0){
			for(Map<String, Object> p:trvdList){
				TtPtPartBaseDcsDTO rowDto = new TtPtPartBaseDcsDTO();
				 rowDto.setRowNO(Integer.valueOf(p.get("ROW_NUM").toString()));
				 rowDto.setErrorMsg(p.get("ERROR").toString());
				 trvdDTOList.add(rowDto);
				 
			}
			importResult.setErrorList(trvdDTOList);
		}

		return trvdDTOList;
	}

	/**
	 * 配件主数据监控查询异常
	 */
	@Override
	public PageInfoDto exceFindList(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.exceList(queryParams);
	}

	/**
	 * 查询状态
	 */
	@Override
	public List<Map> getStatus() throws ServiceBizException {
		return partDao.status();
	}

	/**
	 * 查询数据标记
	 */
	@Override
	public List<Map> getDataFlag() throws ServiceBizException {
		return partDao.dataFlag();
	}

	/**
	 * 配件查询
	 */
	@Override
	public PageInfoDto partBaseDlr(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.getPartBaseDlr(queryParams);
	}

	/**
	 * 导入将数据插入临时表
	 * @param rowDto
	 * @throws ServiceBizException
	 */
	@Override
	public void insertTmpPtPartWbpDcs(TtPtPartBaseDcsDTO rowDto) throws ServiceBizException {
		TmpPtPartWbpDcsPO tppo = new TmpPtPartWbpDcsPO();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tppo.setString("LINE_NO",rowDto.getRowNO() );
		if(!StringUtils.isNullOrEmpty(rowDto.getPartCode())){ 
			tppo.setString("PART_CODE",rowDto.getPartCode() );
	       }
		tppo.setString("PART_NAME",rowDto.getPartName() );
		tppo.setString("WBP",rowDto.getWbp() );
		tppo.setString("CREATE_BY",loginInfo.getUserId().toString());
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time= System.currentTimeMillis();
		try {
			Date date = sdf.parse(sdf.format(new Date(time)));
			java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
			tppo.setTimestamp("CREATE_DATE",st);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		tppo.insert();
	}

	@Override
	public List<Map> findTmpPtPartWbpDcsList() throws ServiceBizException {
		return partDao.tmpPtPartWbpDcsList();
	}

	

}
