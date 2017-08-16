package user.beacon.ggk.com.demodesing;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by komal.gundavarapu on 8/10/2017.
 */

public class TempActiivty extends Activity {

    private static final String TAG = "TempActivity";
    private String mImagesUrl, mAlias, number, mgrantedCredits;
    private ImageView mImageView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE|WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_offer);
//        mImageView = (ImageView) findViewById(R.id.img_id);
//        String jsonFromAsset = loadJSONFromAsset("blue.json");
//        parseBlueHearder(jsonFromAsset);
//
//        // upto here we are getting all the four files , bt from the imageurl need to get bit map to set imageView for that creating asyncTask
//        ImageLoadTask loadTask = new ImageLoadTask(mImagesUrl, mImageView);
//        loadTask.execute();
        WebView wv = (WebView) findViewById(R.id.WebViewTNC);



        wv.loadUrl("file:///android_asset/terms.html");
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
            mImagesUrl = JarryImagesJSONObject.getString("url");

            Log.e(TAG, number);
            Log.e(TAG, mAlias);
            Log.e(TAG, mgrantedCredits);
            Log.e(TAG, mImagesUrl);

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


    /**
     * This class is for loading imageview by passing imageurl and imageview as arguments to constrctor
     */
    private class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask (String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground (Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute (Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }

    }
}
