package com.bupt.yuliyang;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;

import com.bupt.jdbcUtil.JdbcUtils;
import com.bupt.resp.TextMessage;

/**
 * Created by leeyoung on 15/8/29.
 */
public class CoreService {
    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return
     */
    public static String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";

            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);

            // 文本消息

            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                String content = requestMap.get("Content");
                TOP10 t = new TOP10();
                Connection connection = null;
                if(content.length() == 8){
                    try {
                        connection = JdbcUtils.getConnection();
                        if (connection != null) {
                            Statement stmt = connection.createStatement();
                            ResultSet rs = stmt.executeQuery("SELECT * FROM top10 WHERE Tdate ="+ content);
                            respContent = "";
                            int id=0;
                            boolean getdata = false;
                            while (rs.next()) {
                                getdata = true;
                                t.setTitle("【"+(++id)+"】"+rs.getString("title"));
                                t.setUrl(rs.getString("url"));
                                respContent += t.toString();
                            }
                            if(!getdata){
                                respContent = "不好意思，未收录该日期的十大！";
                            }
                        }
                    } catch (SQLException e) {
                        t.setTitle("服务器异常");
                        t.setUrl("http://m.byr.cn");
                        respContent = t.toString();
                    } finally {
                        connection.close();
                    }
                }else{
                    respContent = "日期格式错误!格式类似20150830";
                }
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！回复日期20150830查看2015年8月30号的十大。其他日期的格式类似";
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // TODO 自定义菜单权没有开放，暂不处理该类消息
                }
            }
            textMessage.setContent(respContent);
            respMessage = MessageUtil.textMessageToXml(textMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;
    }
}
