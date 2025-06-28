package test.util;

import exceptions.EmptyItemNameException;
import exceptions.InputEmptyException;
import org.junit.jupiter.api.Test;
import util.UserPrompts;

import java.util.InputMismatchException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class UserPromptsTest {

    @Test
    public void testUserPrompt(){
        Scanner scanner = new Scanner("Test\n");
        UserPrompts userPrompts = new UserPrompts(scanner);

        String input = userPrompts.userPrompt("Enter: ");

        assertEquals("Test",input);
    }

    @Test
    public void shouldThrowExceptionWhenNothingIsEntered(){
        Scanner scanner = new Scanner("\n");
        UserPrompts userPrompts = new UserPrompts(scanner);

        assertThrows(InputEmptyException.class,()->{
            String input = userPrompts.userPrompt("Enter: ");
        });
    }

    @Test
    public void testUserIntegerPrompt(){
        Scanner scanner = new Scanner("42\n");
        UserPrompts userPrompts = new UserPrompts(scanner);

        int input = userPrompts.userIntegerPrompt("Enter: ");

        assertEquals(42,input);
    }

    @Test
    public void shouldThrowExceptionWhenNumberIsZeroOrLower(){
        Scanner scanner = new Scanner("0\n");
        UserPrompts userPrompts = new UserPrompts(scanner);

        assertThrows(InputEmptyException.class, ()->{
            userPrompts.userIntegerPrompt("Enter: ");
        });
    }

    @Test
    public void testUserDoublePrompt(){
        Scanner scanner = new Scanner("14.22\n");
        UserPrompts userPrompts = new UserPrompts(scanner);

        double input = userPrompts.userDoublePrompt("Enter: ");

        assertEquals(14.22,input);
    }

    @Test
    public void shouldThrowExceptionWhenUserEnterInvalidDouble(){

        Scanner scanner = new Scanner("bad\n");
        UserPrompts userPrompts = new UserPrompts(scanner);

        assertThrows(InputMismatchException.class, ()->{
            userPrompts.userDoublePrompt("Enter: ");
        });

    }
}
