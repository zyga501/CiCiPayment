package cc.message;

import cc.ProjectSettings;
import cc.message.api.AccessToken;
import cc.message.api.RequestBean.TemplateNoticeRequestData;
import cc.message.api.TemplateMessage;

public class WeixinMessage {
    public static boolean sendTemplateMessage(String openid,String msgstr) throws Exception {
        String accessToken = new String();
        accessToken = AccessToken.getAccessToken(ProjectSettings.getMapData("weixinServerInfo").get("appid").toString());

//            if (!accessToken.isEmpty()) {
//                TemplateMessageRequestData templateMessageRequestData = new TemplateMessageRequestData();
//                templateMessageRequestData.template_id = ProjectSettings.getMapData("weixinServerInfo").get("chargetemplate_id").toString();
//                templateMessageRequestData.nickName = "尊敬的商户，您成功收到"+openid.substring(20,24)+"的消费付款：";
//                templateMessageRequestData.timeEnd = orderInfo.getTimeEnd();
//                templateMessageRequestData.totalFee = orderInfo.getTotalFee() / 100.0;
//                templateMessageRequestData.storeName = subMerchantUser.getStoreName();
//                templateMessageRequestData.paytype = "微信";
//                templateMessageRequestData.orderno = orderInfo.getOutTradeNo();
//                templateMessageRequestData.remark = ("收银员："+subMerchantUser.getUserName()+"\\n"+subMerchantInfo.getAds()).replaceAll("\r\n","\\\\n");
//
//                TemplateMessage templateMessage = new TemplateMessage(accessToken);
//                    templateMessageRequestData.touser = openid;
//                    templateMessage.postRequest(templateMessageRequestData.buildRequestData());
//                return true;
//            }
        return false;
    }
    public static boolean sendNoticeMessage(String openid,String[] msgstr) throws Exception {
        String accessToken = new String();
        accessToken = AccessToken.getAccessToken(ProjectSettings.getMapData("weixinServerInfo").get("appid").toString());

            if (!accessToken.isEmpty()) {
                TemplateNoticeRequestData templateNoticeRequestData = new TemplateNoticeRequestData();
                templateNoticeRequestData.template_id = ProjectSettings.getMapData("weixinServerInfo").get("noticetemplate_id").toString();
                templateNoticeRequestData.timeArea = msgstr[1];
                templateNoticeRequestData.title = msgstr[0];
                templateNoticeRequestData.notice = msgstr[2];
                templateNoticeRequestData.editer = ProjectSettings.getMapData("weixinServerInfo").get("editer").toString();
                templateNoticeRequestData.remark =  msgstr[3];
                TemplateMessage templateMessage = new TemplateMessage(accessToken);
                templateNoticeRequestData.touser = openid;
                    templateMessage.postRequest(templateNoticeRequestData.buildRequestData());
                return true;
            }
        return false;
    }
}