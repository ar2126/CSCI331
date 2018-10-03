/**
 * Class BFS.java is responsible for performing Breadth-First Search on the city graph
 *
 * @author Aidan Rubenstein
 * @version 28-Feb-2018
 */
import java.util.*;

public class BFS extends superSearch {

    public BFS(String start, String goal, HashMap<String, City> graph){
        super(start, goal, graph);  //inherits superSearch
    }

    //runs the search of the graph from the start city to end city using the BFS algorithm discussed in class
    public void runSearch(){
        Queue<City> queue = new LinkedList<>();
        queue.add(graph.get(start));
        while(!queue.isEmpty()){
            City x = queue.poll();
            queue.remove(0);
            if(x.city.equals(goal)){
                return;
            }
            else {
                x.visited = true;
                ArrayList<String> children = x.adj;
                Collections.sort(children);
                for (String child : children) {
                    if (child.equals(goal)) {
                        graph.get(child).parent = x.city;
                        return;
                    }
                    else if(!graph.get(child).visited){
                        graph.get(child).parent = x.city;
                        graph.get(child).visited = true;
                        queue.add(graph.get(child));
                    }
                }
            }
        }
    }

    //Prints the pathlist the search used to take, along with the number of hops and total miles traveled
    public void print(){
        double total = 0;
        System.out.print("\nBreadth-First Search Results:\n");
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
