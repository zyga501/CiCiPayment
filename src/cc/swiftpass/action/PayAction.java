package cc.swiftpass.action;

import cc.swiftpass.api.JsPay;
import cc.swiftpass.api.RequestBean.JsPayRequestData;
import framework.action.AjaxActionSupport;
import framework.utils.StringUtils;

public class PayAction extends AjaxActionSupport {
    public String jsPay() throws Exception {
        JsPayRequestData jsPayRequestData = new JsPayRequestData();
        jsPayRequestData.mch_id = "100530020860";
        jsPayRequestData.body = getParameter("body").toString();
        jsPayRequestData.total_fee = (int)Double.parseDouble(getParameter("total_fee").toString());
        jsPayRequestData.sub_openid = "";
        String requestUrl = getRequest().getRequestURL().toString();
        requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf('/'));
        requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf('/') + 1) + "swiftpass/"
                + CallbackAction.JSPAYCALLBACK;
        jsPayRequestData.notify_url = requestUrl;
        if (!StringUtils.convertNullableString(getParameter("out_trade_no")).isEmpty()) {
            jsPayRequestData.out_trade_no = getParameter("out_trade_no").toString();
        }

        JsPay jsPay = new JsPay(jsPayRequestData);
        return AjaxActionComplete(jsPay.postRequest("4fe41e8ca0b0c643120ee0aca96cf6cb"));
    }
}
