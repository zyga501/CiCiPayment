package cc.utils;

import cc.ProjectSettings;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class SMS {
    public static void main(String[] args) throws Exception {
        boolean result = SMS.SendMessage("13867433895", "6842");
        System.exit(0);
    }

    private final static String SMSApiUrl = "http://gw.api.taobao.com/router/rest";
    private final static String APPKey = ProjectSettings.getMapData("SMSInfo").get("AppKey").toString();
    private final static String APPSecret = ProjectSettings.getMapData("SMSInfo").get("AppSecret").toString();
    private final static String FreeSignName = ProjectSettings.getMapData("SMSInfo").get("FreeSignName").toString();
    private final static String TemplateCode = ProjectSettings.getMapData("SMSInfo").get("TemplateCode").toString();

    static public boolean SendMessage(String recNum, String number) {
        try {
            TaobaoClient client = new DefaultTaobaoClient(SMSApiUrl, APPKey, APPSecret);
            AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
            req.setExtend("");
            req.setSmsType("normal");
            req.setSmsFreeSignName(FreeSignName);
            req.setSmsParamString("{\"number\":\"" + number + "\"}");
            req.setRecNum(recNum);
            req.setSmsTemplateCode(TemplateCode);
            AlibabaAliqinFcSmsNumSendResponse response = client.execute(req);
            return response.getResult().getErrCode().compareTo("0") == 0;
        }
        catch (Exception exception) {

        }

        return false;
    }
}
