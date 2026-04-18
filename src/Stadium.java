import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Stadium implements Serializable {
    private String name;
    private List<Sector> parts = new ArrayList<>();
    private static Set<Sector> allParts = new HashSet<>();

    private static List<Stadium> stadiumList = new ArrayList<>();
    public Stadium(String name){
        this.name = name;
        stadiumList.add(this);
    }

    public void addPart(Sector sector){
        if(sector == null) throw new IllegalArgumentException("Sector can't be null");
        if(!parts.contains(sector)){
            if(allParts.contains(sector)) throw new IllegalArgumentException("This sector is already connected with a other stadium.");
            parts.add(sector);
            allParts.add(sector);
        }
        else {
            throw new IllegalArgumentException("This sector is already in this stadium.");
        }
    }

    public void removePart(Sector sector){
        if(sector == null) throw new IllegalArgumentException("Sector can't be null");
        if(!parts.contains(sector)) throw new IllegalArgumentException("The stadium isn't connected with this sector.");
        parts.remove(sector);
        allParts.remove(sector);
        if(sector.getStadium()!=null){
            sector.remove();
        }
    }

    public String getName() {
        return name;
    }

    public List<Sector> getParts() {
        return new ArrayList<>(parts);
    }

    public static Set<Sector> getAllParts() {
        return new HashSet<>(allParts);
    }

    public static List<Stadium> getStadiumList() {
        return new ArrayList<>(stadiumList);
    }
}
