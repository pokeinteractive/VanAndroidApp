package com.hkgoodvision.gvpos.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.hkgoodvision.gvpos.constant.FieldConstant;
import com.hkgoodvision.gvpos.dao.Food;

public class AppSession {

	static AppSession app = null;

	int currentPage = 0;

	// for listing the food that submit to system already
	List<Food> orderedFood = new ArrayList<Food>();
	// for listing the food that pending to submit order
	List<Food> pendingOrderFood = new ArrayList<Food>();
	
	

	public void addPendingOrderFood(Food food, int qty) {
		
		// find if the food already in the list
		boolean notfound = true;
		for (Food foodTemp : pendingOrderFood) {
			if (food.getFoodId() == foodTemp.getFoodId()) {
				notfound = false;
				foodTemp.setOrderQty(foodTemp.getOrderQty() + qty);
				
			}
		}
		

		
		if (notfound) {
			food.setOrderQty(qty);
			pendingOrderFood.add(food);

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put(FieldConstant.ORDER_LIST_ITEM_NAME,food.getFoodName());
			map.put(FieldConstant.ORDER_LIST_ITEM_PRICE,food.getPrice());
			map.put(FieldConstant.ORDER_LIST_ITEM_QTY,food.getOrderQty());
			
		}
		

		// calcuate total amount
		int totalAmount = 0; 
		for (Food foodT : pendingOrderFood) {
			totalAmount += foodT.getPrice() * foodT.getOrderQty();
		}
		for (Food foodT : orderedFood) {
			totalAmount += foodT.getPrice() * foodT.getOrderQty();
		}	
	
		
	}
	


	protected AppSession() {

	}

	public static AppSession getInstance() {
		if (app == null)
			app = new AppSession();

		return app;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	


}
