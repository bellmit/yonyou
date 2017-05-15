/**
 * 售后模块相关功能
 */
var gfkRepair=function(){
	//加载维修项目下拉框数据
	var initLabourData = function(){
		var getTableRows = $("#labourList",getElementContext()).dmsTable().getTableRows();
		var repairPro = $("#labourList",getElementContext()).dmsTable().getRowDataByIndex();
		var selectData = new Array();
		for(var i=0;i<getTableRows;i++){
			selectData.push({id:repairPro[i].LABOUR_CODE,name:(i+1)+"-"+repairPro[i].LABOUR_NAME});
		}
		$("select[data-inputname='labourCode']",getElementContext()).each(function(i,labourCode){
		    dmsDict.refreshSelectByData(labourCode,selectData,"id","name");
	    });
		return selectData;
	};
	
	var calcLabourAmount = function(){
		var tab=$("#labourList tbody",getElementContext());//
		var rows=$("tr",tab).length;
		//应收金额
		var afterDiscountAmount= parseFloat(0);
		//console.log("rows="+rows)
		for(var i=0;i<rows;i++){
			var discountAmount = $("tr:eq("+i+") td:eq(12) input[id^=disLabourAmount]",tab).val();//折扣金额
			//console.log("discountAmount:"+discountAmount);
			if(discountAmount =="" || discountAmount ==undefined){
				discountAmount=parseFloat(0);	
			}
			afterDiscountAmount=parseFloat(afterDiscountAmount)+parseFloat(discountAmount);
		
		}
		//console.log("afterDiscountAmount："+afterDiscountAmount)
		$("#labourAmount",getElementContext()).valChange(afterDiscountAmount.toFixed(2));
		
	};
	
	var calcPartAmount=function(){
		var tab=$("#partList tbody",getElementContext());//销售材料
		var rows=$("tr",tab).length;
		//应收金额
		var afterDiscountAmount= parseFloat(0);
		for(var i=0;i<rows;i++){
			var discountAmount = $("tr:eq("+i+") td:eq(12) input[id^=disSalesAmount]",tab).val();//折扣金额
			if(discountAmount =="" || discountAmount ==undefined){
				discountAmount=parseFloat(0);	
			}
			afterDiscountAmount=parseFloat(afterDiscountAmount)+parseFloat(discountAmount);
		}
		$("#partAmount",getElementContext()).setDmsValue(afterDiscountAmount.toFixed(2));
	};
	
	var calcAddItemAmount=function(){
		var tab=$("#subjoinList tbody",getElementContext());//
		var rows=$("tr",tab).length;
		//应收金额
		var afterDiscountAmount= parseFloat(0);
		for(var i=0;i<rows;i++){
			var discountAmount = $("tr:eq("+i+") td:eq(7) input[id^=zkAmountZ]",tab).val();//折扣金额
			if(discountAmount =="" || discountAmount ==undefined){
				discountAmount=parseFloat(0);	
			}
			afterDiscountAmount=parseFloat(afterDiscountAmount)+parseFloat(discountAmount);
		}
		$("#addItemAmount",getElementContext()).setDmsValue(afterDiscountAmount.toFixed(2));
	};
	
	return {
		initLabourData:function(){
			initLabourData();
		},
		calcLabourAmount:function(){
			calcLabourAmount();
		},
		calcPartAmount:function(){
			calcPartAmount();
		},
		calcAddItemAmount:function(){
			calcAddItemAmount();
		}
	};
}();