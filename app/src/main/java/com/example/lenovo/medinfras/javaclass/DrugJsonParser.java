package com.example.lenovo.medinfras.javaclass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.HttpUrl;

/**
 * Created by Lenovo on 5/7/2018.
 */

public class DrugJsonParser {

    public DrugJsonParser() {

    }

    public String makeServiceCall(String serviceUrl) {
        Boolean registered = false;
        StringBuffer response = new StringBuffer();
        URL url = null;

        HttpURLConnection conn = null;
        try {
            url = new URL(serviceUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(30000);
            conn.setDoOutput(false);
            conn.setUseCaches(false);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded;charset=UTF-8");
            int status = conn.getResponseCode();
            if (status != 200) {
                throw new IOException("Post failed with error code : " + status);
            }
            registered = true;

            InputStream is = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response.toString();
    }
}
