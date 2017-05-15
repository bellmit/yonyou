package com.yonyou.dms.web.service.basedata.authority;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.web.domains.DTO.basedata.authority.PoseDTO;

public interface PoseService {

	public PageInfoDto queryList(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> getSeriesList(String id) throws ServiceBizException;

	public PageInfoDto selectRole(Map<String, String> queryParam) throws ServiceBizException;

	public List<Map> getFuncList() throws ServiceBizException;

	public void checkPose(String poseCode, Map<String, Object> message) throws ServiceBizException;

	public Long addPose(PoseDTO poseDto) throws ServiceBizException;

	public Map<String, Object> findByPose(Long id) throws ServiceBizException;

	public void modifyPose(Long id, PoseDTO poseDto)  throws ServiceBizException;

	public List<Map> getPoseRoles(Long id) throws ServiceBizException;

	public String checkPose(Map<String, String> params);

}
