/**
 * Class superSearch is an abstract class used to facilitate the searching techniques of all three searches, mainly
 * to help calculate distances and override search methods
 *
 * @author Aidan Rubenstein
 * @version 28-Feb-2018
 */
import java.lang.Math;
import java.util.HashMap;
import java.util.ArrayList;

public abstract class superSearch {

    protected String start;
    protected String goal;
    protected HashMap<String, City> graph;

    public superSearch(String start, String goal, HashMap<String, City> graph){
        this.start = start;
        this.goal = goal;
        this.graph = graph;
    }

    public double findDistance(double lat1, double lat2, double lon1, double lon2){
        return Math.sqrt( (lat1-lat2)*(lat1-lat2) + (lon1-lon2)*(lon1-lon2) ) * 100;
    }

    protected ArrayList<String> traverse(String node) {
        ArrayList<String> path = new ArrayList<>();
        path.add(node);
        String temp = node;
        if (node.equals(start))
            return path;
        do {
            temp = graph.get(temp).parent;
            path.add(temp);
        } while (!temp.equals(start));
        return path;
    }

    public abstract void runSearch();
    public abstract void print();
}
