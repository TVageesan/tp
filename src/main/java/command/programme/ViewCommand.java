package command.programme;
import command.Command;
import core.Ui;
import programme.ProgrammeList;
import programme.Programme;
import core.History;


public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";
    private final int progId;

    public ViewCommand(int progId) {
        this.progId = progId;
    }

    @Override
    public void execute(Ui ui, ProgrammeList pList, History history){
        Programme programme = pList.getProgramme(progId);
        ui.showMsg("Viewing programme:\n" + programme.toString());
    }
}
