package parser;

import command.Command;
import command.ExitCommand;
import command.HistoryCommand;
import command.LogCommand;
import command.InvalidCommand;


import static parser.ParserUtils.parseIndex;


public class Parser {
    private ProgammeParser progParser;

    public Parser(){
        this.progParser = new ProgammeParser();
    }

    public Command parse(String fullCommand) {
        String[] inputArguments = fullCommand.trim().split(" ", 2);

        String commandString = inputArguments[0];
        String argumentString = "";

        if (inputArguments.length > 1 ){
            argumentString = inputArguments[1];
        }

        switch (commandString) {
        case ProgammeParser.COMMAND_WORD: return progParser.parse(argumentString);
        case LogCommand.COMMAND_WORD: return prepareLogCommand(argumentString);
        case HistoryCommand.COMMAND_WORD: return new HistoryCommand();
        case ExitCommand.COMMAND_WORD: return new ExitCommand();
        default: return new InvalidCommand();
        }
    }

    private Command prepareLogCommand(String argumentString){
        String[] arguments = ParserUtils.parseArguments(argumentString, "/p", "/d");

        if (arguments.length != 3) {
            throw new IllegalArgumentException("Invalid event command. " +
                    "Please provide a programme index, day index, and date using '/p' and '/d' and 'DATE'.");
        }

        int progIndex = parseIndex(arguments[0].trim());
        int dayIndex = parseIndex(arguments[1].trim());

        String date = arguments[2].trim();

        return new LogCommand(progIndex, dayIndex, date);
    }
}
