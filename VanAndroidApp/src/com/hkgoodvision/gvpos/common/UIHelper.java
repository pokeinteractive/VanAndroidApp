package com.hkgoodvision.gvpos.common;


import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Toast;

import com.callvan.gvpos.activity.R;
import com.hkgoodvision.gvpos.app.AppContext;
import com.hkgoodvision.gvpos.app.AppManager;
import com.hkgoodvision.gvpos.ui.AboutUsDialog;
import com.hkgoodvision.gvpos.ui.ImageDialog;
import com.hkgoodvision.gvpos.ui.OrderInfoDialog;
import com.hkgoodvision.gvpos.ui.RewardApplyDialog;
import com.hkgoodvision.gvpos.ui.RewardApplySuccessDialog;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class UIHelper {

	public final static int LISTVIEW_ACTION_INIT = 0x01;
	public final static int LISTVIEW_ACTION_REFRESH = 0x02;
	public final static int LISTVIEW_ACTION_SCROLL = 0x03;
	public final static int LISTVIEW_ACTION_CHANGE_CATALOG = 0x04;
	
	public final static int LISTVIEW_DATA_MORE = 0x01;
	public final static int LISTVIEW_DATA_LOADING = 0x02;
	public final static int LISTVIEW_DATA_FULL = 0x03;
	public final static int LISTVIEW_DATA_EMPTY = 0x04;
	
	public final static int LISTVIEW_DATATYPE_NEWS = 0x01;
	public final static int LISTVIEW_DATATYPE_BLOG = 0x02;
	public final static int LISTVIEW_DATATYPE_POST = 0x03;
	public final static int LISTVIEW_DATATYPE_TWEET = 0x04;
	public final static int LISTVIEW_DATATYPE_ACTIVE = 0x05;
	public final static int LISTVIEW_DATATYPE_MESSAGE = 0x06;
	public final static int LISTVIEW_DATATYPE_COMMENT = 0x07;
	
	public final static int REQUEST_CODE_FOR_RESULT = 0x01;
	public final static int REQUEST_CODE_FOR_REPLY = 0x02;
	
	/** 表情图片匹配 */
	private static Pattern facePattern = Pattern.compile("\\[{1}([0-9]\\d*)\\]{1}");
	
	/** 全局web样式 */
	public final static String WEB_STYLE = "<style>* {font-size:16px;line-height:20px;} p {color:#333;} a {color:#3E62A6;} img {max-width:310px;} " +
			"img.alignleft {float:left;max-width:120px;margin:0 10px 5px 0;border:1px solid #ccc;background:#fff;padding:2px;} " +
			"pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;} " +
			"a.tag {font-size:15px;text-decoration:none;background-color:#bbd6f3;border-bottom:2px solid #3E6D8E;border-right:2px solid #7F9FB6;color:#284a7b;margin:2px 2px 2px 0;padding:2px 4px;white-space:nowrap;}</style>";
	
	
	
	
	
//	/**
//	 * 动态点击跳转到相关新闻、帖子等
//	 * @param context
//	 * @param id
//	 * @param catalog 0其他  1新闻  2帖子  3动弹  4博客  
//	 */
//	public static void showActiveRedirect(Context context, Active active)
//	{
//		String url = active.getUrl();
//		//url为空-旧方法
//		if(StringUtils.isEmpty(url)) {
//			int id = active.getObjectId();
//			int catalog = active.getActiveType();
//			switch (catalog) {
//				case Active.CATALOG_OTHER:
//					//其他-无跳转
//					break;
//				case Active.CATALOG_NEWS:
//					showNewsDetail(context, id);
//					break;
//				case Active.CATALOG_POST:
//					showQuestionDetail(context, id);
//					break;
//				case Active.CATALOG_TWEET:
//					showTweetDetail(context, id);
//					break;
//				case Active.CATALOG_BLOG:
//					showBlogDetail(context, id);
//					break;
//			}
//		} else {
//			showUrlRedirect(context, url);
//		}
//	}

	public static void showRewardApply(Context context,final int rewardId, String rewardTitle, int rewardPoint)
	{
		Intent intent = new Intent(context, RewardApplyDialog.class);
		intent.putExtra("rewardId", rewardId);
		intent.putExtra("title", rewardTitle);
		intent.putExtra("point", rewardPoint);
		context.startActivity(intent);
	}
	
	public static void showRewardApplySuccess(Context context)
	{
		Intent intent = new Intent(context, RewardApplySuccessDialog.class);

		context.startActivity(intent);
	}

	
	public static void showOrderInfo(Context context,final String orderId)
	{
		Intent intent = new Intent(context, OrderInfoDialog.class);
		intent.putExtra("orderId", orderId);
		context.startActivity(intent);
	}	
	
	public static void showAboutUs(Context context)
	{
		Intent intent = new Intent(context, AboutUsDialog.class);
		context.startActivity(intent);
	}

	
	
	/**
	 * 显示图片对话框
	 * @param context
	 * @param imgUrl
	 */
	public static void showImageDialog(Context context, String imgUrl)
	{
		Intent intent = new Intent(context, ImageDialog.class);
		intent.putExtra("img_url", imgUrl);
		context.startActivity(intent);
	}
	


	/**
	 * 打开浏览器
	 * @param context
	 * @param url
	 */
	public static void openBrowser(Context context, String url){
		try {
			Uri uri = Uri.parse(url);  
			Intent it = new Intent(Intent.ACTION_VIEW, uri);  
			context.startActivity(it);
		} catch (Exception e) {
			e.printStackTrace();
			ToastMessage(context, "无法浏览此网页", 500);
		} 
	}
		
	
	/**
	 * 获取TextWatcher对象
	 * @param context
	 * @param tmlKey
	 * @return
	 */
	public static TextWatcher getTextWatcher(final Activity context, final String temlKey) {
		return new TextWatcher() {		
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				//保存当前EditText正在编辑的内容
				((AppContext)context.getApplication()).setProperty(temlKey, s.toString());
			}		
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}		
			public void afterTextChanged(Editable s) {}
		};
	}
	

	
	/**
	 * 组合动态的回复文本
	 * @param name
	 * @param body
	 * @return
	 */
	public static SpannableString parseActiveReply(String name,String body){
		SpannableString sp = new SpannableString(name+"："+body);
		//设置用户名字体加粗、高亮 
		sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#0e5986")), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
	}
	
	/**
	 * 组合消息文本
	 * @param name
	 * @param body
	 * @return
	 */
	public static SpannableString parseMessageSpan(String name,String body,String action){
		SpannableString sp = null;
		int start = 0;
		int end = 0;
		if(StringUtils.isEmpty(action)){
			sp = new SpannableString(name + "：" + body);
			end = name.length();
		}else{
			sp = new SpannableString(action + name + "：" + body);
			start = action.length();
			end = start + name.length();
		}
		//设置用户名字体加粗、高亮 
		sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new ForegroundColorSpan(Color.parseColor("#0e5986")), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
	}
	
	/**
	 * 组合回复引用文本
	 * @param name
	 * @param body
	 * @return
	 */
	public static SpannableString parseQuoteSpan(String name,String body){
		SpannableString sp = new SpannableString("回复："+name+"\n"+body);
		//设置用户名字体加粗、高亮 
		sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 3, 3+name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(Color.parseColor("#0e5986")), 3, 3+name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
	}
	
	/**
	 * 弹出Toast消息
	 * @param msg
	 */
	public static void ToastMessage(Context cont,String msg)
	{
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}
	public static void ToastMessage(Context cont,int msg)
	{
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}
	public static void ToastMessage(Context cont,String msg,int time)
	{
		Toast.makeText(cont, msg, time).show();
	}
	
	/**
	 * 点击返回监听事件
	 * @param activity
	 * @return
	 */
	public static View.OnClickListener finish(final Activity activity)
	{
		return new View.OnClickListener() {
			public void onClick(View v) {
				activity.finish();
			}
		};
	}	
	

	
//	/**
//	 * 显示用户反馈
//	 * @param context
//	 */
//	public static void showFeedBack(Context context)
//	{
//		Intent intent = new Intent(context, FeedBack.class);
//		context.startActivity(intent);
//	}
	
	/**
	 * 文章是否加载图片显示
	 * @param activity
	 */
	public static void changeSettingIsLoadImage(Activity activity)
	{
		AppContext ac = (AppContext)activity.getApplication();
		if(ac.isLoadImage()){
			ac.setConfigLoadimage(false);
			ToastMessage(activity, "已设置文章不加载图片");
		}else{
			ac.setConfigLoadimage(true);
			ToastMessage(activity, "已设置文章加载图片");
		}
	}
	public static void changeSettingIsLoadImage(Activity activity,boolean b)
	{
		AppContext ac = (AppContext)activity.getApplication();
		ac.setConfigLoadimage(b);
	}
	
	/**
	 * 清除app缓存
	 * @param activity
	 */
	public static void clearAppCache(Activity activity)
	{
		final AppContext ac = (AppContext)activity.getApplication();
		final Handler handler = new Handler(){
			public void handleMessage(Message msg) {
				if(msg.what==1){
					ToastMessage(ac, "缓存清除成功");
				}else{
					ToastMessage(ac, "缓存清除失败");
				}
			}
		};
		new Thread(){
			public void run() {
				Message msg = new Message();
				try {				
					ac.clearAppCache();
					msg.what = 1;
				} catch (Exception e) {
					e.printStackTrace();
	            	msg.what = -1;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}
	
	/**
	 * 发送App异常崩溃报告
	 * @param cont
	 * @param crashReport
	 */
	public static void sendAppCrashReport(final Context cont, final String crashReport)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(R.string.app_error);
		builder.setMessage(R.string.app_error_message);
		builder.setPositiveButton(R.string.submit_report, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//发送异常报告
				Intent i = new Intent(Intent.ACTION_SEND);
				//i.setType("text/plain"); //模拟器
				i.setType("message/rfc822") ; //真机
				i.putExtra(Intent.EXTRA_EMAIL, new String[]{"william@hkgoodvision.com"});
				i.putExtra(Intent.EXTRA_SUBJECT,"开源中国Android客户端 - 错误报告");
				i.putExtra(Intent.EXTRA_TEXT,crashReport);
				cont.startActivity(Intent.createChooser(i, "发送错误报告"));
				//退出
				AppManager.getAppManager().AppExit(cont);
			}
		});
		builder.setNegativeButton(R.string.sure, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//退出
				AppManager.getAppManager().AppExit(cont);
			}
		});
		builder.show();
	}
	
	/**
	 * 退出程序
	 * @param cont
	 */
	public static void Exit(final Context cont)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(cont);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(R.string.app_menu_surelogout);
		builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				//退出
				AppManager.getAppManager().AppExit(cont);
			}
		});
		builder.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}
}
