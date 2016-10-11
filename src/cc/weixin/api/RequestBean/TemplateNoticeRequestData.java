package cc.weixin.api.RequestBean;

import framework.utils.I18n;

public class TemplateNoticeRequestData {
    public TemplateNoticeRequestData() {
        url = "";
        topcolor = "#FF0F0F";
    }

    public String buildRequestData() {
        if (touser == null || touser.isEmpty() || template_id == null || template_id.isEmpty()) {
            return "";
        }

        String templateMessage = I18n.GetText("TemplateNotice");
        System.out.println(templateMessage);
        String reqeustData = String.format(templateMessage,
                touser,
                template_id,
                topcolor,
                title, "#173177",
                timeArea, "#173177",
                notice, "#173177",
                editer, "#173177",
                remark, "#173177");
        return reqeustData;
    }

    public String touser;
    public String template_id;
    public String url;
    public String topcolor;
    public String timeArea;
    public String notice;
    public String editer;
    public String remark;
    public String title;
}
