package local.hal.st32.android.asahifeedreader60213;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class FeedDetailActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);

        // webview
        webView = (WebView)findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());

        // インテントを取得
        Intent intent = getIntent();
        // インテントに保存されたデータを取得
        String data = intent.getStringExtra("title");
        String link = intent.getStringExtra("link");
        setTitle(data);

        // URLを読み込み
        webView.loadUrl(link);

        // data
        Toast.makeText(FeedDetailActivity.this,data, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }
}
