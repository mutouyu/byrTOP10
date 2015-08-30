package com.bupt.req;

/**
 * Created by leeyoung on 15/8/29.
 */
public class TextMessage  extends BaseMessage{
    // 消息内容
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
