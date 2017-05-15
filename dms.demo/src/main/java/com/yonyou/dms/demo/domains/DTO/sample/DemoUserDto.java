package com.yonyou.dms.demo.domains.DTO.sample;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.domains.DTO.DataImportDto;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateDeserializer;
import com.yonyou.dms.function.utils.jsonSerializer.date.JsonSimpleDateSerializer;
import com.yonyou.dms.function.utils.validate.define.ChineseLength;
import com.yonyou.dms.function.utils.validate.define.Email;
import com.yonyou.dms.function.utils.validate.define.IDNumber;
import com.yonyou.dms.function.utils.validate.define.License;
import com.yonyou.dms.function.utils.validate.define.MaxDigit;
import com.yonyou.dms.function.utils.validate.define.PartCode;
import com.yonyou.dms.function.utils.validate.define.Passwd;
import com.yonyou.dms.function.utils.validate.define.Phone;
import com.yonyou.dms.function.utils.validate.define.Required;
import com.yonyou.dms.function.utils.validate.define.VIN;
import com.yonyou.dms.function.utils.validate.define.ZipCode;

/**
 * 
* UserDto
* @author zhangxc
* @date 2016年6月30日
* 
* @Null   被注释的元素必须为 null       
@Required    被注释的元素必须不为 null       
@AssertTrue     被注释的元素必须为 true       
@AssertFalse    被注释的元素必须为 false       
@Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值       
@Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值       
@DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值       
@DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值       
@Size(max=, min=)   被注释的元素的大小必须在指定的范围内       
@Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内       
@Past   被注释的元素必须是一个过去的日期       
@Future     被注释的元素必须是一个将来的日期       
@Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式       
Hibernate Validator 附加的 constraint       
@NotBlank(message =)   验证字符串非null，且长度必须大于0       
@Email  被注释的元素必须是电子邮箱地址       
@Length(min=,max=)  被注释的字符串的大小必须在指定的范围内       
@NotEmpty   被注释的字符串的必须非空       
@Range(min=,max=,message=)  被注释的元素必须在合适的范围内  
 */
public class DemoUserDto extends DataImportDto{

    private Long userId;
    @ExcelColumnDefine(value=1)
    @Required
    @ChineseLength(min=5,max=20)
	private String name; //姓名,校验中文长度
    
    @ExcelColumnDefine(value=3)
    @Required
    @Max(99)
    @Min(20)
	private Integer age;//年龄,校验最大值，最小值
    
    @ExcelColumnDefine(value=6,dataType=ExcelDataType.Dict,dataCode = DictCodeConstants.GENDER)
    @Required
    private Integer sex;
    @ExcelColumnDefine(value=5)
    @Past
    private Date bridthday;//日期,校验过去的日期
    @ExcelColumnDefine(value=2)
    @Required
    private String username;
    @ExcelColumnDefine(value=4)
    private Double salary;

    @ExcelColumnDefine(value=7,dataType=ExcelDataType.Region_Provice)
    private Long provinceId;
    
    @ExcelColumnDefine(value=8,dataType=ExcelDataType.Region_City)
    private Long cityId;
    
    @ExcelColumnDefine(value=9,dataType=ExcelDataType.Region_Country)
    private Long countryId;
    
    @JsonDeserialize(using = JsonSimpleDateDeserializer.class)
    @JsonSerialize(using = JsonSimpleDateSerializer.class)
    @Required
    private Date entryTime;
    
    //文件上传的ID
    private String dmsFileIds;
    
    @Size(min=2)
    private List<String> region;//地区类型,校验集合类的大小 
    
    private List<DemoUserAddressDto> addressList;
    
    
    @VIN
    private String vin;//VIN 校验
    
    @IDNumber
    private String idNumber;//身份证号校验
    
    @License
    private String license;//车牌号校验
    
    @Email
    private String email;//邮箱
    
    @Phone
    private String phone;//手机号校验
    
    @ZipCode
    private String zipCode;//邮编校验
    
    @PartCode
    private String partCode;//配件号校验
    
    @Passwd
    private String firstPassword;//密码校验   
    
    @MaxDigit(7)
    private Integer millage; //里程校验,只校验整数位数
    
    @Digits(integer=8,fraction=3)
    //@MaxPrecision(3)//基本上用不到
    private Double decimal; //里程校验，校验整数位数与小数位数
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBridthday() {
		return bridthday;
	}

	public void setBridthday(Date bridthday) {
		this.bridthday = bridthday;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

    
    public List<String> getRegion() {
        return region;
    }

    
    public void setRegion(List<String> region) {
        this.region = region;
    }

    
    public List<DemoUserAddressDto> getAddressList() {
        return addressList;
    }

    
    public void setAddressList(List<DemoUserAddressDto> addressList) {
        this.addressList = addressList;
    }

    
    public Long getUserId() {
        return userId;
    }

    
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    
    public Long getProvinceId() {
        return provinceId;
    }

    
    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    
    public Long getCityId() {
        return cityId;
    }

    
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    
    public Long getCountryId() {
        return countryId;
    }

    
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    
    public String getVin() {
        return vin;
    }

    
    public void setVin(String vin) {
        this.vin = vin;
    }

    
    public String getIdNumber() {
        return idNumber;
    }

    
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    
    public String getLicense() {
        return license;
    }

    
    public void setLicense(String license) {
        this.license = license;
    }

    
    public String getEmail() {
        return email;
    }

    
    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getPhone() {
        return phone;
    }

    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    
    public String getZipCode() {
        return zipCode;
    }

    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    
    public String getPartCode() {
        return partCode;
    }

    
    public void setPartCode(String partCode) {
        this.partCode = partCode;
    }

    
    public String getFirstPassword() {
        return firstPassword;
    }

    
    public void setFirstPassword(String firstPassword) {
        this.firstPassword = firstPassword;
    }

    
    public Integer getMillage() {
        return millage;
    }

    
    public void setMillage(Integer millage) {
        this.millage = millage;
    }

    
    public Double getDecimal() {
        return decimal;
    }

    public void setDecimal(Double decimal) {
        this.decimal = decimal;
    }

    
    public String getDmsFileIds() {
        return dmsFileIds;
    }

    
    public void setDmsFileIds(String dmsFileIds) {
        this.dmsFileIds = dmsFileIds;
    }
    
}