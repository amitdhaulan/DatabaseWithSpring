//package user.function;
//
//import java.util.ArrayList;
//
//import json.parser.JSONParser;
//
//import org.apache.http.NameValuePair;
//
//import database.helper.DatabaseHandler;
//
//public class UserFunction {
//	JSONParser jsonParser;
//
//	DatabaseHandler dh;
//
//	public UserFunction() {
//		jsonParser = new JSONParser();
//
//	}
//
//	/*public static String baseUrl = "http://192.168.168.98:9393/requestcentral/services";*/
//	public static String baseUrl = "http://mobile.ecifm.net/requestcentral/services";
//
//	public String login(ArrayList<NameValuePair> params) {
//		String response = jsonParser.getJSONFromUrl(baseUrl + "/login", 2,
//				params);
//		return response;
//	}
//
//	public String logout(ArrayList<NameValuePair> params) {
//		String response = jsonParser.getJSONFromUrl(baseUrl + "/logout", 2,
//				params);
//		return response;
//	}
//
//
//	public String getLocationRecord(ArrayList<NameValuePair> params) {
//		String response = jsonParser.getJSONFromUrl(baseUrl
//				+ "/download/location", 2, params);
//		return response;
//	}
//
//	public String getRequestList(ArrayList<NameValuePair> params) {
//		String response = jsonParser.getJSONFromUrl(baseUrl
//				+ "/download/requestlist", 2, params);
//		return response;
//	}
//
//	public String downloadWorkRequest(ArrayList<NameValuePair> params) {
//		String response = jsonParser.getJSONFromUrl(baseUrl
//				+ "/download/request", 2, params);
//		return response;
//	}
//
//	public String getFacilityData(ArrayList<NameValuePair> params) {
//		String response = jsonParser.getJSONFromUrl(baseUrl
//				+ "/download/facility", 2, params);
//		return response;
//	}
//
//	// **********************************************************************
//
//	public String uploadUpdatedLocationOnserver(ArrayList<NameValuePair> params) {
//		String response = jsonParser.getJSONFromUrl(baseUrl
//				+ "/download/space.do", 2, params);
//		return response;
//	}
//
//
//	public String uploadRequestData(ArrayList<NameValuePair> params) {
//		String response = jsonParser.getJSONFromUrl(baseUrl
//				+ "/create/request", 2, params);
//		return response;
//	}
//	public String getOrganization(ArrayList<NameValuePair> params) {
//		String response = jsonParser.getJSONFromUrl(baseUrl
//				+ "/download/org", 2, params);
//		return response;
//	}
//
//	public String getFloorsData(ArrayList<NameValuePair> params) {
//		String response = jsonParser.getJSONFromUrl(baseUrl + "/download/floor", 2,
//				params);
//		return response;
//	}
//
//	public String getSpaceData(ArrayList<NameValuePair> params) {
//		String response = jsonParser.getJSONFromUrl(baseUrl + "/download/space", 2,
//				params);
//		return response;
//	}
//}
