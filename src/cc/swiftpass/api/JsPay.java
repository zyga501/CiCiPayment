package cc.swiftpass.api;

import cc.swiftpass.api.RequestBean.JsPayRequestData;

import java.util.Map;

public class JsPay extends SwiftPassAPIWithSign {
    public final static String JSPAY_API = "https://api.mch.weixin.qq.com/pay/micropay";

    public JsPay(JsPayRequestData jsPayRequestData) {
        requestData_ = jsPayRequestData;
    }

    @Override
    protected String getAPIUri() {
        return JSPAY_API;
    }

    @Override
    protected boolean handlerResponse(Map<String,Object> responseResult) {
        return true;
    }
}
