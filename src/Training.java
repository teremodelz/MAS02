import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Training implements Serializable {
    private LocalDateTime start;
    private int duration;
    private List<AthleteTraining> athleteTrainings = new ArrayList<>();
    private static List<Training> trainingList = new ArrayList<>();

    public Training(LocalDateTime start, int duration) {
        //tutaj mysle o jakies walidacji roku
        if(start == null) throw new IllegalArgumentException("Start time can't be null.");
        if(duration <= 0) throw new IllegalArgumentException("Duration must be positive.");

        this.start = start;
        this.duration = duration;
        trainingList.add(this);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void addAthleteTrainings(AthleteTraining athleteTraining){
        if(athleteTraining == null) throw new IllegalArgumentException("Athlete training can't be null");
        if(athleteTrainings.contains(athleteTraining)) throw new IllegalArgumentException("You can't add the same thing two times.");
        if(athleteTraining.getTraining()!=this) throw new IllegalArgumentException("This athlete training doesn't belong to this training.");
        athleteTrainings.add(athleteTraining);
    }
    //pytanie o specyfikator hermetyzacji
    public void removeAthleteTraining(AthleteTraining athleteTraining){
        if(athleteTraining == null) throw new IllegalArgumentException("Athlete training can't be null");
        if(!athleteTrainings.contains(athleteTraining)) throw new IllegalArgumentException("You can't remove the thing which the object doesnt have.");
        //if(athleteTraining.getTraining()!=this) throw new IllegalArgumentException("You can't use this method, it's a helping method.");
        athleteTrainings.remove(athleteTraining);
    }

    public int getDuration() {
        return duration;
    }

    public List<AthleteTraining> getAthleteTrainings() {
        return new ArrayList<>(athleteTrainings);
    }

    public List<Athlete> getAthletes(){
        List<Athlete> athletes = new ArrayList<>();
        for(AthleteTraining athleteTraining : athleteTrainings){
            athletes.add(athleteTraining.getAthlete());
        }
        return athletes;
    }

    public static List<Training> getTrainingList() {
        return new ArrayList<>(trainingList);
    }

    public void remove(){
        if(!trainingList.contains(this)) throw new NoSuchElementException("This element is already removed.");
        for(AthleteTraining at : new ArrayList<>(athleteTrainings)){
            at.remove();
        }
        trainingList.remove(this);
    }

}
