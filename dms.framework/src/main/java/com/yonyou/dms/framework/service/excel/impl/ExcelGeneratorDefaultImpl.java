package com.yonyou.dms.framework.service.excel.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yonyou.dms.framework.domains.DTO.baseData.DictDto;
import com.yonyou.dms.framework.domains.DTO.baseData.OemDictDto;
import com.yonyou.dms.framework.domains.DTO.baseData.RegionDto;
import com.yonyou.dms.framework.service.cache.impl.DictCacheServiceImpl;
import com.yonyou.dms.framework.service.cache.impl.OemDictCacheServiceImpl;
import com.yonyou.dms.framework.service.cache.impl.RegionCacheSerivceImpl;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.exception.UtilException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.io.IOUtils;

/**
 * 根据 Excel数据信息，生成Excel文件流 ExcelGenerator 接口的默认实现，可继承此类并并改写相关方法实现 Created by wfl.
 */

@Component
public class ExcelGeneratorDefaultImpl implements ExcelGenerator {

    @Resource(name="DictCache")
    DictCacheServiceImpl<List<DictDto>> dictCacheSerivce;
    
    @Resource(name="OemDictCache")
    OemDictCacheServiceImpl<List<OemDictDto>> oemDictCacheSerivce;
    
    @Resource(name="RegionCache")
    RegionCacheSerivceImpl<RegionDto> regionCacheService;
    
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(ExcelGeneratorDefaultImpl.class);


    /**
     * 生成Excel 数据
     * @author zhangxc
     * @date 2016年9月28日
     * @param excelData
     * @param columnDefine
     * @param fileName
     * @param response
     * (non-Javadoc)
     * @see com.yonyou.dms.common.service.system.excel.ExcelGenerator#generateExcel(java.util.Map, com.yonyou.dms.common.service.system.excel.ExcelExportColumn[], java.lang.String, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void generateExcel(@SuppressWarnings("rawtypes") Map<String, List<Map>> excelData, List<ExcelExportColumn> columnDefineList,String fileName, HttpServletRequest request, HttpServletResponse response) {
        // 如果excelData 中没有数据，则返回错误
        if (CommonUtils.isNullOrEmpty(excelData)) {
            throw new ServiceBizException("No excel data !");
        }

        Workbook workbook = null;
        OutputStream outputStream = null;
        try {
            // 初始化输出流
            outputStream = initOutputStream(request,response, fileName);
            // 初始化workbook
            workbook = createWorkbook();

            Set<String> sheetSet = excelData.keySet();
            for (String sheetName : sheetSet) {
                @SuppressWarnings("rawtypes")
                List<Map> rowList = excelData.get(sheetName);
                // 创建sheet 页
                Sheet sheet = workbook.createSheet(sheetName);

                // 生成标题
                generateTitleRow(sheet, columnDefineList);

                // 生成数据
                generateDataRows(sheet, rowList, columnDefineList);

                //当数据加载完成后设置sheet 格式
                setSheetFinishStyle(sheet,columnDefineList.size());

            }

            workbook.write(outputStream);
        } catch (Exception exception) {
            logger.warn(exception.getMessage(), exception);
            throw new ServiceBizException(exception.getMessage(), exception);
        } finally {
            IOUtils.closeStream(workbook);
            IOUtils.closeStream(outputStream);
        }

    }
    
    public void generateExcel(@SuppressWarnings("rawtypes") Map<String, List<Map>> excelData, Map<String, List<ExcelExportColumn>> columnDefineListMap,String fileName, HttpServletRequest request, HttpServletResponse response) {
        // 如果excelData 中没有数据，则返回错误
        if (CommonUtils.isNullOrEmpty(excelData)) {
            throw new ServiceBizException("No excel data !");
        }

        Workbook workbook = null;
        OutputStream outputStream = null;
        try {
            // 初始化输出流
            outputStream = initOutputStream(request,response, fileName);
            // 初始化workbook
            workbook = createWorkbook();

            Set<String> sheetSet = excelData.keySet();
            for (String sheetName : sheetSet) {
                @SuppressWarnings("rawtypes")
                List<Map> rowList = excelData.get(sheetName);
                // 创建sheet 页
                Sheet sheet = workbook.createSheet(sheetName);
                Set<String> colSet = columnDefineListMap.keySet();
                for (String col : colSet) {
                	if(col.equals(sheetName)){
                		// 生成标题
                        generateTitleRow(sheet, columnDefineListMap.get(col));

                        // 生成数据
                        generateDataRows(sheet, rowList, columnDefineListMap.get(col));
                	}
				}

                //当数据加载完成后设置sheet 格式
                setSheetFinishStyle(sheet,columnDefineListMap.size());

            }

            workbook.write(outputStream);
        } catch (Exception exception) {
            logger.warn(exception.getMessage(), exception);
            throw new ServiceBizException(exception.getMessage(), exception);
        } finally {
            IOUtils.closeStream(workbook);
            IOUtils.closeStream(outputStream);
        }

    }
    
    /**
     * 生成Excel 数据 For Dms
     * @author wangxin
     * @date 2016年9月28日
     * @param excelData
     * @param columnDefine
     * @param fileName
     * @param response
     * (non-Javadoc)
     * @see com.yonyou.dms.common.service.system.excel.ExcelGenerator#generateExcel(java.util.Map, com.yonyou.dms.common.service.system.excel.ExcelExportColumn[], java.lang.String, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void generateExcelForDms(@SuppressWarnings("rawtypes") Map<String, List<Map>> excelData, List<ExcelExportColumn> columnDefineList,String fileName, HttpServletRequest request, HttpServletResponse response) {
        // 如果excelData 中没有数据，则返回错误
        if (CommonUtils.isNullOrEmpty(excelData)) {
            throw new ServiceBizException("No excel data !");
        }

        Workbook workbook = null;
        OutputStream outputStream = null;
        try {
            // 初始化输出流
            outputStream = initOutputStream(request,response, fileName);
            // 初始化workbook
            workbook = createWorkbook();

            Set<String> sheetSet = excelData.keySet();
            for (String sheetName : sheetSet) {
                @SuppressWarnings("rawtypes")
                List<Map> rowList = excelData.get(sheetName);
                // 创建sheet 页
                Sheet sheet = workbook.createSheet(sheetName);

                // 生成标题
                generateTitleRowForDms(sheet, columnDefineList,fileName);

                // 生成数据
                generateDataRowsForDms(sheet, rowList, columnDefineList);

                //当数据加载完成后设置sheet 格式
                setSheetFinishStyle(sheet,columnDefineList.size());

            }

            workbook.write(outputStream);
        } catch (Exception exception) {
            logger.warn(exception.getMessage(), exception);
            throw new ServiceBizException(exception.getMessage(), exception);
        } finally {
            IOUtils.closeStream(workbook);
            IOUtils.closeStream(outputStream);
        }

    }
    
    /**
     * 生成一行for dms
     * 
     * @author zhangxc
     * @date 2016年7月20日
     * @param sheet
     * @param rowList
     */
    protected void generateDataRowsForDms(final Sheet sheet, @SuppressWarnings("rawtypes") final List<Map> rowList,
                                    final List<ExcelExportColumn> columnDefineList) {
        if (CommonUtils.isNullOrEmpty(rowList)) {
            return;
        }
        //确定excel 每一列的格式化样式
        Map<Integer,CellStyle> columnCellStyle = new HashMap<>();

        // 生成数据
        for (int i = 0; i < rowList.size(); i++) {
            @SuppressWarnings("rawtypes")
            final Map cellList = rowList.get(i);
            //生成一行
            Row row = sheet.createRow((i+4));
            for (int j = 0; j < columnDefineList.size(); j++) {
                createCell(cellList.get(columnDefineList.get(j).getFieldName()),row,j,columnCellStyle,columnDefineList.get(j));
            }
        }
    }

    /**
     * 生成一行
     * 
     * @author zhangxc
     * @date 2016年7月20日
     * @param sheet
     * @param rowList
     */
    protected void generateDataRows(final Sheet sheet, @SuppressWarnings("rawtypes") final List<Map> rowList,
                                    final List<ExcelExportColumn> columnDefineList) {
        if (CommonUtils.isNullOrEmpty(rowList)) {
            return;
        }
        //确定excel 每一列的格式化样式
        Map<Integer,CellStyle> columnCellStyle = new HashMap<>();

        // 生成数据
        for (int i = 0; i < rowList.size(); i++) {
            @SuppressWarnings("rawtypes")
            final Map cellList = rowList.get(i);
            //生成一行
            Row row = sheet.createRow((i+1));
            for (int j = 0; j < columnDefineList.size(); j++) {
                createCell(cellList.get(columnDefineList.get(j).getFieldName()),row,j,columnCellStyle,columnDefineList.get(j));
            }
        }
    }

    /**
     * 
     * 创建一个单元格
     * @author zhangxc
     * @date 2016年9月28日
     * @param cellValue
     * @param row
     * @param cellIndex
     * @param cellstyle
     */
    private void createCell(Object cellValue,Row row,int cellIndex,Map<Integer,CellStyle> columnCellStyle,ExcelExportColumn excelExportColumn){
        Cell cell = row.createCell(cellIndex);
        
        //如果是空值
        if(cellValue == null){
            if(columnCellStyle.get(-1)==null){
                //放入字符串样式,使用-1 作为空值的判断依据
                columnCellStyle.put(-1, getSheetStringStyle(row.getSheet().getWorkbook()));
            }
            cell.setCellStyle(columnCellStyle.get(-1));
            cell.setCellType(Cell.CELL_TYPE_STRING);
            cell.setCellValue((String)cellValue);
            return;
        }
        //设置字符串类型
        if(cellValue instanceof String) {
            if(excelExportColumn.getDataType()!=null){
                if(columnCellStyle.get(cellIndex)==null){
                    columnCellStyle.put(cellIndex, getSheetStringStyle(row.getSheet().getWorkbook()));
                }
                cell.setCellStyle(columnCellStyle.get(cellIndex));
                cell.setCellType(Cell.CELL_TYPE_STRING);
                //设置ID 对应的名称
                cell.setCellValue(getNamesByCodes((String)cellValue,excelExportColumn.getDataType()));
            }else{
                if(columnCellStyle.get(cellIndex)==null){
                    //放入字符串样式
                    columnCellStyle.put(cellIndex, getSheetStringStyle(row.getSheet().getWorkbook()));
                }
                cell.setCellStyle(columnCellStyle.get(cellIndex));
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue((String)cellValue);
            }
            return;
        }

        //设置Double 类型
        if(cellValue instanceof Double||cellValue instanceof BigDecimal) {
            if(columnCellStyle.get(cellIndex)==null){
                columnCellStyle.put(cellIndex, getSheetDoubleStyle(row.getSheet().getWorkbook(),excelExportColumn.getFormat()));
            }
            cell.setCellStyle(columnCellStyle.get(cellIndex));
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            if(cellValue instanceof BigDecimal){
                cell.setCellValue(((BigDecimal)cellValue).doubleValue());
            }else{
                cell.setCellValue((Double)cellValue);
            }
            return;
        }

        //设置整形格式
        if(cellValue instanceof Integer||cellValue instanceof Long) {
            if(excelExportColumn.getDataType()!=null){
                if(columnCellStyle.get(cellIndex)==null){
                    columnCellStyle.put(cellIndex, getSheetCodeDescStyle(row.getSheet().getWorkbook()));
                }
                cell.setCellStyle(columnCellStyle.get(cellIndex));
                cell.setCellType(Cell.CELL_TYPE_STRING);
                //设置ID 对应的名称
                cell.setCellValue(getNameByCode((Number)cellValue,excelExportColumn.getDataType()));
            }else{
                if(columnCellStyle.get(cellIndex)==null){
                    columnCellStyle.put(cellIndex, getSheetIntegerStyle(row.getSheet().getWorkbook(),excelExportColumn.getFormat()));
                }
                cell.setCellStyle(columnCellStyle.get(cellIndex));
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                if(cellValue instanceof Integer){
                	 cell.setCellValue((double)(int)cellValue);
                }else{
                	cell.setCellValue((double)(long)cellValue);
                }
            }
            return;
        }

        //设置日期格式
        if(cellValue instanceof Date) {
            if(columnCellStyle.get(cellIndex)==null){
                //放入日期格式
                columnCellStyle.put(cellIndex, getSheetDateStyle(row.getSheet().getWorkbook(),excelExportColumn.getFormat()));
            }
            cell.setCellStyle(columnCellStyle.get(cellIndex));
            cell.setCellType(Cell.CELL_TYPE_NUMERIC);
            cell.setCellValue((Date)cellValue);
            return;
        }
    }

    /**
     * 
    * 根据代码获得名称,代码存储为：1001001,10011002 ,用","分隔的代码
    * @author zhangxc
    * @date 2016年9月28日
    * @param value
    * @param excelDataType
    * @return
     */
    private String getNamesByCodes(String values,ExcelDataType excelDataType){
        String[] valuesArray = values.split(",");
        StringBuilder sb = new StringBuilder();
        for(String value:valuesArray){
            if(!StringUtils.isNullOrEmpty(value)){
                sb.append(getNameByCode(Long.parseLong(value),excelDataType)).append(",");
            }
        }
        if(sb.length()>0){
            return sb.substring(0, sb.length()-1);
        }else{
            return "";
        }
    }
    /**
     * 
    * 根据代码获得名称
    * @author zhangxc
    * @date 2016年9月28日
    * @param value
    * @param excelDataType
    * @return
     */
    private String getNameByCode(Number value,ExcelDataType excelDataType){
        //如果是TC_CODE
        if(excelDataType==ExcelDataType.Dict&& value.toString().length()==8){
            return dictCacheSerivce.getDescByCodeId(Integer.parseInt(value.toString()));
        }
        //如果是TC_CODE_DCS
        if(excelDataType==ExcelDataType.Oem_Dict){
        	if(null!=value && value.toString().length()==8){
        		return oemDictCacheSerivce.getDescByCodeId(Integer.parseInt(value.toString()));
        	}else{
        		return "";
        	}
        }
        //如果是省份城市
        if(excelDataType==ExcelDataType.Region_Provice){
            return regionCacheService.getProvinceNameById((Long)value);
        }
        //如果是城市
        if(excelDataType==ExcelDataType.Region_City){
            return regionCacheService.getCityNameById((Long)value);
        }
        //如果是区县
        if(excelDataType==ExcelDataType.Region_Country){
            return regionCacheService.getCountryNameById((Long)value);
        }
        return null;
    }
    
    /**
     * 生成标题
     * 
     * @author zhangxc
     * @date 2016年9月28日
     * @param sheet
     * @param titleNames
     * @param cellStyle
     */
    private void generateTitleRowForDms(Sheet sheet, List<ExcelExportColumn> columnDefineList,String fileName) {
    	sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
    	String id = FrameworkUtil.getLoginInfo().getUserName();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	String time = dateFormat.format( new Date()  ); 
    	String title = fileName.substring(fileName.indexOf("_")+1, fileName.lastIndexOf("."));
    	HSSFRow row1 = (HSSFRow) sheet.createRow(0);
    	 HSSFCell cell1 = row1.createCell(0);
    	 cell1.setCellValue(title);
    	 
    	 HSSFRow row2 = (HSSFRow) sheet.createRow(1);
    	 HSSFCell cell2 = row2.createCell(0);
    	 HSSFCell cell3 = row2.createCell(1);
    	 HSSFCell cell4 = row2.createCell(2);
    	 HSSFCell cell5 = row2.createCell(3);
    	 cell2.setCellValue("导出日期:");
    	 cell3.setCellValue(time);
    	 cell4.setCellValue("导出人:");
    	 cell5.setCellValue(id);
    	 
        // 生成标题
        Row row = sheet.createRow(3);
        CellStyle cellStyle = getSheetTitleStyleForDms(sheet.getWorkbook());
        // 生成标题
        for (int i=0; i<columnDefineList.size();i++) {
            Cell cell = row.createCell(i);
            //生成标题的信息
            createStringCell(columnDefineList.get(i).getTitle(),cell,cellStyle);
        }
        CellStyle cellStyle1 = getTitleStyle(sheet.getWorkbook());
        
        cell1.setCellStyle(cellStyle1);
    }
    /**
     * 生成标题
     * 
     * @author zhangxc
     * @date 2016年9月28日
     * @param sheet
     * @param titleNames
     * @param cellStyle
     */
    private void generateTitleRow(Sheet sheet, List<ExcelExportColumn> columnDefineList) {
        // 生成标题
        Row row = sheet.createRow(0);
        CellStyle cellStyle = getSheetTitleStyle(sheet.getWorkbook());
        // 生成标题
        for (int i=0; i<columnDefineList.size();i++) {
            Cell cell = row.createCell(i);
            //生成标题的信息
            createStringCell(columnDefineList.get(i).getTitle(),cell,cellStyle);
        }
    }


    /**
     * 
     * 创建字符串类型
     * @author zhangxc
     * @date 2016年9月28日
     * @param cellValue
     * @param cell
     * @param cellstyle
     */
    private void createStringCell(Object cellValue,Cell cell,CellStyle cellstyle){
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellStyle(cellstyle);
        cell.setCellValue((String)cellValue);
    }

    /**
     * 
     * 当sheet 页加载完成后，设置sheet 面的样式
     * @author zhangxc
     * @date 2016年9月28日
     * @param sheet
     */
    private void setSheetFinishStyle(Sheet sheet,int colSize){
        //设置字段宽度
        for(int i=0;i<colSize;i++){
            sheet.autoSizeColumn((short)i);
        }
        
        //冻结首行
        sheet.createFreezePane(0, 1, 0, 1);
    }
    /**
     * 
     * 获得默认的字体
     * @author zhangxc
     * @date 2016年9月28日
     * @return
     */
    private CellStyle getDefaultCellStyle(Workbook workbook){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_LEFT); // 水平布局：居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//上下居中 
        cellStyle.setBorderTop(CellStyle.BORDER_THIN); //设置边框   
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);   //设置边框    
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);    //设置边框   
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);   //设置边框   
        cellStyle.setWrapText(false);
        return cellStyle;
    }
    
    
    /**
     * 设置excel 的字段
     * 
     * @author zhangxc
     * @date 2016年9月28日
     * @param workbook
     */
    private CellStyle getSheetTitleStyleForDms(Workbook workbook) {
        //修改字段样式
        Font font = getDefaultFont(workbook);
        font.setFontHeightInPoints((short) 12); // 字体高度
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL); // 宽度

        //修改默认单元格样式
        CellStyle cellStyle = getDefaultCellStyle(workbook);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setFont(font);
        return cellStyle;
    }
    /**
     * 设置Excel标题格式
     * @param workbook
     * @return
     */
    private CellStyle getTitleStyle(Workbook workbook) {
        //修改字段样式
        Font font = getDefaultFont(workbook);
        font.setFontHeightInPoints((short) 14); // 字体高度
        font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 宽度

        //修改默认单元格样式
        CellStyle cellStyle = getDefaultCellStyle(workbook);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 
     * 获得默认的字体
     * @author zhangxc
     * @date 2016年9月28日
     * @return
     */
    private Font getDefaultFont(Workbook workbook){
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10); // 字体高度
        font.setColor(Font.COLOR_NORMAL); // 字体颜色
        font.setFontName("微软雅黑"); // 字体
        // font.setItalic(true); //是否使用斜体
        return font;
    }
    /**
     * 设置excel 的字段
     * 
     * @author zhangxc
     * @date 2016年9月28日
     * @param workbook
     */
    private CellStyle getSheetTitleStyle(Workbook workbook) {
        //修改字段样式
        Font font = getDefaultFont(workbook);
        font.setFontHeightInPoints((short) 14); // 字体高度
        font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 宽度

        //修改默认单元格样式
        CellStyle cellStyle = getDefaultCellStyle(workbook);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setFont(font);
        return cellStyle;
    }
    /**
     * 
     * 获得日期格式的样式
     * @author zhangxc
     * @date 2016年9月28日
     * @param workbook
     * @return
     */
    private CellStyle getSheetDateStyle(Workbook workbook,String format) {
        //如果format 未指定，则使用yyyy-MM-dd
        format = format==null?"yyyy-MM-dd":format;
        //修改字段样式
        Font font = getDefaultFont(workbook);
        //修改默认单元格样式
        CellStyle cellStyle = getDefaultCellStyle(workbook);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setFont(font);
        //格式化样式
        DataFormat dataFormat = workbook.createDataFormat(); 
        cellStyle.setDataFormat(dataFormat.getFormat(format)); 
        return cellStyle;
    }

    /**
     * 
     * 获得日期格式的样式
     * @author zhangxc
     * @date 2016年9月28日
     * @param workbook
     * @return
     */
    private CellStyle getSheetStringStyle(Workbook workbook) {
        //修改字段样式
        Font font = getDefaultFont(workbook);
        //修改默认单元格样式
        CellStyle cellStyle = getDefaultCellStyle(workbook);
        
        cellStyle.setFont(font);
        return cellStyle;
    }
    
    /**
     * 
     * 获得日期格式的样式
     * @author zhangxc
     * @date 2016年9月28日
     * @param workbook
     * @return
     */
    private CellStyle getSheetCodeDescStyle(Workbook workbook) {
        //修改字段样式
        Font font = getDefaultFont(workbook);
        //修改默认单元格样式
        CellStyle cellStyle = getDefaultCellStyle(workbook);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        cellStyle.setFont(font);
        return cellStyle;
    }
    
    /**
     * 
     * 获得日期格式的样式
     * @author zhangxc
     * @date 2016年9月28日
     * @param workbook
     * @return
     */
    private CellStyle getSheetDoubleStyle(Workbook workbook,String format) {
        //如果format 未指定，则使用yyyy-MM-dd
        format = format==null?"#,##0.00##":format;
        //修改字段样式
        Font font = getDefaultFont(workbook);
        //修改默认单元格样式
        CellStyle cellStyle = getDefaultCellStyle(workbook);
        cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        cellStyle.setFont(font);
        //格式化样式
        DataFormat dataFormat = workbook.createDataFormat(); 
        cellStyle.setDataFormat(dataFormat.getFormat(format)); 
        return cellStyle;
    }
    
    /**
     * 
     * 获得日期格式的样式
     * @author zhangxc
     * @date 2016年9月28日
     * @param workbook
     * @return
     */
    private CellStyle getSheetIntegerStyle(Workbook workbook,String format) {
        //如果format 未指定，则使用yyyy-MM-dd
        format = format==null?"#,##0":format;
        //修改字段样式
        Font font = getDefaultFont(workbook);
        //修改默认单元格样式
        CellStyle cellStyle = getDefaultCellStyle(workbook);
        cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
        cellStyle.setFont(font);
        //格式化样式
        DataFormat dataFormat = workbook.createDataFormat(); 
        cellStyle.setDataFormat(dataFormat.getFormat(format)); 
        return cellStyle;
    }

    /**
     * 创建workbook
     * 
     * @author zhangxc
     * @date 2016年9月28日
     * @return
     */
    private Workbook createWorkbook() {
        Workbook workbook = new HSSFWorkbook();
        return workbook;
    }

    /**
     * 初始化输出流
     * 
     * @author zhangxc
     * @date 2016年9月28日
     * @param response
     * @param fileName
     * @return
     * @throws IOException
     */
    private OutputStream initOutputStream(HttpServletRequest request,HttpServletResponse response, String fileName) throws UtilException {
        try {
            FrameHttpUtil.setExportFileName(request,response, fileName);
            return response.getOutputStream();
        } catch (Exception e) {
            throw new UtilException("excel 流初始化失败", e);
        }
    }
    /**
     * 设置 WorkBook 相关属性,扩展属性
     *
     * @param wb WorkBook
     */
    @Deprecated
    protected void setWorkbookAttribute(Workbook wb) {
    }

    /**
     * 设置 Sheet 相关属性
     *
     * @param sheetName Sheet名称
     * @param sheet Sheet
     */
    @Deprecated
    protected void setSheetAttribute(String sheetName, Sheet sheet, @SuppressWarnings("rawtypes") final List<Map> rowList, String[] keys,
                                     String[] columnNames) {
    }

    /**
     * 设置 Row 相关属性
     *
     * @param row Row
     * @param rowIndex 行号
     * @param cellList 单元格值列表
     */
    @Deprecated
    protected void setRowAttribute(Row row, Map<String, Object> cellList) {
    }

    /**
     * 设置 Cell 相关属性，如字体 样式等
     *
     * @param row row 对象
     * @param cell cell 对象
     * @param rowIndex 行号
     * @param cellIndex 列号
     * @param cellVal 单元格值
     */
    @Deprecated
    protected void setCellAttribute(Row row, Cell cell, String cellVal) {
    }

}
