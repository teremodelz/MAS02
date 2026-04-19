import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        // ============================================
        // KOMPOZYCJA — Stadium + Sector
        // ============================================
        Stadium wembley = new Stadium("Wembley");
        Sector north = Sector.createPart(wembley, "North Stand", SectorCategory.STANDARD, 20000.0);
        Sector vip = Sector.createPart(wembley, "VIP Lounge", SectorCategory.VIP, 500.0);
        Sector away = Sector.createPart(wembley, "Away End", SectorCategory.AWAY, 3000.0);

        System.out.println("Stadium: " + wembley.getName());
        System.out.println("Total capacity: " + wembley.getTotalCapacity());
        System.out.println("Sectors: " + wembley.getParts().size());

        // ============================================
        // ASOCJACJA KWALIFIKOWANA — FootballLeague + FootballClub
        // ============================================
        FootballLeague premierLeague = new FootballLeague("Premier League", "England", 1);
        FootballClub manchester = new FootballClub("Manchester United", "Manchester");
        FootballClub arsenal = new FootballClub("Arsenal", "London");
        FootballClub chelsea = new FootballClub("Chelsea", "London");

        premierLeague.addFootballClubQualif(manchester);
        premierLeague.addFootballClubQualif(arsenal);
        premierLeague.addFootballClubQualif(chelsea);

        System.out.println("\nPremier League clubs: " + premierLeague.getFootballClubQualif());

        String shortcode = manchester.generateShortcode();
        System.out.println("Manchester shortcode: " + shortcode);
        FootballClub found = premierLeague.findFootballClubQualifByShortCode(shortcode);
        System.out.println("Found by shortcode: " + found.getName());

        FootballLeague championship = new FootballLeague("Championship", "England", 2);
        manchester.setFootballLeague(championship);
        System.out.println("Manchester new league: " + manchester.getFootballLeague().getName());
        System.out.println("Premier League clubs after transfer: " + premierLeague.getFootballClubQualif().size());

        // ============================================
        // ASOCJACJA 1..* — Team + Player
        // ============================================
        Team teamLiquid = new Team("Team Liquid");
        Team navi = new Team("NaVi");

        Player s1mple = new Player("s1mple", navi);
        Player electronic = new Player("electronic", navi);
        Player zywoo = new Player("ZywOo", teamLiquid);

        System.out.println("\n" + navi);
        System.out.println(teamLiquid);

        s1mple.setTeam(teamLiquid);
        System.out.println("After transfer:");
        System.out.println(navi);
        System.out.println(teamLiquid);

        // ============================================
        // ASOCJACJA Z ATRYBUTEM — Athlete + Training + AthleteTraining
        // ============================================
        Athlete adam = new Athlete("Adam Kowalski", SwimmingStyles.FREESTYLE);
        Athlete maria = new Athlete("Maria Nowak", SwimmingStyles.BUTTERFLY);

        Training training1 = new Training(LocalDateTime.of(2026, 5, 1, 10, 0), 90);
        Training training2 = new Training(LocalDateTime.of(2026, 5, 3, 12, 0), 60);

        AthleteTraining at1 = new AthleteTraining(adam, training1, 58.5);
        AthleteTraining at2 = new AthleteTraining(maria, training1, 62.3);
        AthleteTraining at3 = new AthleteTraining(adam, training2, 57.9);

        System.out.println("\nAdam's trainings: " + adam.getAthleteTrainings().size());
        System.out.println("Training1 athletes: " + training1.getAthletes().size());
        System.out.println("Adam best time in training1: " + at1.getBestTime());

        // ============================================
        // ZAPIS I ODCZYT EKSTENSJI
        // ============================================
        System.out.println("\nSaving extensions...");
        ExtensionHelper.saveExtensions("data.ser");

        Stadium.setStadiumList(new java.util.ArrayList<>());
        Sector.setSectorList(new java.util.ArrayList<>());
        FootballClub.setFootballClubList(new java.util.ArrayList<>());
        FootballLeague.setFootballLeagueList(new java.util.ArrayList<>());
        Athlete.setAthleteList(new java.util.ArrayList<>());
        Training.setTrainingList(new java.util.ArrayList<>());
        AthleteTraining.setAthleteTrainingsList(new java.util.ArrayList<>());
        Player.setPlayerList(new java.util.ArrayList<>());
        Team.setTeamList(new java.util.ArrayList<>());

        System.out.println("After clear - stadiums: " + Stadium.getStadiumList().size());

        System.out.println("Loading extensions...");
        ExtensionHelper.loadExtensions("data.ser");

        System.out.println("After load - stadiums: " + Stadium.getStadiumList().size());
        System.out.println("After load - athletes: " + Athlete.getAthleteList().size());
        System.out.println("After load - teams: " + Team.getTeamList().size());

        // Pobierz obiekty z nowych list po załadowaniu
        Stadium loadedWembley = Stadium.getStadiumList().get(0);
        Athlete loadedAdam = Athlete.getAthleteList().get(0);
        Team loadedNavi = Team.getTeamList().stream()
                .filter(t -> t.getName().equals("NaVi"))
                .findFirst().get();

        System.out.println("\nLoaded stadium: " + loadedWembley.getName());
        System.out.println("Loaded stadium sectors: " + loadedWembley.getParts().size());
        System.out.println("Loaded athlete: " + loadedAdam.getName());
        System.out.println("Loaded team: " + loadedNavi.getName());
        System.out.println("Loaded team players: " + loadedNavi.getPlayers().size());

        // ============================================
        // USUWANIE
        // ============================================
        System.out.println("\nRemoving wembley stadium...");
        loadedWembley.remove();
        System.out.println("Stadiums after remove: " + Stadium.getStadiumList().size());
        System.out.println("Sectors after remove: " + Sector.getSectorList().size());

        System.out.println("\nRemoving adam athlete...");
        loadedAdam.remove();
        System.out.println("Athletes after remove: " + Athlete.getAthleteList().size());
        System.out.println("AthleteTrainings after remove: " + AthleteTraining.getAthleteTrainingsList().size());

        System.out.println("\nRemoving NaVi team...");
        loadedNavi.remove();
        System.out.println("Teams after remove: " + Team.getTeamList().size());
        System.out.println("Players after remove: " + Player.getPlayerList().size());
    }
}