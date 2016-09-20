package cc.swiftpass.api.RequestBean;

import cc.ProjectSettings;
import framework.utils.IdWorker;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class JsPayRequestData extends RequestData {
    public JsPayRequestData() throws UnknownHostException {
        service = "pay.weixin.jspay";
        mch_create_ip = InetAddress.getLocalHost().getHostAddress().toString();
        out_trade_no = String.valueOf(new IdWorker(ProjectSettings.getIdWorkerSeed()).nextId());
    }

    public String service; // 接口类型：pay.weixin.jspay
    public String out_trade_no; // 商户系统内部的订单号
    public String body; // 商品描述
    public String sub_openid; // 微信用户关注商家公众号的openid
    public int total_fee; // 总金额，以分为单位，不允许包含任何字、符号
    public String mch_create_ip; // 订单生成的机器IP
    public String notify_url; // 接收中信银行通知的URL

    // option
    public String version; // 版本号，version 默认值是2.0
    public String charset; // 可选值UTF-8 ，默认为UTF-8
    public String sign_type; // 签名类型，取值：MD5 默认：MD5
    public String sign_agentno; // 如果不为空，则用授权渠道的密钥进行签名
    public String is_raw;
    public String device_info; // 终端设备号
    public String attach; // 商户附加信息，可做扩展参数，255 字符内
    public String callback_url; // 交易完成后跳转的URL
    public String time_start; // 订单生成时间
    public String time_expire; // 订单失效时间
    public String goods_tag; // 商品标记
    public String limit_credit_pay; // 限定用户使用微信支付时能否使用信用卡
}
