package example.display;

import example.TerminalDisplayObject;

import java.io.IOException;
import java.util.*;

public class DisplayBlock implements TerminalDisplayObject {

    // public static values to simplify text formatting
    //
    public static final String RESET = "\u001B[0m";
    // UI elements that might be commonly used
    public static final String BAR = "***************************";
    public static final String BAR_THICK = "////////////////////////////";
    public static final String LINE1 = "---------------------------";
    public static final String Line2 = "____________________________";
    public static final String Line3 = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~";

    // These change the color of the terminal text
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    // These change the color of the terminal background
    public static final String BACKGROUND_BLACK = "\u001B[40m";
    public static final String BACKGROUND_RED = "\u001B[41m";
    public static final String BACKGROUND_GREEN = "\u001B[42m";
    public static final String BACKGROUND_YELLOW = "\u001B[43m";
    public static final String BACKGROUND_BLUE = "\u001B[44m";
    public static final String BACKGROUND_MAGENTA = "\u001B[45m";
    public static final String BACKGROUND_CYAN = "\u001B[46m";
    public static final String BACKGROUND_WHITE = "\u001B[47m";

    // These are for effect

    // These are common UI elements that might be used as a prompt
    public static final String ARROW_PROMPT = " >> ";
    private String os = null;

    // actual private properties for each window
    //
    // This stores each individual line to render on the terminal as a string
    private List<String> skeleton = new ArrayList<>();
    private List<String> display = new ArrayList<>();

    // This is used to store state that can change throughout the program wihtout having to reform the view of the skeleton
    private Map<String, String> state = new HashMap<>();


    //private PrintStream outputStream = new PrintStream(System.out);

    // this method clears the terminal screen
    public void clearScreen() {
        try {
            // grabs the os we're using if os has not been initialized
            if (os == null) {
                // this should grab os name
                os = System.getProperty("os.name");
            }

            // checks the operating system of the system we're running on
            if (os.contains("Window") || os.contains("window")) {
                Runtime.getRuntime().exec("cls");
            }
            else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (IOException e) {
            System.out.println("");
            System.out.println(BAR + RED);
            System.out.println("FAILED TO CLEAR SCREEN");
            System.out.println(RESET + BAR);
            System.out.println("");
        }
    }

    public void renderToDisplay() {
        Scanner input = new Scanner(System.in);
        clearScreen();
        // start by clearing screen for a fresh start
        for(String line: display) {
            System.out.println(line);
        }
    }

    public void appendToLastLineInDisplay(String item) {
        // check for state
        List<StateEntry> state = findStateLocation(item);
        for (StateEntry entry : state) {
            this.state.put(entry.getKey(), "");
        }
        skeleton.set(skeleton.size() - 1, skeleton.get(skeleton.size()-1) + item);
        display.set(display.size() - 1, skeleton.get(display.size()-1));
    }

    public void appendToDisplay(String item) {
        // check for state
        List<StateEntry> state = findStateLocation(item);
        for (StateEntry entry : state) {
            this.state.put(entry.getKey(), "");
        }
        skeleton.add(item);
        display.add(item);
    }

    public void clearDisplay() {
        skeleton.clear();
        this.clearScreen();
    }

    public boolean addState(String identifier, Object object) {
        try {
            this.state.put(identifier, object.toString());
            // System.out.println(this.state.get(identifier));
            // we need to find where this key is used, change the skeleton accordingly, then rerender
            adjustDisplay(identifier);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean removeState(String identifier) {
        try {
            this.state.remove(identifier);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean removeState(String... identifiers) {
        try {
            Map<String, String> state = this.state;
            for (String identifier : identifiers) {
                state.remove(identifier);
            }
            this.state = state;
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public String getState(String identifier) {;
        return this.state.get(identifier);
    }

    public String[] getState(String... indentifiers) {
        String[] state = new String[indentifiers.length];
        int i = 0;
        for (String identifier : indentifiers) {
            state[i] = identifier;
            i++;
        }
        return state;
    }

    public Object[] getState() {
        return this.state.values().toArray();
    }

    private List<StateEntry> findStateLocation(String str) {
        int index = str.indexOf('$') + 1;
        boolean bracketOpen = false;
        List<StateEntry> stateFound = new ArrayList<>();
        StateEntry state = new StateEntry();

        while (index < str.length() && index > 0) {
            if (str.charAt(index) == '{' && !bracketOpen) {
                bracketOpen = true;
                state.start = index + 1;
                index++;
            } else if (str.charAt(index) == '}' && bracketOpen) {
                bracketOpen = false;
                state.end = index;
                state.key = (str.substring(state.start, state.end));
                stateFound.add(state);
                state = new StateEntry();
                str = str.substring(index);
                index = str.indexOf('$') + 1;
            } else {
                index++;
            }
        }
        return stateFound;
    }

    private void adjustDisplay(String key) {
        List<StateEntry> state = new ArrayList<>();
//        List<String> updatedDisplay = skeleton;
        String newLine = "";
        for (int i=0; i<skeleton.size(); i++) {
            newLine = skeleton.get(i);
            state = findStateLocation(newLine);
            for (StateEntry entry : state) {
                newLine = newLine.replace("${" + entry.getKey() + "}", this.state.get(entry.getKey()));
            }
            display.set(i, newLine);
        }
    }

    private class StateEntry {
        int start;
        int end;
        private String key;

        StateEntry() {
            this.key = null;
            this.start = 0;
            this.end = 0;
        }

        public String getKey() {
            return key;
        }

        public int getStart() {
            return start;
        }

        public void setStart(int start) {
            this.start = start;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public boolean setKey(String key) {
            if (key != null) {
                this.key = key;
                return true;
            }
            return false;
        }
    }


    public List<String> getDisplay() {
        return display;
    }
}
