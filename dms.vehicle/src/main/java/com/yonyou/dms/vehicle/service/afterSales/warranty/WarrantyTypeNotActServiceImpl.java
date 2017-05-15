package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.dao.afterSales.warranty.WarrantyTypeNotActDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWarrantyTypeDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWtParmaterDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWtBugDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWtOperateDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWtPartDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TtWrWarrantyTypeDcsPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TtWrWtActivityDcsPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TtWrWtBugDcsPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TtWrWtOperateDcsPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TtWrWtParmaterDcsPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TtWrWtPartDcsPO;

/**
 * 保修类型（非活动）
 * @author zhanghongyi
 *
 */
@Service
public class WarrantyTypeNotActServiceImpl implements WarrantyTypeNotActService {
	
	@Autowired
	WarrantyTypeNotActDao warrantyTypeNotActDao;

	/**
	 * 保修类型查询
	 */
	@Override
	public PageInfoDto warrantyTypeQuery(Map<String, String> queryParam) throws  ServiceBizException {
		// TODO Auto-generated method stub
		return warrantyTypeNotActDao.getWarrantyTypeList(queryParam);
	}
	
	/**
	 * 新增保修类型
	 */
	@Override
	public Long addWarrantyType(TtWrWarrantyTypeDTO dto) throws  ServiceBizException {
		TtWrWarrantyTypeDcsPO po = new TtWrWarrantyTypeDcsPO();
		TtWrWtActivityDcsPO act = new TtWrWtActivityDcsPO();
		setWarrantyTypePo(po, dto);
		setActivityPO(po.getLongId(), act);
		return po.getLongId();
	}
	
	private void setWarrantyTypePo(TtWrWarrantyTypeDcsPO po, TtWrWarrantyTypeDTO dto) {
		boolean flag = false;
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if (!StringUtils.isNullOrEmpty(dto.getWtCode())) {
			LazyList<TtWrWarrantyTypeDcsPO> listTtWrWarrantyTypeDcsPO = TtWrWarrantyTypeDcsPO.find("WT_CODE = ?", dto.getWtCode());
			if (listTtWrWarrantyTypeDcsPO.size()>0) {
				throw new ServiceBizException("已存在此保修类型！新增失败！");
			}
			Integer isNoCcf,isPresale,isNoRep,isOptLock,isPtLock,wtTypeParm;
			isOptLock=dto.getIsOptLock();
			isPtLock=dto.getIsPtLock();
			wtTypeParm=dto.getWtTypeParm();
			if(wtTypeParm==99011001){
				isNoCcf=OemDictCodeConstants.IF_TYPE_YES;
				isPresale=OemDictCodeConstants.IF_TYPE_NO;
				isNoRep=OemDictCodeConstants.IF_TYPE_NO;
			}else if(wtTypeParm==99011002){
				isNoCcf=OemDictCodeConstants.IF_TYPE_NO;
				isPresale=OemDictCodeConstants.IF_TYPE_YES;
				isNoRep=OemDictCodeConstants.IF_TYPE_NO;
			}else if(wtTypeParm==99011003){
				isNoCcf=OemDictCodeConstants.IF_TYPE_NO;
				isPresale=OemDictCodeConstants.IF_TYPE_NO;
				isNoRep=OemDictCodeConstants.IF_TYPE_YES;
			}else{
				isNoCcf=OemDictCodeConstants.IF_TYPE_NO;
				isPresale=OemDictCodeConstants.IF_TYPE_NO;
				isNoRep=OemDictCodeConstants.IF_TYPE_NO;
			}
			if(isOptLock==null){isOptLock=OemDictCodeConstants.IF_TYPE_NO;}
			if(isPtLock==null){isPtLock=OemDictCodeConstants.IF_TYPE_NO;}
			po.setString("WT_CODE", dto.getWtCode());
			po.setString("WT_CODE_ESIGI", dto.getWtCode());
			po.setString("WT_NAME", dto.getWtName());
			po.setInteger("IS_NO_CCF", isNoCcf);
			po.setInteger("IS_PRESALE", isPresale);
			po.setInteger("IS_NO_REP", isNoRep);
			po.setInteger("WT_TYPE", dto.getWtType());
			po.setInteger("IS_ACT", OemDictCodeConstants.IF_TYPE_NO);
			po.setInteger("IS_OPT_LOCK", isOptLock);
			po.setInteger("IS_PT_LOCK", isPtLock);
			po.setInteger("STATUS", dto.getStatus());
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
	
	private void setActivityPO(Long wtId, TtWrWtActivityDcsPO po) {
		boolean flag = false;
		if (!StringUtils.isNullOrEmpty(wtId)) {
			po.setLong("WT_ID", wtId);
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
	 * 保修类型修改
	 */
	@Override
	public void updateWarrantyType(TtWrWarrantyTypeDTO dto) throws  ServiceBizException {
		boolean flag = false;
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if(!StringUtils.isNullOrEmpty(dto.getId())){
			TtWrWarrantyTypeDcsPO po = TtWrWarrantyTypeDcsPO.findFirst("ID = ?", dto.getId());
			if(po!=null){
				Integer isNoCcf,isPresale,isNoRep,isOptLock,isPtLock,wtTypeParm;
				isOptLock=dto.getIsOptLock();
				isPtLock=dto.getIsPtLock();
				wtTypeParm=dto.getWtTypeParm();
				if(wtTypeParm==99011001){
					isNoCcf=OemDictCodeConstants.IF_TYPE_YES;
					isPresale=OemDictCodeConstants.IF_TYPE_NO;
					isNoRep=OemDictCodeConstants.IF_TYPE_NO;
				}else if(wtTypeParm==99011002){
					isNoCcf=OemDictCodeConstants.IF_TYPE_NO;
					isPresale=OemDictCodeConstants.IF_TYPE_YES;
					isNoRep=OemDictCodeConstants.IF_TYPE_NO;
				}else if(wtTypeParm==99011003){
					isNoCcf=OemDictCodeConstants.IF_TYPE_NO;
					isPresale=OemDictCodeConstants.IF_TYPE_NO;
					isNoRep=OemDictCodeConstants.IF_TYPE_YES;
				}else{
					isNoCcf=OemDictCodeConstants.IF_TYPE_NO;
					isPresale=OemDictCodeConstants.IF_TYPE_NO;
					isNoRep=OemDictCodeConstants.IF_TYPE_NO;
				}
				if(isOptLock==null){isOptLock=OemDictCodeConstants.IF_TYPE_NO;}
				if(isPtLock==null){isPtLock=OemDictCodeConstants.IF_TYPE_NO;}
				po.setString("WT_CODE", dto.getWtCode());
				po.setString("WT_CODE_ESIGI", dto.getWtCode());
				po.setString("WT_NAME", dto.getWtName());
				po.setInteger("IS_NO_CCF", isNoCcf);
				po.setInteger("IS_PRESALE", isPresale);
				po.setInteger("IS_NO_REP", isNoRep);
				po.setInteger("WT_TYPE", dto.getWtType());
				po.setInteger("IS_OPT_LOCK", isOptLock);
				po.setInteger("IS_PT_LOCK", isPtLock);
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
	 * 删除保修参数
	 */
	@Override
	public void deleteParm(BigDecimal id) throws ServiceBizException {
		boolean flag = false;
		if(!StringUtils.isNullOrEmpty(id)){
			TtWrWtParmaterDcsPO po = TtWrWtParmaterDcsPO.findFirst("ID = ?", id);
			flag=po.delete();
			if(flag){			
			}else{
				throw new ServiceBizException("删除保修参数失败!");
			}
		}
	}
	
	/**
	 * 删除保修故障
	 */
	@Override
	public void deleteBug(BigDecimal id) throws ServiceBizException {
		boolean flag = false;
		if(!StringUtils.isNullOrEmpty(id)){
			TtWrWtBugDcsPO po = TtWrWtBugDcsPO.findFirst("ID = ?", id);
			flag=po.delete();
			if(flag){			
			}else{
				throw new ServiceBizException("删除保修故障失败!");
			}
		}
	}
	
	/**
	 * 删除保修操作
	 */
	@Override
	public void deleteOper(BigDecimal id) throws ServiceBizException {
		boolean flag = false;
		if(!StringUtils.isNullOrEmpty(id)){
			TtWrWtOperateDcsPO po = TtWrWtOperateDcsPO.findFirst("ID = ?", id);
			flag=po.delete();
			if(flag){			
			}else{
				throw new ServiceBizException("删除保修操作失败!");
			}
		}
	}
	
	/**
	 * 删除保修零部件
	 */
	@Override
	public void deletePart(BigDecimal id) throws ServiceBizException {
		boolean flag = false;
		if(!StringUtils.isNullOrEmpty(id)){
			TtWrWtPartDcsPO po = TtWrWtPartDcsPO.findFirst("ID = ?", id);
			flag=po.delete();
			if(flag){			
			}else{
				throw new ServiceBizException("删除保修零部件失败!");
			}
		}
	}
	
	/**
	 * 查询MVS
	 */
	@Override
	public List<Map> getMvs(Map<String, String> queryParams,String mvsType) throws ServiceBizException {
		// TODO Auto-generated method stub
		return warrantyTypeNotActDao.getMvs(queryParams,mvsType);
	}

	/**
	 * 新增保修参数
	 */
	@Override
	public Long addParm(TtWrWtParmaterDTO dto) throws  ServiceBizException {
		TtWrWtParmaterDcsPO po = new TtWrWtParmaterDcsPO();
		setParmPo(po, dto);
		return po.getLongId();
	}
	
	/**
	 * 新增保修故障
	 */
	@Override
	public Long addBug(TtWrWtBugDTO dto) throws  ServiceBizException {
		TtWrWtBugDcsPO po = new TtWrWtBugDcsPO();
		setBugPo(po, dto);
		return po.getLongId();
	}
	
	/**
	 * 新增保修操作
	 */
	@Override
	public Long addOper(TtWrWtOperateDTO dto) throws  ServiceBizException {
		TtWrWtOperateDcsPO po = new TtWrWtOperateDcsPO();
		setOperPo(po, dto);
		return po.getLongId();
	}
	
	/**
	 * 新增保修零部件
	 */
	@Override
	public Long addPart(TtWrWtPartDTO dto) throws  ServiceBizException {
		TtWrWtPartDcsPO po = new TtWrWtPartDcsPO();
		setPartPo(po, dto);
		return po.getLongId();
	}
	
	private void setParmPo(TtWrWtParmaterDcsPO po, TtWrWtParmaterDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long actId=dto.getActId();
		if (actId!=null && dto.getMvs() != null ) {
			po.setLong("ACT_ID", actId);
			po.setString("MVS", dto.getMvs());
			po.setLong("WR_DAYS", dto.getWrDays());
			po.setLong("WR_RANGE", dto.getWrRange());
			po.setLong("WR_NUM", dto.getWrNum());
			po.setLong("CREATE_BY", loginInfo.getUserId());
			po.setDate("CREATE_DATE", new Date());
			po.saveIt();
		} else {
			throw new ServiceBizException("未填写完整重要信息，请输入！");
		}
	}
	
	private void setBugPo(TtWrWtBugDcsPO po, TtWrWtBugDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long actId=dto.getActId();
		if (actId!=null && dto.getMvs() != null ) {
			po.setLong("ACT_ID", actId);
			po.setString("MVS", dto.getMvs());
			po.setString("BUG_CODE", dto.getBugCode());
			po.setString("BUG_NAME", dto.getBugName());
			po.setInteger("TAG", dto.getTag());
			po.setLong("CREATE_BY", loginInfo.getUserId());
			po.setDate("CREATE_DATE", new Date());
			po.saveIt();
		} else {
			throw new ServiceBizException("未填写完整重要信息，请输入！");
		}
	}
	
	private void setOperPo(TtWrWtOperateDcsPO po, TtWrWtOperateDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long actId=dto.getActId();
		if (actId!=null && dto.getMvs() != null ) {
			po.setLong("ACT_ID", actId);
			po.setString("MVS", dto.getMvs());
			po.setLong("OPT_ID", dto.getActId());
			po.setString("OPT_CODE", dto.getOptCode());
			po.setString("OPT_NAME_CN", dto.getOptNameCn());
			po.setInteger("TAG", dto.getTag());
			po.setLong("CREATE_BY", loginInfo.getUserId());
			po.setDate("CREATE_DATE", new Date());
			po.saveIt();
		} else {
			throw new ServiceBizException("未填写完整重要信息，请输入！");
		}
	}
	
	private void setPartPo(TtWrWtPartDcsPO po, TtWrWtPartDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long actId=dto.getActId();
		if (actId!=null && dto.getMvs() != null ) {
			po.setLong("ACT_ID", actId);
			po.setString("MVS", dto.getMvs());
			po.setInteger("PT_TYPE", dto.getPtType());
			po.setString("PT_CODE", dto.getPtCode());
			//po.setLong("OPT_ID", dto.getOptId());
			po.setString("OPT_CODE", dto.getOptCode());
			po.setLong("QTY", dto.getQty());
			po.setInteger("TAG", dto.getTag());
			po.setLong("CREATE_BY", loginInfo.getUserId());
			po.setDate("CREATE_DATE", new Date());
			po.saveIt();
		} else {
			throw new ServiceBizException("未填写完整重要信息，请输入！");
		}
	}
	
	/**
	 * 保修类型查询
	 */
	@Override
	public PageInfoDto operSearch(Map<String, String> queryParam) throws ServiceBizException {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select ID, OPT_CODE, OPT_NAME_EN, OPT_NAME_CN \n");
		sql.append("from tt_wr_operate_dcs where STATUS=10011001 \n");
		if(!StringUtils.isNullOrEmpty(queryParam.get("optCode"))){ 
        	sql.append("AND OPT_CODE like '%" + queryParam.get("optCode") + "%' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("optNameCn"))){ 
        	sql.append("AND OPT_NAME_CN like '%" + queryParam.get("optNameCn") + "%' \n");
        }
		return OemDAOUtil.pageQuery(sql.toString(),params);
	}
	
	/**
	 * 零部件查询
	 */
	@Override
	public PageInfoDto partSearch(Map<String, String> queryParam) throws ServiceBizException {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ( \n");
		sql.append("select distinct TPPB.PART_CODE PT_CODE,TPPB.PART_NAME PT_NAME,10041002 PT_TYPE \n");
		sql.append("from TT_PT_PART_BASE_DCS TPPB where TPPB.PART_STATUS=10011001 union all \n");
		sql.append("select distinct TWSP.PT_CODE PT_CODE,TWSP.PT_NAME PT_NAME,10041001 PT_TYPE \n");
		sql.append("from TT_WR_SPECIAL_PART_DCS TWSP where TWSP.STATUS=10011001 \n");
		sql.append(") pt where 1=1 \n");
		if(!StringUtils.isNullOrEmpty(queryParam.get("optCode"))){ 
        	sql.append("AND pt.PT_CODE like '%" + queryParam.get("optCode") + "%' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("optNameCn"))){ 
        	sql.append("AND pt.PT_NAME like '%" + queryParam.get("optNameCn") + "%' \n");
        }
		return OemDAOUtil.pageQuery(sql.toString(),params);
	}
}
