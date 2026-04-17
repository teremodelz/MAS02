import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Team implements Serializable {
    private String name;
    private List<Player> players = new ArrayList<>();
    public Team(String name){
        this.name = name;
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
