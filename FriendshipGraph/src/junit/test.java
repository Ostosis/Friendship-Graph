package junit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.Assert;
import org.junit.Test;

import driver.Graph;
import driver.Person;

public class test
{
	private static final int N = 30000;


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


	@Test
	/** Shortest Path test - 100000 people, 100 friends each, 25 schools, ~20% not affiliated with a school
	 * */
	public void efficiencyTest1() throws FileNotFoundException
	{
		Graph graph = new Graph();
		String source = "";
		source += N + "\n";
		StringBuilder sb = new StringBuilder(source);
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> schools = new ArrayList<String>();
		boolean[][] friends = new boolean[N][N];
		for(int i = 0; i < friends.length; i++)
		{
			friends[i][i] = true;
		}
		for(int i = 0; i < 25; i++)
		{
			String school = "";
			for(int j = 0; j < 5; j++)
			{
				school += (char) (Math.random() * 100 % 26 + 'a');
			}
			schools.add(school);
		}
		for(int i = 0; i < N; i++)
		{
			String person = "";
			for(int j = 0; j < 10; j++)
			{
				person += (char) (Math.random() * 100 % 26 + 'a');
			}
			sb.append((Math.random() * 100 >= 80) ? person + "|n" : person + "|y|" + schools.get((int) (Math.random() * 100) % 25));
			sb.append(System.getProperty("line.separator"));
			names.add(person);
		}
		for(int i = 0; i < N; i++)
		{
			String p1 = names.get(i);
			for(int j = 0; j < 100; j++)
			{
				int rand = (int) (Math.random() * N);
				while (friends[i][rand] == true)
				{
					rand = (int) (Math.random() * N);
				}
				friends[i][rand] = friends[rand][i] = true;
				sb.append(p1 + "|" + names.get(rand));
				sb.append(System.getProperty("line.separator"));
			}
		}
		Scanner scanner = new Scanner(sb.toString().trim());
		graph.getFileGraph(scanner);
		long beg = System.nanoTime();
		ArrayList<Person> sp = graph.shortestPath(names.get((int) (Math.random() * N)), names.get((int) (Math.random() * N)));
		System.out.println((System.nanoTime() - beg));
		beg = System.nanoTime();
		HashSet<Person> con = graph.connectors();
		System.out.println(con);
		System.out.println(System.nanoTime() - beg);
	}


	@Test
	/** Tests that the general case works, including disconnected graphs
	 * */
	public void connectorTest1() throws FileNotFoundException
	{
		Graph graph = new Graph();
		graph.getFileGraph(new Scanner(new File("Graph1.txt")));
		HashSet<Person> hs = graph.connectors();
		String expected = "jane, aparna, nick, michele, tom";
		for(Iterator<Person> iterator = hs.iterator(); iterator.hasNext();)
		{
			Person person = iterator.next();
			String actual = person.toString();
			Assert.assertTrue(expected.contains(actual));
			expected.replaceAll(actual, "");
		}
	}
}
