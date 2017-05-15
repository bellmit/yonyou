/**
 * 工单相关方法
 */
var repairOrder=function(){
	
	/**
	 * 根据品牌车型组代码查询车型代码集合
	 */
	var findModelLabourCodeAndModelCode = function(){
		dmsCommon.ajaxRestRequest({
			url:dmsCommon.getDmsPath()["repair"]+"/basedata/repairProject/findModelForInput",
			type:'GET',
			async:false,
			sucessCallBack:function(data){
				return data;
			}
		});
	}
	
	/**
	 * 通过车型代码查询品牌车型组代码
	 * code : 车型代码
	 */
	var findModelByModelLabourCode = function(code){
		var reg=new RegExp(code);
		$.each(findModelLabourCodeAndModelCode(),function(name,value) {
			if(reg.test(value)){
				$("#modelLabourCode",container).val(name);
			}
		});
	}
	
	/**
	 * 车牌号回车事件
	 */
	var licenseKeyUp = function(){
		dmsCommon.ajaxRestRequest({
			url : dmsCommon.getDmsPath()['repair']
					+ "/basedata/queryByLinsence/license",
			data : {
				'license' : $("#license",container).val()
			},
			sucessCallBack : function(data) {
				var roType = $("#roType",container).val();//工单类型
				/* var FTempMILEAGE = $("#inMileage",getElementContext()).val(); */
				//车主不是4S站 在本店库存表里能找到，没有做过购车费用开票 则不能做维修开单
				console.log(data);
				if (!isStringNull(data.OWNER_NO)
						&& data.OWNER_NO != '888888888888'
						&& data.OWNER_NO != '999999999999'
						&& !isStringNull(data.VIN)
						&& CheckVehicleInvoice(data.VIN)) {
					dmsCommon.tip({status:"warning",msg:"此车辆还没有做实销上报，不能开工单！"});
					return ;
				}
				if(!isStringNull(data.VIN)&&CheckIsHaveAduitingOrder(data.VIN)){
					dmsCommon.tip({status:"warning",msg:"此车辆已存在“待审核”的索赔工单，不能再开索赔工单！"});
					if(parseInt(roType)==12531004){//表示索赔工单类型
						return ;
					}
				}
				if(parseInt($("#isNewVehicle",container).val())==1){//表示从VIN页面点击新增的车辆
					//新建车辆也需要查看监控信息：车辆监控、预约单等信息   根据车牌号查询
					//打开监控信息页面    if not MonitorVehicleRemind(fcdsVehicle) then     3528
				}
				if(data.length>0){//表示能查到数据
					$("#isLoadVehicle",container).setDmsValue("true");
					//设置该车的换表里程和累计换表里程，方便计算
			        var FChangeMileage = 0;
			        var FToTalChangeMileage = data.TOTAL_CHANGE_MILEAGE;
			        var fLastMaintenancedate = data.LAST_MAINTENANCE_DATE;
			        var fLastRepairDate = data.LAST_MAINTAIN_DATE;
			        var fLastRepairMileage = data.LAST_MAINTAIN_MILEAGE;
			        var flastMaintenanceMileage = data.LAST_MAINTENANCE_MILEAGE;
			        var fModifySaledate = false;
			        if(parseInt(data.IS_SELF_COMPANY)==12781001&&data.SALES_DATE!=null&&data.SALES_DATE!=''){
			        	fModifySaledate = true;
			        }
			        //选择VIN号后，
			        //如果车牌号不为空 而且不是新建的车辆，那么去查询是否存在该车辆的在修工单。
			        var aVinTmp = data.VIN;
			        if(!isStringNull(aVinTmp)){
			        	if(QueryROByLicense(aVinTmp)){
			        		 //如果存在在修车辆，提示“此车牌存在在修车辆，是否继续新建工单？”
			        		obj.confirm('重复的维修组合选择，是否继续新增？',function(confirmObj){},function(confirmObj){
			        			return;
			        		});
			        	}
			        	SetVehicleInfo(data,false,false,false);
			        	//根据车牌号查询工单的监控信息。
			        }
				}else{//表示没查到数据
					//针对跨店维修的车辆-召回活动，监控信息也要显示
					var defaultValue=dmsCommon.getSystemParamInfo("3415","3415");//维修工单支持同步车主车辆
					if(parseInt(defaultValue)==12781001){
						obj.confirm('该车辆信息不存在，是否需要同步总部信息?',function(confirmObj){
							if(!isStringNull($("#license",container).val())&&$("#license",container).val().length!=17){
								dmsCommon.tip({status:"error",msg:"请在车牌号一栏输入准确的17位VIN码，按回车键从总部获取该车主车辆信息！"});
								return ;
							}
							//SEDMS023   根据$("#license",container).val()   后台调用接口
							dmsCommon.ajaxRestRequest({
								url : dmsCommon.getDmsPath()['repair']
										+ "/basedata/queryByLinsence/license",
								data : {
									'license' : $("#license",container).val()
								},
								sucessCallBack : function(data) {
									if(data.length>0){
										$("#isLoadVehicle",container).setDmsValue("true");
										$("#lastMaintenancedate",container).setDmsValue(data.LAST_MAINTENANCE_DATE);
										$("#toTalChangeMileage",container).setDmsValue(data.TOTAL_CHANGE_MILEAGE);
										$("#lastRepairDate",container).setDmsValue(data.LAST_MAINTAIN_DATE);
										$("#lastRepairMileage",container).setDmsValue(data.LAST_MAINTAIN_MILEAGE);
										$("#lastMaintenanceMileage",container).setDmsValue(data.LAST_MAINTENANCE_MILEAGE);
										/* if(parseInt(data.IS_SELF_COMPANY)==12781001&&!isStringNull(data.SALES_DATE)){
											
										} */
										//选择VIN号后，
							            //如果车牌号不为空 而且不是新建的车辆，那么去查询是否存在该车辆的在修工单。
							            if(!isStringNull(data.VIN)){
											//如果该车有会员卡，则更换图标
											QueryMemberCardExist(data.VIN)
											
											setVehicleInfo(data,false,false,false);
											//根据车牌号查询工单的监控信息。
											
							            }
									}else{
										dmsCommon.tip({status:"warning",msg:"该VIN在总部不存在，请核对输入的17位VIN码是否正确！"});
										return ;
									}
								}
							});
						},function(confirmObj){});
					}
				}
				if(data.length>0){
					if(!isStringNull(data.TRACE_TIME)){
						$("#traceTime",container).setDmsValue(data.TRACE_TIME);
					}else if($("#traceTime",container).attr("disabled")&&$("#traceTime",container).val()=="0"){
						$("#traceTime",container).setDmsValue(11251004);
					}
				}else{
					if($("#traceTime",container).attr("disabled")&&$("#traceTime",container).val()=="0"){
						$("#traceTime",container).setDmsValue(11251004);
					}
				}
				var tt = dmsCommon.getSystemParamInfo("1063","1063");//预交车时间为当前时间延迟xx小时
				var ttt = parseDouble(tt)*60*60*1000;
				var tttt = parseLong(new Date().getTime())+parseLong(ttt);
				$("#endTimeSupposed",container).setDmsValue(dateByType(tttt,"1"));//预交车时间
				$("#endTimeSupposed",container).setDmsValue(tttt);//预交车时间
				$("#calculate",container).click();//计算预计下次保修
			}
		});
	}
	
	/**
	 * 预计下次保养日期,下次保养里程计算相关方法
	 */
	var nextMaintain = function(){
		 var index = dmsCommon.getSystemParamInfo("1123","1123");//前台开关查询
		// 如果参数设置不计算则不处理.
		 if(parseInt(index)==12781002){
			 return;
		 }else{
			 var strTemp = '';//给remark2赋值 
			 var sysdate = new Date();//当前时间
			 var i = 0;//开关,预计下次保养日期距本工单最长天数
			 var j = 0;//开关,定期保养里程间隔
			 i = dmsCommon.getSystemParamInfo("1064","1064");//前台开关查询
			 j = dmsCommon.getSystemParamInfo("1072","1072");//前台开关查询
			 //日平均行驶里程
			 $("#averageMileage",container).val(0);
			 if($("#isLoadVehicle",container).val()=='true'){
				 dmsCommon.ajaxRestRequest({
					url:dmsCommon.getDmsPath()['repair']+"/order/repair/queryVehicleforactivity",
					data : {'vin':$("#vin",container).val()},
					sucessCallBack : function(data) {
						$("#lastMaintenanceDate",container).val(data.LAST_MAINTENANCE_DATE);
						$("#lastRepairDate",container).val(data.LAST_MAINTAIN_DATE);
						$("#lastRepairMileage",container).val(data.LAST_MAINTAIN_MILEAGE);
						$("#lastMaintenanceMileage",container).val(data.LAST_MAINTENANCE_MILEAGE);
						$("#averageMileage",container).val(data.DAILY_AVERAGE_MILEAGE);
					}
				 });
			 }
			 //本工单是保养类型的
			 //本次是保养，上次保养日期就用当前日期
			 var IsHaveMAINTAIN = false;
			 var repairPartRow = $("#dms_part",container).dmsTable().getRowDataByIndex();
			 var partNo = '';
			 $.each(repairPartRow,function(name,value){
				 partNo += value.PART_NO+",";
			 });
			 dmsCommon.ajaxRestRequest({
					url:dmsCommon.getDmsPath()['repair']+"/order/repair/getIsMaintenance",
					data : {'partNo':partNo.substr(0,partNo.length-1)},
					sucessCallBack : function(data) {
						if(data=='1'){
							IsHaveMAINTAIN = true;
						}
					}
			 });
			 if(IsHaveMAINTAIN){
				 $("#lastMaintainDate",container).val(sysdate.getTime());
			 }else{
				 //如果有上次保养日期就用上次保养日期
				 if($("#lastMaintenanceDate",container).val()!=null||$("#lastMaintenanceDate",container).val()!=''){
					 $("#lastMaintainDate",container).val($("#lastMaintenanceDate",container).val());//车辆表的上次保养日期;
				 }else{
					 //如果销售日期为空或销售日期+预计下次保养日期距本工单最长天数<当前日期就用当前日期作为上次保养日期
					 if(($("#salesDate",container).val()==""||$("#salesDate",container).val()==null)||(parseLong($("#salesDate",container).val())+parseInt(i)*86400000)<sysdate.getTime()){
						 $("#lastMaintainDate",container).val(sysdate.getTime());//车辆表的上次保养日期;
						 $("#lastMaintenanceMileage",container).val($("#outMileage",container).val());
					 }else{
						 $("#lastMaintainDate",container).val($("#salesDate",container).val());
					 }
				 }
			 }
			 if(($("#averageMileage",container).val()==0)&&$("#lastMaintainDate",container).val()!=''&&(sysdate.getTime()>parseLong($("#lastMaintainDate",container).val()))){
				 //没有日平均行程里程，用（出厂行驶里程－上次维修里程）/（当前日期－上次维修日期）
				 $("#averageMileage",container).val((parseInt($("#outMileage",container).val())-parseInt($("#lastRepairMileage",container).val()))/(parseLong(sysdate.getTime())-parseLong($("#lastMaintainDate",container).val())));
			 }
			 if(($("#averageMileage",container).val()==0)&&$("#lastMaintainDate",container).val()==''&&$("#salesDate",container).val()!=''&&(sysdate.getTime()>parseLong($("#salesDate",container).val()))){
				 $("#averageMileage",container).val(parseInt($("#outMileage",container).val())/(sysdate.getTime()-parseLong($("#salesDate",container).val())));
			 }
			 strTemp += '日均里程:'+$("#averageMileage",container).val();
			 strTemp += '间隔:'+j+'天数:'+i;
			 strTemp += '上保里程:'+$("#lastMaintenanceMileage",container).val()+'上保期'+$("#lastMaintainDate",container).val();
			 strTemp += '上维里程:'+$("#lastRepairMileage",container).val()+'上维期'+$("#lastRepairDate",container).val()+'出里'+$("#outMileage",container).val();
			 if($("#averageMileage",container).val()>0){
				 $("#nextMaintainMileage",container).val(parseInt(j)+parseInt($("#lastMaintenanceMileage",container).val()));
				 if(IsHaveMAINTAIN){
					 $("#nextMaintainMileage",container).val(parseInt(j)+parseInt($("#outMileage",container).val()));
				 }
				 if(parseInt($("#nextMaintainMileage",container).val())<parseInt($("#outMileage",container).val())){
					 $("#nextMaintainMileage",container).val($("#outMileage",container).val());
				 }
				 //（定期保养里程间隔/日平均行程里程）> 预计下次保养日期距本工单最长天数
				 if((parseInt(j)/parseInt($("#averageMileage",container).val()))>parseInt(i)){
					 $("#nextMaintainDate",container).val(parseLong($("#lastMaintainDate",container).val())+parseInt(i)*86400000);
				 }else{
					 $("#nextMaintainDate",container).val(parseLong($("#lastMaintainDate",container).val())+(parseInt(j)/parseInt($("#averageMileage",container).val()))*86400000);
				 }
				 //如果预计下次维修里程小于当前日期，则以当前日期为预计下次保养里程
				 if(parseLong($("#nextMaintainDate",container).val())<sysdate.getTime()){
					 $("#nextMaintainDate",container).val(sysdate.getTime());
				 }
				 strTemp += '下保里：'+Math.round((parseInt($("#nextMaintainMileage",container).val())/100)*100);
				 strTemp += '下保期：'+dateByType($("#nextMaintainDate",container).val(),'1');
				 $("#nextMaintainMileage",container).val((parseInt($("#nextMaintainMileage",container).val())/100)*100);
				 $("#nextMaintainDate",container).val(dateByType($("#nextMaintainDate",container).val(),'1'));
				 $("remark2",container).val(strTemp);
			 }else{
				 //无日平均行程里程应该用上次保养日期+预计下次保养日期距本工单最长天数
				 $("#nextMaintainMileage",container).val(parseInt(j)+parseInt($("#lastRepairMileage",container).val()));
				 if(IsHaveMAINTAIN){
					 $("#nextMaintainMileage",container).val(parseInt(j)+parseInt($("#outMileage",container).val()));
				 }
				 if(parseInt($("#nextMaintainMileage",container).val())<parseInt($("#outMileage",container).val())){
					 $("#nextMaintainMileage",container).val(parseInt($("#outMileage",container).val()));
				 }
				 $("#nextMaintainDate",container).val(parseLong($("#lastMaintainDate",container).val())+parseLong(i));
				 strTemp += '下保里：'+Math.round((parseInt($("#nextMaintainMileage",container).val())/100)*100);
				 $("#nextMaintainMileage",container).val((parseInt($("#nextMaintainMileage",container).val())/100)*100);
				 if($("#nextMaintainDate",container).val()!=''&&parseLong($("#nextMaintainDate",container).val())>sysdate.getTime()){
					 strTemp += '下保期：'+dateByType($("#nextMaintainDate",container).val(),'1');
					 $("#nextMaintainDate",container).val(dateByType($("#nextMaintainDate",container).val(),'1'));
				 }else{
					 strTemp += '下保期：'+dateByType(sysdate.getTime(),'1');
					 $("#nextMaintainDate",container).val(dateByType(sysdate.getTime(),'1'));
				 }
				 $("remark2",container).val(strTemp);
			 }
		 }
	}
	
	//根据车牌号查询是否存在在修工单，如果存在返回True，不存在返回False
	 var QueryROByLicense = function(vin){//QUERY_REPAIR_ORDER_EXISTS
		//只查在修工单
		var roStatus = 12551001;
		dmsCommon.ajaxRestRequest({
			url : dmsCommon.getDmsPath()['repair']
					+ "/order/repair/queryRepairOrderExists",
			data : {
				'vin' : vin,
				'roStatus' : roStatus
			},
			sucessCallBack : function(data) {
				if(data!=null){
					return true;
				}else{
					return false;
				}
			}
		});		 	
	 }
	 
	//品牌，车系，车型，VIN，发动机号，
		 //车主编号，车主性质，车主，
		 // 送修人，送修人性别，送修人电话区号，送修人电话，送修人手机，
		 // 累计换表里程，进厂行驶里程，是否换表，换表里程，
		 //上次维修日期，责任技师，业务接待，保险公司，
		 //上次行驶里程，颜色，下次保养里程，
		 //预计下次保养日期（车主车辆信息中预计下次保养日期大于当前系统日期则显示预计下次保养日期、下次保养里程）
		 //销售日期，上牌日期，日平均行驶里程
	 var setVehicleInfo = function(data,isVehicle,isVinEnter,isDeData){
		 isVehicle=false;
		 isVinEnter=false;
		 isDeData=false;
		 if(data!=null){
			//车牌号，品牌，VIN，发动机号，车系，车型，车身颜色，销售日期
			 if(!isStringNull(data.LICENSE)&&!isVinEnter){//点击VIN号是不返回车牌号。
				 $("#license",container).setDmsValue(data.LICENSE);
			 }else if(!isStringNull(data.LICENSE)&&isVinEnter){
				 //无牌照可以更新。可以直接在工单处更新车辆的信息。 (条件：牌照号为无牌照，且首次进厂）
				 if(data.LICENSE!='无牌照'||(!isStringNull(data.LAST_MAINTAIN_DATE)&&!isStringNull(data.LAST_MAINTAIN_DATE))){
					 $("#license",container).setDmsValue(data.LICENSE);
				 }
			 }
			 $("#brand",container).setDmsValue(data.BRAND);
			 $("#vin",container).setDmsValue(data.VIN);
			 $("#engineNo",container).setDmsValue(data.ENGINE_NO);
			 $("#series",container).setDmsValue(data.SERIES);
			 $("#model",container).setDmsValue(data.MODEL);
			 if(!isStringNull(data.APACKAGE)){
				 $("#configCode",container).setDmsValue(data.APACKAGE);
			 }
			 $("#color",container).setDmsValue(data.COLOR);
			 $("#salesDate",container).setDmsValue(data.SALES_DATE);
			 //车主编号，车主性质，车主，送修人姓名，送修人性别，送修人电话，送修人手机，上牌日期，
			 if(!isVehicle){
				 if(!isStringNull(data.OWNER_NO)){
					 $("#ownerNo",container).setDmsValue(data.OWNER_NO);
				 }
				 if(!isStringNull(data.OWNER_PROPERTY)){
					 $("#ownerProperty",container).setDmsValue(data.OWNER_PROPERTY);
				 }
				 if(!isStringNull(data.OWNER_NAME)){
					 $("#ownerName",container).setDmsValue(data.OWNER_NAME);
				 }
			 }
			 if(!isStringNull(data.DELIVERER)){
				 $("#deliverer",container).setDmsValue(data.DELIVERER);
			 }
			 if(!isStringNull(data.DELIVERER_GENDER)){
				 $("#delivererGender",container).setDmsValue(data.DELIVERER_GENDER);
			 }
			 if(!isStringNull(data.DELIVERER_PHONE)){
				 $("#delivererPhone",container).setDmsValue(data.DELIVERER_PHONE);
			 }
			 if(!isStringNull(data.DELIVERER_MOBILE)){
				 $("#delivererMobile",container).setDmsValue(data.DELIVERER_MOBILE);
			 }
			 if(data.DELIVERER.trim()==''&&parseInt(data.OWNER_PROPERTY)==11901002){
				 if(!isStringNull(data.OWNER_NAME)){
					 $("#ownerName",container).setDmsValue(data.OWNER_NAME);
				 }
				 if(!isStringNull(data.GENDER)){
					 $("#delivererGender",container).setDmsValue(data.GENDER);
				 }
				 if(!isStringNull(data.PHONE)){
					 $("#delivererPhone",container).setDmsValue(data.PHONE);
				 }
				 if(!isStringNull(data.MOBILE)){
					 $("#delivererMobile",container).setDmsValue(data.MOBILE);
				 }
			 }
			 if(!isStringNull(data.LICENSE_DATE)){
				 $("#licenseDate",container).setDmsValue(data.LICENSE_DATE);
			 }
			 if(!isStringNull(data.WRT_BEGIN_DATE)){
				 $("#wrtBeginDate",container).setDmsValue(data.WRT_BEGIN_DATE);//保险开始日期
			 }
			 
			 //进厂表上里程，出厂行驶里程，累计换表里程，上次换表日期，上次维修日期
			 //出厂行驶里程默认等于进厂表上里程。进厂表上里程从车辆的行驶里程
			 var value1 = dmsCommon.getSystemParamInfo("1180","1180");//前台开关查询
			 if(parseInt(value1)==12781001){
				 if(!isStringNull(data.MILEAGE)){
					 if(parseFloat(data.MILEAGE)>parseFloat($("#inMileage").val())){
						 $("#inMileage",container).setDmsValue(data.MILEAGE);
						 $("#outMileage",container).setDmsValue(data.MILEAGE);
					 }
				 }
			 }
			 if(!isStringNull(data.TOTAL_CHANGE_MILEAGE)){
				 $("#totalChangeMileage",container).setDmsValue(data.TOTAL_CHANGE_MILEAGE);//保险开始日期
			 }
			 if(!isStringNull(data.CHANGE_DATE)){
				 $("#changeDate",container).setDmsValue(data.CHANGE_DATE);//保险开始日期
			 }
			 if(!isStringNull(data.LAST_MAINTAIN_DATE)){
				 $("#lastMaintainDate",container).setDmsValue(data.LAST_MAINTAIN_DATE);//保险开始日期
			 }
			 //上次维修里程 上端更新的时候 没有处理到此出导致 计算日平均行使里程 有问题
			 if(isDeData){
				 if(!isStringNull(data.MILEAGE)){
					 $("#lastMaintainMileage",container).setDmsValue(data.MILEAGE);//保险开始日期
				 }
			 }
			 //指定技师
			 if(!isStringNull(data.CHIEF_TECHNICIAN)){
				 $("#chiefTechnician",container).setDmsValue(data.CHIEF_TECHNICIAN);
			 }
			 //如果没有载入服务专员。获取当前员工的操作员
			 if(isStringNull($("#serviceAdvisor",container).val())){
				 
			 }
			 //三日电访时间 如果车主信息中没有，那么默认为全天
			 if(!isStringNull(data.IS_TRACE)){
				 $("#isTrace",container).setDmsValue(data.IS_TRACE);//三日电访前复选框
			 }
			 if(parseInt($("#isTrace",container).val())==12781001){
				 if(!isStringNull(data.TRACE_TIME.trim())){
					 $("#TRACE_TIME",container).setDmsValue(data.TRACE_TIME);
				 }
			 }else{
				 $("#TRACE_TIME",container).setDmsValue("");
			 }
			 //下次保养里程、下次保养日期
			 if(!isStringNull($("#nextMaintainDate",container).val())&&!isStringNull(data.NEXT_MAINTAIN_DATE)){
				 $("#nextMaintainDate",container).setDmsValue(data.NEXT_MAINTAIN_DATE);
			 }
			 if(!isStringNull($("#nextMaintainMileage",container).val())&&!isStringNull(data.NEXT_MAINTAIN_MILEAGE)){
				 $("#nextMaintainMileage",container).setDmsValue(data.NEXT_MAINTAIN_MILEAGE);
			 }
			 //保险公司
			 if(!isStringNull($("#insurationCode",container).val())&&!isStringNull(data.INSURATION_CODE)){
				 if(HaveInsuration($("#repairType",contianer).val())){
					 var value = dmsDict.getSelectedOptionData($("#repairType",container)).IS_INSURANCE;
					 $("#insure",container).removeAttr("disabled");
					 if(parseInt(value)!=12781001){//是否保险字段
						 $("#insure",container).setDmsValue("");
					 }
				 }
			 }
			 //会员编号
			 if(!isStringNull($("#memberNo",container).val())&&!isStringNull(data.MEMBER_NO)){
				 $("#memberNo",container).setDmsValue(data.MEMBER_NO);
			 }
			 if(!isStringNull($("#productCode",container).val())&&!isStringNull(data.PRODUCT_CODE)){
				 $("#productCode",container).setDmsValue(data.PRODUCT_CODE);
			 }
			 //如果不存在product_code,查询上端车辆信息更新product_code
			 if(isStringNull(data.PRODUCT_CODE)){
				 return ;
			 }
			 if(data!=null){
				 var fOemTag = false;
				 if(!isStringNull($("#brand",container).val())&&parseInt(dmsDict.getSelectedOptionData($("#brand",container)).OEM_TAG)==12781001){
					 fOemTag = true;
				 }
				 if(fOemTag&&isStringNull(data.PRODUCT_CODE)&&parseInt(dmsCommon.getSystemParamInfo("1801","1801"))==12781001){
					 //更新本地车辆信息
					 GetVehicle($("#vin",container).val());
				 }
			 }
		 }
	 }
	 
	 /**
	  * 选择工单时确定按钮,查询工单明细
	  * selectItem 选中的那一行
	  */
	 var chooseRoNoInfo = function(roNo){
		 dmsCommon.ajaxRestRequest({
				url : dmsCommon.getDmsPath()['repair']
						+ "/order/repair/findOrderDetails",	
				data : {
					'roNo' : roNo
				},
				sucessCallBack : function(data) {
					if(data!=null){
						$("div[data-selectRepairOrder='true']",getElementContext()).initHtmlContent(data);
					}
				}
			});
	 }
	 
	//DCDSG0238	根据vin号查询车辆信息
	 var GetVehicle = function(vin){
		 dmsCommon.ajaxRestRequest({//查询接口
				url : dmsCommon.getDmsPath()['repair']
						+ "/order/repair/DCDSGO238",	
				data : {
					'vin' : vin
				},
				sucessCallBack : function(data) {
					if(data!=null){
						 $("#productCode",container).setDmsValue(item.PRODUCT_CODE);
					}
				}
			});
	 }
	 
	 var HaveInsuration = function(repairType){
		 //判断维修类型下拉框是否包含repairType,是返回true
		 var ret = false;
		 $("#repairType option").each(function () {
			 if(repairType==$(this).text()){
				 ret = true;
			 }
		 });
		 return ret;
	 }
	 
	//查询非4S站的车辆,在库存存在并且未开票的车
		var CheckVehicleInvoice = function(vin) {//QUERY_NO_INVOICE_VEHICLE
			dmsCommon.ajaxRestRequest({
				url : dmsCommon.getDmsPath()['repair']
						+ "/order/repair/checkVehicleInvoice",
				data : {
					'vin' : vin
				},
				sucessCallBack : function(data) {
					return data.length > 0;
				}
			});
		}
		
		//查询车辆方案状态为”等待审核“的工单
		var CheckIsHaveAduitingOrder = function(vin) {//QUERY_ADUITING_REPAIR_ORDER
			dmsCommon.ajaxRestRequest({
				url : dmsCommon.getDmsPath()['repair']
						+ "/order/repair/checkIsHaveAduitingOrder",
				data : {
					'vin' : vin
				},
				sucessCallBack : function(data) {
					return data.length > 0;
				}
			});
		}
		
		//如果该车有会员卡，则更换图标
		var QueryMemberCardExist = function(vin){//QUERY_MEMBER_CARD_BY_VIN
			dmsCommon.ajaxRestRequest({
				url : dmsCommon.getDmsPath()['repair']
						+ "/order/repair/queryMemberCardExist",
				data : {
					'vin' : vin
				},
				sucessCallBack : function(data) {
					$("#cardTypeCode").setDmsValue(data.CARD_TYPE_NAME);
					return data.length > 0;
				}
			});
		}
		
		var nullToZero = function(flag){
			return flag==null||flag==""?0:flag;
		}
	 
		 var dateByType = function(date,a){
			 if(isStringNull(a)){
				 var time = new Date(date);
				 return date.toLocaleDateString();
			 }else{
				 var time = new Date(date);
				 return time.getFullYear()+"/"+(time.getMonth()+1)+"/"+time.getDate()+" "+time.getHours()+":"+time.getMinutes()+":"+time.getSeconds();
			 }
		 }
	
	return {
		findModelLabourCodeAndModelCode : function(){
			findModelLabourCodeAndModelCode();
		},
		findModelByModelLabourCode : function(){
			findModelByModelLabourCode();
		},
		licenseKeyUp : function(){
			licenseKeyUp();
		},
		nextMaintain : function(){
			nextMaintain();
		},
		chooseRoNoInfo : function(roNo){
			chooseRoNoInfo(roNo);
		}
	};
}