package programme;

import java.util.ArrayList;

public class Day {
    private final String name;
    private final ArrayList<Exercise> exercises;

    public Day(String name, ArrayList<Exercise> exercises) {
        this.name = name;
        this.exercises = exercises;
    }

    public Day(String name){
        this.name = name;
        this.exercises = new ArrayList<>();
    }

    public Exercise updateExercise(int exerciseId, Exercise exercise){
        Exercise toBeUpdated = exercises.get(exerciseId);
        toBeUpdated.updateExercise(exercise);
        exercises.set(exerciseId, toBeUpdated);
        return toBeUpdated;
    }

    public void insertExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    public Exercise deleteExercise(int index) {
        Exercise toBeDeleted = exercises.get(index);
        exercises.remove(index);
        return toBeDeleted;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(name).append("\n\n");

        for (int i = 0; i < exercises.size(); i++) {
            Exercise exercise = exercises.get(i);
            result.append(String.format("%d. %s%n", i + 1, exercise));
        }

        return result.append("\n").toString();
    }

}
