/*
2 * Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 *
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : cmol.common.function
 *
 * @File name : CommonConstants.java
 *
 * @Author : zhangxianchao
 *
 * @Date : 2016年2月24日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年2月24日    zhangxianchao    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.function.common;

/*
 *
 * @author zhangxianchao
 * CommonConstants
 * @date 2016年2月24日
 */

public class DictCodeConstants {

	/**
	 * 4S默认车主编号
	 */
	public static final String DEALER_DEFAULT_OWNER_NO = "888888888888";

	/**
	 * 预收款客户类型
	 */
	public static final String DICT_PRE_PAY_CUSTOMER_TYPE_TYPE = "1370"; // 预收款客户类型类别
	public static final String DICT_PRE_PAY_CUSTOMER_TYPE_OWNER = "13701001"; // 车主
	public static final String DICT_PRE_PAY_CUSTOMER_TYPE_CUSTOMER = "13701002"; // 业务往来客户
	public static final String DICT_PRE_PAY_CUSTOMER_TYPE_POTENTIAL_CUSTOMER = "13701003"; // 潜在客户类型
	public static final String DICT_PRE_PAY_CUSTOMER_TYPE_INSURANCE = "13701004"; // 保险公司
	/**
	 * 客户重要级别
	 */
	public static final String DICT_CUSTOMER_IMPORTANT_LEVEL = "1399"; // 客户重要级别
	public static final String DICT_CUSTOMER_IMPORTANT_LEVEL_VERY = "13991001"; // 非常重要
	public static final String DICT_CUSTOMER_IMPORTANT = "13991002"; // 重要
	public static final String DICT_CUSTOMER_IMPORTANT_LEVEL_NORMAL = "13991003"; // 普通
	/**
	 * 跟进方式
	 */
	public static final String DICT_PROM_WAY = "1325"; // 跟进方式
	public static final String DICT_PROM_WAY_VISIT = "13251001"; // 外出拜访
	public static final String DICT_PROM_WAY_RECEPTION = "13251002"; // 展厅现场接待
	public static final String DICT_PROM_WAY_PHONE = "13251003"; // 电话回访
	public static final String DICT_PROM_WAY_EMAIL = "13251004"; // 电子邮件
	public static final String DICT_PROM_WAY_MESSAGE = "13251005"; // 短信问候
	public static final String DICT_PROM_WAY_POST = "13251006"; // 邮寄资料
	/**
	 * 跟进结果
	 */
	public static final String DICT_PROM_RESULT = "1334"; // 执行结果
	public static final String DICT_PROM_RESULT_MAINTAIN_CUSTOMER = "13341001"; // 维护客户信息
	public static final String DICT_PROM_RESULT_PERFORM_FAILURE = "13341002"; // 执行失败
	public static final String DICT_PROM_RESULT_DELAY_VISIT = "13341003"; // 延期访问
	public static final String DICT_PROM_RESULT_NEW_CUSTOMER = "13341004"; // 新建客户信息
	public static final String DICT_PROM_RESULT_NO_INTENT = "13341005"; // 客户无意向
	public static final String DICT_PROM_RESULT_DROP_VISIT = "13341006"; // 放弃访问
	public static final String DICT_PROM_RESULT_INACCURATE_INFO = "13341007"; // 信息不准
	public static final String DICT_PROM_RESULT_HAVE_BARGAIN = "13341008"; // 已成交
	public static final String DICT_PROM_RESULT_CONTINUE = "13341009"; // 继续跟进

	/**
	 * 收款类型
	 */
	public static final String DICT_RECEIVE_SORT_TYPE = "1112"; // 收款类型类别
	public static final String DICT_RECEIVE_SORT_RECEIVE = "11121001"; // 正常收款
	public static final String DICT_RECEIVE_SORT_USE_RESIDUAL = "11121002"; // 使用余款
	public static final String DICT_RECEIVE_SORT_REFUNDMENT = "11121003"; // 多收退款
	public static final String DICT_RECEIVE_SORT_PRE_RECEIVE = "11121004"; // 预收款
	public static final String DICT_RECEIVE_SORT_INSURANCE = "11121005"; // 保险费
	public static final String DICT_RECEIVE_SORT_MEMBERCARDSTORE = "11121006"; // 会员卡储值
	public static final String DICT_RECEIVE_SORT_MEMBERCARD_CREDIT_USED = "11121007"; // 收款时
																						// 使用积分
	public static final String DICT_RECEIVE_SORT_MEMBERCARD_STORE_USED = "11121008"; // 收款时
																						// 使用会员卡储值
	public static final String DICT_RECEIVE_SORT_MEMBER_ACTIVITY_FUND = "11121009"; // 会员活动专项资金
	public static final String DICT_RECEIVE_SORT_MEMBER_ACTIVITY_FUND_USED = "11121010"; // 收款时
																							// 使用会员活动专项资金

	/**
	 * 保有车辆客户类型
	 */
	public static final String DICT_CUS_TYPE = "1054"; // 保有车辆客户类型
	public static final String DICT_CUS_TYPE_ORDINARY = "10541001"; // 普通潜客
	public static final String DICT_CUS_TYPE_REPLACE = "10541002"; // 置换意向潜客
	/**
	 * 电商订单状态
	 */
	public static final String DICT_DMS_ESC_STATUS = "1619"; // 电商订单状态
	public static final String DICT_DMS_ESC_STATUS_VALID = "16191001"; // 有效
	public static final String DICT_DMS_ESC_STATUS_OVERDUE = "16191002"; // 已逾期
	public static final String DICT_DMS_ESC_STATUS_UNVALID = "16191003"; // 已取消
	/**
	 * 电商订单类型
	 */
	public static final String DICT_DMS_ESC_TYPE = "1618"; // 电商订单类型
	public static final String DICT_DMS_ESC_TYPE_FUTURES = "16181002"; // 期货
	public static final String DICT_DMS_ESC_TYPE_STOCK = "16181001"; // 现货

	// 公共资源状态
	public final static Integer COMMON_RESOURCE_STATUS = 2080;
	public final static Integer COMMON_RESOURCE_STATUS_01 = 20801001; // 未下发
	public final static Integer COMMON_RESOURCE_STATUS_02 = 20801002; // 已下发

	public final static String GROUP_TYPE = "9008";
	public final static String GROUP_TYPE_DOMESTIC = "90081001"; // 国产
	public final static String GROUP_TYPE_IMPORT = "90081002"; // 进口

	// 车辆生命状态
	public final static Integer LIF_CYCLE = 1152;
	public final static Integer LIF_CYCLE_01 = 11521001; // 生产线上
	public final static Integer LIF_CYCLE_02 = 11521002; // 车厂在库
	public final static Integer LIF_CYCLE_03 = 11521003; // 经销商在途
	public final static Integer LIF_CYCLE_04 = 11521004; // 经销商在库
	public final static Integer LIF_CYCLE_05 = 11521005; // 已实销
	public final static Integer LIF_CYCLE_06 = 11521006; // 无效

	/**
	 * 售后无牌照
	 */
	public static final String CON_LICENSE_NULL = "无牌照";
	/**
	 * 证件类别
	 */
	public static final String DICT_CERTIFICATE_TYPE = "1239"; // 证件类别类别
	public static final String DICT_CERTIFICATE_TYPE_IDENTITY_CARD = "12391001"; // 居民身份证
	public static final String DICT_CERTIFICATE_TYPE_PASSPORT = "12391002"; // 护照
	public static final String DICT_CERTIFICATE_TYPE_OFFICER = "12391003"; // 军官
	public static final String DICT_CERTIFICATE_TYPE_SOLDIER = "12391004"; // 士兵
	public static final String DICT_CERTIFICATE_TYPE_POLICE_OFFICER = "12391005"; // 警官
	public static final String DICT_CERTIFICATE_TYPE_OTHER = "12391006"; // 其他
	public static final String DICT_CERTIFICATE_TYPE_ORGAN_CODE = "12391007"; // 机构代码
	/**
	 * 车主性质
	 */
	public static final String DICT_OWNER_PROPERTY_TYPE = "1190"; // 车主性质类别
	public static final String DICT_OWNER_PROPERTY_COMPANY = "11901001"; // 公司
	public static final String DICT_OWNER_PROPERTY_PERSONAL = "11901002"; // 个人

	/**
	 * 装饰装潢订单入账状态
	 */
	public static final String DICT_SN_FINISHED_STATUS = "1406";
	public static final String DICT_SN_FINISHED_STATUS_NOT = "14061001"; // 未入账
	public static final String DICT_SN_FINISHED_STATUS_PART = "14061002"; // 部分入账
	public static final String DICT_SN_FINISHED_STATUS_ALL = "14061003"; // 全部入账

	/**
	 * 营运性质
	 */
	public static final String DICT_BUSINESS_KIND_TYPE = "1194"; // 营运性质类别
	public static final String DICT_BUSINESS_KIND_PASSENGER = "11941001"; // 客运
	public static final String DICT_BUSINESS_KIND_FREIGHT = "11941002"; // 货运

	/**
	 * 客户类型
	 */
	public static final String DICT_CUSTOMER_TYPE = "1018"; // 客户类型
	public static final String DICT_CUSTOMER_TYPE_INDIVIDUAL = "10181001"; // 个人
	public static final String DICT_CUSTOMER_TYPE_COMPANY = "10181002"; // 公司

	/**
	 * 车辆燃油类型
	 */
	public static final String DICT_OIL_TYPE = "5055"; // 车辆燃油类型类别
	public static final String DICT_OIL_TYPE_PETROL = "50551001"; // 汽油
	public static final String DICT_OIL_TYPE_DIESEL = "50551002"; // 柴油

	/* 销售订单类别 */
	public static final String FDICT_SALES_ORDER_SORT_TYPE = "1386"; // 销售订单类别
	public static final String FDICT_SALES_ORDER_SORT_GENERAL = "13861001"; // 普通订单
	public static final String FDICT_SALES_ORDER_SORT_PRE = "13861002"; // 售前装潢

	/**
	 * 投诉处理状态
	 */
	public static final String DICT_COMPLAINT_DEAL_STATUS_TYPE = "1287"; // 投诉处理状态类别
	public static final String DICT_COMPLAINT_DEAL_STATUS_NOT_DEAL = "12871001"; // 未处理
	public static final String DICT_COMPLAINT_DEAL_STATUS_DEALED = "12871002"; // 已处理

	/**
	 * 审核类型
	 */
	public static final String DICT_AUDITING_TYPE = "1340"; // 审核类型
	public static final String DICT_AUDITING_TYPE_MANAGE = "13401001"; // 经理审核
	public static final String DICT_AUDITING_TYPE_FINANCE = "13401002"; // 财务审核

	/**
	 * 使用状态
	 */
	public static final String DICT_IN_USE_TYPE = "1210"; // 使用状态类别
	public static final String DICT_IN_USE_START = "12101001"; // 启用
	public static final String DICT_IN_USE_STOP = "12101002"; // 停用
	/**
	 * 是否
	 */
	public static final String DICT_IS_TYPE = "1278"; // 是否类别
	public static final String DICT_IS_YES = "12781001"; // 是
	public static final String DICT_IS_NO = "12781002"; // 否

	/**
	 * 结算精度
	 */
	public static final String DICT_YUAN = "12921001"; // 元
	public static final String DICT_JIAO = "12921002"; // 角
	public static final String DICT_FEN = "12921003"; // 分

	/**
	 * 订单审核结果
	 */
	public static final String DICT_AUDITING_RESULT = "1315"; // 订单审核结果
	public static final String DICT_AUDITING_RESULT_ACCEPT = "13151001"; // 审核通过
	public static final String DICT_AUDITING_RESULT_REJECT = "13151002"; // 审核驳回
	/**
	 * 意向订单状态
	 */
	public static final String DICT_INTENT_SO_STATUS = "5116"; // 意向订单状态
	public static final String DICT_INTENT_SO_STATUS_NOT_AUDIT = "51161001"; // 未提交审核
	public static final String DICT_INTENT_SO_STATUS_MANAGER_AUDITING = "51161002"; // 经理审核中
	public static final String DICT_INTENT_SO_STATUS_ACCOUNTANT_AUDITING = "51161003"; // 财务审核中
	public static final String DICT_INTENT_SO_STATUS_MANAGER_REJECT = "51161004"; // 经理驳回
	public static final String DICT_INTENT_SO_STATUS_ACCOUNTANT_REJECT = "51161005"; // 财务驳回
	public static final String DICT_INTENT_SO_STATUS_SYSTEM_REJECT = "51161006"; // 系统驳回
	public static final String DICT_INTENT_SO_STATUS_HAVE_OUT_STOCK = "51161007"; // 结算中
	public static final String DICT_INTENT_SO_STATUS_HAVE_BALANCE = "51161008"; // 已结算。
	public static final String DICT_INTENT_SO_STATUS_HAVE_UNTREAD = "51161009"; // 已退回
	public static final String DICT_INTENT_SO_STATUS_HAVE_CLOSED = "51161010"; // 已成交
	public static final String DICT_INTENT_SO_STATUS_FAIL = "51161011"; // 成交失败
	/**
	 * 批售车辆状态
	 */
	public static final String DICT_WHOLESALE_CAR_STATUS = "2015"; // 批售车辆状态
	public static final String DICT_WHOLESALE_CAR_STATUS_NOT = "20151001"; // 未提交
	public static final String DICT_WHOLESALE_CAR_STATUS_AUDITING = "20151002"; // 审核中
	public static final String DICT_WHOLESALE_CAR_STATUS_PASS = "20151003"; // 审核通过
	public static final String DICT_WHOLESALE_CAR_STATUS_REFUSE = "20151004"; // 审核否决
	/**
	 * 交车方式
	 */
	public static final String DICT_DELIVERY_MODE = "1302"; // 交车方式
	public static final String DICT_DELIVERY_MODE_LOCAL = "13021001"; // 本地交车
	public static final String DICT_DELIVERY_MODE_ENTRUST = "13021002"; // 委托交车

	public static final String DICT_IS_CHECKED = "10571001"; // 是
	/**
	 * 操作日志类型
	 */
	public static final String DICT_ASCLOG_TYPE = "1206"; // 操作日志类型类别
	public static final String DICT_ASCLOG_SYSTEM_MANAGE = "12061001"; // 系统管理
	public static final String DICT_ASCLOG_CLIENT_MANAGE = "12061008"; // 客户管理
	public static final String DICT_ASCLOG_TOOL_MANAGE = "12061007"; // 工具管理
	public static final String DICT_ASCLOG_OPERATION_RECEIVE = "12061002"; // 业务接待
	public static final String DICT_ASCLOG_WORKSHOP_MANAGE = "12061003"; // 车间管理
	public static final String DICT_ASCLOG_BALANCE_MANAGE = "12061004"; // 结算管理
	public static final String DICT_ASCLOG_CLAIM_MANAGE = "12061006"; // 索赔管理
	public static final String DICT_ASCLOG_PART_MANAGE = "12061005"; // 配件管理
	public static final String DICT_SEND_INFO = "12061009"; // INFOX失败消息
	public static final String DICT_SEND_SUCCEED = "12061010"; // INFOX成功消息
	public static final String DICT_ASCLOG_VEHICLE_MANAGE = "12061012"; // 整车管理
	public static final String DICT_ASCLOG_MEMBER_MANAGE = "12061011"; // 会员管理
	public static final String DICT_ASCLOG_DERIVATIVE_BUSINESS = "12061013"; // 衍生业务
	public static final String DICT_ASCLOG_MARKET_ACTIVITY = "12061014"; // 市场活动
	public static final String DICT_BALANCE_SEND_SUCCEED = "12061015"; // 结算单INFOX成功消息
	public static final String DICT_BALANCE_SEND_FAIL = "12061016"; // 结算单INFOX失败消息
	// 有效状态
	public static final int STATUS_IS_VALID = 10011001;
	// 无效状态
	public static final int STATUS_NOT_VALID = 10011002;

	// 性别
	public static final int GENDER = 1002;
	// 性别男
	public static final int GENDER_MALE = 10021001;
	// 性别女
	public static final int GENDER_FEMALE = 10021002;
	// 性别未知
	public static final int GENDER_NOT_DEFINE = 10021003;

	// 是否
	public static final int YESORNO = 127;
	// 是
	public static final int STATUS_IS_YES = 12781001;
	// 否
	public static final int STATUS_IS_NOT = 12781002;

	// 是
	public static final long IS_YES = 12781001;

	// 否
	public static final long IS_NOT = 12781002;

	// 启用状态
	public static final int STATUS_QY = 12101001;
	// 禁用状态
	public static final int STATUS_JY = 12101002;

	// 系统参数 10131001
	public static final int SYSTEM_PARAM_TYPE_JSJEYZFS = 10131001;

	public static final int DELIVERY_STATUS_ALL = 13061001;

	public static final int DELIVERY_STATUS_PORTION = 13061002;

	/**
	 * 用户控制权限
	 */
	// 接待
	public static final int RECEPTION_MAINTAIN_OPTION = 1;
	// 配件
	public static final int PARTS_OPTION = 2;
	// 结算
	public static final int SETTLE_ACCOUNTS_OPTION = 3;
	// 系统提醒
	public static final int SYSTEM_ATTENTION_OPTION = 7;
	// 客户管理
	public static final int CUSTOMER_OPTION = 8;
	// 整车
	public static final int VEHICLE_OPTION = 9;

	// 业务类型---配件销售
	public static final int BUSINESS_TYPE_PJXS = 14031001;
	// 业务类型---采购入库
	public static final int BUSINESS_TYPE_CGRK = 14031002;
	// 业务类型---调拨入库
	public static final int BUSINESS_TYPE_DBRK = 14031003;
	// 业务类型---例外入库
	public static final int BUSINESS_TYPE_LWRK = 14031004;
	// 业务类型---维修领料
	public static final int BUSINESS_TYPE_WXLL = 14031005;
	// 业务类型---调拨出库
	public static final int BUSINESS_TYPE_DBCK = 14031006;
	// 业务类型---例外出库
	public static final int BUSINESS_TYPE_LWCK = 14031007;

	// 配件种类---纯正配件
	public static final int PARTS_TYPE_CZPJ = 14011001;
	// 配件种类---德科配件
	public static final int PARTS_TYPE_DKPJ = 14011002;
	// 配件种类---其他配件
	public static final int PARTS_TYPE_QTPJ = 14011003;

	// 菜单分类 -PC 端菜单
	public static final int ENTRY_MENU_CATEGORY_PC = 10171001;
	// 菜单分类 -APP 端菜单
	public static final int ENTRY_MENU_CATEGORY_APP = 10171002;

	// 用户类型-Yonyou
	public static final int EMPLOYEE_TYPE_Yonyou = 10501001;
	// 用户类型-车工坊
	public static final int EMPLOYEE_TYPE_CGF = 10501002;

	// 岗位类型-技师
	public static final int POSITION_TYPE_JS = 11031001;

	// 省市区县-省份
	public static final int REGION_TYPE_PROVINCE = 9001;
	// 省市区县-城市
	public static final int REGION_TYPE_CITY = 9002;
	// 省市区县-区县
	public static final int REGION_TYPE_COUNTY = 9003;

	// 员工在职状态-在职
	public static final int EMPLOYEE_ISJOB = 10081001;
	// 员工在职状态-离职
	public static final int EMPLOYEE_NOJOB = 10081002;

	// 出库 单据状态-未出库
	public static final int OUT_ORDER_NOSTATUS = 13031001;
	// 出库 单据状态-已出库
	public static final int OUT_ORDER_ISSTATUS = 13031002;

	// 入库 单据状态-未入库
	public static final int IN_ORDER_NOSTATUS = 13041001;
	// 入库 单据状态-已入库
	public static final int IN_ORDER_ISSTATUS = 13041002;
	// 入库类型
	public static final int ENTRY_TYPE_SENDBACK = 13171003;
	// 费用类型
	public static final int INVOICE_CHARGE_TYPE_BUYCAR = 14211001;
	// 库存状态14081001库存大于零
	public static final int INVENTORY_GREATER = 14081001;

	// 库存状态14081002库存等于零
	public static final int INVENTORY_EQUAL = 14081002;

	// 库存状态14081003库存小于零
	public static final int INVENTORY_LESS = 14081003;

	// 库存状态(在途)
	public static final int DISPATCHED_STATUS_ONPASSAGE = 13041001;
	// 库存状态(在库)
	public static final int DISPATCHED_STATUS_INSTOCK = 13041002;
	// 库存状态(出库)
	public static final int DISPATCHED_STATUS_OUTSTOCK = 13041003;
	// 库存状态(移库)
	public static final int VEHICLE_STORAGE_TYPE_MOVE_STOCK = 13201009;

	// 出入库类型-销售出库
	public static final int SALES_OUT_TYPE = 13151005;

	// 出入库类型-维修领料出库
	public static final int REPAIR_ORDER_TYPE = 13151004;

	// 出入库类型-配件报损
	public static final int PART_LOSS_TYPE = 13151006;

	// 出入库类型-内部领用
	public static final int PART_INNER_TYPE = 13151008;

	// 出入库类型-调拨出库
	public static final int PART_ALLOCATE_OUT = 13151003;
	// 出入库类型-采购入库
	public static final int PART_BUY_IN = 13151001;
	// 出入库类型-配件报溢
	public static final int PART_PROFIT = 13151007;
	// 出入库类型-调拨入库
	public static final int PART_ALLOCATE_IN = 13151002;
	// 收费区分-索赔
	public static final int CHARGE_DIF_COMPENSATE = 12141001;

	// 收费区分-赠送
	public static final int CHARGE_DIF_GIVE = 12141002;
	// 日志分类：系统管理
	public static final int LOG_SYSTEM_MANAGEMENT = 10091001;
	// 日志分类：客户管理
	public static final int LOG_CUSTOMER_MANAGEMENT = 10091006;
	// 日志分类：零售管理
	public static final int LOG_RETAIL_MANAGEMENT = 10091007;
	// 日志分类：维修管理
	public static final int LOG_REPAIR_MANAGEMENT = 10091002;
	// 配件管理
	public static final int LOG_SYSTEM_PART = 10091005;

	// 客户投诉已结案
	public static final int COMPLAINT_DEAL_STATUS_04 = 15051004;
	// 客户投诉未处理
	public static final int COMPLAINT_DEAL_STATUS_01 = 15051001;
	// 客户投诉处理中
	public static final int COMPLAINT_DEAL_STATUS_02 = 15051002;
	// 客户投诉结案申请中
	public static final int COMPLAINT_DEAL_STATUS_03 = 15051003;
	// 客户投诉来源
	public static final int COMPLAINT_ORIGIN_01 = 15001001;
	// 严重性(投诉性质) 严重
	public static final int COMPLAINT_SERIOUS_01 = 15031001;
	// 来访方式(邀约)
	public static final int COMPLAINT_VISIT_TYPE_03 = 15181003;
	// 来访方式(到店)
	public static final int COMPLAINT_VISIT_TYPE_02 = 15181002;
	// 是否手动操作结案ID
	public static final int COMPLAINT_OPE_TYPE_ID = 10391001;
	// 来源方式 手工
	public static final int SOURCE_TYPE_MANUAL = 13051002;
	// 来源方式 发运单
	public static final int SOURCE_TYPE_ORDER = 13051001;
	public static final int CUS_SOURCE_03 = 15121003;
	/*
	 * zhongsw：2016-08-09
	 */
	// 配件价格调整：销售价＜成本价
	public static final int SALES_COST = 10131001;
	// 配件价格调整：销售价＜建议销售价
	public static final int SALES_ADVICE = 10131001;
	// 配件价格调整：销售价＞销售限价
	public static final int SALES_LIMIT = 10131001;

	// 13111001销售价
	public static final int SALES_PRICE = 13111001;
	// 13111002建议销售价
	public static final int ADVICE_SALE_PRICE = 13111002;
	// 13111003索赔价
	public static final int CLAIM_PRICE = 13111003;
	// 13111004销售限价
	public static final int LIMIT_PRICE = 13111004;
	// 13111005成本单价
	public static final int COST_PRICE = 13111005;

	// 12151001工单状态--新建
	public static final int NEW_CREATE_STATUS = 12151001;

	// 12151003工单状态--派工
	public static final int ASSIGN_CREATE_STATUS = 12151002;
	// 12151003工单状态--已竣工
	public static final int COM_CREATE_STATUS = 12151003;
	// 12151008工单状态--取消竣工
	public static final int COM_CANCEL_STATUS = 12151008;

	// 12151004工单状态--已提交结算
	public static final int SUB_CREATE_STATUS = 12151004;

	// 12151005工单状态--已结算
	public static final int SET_CREATE_STATUS = 12151005;

	// 12151006工单状态--收款
	public static final int SET_CREATE_COLLECTION = 12151006;

	// 12151007工单状态--交车
	public static final int SET_CREATE_CAR = 12151007;

	/*
	 * // 12151006工单状态--收款 public static final int SET_CREATE_COLLECTION =
	 * 12151006; // 12151007工单状态--交车 public static final int SET_CREATE_CAR =
	 * 12151007;
	 */

	// 12131001进厂油量
	public static final int OIL_LEVEL_ZERO = 12131001;

	// 12131002进厂油量
	public static final int OIL_LEVEL_QUARTER = 12131002;

	// 12131003进厂油量
	public static final int OIL_LEVEL_HALF = 12131003;

	// 12131004进厂油量
	public static final int OIL_LEVEL_FOUR = 12131004;

	// 12131005进厂油量
	public static final int OIL_LEVEL_ALL = 12131005;

	// 客户类型
	public static final int CUSTOMER_CONTACT = 10181001; // 个人
	public static final int CUSTOMER_OWNER = 10181002; // 公司
	// 配件类别
	public static final int GROUPCODE = 1300;
	// 九大类
	public static final int MAINTYPE = 1301;
	public static final int TRACKING_RESULT_NOT = 15171003; // 未跟进
	public static final int TRACKING_RESULT_GOON = 15171004; // 继续跟进
	public static final int COMPLAINT_TRACKING_RESULT_6 = 15171005; // 战败申请
	// 维修
	public static final int MAINTAIN = 1211;
	// 配件
	public static final int ACCESSORIES = 1313;
	// 整车仓库
	public static final int VEHICLE_WAREHOUSE = 1319;
	// 配件仓库
	public static final int ACCESSORIES_WAREHOUSE = 1320;
	// 工时打折
	public static final int FAVORABLE_MODELS = 1400;
	// 员工属性- 销售顾问
	public static final int SALES_CONSULTANT = 10061001;
	// 员工属性- 销售经理
	public static final int SALES_AUDIT = 10061013;
	// 员工属性- 财务经理
	public static final int FINANCE_AUDIT = 10061014;

	// 意向级别 -H级
	public static final int H_LEVEL = 13101001;
	// 意向级别 -A级
	public static final int A_LEVEL = 13101002;
	// 意向级别 -B级
	public static final int B_LEVEL = 13101003;
	// 意向级别 -C级
	public static final int C_LEVEL = 13101004;
	// 意向级别 -D级
	public static final int D_LEVEL = 13101005;
	// 意向级别 -O级
	public static final int O_LEVEL = 13101006;
	// 意向级别 -N级
	public static final int N_LEVEL = 13101007;
	// 意向级别 -FO级
	public static final int FO_LEVEL = 13101008;
	// 意向级别 -F级
	public static final int F_LEVEL = 13101009;

	// 仓库类别 -配件
	public static final int PART_STORAGE = 13021002;

	// 仓库类别 -整车
	public static final int VEHICLE_STORAGE = 13021001;

	// 业务类型 -一般销售
	public static final int GENERAL_SALES_TYPE = 14031001;
	// 业务类型 -委托交车
	public static final int COMMISSIONED_TYPE = 14031002;
	// 业务类型 -销售退货
	public static final int SALES_RETURN_TYPE = 14031003;

	// 销售订单状态 -未提交
	public static final int NO_SUBMITTED = 14041001;
	// 销售订单状态 -经理审核中
	public static final int MANAGER_IN_REVIEW = 14041002;
	// 销售订单状态 -经理审核通过
	public static final int MANAGER_APPROVED = 14041003;
	// 销售订单状态 -经理审核驳回
	public static final int MANAGER_REJECTED = 14041004;
	// 销售订单状态 -财务审核中
	public static final int FINANCE_IN_REVIEW = 14041005;
	// 销售订单状态 -财务审核通过
	public static final int FINANCE_APPROVED = 14041006;
	// 销售订单状态 -财务审核驳回
	public static final int FINANCE_REJECTED = 14041007;
	// 销售订单状态 -交车确认中
	public static final int CAR_IN_CONFIRMATION = 14041008;
	// 销售订单状态 -已交车确认
	public static final int CAR_CONFIRMED = 14041009;
	// 销售订单状态 -已完成
	public static final int ORDER_COMPLETED = 14041010;
	// 销售订单状态 -已取消
	public static final int ORDER_CANCEL = 14041011;
	// 销售订单状态 -已退回
	public static final int ORDER_BACK = 14041012;
	// 销售订单状态 -等待退回入库
	public static final int ORDER_BACK_INWAREHOUSE = 14041013;
	// 整车基本参数
	public static final int VEHICLE_BASIC_CODE = 1020;
	// 整车基本参数
	public static final int BASICOM_TAXRATE = 1019;
	// PDI检查类型（车辆入库）
	public static final int STOCK_IN_PDI = 14121008;
	// PDI检查类型（库存检查）
	public static final int STOCK_KC_PDI = 14121010;

	// 自动结清
	public static final int AUTOMATICALLY_LIQUIDATE = 12091001;

	// 出库类型(销售出库)
	public static final int SALES_DELIVERY_TYPE = 13181001;

	// 出库类型(调拨出库)
	public static final int ALLOT_DELIVERY_TYPE = 13181002;

	// 出库类型(采购退回出库)
	public static final int BAKE_DELIVERY_TYPE = 13181003;

	// 不自动结清
	public static final int DO_NOT_AUTOMATICALLY_LIQUIDATE = 12091002;

	// PDI检查类型(出库检查)
	public static final int STOCK_OUT_PDI = 14121009;
	// 出库 出入库状态-未出库
	public static final int SD_NO_NOSTATUS = 14151001;
	// 出库 出入库状态-已出库
	public static final int SD_NO_ISSTATUS = 14151002;

	/**
	 * 工单状态
	 */
	// 已结算
	public static final int ORDER_SETTLED = 12151005;

	/**
	 * 业务类型
	 */
	// 维修
	public static final int RO_TYPE_REPAIR = 12161001;

	// 销售
	public static final int RO_TYPE_SALES = 12161002;

	// 调拨
	public static final int RO_TYPE_TRANSFERS = 12161003;

	// 是否结清(已结清) (作废)
	public static final int REPAIR_IS_SETTLE = 12231001;
	// 未结清 （作废）
	public static final int REPAIR_NO_SETTLE = 12231002;

	// 往来客户
	public static final int CUSTOMER_TYPE = 10181001;

	// 车主
	public static final int VEHICLE_TYPE = 10181002;

	// 销售订单审核结果-审核通过
	public static final int AUDIT_PASS = 14161001;
	// 销售订单审核结果-审核驳回
	public static final int AUDIT_BACK = 14161002;

	// 销售订单审核类型-经理审核
	public static final int AUDIT_MANAGE = 14171002;
	// 销售订单审核类型-财务审核
	public static final int AUDIT_FINANCE = 14171001;

	// 未收账款 （>0 , =0）
	public static final int GREATER_ZERO = 12241001;
	public static final int EQUAL_ZERO = 12241002;
	// 统计方式(按部门)
	public static final int STATISTICAL_MODE_01 = 10411001;
	// 统计方式(按班组)
	public static final int STATISTICAL_MODE_02 = 10411002;
	// 统计方式(按工位)
	public static final int STATISTICAL_MODE_03 = 10411003;
	// 配车状态-已配车
	public static final int ALREADY_DISPATCHED_STATUS = 13051002;
	// 配车状态-未配车
	public static final int NOT_DISPATCHED_STATUS = 13051001;
	// 配车状态-已交车
	public static final int ALREADY_SO_STATUS_08 = 13051004;
	// 配车状态-已交车确认
	public static final int FORWARDED_TO_CONFIRM = 13051003;
	// 交车确认中
	public static final int SO_STATUS_08 = 14041008;

	/**
	 * 预约单状态
	 */
	// 未进厂
	public static final int BOOKING_ORDER_NOT = 12261001;
	// 提前进厂
	public static final int BOOKING_ORDER_ADVANCE = 12261002;
	// 按时进厂
	public static final int BOOKING_ORDER_TIME = 12261003;
	// 延迟进厂
	public static final int BOOKING_ORDER_DELAY = 12261004;
	// 取消进厂
	public static final int BOOKING_ORDER_CANCEL = 12261005;

	/**
	 * 文件单据类型--1042
	 */

	public static final int FILE_TYPE_USER_INFO = 10421001; // 测试类型
	public static final int FILE_TYPE_USER_INFO_ADDRESS = 10421002; // 测试类型
	public static final int FILE_TYPE_CUSTOMER_INFO_COMPLAINT = 10421003; // 客户投诉
	public static final int FILE_TYPE_CAR_INFO_VEHICLEIN = 10421004; // 车辆入库
	public static final int FILE_TYPE_CAR_INFO_INSPECTION = 10421005; // 车辆验收

	/**
	 * 派工状态
	 */
	// 已派工
	public static final int ASSIGN_ORDER_ALL = 12271001;
	// 部分派工
	public static final int ASSIGN_ORDER_SOME = 12271002;
	// 未派工
	public static final int ASSIGN_ORDER_NO = 12271003;
	// 竣工
	public static final int ASSIGN_ORDER_COMPLETE = 12271004;
	// 待信
	public static final int ASSIGN_ORDER_INFO = 12271005;
	// 待料
	public static final int ASSIGN_ORDER_PART = 12271006;

	/**
	 * 质损状态
	 */
	// 正常
	public static final int TRAFFIC_MAR_STATUS_YES = 10401001;
	// 质损
	public static final int TRAFFIC_MAR_STATUS_NO = 10401002;
	/**
	 * 验收结果
	 */
	// 验收成功
	public static final int INSPECTION_RESULT_SUCCESS = 13351002;
	// 未验收
	public static final int INSPECTION_RESULT = 10391001;
	/**
	 * 入库类型???
	 */
	// 采购入库
	public static final int ENTRY_TYPE_CGRK = 13171001;
	// 调拨入库
	public static final int ENTRY_TYPE_DBRK = 13171002;
	// 销售退回
	public static final int ENTRY_TYPE_XSTH = 13171003;
	/*
	 * 维修类型
	 */
	// 维修
	public static final int REPAIR_TYPE_REPAIR = 12121001;
	// 返工
	public static final int REPAIR_TYPE_REWORK = 12121002;
	// 索赔
	public static final int REPAIR_TYPE_CLAIM = 12121003;

	// 质损状态
	// 全部签收
	public static final int MAR_STATUS_YES = 13061001; // 质损

	public static final int MAR_STATUS_NOT = 13061002; // 正常
	/**
	 * 销售订单状态
	 */
	public static final String DICT_SO_STATUS = "1301"; // 销售订单状态
	public static final String DICT_SO_STATUS_NOT_AUDIT = "13011010"; // 未提交审核
	public static final String DICT_SO_STATUS_MANAGER_AUDITING = "13011015"; // 经理审核中
	public static final String DICT_SO_STATUS_ACCOUNTANT_AUDITING = "13011020"; // 财务审核中
	public static final String DICT_SO_STATUS_DELIVERY_CONFIRMING = "13011025"; // 交车确认中
	public static final String DICT_SO_STATUS_HAVE_CONFIRMED = "13011030"; // 已交车确认
	public static final String DICT_SO_STATUS_HAVE_OUT_STOCK = "13011035"; // 已完成
	public static final String DICT_SO_STATUS_MANAGER_REJECT = "13011040"; // 经理驳回
	public static final String DICT_SO_STATUS_ACCOUNTANT_REJECT = "13011045"; // 财务驳回
	public static final String DICT_SO_STATUS_SYSTEM_REJECT = "13011050"; // 系统驳回
	public static final String DICT_SO_STATUS_HAVE_CANCEL = "13011055"; // 已取消
	public static final String DICT_SO_STATUS_HAVE_UNTREAD = "13011060"; // 已退回
	public static final String DICT_SO_STATUS_WAIT_UNTREAT_IN_STOCK = "13011065"; // 等待退回入库
	public static final String DICT_SO_STATUS_UNTREAT_HAVE_IN_STOCK = "13011070"; // 退回已入库
	public static final String DICT_SO_STATUS_CLOSED = "13011075"; // 已关单
	/**
	 * 来访方式
	 */
	public static final String DICT_VISIT_TYPE = "1309"; // 来访方式
	public static final String DICT_VISIT_TYPE_BY_CALL = "13091001"; // 来电
	public static final String DICT_VISIT_TYPE_IN_BODY = "13091002"; // 来店
	// public static final String DICT_VISIT_TYPE_OTHER = "13091003"; //其它
	public static final String DICT_VISIT_TYPE_SPECIAL_OUT = "13091004"; // 特定外拓
	// add by Bill Tang 2013-03-08 start
	public static final String DICT_VISIT_TYPE_STRANGE_VISIT = "13091003"; // 陌生拜访
	public static final String DICT_VISIT_TYPE_OTHER = "13091005"; // 其它
	public static final String DICT_VISIT_TYPE_INVITE = "13091006"; // 邀约

	/**
	 * 销售订单类型
	 */
	public static final String DICT_SO_TYPE = "1300"; // 销售订单类型
	public static final String DICT_SO_TYPE_GENERAL = "13001001"; // 一般销售订单
	public static final String DICT_SO_TYPE_ALLOCATION = "13001002"; // 车辆调拨
	public static final String DICT_SO_TYPE_ENTRUST_DELIVERY = "13001003"; // 受托交车
	public static final String DICT_SO_TYPE_SERVICE = "13001004"; // 服务销售
	public static final String DICT_SO_TYPE_RERURN = "13001005"; // 销售退回
	/**
	 * 客户状态
	 */
	public static final String DICT_CUSTOMER_STATUS = "1321"; // 客户状态
	public static final String DICT_CUSTOMER_STATUS_LATENCY = "13211001"; // 潜在客户
	public static final String DICT_CUSTOMER_STATUS_TENURE = "13211002"; // 基盘客户
	public static final String DICT_CUSTOMER_STATUS_BATH = "13211003"; // 基盘&潜在
	/**
	 * 创建方式
	 */
	public static final String DICT_CREATE_TYPE = "1329"; // 创建方式
	public static final String DICT_CREATE_TYPE_SYSTEM = "13291001"; // 系统创建
	public static final String DICT_CREATE_TYPE_HANDWORK = "13291002"; // 手工创建
	/**
	 * 市场部审批状态
	 */
	public static final String DICT_MARKET_DEPT_AUDIT_STATUS = "1362"; // 市场部审批状态
	public static final String DICT_MARKET_DEPT_AUDIT_STATUS_1001 = "13621001"; // 经销商未上报
	public static final String DICT_MARKET_DEPT_AUDIT_STATUS_1002 = "13621002"; // 经销商上报
	public static final String DICT_MARKET_DEPT_AUDIT_STATUS_1003 = "13621003"; // 区域经理通过
	public static final String DICT_MARKET_DEPT_AUDIT_STATUS_1004 = "13621004"; // 区域经理驳回
	public static final String DICT_MARKET_DEPT_AUDIT_STATUS_1005 = "13621005"; // 促销专员通过
	public static final String DICT_MARKET_DEPT_AUDIT_STATUS_1006 = "13621006"; // 促销专员驳回
	public static final String DICT_MARKET_DEPT_AUDIT_STATUS_1007 = "13621007"; // 大区经理通过
	public static final String DICT_MARKET_DEPT_AUDIT_STATUS_1008 = "13621008"; // 大区经理驳回
	public static final String DICT_MARKET_DEPT_AUDIT_STATUS_1009 = "13621009"; // 市场部通过
	public static final String DICT_MARKET_DEPT_AUDIT_STATUS_1010 = "13621010"; // 市场部驳回

	/**
	 * 数据存储分区标识
	 */
	public static String D_KEY = "0";

	// 执行状态
	public static final int DICT_PERFORMANCE_STATUS_NOT = 90051001;
	public static final int DICT_PERFORMANCE_STATUS_HAVE = 90051002;
	/**
	 * 意向级别
	 */
	public static final String DICT_INTENT_LEVEL = "1310"; // 意向级别
	public static final String DICT_INTENT_LEVEL_H = "13101001"; // H级
	public static final String DICT_INTENT_LEVEL_A = "13101002"; // A级
	public static final String DICT_INTENT_LEVEL_B = "13101003"; // B级
	public static final String DICT_INTENT_LEVEL_C = "13101004"; // C级
	public static final String DICT_INTENT_LEVEL_N = "13101005"; // N级
	public static final String DICT_INTENT_LEVEL_FO = "13101006"; // F0级
	public static final String DICT_INTENT_LEVEL_F = "13101007"; // F级
	public static final String DICT_INTENT_LEVEL_O = "13101008"; // O级
	public static final String DICT_INTENT_LEVEL_D = "13101009"; // D级
	/**
	 * 客户休眠审批状态
	 */
	public static final String DICT_AUDIT_STATUS = "3335"; // 客户休眠审批状态
	public static final String DICT_AUDIT_STATUS_ING = "33351001"; // 经理审核中
	public static final String DICT_AUDIT_STATUS_YES = "33351002"; // 经理审核通过
	public static final String DICT_AUDIT_STATUS_NO = "33351003"; // 经理审核不通过

	/**
	 * 客户来源
	 */
	public static final int DICT_CUS_SOURCE = 1311; // 客户来源
	public static final int DICT_CUS_SOURCE_EXHI_HALL = 13111001; // 来店/展厅顾客
	public static final int DICT_CUS_SOURCE_MARKET_ACTIVITY = 13111003; // 活动/展厅活动
	public static final int DICT_CUS_SOURCE_TENURE_CUSTOMER = 13111004; // 保客增购
	public static final int DICT_CUS_SOURCE_FRIEND = 13111005; // 保客推荐
	public static final int DICT_CUS_SOURCE_OTHER = 13111007; // 其他
	public static final int DICT_CUS_SOURCE_PHONE_VISITER = 13111008; // 陌生拜访/电话销售
	public static final int DICT_CUS_SOURCE_INTERNET = 13111012; // 网络/电子商务
	public static final int DICT_CUS_SOURCE_BY_WAY = 13111013; // 路过
	public static final int DICT_CUS_SOURCE_ORG_CODE = 13111014; // 代理商/代销网点
	public static final int DICT_CUS_SOURCE_PHONE_CUSTOMER = 13111015; // 来电顾客
	public static final int DICT_CUS_SOURCE_BY_DCC = 13111016; // DCC转入
	public static final int DICT_CUS_SOURCE_BY_SHOW = 13111017; // 活动-车展
	public static final int DICT_CUS_SOURCE_BY_EXPERIENCE_DAY = 13111018; // 活动-外场试驾活动
	public static final int DICT_CUS_SOURCE_BY_CARAVAN = 13111019; // 活动-巡展/外展
	// public static final int DICT_CUS_SOURCE_BY_WEB = 13111020; // 保客置换
	public static final int DICT_CUS_SOURCE_BY_WEB = 13111021; // 官网客户
	public static final int DICT_CUS_SOURCE_CALL_CENTER = 13111101; // CAPSA
																	// CALL
																	// CENTER
	public static final int DICT_CUS_SOURCE_DS_WEBSITE = 13111102; // DS
																	// WEBSITE

	/**
	 * 回访结果
	 */
	public static final int DICT_COMPLAINT_RESULT = 1328;
	public static final int DICT_COMPLAINT_RESULT_CREATE = 13281001; // 新建客户信息
	public static final int DICT_COMPLAINT_RESULT_BARGAIN = 13281002; // 签订合同
	public static final int DICT_COMPLAINT_RESULT_NO_INTENT = 13281003; // 无意向
	public static final int DICT_COMPLAINT_RESULT_OTHER_BRAND = 13281004; // 已购其它品牌车
	public static final int DICT_COMPLAINT_RESULT_OTHER_SHOP = 13281005; // 已订其它店本品牌车
	public static final int DICT_COMPLAINT_RESULT_INFO_SCARCITY = 13281006; // 信息不准
	public static final int DICT_COMPLAINT_RESULT_CREATEED = 13281007; // 已来过已建卡

	/**
	 * 投放媒体类型
	 */
	public static final int DICT_MEDIA_TYPE = 1298; // 广告投放媒体类型
	public static final int DICT_MEDIA_TYPE_NEWSPAPER = 12981001; // 报纸
	public static final int DICT_MEDIA_TYPE_TELEVISION = 12981002; // 电视
	public static final int DICT_MEDIA_TYPE_BROADCASTING = 12981003; // 户外
	public static final int DICT_MEDIA_TYPE_NET = 12981004; // 广播
	public static final int DICT_MEDIA_TYPE_MAGAZINE = 12981005; // BTL
																	// Event
	public static final int DICT_MEDIA_TYPE_GUIDEPOST = 12981006; // Hotline
	public static final int DICT_MEDIA_TYPE_OUTDOORS = 12981007; // 网络
	public static final int DICT_MEDIA_TYPE_OTHER = 12981008; // 其它
	public static final int DICT_MEDIA_TYPE_BODYWORK = 12981009; // Inhouse

	/**
	 * 性别
	 */
	public static final int DICT_GENDER_TYPE = 1006; // 性别类别
	public static final int DICT_GENDER_MAN = 10061001; // 男
	public static final int DICT_GENDER_WOMAN = 10061002; // 女

	/**
	 * 修后跟踪任务状态
	 */
	public static final String DICT_TRACING_STATUS_TYPE = "1237"; // 修后跟踪任务状态类别
	public static final String DICT_TRACING_STATUS_NO = "12371001"; // 未跟踪
	public static final String DICT_TRACING_STATUS_SUCCEED_END = "12371003"; // 成功结束跟踪
	public static final String DICT_TRACING_STATUS_CONTINUE = "12371002"; // 继续跟踪
	public static final String DICT_TRACING_STATUS_FAIL_END = "12371004"; // 失败结束跟踪

	/**
	 * 库存状态
	 */
	public static final int DICT_STORAGE_STATUS = 1304; // 整车库存状态
	public static final int DICT_STORAGE_STATUS_MOVE_OUT = 13041001; // 出库
	public static final int DICT_STORAGE_STATUS_IN_STORAGE = 13041002; // 在库
	public static final int DICT_STORAGE_STATUS_ON_WAY = 13041003; // 在途
	public static final int DICT_STORAGE_STATUS_LEND_TO = 13041004; // 借出

	/**
	 * 配车状态
	 */
	public static final int DICT_DISPATCHED_STATUS = 1305; // 整车库存配车状态
	public static final int DICT_DISPATCHED_STATUS_NOT_DISPATCHED = 13051001; // 未配车
	public static final int DICT_DISPATCHED_STATUS_HAVE_DISPATCHED = 13051002; // 已配车
	public static final int DICT_DISPATCHED_STATUS_DELIVERY_CONFIRM = 13051003; // 交车确认
	public static final int DICT_DISPATCHED_STATUS_HAVE_DELIVER = 13051004; // 已交车

	/**
	 * 入库业务类型
	 */
	public static final int DICT_STOCK_IN_TYPE = 1307; // 车辆入库业务类型
	public static final int DICT_STOCK_IN_TYPE_DEALER_BUY = 13071001; // 经销商自购
	public static final int DICT_STOCK_IN_TYPE_BUY_NEW_VEHICLE = 13071002; // 新车采购入库
	public static final int DICT_STOCK_IN_TYPE_ALLOCATION = 13071003; // 调拨入库
	public static final int DICT_STOCK_IN_TYPE_ENTRUST_DELIVERY = 13071004; // 受托交车入库
	public static final int DICT_STOCK_IN_TYPE_SALE_UNTREAD = 13071005; // 销售退回入库
	public static final int DICT_STOCK_IN_TYPE_SEC_SALE_UNTREAD = 13071006; // 二网销售退回入库

	/**
	 * 车辆出库业务类型
	 */
	public static final int DICT_STOCK_OUT_TYPE = 1324; // 车辆出库业务类型
	public static final int DICT_STOCK_OUT_TYPE_SALE = 13241001; // 销售出库
	public static final int DICT_STOCK_OUT_TYPE_ALLOCATION = 13241002; // 调拨出库
	public static final int DICT_STOCK_OUT_TYPE_DELIVERY = 13241003; // 受托交车出库
	public static final int DICT_STOCK_OUT_TYPE_UNTREAD = 13241004; // 采购退回出库
	public static final int DICT_STOCK_OUT_TYPE_OTHER = 13241005; // 其它类型出库
	public static final int DICT_STOCK_OUT_TYPE_SEC_SALE = 13241006; // 二网销售类型出库

	/**
	 * 车辆主数据业务类型
	 */
	public static final int DICT_PRODUCT_STATUS = 1308; // 车辆主数据业务类型
	public static final int DICT_PRODUCT_STATUS_NORMAL = 13081001; // 正常
	public static final int DICT_PRODUCT_STATUS_DELIST = 13081002; // 退市

	/**
	 * 产品类别
	 */
	public static final String DICT_PRODUCT_TYPE = "1038";
	public static final String DICT_PRODUCT_TYPE_NOMAL = "10381001"; // 正常库存车辆
	public static final String DICT_PRODUCT_TYPE_PROMOTION = "10381002"; // 促销车
	public static final String DICT_PRODUCT_TYPE_SHOW = "10381003"; // 展车
	public static final String DICT_PRODUCT_TYPE_REFIT = "10381004"; // 改装车
	/**
	 * 开票费用类型
	 */
	public static final String DICT_INVOICE_FEE_TYPE = "1318"; // 开票费用类型
	public static final String DICT_INVOICE_FEE_VEHICLE = "13181001"; // 购车费用
	public static final String DICT_INVOICE_FEE_COMMISSION = "13181002"; // 代办服务费
	public static final String DICT_INVOICE_FEE_GARNITURE = "13181003"; // 精品装潢费
	public static final String DICT_INVOICE_FEE_INSURANCE = "13181004"; // 保险费用
	public static final String DICT_INVOICE_FEE_TAXE = "13181005"; // 购税费用
	public static final String DICT_INVOICE_FEE_PLATE = "13181006"; // 牌照费用
	public static final String DICT_INVOICE_FEE_LOAN = "13181007"; // 信贷费用

	/**
	 * 整车库存操作类型
	 */
	public static final String DICT_VEHICLE_STORAGE_TYPE = "1320"; // 减免费用类型
	public static final String DICT_VEHICLE_STORAGE_TYPE_BUY = "13201001"; // 新车采购入库
	public static final String DICT_VEHICLE_STORAGE_TYPE_IN = "13201002"; // 受托交车入库
	public static final String DICT_VEHICLE_STORAGE_TYPE_ALLOCATE_IN = "13201003"; // 调拨入库
	public static final String DICT_VEHICLE_STORAGE_TYPE_RETURN_IN = "13201004"; // 销售退回入库
	public static final String DICT_VEHICLE_STORAGE_TYPE_SALE_OUT = "13201005"; // 销售出库
	public static final String DICT_VEHICLE_STORAGE_TYPE_ALLOCATE_OUT = "13201006"; // 调拨出库
	public static final String DICT_VEHICLE_STORAGE_TYPE_RETURN_OUT = "13201007"; // 采购退回出库
	public static final String DICT_VEHICLE_STORAGE_TYPE_OTHER_OUT = "13201008"; // 其它类型出库
	public static final String DICT_VEHICLE_STORAGE_TYPE_MOVE_STOCK = "13201009"; // 移库
	public static final String DICT_VEHICLE_STORAGE_TYPE_MOVE_POSITION = "13201010"; // 移位
	public static final String DICT_VEHICLE_STORAGE_TYPE_LEND = "13201011"; // 借出
	public static final String DICT_VEHICLE_STORAGE_TYPE_RETURN = "13201012"; // 归还
	public static final String DICT_VEHICLE_STORAGE_TYPE_CHANGE_PRICE = "13201013"; // 调价
	public static final String DICT_VEHICLE_STORAGE_TYPE_CHANGE_PROPERTY = "13201014"; // 修改库存属性
	public static final String DICT_VEHICLE_STORAGE_TYPE_OUT = "13201015"; // 受托交车出库
	public static final String DICT_VEHICLE_STORAGE_TYPE_BUY_SELF = "13201016"; // 自购入库

	/**
	 * 监管车类型
	 */
	public static final String DICT_SUPERVISE_VEHICLE_TYPE = "1062"; // 监管车类型
	public static final String DICT_SUPERVISE_VEHICLE_FINANCIAL = "10621001"; // 金融监管车
	public static final String DICT_SUPERVISE_VEHICLE_TRANSFER = "10621002"; // 中转监控车

	/**
	 * 付款单据的付款业务类型
	 */
	public static final String DICT_PAY_BUSINESS_TYPE = "1405";
	public static final String DICT_PAY_BUSINESS_TYPE_VEHICLE_STOCK_IN = "14051001"; // 整车采购入库
	public static final String DICT_PAY_BUSINESS_TYPE_VEHICLE_ALLOCATE_IN = "14051002"; // 整车调拨入库
	public static final String DICT_PAY_BUSINESS_TYPE_PART_STOCK_IN = "14051003"; // 配件采购入库
	public static final String DICT_PAY_BUSINESS_TYPE_PART_ALLOCATE_IN = "14051004"; // 配件调拨入库

	/**
	 * 整车采购订单状态
	 */
	public static final String DICT_Vehicle_Purchase_Order = "1502";
	public static final String DICT_Vehicle_Purchase_Order_Status_INSERT = "15021009"; // 未提交
	public static final String DICT_Vehicle_Purchase_Order_Status_Financial_Audit = "15021001"; // 财务审核中
	public static final String DICT_Vehicle_Purchase_Order_Status_Audit_Manager = "15021002"; // 经理审核中
	public static final String DICT_Vehicle_Purchase_Order_Status_Audit_By = "15021003"; // 审核通过
	public static final String DICT_Vehicle_Purchase_Order_Status_Financial_Review_Dismissed = "15021004"; // 财务审核驳回
	public static final String DICT_Vehicle_Purchase_Order_Status_Audit_Manager_Dismissed = "15021005"; // 经理审核驳回
	public static final String DICT_Vehicle_Purchase_Order_Status_Part_Of_Arrival = "15021006"; // 部分到货
	public static final String DICT_Vehicle_Purchase_Order_Status_All_Arrival = "15021007"; // 全部到货
	public static final String DICT_Vehicle_Purchase_Order_Status_Completed = "15021008"; // 已完成
	public static final String DICT_Vehicle_Purchase_Order_Status_Audit_Boss = "15021010"; // 总经理审核中
	public static final String DICT_Vehicle_Purchase_Order_Status_Audit_Boss_Dismissed = "15021011"; // 总经理驳回
	public static final String DICT_Vehicle_Purchase_Order_Status_Audit_GROUP = "15021012"; // 集团审核中
	public static final String DICT_Vehicle_Purchase_Order_Status_Brand_Dismissed = "15021013"; // 品牌/运营经理驳回
	public static final String DICT_Vehicle_Purchase_Order_Status_BrandBoss_Dismissed = "15021014"; // 品牌总经理驳回
	public static final String DICT_Vehicle_Purchase_Order_Status_Center_Dismissed = "15021015"; // 运营中心驳回
	public static final String DICT_Vehicle_Purchase_Order_Status_Leader_Dismissed = "15021016"; // 主管高层驳回
	public static final String DICT_Vehicle_Purchase_Order_Status_GROUP_Dismissed = "15021017"; // 集团驳回

	/**
	 * 生成状态
	 */
	public static final String DICT_EXEC_STATUS_TYPE = "1698"; // 生成状态
	public static final String DICT_EXEC_STATUS_NOT_EXEC = "16980001"; // 未生成
	public static final String DICT_EXEC_STATUS_EXEC = "16980002"; // 已生成
	public static final String DICT_EXEC_STATUS_RESET_EXEC = "16980003"; // 重新生成

	/**
	 * 附加项目结算方式
	 */
	public static final String DICT_ADD_ITEM_BALANCE_MODE_TYPE = "1312"; // 附加项目结算方式
	public static final String DICT_ADD_ITEM_BALANCE_MODE_TYPE_SALES = "13121001"; // 销售
	public static final String DICT_ADD_ITEM_BALANCE_MODE_TYPE_CONTAINED = "13121002"; // 车价已包含
	public static final String DICT_ADD_ITEM_BALANCE_MODE_TYPE_FREE = "13121003"; // 免费赠送
	public static final String DICT_ADD_ITEM_BALANCE_MODE_TYPE_COMMISSION = "13121004"; // 代办

	/**
	 * 整车收款类型
	 */
	public static final String DICT_GATHERING_TYPE = "1316"; // 整车收款类型
	public static final String DICT_GATHERING_TYPE_CASH = "13161001"; // 定金
	public static final String DICT_GATHERING_TYPE_BEFOREHAND = "13161002"; // 预收款
	public static final String DICT_GATHERING_TYPE_SUBSTITUTE = "13161003"; // 代收款
	public static final String DICT_GATHERING_TYPE_CAR_BALANCE = "13161004"; // 购车余款
	public static final String DICT_GATHERING_TYPE_CAR_ALL = "13161005"; // 购车全款
	/**
	 * 二手车支付方式
	 */
	public static final String DICT_SC_PAY_TYPE = "6080"; // 支付方式
	public static final String DICT_SC_PAY_TYPE_CASH = "60801000"; // 现金
	public static final String DICT_SC_PAY_TYPE_CHECK = "60801005"; // 支票
	public static final String DICT_SC_PAY_TYPE_TRANSFER_ACCOUNTS = "60801010"; // 转账
	public static final String DICT_SC_PAY_TYPE_OTHERS = "60801015"; // 其他
	public static final String DICT_SC_PAY_TYPE_NEW_CAR_PAY = "60801020"; // 作为新车款垫付

	/**
	 * add by zhengzengliang
	 */
	public static final Integer TT_VS_MONTHLY_FORECAST_SUBMIT = 20791002; // 预测状态
																			// （已提交）
	public static final Integer IS_DEL_00 = 0; // 删除标示
												// ：逻辑未删除
	public static final Integer TT_VS_MONTHLY_FORECAST_SAVE = 20791001; // 预测状态(已保存)
	public final static Integer TT_VS_MONTHLY_FORECAST_AUDIT = 20791003; // 预测状态（已审核）
	public final static Integer TT_VS_MONTHLY_FORECAST_REPORT = 20791004; // 预测状态(经销商已反馈上报)
	public static final Integer ORG_TYPE_DEALER = 10191002; // 组织类型(经销商)
	public static final Integer ZHONGJIN_ORDER_CONFIRM = 21112102; // 生产期货序列号状态(中进定金确认)
	public static final int DEALER_TYPE_DVS = 10771001; // 经销商类型（经销商整车）
	public static Integer STATUS_ENABLE = 10011001; // 状态（有效）
	public final static Integer TASK_TYPE_02 = 1; // N+3预测任务

	public static final String DICT_WHOLESALE_STATUS = "2014"; // 批售状态
	public static final String DICT_WHOLESALE_STATUS_AUDITING = "20141001"; // 审核中
	public static final String DICT_WHOLESALE_STATUS_PASS = "20141002"; // 审核通过
	public static final String DICT_WHOLESALE_STATUS_REFUSE = "20141003"; // 审核否决
	public static final String DICT_WHOLESALE_STATUS_NOT = "20141004"; // 未提交

	/**
	 * 预计成交时段
	 */
	public static final String DICT_TIME_EXPECT_TIMES_RANGE = "5056"; // 预计成交时段
	public static final String DICT_TIME_EXPECT_TIMES_RANGE_ONE = "50561001"; // 0-7天
	public static final String DICT_TIME_EXPECT_TIMES_RANGE_TWO = "50561002"; // 7-15天
	public static final String DICT_TIME_EXPECT_TIMES_RANGE_THREE = "50561003"; // 15-30天
	public static final String DICT_TIME_EXPECT_TIMES_RANGE_FOUR = "50561004"; // 30-90天
	public static final String DICT_TIME_EXPECT_TIMES_RANGE_FIVE = "50561005"; // 90天以上

	/**
	 * 市场活动发起方式
	 */
	public static final String DICT_CAMPAIGN_PERFORM_TYPE = "1299"; // 市场活动发起方式
	public static final String DICT_CAMPAIGN_PERFORM_TYPE_INDEPENDANCE = "12991001"; // 独立组织活动
	public static final String DICT_CAMPAIGN_PERFORM_TYPE_UNION = "12991002"; // 与车厂联合活动
	public static final String DICT_CAMPAIGN_PERFORM_TYPE_OEM = "12991003"; // 车厂下发

	/**
	 * 外呼核实结果状态
	 */
	public static final String DICT_OUTBOUND_RETURN_STATUS = "7003";
	public static final String DICT_OUTBOUND_RETURN_STATUS_SUCCESS = "70031001"; // 成功
	public static final String DICT_OUTBOUND_RETURN_STATUS_FAILED = "70031002"; // 失败
	public static final String DICT_OUTBOUND_RETURN_STATUS_CHECKING = "70031003"; // 已上报待核实

	/**
	 * 调拨审核状态
	 */
	public final static Integer TRANSFER_CHECK_STATUS = 2020;
	public final static Integer TRANSFER_CHECK_STATUS_01 = 20201001; // 待审核
	public final static Integer TRANSFER_CHECK_STATUS_02 = 20201002; // 审核通过
	public final static Integer TRANSFER_CHECK_STATUS_03 = 20201003; // 审核驳回
	public final static Integer TRANSFER_CHECK_STATUS_04 = 20201004; // 已调拨
	public final static Integer TRANSFER_CHECK_STATUS_05 = 20201005; // 审核中

	/**
	 * 物料组级别
	 */
	public final static Integer TM_VHCL_MATERIAL_GROUP_BRAND = 1; // 品牌
	public final static Integer TM_VHCL_MATERIAL_GROUP_CAR = 2; // 车系
	public final static Integer TM_VHCL_MATERIAL_GROUP_MODEL = 3; // 车型
	public final static Integer TM_VHCL_MATERIAL_GROUP_TYPE = 4; // 款式

	/**
	 * 详细车籍类型状态
	 */
	public static final Integer VEHICLE_CHANGE_TYPE = 2021; // 详细车籍类型状态
	public static final Integer VEHICLE_CHANGE_TYPE_01 = 20211001; // 装船登记
	public static final Integer VEHICLE_CHANGE_TYPE_02 = 20211002; // ZGOR-车辆到港
	public static final Integer VEHICLE_CHANGE_TYPE_03 = 20211003; // 点检
	public static final Integer VEHICLE_CHANGE_TYPE_04 = 20211004; // 通关完成
	public static final Integer VEHICLE_CHANGE_TYPE_05 = 20211005; // ZVP8-入VPC仓库
	public static final Integer VEHICLE_CHANGE_TYPE_06 = 20211006; // ZPDP-PDI检查通过
	public static final Integer VEHICLE_CHANGE_TYPE_07 = 20211007; // ZPDU-发车出库
	public static final Integer VEHICLE_CHANGE_TYPE_08 = 20211008; // 移库出库
	public static final Integer VEHICLE_CHANGE_TYPE_09 = 20211009; // 经销商库存
	public static final Integer VEHICLE_CHANGE_TYPE_10 = 20211010; // 已实销
	public static final Integer VEHICLE_CHANGE_TYPE_11 = 20211011; // 退车出库
	public static final Integer VEHICLE_CHANGE_TYPE_12 = 20211012; // ZTPR-退车入库
	public static final Integer VEHICLE_CHANGE_TYPE_13 = 20211013; // 调拨出库
	public static final Integer VEHICLE_CHANGE_TYPE_14 = 20211014; // 调拨入库
	public static final Integer VEHICLE_CHANGE_TYPE_15 = 20211015; // ZFSC-工厂订单冻结
	public static final Integer VEHICLE_CHANGE_TYPE_16 = 20211016; // ZVDU-工厂订单车辆数据更新
	public static final Integer VEHICLE_CHANGE_TYPE_17 = 20211017; // ZSHP-海运在途
	public static final Integer VEHICLE_CHANGE_TYPE_18 = 20211018; // ZCBC-车辆清关
	public static final Integer VEHICLE_CHANGE_TYPE_19 = 20211019; // ZBIL-一次开票
	public static final Integer VEHICLE_CHANGE_TYPE_20 = 20211020; // ZDRL-中进车款确认
	public static final Integer VEHICLE_CHANGE_TYPE_21 = 20211021; // ZDRQ-换车入库
	public static final Integer VEHICLE_CHANGE_TYPE_22 = 20211022; // ZPDF-PDI检查失败
	public static final Integer VEHICLE_CHANGE_TYPE_23 = 20211023; // ZFSN-工厂订单冻结
	public static final Integer VEHICLE_CHANGE_TYPE_24 = 20211024; // ZRL1-资源已分配
	public static final Integer VEHICLE_CHANGE_TYPE_25 = 20211025; // ZDRR-经销商订单确认
	public static final Integer VEHICLE_CHANGE_TYPE_26 = 20211026; // ZDRV-中进车款取消
	public static final Integer VEHICLE_CHANGE_TYPE_27 = 20211027; // 经销商订单撤单
	/**
	 * 会员状态
	 */
	public static final String DICT_MEMBER_STATUS = "5004"; // 会员状态
	public static final String DICT_MEMBER_STATUS_NORMAL = "50041001"; // 正常
	public static final String DICT_MEMBER_STATUS_QUIT = "50041002"; // 退会

	/**
	 * 卡状态
	 */
	public static final String DICT_CARD_STATUS = "5005"; // 卡状态
	public static final String DICT_CARD_STATUS_NOT_ACTIVE = "50051001"; // 未激活
	public static final String DICT_CARD_STATUS_NORMAL = "50051002"; // 正常
	public static final String DICT_CARD_STATUS_FREEZE = "50051003"; // 冻结
	public static final String DICT_CARD_STATUS_NON_USE = "50051004"; // 停用
	public static final String DICT_CARD_STATUS_INVALID = "50051005"; // 作废

	/**
	 * 付款方式
	 */
	public static final int DICT_PAY_MODE = 1025; // 付款方式
	public static final int DICT_PAY_MODE_LAMP_SUM = 10251001; // 一次性付清
	public static final int DICT_PAY_MODE_INSTALLMENT = 10251003; // 分期付款

	/**
	 * 排放标准
	 */
	public static final int DISCHARGE_STANDARD = 3010; // 排放标准

	/**
	 * 提醒状态
	 */
	public static final int DICT_VEHICLE_REMINDER_STATUS = 1382; // 提醒状态
	public static final int DICT_VEHICLE_REMINDER_STATUS_AGAIN = 13821001; // 继续提醒
	public static final int DICT_VEHICLE_REMINDER_STATUS_FAIL = 13821002; // 失败结束提醒
	public static final int DICT_VEHICLE_REMINDER_STATUS_SUCCESS = 13821003; // 成功结束提醒
	/**
	 * 车辆用途
	 */
	public static final String DICT_VEHICLE_PURPOSE_TYPE = "1193"; // 车辆用途类别
	public static final String DICT_VEHICLE_PURPOSE_TYPE_BUSINESS = "11931001"; // 商务车
	public static final String DICT_VEHICLE_PURPOSE_TYPE_FAMILY = "11931002"; // 私家车
	public static final String DICT_VEHICLE_PURPOSE_TYPE_POLICE = "11931003"; // 警车
	public static final String DICT_VEHICLE_PURPOSE_TYPE_TAXI = "11931004"; // 出租车
	public static final String DICT_VEHICLE_PURPOSE_TYPE_COMPANY = "11931005"; // 租赁公司
	/**
	 * 提醒方式
	 */
	public static final int DICT_REMIND_WAY_TYPE = 1128; // 提醒方式类别
	public static final int DICT_REMIND_WAY_TEL = 11281001; // 电话
	public static final int DICT_REMIND_WAY_MESSAGE = 11281003; // 短信
	public static final int DICT_REMIND_WAY_MISSIVE = 11281002; // 书信

	/**
	 * 提醒类型
	 */
	public static final String DICT_REMINDER_TYPE = "1384"; // 提醒类型
	public static final String DICT_REMINDER_TYPE_TERMLY_MAINTAIN = "13841001"; // 定期保养提醒
	public static final String DICT_REMINDER_TYPE_INSURANCE_ATTERM = "13841002"; // 保险到期提醒
	public static final String DICT_REMINDER_TYPE_VERYCAR_ATTERM = "13841003"; // 验车到期提醒
	public static final String DICT_REMINDER_TYPE_NEW_CAR = "13841004"; // 新车提醒
	public static final String DICT_REMINDER_TYPE_OWNER_BIRTHDAY = "13841005"; // 车主生日函
	public static final String DICT_REMINDER_TYPE_CUSTOMER_LOSE = "13841006"; // 客户流失报警
	public static final String DICT_REMINDER_TYPE_CUSTOMER_BIRTHDAY = "13841007"; // 客户生日提醒
	public static final String DICT_REMINDER_TYPE_YEAR_CHECK = "13841008"; // 年检到期提醒
	public static final String DICT_REMINDER_TYPE_GUAR_ATTERM = "13841009"; // 保修到期提醒

	/**
	 * 客户收款类别
	 */
	public static final int DICT_CUS_RECEIVE_SORT = 1378; // 客户收款类别
	public static final int DICT_CUS_RECEIVE_SORT_CUSTOMER_PAY = 13781001; // 客户付费
	public static final int DICT_CUS_RECEIVE_SORT_INSURANCE_PAY = 13781002; // 保险付费
	public static final int DICT_CUS_RECEIVE_SORT_OEM_INDEMNITY = 13781003; // OEM索赔
	public static final int DICT_CUS_RECEIVE_SORT_4S_PAY = 13781004; // 4S店付费
	public static final int DICT_CUS_RECEIVE_SORT_OTHER_INDEMNITY = 13781005; // 其他索赔

	/**
	 * 销售基价（价格类型）
	 */
	public static final int DICT_SALES_BASE_PRICE_TYPE = 1236; // 价格类型
	public static final int DICT_SALES_BASE_PRICE_TYPE_COST = 12361001; // 成本价
	public static final int DICT_SALES_BASE_PRICE_TYPE_SALE = 12361002; // 销售价
	public static final int DICT_SALES_BASE_PRICE_TYPE_INDEMNITY = 12361003; // 索赔价
	public static final int DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT = 12361004; // 网点价
	public static final int DICT_SALES_BASE_PRICE_TYPE_LATEST = 12361005; // 最新进货价
	public static final int DICT_SALES_BASE_PRICE_TYPE_TAX_COST = 12361006; // 含税成本价
	public static final int DICT_SALES_BASE_PRICE_TYPE_INSURANCE = 12361007; // 保险价
	public static final int DICT_SALES_BASE_PRICE_TYPE_INSURANCE_PRICE = 12361008; // 建议销售价
	public static final int DICT_SALES_BASE_PRICE_TYPE_LIMIT_PRICE = 12361009; // 销售限价

	// 技师等级 -1级
	public static final int ONE_LEVEL = 12321001;
	// 技师等级 -2级
	public static final int TWO_LEVEL = 12321002;
	// 技师等级 -3级
	public static final int THREE_LEVEL = 12321003;
	// 技师等级 -4级
	public static final int FOUR_LEVEL = 12321004;

	/**
	 * 货运单签收状态
	 */
	public static final int DELIVERY_STATUS_SIGNED = 50081003; // 全部签收
	public static final int DELIVERY_STATUS_NOTSIGN = 50081005; // 未签收

	/**
	 * 排放标准
	 */
	public static final String DICT_DISCHARGE_STANDARD = "3010";
	public static final String DICT_DISCHARGE_STANDARD_COUNTRY_III = "30101001"; // 国Ⅲ
	public static final String DICT_DISCHARGE_STANDARD_COUNTRY_IV = "30101002"; // 国Ⅳ
	public static final String DICT_DISCHARGE_STANDARD_COUNTRY_IIIOBD = "30101003"; // 国Ⅲ+OBD
	public static final String DICT_DISCHARGE_STANDARD_OU_III = "30101004"; // 欧Ⅲ
	public static final String DICT_DISCHARGE_STANDARD_OU_IV = "30101005"; // 欧Ⅳ
	public static final String DICT_DISCHARGE_STANDARD_OU_IVOBD = "30101006"; // 欧Ⅳ+OBD
	public static final String DICT_DISCHARGE_STANDARD_COUNTRY_III_OU_IV = "30101007"; // 国Ⅲ欧Ⅳ

	/**
	 * 购买方式
	 */
	public static final String DICT_WAYS_TO_BUY = "1067"; // 购买方式
	public static final String DICT_WAYS_TO_BUY_NEW_CAR = "10671001"; // 新车
	public static final String DICT_WAYS_TO_BUY_USED_CAR = "10671002"; // 二手车
	public static final String DICT_WAYS_TO_BUY_REPLACE_CAR = "10671003"; // 置换
	public static final String DICT_WAYS_TO_BUY_REBUY_CAR = "10671004"; // 重购
	public static final String DICT_WAYS_TO_BUY_FINANCE_CAR = "10671005"; // 金融

	/**
	 * 出入库类型
	 */
	public static final String DICT_IN_OUT_TYPE = "1215"; // 出入库类型类别
	public static final String DICT_IN_OUT_TYPE_PART_LEND = "12151011"; // 借出登记
	public static final String DICT_IN_OUT_TYPE_OTHER_OUT_STOCK = "12151017"; // 其他出库
	public static final String DICT_IN_OUT_TYPE_OTHER_IN_STOCK = "12151016"; // 其他入库
	public static final String DICT_IN_OUT_TYPE_HAND_BACK = "12151015"; // 采购退货
	public static final String DICT_IN_OUT_TYPE_PART_LOSS = "12151014"; // 配件报损
	public static final String DICT_IN_OUT_TYPE_PART_MOVE_OUT = "12151013"; // 配件移库出库
	public static final String DICT_IN_OUT_TYPE_BORROW_RETURN = "12151012"; // 借进归还
	public static final String DICT_IN_OUT_TYPE_ALLOCATE_OUT = "12151010"; // 调拨出库
	public static final String DICT_IN_OUT_TYPE_WORKSHOP_BORROW = "12151008"; // 车间借料
	public static final String DICT_IN_OUT_TYPE_REPAIR_DISPENSE_PART = "12151006"; // 维修发料
	public static final String DICT_IN_OUT_TYPE_LEND_RETURN = "12151004"; // 借出归还
	public static final String DICT_IN_OUT_TYPE_ALLOCATE_IN = "12151002"; // 调拨入库
	public static final String DICT_IN_OUT_TYPE_PART_BORROW = "12151003"; // 借进登记
	public static final String DICT_IN_OUT_TYPE_PART_SALE = "12151007"; // 配件销售
	public static final String DICT_IN_OUT_TYPE_INNER = "12151009"; // 内部领用
	public static final String DICT_IN_OUT_TYPE_IN_STOCK = "12151001"; // 采购入库
	public static final String DICT_IN_OUT_TYPE_PART_PROFIT = "12151005"; // 配件报溢
	public static final String DICT_IN_OUT_TYPE_INDENT_UNTREAD = "12151018"; // 订货退回
	public static final String DICT_IN_OUT_TYPE_COST_PRICE_IN_STOCK = "12151019"; // 成本价调整入库
	public static final String DICT_IN_OUT_TYPE_COST_PRICE_OUT_STOCK = "12151020"; // 成本价调整出库
	public static final String DICT_IN_OUT_TYPE_DISPENSE_PRICE_IN_STOCK = "12151021"; // 发料价格调整入库
	public static final String DICT_IN_OUT_TYPE_DISPENSE_PRICE_OUT_STOCK = "12151022"; // 发料价格调整出库
	public static final String DICT_IN_OUT_TYPE_PART_MOVE_IN = "12151023"; // 配件移库入库
	public static final String DICT_IN_OUT_TYPE_PART_BOOKPART = "12151024"; // 配件预留
	public static final String DICT_IN_OUT_TYPE_DECK_UPHOLSTER = "12151025"; // 装饰装潢出库

	/**
	 * 事务类型
	 */
	public static final String DICT_BUSINESS_TYPE = "1588"; // 事务类型
	public static final String DICT_BUSINESS_TYPE_PART_STOCK = "15880001"; // 采购入库
	public static final String DICT_BUSINESS_TYPE_BALANCE_ACCOUNTS = "15880002"; // 维修结算
	public static final String DICT_BUSINESS_TYPE_RECEIVE_MONEY = "15880003"; // 收款
	public static final String DICT_BUSINESS_TYPE_REPAIR_PART = "15880004"; // 维修领料
	public static final String DICT_BUSINESS_TYPE_CANCEL_BALANCE_ACCOUNTS = "15880005"; // 取消结算
	public static final String DICT_BUSINESS_TYPE_PART_PROFIT = "15880006"; // 配件报溢
	public static final String DICT_BUSINESS_TYPE_PART_LOSS = "15880007"; // 配件报损
	public static final String DICT_BUSINESS_TYPE_VEHICLE_STOCK_OUT_BY = "15880008"; // 车辆出库
	public static final String DICT_BUSINESS_TYPE_SO_INVOICE = "15880009"; // 开票登记
	public static final String DICT_BUSINESS_TYPE_STOCK_ENTRY_IN = "15880010"; // 整车入库
	public static final String DICT_BUSINESS_TYPE_PART_BUY_IS_PAYOFF = "15880011"; // 财务应付账款管理
	public static final String DICT_BUSINESS_TYPE_PART_ALLOCATE_IN = "15880012"; // 调拨入库
	public static final String DICT_BUSINESS_TYPE_PART_BORROW = "15880013"; // 借进登记
	public static final String DICT_BUSINESS_TYPE_PART_BORROW_RETURN = "15880014"; // 借出归还
	public static final String DICT_BUSINESS_TYPE_PART_SALES = "15880015"; // 配件销售
	public static final String DICT_BUSINESS_TYPE_PART_INNER = "15880016"; // 内部领用
	public static final String DICT_BUSINESS_TYPE_PART_ALLOCATE_OUT = "15880017"; // 调拨出库
	public static final String DICT_BUSINESS_TYPE_STOCK_LEND = "15880018"; // 借出登记
	public static final String DICT_BUSINESS_TYPE_PART_LEND_RETURN = "15880019"; // 借进归还
	public static final String DICT_BUSINESS_TYPE_STOCK_TRANSFER = "15880020"; // 配件移库
	public static final String DICT_BUSINESS_TYPE_BALANCE_SERVERTIME = "15880021"; // 结算收款
	public static final String DICT_BUSINESS_TYPE_UPHOLSTER_OUT = "15880022"; // 装饰装潢领料

	/**
	 * 预约来源
	 */
	public static final String DICT_PRECONTRACT_ORIGI = "1279";
	public static final String DICT_PRECONTRACT_COMEELETRICITY = "12791001"; // 来电
	public static final String DICT_PRECONTRACT_TOMILL = "12791002"; // 到厂
	public static final String DICT_PRECONTRACT_MAINTENANCE = "12791003"; // 定期保养
	public static final String DICT_PRECONTRACT_INSURANCE = "12791004"; // 保险到期
	public static final String DICT_PRECONTRACT_CHECK_VEHICLE = "12791005"; // 验车到期
	public static final String DICT_PRECONTRACT_NEW_VEHICLE = "12791006"; // 新车到期
	public static final String DICT_PRECONTRACT_VEHICLE_PART = "12791010"; // 配件到期
	public static final String DICT_PRECONTRACT_VEHICLE_MAINTENCE = "12791012"; // 保养邀约
	public static final String DICT_PRECONTRACT_SERVICE_ACTIVE = "12791014"; // 服务活动邀约
	public static final String DICT_PRECONTRACT_INTERNET = "12791015"; // 网络预约
	public static final String DICT_PRECONTRACT_MICROMSG = "12791017"; // 微信预约

	/**
	 * 预约单状态
	 */
	public static final String DICT_BOS_TYPE = "1254"; // 预约单状态类别
	public static final String DICT_BOS_NOT_ENTER = "12541001"; // 未进厂
	public static final String DICT_BOS_DELAY_ENTER = "12541003"; // 延迟进厂
	public static final String DICT_BOS_ADVANCE_ENTER = "12541004"; // 提前进厂
	public static final String DICT_BOS_ENTER_ON_TIME = "12541005"; // 准时进厂
	public static final String DICT_BOS_CANCEL_ENTER = "12541002"; // 取消进厂

	/**
	 * 工单状态
	 */
	public static final String DICT_RO_STATUS_TYPE = "1255"; // 工单状态类别
	public static final String DICT_RO_STATUS_TYPE_ON_REPAIR = "12551001"; // 在修
	public static final String DICT_RO_STATUS_TYPE_FOR_BALANCE = "12551005"; // 已提交结算
	public static final String DICT_RO_STATUS_TYPE_BALANCED = "12551010"; // 已结算

	/**
	 * 工单派工状态
	 */
	public static final String DICT_ASSIGN_TAG_TYPE = "1290"; // 工单派工状态类别
	public static final String DICT_ASSIGN_TAG_NOT_ASSIGN = "12901001"; // 未派工
	public static final String DICT_ASSIGN_TAG_ASSIGNED_PARTIAL = "12901002"; // 部分派工
	public static final String DICT_ASSIGN_TAG_ASSIGNED = "12901003"; // 已派工

	/**
	 * 特殊保修类型
	 */
	public static final String DICT_SPECIAL_WARRANTY = "3317";
	public static final String DICT_ESPECIAL_MAINTAIN_TERM = "33171001"; // 特殊保修期限
	public static final String DICT_LARGESS_MAINTAIN = "33171002"; // 赠送保养
	public static final String DICT_CANCEL_THREE_CONTRACT = "33171003"; // 取消三包索赔
	public static final String DICT_CANCEL_PURCHASE_MAINTENANCE = "33171004"; // 购买保养

	/**
	 * 配件订单状态
	 */
	public static final String DICT_PORS_TYPE = "1361"; // 配件订单状态类别
	public static final String DICT_PORS_NOT_REPORT = "13611001"; // 未上报
	public static final String DICT_PORS_REPORT = "13611002"; // 已上报
	public static final String DICT_PORS_RECEIVED = "13611003"; // 已接收
	public static final String DICT_PORS_REFUSE = "13611007"; // 已拒绝
	public static final String DICT_PORS_REJECT = "13611006"; // 已驳回
	public static final String DICT_PORS_PASSED = "13611005"; // 已通过
	public static final String DICT_PORS_DEALING = "13611011"; // 处理中
	public static final String DICT_PORS_CANCEL = "13611012"; // 取消
	public static final String DICT_PORS_FINISH = "13611013"; // 已完成

	/**
	 * 投诉来源
	 */
	public static final int DICT_COMPLAINT_ORIGIN_TYPE = 1121; // 投诉来源类别
	public static final int DICT_COMPLAINT_ORIGIN_OEM = 11211002; // OEM下发
	public static final int DICT_COMPLAINT_ORIGIN_INNER = 11211003; // 内部投诉
	public static final int DICT_COMPLAINT_ORIGIN_CUSTOMER = 11211004; // 客户投诉
	public static final int DICT_COMPLAINT_ORIGIN_TEL = 11211001; // 电访投诉

	/**
	 * 配件仓库属性
	 */
	public static final String PART_WAREHOUSE = "70041001"; // A仓库

	/**
	 * 工单类型
	 */
	public static final String DICT_RPT_TYPE = "1253"; // 工单类型类别
	public static final String DICT_RPT_REPAIR = "12531001"; // 维修
	public static final String DICT_RPT_RETURN = "12531003"; // 返工
	public static final String DICT_RPT_CLAIM = "12531004"; // 索赔
	public static final String DICT_RPT_SALE = "12531002"; // 销售
	/**
	 * 电商交车方式
	 **/
	public static final String DICT_DELIVERY_MODE_ELEC_ELEC = "1600"; // 电商交车方式
	public static final String DICT_DELIVERY_MODE_ELEC_ELEC_SPOT = "16001001"; // 现车资源
	public static final String DICT_DELIVERY_MODE_ELEC_ELEC_CALL = "16001002"; // CALL车资源

	/**
	 * 外呼成功原因
	 */
	public static final String DICT_OUTBOUND_SUCCESS_REASONS = "7002"; // 外呼成功原因
	public static final String DICT_OUTBOUND_SUCCESS_REASONS_NEED_CONTACT = "70021001"; // 需再联系
	public static final String DICT_OUTBOUND_SUCCESS_REASONS_SUCCESS_CHECK = "70021002"; // 成功核实
	public static final String DICT_OUTBOUND_SUCCESS_REASONS_INFO_UNCHECK = "70021003"; // 信息未核实

	/**
	 * 活动发布状态
	 */
	public static final String DICT_ACTIVITY_RELEASE_TAG_TYPE = "1289"; // 活动发布状态类别
	public static final String DICT_ACTIVITY_RELEASE_TAG_NOT_RELEASE = "12891001"; // 未发布
	public static final String DICT_ACTIVITY_RELEASE_TAG_RELEASED = "12891002"; // 已发布
	public static final String DICT_ACTIVITY_RELEASE_TAG_CANCEL = "12891003"; // 已取消

	/**
	 * 外呼失败原因
	 */
	public static final String DICT_OUTBOUND_FAILED_REASONS = "7001"; // 外呼失败原因
	public static final String DICT_OUTBOUND_FAILED_REASONS_NOT_PHONEOWNER = "70011001"; // 非机主
	public static final String DICT_OUTBOUND_FAILED_REASONS_NOT_CAROWNER = "70011002"; // 非车主
	public static final String DICT_OUTBOUND_FAILED_REASONS_NULL_NUMBER = "70011003"; // 空号
																						// 错号
	public static final String DICT_OUTBOUND_FAILED_REASONS_BUSY_LINE = "70011004"; // 占线
																					// 无人接听
																					// 停机

	/**
	 * 三包索赔
	 */
	public static final String DICT_WARNING_LEVEL_RED = "20831001"; // 预警等级红色
	public static final String DICT_WARNING_LEVEL_ORANGE = "20831002"; // 预警等级橙色
	public static final String DICT_WARNING_LEVEL_YELLOW = "20831003"; // 预警等级黄色
	public static final String DICT_WARNING_CATEGORY_POSITION = "20841001"; // 预警类别部位
	public static final String DICT_WARNING_CATEGORY_INFIX = "20841002"; // 预警类别中缀
	public static final String DICT_WARNING_CATEGORY_LIMIT = "20841003"; // 预警类别时限
	public static final String DICT_REPAIR_METHOD_REPAIR = "20841001"; // 修理方式维修
	public static final String DICT_REPAIR_METHOD_REPLACEMENT = "20841002"; // 修理方式更换

	/**
	 * 配件订单类型
	 */
	public static final String DICT_PART_ORDER_TYPE = "1359"; // 配件订单类型
	public static final String DICT_PART_ORDER_TYPE_STOCK = "13591001"; // STOCK
	public static final String DICT_PART_ORDER_TYPE_DAILY_URGENT = "13591002"; // DAILY\URGENT
	public static final String DICT_PART_ORDER_TYPE_VOR = "13591003"; // VOR

	/**
	 * 配件缺料结案状态
	 */
	public static final String DICT_PART_SHORT_CLOSE_STATUS_TYPE = "1286"; // 配件缺料结案状态
	public static final String DICT_PART_SHORT_CLOSE_STATUS_TYPE_END_A_CASE = "12861001"; // 已结案
	public static final String DICT_PART_SHORT_CLOSE_STATUS_TYPE_NOT_END_A_CASE = "12861002"; // 未结案

	/**
	 * 消息提醒类型
	 */
	public static final String DICT_MSG_TYPE = "6075";
	public static final String DICT_MSG_TYPE_BOOKING = "60751001"; // 预约提醒
	public static final String DICT_MSG_TYPE_SHORTAGE = "60751002"; // 缺料提醒
	public static final String DICT_MSG_TYPE_ARRIVAL = "60751003"; // 到货提醒
	public static final String DICT_MSG_TYPE_SEND_CAR = "60751004"; // 交车提醒
	public static final String DICT_MSG_TYPE_SALES_CLUE = "60751005"; // 销售线索下发提醒

	/**
	 * //菲亚特配件提报方式
	 */
	// ** 菲亚特订货状态
	public static final String DICT_ORDER_GOODS_STATUS = "4005"; // 菲亚特订货状态
	public static final String DICT_ORDER_GOODS_STATUS_NO = "40051001"; // 未订货
	public static final String DICT_ORDER_GOODS_STATUS_YES = "40051002"; // 已订货
	public static final String DICT_ORDER_GOODS_STATUS_MAKE_YES = "40051003"; // 强制完成

	/**
	 * 产品配件订单类型
	 */
	public static final String DICT_DMS_PART_ORDER_TYPE = "1248"; // 产品订单类型
	public static final String DICT_DMS_PART_ORDER_TYPE_CG = "12481001"; // 常规(库存)
	public static final String DICT_DMS_PART_ORDER_TYPE_JJ = "12481002"; // 紧急(客户)
	public static final String DICT_DMS_PART_ORDER_TYPE_BX = "12481003"; // 保修
	public static final String DICT_DMS_PART_ORDER_TYPE_QY = "12481004"; // 紧急汽运
	public static final String DICT_DMS_PART_ORDER_TYPE_EO = "12481005"; // EO
	public static final String DICT_DMS_PART_ORDER_TYPE_SO = "12481006"; // SO
	public static final String DICT_DMS_PART_ORDER_TYPE_VOR = "12481007"; // VOR
	public static final String DICT_DMS_PART_ORDER_TYPE_WO = "12481008"; // WO
	public static final String DICT_DMS_PART_ORDER_TYPE_TO = "12481009"; // TO
	public static final String DICT_DMS_PART_ORDER_TYPE_E_CODE = "12481010"; // ECODE
	public static final String DICT_DMS_PART_ORDER_TYPE_S_CODE = "12481011"; // SCODE
	public static final String DICT_DMS_PART_ORDER_TYPE_FO = "12481012"; // FO

	/**
	 * 订单分类
	 */
	public static final String DICT_MAIN_ORDER_TYPE = "1358"; // 订单分类
	public static final String DICT_MAIN_ORDER_TYPE_NORMAL = "13581001"; // Normal
	public static final String DICT_MAIN_ORDER_TYPE_BOD = "13581002"; // BOD
	public static final String DICT_MAIN_ORDER_TYPE_DFS = "13581003"; // DFS
	public static final String DICT_MAIN_ORDER_TYPE_REF = "13581005"; // REF

	/**
	 * 配件类别
	 */
	public static final String DICT_PART_CLASS_TYPE = "1136"; // 配件类别
	public static final String DICT_PART_CLASS_IN_COMMON_USE = "11361001"; // 常用
	public static final String DICT_PART_CLASS_DAMAGEABLE = "11361002"; // 易损
	public static final String DICT_PART_CLASS_BASE = "11361003"; // 基础
	public static final String DICT_PART_CLASS_OVER = "11361004"; // 辅料
	public static final String DICT_PART_CLASS_MAINTAIN_PRODUCT = "11361005"; // 养护品
	public static final String DICT_PART_CLASS_REFINE_PRODUCT = "11361006"; // 厂家精品
	public static final String DICT_PART_CLASS_SUPPLIES = "11361007"; // 用品
	public static final String DICT_PART_CLASS_CHASSIS_NUMBER = "11361008"; // 底盘号相关
	public static final String DICT_PART_CLASS_CONVENTIONAL_PART = "11361009"; // 常规零部件
	public static final String DICT_PART_CLASS_THE_THIRD = "11361010"; // 第三方
	public static final String DICT_PART_CLASS_BATTERY = "11361011"; // 电瓶

	/* 旧件状态 */
	public static final String DICT_OLD_PART_STATUS_WDHAC = "5022"; // 旧件状态
	public static final String DICT_OLD_PART_STATUS_ASC_HOLD = "50221001"; // 店内保留
	public static final String DICT_OLD_PART_STATUS_HAS_NOTIFY_RETURN = "50221002"; // 已通知回运
	public static final String DICT_OLD_PART_STATUS_RETURN_FINISHED = "50221003"; // 回运完成
	public static final String DICT_OLD_PART_STATUS_NOT_RETURN = "50221004"; // 未回运
	public static final String DICT_OLD_PART_STATUS_INSPECTION_INVALID = "50221005"; // 销毁检核无效
	public static final String DICT_OLD_PART_STATUS_HAS_DISTROY = "50221006"; // 已销毁

	/**
	 * 投保单单据状态
	 */
	public static final String DICT_INS_FORMS_STATUS = "1229";
	public static final String DICT_INS_FORMS_STATUS_UNFINISHED = "12291001";// 未完成
	public static final String DICT_INS_FORMS_STATUS_TURNOVER = "12291002";// 已成交
	public static final String DICT_INS_FORMS_STATUS_CONFIRMING = "12291003";// 财务确认中
	public static final String DICT_INS_FORMS_STATUS_CLOSED = "12291004"; // 已完成
	public static final String DICT_INS_FORMS_STATUS_INVALID = "12291005";// 已作废
	public static final String DICT_INS_FORMS_STATUS_WIND_UP = "12291006";// 已结案

	/**
	 * 不修原因
	 */
	public static final int DICT_SUGGEST_REASON_TYPE = 1251; // 不修原因类别
	public static final int DICT_SUGGEST_REASON_PRICE_HIGH = 12511001; // 价格太高
	public static final int DICT_SUGGEST_REASON_SHORT_PART = 12511002; // 缺件/料
	public static final int DICT_SUGGEST_REASON_CLIENT = 12511003; // 客户不修
	public static final int DICT_SUGGEST_REASON_OTHER = 12511004; // 其他

	/**
	 * 客户投诉结案状态
	 */
	public static final int DICT_COMPLAINT_CLOSE_STATUS_TYPE = 1120; // 客户投诉结案状态类别
	public static final int DICT_COMPLAINT_CLOSE_STATUS_NOT_END_A_CASE = 11201001; // 未结案
	public static final int DICT_COMPLAINT_CLOSE_STATUS_END_A_CASE = 11201002; // 已结案
	public static final int DICT_COMPLAINT_CLOSE_STATUS_APPLY_A_CASE = 11201003; // OEM申请结案
	public static final int DICT_COMPLAINT_CLOSE_STATUS_REJECT_A_CASE = 11201004; // OEM拒绝结案

	/**
	 * 投诉回访结果
	 */
	public static final int DICT_COMPLAINT_RESULT_TYPE = 1135; // 投诉回访结果类别
	public static final int DICT_COMPLAINT_RESULT_MORE_CONTENTED = 11351001; // 完全满意
	public static final int DICT_COMPLAINT_RESULT_DISCONTENTED = 11351004; // 有点不满意
	public static final int DICT_COMPLAINT_RESULT_CONTENTED = 11351002; // 非常满意
	public static final int DICT_COMPLAINT_RESULT_COMMON = 11351003; // 比较满意
	public static final int DICT_COMPLAINT_RESULT_TOTALLY_DISCONTENTED = 11351005; // 非常不满意

	/**
	 * 投诉类型
	 */
	public static final int DICT_COMPLAINT_TYPE = 1124; // 投诉类型类别;
	public static final int DICT_COMPLAINT_TYPE_SERVICE_ATTITUDE = 11241001; // 服务人员服务态度
	public static final int DICT_COMPLAINT_TYPE_PART_QUALITY = 11241007; // 供应配件的质量问题
	public static final int DICT_COMPLAINT_TYPE_PART_SUPPLY = 11241008; // 配件供应不及时
	public static final int DICT_COMPLAINT_TYPE_REPAIR_TECHNIC = 11241006; // 维修技术
	public static final int DICT_COMPLAINT_TYPE_NOT_FORMER_PART = 11241003; // 非原厂配件
	public static final int DICT_COMPLAINT_TYPE_REPAIR_TIME_LONGER = 11241004; // 维修时间长
	public static final int DICT_COMPLAINT_TYPE_PART_PRICE_HIGH = 11241005; // 配件价格过高/收费不合理
	public static final int DICT_COMPLAINT_TYPE_LOCAL_SERVICE = 11241002; // 现场服务及时性
	public static final int DICT_COMPLAINT_TYPE_GIVE_CAR_ON_TIME = 11241009; // 没有及时交车
	public static final int DICT_COMPLAINT_TYPE_NO_RESPONSION_HOT_LINE = 11241010; // 24小时热线无人接听

	/**
	 * 服务活动类别
	 */
	public static final String DICT_ACTIVITY_KIND_TYPE = "2003"; // 服务活动类别
	public static final String DICT_ACTIVITY_KIND_RRT = "20031001"; // 'RRT快速反应'
	public static final String DICT_ACTIVITY_KIND_TSB = "20031002"; // 'TSB技术通报'
	public static final String DICT_ACTIVITY_KIND_CSN = "20031003"; // 'CSN满意度改进'
	public static final String DICT_ACTIVITY_KIND_RETURN = "20031004"; // 'Recall召回活动'
	public static final String DICT_ACTIVITY_KIND_WX = "20031005"; // '免费维修'
	public static final String DICT_ACTIVITY_KIND_JC = "20031006"; // '免费检查'
	public static final String DICT_ACTIVITY_KIND_FSA = "20031007";// '维修套餐'
	public static final String DICT_ACTIVITY_KIND_PS = "20031008";// '配件活动'

	/**
	 * 交车状态
	 */
	public static final String DICT_DELIVERY_STATUS_TYPE = "1258"; // 交车状态类别
	public static final String DICT_DELIVERY_STATUS_TYPE_YES = "12581001"; // 已交车
	public static final String DICT_DELIVERY_STATUS_TYPE_NO = "12581002"; // 未交车
	
	/**
	 * 出险信息跟踪状态
	 */
	public static final String DICT_OUT_DANGER_TRACE_STATUS="1606";
	public static final String DICT_OUT_DANGER_TRACE_STATUS_TREATING="16061001";//未跟踪
	public static final String DICT_OUT_DANGER_TRACE_STATUS_CAME="16061002";//已来厂
	public static final String DICT_OUT_DANGER_TRACE_STATUS_SUCCEED="16061003";// 成功结束跟踪
	public static final String DICT_OUT_DANGER_TRACE_STATUS_FAILED="16061004";// 失败结束
	public static final String DICT_OUT_DANGER_TRACE_STATUS_CONTINUE="16061005";//继续跟踪
	
	/**
	  * 延保索赔申请单状态
	  */
	 public static final String  DICT_CLAIMA_STATUS = "3334"; //延保索赔申请单状态
	 public static final String  DICT_CLAIMA_STATUS_NOT_AUDIT = "33341001"; //未提交
	 public static final String  DICT_CLAIMA_STATUS_CANCEL = "33341002"; //已作废
	 public static final String  DICT_CLAIMA_STATUS_TECHNICAL_AUDITING = "33341011"; //技术经理审核中
	 public static final String  DICT_CLAIMA_STATUS_TECHNICAL_REFUSE = "33341012"; //技术经理拒绝
	 public static final String  DICT_CLAIMA_STATUS_TECHNICAL_TURN = "33341013"; //技术经理驳回
	 public static final String  DICT_CLAIMA_STATUS_SERVICE_AUDITING = "33341021"; //服务经理审核中
	 public static final String  DICT_CLAIMA_STATUS_SERVICE_REFUSE = "33341022"; //服务经理审拒绝
	 public static final String  DICT_CLAIMA_STATUS_SERVICE_TURN = "33341023"; //服务经理驳回
	 public static final String  DICT_CLAIMA_STATUS_GENERAL_AUDITING = "33341031"; //总经理审核中
	 public static final String  DICT_CLAIMA_STATUS_GENERAL_REFUSE = "33341032"; //总经理拒绝
	 public static final String  DICT_CLAIMA_STATUS_GENERAL_TURN = "33341033"; // 总经理驳回
	 public static final String  DICT_CLAIMA_STATUS_4S_AUDITED = "33341038"; //店面审核通过
	 public static final String  DICT_CLAIMA_STATUS_IS_UPLOAD = "33341040"; //已上报
	 public static final String  DICT_CLAIMA_STATUS_OEM_AUDITED = "33341041"; //总部索赔通过
	 public static final String  DICT_CLAIMA_STATUS_OEM_REFUSE = "33341042";//总部索赔拒绝
	 public static final String  DICT_CLAIMA_STATUS_OEM_TURN = "33341043"; //总部驳回修改
}
