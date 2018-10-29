package local.hal.st32.android.asahifeedreader60213;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.util.Log;
import android.util.Xml;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class RssParse {
    public static String getRss(Context context, String url)
            throws IOException{

        StringBuilder sb = new StringBuilder();

        AndroidHttpClient client = AndroidHttpClient.newInstance("RssFeed");
        HttpGet get = new HttpGet(url);

        try{
            // リクエストを取得
            HttpResponse response = client.execute(get);
            Log.d("sample" ,""+response);
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
            Log.d("sample", "Error");
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
                    Log.d("sample", "Start document");

                } else if(eventType == XmlPullParser.END_DOCUMENT) {
                    Log.d("sample", "End document");

                } else if(eventType == XmlPullParser.START_TAG) {
                    data = xmlPullParser.getName();
                    Log.d("sample", "Start tag "+ data);
                    if(data.equals("item")){
                        itemFlg = 1;
                        item = new Item();
                    }
                    fieldName = data;

                } else if(eventType == XmlPullParser.END_TAG) {
                    data = xmlPullParser.getName();
                    Log.d("sample", "End tag "+ data);
                    if(data.equals("item")){
                        itemFlg = 0;
                        list.add(item);
                    }

                } else if(eventType == XmlPullParser.TEXT) {
                    data = xmlPullParser.getText();
                    Log.d("sample", "Text "+ data);

                    if(itemFlg == 1){
                        if(fieldName.equals("title")){
                            Log.d("sample", "title = "+ data);
                            item.setTitle(data);
                            fieldName = "";
                        }
                        if(fieldName.equals("date")){
                            Log.d("sample", "pubDate = "+ data);
                            item.setPubDate(data);
                            fieldName = "";
                        }
                        if(fieldName.equals("link")){
                            Log.d("sample", "link = "+ data);
                            item.setLink(data);
                            fieldName = "";
                        }
                    }

                }
                eventType = xmlPullParser.next();
        }

        } catch (Exception e) {
            Log.d("sample", "Error");
        }

        return list;
    }
}
