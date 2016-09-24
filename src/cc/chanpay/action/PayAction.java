package cc.chanpay.action;

import cc.chanpay.api.RequestBean.SinglePayRequestData;
import cc.chanpay.api.SinglePay;
import framework.action.AjaxActionSupport;
import framework.utils.StringUtils;

public class PayAction extends AjaxActionSupport{
    public String singlePay() throws Exception {
        SinglePayRequestData singlePayRequestData = new SinglePayRequestData();
        singlePayRequestData.amount = Integer.parseInt(getParameter("amount").toString());
        singlePayRequestData.bankGeneralName = getParameter("bankGeneralName").toString();
        singlePayRequestData.bankName = getParameter("bankName").toString();
        singlePayRequestData.bankCode = getParameter("bankCode").toString();
        singlePayRequestData.accountType = getParameter("accountType").toString();
        singlePayRequestData.accountNo = getParameter("accountNo").toString();
        singlePayRequestData.accountName = getParameter("accountName").toString();
        singlePayRequestData.tel = StringUtils.convertNullableString(getParameter("tel"));
        SinglePay singlePay = new SinglePay(singlePayRequestData);
        return AjaxActionComplete(singlePay.postRequest());
    }
}
