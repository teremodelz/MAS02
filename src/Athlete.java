import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Athlete implements Serializable {
    private String name;
    private SwimmingStyles crownStyle;
    private List<AthleteTraining> athleteTrainings = new ArrayList<>();

    public Athlete(String name, SwimmingStyles crownStyle) {
        if(name == null || name.isBlank()) throw new IllegalArgumentException("Name can't be null or blank.");
        if(crownStyle==null) throw new IllegalArgumentException("Crown style can't be null.");
        this.name = name;
        this.crownStyle = crownStyle;
    }

    public void addAthleteTrainings(AthleteTraining athleteTraining){
        if(athleteTrainings.contains(athleteTraining)) throw new IllegalArgumentException("You can't add the same thing two times.");
        athleteTrainings.add(athleteTraining);
    }


    public String getName() {
        return name;
    }

    public SwimmingStyles getCrownStyle() {
        return crownStyle;
    }

    public List<AthleteTraining> getAthleteTrainings() {
        return new ArrayList<>(athleteTrainings);
    }

    public List<Training> getTraining(){
        List<Training> trainings = new ArrayList<>();
        for(AthleteTraining athleteTraining : athleteTrainings){
            trainings.add(athleteTraining.getTraining());
        }
        return trainings;
    }


}
