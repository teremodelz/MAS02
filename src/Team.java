import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Team implements Serializable {
    private String name;
    private List<Player> players = new ArrayList<>();
    private static List<Team> teamList = new ArrayList<>();
    public Team(String name){
        if(name==null || name.isBlank()) throw new IllegalArgumentException("Team can't be null or blank");
        this.name = name;
        teamList.add(this);
    }
    public void addPlayer(Player player){
        if(players.contains(player)){
            throw new IllegalArgumentException("You can't add player again to the team.");
        }
        players.add(player);
        player.setTeam(this);
    }


    public void removePlayer(Player player){
        if(!players.contains(player)){
            throw new IllegalArgumentException("Removing player who already isn't a part of a team is impossible.");
        }
        players.remove(player);
        player.setTeam(null);
    }

    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public void remove(){
        if(!teamList.contains(this)) System.out.println("This team doesn't exist in the list.");
        for(Player player : players){
            removePlayer(player);
            //tu zrobic pozniej logi zeby sprawdzic czy wszystko ok z metodą
        }
        this.name = null;
        teamList.remove(this);
    }


    @Override
    public String toString() {
        var info = "Team : " + name + "\n";
            info += "Players in this team; " +"\n";

        for(Player player : players){
            info += " " + player.getNickname()+ "\n";
        }
        return info;
    }

    //ASOCJACJA KWALIFIKOWANA
    //FOOTBALL LEAGUE[NAME] - FOOTBALL CLUB
}
