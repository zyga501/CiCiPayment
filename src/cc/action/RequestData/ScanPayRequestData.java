package cc.action.RequestData;

public class ScanPayRequestData {
    public String id;
    public String mode;
    public String body; // 商品描述 商品或支付单简要描述
    public String total_fee; // 总金额 订单总金额，单位为分，只能为整数
    public String sign;
    public String redirect_uri;
    public String data;
}
