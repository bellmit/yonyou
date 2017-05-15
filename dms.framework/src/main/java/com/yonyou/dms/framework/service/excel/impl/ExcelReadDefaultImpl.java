package com.yonyou.dms.framework.service.excel.impl;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.DictDto;
import com.yonyou.dms.framework.domains.DTO.baseData.RegionDto;
import com.yonyou.dms.framework.service.cache.impl.DictCacheServiceImpl;
import com.yonyou.dms.framework.service.cache.impl.RegionCacheSerivceImpl;
import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelDefine;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.exception.UtilException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.NumberUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.io.IOUtils;

/**
 * 根据 Excel数据信息，生成Excel文件流 ExcelGenerator 接口的默认实现，可继承此类并并改写相关方法实现 Created by wfl.
 */

@Component
public class ExcelReadDefaultImpl<T extends DataImportDto> implements ExcelRead<T> {

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(ExcelReadDefaultImpl.class);
    @Autowired
    Validator                   validator;
    @Autowired
    ResourceBundleMessageSource messageSource;
    
    @Resource(name="DictCache")
    DictCacheServiceImpl<List<DictDto>> dictCacheSerivce;
    
    @Resource(name="RegionCache")
    RegionCacheSerivceImpl<RegionDto> regionCacheService;

    /**
     * Excel 解析, 本方法不负责 inputStream 的关闭
     *
     * @param inputStream 输入法，本方法不负责 inputStream 的关闭
     * @return Map<String,List<List<Object>> > 一个sheet名称对应一个List数据
     * @throws IOException
     */

    public Map<String, ImportResultDto<T>> analyzeExcel(MultipartFile excelFile,
                                                        List<AbstractExcelReadCallBack<T>> callBackList) throws IOException {
        Workbook wb = null;
        try {
            String fileName = excelFile.getOriginalFilename();

            if (fileName.indexOf(".xlsx") > -1) {
                wb = new XSSFWorkbook(excelFile.getInputStream());
            } else {
                wb = new HSSFWorkbook(excelFile.getInputStream());
            }

            if (!CommonUtils.isNullOrEmpty(callBackList) && callBackList.size() >= 1) {
                Map<String, ImportResultDto<T>> sheetMap = new HashMap<>();
                for (int i = 0; i < callBackList.size(); i++) {
                    Sheet sheet = wb.getSheetAt(i);
                    sheetMap.put(sheet.getSheetName(), analyzeExcelXls(sheet, callBackList.get(i)));
                }
                return sheetMap;
            } else {
                throw new UtilException("excel 配置不正确");
            }
        } finally {
            IOUtils.closeStream(wb);
        }

    }

    /**
     * 解析 2003 格式excel ;.xls
     * 
     * @author zhangxc
     * @date 2016年8月10日
     * @param inputStream
     * @return
     */
    private ImportResultDto<T> analyzeExcelXls(Sheet sheet, AbstractExcelReadCallBack<T> callBack) {
        try {
            //如果包含经销商字段，则对经销商字段进行赋值
            LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
            Locale locale = loginInfo.getLocale();
            // 循环sheet页
            int rowCount = sheet.getLastRowNum();
            logger.debug("rownum:"+rowCount);
            if (rowCount == 0) {
                throw new ServiceBizException("导入的excel文件为空");
            }

            ArrayList<T> dataList = new ArrayList<>();
            ArrayList<T> errorList = new ArrayList<>();
            List<String> fieldList = new ArrayList<>();
            // 定义导入结果对象
            ImportResultDto<T> resultDto = new ImportResultDto<>();
            resultDto.setDataList(dataList);
            resultDto.setErrorList(errorList);

            Class<T> dtoClass = callBack.getDtoClass();
            // 获得字段映射关系
            Map<Integer, ExcelMapping> columnMapping = getExcelColumnMapping(dtoClass);
            // 获得列数
            int cellNum = sheet.getRow(0).getLastCellNum();// 取第一行的有效单元格数量
            // 获得从哪一行开始读取
            int startRow = getStartRow(dtoClass);

            boolean isValidateSucess = true;
            
            //设置excel 的标题列
            for(int j=0;j<startRow;j++){
                Row row = sheet.getRow(j);
                for (int k = 0; k < cellNum; k++) {
                    Cell cell = row.getCell(k);
                    Object cellValue = getCellStringValue(cell);
                    fieldList.add(cellValue.toString());
                }
            }
            
            // 循环有效的行
            for (int j = startRow; j <= rowCount; j++) {
                boolean rowSucess = true;
                Row row = sheet.getRow(j);
                if (row == null) {
                    // 有效行为null是跳过
                    continue;
                }
                // 返回每一行的List数据
                T tinstance = dtoClass.newInstance();
                // 设置行号
                setExcelRowNO(tinstance, j);

                int blankCnt = cellNum;
                for (int k = 0; k < cellNum; k++) {
                    Cell cell = row.getCell(k);
                    Object cellValue = getCellStringValue(cell);
                    ExcelMapping excelMapping = columnMapping.get(k + 1);

                    if (excelMapping == null) {
                        throw new UtilException("excel 配置不正确，当前列：" + (k + 1));
                    }
                    // 设置字段的值给对应的对象
                    try{
                        setObjectValue(cellValue, tinstance, excelMapping,columnMapping);
                    }catch(Exception e){
                        logger.error(e.getMessage(),e);
                        setExcelErrorMsg(tinstance, fieldList.get(k)+":"+e.getMessage());
                        if (isValidateSucess) {
                            isValidateSucess = false;
                        }
                        rowSucess = false;
                        continue;
                    }

                    // 判断空列
                    if (!StringUtils.EMPTY_STRING.equals(cellValue)) {
                        blankCnt--;
                    }
                }
                // 跳出数据读取
                if (blankCnt >= cellNum) {
                    break;
                }

                // 对字段进行验证
                boolean isSucess = validateRowData(tinstance,locale,columnMapping,fieldList);
                if (!isSucess) {
                    if (isValidateSucess) {
                        isValidateSucess = false;
                    }
                    rowSucess = false;
                }

                // 如果存在回调函数
                if (callBack.getExcelReadCallBack() != null) {
                    try {
                        callBack.getExcelReadCallBack().readRowCallBack(tinstance, isValidateSucess);
                    } catch (Exception e) {
                        logger.error(e.getMessage(),e);
                        String errorMsg = e.getMessage();
                        if (isValidateSucess) {
                            isValidateSucess = false;
                        }
                        rowSucess = false;
                        // 设置错误信息
                        setExcelErrorMsg(tinstance, errorMsg);
                    }
                }
                // 如果本行也是失败的，则添加
                if (!rowSucess) {
                    errorList.add(tinstance);
                    if (errorList.size() >= CommonConstants.IMPORT_MAX_ERRORS_ROWS) {
                        break;
                    }
                }
                // 添加数据行
                dataList.add(tinstance);
            }
            if(CommonUtils.isNullOrEmpty(dataList)){
                throw new ServiceBizException("导入的excel文件为空");
            }
            // 设置成功标记
            resultDto.setSucess(isValidateSucess);
            return resultDto;
        } catch (ServiceBizException e) {
            throw e;
        } catch (Exception e) {
            throw new UtilException(e.getMessage(), e);
        } finally {

        }
    }

    /**
     * 设置行号
     * 
     * @author zhangxc
     * @date 2016年8月16日
     * @param tinstance
     * @param rowNo
     */
    private void setExcelRowNO(T tinstance, int rowNo) {
        Class<?> dtoClass = tinstance.getClass();
        Method setRowNOMethod;
        try {
            setRowNOMethod = dtoClass.getMethod("setRowNO", Integer.class);
            setObjectValueByMethod(setRowNOMethod, tinstance, rowNo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new UtilException("设置行号方法不存在");
        }
    }
    
    /**
     * 
    * 获得对象的值，根据字段定义
    * @author zhangxc
    * @date 2016年8月26日
    * @param tinstance
    * @param dataType
    * @param columnMapping
    * @return
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */
    private Object getObjectValueByDefineType(T tinstance,ExcelDataType dataType,Map<Integer, ExcelMapping> columnMapping){
        try{
            Class<?> dtoClass = tinstance.getClass();
            for(Map.Entry<Integer, ExcelMapping> entry:columnMapping.entrySet()){
                ExcelMapping mapping = entry.getValue();
                ExcelColumnDefine columnDefine = mapping.getColumnDefine();
                if(dataType==columnDefine.dataType()){
                    Field field = mapping.getField();
                    String fieldName = field.getName();
                    String getFieldName = "get" + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1, fieldName.length());
                    Method getValueMethod = dtoClass.getMethod(getFieldName);
                    
                    Object getValue = getValueMethod.invoke(tinstance);
                    return getValue;
                }
            }
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw new UtilException("get 方法不存在");
        }
        return null;
    }

    /**
     * 设置行号
     * 
     * @author zhangxc
     * @date 2016年8月16日
     * @param tinstance
     * @param rowNo
     */
    private void setExcelErrorMsg(T tinstance, String errorMsg) {
        Class<?> dtoClass = tinstance.getClass();
        Method setErrorMsgMethod;
        Method getErrorMsgMethod;
        try {
            setErrorMsgMethod = dtoClass.getMethod("setErrorMsg", String.class);
            getErrorMsgMethod = dtoClass.getMethod("getErrorMsg");
            String errorMsgNow = (String) getErrorMsgMethod.invoke(tinstance);
            if (StringUtils.isNullOrEmpty(errorMsgNow)) {
                setObjectValueByMethod(setErrorMsgMethod, tinstance, errorMsg);
            } else {
                setObjectValueByMethod(setErrorMsgMethod, tinstance, errorMsgNow + " ; " + errorMsg);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new UtilException("设置错误信息的方法不存在");
        }
    }

    /**
     * 对一行的数据进行校验
     * 
     * @author zhangxc
     * @date 2016年8月16日
     * @param tinstance
     */
    private boolean validateRowData(T tinstance,Locale locale,Map<Integer, ExcelMapping> columnMapping,List<String> fieldList) {
        String className = tinstance.getClass().getSimpleName();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(tinstance);
        if (constraintViolations != null && constraintViolations.size() > 0) {
            StringBuilder errorMsg = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : constraintViolations) {
                String consstraintName = constraintViolation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
                String constinatField = constraintViolation.getPropertyPath().toString();
                String newCode = consstraintName + "." + className + "." + constinatField;
                int errorFieldColumn = -1;
                //如果是excel 导入的字段
                if((errorFieldColumn = isExcelImportField(constinatField,columnMapping))!=-1){
                    String message;
                    // 获取错误信息
                    try {
                        message = messageSource.getMessage(newCode, null, locale);
                    } catch (NoSuchMessageException e) {
                        logger.error(e.getMessage(),e);
                        message = fieldList.get(errorFieldColumn-1)+":"+constraintViolation.getMessage();
                    }
                    errorMsg.append(message).append(" ; ");
                }
            }
            //如果存在错误
            if(errorMsg.length()>0){
                // 设置excel 错误信息
                setExcelErrorMsg(tinstance, errorMsg.toString());
                return false;
            }
        }
        return true;
    }

    /**
     * 
    * 判断是否是excel 导入的字段
    * @author zhangxc
    * @date 2016年8月29日
    * @param fieldName
    * @param columnMapping
    * @return
     */
    private int isExcelImportField(String fieldName,Map<Integer, ExcelMapping> columnMapping){
        for(Map.Entry<Integer, ExcelMapping> entry:columnMapping.entrySet()){
            ExcelMapping entryValue = entry.getValue();
            String fieldExcelName = entryValue.getField().getName();
            if(fieldExcelName.equals(fieldName)){
                return entry.getKey();
            }
        }
        return -1;
    }
    /**
     * 解析Excel 信息
     * 
     * @author zhangxc
     * @date 2016年8月12日
     * @param cellValue
     * @param tinstance
     * @param excelMapping
     */
    private void setObjectValue(Object cellValue, T tinstance, ExcelMapping excelMapping,Map<Integer, ExcelMapping> columnMapping) {
        //进行字段赋值
        try{
            Field field = excelMapping.getField();
            Method setMethod = excelMapping.getSetMethod();
            ExcelColumnDefine columnDefine = excelMapping.getColumnDefine();
            if (!StringUtils.isNullOrEmpty(cellValue)) {
                Class<?> valueClass = cellValue.getClass();
                Class<?> fieldClass = field.getType();

                ExcelDataType dataType = columnDefine.dataType();
                //如果未指定数据类型
                if (dataType != null && dataType != ExcelDataType.NotDefine) {
                    if(dataType==ExcelDataType.Dict){
                        Integer dataCode = columnDefine.dataCode();
                        if(dataCode==null||dataCode==-1){
                            throw new UtilException("TC_CODE 字典数据定义不正确");
                        }
                        Integer codeId = dictCacheSerivce.getCodeIdByDesc(dataCode, cellValue+"");
                        //设置tc_code 字典值
                        setObjectValueByMethod(setMethod, tinstance, codeId);
                        return;
                    }
                    
                    //设置省份
                    if(dataType==ExcelDataType.Region_Provice){
                        Long proviceId = regionCacheService.getProvinceIdByName(cellValue+"");
                        //设置省份ID
                        setObjectValueByMethod(setMethod, tinstance, proviceId);
                        return;
                    }
                    
                    //设置城市
                    if(dataType==ExcelDataType.Region_City){
                        Long provinceId = (Long)getObjectValueByDefineType(tinstance,ExcelDataType.Region_Provice,columnMapping);
                        Long cityId = regionCacheService.getCityIdByName(provinceId, cellValue+"");
                        //设置省份ID
                        setObjectValueByMethod(setMethod, tinstance, cityId);
                        return;
                    }
                    //设置区县
                    if(dataType==ExcelDataType.Region_Country){
                        Long provinceId = (Long)getObjectValueByDefineType(tinstance,ExcelDataType.Region_Provice,columnMapping);
                        Long cityId = (Long)getObjectValueByDefineType(tinstance,ExcelDataType.Region_City,columnMapping);
                        Long countryId = regionCacheService.getCountryIdByName(provinceId, cityId, cellValue+"");
                        //设置省份ID
                        setObjectValueByMethod(setMethod, tinstance, countryId);
                        return;
                    }
                    
                } else {

                    // 判断类型是否一致,或是否是
                    if (valueClass == fieldClass || fieldClass.toString().equals(valueClass.toString())
                        || valueClass.isAssignableFrom(fieldClass)) {
                        setObjectValueByMethod(setMethod, tinstance, cellValue);
                        return;
                    }
                    // 如果是日期类型
                    if (Date.class.toString().equals(fieldClass.toString())) {
                        if (String.class.toString().equals(valueClass.toString())) {
                            String dateFormat = null;
                            if (StringUtils.isNullOrEmpty(columnDefine.format())) {
                                dateFormat = "yyyy-mm-dd";
                            } else {
                                dateFormat = columnDefine.format();
                            }
                            // 对值进行转换
                            Date formatDate = com.yonyou.dms.function.utils.common.DateUtil.parseDate(cellValue.toString(),
                                                                                                      dateFormat);
                            // 设置值
                            setObjectValueByMethod(setMethod, tinstance, formatDate);
                            return;
                        }
                    }

                    // 如果是数字类型
                    if (Integer.class.toString().equals(fieldClass.toString())) {
                        if (String.class.toString().equals(valueClass.toString())
                            || Double.class.toString().equals(valueClass.toString())) {
                            // 设置值
                            setObjectValueByMethod(setMethod, tinstance, (int) Double.parseDouble(cellValue.toString()));
                            return;
                        }
                    }
                    
                    // 如果是数字类型
                    if (Long.class.toString().equals(fieldClass.toString())) {
                        if (String.class.toString().equals(valueClass.toString())
                            || Double.class.toString().equals(valueClass.toString())) {
                            // 设置值
                            setObjectValueByMethod(setMethod, tinstance, (long) Double.parseDouble(cellValue.toString()));
                            return;
                        }
                    }

                    // 如果是数字类型
                    if (Double.class.toString().equals(fieldClass.toString())) {
                        if (String.class.toString().equals(valueClass.toString())
                            || Integer.class.toString().equals(valueClass.toString())) {
                            // 设置值
                            setObjectValueByMethod(setMethod, tinstance, Double.parseDouble(cellValue.toString()));
                            return;
                        }
                    }
                    
                    // 如果是数字类型
                    if (String.class.toString().equals(fieldClass.toString())) {
                        if (Double.class.toString().equals(valueClass.toString())) {
                            // 设置值
                            setObjectValueByMethod(setMethod, tinstance, NumberUtil.getShortString((Double)cellValue));
                            return;
                        }
                    }
                }
            }
        }catch(UtilException e){
            throw e;
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            throw new UtilException(cellValue+"-->数据类型不正确");
        }
       
    }

    /**
     * 通过反射对字段进行赋值
     * 
     * @author zhangxc
     * @date 2016年8月16日
     * @param setMethod
     * @param tinstance
     * @param args
     */
    private void setObjectValueByMethod(Method setMethod, T tinstance, Object... args) {
        try {
            setMethod.invoke(tinstance, args);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new UtilException("SetValue 出错");
        }
    }

    /**
     * 获得某个DTO 类的字段映射关系
     * 
     * @author zhangxc
     * @date 2016年8月12日
     * @param dtoClass
     * @return key: 列的序号 value:列的字段定义
     */
    private Map<Integer, ExcelMapping> getExcelColumnMapping(Class<?> dtoClass) {
        Field[] fieldList = dtoClass.getDeclaredFields();
        Map<Integer, ExcelMapping> columnMapping = new HashMap<>();
        // 循环anotation
        if (fieldList != null && fieldList.length > 0) {
            for (int i = 0; i < fieldList.length; i++) {
                Field field = fieldList[i];
                ExcelColumnDefine excelColumnDefine = field.getAnnotation(ExcelColumnDefine.class);

                if (excelColumnDefine != null) {
                    Method setValueMethod = null;
                    try {
                        String fieldName = field.getName();
                        String setFieldName = "set" + fieldName.substring(0, 1).toUpperCase()
                                              + fieldName.substring(1, fieldName.length());
                        setValueMethod = dtoClass.getMethod(setFieldName, field.getType());
                    } catch (Exception e) {
                        logger.error(e.getMessage(),e);
                        throw new UtilException("未设置set 方法");
                    }
                    columnMapping.put(excelColumnDefine.value(),
                                      new ExcelMapping(field, excelColumnDefine, setValueMethod));
                }
            }

        }
        return columnMapping;
    }

    /**
     * 获得某个DTO 类的字段映射关系
     * 
     * @author zhangxc
     * @date 2016年8月12日
     * @param dtoClass
     * @return key: 列的序号 value:列的字段定义
     */
    private ExcelDefine getExcelDefine(Class<?> dtoClass) {
        ExcelDefine excelDefine = dtoClass.getAnnotation(ExcelDefine.class);
        return excelDefine;
    }

    /**
     * 查询从哪一行开始读取数据
     * 
     * @author zhangxc
     * @date 2016年8月16日
     * @param dtoClass
     * @return
     */
    private int getStartRow(Class<?> dtoClass) {
        ExcelDefine excelDefine = getExcelDefine(dtoClass);
        if (excelDefine != null) {
            return excelDefine.startRow();
        } else {
            return 1;
        }
    }

    /**
     * 解析excel 表格，并返回第一个sheet 页中内容
     * 
     * @author zhangxc
     * @date 2016年8月10日
     * @param inputStream
     * @return (non-Javadoc)
     * @throws IOException
     * @see com.yonyou.dms.function.service.excel.ExcelGenerator#analyzeExcelFirstSheet(java.io.InputStream)
     */
    public ImportResultDto<T> analyzeExcelFirstSheet(MultipartFile excelFile,
                                                     AbstractExcelReadCallBack<T> callBack) throws IOException {
        List<AbstractExcelReadCallBack<T>> callBackList = new ArrayList<>();
        callBackList.add(callBack);
        Map<String, ImportResultDto<T>> excelMap = analyzeExcel(excelFile, callBackList);
        if (!CommonUtils.isNullOrEmpty(excelMap)) {
            Iterator<ImportResultDto<T>> iterator = excelMap.values().iterator();
            if (iterator.hasNext()) {
                return iterator.next();
            } else {
                throw new ServiceBizException("excel 不正确");
            }
        } else {
            throw new ServiceBizException("excel 不正确");
        }
    }

    /**
     * 根据单元格不同属性返回字符串数值
     * 
     * @author zhangxc
     * @date 2016年8月12日
     * @param cell
     * @return
     */
    private static Object getCellStringValue(Cell cell) {
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:// 字符串类型
                    String cellStringValue = cell.getRichStringCellValue().toString();
                    if (StringUtils.isNullOrEmpty(cellStringValue.trim()))
                        cellStringValue = StringUtils.BLANK_SPRING_STRING;
                    return cellStringValue;
                case Cell.CELL_TYPE_NUMERIC: // 数值类型
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue();
                    } else if (cell.getCellStyle().getDataFormat() == 58) {
                        double value = cell.getNumericCellValue();
                        return org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
                    } else {
                        return cell.getNumericCellValue();
                    }
                case Cell.CELL_TYPE_FORMULA: // 公式
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    return String.valueOf(cell.getNumericCellValue());
                case Cell.CELL_TYPE_BLANK:
                    return StringUtils.EMPTY_STRING;
                case Cell.CELL_TYPE_BOOLEAN:
                    return StringUtils.EMPTY_STRING;
                case Cell.CELL_TYPE_ERROR:
                    return StringUtils.EMPTY_STRING;
                default:
                    return StringUtils.EMPTY_STRING;
            }
        } else {
            return StringUtils.EMPTY_STRING;
        }
    }

    /**
     * 定义Excel 字段映射关系对象
     * 
     * @author zhangxc
     * @date 2016年8月12日
     */
    class ExcelMapping {

        ExcelMapping(Field field, ExcelColumnDefine columnDefine, Method setMethod){
            this.field = field;
            this.columnDefine = columnDefine;
            this.setMethod = setMethod;
        }

        private Field             field;
        private ExcelColumnDefine columnDefine;
        private Method            setMethod;

        public Field getField() {
            return field;
        }

        public void setField(Field field) {
            this.field = field;
        }

        public ExcelColumnDefine getColumnDefine() {
            return columnDefine;
        }

        public void setColumnDefine(ExcelColumnDefine columnDefine) {
            this.columnDefine = columnDefine;
        }

        public Method getSetMethod() {
            return setMethod;
        }

        public void setSetMethod(Method setMethod) {
            this.setMethod = setMethod;
        }
    }
}
