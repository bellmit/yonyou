/*
 * Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
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

/**
 * 业务描述：常用系统错误静态变量定义
 * 
 * @author kevin
 * @date 2007-9-30 下午02:27:35
 *
 */

public class CommonErrorConstant {

    /**
     *  描述： 常用错误
     */
    public static final String COMMON_ERROR_CODE = "EC-GEN-0001";
    /**
     *  描述： 登录错误
     */
    public static final String MSG_ERROR_USER_NOT_LOGIN = "MSG_ERROR_USER_NOT_LOGIN";
    /**
     *  描述： 保存错误
     */
    public static final String MSG_ERROR_SAVE = "MSG_ERROR_SAVE";
    /**
     *  描述： 增加错误
     */
    public static final String MSG_ERROR_ADD = "MSG_ERROR_ADD";
    /**
     *  描述： 删除错误
     */
    public static final String MSG_ERROR_DELETE = "MSG_ERROR_DELETE";
    /**
     *  描述： 更新错误
     */
    public static final String MSG_ERROR_UPDATE = "MSG_ERROR_UPDATE";
    /**
     *  描述： 查询错误
     */
    public static final String MSG_ERROR_QUERY = "MSG_ERROR_QUERY";
    /**
     *  描述： 取消错误
     */
    public static final String MSG_ERROR_CANCEL = "MSG_ERROR_CANCEL";   
    
    
    /**
     *  描述： 核对参数错误
     */
    public static final String MSG_ERROR_CHECKPARAMS="MSG_ERROR_CHECKPARAMS";
    
    
    /**
     *  描述： 业务加锁错误
     */
    public static final String MSG_ERROR_BUSINESS_LOCK = "MSG_ERROR_BUSINESS_LOCK";
    
    
    /**
     *  描述： 丢失主键值
     */
    public static final String MSG_ERROR_LOST_KEY = "MSG_ERROR_LOST_KEY";
    /**
     * 描述： 库存状态为一件一库，仓库已存在此配件
     */
    public static final String MSG_ONETOONE_PART_SAVE = "MSG_ONETOONE_PART_SAVE";
    
    /**
     *  描述：工单被锁
     */
    public static final String MSG_CONVERSION_REPAIR_ORDER_LOCK = "MSG_CONVERSION_REPAIR_ORDER_LOCK";
    
    /**
     *  描述：工单不是维修状态
     */
    public static final String MSG_CONVERSION_REPAIR_ORDER_ERRO = "MSG_CONVERSION_PART_ORDER_LOCK";
    
    /**
     *  描述：配件销售订单被锁 
     */
    public static final String MSG_CONVERSION_PART_ORDER_LOCK = "MSG_CONVERSION_PART_ORDER_LOCK";
    
    /**
     *  描述：配件销售订单应经结算
     */
    public static final String MSG_CONVERSION_PART_ORDER_ERRO = "MSG_CONVERSION_PART_ORDER_ERRO";
    /**
     *  描述：检查输入的VIN是否在车辆表里存在
     */
    public static final String MSG_QUERY_VEHICLE_ERRO = "MSG_QUERY_VEHICLE_ERRO";
    
    /**
     *  描述：订单转换过工单，工单正在操作中，不能更换VIN，请稍后
     */
    public static final String MSG_VIN_REPAIRORDER_ERRO = "MSG_VIN_REPAIRORDER_ERRO";
    
    /**
     *  描述：销售订单检查客户编号为空
     */
    public static final String MSG_CUTOMER_NULL_ERRO = "MSG_CUTOMER_NULL_ERRO";
    
    /**
     * 描述：此车辆是销售出库产生的，不能删除
     */
    public static final String MSG_ERROR_CAR_CUSTOMERNO_NULL="MSG_ERROR_CAR_CUSTOMERNO_NULL";
    /**
     * 描述：描述：此车辆未登记销售发票，不能上报，请先登记车辆销售发票
     */
    public static final String MSG_ERROR_CAR_INVOICE_NULL="MSG_ERROR_CAR_INVOICE_NULL";
    
    /**
     * 描述：销售日期不能为空，请填写销售日期。
     */
    public static final String MSG_ERROR_SALES_DATE_NULL="MSG_ERROR_SALES_DATE_NULL";
    
    /**
     * 描述：销售资料已上报，不能修改销售日期，请使用销售车辆信息变更申请功能。
     */
    public static final String MSG_ERROR_NO_CHANGE_SALES_DATE="MSG_ERROR_NO_CHANGE_SALES_DATE";
    /**
     * 描述：销售资料已上报，不能修改客户名称，请使用销售车辆信息变更申请功能。
     */
    public static final String MSG_ERROR_NO_CHANGE_CUSTOMER_NAME="MSG_ERROR_NO_CHANGE_CUSTOMER_NAME";
    /**
     * 描述：销售资料已上报，不能修改客户地址，请使用销售车辆信息变更申请功能。
     */
    public static final String MSG_ERROR_NO_CHANGE_CUSTOMER_ADDRESS="MSG_ERROR_NO_CHANGE_CUSTOMER_ADDRESS";
    
    /**
     * 描述：输入信息不完整！
     */
    public static final String MSG_ERROR_INPUT_NOT_INTEGRITED="MSG_ERROR_INPUT_NOT_INTEGRITED";
    /**
     * 描述：您输入的密码不正确！
     */
    public static final String MSG_ERROR_PASSWORD_NOT_CORRECT="MSG_ERROR_PASSWORD_NOT_CORRECT";
    /**
     * 描述：配件
     */
    public static final String MSG_ERROR_PART_NOT_LOW_COST="MSG_ERROR_PART_NOT_LOW_COST";
    /**
     * 描述：该配件已被入账
     */
    public static final String MSG_ERROR_PART_BEEN_ACCOUNT="MSG_ERROR_PART_BEEN_ACCOUNT";
    /**
     * 描述：该工单已经有配件销售单据
     */
    public static final String MSG_ERROR_SALES_PART_RO_BEEN="MSG_ERROR_SALES_PART_RO_BEEN";
    /**
     * 描述：配件库存中没该配件信息
     */
    public static final String MSG_ERROR_PART_NO="MSG_ERROR_PART_NO";
    /**
     * 描述：工单中有未入帐的配件，不能关单
     */
    public static final String MSG_ERROR_NOT_ACCOUNT_PART_NOT_CLOSE="MSG_ERROR_NOT_ACCOUNT_PART_NOT_CLOSE";
    /**
     * 描述：配件已经停用，不能转销售单
     */
    public static final String MSG_ERROR_PART_NOT_SALES="MSG_ERROR_PART_NOT_SALES";
    /**
     * 维修项目、项目车型组、配件号码存在为空的字符，请检查
     */
    public static final String MSG_ERROR_PART_NO_NULL="MSG_ERROR_PART_NO_NULL";
    /**
     * 该结算单相关联的工单已做过索赔，不能负结算！
     */
    public static final String MSG_ERROR_NO_RED="MSG_ERROR_NO_RED";
    /**
     * 索赔单状态不是未上报！
     */
    public static final String MSG_ERROR_NO_UPLOAD="MSG_ERROR_NO_UPLOAD";
    
    /**
     * DMS系统已经超过授权使用日期，请联系客服！
     */
    public static final String MSG_VALID_MESSAGE="MSG_VALID_MESSAGE";
    /**
     * 已入帐的内部领用单据不能再进行保存操作！
     */ 
    public static final String MSG_CAN_NOT_SAVE_MESSAGE="MSG_CAN_NOT_SAVE_MESSAGE"; 
    /**
     * 工单状态已变更,不能取消竣工！
     */ 
    public static final String MSG_SAVE_MESSAGE_CHANGE="MSG_SAVE_MESSAGE_CHANGE";   
    /**
     * 该结算单相关联的工单已做过延保索赔单，不能负结算！
     */
    public static final String MSG_ERROR_EX_CLAIMED="MSG_ERROR_EX_CLAIMED";
    
    
    
    
    
    
    
    /**
     *单据已结算，不能重复结算！
     **/
public static final String MSG_ERROR_1="MSG_ERROR_1";

    /**
     *结算单存在收款记录，不能负结算！
     **/
public static final String MSG_ERROR_2="MSG_ERROR_2";

    /**
     *结算单存在减免记录，不能负结算！
     **/
public static final String MSG_ERROR_3="MSG_ERROR_3";

    /**
     *结算单已关单，不能负结算！
     **/
public static final String MSG_ERROR_4="MSG_ERROR_4";

    /**
     *退款金额不能大于相同付款方式的已收金额！【收费对象：{0}，付款方式：{1}，已收金额为：{2}
     **/
public static final String MSG_ERROR_5="MSG_ERROR_5";

    /**
     *已经被负结算，不能进行收款作业
     **/
public static final String MSG_ERROR_6="MSG_ERROR_6";

    /**
     *结算单{0}已经被负结算，不能进行收款作业
     **/
public static final String MSG_ERROR_7="MSG_ERROR_7";

    /**
     *未付金额已经变动，请重新刷新后再做收款
     **/
public static final String MSG_ERROR_8="MSG_ERROR_8";

    /**
     *实际退款金额不能大于应退金额
     **/
public static final String MSG_ERROR_9="MSG_ERROR_9";

    /**
     *请重新输入,将导致可用余额小于0
     **/
public static final String MSG_ERROR_10="MSG_ERROR_10";

    /**
     *车辆价格要大于等于批售价，批售价是{0}
     **/
public static final String MSG_ERROR_11="MSG_ERROR_11";

    /**
     *该车已经生成保有客户，发票不能删除
     **/
public static final String MSG_ERROR_12="MSG_ERROR_12";

    /**
     *该订单有批售单，发票不能删除
     **/
public static final String MSG_ERROR_13="MSG_ERROR_13";

    /**
     *该销售订单有退回单，不能进行编辑
     **/
public static final String MSG_ERROR_14="MSG_ERROR_14";

    /**
     *已经被负结算，不能进行收款作业
     **/
public static final String MSG_ERROR_15="MSG_ERROR_15";

    /**
     *结算单{0}已经被负结算，不能进行收款作业
     **/
public static final String MSG_ERROR_16="MSG_ERROR_16";

    /**
     *未付金额已经变动，请重新刷新后再做收款
     **/
public static final String MSG_ERROR_17="MSG_ERROR_17";

    /**
     *结算单{0}未付金额已经变动，请重新刷新后再做收款
     **/
public static final String MSG_ERROR_18="MSG_ERROR_18";

    /**
     *该销售订单有退回单，不能进行编辑
     **/
public static final String MSG_ERROR_19="MSG_ERROR_19";

    /**
     *请重新输入,将导致可用余额小于0
     **/
public static final String MSG_ERROR_20="MSG_ERROR_20";

    /**
     *结算单号{0}已关单,不能重复关单!
     **/
public static final String MSG_ERROR_21="MSG_ERROR_21";

    /**
     *单据：{0}已被负结算，不能结算关单！
     **/
public static final String MSG_ERROR_22="MSG_ERROR_22";

    /**
     *配置代码是{0}的不能选择OEM品牌
     **/
public static final String MSG_ERROR_23="MSG_ERROR_23";

    /**
     *车型代码是{0}的不能选择OEM品牌
     **/
public static final String MSG_ERROR_24="MSG_ERROR_24";

    /**
     *车型【{0}】所属的车系为无效状态，请先将其车系改成有效！
     **/
public static final String MSG_ERROR_25="MSG_ERROR_25";

    /**
     *车系代码是{0}的不能选择OEM品牌
     **/
public static final String MSG_ERROR_26="MSG_ERROR_26";

    /**
     *车系【{0}】所属的品牌为无效状态，请先将其品牌改成有效！
     **/
public static final String MSG_ERROR_27="MSG_ERROR_27";

    /**
     *没有备份文件存在！
     **/
public static final String MSG_ERROR_28="MSG_ERROR_28";

    /**
     *调拨入库单号:{0}已经入账，不能重复入账！
     **/
public static final String MSG_ERROR_29="MSG_ERROR_29";

    /**
     *调拨出库单号:{0}已经入账，不能重复入账！
     **/
public static final String MSG_ERROR_30="MSG_ERROR_30";

    /**
     *借进登记单号:{0}已经入账，不能重复入账！
     **/
public static final String MSG_ERROR_31="MSG_ERROR_31";

    /**
     *内部领用单号:{0}已经入账，不能重复入账！
     **/
public static final String MSG_ERROR_32="MSG_ERROR_32";

    /**
     *借出登记单号:{0}已经入账，不能重复入账！
     **/
public static final String MSG_ERROR_33="MSG_ERROR_33";

    /**
     *单据状态已改变,请重新选择
     **/
public static final String MSG_ERROR_34="MSG_ERROR_34";

    /**
     *内部领用单号:{0}已经入账，不能重复入账！
     **/
public static final String MSG_ERROR_35="MSG_ERROR_35";

    /**
     *报溢单号:{0}已经入账，不能重复入账！
     **/
public static final String MSG_ERROR_36="MSG_ERROR_36";

    /**
     *工单已经提交结算或者已经结算
     **/
public static final String MSG_ERROR_37="MSG_ERROR_37";

    /**
     *采购入库单号:{0}已经入账，不能重复入账！
     **/
public static final String MSG_ERROR_38="MSG_ERROR_38";

    /**
     *调拨出库单号:{0}已经入账，不能重复入账！
     **/
public static final String MSG_ERROR_39="MSG_ERROR_39";

    /**
     *归还数量不能大于借进数量
     **/
public static final String MSG_ERROR_40="MSG_ERROR_40";

    /**
     *没有低于成本价出库的权限!
     **/
public static final String MSG_ERROR_41="MSG_ERROR_41";

    /**
     *配件代码不能为空
     **/
public static final String MSG_ERROR_42="MSG_ERROR_42";

    /**
     *仓库代码不能为空
     **/
public static final String MSG_ERROR_43="MSG_ERROR_43";

    /**
     *配件代码为{0}的配件已被停用
     **/
public static final String MSG_ERROR_44="MSG_ERROR_44";

    /**
     *仓库{0}配件{1}可用库存小于预留数量！
     **/
public static final String MSG_ERROR_45="MSG_ERROR_45";

    /**
     *该配件:{0}已经做过预留,不允许删除!
     **/
public static final String MSG_ERROR_46="MSG_ERROR_46";

    /**
     *货运单:{0}已有采购入库单:{1}
     **/
public static final String MSG_ERROR_47="MSG_ERROR_47";

    /**
     *工单{0}不为在修,不允许维修发料
     **/
public static final String MSG_ERROR_48="MSG_ERROR_48";

    /**
     *配件库存中没该配件信息
     **/
public static final String MSG_ERROR_49="MSG_ERROR_49";

    /**
     *仓库代码为:{0}该货运单不满足作废条件，不能作废!
     **/
public static final String MSG_ERROR_50="MSG_ERROR_50";

    /**
     *该订单已上报,无法操作!
     **/
public static final String MSG_ERROR_51="MSG_ERROR_51";

    /**
     *该订单已完成,无法操作!
     **/
public static final String MSG_ERROR_52="MSG_ERROR_52";

    /**
     *预留大于可用库存，不能预留
     **/
public static final String MSG_ERROR_53="MSG_ERROR_53";

    /**
     *工单的开单时间和创建时间不在同一天内!
     **/
public static final String MSG_ERROR_54="MSG_ERROR_54";

    /**
     *单据已被负结算，不能取消交车
     **/
public static final String MSG_ERROR_55="MSG_ERROR_55";

    /**
     *单据已被负结算，不能交车
     **/
public static final String MSG_ERROR_56="MSG_ERROR_56";

    /**
     *配件{0}已经入账,不允许删除!
     **/
public static final String MSG_ERROR_57="MSG_ERROR_57";

    /**
     *预留大于可用库存，不能预留
     **/
public static final String MSG_ERROR_58="MSG_ERROR_58";

    /**
     *配件不存在对应的仓库，不能添加该 活动
     **/
public static final String MSG_ERROR_59="MSG_ERROR_59";

    /**
     *存在索赔配件但不存在索赔项目，不能拆分工单！
     **/
public static final String MSG_ERROR_60="MSG_ERROR_60";

    /**
     *未入帐销售配件数量大于零，不能提交结算
     **/
public static final String MSG_ERROR_61="MSG_ERROR_61";

    /**
     *存在索赔配件但不存在索赔项目，不能拆分工单和提交结算！
     **/
public static final String MSG_ERROR_62="MSG_ERROR_62";

    /**
     *工单保存未成功，可能已经提交结算或结算!","工单保存未成功，可能已经提交结算或结算!
     **/
public static final String MSG_ERROR_63="MSG_ERROR_63";

    /**
     *车辆不存在
     **/
public static final String MSG_ERROR_64="MSG_ERROR_64";

    /**
     *该车辆不存在,不能做预收款操作
     **/
public static final String MSG_ERROR_65="MSG_ERROR_65";

    /**
     *{0}中的配件{1}已存在!
     **/
public static final String MSG_ERROR_66="MSG_ERROR_66";

    /**
     *数据量太大，请选择条件缩小查询范围！
     **/
public static final String MSG_ERROR_67="MSG_ERROR_67";

    /**
     *该用户已经在其它客户端已经重新登陆过,请重新登陆!
     **/
public static final String MSG_ERROR_68="MSG_ERROR_68";

    /**
     *没有获取到有效的版本校验记录，请联系服务热线！
     **/
public static final String MSG_ERROR_69="MSG_ERROR_69";

    /**
     *服务器端的版本已过期，请更新服务器版本！
     **/
public static final String MSG_ERROR_70="MSG_ERROR_70";

    /**
     *发现新的程序版本，请在{0}以前更新服务器端程序，如果不更新服务器端程序，{1}以后系统将无法正常使用，可升级的服务器版本号为：{2}
     **/
public static final String MSG_ERROR_71="MSG_ERROR_71";

    /**
     *客户端的版本与服务器要求的版本不一致，请更新客户端程序， 当前客户端版本：{0}[{1}]新的客户端版本{2}[{3}]
     **/
public static final String MSG_ERROR_72="MSG_ERROR_72";

    /**
     *该用户已经在其它客户端已经重新登陆过,请重新登陆!
     **/
public static final String MSG_ERROR_73="MSG_ERROR_73";

    /**
     *用户重复登陆!
     **/
public static final String MSG_ERROR_74="MSG_ERROR_74";

    /**
     *未做任何处理的旧件回运消息共{0}条
     **/
public static final String MSG_ERROR_75="MSG_ERROR_75";

    /**
     *工具
     **/
public static final String MSG_ERROR_76="MSG_ERROR_76";

    /**
     *已经在业务中使用过，不能删除！
     **/
public static final String MSG_ERROR_77="MSG_ERROR_77";

    /**
     *工具代码已存在！
     **/
public static final String MSG_ERROR_78="MSG_ERROR_78";

    /**
     *精确小数点后几位必须为正数或者0
     **/
public static final String MSG_ERROR_79="MSG_ERROR_79";

    /**
     *配置信息下挂有批售销售信息不能删除
     **/
public static final String MSG_ERROR_80="MSG_ERROR_80";

    /**
     *批售单只有未上报才能删除
     **/
public static final String MSG_ERROR_81="MSG_ERROR_81";

    /**
     *折让率{0}超出长度
     **/
public static final String MSG_ERROR_82="MSG_ERROR_82";

    /**
     *二次标记已打上，主单处于未上报或者通过状态下才可以修改配置信息
     **/
public static final String MSG_ERROR_83="MSG_ERROR_83";

    /**
     *二次上报标记未打上,批售单处于未上报状态或者处于通过状态才可以修改
     **/
public static final String MSG_ERROR_84="MSG_ERROR_84";

    /**
     *配置下挂有批售销售信息不能删除
     **/
public static final String MSG_ERROR_85="MSG_ERROR_85";

    /**
     *销售资料已上报，不能修改客户名称，请使用销售车辆信息变更申请功能。
     **/
public static final String MSG_ERROR_86="MSG_ERROR_86";

    /**
     *销售资料已上报，不能修改客户地址，请使用销售车辆信息变更申请功能。
     **/
public static final String MSG_ERROR_87="MSG_ERROR_87";

    /**
     *销售资料已上报，不能修改销售日期，请使用销售车辆信息变更申请功能。
     **/
public static final String MSG_ERROR_88="MSG_ERROR_88";

    /**
     *客户编号为{0}的级别已经变化不能保存
     **/
public static final String MSG_ERROR_89="MSG_ERROR_89";

    /**
     *批售单状态已经发生变化不能操作
     **/
public static final String MSG_ERROR_90="MSG_ERROR_90";

    /**
     *该车辆已经存在,不能新增
     **/
public static final String MSG_ERROR_91="MSG_ERROR_91";

    /**
     *本站销售的车辆的的销售日期不可修改，如需修改请走销售日期变更流程!
     **/
public static final String MSG_ERROR_92="MSG_ERROR_92";

    /**
     *数据版本不一致！
     **/
public static final String MSG_ERROR_93="MSG_ERROR_93";

    /**
     *储值余额不足！
     **/
public static final String MSG_ERROR_94="MSG_ERROR_94";

    /**
     *没有要建卡的车辆！
     **/
public static final String MSG_ERROR_95="MSG_ERROR_95";

    /**
     *会员名称为空不能保存！
     **/
public static final String MSG_ERROR_96="MSG_ERROR_96";

    /**
     *vin{0}已经办过会员卡了，不能再办了
     **/
public static final String MSG_ERROR_97="MSG_ERROR_97";

    /**
     *请选择会员卡！
     **/
public static final String MSG_ERROR_98="MSG_ERROR_98";

    /**
     *会员名称为空不能保存！
     **/
public static final String MSG_ERROR_99="MSG_ERROR_99";

    /**
     *会员卡编号为空不能保存！
     **/
public static final String MSG_ERROR_100="MSG_ERROR_100";

    /**
     *VIN:{0}所在订单状态不为已关单
     **/
public static final String MSG_ERROR_101="MSG_ERROR_101";

    /**
     *车辆库存中有该{0}VIN码车辆,不能做入库操作
     **/
public static final String MSG_ERROR_102="MSG_ERROR_102";

    /**
     *车辆库存中该{0}VIN码车辆库存状态不为在库状态，不能做移位操作。
     **/
public static final String MSG_ERROR_103="MSG_ERROR_103";

    /**
     *车不在库
     **/
public static final String MSG_ERROR_104="MSG_ERROR_104";

    /**
     *该VIN码{0}车辆已经出库
     **/
public static final String MSG_ERROR_105="MSG_ERROR_105";

    /**
     *该VIN码{0}车辆库存状态不为在库
     **/
public static final String MSG_ERROR_106="MSG_ERROR_106";

    /**
     *该{0}VIN码车辆已经出库
     **/
public static final String MSG_ERROR_107="MSG_ERROR_107";

    /**
     *订单已发生变化，{0}不能出库，系统已删除出库单记录
     **/
public static final String MSG_ERROR_108="MSG_ERROR_108";

    /**
     *订单已发生变化，{0}不能出库，订单状态不为已交车确认
     **/
public static final String MSG_ERROR_109="MSG_ERROR_109";

    /**
     *工单号为{0}的被锁定，请解锁后出库
     **/
public static final String MSG_ERROR_110="MSG_ERROR_110";

    /**
     *车辆库存中该{0}VIN码车辆库存状态不为在库，不能做移库操作。
     **/
public static final String MSG_ERROR_111="MSG_ERROR_111";

    /**
     *车辆库存中该{0}VIN码车辆配车状态不为未配车,不能做移库操作。
     **/
public static final String MSG_ERROR_112="MSG_ERROR_112";

    /**
     *该订单在处理状态中，暂不能处理！
     **/
public static final String MSG_ERROR_113="MSG_ERROR_113";

    /**
     *该订单存在收款，请先退款！
     **/
public static final String MSG_ERROR_114="MSG_ERROR_114";

    /**
     *已存在保有客户信息,是否修改!
     **/
public static final String MSG_ERROR_115="MSG_ERROR_115";

    /**
     *联系人电话,手机或证件已存在!
     **/
public static final String MSG_ERROR_116="MSG_ERROR_116";

    /**
     *{0}产品名称为{1}
     **/
public static final String MSG_ERROR_117="MSG_ERROR_117";

    /**
     *联系电话为：{0}
     **/
public static final String MSG_ERROR_118="MSG_ERROR_118";

    /**
     *联系手机为：{0}
     **/
public static final String MSG_ERROR_119="MSG_ERROR_119";

    /**
     *证件号码为:{0}
     **/
public static final String MSG_ERROR_120="MSG_ERROR_120";

    /**
     *在保有客户中已经存在。
     **/
public static final String MSG_ERROR_121="MSG_ERROR_121";

    /**
     *该售前服务订单已关联销售订单，不能再修改！
     **/
public static final String MSG_ERROR_122="MSG_ERROR_122";

    /**
     *该车辆不在库！
     **/
public static final String MSG_ERROR_123="MSG_ERROR_123";

    /**
     *该车辆已配车！
     **/
public static final String MSG_ERROR_124="MSG_ERROR_124";

    /**
     *该车辆不存在！
     **/
public static final String MSG_ERROR_125="MSG_ERROR_125";

    /**
     *该车辆已经做过售前装潢订单！
     **/
public static final String MSG_ERROR_126="MSG_ERROR_126";

    /**
     *{0}: 联系电话：{1}
     **/
public static final String MSG_ERROR_127="MSG_ERROR_127";

    /**
     *{0}:联系手机：{1}
     **/
public static final String MSG_ERROR_128="MSG_ERROR_128";

    /**
     *{0}:证件号码为：{1}
     **/
public static final String MSG_ERROR_129="MSG_ERROR_129";

    /**
     *以上车辆将生成的保有客户已经存在,是否全部新建保有客户？
     **/
public static final String MSG_ERROR_130="MSG_ERROR_130";

    /**
     *移库单号不能为空
     **/
public static final String MSG_ERROR_131="MSG_ERROR_131";

    /**
     *QCS流程订单编号在数据库中已存在，不能新增
     **/
public static final String MSG_ERROR_132="MSG_ERROR_132";

    /**
     *QCS流程客户意向在数据库中已存在，不能新增
     **/
public static final String MSG_ERROR_133="MSG_ERROR_133";

    /**
     *QCS流程订单编号在数据库中已存在，不能修改
     **/
public static final String MSG_ERROR_134="MSG_ERROR_134";

    /**
     *【订单状态】已修改，请重新打开该订单进行编辑
     **/
public static final String MSG_ERROR_135="MSG_ERROR_135";

    /**
     *已存在电话或者手机号码重复的客户，客户名称为：{0}客户编号为：{1}，属于销售顾问：{2}。请选择原有客户信息建立订单或者重新维护订单界面客户信息
     **/
public static final String MSG_ERROR_136="MSG_ERROR_136";

    /**
     *车辆明细中已有与此{0}VIN码和此{1}入库单号相同的记录
     **/
public static final String MSG_ERROR_137="MSG_ERROR_137";

    /**
     *新VIN已经存在，请重新输入!
     **/
public static final String MSG_ERROR_138="MSG_ERROR_138";

    /**
     *该{0}VIN码车辆已经出库
     **/
public static final String MSG_ERROR_139="MSG_ERROR_139";

    /**
     *工单号为{0}的被锁定，请解锁后出库
     **/
public static final String MSG_ERROR_140="MSG_ERROR_140";

    /**
     *该VIN码车辆库存状态不为在库
     **/
public static final String MSG_ERROR_141="MSG_ERROR_141";

    /**
     *该VIN:{0}车已经产生保有客户信息，不允许删除！
     **/
public static final String MSG_ERROR_142="MSG_ERROR_142";

    /**
     *该VIN:{1}已交车出库，此单据不允许删除！
     **/
public static final String MSG_ERROR_143="MSG_ERROR_143";

    /**
     *车辆表里不存在此VIN
     **/
public static final String MSG_ERROR_144="MSG_ERROR_144";

    /**
     *工单状态已变更,不能取消竣工!
     **/
public static final String MSG_ERROR_145="MSG_ERROR_145";

    /**
     *此用户已存在，请重新输入！
     **/
public static final String MSG_ERROR_146="MSG_ERROR_146";

    /**
     *此科目编码已存在，请重新输入！
     **/
public static final String MSG_ERROR_147="MSG_ERROR_147";

    /**
     *短信账户密码不正确,无法查询!
     **/
public static final String MSG_ERROR_148="MSG_ERROR_148";

    /**
     *工单状态不是在修状态,不允许返回车间!
     **/
public static final String MSG_ERROR_149="MSG_ERROR_149";

    /**
     *工单:{0}工单拆分状态为:{1}
     **/
public static final String MSG_ERROR_150="MSG_ERROR_150";

   /**
    * 保有客户上报:上报标识为是的，提示已经上报，不允许在上报
    */

public static final String MSG_ERROR_151="MSG_ERROR_151";
/**
 * 配件报损   入库单号{0}已经入账，不能重复入账。
 */
public static final String MSG_ERROR_152="MSG_ERROR_152";
/**
 * 付款对象{0}的已用金额大于可用总金额，请确认后重新操作！
 */
public static final String MSG_ERROR_153="MSG_ERROR_153";
/**
 * 付款单{0}的付款金额大于付款总额，请确认后重新操作！
 */
public static final String MSG_ERROR_154="MSG_ERROR_154";

/**
 * 用户重复登录！
 */
public static final String MSG_USER_REPEAT_LOGIN="MSG_USER_REPEAT_LOGIN";

/**
 * 用户登录出错
 */
public static final String MSG_USER_LOGIN_ERROR="MSG_USER_LOGIN_ERROR";

/**
 * *修改版本控制
 */

public static final String MSG_VER_LOCEKD_ERROR="MSG_VER_LOCEKD_ERROR";
/**
 * 配件{0}已删除，不能预留
 */
public static final String MSG_PART_DELETE_ERROR="MSG_PART_DELETE_ERROR";
/**
 * 配件{0} 数量不能为空
 */
public static final String MSG_PART_QUANTITY_ERROR="MSG_PART_QUANTITY_ERROR";
/**
 * 客户编号为{0}的战败原因不能为空
 */
public static final String MSG_CONFIRM_CUSCOMER_FAILURE="MSG_CONFIRM_CUSCOMER_FAILURE";
/**
 * 只能删除出库类型为其它出库的4S店车辆
 */
public static final String MSG_ONLY_ALOW_DELETE_4S_VEHICLE="MSG_ONLY_ALOW_DELETE_4S_VEHICLE";
/**
 * 潜在客户级别已经发生变化，修改操作失败
 */
public static final String MSG_ONLY_ALOW_MODIFY_POTENTIAL_CUSTOMER="MSG_ONLY_ALOW_MODIFY_POTENTIAL_CUSTOMER";
/**
 * 同一二网订单中的车辆必须一次全部出库
 */
public static final String MSG_ONLY_ALOW_2S_VIN_ORDER_ONE_TIME_OUT="MSG_ONLY_ALOW_2S_VIN_ORDER_ONE_TIME_OUT";
/**
 * 同一二网订单中的车辆必须一次全部入库
 */
public static final String MSG_ONLY_ALOW_2S_VIN_ORDER_ONE_TIME_IN="MSG_ONLY_ALOW_2S_VIN_ORDER_ONE_TIME_IN";
/**
 * 车架号未入库的调拨申请单不得关单
 */
public static final String MSG_CAN_NOT_CLOSE_WITHOUT_STOCK_IN="MSG_CAN_NOT_CLOSE_WITHOUT_STOCK_IN";
}
