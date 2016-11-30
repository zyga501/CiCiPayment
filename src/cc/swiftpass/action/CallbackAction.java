package cc.swiftpass.action;

import cc.ProjectLogger;
import cc.chanpay.api.RequestBean.SinglePayRequestData;
import cc.chanpay.api.SinglePay;
import cc.database.merchant.MerchantInfo;
import cc.database.order.ChanOrderInfo;
import cc.database.order.PayOrderInfo;
import cc.message.WeixinMessage;
import cc.utils.IdConvert;
import QimCommon.struts.AjaxActionSupport;
import QimCommon.utils.StringUtils;
import QimCommon.utils.XMLParser;

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
        Map<String,Object> responseResult = XMLParser.convertMapFromXml(getInputStreamAsString());
        if (responseResult.get("result_code").toString().compareTo("0") == 0 &&
                responseResult.get("pay_result").toString().compareTo("0") == 0) {
            doChanPay(responseResult, tradeType);
            return;
        }

        ProjectLogger.error("Swiftpass Callback Error!");
    }

    private boolean doChanPay(Map<String,Object> responseResult, String tradeType) throws Exception {
        long merchantId = IdConvert.DecryptionId(Long.parseLong(responseResult.get("attach").toString()));
        int total_fee = Integer.parseInt(responseResult.get("total_fee").toString());
        String tradeNo = StringUtils.convertNullableString(responseResult.get("out_trade_no"));
        String timeEnd = StringUtils.convertNullableString(responseResult.get("time_end"));

        MerchantInfo merchantInfo = MerchantInfo.getMerchantInfoById(merchantId);
        if (merchantInfo == null) {
            return false;
        }

        boolean paid = false;
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
                    singlePayRequestData.amount = (int)(total_fee * (1 - merchantInfo.getWxRate()));
                    break;
                case ALIJSPAY:
                    singlePayRequestData.amount = (int)(total_fee * (1 - merchantInfo.getAliRate()));
                    break;
            }
            SinglePay singlePay = new SinglePay(singlePayRequestData);
            if (paid = singlePay.postRequest()) {
                saveChanOrder(merchantId, tradeNo, singlePayRequestData.amount, singlePay.getReqSn(), singlePay.getTimeStamp());
                return true;
            }
        }
        catch (Exception exception) {

        }
        finally {
            WeixinMessage.sendTemplateMessage(merchantInfo.getOpenid(), timeEnd, total_fee / 100.0, merchantInfo.getName(), "企盟支付", tradeNo, "");
            savePayOrder(merchantId, total_fee,
                    tradeNo,
                    tradeType,
                    timeEnd,
                    merchantInfo.getWxRate(),
                    paid);
        }

        return false;
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