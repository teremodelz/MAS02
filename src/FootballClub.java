import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FootballClub implements Serializable {
    private String name;
    private String city;
    private FootballLeague footballLeague;
    private static List<FootballClub> footballClubList = new ArrayList<>();
    public FootballClub(String name, String city) {
        if(name == null || name.isBlank()) throw new IllegalArgumentException("Name can't be null/blank.");
        if(city == null || city.isBlank()) throw new IllegalArgumentException("City can't be null/blank.");
        this.name = name;
        this.city = city;
        footballClubList.add(this);
    }

    public void setFootballLeague(FootballLeague footballLeague) {
        if(this.footballLeague != footballLeague) {
            if(this.footballLeague != null) {
                FootballLeague oldFootballLeague = this.footballLeague;
                this.footballLeague = null;
                if(oldFootballLeague.getFootballClubQualif().containsValue(this)) {
                    oldFootballLeague.removeFootballClubQualif(this);
                }
            }
            this.footballLeague = footballLeague;
            if(footballLeague != null && !footballLeague.getFootballClubQualif().containsValue(this)) {
                footballLeague.addFootballClubQualif(this);
            }
        } else if(this.footballLeague!=null){
            throw new IllegalArgumentException("You can't set the same league two times.");
        }
    }



    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public FootballLeague getFootballLeague() {
        return footballLeague;
    }

    public static List<FootballClub> getFootballClubList() {
        return new ArrayList<>(footballClubList);
    }

    public String generateShortcode() {
        String[] nameParts = name.split(" ");
        StringBuilder shortcode = new StringBuilder();
        for(String part : nameParts) {
            shortcode.append(part.charAt(0));
        }

        shortcode.append(city.substring(0, Math.min(3, city.length())));

        return shortcode.toString().toUpperCase();
    }

    public void remove(){
        if(!footballClubList.contains(this)){
            System.out.println("This football club doesn't exist in the list");
            return;
        }
        setFootballLeague(null);
        this.name = null;
        this.city = null;
        footballClubList.remove(this);
    }

    public static void setFootballClubList(List<FootballClub> footballClubList) {
        FootballClub.footballClubList = footballClubList;
    }
}
