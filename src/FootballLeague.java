import java.io.Serializable;
import java.util.*;

public class FootballLeague implements Serializable {
    private String name;
    private String country;
    private int tier;
    private Map<String, FootballClub> footballClubQualif = new TreeMap<>();
    private static List<FootballLeague> footballLeagueList = new ArrayList<>();
    public FootballLeague(String name, String country, int tier) {
        if(name == null || name.isBlank()) throw new IllegalArgumentException("Name can't be null or blank.");
        if(country == null || country.isBlank()) throw new IllegalArgumentException("Country can't be null or blank.");
        if(tier<=0 || tier > 10) throw new IllegalArgumentException("Tier can't be less than 0 and higher than 10.");
        this.name = name;
        this.country = country;
        this.tier = tier;
        footballLeagueList.add(this);
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getTier() {
        return tier;
    }

    public Map<String, FootballClub> getFootballClubQualif() {
        return new TreeMap<>(footballClubQualif);
    }

    public void addFootballClubQualif(FootballClub footballClub){
        if(footballClubQualif.containsValue(footballClub)) throw new IllegalArgumentException("This club is already added to this league.");
        footballClubQualif.put(footballClub.generateShortcode(), footballClub);
        footballClub.setFootballLeague(this);
    }


    public void removeFootballClubQualif(FootballClub footballClub){
        if(!footballClubQualif.containsValue(footballClub)) throw new IllegalArgumentException("This club doesn't exist in this league");
        footballClub.setFootballLeague(null);
        footballClubQualif.remove(footballClub.generateShortcode());
    }

    public FootballClub findFootballClubQualifByShortCode(String shortCode){
        if(!footballClubQualif.containsKey(shortCode.toUpperCase())) throw new IllegalArgumentException("The club with this short code doesn't exist in this league");
        return footballClubQualif.get(shortCode.toUpperCase());
    }

    public void remove(){
        if(!footballLeagueList.contains(this)) {
            System.out.println("This league isn't at the list.");
            return;
        }
        for(FootballClub fc : new ArrayList<>(footballClubQualif.values())){
            fc.setFootballLeague(null);
        }
        this.name = null;
        this.country = null;
        this.tier = 0;
        footballLeagueList.remove(this);
    }
}
