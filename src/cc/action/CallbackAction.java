package cc.action;

import QimCommon.struts.AjaxActionSupport;
import QimCommon.utils.JsonUtils;
import QimCommon.utils.StringUtils;
import cc.chanpay.api.RequestBean.SinglePayRequestData;
import cc.chanpay.api.SinglePay;
import cc.database.merchant.MerchantInfo;
import cc.database.merchant.PayMethod;
import cc.database.order.ChanOrderInfo;
import cc.database.order.PayOrderInfo;
import cc.message.WeixinMessage;
import cc.utils.IdConvert;

import java.util.Map;

public class CallbackAction extends AjaxActionSupport {
    public final static String JSPAYCALLBACK = "Callback!jsPay";
    public final static Object syncObject = new Object();

    public void jsPay() throws Exception {
        handlerCallback();
    }

    private void handlerCallback() throws Exception {
        Map<String,Object> responseResult = JsonUtils.toMap(getInputStreamAsString(), true);
        if (responseResult == null) {
            return;
        }

        synchronized (syncObject) {
            String[] attatch = responseResult.get("data").toString().split("&");
            long merchantId = IdConvert.DecryptionId(Long.parseLong(attatch[0]));
            boolean privateQualification = Integer.parseInt(attatch[1]) == 1;
            PayMethod.PayType payType = PayMethod.payTypeValueOf(Integer.parseInt(attatch[2]));
            String mode = attatch[1];
            String method = attatch[2];
            int total_fee = Integer.parseInt(responseResult.get("total_fee").toString());
            String tradeNo = StringUtils.convertNullableString(responseResult.get("out_trade_no"));
            String timeEnd = StringUtils.convertNullableString(responseResult.get("time_end"));

            if (PayOrderInfo.getOrderInfoByTradeNo(tradeNo) != null) {
                return;
            }

            MerchantInfo merchantInfo = MerchantInfo.getMerchantInfoById(merchantId);
            if (merchantInfo == null) {
                return;
            }

            boolean paid = false;
            try {
                if (!privateQualification) {
                    SinglePayRequestData singlePayRequestData = new SinglePayRequestData();
                    singlePayRequestData.bankGeneralName = merchantInfo.getBankGeneralName();
                    singlePayRequestData.bankName = merchantInfo.getBankName();
                    singlePayRequestData.bankCode = merchantInfo.getBankCode();
                    singlePayRequestData.accountType = "00";
                    singlePayRequestData.accountNo = merchantInfo.getAccountNo();
                    singlePayRequestData.accountName = merchantInfo.getAccountName();
                    singlePayRequestData.tel = merchantInfo.getAccountPhone();
                    if (mode.indexOf("weixin") != -1 || method.indexOf("weixin") != -1) {
                        singlePayRequestData.amount = (int)(total_fee * (1 - merchantInfo.getWxRate()));
                    }
                    else if (mode.indexOf("ali") != -1) {
                        singlePayRequestData.amount = (int)(total_fee * (1 - merchantInfo.getAliRate()));
                    }
                    else {
                        singlePayRequestData.amount = total_fee;
                    }
                    SinglePay singlePay = new SinglePay(singlePayRequestData);
                    if (paid = singlePay.postRequest()) {
                        saveChanOrder(merchantId, tradeNo, singlePayRequestData.amount, singlePay.getReqSn(), singlePay.getTimeStamp());
                    }
                }
            }
            catch (Exception exception) {

            }
            finally {
                WeixinMessage.sendTemplateMessage(merchantInfo.getOpenid(), timeEnd, total_fee / 100.0, merchantInfo.getName(), "CiCi卡支付", tradeNo, "");
                savePayOrder(merchantId, total_fee,
                        tradeNo,
                        payType.toString(),
                        timeEnd,
                        merchantInfo.getWxRate(),
                        paid);
            }
            return;
        }
    }

    private boolean savePayOrder(long merchantId, int tradeAmount, String tradeNo, String tradeType, String tradeTime, double tradeRate, boolean paid) {
        PayOrderInfo payOrderInfo = new PayOrderInfo();
        payOrderInfo.setMerchantId(merchantId);
        payOrderInfo.setTradeNo(tradeNo);
        payOrderInfo.setTradeAmount(tradeAmount);
        payOrderInfo.setTradeType(tradeType);
        payOrderInfo.setTradeTime(tradeTime);
        payOrderInfo.setTradeRate(tradeRate);
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
