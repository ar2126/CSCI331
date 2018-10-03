/**
 * Class DFS.java is responsible for performing Depth-First Search on the city graph
 *
 * @author Aidan Rubenstein
 * @version 28-Feb-2018
 */
import java.util.*;

public class DFS extends superSearch {

    public DFS(String start, String goal, HashMap<String, City> graph){
        super(start, goal, graph); //inherits superSearch
    }

    //runs the search of the graph from the start city to end city using the DFS algorithm discussed in class
    public void runSearch(){
        Stack<City> stack = new Stack<>();
        stack.add(graph.get(start));
        while(!stack.isEmpty()){
            City x = stack.pop();
            if(x.city.equals(goal)){
                return;
            }
            else {
                x.visited = true;
                ArrayList<String> children = x.adj;
                Collections.sort(children);
                Collections.reverse(children);
                for (String child : children) {
                    if (child.equals(goal)) {
                        graph.get(child).parent = x.city;
                        return;
                    }
                    else if(!graph.get(child).visited){
                        graph.get(child).parent = x.city;
                        graph.get(child).visited = true;
                        stack.add(graph.get(child));
                    }
                }
            }
        }
    }

    //Prints the pathlist the search used to take, along with the number of hops and total miles traveled
    public void print(){
        double total = 0;
        System.out.println("\nDepth-First Search Results:");
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
        System.out.print("Total distance = " + Math.round(total) + " miles.\n");

    }
}
