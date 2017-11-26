package main.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class User implements Serializable{
	
	private String id;
	private String userName;
	private String sex;
	private String age;
	
	private static User user = new User();
	public static User getInstance() {
		return user;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	
}
