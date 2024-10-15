package command.programme.edit;

import programme.Day;
import programme.Exercise;
import programme.ProgrammeList;

public class CreateExerciseCommand  extends EditSubCommand {

    private final Exercise created;

    public CreateExerciseCommand(int progId, int dayId, Exercise created) {
        super(progId,dayId);
        this.created = created;
    }

    public String execute(ProgrammeList pList) {
        Day day = pList.getDay(progId, dayId);
        day.insertExercise(created);
        return String.format("Created new exercise: %s%n", created);
    }
}
