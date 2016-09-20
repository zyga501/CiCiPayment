package cc.swiftpass.action;

import framework.action.AjaxActionSupport;

public class PayAction extends AjaxActionSupport {
    public String jsPay() {
        return AjaxActionComplete(false);
    }
}
