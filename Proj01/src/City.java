/**
 * Class City.java details all characteristics of a city and their adjacent cities, including: name, state,
 * latitude & longitude, and their f-value for A* search
 *
 * @author Aidan Rubenstein
 * @version 28-Feb-2018
 */
import java.util.ArrayList;

public class City {
    protected String city;
    protected String state;
    protected double lat;
    protected double lon;
    protected String parent;
    protected boolean visited;
    protected ArrayList<String> adj;
    protected double f;

    public City(String city, String state, double lat, double lon){
        this.city = city;
        this.state = state;
        this.lat = lat;
        this.lon = lon;
        visited = false;
        adj = new ArrayList<>();
    }



}
