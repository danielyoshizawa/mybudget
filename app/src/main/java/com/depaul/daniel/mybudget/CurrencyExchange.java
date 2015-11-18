package com.depaul.daniel.mybudget;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by JordanMiguel on 18/11/15.
 */
public class CurrencyExchange {

    private static String api = "http://www.apilayer.net/api/live?access_key=76be9aca21ed2cfa4779bb7cf9eecd89";
    private static int timeout = 1000;
    private static JSONObject jObj = null;
    private static String json = "";

    public CurrencyExchange() {

    }

    public String getCurrencyValue(String currencyWanted) throws JSONException {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(api);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.connect();

            int status = conn.getResponseCode();

            if(status == 200 || status == 201) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;

                while((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();
                json = sb.toString();
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        JSONObject currency = jObj.getJSONObject("quotes");

        return currency.getString("USD"+currencyWanted);
    }
}
