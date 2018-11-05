package local.hal.st32.android.asahifeedreader60213;

import android.util.Log;

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
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
