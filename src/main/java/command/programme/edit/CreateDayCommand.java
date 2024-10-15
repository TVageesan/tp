package command.programme.edit;
import programme.Day;
import programme.ProgrammeList;

public class CreateDayCommand  extends EditSubCommand{
    private final Day created;

    public CreateDayCommand(int progId, Day day) {
        super(progId);
        created = day;
    }

    @Override
    public String execute(ProgrammeList pList) {
        pList.insertDay(progId, created);
        return String.format("Created: %s%n",created.toString());
    }
}
