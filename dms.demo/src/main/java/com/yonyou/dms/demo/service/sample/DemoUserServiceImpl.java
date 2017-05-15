package com.yonyou.dms.demo.service.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.demo.controller.sample.DemoUserController;
import com.yonyou.dms.demo.domains.DTO.sample.DemoUserAddressDto;
import com.yonyou.dms.demo.domains.DTO.sample.DemoUserDto;
import com.yonyou.dms.demo.domains.DTO.sample.DemoUserUpdateDto;
import com.yonyou.dms.demo.domains.PO.sample.DemoAddressPO;
import com.yonyou.dms.demo.domains.PO.sample.DemoUserPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class DemoUserServiceImpl implements DemoUserService{
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(DemoUserController.class);
	
	@Autowired
    FileStoreService fileStoreService;
	/**
	 * 根据ID 获取用户信息
	* @author zhangxc
	* @date 2016年7月7日
	* @param id 用户ID
	* @return 用户对象
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.sample.DemoUserService#getUserById(java.lang.Long)
	 */
	@Override
	public DemoUserPO getUserById(Long id) {
		return DemoUserPO.findById(id);
	}
	
	
	/**
	 * 根据查询条件查询符合条件的数据
	* @author zhangxc
	* @date 2016年7月7日
	* @param queryParam
	* @return
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.sample.DemoUserService#queryUsers(java.util.Map)
	 */
	@Override
	public PageInfoDto queryUsers(Map<String,String> queryParam) {
	    List<Object> params = new ArrayList<>();
	    String sql = getQuerySql(queryParam,params);
	    PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql,params);
	    
//	    PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(),params,new DefinedRowProcessor() {
//            @Override
//            protected void process(Map<String, Object> row) {
//                row.put("ENTRY_TIME",DateUtil.formatDefaultDateTime((Date)row.get("ENTRY_TIME")));
//            }
//        });
	    //执行查询操作
//	    Paginator paginator = new Paginator(10, sqlSb.toString()).orderBy("DFN desc");
//	    List<Map> result = Base.findAll(sqlSb.toString(),params.toArray());
//		final List<Map<String,Object>> result = new ArrayList<>();
//		processor.with(new RowListener(){
//			@Override
//			public boolean next(Map<String, Object> row) {
////				row.put("ENTRY_TIME",DateUtil.formatDefaultDateTime((Date)row.get("ENTRY_TIME")));
//				result.add(row);
//				return true;
//			}
//		});
		return pageInfoDto;
	}
	
	/**
	 * 
	* 封装SQL 语句
	* @author zhangxc
	* @date 2016年7月20日
	* @param queryParam
	* @param params
	* @return
	 */
	private String getQuerySql(Map<String,String> queryParam,List<Object> params){
        StringBuilder sqlSb = new StringBuilder("select USER_ID,USER_NAME,NAME,AGE,SALARY,BIRTHDAY,ENTRY_TIME,SEX,DEALER_CODE,CREATED_AT,AGE/SALARY AGE_PERCENT from TMP_tm_USERS where 1=1 ");
        
        if(!StringUtils.isNullOrEmpty(queryParam.get("name"))){
            sqlSb.append(" and NAME like ?");
            params.add("%"+queryParam.get("name")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("userName"))){
            sqlSb.append(" and USER_NAME like ?");
            params.add("%"+queryParam.get("userName")+"%");
        }
        //新增查询条件
        if(!StringUtils.isNullOrEmpty(queryParam.get("birdthFrom"))){
            sqlSb.append(" and BIRTHDAY>=? ");
            params.add(DateUtil.parseDefaultDate(queryParam.get("birdthFrom")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("birdthTo"))){
            sqlSb.append(" and BIRTHDAY<=? ");
            params.add(DateUtil.parseDefaultDate(queryParam.get("birdthTo")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("ageFrom"))){
            sqlSb.append(" and AGE>=? ");
            params.add(Integer.parseInt(queryParam.get("ageFrom")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("ageTo"))){
            sqlSb.append(" and AGE<=? ");
            params.add(Integer.parseInt(queryParam.get("ageTo")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("sex"))){
            sqlSb.append(" and SEX=?");
            params.add(Integer.parseInt(queryParam.get("sex")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("salaryFrom"))){
            sqlSb.append(" and SALARY>=? ");
            params.add(Double.parseDouble(queryParam.get("salaryFrom")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("salaryTo"))){
            sqlSb.append(" and SALARY<=? ");
            params.add(Double.parseDouble(queryParam.get("salaryTo")));
        }
        return sqlSb.toString();
    }
	/**
	 * 修改用户信息
	* @author zhangxc
	* @date 2016年7月7日
	* @param id
	* @param userDto
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.sample.DemoUserService#modifyUser(java.lang.Long, com.yonyou.dms.manage.DemoUserDto.DTO.sample.TmpUserDto)
	 */
	@Override
	public void modifyUser(Long id,DemoUserDto userDto) {
		DemoUserPO userPO = DemoUserPO.findById(id);
		//设置对象属性
        setUserPO(userPO,userDto);
        userPO.saveIt();
        
        //删除原地址
        //重置所有的附件
        List<DemoAddressPO> addressList = DemoAddressPO.find("USER_ID = ?", id);
        for(DemoAddressPO addressPo:addressList){
            fileStoreService.updateNotValidByBillId(addressPo.getLongId().toString(), DictCodeConstants.FILE_TYPE_USER_INFO_ADDRESS);
        }
        DemoAddressPO.delete("USER_ID = ?", id);
        
        //增加将的地址
        for(DemoUserAddressDto address:userDto.getAddressList()){
            DemoAddressPO addressPo = getUserAddressPO(address);
            userPO.add(addressPo);
            //插入附件信息
            fileStoreService.addFileUploadInfo(address.getAddressFile(), addressPo.getLongId().toString(), DictCodeConstants.FILE_TYPE_USER_INFO_ADDRESS);
        }
        
        //更新附件的信息
        fileStoreService.updateFileUploadInfo(userDto.getDmsFileIds(),id.toString(),DictCodeConstants.FILE_TYPE_USER_INFO);
        
	}

	/**
	 * 新增用户信息
	* @author zhangxc
	* @date 2016年7月7日
	* @param userDto
	* @return
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.sample.DemoUserService#addUser(com.yonyou.dms.manage.DemoUserDto.DTO.sample.TmpUserDto)
	 */
	@Override
	public Long addUser(DemoUserDto userDto) {
	    if(StringUtils.isNullOrEmpty(userDto.getAge())){
	        throw new ServiceBizException("年龄不能为空");
	    }
		DemoUserPO userPO = new DemoUserPO();
		//设置对象属性
		setUserPO(userPO,userDto);
		userPO.saveIt();
		if(userDto.getAddressList()!=null){
		    for(DemoUserAddressDto address:userDto.getAddressList()){
		        DemoAddressPO addressPo = getUserAddressPO(address);
	            userPO.add(addressPo);
	            //插入附件信息
	            fileStoreService.addFileUploadInfo(address.getAddressFile(), addressPo.getLongId().toString(), DictCodeConstants.FILE_TYPE_USER_INFO_ADDRESS);
	        }
		}
		Long billId = userPO.getLongId();
		//插入附件信息
		fileStoreService.addFileUploadInfo(userDto.getDmsFileIds(), billId.toString(), DictCodeConstants.FILE_TYPE_USER_INFO);
		
		return billId;
	}

	/**
	 * 删除用户信息
	* @author zhangxc
	* @date 2016年7月7日
	* @param id
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.sample.DemoUserService#deleteUserById(java.lang.Long)
	 */
	@Override
	public void deleteUserById(Long id) {
		DemoUserPO user = DemoUserPO.findById(id);
		user.deleteCascadeShallow();
	}
	/**
	 * 批量修改用户信息
	* @author zhangxc
	* @date 2016年8月1日
	* @param id
	* @param userDto
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.sample.DemoUserService#modifyUser(java.lang.Long, com.yonyou.dms.manage.DemoUserDto.DTO.sample.TmpUserDto)
	 */
	@Override
    public void modifyUser(DemoUserUpdateDto userUpdateDto) {
	    String[] ids = userUpdateDto.getUserIds().split(",");
	    for(int i=0;i<ids.length;i++){
	        Long id = Long.parseLong(ids[i]);
	        DemoUserPO userPO = DemoUserPO.findById(id);
	        //设置对象属性
	        setUserUpdatePO(userPO,userUpdateDto);
	        userPO.saveIt();
	    }
    }
	/**
	 * 
	* 设置对象属性
	* @author zhangxc
	* @date 2016年7月6日
	* @param user
	* @param userDto
	 */
	private void setUserPO(DemoUserPO user,DemoUserDto userDto){
	    user.setString("USER_NAME", userDto.getUsername());
        user.setInteger("AGE", userDto.getAge());
        user.setString("NAME",userDto.getName());
        user.setDate("birthday", userDto.getBridthday());
        user.setInteger("sex", userDto.getSex());
        user.setDouble("salary", userDto.getSalary());
        user.setTimestamp("entry_time", userDto.getEntryTime());
        user.setLong("PROVINCE", userDto.getProvinceId());
        user.setLong("CITY", userDto.getCityId());
        user.setLong("COUNTRY", userDto.getCountryId());
	}
	/**
     * 
    * 设置对象属性
    * @author zhangxc
    * @date 2016年7月6日
    * @param user
    * @param userDto
     */
    private void setUserUpdatePO(DemoUserPO user,DemoUserUpdateDto userDto){
        user.setDate("birthday", userDto.getBridthday());
        user.setInteger("sex", userDto.getSex());
    }
	
	/**
	 * 
	* 设置用户地址
	* @author zhangxc
	* @date 2016年7月19日
	* @param user
	* @param userDto
	 */
	private DemoAddressPO getUserAddressPO(DemoUserAddressDto addressDto){
	    DemoAddressPO addressPo = new DemoAddressPO();
	    addressPo.setString("PROVINCE", StringUtils.listToString(addressDto.getProvince(), ','));
	    addressPo.setInteger("COUNTRY", addressDto.getCountry());
	    addressPo.setString("ADDRESS",addressDto.getAddress());
	    addressPo.setInteger("STATUS", addressDto.getStatus());
	    addressPo.setDouble("MILLS", addressDto.getMills());
	    addressPo.setDate("address_date", addressDto.getAddressDate());
        return addressPo;
    }

	
	/**
	 * 查询用户的地址信息
	* @author zhangxc
	* @date 2016年7月12日
	* @param id
	* @throws ServiceBizException
	* (non-Javadoc)
	* @see com.yonyou.dms.manage.service.sample.DemoUserService#queryUserAddress(java.lang.Long)
	 */
    @Override
    public List<Map> queryUserAddress(Long id) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select DEALER_CODE,t.user_id,t.ADDRESS_ID,t.ADDRESS,t.COUNTRY,t.PROVINCE,t.STATUS,MILLS,ADDRESS_DATE  from tmp_tm_address t where 1=1 and USER_ID = ?");
        List<Object> queryParams = new ArrayList<>();
        queryParams.add(id);
        return DAOUtil.findAll(sqlSb.toString(), queryParams);
    }
    
    /**
     * 查询用户的地址信息
    * @author zhangxc
    * @date 2016年7月12日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.sample.DemoUserService#queryUserAddress(java.lang.Long)
     */
    @Override
    public PageInfoDto queryUserAddressPage(Long id) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select DEALER_CODE,t.user_id,t.ADDRESS_ID,t.ADDRESS,t.COUNTRY,t.PROVINCE,t.STATUS,MILLS,ADDRESS_DATE  from tmp_tm_address t where 1=1 and USER_ID = ?");
        List<Object> queryParams = new ArrayList<>();
        queryParams.add(id);
        return DAOUtil.pageQuery(sqlSb.toString(), queryParams);
    }


    
    /**
    * @author zhangxc
    * @date 2016年7月20日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.sample.DemoUserService#queryUsersForExport(java.util.Map)
    */
    @Override
    public List<Map> queryUsersForExport(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<>();
        String sql = getQuerySql(queryParam,params);
        List<Map> resultList = DAOUtil.findAll(sql,params);
        return resultList;
    }
}
