package com.vanapp.parser;

import com.vanapp.bean.ClientOrder;
import com.vanapp.util.DateUtil;

public class OrderMessageParser {
	public static ClientOrder parseMessage(String msg) {
		ClientOrder order = new ClientOrder();
		
		if (msg != null && msg.length() > 40) {
			
			order.setOrderId(msg.substring(0,8));
			order.setPhone(msg.substring(8,20));
			order.setOrderTime(DateUtil.toTimestamp(msg.substring(20, 39)));
			order.setMessage(msg.substring(40));
			
		} else {
			return null;
		}
			
		
		return order;
		
		
	}
}
