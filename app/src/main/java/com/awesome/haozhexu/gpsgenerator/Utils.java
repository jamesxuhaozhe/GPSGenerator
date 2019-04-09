package com.awesome.haozhexu.gpsgenerator;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

final class Utils {

    private static final String TAG = Utils.getTag(Utils.class);

    private static final String RECORD_GPS_LOCATION_URL = "https://awesomexu8923.natapp4.cc/recordGPSLocation";

    private static final String LONGITUDE = "longitude";

    private static final String LATITUDE = "latitude";

    private static final String ADCODE = "adcode";

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

    static void testRecordGPSLocation(final Context context) {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        Map<String, Object> map = new HashMap<>();
        map.put(LONGITUDE, 120.06345);
        map.put(LATITUDE, 31.77156);
        map.put(ADCODE, "32032");
        JsonObjectRequest request = new JsonObjectRequest(RECORD_GPS_LOCATION_URL, new JSONObject(map),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error");
                    }
                });
        requestQueue.add(request);
    }
}
