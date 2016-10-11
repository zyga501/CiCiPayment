package cc.swiftpass.action;

import cc.ProjectLogger;
import cc.ProjectSettings;
import cc.swiftpass.api.AliJsPay;
import cc.swiftpass.api.RequestBean.AliJsPayRequestData;
import cc.swiftpass.api.RequestBean.WeixinJsPayRequestData;
import cc.swiftpass.api.WeixinJsPay;
import cc.weixin.api.OpenId;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import framework.action.AjaxActionSupport;
import framework.utils.StringUtils;

public class PayAction extends AjaxActionSupport {
    public String weixinJsPay() throws Exception {
        try{
        WeixinJsPayRequestData weixinJsPayRequestData = new WeixinJsPayRequestData();
        weixinJsPayRequestData.mch_id = "100530020860";
        weixinJsPayRequestData.body = getParameter("body").toString();
        weixinJsPayRequestData.total_fee = (int)Double.parseDouble(getParameter("total_fee").toString());
            OpenId weixinOpenId = new OpenId(ProjectSettings.getMapData("weixinServerInfo").get("appid").toString(),
                ProjectSettings.getMapData("weixinServerInfo").get("appSecret").toString(), getParameter("code").toString());
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
        catch (Exception e){
            e.printStackTrace();
        }
        return AjaxActionComplete(false);
    }

    public void aliJsPay() throws Exception {
        if (getParameter("auth_code") == null) {
            fetchBuyerId();
            return;
        }

        AliJsPayRequestData aliJsPayRequestData = new AliJsPayRequestData();
        aliJsPayRequestData.mch_id = ProjectSettings.getMapData("swiftpass").get("merchantId").toString();
        aliJsPayRequestData.body = ProjectSettings.getMapData("swiftpass").get("body").toString();
        aliJsPayRequestData.attach = getParameter("state").toString();
        aliJsPayRequestData.total_fee = (int)Double.parseDouble(getParameter("total_amount").toString());
        String requestUrl = getRequest().getRequestURL().toString();
        requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf('/'));
        requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf('/') + 1) + "swiftpass/"
                + CallbackAction.ALIJSPAYCALLBACK;
        aliJsPayRequestData.notify_url = requestUrl;
        aliJsPayRequestData.buyer_id = fetchBuyerId(getParameter("auth_code").toString());
        if (!StringUtils.convertNullableString(getParameter("out_trade_no")).isEmpty()) {
            aliJsPayRequestData.out_trade_no = getParameter("out_trade_no").toString();
        }

        AliJsPay aliJsPay = new AliJsPay(aliJsPayRequestData);
        if (aliJsPay.postRequest(ProjectSettings.getMapData("swiftpass").get("apiKey").toString())) {
            getResponse().sendRedirect(aliJsPay.getPayUrl());
        }
    }

    void fetchBuyerId() throws Exception {
        String appid = ProjectSettings.getMapData("aliServerInfo").get("appid").toString();
        String redirect_uri =  getRequest().getScheme()+"://" + getRequest().getServerName() + getRequest().getContextPath() + "/swiftpass/alijspay.jsp";
        String fetchOpenidUri = String.format("https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=%s&scope=auth_base&state=%s&redirect_uri=%s",
                appid, getParameter("cid").toString(), redirect_uri);
        getResponse().sendRedirect(fetchOpenidUri);
    }

    String fetchBuyerId(String auth_code) throws Exception {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
                ProjectSettings.getMapData("aliServerInfo").get("appid").toString(),
                ProjectSettings.getMapData("aliServerInfo").get("privateKey").toString(),
                "json",
                "GBK",
                ProjectSettings.getMapData("aliServerInfo").get("publicKey").toString());
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setGrantType("authorization_code");
        request.setCode(auth_code);
        AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            return response.getUserId();
        }

        ProjectLogger.error("Fetch SwitfPass BuyerId Failed!");
        return "";
    }
}
