package cc.swiftpass.api;

import cc.swiftpass.api.RequestBean.WeixinJsPayRequestData;

import java.util.Map;

public class WeixinJsPay extends SwiftPassAPIWithSign {
    public final static String JSPAY_API = "https://pay.swiftpass.cn/pay/gateway";

    public WeixinJsPay(WeixinJsPayRequestData weixinJsPayRequestData) {
        requestData_ = weixinJsPayRequestData;
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
