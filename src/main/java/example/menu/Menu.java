package example.menu;

import example.TerminalDisplayObject;
import example.menu.options.OptionResponse;
import example.menu.options.OptionResponseFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu extends BaseMenu {
    public Menu(String menuName, String exitOption){
        super(menuName, exitOption);
    }

    public void renderToDisplay(){
        Scanner input = new Scanner(System.in);
        boolean stay = true;
        String choice;

        while(stay) {
            promptUser();
            System.out.println("Enter value >> ");
            choice = input.nextLine().strip();

            if(options.containsKey(choice)){
                stay = options.get(choice).getResponse().respond(choice);
            }
            else if(choice.equals(super.getExitOption())){
                stay = false;
            }
            else {
                System.out.println("\u001B[1;91m;" + super.getInvalidMessage() + "\u001B[0m;");
            }
        }
    }

    void promptUser(){
        System.out.println("***" + super.getMenuName() + "***");
        for(Map.Entry<String, OptionResponse> opt : options.entrySet()){
            System.out.println("Enter " + opt.getKey() + " to " + opt.getValue().getActionName().toLowerCase());
        }
        System.out.println("Enter" + super.getExitOption() + "to exit menu");
    }
}
