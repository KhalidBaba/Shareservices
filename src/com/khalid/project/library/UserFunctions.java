package com.khalid.project.library;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class UserFunctions {
	
	private JSONParser jsonParser;
	
	private static String loginURL = "http://10.0.0.3/android_login_api/";
	private static String registerURL = "http://10.0.0.3/android_login_api/";
	
	private static String login_tag = "login";
	private static String register_tag = "register";
	private static String register_courrier_tag = "NewCourrier";
	private static String send_courrier = "send";
	private static String send_courrier_interne = "send_i";
	
	// constructor
	public UserFunctions(){
		Log.i("Avant getJSON","Vrai");
		jsonParser = new JSONParser();
	}
	
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * */
	public JSONObject loginUser(String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		Log.i("Avant getJSON","Vrai");
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		Log.i("aprés getJSON","Vrai");
		// return json
		Log.e("JSON", json.toString());
		return json;
	}
	
	/**
	 * function make Login Request
	 * @param name
	 * @param email
	 * @param password
	 * */
	public JSONObject registerUser(String name, String email, String password,int id_service){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("id_service",""+id_service));
		
		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		// return json
		return json;
	}
	/**
	 * Function maque Courrier request
	 * 
	 *  @param CservE
	 *  @param CnomExp
	 *  @param CObjet
	 *  @param CNature
	 *  @param CservD
	 *  @param CnomDest
	 * @param index 
	 * 
	 */
	public JSONObject registerCourrier(String CservE,String CnomExp, String CObjet,String CNature,String CservD ,String CnomDest, int index)
	{
		Log.i("CservE  >", CservE);
		Log.i("CnomExp", CnomExp);
		Log.i("CObjet", CObjet);
		Log.i("CNature",CNature);
		Log.i("CservD",CservD);
		Log.i("CnomDest", CnomDest);
		index = index +1;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag",register_courrier_tag));
		params.add(new BasicNameValuePair("CservE",CservE));
		params.add(new BasicNameValuePair("CnomExp",CnomExp));
		params.add(new BasicNameValuePair("CObjet",CObjet));
		params.add(new BasicNameValuePair("CNature",CNature));
		params.add(new BasicNameValuePair("CservD",CservD));
		params.add(new BasicNameValuePair("CnomDest",CnomDest));
		
		params.add(new BasicNameValuePair("id_user",""+index));
		
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		return json;
	}
	
	/**
	 * Function Send Courrier request
	 * 
	 *  @param serv_d
	 *  @param nom_d
	 *  @param objet
	 *  @param nature
	 *  @param adresse
	 *  @param id_user
	 * 
	 * 
	 */
	public JSONObject SendCourrier(String serv_d,String nom_d, String objet,String nature,String adresse ,String id_user)
	{
		Log.i("serv_d  >", serv_d);
		Log.i("nom_d", nom_d);
		Log.i("objet", objet);
		Log.i("nature",nature);
		Log.i("adresse",adresse);
		Log.i("id_user", id_user);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag",send_courrier));
		params.add(new BasicNameValuePair("serv_d_send",serv_d));
		params.add(new BasicNameValuePair("nom_d_send",nom_d));
		params.add(new BasicNameValuePair("objet_send",objet));
		params.add(new BasicNameValuePair("nature_send",nature));
		params.add(new BasicNameValuePair("adresse_send",adresse));
		params.add(new BasicNameValuePair("id_user_send",id_user));
		
		
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		return json;
	}
	
	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if(count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public boolean logoutUser(Context context){
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}

	public JSONObject SendCourrierIntern(String id_send, String id_recep,
			String objet, String nature, String message) {
		Log.i("id_send  >", id_send);
		Log.i("id_recep", id_recep);
		Log.i("objet", objet);
		Log.i("nature",nature);
		Log.i("adresse",message);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag",send_courrier_interne));
		params.add(new BasicNameValuePair("id_send",id_send));
		params.add(new BasicNameValuePair("id_recep",id_recep));
		params.add(new BasicNameValuePair("objet",objet));
		params.add(new BasicNameValuePair("nature",nature));
		params.add(new BasicNameValuePair("message",message));
		
		
		
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		return json;
	}
	
}
