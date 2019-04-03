package com.awesome.haozhexu.gpsgenerator;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class CoordinateGenerator {

    private static final String TAG = Utils.getTag(CoordinateGenerator.class);

    private static final String COMMA_DELIMITER = ",";

    private static final String FILE_PATH = "data.csv";

    private static final String ZERO = "0";

    private static List<Coordinate> coordinates = new ArrayList<>();

    public static synchronized void initialize(Context context) {
        readDataFromFile(context);
    }

    private static void readDataFromFile(Context context) {
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader br = null;

        try {
            inputStream = context.getAssets().open(FILE_PATH);
            inputStreamReader = new InputStreamReader(inputStream);
            br = new BufferedReader(inputStreamReader);

            String line = "";
            //Let's skip the header
            br.readLine();

            while ((line = br.readLine()) != null) {
                if (line.length() > 0) {
                    String[] coordinatesDetail = line.split(COMMA_DELIMITER);
                    if (isValidCoordinatesDetail(coordinatesDetail)) {
                        fillCoordinatesListIfNeeded(coordinates, coordinatesDetail);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Something went wrong when reading csv file");
        } finally {
            Utils.closeQuietly(inputStream);
            Utils.closeQuietly(inputStreamReader);
            Utils.closeQuietly(br);
        }
    }

    private static boolean isValidCoordinatesDetail(final String[] coordinatesDetail) {
        if (coordinatesDetail == null) {
            return false;
        }

        // detail array length has to be 2, the first entry is long, and the latter is latitude
        return coordinatesDetail.length == 2;
    }

    private static void fillCoordinatesListIfNeeded(List<Coordinate> coordinates, String[] coordinatesDetail) {
        String longStr = coordinatesDetail[0];
        String latStr = coordinatesDetail[1];

        if (isValidLongitudeOrLatitudeString(longStr) && isValidLongitudeOrLatitudeString(latStr)) {
            Coordinate coordinate = new Coordinate(Double.parseDouble(longStr), Double.parseDouble(latStr));
            Log.i(TAG, coordinate.toString());
            coordinates.add(coordinate);
        }
    }

    private static boolean isValidLongitudeOrLatitudeString(String longLatStr) {
        if (longLatStr == null) {
            return false;
        }
        return !longLatStr.equals(ZERO);
    }

    public static Coordinate getNextCoordinate() {
        Random random = new Random();
        return coordinates.get(random.nextInt(coordinates.size() - 1));
    }
}

