package com.znv.controller;

import com.alibaba.fastjson.JSONObject;
import com.znv.bean.Subscriber;
import com.znv.utils.LogUtil;
import com.znv.websocket.PushService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscribe")
public class SubscribeController {

	/**
	 * 消息订阅， 样例：http://localhost:8018/subscribe/device?sessionId=&isSubscribe=1&
	 * subscribeTypes=1000201&precinctId=&deviceKinds=all&deviceIds=all
	 * 
	 * @param sessionId
	 *            客户端session
	 * @param isSubscribe
	 *            订阅/取消订阅
	 * @param types
	 *            业务类型,可多个用逗号隔开
	 * @param deviceKinds
	 *            设备类型,可多个用逗号隔开
	 * @param deviceIds
	 *            设备id,可多个用逗号隔开
	 * @param precinctId
	 *            区域id
	 * @return
	 */
	@RequestMapping(value = "/device", method = RequestMethod.GET)
	public String SubscribeByDevice(
			@RequestParam(value = "sessionId", required = true) String sessionId,
			@RequestParam(value = "isSubscribe", required = true) String isSubscribe,
			@RequestParam(value = "subscribeTypes", required = false) String types,
			@RequestParam(value = "deviceKinds", required = false) String deviceKinds,
			@RequestParam(value = "deviceIds", required = false) String deviceIds,
			@RequestParam(value = "precinctId", required = false) String precinctId) {

		JSONObject result = new JSONObject();
		result.put("result", 0);

		try {
			if ("0".equals(isSubscribe)) {
				PushService.subscriberMap.remove(sessionId);
			} else if (PushService.webSocketMap.containsKey(sessionId)) {
				Subscriber subscriber = PushService.subscriberMap
						.get(sessionId);
				if (subscriber == null) {
					subscriber = new Subscriber(sessionId, types, precinctId,
							deviceKinds, deviceIds);
					PushService.subscriberMap.put(sessionId, subscriber);
				} else {
					subscriber.setTypes(types);
					subscriber.setPrecinctId(precinctId);
					subscriber.setDeviceKinds(deviceKinds);
					subscriber.setDeviceIds(deviceIds);
				}

				System.out.println();
				LogUtil.info(
						"Subscribe Client...session is:({}),types is:({}),deviceKinds is:({}),deviceIds is:({}),precinctId is:({})",
						sessionId, types, deviceKinds, deviceIds, precinctId);
				System.out.println();
			}
		} catch (Exception e) {
			result.put("result", 1);
			result.put("remark", e.getMessage());
			LogUtil.error(e.getMessage());
		}

		return result.toJSONString();
	}
}
