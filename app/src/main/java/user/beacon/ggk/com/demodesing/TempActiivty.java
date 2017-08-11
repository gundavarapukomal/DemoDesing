package user.beacon.ggk.com.demodesing;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by komal.gundavarapu on 8/10/2017.
 */

public class TempActiivty extends Activity {

    private static final String TAG = "TempActivity";
    private String mImages, mAlias, number, mgrantedCredits;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.temo);
        String jsonFromAsset = loadJSONFromAsset("blue.json");
        parseBlueHearder(jsonFromAsset);

        // json parsing done


    }

    private void parseBlueHearder (String jsonFromAsset) {
        try {
            JSONObject jsonObj = new JSONObject(jsonFromAsset);
            JSONObject jsonObject = jsonObj.getJSONObject("data");
            number = jsonObject.getString("number");
            mAlias = jsonObject.getString("alias");

            JSONArray grantedCredits = jsonObject.getJSONArray("grantedCredits");
            JSONObject jsonObjgrnt = grantedCredits.getJSONObject(0);
            mgrantedCredits = jsonObjgrnt.getString("amount");


            JSONArray JarryImages = jsonObject.getJSONArray("images");
            JSONObject JarryImagesJSONObject = JarryImages.getJSONObject(0);
            mImages = JarryImagesJSONObject.getString("url");

            Log.e(TAG, number);
            Log.e(TAG, mAlias);
            Log.e(TAG, mgrantedCredits);
            Log.e(TAG, mImages);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset (String strJson) {
        String json = null;
        try {
            InputStream is = getAssets().open(strJson);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
