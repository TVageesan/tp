package command.programme;
import command.Command;

import command.programme.edit.CreateDayCommand;
import command.programme.edit.DeleteDayCommand;
import command.programme.edit.EditSubCommand;
import command.programme.edit.DeleteExerciseCommand;
import command.programme.edit.EditExerciseCommand;
import command.programme.edit.CreateExerciseCommand;
import core.Ui;
import programme.Exercise;
import programme.Day;
import programme.ProgrammeList;
import core.History;

import java.util.ArrayList;

public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    private final ArrayList<EditSubCommand> subCommands;

    public EditCommand(){
        subCommands = new ArrayList<>();
    }

    public void addCreate(int progId, int dayId, Exercise created){
        CreateExerciseCommand c = new CreateExerciseCommand(progId, dayId, created);
        subCommands.add(c);
    }

    public void addDelete(int progId, int dayId, int exerciseId){
        DeleteExerciseCommand c = new DeleteExerciseCommand(progId, dayId, exerciseId);
        subCommands.add(c);
    }

    public void addEdit(int progId, int dayId, int exerciseId, Exercise updated){
        EditExerciseCommand c = new EditExerciseCommand(progId, dayId, exerciseId, updated);
        subCommands.add(c);
    }

    public void addCreateDay(int progId, Day day){
        CreateDayCommand c = new CreateDayCommand(progId, day);
        subCommands.add(c);
    }

    public void addDeleteDay(int progId, int dayId){
        DeleteDayCommand c = new DeleteDayCommand(progId, dayId);
        subCommands.add(c);
    }

    @Override
    public void execute(Ui ui, ProgrammeList pList, History history){
        for (EditSubCommand c : subCommands){
            String result =  c.execute(pList);
            ui.showMsg(result);
        }
    }
}
