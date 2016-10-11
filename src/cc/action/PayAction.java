package cc.action;

import cc.ProjectLogger;
import cc.database.merchant.MerchantInfo;
import cc.utils.IdConvert;
import com.opensymphony.xwork2.ActionContext;
import framework.action.AjaxActionSupport;
import framework.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class PayAction extends AjaxActionSupport {
    public String payAdapter() {
        try {
            do {
                if (StringUtils.convertNullableString(getParameter("cid")).length() == 0) {
                    break;
                }
                setAttribute("openid", "oBhD-wj1zMF5-FET_9dwK8rI2nt0");
                long cardId = Long.parseLong(getParameter("cid").toString());
                MerchantInfo merchantInfo = MerchantInfo.getMerchantInfoById(IdConvert.DecryptionId(cardId));
                if (merchantInfo == null) {
                    return "registerPrepare";
                }

                return choogPayment();
            } while (false);
        }
        catch (Exception exception) {
            ProjectLogger.error("payAdapter Error, cid:" + getParameter("cid"));
        }
        return AjaxActionComplete();
    }

    String choogPayment() throws IOException {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
        String userAgent = request.getHeader("User-Agent").toLowerCase();
        if (userAgent.contains("micromessenger")){
            return "WeixinPay";
        }
        else if (userAgent.contains("walletclient")){
            return "JDPay";
        }
        else if (userAgent.contains("alipayclient")){
            return "AliPay";
        }
        else if (userAgent.contains("bestpay")){
            return "BestPay";
        }
        else
            return "goto404";
    }
}