package com.techelevator;

import org.junit.Test;
import techelevator.Response;
import techelevator.Terminal_UI;

import static org.junit.Assert.assertEquals;


public class Terminal_Ui_Tests {
    @Test
    public void testDisplayAppendLine(){
        Terminal_UI ui = new Terminal_UI();
        String[] messages = {"testing display", ", eating the frog", "want a mint?", ", nah, I like my breath to stink", "when will pathway start", ", in about 50 minutes"};
        int ui_index = 0;
        for(int i=1; i<messages.length; i+=2){
            ui.appendToDisplay(messages[i-1]);
            ui.appendToLastLineInDisplay(messages[i]);
            assertEquals(messages[i-1] + messages[i], ui.getDisplay().get(ui_index));
            ui_index++;
        }
    }

    @Test
    public void testDisplayCreatesNewState(){
        Terminal_UI ui = new Terminal_UI();
        String[] messages = {"Have you eaten yet ${state0}", "${state1}", "eating the ${state2}", "testing ${state3}"};
        String[] expected = {"Jerry", "SUCCESS!", "frog", "display"};
        for(int i=0; i<messages.length; i++){
            ui.appendToDisplay(messages[i]);
            assertEquals("", ui.getState("state" + i));

            ui.addState("state" + i, expected[i]);
            assertEquals(expected[i], ui.getState("state" + i));
        }
    }

    @Test
    public void testDisplayWithMultiStateLines(){
        Terminal_UI ui = new Terminal_UI();

        String[] messages = {"${name} is eating the ${animal}", "why do ${animal2} never ${action}", "my ${person} has ${number} dollars", "the ${adj1}, ${adj2} ${animal3} does not like to ${verb} ${food}"};
        String[][] names = {{"name", "animal"}, {"animal2", "action"}, {"person", "number"}, {"adj1", "adj2", "animal3", "verb", "food"}};
        String[][] state = {{"Jerry", "frog"}, {"elephants", "forget"}, {"mother", "20"}, {"big", "black", "bear", "eat", "honey"}};

        for(int i=0; i<messages.length; i++){
            //System.out.println("testing message: " + messages[i]);
            ui.appendToDisplay(messages[i]);
            for(String name: names[i]){
                assertEquals("", ui.getState(name));
            }
            //System.out.println("Testing added state " + state[i]);
            for(int j=0; j<state[i].length; j++){
                //System.out.println("case: " + state[i][j]);
                ui.addState(names[i][j], state[i][j]);
                assertEquals(state[i][j], ui.getState(names[i][j]));
            }
        }
    }
}
