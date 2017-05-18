package com.yonyou.dms.manage.service.basedata.bulletin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.dao.bulletin.BulletinTypeDao;
import com.yonyou.dms.manage.domains.DTO.bulletin.BulletinTypeDTO;
import com.yonyou.dms.manage.domains.DTO.personInfoManager.TcUserDTO;
import com.yonyou.dms.manage.domains.PO.bulletin.TmBltntypeRolePO;
import com.yonyou.dms.manage.domains.PO.bulletin.TmBulletinTypePO;

@Service
public class BulletinTypeServiceImpl implements BulletinTypeService {
	
	@Autowired
	private BulletinTypeDao typeDao;

	@Override
	public PageInfoDto search(Map<String, String> param) {
		PageInfoDto dto = typeDao.search(param);
		return dto;
	}

	@Override
	public Map<String, String> checkType(String typename) {
		List<Map> list = typeDao.checkType(typename);
		Map<String,String> map = new HashMap<String,String>();
		if(list != null && !list.isEmpty()){
			map.put("code", "false");
			map.put("msg", "该通告类别已经存在，请重新填写！");
		}
		return map;
	}

	@Override
	public void addBulletinType(BulletinTypeDTO dto) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);		
		Date currentTime = new Date();
		List<Map> list = typeDao.checkType(dto.getTypename());
		boolean flag = false;
		//修改通报类别
		if(list != null && !list.isEmpty()){
			throw new ServiceBizException("该通告类别已经存在，请重新填写!");
		}else{
			try {			
				TmBulletinTypePO po = new TmBulletinTypePO();
				po.setString("TYPENAME", dto.getTypename());
				po.setInteger("STATUS", dto.getStatus());
				po.setLong("UPDATE_BY",loginUser.getUserId());
				po.setTimestamp("UPDATE_DATE", currentTime);
				flag = po.saveIt();
				if(!flag){
					throw new ServiceBizException("数据库插入数据失败！");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceBizException("数据库插入数据失败！");
			}
		}
		
		
	}

	@Override
	public BulletinTypeDTO editBulletinTypeInit(Long typeId) {
		TmBulletinTypePO po = TmBulletinTypePO.findById(typeId);
		BulletinTypeDTO dto = new BulletinTypeDTO();
		dto.setTypeId(po.getLongId());
		dto.setTypename(po.getString("TYPENAME"));
		dto.setStatus(po.getInteger("STATUS"));
		return dto;
	}

	@Override
	public List<Map> editBulletinTypeUserInit(Long typeId) {
		List<Map> list = typeDao.getTypeUser(typeId);
		return list;
	}

	@Override
	public void editBulletinType(BulletinTypeDTO dto) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);		
		Date currentTime = new Date();
		List<Map> list = typeDao.checkType(dto.getTypename(),dto.getTypeId());
		boolean flag = false;
		Long typeId = dto.getTypeId();
		String typename = dto.getTypename();
		Integer status = dto.getStatus();
		List<TcUserDTO> userList = dto.getUserList();
		String[] userId = null;
		String userIds = dto.getUserIds();
		
		if(list != null && !list.isEmpty()){
			throw new ServiceBizException("该通告类别已经存在，请重新填写!");
		}else{		
			try {				
				TmBulletinTypePO po = TmBulletinTypePO.findById(typeId);
				po.setString("TYPENAME", dto.getTypename());
				po.setInteger("STATUS", dto.getStatus());
				po.setLong("CREATE_BY",loginUser.getUserId());
				po.setTimestamp("CREATE_DATE", currentTime);
				flag = po.saveIt();
				if(!flag){
					throw new ServiceBizException("数据库插入数据失败！");
				}
				
				/*
				 * 修改人员
				 */
				//先删除tm_bltntype_role表信息
//				typeDao.delTypeRoleBeforeUpd(typeId);
//				List<TmBltntypeRolePO> roleList = typeDao.getTypeRoleForDel(typeId);
//				if(roleList != null && !roleList.isEmpty()){					
//					for(int i = 0;i<roleList.size();i++){
//						TmBltntypeRolePO rolePo = roleList.get(i);
//						flag = rolePo.delete();
//					}
//				}
				
//				TmBltntypeRolePO.delete(" TYPE_ID = ? ", typeId);
				
				//再往tm_bltntype_role插入数据
//				if(userList != null && !userList.isEmpty()){
//					for(int i = 0;i < userList.size();i++){		
//						TcUserDTO user = userList.get(i);
////						TmBltntypeRolePO trPo = TmBltntypeRolePO.findById(user.getUserId());
//						//根据userId 查找这条记录，如果存在则修改typeId 不存在则新建
//						TmBltntypeRolePO trPo = typeDao.getTypeRolePO(user.getUserId());
//						if(trPo == null){
//							trPo = new TmBltntypeRolePO();							
//							trPo.setLong("EMPLOYEE_ID", user.getUserId());
//						}
//						trPo.setLong("TYPE_ID", typeId);
//						trPo.setTimestamp("CREATE_DATE", currentTime);
//						trPo.setLong("CREATE_BY", loginUser.getUserId());
//						flag = trPo.saveIt();
//						if(!flag){
//							throw new ServiceBizException("数据库插入数据失败！");
//						}
//					}
//				}
				//先删除tm_bltntype_role表信息 
				TmBltntypeRolePO.delete("TYPE_ID = ?", dto.getTypeId());
				if(!StringUtils.isNullOrEmpty(userIds)){
					userId = userIds.split(",");
					for(String str:userId){						
						TmBltntypeRolePO trPo = typeDao.getTypeRolePO(Long.valueOf(str));
						//根据userId 查找这条记录，如果存在则修改typeId 不存在则新建
						if(trPo == null){
							trPo = new TmBltntypeRolePO();							
							trPo.setLong("EMPLOYEE_ID", str);
						}
						trPo.setLong("TYPE_ID", typeId);
						trPo.setTimestamp("CREATE_DATE", currentTime);
						trPo.setLong("CREATE_BY", loginUser.getUserId());
						flag = trPo.saveIt();
						if(!flag){
							throw new ServiceBizException("数据库插入数据失败！");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new ServiceBizException("数据库插入数据失败！");
			}
		}
		
		
		
	}

	@Override
	public PageInfoDto searchUser(Map<String, String> param) {
		PageInfoDto dto = typeDao.searchUser(param);
		return dto;
	}

	@Override
	public void deleteUser(Long userId,Long typeId) {
		int po = TmBltntypeRolePO.delete("EMPLOYEE_ID = ? AND TYPE_ID = ? ", userId,typeId);
	}

}
