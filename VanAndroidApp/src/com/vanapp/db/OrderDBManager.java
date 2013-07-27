package com.vanapp.db;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vanapp.bean.ClientOrder;
import com.vanapp.util.DateUtil;

public class OrderDBManager {

	private DatabaseHelper dbHelper;

	private SQLiteDatabase database;

	public final static String ORDER_TABLE = "CLIENTORDER"; // name of table

	/**
	 * 
	 * @param context
	 */
	public OrderDBManager(Context context) {
		dbHelper = new DatabaseHelper(context);
		database = dbHelper.getWritableDatabase();
	}

	public long createOrder(ClientOrder order) {
		ContentValues values = new ContentValues();
		values.put(ClientOrder.ORDER_ID, order.getOrderId());
		values.put(ClientOrder.MESSAGE, order.getMessage());
		values.put(ClientOrder.PHONE, order.getPhone());
		values.put(ClientOrder.ORDERTIME, DateUtil.fromTimestamp(order.getOrderTime()));
		return database.insert(ORDER_TABLE, null, values);
	}

	public List<ClientOrder> selectOutstandingOrder() {
		String[] cols = new String[] { ClientOrder.ORDER_ID, ClientOrder.MESSAGE, ClientOrder.PHONE,
				ClientOrder.ORDERTIME };
		Cursor mCursor = database.query(true, ORDER_TABLE, cols, null, null, null, null, null, null);
		List<ClientOrder> orderList = new ArrayList<ClientOrder>();
		
		Timestamp currentTime = new Timestamp(System.currentTimeMillis()-600000);
		
		while (mCursor.moveToNext()) {
			Timestamp time = DateUtil.toTimestamp(mCursor.getString(4));
			if (time.after(currentTime)) {

				ClientOrder order = new ClientOrder();
				order.setOrderId(mCursor.getString(1));
				order.setMessage(mCursor.getString(2));
				order.setPhone(mCursor.getString(3));
				order.setOrderTime(DateUtil.toTimestamp(mCursor.getString(4)));

				orderList.add(order);
			}

		}

		return orderList;

	}

	public List<ClientOrder> selectAllOrder() {
		String[] cols = new String[] { ClientOrder.ORDER_ID, ClientOrder.MESSAGE, ClientOrder.PHONE,
				ClientOrder.ORDERTIME };
		Cursor mCursor = database.query(true, ORDER_TABLE, cols, null, null, null, null, null, null);
		List<ClientOrder> orderList = new ArrayList<ClientOrder>();

		while (mCursor.moveToNext()) {
			ClientOrder order = new ClientOrder();
			order.setOrderId(mCursor.getString(1));
			order.setMessage(mCursor.getString(2));
			order.setPhone(mCursor.getString(3));
			order.setOrderTime(DateUtil.toTimestamp(mCursor.getString(4)));

			orderList.add(order);

		}

		return orderList;

	}
}