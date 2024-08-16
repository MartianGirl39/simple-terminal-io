package example.menu.options;

@FunctionalInterface
public interface OptionResponseFunction {
    public boolean respond(String userInput);
}

