package user.beacon.ggk.com.demodesing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class offerActivity1 extends Activity implements View.OnTouchListener, Handler.Callback {

    private static final String TAG = "OFFERS";
    private List<DetailsBean> detailsList = new ArrayList<DetailsBean>();
    private WebViewClient client;
    private static final int CLICK_ON_WEBVIEW = 1;
    private static final int CLICK_ON_URL = 2;
    private final Handler handler = new Handler(this);
    private EditText mEditText;
    private String mCat_id, mCat_name;
    private boolean EditTExt_focuse = true;
    private String mImages, mAlias, number, mgrantedCredits;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.offer_screen1);
        mEditText = (EditText) findViewById(R.id.txt_footer1);

        if (EditTExt_focuse) {
            mEditText.setText("$50,000");
            mEditText.setCursorVisible(true);
            mEditText.setLongClickable(true);
            mEditText.setClickable(true);
            mEditText.setFocusable(true);
            mEditText.setSelected(true);
            mEditText.setBackgroundResource(R.color.grycolor);
        } else {
            mEditText.setText("$50,000");
            mEditText.setCursorVisible(false);
            mEditText.setLongClickable(false);
            mEditText.setClickable(false);
            mEditText.setFocusable(false);
            mEditText.setSelected(false);
            mEditText.setKeyListener(null);
            mEditText.setBackgroundResource(android.R.color.transparent);
            mEditText.clearFocus();
        }
        String fromAsset1 = loadJSONFromAsset("formate1.json");
        parseJsonF1(fromAsset1);

        String fromAsset2 = loadJSONFromAsset("formate2.json");
        parseJsonF2(fromAsset2);

        String fromAsset3 = loadJSONFromAsset("blue.json");
        parseBlueHearder(fromAsset3);

        WebView wv = (WebView) findViewById(R.id.web_indx);
        client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading (WebView view, String url) {
                handler.sendEmptyMessage(CLICK_ON_URL);
                return false;
            }
        };

        wv.setOnTouchListener(this);
        wv.setWebViewClient(client);
        wv.loadUrl("file:///android_asset/terms.html");
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        return super.onTouchEvent(event);
    }


    /**
     * parsing json
     *
     * @param fromAsset
     */
    private void parseJsonF2 (String fromAsset) {
        try {
            JSONObject jsonObj = new JSONObject(fromAsset);
            JSONArray ratesArry = jsonObj.getJSONArray("rates");
            JSONObject jsonrate = ratesArry.getJSONObject(0);
            JSONObject rateType = jsonrate.getJSONObject("rateType");
            mCat_id = rateType.getString("id");
            mCat_name = rateType.getString("name");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //    detailsList consist of total 6 objects
    //    Details detailsBean = detailsList.get(0);
    //    int amout = detailsBean.getAmout();
    //    String currency1 = detailsBean.getCurrency();
       /*
       minimumAmount    detailsList.get(0),detailsList.get(1)
       minimumAmount    detailsList.get(2),detailsList.get(3)
       suggestedAmount  detailsList.get(4),detailsList.get(5)
       */
    private void parseJsonF1 (String fromAsset) {
        try {
            JSONObject jsonObj = new JSONObject(fromAsset);
            JSONObject jsonObject = jsonObj.getJSONObject("data");
            JSONObject details = jsonObject.getJSONObject("details");
            Iterator<String> itr = details.keys();
            while (itr.hasNext()) {
                String key = itr.next();
                if (key.equals("minimumAmount") || key.equals("minimumAmount") || key.equals("suggestedAmount")) {
                    JSONArray jsonArray = details.getJSONArray(key);
                    for (int i = 0; i < jsonArray.length(); i++) {  // **line 2**
                        JSONObject childJSONObject = jsonArray.getJSONObject(i);
                        int amount = childJSONObject.getInt("amount");
                        String currency = childJSONObject.getString("currency");
                        DetailsBean details1 = new DetailsBean();
                        details1.setAmout(amount);
                        details1.setCurrency(currency);
                        detailsList.add(details1);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * getting json from asserts
     *
     * @param strJson
     * @return
     */
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


    @Override
    public boolean handleMessage (Message msg) {
        if (msg.what == CLICK_ON_URL) {
            handler.removeMessages(CLICK_ON_WEBVIEW);
            return true;
        }
        if (msg.what == CLICK_ON_WEBVIEW) {
            Intent intent = new Intent(getApplicationContext(), OfferActivity2.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouch (View v, MotionEvent event) {
        if (v.getId() == R.id.web_indx && event.getAction() == MotionEvent.ACTION_DOWN) {
            handler.sendEmptyMessageDelayed(CLICK_ON_WEBVIEW, 500);
        }
        return false;
    }

}
