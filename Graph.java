import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * @author Manickam Manickam

 * @author Juan Bautista Berretta
 */

public class Graph {	
    
    //class variables
    private HashMap<String, Vertex> vertexMap = new HashMap<String, Vertex>();
    String center = "Kevin Bacon (I)";
    
    /**
     * prints out the top n centers of the given database
     * @param n the number of the centers to be printed out
     */
    public void topcenter(int n) {
	//create an iterator to go through vertices
	Iterator<Vertex> iter = vertexMap.values().iterator();
	//a tree map to sort the avg distance and its center
	TreeMap<Double, String> TC = new TreeMap<Double, String>();
	//an arraylist to check if a given vertex is already iterated thru
	ArrayList<Vertex> arr = new ArrayList<Vertex>();
	while (iter.hasNext()) {
	    Vertex v = iter.next();
	    if (!arr.contains(v)) {
		Iterator<Edge> e = v.adj.iterator();
		if (e.next().cost == 0) { //check to see if the vertices outbounds
		    //are movies (they should be)
		    //run an average distance
		    if (v.dist != Double.POSITIVE_INFINITY) {
			String s = avgdist();
			String[] array = s.split(": ");
			TC.put(Double.parseDouble(array[0]), center);
		    }
		}
	    }
	    //add computed vertex into list, recenter the graph
	    arr.add(v);
	    recenter(v.name);
	}
	
	//iterate through the tree map's keys and values and output them in a
	//clean format
	Iterator<Double> key = TC.keySet().iterator();
	Iterator<String> val = TC.values().iterator();
	int counter = 0;
	
	while (counter < n) {
	    System.out.printf("%-20s %-20s %n", key.next(), val.next());
	    counter++;
	}
    }
    
    
    /**
     * outputs a table of the distances and how many vertices in the graph have
     * that listed distance.
     */
    public void table() {
	//header of the table
	System.out.println("Table of distances for " + center);
	//create a treemap for sorting purposes
	TreeMap<Double, Integer> DV = new TreeMap<Double, Integer>();
	//iterate through the vertices of the graph
	Iterator<Vertex> iter = vertexMap.values().iterator();
	//the value keeps track of the respective count for each distance
	int value = 0;
	//iteratre thru vertices
	while(iter.hasNext()) {
	    Vertex v = iter.next();
	    if (DV.containsKey(v.dist)) {
		Integer c = DV.get(v.dist);
		if (c == null)
		    c = 0;
		c++;
		DV.put(v.dist, c);
	    }
	    else
		DV.put(v.dist, value);
	}
	
	//iterate thru the treemap's keys and values and output them in a
	//clean format
	Iterator<Double> dist = DV.keySet().iterator();
	Iterator<Integer> arr = DV.values().iterator();
	while (dist.hasNext()) {
	    Double d = dist.next();
	    Integer a = arr.next();
	    if (d.equals(Double.POSITIVE_INFINITY)) {
		System.out.printf("%-3s %10.3s %n", "Unreachable:", a);
	    }
	    else
		System.out.printf("%-3s %-5s %10.3s %n", "Number", d.intValue() + ":", a);
	}
    }
    
    
    /**
     * return a string representation of the center and its avg distance to all other
     * vertices
     * @return a formatted string of the center and its avg distance
     */
    public String avgdist() {
	//tracker variables
	int reachable = 0;
	int unreachable = 0;
	int sum = 0;
	
	//iterate thru each vertex
	Iterator<Vertex> iter = vertexMap.values().iterator();
	while(iter.hasNext()) {
	    Vertex v = iter.next();
	    //if its distance is infinity, it is unreachable
	    if (v.dist == Double.POSITIVE_INFINITY)
		unreachable++;
	    else {
		reachable++;
		sum += v.dist;
	    }
	}
	
	return ((float) sum/reachable + " : " + center + " (" + reachable + ", " + unreachable + ")" );
    }
    
    /**
     * resets the center to the specified string name
     * @param new_center the new center to be set
     */
    public void recenter(String new_center) {
	center = new_center;
	//after resetting, we need to run dijkstra's algorithm
	this.dijkstra();
    }
    
    public void addEdge(String src, String dest, double cost) {
	Vertex v = getVertex(src);
	Vertex w = getVertex(dest);
	v.adj.add(new Edge(w, cost));
    }
    
    public void printPath(String destName) {
	Vertex w = vertexMap.get(destName);
	if (w == null) {
	    throw new NoSuchElementException();
	}
	else if (w.dist == Double.POSITIVE_INFINITY)
	    System.out.println(destName + " is unreachable");
	else {
	    printPath(w);
	}
	System.out.print(" (" + (int) (w.dist) + ")");
    }
    
    public void dijkstra() {
	PriorityQueue<Path> PQ = new PriorityQueue<Path>();
	
	Vertex start = vertexMap.get(center);
	if (start == null) {
	    throw new NoSuchElementException("Start vertex not found");
	}
	
	clearAll();
	PQ.add(new Path(start, 0));
	start.dist = 0;
	
	int counter = 0;
	while (!PQ.isEmpty() && counter < vertexMap.size()) {
	    Path vrec = PQ.remove();
	    Vertex v = vrec.dest;
	    if (v.scratch != 0)
		continue;
	    
	    v.scratch = 1;
	    counter++;
	    
	    for (Edge e: v.adj) {
		Vertex w = e.dest;
		double cvw = e.cost;
		
		if (cvw < 0)
		    throw new RuntimeException();
		
		if (w.dist > v.dist + cvw) {
		    w.dist = v.dist + cvw;
		    w.prev = v;
		    PQ.add(new Path(w, w.dist));
		}
	    }
	}
    }
    
    public int getSize() {
	return vertexMap.size();
    }
    
    private Vertex getVertex(String name) {
	Vertex v = vertexMap.get(name);
	if (v == null) {
	    v = new Vertex(name);
	    vertexMap.put(name, v);
	}
	return v;
    }
    
    private void printPath(Vertex dest) {
	if (dest.prev != null) {
	    printPath(dest.prev);
	    System.out.print(" -> ");
	}
	System.out.print(dest.name);
    }
    
    private void clearAll() {
	for (Vertex v: vertexMap.values()) {
	    v.reset();
	}
    }
    
    //edge class
    class Edge {
	public Vertex dest;
	public double cost;
	
	public Edge(Vertex d, double c) {
	    dest = d;
	    cost = c;
	}
    }

    // vertex class
    class Vertex {
	public String name;
	public LinkedList<Edge> adj;
	public double dist;
	public Vertex prev;
	public int scratch;
	
	public Vertex (String name) {
	    this.name = name;
	    adj = new LinkedList<Edge>();
	    reset();
	}
	
	public void reset() {
	    dist = Double.POSITIVE_INFINITY; 
	    prev = null; 
	    scratch = 0;
	}
	
    }
    
    //path class
    class Path implements Comparable<Path> {
	public Vertex dest;
	public double cost;
	
	public Path(Vertex d, double c) {
	    dest = d;
	    cost = c;
	}
	
	public int compareTo(Path rhs) {
	    double otherCost = rhs.cost;
	    return cost < otherCost ? -1: cost > otherCost ? 1: 0;
	}
    }

}
