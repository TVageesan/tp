package core;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Represents the user interface for the task management system.
 * This class handles user input and output, providing methods to read commands,
 * display messages, and show welcome or farewell messages.
 */
public class Ui {
    private static final String ERROR_HEADER = "Error: ";

    private static final String LINE_CHAR = "=";
    private static final int LINE_LENGTH = 50;

    private static final String GREETING = "Hello! I'm...";
    private static final String LOGO = "BUFFBUDDY";
    private static final String PROMPT = "What can I do for you?";

    private static final String FAREWELL ="Bye. Hope to see you again soon!";

    private Scanner in;
    private PrintStream out;

    /**
     * Constructs an Ui object, initializing the input and output streams.
     */
    public Ui() {
        in = new Scanner(System.in);
        out = new PrintStream(System.out);
    }

    /**
     * Reads a command input from the user.
     *
     * @return the input command as a string
     */
    public String readCommand() {
        return in.nextLine();
    }

    /**
     * Displays a line for visual separation in the output.
     */
    public void showLine() {
        out.println(LINE_CHAR.repeat(LINE_LENGTH));
    }

    /**
     * Displays an error message to the user.
     *
     * @param e the exception to be displayed
     */
    public void showError(Exception e) {
        out.println(ERROR_HEADER + e.getMessage());
    }

    /**
     * Displays a message to the user.
     *
     * @param msg the message to be displayed
     */
    public void showMsg(String msg) {
        out.println(msg);
    }

    /**
     * Displays a welcome message to the user.
     */
    public void showWelcome() {
        out.println(GREETING);
        out.println(LOGO);
        out.println(PROMPT);
    }

    /**
     * Displays a farewell message to the user.
     */
    public void showFarewell() {
        out.println(FAREWELL);
    }
}
