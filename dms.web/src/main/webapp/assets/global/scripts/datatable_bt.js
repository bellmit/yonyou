/***
Wrapper/Helper Class for datagrid based on Boostrap table plugin
***/
var Datatable = /**
 * @returns {___anonymous52119_54935}
 */
function() {

    var tableOptions; // main options
    var tableOldOptions; //原始Options 属性
    var dataTable; // datatable object
    var table; // actual table jquery object
    var tableContainer; // actual table container object
    var tableWrapper; // actual table wrapper jquery object
    var tableInitialized = false;
    var ajaxParams = {}; // set filter mode
    var parentRowData;
    var operateFormat;
    var exculeTableHeight;
    var isExistAutoValue = false;
    var searchOption;
    var searchForm;

    //设置默认
    var defaultSetting = {
    	defaultAlign:"center", //默认列的对齐方式
    	isShowLineNumber:true  //默认是否显示行号
    };
    
    /**
     * 默认验证框架规则
     */
    var defaultValidateSetting = {
		validateClass:" ",
		validateAttr:" ",
		attr:" "
    };
    
    //定义操作按钮信息
    var defaultOperateFormat={
    		edit:{template:'<a class="btn {0}" {1} title="{2}"  {4} {5} data-tableFlag="true" {6} {7} {[disabled]} >{3}</a>',operateIcon:'<i class="fa fa-lg fa-edit"></i>',defaultTitle:"编辑"},
    		detail:{template:'<a class="btn {0}" {1} title="{2}" {4} {5} data-tableFlag="true" {6} {7} {[disabled]} >{3}</a>',operateIcon:'<i class="fa fa-lg fa-list-alt "></i>',defaultTitle:"明细"},
    		del:{template:'<a class="btn" href="javascript:;" title="{0}" data-url="{1}" data-model="{2}"  data-toggle="confirmation" data-method="{3}" {5} {6} {[disabled]}>{4}</a>',operateIcon:'<i class="font-red fa fa-lg fa-minus-square"></i>',defaultTitle:"删除",defaultMethod:"DELETE"},
    		localDel:{template:'<a class="btn" href="javascript:;" title="{0}" {[disabled]} >{1}</a>',operateIcon:'<i class="font-red fa fa-lg fa-minus-square" ></i>',defaultTitle:"删除"},
    		selectRow:{template:'<a class="btn selectRow" href="javascript:;" title="{0}" {[disabled]} row-index="{[indexNO]}">{1}</a>',operateIcon:'<i class="fa fa-lg fa-share" ></i>',defaultTitle:"选择"},
    		event:{template:'<a class="btn" href="javascript:;" title="{0}" {2} {[disabled]}>{1}</a>',operateIcon:'<i class="glyphicon glyphicon-lg glyphicon-remove"></i>',defaultTitle:"不知道"}
    };
    
    /**
     * 定义分页初始化方法
     */
    var initPagination = function(options){
        if (!$().bootstrapTable) {
            return;
        }
        //更改Url
        options.url = getDmsFuncIdUrl(options.url);
        
        //记录原始的options 属性
        tableOldOptions = options;

        // default settings
        var optionsExtend = $.extend(true, {
            src: "", // actual tableID
            select:true,
        	pagination:true,
        	idField:options.rowID,
        	uniqueId:options.rowID,
        	detailView:false,
        	autoHeight:true,
        	queryParamsType:"limit",
        	sidePagination:"server",
        	pageSize:10,
        	pageList:[10, 25, 50,100],
        	mergeTotal:{
        		megerCellName:"合计"
        	},
        	sortable:true,
        	silentSort:false, //to set when sidePagination set to server
        	clickToSelect:true,
        	cache:false,
        	isQueryFirst:true,
        	isFormParam:true //标记查询时，是否带查询界面的Form 表单
        },defaultSetting, options);
        
        //增加ajax options 参数
        
        
        //默认初始化
        defaultInit(optionsExtend,options);
        
        //分页表格初始化
        paginationTableInit();
    };
    
    /**
     * 定义分页初始化方法
     */
    var initLocale = function(options){
        if (!$().bootstrapTable) {
            return;
        }
        //更改Url
        options.url = getDmsFuncIdUrl(options.url);
        
        //记录原始的options 属性
        tableOldOptions = options;
        
        // default settings
        var optionsExtend = $.extend(true, {
            src: "", // actual tableID
        	pagination:false,
        	idField:options.rowID,
        	uniqueId:options.rowID,
        	detailView:false,
        	autoHeight:false,
        	sidePagination:"client",
        	selectItemName:options.rowID,
        	mergeTotal:{
        		megerCellName:"合计"
        	},
        	sortable:false,
        	clickToSelect:true,
        	cache:false,
        	isQueryFirst:true,
        	isFormParam:false
        },defaultSetting, options);
        
        //默认初始化
        defaultInit(optionsExtend,options);
        
        //本地表格初始化
//        localTableInit();
    };
    
    /**
     * 进行表格扩展
     */
    function defaultInit(optionsExtend,options){
    	 // 创建BS 对象
        tableContainer = optionsExtend.container==undefined?getElementContext():optionsExtend.container;
        table = $("table[id="+optionsExtend.src+"]",tableContainer);
        //查询影响的Form
        searchForm = getTableAffectForm();
        
        
    	//扩展表格高度
    	extendTableHeight(optionsExtend,options);
    	//扩展父表格操作
    	extendParentTable(optionsExtend,options);
    	
        //调用Function 扩展
        extendTableFunction(optionsExtend,options);
        
        //调用Column 扩展
        extendTableColumn(optionsExtend);
        
        //feture 扩展
        extendFeture(optionsExtend,true);
        
        //表头
        extendtablHeader(optionsExtend);
        
        //进行赋值
        tableOptions = optionsExtend;
        
        // initialize a datatable
        dataTable = table.bootstrapTable(tableOptions);
        
        //进行事件绑定
        bindEvent();
        
        //加载特性之后
        extendFeture(optionsExtend,false);
    }
    
    
    /**
     * 进行特性扩展
     */
    function extendFeture(optionsExtend,initBefore){
    	//初始化之前
    	if(initBefore){
    		//如果是第一次调用 
    		if(!optionsExtend.isQueryFirst&&($("#dmsPageContent").data("memorySearchData")==undefined||$(table).closest("div.modal-content").size()>0)){
        		optionsExtend.url = undefined;
        	}
    		//如果发现URL 中存在通配置符，也将URL改为第一次不查询
    		if(optionsExtend.url && optionsExtend.url.indexOf("{[")!=-1){
    			optionsExtend.url = undefined;
    			optionsExtend.isQueryFirst = false;
    		}
    	}else{
    		//还原URL 属性
    		if(optionsExtend.url==undefined && !optionsExtend.isQueryFirst){
    			var tableOption = table.bootstrapTable('getOptions');
        		tableOption.url = tableOldOptions.url;
    		}
    	}
    	
    }
    

	    function extendtablHeader(optionsExtend) {
		if (!optionsExtend.tablHeader)
			return;
		if (optionsExtend.tablHeader[1] instanceof Array) {
			optionsExtend.tablHeader.push(optionsExtend.columns);
			optionsExtend.columns = optionsExtend.tablHeader;
		} else {
			optionsExtend.columns = [ optionsExtend.tablHeader,
					optionsExtend.columns ];
		}
	}
    /**
	 * 分页表格初始化
	 */
    function paginationTableInit(optionsExtend,options){
    	
    }
    /**
     * 本地表格初始化
     */
    function localTableInit(){
    	extendLocalTableStyle();
    }
    /**
     * 分页表格初始化
     */
    function extendTableHeight(optionsExtend,options){
    	if(optionsExtend.autoHeight){
    		optionsExtend.height = 0;
    		//计算表格高度
    		var calPageHeight = calcSurplusHeight(optionsExtend);
    		optionsExtend.height = calPageHeight;
    		optionsExtend.pageSize = calPageSizeByHeight(calPageHeight);
    	}
    	
    	//增加小于10行的分页清单
    	if(optionsExtend.pageSize<10){
    		optionsExtend.pageList.splice(0,0,optionsExtend.pageSize);
    	}
    }
    
    /**
     * 计算剩余高度
     */
    function calcSurplusHeight(optionsExtend){
    	var calHeight = $(window).height()-$('.page-header').height()-$("div.page-bar").height();
    	//计算最上面的tab 页的高度
    	var tabHeight = $("> div.tabbable-custom > ul","#dmsPageContent").height();
    	if(tabHeight&&tabHeight>0){
    		calHeight = calHeight - tabHeight - 20;
    	}
    	var container = $(table).closest("div.dms-search",tableContainer);
    	$("div.panel,ul.nav",container).each(function(index,item){
    		if($(item).find(table).size()==0){
    			if($(item).is("ul")){
    				calHeight = calHeight - $(item).height()-20;
    				return;
    			}
    			if($(item).closest("div.dms-search div.tab-content",tableContainer).size()==0){
    				calHeight = calHeight - $(item).height();
    				return;
    			}
    		}else{
    			calHeight = calHeight - $(item).find("div.panel-heading").height();
    		}
    	});
    	return calHeight-30<50?50:calHeight-30;
    }
    /**
     * 扩展父表格
     */
    function extendParentTable(optionsExtend,options){
    	//如果指定了parentTable,则将URL 清空
    	if(optionsExtend.parentTable){
    		optionsExtend.url = undefined;
    		optionsExtend.isFormParam = false;
    		
    		var thisTableOnDbclick = function(rowData,trElement){
				var newUrl = tableOldOptions.url.format(rowData);
				parentRowData = rowData;
				table.bootstrapTable('refreshOptions',{
					url:getDmsFuncIdUrl(newUrl),
					parentTable:undefined
				});
			};
			//判断是否存在onDblClickRow事件
    		var parentOptions = $("#"+optionsExtend.parentTable,getElementContext()).bootstrapTable('getOptions');
    		//如果已经存在原onDblClickRow
			var newOnDblClickRow;
    		if(parentOptions.onDblClickRow){
    			var oldOnDblClickRow = parentOptions.onDblClickRow;
    			newOnDblClickRow = function(rowData,trElement){
    				//先执行原dbClick,再执行新的click
    				oldOnDblClickRow(rowData,trElement);
    				thisTableOnDbclick(rowData,trElement);
    			}
    		}else{
    			newOnDblClickRow = function(rowData,trElement){
    				//执行新的click
    				thisTableOnDbclick(rowData,trElement);
    			}
    		}
    		var extendParentOptions = $.extend({onDblClickRow:newOnDblClickRow},optionsExtend.parentEvent);
    		$("#"+optionsExtend.parentTable,getElementContext()).bootstrapTable('refreshOptions',extendParentOptions,false);
    	}
    	
    	//如果指定了parentElement,则将URL 清空
    	if(optionsExtend.parentChangeElement){
    		optionsExtend.url = undefined;
    		optionsExtend.isFormParam = false;
    		
    		//绑定处理事件
    		$(optionsExtend.parentChangeElement,getElementContext()).bindChange(function(obj){
    			var elementData = $(obj).parent().serializeFormJson();
    			var newUrl = tableOldOptions.url.format(elementData);
				//如果替换成功
				if(newUrl.indexOf("{[")==-1){
					table.bootstrapTable('refreshOptions',{
						url:getDmsFuncIdUrl(newUrl),
						parentChangeElement:undefined
					});
				}
				
    		});
    	}
    }
    /**
     * 扩展样式
     */
    function extendLocalTableStyle(){
    	table.parent("div.fixed-table-body").css("overflow-x","visible")
    }
    
    /**
     * 扩展属性
     */
    function calPageSizeByHeight(calHeight){
    	var calPageSize=Math.floor((calHeight-50)/30);
		if(calPageSize<10){
			calPageSize = 5;
		}else if(calPageSize>=10 && calPageSize<15){
			calPageSize = 10;
		}else if(calPageSize>=15&&calPageSize<20 ){
			calPageSize = 15;
		}else if(calPageSize>=20){
			calPageSize = 20;
		}
		return calPageSize;
    }
    /**
     * 进行事件绑定
     */
    var bindEvent = function (){
    	var execFlag = 0;
    	//绑定事件
        $(window).on("resize",function () {
    		if(execFlag==0){
    			execFlag = 1;
    			setTimeout(function(){
    				Layout.fixContentHeight();
	    			if($(table).is(":visible")&&tableOptions.autoHeight){
	            		var calHeight =  calcSurplusHeight(tableOptions);
	            		if(calHeight!=tableOptions.height){
	            			table.bootstrapTable('refreshOptions',{height:calHeight,pageSize:calPageSizeByHeight(calHeight)});
		            		tableOptions.height = calHeight;
	            		}
	            	}
    			},100);
    			
    			//1s 后重置标识位，保证在100ms内只发起1个请求
    	    	setTimeout(function(){
    	    		execFlag = 0;
    	    	},1000);
    		}
        }); 
        
        //绑定查询按钮
        table.closest(dmsCommon.DMS_CLOSEST_DIV,tableContainer).find("div.query-btn i.fa-search").parent().each(function() {
        	if(!tableOldOptions.parentTable){
        		var that = this;
        		//加载查询按钮的属性
        		searchOption = dmsCommon.requestSimpleRestFormObj(that,tableContainer);
        		
        		$(that).on('click',function(){
        			refreshSearchQuery(that,searchForm);
                });
        		
        		//绑定回车功能
        		$(tableContainer).bindEnterEvent(function(){
        			refreshSearchQuery(that,searchForm);
        		});
        	}else{
        		$(this).parent().on('dms.parentClick',function(){
        			table.bootstrapTable('refreshOptions',{
    					url:"",
    					data:[]
    				});
                });
        	}
        });
    };
    
    /**
     * 查询执行功能
     */
    function refreshSearchQuery(that,searchForm){
    	//如果表格校验通过
    	if(dmsCommon.validateForm(searchForm)){
        	//禁用按钮
        	$(that).attr("disabled","disabled");
        	//重置为第一页
        	var tableOptions = table.bootstrapTable("getOptions");
        	tableOptions.pageNumber = 1;
        	//刷新表格
        	refreshTableWithForm();
        	//触发事件
    		$(that).trigger("dms.click");
    		//触发父表格事件
    		$(that).trigger("dms.parentClick");
    	}
    }
    
    
    //刷新表格
    var refreshTableWithForm = function(){
     	table.bootstrapTable('refresh');
    };
    
    /**
     * 扩展table对应的Function 功能
     */
    var extendTableFunction = function (optionsExtend,optionsOld){
    	
    	/**
    	 * 设置明细的Detail
    	 */
    	optionsExtend.detailFormatter =function(index, row, element){
    		if(optionsOld.detailFormatter){
    			return optionsOld.detailFormatter(index, row, element);
    		}
    	};
    	/**
    	 * 设置行属性
    	 */
    	optionsExtend.rowAttributes = function(row, index){
    		if(optionsOld.rowAttributes){
    			return optionsOld.rowAttributes(index, row);
    		}
    	};
    	
    	/**
    	 * 当查询成功后的回调函数
    	 */
    	optionsExtend.onLoadSuccess = function(data){
    		//重置横向滚动条
    		table.data('bootstrap.table').$tableBody.scrollLeft(0);
//    		 table.bootstrapTable('resetView');
    		 //绑定加载完成后的事件
    		 bindOnLoadEvent();
    		 //执行初始化--加载自定义方法前
    		 initTableContainerBefore(table);
    		 //自已的初始化操作
    		 if(optionsOld.onLoadSuccess){
        		 optionsOld.onLoadSuccess(data);
    		 }
    		 //执行初始化--加载自定义方法后
    		 initTableContainerAfter(table);
    		 
    		//将查询按钮改为可用
         	if(searchOption&&searchOption.btn){
         		$(searchOption.btn).removeAttr("disabled");
         	}
         	//添加合计
         	if(optionsExtend.mergeTotal){
         		var merge= optionsExtend.mergeTotal;
         		if(merge.megerCellNumber){
         			mergeTotalTable(merge.megerCellNumber,merge.megerCellName);
         		}
         	}
        };
        
        
        /**
         * 设置查询条件
         */
        if(optionsExtend.isFormParam){
        	optionsExtend.queryParams = function(params){
        		
        		$(searchForm).each(function() {
            		 params = $.extend(true,params,$(this).serializeFormJson());
                 });
            	if(optionsOld.queryParams){
            		params = $.extend(true,params,optionsOld.queryParams(params));
            	}
            	//将参数中是数组的属性改为用","分隔的字符串
            	$.each(params,function(name,value){
            		if($.isArray(value)){
            			params[name]=value.join(",");
            		}
            	});
            	
            	//TODO after remove
            	var dmsFuncId = $("#dmsPageContent").data("dmsFuncId");
            	if(dmsFuncId){
            		params["dmsFuncId"] = dmsFuncId;
            	}
            	var currentToken = dmsCommon.getCurrentToken();
            	if(currentToken){
            		params["urlToken"] = currentToken;
            	}
            	return params;
            };
        }else{
        	optionsExtend.queryParams = function(params){
            	//TODO after remove
            	var dmsFuncId = $("#dmsPageContent").data("dmsFuncId");
            	if(dmsFuncId){
            		params["dmsFuncId"] = dmsFuncId;
            	}
            	var currentToken = dmsCommon.getCurrentToken();
            	if(currentToken){
            		params["urlToken"] = currentToken;
            	}
            	return params;
            };
        }
        
        /**
         * 当加载出错时处理函数
         */
        optionsExtend.onLoadError = function(status,resource){
        	status=status==0?"timeout":status;
        	//执行状态码处理
        	dmsCommon.statusCodeHandel(searchOption,true)[status](resource);
        	//将查询按钮改为可用
        	if(searchOption&&searchOption.btn){
        		$(searchOption.btn).removeAttr("disabled");
        	}
        	return;
        };
        
        /**
         * 在加载数据前进行数据预处理操作
         */
        optionsExtend.onPreBody = function(data){
        	if(optionsOld.onPreBody){
        		optionsOld.onPreBody(data);
        	}
        };   
        //定义单击事件
        optionsExtend.onClickCell = function(field, value, row, element){
        	 $("tr.selected",table).removeClass("selected");
			 $(element).closest("tr").addClass("selected");
        };
    };
    
    /**
     * 初始化表格数据
     */
    function initTableContainerBefore(container){
    	//执行初始化
		 dmsCommon.init(container);
		 dmsCommon.afterInit(container);
    }
    
    /**
     * 获得影响表格的Form
     */
    function getTableAffectForm(){
    	var tableForm =table.closest("div.dms-search",tableContainer).find("form:first")
		if(tableForm==undefined||$(tableForm).size()==0){
			tableForm = table.closest("form",tableContainer);
		}
    	return tableForm;
    }
    
    /**
     * 初始化表格数据
     */
    function initTableContainerAfter(container){
    	//绑定事件
    	bindTableAutoValue(container);
    	
    	//修改表格里对应的样式
    	updateInputElementStyle(container);
    }
    
    /**
     * 修改表格里的样式
     */
    function updateInputElementStyle(container){
    	$(".form-control[type!='hidden']",container).each(function(index,item){
    		var tdElement = $(item).closest("td");
    		$(tdElement).css("padding-left","1px");
    		$(tdElement).css("padding-right","1px");
    	});
    }
    /**
     * 绑定表格中的自动计算公式
     */
    function bindTableAutoValue(container){
    	if(isExistAutoValue){
    		var tableOptions = table.bootstrapTable("getOptions");
        	var columns = tableOptions.columns;
        	var columnIndexArray = new Array();
        	//拿到存在自动公式计算的列
        	var visiableNumber = 0;
        	var autoValueColumns = $.grep(columns[0],function(item,index){
        		if(item.visible==false){
        			visiableNumber ++;
        		}
        		if(item.numberFormat&&item.numberFormat.autoValueFormat){
        			columnIndexArray.push(index-visiableNumber);
        			return true;
        		}else{
        			return false;
        		}
        	});
        	//遍历自动计算公式
        	$.each(autoValueColumns,function(index,item){
        		var autoValueFormat = item.numberFormat.autoValueFormat;
        		var autoValueAfterEvent = autoValueFormat.afterEvent;
        		var columnIndex = columnIndexArray[index]+1;
        		var autoValueCells = $("td:nth-child("+columnIndex+") span[data-tableAutoValue] ",container);
        		//循环计算
        		$.each(autoValueCells,function(j,cell){
        			var formual = $(cell).attr("data-tableAutoValue");
        			$(cell).bindAutoValueEvent(formual,$(cell).closest("tr"),autoValueAfterEvent);
        		})
        	});
        	
    	}
    }
    /**
     * 绑定加载完成后事件
     */
    function bindOnLoadEvent(){
    	//循环操作
    	$(operateFormat).each(function(i,operteitem){
    		if(operteitem.callBack){
    			$("a[data-callBack='true'][title='"+operteitem.title+"']",table).on("callBack.dms",function(event,response){
    				operteitem.callBack(response);
    	    	});
    		}
    	});
    	
    	//绑定初始化表格事件
		$("a[data-tableFlag]",table).on("initTableData.dms",function(event){
			var index = $(this).parents("tr:first").attr("data-index");
			var rowData = getRowDataByIndex(index);
			$(this).data("pageData",rowData);
    	});
		//绑定选择某一行数据的功能
		//绑定初始化表格事件
		$("a.selectRow[row-index]",table).on("click",function(event){
			var rowElement = $(this).closest("tr");
			var index = $(rowElement).attr("data-index");
			var rowData = getRowDataByIndex(index);
			//获得tableOption
			var tableOptions = table.bootstrapTable("getOptions");
			//如果存在dbClick函数
			if(tableOptions&&tableOptions.onDblClickRow){
				tableOptions.onDblClickRow(rowData,rowElement);
			}
			
    	});
    }
    /**
     * 扩展Column 信息
     * 1、增加序号列
     * 2、增加默认排序方式
     * 3、
     */
    var extendTableColumn = function(optionsExtend){
    	
    	var lineColumn = [{title:"序号",sortable:false,formatter: function (value, row, index) {return index+1;}}];
    	/**
    	 * 开启行号功能
    	 */
    	if(optionsExtend.isShowLineNumber){
    		optionsExtend.columns = $.merge(lineColumn,optionsExtend.columns);
    	}
    	
    	/**
    	 * 检查字段是否需要格式化，如果需要格式化，则进行对应的格式化操作
    	 */
    	$.each(optionsExtend.columns,function(i, item){
    		 //默认格式化
    		 extendDefaultFormat(item,optionsExtend);
    		 
			 //对日期进行格式化
			 if(extendDateFormat(item,optionsExtend)){
				 return true;
			 };
			 
			 //对数字进行格式化
			 if(extendNumberFormat(item,optionsExtend)){
				 return true;
			 };
			 
			 //对操作函数进行格式化
			 if(extendOperateFormat(item,optionsExtend,i)){
				 return true;
			 };
			 
			 //对字段代码进行转换
			 if(extendCodeFormat(item,optionsExtend)){
				 return true;
			 };
			 
			 //对最大显示长度进行格式化
			 if(extendMaxLengthFormat(item,optionsExtend)){
				 return true;
			 }
			 
			 //对文本输入框进行扩展
			 if(extendInputTextFormat(item,optionsExtend)){
				 return true;
			 };
			 
			 //对数字输入框进行扩展
			 if(extendInputNumberFormat(item,optionsExtend)){
				 return true;
			 };
			 
			 //对下拉框进行扩展
			 if(extendInputSelectFormat(item,optionsExtend)){
				 return true;
			 };
			 
			 //对checkBox输入框进行扩展
			 if(extendInputCheckBoxFormat(item,optionsExtend)){
				 return true;
			 };
			 
			 //对单选按钮进行扩展
			 if(extendInputRadioFormat(item,optionsExtend)){
				 return true;
			 };
			 
			 //对输入日期框进行格式化
			 if(extendInputDateFormat(item,optionsExtend)){
				 return true;
			 };
			//对输入日期框进行格式化
			 if( extendInputFileFormat(item,optionsExtend)){
				 return true;
			 };
			 //对输入日期时间框进行格式化
			 if(extendInputDateTimeFormat(item,optionsExtend)){
				 return true;
			 };
			 
			 //对瘾藏参数进行格式化
			 extendInputHiddenFormat(item,optionsExtend);
			
    	});
    };
    
    /**
     * 扩展CheckBox 
     */
    function extendInputCheckBoxFormat(item,optionsExtend){
    	if(item.inputCheckBoxFormat){
			 item.formatter = function(value, row, index){
				value = formatNull(value);
				if(value=="" && item.inputCheckBoxFormat.defaultValue){
					 value = item.inputCheckBoxFormat.defaultValue;
				}
				var templete = '<input id="'+item.inputField+index+'" name="'+item.inputField+index+'" data-inputName="'+item.inputField+'" type="checkbox" data-type="'+item.inputCheckBoxFormat.type+'" data-dictCode="'+item.inputCheckBoxFormat.codeType+'" data-value="'+value+'" class="{[validateClass]}" {[validateAttr]} {[otherAttr]}/>';
				
				//加载其它属性
				var attrArray = new Array();
				//如果是禁用类型
				if(item.inputCheckBoxFormat.disabled){
					attrArray.push("disabled='disabled'");
				}
				var otherAttr = {};
				otherAttr.otherAttr = attrArray.join(" ");
				templete = templete.format($.extend({},defaultValidateSetting,item.inputCheckBoxFormat.validate,otherAttr));
				return templete;
			 };
			 return true;
		 }
    	return false;
    }
    
    /**
     * 扩展Radio
     */
    function extendInputRadioFormat(item,optionsExtend){
    	if(item.inputRadioFormat){
			 item.formatter = function(value, row, index){
				value = formatNull(value);
				if(value=="" && item.inputRadioFormat.defaultValue){
					 value = item.inputRadioFormat.defaultValue;
				}
				var templete = '<input id="'+item.inputField+index+'" name="'+item.inputField+index+'" data-inputName="'+item.inputField+'"  type="radio" data-type="'+item.inputRadioFormat.type+'" data-dictCode="'+item.inputRadioFormat.codeType+'" data-value="'+value+'" class="{[validateClass]}" {[validateAttr]}/>';
				templete = templete.format($.extend({},defaultValidateSetting,item.inputRadioFormat.validate));
				return templete;
			 };
			 return true;
		 }
    	return false;
    }
    
    /**
     * 扩展select下拉框
     */
    function extendInputSelectFormat(item,optionsExtend){
    	if(item.inputSelectFormat){
			 item.formatter = function(value, row, index){
				 value = formatNull(value);
				 if(value=="" && item.inputSelectFormat.defaultValue){
					 value = item.inputSelectFormat.defaultValue;
				 }
				 if(item.inputSelectFormat.model){
					 var newUrl = item.inputSelectFormat.url;
					 newUrl = newUrl.replace(new RegExp(/(\]\})/g),index+"]}");
					 var modalTemplete = '<select id="'+item.inputField+index+'" name="'+item.inputField+index+'" data-inputName="'+item.inputField+'" class="form-control {[validateClass]}" {[validateAttr]} {[otherAttr]} data-url="'+newUrl+'" data-model="'+item.inputSelectFormat.model+'" data-labelValue="'+item.inputSelectFormat.labelValue+'" data-lableDesc="'+item.inputSelectFormat.lableDesc+'" data-value="'+value+'" {[parent]} ></select>';
					 var formatObj = {};
					 formatObj.parent = item.inputSelectFormat.parent!=undefined?"parent="+item.inputSelectFormat.parent+index:"";
					 //生成格式化操作
					 var attrArray = new Array();
					 //如果是禁用类型
					 if(item.inputSelectFormat.disabled){
						 attrArray.push("disabled='disabled'");
					 }
					 //otherAttr 属性
					 if(item.inputSelectFormat.attr){
						 attrArray.push(item.inputSelectFormat.attr);
					 }
					 //重置规则
					 var otherAttr = {};
					 otherAttr.otherAttr = attrArray.join(" ");
					 
					 formatObj = $.extend({},formatObj,defaultValidateSetting,item.inputSelectFormat.validate,otherAttr);
					 modalTemplete = modalTemplete.format(formatObj);
					 return modalTemplete;
				 }else{
					 var templete = '<select id="'+item.inputField+index+'" name="'+item.inputField+index+'" data-inputName="'+item.inputField+'" class="form-control {[validateClass]}" {[validateAttr]} {[otherAttr]} data-type="'+item.inputSelectFormat.type+'" data-dictCode="'+item.inputSelectFormat.codeType+'" data-value="'+value+'" ></select>';
					 
					 //生成格式化操作
					 var attrArray = new Array();
					 //如果是禁用类型
					 if(item.inputSelectFormat.disabled){
						 attrArray.push("disabled='disabled'");
					 }
					 //otherAttr 属性
					 if(item.inputSelectFormat.attr){
						 attrArray.push(item.inputSelectFormat.attr);
					 }
					 //重置规则
					 var otherAttr = {};
					 otherAttr.otherAttr = attrArray.join(" ");
					 
					 templete = templete.format($.extend({},defaultValidateSetting,item.inputSelectFormat.validate,otherAttr));
					 return templete;
				 }
			 };
			 return true;
		 }
    	return false;
    }
    /**
     * 扩展input 输入框
     */
    function extendInputTextFormat(item,optionsExtend){
    	if(item.inputTextFormat){
			 item.formatter = function(value, row, index){
				 //设置默认值
				 if(item.inputTextFormat.defaultValue){
					 value = value==undefined?item.inputTextFormat.defaultValue:value;
				 }
				 value = formatNull(value);
				 var templete = '<input id="'+item.inputField+index+'" name="'+item.inputField+index+'" data-inputName="'+item.inputField+'" type="text" class="form-control {[validateClass]}" {[validateAttr]}  value = "'+value+'"/>';
				 templete = templete.format($.extend({},defaultValidateSetting,item.inputTextFormat.validate));
				 return templete;
			 };
			 return true;
		 }
    	return false;
    }
    /**
     * 扩展input hidden框
     */
    function extendInputHiddenFormat(item,optionsExtend){
    	if(item.inputHiddenFormat){
			 item.formatter = function(value, row, index){
				 //设置默认值
				 if(item.inputHiddenFormat.defaultValue){
					 value = value==undefined?item.inputHiddenFormat.defaultValue:value;
				 }
				 value = formatNull(value);
				 return value+=returnHiddenFormat(item,value, row, index);
			 };
		 }
    }
    
    /**
     * 扩展input 输入框
     */
    function extendInputNumberFormat(item,optionsExtend){
    	if(item.inputNumberFormat){
			 item.formatter = function(value, row, index){
				 //设置默认值
				 if(item.inputNumberFormat.defaultValue){
					 value = value==undefined?item.inputNumberFormat.defaultValue:value;
				 }
				 value = formatNull(value);
				 var templete = '<input id="'+item.inputField+index+'" name="'+item.inputField+index+'" data-inputName="'+item.inputField+'" type="text" class="form-control {[validateClass]}" {[validateAttr]} value="'+value+'"/>';
				 templete = templete.format($.extend({},defaultValidateSetting,item.inputNumberFormat.validate));
				 return templete;
			 };
			 return true;
		 }
    	 return false;
    }
    
    /**
     * 扩展input 日期框
     */
    function extendInputDateFormat(item,optionsExtend){
    	if(item.inputDateFormat){
			 item.formatter = function(value, row, index){
				 //设置默认值
				 if(item.inputDateFormat.defaultValue){
					 value = value==undefined?item.inputDateFormat.defaultValue:value;
				 }
				 value = formatDate(value);
				 var templete = '<div class="input-group date date-picker" data-date="'+value+'" {[attr]} > <input id="'+item.inputField+index+'" name="'+item.inputField+index+'" data-inputName="'+item.inputField+'" readonly class="form-control {[validateClass]}" {[validateAttr]} type="text" value="'+value+'" /> <span class="input-group-btn"> <button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button></span></div>';
				 templete = templete.format($.extend({},defaultValidateSetting,item.inputDateFormat,item.inputDateFormat.validate));
				 return templete;
			 };
			 return true;
		 }
    	return false;
    }
    
    /**
     * 扩展input 日期时间框
     */
    function extendInputDateTimeFormat(item,optionsExtend){
    	if(item.inputDateTimeFormat){
			 item.formatter = function(value, row, index){
				 //设置默认值
				 if(item.inputDateTimeFormat.defaultValue){
					 value = value==undefined?item.inputDateTimeFormat.defaultValue:value;
				 }
				 value = formatDate(value);
				 var templete = '<div class="input-group date datetime" data-date="'+value+'" {[attr]} > <input id="'+item.inputField+index+'" name="'+item.inputField+index+'" data-inputName="'+item.inputField+'" readonly 	class="form-control {[validateClass]}" {[validateAttr]}  type="text" value="'+value+'"/>  <span class="input-group-addon"><span class="fa fa-calendar"></span></span> </div>';
				 templete = templete.format($.extend({},defaultValidateSetting,item.inputDateTimeFormat,item.inputDateTimeFormat.validate));
				
				 return templete;
			 };
			 return true;
		 }
    	return false;
    }
    
    /**
     * 扩展input 输入框
     */
    function extendInputFileFormat(item,optionsExtend){
    	if(item.inputFileFormat){
			 item.formatter = function(value, row, index){
				 value = formatNull(value);
				 var templete = '<input id="dmsFile'+index+'" name="dmsFile" data-inputName="'+item.inputField+'" type="file" class="importFiles {[validateClass]}" {[validateAttr]} {[otherAttr]} data-billId="'+value+'" data-billType="'+item.inputFileFormat.billTypeCode+'" value=""/>';
				 //绑定返回值
				 templete+="<input type='hidden' id = '"+item.inputField+index+"' value='' name='"+item.inputField+"'/>";
				 //如果不为空
				 if(!isStringNull(value)){
					 //item.inputFileFormat.validate.otherAttr="data-isComplete = 'true'";
				 }
				 templete = templete.format($.extend({},defaultValidateSetting,item.inputFileFormat.validate));
				 return templete;
			 };
			 return true;
		 }
    	 return false;
    }
    /**
     * 扩展自动计算Format
     */
    function extendAutoValueFormat(item,optionsExtend){
    	if(item.autoValueFormat){
   		 item.align = "right"; //设置默认对齐
   		 var numberFormat = $.extend({},{
   			 decimal:0,
   		 },item.numberFormat);
   		 item.formatter = function(value, row, index){
   			 var result = "";
   			 if(value!=undefined){
   				 result = formatNumber(value,numberFormat);
   			 }
   			 if(item.inputHiddenFormat){
   				 result+=returnHiddenFormat(item,value, row, index);
   			 }
   			 return result;
   		 };
   		 return true;
   	 }
   	 return false;
    }
    
    /**
     * 默认格式化
     */
    function extendDefaultFormat(item,optionsExtend){
    	//设置默认对齐方式&是否排序
		 if(!item.align){
			 item.align = "center";
		 }
		 if((item.sortable==undefined)&&(item.checkbox==undefined)){
			 item.sortable = true;
		 }
		 
		 //如果inputField没有设置，则使用item.field 来代替
		 if(item.inputField==undefined){
			 item.inputField = item.field;
		 }
		 
		 //如果设置了最小宽度
		 if(item.minWidth){
			 item.title = getAppendLenString(item.title,item.minWidth);
		 }
		 
		 //如果有isVisible 这个参数
		 if(item.isVisible){
			 var isVisable = item.isVisible(item);
			 if(!isVisable){
				 item.visible = false; 
			 }
		 }
		 
    }
    
    /**
     * 日期时间格式化
     */
    function extendDateFormat(item,optionsExtend){
		 if(item.dateFormat){
			 item.align = "center";
			 item.formatter = function(value, row, index){
				 //设置默认值
				 if(item.dateFormat.defaultValue){
					 value = value==undefined?item.dateFormat.defaultValue:value;
				 }
				 var result =  formatDate(value,item.dateFormat.format);
				 if(item.inputHiddenFormat){
					 result+=returnHiddenFormat(item,value, row, index);
				 }
				 return result;
			 };
			 return true;
		 }
		 return false;
    }
    /**
	  * 数字格式化
	  */
   function extendNumberFormat(item,optionsExtend){
	 if(item.numberFormat){
		 item.align = "right"; //设置默认对齐
		 item.formatter = function(value, row, index){
			 //设置默认值
			 if(item.numberFormat.defaultValue){
				 value = value==undefined?item.numberFormat.defaultValue:value;
			 }
			 var result=returnAutoValueFormat(item,value, row, index);
			 result = result || "";
			 if(item.inputHiddenFormat){
				 result+=returnHiddenFormat(item,value, row, index);
			 }
			 return result;
		 };
		 return true;
	 }
	 return false;
   }
   
   /**
    * 返回自动计算的公式
    */
   function returnAutoValueFormat(item,value, row, index){
	   //定义格式
	   var numberFormat = $.extend({},{
			 decimal:0,
		 },item.numberFormat);
	   
	   if(value!=undefined&&!item.numberFormat.autoValueFormat){
		  return formatNumber(value,numberFormat);
	   }
	   //如果存在自动公式
	   if(item.numberFormat.autoValueFormat){
		   //更新为存在自动计算公式
		   if(!isExistAutoValue){
			   isExistAutoValue = true;
		   }
		   var autoValueFormat = item.numberFormat.autoValueFormat;
		   var template = "<span data-tableAutoValue={1} {2}>{0}</span>";
		   var argsArray = new Array();
		   if(value!=undefined){
			   argsArray.push(formatNumber(value,numberFormat));
		   }else{
			   argsArray.push("");   
		   }
		   //解析公式
		   var fieldArray = analysisFormual(autoValueFormat.autoValue);
		   var newAutoValue=autoValueFormat.autoValue;
		   $(fieldArray).each(function(i,idValue){
			   newAutoValue=newAutoValue.replace(idValue,idValue+index);
		   });
		   argsArray.push(newAutoValue);
		   argsArray.push("data-autoValueDigits="+numberFormat.decimal);
		   return template.format(argsArray);
	   }
   }
 
   	/**
	  * 数字格式化
	  */
	function extendMaxLengthFormat(item,optionsExtend){
		 if(item.maxLengthFormat){
			 var maxLengthFormat = $.extend({},{
			 },item.maxLengthFormat);
			 item.formatter = function(value, row, index){
				 var result = "";
				 if(value){
					 result = formatMaxShowLength(value,maxLengthFormat);
				 }
				 if(item.inputHiddenFormat){
					 result+=returnHiddenFormat(item,value, row, index);
				 }
				 return result;
			 };
			 return true;
		 }
		 return false;
	}
   
    /**
	  * 对代码进行转换
	  */
    function extendCodeFormat(item,optionsExtend){
		 if(item.codeFormat){
			 var codeForamt = item.codeFormat;
			 item.formatter = function(value, row, index){
				 var type = codeForamt.type;
				 var codeType = codeForamt.codeType;
				 if(codeForamt.defaultValue&&isStringNull(value)){
					 value = codeForamt.defaultValue;
				 }
				 var result =  null;
				 if(type=='dict'){
					 result = dmsDict.getCodeDescById(type,codeType,value);
				 }else if(type=='oemDict'){
					 result = dmsDict.getCodeDescById(type,codeType,value);
				 }
				 if(item.inputHiddenFormat){
					 result+=returnHiddenFormat(item,value, row, index);
				 }
				 return result;
			 };
			 return true;
		 }
		 return false;
    }
    
    /**
     * 增加空格数量
     */
    function addBlankByLength(title,length){
    	if(length>1){
    		for(var i=1;i<=length;i++){
    			title = "&nbsp;&nbsp;"+title+"&nbsp;&nbsp;";
    		}
    	}
    	return title;
    }
    
    /**
     * 对操作函数进行格式化
     */
    function extendOperateFormat(item,optionsExtend,i){
    	//操作格式化
		 if(item.operateFormat){
			 item.sortable = false; //把排序关闭
			 var linkNum = item.operateFormat.length;
			 /**
			  * 扩站标题的宽度，以保证不会换行
			  */
			 var formats = [];
			 var operateEvents = {};
			 $.each(item.operateFormat,function(j,item){
				 //拿到模板
				 var defaultSetting = defaultOperateFormat[item.type];
				 var templete = defaultSetting.template;
				 if(item.type&&(item.type=="edit"||item.type=="detail")){
					 beforeEditRowEvent(item);
					 formats.push(handelEditADetail(templete,item,defaultSetting,operateEvents));
				 }else if(item.type&&(item.type=="del")){
					 //后台删除
					 formats.push(handelDelete(templete,item,defaultSetting,optionsExtend));
					 
				 }else if(item.type&&(item.type=="localDel")){
					 //定义本地删除的事件
					 defineLocaleDeleteRowEvent(item);
					 formats.push(handelEvent(templete,item,defaultSetting,operateEvents));
				 }else{
					 formats.push(handelEvent(templete,item,defaultSetting,operateEvents));
				 }
				 //如果是双击事件
				 if(item.doubleClick&&item.doubleClick==true){
					 optionsExtend.onDblClickRow = function(row,element){
						 $("td:eq("+(optionsExtend.detailView==true?(i+1):i)+") a[title=\""+(item.title!=undefined?item.title:defaultSetting.defaultTitle)+"\"]",element).click();
					 }
				 }
			 });
			 //指定格式化
			 item.formatter = function(value, row, index){
				 var alreadyFormats = new Array;
				 //循环
				 $.each(formats,function(formatIndex,itemStr){
					 var operatorDefine = item.operateFormat[formatIndex];
					 var disabledObj = {disabled:" "};
					 if(operatorDefine.isShow){
						 var isShowResult = operatorDefine.isShow(value, row, index);
						 if(!isShowResult){
							 if(operatorDefine.disableHidden){
								 disabledObj = {disabled:"style='display:none;'"};
							 }else{
								 disabledObj = {disabled:"disabled=disabled"};
							 }
						 }
					 }
					 //循环
					 alreadyFormats[formatIndex] = itemStr.format(disabledObj);
				 });
				 
				 var formatstr = alreadyFormats.join("");
				 var result;
				 var formatJson = $.extend({},row);
				 result =  formatstr.format(formatJson);
				 if(item.inputHiddenFormat){
					 result+=returnHiddenFormat(item,value, row, index);
				 }
				 return result;
			 };
			 //绑定事件
			 item.events = operateEvents;
			 
			 //对操作列进行赋值
			 operateFormat = item.operateFormat;
			 
			 return true;
		 }
		 return false;
    }
    
    /**
     * 返回hidden 的值
     * @param item
     * @param value
     * @returns {String}
     */
    function returnHiddenFormat(item,value,row,index){
    	if(item.inputHiddenFormat){
    		var hiddenFields = item.inputHiddenFormat.hiddenField==undefined?"":item.inputHiddenFormat.hiddenField;
    		var hiddenField=hiddenFields.split(",");
    		//判断是否设置了隐藏的名称，如果设置了，则用自行设置的名称
    		var hiddenNames = item.inputHiddenFormat.hiddenFieldName==undefined?item.inputField:item.inputHiddenFormat.hiddenFieldName;
    		var hiddenName=hiddenNames.split(",");
    		var hiddenFormat="";
    		for(var i=0;i<hiddenField.length;i++){
	    		var hiddenValue = value;
	    		if(item.inputHiddenFormat.hiddenField){
	    			hiddenValue = row[hiddenField[i]];
	    		}
	    		hiddenValue = hiddenValue==undefined?"":hiddenValue;
	    		hiddenFormat=hiddenFormat+'<input id="'+hiddenName[i]+index+'" name="'+hiddenName[i]+'" type="hidden" value = "'+hiddenValue+'"/>';
    		}
			return hiddenFormat;
    	}
    }
    
    /**
     * 编辑前事件
     */
    function beforeEditRowEvent(item) {
    	item.onclick = function(value, row, index,obj) {
    	    var beforeResult;
 			if (item.onBeforeEvent) {
 				beforeResult = executeRowEvent(value, row, index,item.onBeforeEvent,obj);;
 			}
    	}
    }
    /**
     * 删除一行的function
     */
	function defineLocaleDeleteRowEvent(item) {
		// 本地删除
		item.onclick = function(value, row, index,obj) {
			//如果value 未定义，则使用index 作为value
			value =value==undefined?index:value;
			row = getRowDataByIndex(index);
			// 调用行触发前事件
			
			var beforeResult;
			if (item.onBeforeEvent) {
				beforeResult = executeRowEvent(value, row, index,item.onBeforeEvent,obj);
			}
			// 执行删除操作
			if(beforeResult==undefined || beforeResult!=false){
				//删除数据
				deleteRowByIndex(index);

				// 调用行触发后事件
				if (item.onAfterEvent) {
					executeRowEvent(value, row, index,item.onAfterEvent,obj);
				}
			}
		}
	}
	
	/**
	 * 执行指定的行事件
	 */
	function executeRowEvent(value, row, index,func,obj){
		var returnResult;
		if ($.isFunction(func)) {
			returnResult =  func(value, row, index,obj);
		} else {
			returnResult =  eval(func)(value, row, index,obj);
		}
		if(returnResult==undefined){
			return true;
		}
		return returnResult;
	}
	
    /**
     * 返回格式化字符串,针对页面
     */
    function handelEditADetail(templete,item,defaultSetting,operateEvents){
    	var args = [];
		 //定义跳转页面
		 if(item.openType&&item.openType=="jump"){
			 args.push("ajaxify");
			 args.push('href="'+item.url+'"');
		 }else{
			 args.push("");
			 args.push('data-url="'+item.url+'"');
		 }
		
		 if(item.title){
			 args.push(item.title);
		 }else{
			 args.push(defaultSetting.defaultTitle);
			 item.title = defaultSetting.defaultTitle;
		 }
		 
		 if(item.operateIcon){
			 args.push(item.operateIcon);
		 }else{
			 args.push(defaultSetting.operateIcon);
		 }
		 if(item.openType==undefined||item.openType=="open"){
			 args.push('data-toggle="modal"');
			 if(item.openWidth){
				 args.push('data-width="'+item.openWidth+'"');
			 }else{
				 args.push('');
			 }
		 }else{
			 args.push('');
			 args.push('');
		 }
		 
		 //设置detail flag
		 if(item.type=="detail"){
			 args.push('data-isDetailFlag="true"');
		 }else{
			 args.push('');
		 }
		 
		 //如果存在其他属性
		 if(item.attr){
			 args.push(item.attr);
		 }else{
			 args.push('');
		 }
		 
		 if(item.onclick){
			 operateEvents['click [title="'+item.title+'"]'] = function(e, value, row, index) {
				if($(this).isDisabled()){
						return false;
				}
			    if($.isFunction(item.onclick)){
			    	item.onclick(value, row, index,this);
			    }else{
					eval(item.onclick)(value, row, index,this);
			    }
			};
		 }
		 
		 return templete.format(args);
    };
    
    /**
     * 返回格式化字符串,针对页面
     */
    function handelDelete(templete,item,defaultSetting,optionsExtend){
    	var args = [];
		 //定义跳转页面
    	 var title;
    	 if(item.title){
			 args.push(item.title);
		 }else{
			 args.push(defaultSetting.defaultTitle);
			 item.title = defaultSetting.defaultTitle;
		 }
		 args.push(item.url);
		 args.push(item.model);
		 if(item.method){
			 args.push(item.method);
		 }else{
			 args.push(defaultSetting.defaultMethod);
		 }
		 if(item.operateIcon){
			 args.push(item.operateIcon);
		 }else{
			 args.push(defaultSetting.operateIcon);
		 }
		
		 //放入表格ID
		 if(item.callBack){
			 args.push("data-callBack='true'");
		 }else{
			 args.push("");
		 }
		 
		 //如果存在其他属性
		 if(item.attr){
			 args.push(item.attr);
		 }else{
			 args.push('');
		 }
		 
		 return templete.format(args);
    };
    /**
     * 返回格式化字符串,针对事件
     */
    function handelEvent(templete,item,defaultSetting,operateEvents){
    	var args = [];
    	
    	var title;
		 if(item.title){
			 args.push(item.title);
		 }else{
			 args.push(defaultSetting.defaultTitle);
			 item.title = defaultSetting.defaultTitle;
		 }
		 if(item.onclick){
			 operateEvents['click [title="'+item.title+'"]'] = function(e, value, row, index) {
				if($(this).isDisabled()){
						return false;
				}
			    if($.isFunction(item.onclick)){
			    	item.onclick(value, row, index,this);
			    }else{
					eval(item.onclick)(value, row, index,this);
			    }
			};
		 }
		 if(item.operateIcon){
			 args.push(item.operateIcon);
		 }else{
			 args.push(defaultSetting.operateIcon);
		 }
		 
		 //如果存在其他属性
		 if(item.attr){
			 args.push(item.attr);
		 }else{
			 args.push('');
		 }
		 
		 return templete.format(args);
    };
    
    
    
    /**
     * 根据index 删除指定的行
     */
    var deleteRowByIndex = function(index){
    	table.bootstrapTable('removeByIndex',index);
    }
    /**
     * 获取行数
     */
    var getTableRows = function(){
    	var tableOptions = table.bootstrapTable('getOptions');
    	var columns = tableOptions.columns;
    	return table.bootstrapTable('getData').length; 
    }
    /**
     * 增加一行
     */
    var appendRow = function(row,isFinishTip,allowDuplicateBtn,appendRowCallBack){
    	//判断是否重复
		var flag = isDuplicateRow(row);
		if(flag&&tableOptions.allowDuplicate&&allowDuplicateBtn){
			$(allowDuplicateBtn).confirm("此信息已经存在，是否重复添加？",function(obj){
				var trRow =  addRowData(row,isFinishTip);
				//执行添加个一行的回调
				if(appendRowCallBack){
					appendRowCallBack(trRow);
				}
			});
		}else{
			if(!flag){
				//添加一行数据
		    	var trRow =  addRowData(row,isFinishTip);
		    	if(appendRowCallBack){
		    		//执行添加个一行的回调
					appendRowCallBack(trRow);
		    	}
		    	return trRow;
			}
		}
		return undefined;
    }
    
    /**
     * 添加一行数据
     */
    function addRowData(row,isFinishTip){
    	table.bootstrapTable('append', row);
    	//获取行号
    	var rowSize = table.bootstrapTable('getData').length;
    	var trRow = $('tbody > tr[data-index=\"'+(rowSize-1)+'\"] ', table);
    	//执行初始化--加载自定义方法前
		initTableContainerBefore(trRow);
		//执行初始化--加载自定义方法后
		initTableContainerAfter(trRow);
		//如果需要提示添加成功，则弹出提示消息
		if(isFinishTip){
			dmsCommon.tip({status:"success",msg:"信息添加成功",timeOut:"2000"});
		}
    	return trRow;
    }
    /*
     * 是否存在重复的数据
     */
    function isDuplicateRow(row){
    	var flag = false;
    	if(tableOptions.uniqueDataName){
    		var uniqueDataName = tableOptions.uniqueDataName;
    		var uniqueDataNames = uniqueDataName.split(",");
    		var uniqueDataValue = getRowUniqueDataValue(uniqueDataNames,row);
    		
    		var dataArray = table.bootstrapTable('getData'); 
    		$.each(dataArray,function(index,item){
    			if(uniqueDataValue==getRowUniqueDataValue(uniqueDataNames,item)){
    				if(!tableOptions.allowDuplicate){
    					dmsCommon.tip({status:"warning",msg:"信息已存在，不允许重复添加",timeOut:"2000"});
    				}
    				flag = true;
    				return false;
    			}
    		});
    	}
    	return flag;
    }
    
    /**
     * 获得唯一的数值
     * @param uniqueNames
     * @param row
     * @returns
     */
    function getRowUniqueDataValue(uniqueDataNames,row){
    	var returnVal;
    	$.each(uniqueDataNames,function(index,item){
    		returnVal+=row[item]+",";
    	});
    	return returnVal;
    }
    /**
     * 增加一行
     */
    var appendRows = function(rows,isFinishTip,allowDuplicateBtn,appendRowCallBack){
    	$.each(rows,function(index,row){
    		appendRow(row,isFinishTip,allowDuplicateBtn,appendRowCallBack);
    	});
    }
    
    /**
     * 增加一行
     */
    var insertRow = function(index,row){
    	//获取行号
    	table.bootstrapTable('insertRow', {index: index, row: row});
    	var trRow = $('tbody > tr[data-index=\"'+(index)+'\"] ', table);
    	//执行初始化--加载自定义方法前
		initTableContainerBefore(trRow);
		//执行初始化--加载自定义方法后
		initTableContainerAfter(trRow);
    }
    
    /**
     * 增加一行
     */
    var prependRow = function(row){
    	table.bootstrapTable('prepend', row); 
    }
    
    /**
     * 获得父窗口表格行
     */
    var getParentTabelRow = function(){
    	return parentRowData;
    }
    
    var setParentTabelRow = function(row){
    	parentRowData = row;
    }
    
    /**
     * 获取选中的行
     */
    var getSelections = function(){
    	var selects = table.bootstrapTable('getSelections');  
    	return selects.length>0?selects:undefined;
    }
    
    /**
     * 获取选中的行
     */
    var getFirstSelection = function(){
    	var selects = table.bootstrapTable('getSelections'); 
    	return selects.length>0?selects[0]:undefined;
    }
    /**
     * 获取选中行的ID 集合
     */
    var getSelectionIds = function(){
    	var selects = table.bootstrapTable('getSelections');   
    	if(selects.length>0){
    		var ids = new Array();
    		$.each(selects,function(i,item){
        		ids.push(item[tableOptions.rowID]);
        	});
        	return ids.join(",");
    	}else{
    		return "";
    	}
    }
    
    /**
     * 获取数据根据index 值
     */
    var getRowDataByIndex = function(index){
    	var dataArray = table.bootstrapTable('getData'); 
    	if(index!=undefined){
    		return dataArray[index];
    	}else{
    		return dataArray;
    	}
    }
    
    /**
     * 刷新表格数据
     */
    var refreshLocalData = function(data){
    	table.bootstrapTable('refreshOptions', {
             data: data
         });
    	//执行初始化--加载自定义方法前
		initTableContainerBefore(table);
		//执行初始化--加载自定义方法后
		initTableContainerAfter(table);
    }
    
    //刷新表格的URL
    var refreshUrl = function(url){
    	table.bootstrapTable('refreshOptions', {
    		url:getDmsFuncIdUrl(url)
         });
    }
    
    //根据动态参数-刷新表格的
    var refreshUrlByRowData = function(rowData){
    	var newUrl = tableOldOptions.url.format(rowData);
		table.bootstrapTable('refreshOptions',{
			url:getDmsFuncIdUrl(newUrl),
			parentTable:undefined
		});
    	
    }
    
    //更新表格的某些数据
    var updateRowByIndex = function(index,row){
    	//更新表格中的数据
    	table.bootstrapTable('updateRow', {index: index, row: row});
    	//触发input 变更事件
    	$(">tbody>tr:eq("+index+") :input",table).trigger("input");
    }
    //添加合计
    var mergeTotalTable =function(megerCellNumber,megerCellName){
    	var tableOptions = table.bootstrapTable('getOptions');
    	var columns = tableOptions.columns;
    	var cols=columns[0].length;
    	var map={};
    	var keyArray=[];
    	for(var i=megerCellNumber+1;i<cols;i++){
    		var key=columns[0][i].field;
    		keyArray.push(key);
    		map[key]=0;
    	}
    	var data=tableOptions.data;
    	var rows=tableOptions.data.length;
    	var floatNum=1;
    	for(var i=0;i<rows;i++){
    		for(var j=megerCellNumber+1;j<cols;j++){
    			var key=keyArray[j-megerCellNumber-1];
    			var arr=String(data[i][key]).split('.');
    			var floatNum=1;
    			if(arr[1]==undefined){
    				floatNum=0;
    			}else{
    				floatNum=arr[1].length;
    			}

    			var addend=data[i][key];
    			var summand=map[key];
    			map[key]=(parseFloat(summand)+parseFloat(addend)).toFixed(floatNum);
    		}
    	}
    	appendRow(map);
    	$("tbody tr:eq("+rows+") td:lt("+megerCellNumber+")",table).remove();
    	var mergerTotalTd = $("tbody tr:eq("+rows+") td:first",table);
    	$(mergerTotalTd).attr("colspan",megerCellNumber+1);
    	$(mergerTotalTd).text(megerCellName);
    	$(mergerTotalTd).css("text-align","center");
    }
    
    //获取该列前不包含visible的列数
    var getVisiableCellNumber=function(configNumber){
    	var columns = tableOptions.columns;
    	var visiableNumber = 0;
    	$.each(columns,function(index,item){
    		if(item.visible==false&&index<=configNumber){
    			visiableNumber ++;
    		}
    	});
    	return configNumber-visiableNumber;
    }
    
    return {
        //main function to initiate the module
        initPagination: function(options) {
        	initPagination(options);
        	//绑定表格实体对象
            $(table).data("tableObject",this);
        	return this;
        },
        initLocale:function(options){
        	initLocale(options);
        	//绑定表格实体对象
            $(table).data("tableObject",this);
        	return this;
        },
        
        getDataTable: function() {
            return dataTable;
        },

        getTableWrapper: function() {
            return tableWrapper;
        },

        gettableContainer: function() {
            return tableContainer;
        },

        getTable: function() {
            return table;
        },
        refreshTableWithForm:function(){
        	refreshTableWithForm();
        },
        refreshLocalData:function(data){
        	refreshLocalData(data);
        },
        refreshUrl:function(url){
        	refreshUrl(url);
        },
        refreshUrlByRowData:function(rowData){
        	refreshUrlByRowData(rowData);
        },
        bootstrapTable:function(command,options){
        	table.bootstrapTable(command,options);
        },
        appendRow:function (row,isFinishTip,allowDuplicateBtn,appendRowCallBack){
        	return appendRow(row,isFinishTip,allowDuplicateBtn,appendRowCallBack);
        },
        appendRows:function(rows,isFinishTip,allowDuplicateBtn,appendRowCallBack){
        	appendRows(rows,isFinishTip,allowDuplicateBtn,appendRowCallBack);
        },
        appendBlankRow:function (){
        	appendRow({});
        },
        getSelections:function(){
        	return getSelections();
        },
        getFirstSelection:function(){
        	return getFirstSelection();
        },
        getSelectionIds:function(){
        	return getSelectionIds();
        },
        getParentTabelRow:function (){
        	return getParentTabelRow();
        },
        setParentTabelRow:function (row){
        	setParentTabelRow(row);
        },
        getRowDataByIndex:function(index){
        	return getRowDataByIndex(index);
        },
        getTableRows:function(){
        	return getTableRows();
        },
        getVisiableCellNumber:function(configNumber){
        	return  getVisiableCellNumber(configNumber);
        },
        deleteRowByIndex:function(index){
        	deleteRowByIndex(index);
        },
        updateRowByIndex:function(index,row){
        	updateRowByIndex(index,row);
        },
        mergeTotalTable:function(megerCellNumber,megerCellName){
        	mergeTotalTable(megerCellNumber,megerCellName);
        },
        getVisiableCellNumber:function(configNumber){
        	return  getVisiableCellNumber(configNumber);
        }
    };
};