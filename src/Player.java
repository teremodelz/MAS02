import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Player implements Serializable {
    private String nickname;
    private Team team;

    private static List<Player> playerList = new ArrayList<>();

    public Player(String nickname, Team team){
        if(nickname.isEmpty()|| nickname.isBlank())
            throw new IllegalArgumentException("Nickname can't be null");
        this.nickname = nickname;
        if(team==null)
            throw new IllegalArgumentException("Team can't be null");
        this.team = team;
        playerList.add(this);
    }

    public void setTeam(Team newTeam) {
        if(this.team != newTeam) {
            if(this.team != null) {
                Team oldTeam = this.team;
                this.team = null;
                if(oldTeam.getPlayers().contains(this)) {
                    oldTeam.removePlayer(this);
                }
            }
            this.team = newTeam;
            if(newTeam != null && !newTeam.getPlayers().contains(this)) {
                newTeam.addPlayer(this);
            }
        } else if(this.team!=null){
            throw new IllegalArgumentException("You can't set the same team two times.");
        }
    }

    public String getNickname() {
        return nickname;
    }

    public Team getTeam() {
        return team;
    }

    public void remove(){
        if(!playerList.contains(this)) System.out.println("This player doesnt exist in the list.");
        setTeam(null);
        this.nickname = null;
        playerList.remove(this);
    }

    @Override
    public String toString() {
        var info = "Player: " +nickname+ "\n";
        return info;
    }
}
