import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


/**
 * @author Juan Bautista Berretta
 * @author Manickam Manickam
 *
 */


public class BaconNumber {
    
    public static void main(String[] args) throws MalformedURLException, IOException {
	    
	//the default list is the imdb top 250 list
	Scanner sc = new Scanner(new URL("http://cs.oberlin.edu/~gr151/imdb/imdb.top250.txt").openStream());
	Graph graph = new Graph();
	
	//create the graph
	while (sc.hasNextLine()) {
	    String line = sc.nextLine();
	    String[] array = line.split("\\|");
	    graph.addEdge(array[0], array[1], 0);
	    graph.addEdge(array[1], array[0], 1);
	}
	
	sc.close();
	
	//run dijkstra on it
	graph.dijkstra();
	Scanner in = new Scanner(System.in);
	
	//a nice entry prompt
	System.out.println();
	System.out.print("Welcome to the Kevin Bacon Game! The current database is the imdb top250."
	+ " You can enter command such as find, recenter, avgdist, table, and topcenter. For the commands"
	+ " find and recenter, you should follow the command with the name of the actor you want to complete the command"
	+ " on. For topcenter, you should follow the command with an integer of the number of top centers you want listed."
	+ " Type 'quit' if you want to exit the game.");
	System.out.println();
	System.out.println();
	
	
	//a while loop that allows gameplay
	while (in.hasNextLine()) {
	    String input = in.nextLine();
	    Scanner line = new Scanner(input);
	    
	    //the first thing entered is always the command
	    //we have handled silly input
	    String first = line.next();
	   
	    if (first.equals("find")) {
		String build = "";
		while (line.hasNext()) {
		    build += line.next() + " ";
		}
		build = build.trim();
		graph.printPath(build);
		System.out.println();
		System.out.println();
	    }
	    
	    else if (first.equals("recenter")) {
		String build = "";
		while (line.hasNext()) {
		    build += line.next() + " ";
		}
		build = build.trim();
		graph.recenter(build);
		System.out.println();
		System.out.println("The new center is now " + build);
		System.out.println();
		System.out.println();
	    }
	    
	    else if (first.equals("avgdist")) {
		System.out.println(graph.avgdist());
		System.out.println();
		System.out.println();
	    }
	    
	    else if (first.equals("table")) {
		graph.table();
		System.out.println();
		System.out.println();
	    }
	    
	    else if (first.equals("topcenter")) {
		String build = "";
		while (line.hasNext()) {
		    build += line.next() + " ";
		}
		build = build.trim();
		if (build != "")
		    graph.topcenter(Integer.parseInt(build));
		else
		    graph.topcenter(5);
		System.out.println();
		System.out.println();
	    }
	    
	    else if (first.equals("quit")) {
		in.close();
		line.close();
		System.out.println("Thanks for playing!");
		System.out.println();
		break;
	    }
	    
	    else if(first != "topcenter" || first != "quit" || first != "find" 
		    || first != "avgdist" || first != "table" || first != "recenter") {
		System.out.println("You must enter valid input!");
		System.out.println();
		System.out.println();
		
		line.close();
	    }
	}
    }

}
