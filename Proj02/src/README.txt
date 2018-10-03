INSTRUCTIONS ON HOW TO RUN THIS PROGRAM:
1.) 	Edit command line parameters to include: total population, percentage of hawks, the value of resources, and the Hawk-Hawk cost. 
	Any values not given that are not the pop. number are assigned default values.
2.) 	When run, the program will boot up a menu on the console with 8 commands. Each command has a description, and the user can
	choose what command to run by typing the number of the menu option followed by ENTER
3.) 	Whatever selection the user made as an option will either perform a function, display output, or both.
4.) 	If the simulation has concluded, the user may only: Display Starting Stats, Display Individuals and Points, and Display Sorted.
5.) 	When finished, type "8" followed by ENTER in the console at the menu screen to terminate the program.

For this program, I had decided to program the project in Java, as I am most familiar with the language and it was easier to visulize the 
requirements for myself personally. That being said, in my opinion it would be much easier and efficient to code the project in Python,
as the use of tuples would reduce the amount of computation time needed to assign an ArrayList of objects, and eliminate the need for another
class within the program.

After running the simulation a couple of times, I noticed that both the Hawks and the Doves do not employ an ESS strategy, as Hawks will only
kill other Hawks which makes the Doves more successful than the Hawks at the end of the simulation, unless one hawk remains. The Doves are
also not employing an ESS, as their only tactic is to retreat in combat which results in the smallest payoff (resource/2 to be exact).

In changing the initial parameters, I noticed that there were many less fatalities once the population of hawks decreased, as there are seldom
hawks to fight with one another, however the total resource pool amongst individuals were lower and roughly the same as opposed to one or
two individuals dominating the resource. In terms of Evolutionary Stable Strategies, I have learned that the most successful tactic to this
approach is to use both "conventional" and "dangerous" tactics, as it will yield the highest payoff amongst other species. This applies
particularly well to game theory in Intelligent Systems, as the payoff of using a mixed strategy is typically the greatest when two players
are facing each other, and the whole point of games and game theory is to maximize payoff when faced with a set of "turns" or choices.
