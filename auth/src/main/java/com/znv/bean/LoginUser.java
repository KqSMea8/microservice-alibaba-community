package com.znv.bean;

import java.security.MessageDigest;
import java.util.Date;

/**
 * 用户登录映射Bean
 * 
 * @author wr
 * @date 2018-3-7
 */
public class LoginUser {
	private String userId;
	private String userName;
	private String password;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String creatToken(){
		String time = new Date().toString();
		String text = userName + password + time;
		return MD5(text);
	}

	private String MD5(String s) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(s.getBytes("utf-8"));
			final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
			StringBuilder ret = new StringBuilder(bytes.length * 2);
			for (int i=0; i<bytes.length; i++) {
				ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
				ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
			}
			return ret.toString();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
