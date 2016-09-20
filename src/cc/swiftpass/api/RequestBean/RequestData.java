package cc.swiftpass.api.RequestBean;

import cc.swiftpass.utils.Signature;
import framework.utils.StringUtils;

public class RequestData {
    public RequestData() {
        nonce_str = StringUtils.generateRandomString(32);
    }

    public boolean checkParameter() {
        try {
            return !mch_id.isEmpty()
                    && !nonce_str.isEmpty();
        }
        catch (Exception exception) {

        }

        return false;
    }

    public void buildSign(String apiKey) throws IllegalAccessException {
        this.sign = Signature.generateSign(this, apiKey);
    }

    public String mch_id; // 商户号，由中信银行分配
    public String nonce_str; // 随机字符串，不长于32 位
    public String sign; // MD5 签名结果
}
