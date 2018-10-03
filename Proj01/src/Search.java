/**
 * Class Search.java contains the main program used to run Project 01. The main method reads in an input/output
 * from stdin/out and creates a HashMap of all of the City relationships as detailed in city.dat and edge.dat.
 *
 * The program then performs BFS, DFS, and A* search in that order with the given start/end cities
 *
 * @author Aidan Rubenstein
 * @version 28-Feb-2018
 */

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class Search {
    public static void main(String[] args){
        //Prints an error to stderr if the arguments are not 2
        if(args.length != 2){
            System.err.println("Usage: java Search inputFile outputFile");
            System.exit(0);
        }

        String startCity;
        String endCity;

        if(!args[0].equals("-")) {
            String[] input = readInput(args[0]).split(" ");
            startCity = input[0];
            endCity = input[1];
        }
        else{
            Scanner sc = new Scanner(System.in);
            startCity = sc.nextLine();
            endCity = sc.nextLine();
        }

        HashMap<String, City> map = new HashMap<>();    //creates a map of all of the cities


        //Adding all cities to a dictionary with its characteristics
        try{
            String[] city;
            FileReader cities = new FileReader("city.dat");
            String cityLine = null;
            BufferedReader cit = new BufferedReader(cities);
            while((cityLine = cit.readLine()) != null){
                city = cityLine.split("\\s+");
                City cityObj = new City(city[0], city[1], Double.parseDouble(city[2]), Double.parseDouble(city[3]));
                map.put(city[0], cityObj);
            }
        }catch(FileNotFoundException f){
            f.printStackTrace();
        }catch(IOException i){
            i.printStackTrace();
        }


        //Adding adjancent city to all connected cities
        try{
            String[] edge;
            FileReader cities = new FileReader("edge.dat");
            String edgeLine = null;
            BufferedReader edg = new BufferedReader(cities);
            while((edgeLine = edg.readLine()) != null){
                if(!edgeLine.isEmpty()) {
                    edge = edgeLine.split("\\s+");
                    map.get(edge[0]).adj.add(edge[1]);
                    map.get(edge[1]).adj.add(edge[0]);
                }
            }
        }catch(FileNotFoundException f){
            f.printStackTrace();
        }catch(IOException i){
            i.printStackTrace();
        }

        //running BFS
        BFS bfs = new BFS(startCity, endCity, map);
        bfs.runSearch();
        bfs.print();
        for(String keys : map.keySet()){
            map.get(keys).visited = false;
        }

        //running DFS
        DFS dfs = new DFS(startCity, endCity, map);
        dfs.runSearch();
        dfs.print();
        for(String keys1 : map.keySet()){
            map.get(keys1).visited = false;
        }

        //running A*
        aStar a = new aStar(startCity, endCity, map);
        a.runSearch();
        a.print();

    }

    /*
    * Helper method dedicated to reading a given input file if args[0] was not "-" exactly
    *
    * @param input the input string file to be read
    * @return a concatenated string of start and end cities
    */
    public static String readInput(String input){
        String in = "";
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(input);
            String inputLine = null;

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((inputLine = bufferedReader.readLine()) != null) {
                in += inputLine + " ";
            }
        }catch(FileNotFoundException e) {
            System.err.println("File not found:" + input);
            System.exit(0);
        }catch(IOException i){
            i.printStackTrace();
        }

        return in;

    }
}
