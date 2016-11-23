package cc.action;

import cc.ProjectLogger;
import cc.database.merchant.MerchantInfo;
import cc.utils.IdConvert;
import com.opensymphony.xwork2.ActionContext;
import QimCommon.struts.AjaxActionSupport;
import QimCommon.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

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
            return "SwiftPassAliPay";
        }
        else if (userAgent.contains("bestpay")){
            return "BestPay";
        }
        else
            return "goto404";
    }
}
