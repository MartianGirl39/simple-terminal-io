package example.menu;

import com.sun.source.tree.LabeledStatementTree;
import example.TerminalDisplayObject;
import example.menu.options.OptionResponse;
import example.menu.options.OptionResponseFunction;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseMenu implements TerminalDisplayObject {
    Map<String, OptionResponse> options = new HashMap<>();
    private String exitOption;
    private String menuName;
    private String invalidMessage;

    BaseMenu(String menuName, String exitOption) {
        this.menuName = menuName;
        this.exitOption = exitOption;
        this.invalidMessage = "Invalid Input";
    }

    public void putOption(String option, String description, OptionResponseFunction response){
        options.putIfAbsent(option, new OptionResponse(description, response));
    }

    public String getExitOption() {
        return exitOption;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getInvalidMessage() {
        return invalidMessage;
    }

    public void setInvalidMessage(String invalidMessage) {
        this.invalidMessage = invalidMessage;
    }

    public abstract void renderToDisplay();
    abstract void promptUser();
}
