package cc.chanpay.action;

import cc.chanpay.api.RequestBean.VerifiedRequestData;
import cc.chanpay.api.Verified;
import QimCommon.struts.AjaxActionSupport;
import QimCommon.utils.StringUtils;

public class GeneralAction extends AjaxActionSupport{
    public String verified() throws Exception {
        VerifiedRequestData verifiedRequestData = new VerifiedRequestData();
        verifiedRequestData.bankGeneralName = getParameter("bankGeneralName").toString();
        verifiedRequestData.bankName = getParameter("bankName").toString();
        verifiedRequestData.bankCode = getParameter("bankCode").toString();
        verifiedRequestData.accountType = getParameter("accountType").toString();
        verifiedRequestData.accountNo = getParameter("accountNo").toString();
        verifiedRequestData.accountName = getParameter("accountName").toString();
        verifiedRequestData.tel = StringUtils.convertNullableString(getParameter("tel"));
        verifiedRequestData.idType = StringUtils.convertNullableString(getParameter("idType"));
        verifiedRequestData.id = StringUtils.convertNullableString(getParameter("id"));
        Verified verified = new Verified(verifiedRequestData);
        return AjaxActionComplete(verified.postRequest());
    }
}
