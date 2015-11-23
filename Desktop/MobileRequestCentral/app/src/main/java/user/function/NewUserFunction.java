package user.function;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

import database.helper.DatabaseHandler;
import json.parser.NewJsonParser;

public class NewUserFunction {
    /*public static String baseUrl = "http://192.168.168.98:9393/requestcentral/services";*/
    public static String baseUrl = "http://mobile.ecifm.net/requestcentral/services";
    /*public static String baseUrl = "http://fidelity.ecifm.net/requestcentral/services";*/
    private static String facility = "/facility/";
    private static String reservation = "/reservation/";
    NewJsonParser jsonParser;
    DatabaseHandler dh;
    URL url;


    public NewUserFunction() {
        jsonParser = new NewJsonParser();

    }

    public String login(HashMap<String, String> postDataParams) throws MalformedURLException {
        String response = "";
        url = new URL(baseUrl + "/login");
        response = openConnection(postDataParams, url);
        System.out.println("\n=====>login response<=====" + response);
        return response;
    }


    public String logout(HashMap<String, String> postDataParams) throws MalformedURLException {

        String response = "";
        url = new URL(baseUrl + "/logout");
        response = openConnection(postDataParams, url);
        System.out.println("\n=====>logout response<=====" + response);
        return response;

    }


    public String getLocationRecord(HashMap<String, String> postDataParams) throws MalformedURLException {
        String response = "";
        /*url = new URL(baseUrl + "/download/location);*/
        url = new URL(baseUrl + facility + "download/location");
        response = openConnection(postDataParams, url);
        System.out.println("\n=====>location response<=====" + response);
        return response;

    }

    public String getRequestList(HashMap<String, String> postDataParams) throws MalformedURLException {
        String response = "";
        /*url = new URL(baseUrl + "/download/requestlist");*/
        url = new URL(baseUrl + facility + "download/requestlist");
        response = openConnection(postDataParams, url);
        System.out.println("\n=====>Request list response<=====" + response);
        return response;
    }

    public String downloadWorkRequest(HashMap<String, String> postDataParams) throws MalformedURLException {

        String response = "";
        /*url = new URL(baseUrl + "/download/request");*/
        url = new URL(baseUrl + facility + "download/request");
        response = openConnection(postDataParams, url);
        System.out.println("\n=====>Download Work Request response<=====" + response);
        return response;
    }

    public String getFacilityData(HashMap<String, String> postDataParams) throws MalformedURLException {

        String response = "";
        /*url = new URL(baseUrl + "/download/facility");*/
        url = new URL(baseUrl + facility + "download/facility");
        response = openConnection(postDataParams, url);
        System.out.println("\n=====>facility response<=====" + response);
        return response;
    }

    // **********************************************************************

    /*public String uploadUpdatedLocationOnserver(HashMap<String, String> postDataParams) throws MalformedURLException {

        String response = "";
        *//*url = new URL(baseUrl + "/download/space.do");*//*
        url = new URL(baseUrl + facility +"download/space.do");
        response = openConnection(postDataParams, url);
        System.out.println("\n=====>Upload location on server response<=====" + response);
        return response;
    }
*/

    public String uploadRequestData(HashMap<String, String> postDataParams) throws MalformedURLException {
        String response = "";
        /*url = new URL(baseUrl + "/create/request");*/
        url = new URL(baseUrl + facility + "create/request");
        response = openConnection(postDataParams, url);
        System.out.println("\n=====>upload Request data response<=====" + response);
        return response;
    }

    public String getOrganization(HashMap<String, String> postDataParams) throws MalformedURLException {
        String response = "";
        /*url = new URL(baseUrl + "/download/org");*/
        url = new URL(baseUrl + facility + "download/org");
        response = openConnection(postDataParams, url);
        System.out.println("\n=====>Organization response<=====" + response);
        return response;
    }

    public String getFloorsData(HashMap<String, String> postDataParams) throws MalformedURLException {
        String response = "";
        /*url = new URL(baseUrl + "/download/floor");*/
        url = new URL(baseUrl + facility + "download/floor");
        response = openConnection(postDataParams, url);
        System.out.println("\n=====>floors response<=====" + response);
        return response;
    }

    public String getSpaceData(HashMap<String, String> postDataParams) throws MalformedURLException {
        String response = "";
        /*url = new URL(baseUrl + "/download/space");*/
        url = new URL(baseUrl + facility + "download/space");
        response = openConnection(postDataParams, url);
        System.out.println("\n=====>space response<=====" + response);
        return response;
    }

    public String makeReservation(HashMap<String, String> postDataParams) throws MalformedURLException {
        String response = "";
        /*url = new URL(baseUrl + "/create/reservation");*/
        url = new URL(baseUrl + reservation + "create/building");
        response = openConnection(postDataParams, url);
        System.out.println("\n=====>create reservation response<=====" + response);
        return response;
    }

    public String getBuildingForReservation(HashMap<String, String> postDataParams) throws MalformedURLException {
        String response = "";
        /*url = new URL(baseUrl + "/download/reservation/buildings");*/
        url = new URL(baseUrl + reservation + "download/buildings");
        response = openConnection(postDataParams, url);
        System.out.println("\n=====>download reservation buildings <=====" + response);
        return response;
    }

    public String getSpaceForReservation(HashMap<String, String> postDataParams) throws MalformedURLException {
        String response = "";
        /*url = new URL(baseUrl + "/download/reservation/spaces");*/
        url = new URL(baseUrl + reservation + "download/spaces");
        response = openConnection(postDataParams, url);
        System.out.println("\n=====>download reservation spaces <=====" + response);
        return response;
    }

    public String getEquipmentData(HashMap<String, String> postDataParams) throws MalformedURLException {
        String response = "";
        /*url = new URL(baseUrl + "/download/reservation/spaces");*/
        url = new URL(baseUrl + reservation + "download/equipment");
        response = openConnection(postDataParams, url);
        System.out.println("\n=====>download reservation equipment <=====" + response);
        return response;
    }

    public String getReservations(HashMap<String, String> postDataParams) throws MalformedURLException {
        String response = "";
        /*url = new URL(baseUrl + "/download/reservation/spaces");*/
        url = new URL(baseUrl + reservation + "download/");
        response = openConnection(postDataParams, url);
        System.out.println("\n=====>download reservation spaces <=====" + response);
        return response;
    }


    public String openConnection(HashMap<String, String> postDataParams, URL url) {
        System.out.println("\n=====>URL<=====" + url);
        System.out.println("\n=====>params<=====" + postDataParams);
        String response = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(1500000);
            conn.setConnectTimeout(1500000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(NewJsonParser.getNewJSONFromParams(postDataParams));

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            System.out.println();
            System.out.println("responseCode ========>" + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
