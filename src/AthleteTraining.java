import java.io.Serializable;

public class AthleteTraining implements Serializable {
    private Double bestTime;
    private Athlete athlete;
    private Training training;


    public AthleteTraining(Athlete athlete, Training training, Double bestTime) {
        if(athlete==null) throw new IllegalArgumentException("Athlete can't be null.");
        if(training==null) throw new IllegalArgumentException("Training can't be null.");
        if(bestTime==null) throw new IllegalArgumentException("Best time can't be null.");
        if(bestTime < 0) throw new IllegalArgumentException("Best time can't be below 0.");

        this.athlete = athlete;
        this.training = training;
        this.bestTime = bestTime;

        for(AthleteTraining at : athlete.getAthleteTrainings()) {
            if (at.getTraining() == training) {
                throw new IllegalArgumentException("This athlete is already assigned to this training!");
            }
        }

        athlete.addAthleteTrainings(this);
        training.addAthleteTrainings(this);

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


    //pytanie o remove w przypadku asocjaji z atrybutem

}
