import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Training implements Serializable {
    private LocalDateTime start;
    private int duration;
    private List<AthleteTraining> athleteTrainings = new ArrayList<>();

    public Training(LocalDateTime start, int duration) {
        //tutaj mysle o jakies walidacji roku
        if(start == null) throw new IllegalArgumentException("Start time can't be null.");
        if(duration <= 0) throw new IllegalArgumentException("Duration must be positive.");

        this.start = start;
        this.duration = duration;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void addAthleteTrainings(AthleteTraining athleteTraining){
        if(athleteTrainings.contains(athleteTraining)) throw new IllegalArgumentException("You can't add the same thing two times.");
        athleteTrainings.add(athleteTraining);
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



}
