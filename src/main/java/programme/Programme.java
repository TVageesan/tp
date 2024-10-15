package programme;

import java.util.ArrayList;

public class Programme {
    private final String programmeName;
    private final ArrayList<Day> dayList;

    public Programme(String programmeName, ArrayList<Day> dayList) {
        this.programmeName = programmeName;
        this.dayList = dayList;
    }

    public Day getDay(int index) {
        return dayList.get(index);
    }

    public void insertDay(Day day) {
        dayList.add(day);
    }

    public Day deleteDay(int index){
        if (dayList.size() < index){
            System.out.println("invalid index");
        }
        Day toBeDeleted = dayList.get(index);
        dayList.remove(index);
        return toBeDeleted;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(programmeName).append("\n\n");

        for (int i = 0; i < dayList.size(); i++) {
            str.append("Day ").append(i+1).append(": ").append(dayList.get(i));
        }

        return str.toString();
    }
}
