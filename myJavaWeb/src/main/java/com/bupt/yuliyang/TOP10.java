package com.bupt.yuliyang;

/**
 * Created by leeyoung on 15/8/29.
 */
public class TOP10 {
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String title;

    @Override
    public String toString() {
        return "<a href=\""+ this.url+"\">"+this.title+"</a>\n";
    }
}
