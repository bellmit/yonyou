package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TiDmsNSalesPersonnelDto;
import com.yonyou.dms.common.domains.PO.basedata.TiDmsNSalesPersonnelPO;
import com.yonyou.dms.common.domains.PO.basedata.TmSalesPersonnelPO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 
 * Title:SOTDCS002CloudImpl
 * Description: 展厅销售人员信息同步(DMS新增)上报接收DMS->DCS
 * @author DC
 * @date 2017年4月13日 下午3:41:45
 * result msg 1：成功 0：失败
 */
@Service
public class SOTDCS002CloudImpl extends BaseCloudImpl implements SOTDCS002Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS056CloudImpl.class);

	@Override
	public String handleExecutor(List<TiDmsNSalesPersonnelDto> dtoList) throws Exception {
		String msg = "1";
		logger.info("====展厅销售人员信息同步(DMS新增)接收开始====");
		for (TiDmsNSalesPersonnelDto entry : dtoList) {
			try {
				insertData(entry);
				insertTMData(entry);
			} catch (Exception e) {
				logger.error("展厅销售人员信息同步(DMS新增)接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====展厅销售人员信息同步(DMS新增)接收结束===="); 
		return msg;
	}

	// 插入新增的客户信息
		public void insertData(TiDmsNSalesPersonnelDto vo) throws Exception {
			TiDmsNSalesPersonnelPO insertPO = new TiDmsNSalesPersonnelPO();
			insertPO.setString("USER_ID", vo.getUserId());// 销售人员ID
			insertPO.setString("DEALER_CODE", vo.getDealerCode());// 经销商代码
			insertPO.setString("USER_NAME", vo.getUserName());// 销售人员名称
			insertPO.setString("ROLE_ID", vo.getRoleId());// 角色ID
			insertPO.setString("EMAIL", vo.getEmail());// 销售人员邮箱
			insertPO.setString("MOBILE", vo.getMobile());// 销售人员手机号
			insertPO.setString("ROLE_NAME", vo.getRoleName());// 角色名称
			insertPO.setString("IS_DCC_VIEW", vo.getIsDccView());// 是否可以查看DCC 客户
			insertPO.setString("USER_STATUS", vo.getUserStatus());// 销售人员状态
			insertPO.setDate("CREATE_DATE", new Date());// 创建日期
			insertPO.setString("IS_SEND", "0");// 同步标志
			insertPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);// 创建者
			// 插入数据
			insertPO.insert();
		}

		// 认证中心用销售人员数据插入/更新
		public void insertTMData(TiDmsNSalesPersonnelDto vo) throws Exception {
			TmSalesPersonnelPO insertPO = TmSalesPersonnelPO.findFirst("USER_ID = ? AND DEALER_CODE = ?", vo.getUserId(),vo.getDealerCode());
			if(insertPO == null ){
				insertPO = new TmSalesPersonnelPO();
			}
			insertPO.setString("USER_ID", vo.getUserId());// 销售人员ID
			insertPO.setString("DEALER_CODE", vo.getDealerCode());// 经销商代码
			insertPO.setString("USER_NAME", vo.getUserName());// 销售人员名称
			insertPO.setString("SAL_PASSWORD", vo.getSalPassword());// 销售人员密码
			if (!StringUtils.isNullOrEmpty(vo.getRoleId())) {
				insertPO.setString("ROLE_ID", vo.getRoleId());// 角色ID
			}
			insertPO.setString("EMAIL", vo.getEmail());// 销售人员邮箱

			if (!StringUtils.isNullOrEmpty(vo.getRoleName())) {
				insertPO.setString("ROLE_NAME", vo.getRoleName());// 角色名称
			}
			insertPO.setString("IS_DCC_VIEW", vo.getIsDccView());// 是否可以查看DCC 客户
			insertPO.setString("USER_STATUS", vo.getUserStatus());// 销售人员状态
			insertPO.setString("USER_CODE", vo.getUserCode());// 用户Code
			List<TmSalesPersonnelPO> list = TmSalesPersonnelPO.find("USER_ID = ? AND DEALER_CODE = ?",vo.getUserId(), vo.getDealerCode());
			// 存在的场合做更新
			if (list != null && list.size() > 0 && list.get(0) != null) {
				insertPO.setTimestamp("UPDATE_DATE", new Date());// 更新日期
				// 更新数据
				insertPO.saveIt();
			} else {
				// 不存在的场合做插入
				Date now = new Date();
				insertPO.setTimestamp("CREATE_DATE", now);// 创建日期
				insertPO.setTimestamp("UPDATE_DATE", now);// 更新日期
				insertPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);// 创建者
				// 插入数据
				insertPO.insert();
			}
		}

}
