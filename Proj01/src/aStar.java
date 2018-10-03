/**
 * Class aStar.java is responsible for performing A* on the city graph
 *
 * @author Aidan Rubenstein
 * @version 28-Feb-2018
 */
import java.util.*;

public class aStar extends superSearch {

    public aStar(String start, String goal, HashMap<String, City> graph){
        super(start, goal, graph); //inherits superSearch
    }

    //runs the search of the graph from the start city to end city using the A* algorithm discussed in class
    public void runSearch(){
        ArrayList<City> queue = new ArrayList<>();
        graph.get(start).f = findG(start, start) + findH(goal, start);
        queue = sortByF(queue, graph.get(start));
        while(!queue.isEmpty()){
            City x = queue.get(0);
            queue.remove(0);
            if(x.city.equals(goal)){
                return;
            }
            else {
                x.visited = true;
                ArrayList<String> children = x.adj;
                for (String child : children) {
                    if (child.equals(goal)) {
                        graph.get(child).parent = x.city;
                        return;
                    }
                    else if(!graph.get(child).visited){
                        graph.get(child).parent = x.city;
                        graph.get(child).visited = true;
                        graph.get(child).f = findG(start, child) + findH(goal, child);
                        queue = sortByF(queue, graph.get(child));

                    }
                }
            }
        }
    }

    public ArrayList<City> sortByF(ArrayList<City> paths, City city){
        int size = paths.size();
        for(int i = 0; i < paths.size(); i++){
            if(city.f < paths.get(i).f){
                paths.add(i, city);
                break;
            }
        }
        if(paths.size() == size)
            paths.add(city);
        return paths;
    }

    /*
    * findH finds the current value of H used in the A* search
    *
    * @param goal the target city to go to
    * @param curr the current city being looked at
    *
    * @returns double the straight line distance from the current city to the goal
    */
    public double findH(String goal, String curr){
        return findDistance(graph.get(curr).lat, graph.get(goal).lat, graph.get(curr).lon, graph.get(goal).lon);
    }

    /*
    * findG finds the current value of G used in the A* search
    *
    * @param start the starting city
    * @param curr the current city being looked at
    *
    * @returns double the total mileage traveled along the path from start to curr
    */
    public double findG(String start, String curr){
        int total = 0;
        ArrayList<String> path = traverse(curr);
        for(int i = 0; i < path.size()-1; i++){
            total += findDistance(graph.get(path.get(i)).lat,
                    graph.get(path.get(i+1)).lat,
                    graph.get(path.get(i)).lon,
                    graph.get(path.get(i+1)).lon);
        }
        return total;
    }

    //Prints the pathlist the search used to take, along with the number of hops and total miles traveled
    public void print(){
        double total = 0;
        System.out.print("\nA* Search Results:\n");
        ArrayList <String> path = traverse(goal);
        Collections.reverse(path);
        for(int i = 0; i < path.size()-1; i++){
            System.out.println(path.get(i));
            total += findDistance(graph.get(path.get(i)).lat,
                    graph.get(path.get(i+1)).lat,
                    graph.get(path.get(i)).lon,
                    graph.get(path.get(i+1)).lon);
        }
        System.out.print(path.get(path.size()-1)+"\n");
        System.out.print("That took " + (path.size()-1) + " hops to find.\n");
        System.out.print("Total distance = " + Math.round(total) +" miles.\n");

    }
}
