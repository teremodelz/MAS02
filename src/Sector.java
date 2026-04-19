import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sector implements Serializable {
    private String name;
    private SectorCategory sectorCategory;
    private Double capacity;
    private Stadium stadium;
    private static List<Sector> sectorList = new ArrayList<>();
    private Sector(Stadium stadium, String name, SectorCategory sectorCategory, Double capacity){
        this.stadium = stadium;
        this.name = name;
        this.sectorCategory = sectorCategory;
        this.capacity = capacity;
        sectorList.add(this);
    }

    public static Sector createPart(Stadium stadium, String name, SectorCategory sectorCategory, Double capacity){
        if(stadium == null) throw new IllegalArgumentException("Stadium can't be null");
        if(name == null || name.isBlank()) throw new IllegalArgumentException("Name of the part can't be null or blank.");
        if(sectorCategory == null) throw new IllegalArgumentException("Sector category can't be null.");
        if(capacity == null || capacity<0 ) throw new IllegalArgumentException("Capacity can't be null or can't be less than 0.");
        Sector sector = new Sector(stadium, name, sectorCategory, capacity);
        stadium.addPart(sector);
        return sector;
    }

    public void remove(){
        Stadium oldStadium = this.stadium;
        this.stadium = null;
        oldStadium.removePart(this);
        this.name = null;
        this.sectorCategory = null;
        this.capacity = null;
        sectorList.remove(this);
    }

    public String getName() {
        return name;
    }

    public SectorCategory getSectorCategory() {
        return sectorCategory;
    }

    public Double getCapacity() {
        return capacity;
    }

    public Stadium getStadium() {
        return stadium;
    }

    public static List<Sector> getSectorList() {
        return new ArrayList<>(sectorList);
    }

    public static void setSectorList(List<Sector> sectorList) {
        Sector.sectorList = sectorList;
    }
}
