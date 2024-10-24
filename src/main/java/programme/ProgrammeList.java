package programme;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ProgrammeList {

    private static final Logger logger = Logger.getLogger(ProgrammeList.class.getName());

    int currentActiveProgramme;
    private final ArrayList<Programme> programmeList;

    public ProgrammeList() {
        programmeList = new ArrayList<>();
        logger.log(Level.INFO, "ProgrammeList created with an empty list.");
    }

    public ArrayList<Programme> getProgrammeList() {
        return programmeList;
    }

    public int getProgrammeListSize(){
        logger.log(Level.INFO, "Getting programme list size: {0}", programmeList.size());
        return programmeList.size();
    }

    public Programme insertProgramme(String programmeName, ArrayList<Day> days) {
        Programme programmeToAdd = new Programme(programmeName, days);
        programmeList.add(programmeToAdd);
        return programmeToAdd;
    }

    public Programme deleteProgram(int index){
        if (index < 0 || index >= programmeList.size()) {
            logger.log(Level.WARNING, "Invalid index: {0} for deleteProgram()", index);
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for programme list.");
        }

        Programme programmeToDelete = programmeList.get(index);
        programmeList.remove(index);
        logger.log(Level.INFO, "Deleted programme at index {0}: {1}", new Object[]{index, programmeToDelete});
        return programmeToDelete;
    }

    public Programme getProgramme(int index){
        if (index < 0 || index >= programmeList.size()) {
            logger.log(Level.WARNING, "Invalid index: {0} for getProgramme()", index);
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds for programme list.");
        }

        logger.log(Level.INFO, "Retrieving programme at index {0}: {1}", new Object[]{index, programmeList.get(index)});
        return programmeList.get(index);
    }

    public Programme startProgramme(int startIndex) {
        if (startIndex < 0 || startIndex >= programmeList.size()) {
            logger.log(Level.WARNING, "Invalid index: {0} for startProgramme()", startIndex);
            throw new IndexOutOfBoundsException("Index " + startIndex + " is out of bounds for programme list.");
        }

        currentActiveProgramme = startIndex;
        Programme activeProgramme = programmeList.get(currentActiveProgramme);
        logger.log(Level.INFO, "Started programme at index {0}: {1}", new Object[]{startIndex, activeProgramme});
        return activeProgramme;
    }

    public Day getDay(int progIndex, int dayIndex) {
        if (progIndex < 0 || progIndex >= programmeList.size()) {
            logger.log(Level.WARNING, "Invalid programme index: {0} for getDay()", progIndex);
            throw new IndexOutOfBoundsException("Programme index " + progIndex + " is out of bounds.");
        }

        if (dayIndex  == -1) {
            dayIndex = currentActiveProgramme;
        }

        Programme progContent = programmeList.get(progIndex);
        Day day = progContent.getDay(dayIndex);
        logger.log(Level.INFO, "Retrieved day from programme index {0}, day index {1}: {2}",
                new Object[]{progIndex, dayIndex, day});

        return day;
    }

    public Day deleteDay(int progIndex, int dayIndex) {
        if (progIndex < 0 || progIndex >= programmeList.size()) {
            logger.log(Level.WARNING, "Invalid programme index: {0} for deleteDay()", progIndex);
            throw new IndexOutOfBoundsException("Programme index " + progIndex + " is out of bounds.");
        }
        Day deletedDay = programmeList.get(progIndex).deleteDay(dayIndex);
        logger.log(Level.INFO, "Deleted day from programme index {0}, day index {1}: {2}",
                new Object[]{progIndex, dayIndex, deletedDay});
        return deletedDay;
    }

    public void insertDay(int progIndex, Day day) {
        if (progIndex < 0 || progIndex >= programmeList.size()) {
            logger.log(Level.WARNING, "Invalid programme index: {0} for insertDay()", progIndex);
            throw new IndexOutOfBoundsException("Programme index " + progIndex + " is out of bounds.");
        }

        assert day != null : "Day to insert cannot be null";

        programmeList.get(progIndex).insertDay(day);
        logger.log(Level.INFO, "Inserted day into programme index {0}: {1}", new Object[]{progIndex, day});
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < programmeList.size(); i++) {
            Programme programme = programmeList.get(i);
            if (i == currentActiveProgramme) {
                str.append("*Active* ");
            }
            str.append(programme).append("\n");
        }
        return str.toString();
    }

    public JsonObject toJson() {
        Gson gson = new Gson();
        logger.log(Level.INFO, "Programme list converted to Json for saving.");
        return gson.toJsonTree(this).getAsJsonObject();
    }

    public static ProgrammeList fromJson(JsonObject jsonObject) {
        Gson gson = new Gson();
        logger.log(Level.INFO, "Programme list converted from Json for loading.");
        return gson.fromJson(jsonObject, ProgrammeList.class);
    }
}
