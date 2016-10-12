package cc.swiftpass.action;

import cc.ProjectLogger;
import cc.chanpay.api.RequestBean.SinglePayRequestData;
import cc.chanpay.api.SinglePay;
import cc.database.merchant.MerchantInfo;
import cc.database.order.ChanOrderInfo;
import cc.database.order.PayOrderInfo;
import cc.utils.IdConvert;
import framework.action.AjaxActionSupport;
import framework.utils.StringUtils;

import java.util.Map;

public class CallbackAction extends AjaxActionSupport {
    public final static String WEIXINJSPAYCALLBACK = "Callback!weixinJsPay";
    public final static String ALIJSPAYCALLBACK = "Callback!aliJsPay";
    public final static String SUCCESS = "success";
    public final static String WEIXINPJSPAY = "SwiftPass.WeixinJsPay";
    public final static String ALIJSPAY = "SwiftPass.AliJsPay";

    public void weixinJsPay() throws Exception {
        handlerCallback(WEIXINPJSPAY);
        getResponse().getWriter().write(SUCCESS);
    }

    public void aliJsPay() throws Exception {
        handlerCallback(ALIJSPAY);
        getResponse().getWriter().write(SUCCESS);
    }

    private void handlerCallback(String tradeType) throws Exception {
        Map<String,Object> responseResult = getInputStreamMap();
        if (responseResult.get("result_code").toString().compareTo("0") == 0 &&
            responseResult.get("pay_result").toString().compareTo("0") == 0) {
            long merchantId = IdConvert.DecryptionId(Long.parseLong(responseResult.get("attach").toString()));
            int total_fee = Integer.parseInt(responseResult.get("total_fee").toString());
            boolean paid = doChanPay(merchantId, StringUtils.convertNullableString(responseResult.get("out_trade_no")), total_fee, tradeType);
            if (savePayOrder(merchantId, total_fee, StringUtils.convertNullableString(responseResult.get("out_trade_no")), tradeType, StringUtils.convertNullableString(responseResult.get("time_end")), paid))
                return;
        }

        ProjectLogger.error("Swiftpass Callback Error!");
    }

    private boolean doChanPay(long merchantId, String payTradeNo, int totalFee, String tradeType) {
        MerchantInfo merchantInfo = MerchantInfo.getMerchantInfoById(merchantId);
        if (merchantInfo == null) {
            return false;
        }

        try {
            SinglePayRequestData singlePayRequestData = new SinglePayRequestData();
            singlePayRequestData.bankGeneralName = merchantInfo.getBankGeneralName();
            singlePayRequestData.bankName = merchantInfo.getBankName();
            singlePayRequestData.bankCode = merchantInfo.getBankCode();
            singlePayRequestData.accountType = "00";
            singlePayRequestData.accountNo = merchantInfo.getAccountNo();
            singlePayRequestData.accountName = merchantInfo.getAccountName();
            singlePayRequestData.tel = merchantInfo.getAccountPhone();
            switch (tradeType) {
                case WEIXINPJSPAY:
                    singlePayRequestData.amount = (int)(totalFee * (1 - merchantInfo.getWxRate()));
                    break;
                case ALIJSPAY:
                    singlePayRequestData.amount = (int)(totalFee * (1 - merchantInfo.getAliRate()));
                    break;
            }
            SinglePay singlePay = new SinglePay(singlePayRequestData);
            if (singlePay.postRequest()) {
                saveChanOrder(merchantId, payTradeNo, singlePayRequestData.amount, singlePay.getReqSn(), singlePay.getTimeStamp());
                return true;
            }
        }
        catch (Exception exception) {

        }

        return false;
    }

    private boolean savePayOrder(long merchantId, int tradeAmount, String tradeNo, String tradeType, String tradeTime, boolean paid) {
        PayOrderInfo payOrderInfo = new PayOrderInfo();
        payOrderInfo.setMerchantId(merchantId);
        payOrderInfo.setTradeNo(tradeNo);
        payOrderInfo.setTradeAmount(tradeAmount);
        payOrderInfo.setTradeType(tradeType);
        payOrderInfo.setTradeTime(tradeTime);
        payOrderInfo.setPaid(paid);
        return PayOrderInfo.insertOrderInfo(payOrderInfo);
    }

    private boolean saveChanOrder(long merchantId, String payTradeNo, int tradeAmount, String tradeNo, String tradeTime) {
        ChanOrderInfo chanOrderInfo = new ChanOrderInfo();
        chanOrderInfo.setMerchantId(merchantId);
        chanOrderInfo.setPayTradeNo(payTradeNo);
        chanOrderInfo.setTradeNo(tradeNo);
        chanOrderInfo.setTradeAmount(tradeAmount);
        chanOrderInfo.setTradeTime(tradeTime);
        return ChanOrderInfo.insertOrderInfo(chanOrderInfo);
    }
}
