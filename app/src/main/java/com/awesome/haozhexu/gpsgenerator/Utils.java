package com.awesome.haozhexu.gpsgenerator;

import android.util.Log;

import org.apache.commons.io.IOUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

final class Utils {

    private static final String TAG = Utils.getTag(Utils.class);

    private static final String CONNECTION_TEST_URL = "https://awesomexu8923.natapp4.cc/isalive";

    private static final int HTTP_OK = 200;

    static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "Something went wrong when closing resource");
            e.printStackTrace();
        }
    }

    static String getTag(Class<?> cls) {
        return cls.getSimpleName();
    }

    static void testClientServerConnection() {
        try {
            URL url = new URL(CONNECTION_TEST_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            conn.setReadTimeout(5 * 1000);
            conn.setUseCaches(true);
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() == HTTP_OK) {
                String result = streamToString(conn.getInputStream());
                Log.i(TAG, "Get result: " + result);
            } else {
                Log.e(TAG, "Get request failure");
            }
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String streamToString(InputStream inputStream) {
        String result = "";

        try {
            result = IOUtils.toString(inputStream, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
