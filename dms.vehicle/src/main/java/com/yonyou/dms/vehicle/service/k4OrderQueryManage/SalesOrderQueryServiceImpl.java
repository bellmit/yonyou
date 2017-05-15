package com.yonyou.dms.vehicle.service.k4OrderQueryManage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.k4OrderQueryManage.SalesOrderQueryDao;

@Service
public class SalesOrderQueryServiceImpl implements SalesOrderQueryService {

	@Autowired
	SalesOrderQueryDao dao;

	/**
	 * 加载查询
	 */
	@Override
	public PageInfoDto queryList(Map<String, String> queryParam, LoginInfoDto loginInfo) throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryList(queryParam, loginInfo);
		return pgInfo;
	}

	/**
	 * 销售订单信息下载查询
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	@Override
	public List<Map> findSalesOrderSuccList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Map> list = dao.querySalesOrderForExport(queryParam, loginInfo);
		return list;
	}

	/**
	 * 根据ID获取详细信息
	 */
	@Override
	public Map<String, Object> queryDetail(String id, LoginInfoDto loginInfo) throws ServiceBizException {
		Map<String, Object> map = dao.queryDetail(id, loginInfo);
		Map<String, Object> map1 = new HashMap();
		String orderType = "";
		int parseInt = Integer.parseInt(map.get("ORDER_TYPE").toString());
		if (OemDictCodeConstants.ORDER_TYPE_DOMESTIC_01.equals(parseInt)) {
			orderType = "指派订单";
		}
		if (OemDictCodeConstants.ORDER_TYPE_DOMESTIC_02.equals(parseInt)) {
			orderType = "紧急订单";
		}
		if (OemDictCodeConstants.ORDER_TYPE_DOMESTIC_03.equals(parseInt)) {
			orderType = "直销订单";
		}
		if (OemDictCodeConstants.ORDER_TYPE_DOMESTIC_04.equals(parseInt)) {
			orderType = "常规订单";
		}
		if (OemDictCodeConstants.ORDER_TYPE_DOMESTIC_05.equals(parseInt)) {
			orderType = "官网订单";
		}
		int pars = Integer.parseInt(map.get("ORDER_STATUS").toString());
		String orderStatus = "";
		if (OemDictCodeConstants.ORDER_STATUS_01.equals(pars)) {
			orderStatus = "未提报(期货)";
		}
		if (OemDictCodeConstants.ORDER_STATUS_02.equals(pars)) {
			orderStatus = "待审核(期货)";
		}
		if (OemDictCodeConstants.ORDER_STATUS_03.equals(pars)) {
			orderStatus = "审批中(期货)";
		}
		if (OemDictCodeConstants.ORDER_STATUS_04.equals(pars)) {
			orderStatus = "定金未确认";
		}
		if (OemDictCodeConstants.ORDER_STATUS_05.equals(pars)) {
			orderStatus = "定金已确认";
		}
		if (OemDictCodeConstants.ORDER_STATUS_06.equals(pars)) {
			orderStatus = "订单已确认";
		}
		if (OemDictCodeConstants.ORDER_STATUS_07.equals(pars)) {
			orderStatus = "资源已分配";
		}
		if (OemDictCodeConstants.ORDER_STATUS_08.equals(pars)) {
			orderStatus = "已取消";
		}
		if (OemDictCodeConstants.ORDER_STATUS_09.equals(pars)) {
			orderStatus = "已撤单";
		}
		String VEHICLE_USE = "";
		int vehicle_use = Integer.parseInt(map.get("VEHICLE_USE").toString());
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_68.equals(vehicle_use)) {
			VEHICLE_USE = "普通";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_69.equals(vehicle_use)) {
			VEHICLE_USE = "试乘试驾车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_70.equals(vehicle_use)) {
			VEHICLE_USE = " 员工购车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_71.equals(vehicle_use)) {
			VEHICLE_USE = "公司租赁车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_72.equals(vehicle_use)) {
			VEHICLE_USE = "售后服务车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_73.equals(vehicle_use)) {
			VEHICLE_USE = "其他";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_74.equals(vehicle_use)) {
			VEHICLE_USE = "媒体置换车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_75.equals(vehicle_use)) {
			VEHICLE_USE = "媒体试驾车-申固";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_76.equals(vehicle_use)) {
			VEHICLE_USE = "大客户购车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_77.equals(vehicle_use)) {
			VEHICLE_USE = "瑕疵车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_78.equals(vehicle_use)) {
			VEHICLE_USE = "瑕疵车其他";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_79.equals(vehicle_use)) {
			VEHICLE_USE = "媒体优惠购车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_80.equals(vehicle_use)) {
			VEHICLE_USE = "长库龄车辆";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_81.equals(vehicle_use)) {
			VEHICLE_USE = "81折销售部试驾车辆";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_82.equals(vehicle_use)) {
			VEHICLE_USE = "售后培训用车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_83.equals(vehicle_use)) {
			VEHICLE_USE = "媒体试驾车-非申固";
		}
		String IS_REBATE = "";
		int is_rebate = Integer.parseInt(map.get("IS_REBATE").toString());
		if (OemDictCodeConstants.IF_TYPE_YES.equals(is_rebate)) {
			IS_REBATE = "是";
		}
		if (OemDictCodeConstants.IF_TYPE_NO.equals(is_rebate)) {
			IS_REBATE = "否";
		}
		String IS_TOP = "";
		if (map.get("IS_TOP") != null) {
			int is_top = Integer.parseInt(map.get("IS_TOP").toString());
			if (OemDictCodeConstants.IF_TYPE_YES.equals(is_top)) {
				IS_TOP = "是";
			}
			if (OemDictCodeConstants.IF_TYPE_NO.equals(is_top)) {
				IS_TOP = "否";
			}

		}
		String PAYMENT_TYPE = "";
		int payment_type = Integer.parseInt(map.get("PAYMENT_TYPE").toString());
		if (OemDictCodeConstants.K4_PAYMENT_01.equals(payment_type)) {
			PAYMENT_TYPE = "现金支付";
		}
		if (OemDictCodeConstants.K4_PAYMENT_02.equals(payment_type)) {
			PAYMENT_TYPE = "广汽汇理汽车金融有限公司";
		}
		if (OemDictCodeConstants.K4_PAYMENT_03.equals(payment_type)) {
			PAYMENT_TYPE = "菲亚特汽车金融有限责任公司";
		}
		if (OemDictCodeConstants.K4_PAYMENT_04.equals(payment_type)) {
			PAYMENT_TYPE = "兴业银行";
		}
		if (OemDictCodeConstants.K4_PAYMENT_05.equals(payment_type)) {
			PAYMENT_TYPE = "交通银行";
		}
		if (OemDictCodeConstants.K4_PAYMENT_06.equals(payment_type)) {
			PAYMENT_TYPE = "中国银行上海静安支行";
		}
		if (OemDictCodeConstants.K4_PAYMENT_07.equals(payment_type)) {
			PAYMENT_TYPE = "建行融资";
		}
		if (OemDictCodeConstants.K4_PAYMENT_08.equals(payment_type)) {
			PAYMENT_TYPE = "中信银行承兑汇票";
		}
		map.put("IS_TOP1", IS_TOP);
		map.put("PAYMENT_TYPE1", PAYMENT_TYPE);
		map.put("IS_REBATE1", IS_REBATE);
		map.put("VEHICLE_USE1", VEHICLE_USE);
		map.put("ORDER_TYPE1", orderType);
		map.put("ORDER_STATUS1", orderStatus);
		return map;
	}

	/**
	 * 加载查询(经销商端)
	 */
	@Override
	public PageInfoDto queryDealerList(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryDealerList(queryParam, loginInfo);
		return pgInfo;
	}

	/**
	 * 根据ID获取详细信息(经销商端)
	 */
	@Override
	public Map<String, Object> queryDealerDetail(String id, LoginInfoDto loginInfo) throws ServiceBizException {
		Map<String, Object> map = dao.queryDealerDetail(id, loginInfo);

		String orderType = "";
		int parseInt = Integer.parseInt(map.get("ORDER_TYPE").toString());
		if (OemDictCodeConstants.ORDER_TYPE_DOMESTIC_01.equals(parseInt)) {
			orderType = "指派订单";
		}
		if (OemDictCodeConstants.ORDER_TYPE_DOMESTIC_02.equals(parseInt)) {
			orderType = "紧急订单";
		}
		if (OemDictCodeConstants.ORDER_TYPE_DOMESTIC_03.equals(parseInt)) {
			orderType = "直销订单";
		}
		if (OemDictCodeConstants.ORDER_TYPE_DOMESTIC_04.equals(parseInt)) {
			orderType = "常规订单";
		}
		if (OemDictCodeConstants.ORDER_TYPE_DOMESTIC_05.equals(parseInt)) {
			orderType = "官网订单";
		}
		int pars = Integer.parseInt(map.get("ORDER_STATUS").toString());
		String orderStatus = "";
		if (OemDictCodeConstants.ORDER_STATUS_01.equals(pars)) {
			orderStatus = "未提报(期货)";
		}
		if (OemDictCodeConstants.ORDER_STATUS_02.equals(pars)) {
			orderStatus = "待审核(期货)";
		}
		if (OemDictCodeConstants.ORDER_STATUS_03.equals(pars)) {
			orderStatus = "审批中(期货)";
		}
		if (OemDictCodeConstants.ORDER_STATUS_04.equals(pars)) {
			orderStatus = "定金未确认";
		}
		if (OemDictCodeConstants.ORDER_STATUS_05.equals(pars)) {
			orderStatus = "定金已确认";
		}
		if (OemDictCodeConstants.ORDER_STATUS_06.equals(pars)) {
			orderStatus = "订单已确认";
		}
		if (OemDictCodeConstants.ORDER_STATUS_07.equals(pars)) {
			orderStatus = "资源已分配";
		}
		if (OemDictCodeConstants.ORDER_STATUS_08.equals(pars)) {
			orderStatus = "已取消";
		}
		if (OemDictCodeConstants.ORDER_STATUS_09.equals(pars)) {
			orderStatus = "已撤单";
		}
		String VEHICLE_USE = "";
		int vehicle_use = Integer.parseInt(map.get("VEHICLE_USE").toString());
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_68.equals(vehicle_use)) {
			VEHICLE_USE = "普通";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_69.equals(vehicle_use)) {
			VEHICLE_USE = "试乘试驾车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_70.equals(vehicle_use)) {
			VEHICLE_USE = " 员工购车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_71.equals(vehicle_use)) {
			VEHICLE_USE = "公司租赁车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_72.equals(vehicle_use)) {
			VEHICLE_USE = "售后服务车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_73.equals(vehicle_use)) {
			VEHICLE_USE = "其他";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_74.equals(vehicle_use)) {
			VEHICLE_USE = "媒体置换车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_75.equals(vehicle_use)) {
			VEHICLE_USE = "媒体试驾车-申固";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_76.equals(vehicle_use)) {
			VEHICLE_USE = "大客户购车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_77.equals(vehicle_use)) {
			VEHICLE_USE = "瑕疵车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_78.equals(vehicle_use)) {
			VEHICLE_USE = "瑕疵车其他";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_79.equals(vehicle_use)) {
			VEHICLE_USE = "媒体优惠购车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_80.equals(vehicle_use)) {
			VEHICLE_USE = "长库龄车辆";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_81.equals(vehicle_use)) {
			VEHICLE_USE = "81折销售部试驾车辆";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_82.equals(vehicle_use)) {
			VEHICLE_USE = "售后培训用车";
		}
		if (OemDictCodeConstants.VEHICLE_USAGE_TYPE_83.equals(vehicle_use)) {
			VEHICLE_USE = "媒体试驾车-非申固";
		}
		String IS_REBATE = "";
		int is_rebate = Integer.parseInt(map.get("IS_REBATE").toString());
		if (OemDictCodeConstants.IF_TYPE_YES.equals(is_rebate)) {
			IS_REBATE = "是";
		}
		if (OemDictCodeConstants.IF_TYPE_NO.equals(is_rebate)) {
			IS_REBATE = "否";
		}
		String IS_TOP = "";
		if (map.get("IS_TOP") != null) {
			int is_top = Integer.parseInt(map.get("IS_TOP").toString());
			if (OemDictCodeConstants.IF_TYPE_YES.equals(is_top)) {
				IS_TOP = "是";
			}
			if (OemDictCodeConstants.IF_TYPE_NO.equals(is_top)) {
				IS_TOP = "否";
			}

		}
		String PAYMENT_TYPE = "";
		int payment_type = Integer.parseInt(map.get("PAYMENT_TYPE").toString());
		if (OemDictCodeConstants.K4_PAYMENT_01.equals(payment_type)) {
			PAYMENT_TYPE = "现金支付";
		}
		if (OemDictCodeConstants.K4_PAYMENT_02.equals(payment_type)) {
			PAYMENT_TYPE = "广汽汇理汽车金融有限公司";
		}
		if (OemDictCodeConstants.K4_PAYMENT_03.equals(payment_type)) {
			PAYMENT_TYPE = "菲亚特汽车金融有限责任公司";
		}
		if (OemDictCodeConstants.K4_PAYMENT_04.equals(payment_type)) {
			PAYMENT_TYPE = "兴业银行";
		}
		if (OemDictCodeConstants.K4_PAYMENT_05.equals(payment_type)) {
			PAYMENT_TYPE = "交通银行";
		}
		if (OemDictCodeConstants.K4_PAYMENT_06.equals(payment_type)) {
			PAYMENT_TYPE = "中国银行上海静安支行";
		}
		if (OemDictCodeConstants.K4_PAYMENT_07.equals(payment_type)) {
			PAYMENT_TYPE = "建行融资";
		}
		if (OemDictCodeConstants.K4_PAYMENT_08.equals(payment_type)) {
			PAYMENT_TYPE = "中信银行承兑汇票";
		}
		map.put("IS_TOP1", IS_TOP);
		map.put("PAYMENT_TYPE1", PAYMENT_TYPE);
		map.put("IS_REBATE1", IS_REBATE);
		map.put("VEHICLE_USE1", VEHICLE_USE);
		map.put("ORDER_TYPE1", orderType);
		map.put("ORDER_STATUS1", orderStatus);

		return map;
	}

	/**
	 * 销售订单下载查询(经销商段)
	 */
	@Override
	public List<Map> findDealerSalesOrderSuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		List<Map> list = dao.queryDealerSalesOrderForExport(queryParam, loginInfo);
		return list;
	}

	@Override
	public PageInfoDto orderRecords(Map<String, String> queryParam, LoginInfoDto loginInfo) throws ServiceBizException {
		String orderId = CommonUtils.checkNull(queryParam.get("id"));
		return dao.orderRecords(orderId, loginInfo);
	}

	@Override
	public List<Map> findsalesOrder(String id) {
		String sql = "select CODE_ID,CODE_DESC from TC_CODE_DCS where type=" + OemDictCodeConstants.SALE_ORDER_TYPE
				+ " and num>(select num from TC_CODE_DCS where CODE_ID=" + id + ") order by num";
		return OemDAOUtil.findAll(sql, null);
	}

}
