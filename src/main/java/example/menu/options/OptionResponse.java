package example.menu.options;

public class OptionResponse {
    String actionName;
    OptionResponseFunction response;

    public OptionResponse(String actionName, OptionResponseFunction lambda){
        this.actionName = actionName;
        this.response = lambda;
    }

    public String getActionName() {
        return actionName;
    }

    public OptionResponseFunction getResponse() {
        return response;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}
