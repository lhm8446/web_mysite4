package com.bit2016.mysite.vo;

public class GuestBookVo {
	private Long no;
	private String name;
	private String content;
	private String password;
	private String reg_date;
	private int rank;
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	@Override
	public String toString() {
		return "GuestBookVo [no=" + no + ", name=" + name + ", content=" + content + ", password=" + password
				+ ", reg_date=" + reg_date + ", rank=" + rank + "]";
	}


}
