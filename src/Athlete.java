import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Athlete implements Serializable {
    private String name;
    private SwimmingStyles crownStyle;
    private List<AthleteTraining> athleteTrainings = new ArrayList<>();
    private static List<Athlete> athleteList = new ArrayList<>();

    public Athlete(String name, SwimmingStyles crownStyle) {
        if(name == null || name.isBlank()) throw new IllegalArgumentException("Name can't be null or blank.");
        if(crownStyle==null) throw new IllegalArgumentException("Crown style can't be null.");
        this.name = name;
        this.crownStyle = crownStyle;
        athleteList.add(this);
    }

    public void addAthleteTrainings(AthleteTraining athleteTraining){
        if(athleteTraining==null) throw new IllegalArgumentException("Athlete training can't be null.");
        if(athleteTrainings.contains(athleteTraining)) throw new IllegalArgumentException("You can't add the same thing two times.");
        if(athleteTraining.getAthlete()!=this) throw new IllegalArgumentException("This athlete training doesn't belong to this athelete.");
        athleteTrainings.add(athleteTraining);
    }


    public void removeAthleteTraining(AthleteTraining athleteTraining){
        if(athleteTraining == null) throw new IllegalArgumentException("Athlete training can't be null");
        if(!athleteTrainings.contains(athleteTraining)) throw new IllegalArgumentException("You can't remove the thing which the object doesnt have.");
        //if(athleteTraining.getAthlete()!=this) throw new IllegalArgumentException("You can't use this method, it's a helping method.");
        athleteTrainings.remove(athleteTraining);
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

    public static List<Athlete> getAthleteList() {
        return new ArrayList<>(athleteList);
    }

    public List<Training> getTraining(){
        List<Training> trainings = new ArrayList<>();
        for(AthleteTraining athleteTraining : athleteTrainings){
            trainings.add(athleteTraining.getTraining());
        }
        return trainings;
    }

    public void remove(){
        if(!athleteList.contains(this)) throw new NoSuchElementException("This element is already removed.");
        for(AthleteTraining at : new ArrayList<>(athleteTrainings)){
            at.remove();
        }
        athleteList.remove(this);
    }

}
