var dmsDict = function() {

	/**
	 * 设置默认值
	 */
	var defaultDictSetting = {
		fieldName : "",
		value : "",
		isInit : "data-isInit=\"true\"",
		classInfo : "",
		minlength : ""
	};
	var dictDataMap;

	var oemDictDataMap;

	/**
	 * 初始化checkBox 对于修改页面，在更新掉默认值后才触发URL操作
	 * 
	 * @param dictValueArray
	 *            checkBox 的数据源
	 * @param checkBoxObj
	 *            ：原始checkBox 对象
	 * @param valueName：checkBox
	 *            Value 的名称
	 * @param descName：checkBox
	 *            DESC 的名称
	 */
	function initCheckBoxValue(valueArray, checkBoxObj, valueName, descName,
			checkBoxContainer,excluedeItems) {
		var name = $(checkBoxObj).attr("name");
		var id = $(checkBoxObj).attr("id");
		var checkAll = $(checkBoxObj).attr("data-allFlag") == "true" ? true
				: false; // 是否默认全部选中
		var dictLabelName = $(checkBoxObj).attr("data-dictLabel");
		dictLabelName = dictLabelName == undefined ? "" : dictLabelName;
		var displayStyle = $(checkBoxObj).attr("data-displayStyle");
		// 此属性主要用于表格中的单选按钮
		var inputName = $(checkBoxObj).attr("data-inputName");

		// 开始构建checkBox信息
		var checkBoxArray = new Array();
		checkBoxArray.push('<div class="checkbox-list" >');
		// 如果是竖向显示，并且定义了LabelName
		if (displayStyle != undefined && displayStyle == "vertical"
				&& dictLabelName != undefined) {
			var htmlStr = "<label class='checkbox-title' ><input type='checkbox' name='"
					+ name
					+ "_all' id='"
					+ (id)
					+ "_all' ><span>"
					+ dictLabelName + "</span></label>";
			checkBoxArray.push(htmlStr);
		}
		// 复制checkBox 对象
		var checkBoxObjClone2 = $(checkBoxObj).clone();
		$
				.each(
						valueArray,
						function(j, value) {
							var dictValue = valueArray[j];
							if (excluedeItems == undefined
									|| excluedeItems.indexOf(dictValue[valueName]) == -1) {
							// 增加checkBoxArray 信息
							checkBoxArray
									.push("<label class='"
											+ (displayStyle != undefined
													&& displayStyle == "vertical" ? "checkbox-vertical"
													: "checkbox-inline") + "'>");
							// 定义checkBox 信息
							var htmlStr = "<input type='checkbox' name='"
									+ name
									+ "' id='"
									+ (id + j)
									+ "' "
									+ (inputName != undefined ? "data-inputName='"
											+ inputName + "'"
											: "") + " value='"
									+ dictValue[valueName] + "'>";
							if (j != 0) {
								checkBoxArray.push(htmlStr);
							} else {
								$(checkBoxObjClone2).attr("id", id + j);
								$(checkBoxObjClone2).attr("value",
										dictValue[valueName]);
							}
							// 生成显示内容框
							
							checkBoxArray
									.push("<span>"
											+ (dictValue[descName] != undefined
													&& isStringNull(dictValue[descName]) == false ? dictValue[descName]
													: dictLabelName)
											+ "</span></label>");
							}
						});
		checkBoxArray.push('</div>');
		$(checkBoxContainer).html(checkBoxArray.join(''));
		// 如果已经禁用
		if ($(checkBoxObj).attr("disabled") == "disabled") {
			$("input[type='checkbox']", $(checkBoxContainer)).attr("disabled",
					"disabled");
		}
		// 插入第一个属性
		$(checkBoxContainer).find("label:not(.checkbox-title):first").prepend(
				checkBoxObjClone2);
		// 更新为已经增加成功
		$("label:not(.checkbox-title) input[type='checkbox']",
				$(checkBoxContainer)).attr("data-isInit", "true");
		// 缓存原始对象
		var checkBoxObjClone = $(checkBoxObj).clone();
		$("div.checkbox-list", $(checkBoxContainer)).data("cloneObj",
				checkBoxObjClone);

		// 判断是否全选
		if (checkAll) {
			$("input[type='checkbox']", $(checkBoxContainer)).prop("checked",
					true);
		} else {
			initData(checkBoxObjClone2);
		}
		// 绑定全选事件
		bindCheckAllEvent(checkBoxObj, $(checkBoxContainer));

		// 删除原节点
		$(checkBoxObj).remove();

	}
	;

	/**
	 * 更新为全部
	 */
	function updateCheckAll(checkBoxObj, checkBoxContainer) {
		var isAllFlag = true;
		var isExistsChildFlag = false;
		$(".checkbox-vertical input[type='checkbox']", checkBoxContainer).each(
				function(index, item) {
					if (!$(item).prop('checked')) {
						isAllFlag = false;
					}
					isExistsChildFlag = true;
				});
		$(".checkbox-title input[type='checkbox'] ", checkBoxContainer).prop(
				"checked", isAllFlag && isExistsChildFlag);
	}

	// 绑定全选事件
	function bindCheckAllEvent(checkBoxObj, checkBoxContainer) {
		// 执行初始化
		$(checkBoxContainer).on("dms.change", function() {
			updateCheckAll(checkBoxObj, checkBoxContainer);
		});
		// 触发变更
		$(checkBoxContainer).trigger("dms.change");

		$("div.checkbox-list .checkbox-title input[type='checkbox']",
				checkBoxContainer).bindChange(
				function(obj) {
					$(".checkbox-vertical input[type='checkbox']",
							$(obj).closest("div.checkbox-list")).prop(
							"checked", $(obj).is(':checked'));
				});

		$("div.checkbox-list .checkbox-vertical input[type='checkbox']",
				checkBoxContainer).bindChange(
				function(obj) {
					if (!$(obj).prop('checked')) {
						$(".checkbox-title input[type='checkbox'] ",
								$(obj).closest("div.checkbox-list")).prop(
								"checked", false);
					} else {
						// 更新全选
						updateCheckAll(checkBoxObj, checkBoxContainer);
					}
				});
	}
	/**
	 * 初始化其中一个checkbox
	 * 
	 * @param checkBoxObj
	 */
	function initCheckBox(checkBoxObj) {
		// 清空多选框
		// $(checkBoxObj).children().remove();
		var checkBoxContainer;
		if ($(checkBoxObj).parent().is('label')) {
			var checkBoxObjNew = $(checkBoxObj).parents(
					"div.checkbox-list:first").data("cloneObj");
			// 更新URL
			$(checkBoxObjNew).attr("data-url", $(checkBoxObj).attr("data-url"));
			// 更新URL
			$(checkBoxObjNew).attr("data-value",
					$(checkBoxObj).attr("data-value"));
			// 更新容器
			checkBoxContainer = $(checkBoxObj).closest("div.checkbox-list")
					.parent();
			checkBoxObj = checkBoxObjNew;
		} else {
			checkBoxContainer = $(checkBoxObj).parent();
		}

		if ($(checkBoxObj).attr("data-model")) {
			var url = $(checkBoxObj).attr("data-url");
			var model = $(checkBoxObj).attr("data-model");
			var excluedeItems = $(checkBoxObj).attr("data-excludeItems");
			if (!isStringNull(url)) {
				dmsCommon.ajaxRestRequest({
					url : dmsCommon.getDmsPath()[model] + url,
					type : 'GET',
					sucessCallBack : function(data) {
						// 初始化数据
						initCheckBoxValue(data, checkBoxObj, $(checkBoxObj)
								.attr("data-labelValue"), $(checkBoxObj).attr(
								"data-lableDesc"), checkBoxContainer,excluedeItems);
					}
				});
			}
		} else {
			if (dictDataMap) {
				var type = $(checkBoxObj).attr("data-type");
				type = (type == undefined ? "dict" : type);
				var dictCode = $(checkBoxObj).attr("data-dictCode");
				var excluedeItems = $(checkBoxObj).attr("data-excludeItems");
				if(type=='dict'){
					dictData = dictDataMap[type];
					var dictValueArray = dictData[dictCode];
					// 初始化数据
					initCheckBoxValue(dictValueArray, checkBoxObj, "code_id",
							"code_cn_desc", checkBoxContainer,excluedeItems);
				}else if(type=='oemDict'){
					dictData = oemDictDataMap[type];
					var dictValueArray = dictData[dictCode];
					// 初始化数据
					initCheckBoxValue(dictValueArray, checkBoxObj, "code_id",
							"code_desc", checkBoxContainer,excluedeItems);
				}
			}
		}
	}
	;

	/**
	 * 触发redio 值更新 对于修改页面，
	 * 
	 * @param valueArray
	 * @param radioBoxObj
	 * @param valueName
	 * @param descName
	 */
	function initRadioValue(valueArray, radioBoxObj, valueName, descName) {
		var fieldName = $(radioBoxObj).attr("data-fieldName");
		// 此属性主要用于表格中的单选按钮
		var inputName = $(radioBoxObj).attr("data-inputName");
		var name = $(radioBoxObj).attr("name");
		var id = $(radioBoxObj).attr("id");
		var classStr = $(radioBoxObj).attr("class");

		var radioArray = new Array();
		radioArray.push('<div class="radio-list" >');

		// 复制checkBox 对象
		var radioBoxObjClone = $(radioBoxObj).clone();

		$
				.each(
						valueArray,
						function(j, value) {
							var dataValue = valueArray[j];
							radioArray.push("<label class=\"radio-inline\">");
							var htmlStr = "<input type=radio name='"
									+ name
									+ "' id='"
									+ (id + j)
									+ "' "
									+ (inputName != undefined ? "data-inputName='"
											+ inputName + "'"
											: "") + " value='"
									+ dataValue[valueName] + "' >";
							if (j == 0) {
								$(radioBoxObjClone).attr("id", id + j);
								$(radioBoxObjClone).attr("value",
										dataValue[valueName]);
							} else {
								radioArray.push(htmlStr);
							}
							radioArray.push("<span>" + dataValue[descName]
									+ "</span></label>");
						});
		radioArray.push('</div>');

		$(radioBoxObj).parent().append(radioArray.join(''));

		// 如果已经禁用
		if ($(radioBoxObj).attr("disabled") == "disabled") {
			$("input[type='radio']", $(radioBoxObj).parent()).attr("disabled",
					"disabled");
		}
		// 插入第一个属性
		$(radioBoxObj).parent().find("label:first").prepend(radioBoxObjClone);
		// 更新为已经增加成功
		$("input[type='radio']", $(radioBoxObj).parent()).attr("data-isInit",
				"true");

		// 初始化选中数据
		initData($("label input[type='radio']:first", $(radioBoxObj).parent()));

		// 删除原节点
		$(radioBoxObj).remove();

	}
	;

	/**
	 * 初始化单选框
	 * 
	 * @param radioBoxObj
	 */
	function initRedio(radioBoxObj) {
		var model = $(radioBoxObj).attr("data-model");
		if (model) {
			var url = $(radioBoxObj).attr("data-url");
			dmsCommon.ajaxRestRequest({
				url : dmsCommon.getDmsPath()[dataModel] + url,
				type : 'GET',
				sucessCallBack : function(data) {
					// 初始化Redio Value
					initRadioValue(data, radioBoxObj, $(radioBoxObj).attr(
							"data-labelValue"), $(radioBoxObj).attr(
							"data-lableDesc"));
				}
			});
		} else {
			// 初始化Redio Value
			if (dictDataMap) {
				var type = $(radioBoxObj).attr("data-type");
				type = (type == undefined ? "dict" : type);
				var dictCode = $(radioBoxObj).attr("data-dictCode");
				if (type == 'dict') {
					dictData = dictDataMap[type];
					var dictValueArray = dictData[dictCode];
					initRadioValue(dictValueArray, radioBoxObj, "code_id",
							"code_cn_desc");
				} else if (type == 'oemDict') {
					dictData = oemDictDataMap[type];
					var dictValueArray = dictData[dictCode];
					initRadioValue(dictValueArray, radioBoxObj, "code_id",
							"code_desc");
				}
			}
		}
	}
	;

	/**
	 * 初始化Select 下拉框的某一个option
	 */
	function initSelectOneOption(selectObj, optionValue, valueName, descName,
			excluedeItems) {
		if (excluedeItems == undefined
				|| excluedeItems.indexOf(optionValue[valueName]) == -1) {
			var optionDesc;
			if (descName.indexOf("{[") >= 0) {
				optionDesc = descName.format(optionValue);
			} else {
				optionDesc = optionValue[descName];
			}
			var selectElement = " <option value='" + optionValue[valueName]
					+ "' >" + optionDesc + "</option>";
			$(selectObj).append(selectElement);
			$("option:last", selectObj).data("optionData", optionValue);
		}
	}
	/**
	 * 初始化下拉框值
	 * 
	 * @param valueArray
	 * @param radioBoxObj
	 * @param valueName
	 * @param descName
	 */
	function initSelectValue(valueArray, selectObj, valueName, descName) {
		// 清空列表
		cleanSelect(selectObj, valueArray);
		var excluedeItems = $(selectObj).attr("data-excludeItems");
		// 展示下拉框值
		if (valueArray) {
			if ($.isArray(valueArray)) {
				$.each(valueArray, function(j, value) {
					// 初始化某一个option值
					initSelectOneOption(selectObj, valueArray[j], valueName,
							descName, excluedeItems);
				});
			} else if (typeof (valueArray) == "object") {
				for ( var key in valueArray) {
					// 初始化某一个option值
					initSelectOneOption(selectObj, valueArray[key], valueName,
							descName, excluedeItems);
				}
			}
		}

		// 刷新表格
		if ($(selectObj).hasClass("bs-select")) {
			$(selectObj).selectpicker('refresh');
		}

		// 标记为已初始化
		$(selectObj).parent().attr("data-isInit", "true");

		// 初始化默认值
		initData(selectObj);
	}
	
	/**
	 * 动态类型下拉框
	 */
	function modeUrl(selectObj) {
		var url = $(selectObj).attr("data-url");
		var model = $(selectObj).attr("data-model");
		var alwaysRefresh = $(selectObj).attr("data-alwaysRefresh");
		var parentDiv = $(selectObj).closest(dmsCommon.DMS_CLOSEST_DIV);
		var cacheData = $(parentDiv).data(url);
		if (cacheData != undefined) {
			initSelectValue(cacheData, selectObj, $(selectObj).attr(
					"data-labelValue"), $(selectObj).attr("data-lableDesc"));
		} else {
			// 定义缓存机制
			if (alwaysRefresh == undefined || alwaysRefresh == "false") {
				// 定义缓存数据
				if (!$(parentDiv).data(url + "_dict")) {
					$(parentDiv).data(url + "_dict", new Array());
					$(parentDiv).data(url + "_dict").push($(selectObj));
				} else {
					$(parentDiv).data(url + "_dict").push($(selectObj));
					return;
				}
			}

			var labelValue = $(selectObj).attr("data-labelValue");
			var labelDesc = $(selectObj).attr("data-lableDesc");
			var dataAjaxSync = $(selectObj).attr("data-ajaxSync");
			dmsCommon
					.ajaxRestRequest({
						url : dmsCommon.getDmsPath()[model] + url,
						type : 'GET',
						async : dataAjaxSync == "true" ? false : true,
						sucessCallBack : function(data) {
							if (alwaysRefresh == undefined
									|| alwaysRefresh == "false") {
								$(parentDiv).data(url, data);
								$.each($(parentDiv).data(url + "_dict"),
										function(index, item) {
											initSelectValue(data, item,
													labelValue, labelDesc);
										});
							} else {
								initSelectValue(data, selectObj, labelValue,
										labelDesc);
							}
						}
					});
		}
	}

	/**
	 * 初始化Select
	 */
	function initSelectData(selectObj) {
		if ($(selectObj).attr("data-model")) {
			modeUrl(selectObj);
		} else {
			if (dictDataMap) {
				var type = $(selectObj).attr("data-type");
				type = (type == undefined ? "dict" : type);
				var dictCode = $(selectObj).attr("data-dictCode");
				var dictValueArray;

				if (type == "dict") {
					dictData = dictDataMap[type];
					dictValueArray = dictData[dictCode];
					initSelectValue(dictValueArray, selectObj, "code_id",
							"code_cn_desc");
				}
				if (type == "oemDict") {
					dictData = oemDictDataMap[type];
					dictValueArray = dictData[dictCode];
					initSelectValue(dictValueArray, selectObj, "code_id",
						"code_desc");
				}
				
				if (type == "oemRegion") {
					dictData = oemDictDataMap[type];
					dictValueArray = getParentData(dictData, selectObj);
					initSelectValue(dictValueArray, selectObj, "region_code",
							"region_name");
				}
				
				// 省级城市区县
				if (type == "region") {
					dictData = dictDataMap[type];
					dictValueArray = getParentData(dictData, selectObj);
					initSelectValue(dictValueArray, selectObj, "region_id",
							"region_name");
				}

			}
		}
	}

	/**
	 * 拿到对应的数据集合
	 */
	function getParentData(dictValueArray, selectObj) {
		var parentArray = new Array();
		var obj = selectObj;
		while ($(obj).attr("parent")) {
			var parentObj = $("#" + $(obj).attr("parent"));
			var provinceVal = parentObj.children("option:selected").val();
			if (provinceVal) {
				parentArray.splice(0, 0, provinceVal);
			} else {
				return undefined;
			}
			obj = parentObj;
		}
		var returnData = dictValueArray;
		$.each(parentArray, function(i, item) {
			returnData = returnData[item].children;
		});
		return returnData;
	}
	/**
	 * 初始化下拉框组件
	 * 
	 * @param selectObj
	 */
	function initSelect(selectObj) {
		var parentDiv = $(selectObj).closest(dmsCommon.DMS_CLOSEST_DIV);
		// 绑定parent 事件
		$(selectObj).data("cascadeData", {});
		bindSelectParentEvent(selectObj, selectObj, parentDiv);
		// 执行select 数据初始化
		initSelectData(selectObj);
	}
	;

	/**
	 * 初始化下拉框组件
	 * 
	 * @param selectObj
	 */
	function initSelectInput(inputObj) {
		var parentDiv = $(inputObj).closest(dmsCommon.DMS_CLOSEST_DIV);
		// 绑定parent 事件
		bindSelectInputParentEvent(inputObj, {}, parentDiv);
		// 执行select 数据初始化
		$(inputObj).trigger("input");
	}
	;

	/**
	 * 绑定parent 事件
	 */
	function bindSelectInputParentEvent(inputObj, selectParentData, container) {
		var inputId = $(inputObj).attr("id");
		// 绑定parent 事件
		if ($("select[parent='" + inputId + "']", container).size() > 0) {
			$(inputObj)
					.off("input")
					.on(
							"input",
							function() {
								$
										.each(
												$("select[parent='" + inputId
														+ "']", container),
												function(i, childObj) {
													// 获取Input 框的值
													var currentVal = $(inputObj)
															.val();
													// 设置默认值
													currentVal = isStringNull(currentVal) ? "-1"
															: currentVal;
													if (currentVal) {
														var parentId = $(
																inputObj).attr(
																"id");
														selectParentData[parentId] = currentVal;
													}
													// 执行动态查询
													selectChangeEvent(childObj,
															selectParentData);
												});
							});

			$.each($("select[parent='" + inputId + "']"),
					function(i, childObj) {
						// 递归绑定
						bindSelectInputParentEvent(childObj, selectParentData,
								container);
					});
		}
	}
	/**
	 * 绑定parent 事件
	 */
	function bindSelectParentEvent(selectObj, rootSelect, container) {
		var selectId = $(selectObj).attr("id");
		// 绑定parent 事件
		if ($("select[parent='" + selectId + "']", container).size() > 0) {
			$(selectObj)
					.off("change")
					.on(
							"change",
							function() {
								if ($(selectObj).attr("parent") == undefined) {
									$(rootSelect).data("cascadeData", {});
								}
								var rootCascadeData = $(rootSelect).data(
										"cascadeData");
								$
										.each(
												$("select[parent='" + selectId
														+ "']", container),
												function(i, childObj) {
													// 动态三级联动(初始化父下拉框的值)
													var currentVal = $(
															selectObj).val();
													if (currentVal) {
														var parentId = $(
																selectObj)
																.attr("id");
														rootCascadeData[parentId] = currentVal;
														// 复制datatype 属性
														if ($(selectObj).attr(
																"data-type")) {
															$(childObj)
																	.attr(
																			"data-type",
																			$(
																					selectObj)
																					.attr(
																							"data-type"));
														}
													}
													// 执行动态查询
													selectChangeEvent(childObj,
															rootCascadeData);
												});
							});

			$.each($("select[parent='" + selectId + "']"),
					function(i, childObj) {
						// 递归绑定
						bindSelectParentEvent(childObj, rootSelect, container);
					});
		}
	}
	/**
	 * 清理下拉框
	 */
	var cleanSelect = function(obj, valueArray) {
		var defautlValue = $(obj).attr("data-existsDefault");
		$(obj).children().remove();
		if ((defautlValue == undefined || defautlValue == "true")) {
			$(obj).append('<option value="">请选择</option>');
		}
	}

	/**
	 * 动态三级联动
	 */
	var selectChangeEvent = function(childObj, selectParentData) {
		// 清空子节点
		initSelectValue(null, $(childObj));
		var initFlag = false;
		// 如果存在parent 属性
		var parentId = $(childObj).attr("parent");
		if (parentId) {
			if (selectParentData[parentId]) {
				initFlag = true;
			}
		} else {
			initFlag = true;
		}
		if (initFlag) {
			var oldUrl = $(childObj).attr("data-url");
			// 如果是URL 格式
			if (oldUrl) {
				var dataUrl = $(childObj).attr("data-url");
				dataUrl = dataUrl.format(selectParentData);
				$(childObj).attr("data-url", dataUrl);
			}
			// 执行数据初始化
			initSelectData(childObj);

			if (oldUrl) {
				$(childObj).attr("data-url", oldUrl);
			}
		}
	};

	/**
	 * 判断是否已经初始过
	 */
	function isInit(obj) {
		// TODO 删除判断逻辑
		if ($(obj).is('select')) {
			if ($(obj).attr("data-excludeInit") == "true") {
				return ($(obj).parent().attr("data-isInit") == "true");
			} else {
				return ($(obj).parent().attr("data-isInit") == "true");
			}
			// return ($(obj).parent("div").attr("data-isInit") ==
			// "true"&&($(obj).attr("data-alwaysRefresh")==undefined||$(obj).attr("data-alwaysRefresh")=="false"));
		} else if ($(obj).attr("type") == "radio") {
			// return
			// ($("label.radio-inline",$(obj).parent().parent()).size()>0);
			return ($(obj).attr("data-isInit") == "true");
		} else if ($(obj).attr("type") == "checkbox") {
			// return
			// ($("label.checkbox-inline",$(obj).parent().parent()).size()>0);
			return ($(obj).attr("data-isInit") == "true");
		}
	}
	/**
	 * 初始化组件
	 */
	var initObject = function(obj) {

		// 如果初始化过
		if (isInit(obj)
				&& ($(obj).attr("data-alwaysRefresh") == undefined || $(obj)
						.attr("data-alwaysRefresh") == "false")) {
			initData(obj);
			return;
		}

		if ($(obj).attr("parent")) {
			return;
		}

		if ($(obj).attr("type") == "radio") {
			initRedio(obj);
			return;
		}

		if ($(obj).attr("type") == "checkbox") {
			initCheckBox(obj);
			return;
		}

		if ($(obj).is('select')) {
			initSelect(obj);
			return;
		}
	}

	/**
	 * 执行dict 初始化
	 */
	var init = function(container) {
		var objList = $(
				"select:not([parent]),input[type='radio']:not([data-index]),input[type='checkbox']:not([data-index],[name='btSelectAll'])",
				container);
		// 对组件进行初始化
		$.each(objList, function(i, obj) {
			initObject(obj);
		});

		// 对于select 的parent 如果是下拉框
		var inputSelectList = $("select[parent]", container);
		// 对组件进行初始化
		$.each(inputSelectList, function(i, obj) {
			var parentId = $(obj).attr("parent");
			var parentObj = $("#" + parentId, container);
			// 如果是input 父节点
			if (parentObj.is("input")) {
				initSelectInput(parentObj);
			}
		});

	};

	/**
	 * 初始化默认值
	 */
	var initData = function(obj) {
		if (isInit(obj)) {
			var objValue = $(obj).attr("data-value");
			if (objValue != undefined) {
				$(obj).setDmsValue(objValue);
				// 触发dms.change
				if ($(obj).attr("type") == "checkbox") {
					var elementContainer = $(obj).parent().is('label') ? $(obj)
							.closest("div.checkbox-list").parent() : $(obj)
							.parent();
					// 进行触发
					$(elementContainer).trigger("dms.change");
				}
			}
		}
	};

	/**
	 * 根据ID 获取对应的描述
	 */
	var getCodeDescById = function(type, codeType, codeId) {
		if (isStringNull(codeId)) {
			return "";
		} else {
			var codeList;
			var desc;
			if(type=='dict'){
				codeList = dictDataMap[type][codeType];
				// 获取符合ID 描述
				var codeObjectArray = $.grep(codeList, function(item, index) {
					return item.code_id == codeId;
				});
				$.each(codeObjectArray, function(j, dictValue) {
					desc = dictValue.code_cn_desc;
				});
			}else if(type=='oemDict'){
				codeList = oemDictDataMap[type][codeType];
				// 获取符合ID 描述
				var codeObjectArray = $.grep(codeList, function(item, index) {
					return item.code_id == codeId;
				});
				$.each(codeObjectArray, function(j, dictValue) {
					desc = dictValue.code_desc;
				});
			}
			return desc;
		}
	};

	/**
	 * 获取下拉框选中的option 的值信息
	 */
	var getSelectedOptionData = function(selectObj) {
		var result = $('option:selected', selectObj).data("optionData");
		if (result) {
			return result;
		} else {
			return {};
		}
	}

	/**
	 * 初始化字典数据
	 */
	var getDictData = function() {
		// 进行ajax 请求
		dmsCommon.ajaxRestRequest({
			url : dmsCommon.getDmsPath()["web"] + "/basedata/dicts",
			type : 'GET',
			sucessCallBack : function(data) {
				dictDataMap = data;
			}
		});
		// 进行ajax 请求
		dmsCommon.ajaxRestRequest({
			url : dmsCommon.getDmsPath()["web"] + "/basedata/oemDicts",
			type : 'GET',
			sucessCallBack : function(data) {
				oemDictDataMap = data;
			}
		});
	};

	return {
		getDictData : function() {
			getDictData();
		},
		init : function(container) {
			init(container);
		},
		initData : function(obj) {
			initData(obj);
		},
		initObject : function(obj) {
			initObject(obj);
		},
		getCodeDescById : function(type, codeType, codeId) {
			return getCodeDescById(type, codeType, codeId);
		},
		// 刷新URL下拉框
		refreshSelectByUrl : function(selectObj, urlData) {
			selectChangeEvent(selectObj, urlData);
		},
		// 刷新下拉框的值by数据
		refreshSelectByData : function(selectObj, valueArray, valueName,
				descName) {
			var data_value = $('option:selected', selectObj).val();
			// 刷新下拉框的默认值
			if (!isStringNull(data_value)) {
				$(selectObj).attr("data-value", data_value);
			}
			initSelectValue(valueArray, selectObj, valueName, descName);
		},
		// 获取下拉框option 的对象信息
		getSelectedOptionData : function(selectObj) {
			return getSelectedOptionData(selectObj);
		},
		// 根据function 刷新
		refreshSelectByFunction : function(selectObj, callBack) {
			callBack(selectObj);
			// 刷新表格
			if ($(selectObj).hasClass("bs-select")) {
				$(selectObj).selectpicker('refresh');
			}
		}

	};
}();
