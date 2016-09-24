package cc.chanpay.api;

import cc.chanpay.api.RequestBean.VerifiedRequestData;

public class Verified extends ChanPayAPIWithSign {
    public Verified(VerifiedRequestData verifiedRequestData) {
        requestData_ = verifiedRequestData;
    }
}
