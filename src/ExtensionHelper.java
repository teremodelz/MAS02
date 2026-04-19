import java.io.*;
import java.util.List;

public class ExtensionHelper {
    public static void saveExtensions(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(Stadium.getStadiumList());
            oos.writeObject(Sector.getSectorList());
            oos.writeObject(FootballClub.getFootballClubList());
            oos.writeObject(FootballLeague.getFootballLeagueList());
            oos.writeObject(Athlete.getAthleteList());
            oos.writeObject(Training.getTrainingList());
            oos.writeObject(AthleteTraining.getAthleteTrainingsList());
            oos.writeObject(Player.getPlayerList());
            oos.writeObject(Team.getTeamList());
        } catch (IOException e) {
            System.err.println("Error saving extensions: " + e.getMessage());
        }
    }


    public static void loadExtensions(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            List<Stadium> stadiums = (List<Stadium>) ois.readObject();
            List<Sector> sectors = (List<Sector>) ois.readObject();
            List<FootballClub> clubs = (List<FootballClub>) ois.readObject();
            List<FootballLeague> leagues = (List<FootballLeague>) ois.readObject();
            List<Athlete> athletes = (List<Athlete>) ois.readObject();
            List<Training> trainings = (List<Training>) ois.readObject();
            List<AthleteTraining> athleteTrainings = (List<AthleteTraining>) ois.readObject();
            List<Player> players = (List<Player>) ois.readObject();
            List<Team> teams = (List<Team>) ois.readObject();


            Stadium.setStadiumList(stadiums);
            Sector.setSectorList(sectors);
            FootballClub.setFootballClubList(clubs);
            FootballLeague.setFootballLeagueList(leagues);
            Athlete.setAthleteList(athletes);
            Training.setTrainingList(trainings);
            AthleteTraining.setAthleteTrainingsList(athleteTrainings);
            Player.setPlayerList(players);
            Team.setTeamList(teams);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading extensions: " + e.getMessage());
        }
    }
}