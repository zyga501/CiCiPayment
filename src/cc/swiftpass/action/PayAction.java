package cc.swiftpass.action;

import cc.swiftpass.api.AliJsPay;
import cc.swiftpass.api.RequestBean.AliJsPayRequestData;
import cc.swiftpass.api.WeixinJsPay;
import cc.swiftpass.api.RequestBean.WeixinJsPayRequestData;
import cc.swiftpass.api.WeixinOpenId;
import framework.action.AjaxActionSupport;
import framework.utils.StringUtils;

public class PayAction extends AjaxActionSupport {
    public String weixinJsPay() throws Exception {
        WeixinJsPayRequestData weixinJsPayRequestData = new WeixinJsPayRequestData();
        weixinJsPayRequestData.mch_id = "100530020860";
        weixinJsPayRequestData.body = getParameter("body").toString();
        weixinJsPayRequestData.total_fee = (int)Double.parseDouble(getParameter("total_fee").toString());
        WeixinOpenId weixinOpenId = new WeixinOpenId("", "", getParameter("code").toString());
        if (!weixinOpenId.getRequest()) {
            return AjaxActionComplete(false) ;
        }
        weixinJsPayRequestData.sub_openid = weixinOpenId.getOpenId();
        String requestUrl = getRequest().getRequestURL().toString();
        requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf('/'));
        requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf('/') + 1) + "swiftpass/"
                + CallbackAction.WEIXINJSPAYCALLBACK;
        weixinJsPayRequestData.notify_url = requestUrl;
        if (!StringUtils.convertNullableString(getParameter("out_trade_no")).isEmpty()) {
            weixinJsPayRequestData.out_trade_no = getParameter("out_trade_no").toString();
        }

        WeixinJsPay jsPay = new WeixinJsPay(weixinJsPayRequestData);
        return AjaxActionComplete(jsPay.postRequest("4fe41e8ca0b0c643120ee0aca96cf6cb"));
    }

    public String aliJsPay() throws Exception {
        AliJsPayRequestData aliJsPayRequestData = new AliJsPayRequestData();
        aliJsPayRequestData.mch_id = "100530020860";
        aliJsPayRequestData.body = getParameter("body").toString();
        aliJsPayRequestData.total_fee = (int)Double.parseDouble(getParameter("total_fee").toString());
        String requestUrl = getRequest().getRequestURL().toString();
        requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf('/'));
        requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf('/') + 1) + "swiftpass/"
                + CallbackAction.ALIJSPAYCALLBACK;
        aliJsPayRequestData.notify_url = requestUrl;
        aliJsPayRequestData.buyer_logon_id = "vonchenplus@163.com";
        if (!StringUtils.convertNullableString(getParameter("out_trade_no")).isEmpty()) {
            aliJsPayRequestData.out_trade_no = getParameter("out_trade_no").toString();
        }

        AliJsPay aliJsPay = new AliJsPay(aliJsPayRequestData);
        return AjaxActionComplete(aliJsPay.postRequest("4fe41e8ca0b0c643120ee0aca96cf6cb"));
    }
}
