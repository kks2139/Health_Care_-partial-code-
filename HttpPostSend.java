package com.example.app_dev.healthcare.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;

public class HttpPostSend {
    public static final String API_SERVER_URL = "http://1.233.82.153:40404";    // test server url

	/*private static String executeClient(String url, ArrayList<NameValuePair> post) {

		HttpClient client = new DefaultHttpClient();

		HttpParams params = client.getParams();
		HttpConnectionParams.setConnectionTimeout(params, 5000);
		HttpConnectionParams.setSoTimeout(params, 5000);

		HttpPost httpPost = new HttpPost(url);
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, "UTF-8");
			httpPost.setEntity(entity);
			HttpResponse response = client.execute(httpPost);
			String responseString = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
			Log.e("executeClient", "response="+responseString);
			return responseString;
		} catch(ClientProtocolException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}

		return null;
	}*/

	private static String executeClient(String urlString, String postParams) {
		try {
			/*String query = "";
			try {
				query = URLEncoder.encode(postParams, "utf-8");
			} catch(Exception e) {
				e.printStackTrace();
			}*/
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
			dStream.write(postParams.getBytes("utf-8"));
			//dStream.writeBytes(postParams);
			dStream.flush();
			dStream.close();
			int responseCode = connection.getResponseCode();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = "";
			StringBuilder responseOutput = new StringBuilder();
			while ((line = br.readLine()) != null) {
				responseOutput.append(line);
			}
			br.close();
			return responseOutput.toString();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String executeRegisterUser(String name, String email, String password, String gender,
			String age, String weight) {
		return executeClient(API_SERVER_URL+"/app/joinMember.json", "NICKNAME="+name+"&LOGIN_ID="+email+
				"&PASSWORD="+password+"&SEX="+gender+"&BIRTH_DATE="+age+"&WEIGHT="+weight);
	}
	
	public static String executeLoginIDOverlapCheck(String loginID) {
		return executeClient(API_SERVER_URL+"/app/loginIdCheck.json", "LOGIN_ID="+loginID);
	}
	
	public static String executeGetUserAgencyList() {
		return executeClient(API_SERVER_URL+"/healthup/get_user_agent_list", "");
		//return executeClient(API_SERVER_URL+"/healthup/get_user_agent_list", post);
	}
	
	public static String executeLoginUser(String email, String password) {
		return executeClient(API_SERVER_URL+"/app/login.json", "LOGIN_ID="+email+"&PASSWORD="+password);
	}
	
	public static String executeSendBeaconInfo(String beacon_mac, String group_id, String member_id, String device_uuid, String date, String beacon_distance, String beacon_rssi){
		return executeClient(API_SERVER_URL+"/app/saveBeaconData.json", "BEACON_MAC="+beacon_mac+"&GROUP_ID="+group_id+"&MEMBER_ID="+member_id+
				"&DEVICE_UUID="+ device_uuid +"&RECEIVE_DATE="+date+"&DISTANCE="+ beacon_distance +"&RSSI="+beacon_rssi);
	}
	
	public static String executeSendDeviceInfo(String member_id, String uuid, String d_platform, String d_model, String registration_id){
		return executeClient(API_SERVER_URL+"/app/savePushMemberInfo.json", "MEMBER_ID="+member_id+"&DEVICE_UUID="+uuid+"&DEVICE_PLATFORM="+d_platform+
				"&DEVICE_MODEL="+ d_model +"&REGISTRATION_ID="+registration_id);
	}
	
	public static String executeGetUserInfo(String member_id){
		return executeClient(API_SERVER_URL+"/app/selectMemberProfile.json", "MEMBER_ID="+member_id);
	}
	
	public static String executeGetMainInfo(String member_id){
		return executeClient(API_SERVER_URL+"/app/selectMainInfo.json", "MEMBER_ID="+member_id);
	}
	
	public static String executeGetTargetStep(String member_id){
		return executeClient(API_SERVER_URL+"/app/getGoals.json", "MEMBER_ID="+member_id);
	}
	
	public static String executeSendTargetStep(String member_id, String goal_kind, String goal_value, String goal_period_unit){
		return executeClient(API_SERVER_URL+"/app/setGoal.json", "MEMBER_ID="+member_id +"&GOAL_KIND="+ goal_kind + "&GOAL_VALUE=" + goal_value + "&GOAL_PERIOD_UNIT=" + goal_period_unit);
	}
	
	public static String executeGetRankingList(String member_id, String page_index){
		return executeClient(API_SERVER_URL+"/app/ranking/Floor.json", "MEMBER_ID="+member_id +"&pageIndex="+ page_index);
	}
	
//	public static String executeUpdateUser(String member_id, String sex, String picture_name, String birth_date, String weight, String goal_kind, String goal_value, String goal_period_unit) {
//		return executeClient2(API_SERVER_URL+"/app/updateMemberProfile.json", "MEMBER_ID="+member_id+"&SEX="+sex+
//				"&PICTURE_NAME="+picture_name+"&BIRTH_DATE="+birth_date+"&WEIGHT="+weight+"&GOAL_KIND="+goal_kind+"&GOAL_VALUE="+goal_value+"&GOAL_PERIOD_UNIT="+goal_period_unit);
//	}
	
	public static String executeUpdateUser(String member_id, String sex, String birth_date, String weight, String goal_kind, String goal_value, String goal_period_unit) {
		return executeClient(API_SERVER_URL+"/app/updateMemberProfile.json", "MEMBER_ID="+member_id+"&SEX="+sex+
				"&BIRTH_DATE="+birth_date+"&WEIGHT="+weight+"&GOAL_KIND="+goal_kind+"&GOAL_VALUE="+goal_value+"&GOAL_PERIOD_UNIT="+goal_period_unit);
	}
	
	public static String executeSetPassword(String member_id, String old_password, String new_password){
		return executeClient(API_SERVER_URL+"/app/setPassword.json", "MEMBER_ID="+member_id +"&OLD_PASSWORD="+ old_password +"&NEW_PASSWORD="+ new_password);
	}
	
	public static String executeGetFloorData(String member_id, String begin_date, String search_kind){
		return  executeClient(API_SERVER_URL+"/app/selectFloorData.json", "MEMBER_ID="+member_id +"&BEGIN_DATE="+ begin_date +"&SEARCH_KIND="+ search_kind);
	}
	
	public static String executeGetNoticeList(String category){
		return  executeClient(API_SERVER_URL+"/app/selectBoardList.json", "CATEGORY=" + category);
	}
	
	public static String executeGetBoardDetail(String board_no){
		return  executeClient(API_SERVER_URL+"/app/selectBoardDetail.json", "BOARD_NO=" + board_no);
	}

    // 17.02.13
//    public static String executeTest(String test01, String test02, String test03){
//        return  executeClient(API_SERVER_URL + "/app/testing.json", "test01 =" + test01 + "test02 =" + test02 + "test03 =" + test03);
//    }

	public static String executeTest(String test01){
		return  executeClient(API_SERVER_URL + "/app/wellbing/healing_new_survey_info.json", "MEMBER_ID=" + test01);
	}

	public static String executeSaveTest(String test01){
		return  executeClient(API_SERVER_URL + "/app/wellbing/healing_new_survey_info_save.json", "MEMBER_ID=" + test01);
	}

	// 2016.11.29 image upload
	public static String executeUploadPictureImage(String member_id, String filepath) {
		String url = API_SERVER_URL+"/app/joinMemberPictureUpload.json";
		String responseString = "";
		
		DataOutputStream dos = null;
		InputStream is = null;
		
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		
		try {
			FileInputStream fstrm = new FileInputStream(filepath);
			String filename = new File(filepath).getName();
			URL connectUrl = new URL(url);
			
			HttpURLConnection conn = (HttpURLConnection)connectUrl.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			
			dos = new DataOutputStream(conn.getOutputStream());
			
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition:form-data;name=\"MEMBER_ID\"");
			dos.writeBytes(lineEnd + lineEnd + member_id + lineEnd);
			
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition:form-data;name=\"file\";filename=\""+filename+"\""+lineEnd);
			dos.writeBytes(lineEnd);
			
			int bytesAvailable = fstrm.available();
			int maxBufferSize = 1024;
			int bufferSize = Math.min(bytesAvailable, maxBufferSize);
			byte[] buffer = new byte[bufferSize];
			int bytesRead = fstrm.read(buffer, 0, bufferSize);
			while(bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fstrm.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fstrm.read(buffer, 0, bufferSize);
			}
			fstrm.close();
			
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
			
			dos.flush();
			
			//---------------------------
			
			int ch;
			is = conn.getInputStream();
			StringBuffer b = new StringBuffer();
			while((ch = is.read()) != -1) {
				b.append((char)ch);
			}
			String str = b.toString();
			responseString  = URLDecoder.decode(str, "utf-8");
//			responseString = URLDecoder.decode(str, HTTP.UTF_8);

			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return responseString;
	}
	
	// 2016.12.01 image set from server
	public static Bitmap executeSetPictureImage(String filepath) {
		String url = API_SERVER_URL + filepath;
		URL myFileUrl =null;          
        try {
             myFileUrl= new URL(url);
        } catch (MalformedURLException e) {
            return null;
        }
        try {
             HttpURLConnection conn= (HttpURLConnection)myFileUrl.openConnection();
             conn.setInstanceFollowRedirects(false);
             conn.setDoInput(true);
             conn.connect();                       
             InputStream is = conn.getInputStream();
             Bitmap bitmap = BitmapFactory.decodeStream(is);
//             image.setImageBitmap(bitmap);
             return bitmap;
        } catch (IOException e) {
            System.out.println("error="+e.getMessage());
            e.printStackTrace();        
            return null;
        }
	}}
	
	
	
	
