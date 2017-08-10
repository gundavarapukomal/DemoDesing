package user.beacon.ggk.com.demodesing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

/**
 * Created by komal.gundavarapu on 8/10/2017.
 */

public class OfferActivity2 extends Activity {
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.offer_screen2);
        WebView wv = (WebView) findViewById(R.id.web_id);
        wv.loadUrl("file:///android_asset/second_scrnntxt.html");   // now it will not fail here
    }
}
