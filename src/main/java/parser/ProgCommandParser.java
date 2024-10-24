package parser;

import command.Command;
import command.InvalidCommand;
import programme.Day;
import programme.Exercise;

import command.programme.CreateCommand;
import command.programme.ViewCommand;
import command.programme.ListCommand;
import command.programme.StartCommand;
import command.programme.EditCommand;
import command.programme.DeleteCommand;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import static parser.ParserUtils.parseDay;
import static parser.ParserUtils.parseExercise;
import static parser.ParserUtils.parseIndex;

public class ProgCommandParser {
    public static final String COMMAND_WORD = "prog";
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public Command parse(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        String[] inputArguments = argumentString.split(" ", 2);

        String subCommandString = inputArguments[0];
        String arguments = "";

        if (inputArguments.length > 1 ){
            arguments = inputArguments[1];
        }

        logger.log(Level.INFO, "Parsed sub-command: {0}, with arguments: {1}",
                new Object[]{subCommandString, arguments});

        return switch (subCommandString) {
        case CreateCommand.COMMAND_WORD -> prepareCreateCommand(arguments);
        case ViewCommand.COMMAND_WORD -> prepareViewCommand(arguments);
        case ListCommand.COMMAND_WORD -> new ListCommand();
        case EditCommand.COMMAND_WORD -> prepareEditCommand(arguments);
        case StartCommand.COMMAND_WORD -> prepareStartCommand(arguments);
        case DeleteCommand.COMMAND_WORD ->  prepareDeleteCommand(arguments);
        default -> new InvalidCommand();
        };
    }

    private Command prepareEditCommand(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        FlagParser flagParser = new FlagParser(argumentString);
        EditCommand editCommand = new EditCommand();

        int progIndex = -1;
        int dayIndex = -1;

        if (flagParser.hasFlag("/p")) {
            progIndex = parseIndex(flagParser.getFlagValue("/p"), "Invalid programme index.");
        }

        if (flagParser.hasFlag("/d")) {
            dayIndex = parseIndex(flagParser.getFlagValue("/d"), "Invalid day index.");
        } else if (flagParser.hasFlag("/xd")) {
            dayIndex = parseIndex(flagParser.getFlagValue("/xd"), "Invalid day index in /xd flag.");
        }

        if (flagParser.hasFlag("/ad")) {
            Day day = parseDay(flagParser.getFlagValue("/ad"));
            editCommand.addCreateDay(progIndex, day);
        }

        if (flagParser.hasFlag("/a")) {
            Exercise created = parseExercise(flagParser.getFlagValue("/a"));
            editCommand.addCreateExercise(progIndex, dayIndex, created);
        }

        if (flagParser.hasFlag("/xd")) {
            editCommand.addDeleteDay(progIndex, dayIndex);
        }

        if (flagParser.hasFlag("/x")) {
            int exerciseIndex = parseIndex(flagParser.getFlagValue("/x"), "Invalid exercise index for deletion.");
            editCommand.addDeleteExercise(progIndex, dayIndex, exerciseIndex);
        }

        if (flagParser.hasFlag("/u")) {
            String[] updateParts = flagParser.getFlagValue("/u").split(" ", 2);
            int exerciseIndex = parseIndex(updateParts[0], "Invalid exercise index for update.");
            Exercise updated = parseExercise(updateParts[1]);
            editCommand.addEditExercise(progIndex, dayIndex, exerciseIndex, updated);
        }

        logger.log(Level.INFO, "EditCommand prepared successfully");
        return editCommand;
    }

    private Command prepareCreateCommand(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        ArrayList<Day> days = new ArrayList<>();
        String[] progParts = argumentString.split("/d");
        String progName = progParts[0].trim();
        if (progName.isEmpty()) {
            throw new IllegalArgumentException("Programme name cannot be empty. Please enter a name.");
        }

        for (int i = 1; i < progParts.length; i++) {
            String dayString = progParts[i].trim();
            Day day = parseDay(dayString);
            days.add(day);
        }

        logger.log(Level.INFO, "CreateCommand prepared with programme: {0}", progName);
        return new CreateCommand(progName, days);
    }

    private Command prepareViewCommand(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        int progIndex = parseIndex(argumentString, "Invalid programme index. ");

        logger.log(Level.INFO, "ViewCommand prepared successfully");
        return new ViewCommand(progIndex);
    }

    private Command prepareStartCommand(String argumentString) {
        assert argumentString != null : "Argument string must not be null";

        int progIndex = parseIndex(argumentString, "Invalid programme index. ");

        logger.log(Level.INFO, "StartCommand prepared successfully");
        return new StartCommand(progIndex);
    }

    private Command prepareDeleteCommand(String argumentString){
        assert argumentString != null : "Argument string must not be null";

        int progIndex = parseIndex(argumentString, "Invalid programme index. ");

        logger.log(Level.INFO, "DeleteCommand prepared successfully");
        return new DeleteCommand(progIndex);
    }
}

