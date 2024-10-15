package command.programme.edit;

import programme.Day;
import programme.Exercise;
import programme.ProgrammeList;

public class DeleteExerciseCommand  extends EditSubCommand {

    private final int exerciseId;

    public DeleteExerciseCommand(int progId, int dayId, int exerciseId) {
        super(progId, dayId);
        this.exerciseId = exerciseId;
    }

    public String execute(ProgrammeList pList) {
        Day day = pList.getDay(progId, dayId);
        Exercise deleted = day.deleteExercise(exerciseId);
        return String.format("Deleted exercise %d: %s%n", exerciseId, deleted);
    }
}
