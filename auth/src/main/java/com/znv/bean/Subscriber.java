package com.znv.bean;

/**
 * 协议订阅信息
 */
public class Subscriber {
	private String sessionId;
	private String types;
	private String precinctId;
	private String deviceIds;
	private String deviceKinds;

	public Subscriber(String sessionId, String types, String precinctId,
                      String deviceKinds, String deviceIds) {
		this.sessionId = sessionId;
		this.types = types;
		this.precinctId = precinctId;
		this.deviceKinds = deviceKinds;
		this.deviceIds = deviceIds;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getDeviceIds() {
		return deviceIds;
	}

	public void setDeviceIds(String deviceIds) {
		this.deviceIds = deviceIds;
	}

	public String getPrecinctId() {
		return precinctId;
	}

	public void setPrecinctId(String precinctId) {
		this.precinctId = precinctId;
	}

	public String getDeviceKinds() {
		return deviceKinds;
	}

	public void setDeviceKinds(String deviceKinds) {
		this.deviceKinds = deviceKinds;
	}
}
