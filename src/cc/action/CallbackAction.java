package cc.action;

import QimCommon.struts.AjaxActionSupport;
import QimCommon.utils.StringUtils;
import QimCommon.utils.XMLParser;
import cc.ProjectLogger;
import cc.chanpay.api.RequestBean.SinglePayRequestData;
import cc.chanpay.api.SinglePay;
import cc.database.merchant.MerchantInfo;
import cc.database.merchant.PayMethod;
import cc.database.order.ChanOrderInfo;
import cc.database.order.PayOrderInfo;
import cc.message.WeixinMessage;
import cc.utils.IdConvert;
import cc.utils.PublicFunc;

import java.util.Map;

public class CallbackAction extends AjaxActionSupport {
    public final static String JSPAYCALLBACK = "Callback!jsPay";
    public final static Object syncObject = new Object();

    public void jsPay() throws Exception {
        handlerCallback();
    }

    private void handlerCallback() throws Exception {
        Map<String,Object> responseResult = XMLParser.convertMapFromXml(getInputStreamAsString());
        if (responseResult == null) {
            return;
        }

        synchronized (syncObject) {
            long merchantId = IdConvert.DecryptionId(Long.parseLong(responseResult.get("data").toString()));
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

            PayMethod payMethod = PayMethod.getPayMethodById(merchantInfo.getPayMethodId());
            if (payMethod == null) {
                return;
            }

            boolean paid = false;
            try {
                if (payMethod.getMerchantId() == 1999999999999999L) {
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
                    if (paid = singlePay.postRequest()) {
                        saveChanOrder(merchantId, tradeNo, singlePayRequestData.amount, singlePay.getReqSn(), singlePay.getTimeStamp());
                    }
                }
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

            }
            catch (Exception exception) {

            }
            finally {
                WeixinMessage.sendTemplateMessage(merchantInfo.getOpenid(), timeEnd, total_fee / 100.0, merchantInfo.getName(), "CiCi卡支付", tradeNo, "");
                savePayOrder(merchantId, total_fee,
                        tradeNo,
                        payMethod.getMode(),
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
