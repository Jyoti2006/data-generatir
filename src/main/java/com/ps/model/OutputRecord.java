package com.ps.model;

import java.util.Objects;

public class OutputRecord {

	private String id;
	private String entityId;
	private String sourceName;
	private String logicalJson;
	private String validFrom;
	private String validTo;
	private String created;
	private String transactionTime;
	private String lastUpdated;
	private String ccid;
	private String posnId;
	private String locId;
	private String jobPrfId;
	private String emailId;
	private String activeStatus;
	private String st1LastUpdatedTime;
	private String coId;
	private String entitiyIdHash;
	private String employmentTypeCode;
	private String logicalJsonHash;
	private String logicalJsonZip;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getLogicalJson() {
		return logicalJson;
	}

	public void setLogicalJson(String logicalJson) {
		this.logicalJson = logicalJson;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidTo() {
		return validTo;
	}

	public void setValidTo(String validTo) {
		this.validTo = validTo;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(String transactionTime) {
		this.transactionTime = transactionTime;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getCcid() {
		return ccid;
	}

	public void setCcid(String ccid) {
		this.ccid = ccid;
	}

	public String getPosnId() {
		return posnId;
	}

	public void setPosnId(String posnId) {
		this.posnId = posnId;
	}

	public String getLocId() {
		return locId;
	}

	public void setLocId(String locId) {
		this.locId = locId;
	}

	public String getJobPrfId() {
		return jobPrfId;
	}

	public void setJobPrfId(String jobPrfId) {
		this.jobPrfId = jobPrfId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}

	public String getSt1LastUpdatedTime() {
		return st1LastUpdatedTime;
	}

	public void setSt1LastUpdatedTime(String st1LastUpdatedTime) {
		this.st1LastUpdatedTime = st1LastUpdatedTime;
	}

	public String getCoId() {
		return coId;
	}

	public void setCoId(String coId) {
		this.coId = coId;
	}

	public String getEntitiyIdHash() {
		return entitiyIdHash;
	}

	public void setEntitiyIdHash(String entitiyIdHash) {
		this.entitiyIdHash = entitiyIdHash;
	}

	public String getEmploymentTypeCode() {
		return employmentTypeCode;
	}

	public void setEmploymentTypeCode(String employmentTypeCode) {
		this.employmentTypeCode = employmentTypeCode;
	}

	public String getLogicalJsonHash() {
		return logicalJsonHash;
	}

	public void setLogicalJsonHash(String logicalJsonHash) {
		this.logicalJsonHash = logicalJsonHash;
	}

	public String getLogicalJsonZip() {
		return logicalJsonZip;
	}

	public void setLogicalJsonZip(String logicalJsonZip) {
		this.logicalJsonZip = logicalJsonZip;
	}

	@Override
	public String toString() {

		// "id+","+entityId+","+sourceName+","+logicalJson+","+validFrom+","+validTo+","+created+","+transactionTime+
		// ","+lastUpdated+","+ccid+","+posnId+","+locId+","+jobPrfId+","+emailId+","+
		// activeStatus+","+st1LastUpdatedTime+","+coId+","+entitiyIdHash+","+employmentTypeCode+","+logicalJsonHash+","+logicalJsonZip"

		return "\"" + id + "\"" + "," + "\"" + entityId + "\"" + "," + "\"" + sourceName + "\"" + "," + "\""
				+ logicalJson + "\"" + "," + "\"" + validFrom + "\"" + "," + "\"" + validTo + "\"" + "," + "\""
				+ created + "\"" + "," + "\"" + transactionTime + "\"" + "," + "\"" + lastUpdated + "\"" + "," + "\""
				+ Objects.toString(ccid, "") + "\"" + "," + "\"" + Objects.toString(posnId, "") + "\"" + "," + "\""
				+ Objects.toString(locId, "") + "\"" + "," + "\"" + Objects.toString(jobPrfId, "") + "\"" + "," + "\""
				+ Objects.toString(emailId, "") + "\"" + "," + "\"" + Objects.toString(activeStatus, "") + "\"" + ","
				+ "\"" + st1LastUpdatedTime + "\"" + "," + "\"" + Objects.toString(coId, "") + "\"" + "," + "\""
				+ Objects.toString(entitiyIdHash, "") + "\"" + "," + "\"" + Objects.toString(employmentTypeCode, "")
				+ "\"" + "," + "\"" + Objects.toString(logicalJsonHash, "") + "\"" + "," + "\""
				+ Objects.toString(logicalJsonZip, "") + "\"";

	}

}
