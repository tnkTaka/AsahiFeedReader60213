package local.hal.st32.android.asahifeedreader60213;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.util.Log;
import android.util.Xml;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class RssParse {
    public static String getRss(Context context, String url)
            throws IOException{

        StringBuilder sb = new StringBuilder();

        AndroidHttpClient client = AndroidHttpClient.newInstance("RssFeed");
        HttpGet get = new HttpGet(url);

        try{
            // リクエストを取得
            HttpResponse response = client.execute(get);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = null;
            while((line = br.readLine()) != null){
                sb.append(line);
            }

        }finally {
            // 閉じる
            client.close();
        }

        return sb.toString();

    }

    public static List<Item> parse(String xml){

        List<Item> list = new ArrayList<Item>();

        // XMLパーサを生成
        XmlPullParser xmlPullParser = Xml.newPullParser();

        // 入力データを設定
        try {
            xmlPullParser.setInput(new StringReader(xml));
        } catch (XmlPullParserException e) {
            Log.d("XmlPullParserException", e.getMessage());
        }

        // 解析して記事のタイトルやリンク、日時を取得
        try {
            int eventType;
            String data = null;
            int itemFlg = -1;
            String fieldName = null;
            Item item = new Item();

            eventType = xmlPullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if(eventType == XmlPullParser.START_DOCUMENT) {
                } else if(eventType == XmlPullParser.END_DOCUMENT) {
                } else if(eventType == XmlPullParser.START_TAG) {
                    data = xmlPullParser.getName();
                    if(data.equals("item")){
                        itemFlg = 1;
                        item = new Item();
                    }
                    fieldName = data;
                } else if(eventType == XmlPullParser.END_TAG) {
                    data = xmlPullParser.getName();
                    if(data.equals("item")){
                        itemFlg = 0;
                        list.add(item);
                    }

                } else if(eventType == XmlPullParser.TEXT) {
                    data = xmlPullParser.getText();
                    if(itemFlg == 1){
                        if(fieldName.equals("title")){
                            item.setTitle(data);
                            fieldName = "";
                        }
                        if(fieldName.equals("date")){
                            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
                            SimpleDateFormat str = new SimpleDateFormat("yyyy/MM/dd HH:mm",Locale.JAPAN);
                            item.setPubDate(str.format(date.parse(data)));
                            fieldName = "";
                        }
                        if(fieldName.equals("link")){
                            item.setLink(data);
                            fieldName = "";
                        }
                    }

                }
                eventType = xmlPullParser.next();
        }

        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }

        return list;
    }
}
