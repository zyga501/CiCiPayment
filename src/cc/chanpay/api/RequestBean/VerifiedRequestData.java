package cc.chanpay.api.RequestBean;

import cc.ProjectSettings;
import framework.utils.IdWorker;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class VerifiedRequestData extends RequestData {
    public VerifiedRequestData() {
        trxCode = "G60001";
        accountType = "00";
        sn = String.valueOf(new IdWorker(ProjectSettings.getIdWorkerSeed()));
    }

    public boolean checkParameter() {
        if (!super.checkParameter()) {
            return false;
        }

        try {
            return !bankGeneralName.isEmpty()
                    && !accountType.isEmpty()
                    && !accountNo.isEmpty()
                    && !accountName.isEmpty()
                    && !idType.isEmpty()
                    && !id.isEmpty();
        }
        catch (Exception exception) {

        }

        return false;
    }

    protected Element buildElement() {
        Document doc = DocumentHelper.createDocument();
        Element message = doc.addElement("MESSAGE");
        message.add(super.buildElement());

        Element body = message.addElement("BODY");
        Element details = body.addElement("TRANS_DETAILS");
        Element dtl = details.addElement("DTL");
        dtl.addElement("SN").setText(sn);
        dtl.addElement("BANK_GENERAL_NAME").setText(bankGeneralName);
        if (bankName != null) {
            dtl.addElement("BANK_NAME").setText(bankName);
        }
        if (bankCode != null) {
            dtl.addElement("BANK_CODE").setText(bankCode);
        }
        dtl.addElement("ACCOUNT_TYPE").setText(accountType);
        dtl.addElement("ACCOUNT_NO").setText(accountNo);
        dtl.addElement("ACCOUNT_NAME").setText(accountName);
        dtl.addElement("ID_TYPE").setText(idType);
        dtl.addElement("ID").setText(id);
        if (tel != null) {
            dtl.addElement("TEL").setText(tel);
        }

        return doc.getRootElement();
    }

    public String sn;//明细号
    public String bankGeneralName;//银行通用名称
    public String bankName;//开户行名称
    public String bankCode;//开户行号
    public String accountType;//账号类型
    public String accountNo;//账号
    public String accountName;//账户名称
    public String idType;//开户证件类型
    public String id;//证件号
    public String tel;//手机号
}
