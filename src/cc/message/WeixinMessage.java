package cc.message;

import cc.ProjectSettings;
import cc.weixin.api.AccessToken;
import cc.weixin.api.RequestBean.TemplateMessageRequestData;
import cc.weixin.api.RequestBean.TemplateNoticeRequestData;
import cc.weixin.api.TemplateMessage;

public class WeixinMessage {
    public static boolean sendTemplateMessage(String openid, String timeEnd, double totalFee, String storeName, String payType, String orderNo, String remark) throws Exception {
        String accessToken = AccessToken.getAccessToken(ProjectSettings.getMapData("weixinServerInfo").get("appid").toString());

        if (!accessToken.isEmpty()) {
            TemplateMessageRequestData templateMessageRequestData = new TemplateMessageRequestData();
            templateMessageRequestData.template_id = ProjectSettings.getMapData("weixinServerInfo").get("templateId").toString();
            templateMessageRequestData.nickName = "尊敬的商户，您成功收到"+openid.substring(20,24)+"的消费付款：";
            templateMessageRequestData.timeEnd = timeEnd;
            templateMessageRequestData.totalFee = totalFee;
            templateMessageRequestData.storeName = storeName;
            templateMessageRequestData.paytype = payType;
            templateMessageRequestData.orderno = orderNo;
            templateMessageRequestData.remark = remark;

            TemplateMessage templateMessage = new TemplateMessage(accessToken);
            templateMessageRequestData.touser = openid;
            templateMessage.postRequest(templateMessageRequestData.buildRequestData());
            return true;
        }
        return false;
    }

    public static boolean sendNoticeMessage(String appid,String templateid ,String accessToken,String openid,String[] msgstr,String editer) throws Exception {
        // String accessToken = new String();
        if (accessToken.equals(""))
            accessToken = AccessToken.getAccessToken(appid);

        if (!accessToken.isEmpty()) {
            TemplateNoticeRequestData templateNoticeRequestData = new TemplateNoticeRequestData();
            templateNoticeRequestData.template_id =templateid;
            templateNoticeRequestData.timeArea = msgstr[1];
            templateNoticeRequestData.title = msgstr[0];
            templateNoticeRequestData.notice = msgstr[2];
            templateNoticeRequestData.editer = editer;
            templateNoticeRequestData.remark =  msgstr[3];
            TemplateMessage templateMessage = new TemplateMessage(accessToken);
            templateNoticeRequestData.touser = openid;
            templateMessage.postRequest(templateNoticeRequestData.buildRequestData());
            return true;
        }
        return false;
    }
}