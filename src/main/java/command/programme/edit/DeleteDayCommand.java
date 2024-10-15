package command.programme.edit;

import programme.Day;
import programme.ProgrammeList;

public class DeleteDayCommand  extends EditSubCommand {


    public DeleteDayCommand(int progId, int dayId) {
        super(progId, dayId);
    }

    public String execute(ProgrammeList pList) {
        Day deleted = pList.deleteDay(progId, dayId);
        return String.format("Deleted day: %s%n", deleted);
    }
}
