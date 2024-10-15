package command.programme.edit;


import programme.Day;
import programme.Exercise;
import programme.ProgrammeList;

public class EditExerciseCommand  extends EditSubCommand {

    private final int exerciseId;
    private final Exercise update;

    public EditExerciseCommand(int progId, int dayId, int exerciseId, Exercise update) {
        super(progId,dayId);
        this.exerciseId = exerciseId;
        this.update = update;
    }

    public String execute(ProgrammeList pList) {
        Day day = pList.getDay(progId, dayId);
        Exercise updated = day.updateExercise(exerciseId, update);
        return String.format("Update exercise %d to: %s%n",exerciseId, updated);
    }
}
