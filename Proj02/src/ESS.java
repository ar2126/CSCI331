/**
 * Class ESS.java contains all of the animal logic, menu options, and error checking for the ESS Simulation in
 * reference to Game Theory
 *
 * @author Aidan Rubenstein
 * @version 04-Apr-2018
 */
import java.util.*;

public class ESS {

    public static void main(String[] args){
        if(args.length < 1 || args.length > 4) {
            System.err.print("Usage: ./project02 popSize [percentHawks] [resourceAmt] [costHawk-Hawk]");
            System.exit(0);
        }
        else{
            int population = Integer.parseInt(args[0]);
            int percent;
            int resource;
            int hawkcost;

            //Checking for optional parameters, if none exist then default values are placed
            try {
                percent = Integer.parseInt(args[1]);
            }catch(ArrayIndexOutOfBoundsException e){
                percent = 20;
            }
            try{
                resource = Integer.parseInt(args[2]);
            }catch(ArrayIndexOutOfBoundsException e){
                resource = 50;
            }
            try{
                hawkcost = Integer.parseInt(args[3]);
            }catch(ArrayIndexOutOfBoundsException e){
                hawkcost = 100;
            }

            double decimal = (double) percent/100;
            ArrayList<Birds> birds = createBirds(population, percent);

            while(true){
                int count = birds.size();
                int hawks = (int)(population * decimal);

                //Gets the count of both the total population and the population of hawks after a user command
                for(int i = count-1; i >= 0; i--){
                    if(birds.get(i).status.equals("DEAD")) {
                        count--;
                        hawks--;
                    }
                }

                //Menu Screen
                System.out.print("===============MENU=============\n" +
                        "1 ) Starting Stats\n" +
                        "2 ) Display Individuals and Points\n" +
                        "3 ) Display Sorted\n" +
                        "4 ) Have 1000 interactions\n" +
                        "5 ) Have 10000 interactions\n" +
                        "6 ) Have N interactions\n" +
                        "7 ) Step through interactions \"Stop\" to return to menu\n" +
                        "8 ) Quit\n" +
                        "================================\n");
                Scanner s1 = new Scanner(System.in);
                int choice = s1.nextInt();

                //Displays the statistics of the simulation currently implemented
                if(choice == 1){

                    System.out.print("Population size: " + population + "\n");
                    System.out.print("Percentage of Hawks: " + percent + "%\n");
                    System.out.print("Number of Hawks: " + (int)(population * decimal) + "\n");
                    System.out.print("\nPercentage of Doves: " + (100-percent) + "%\n");
                    System.out.print("Number of Doves: " + (int)(population - (population * decimal)) + "\n");
                    System.out.print("\nEach resource is worth: " + resource +"\n");
                    System.out.print("Cost of Hawk-Hawk interaction: " + hawkcost +"\n");

                }

                //Displays the list of individuals and their resource score amongst each other
                else if(choice == 2){
                    for(int i = 0; i < birds.size(); i++){
                        System.out.print("Individual [" + i + "]=" + birds.get(i).status + ":" + birds.get(i).resource +"\n");
                    }
                    System.out.print("Living: " + count +"\n");
                }

                //Displays a sorted list of each individual's strategy and resource in descending order by resource
                else if(choice == 3){
                    Collections.sort(birds);
                    for(int i = 0; i < birds.size(); i++){
                        System.out.print(birds.get(i).status + ":" + birds.get(i).resource +"\n");
                    }
                }

                //Iterates through a simulation step 1000 times with no output to the console
                else if(choice == 4 && hawks > 1){
                    for(int i = 0; i < 1000; i++){
                        birds = simulate(population, percent, resource, hawkcost, birds);
                    }
                }

                //Iterates through a simulation step 10,000 times with no output to the console
                else if(choice == 5 && hawks > 1){
                    for(int i = 0; i < 10000; i++){
                        birds = simulate(population, percent, resource, hawkcost, birds);
                    }
                }

                //Iterates through a simulation step N times with no output to the console
                else if(choice == 6 && hawks > 1){
                    System.out.print("Enter the amount of N iterations: \n");
                    int n = s1.nextInt();
                    for(int i = 0; i < n; i++){
                        birds = simulate(population, percent, resource, hawkcost, birds);
                    }
                }

                //Individually steps through an iteration of the simulation, showing which random entities were picked,
                // the result of the choice, and how the resources were doled out to each individual
                else if(choice == 7 && hawks > 1){
                    Scanner s2 = new Scanner(System.in);
                    int encounter = 0;
                    while(true) {
                        System.out.print("\nPress ENTER to step through simulation, or type 'STOP' to terminate.\n");
                        String step = s2.nextLine();
                        if (step.equals("")) {
                            System.out.print("\nEncounter: " + (encounter + 1) + "\n");
                            birds = simulateStep(population, percent, resource, hawkcost, birds);
                            encounter++;
                        }
                        else if (step.equals("STOP"))
                            break;
                    }
                }

                //Breaks the infinite while loop to end the simulation
                else if(choice == 8){
                    break;
                }

                //Prevents any further simulating once there is either one hawk or all hawks are dead
                else if(hawks <= 1){
                    System.out.print("Simulation Cannot Continue\n");
                }
            }
            System.out.print("Simulation Finished\n");
            System.exit(0);
        }
    }

    /*
    *   Creates a list of Birds entities with a status (Hawk, Dove, or DEAD) and resources they currently have
    *
    *   @param population - the total population
    *   @param percent - the percentage of the population that are hawks
    *
    *   @returns birds - the newly created list of all individuals
    */
    private static ArrayList<Birds> createBirds(int population, int percent){
        ArrayList<Birds> birds = new ArrayList<>();
        double decimal = (double) percent/100;

        for(int i = 0; i < (int)(population * decimal); i++){
            Birds hawk = new Birds("Hawk", 0);
            birds.add(hawk);
        }

        for(int j = 0; j < (int)(population - (population * decimal)); j++){
            Birds dove = new Birds("Dove", 0);
            birds.add(dove);
        }

        return birds;
    }


    /*
    *   Simulates an individual step in the simulation that prints status messages and follows the Logic of
    *   Animal Conflict
    *
    *   @param population - the total population
    *   @param percent - the percentage of the population that are hawks
    *   @param resource - the value of resources for a given interaction
    *   @param hawkcost - the cost of the Hawk-Hawk interaction
    *   @param members - the current list of all individuals both alive and dead
    *
    *   @returns members - the newly updated list of all individuals after performing one step of the Logic
    */
    private static ArrayList<Birds> simulateStep(int population, int percent, int resource, int hawkcost, ArrayList<Birds> members){
        Random r1 = new Random();
        int encounter = 0;
        int indiv1 = r1.nextInt(members.size());
        int indiv2 = r1.nextInt(members.size());
        if(indiv1 == indiv2){
            indiv1 = r1.nextInt(members.size());
        }

        while(members.get(indiv1).status.equals("DEAD") || members.get(indiv2).status.equals("DEAD")) {
            if (members.get(indiv1).status.equals("DEAD")) {
                indiv1 = r1.nextInt(members.size());
            } else if (members.get(indiv2).status.equals("DEAD")) {
                indiv2 = r1.nextInt(members.size());
            }
        }

        System.out.print("Individual " + indiv1 + ":" + members.get(indiv1).status + "\n");
        System.out.print("Individual " + indiv2 + ":" + members.get(indiv2).status + "\n");

        if (members.get(indiv1).status.equals("Dove") && members.get(indiv2).status.equals("Dove")) {
            members.get(indiv1).resource += resource / 2;
            members.get(indiv2).resource += resource / 2;
            System.out.print("Dove/Dove: Dove: +" + resource / 2 + "\tDove: +" + resource / 2 + "\n");
            System.out.print("Individual " + indiv1 + "=" + members.get(indiv1).resource + "\tIndividual " + indiv2 + "=" + members.get(indiv2).resource + "\n");
        } else if (members.get(indiv1).status.equals("Dove") && members.get(indiv2).status.equals("Hawk")) {
            members.get(indiv2).resource += resource;
            System.out.print("Dove/Hawk: Dove: +" + 0 + "\tHawk: +" + resource + "\n");
            System.out.print("Individual " + indiv1 + "=" + members.get(indiv1).resource + "\tIndividual " + indiv2 + "=" + members.get(indiv2).resource + "\n");
        } else if (members.get(indiv1).status.equals("Hawk") && members.get(indiv2).status.equals("Dove")) {
            members.get(indiv1).resource += resource;
            System.out.print("Hawk/Dove: Hawk: +" + resource + "\tDove: +" + 0 + "\n");
            System.out.print("Individual " + indiv1 + "=" + members.get(indiv1).resource + "\tIndividual " + indiv2 + "=" + members.get(indiv2).resource + "\n");
        } else if (members.get(indiv1).status.equals("Hawk") && members.get(indiv2).status.equals("Hawk")) {
            members.get(indiv1).resource -= resource;
            members.get(indiv2).resource -= hawkcost;
            System.out.print("Hawk/Hawk: Hawk: -" + resource + "\tHawk: -" + hawkcost + "\n");
            if (members.get(indiv1).resource <= 0) {
                members.get(indiv1).status = "DEAD";
                System.out.print("Hawk " + indiv1 + " has died!\n");
            }
            if (members.get(indiv2).resource <= 0) {
                members.get(indiv2).status = "DEAD";
                System.out.print("Hawk " + indiv2 + " has died!\n");
            }
            System.out.print("Individual " + indiv1 + "=" + members.get(indiv1).resource + "\tIndividual " + indiv2 + "=" + members.get(indiv2).resource + "\n");
        }
        return members;
    }

    /*
    *   Simulates the Logic of Animal Conflict to be repeated X times depending on what menu options are chosen by the
    *   user
    *
    *   @param population - the total population
    *   @param percent - the percentage of the population that are hawks
    *   @param resource - the value of resources for a given interaction
    *   @param hawkcost - the cost of the Hawk-Hawk interaction
    *   @param members - the current list of all individuals both alive and dead
    *
    *   @returns members - the newly updated list of all individuals after performing one step of the Logic
    */
    private static ArrayList<Birds> simulate(int population, int percent, int resource, int hawkcost, ArrayList<Birds> members){
        Random r1 = new Random();

        int indiv1 = r1.nextInt(members.size());
        int indiv2 = r1.nextInt(members.size());
        if(indiv1 == indiv2){
            indiv1 = r1.nextInt(members.size());
        }

        if(members.get(indiv1).status.equals("DEAD")){
            indiv1 = r1.nextInt(members.size());
        }
        else if(members.get(indiv2).status.equals("DEAD")){
            indiv2 = r1.nextInt(members.size());
        }
        else {
            if (members.get(indiv1).status.equals("Dove") && members.get(indiv2).status.equals("Dove")) {
                members.get(indiv1).resource += resource / 2;
                members.get(indiv2).resource += resource / 2;
            } else if (members.get(indiv1).status.equals("Dove") && members.get(indiv2).status.equals("Hawk")) {
                members.get(indiv2).resource += resource;
            } else if (members.get(indiv1).status.equals("Hawk") && members.get(indiv2).status.equals("Dove")) {
                members.get(indiv1).resource += resource;
            } else if (members.get(indiv1).status.equals("Hawk") && members.get(indiv2).status.equals("Hawk")) {
                members.get(indiv1).resource -= resource;
                members.get(indiv2).resource -= hawkcost;
                if (members.get(indiv1).resource <= 0) {
                    members.get(indiv1).status = "DEAD";
                }
                if (members.get(indiv2).resource <= 0) {
                    members.get(indiv2).status = "DEAD";
                }
            }
        }
        return members;
    }

}
