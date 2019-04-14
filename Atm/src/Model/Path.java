package Model;

/**
 * This model is as a node in a graph that interconects 2 atms
 */
public class Path implements Model{
    private String startATMname;
    private String stopATMname;
    private int duration;

    /**
     * Constructor
     * @param startATMname Starting point, if it isn`t an atm then write "User starting point"
     * @param stopATMname ending point, an atm
     * @param duration  how much time does it takes to get there
     */
    public Path(String startATMname, String stopATMname, int duration) {
        this.startATMname = startATMname;
        this.stopATMname = stopATMname;
        this.duration = duration;
    }

    /**
     * Get the starting point
     * @return String
     */
    public String getStart() {
        return startATMname;
    }

    /**
     * Get the stop point
     * @return String
     */
    public String getStop() {
        return stopATMname;
    }

    /**
     * Get the duration in minutes
     * @return int
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Show the entity with all the attributes
     * @return
     */
    @Override
    public String toString() {
        return "Path{" +
                "startATMname='" + startATMname + '\'' +
                ", stopATMname='" + stopATMname + '\'' +
                ", duration=" + duration +
                '}';
    }
}
