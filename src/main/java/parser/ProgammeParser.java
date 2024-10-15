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

import static parser.ParserUtils.parseIndex;

public class ProgammeParser {

    public static final String COMMAND_WORD = "prog";

    public Command parse(String argumentString) {
        String[] inputArguments = argumentString.split(" ", 2);

        String subCommandString = inputArguments[0];
        String arguments = "";

        if (inputArguments.length > 1 ){
            arguments = inputArguments[1];
        }

        return switch (subCommandString) {
        case CreateCommand.COMMAND_WORD -> prepareCreateCommand(arguments);
        case ViewCommand.COMMAND_WORD -> prepareViewCommand(arguments);
        case ListCommand.COMMAND_WORD -> new ListCommand();
        case EditCommand.COMMAND_WORD -> prepareEditCommand(arguments);
        case StartCommand.COMMAND_WORD -> prepareStartCommand(arguments);
        default -> new InvalidCommand();
        };
    }

    private Command prepareCreateCommand(String argumentString) {
        String[] progParts = argumentString.split("/d");
        String progName = progParts[0].trim();
        ArrayList<Day> days = new ArrayList<>();

        for (int i = 1; i < progParts.length; i++) {
            String dayString = progParts[i].trim();
            String[] dayParts  = dayString.split("/e");
            String dayName = dayParts[0].trim();

            Day day = new Day(dayName);

            for (int j = 1; j < dayParts.length; j++) {
                String exerciseString = dayParts[j].trim();
                Exercise exercise = parseExercise(exerciseString);
                day.insertExercise(exercise);
            }

            days.add(day);
        }

        return new CreateCommand(progName, days);
    }

    private Command prepareEditCommand(String argumentString) {
        // Regex: Split string by / except when followed by n, r, s, w
        String[] args = argumentString.split("/(?![nrsw])");
        EditCommand editCommand = new EditCommand();

        int progIndex = -1;
        int dayIndex = -1;
        int exerciseIndex = -1;

        for (String arg : args) {
            if (arg.trim().isEmpty()) {
                continue;
            }

            String[] argParts = arg.trim().split(" ", 2);
            String flag = argParts[0].trim();
            String value = argParts.length > 1 ? argParts[1].trim() : "";

            switch (flag) {
            case "p":
                progIndex = parseIndex(value);
                break;

            case "d": // Day index
                dayIndex = parseIndex(value);
                break;

            case "x": // Remove exercise at index
                exerciseIndex = parseIndex(value);
                editCommand.addDelete(progIndex, dayIndex, exerciseIndex);
                break;

            case "xd":
                editCommand.addDeleteDay(progIndex, dayIndex);
                break;

            case "u": // Update exercise (parse the value string to create an Exercise)
                String[] updateParts = value.split(" ", 2);
                exerciseIndex = parseIndex(updateParts[0]);
                Exercise updated = parseExercise(updateParts[1]);
                editCommand.addEdit(progIndex, dayIndex, exerciseIndex, updated);
                break;

            case "a": // Add new exercise (parse the value string to create an Exercise)
                Exercise created = parseExercise(value);
                editCommand.addCreate(progIndex, dayIndex, created);
                break;

            case "ad":
                Day day = parseDay(value);
                editCommand.addCreateDay(progIndex, day);
                break;

            default:
                System.out.println("Unknown flag: " + flag);
                break;
            }
        }

        return editCommand;
    }

    private Exercise parseExercise(String exerciseString) {
        String name = "";
        int reps = -1;
        int sets = -1;
        int weight = -1;

        String[] args = exerciseString.trim().split("/(?)");

        for (int i = 1; i < args.length; i++) {

            String[] argParts = args[i].split(" ");

            if (argParts.length != 2){
                throw new IllegalArgumentException("Invalid create exercise command: " + args[i]);
            }

            String flag = argParts[0].trim();
            String value = argParts[1].trim();

            switch (flag) {
            case "n":
                name = value;
                break;
            case "s":
                sets = Integer.parseInt(value);
                break;
            case "r":
                reps = Integer.parseInt(value);
                break;
            case "w":
                weight = Integer.parseInt(value);
                break;
            default:
                throw new IllegalArgumentException("Invalid command flag " + flag);
            }
        }

        return new Exercise(sets, reps, weight, name);
    }


    private Command prepareViewCommand(String argumentString) {
        int progIndex = parseIndex(argumentString);
        return new ViewCommand(progIndex);
    }

    private Command prepareStartCommand(String argumentString) {
        int progIndex = parseIndex(argumentString);
        return new StartCommand(progIndex);
    }

    private Command prepareDeleteCommand(String argumentString){
        int progIndex = parseIndex(argumentString);
        return new DeleteCommand(progIndex);
    }
}
