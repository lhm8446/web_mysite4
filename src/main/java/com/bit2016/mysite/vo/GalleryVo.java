package com.bit2016.mysite.vo;

public class GalleryVo {
	private Long no;
	private String orgFileName;
	private String saveFileName;
	private String comments;
	private String extName;
	private Long fileSize;
	private String regDate;
	private Long userNo;
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getOrgFileName() {
		return orgFileName;
	}
	public void setOrgFileName(String orgFileName) {
		this.orgFileName = orgFileName;
	}
	public String getSaveFileName() {
		return saveFileName;
	}
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getExtName() {
		return extName;
	}
	public void setExtName(String extName) {
		this.extName = extName;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public Long getUserNo() {
		return userNo;
	}
	public void setUserNo(Long userNo) {
		this.userNo = userNo;
	}
	@Override
	public String toString() {
		return "GalleryVo [no=" + no + ", orgFileName=" + orgFileName + ", saveFileName=" + saveFileName + ", comments="
				+ comments + ", extName=" + extName + ", fileSize=" + fileSize + ", regDate=" + regDate + ", userNo="
				+ userNo + "]";
	}
	
}
