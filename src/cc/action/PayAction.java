package cc.action;

import cc.ProjectLogger;
import cc.database.merchant.MerchantInfo;
import cc.utils.IdConvert;
import framework.action.AjaxActionSupport;
import framework.utils.StringUtils;

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
                    return "registerMerchant";
                }

            } while (false);
        }
        catch (Exception exception) {
            ProjectLogger.error("payAdapter Error", "cid:" + getParameter("cid"));
        }
        return AjaxActionComplete();
    }
}
