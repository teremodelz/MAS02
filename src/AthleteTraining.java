import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class AthleteTraining implements Serializable {
    private Double bestTime;
    private Athlete athlete;
    private Training training;
    private static List<AthleteTraining> athleteTrainingsList = new ArrayList<>();
    public AthleteTraining(Athlete athlete, Training training, Double bestTime) {
        if(athlete==null) throw new IllegalArgumentException("Athlete can't be null.");
        if(training==null) throw new IllegalArgumentException("Training can't be null.");
        if(bestTime==null) throw new IllegalArgumentException("Best time can't be null.");
        if(bestTime < 0) throw new IllegalArgumentException("Best time can't be below 0.");

        for(AthleteTraining at : athlete.getAthleteTrainings()) {
            if (at.getTraining() == training) {
                throw new IllegalArgumentException("This athlete is already assigned to this training!");
            }
        }

        this.athlete = athlete;
        this.training = training;
        this.bestTime = bestTime;


        athlete.addAthleteTrainings(this);
        training.addAthleteTrainings(this);
        athleteTrainingsList.add(this);

    }

    public Double getBestTime() {
        return bestTime;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public Training getTraining() {
        return training;
    }

    public static List<AthleteTraining> getAthleteTrainingsList() {
        return new ArrayList<>(athleteTrainingsList);
    }

    //pytanie o remove w sob(zadac)
    public void remove(){
        if(!athleteTrainingsList.contains(this)) throw new NoSuchElementException("This element is already removed.");
        athlete.removeAthleteTraining(this);
        training.removeAthleteTraining(this);
        this.athlete = null;
        this.training = null;
        this.bestTime = null;
        athleteTrainingsList.remove(this);
    }

    public static void setAthleteTrainingsList(List<AthleteTraining> athleteTrainingsList) {
        AthleteTraining.athleteTrainingsList = athleteTrainingsList;
    }
}
