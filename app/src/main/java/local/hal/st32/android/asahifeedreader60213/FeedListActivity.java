package local.hal.st32.android.asahifeedreader60213;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class FeedListActivity extends AppCompatActivity {

    private Handler handler;
    private ListView rssList;
    private final String RSS_URL = "http://rss.asahi.com/rss/asahi/newsheadlines.rdf";
    private String RssText;
    List<Item> itemList = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_list);

        handler = new Handler();

        rssList = findViewById(R.id.rssList);

        RowItemAdapter rowAdapter = new RowItemAdapter(this, 0, itemList);

        rssList.setAdapter(rowAdapter);

        // リストをクリック
        rssList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // リストビューの項目を取得
                ListView listview = (ListView) parent;
                Item item = (Item) listview.getItemAtPosition(position);

                // インテントのインスタンス生成
                Intent intent = new Intent(FeedListActivity.this, FeedDetailActivity.class);

                // 値をセットする
                intent.putExtra("title", item.getTitle());
                intent.putExtra("link", item.getLink());

                // 次画面のアクティビティ起動
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // メニューボタンを追加
        MenuItem item1 = menu.add("UPDATE");
        item1.setIcon(android.R.drawable.ic_menu_upload);

        item1.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Thread thread1 = new Thread() {
                    @Override
                    public void run() {
                        try {
                            // URLよりRSSを取得
                            RssText = RssParse.getRss(FeedListActivity.this, RSS_URL);
                            itemList = RssParse.parse(RssText);

                            // handler
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // 読み込み終了
                                    RowItemAdapter rowAdapater = new RowItemAdapter(FeedListActivity.this, 0, itemList);
                                    rssList.setAdapter(rowAdapater);
                                    Toast.makeText(FeedListActivity.this, "読み込み終了", Toast.LENGTH_SHORT).show();
                                }
                            });

                        } catch (Exception e) {
                            Log.e("Exception", e.getMessage());
                        }
                    }
                };
                thread1.start();

                return false;
            }
        });

        // 画面にメニューを表示
        item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }
}
