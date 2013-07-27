package com.hkgoodvision.gvpos.dao;

import java.math.BigDecimal;

public class Food {
	
	// dynamic field
	int orderQty;
	
	// ==================
	
	public int getOrderQty() {
		return orderQty;
	}

	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}

	int foodId;
	
	String foodName;
	String foodNameEng;
	String foodNameOther;
	
	int price;
	
	// if click on this range box, show photo.
	int photoX;
	int photoY;
	int photoWidth;
	int photoHeight;
	
	// if click, add the food to order list
	int orderX;
	int orderY;	
	int orderWidth;
	int orderHeight;
	
	// the category of food.
	int foodCatId;
	int page;

	public int getFoodId() {
		return foodId;
	}

	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public String getFoodNameEng() {
		return foodNameEng;
	}

	public void setFoodNameEng(String foodNameEng) {
		this.foodNameEng = foodNameEng;
	}

	public String getFoodNameOther() {
		return foodNameOther;
	}

	public void setFoodNameOther(String foodNameOther) {
		this.foodNameOther = foodNameOther;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getPhotoX() {
		return photoX;
	}

	public void setPhotoX(int photoX) {
		this.photoX = photoX;
	}

	public int getPhotoY() {
		return photoY;
	}

	public void setPhotoY(int photoY) {
		this.photoY = photoY;
	}

	public int getPhotoWidth() {
		return photoWidth;
	}

	public void setPhotoWidth(int photoWidth) {
		this.photoWidth = photoWidth;
	}

	public int getPhotoHeight() {
		return photoHeight;
	}

	public void setPhotoHeight(int photoHeight) {
		this.photoHeight = photoHeight;
	}

	public int getOrderX() {
		return orderX;
	}

	public void setOrderX(int orderX) {
		this.orderX = orderX;
	}

	public int getOrderY() {
		return orderY;
	}

	public void setOrderY(int orderY) {
		this.orderY = orderY;
	}

	public int getOrderWidth() {
		return orderWidth;
	}

	public void setOrderWidth(int orderWidth) {
		this.orderWidth = orderWidth;
	}

	public int getOrderHeight() {
		return orderHeight;
	}

	public void setOrderHeight(int orderHeight) {
		this.orderHeight = orderHeight;
	}

	public int getFoodCatId() {
		return foodCatId;
	}

	public void setFoodCatId(int foodCatId) {
		this.foodCatId = foodCatId;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	
}
