package junit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

import driver.Graph;

public class test
{
	@Test
	/**Tests that the returned string representation has the same number of lines as the original file.
	 */
	public void subgraphTest1() throws IOException
	{
		Graph graph = new Graph();
		graph.getFileGraph(new Scanner(new File("Graph1.txt")));
		byte[] encoded = Files.readAllBytes(Paths.get("Graph1.txt"));
		String expected = new String(encoded);
		String actual = graph.toString();
		String[] expect = expected.split("\r\n|\r|\n");
		String[] actua = actual.split("\r\n|\r|\n");
		Assert.assertEquals(expect.length, actua.length);
	}


	@Test
	/**Tests that the substring is correct.
	 * */
	public void subgraphTest2() throws IOException
	{
		Graph graph = new Graph();
		graph.getFileGraph(new Scanner(new File("Graph1.txt")));
		byte[] encoded = Files.readAllBytes(Paths.get("Graph2.txt"));
		String expected = new String(encoded);
		String actual = graph.subgraph("rutgers").toString();
		String[] expect = expected.split("\r\n|\r|\n");
		String[] actua = actual.split("\r\n|\r|\n");
		Assert.assertEquals(expect.length, actua.length);
	}


	@Test
	/**Tests that substring works for case with just 1 person.
	 * */
	public void subgraphTest3() throws IOException
	{
		Graph graph = new Graph();
		graph.getFileGraph(new Scanner(new File("Graph1.txt")));
		byte[] encoded = Files.readAllBytes(Paths.get("Graph3.txt"));
		String expected = new String(encoded);
		String actual = graph.subgraph("cornell").toString();
		String[] expect = expected.split("\r\n|\r|\n");
		String[] actua = actual.split("\r\n|\r|\n");
		Assert.assertEquals(expect.length, actua.length);
	}


	@Test
	/**Tests that substring works for 0 people
	 * */
	public void subGraphtest4() throws IOException
	{
		Graph graph = new Graph();
		graph.getFileGraph(new Scanner(new File("Graph1.txt")));
		String actual = graph.subgraph("aba").toString().trim();
		Assert.assertEquals("0", actual);
	}


	@Test
	/**Tests that shortest path works for general case
	 * */
	public void shortestPathTest1() throws FileNotFoundException
	{
		Graph graph = new Graph();
		graph.getFileGraph(new Scanner(new File("Graph1.txt")));
		String actual = graph.shortestPath("sam", "Aparna").toString();
		Assert.assertEquals("[sam, jane, bob, samir, aparna]", actual);
	}


	@Test
	/**Tests that shortest path works for non-connected case.
	 * */
	public void shortestPathTest2() throws FileNotFoundException
	{
		Graph graph = new Graph();
		graph.getFileGraph(new Scanner(new File("Graph1.txt")));
		Object actual = graph.shortestPath("Heather", "Michele");
		Assert.assertNull(actual);
	}


	@Test
	/**Tests that shortest path still works with multiple possible paths
	 * */
	public void shortestPathTest3() throws FileNotFoundException
	{
		Graph graph = new Graph();
		graph.getFileGraph(new Scanner(new File("Graph4.txt")));
		String actual = graph.shortestPath("Kaitlin", "aparna").toString();
		boolean test = actual.equals("[kaitlin, jane, bob, aparna]") || actual.equals("[kaitlin, nick, ricardo, aparna]");
		Assert.assertTrue(test);
	}


	@Test
	/**Tests that shortest path still works with one thing
	 * */
	public void shortestPathTest4() throws FileNotFoundException
	{
		Graph graph = new Graph();
		graph.getFileGraph(new Scanner(new File("Graph1.txt")));
		String actual = graph.shortestPath("aparna", "aparna").toString();
		Assert.assertEquals("[aparna]", actual);
	}


	@Test
	/**Tests that shortest path returns null if the input doesn't contain one or both of the people 
	 * */
	public void shortestPathTest5() throws FileNotFoundException
	{
		Graph graph = new Graph();
		graph.getFileGraph(new Scanner(new File("Graph1.txt")));
		Object actual = graph.shortestPath("aparna", "asdasd");
		Assert.assertNull(actual);
		actual = graph.shortestPath("asdasd", "asdasd");
		Assert.assertNull(actual);
	}
}
