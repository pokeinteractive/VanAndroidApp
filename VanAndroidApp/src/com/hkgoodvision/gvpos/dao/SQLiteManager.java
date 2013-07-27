package com.hkgoodvision.gvpos.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteManager extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "gv_food.db"; // 資料庫名稱
	private static final int DATABASE_VERSION = 5; // 資料庫版本

	private SQLiteDatabase db;

	public SQLiteManager(Context context) { // 建構子
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		//db = this.getWritableDatabase();
	}

	public List<Food> getFoodByPage(int pageNo) {

		db = this.getWritableDatabase();
		
		List<Food> list = new ArrayList<Food>();

		Cursor cursor = null;
		try {
			cursor = db.rawQuery("select f.food_id,f.food_name,f.photo_x,f.photo_y,f.photo_width,f.photo_height, "
					+ " f.order_x,f.order_y,f.order_width,f.order_height,f.food_cat_id,price,f.page "
					+ " from food f, food_cat c where f.food_cat_id=c.food_cat_id and f.page = " + pageNo
					+ " ORDER BY order_x ", null);

			int rows_num = cursor.getCount();// 取得資料表列數
			if (rows_num != 0) {
				cursor.moveToFirst(); // 將指標移至第一筆資料
				
				for (int i = 0; i < rows_num; i++) {
					Food food = new Food();
					food.foodId = cursor.getInt(0);
					food.foodName =cursor.getString(1);
					food.photoX =cursor.getInt(2);
					food.photoY =cursor.getInt(3);
					food.photoWidth =cursor.getInt(4);
					food.photoHeight =cursor.getInt(5);
					food.orderX =cursor.getInt(6);
					food.orderY =cursor.getInt(7);
					food.orderWidth =cursor.getInt(8);
					food.orderHeight =cursor.getInt(9);
					food.foodCatId =cursor.getInt(10);
					food.price =cursor.getInt(11);
					food.page =cursor.getInt(12);
					list.add(food);
					Log.d("getFoodByPage", "[foodid]=" + food.foodId);
					cursor.moveToNext();
				}
			}
		} finally {
			if (cursor != null)
				cursor.close();
			db.close();
		}

		return list;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db = this.getWritableDatabase();
		
		String DATABASE_CREATE_TABLE = "create table food (" + "_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ " food_id INT NOT NULL," + "food_name VARCHAR NOT NULL," + "food_name_eng VARCHAR," + "food_name_other VARCHAR, "
				+ " price INT," + "photo_x INT," + "photo_y INT," + "photo_width INT," + "photo_height INT,"
				+ " order_x INT," + "order_y INT," + "order_width INT," + "order_height INT, food_cat_id INT NOT NULL, page INT NOT NULL"
				+ ");";

		db.execSQL(DATABASE_CREATE_TABLE);

		DATABASE_CREATE_TABLE = "create table food_cat (" + "_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ " food_cat_id INT NOT NULL," + "food_cat_name VARCHAR NOT NULL," + "food_cat_name_eng VARCHAR,"
				+ "food_cat_name_other VARCHAR " + ");";

		db.execSQL(DATABASE_CREATE_TABLE);
		
		// insert init record
		String sql = "insert into food_cat (food_cat_id,food_cat_name) values (1,'湯麵')";
		db.execSQL(sql);
		sql = " insert into food (food_cat_id,food_id,food_name,price,photo_x,photo_y,photo_width,photo_height," +
					 "order_x,order_y,order_width,order_height,page) " +
				"values (1,1,'牛肉湯麵A',45," + "50,100,100,40," + 	"250,100,50,40," + "0);";
		db.execSQL(sql);
		sql = " insert into food (food_cat_id,food_id,food_name,price,photo_x,photo_y,photo_width,photo_height," +
				 "order_x,order_y,order_width,order_height,page) " +
			"values (1,2,'牛肉湯麵B',51," + "50,200,100,40," + 	"250,200,50,40," + "0);";
		
		db.execSQL(sql);
		sql = " insert into food (food_cat_id,food_id,food_name,price,photo_x,photo_y,photo_width,photo_height," +
				 "order_x,order_y,order_width,order_height,page) " +
			"values (1,3,'牛肉湯麵C',72," + "50,300,100,40," + 	"250,300,50,40," + "0);";
		
		Log.d("SQL insert data", "[sql]=" + sql);
		
		db.execSQL(sql);	
		
		db.close();

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// oldVersion=舊的資料庫版本；newVersion=新的資料庫版本
		if (true || newVersion>oldVersion ) {
			db.execSQL("DROP TABLE IF EXISTS food"); // 刪除舊有的資料表
			db.execSQL("DROP TABLE IF EXISTS food_cat"); // 刪除舊有的資料表
			onCreate(db);
		}
	}
	
	
	protected void onDestroy(){
	    // close befor super is called
		if (db.isOpen())
			db.close();
	    // sqliteDB.close(); // super.onDestroy may already has destroyed the DB
	}
	
}