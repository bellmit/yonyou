package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.dao.afterSales.warranty.WarrantyTypeActDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWarrantyTypeDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrWtActivityDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TtWrWarrantyTypeDcsPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TtWrWtActivityDcsPO;

/**
 * 保修类型（活动）
 * @author zhanghongyi
 *
 */
@Service
public class WarrantyTypeActServiceImpl implements WarrantyTypeActService {
	
	@Autowired
	WarrantyTypeActDao warrantyTypeActDao;

	/**
	 * 保修类型查询
	 */
	@Override
	public PageInfoDto warrantyTypeQuery(Map<String, String> queryParam) throws  ServiceBizException {
		// TODO Auto-generated method stub
		return warrantyTypeActDao.getWarrantyTypeList(queryParam);
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
			Integer isNoCcf,isPresale,isNoRep,wtTypeParm;
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
			po.setString("WT_CODE", dto.getWtCode());
			po.setString("WT_CODE_ESIGI", dto.getWtCodeEsigi());
			po.setString("WT_NAME", dto.getWtName());
			po.setInteger("IS_NO_CCF", isNoCcf);
			po.setInteger("IS_PRESALE", isPresale);
			po.setInteger("IS_NO_REP", isNoRep);
			po.setInteger("WT_TYPE", dto.getWtType());
			po.setInteger("IS_ACT", OemDictCodeConstants.IF_TYPE_YES);
			po.setInteger("ACT_TYPE", dto.getActType());
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
				Integer isNoCcf,isPresale,isNoRep,wtTypeParm;
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
				po.setString("WT_CODE", dto.getWtCode());
				po.setString("WT_CODE_ESIGI", dto.getWtCodeEsigi());
				po.setString("WT_NAME", dto.getWtName());
				po.setInteger("IS_NO_CCF", isNoCcf);
				po.setInteger("IS_PRESALE", isPresale);
				po.setInteger("IS_NO_REP", isNoRep);
				po.setInteger("WT_TYPE", dto.getWtType());
				po.setInteger("IS_ACT", OemDictCodeConstants.IF_TYPE_YES);
				po.setInteger("ACT_TYPE", dto.getActType());
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
	 * 新增活动
	 */
	@Override
	public void addAct(TtWrWtActivityDTO dto) throws  ServiceBizException {
		boolean flag = false;
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if(!StringUtils.isNullOrEmpty(dto.getId())){
			TtWrWtActivityDcsPO po = TtWrWtActivityDcsPO.findFirst("ID = ?", dto.getId());
			if(po!=null){
				Integer isOptLock,isPtLock;
				isOptLock=dto.getIsOptLock();
				isPtLock=dto.getIsPtLock();
				if(isOptLock==null){isOptLock=OemDictCodeConstants.IF_TYPE_NO;}
				if(isPtLock==null){isPtLock=OemDictCodeConstants.IF_TYPE_NO;}
				po.setString("ACT_CODE", dto.getActCode());
				po.setString("ACT_NAME", dto.getActName());
				po.setDate("BEGIN_TIME", dto.getBeginTime());
				po.setDate("END_TIME", dto.getEndTime());
				po.setInteger("IS_OPT_LOCK", isOptLock);
				po.setInteger("IS_PT_LOCK", isPtLock);
				po.setLong("CREATE_BY", loginUser.getUserId());
				po.setDate("CREATE_DATE", new Date());
				po.setLong("UPDATE_BY", loginUser.getUserId());
				po.setDate("UPDATE_DATE", new Date());
				flag=po.saveIt();
			}
			if(flag){
			}else{
				throw new ServiceBizException("新增活动失败!");
			}
			TtWrWtActivityDcsPO act = new TtWrWtActivityDcsPO();
			setActivityPO(po.getLong("WT_ID"), act);
		}
	}
	
	/**
	 * 删除活动
	 */
	@Override
	public void deleteAct(BigDecimal id) throws ServiceBizException {
		boolean flag = false;
		if(!StringUtils.isNullOrEmpty(id)){
			TtWrWtActivityDcsPO po = TtWrWtActivityDcsPO.findFirst("ID = ?", id);
			flag=po.delete();
			if(flag){			
			}else{
				throw new ServiceBizException("删除活动失败!");
			}
		}
	}
	
	/**
	 * 修改活动
	 */
	@Override
	public void updateAct(TtWrWtActivityDTO dto) throws  ServiceBizException {
		boolean flag = false;
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if(!StringUtils.isNullOrEmpty(dto.getId())){
			TtWrWtActivityDcsPO po = TtWrWtActivityDcsPO.findFirst("ID = ?", dto.getId());
			if(po!=null){
				Integer isOptLock,isPtLock;
				isOptLock=dto.getIsOptLock();
				isPtLock=dto.getIsPtLock();
				if(isOptLock==null){isOptLock=OemDictCodeConstants.IF_TYPE_NO;}
				if(isPtLock==null){isPtLock=OemDictCodeConstants.IF_TYPE_NO;}
				po.setString("ACT_CODE", dto.getActCode());
				po.setString("ACT_NAME", dto.getActName());
				po.setDate("BEGIN_TIME", dto.getBeginTime());
				po.setDate("END_TIME", dto.getEndTime());
				po.setInteger("IS_OPT_LOCK", isOptLock);
				po.setInteger("IS_PT_LOCK", isPtLock);
				po.setLong("UPDATE_BY", loginUser.getUserId());
				po.setDate("UPDATE_DATE", new Date());
				flag=po.saveIt();
			}
			if(flag){
			}else{
				throw new ServiceBizException("修改活动失败!");
			}
		}
	}
}
