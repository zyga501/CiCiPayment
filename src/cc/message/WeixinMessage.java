package cc.message;

import cc.ProjectSettings;
import cc.weixin.api.AccessToken;
import cc.weixin.api.RequestBean.TemplateMessageRequestData;
import cc.weixin.api.RequestBean.TemplateNoticeRequestData;
import cc.weixin.api.TemplateMessage;

import java.util.Map;

public class WeixinMessage {
    public static boolean sendTemplateMessage(Map orderInfo) throws Exception {
        String accessToken = new String();
        AccessToken.AccessTokenInit(ProjectSettings.getMapData("weixinServerInfo").get("appid").toString(),ProjectSettings.getMapData("weixinServerInfo").get("appSecret").toString());
        accessToken = AccessToken.getAccessToken(ProjectSettings.getMapData("weixinServerInfo").get("appid").toString());

        if (!accessToken.isEmpty()) {
            TemplateMessageRequestData templateMessageRequestData = new TemplateMessageRequestData();
            templateMessageRequestData.template_id = ProjectSettings.getMapData("weixinServerInfo").get("templateId").toString();
            templateMessageRequestData.nickName = "尊敬的商户，您成功收到CiCi二维码消费付款：";
            templateMessageRequestData.timeEnd = orderInfo.get("timeend").toString();
            templateMessageRequestData.totalFee = Float.parseFloat(orderInfo.get("totalfee").toString()) / 100.0;
            templateMessageRequestData.storeName = orderInfo.get("storename").toString();
            templateMessageRequestData.paytype = orderInfo.get("paytype").toString();
            templateMessageRequestData.orderno = orderInfo.get("tradeno").toString();
            templateMessageRequestData.remark = "";

            TemplateMessage templateMessage = new TemplateMessage(accessToken);
                templateMessageRequestData.touser = orderInfo.get("openid").toString();
                templateMessage.postRequest(templateMessageRequestData.buildRequestData());
            return true;
        }
        return false;
    }

    public static boolean sendNoticeMessage(String appid,String templateid ,String accessToken_,String openid,String[] msgstr,String editer) throws Exception {
        String accessToken = new String();
        AccessToken.AccessTokenInit(ProjectSettings.getMapData("weixinServerInfo").get("appid").toString(),ProjectSettings.getMapData("weixinServerInfo").get("appSecret").toString());
        accessToken = AccessToken.getAccessToken(ProjectSettings.getMapData("weixinServerInfo").get("appid").toString());

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