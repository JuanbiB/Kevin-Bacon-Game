import static org.junit.Assert.assertEquals;

import org.junit.Test;


/**
 * this test class is not very difficult because the entire graph implementation is based
 * off of Mark Allen Weiss' implementation in Chapter 14 of his textbook.
 * @author mmanicka
 *
 */

public class GraphTest {

    @Test
    public void testGraphCenter() {
	Graph G = new Graph();
	assertEquals("default center ", "Kevin Bacon (I)", G.center);
    }
    
    @Test
    public void testGraph() {
	Graph g = new Graph();
	assertEquals("Size after construction ", 0, g.getSize());
    }
    
    /**
     * I want to mention that the testing of avgdist, table, topcenter, recenter, and
     * find were tests that we did and compared to Ben's results that he specified in
     * the lab10 assignment instructions. We chose to just do basic tests on the default center
     * and the construction of the graph. Some things we noticed when running these methods were noted in the README
     * file.
     */

}
