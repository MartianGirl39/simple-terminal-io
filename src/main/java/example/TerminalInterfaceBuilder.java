package example;

import example.menu.BaseMenu;
import example.menu.Menu;

import java.util.LinkedList;
import java.util.List;

public class TerminalInterfaceBuilder {
    private BaseMenu root;

    public TerminalInterfaceBuilder(){
        root = new Menu("Welcome to our app", "q");
    }

    public void setRoot(BaseMenu menu){
        root = menu;
    }

    public void run(){
        this.root.renderToDisplay();
    }

}
