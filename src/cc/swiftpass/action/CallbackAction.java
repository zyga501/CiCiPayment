package cc.swiftpass.action;

import cc.ProjectLogger;
import cc.chanpay.api.RequestBean.SinglePayRequestData;
import cc.chanpay.api.SinglePay;
import cc.database.merchant.MerchantInfo;
import cc.utils.IdConvert;
import framework.action.AjaxActionSupport;
import framework.utils.XMLParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

public class CallbackAction extends AjaxActionSupport {
    public final static String WEIXINJSPAYCALLBACK = "Callback!weixinJsPay";
    public final static String ALIJSPAYCALLBACK = "Callback!aliJsPay";
    public final static String SUCCESS = "success";

    public void weixinJsPay() throws Exception {
        getResponse().getWriter().write(SUCCESS);
    }

    public void aliJsPay() throws Exception {
        handlerCallback();
        getResponse().getWriter().write(SUCCESS);
    }

    private void handlerCallback() throws Exception {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getRequest().getInputStream(), "utf-8"));
            StringBuilder stringBuilder = new StringBuilder();
            String lineBuffer;
            while ((lineBuffer = bufferedReader.readLine()) != null) {
                stringBuilder.append(lineBuffer);
            }
            bufferedReader.close();

            String responseString = stringBuilder.toString();
            Map<String,Object> responseResult = XMLParser.convertMapFromXml(responseString);
            if (responseResult.get("result_code").toString().compareTo("0") == 0 &&
                responseResult.get("pay_result").toString().compareTo("0") == 0) {
                long merchantId = IdConvert.DecryptionId(Long.parseLong(responseResult.get("attach").toString()));
                int total_fee = Integer.parseInt(responseResult.get("total_fee").toString());
                MerchantInfo merchantInfo = MerchantInfo.getMerchantInfoById(merchantId);
                SinglePayRequestData singlePayRequestData = new SinglePayRequestData();
                singlePayRequestData.bankGeneralName = merchantInfo.getBankGeneralName();
                singlePayRequestData.bankName = merchantInfo.getBankName();
                singlePayRequestData.bankCode = merchantInfo.getBankCode();
                singlePayRequestData.accountType = "00";
                singlePayRequestData.accountNo = merchantInfo.getAccountNo();
                singlePayRequestData.accountName = merchantInfo.getAccountName();
                singlePayRequestData.tel = merchantInfo.getAccountPhone();
                singlePayRequestData.amount = total_fee;
                SinglePay singlePay = new SinglePay(singlePayRequestData);
                if (singlePay.postRequest()) {

                }
            }
        }
        catch (Exception exception) {
            ProjectLogger.error("Swiftpass Callback Error!");
        }
    }
}
