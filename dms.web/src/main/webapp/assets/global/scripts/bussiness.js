/**
 * 配件模块相关功能
 */
var dmsPart = function() {
	var showOutPrice = function(priceType, container) {
		var price = "";
		// 销售价
		if (priceType == "13111001") {
			price = $("#salesPrice", container).val();
		}
		// 建议销售价
		if (priceType == "13111002") {
			price = $("#adviceSalePrice", container).val();
		}
		// 索赔价
		if (priceType == "13111003") {
			price = $("#claimPrice", container).val();
		}
		// 销售限价
		if (priceType == "13111004") {
			price = $("#limitPrice", container).val();
		}
		// 成本单价
		if (priceType == "13111005") {
			price = $("#costPrice", container).val();
		}
		$("#price", container).val(price);
		var priceTie = $("#priceTieShow", container).val();
		result = parseFloat(price) * parseFloat(priceTie);
		if (isNaN(result)) {
			result = price;
		}
		return result;
	};

	var initElementReadOnly = function(container) {
		// 文本输入框
		var inputArray = $(
				'input[type!="hidden"],textarea,select,a:not([data-goback],[data-dismiss],[data-toggle="tab"],.expand)',
				container);
		inputArray.each(function(index, item) {
			setElementReadOnly(item);
		});
	};
	var setElementReadOnly = function(element) {
		var obj = element;
		var isExcludeReadOnly = $(obj).attr("data-isExcludeReadOnly");
		// 如果是排除的元素，则不执行隐藏
		if (isExcludeReadOnly && isExcludeReadOnly == "true") {
			return;
		}
		// 移除required 属性
		if ($(obj).hasClass("required")) {
			$(obj).attr("data-removeClass", "required");
			$(obj).removeClass("required");
		}
		if ($(obj).is('select')) {
			if ($(obj).hasClass("bs-select")) {
				$(obj).attr("disabled", "disabled");
				$(obj).closest("div.bs-select").addClass("disabled");
				$("button", $(obj).closest("div.bs-select")).addClass(
						"disabled");
				$(obj).closest("div.bs-select").removeClass("required");
				$("span.bs-caret", $(obj).closest("div.bs-select")).hide();
			} else {
				$(obj).attr("disabled", "disabled");
			}
		} else if ($(obj).attr("type") == "checkbox") {
			$(obj).attr("disabled", "disabled");

		} else if ($(obj).attr("type") == "radio") {
			$(obj).attr("disabled", "disabled");

		} else if ($(obj).attr("type") == "text"
				|| $(obj).attr("type") == "hidden" || $(obj).is('textarea')) {
			if ($(obj).hasClass("ionRangeSlider")) {
				$(obj).data("ionRangeSlider").appendDisableMask();
			} else {
				$(obj).attr("disabled", "disabled");
				var objParentGroup = $(obj).parent("div.input-group");
				$(":not(input,textarea,span.input-group-addon)",
						$(obj).parent()).attr("disabled", "disabled");
			}
		} else if ($(obj).is('a')) {
			$(obj).attr("disabled", "disabled");
		} else if ($(obj).attr("type") == "file") {
			$(obj).attr("disabled", "disabled");
		}
	};

	//刷新移库页面的按钮
	var fushPageBtn = function(type,container){
		switch(type){
			case "init":
			$("#addBtn",container).removeAttr("disabled");
			$(".transferNo",container).removeAttr("disabled");
			$(".transferDate",container).removeAttr("disabled");
			break;
			case "addBtn":
			$("#saveBtn",container).removeAttr("disabled");
			$("#batchPartBtn",container).removeAttr("disabled");
			$("#cancelBtn",container).removeAttr("disabled");
			$("#addPartBtn",container).removeAttr("disabled");
			$("#transferNo",container).val("");
			break;
			case "cancelBtn":
			$(".transferNo",container).removeAttr("disabled");
			$(".transferDate",container).removeAttr("disabled");
			$("#addBtn",container).removeAttr("disabled");
			$("#batchSign",container).val("");
			break;
			case "searchBtn":
				$("#outStorgeBtn",container).removeAttr("disabled");
				$("#addPartBtn",container).removeAttr("disabled");
				$("#batchPartBtn",container).removeAttr("disabled");
				$("#saveBtn",container).removeAttr("disabled");
				$("#cancelBtn",container).removeAttr("disabled");
				$("#cancellationBtn",container).removeAttr("disabled");
			break;
			case "batchBtn":
				$("#batchPartBtn",container).attr("disabled","true");
				$("#addPartBtn",container).attr("disabled","true");
				break;
			case "saveBtn":
				$("#outStorgeBtn",container).removeAttr("disabled");
				$("#cancelBtn",container).removeAttr("disabled");
				$("#saveBtn",container).removeAttr("disabled");
				$("#addPartBtn",container).removeAttr("disabled");
				$("#cancellationBtn",container).removeAttr("disabled");
				break;
			case "outStorageBtn":
				$("#addBtn",container).removeAttr("disabled");
				$("#cancelBtn",container).removeAttr("disabled");
				$("#printBtn",container).removeAttr("disabled");
				break;
			case "resetType":
				$(".transferNo",container).attr("disabled","true");
				$(".transferDate",container).attr("disabled","true");
				$("#addBtn",container).attr("disabled","true");
				$("#outStorgeBtn",container).attr("disabled","true");
				$("#addPartBtn",container).attr("disabled","true");
				$("#saveBtn",container).attr("disabled","true");
				$("#cancelBtn",container).attr("disabled","true");
				$("#cancellationBtn",container).attr("disabled","true");
				$("#printBtn",container).attr("disabled","true");
				$("#batchPartBtn",container).attr("disabled","true");
				$("#delJsonStr",container).val("");
				break;
		}
	}

	var removeElementsReadOnly = function(container, filterFunction) {
		// 文本输入框
		var inputArray = $(
				'input[type!="hidden"],textarea,select,a:not([data-goback],[data-dismiss],[data-toggle="tab"],.expand)',
				container);
		// 设置过滤函数
		if (filterFunction) {
			inputArray = inputArray.filter(filterFunction);
		}
		// 循环过滤
		inputArray.each(function(index, item) {
			removeElementReadOnly(item);
		});
	};
	var removeElementReadOnly = function(element) {
		var obj = element;
		var isExcludeReadOnly = $(obj).attr("data-isExcludeReadOnly");
		// 如果是排除的元素，则不执行隐藏
		if (isExcludeReadOnly && isExcludeReadOnly == "true") {
			return;
		}
		// 移除required 属性
		// 移除required 属性
		if ($(obj).attr("data-removeClass")) {
			$(obj).addClass($(obj).attr("data-removeClass"));
			$(obj).removeAttr("data-removeClass");
		}
		if ($(obj).is('select')) {
			if ($(obj).hasClass("bs-select")) {
				$(obj).removeAttr("disabled");
				$(obj).closest("div.bs-select").removeClass("disabled");
				$("button", $(obj).closest("div.bs-select")).removeClass(
						"disabled");
			} else {
				$(obj).removeAttr("disabled");
			}
		} else if ($(obj).attr("type") == "checkbox") {
			$(obj).removeAttr("disabled");

		} else if ($(obj).attr("type") == "radio") {
			$(obj).removeAttr("disabled");

		} else if ($(obj).attr("type") == "text"
				|| $(obj).attr("type") == "hidden" || $(obj).is('textarea')) {
			$(obj).removeAttr("disabled");
			// 隐藏按钮
			$(":not(input)", $(obj).parent()).removeAttr("disabled");

		} else if ($(obj).is('a')) {
			$(obj).removeAttr("disabled");
		}
	}

	return {
		sample : function(priceType, container) {
			return showOutPrice(priceType, container);
		},
		initElementReadOnly : function(container) {
			return initElementReadOnly(container);
		},
		removeElementsReadOnly : function(container, filterFunction) {
			return removeElementsReadOnly(container, filterFunction);
		},
		fushPageBtn:function(type,container){
					return fushPageBtn(type,container);
				}


	};
}();

/**
 * 维修模块相关功能
 */
var dmsRepair = function() {
	// 索赔类工单必须维修项目的故障原因必须填写，并且维修材料必须关联到维修项目
	var checkClaimOrder = function(container, returnResult, repairItem) {
		var roType = $("#roType", container).val();
		if (roType == '12531004') {// 索赔
			if (IsValueExist(repairItem, "DOWN_TAG", "12781002")
					|| IsValueExist(repairItem, "DOWN_TAG", "")) {
				dmsCommon.tip({
					status : "warning",
					msg : "该VIN在总部不存在，请核对输入的17位VIN码是否正确！"
				});
				returnResult.status = false;
			}
			$.each(repairItem, function(i, j) {
				if (isStringNull(j.TROUBLE_CAUSE)) {
					dmsCommon.tip({
						status : "warning",
						msg : "工单类型为索赔时，维修项目明细中的故障原因必须填写"
					});
					returnResult.status = false;
				}
			});
		}
	}

	/**
	 * 判断值是否存在 item 数据/JSON数据 field 字段 val 值
	 */
	var IsValueExist = function(item, field, val) {
		var flag = false;
		$.each(item, function(i, j) {
			if (j.field == val) {
				flag = true;
			}
		});
		return flag;
	}

	// 未结算索赔工单查询页面 加载维修类型下拉框
	var initRepairType1 = function(container) {
		var selectData = new Array();
		selectData.push({
			id : "SCBY",
			name : "首次保养"
		});
		selectData.push({
			id : "BXIU",
			name : "保修"
		});
		selectData.push({
			id : "SHYX",
			name : "善意维修"
		});
		selectData.push({
			id : "SBWS",
			name : "三包维修"
		});
		selectData.push({
			id : "YXYX",
			name : "营销活动"
		});
		selectData.push({
			id : "FUWU",
			name : "服务活动"
		});
		selectData.push({
			id : "SQWX",
			name : "PDI检查"
		});
		dmsDict.refreshSelectByData($("#repairTypeCode", container),
				selectData, "id", "name");
	}
	// 未结算索赔工单查询页面 加载日期类型
	var initQueryDateType = function(container) {
		var selectData = new Array();
		selectData.push({
			id : "RO_CREATE_DATE",
			name : "工单开单日期"
		});
		selectData.push({
			id : "FOR_BALANCE_TIME",
			name : "提交结算日期"
		});
		selectData.push({
			id : "COMPLETE_TIME",
			name : "竣工日期"
		});
		selectData.push({
			id : "DELIVERY_DATE",
			name : "交车日期"
		});
		selectData.push({
			id : "BALANCE_CLOSE_TIME",
			name : "关单日期"
		});
		selectData.push({
			id : "BALANCE_TIME",
			name : "结算时间"
		});
		selectData.push({
			id : "PRINT_RO_TIME",
			name : "工单打印时间"
		});
		selectData.push({
			id : "PRINT_BALANCE_TIME",
			name : "结算单打印时间"
		});
		dmsDict.refreshSelectByData($("#queryDateType", container), selectData,
				"id", "name");
	}

	var CheckRepairLabourCodeList = function(repairLabourCode, repairItem, repairPart) {
		$.each(repairItem,
				function(i, j) {
					if (!isStringNull(j.LABOUR_CODE)
							&& j.NEEDLESS_REPAIR != '12781001') {
						// 委外的项目不可选择
						if (isStringNull(j.CONSIGN_EXTERIOR)
								|| j.CONSIGN_EXTERIOR != "12781001") {
							var child = {};
							child[LABOUR_CODE] = j.LABOUR_CODE;
							child[LABOUR_NAME] = j.LABOUR_NAME;
							child[NEEDLESS_REPAIR] = j.NEEDLESS_REPAIR;
							repairLabourCode.push(child);
						}
					}
				});
		$.each(repairPart, function(i, j) {
			var flag = false;
			$.each(repairLabourCode, function(k, l) {
				if (j.LABOUR_CODE == l.LABOUR_CODE) {
					flag = true;
				}
			});
			if (!flag) {
				j.LABOUR_CODE = '';
				j.LABOUR_NAME = '';
			}
		});
	}

	var iniInfo = [];// 预警信息JSON数据

	/**
	 * 保存按钮校验
	 */
	var checkSave = function(obj, container, returnResult) {
		if (isStringNull($("#checkFlag", container).val())
				|| $("#checkFlag", container).val() == '') {
			var activityItem = new Array();// 表示还未参加的服务活动编号
			var repairItem = $("#dms_table", container).dmsTable()
					.getRowDataByIndex();
			var repairPart = $("#dms_part", container).dmsTable()
					.getRowDataByIndex();
			var repairItemCount = $("#dms_table", container).dmsTable()
					.getTableRows();
			var repairPartCount = $("#dms_part", container).dmsTable()
					.getTableRows();
			var addItem = $("#dms_subjoinItem", container).dmsTable()
					.getRowDataByIndex();
			var repairLabourCode = [];
			checkClaimOrder(container, returnResult, repairItem);
			CheckRepairLabourCodeList(repairLabourCode, repairItem, repairPart);
			if ($("#ownerNo", container).val() == "888888888888") {
				if ($("#repairType", container).val() != "SQWX") {
					dmsCommon.tip({
						status : "warning",
						msg : "售前维修的车辆，开单时维修类型只能选择PDI检查！"
					});
					returnResult.status = false;
				}
			}
			// 校验维修类型为必填
			var index = dmsCommon.getSystemParamInfo("9006", "9006");// 前台开关查询
			$.each(repairItem, function(i, j) {
				if (isStringNull(j.LABOUR_NAME)) {
					if (j.LABOUR_CODE == 'G9999999') {
						dmsCommon.tip({
							status : "warning",
							msg : "通用项目名称不能为空！"
						});
						returnResult.status = false;
					}
				}
				if (index == '12781001') {
					if (isStringNull(j.REPAIR_TYPE_CODE)) {
						dmsCommon.tip({
							status : "warning",
							msg : "维修项目 维修类型为必填"
						});
						returnResult.status = false;
					}
				}
			});
			// 检验交车规范
			// 因开关关闭,不做0.0
			index = dmsCommon.getSystemParamInfo("1160", "1160");// 前台开关查询
			if (index == '12781001') {
				// 检验派工规范
				checkPaiGongGuiFan(repairItem, returnResult);
				// 检验中断规范
				CheckZhongDuanGuiFan(returnResult);
			}
			// ****************************************************************************
			// 索赔单，故障发生日期，必填
			if ($("#roType", container).val() != "12531004") {
				if (dmsCommon.getSystemParamInfo("8089", "8089") == '12781001') {
					if (isStringNull($("#troubleOccurDate", container).val())) {
						dmsCommon.tip({
							status : "warning",
							msg : "索赔工单 故障发生日期必填"
						});
						returnResult.status = false;
					}
				}
			}
			// 索赔单服务活动控制
			if (dmsCommon.getSystemParamInfo("3340", "3340") == '12781001'
					&& $("#roType", container).val() != "12531004") {
				var oemaActivityCount = 0;
				var activityCount = 0;
				var activityCount = CountActivity(repairItem, repairPart); // 活动数量
				var oemaActivityCount = CountOemActivity(repairItem, repairPart);// 下发活动数量
				// 只能选择一个下发活动
				if (oemaActivityCount > 1) {
					dmsCommon.tip({
						status : "warning",
						msg : "索赔单不允许选择多个下发活动"
					});
					returnResult.status = false;
				} else if (oemaActivityCount == 1) {
					// 不能有其他项目和配件
					if (HaveOtherItem(repairItem) && HaveOtherPart(repairPart)) {
						dmsCommon.tip({
							status : "warning",
							msg : "索赔单选择了下发活动不允许添加其他项目和配件"
						});
						returnResult.status = false;
					}
					// 不能选择非下发的活动
					if (activityCount > oemaActivityCount) {
						dmsCommon.tip({
							status : "warning",
							msg : "索赔单不允许添加非下发的服务活动"
						});
						returnResult.status = false;
					}
				}
			}
			var fcdsLABOUR = {};
			var fcdsOEM = {};
			dmsCommon.ajaxRestRequest({
				url : dmsCommon.getDmsPath()["repair"]
						+ "/basedata/queryByLinsence/queryIsRestrict",
				type : 'GET',
				async : false,
				sucessCallBack : function(data) {
					fcdsLABOUR = data;
				}
			});
			if (fcdsLABOUR.IS_RESTRICT == '12781001'
					&& dmsCommon.getSystemParamInfo("9008", "9008") == '12781001'
					&& new Date(new String($("#createDate", container).val()))
							.getTime() > new Date(new String(dmsCommon
							.getSystemParamInfo("9018", "9018"))).getTime()) {
				dmsCommon.ajaxRestRequest({
					url : dmsCommon.getDmsPath()["repair"]
							+ "/basedata/queryByLinsence/queryOEMTAG" + "/"
							+ $("#vin", container).val(),
					type : 'GET',
					async : false,
					sucessCallBack : function(data) {
						fcdsOEM = data;
					}
				});
			}
			var aLabourCode = "";
			var aModelLabourCode = "";
			if (fcdsLABOUR.IS_RESTRICT == '12781002'
					&& dmsCommon.getSystemParamInfo("9008", "9008") == '12781001'
					&& new Date(new String($("#createDate", container).val()))
							.getTime() > new Date(new String(dmsCommon
							.getSystemParamInfo("9018", "9018"))).getTime()) {
				if (fcdsOEM.BRAND == 'QT') {
					if (fcdsLABOUR.IS_OTHER == '12781001') {
						$.each(repairItem, function(i, j) {
							aLabourCode = aLabourCode + "," + j.LABOUR_CODE;
							aModelLabourCode = aModelLabourCode + ","
									+ j.MODEL_LABOUR_CODE;
						});
						dmsCommon.ajaxRestRequest({
							url : dmsCommon.getDmsPath()["repair"]
									+ "/basedata/queryByLinsence/queryOEMTAG"
									+ "/" + $("#vin", container).val(),
							type : 'GET',
							data : {
								'labourCode' : aLabourCode,
								'modelLabourCode' : aModelLabourCode
							},
							async : false,
							sucessCallBack : function(data) {
								if (data == '12781002') {
									dmsCommon.tip({
										status : "warning",
										msg : "非行管经销商只能选择下发的维修项目！"
									});
									returnResult.status = false;
								}
							}
						});
					}
				} else {
					$.each(repairItem, function(i, j) {
						aLabourCode = aLabourCode + "," + j.LABOUR_CODE;
						aModelLabourCode = aModelLabourCode + ","
								+ j.MODEL_LABOUR_CODE;
					});
					dmsCommon.ajaxRestRequest({
						url : dmsCommon.getDmsPath()["repair"]
								+ "/basedata/queryByLinsence/queryLabourCode",
						type : 'GET',
						data : {
							'labourCode' : aLabourCode,
							'modelLabourCode' : aModelLabourCode
						},
						async : false,
						sucessCallBack : function(data) {
							if (data == '12781002') {
								dmsCommon.tip({
									status : "warning",
									msg : "非行管经销商只能选择下发的维修项目！"
								});
								returnResult.status = false;
							}
						}
					});
				}
			}
			// 计算预计下次保养日期
			if (dmsCommon.getSystemParamInfo("1074", "1074") == '12781001') {
				nextMaintain(container);
			}
			// 如果本地存在工单号则为编辑工单，执行UpdateRepairOrder，如果没，执行新增工单AddRepairOrder；
			// 工单服务活动必填检验逻辑
			var aModel = '';
			var brand = '';
			var series = '';
			var configCode = '';
			var salesdate = '';
			var mileage = '';
			var i = 0;
			var j = 0;
			var k = 0;
			var listActCod = GetActivityCodes2(repairItem, repairPart, addItem);
			// 新增开关：是否必选下发服务活动
			if (dmsCommon.getSystemParamInfo("3421", "3421") == '12781001') {
				if (!isStringNull($("#model", container).val()))
					aModel = $("#model", container).val();
				if (!isStringNull($("#brand", container).val()))
					brand = $("#brand", container).val();
				if (!isStringNull($("#series", container).val()))
					series = $("#series", container).val();
				if (!isStringNull($("#config", container).val()))
					configCode = $("#config", container).val();
				if (!isStringNull($("#salesDate", container).val()))
					salesdate = $("#salesDate", container).val();
				if (!isStringNull($("#toTalChangeMileage", container).val()))
					i = parseInt($("#toTalChangeMileage", container).val());
				if (!isStringNull($("#inMileage", container).val()))
					i = i + parseInt($("#inMileage", container).val());
				mileage = new String(i);
				activityItem = GetAllEnterableActivityInfo(
						$("#roNo", container).val(), $("#license", container)
								.val(), $("#vin", container).val(), aModel,
						brand, series, salesdate, mileage, $("#cardTypeCode",
								container).val(), configCode);
				if (!isEmpty(activityItem)) {
					var choose = false;
					if (listActCod.split(',').length > 0) {
						for (var i = 0; i < activityItem.length; i++) {
							if (listActCod.split(',').indexOf(activityItem[i]) == -1) {// 表示未参加此服务活动
								choose = true;
							}
						}
						if (choose) {
							dmsCommon.tip({
								status : "warning",
								msg : "该车辆存在必选的服务活动，请点击【服务活动】选择！"
							});
							returnResult.status = false;
						}
					} else {
						dmsCommon.tip({
							status : "warning",
							msg : "该车辆存在必选的服务活动，请点击【服务活动】选择！"
						});
						returnResult.status = false;
					}
				}
			}
			SetModifyNum(repairItem, repairItemCount, repairPart,
					repairPartCount, container);
		}
		if (CheckTripleInfo(container)) {
			/*
			 * { 0 代表 不需要预警。 1 代表 当前经销商是授权经销商。 2 代表 当前经销商不是授权经销商。 3 代表
			 * 当前车辆不属于三包车辆。 }
			 */
			switch (parseInt(GetTripleInfoFromDCS($("#vin", container).val(),
					repairPart))) {
			case 0:
				$("#schemeStatus", container).setDmsValue("22021006");// 方案状态“未达预警”
				break;
			case 1:
				if (isStringNull($("#checkFlag", container).val())
						|| $("#checkFlag", container).val() == '') {// 表示第一次进方法,需要校验权限
					// 如果当前用户没有授权权限，那么需要获取到别人的权限
					if (!dmsCommon.getBusinessPurview("10470000")) {
						$("#3BaoFlag", container).setDmsValue("3bao");
						// 获取别的用户的权限。如果获取不成功，那么就不能保存
						$("#discountAuthorizeBtn", container).click();
						returnResult.status = false;
					}
				}
				$("#checkFlag", container).setDmsValue("");
				$("#schemeStatus", container).setDmsValue("22021005");// 方案状态“授权维修”
				break;
			case 2:
				// 当前经销商不是授权经销商，要生成方案时，当前DMS的提示不友好，需修改为"该车已经达到警戒标准，请联系厂方解锁"
				if (isStringNull($("#checkFlag", container).val())
						|| $("#checkFlag", container).val() == '') {// 表示第一次进方法,需要校验权限
					obj.confirm('该车已经达到警戒标准，请联系厂方解锁！', function(confirmObj) {
					}, function(confirmObj) {
						returnResult.status = false;
					});
					$("#clOpinion", container).click();
					returnResult.status = false;
				}
				$("#checkFlag", container).setDmsValue("");
				$("#schemeStatus", container).setDmsValue("22021001");// 方案状态“授权维修”
				break;
			default:
				$("#schemeStatus", container).setDmsValue("0");
				break;
			}
		} else {
			$("#schemeStatus", container).setDmsValue("0");
		}
		if (isStringNull($("#roNO", container).val())) {
			$(this).attr("data-method", "PUT");
			$(this).attr("data-url", "/order/repair/btnSaveForAdd");
		} else {
			$(this).attr("data-method", "POST");
			$(this).attr("data-url", "/order/repair/btnSaveForUpdate");
		}
		// 出险单相关
		occurInsuranceAbout($("#insuranceNo", container).val(), $(
				"#occurInsuranceNo", container).val(), $("#vin", container)
				.val(), $("#roNo", container).val(), returnResult);
		if (!isStringNull($("#IActivityRo", container).val())
				&& !isStringNull($("#roNo", container).val())) {
			activityResult($("#vin", container).val(), $("#roNo", container)
					.val());
		}
		if (!isStringNull($("#roNo", container).val())
				&& !isStringNull($("#insuranceNo", container).val())
				&& !isStringNull($("#occurInsuranceNo", container).val())) {
			var insuranceNo = $("#insuranceNo", container).val();
			if (insuranceNo.length > 194)
				insuranceNo = insuranceNo.substr(0, 194);
			dmsCommon.ajaxRestRequest({
				url : dmsCommon.getDmsPath()["repair"]
						+ "/order/repair/saveSettlementOldpart",
				type : 'GET',
				data : {
					'roNo' : $("#roNo", container).val(),
					'occurInsuranceNo' : insuranceNo
				},
				async : false,
				sucessCallBack : function(data) {
				},
				errorCallBack : function(data) {
					dmsCommon.tip({
						status : "error",
						msg : "查询失败!"
					});
					returnResult.status = false;
				}
			});
		}
		$("#save", container).attr('disabled', 'disabled');
		returnResult.status = true;
	}

	// 为空判断
	function isEmpty(value) {
		return (Array.isArray(value) && value.length === 0)
				|| (Object.prototype.isPrototypeOf(value) && Object.keys(value).length === 0);
	}

	// 工单号回车事件
	var roNoKeyUp = function(obj, container) {
		$("#openRoNo", container).click();
	}

	// vin号回车事件
	// VIN号直接回车，只有是新建车辆的时候VIN号才可以编辑，其他时候都是点击？选择VIN号。
	// 回车的时候执行DE，去OEM查询车辆信息。
	var vinKeyUp = function(obj, container) {
		alert(1)
		if (isStringNull($("#vin", container).attr("disabled"))
				|| $("#vin", container).attr("disabled") != "disabled") {
			// 从本地查找车辆信息。如果没有通过DE调用OEM的车辆信息。
			dmsCommon.ajaxRestRequest({
				url : dmsCommon.getDmsPath()["repair"]
						+ "/order/repair/queryVinByVin",
				type : 'GET',
				data : {
					'vin' : $("#vin", container).val(),
				},
				async : false,
				sucessCallBack : function(data) {
					if (data.length == 1) {
						// 接受返回的车辆信息。 need to do DE 设置afcds的字段。否则不能获取到字段值。
						SetVehicleInfo(data, false, true, false);
					}
				},
				errorCallBack : function() {
					dmsCommon.tip({
						status : "error",
						msg : "查询失败!"
					});
				}
			});
		}
	}

	function activityResult(vin, roNo) {
		dmsCommon.ajaxRestRequest({
			url : dmsCommon.getDmsPath()["repair"]
					+ "/order/repair/queryActivityResult",
			type : 'GET',
			data : {
				'vin' : vin,
				'activityRo' : roNo
			},
			async : false,
			sucessCallBack : function(data) {
				return data;
			},
			errorCallBack : function(data) {
				dmsCommon.tip({
					status : "error",
					msg : "查询失败!"
				});
			}
		});
	}

	// 工单保存成功后，如果该工单关联了出险信息，则将工单号 保存到所关联的出险信息中,并将出险单的跟踪状态更新为‘已来厂’
	// 另外还要将该出险单的处理状态更新为‘维修中’
	function occurInsuranceAbout(no1, no2, vin, roNo, returnResult) {
		dmsCommon.ajaxRestRequest({
			url : dmsCommon.getDmsPath()["repair"]
					+ "/order/repair/occurInsuranceAbout",
			type : 'GET',
			data : {
				'no1' : no1,
				'no2' : no2,
				'vin' : vin,
				'roNo' : roNo
			},
			async : false,
			sucessCallBack : function(data) {
			},
			errorCallBack : function(data) {
				dmsCommon.tip({
					status : "error",
					msg : "查询失败!"
				});
				returnResult.status = false;
			}
		});
	}

	/*
	 * 功能说明： b）满足三包条件之后，需要开发一个同步接口来到DCS系统中查询三包预警。对返回的预警结果进行如下判断：
	 * b1）如果返回的预警结果达到三包预警级别，那么判断当前经销商是否授权。 如果当前经销商是授权，那么判断当前用户是否有授权开单？
	 * b1a)如果有权限，那么直接保存当前工单（方案状态为"授权维修"）。
	 * b1b)如果没有权限，那么弹出授权窗口。登录已有此权限的用户，来获取到此权限。 成功得到权限之后，可以直接保存当前工单（方案状态为"授权维修"）。
	 * 如果当前经销商是没有授权，那么进行如下判断是否要生成预警方案？ b11）如果不生成方案，那么不保存，继续编辑。
	 * b12）如果生成方案，那么保存工单（方案状态为"等待审核"），同时上报工单以及其明细信息。 返回函数值： 0 代表 不需要预警。 1 代表
	 * 当前经销商是授权经销商。 2 代表 当前经销商不是授权经销商。 3 代表 当前车辆不属于三包车辆。
	 */
	function GetTripleInfoFromDCS(vin, repairPart) {
		dmsCommon.ajaxRestRequest({
			url : dmsCommon.getDmsPath()["repair"]
					+ "/order/repair/getTripleInfo",
			type : 'GET',
			data : {
				'vin' : vin,
				'activityCode' : findActivityCode(repairPart),
				'partNo' : findPartNo(repairPart)
			},
			async : false,
			sucessCallBack : function(data) {
				if (data != null) {
					iniInfo = data.ttTripleInfo;
					$("#pre3BaoWarm", container).click();
					return data.tripleResult;
				}
			}
		});
		return "999";
	}

	/*
	 * 弹出预警信息表格初始化
	 */
	function initPre3BaoWarm(srca) {
		new Datatable().initPagination({
			src : srca,
			data : iniInfo,
			rowID : "PART_CODE",
			sortName : "PART_CODE",
			sortOrder : "asc",
			autoHeight : false,
			undefinedText : "",
			columns : [ {
				field : "PART_CODE",
				title : "零件编码"
			}, {
				field : "PTMUM",
				title : "零件次数"
			}, {
				field : "WARN_ITEM_NAME",
				title : "项目"
			}, {
				field : "WARNTIMES",
				title : "项目次数/总天数"
			}, {
				field : "WARN_STANDARD",
				title : "预警次数"
			}, {
				field : "LEGAL_STANDARD",
				title : "法定标准"
			}, {
				field : "YELLOW_STANDARD",
				title : "黄色标准"
			}, {
				field : "ORANGE_STANDARD",
				title : "橙色标准"
			}, {
				field : "RED_STANDARD",
				title : "红色标准"
			} ]
		});
	}

	function findActivityCode(repairPart) {
		var code = "";
		$.each(repairPart, function(i, j) {
			code = code + "," + j.ACTIVITY_CODE;
		});
		return code;
	}

	function findPartNo(repairPart) {
		var code = "";
		$.each(repairPart, function(i, j) {
			code = code + "," + j.PART_NO;
		});
		return code;
	}

	var CheckTripleInfo = function(container) {
		if (!checkBrandIsOem(container)) {
			return false;
		}
		if (isStringNull($("#salesDate", container).val())) {
			return false;
		}
		// 1、三包预警开关
		// 2、是否索赔工单
		if (dmsCommon.getSystemParamInfo("1180", "1180") == '12781002'
				&& $("#roType", container).val() != "12531004") {
			return false;
		}
		// 几种维修类型 不做三包预警 首次保养 常规保养 定期保养
		var repairType = $("#repairType", container).val();
		if (repairType == 'SCBY' || repairType == 'CGBY'
				|| repairType == 'DQBY') {
			return false;
		}
		// 进厂里程和当前日期是否在三包期内
		if (!checkRule(container)) {
			return false;
		}
		return true;
	}
	var checkRule = function(container) {
		var v_fDateDis = 0;
		var v_fMiliageDis = 0.0;
		var deDate = $("#salesDate", container).val();
		var afMilage = $("#inMileage", container).val();
		var v_sCurr = new Date();
		if (!isStringNull(dmsCommon.getSystemParamInfo("1903", "1903"))) {
			v_fDateDis = parseInt(dmsCommon.getSystemParamInfo("1903", "1903"));
		} else {
			v_fDateDis = 2;
		}
		if (!isStringNull(dmsCommon.getSystemParamInfo("1904", "1904"))) {
			v_fMiliageDis = parseInt(dmsCommon.getSystemParamInfo("1904",
					"1904"));
		} else {
			v_fMiliageDis = 50000;
		}
		var ddate = new Date(deDate);
		var dyear = ddate.getFullYear();
		ddate.setFullYear(dyear + v_fDateDis);
		if ((new Date(deDate).getTime() <= v_sCurr.getTime())
				&& (v_sCurr.getTime() <= ddate.getTime())
				&& (afMilage <= v_fMiliageDis)) {
			return true;
		} else {
			return false;
		}
	}

	var checkBrandIsOem = function(container) {
		// 非OEM品牌不校验三包规则
		var text = dmsDict.getSelectedOptionData($("#brand", container)).OEM_TAG;
		if (text == '12781001') {
			return true;
		} else {
			return false;
		}
	}

	var SetModifyNum = function(repairItem, repairItemCount, repairPart,
			repairPartCount, container) {
		// 查询维修项目和维修配件是否发生了值的改变
		if (parseInt(repairItemCount) != parseInt(repairItem.length)
				|| parseInt(repairPartCount) != parseInt(repairPart.length)) {
			if (!isStringNull($("#printRoTime", container).val())) {
				$("#modifyNum", container).setDmsValue(
						parseInt($("#modifyNum", container).val()) + 1);
				return;
			}
		}
		// 判断维修项目和维修配件
		if (checkItemIsModify(repairItem) || checkItemIsModify(repairPart)) {
			$("#modifyNum", container).setDmsValue(
					parseInt($("#modifyNum", container).val()) + 1);
			return;
		}
	}

	// 工单判断表格字段是否发生变化
	var checkItemIsModify = function(item) {
		var result = false;
		var flags = new Array();
		flags.push("A");
		flags.push("U");
		$.each(item, function(i, j) {
			if (flags.indexOf(j.rowKey) > 0) {// 表示做了修改
				result = true;
			}
		});
		return result;
	}

	// 工单保存获取必须参加还没参加的所有服务活动编号
	var GetAllEnterableActivityInfo = function(aRoNo, aLicense, aVin, aModel,
			brand, series, salesdate, mileage, cardTypeCode, configCode) {
		var infoItem = new Array();// 获取可参加的服务活动数组(field只有一个活动编号)
		var couldItem = new Array();// 获取还未参加的可参加的服务活动数组(field只有一个活动编号)
		var Enterable = null;// 可参加的服务活动
		var isable = null;// 已经参加的服务活动
		dmsCommon.ajaxRestRequest({
			url : dmsCommon.getDmsPath()["repair"]
					+ "/order/repair/getAllEnterableActivityInfo",
			type : 'GET',
			data : {
				'aRoNo' : aRoNo,
				'aLicense' : aLicense,
				'aVin' : aVin,
				'aModel' : aModel,
				'brand' : brand,
				'series' : series,
				'salesdate' : salesdate,
				'mileage' : mileage,
				'cardTypeCode' : cardTypeCode,
				'configCode' : configCode
			},
			async : false,
			sucessCallBack : function(data) {
				if (data != null) {
					Enterable = data;
				}
			},
			errorCallBack : function(data) {
				dmsCommon.tip({
					status : "error",
					msg : "查询失败!"
				});
				returnResult.status = false;
			}
		});
		// 查询参加的活动,null表示没有参加活动
		/*
		 * dmsCommon.ajaxRestRequest({ url : dmsCommon.getDmsPath()["repair"] +
		 * "", type : 'GET', data : { }, async : false, sucessCallBack :
		 * function(data) { if(data!=null){ isable = data; } }, errorCallBack :
		 * function(data){ dmsCommon.tip({ status : "warning", msg : data });
		 * returnResult.status = false; } });
		 */
		if (Enterable != null) {
			$.each(Enterable, function(i, j) {
				// 活动性质必填,下发
				if (j.ACTIVITY_PROPERTY == '11961001'
						&& j.DOWN_TAG == '12781001') {
					infoItem.push(j.ACTIVITY_CODE);
				}
			});
			infoItem = filterArray(infoItem);
		}
		if (isable != null && infoItem != null) {
			$.each(isable, function(i, j) {
				if (infoItem.indexOf(j.ACTIVITY_CODE) == -1) {// 表示没有参加此项服务活动
					couldItem.push(j.ACTIVITY_CODE);
				}
			});
		} else {
			couldItem = infoItem;
		}
		return couldItem;
	}

	// 删除数组指定元素
	function removeByValue(arr, val) {
		for (var i = 0; i < arr.length; i++) {
			if (arr[i] == val) {
				arr.splice(i, 1);
				break;
			}
		}
	}

	// 去除数组重复项
	function filterArray(receiveArray) {
		var arrResult = new Array(); // 定义一个返回结果数组.
		for (var i = 0; i < receiveArray.length; ++i) {
			if (check(arrResult, receiveArray[i]) == -1) {
				// 在这里做i元素与所有判断相同与否
				arrResult.push(receiveArray[i]);
				// 添加该元素到新数组。如果if内判断为false（即已添加过），
				// 则不添加。
			}
		}
		return arrResult;
	}

	function check(receiveArray, checkItem) {
		var index = -1; // 函数返回值用于布尔判断
		for (var i = 0; i < receiveArray.length; ++i) {
			if (receiveArray[i] == checkItem) {
				index = i;
				break;
			}
		}
		return index;
	}

	var GetActivityCodes2 = function(repairItem, repairPart, addItem) {
		var item = '';
		// 维修项目
		$.each(repairItem, function(i, j) {
			if (!isStringNull(j.ACTIVITY_CODE)) {
				var reg = new RegExp(j.ACTIVITY_CODE);
				if (!reg.test(item)) {
					item = item + "," + j.ACTIVITY_CODE;
				}
			}
		});
		// 维修配件
		$.each(repairPart, function(i, j) {
			if (!isStringNull(j.ACTIVITY_CODE)) {
				var reg = new RegExp(j.ACTIVITY_CODE);
				if (!reg.test(item)) {
					item = item + "," + j.ACTIVITY_CODE;
				}
			}
		});
		$.each(addItem, function(i, j) {
			if (!isStringNull(j.ACTIVITY_CODE)) {
				var reg = new RegExp(j.ACTIVITY_CODE);
				if (!reg.test(item)) {
					item = item + "," + j.ACTIVITY_CODE;
				}
			}
		});
	}

	var HaveOtherItem = function(repairItem, repairPart) {
		$.each(repairItem, function(i, j) {
			if (isStringNull(j.ACTIVITY_CODE)) {
				return true;
			}
		});
		return false;
	}

	var HaveOtherPart = function(repairPart) {
		$.each(repairItem, function(i, j) {
			if (isStringNull(j.ACTIVITY_CODE)) {
				return true;
			}
		});
		return false;
	}

	var CountActivity = function(repairItem, repairPart) {
		var item = GetActivityCodes(repairItem, repairPart);
		var arr = item.split(',');
		var count = arr.length;
		return count;
	}

	var CountOemActivity = function(repairItem, repairPart) {
		var item = GetActivityCodes(repairItem, repairPart);
		var count = 0;
		dmsCommon.ajaxRestRequest({
			url : dmsCommon.getDmsPath()[""] + "",
			type : 'GET',
			data : {
				'activityCode' : item
			},
			async : false,
			sucessCallBack : function(data) {
				count = data;
			}
		});
	}

	var GetActivityCodes = function(repairItem, repairPart) {
		var item = '';
		// 维修项目
		$.each(repairItem, function(i, j) {
			if (!isStringNull(j.ACTIVITY_CODE)) {
				var reg = new RegExp(j.ACTIVITY_CODE);
				if (!reg.test(item)) {
					item = item + "," + j.ACTIVITY_CODE;
				}
			}
		});
		// 维修配件
		$.each(repairPart, function(i, j) {
			if (!isStringNull(j.ACTIVITY_CODE)) {
				var reg = new RegExp(j.ACTIVITY_CODE);
				if (!reg.test(item)) {
					item = item + "," + j.ACTIVITY_CODE;
				}
			}
		});
		return item;
	}

	var CheckZhongDuanGuiFan = function(returnResult) {

	}

	var checkPaiGongGuiFan = function(repairItem, returnResult) {
		CheckLabourPaiGong(repairItem, returnResult);
	}

	var CheckLabourPaiGong = function(repairItem, returnResult) {
		var AllWorkTimes = 0;
		var assignHour = 0;
		var minHour = 0;
		var maxHour = 0;
		$.each(repairItem,
				function(i, j) {
					// 维修项目派工最大、最小比率
					AllWorkTimes = AllWorkTimes
							+ parseFloat(j.ASSIGN_LABOUR_HOUR);
					if (!isStringNull(dmsCommon.getSystemParamInfo("8040",
							"8040"))
							&& !isStringNull(dmsCommon.getSystemParamInfo(
									"8041", "8041"))
							&& parseFloat(dmsCommon.getSystemParamInfo("8041",
									"8041")) > 0
							&& parseFloat(dmsCommon.getSystemParamInfo("8040",
									"8040")) > 0) {
						assignHour = j.ASSIGN_LABOUR_HOUR;
						minHour = parseFloat(j.OEM_LABOUR_HOUR)
								* parseFloat(dmsCommon.getSystemParamInfo(
										"8040", "8040"));
						maxHour = parseFloat(j.OEM_LABOUR_HOUR)
								* parseFloat(dmsCommon.getSystemParamInfo(
										"8041", "8041"));
						// 解决浮点计算不精确的问题
						if (assignHour - minHour <= -0.01) {
							dmsCommon.tip({
								status : "warning",
								msg : "维修项目 " + j.LABOUR_CODE + " 派工工时低于下限"
							});
							returnResult.status = false;
						} else if (assignHour - maxHour >= 0.01) {
							dmsCommon.tip({
								status : "warning",
								msg : "维修项目 " + j.LABOUR_CODE + " 派工工时超过上限"
							});
							returnResult.status = false;
						}
					}
				});
	}

	// 加载维修类型下拉框
	var initRepairType = function(orderType, container) {
		var index = dmsCommon.getSystemParamInfo("1413", "1413");// 前台开关查询
		var selectData = new Array();
		if (parseInt(index) == 12781001) {
			if (!isStringNull($("#ownerNo", container).val())
					&& $("#ownerNo", container).val() == '888888888888') {
				selectData.push({
					id : "SQWX",
					name : "PDI检查"
				});
			} else if (!isStringNull(orderType)
					&& parseInt(orderType) == 12531004) {// 索赔
				selectData.push({
					id : "SCBY",
					name : "首次保养"
				});
				selectData.push({
					id : "BXIU",
					name : "保修"
				});
				selectData.push({
					id : "SHYX",
					name : "善意维修"
				});
				selectData.push({
					id : "SBWS",
					name : "三包维修"
				});
				selectData.push({
					id : "YXYX",
					name : "营销活动"
				});
				selectData.push({
					id : "FUWU",
					name : "服务活动"
				});
				selectData.push({
					id : "SQWX",
					name : "PDI检查"
				});
			} else if (!isStringNull(orderType)
					&& parseInt(orderType) == 12531001) {// 自费
				selectData.push({
					id : "CGBY",
					name : "常规保养"
				});
				selectData.push({
					id : "PTWX",
					name : "普通维修"
				});
				selectData.push({
					id : "NBXL",
					name : "内部修理"
				});
				selectData.push({
					id : "NFWX",
					name : "内部返工维修"
				});
				selectData.push({
					id : "WFWX",
					name : "外部返工维修"
				});
				selectData.push({
					id : "BJPQ",
					name : "钣金喷漆"
				});
				selectData.push({
					id : "XCZH",
					name : "精品装潢"
				});
				selectData.push({
					id : "ZDHD",
					name : "自店活动"
				});
				selectData.push({
					id : "QITA",
					name : "其他"
				});
			}
		} else {
			if (!isStringNull($("#ownerNo", container).val())
					&& $("#ownerNo", container).val() == '888888888888') {
				selectData.push({
					id : "SQWX",
					name : "PDI检查"
				});
			} else if (!isStringNull(orderType)
					&& parseInt(orderType) == 12531004) {// 索赔
				selectData.push({
					id : "PTWX",
					name : "普通维修"
				});
				selectData.push({
					id : "BJWX",
					name : "钣金维修"
				});
				selectData.push({
					id : "PQWX",
					name : "喷漆维修"
				});
				selectData.push({
					id : "SCBY",
					name : "首次保养"
				});
				selectData.push({
					id : "XCZH",
					name : "精品装潢"
				});
				selectData.push({
					id : "SQWX",
					name : "PDI检查"
				});
			}
		}
		dmsDict.refreshSelectByData($("#repairType", container), selectData,
				"id", "name");
	}

	// 加载维修项目下拉框数据
	var initLabourData = function() {
		var getTableRows = $("#labourList", getElementContext()).dmsTable()
				.getTableRows();
		var repairPro = $("#labourList", getElementContext()).dmsTable()
				.getRowDataByIndex();
		var selectData = new Array();
		for (var i = 0; i < getTableRows; i++) {
			selectData.push({
				id : repairPro[i].labourCode,
				name : repairPro[i].labourName
			});
		}
		return selectData;
	};
	// 费用结算为查询到数据不可编辑
	var addDisabled = function(container) {
		$(".disDiv a", container).attr("disabled", "disabled");
		// $("#remark",container).setElementReadOnly();
		$("#remark", container).attr("disabled", "disabled");
		$("#discountModeCode", container).setElementReadOnly();
		return "";
	};

	// 费用结算查询到数据后可编辑
	var removeDisabled = function() {
		//
		$(".disDiv a", getElementContext()).removeAttr("disabled");
		$("#remark", getElementContext()).removeAttr("disabled");
		$("#discountModeCode", getElementContext()).removeElementReadOnly();
	};
	// 计算维修材料费
	var calRepairPartAmount = function(curPartAmount) {
		// 计算材料费
		var oldDisRepairPartAmount = $("#disRepairPartAmount",
				getElementContext()).val(); // 折后维修材料费（上次总和）
		var oldRepairPartAmount = $("#repairPartAmount", getElementContext())
				.val();// 维修材料费 （上次总和）
		// var partAmount=parseFloat($("#salesAmountShow",container).val());
		// //金额
		var partAmount = parseFloat(curPartAmount);
		if (oldDisRepairPartAmount == null || oldDisRepairPartAmount == "") {
			oldDisRepairPartAmount = 0;
		}
		if (oldRepairPartAmount == null || oldRepairPartAmount == "") {
			oldRepairPartAmount = 0;
		}
		var disRepairPartAmount = parseFloat(oldDisRepairPartAmount)
				+ partAmount;
		var repairPartAmount = parseFloat(oldRepairPartAmount) + partAmount;

		var map = {
			dis_repair_part_amount : disRepairPartAmount.toFixed(2),
			repair_part_amount : repairPartAmount.toFixed(2)
		};
		$("div[data-partAmount='true']", getElementContext()).initHtmlContent(
				map, false);
	};
	var appendRowInRolabour = function(repairPro, workOrderType, selectData,
			receiveMoney, i, labourPrice) {
		var workOrderRepairProFlag = true;
		var workOrderRepairPro = {};
		if ("12121003" == workOrderType) {
			workOrderRepairPro.chargePartitionName = "S";
			workOrderRepairProFlag = false;
		}
		workOrderRepairPro.assignLabourHour = repairPro[i].ASSIGN_LABOUR_HOUR;
		workOrderRepairPro.repairType = repairPro[i].REPAIR_TYPE_CODE;
		workOrderRepairPro.labourName = repairPro[i].LABOUR_NAME;
		workOrderRepairPro.labourCode = repairPro[i].LABOUR_CODE;
		workOrderRepairPro.stdHour = repairPro[i].STD_LABOUR_HOUR;
		workOrderRepairPro.workType = repairPro[i].WORKER_TYPE_CODE;
		workOrderRepairPro.modeGroup = repairPro[i].MODEL_LABOUR_CODE;
		workOrderRepairPro.localLabourCode = repairPro[i].LOCAL_LABOUR_CODE;
		workOrderRepairPro.localLabourName = repairPro[i].LOCAL_LABOUR_NAME;
		workOrderRepairPro.workHourSinglePrice = labourPrice;
		workOrderRepairPro.discountRate = discountMode;
		var workHourPriceF = parseFloat(repairPro[i].STD_LABOUR_HOUR)
				* parseFloat(labourPrice);
		workOrderRepairPro.workHourPrice = workHourPriceF; // 工时费
		selectData.push({
			id : repairPro[i].LABOUR_CODE,
			name : repairPro[i].LABOUR_NAME
		});
		var discountMoneyF = workHourPriceF
				* (parseFloat(1) - parseFloat(discountMode));
		workOrderRepairPro.discountMoney = discountMoneyF.toFixed(2);// 优惠金额
		if (workOrderRepairProFlag) {
			var receiveMoneyF = workHourPriceF - discountMoneyF
			receiveMoney = receiveMoneyF.toFixed(2);
			workOrderRepairPro.receiveMoney = receiveMoney;
		} else {
			workOrderRepairPro.receiveMoney = receiveMoney.toFixed(2);
		}

		var appRowTr = $("#labourList", getElementContext()).dmsTable()
				.appendRow(workOrderRepairPro);
		var tableRow = $("#labourList", getElementContext()).dmsTable()
				.getTableRows();
		if ("12121003" == workOrderType) {

		} else {
			var roTypeSet = $("#roTypeSet", getElementContext()).val();
			if (roTypeSet == '10131001') {
				dmsDict.refreshSelectByFunction($(
						"select[id^=chargePartitionName]", appRowTr), function(
						selectObj) {
					$(selectObj).find("option[value='S']").remove();
				});
			}
		}
		$("select[id^=chargePartitionName]", appRowTr).bindChange(
				function(obj) {

					var index = $(obj).attr("id").substr(
							$(obj).attr("id").length - 1);
					if ($(obj).val() == "S" || $(obj).val() == "Z") {
						$("input[name^=receiveMoney]", $(obj).closest("tr"))
								.valChange(0);
						$(
								"span",
								$("input[name^=receiveMoney]",
										$(obj).closest("tr")).parent("td"))
								.text(0);
					}
				});

		// 赠送索赔下拉框
		if (workOrderRepairPro.chargePartitionName == "S") {
			$("#chargePartitionName" + (tableRow - 1), getElementContext())
					.attr("disabled", true);
		} else {
			$("#chargePartitionName" + (tableRow - 1), getElementContext())
					.removeAttr("disabled");
			$("#chargePartitionName" + (tableRow - 1) + " option[value='S']",
					getElementContext()).remove();
		}
		if (discountMode > 0) {
			var minRate = $("#minRate", container).val();
			$("#discountRate" + (tableRow - 1), getElementContext()).attr(
					"min", minRate);
		}
	};
	/**
	 * 结算（结算圆整方式） param val 值 settlementType 结算圆整方式 （四舍五入、只入不舍、只舍不如） precision
	 * 结算精度 （元、角、分）
	 */
	var settlementPre = function(val) {
		var settlementType = $("#settlementTypeHidden", getElementContext())
				.val();
		var precision = $("#accuracy", getElementContext()).val();
		var resultVal = "";
		val = parseFloat(val);
		if (precision == "10121001") { // 元
			if (settlementType == "10141001") { // 四舍五入 （整数）
				resultVal = parseFloat(val).toFixed(0);
			} else if (settlementType == "10141002") { // 只舍不如 (整数)
				resultVal = parseInt(val);
			} else if (settlementType == "10141003") { // 只入不舍 (保留整数位)
				resultVal = Math.ceil(val);
			}
		} else if (precision == "10121002") { // 角
			if (settlementType == "10141001") { // 四舍五入
				resultVal = val.toFixed(1);
			} else if (settlementType == "10141002") { // 只舍不如
				var tag = '.';
				var str = val + "";
				if (str.indexOf(tag) == -1) {
					resultVal = val.toFixed(1);
				} else {
					resultVal = parseFloat(str.substring(0, str
							.lastIndexOf('.') + 2));
				}
			} else if (settlementType == "10141003") { // 只入不舍

				var bb = val + "";
				var dian = bb.indexOf('.');
				if (dian == -1) {
					resultVal = val.toFixed(1);
				} else {
					var cc = bb.substring(dian + 1, bb.length);
					if (cc.length >= 2) {
						bb = bb.substring(0, dian + 2);
						resultVal = parseFloat(bb) + parseFloat(0.1);
					} else {
						resultVal = val.toFixed(1);
					}
				}
			}
		} else if (precision == "10121003") { // 分
			resultVal = val.toFixed(2);
		}
		return resultVal;
	};
	var countDisAmount = function() {
		var tab = $("#labourList tbody");
		var rows = $("tr", tab).length;
		var labourAmount = 0;
		var disLabourAmount = 0;
		var workHourPriceTrueNum = $("#labourList", getElementContext())
				.dmsTable().getVisiableCellNumber(10);
		var receiveMoneyTrueNum = $("#labourList", getElementContext())
				.dmsTable().getVisiableCellNumber(13);
		for (var i = 0; i < rows; i++) {
			labourAmount = parseFloat(labourAmount)
					+ parseFloat($(
							"tr:eq(" + i + ") td:eq(" + workHourPriceTrueNum
									+ ") input[id^=workHourPrice]", tab).val());
			disLabourAmount = parseFloat(disLabourAmount)
					+ parseFloat($(
							"tr:eq(" + i + ") td:eq(" + receiveMoneyTrueNum
									+ ") input[id^=receiveMoney]", tab).val());
		}
		var map = {
			labour_amount : labourAmount.toFixed(2),
			dis_labour_amount : disLabourAmount.toFixed(2)
		};
		$("div[data-repairAmount='true']", getElementContext())
				.initHtmlContent(map, false);
	};

	/**
	 * 根据品牌车型组代码查询车型代码集合
	 */
	var findModelLabourCodeAndModelCode = function() {
		dmsCommon.ajaxRestRequest({
			url : dmsCommon.getDmsPath()["repair"]
					+ "/basedata/repairProject/findModelForInput",
			type : 'GET',
			async : false,
			sucessCallBack : function(data) {
				return data;
			}
		});
	}

	/**
	 * 通过车型代码查询品牌车型组代码 code : 车型代码
	 */
	var findModelByModelLabourCode = function(code, container) {
		var reg = new RegExp(code);
		$.each(findModelLabourCodeAndModelCode(), function(name, value) {
			if (reg.test(value)) {
				$("#modelLabourCode", container).val(name);
			}
		});
	}

	/**
	 * 车牌号回车事件
	 */
	var licenseKeyUp = function(obj, container) {
		dmsCommon
				.ajaxRestRequest({
					url : dmsCommon.getDmsPath()['repair']
							+ "/basedata/queryByLinsence/license3",
					data : {
						'license' : $("#license", container).val()
					},
					async : false,
					sucessCallBack : function(datas) {
						if(datas.length<=1){
							var data = {};
							if(datas.length==1){
								data = datas[0];
							}
							var roType = $("#roType", container).val();// 工单类型
							/*
							 * var FTempMILEAGE =
							 * $("#inMileage",getElementContext()).val();
							 */
							// 车主不是4S站 在本店库存表里能找到，没有做过购车费用开票 则不能做维修开单
							console.log(data);
							if (!isStringNull(data.OWNER_NO)
									&& data.OWNER_NO != '888888888888'
									&& data.OWNER_NO != '999999999999'
									&& !isStringNull(data.VIN)
									&& CheckVehicleInvoice(data.VIN)) {
								dmsCommon.tip({
									status : "warning",
									msg : "此车辆还没有做实销上报，不能开工单！"
								});
								return;
							}
							if (!isStringNull(data.VIN)
									&& CheckIsHaveAduitingOrder(data.VIN)) {
								dmsCommon.tip({
									status : "warning",
									msg : "此车辆已存在“待审核”的索赔工单，不能再开索赔工单！"
								});
								if (parseInt(roType) == 12531004) {// 表示索赔工单类型
									return;
								}
							}
							if (parseInt($("#FIsNewVehicle", container).val()) == 1) {// 表示从VIN页面点击新增的车辆
								// 新建车辆也需要查看监控信息：车辆监控、预约单等信息 根据车牌号查询
								// 打开监控信息页面 if not MonitorVehicleRemind(fcdsVehicle)
								// then 3528
							}
							if (datas.length > 0) {// 表示能查到数据
								$("#FIsLoadVehicle", container).setDmsValue("true");
								// 设置该车的换表里程和累计换表里程，方便计算
								var FChangeMileage = 0;
								var FToTalChangeMileage = data.TOTAL_CHANGE_MILEAGE;
								var fLastMaintenancedate = data.LAST_MAINTENANCE_DATE;
								var fLastRepairDate = data.LAST_MAINTAIN_DATE;
								var fLastRepairMileage = data.LAST_MAINTAIN_MILEAGE;
								var flastMaintenanceMileage = data.LAST_MAINTENANCE_MILEAGE;
								var fModifySaledate = false;
								if (parseInt(nullToZero(data.IS_SELF_COMPANY)) == 12781001
										&& data.SALES_DATE != null
										&& data.SALES_DATE != '') {
									fModifySaledate = true;
								}
								// 选择VIN号后，
								// 如果车牌号不为空 而且不是新建的车辆，那么去查询是否存在该车辆的在修工单。
								var aVinTmp = data.VIN;
								if (!isStringNull(aVinTmp)) {
									if (QueryROByLicense(aVinTmp)) {
										// 如果存在在修车辆，提示“此车牌存在在修车辆，是否继续新建工单？”
										obj
												.confirm(
														'重复的维修组合选择，是否继续新增？',
														function(confirmObj) {
															SetVehicleInfo(data,
																	false, false,
																	false);
															// 根据车牌号查询工单的监控信息。
															if (datas.length > 0) {
																if (!isStringNull(data.TRACE_TIME)) {
																	$("#traceTime",
																			container)
																			.setDmsValue(
																					data.TRACE_TIME);
																} else if ($("#traceTime",container).attr("disabled")=='disabled'
																		&& $(
																				"#traceTime",
																				container)
																				.val() == "0") {
																	$("#traceTime",
																			container)
																			.setDmsValue(
																					11251004);
																}
															} else {
																if ($("#traceTime",container).attr("disabled")=='disabled'
																		&& $(
																				"#traceTime",
																				container)
																				.val() == "0") {
																	$("#traceTime",
																			container)
																			.setDmsValue(
																					11251004);
																}
															}
															var tt = dmsCommon
																	.getSystemParamInfo(
																			"1063",
																			"1063");// 预交车时间为当前时间延迟xx小时
															var ttt = parseFloat(tt) * 60 * 60 * 1000;
															var tttt = Number(new Date()
																	.getTime())
																	+ Number(ttt);
															$("#endTimeSupposed",
																	container)
																	.setDmsValue(
																			dateByType(
																					tttt,
																					"1"));// 预交车时间
															$("#calculate",
																	container)
																	.click();// 计算预计下次保修
														}, function(confirmObj) {
															return;
														});
									}
								}
							} else {// 表示没查到数据
								// 针对跨店维修的车辆-召回活动，监控信息也要显示
								var defaultValue = dmsCommon.getSystemParamInfo(
										"3415", "3415");// 维修工单支持同步车主车辆
								if (parseInt(defaultValue) == 12781001) {
									obj
											.confirm(
													'该车辆信息不存在，是否需要同步总部信息?',
													function(confirmObj) {
														if (!isStringNull($(
																"#license",
																container).val())
																&& $("#license",
																		container)
																		.val().length != 17) {
															dmsCommon
																	.tip({
																		status : "error",
																		msg : "请在车牌号一栏输入准确的17位VIN码，按回车键从总部获取该车主车辆信息！"
																	});
															return;
														}
														// SEDMS023
														// 根据$("#license",container).val()
														// 后台调用接口
														dmsCommon
																.ajaxRestRequest({
																	url : dmsCommon
																			.getDmsPath()['repair']
																			+ "/order/repair/SEDMS023",
																	data : {
																		'license' : $(
																				"#license",
																				container)
																				.val()
																	},
																	async : false,
																	sucessCallBack : function(
																			data2) {
																		if (data2.length > 0) {
																			$(
																					"#FIsLoadVehicle",
																					container)
																					.setDmsValue(
																							"true");
																			$(
																					"#lastMaintenancedate",
																					container)
																					.setDmsValue(
																							data2.LAST_MAINTENANCE_DATE);
																			$(
																					"#toTalChangeMileage",
																					container)
																					.setDmsValue(
																							data2.TOTAL_CHANGE_MILEAGE);
																			$(
																					"#lastRepairDate",
																					container)
																					.setDmsValue(
																							data2.LAST_MAINTAIN_DATE);
																			$(
																					"#lastRepairMileage",
																					container)
																					.setDmsValue(
																							data2.LAST_MAINTAIN_MILEAGE);
																			$(
																					"#lastMaintenanceMileage",
																					container)
																					.setDmsValue(
																							data2.LAST_MAINTENANCE_MILEAGE);
																			/*
																			 * if(parseInt(data2.IS_SELF_COMPANY)==12781001&&!isStringNull(data2.SALES_DATE)){ }
																			 */
																			// 选择VIN号后，
																			// 如果车牌号不为空
																			// 而且不是新建的车辆，那么去查询是否存在该车辆的在修工单。
																			if (!isStringNull(data2.VIN)) {
																				// 如果该车有会员卡，则更换图标
																				QueryMemberCardExist(data2.VIN)

																				setVehicleInfo(
																						data2,
																						false,
																						false,
																						false);
																				// 根据车牌号查询工单的监控信息。

																			}
																		} else {
																			dmsCommon
																					.tip({
																						status : "warning",
																						msg : "该VIN在总部不存在，请核对输入的17位VIN码是否正确！"
																					});
																			return;
																		}
																	}
																});
														if (data.length > 0) {
															if (!isStringNull(data.TRACE_TIME)) {
																$("#traceTime",
																		container)
																		.setDmsValue(
																				data.TRACE_TIME);
															} else if ($(
																	"#traceTime",
																	container)
																	.attr(
																			"disabled")
																	&& $(
																			"#traceTime",
																			container)
																			.val() == "0") {
																$("#traceTime",
																		container)
																		.setDmsValue(
																				11251004);
															}
														} else {
															if ($("#traceTime",
																	container)
																	.attr(
																			"disabled")
																	&& $(
																			"#traceTime",
																			container)
																			.val() == "0") {
																$("#traceTime",
																		container)
																		.setDmsValue(
																				11251004);
															}
														}
														var tt = dmsCommon
																.getSystemParamInfo(
																		"1063",
																		"1063");// 预交车时间为当前时间延迟xx小时
														var ttt = parseFloat(tt) * 60 * 60 * 1000;
														var tttt = Number(new Date()
																.getTime())
																+ Number(ttt);
														$("#endTimeSupposed",
																container)
																.setDmsValue(
																		dateByType(
																				tttt,
																				"1"));// 预交车时间
														$("#calculate", container)
																.click();// 计算预计下次保修
													},
													function(confirmObj) {
														if (data.length > 0) {
															if (!isStringNull(data.TRACE_TIME)) {
																$("#traceTime",
																		container)
																		.setDmsValue(
																				data.TRACE_TIME);
															} else if ($(
																	"#traceTime",
																	container)
																	.attr(
																			"disabled")
																	&& $(
																			"#traceTime",
																			container)
																			.val() == "0") {
																$("#traceTime",
																		container)
																		.setDmsValue(
																				11251004);
															}
														} else {
															if ($("#traceTime",
																	container)
																	.attr(
																			"disabled")
																	&& $(
																			"#traceTime",
																			container)
																			.val() == "0") {
																$("#traceTime",
																		container)
																		.setDmsValue(
																				11251004);
															}
														}
														var tt = dmsCommon
																.getSystemParamInfo(
																		"1063",
																		"1063");// 预交车时间为当前时间延迟xx小时
														var ttt = parseFloat(tt) * 60 * 60 * 1000;
														var tttt = Number(new Date()
																.getTime())
																+ Number(ttt);
														$("#endTimeSupposed",
																container)
																.setDmsValue(
																		dateByType(
																				tttt,
																				"1"));// 预交车时间
														$("#calculate", container)
																.click();// 计算预计下次保修
													});
								}
							}
						
						}else{
							alert($("#license",container).val())
							$("#licenseBtn",container).click();
						}
					}
				});
	}

	/**
	 * 预计下次保养日期,下次保养里程计算相关方法
	 */
	var nextMaintain = function(container) {
		debugger;
		var index = dmsCommon.getSystemParamInfo("1123", "1123");// 前台开关查询
		// 如果参数设置不计算则不处理.
		if (parseInt(index) == 12781002) {
			return;
		} else {
			var strTemp = '';// 给remark2赋值
			var sysdate = new Date();// 当前时间
			var i = 0;// 开关,预计下次保养日期距本工单最长天数
			var j = 0;// 开关,定期保养里程间隔
			i = dmsCommon.getSystemParamInfo("1064", "1064");// 前台开关查询
			j = dmsCommon.getSystemParamInfo("1072", "1072");// 前台开关查询
			// 日平均行驶里程
			$("#averageMileage", container).val(0);
			if ($("#FIsLoadVehicle", container).val() == 'true') {
				dmsCommon.ajaxRestRequest({
					url : dmsCommon.getDmsPath()['repair']
							+ "/order/repair/queryVehicleforactivity",
					data : {
						'vin' : $("#vin", container).val()
					},
					async : false,
					sucessCallBack : function(data) {
						$("#lastMaintenanceDate", container).val(
								data.LAST_MAINTENANCE_DATE);
						$("#lastRepairDate", container).val(
								data.LAST_MAINTAIN_DATE);
						$("#lastRepairMileage", container).val(
								data.LAST_MAINTAIN_MILEAGE);
						$("#lastMaintenanceMileage", container).val(
								data.LAST_MAINTENANCE_MILEAGE);
						$("#averageMileage", container).val(
								data.DAILY_AVERAGE_MILEAGE);
					}
				});
			}
			// 本工单是保养类型的
			// 本次是保养，上次保养日期就用当前日期
			var IsHaveMAINTAIN = false;
			var repairPartRow = $("#dms_part", container).dmsTable()
					.getRowDataByIndex();
			var partNo = '';
			$.each(repairPartRow, function(name, value) {
				partNo += value.PART_NO + ",";
			});
			dmsCommon.ajaxRestRequest({
				url : dmsCommon.getDmsPath()['repair']
						+ "/order/repair/getIsMaintenance",
				data : {
					'partNo' : partNo.substr(0, partNo.length - 1)
				},
				async : false,
				sucessCallBack : function(data) {
					console.log(JSON.stringify(data))
					if (data == '1') {
						IsHaveMAINTAIN = true;
					}
				}
			});
			if (IsHaveMAINTAIN) {
				$("#lastMaintainDate", container).val(sysdate.getTime());
			} else {
				// 如果有上次保养日期就用上次保养日期
				if ($("#lastMaintenanceDate", container).val() != null
						|| $("#lastMaintenanceDate", container).val() != '') {
					$("#lastMaintainDate", container).val(
							$("#lastMaintenanceDate", container).val());// 车辆表的上次保养日期;
				} else {
					// 如果销售日期为空或销售日期+预计下次保养日期距本工单最长天数<当前日期就用当前日期作为上次保养日期
					if (($("#salesDate", container).val() == "" || $(
							"#salesDate", container).val() == null)
							|| (Number(new Date($("#salesDate", container).val()).getTime()) + parseInt(i) * 86400000) < sysdate
									.getTime()) {
						$("#lastMaintainDate", container)
								.setDmsValue(sysdate.getTime());// 车辆表的上次保养日期;
						$("#lastMaintenanceMileage", container).setDmsValue(
								$("#outMileage", container).val());
					} else {
						$("#lastMaintainDate", container).setDmsValue(
								new Date($("#salesDate", container).val()).getTime());
					}
				}
			}
			if (($("#averageMileage", container).val() == 0)
					&& $("#lastMaintainDate", container).val() != ''
					&& (sysdate.getTime() > Number(nullToZero($("#lastMaintainDate",container).val())))) {
				// 没有日平均行程里程，用（出厂行驶里程－上次维修里程）/（当前日期－上次维修日期）
				$("#averageMileage", container).setDmsValue((parseInt(nullToZero($("#outMileage", container).val())) - parseInt(nullToZero($("#lastRepairMileage", container).val()))) / (Number(sysdate.getTime()) - Number($("#lastMaintainDate", container).val())));
			}
			if (($("#averageMileage", container).val() == 0)
					&& $("#lastMaintainDate", container).val() == ''
					&& $("#salesDate", container).val() != ''
					&& (sysdate.getTime() > Number(nullToZero(new Date($("#salesDate", container).val()).getTime())))) {
				$("#averageMileage", container).val(
						parseInt(nullToZero($("#outMileage", container).val())) / (sysdate.getTime() - Number(nullToZero($("#salesDate",container).val()))));
			}
			strTemp += '日均里程:' + nullToZero($("#averageMileage", container).val());
			strTemp += '间隔:' + j + '天数:' + i;
			strTemp += '上保里程:' + nullToZero($("#lastMaintenanceMileage", container).val())
					+ '上保期' + nullToZero($("#lastMaintainDate", container).val());
			strTemp += '上维里程:' + nullToZero($("#lastRepairMileage", container).val())
					+ '上维期' + nullToZero($("#lastRepairDate", container).val()) + '出里'
					+ nullToZero($("#outMileage", container).val());
			if (nullToZero($("#averageMileage", container).val()) > 0) {
				$("#nextMaintainMileage", container).val(
						parseInt(j)
								+ parseInt(nullToZero($("#lastMaintenanceMileage",container).val())));
				if (IsHaveMAINTAIN) {
					$("#nextMaintainMileage", container).val(
							parseInt(j)
									+ parseInt(nullToZero($("#outMileage", container).val())));
				}
				if (parseInt(nullToZero($("#nextMaintainMileage", container).val())) < parseInt(nullToZero($("#outMileage", container).val()))) {
					$("#nextMaintainMileage", container).setDmsValue(nullToZero($("#outMileage", container).val()));
				}
				// （定期保养里程间隔/日平均行程里程）> 预计下次保养日期距本工单最长天数
				if ((parseInt(j) / parseInt(nullToZero($("#averageMileage", container).val()))) > parseInt(i)) {
					$("#nextMaintainDate", container).setDmsValue(
							Number(nullToZero($("#lastMaintainDate", container).val()))
									+ parseInt(i) * 86400000);
				} else {
					$("#nextMaintainDate", container)
							.setDmsValue(
									Number(nullToZero($("#lastMaintainDate", container).val()))
											+ (parseInt(j) / parseInt(nullToZero($("#averageMileage",container).val())))
											* 86400000);
				}
				// 如果预计下次维修里程小于当前日期，则以当前日期为预计下次保养里程
				if (Number(nullToZero($("#nextMaintainDate", container).val())) < sysdate
						.getTime()) {
					$("#nextMaintainDate", container).setDmsValue(sysdate.getTime());
				}
				strTemp += '下保里：'
						+ Math.round((parseInt(nullToZero($("#nextMaintainMileage",container).val())) / 100) * 100);
				strTemp += '下保期：'
						+ dateByType($("#nextMaintainDate", container).val(),
								'1');
				$("#nextMaintainMileage", container)
						.val(
								(parseInt(nullToZero($("#nextMaintainMileage", container).val())) / 100) * 100);
				$("#nextMaintainDate", container)
						.val(
								dateByType($("#nextMaintainDate", container)
										.val(), '1'));
				$("remark2", container).val(strTemp);
			} else {
				// 无日平均行程里程应该用上次保养日期+预计下次保养日期距本工单最长天数
				$("#nextMaintainMileage", container).val(
						parseInt(j)
								+ parseInt(nullToZero($("#lastRepairMileage", container).val())));
				if (IsHaveMAINTAIN) {
					$("#nextMaintainMileage", container).val(
							parseInt(j)
									+ parseInt(nullToZero($("#outMileage", container).val())));
				}
				if (parseInt(nullToZero($("#nextMaintainMileage", container).val())) < parseInt(nullToZero($("#outMileage", container).val()))) {
					$("#nextMaintainMileage", container).setDmsValue(
							parseInt(nullToZero($("#outMileage", container).val())));
				}
				$("#nextMaintainDate", container).setDmsValue(
						Number(nullToZero($("#lastMaintainDate", container).val()))
								+ Number(i));
				strTemp += '下保里：'
						+ Math.round((parseInt(nullToZero($("#nextMaintainMileage",container).val())) / 100) * 100);
				$("#nextMaintainMileage", container)
						.setDmsValue(
								(parseInt(nullToZero($("#nextMaintainMileage", container).val())) / 100) * 100);
				if ($("#nextMaintainDate", container).val() != ''
						&& Number(nullToZero($("#nextMaintainDate", container).val())) > sysdate
								.getTime()) {
					strTemp += '下保期：'
							+ dateByType($("#nextMaintainDate", container)
									.val(), '1');
					$("#nextMaintainDate", container).setDmsValue(
							dateByType($("#nextMaintainDate", container).val(),
									'1'));
				} else {
					strTemp += '下保期：' + dateByType(sysdate.getTime(), '1');
					$("#nextMaintainDate", container).setDmsValue(
							dateByType(sysdate.getTime(), '1'));
				}
				$("remark2", container).val(strTemp);
			}
		}
	}

	// 根据车牌号查询是否存在在修工单，如果存在返回True，不存在返回False
	var QueryROByLicense = function(vin) {// QUERY_REPAIR_ORDER_EXISTS
		// 只查在修工单
		var roStatus = 12551001;
		dmsCommon.ajaxRestRequest({
			url : dmsCommon.getDmsPath()['repair']
					+ "/order/repair/queryRepairOrderExists",
			data : {
				'vin' : vin,
				'roStatus' : roStatus
			},
			async : false,
			sucessCallBack : function(data) {
				if (data != null) {
					return true;
				} else {
					return false;
				}
			}
		});
	}

	// 品牌，车系，车型，VIN，发动机号，
	// 车主编号，车主性质，车主，
	// 送修人，送修人性别，送修人电话区号，送修人电话，送修人手机，
	// 累计换表里程，进厂行驶里程，是否换表，换表里程，
	// 上次维修日期，责任技师，业务接待，保险公司，
	// 上次行驶里程，颜色，下次保养里程，
	// 预计下次保养日期（车主车辆信息中预计下次保养日期大于当前系统日期则显示预计下次保养日期、下次保养里程）
	// 销售日期，上牌日期，日平均行驶里程
	var setVehicleInfo = function(data, isVehicle, isVinEnter, isDeData) {
		isVehicle = false;
		isVinEnter = false;
		isDeData = false;
		if (data != null) {
			// 车牌号，品牌，VIN，发动机号，车系，车型，车身颜色，销售日期
			if (!isStringNull(data.LICENSE) && !isVinEnter) {// 点击VIN号是不返回车牌号。
				$("#license", container).setDmsValue(data.LICENSE);
			} else if (!isStringNull(data.LICENSE) && isVinEnter) {
				// 无牌照可以更新。可以直接在工单处更新车辆的信息。 (条件：牌照号为无牌照，且首次进厂）
				if (data.LICENSE != '无牌照'
						|| (!isStringNull(data.LAST_MAINTAIN_DATE) && !isStringNull(data.LAST_MAINTAIN_DATE))) {
					$("#license", container).setDmsValue(data.LICENSE);
				}
			}
			$("#brand", container).setDmsValue(data.BRAND);
			$("#vin", container).setDmsValue(data.VIN);
			$("#engineNo", container).setDmsValue(data.ENGINE_NO);
			$("#series", container).setDmsValue(data.SERIES);
			$("#model", container).setDmsValue(data.MODEL);
			if (!isStringNull(data.APACKAGE)) {
				$("#configCode", container).setDmsValue(data.APACKAGE);
			}
			$("#color", container).setDmsValue(data.COLOR);
			$("#salesDate", container).setDmsValue(data.SALES_DATE);
			// 车主编号，车主性质，车主，送修人姓名，送修人性别，送修人电话，送修人手机，上牌日期，
			if (!isVehicle) {
				if (!isStringNull(data.OWNER_NO)) {
					$("#ownerNo", container).setDmsValue(data.OWNER_NO);
				}
				if (!isStringNull(data.OWNER_PROPERTY)) {
					$("#ownerProperty", container).setDmsValue(
							data.OWNER_PROPERTY);
				}
				if (!isStringNull(data.OWNER_NAME)) {
					$("#ownerName", container).setDmsValue(data.OWNER_NAME);
				}
			}
			if (!isStringNull(data.DELIVERER)) {
				$("#deliverer", container).setDmsValue(data.DELIVERER);
			}
			if (!isStringNull(data.DELIVERER_GENDER)) {
				$("#delivererGender", container).setDmsValue(
						data.DELIVERER_GENDER);
			}
			if (!isStringNull(data.DELIVERER_PHONE)) {
				$("#delivererPhone", container).setDmsValue(
						data.DELIVERER_PHONE);
			}
			if (!isStringNull(data.DELIVERER_MOBILE)) {
				$("#delivererMobile", container).setDmsValue(
						data.DELIVERER_MOBILE);
			}
			if (data.DELIVERER.trim() == ''
					&& parseInt(data.OWNER_PROPERTY) == 11901002) {
				if (!isStringNull(data.OWNER_NAME)) {
					$("#ownerName", container).setDmsValue(data.OWNER_NAME);
				}
				if (!isStringNull(data.GENDER)) {
					$("#delivererGender", container).setDmsValue(data.GENDER);
				}
				if (!isStringNull(data.PHONE)) {
					$("#delivererPhone", container).setDmsValue(data.PHONE);
				}
				if (!isStringNull(data.MOBILE)) {
					$("#delivererMobile", container).setDmsValue(data.MOBILE);
				}
			}
			if (!isStringNull(data.LICENSE_DATE)) {
				$("#licenseDate", container).setDmsValue(data.LICENSE_DATE);
			}
			if (!isStringNull(data.WRT_BEGIN_DATE)) {
				$("#wrtBeginDate", container).setDmsValue(data.WRT_BEGIN_DATE);// 保险开始日期
			}

			// 进厂表上里程，出厂行驶里程，累计换表里程，上次换表日期，上次维修日期
			// 出厂行驶里程默认等于进厂表上里程。进厂表上里程从车辆的行驶里程
			var value1 = dmsCommon.getSystemParamInfo("1180", "1180");// 前台开关查询
			if (parseInt(value1) == 12781001) {
				if (!isStringNull(data.MILEAGE)) {
					if (parseFloat(data.MILEAGE) > parseFloat($("#inMileage")
							.val())) {
						$("#inMileage", container).setDmsValue(data.MILEAGE);
						$("#outMileage", container).setDmsValue(data.MILEAGE);
					}
				}
			}
			if (!isStringNull(data.TOTAL_CHANGE_MILEAGE)) {
				$("#totalChangeMileage", container).setDmsValue(
						data.TOTAL_CHANGE_MILEAGE);// 保险开始日期
			}
			if (!isStringNull(data.CHANGE_DATE)) {
				$("#changeDate", container).setDmsValue(data.CHANGE_DATE);// 保险开始日期
			}
			if (!isStringNull(data.LAST_MAINTAIN_DATE)) {
				$("#lastMaintainDate", container).setDmsValue(
						data.LAST_MAINTAIN_DATE);// 保险开始日期
			}
			// 上次维修里程 上端更新的时候 没有处理到此出导致 计算日平均行使里程 有问题
			if (isDeData) {
				if (!isStringNull(data.MILEAGE)) {
					$("#lastMaintainMileage", container).setDmsValue(
							data.MILEAGE);// 保险开始日期
				}
			}
			// 指定技师
			if (!isStringNull(data.CHIEF_TECHNICIAN)) {
				$("#chiefTechnician", container).setDmsValue(
						data.CHIEF_TECHNICIAN);
			}
			// 如果没有载入服务专员。获取当前员工的操作员
			if (isStringNull($("#serviceAdvisor", container).val())) {

			}
			// 三日电访时间 如果车主信息中没有，那么默认为全天
			if (!isStringNull(data.IS_TRACE)) {
				$("#isTrace", container).setDmsValue(data.IS_TRACE);// 三日电访前复选框
			}
			if (parseInt($("#isTrace", container).val()) == 12781001) {
				if (!isStringNull(data.TRACE_TIME.trim())) {
					$("#TRACE_TIME", container).setDmsValue(data.TRACE_TIME);
				}
			} else {
				$("#TRACE_TIME", container).setDmsValue("");
			}
			// 下次保养里程、下次保养日期
			if (!isStringNull($("#nextMaintainDate", container).val())
					&& !isStringNull(data.NEXT_MAINTAIN_DATE)) {
				$("#nextMaintainDate", container).setDmsValue(
						data.NEXT_MAINTAIN_DATE);
			}
			if (!isStringNull($("#nextMaintainMileage", container).val())
					&& !isStringNull(data.NEXT_MAINTAIN_MILEAGE)) {
				$("#nextMaintainMileage", container).setDmsValue(
						data.NEXT_MAINTAIN_MILEAGE);
			}
			// 保险公司
			if (!isStringNull($("#insurationCode", container).val())
					&& !isStringNull(data.INSURATION_CODE)) {
				if (HaveInsuration($("#repairType", contianer).val())) {
					var value = dmsDict.getSelectedOptionData($("#repairType",
							container)).IS_INSURANCE;
					$("#insure", container).removeAttr("disabled");
					if (parseInt(value) != 12781001) {// 是否保险字段
						$("#insure", container).setDmsValue("");
					}
				}
			}
			// 会员编号
			if (!isStringNull($("#memberNo", container).val())
					&& !isStringNull(data.MEMBER_NO)) {
				$("#memberNo", container).setDmsValue(data.MEMBER_NO);
			}
			if (!isStringNull($("#productCode", container).val())
					&& !isStringNull(data.PRODUCT_CODE)) {
				$("#productCode", container).setDmsValue(data.PRODUCT_CODE);
			}
			// 如果不存在product_code,查询上端车辆信息更新product_code
			if (isStringNull(data.PRODUCT_CODE)) {
				return;
			}
			if (data != null) {
				var fOemTag = false;
				if (!isStringNull($("#brand", container).val())
						&& parseInt(dmsDict.getSelectedOptionData($("#brand",
								container)).OEM_TAG) == 12781001) {
					fOemTag = true;
				}
				if (fOemTag
						&& isStringNull(data.PRODUCT_CODE)
						&& parseInt(dmsCommon
								.getSystemParamInfo("1801", "1801")) == 12781001) {
					// 更新本地车辆信息
					GetVehicle($("#vin", container).val());
				}
			}
		}
	}

	/**
	 * 选择工单时确定按钮,查询工单明细 selectItem 选中的那一行
	 */
	var chooseRoNoInfo = function(roNo) {
		dmsCommon
				.ajaxRestRequest({
					url : dmsCommon.getDmsPath()['repair']
							+ "/order/repair/findOrderDetails",
					data : {
						'roNo' : roNo
					},
					async : false,
					sucessCallBack : function(data) {
						if (data != null) {
							$("div[data-selectRepairOrder='true']",
									getElementContext()).initHtmlContent(data);
						}
					}
				});
	}

	// DCDSG0238 根据vin号查询车辆信息
	var GetVehicle = function(vin) {
		dmsCommon
				.ajaxRestRequest({// 查询接口
					url : dmsCommon.getDmsPath()['repair']
							+ "/order/repair/DCDSGO238",
					data : {
						'vin' : vin
					},
					async : false,
					sucessCallBack : function(data) {
						if (data != null) {
							$("#productCode", container).setDmsValue(
									item.PRODUCT_CODE);
						}
					}
				});
	}

	var HaveInsuration = function(repairType) {
		// 判断维修类型下拉框是否包含repairType,是返回true
		var ret = false;
		$("#repairType option").each(function() {
			if (repairType == $(this).text()) {
				ret = true;
			}
		});
		return ret;
	}

	// 查询非4S站的车辆,在库存存在并且未开票的车
	var CheckVehicleInvoice = function(vin) {// QUERY_NO_INVOICE_VEHICLE
		var bool = false;
		dmsCommon.ajaxRestRequest({
			url : dmsCommon.getDmsPath()['repair']
					+ "/order/repair/checkVehicleInvoice",
			data : {
				'vin' : vin
			},
			async : false,
			sucessCallBack : function(data) {
				if(data!=null){
					bool = true;
				}
			}
		});
		return bool;
	}

	// 查询车辆方案状态为”等待审核“的工单
	var CheckIsHaveAduitingOrder = function(vin) {// QUERY_ADUITING_REPAIR_ORDER
		var bool = false;
		dmsCommon.ajaxRestRequest({
			url : dmsCommon.getDmsPath()['repair']
					+ "/order/repair/checkIsHaveAduitingOrder",
			data : {
				'vin' : vin
			},
			async : false,
			sucessCallBack : function(data) {
				if(data!=null){
					bool = true;
				}
			}
		});
		return bool;
	}

	// 如果该车有会员卡，则更换图标
	var QueryMemberCardExist = function(vin) {// QUERY_MEMBER_CARD_BY_VIN
		dmsCommon.ajaxRestRequest({
			url : dmsCommon.getDmsPath()['repair']
					+ "/order/repair/queryMemberCardExist",
			data : {
				'vin' : vin
			},
			async : false,
			sucessCallBack : function(data) {
				$("#cardTypeCode").setDmsValue(data.CARD_TYPE_NAME);
				return data!=null;
			}
		});
		return false;
	}

	var nullToZero = function(flag) {
		return flag == null || flag == "" ? 0 : flag;
	}

	var dateByType = function(date, a) {
		if (isStringNull(a)) {
			var time = new Date(date);
			return date.toLocaleDateString();
		} else {
			var time = new Date(date);
			return time.getFullYear() + "/" + (time.getMonth() + 1) + "/"
					+ time.getDate() + " " + time.getHours() + ":"
					+ time.getMinutes() + ":" + time.getSeconds();
		}
	}

	return {
		initLabourData : function() {
			return initLabourData();
		},
		addDisabled : function(container) {
			return addDisabled(container);
		},
		removeDisabled : function() {
			removeDisabled();
		},
		calRepairPartAmount : function(curPartAmount) {
			calRepairPartAmount(curPartAmount);
		},
		appendRowInRolabour : function(repairPro, workOrderType, selectData,
				receiveMoney, i, labourPrice) {
			appendRowInRolabour(repairPro, workOrderType, selectData,
					receiveMoney, i, labourPrice);
		},
		settlementPre : function(val) {
			return settlementPre(val);
		},
		countDisAmount : function() {
			return countDisAmount();
		},
		findModelLabourCodeAndModelCode : function() {
			findModelLabourCodeAndModelCode();
		},
		findModelByModelLabourCode : function(code, container) {
			findModelByModelLabourCode(code, container);
		},
		licenseKeyUp : function(obj, container) {
			licenseKeyUp(obj, container);
		},
		nextMaintain : function(container) {
			nextMaintain(container);
		},
		chooseRoNoInfo : function(roNo) {
			chooseRoNoInfo(roNo);
		},
		initRepairType : function(orderType) {
			initRepairType(orderType);
		},
		checkSave : function(container, returnResult) {
			checkSave(container, returnResult);
		},
		initRepairType1 : function(container) {
			initRepairType1(container);
		},
		initQueryDateType : function(container) {
			initQueryDateType(container);
		},
		initPre3BaoWarm : function(srca) {
			initPre3BaoWarm(srca);
		},
		roNoKeyUp : function(obj, container) {
			roNoKeyUp(obj, container);
		},
		vinKeyUp : function(obj, container) {
			vinKeyUp(obj, container);
		}
	};

}();

/**
 * 客户模块模块相关功能
 */
var dmsCustomer = function() {

}();

/**
 * 管理模块相关功能
 */
var dmsManager = function() {
	var sample = function() {

	};

	return {
		sample : function() {
			sample();
		}
	};
}();

/**
 * 零售模块相关功能
 */
var dmsRetail = function() {
	var saleDatabindChange = function(tableObject, container) {
		var tab = $("#soDecrodateList tbody", container);
		var tab2 = $("#soDecrodatePartList tbody", container);
		var tab3 = $("#soServicesList tbody", container);
		$("select[id^=accountMode]", tab2).each(
				function() {
					if ($(this).val() == "14061003"
							|| $(this).val() == "14061004") {
						$("input[id^=discount]", $(this).closest("tr")).attr(
								"disabled", "true");
					} else {
						$("input[id^=discount]", $(this).closest("tr"))
								.removeAttr("disabled");
					}
				});
		/** *装潢材料***** */
		// 改变折扣率重置结算方式
		var calDecrodateDiscount = function(obj) {
			var inputValue = $(obj).val();
			if (inputValue == 0 || inputValue == "") {

			} else {
				// $("select[id^=accountMode]",$(obj).closest("tr")).setDmsValue("");
			}
		}
		// 通过下拉框数据实时变更左侧数值
		var calDecrodatePartItemAmount = function(obj) {
			var selectValue = $(obj).val();
			if (selectValue == "14061003" || selectValue == "14061004") {
				$("input[id^=discount]", $(obj).closest("tr")).valChange("0");
				/*
				 * $("input[id^=afterDiscountAmountPartList]",$(obj).closest("tr")).valChange("0");
				 * $("input[id^=afterDiscountAmountPartList]",$(obj).closest("tr")).parents("td").find("span").valChange("0");
				 */
				$("input[id^=discount]", $(obj).closest("tr")).attr("disabled",
						"true");
			} else {
				$("input[id^=discount]", $(obj).closest("tr")).valChange(1);
				$("input[id^=discount]", $(obj).closest("tr")).removeAttr(
						"disabled");
			}
			// dmsRetail.moneyCalculate(container);
		}
		// 填写序列号数值表格变成1并且不可编辑
		var calDecrodatePartItem = function(obj) {
			var inputValue = $(obj).val();
			if (inputValue == "" || inputValue == undefined) {
				$("input[id^=partQuantity]", $(obj).closest("tr")).valChange(
						"1");
				$("input[id^=partQuantity]", $(obj).closest("tr")).removeAttr(
						"disabled");
			} else {
				$("input[id^=partQuantity]", $(obj).closest("tr")).valChange(
						"1");
				$("input[id^=partQuantity]", $(obj).closest("tr")).attr(
						"disabled", "true");
			}
		}
		// 绑定select事件-- 结算方式
		$("select[id^=accountMode]", tab2).bindChange(function(obj) {
			// 计算服务项目实收金额
			calDecrodatePartItemAmount(obj);
		});

		$("input[id^=partSequence]", tab2).bindChange(function(obj) {
			// 计算服务项目实收金额
			calDecrodatePartItem(obj);
		});

		$("input[id^=discount]", tab2).bindChange(function(obj) {
			// 计算服务项目实收金额
			calDecrodateDiscount(obj);
		});

		/** *装潢项目***** */
		// 通过下拉框数据实时变更左侧数值
		var calDecrodateAmount = function(obj) {
			var selectValue = $(obj).val();
			if (selectValue == "14061003" || selectValue == "14061004") {
				$("input[id^=discount]", $(obj).closest("tr")).valChange("0");
			}
		}
		// 填写序列号数值表格变成1并且不可编辑
		var calDecrodateItem = function(obj) {
			$("input[id^=partQuantity]", $(obj).closest("tr")).valChange("1");
			$("input[id^=partQuantity]", $(obj).closest("tr")).attr("disabled",
					"true");
		}
		// 改变加价率重置select值
		var calDecrodateDiscount2 = function(obj) {
			var inputValue = $(obj).val();
			if (inputValue == 0 || inputValue == "") {

			} else {
				$("select[id^=accountMode]", $(obj).closest("tr")).setDmsValue(
						"");
			}
		}

		var calServiceItemAmountList = function(obj) {
			var inputValue = $(obj).val();
			if (inputValue == 0 || inputValue == "") {

			} else {
				$("select[id^=accountMode]", $(obj).closest("tr")).setDmsValue(
						"14061002");
			}
			dmsRetail.moneyCalculate(container);
		}

		// 通过下拉框数据实时变更左侧数值
		var calServiceItemAmount = function(obj) {
			var selectValue = $(obj).val();
			if (selectValue == "14061003" || selectValue == "14061004") {
				$("input[id^=afterDiscountAmountServicesList]",
						$(obj).closest("tr")).valChange("0");
			}
			dmsRetail.moneyCalculate(container);
		}
		// 绑定select事件-- 结算方式
		$("select[id^=accountModeList]", tab3).bindChange(function(obj) {
			// 计算服务项目实收金额
			calServiceItemAmount(obj);
		});

		$("input[id^=afterDiscountAmountServicesList]", tab3).bindChange(
				function(obj) {
					// 计算服务项目实收金额
					calServiceItemAmountList(obj);
				});

	}
	var moneyCalculate = function(container) {
		var tab = $("#soDecrodateList tbody", container);
		var tab2 = $("#soDecrodatePartList tbody", container);
		var tab3 = $("#soServicesList tbody", container);
		var rows = $("tr", tab).length;
		var rows2 = $("tr", tab2).length;
		var rows3 = $("tr", tab3).length;
		var afterDiscountAmount = parseFloat(0);
		var afterDiscountAmountPartList = parseFloat(0);
		var afterDiscountAmountServicesList = parseFloat(0);
		var partSalesAmount = parseFloat(0);
		var directivePrice = parseFloat(0);
		var DiscountAmountPrice = parseFloat(0);
		var sumDiscountAmountPrice = parseFloat(0);
		var sumPresentPrice = parseFloat(0);
		var sumAfterDiscountAmount = parseFloat(0);
		var sumAfterDiscountAmountPartList = parseFloat(0);
		var sumAfterDiscountAmountServicesList = parseFloat(0);
		var sumPartSalesAmount = parseFloat(0);
		var sumDirectivePrice = parseFloat(0);
		var sumPartPresentAmount = parseFloat(0);

		// 装潢项目计算
		if (rows > 0) {
			for (var i = 0; i < rows; i++) {
				afterDiscountAmount = $(
						"tr:eq(" + i
								+ ") td:eq(7) input[id^=afterDiscountAmount]",
						tab).val();// 装潢项目实收金额
				if (afterDiscountAmount == ""
						|| afterDiscountAmount == undefined) {
					var afterDiscountAmount = parseFloat(0);
					sumAfterDiscountAmount = parseFloat(sumAfterDiscountAmount)
							+ parseFloat(afterDiscountAmount);// 装潢项目实收金额总和
				} else {
					sumAfterDiscountAmount = parseFloat(sumAfterDiscountAmount)
							+ parseFloat(afterDiscountAmount);// 装潢项目实收金额总和
				}
			}
		} else {
			var sumAfterDiscountAmount = parseFloat(0);
		}
		// console.log("状态状态状态很好"+sumAfterDiscountAmount+"价格:");

		// 装潢材料
		if (rows2 > 0) {
			for (var i = 0; i < rows2; i++) {
				afterDiscountAmountPartList = $(
						"tr:eq("
								+ i
								+ ") td:eq(11) input[id^=afterDiscountAmountPartList]",
						tab2).val();// 装潢材料实收金额
				partSalesPrice = $(
						"tr:eq(" + i + ") td:eq(7) input[id^=partSalesPrice]",
						tab2).val();// 装潢材料销售价格
				partQuantity = $(
						"tr:eq(" + i + ") td:eq(8) input[id^=partQuantity]",
						tab2).val();// 装潢材料销售数量
				partSalesAmount = $(
						"tr:eq(" + i + ") td:eq(9) input[id^=partSalesAmount]",
						tab2).val();// 装潢材料销售金额
				// discount2= $("tr:eq("+i+") td:eq(10)
				// input[id^=discount]",tab2).val();//折扣率
				accountMode = $(
						"tr:eq(" + i + ") td:eq(12) select[id^=accountMode]",
						tab2).val();// 结算方式
				if (typeof (afterDiscountAmountPartList) == "undefined"
						|| afterDiscountAmountPartList == '') {
					afterDiscountAmountPartList = parseFloat(0).toFixed(2);
				}

				if (typeof (partSalesPrice) == "undefined"
						|| partSalesPrice == '') {
					partSalesPrice = parseFloat(0).toFixed(2);
				}

				if (typeof (partQuantity) == "undefined" || partQuantity == '') {
					partQuantity = parseFloat(0).toFixed(2);
				}

				/*
				 * if(accountMode !="14061003"&&accountMode !="14061004"){
				 * afterDiscountAmountPartList=(partSalesPrice*partQuantity*discount2).toFixed(2)
				 * $("tr:eq("+i+") td:eq(11)
				 * input[id^=afterDiscountAmountPartList]",tab2).valChange(afterDiscountAmountPartList);
				 * $("tr:eq("+i+") td:eq(11)
				 * span",tab2).valChange(afterDiscountAmountPartList); }
				 */
				if (typeof (partSalesAmount) == "undefined"
						|| partSalesAmount == '') {
					partSalesAmount = parseFloat(partSalesPrice)
							* parseFloat(partQuantity);
					var item = $("tr:eq(" + i + ") td:eq(9)>span", tab2);
					var digits = $(item).attr("data-autoValueDigits");
					if (digits) {
						partSalesAmount = partSalesAmount
								.toFixed(parseInt(digits));
					}
					$(item).valChange(partSalesAmount);
					// 如果item 不是input 属性
					if (!$(item).is(":input")) {
						$("input", $(item).parent()).valChange(partSalesAmount);
					}
				}

				if (accountMode == "14061004") {
					sumPartPresentAmount = parseFloat(sumPartPresentAmount)
							+ parseFloat(partSalesAmount);// 状态为赠送时的金额总和(销售金额)
				}
				if (partSalesAmount == "" || partSalesAmount == undefined) {
					var afterDiscountAmountPartList = parseFloat(0);
					var partSalesAmount = parseFloat(0);
					sumPartSalesAmount = parseFloat(sumPartSalesAmount)
							+ parseFloat(partSalesAmount);// 销售金额总和
					sumAfterDiscountAmountPartList = parseFloat(sumAfterDiscountAmountPartList)
							+ parseFloat(afterDiscountAmountPartList); // 装潢材料实收金额总和
				} else {
					sumPartSalesAmount = parseFloat(sumPartSalesAmount)
							+ parseFloat(partSalesAmount);// 销售金额总和
					sumAfterDiscountAmountPartList = parseFloat(sumAfterDiscountAmountPartList)
							+ parseFloat(afterDiscountAmountPartList); // 装潢材料实收金额总和
				}

			}
		} else {
			var afterDiscountAmountPartList = parseFloat(0);
			var partSalesAmount = parseFloat(0);
			var DiscountAmountPrice = parseFloat(0);
			var sumDiscountAmountPrice = parseFloat(0);
			var sumPartSalesAmount = parseFloat(0);
			var sumAfterDiscountAmountPartList = parseFloat(0);
		}

		// 服务项目
		if (rows3 > 0) {
			for (var i = 0; i < rows3; i++) {
				afterDiscountAmountServicesList = $(
						"tr:eq("
								+ i
								+ ") td:eq(6) input[id^=afterDiscountAmountServicesList]",
						tab3).val();// 服务项目实收金额
				directivePrice = $(
						"tr:eq(" + i + ") td:eq(5) input[id^=directivePrice]",
						tab3).val();
				accountModeList = $(
						"tr:eq(" + i + ") td:eq(7) select[id^=accountModeList]",
						tab3).val();
				if (accountModeList == "14061004") {
					sumDirectivePrice = parseFloat(sumDirectivePrice)
							+ parseFloat(directivePrice);
				}
				if (afterDiscountAmountServicesList == ""
						|| afterDiscountAmountServicesList == undefined) {
					var afterDiscountAmountServicesList = parseFloat(0);
					sumAfterDiscountAmountServicesList = parseFloat(sumAfterDiscountAmountServicesList)
							+ parseFloat(afterDiscountAmountServicesList);// 服务项目实收金额总和
				} else {
					sumAfterDiscountAmountServicesList = parseFloat(sumAfterDiscountAmountServicesList)
							+ parseFloat(afterDiscountAmountServicesList);// 服务项目实收金额总和
				}
			}
		} else {
			var afterDiscountAmountServicesList = parseFloat(0);
			var presentPrice = parseFloat(0);
			var directivePrice = parseFloat(0);
			var presentPrice = parseFloat(0);
			var sumPresentPrice = parseFloat(0);
			var sumAfterDiscountAmountServicesList = parseFloat(0);
		}

		if (sumAfterDiscountAmount == ""
				&& sumAfterDiscountAmountPartList != "") {
			$("#upholsterSum", container).setDmsValue(
					parseFloat(sumAfterDiscountAmountPartList).toFixed(2));
		} else if (sumAfterDiscountAmountPartList == ""
				&& sumAfterDiscountAmount != "") {
			$("#upholsterSum", container).setDmsValue(
					parseFloat(sumAfterDiscountAmount).toFixed(2));
		} else if (sumAfterDiscountAmountPartList == ""
				&& sumAfterDiscountAmount == "") {
			$("#upholsterSum", container).setDmsValue(parseFloat(0));
		} else {
			$("#upholsterSum", container)
					.setDmsValue(
							(parseFloat(sumAfterDiscountAmount) + parseFloat(sumAfterDiscountAmountPartList))
									.toFixed(2));// 装潢金额总和

		}
		// 计算赠送金额
		if (accountModeList == "14061004" && accountMode != "14061004") {
			$("#presentSum", container).setDmsValue(
					parseFloat(sumDirectivePrice).toFixed(2));
		} else if (accountMode == "14061004" && accountModeList != "14061004") {
			$("#presentSum", container).setDmsValue(
					parseFloat(sumPartPresentAmount).toFixed(2));
		} else if (accountModeList == "" && accountMode == "") {
			$("#presentSum", container).setDmsValue(parseFloat(0));
		} else {
			$("#presentSum", container)
					.setDmsValue(
							(parseFloat(sumDirectivePrice) + parseFloat(sumPartPresentAmount))
									.toFixed(2));// 赠送金额总和
		}

		$("#serviceSum", container).setDmsValue(
				parseFloat(sumAfterDiscountAmountServicesList).toFixed(2));// 服务项目金额总和

	};

	return {
		moneyCalculate : function(container) {
			moneyCalculate(container);
		},
		saleDatabindChange : function(tableObject, container) {
			saleDatabindChange(tableObject, container);
		}
	};
}();

/**
 * 车辆模块相关功能
 */
var dmsVehicle = function() {
	var sample = function(container) {

	};

	return {
		sample : function() {
			sample();
		}
	};
}();

var dmsHomePageReport = function() {
	var homePageReport = function(container, url) {
		dmsCommon.ajaxRestRequest({
			url : dmsCommon.getDmsPath()["report"] + "/homePage/statistical",
			type : 'GET',
			sucessCallBack : function(data) {
				if ($.trim(data) != "null") {
					$("#complaincounts", container).html(
							'<span data-counter="counterup"  data-value='
									+ data.complainNum + '></span>个');
					$("#repairCar", container).html(
							'<span data-counter="counterup"  data-value='
									+ data.repairNum + '></span>台');
					$("#saleCar", container).html(
							'<span data-counter="counterup"  data-value='
									+ data.saleNum + '></span>台');
					$("#conversionRate", container).html(
							'<span data-counter="counterup"  data-value='
									+ data.conversionRate + '></span>%');
				}
				var trs = [];
				var graphs = [];
				dataProvider(data.saleStai, trs, graphs);
				/*
				 * console.log(JSON.stringify(trs));
				 * console.log(JSON.stringify(graphs));
				 */
				Dashboard.initAmChart5(graphs, trs);
				App.init();

			}
		});

		dmsCommon.ajaxRestRequest({
			url : dmsCommon.getDmsPath()["report"]
					+ "/homePage/statistical/repairs",
			type : 'GET',
			sucessCallBack : function(data) {
				Dashboard.initCharts(data);
			}
		});

		dmsCommon.ajaxRestRequest({
			url : dmsCommon.getDmsPath()["report"]
					+ "/homePage/statistical/repairs/summation",
			type : 'GET',
			sucessCallBack : function(data) {
				if ($.trim(data) != "null" && $.trim(data) != "") {
					$("#car_num", container).append(
							' <h3>' + data[0].car_num + '台</h3>');
					$("#cars_num", container).append(
							' <h3>' + data[0].cars_num + '台</h3>');
					$("#productionValue", container).append(
							' <h3>' + data[0].DIS_AMOUNT + '万元</h3>');
				}

			}
		});
	};
	var dataProvider = function(list, trs, graphs) {
		var brand_code = {
			"index" : 0
		};
		$
				.each(
						list,
						function(n, ListValue) {
							var json = {};
							var brandCode = '';
							$
									.each(
											ListValue,
											function(i, value) {
												brandCode = list[n][i].BRAND_CODE;
												if (brand_code[list[n][i].BRAND_NAME] != list[n][i].BRAND_NAME
														|| brand_code[list[n][i].BRAND_NAME] == null) {
													var jsonG = {};
													brand_code[''
															+ list[n][i].BRAND_NAME
															+ ''] = list[n][i].BRAND_NAME
													brand_code['index'] = brand_code['index'] + 1;
													brand_code[''
															+ list[n][i].BRAND_CODE
															+ ''] = brand_code['index'];

													jsonG['balloonText'] = '[[title]] of [[category]]:[[value]]';
													jsonG['fillAlphas'] = brand_code['index'];
													jsonG['id'] = 'AmGraph-'
															+ brand_code[''
																	+ list[n][i].BRAND_CODE
																	+ ''] + '';
													jsonG['type'] = 'column';
													jsonG['title'] = list[n][i].BRAND_NAME == null ? "未知品牌"
															: list[n][i].BRAND_NAME;
													jsonG['valueField'] = 'column-'
															+ brand_code[''
																	+ list[n][i].BRAND_CODE
																	+ ''] + '';
													graphs.push(jsonG);

												}
												json['category'] = list[n][i].EMPLOYEE_NAME;
												json['column-'
														+ brand_code[brandCode]
														+ ''] = list[n][i].SAL_NUM
												// console.log("杜卡迪:"+JSON.stringify(brand_code[brandCode]));
											});
							trs.push(json);
						});

	};

	_toFixed = function(val, d) {
		var s = val + "";
		if (!d)
			d = 0;
		if (s.indexOf(".") == -1)
			s += ".";
		s += new Array(d + 1).join("0");
		if (new RegExp("^(-|\\+)?(\\d+(\\.\\d{0," + (d + 1) + "})?)\\d*$")
				.test(s)) {
			var s = "0" + RegExp.$2, pm = RegExp.$1, a = RegExp.$3.length, b = true;
			if (a == d + 2) {
				a = s.match(/\d/g);
				if (parseInt(a[a.length - 1]) > 4) {
					for (var i = a.length - 2; i >= 0; i--) {
						a[i] = parseInt(a[i]) + 1;
						if (a[i] == 10) {
							a[i] = 0;
							b = i != 1;
						} else
							break;
					}
				}
				s = a.join("").replace(
						new RegExp("(\\d+)(\\d{" + d + "})\\d$"), "$1.$2");
			}
			if (b)
				s = s.substr(1);
			return (pm + s).replace(/\.$/, "");
		}
		return val + "";
	};

	var setRound = function(val) {
		var settlementType = dmsCommon.getSystemParamInfo("8011", "8011");
		var precision = dmsCommon.getSystemParamInfo("1009", "1009");
		var resultVal = "";
		val = parseFloat(val);
		resultVal = val;
		if (precision == "10121001") { // 元
			if (settlementType == "12931001") { // 四舍五入 （整数）
				resultVal = parseFloat(val).toFixed(0);
			} else if (settlementType == "12931002") { // 只舍不如 (整数)
				resultVal = parseInt(val);
			} else if (settlementType == "12931003") { // 只入不舍 (保留整数位)
				resultVal = Math.ceil(val);
			}
		} else if (precision == "10121002") { // 角
			if (settlementType == "12931001") { // 四舍五入
				resultVal = _toFixed(val, 1);
			} else if (settlementType == "12931002") { // 只舍不如
				var tag = '.';
				var str = val + "";
				if (str.indexOf(tag) == -1) {
					resultVal = val.toFixed(1);
				} else {
					resultVal = parseFloat(str.substring(0, str
							.lastIndexOf('.') + 2));
				}
			} else if (settlementType == "12931003") { // 只入不舍

				var bb = val + "";
				var dian = bb.indexOf('.');
				if (dian == -1) {
					resultVal = val.toFixed(1);
				} else {
					var cc = bb.substring(dian + 1, bb.length);
					if (cc.length >= 2) {
						/*
						 * bb=bb.substring(0,dian+2);
						 * resultVal=parseFloat(bb)+parseFloat(0.1);
						 */
						resultVal = (bb.toString().match(/^\d+(?:\.\d{0,1})?/));
					} else {
						resultVal = val.toFixed(1);
					}
				}
			}
		} else if (precision == "10121003") { // 分
			resultVal = val.toFixed(2);
		}

		return resultVal;
	};

	return {
		homePageReport : function(container, url) {
			homePageReport(container, url);
		},
		setRound : function(val) {
			return setRound(val);
		}
	};
}();
