package com.vanapp.constant;

public class URLConstant {
	
	public final static String VANAPPHOST = "van.poke.com.hk";//  www.oschina.net
	
	public final static String HTTP = "http://";
	public final static String HTTPS = "https://";
	
	private final static String URL_SPLITTER = "/";
	private final static String URL_UNDERLINE = "_";
	
	//private final static String URL_API_HOST = HTTP + HOST + URL_SPLITTER;
	
	private final static String URL_WEDDINGIDO_API_HOST = HTTP + VANAPPHOST + URL_SPLITTER;
	
	public final static String SERVICE_LIST = URL_WEDDINGIDO_API_HOST+"jsonapi/getOrderList";
	public final static String SERVICE_DETAIL = URL_WEDDINGIDO_API_HOST+"jsonapi/getOrder";
	public final static String ORDERHISTRY_LIST = URL_WEDDINGIDO_API_HOST+"jsonapi/getOrderHistory";
	
	
	
	
	static final  String URL_BASE = "http://van.poke.com.hk/messaging/index.php?";
	
	
	public static final  String URL_PHONE_CHECK = URL_BASE+ "action=phonecheck";
	
	public static final  String URL_UPDATE_GPS_LOCATION = URL_BASE+ "action=loc&";
	
	public static final  String URL_GET_DRIVER_ID_BY_PHONE = "http://van.poke.com.hk/jsonapi/getDriverIdByPhone/";
	
	public static final  String URL_GET_DRIVER_ACCOUNT_BALANCE = "http://van.poke.com.hk/jsonapi/getDriverAccountBalance/";
	
	public static final  String URL_MATCH_ORDER = "http://van.poke.com.hk/jsonapi/matchOrder/";
	
	
	// give your server registration url here
    public static final String REGISTER_SERVER_URL = "http://van.poke.com.hk/messaging/gcm/register.php"; 

    // Google project id
    public static final String GCM_SENDER_ID = "569974433968"; 
    
    
    
    

}
