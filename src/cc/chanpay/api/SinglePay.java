package cc.chanpay.api;

import cc.chanpay.api.RequestBean.SinglePayRequestData;

import java.util.Map;

public class SinglePay extends ChanPayAPIWithSign {
    public SinglePay(SinglePayRequestData requestData) {
        requestData_ = requestData;
    }

    public String getReqSn() {
        return reqSn_;
    }

    public String getTimeStamp() {
        return timeStamp_;
    }

    @Override
    protected boolean handlerResponse(Map<String, Object> responseResult) throws Exception {
        reqSn_ = ((Map<String, Object>)responseResult.get("INFO")).get("REQ_SN").toString();
        timeStamp_ = ((Map<String, Object>)responseResult.get("INFO")).get("TIMESTAMP").toString();
        return true;
    }

    private String reqSn_;
    private String timeStamp_;
}