package cc.chanpay.api;

import cc.chanpay.api.RequestBean.SinglePayRequestData;

public class SinglePay extends ChanPayAPIWithSign {
    public SinglePay(SinglePayRequestData requestData) {
        requestData_ = requestData;
    }
}