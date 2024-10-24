import core.CommandHandler;
import core.DataManager;

import history.History;
import ui.Ui;
import programme.ProgrammeList;

public class BuffBuddy {
    private static final String DEFAULT_FILE_PATH = "./data/data.json";
    private final Ui ui;

    private final History history;
    private final ProgrammeList programmes;
    private final DataManager dataManager;
    private final CommandHandler commandHandler;


    public BuffBuddy(String filePath) {
        ui = new Ui();
        dataManager = new DataManager(filePath);
        programmes = dataManager.loadProgrammeList();
        history = dataManager.loadHistory();
        commandHandler = new CommandHandler();
    }

    public static void main(String[] args) {
        new BuffBuddy(DEFAULT_FILE_PATH).run();
    }

    public void run() {
        ui.showWelcome();
        commandHandler.run(ui, programmes, history);
        ui.showFarewell();
        dataManager.saveData(programmes, history);
    }
}
