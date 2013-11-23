package junit;

import java.io.File;
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
	public void test1() throws IOException
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
	public void test2() throws IOException
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
	public void test3() throws IOException{
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
	public void test4() throws IOException{
		Graph graph = new Graph();
		graph.getFileGraph(new Scanner(new File("Graph1.txt")));
		String actual = graph.subgraph("aba").toString().trim();
		Assert.assertEquals("0",actual);
	}
	
	
}
