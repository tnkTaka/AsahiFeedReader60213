package local.hal.st32.android.asahifeedreader60213;

import android.util.Log;

import java.text.SimpleDateFormat;

public class Item {
    private String title;
    private String pubDate;
    private String link;

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setTitle(String title) {
        Log.d("setItem",""+title);
        this.title = title;
    }

    public void setLink(String link) {
        Log.d("setItem",""+link);
        this.link = link;
    }

    public void setPubDate(String pubDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        Log.d("setItem",""+pubDate);
        this.pubDate = pubDate;
    }
}
