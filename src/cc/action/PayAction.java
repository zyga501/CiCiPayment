package cc.action;

import QimCommon.struts.AjaxActionSupport;
import QimCommon.utils.HttpUtils;
import QimCommon.utils.StringUtils;
import cc.ProjectLogger;
import cc.action.RequestData.JsPayData;
import cc.database.merchant.MerchantInfo;
import cc.database.merchant.PayMethod;
import cc.utils.IdConvert;
import cc.weixin.utils.Signature;
import com.opensymphony.xwork2.ActionContext;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import javax.servlet.http.HttpServletRequest;

public class PayAction extends AjaxActionSupport {
    public String payAdapter() {
        try {
            do {
                if (StringUtils.convertNullableString(getParameter("cid")).length() == 0) {
                    break;
                }

                long cardId = Long.parseLong(getParameter("cid").toString());
                MerchantInfo merchantInfo = MerchantInfo.getMerchantInfoById(IdConvert.DecryptionId(cardId));
                if (merchantInfo == null) {
                    return "registerPrepare";
                }

                return "payPage";
            } while (false);
        }
        catch (Exception exception) {
            ProjectLogger.error("payAdapter Error, cid:" + getParameter("cid"));
        }
        return AjaxActionComplete();
    }

    public void jsPay() throws Exception {
        PayMethod payMethod = choosePayMethod(Long.parseLong(getParameter("cid").toString()));
        if (payMethod == null) {
            return;
        }

        JsPayData jsPayData = new JsPayData();
        jsPayData.mode = payMethod.getMode();
        jsPayData.id = String.valueOf(payMethod.getMerchantId());
        jsPayData.body = "CiCi卡支付";
        jsPayData.total_fee = getParameter("total_amount").toString();
        jsPayData.method = payMethod.getMethod();
        String requestUrl = getRequest().getRequestURL().toString();
        requestUrl = requestUrl.substring(0, requestUrl.lastIndexOf('/') + 1) + CallbackAction.JSPAYCALLBACK;
        jsPayData.redirect_uri = requestUrl;
        jsPayData.data = String.format("%s&%d&%d",
                getParameter("cid").toString(),
                payMethod.getPrivateQualification() ? 1 : 0,
                payMethod.getPayType().ordinal());
        jsPayData.sign = Signature.generateSign(jsPayData, jsPayData.id);
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        String postDataXML = xStreamForRequestPostData.toXML(jsPayData);
        HttpPost httpPost = new HttpPost("http://www.qimpay.com/qlpay/api/JsPay");
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        try {
            HttpUtils.PostRequest(httpPost, (HttpResponse httpResponse)->
            {
                Header[] headers = httpResponse.getHeaders("Location");
                if (headers.length != 0)
                    getResponse().sendRedirect(headers[0].getValue());
                return null;
            });
        }
        finally {
            httpPost.abort();
        }
    }

    private PayMethod choosePayMethod(long cardId) {
        MerchantInfo merchantInfo = MerchantInfo.getMerchantInfoById(IdConvert.DecryptionId(cardId));
        if (merchantInfo == null) {
            return null;
        }

        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
        String userAgent = request.getHeader("User-Agent").toLowerCase();
        if (userAgent.contains("micromessenger")) {
            if (merchantInfo.getWxStatus()) {
                return PayMethod.getPayMethodById(merchantInfo.getPayMethodWeixinId());
            }
        }
        else if (userAgent.contains("walletclient")){
            if (merchantInfo.getJdStatus()) {
                return PayMethod.getPayMethodById(merchantInfo.getPayMethodJDId());
            }
        }
        else if (userAgent.contains("alipayclient")){
            if (merchantInfo.getAliStatus()) {
                return PayMethod.getPayMethodById(merchantInfo.getPayMethodAliId());
            }
        }
        else if (userAgent.contains("bestpay")){
            if (merchantInfo.getBestStatus()) {
                return PayMethod.getPayMethodById(merchantInfo.getPayMethodBestId());
            }
        }

        return null;
    }
}
