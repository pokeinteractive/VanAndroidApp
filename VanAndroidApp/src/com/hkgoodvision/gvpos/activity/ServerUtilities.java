package com.hkgoodvision.gvpos.activity;

import static com.hkgoodvision.gvpos.activity.CommonUtilities.SERVER_URL;
import static com.hkgoodvision.gvpos.activity.CommonUtilities.TAG;
import static com.hkgoodvision.gvpos.activity.CommonUtilities.displayMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import android.content.Context;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;
import com.hkgoodvision.gvpos.activity.R;
import com.vanapp.constant.URLConstant;
import com.vanapp.db.KeyPairDB;


public final class ServerUtilities {
	private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();

    private static final String HTTP_REQUEST_FAILED = null;
    
    /**
     * Register this account/device pair within the server.
     *
     */
    public static void register(final Context context, String name, String phoneUUID, final String regId) {
        Log.i(TAG, "registering device (regId = " + regId + ")");
        String serverUrl = SERVER_URL;
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        params.put("name", name);
        params.put("phoneUUID", phoneUUID);
        
        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        // Once GCM returns a registration id, we need to register on our server
        // As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(TAG, "Attempt #" + i + " to register");
            try {
                displayMessage(context, context.getString(
                        R.string.server_registering, i, MAX_ATTEMPTS));
                post(serverUrl, params);
                GCMRegistrar.setRegisteredOnServer(context, true);
                String message = context.getString(R.string.server_registered);
                CommonUtilities.displayMessage(context, message);

                return;
            } catch (IOException e) {
                // Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).
                Log.e(TAG, "Failed to register on attempt " + i + ":" + e);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                    Log.d(TAG, "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    // Activity finished before we complete - exit.
                    Log.d(TAG, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return;
                }
                // increase backoff exponentially
                backoff *= 2;
            }
        }
        String message = context.getString(R.string.server_register_error,
                MAX_ATTEMPTS);
        CommonUtilities.displayMessage(context, message);
    }

    /**
     * TODO: Unregister this account/device pair within the server.
     */
    public static void unregister(final Context context, final String regId) {
        Log.i(TAG, "unregistering device (regId = " + regId + ")");
        String serverUrl = SERVER_URL + "/unregister";
        Map<String, String> params = new HashMap<String, String>();
        params.put("regId", regId);
        try {
            //post(serverUrl, params);
            GCMRegistrar.setRegisteredOnServer(context, false);
            String message = context.getString(R.string.server_unregistered);
            CommonUtilities.displayMessage(context, message);
        } catch (Exception e) {
            // At this point the device is unregistered from GCM, but still
            // registered in the server.
            // We could try to unregister again, but it is not necessary:
            // if the server tries to send a message to the device, it will get
            // a "NotRegistered" error message and should unregister the device.
            String message = context.getString(R.string.server_unregister_error,
                    e.getMessage());
            CommonUtilities.displayMessage(context, message);
        }
    }

    /**
     * Issue a POST request to the server.
     *
     * @param endpoint POST address.
     * @param params request parameters.
     *
     * @throws IOException propagated from POST.
     */
    private static void post(String endpoint, Map<String, String> params)
            throws IOException {   	
        
        URL url;
        try {
            url = new URL(endpoint);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + endpoint);
        }
        StringBuilder bodyBuilder = new StringBuilder();
        Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
        // constructs the POST body using the parameters
        while (iterator.hasNext()) {
            Entry<String, String> param = iterator.next();
            bodyBuilder.append(param.getKey()).append('=')
                    .append(param.getValue());
            if (iterator.hasNext()) {
                bodyBuilder.append('&');
            }
        }
        String body = bodyBuilder.toString();
        Log.v(TAG, "Posting '" + body + "' to " + url);
        byte[] bytes = body.getBytes();
        HttpURLConnection conn = null;
        try {
        	Log.e("URL", "> " + url);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setFixedLengthStreamingMode(bytes.length);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            // post the request
            OutputStream out = conn.getOutputStream();
            out.write(bytes);
            out.close();
            // handle the response
            int status = conn.getResponseCode();
            if (status != 200) {
              throw new IOException("Post failed with error code " + status);
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
      }
    
//    public static void usePhoneLogin(final String phone, final Context ctx) {
//    	Thread loginThread = new Thread(){
//			private Handler handler = new Handler();
//			@Override
//			public void run() {
//				String driver_id = sendHttpRequest(URLConstant.URL_PHONE_CHECK + "&phone=" + phone, "");
//				
//				// put the result to system db
//				KeyPairDB.setDriverId(driver_id, ctx);
//				
//			}
//		};
//		loginThread.start();
//    }
    
    
    public static String sendHttpRequest(String urlStr, String params)
	{		
		URL url;
		String result = new String();
		try 
		{
			Log.d("send to HTTP", params);
			
			url = new URL(urlStr);
			HttpURLConnection connection;
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			
			PrintWriter out = new PrintWriter(connection.getOutputStream());
			
			out.println(params);
			out.close();

			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							connection.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				result = result.concat(inputLine);				
			}
			Log.d("send to HTTP result", result);
			in.close();			
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}			
		
		if (result.length() == 0) {
			result = HTTP_REQUEST_FAILED;
		}
		
		return result;
		
	
	}
}
