package com.raycloud.overseas.erp.data.services.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class Sdk {
    public static String _domain="http://superseller.kf5.com";
    public static String _secretKey="edf34327dcc294d053dc514cb66b10";

    public static String ADMIN_NAME = "yangnan@raycloud.com";
    public static String HOST = "superseller.kf5.com";


    public static String getContent(TreeMap<String, String> apiparamsMap) {


        StringBuilder param = new StringBuilder();

        for (Iterator<Map.Entry<String, String>> it = apiparamsMap.entrySet()

                .iterator(); it.hasNext(); ) {

            Map.Entry<String, String> e = it.next();

            param.append("&").append(e.getKey()).append("=").append(e.getValue());

        }

        return param.toString().substring(1);

    }


    public static String md5Signature(TreeMap<String, String> params ) {

        String result = null;

        StringBuffer orgin = new StringBuffer().append(params.get("username")).append(params.get("time"));

        if (orgin == null)

            return result;

        orgin.append(_secretKey);

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");

            result = byte2hex(md.digest(orgin.toString().getBytes("utf-8")));

        } catch (Exception e) {

            throw new RuntimeException("sign error !");

        }

        return result;

    }



    public static String byte2hex(byte[] b) {

        StringBuffer hs = new StringBuffer();

        String stmp = "";

        for (int n = 0; n < b.length; n++) {

            stmp = (Integer.toHexString(b[n] & 0XFF));

            if (stmp.length() == 1)

                hs.append("0").append(stmp);

            else

                hs.append(stmp);

        }

        return hs.toString().toLowerCase();

    }



    private static StringBuffer getBeforeSign(TreeMap<String, String> params) {
        StringBuffer sb=new StringBuffer();

        if (params == null)

            return null;

        Map<String, String> treeMap = new TreeMap<String, String>();

        treeMap.putAll(params);

        Iterator<String> iter = treeMap.keySet().iterator();

        while (iter.hasNext()) {

            String name = (String) iter.next();

            sb.append(name).append("=").append(params.get(name)).append("&");

        }

        return sb;

    }


    public static String getResult(String method, String content) {

        URL url = null;
        String urlStr=_domain+"/api/v1/"+method;

        HttpURLConnection connection = null;



        try {

            url = new URL(urlStr);

            connection = (HttpURLConnection) url.openConnection();

            connection.setDoOutput(true);

            connection.setDoInput(true);

            connection.setRequestMethod("POST");

            connection.setUseCaches(false);

            connection.connect();



            DataOutputStream out = new DataOutputStream(connection.getOutputStream());

            out.write(content.getBytes("utf-8"));

            out.flush();

            out.close();



            BufferedReader reader =

                    new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

            StringBuffer buffer = new StringBuffer();

            String line = "";

            while ((line = reader.readLine()) != null) {

                buffer.append(line);

            }

            reader.close();

            return buffer.toString();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (connection != null) {

                connection.disconnect();

            }

        }

        return null;

    }

}
