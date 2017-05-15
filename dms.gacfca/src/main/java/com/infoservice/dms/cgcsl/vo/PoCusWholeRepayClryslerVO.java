package com.infoservice.dms.cgcsl.vo;

import java.util.Date;
import java.util.LinkedList;

public class PoCusWholeRepayClryslerVO extends BaseVO{
	
	private static final long serialVersionUID = 1L;
	//批售审批单主VO	
	private String wsNo;//	报备单号	CHAR(12)
	private String wsAuditor;//批返利审核人		VARCHAR(60)
	private Integer soStatus;//批售返利申请状态	NUMERIC(8)
	private Date auditingDate;//审核日期 TIMESTAMP
	private String wsAuditingRemark;//	审核备注		VARCHAR(255)
	private String companyName;
	private String dealerCode;//经销商代码 	CHAR(8)
	private Date submitTime;//上报日期  TIMESTAMP
	private Long submitBy;//上报人
	private String remark; //备注
	private Integer wsAppType;//批售类型 ： 集团销售 影响力人物 团购 可修改
	private Integer amount;//成交数量
	
	private String fileUrlAId;//									集团销售申请表
	private String fileUrlBId;//										客户单位组织机构代码证
	private String fileUrlCId;//										租赁类企业营业执照
	private String fileUrlDId;//										集团销售明细表
	private String fileUrlEId;//										销售合同
	private String fileUrlFId;//										影响力人物申请表
	private String fileUrlGId;//										客户身份证明
	private String fileUrlHId;//										身份证
	private String fileUrlIId;//										销售合同
	private String fileUrlJId;//										团体客户申请表
	private String fileUrlKId;//										团体客户身份证明
	private String fileUrlLId;//										销售合同
	private String fileUrlRId;//                                 补充5                           
	private String fileUrlNId;//                                 补充1
	private String fileUrlOId;//                                 补充2
	private String fileUrlPId;//                                 补充3
	private String fileUrlQId;//                                 补充4
	 
	private String fileUrlA;//									集团销售申请表
	private String fileUrlB;//										客户单位组织机构代码证
	private String fileUrlC;//										租赁类企业营业执照
	private String fileUrlD;//										集团销售明细表
	private String fileUrlE;//										销售合同
	private String fileUrlF;//										影响力人物申请表
	private String fileUrlG;//										客户身份证明
	private String fileUrlH;//										身份证
	private String fileUrlI;//										销售合同
	private String fileUrlJ;//										团体客户申请表
	private String fileUrlK;//										团体客户身份证明
	private String fileUrlL;//										销售合同
	private String fileUrlR; //                                      补充5
	private String fileUrlN;//                                      补充1
	private String fileUrlO;//                                      补充2
	private String fileUrlP;//                                      补充3
	private String fileUrlQ;//                                      补充4
    

	
	private String fileUrlMId; //团购客户营业执照
	private String fileUrlM; //团购客户营业执照
	private String headApprovalFileUrl; //总部审批附件(审核通过之后下发)
	
	private String contractFileAid; //销售合同a附件ID
	private String contractFileAurl; //销售合同a附件URL
	private String contractFileBid; //销售合同b附件ID
	private String contractFileBurl; //销售合同b附件URL
	
	private Integer wsEmployeeType;// 企业员工分类    15961001  一般企业员工 15961002  战略企业及其下属子公司分公司员工
									//15961003  平安集团员工   15961004  公务员

	private LinkedList<WsConfigInfoRepayClryslerVO> configList;//批售配置VO
	private Date activityDate;//激活时间
	private Integer wsthreeType;
	public String getWsNo() {
		return wsNo;
	}
	public void setWsNo(String wsNo) {
		this.wsNo = wsNo;
	}
	public String getWsAuditor() {
		return wsAuditor;
	}
	public void setWsAuditor(String wsAuditor) {
		this.wsAuditor = wsAuditor;
	}
	public Integer getSoStatus() {
		return soStatus;
	}
	public void setSoStatus(Integer soStatus) {
		this.soStatus = soStatus;
	}
	public Date getAuditingDate() {
		return auditingDate;
	}
	public void setAuditingDate(Date auditingDate) {
		this.auditingDate = auditingDate;
	}
	public String getWsAuditingRemark() {
		return wsAuditingRemark;
	}
	public void setWsAuditingRemark(String wsAuditingRemark) {
		this.wsAuditingRemark = wsAuditingRemark;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public Long getSubmitBy() {
		return submitBy;
	}
	public void setSubmitBy(Long submitBy) {
		this.submitBy = submitBy;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getWsAppType() {
		return wsAppType;
	}
	public void setWsAppType(Integer wsAppType) {
		this.wsAppType = wsAppType;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getFileUrlAId() {
		return fileUrlAId;
	}
	public void setFileUrlAId(String fileUrlAId) {
		this.fileUrlAId = fileUrlAId;
	}
	public String getFileUrlBId() {
		return fileUrlBId;
	}
	public void setFileUrlBId(String fileUrlBId) {
		this.fileUrlBId = fileUrlBId;
	}
	public String getFileUrlCId() {
		return fileUrlCId;
	}
	public void setFileUrlCId(String fileUrlCId) {
		this.fileUrlCId = fileUrlCId;
	}
	public String getFileUrlDId() {
		return fileUrlDId;
	}
	public void setFileUrlDId(String fileUrlDId) {
		this.fileUrlDId = fileUrlDId;
	}
	public String getFileUrlEId() {
		return fileUrlEId;
	}
	public void setFileUrlEId(String fileUrlEId) {
		this.fileUrlEId = fileUrlEId;
	}
	public String getFileUrlFId() {
		return fileUrlFId;
	}
	public void setFileUrlFId(String fileUrlFId) {
		this.fileUrlFId = fileUrlFId;
	}
	public String getFileUrlGId() {
		return fileUrlGId;
	}
	public void setFileUrlGId(String fileUrlGId) {
		this.fileUrlGId = fileUrlGId;
	}
	public String getFileUrlHId() {
		return fileUrlHId;
	}
	public void setFileUrlHId(String fileUrlHId) {
		this.fileUrlHId = fileUrlHId;
	}
	public String getFileUrlIId() {
		return fileUrlIId;
	}
	public void setFileUrlIId(String fileUrlIId) {
		this.fileUrlIId = fileUrlIId;
	}
	public String getFileUrlJId() {
		return fileUrlJId;
	}
	public void setFileUrlJId(String fileUrlJId) {
		this.fileUrlJId = fileUrlJId;
	}
	public String getFileUrlKId() {
		return fileUrlKId;
	}
	public void setFileUrlKId(String fileUrlKId) {
		this.fileUrlKId = fileUrlKId;
	}
	public String getFileUrlLId() {
		return fileUrlLId;
	}
	public void setFileUrlLId(String fileUrlLId) {
		this.fileUrlLId = fileUrlLId;
	}
	public String getFileUrlRId() {
		return fileUrlRId;
	}
	public void setFileUrlRId(String fileUrlRId) {
		this.fileUrlRId = fileUrlRId;
	}
	public String getFileUrlNId() {
		return fileUrlNId;
	}
	public void setFileUrlNId(String fileUrlNId) {
		this.fileUrlNId = fileUrlNId;
	}
	public String getFileUrlOId() {
		return fileUrlOId;
	}
	public void setFileUrlOId(String fileUrlOId) {
		this.fileUrlOId = fileUrlOId;
	}
	public String getFileUrlPId() {
		return fileUrlPId;
	}
	public void setFileUrlPId(String fileUrlPId) {
		this.fileUrlPId = fileUrlPId;
	}
	public String getFileUrlQId() {
		return fileUrlQId;
	}
	public void setFileUrlQId(String fileUrlQId) {
		this.fileUrlQId = fileUrlQId;
	}
	public String getFileUrlA() {
		return fileUrlA;
	}
	public void setFileUrlA(String fileUrlA) {
		this.fileUrlA = fileUrlA;
	}
	public String getFileUrlB() {
		return fileUrlB;
	}
	public void setFileUrlB(String fileUrlB) {
		this.fileUrlB = fileUrlB;
	}
	public String getFileUrlC() {
		return fileUrlC;
	}
	public void setFileUrlC(String fileUrlC) {
		this.fileUrlC = fileUrlC;
	}
	public String getFileUrlD() {
		return fileUrlD;
	}
	public void setFileUrlD(String fileUrlD) {
		this.fileUrlD = fileUrlD;
	}
	public String getFileUrlE() {
		return fileUrlE;
	}
	public void setFileUrlE(String fileUrlE) {
		this.fileUrlE = fileUrlE;
	}
	public String getFileUrlF() {
		return fileUrlF;
	}
	public void setFileUrlF(String fileUrlF) {
		this.fileUrlF = fileUrlF;
	}
	public String getFileUrlG() {
		return fileUrlG;
	}
	public void setFileUrlG(String fileUrlG) {
		this.fileUrlG = fileUrlG;
	}
	public String getFileUrlH() {
		return fileUrlH;
	}
	public void setFileUrlH(String fileUrlH) {
		this.fileUrlH = fileUrlH;
	}
	public String getFileUrlI() {
		return fileUrlI;
	}
	public void setFileUrlI(String fileUrlI) {
		this.fileUrlI = fileUrlI;
	}
	public String getFileUrlJ() {
		return fileUrlJ;
	}
	public void setFileUrlJ(String fileUrlJ) {
		this.fileUrlJ = fileUrlJ;
	}
	public String getFileUrlK() {
		return fileUrlK;
	}
	public void setFileUrlK(String fileUrlK) {
		this.fileUrlK = fileUrlK;
	}
	public String getFileUrlL() {
		return fileUrlL;
	}
	public void setFileUrlL(String fileUrlL) {
		this.fileUrlL = fileUrlL;
	}
	public String getFileUrlR() {
		return fileUrlR;
	}
	public void setFileUrlR(String fileUrlR) {
		this.fileUrlR = fileUrlR;
	}
	public String getFileUrlN() {
		return fileUrlN;
	}
	public void setFileUrlN(String fileUrlN) {
		this.fileUrlN = fileUrlN;
	}
	public String getFileUrlO() {
		return fileUrlO;
	}
	public void setFileUrlO(String fileUrlO) {
		this.fileUrlO = fileUrlO;
	}
	public String getFileUrlP() {
		return fileUrlP;
	}
	public void setFileUrlP(String fileUrlP) {
		this.fileUrlP = fileUrlP;
	}
	public String getFileUrlQ() {
		return fileUrlQ;
	}
	public void setFileUrlQ(String fileUrlQ) {
		this.fileUrlQ = fileUrlQ;
	}
	public String getFileUrlMId() {
		return fileUrlMId;
	}
	public void setFileUrlMId(String fileUrlMId) {
		this.fileUrlMId = fileUrlMId;
	}
	public String getFileUrlM() {
		return fileUrlM;
	}
	public void setFileUrlM(String fileUrlM) {
		this.fileUrlM = fileUrlM;
	}
	public String getHeadApprovalFileUrl() {
		return headApprovalFileUrl;
	}
	public void setHeadApprovalFileUrl(String headApprovalFileUrl) {
		this.headApprovalFileUrl = headApprovalFileUrl;
	}
	public String getContractFileAid() {
		return contractFileAid;
	}
	public void setContractFileAid(String contractFileAid) {
		this.contractFileAid = contractFileAid;
	}
	public String getContractFileAurl() {
		return contractFileAurl;
	}
	public void setContractFileAurl(String contractFileAurl) {
		this.contractFileAurl = contractFileAurl;
	}
	public String getContractFileBid() {
		return contractFileBid;
	}
	public void setContractFileBid(String contractFileBid) {
		this.contractFileBid = contractFileBid;
	}
	public String getContractFileBurl() {
		return contractFileBurl;
	}
	public void setContractFileBurl(String contractFileBurl) {
		this.contractFileBurl = contractFileBurl;
	}
	public Integer getWsEmployeeType() {
		return wsEmployeeType;
	}
	public void setWsEmployeeType(Integer wsEmployeeType) {
		this.wsEmployeeType = wsEmployeeType;
	}
	public LinkedList<WsConfigInfoRepayClryslerVO> getConfigList() {
		return configList;
	}
	public void setConfigList(LinkedList<WsConfigInfoRepayClryslerVO> configList) {
		this.configList = configList;
	}
	public Date getActivityDate() {
		return activityDate;
	}
	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}
	public Integer getWsthreeType() {
		return wsthreeType;
	}
	public void setWsthreeType(Integer wsthreeType) {
		this.wsthreeType = wsthreeType;
	}
	

}
