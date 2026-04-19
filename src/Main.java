import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {

        // ============================================
        // STADIUM + SECTOR — KOMPOZYCJA
        // ============================================
        System.out.println("=== STADIUM + SECTOR ===");

        Stadium wembley = new Stadium("Wembley");
        Sector north = Sector.createPart(wembley, "North Stand", SectorCategory.STANDARD, 20000.0);
        Sector vip = Sector.createPart(wembley, "VIP Lounge", SectorCategory.VIP, 500.0);
        Sector away = Sector.createPart(wembley, "Away End", SectorCategory.AWAY, 3000.0);

        System.out.println("Stadium: " + wembley.getName());
        System.out.println("Total capacity: " + wembley.getTotalCapacity()); // 23500
        System.out.println("Sectors: " + wembley.getParts().size()); // 3
        System.out.println("North sector stadium: " + north.getStadium().getName()); // Wembley

        // Walidacje
        try { new Stadium(null); } catch (IllegalArgumentException e) { System.out.println("Stadium null name caught: " + e.getMessage()); }
        try { new Stadium(""); } catch (IllegalArgumentException e) { System.out.println("Stadium blank name caught: " + e.getMessage()); }
        try { Sector.createPart(null, "X", SectorCategory.VIP, 100.0); } catch (IllegalArgumentException e) { System.out.println("Sector null stadium caught: " + e.getMessage()); }
        try { Sector.createPart(wembley, null, SectorCategory.VIP, 100.0); } catch (IllegalArgumentException e) { System.out.println("Sector null name caught: " + e.getMessage()); }
        try { Sector.createPart(wembley, "X", null, 100.0); } catch (IllegalArgumentException e) { System.out.println("Sector null category caught: " + e.getMessage()); }
        try { Sector.createPart(wembley, "X", SectorCategory.VIP, null); } catch (IllegalArgumentException e) { System.out.println("Sector null capacity caught: " + e.getMessage()); }
        try { Sector.createPart(wembley, "X", SectorCategory.VIP, -1.0); } catch (IllegalArgumentException e) { System.out.println("Sector negative capacity caught: " + e.getMessage()); }

        // Sektor już w innym stadium
        Stadium anfield = new Stadium("Anfield");
        try { anfield.addPart(north); } catch (IllegalArgumentException e) { System.out.println("Sector already in stadium caught: " + e.getMessage()); }

        // removePart
        wembley.removePart(vip);
        System.out.println("Sectors after removePart: " + wembley.getParts().size()); // 2
        System.out.println("VIP stadium after removePart: " + vip.getStadium()); // null

        // Sector.remove()
        north.remove();
        System.out.println("Sectors after north.remove(): " + wembley.getParts().size()); // 1
        System.out.println("North stadium after remove: " + north.getStadium()); // null

        // Stadium.remove() — kaskadowe
        Stadium stadiumToRemove = new Stadium("Old Trafford");
        Sector east = Sector.createPart(stadiumToRemove, "East Stand", SectorCategory.STANDARD, 10000.0);
        Sector west = Sector.createPart(stadiumToRemove, "West Stand", SectorCategory.STANDARD, 10000.0);
        System.out.println("Parts before stadium.remove(): " + stadiumToRemove.getParts().size()); // 2
        stadiumToRemove.remove();
        System.out.println("Parts after stadium.remove(): " + stadiumToRemove.getParts().size()); // 0
        System.out.println("East stadium after remove: " + east.getStadium()); // null
        System.out.println("West stadium after remove: " + west.getStadium()); // null

        // ============================================
        // FOOTBALL LEAGUE + FOOTBALL CLUB — ASOCJACJA KWALIFIKOWANA
        // ============================================
        System.out.println("\n=== FOOTBALL LEAGUE + FOOTBALL CLUB ===");

        FootballLeague premierLeague = new FootballLeague("Premier League", "England", 1);
        FootballLeague championship = new FootballLeague("Championship", "England", 2);
        FootballClub manchester = new FootballClub("Manchester United", "Manchester");
        FootballClub arsenal = new FootballClub("Arsenal", "London");
        FootballClub chelsea = new FootballClub("Chelsea", "London");

        // Walidacje
        try { new FootballLeague(null, "England", 1); } catch (IllegalArgumentException e) { System.out.println("League null name caught: " + e.getMessage()); }
        try { new FootballLeague("PL", null, 1); } catch (IllegalArgumentException e) { System.out.println("League null country caught: " + e.getMessage()); }
        try { new FootballLeague("PL", "England", 0); } catch (IllegalArgumentException e) { System.out.println("League tier 0 caught: " + e.getMessage()); }
        try { new FootballLeague("PL", "England", 11); } catch (IllegalArgumentException e) { System.out.println("League tier 11 caught: " + e.getMessage()); }
        try { new FootballClub(null, "London"); } catch (IllegalArgumentException e) { System.out.println("Club null name caught: " + e.getMessage()); }
        try { new FootballClub("Arsenal", null); } catch (IllegalArgumentException e) { System.out.println("Club null city caught: " + e.getMessage()); }

        // addFootballClubQualif
        premierLeague.addFootballClubQualif(manchester);
        premierLeague.addFootballClubQualif(arsenal);
        premierLeague.addFootballClubQualif(chelsea);
        System.out.println("Premier League clubs: " + premierLeague.getFootballClubQualif().size()); // 3
        System.out.println("Manchester league: " + manchester.getFootballLeague().getName()); // Premier League
        try { premierLeague.addFootballClubQualif(manchester); } catch (IllegalArgumentException e) { System.out.println("Duplicate club caught: " + e.getMessage()); }

        // findByShortCode
        String shortcode = manchester.generateShortcode();
        System.out.println("Manchester shortcode: " + shortcode);
        System.out.println("Found by shortcode: " + premierLeague.findFootballClubQualifByShortCode(shortcode).getName());
        try { premierLeague.findFootballClubQualifByShortCode("XXX"); } catch (IllegalArgumentException e) { System.out.println("Not found shortcode caught: " + e.getMessage()); }

        // setFootballLeague — przeniesienie
        manchester.setFootballLeague(championship);
        System.out.println("Manchester new league: " + manchester.getFootballLeague().getName()); // Championship
        System.out.println("Premier League after transfer: " + premierLeague.getFootballClubQualif().size()); // 2
        System.out.println("Championship after transfer: " + championship.getFootballClubQualif().size()); // 1
        try { manchester.setFootballLeague(championship); } catch (IllegalArgumentException e) { System.out.println("Same league caught: " + e.getMessage()); }

        // setFootballLeague(null)
        manchester.setFootballLeague(null);
        System.out.println("Manchester league after null: " + manchester.getFootballLeague()); // null
        System.out.println("Championship after null: " + championship.getFootballClubQualif().size()); // 0

        // removeFootballClubQualif
        premierLeague.removeFootballClubQualif(arsenal);
        System.out.println("Premier League after remove: " + premierLeague.getFootballClubQualif().size()); // 1
        System.out.println("Arsenal league after remove: " + arsenal.getFootballLeague()); // null
        try { premierLeague.removeFootballClubQualif(arsenal); } catch (IllegalArgumentException e) { System.out.println("Remove nonexistent caught: " + e.getMessage()); }

        // FootballLeague.remove() — kaskadowe
        FootballLeague leagueToRemove = new FootballLeague("Test League", "Poland", 3);
        FootballClub club1 = new FootballClub("Club One", "Warsaw");
        FootballClub club2 = new FootballClub("Club Two", "Krakow");
        leagueToRemove.addFootballClubQualif(club1);
        leagueToRemove.addFootballClubQualif(club2);
        leagueToRemove.remove();
        System.out.println("club1 league after league.remove(): " + club1.getFootballLeague()); // null
        System.out.println("club2 league after league.remove(): " + club2.getFootballLeague()); // null

        // FootballClub.remove()
        chelsea.remove();
        System.out.println("Chelsea league after remove: " + chelsea.getFootballLeague()); // null
        System.out.println("Premier League after chelsea.remove(): " + premierLeague.getFootballClubQualif().size()); // 0

        // ============================================
        // TEAM + PLAYER — ASOCJACJA 1..*
        // ============================================
        System.out.println("\n=== TEAM + PLAYER ===");

        Team navi = new Team("NaVi");
        Team teamLiquid = new Team("Team Liquid");

        // Walidacje
        try { new Team(null); } catch (IllegalArgumentException e) { System.out.println("Team null name caught: " + e.getMessage()); }
        try { new Team(""); } catch (IllegalArgumentException e) { System.out.println("Team blank name caught: " + e.getMessage()); }
        try { new Player("", navi); } catch (IllegalArgumentException e) { System.out.println("Player blank nickname caught: " + e.getMessage()); }
        try { new Player("test", null); } catch (IllegalArgumentException e) { System.out.println("Player null team caught: " + e.getMessage()); }

        // addPlayer
        Player s1mple = new Player("s1mple", navi);
        Player electronic = new Player("electronic", navi);
        Player zywoo = new Player("ZywOo", teamLiquid);
        System.out.println("NaVi players: " + navi.getPlayers().size()); // 2
        System.out.println("s1mple team: " + s1mple.getTeam().getName()); // NaVi
        try { navi.addPlayer(s1mple); } catch (IllegalArgumentException e) { System.out.println("Duplicate player caught: " + e.getMessage()); }

        // setTeam — przeniesienie
        s1mple.setTeam(teamLiquid);
        System.out.println("NaVi after transfer: " + navi.getPlayers().size()); // 1
        System.out.println("TeamLiquid after transfer: " + teamLiquid.getPlayers().size()); // 2
        System.out.println("s1mple new team: " + s1mple.getTeam().getName()); // Team Liquid
        try { s1mple.setTeam(teamLiquid); } catch (IllegalArgumentException e) { System.out.println("Same team caught: " + e.getMessage()); }

        // setTeam(null)
        s1mple.setTeam(null);
        System.out.println("s1mple team after null: " + s1mple.getTeam()); // null
        System.out.println("TeamLiquid after setTeam(null): " + teamLiquid.getPlayers().size()); // 1

        // removePlayer
        navi.removePlayer(electronic);
        System.out.println("NaVi after removePlayer: " + navi.getPlayers().size()); // 0
        System.out.println("electronic team after remove: " + electronic.getTeam()); // null
        try { navi.removePlayer(electronic); } catch (IllegalArgumentException e) { System.out.println("Remove nonexistent player caught: " + e.getMessage()); }

        // Player.remove()
        Player playerToRemove = new Player("temp", teamLiquid);
        playerToRemove.remove();
        System.out.println("TeamLiquid after player.remove(): " + teamLiquid.getPlayers().size()); // 1
        playerToRemove.remove(); // "doesn't exist"

        // Team.remove() — kaskadowe
        Team teamToRemove = new Team("Temp Team");
        Player p1 = new Player("p1", teamToRemove);
        Player p2 = new Player("p2", teamToRemove);
        System.out.println("teamToRemove players before: " + teamToRemove.getPlayers().size()); // 2
        teamToRemove.remove();
        System.out.println("p1 team after team.remove(): " + p1.getTeam()); // null
        System.out.println("p2 team after team.remove(): " + p2.getTeam()); // null
        teamToRemove.remove(); // "doesn't exist"

        // ============================================
        // ATHLETE + TRAINING + ATHLETETRAINING — ASOCJACJA Z ATRYBUTEM
        // ============================================
        System.out.println("\n=== ATHLETE + TRAINING + ATHLETETRAINING ===");

        Athlete adam = new Athlete("Adam Kowalski", SwimmingStyles.FREESTYLE);
        Athlete maria = new Athlete("Maria Nowak", SwimmingStyles.BUTTERFLY);
        Training training1 = new Training(LocalDateTime.of(2026, 5, 1, 10, 0), 90);
        Training training2 = new Training(LocalDateTime.of(2026, 5, 3, 12, 0), 60);

        // Walidacje
        try { new Athlete(null, SwimmingStyles.FREESTYLE); } catch (IllegalArgumentException e) { System.out.println("Athlete null name caught: " + e.getMessage()); }
        try { new Athlete("Adam", null); } catch (IllegalArgumentException e) { System.out.println("Athlete null style caught: " + e.getMessage()); }
        try { new Training(null, 90); } catch (IllegalArgumentException e) { System.out.println("Training null start caught: " + e.getMessage()); }
        try { new Training(LocalDateTime.now(), 0); } catch (IllegalArgumentException e) { System.out.println("Training zero duration caught: " + e.getMessage()); }
        try { new AthleteTraining(null, training1, 58.5); } catch (IllegalArgumentException e) { System.out.println("AT null athlete caught: " + e.getMessage()); }
        try { new AthleteTraining(adam, null, 58.5); } catch (IllegalArgumentException e) { System.out.println("AT null training caught: " + e.getMessage()); }
        try { new AthleteTraining(adam, training1, null); } catch (IllegalArgumentException e) { System.out.println("AT null bestTime caught: " + e.getMessage()); }
        try { new AthleteTraining(adam, training1, -1.0); } catch (IllegalArgumentException e) { System.out.println("AT negative bestTime caught: " + e.getMessage()); }

        // Tworzenie
        AthleteTraining at1 = new AthleteTraining(adam, training1, 58.5);
        AthleteTraining at2 = new AthleteTraining(maria, training1, 62.3);
        AthleteTraining at3 = new AthleteTraining(adam, training2, 57.9);
        System.out.println("Adam's trainings: " + adam.getAthleteTrainings().size()); // 2
        System.out.println("Training1 athletes: " + training1.getAthletes().size()); // 2
        System.out.println("Adam best time in training1: " + at1.getBestTime()); // 58.5
        try { new AthleteTraining(adam, training1, 55.0); } catch (IllegalArgumentException e) { System.out.println("Duplicate AT caught: " + e.getMessage()); }

        // AthleteTraining.remove()
        at1.remove();
        System.out.println("Adam trainings after at1.remove(): " + adam.getAthleteTrainings().size()); // 1
        System.out.println("Training1 athletes after at1.remove(): " + training1.getAthletes().size()); // 1
        try { at1.remove(); } catch (Exception e) { System.out.println("Remove already removed AT: " + e.getMessage()); }

        // Athlete.remove() — kaskadowe
        adam.remove();
        System.out.println("Athletes after adam.remove(): " + Athlete.getAthleteList().size()); // 1
        System.out.println("ATs after adam.remove(): " + AthleteTraining.getAthleteTrainingsList().size()); // 1 (at2 Marii)

        // Training.remove() — kaskadowe
        training1.remove();
        System.out.println("Trainings after training1.remove(): " + Training.getTrainingList().size()); // 1
        System.out.println("ATs after training1.remove(): " + AthleteTraining.getAthleteTrainingsList().size()); // 0

        // ============================================
        // SERIALIZACJA
        // ============================================
        System.out.println("\n=== SERIALIZACJA ===");

        // Wyczyść wszystko przed testem serializacji
        Stadium.setStadiumList(new ArrayList<>());
        Sector.setSectorList(new ArrayList<>());
        FootballClub.setFootballClubList(new ArrayList<>());
        FootballLeague.setFootballLeagueList(new ArrayList<>());
        Athlete.setAthleteList(new ArrayList<>());
        Training.setTrainingList(new ArrayList<>());
        AthleteTraining.setAthleteTrainingsList(new ArrayList<>());
        Player.setPlayerList(new ArrayList<>());
        Team.setTeamList(new ArrayList<>());

        // Stwórz świeże dane
        Stadium serStadium = new Stadium("Serialized Stadium");
        Sector.createPart(serStadium, "Sector A", SectorCategory.STANDARD, 5000.0);
        Team serTeam = new Team("Serialized Team");
        Player serPlayer = new Player("serPlayer", serTeam);
        Athlete serAthlete = new Athlete("Serialized Athlete", SwimmingStyles.BACKSTROKE);
        Training serTraining = new Training(LocalDateTime.of(2026, 6, 1, 9, 0), 45);
        new AthleteTraining(serAthlete, serTraining, 60.0);

        System.out.println("Before save - stadiums: " + Stadium.getStadiumList().size()); // 1
        System.out.println("Before save - teams: " + Team.getTeamList().size()); // 1
        System.out.println("Before save - athletes: " + Athlete.getAthleteList().size()); // 1
        System.out.println("Before save - ATs: " + AthleteTraining.getAthleteTrainingsList().size()); // 1

        ExtensionHelper.saveExtensions("data.ser");

        // Wyczyść
        Stadium.setStadiumList(new ArrayList<>());
        Sector.setSectorList(new ArrayList<>());
        FootballClub.setFootballClubList(new ArrayList<>());
        FootballLeague.setFootballLeagueList(new ArrayList<>());
        Athlete.setAthleteList(new ArrayList<>());
        Training.setTrainingList(new ArrayList<>());
        AthleteTraining.setAthleteTrainingsList(new ArrayList<>());
        Player.setPlayerList(new ArrayList<>());
        Team.setTeamList(new ArrayList<>());

        System.out.println("After clear - stadiums: " + Stadium.getStadiumList().size()); // 0

        ExtensionHelper.loadExtensions("data.ser");

        System.out.println("After load - stadiums: " + Stadium.getStadiumList().size()); // 1
        System.out.println("After load - sectors: " + Sector.getSectorList().size()); // 1
        System.out.println("After load - teams: " + Team.getTeamList().size()); // 1
        System.out.println("After load - players: " + Player.getPlayerList().size()); // 1
        System.out.println("After load - athletes: " + Athlete.getAthleteList().size()); // 1
        System.out.println("After load - ATs: " + AthleteTraining.getAthleteTrainingsList().size()); // 1

        // Pobierz załadowane obiekty
        Stadium loadedStadium = Stadium.getStadiumList().get(0);
        Team loadedTeam = Team.getTeamList().get(0);
        Athlete loadedAthlete = Athlete.getAthleteList().get(0);

        System.out.println("Loaded stadium: " + loadedStadium.getName());
        System.out.println("Loaded stadium sectors: " + loadedStadium.getParts().size()); // 1
        System.out.println("Loaded team: " + loadedTeam.getName());
        System.out.println("Loaded team players: " + loadedTeam.getPlayers().size()); // 1
        System.out.println("Loaded athlete: " + loadedAthlete.getName());
        System.out.println("Loaded athlete trainings: " + loadedAthlete.getAthleteTrainings().size()); // 1

        // Usuń załadowane
        loadedStadium.remove();
        System.out.println("Stadiums after loaded remove: " + Stadium.getStadiumList().size()); // 0
        System.out.println("Sectors after loaded remove: " + Sector.getSectorList().size()); // 0

        loadedAthlete.remove();
        System.out.println("Athletes after loaded remove: " + Athlete.getAthleteList().size()); // 0
        System.out.println("ATs after loaded remove: " + AthleteTraining.getAthleteTrainingsList().size()); // 0

        loadedTeam.remove();
        System.out.println("Teams after loaded remove: " + Team.getTeamList().size()); // 0
        System.out.println("Players after loaded remove: " + Player.getPlayerList().size()); // 0
    }
}