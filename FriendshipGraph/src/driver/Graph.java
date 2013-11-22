package driver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Graph
{
	private int size; // number of vertices in graph
	private HashMap<String, Person> hm; // name, person
	private HashMap<Integer, String> in; // vertex number, name


	/**
	 * This class holds the representation of our graph.
	 * 
	 * @param scanner
	 *            The scanner containing the information about the graph.
	 * */
	public Graph()
	{
	}


	public void getGraph(Scanner scanner)
	{
		setVertices(scanner);
		setEdges(scanner);
	}


	/**
	 * Sets the two-way connections between different people.
	 * 
	 * @param scanner
	 *            The scanner at the point where the connection information begins.
	 * */
	private void setEdges(Scanner scanner)
	{
		while (scanner.hasNext())
		{
			String line = scanner.nextLine();
			String name1 = line.substring(0, line.indexOf('|')).toLowerCase();
			String name2 = line.substring(name1.length() + 1).toLowerCase();
			hm.get(name1).addFriend(hm.get(name2));
			hm.get(name2).addFriend(hm.get(name1));
		}
	}


	/**
	 * Creates our Person nodes.
	 * 
	 * @param scanner
	 *            The scanner at the beginning of the file.
	 * */
	private void setVertices(Scanner scanner)
	{
		size = scanner.nextInt();
		for(int i = 0; i < size; i++)
		{
			String line = scanner.nextLine();
			String name = line.substring(0, line.indexOf('|')).toLowerCase();
			String school = null;
			switch (line.charAt(name.length() + 1))
			{
				case 'n':
					break;
				case 'y':
					school = line.substring(name.length() + 3).toLowerCase();
					break;
				default:
					break;
			}
			Integer vertexNum = Integer.valueOf(i + 1);
			hm.put(name, new Person(name, school, vertexNum));
			in.put(vertexNum, name);
		}
	}


	public String toString()
	{
		return size() + verticesToString() + edgesToString();
	}


	private String size()
	{
		String size = Integer.toString(this.size);
		return size;
	}


	private String verticesToString()
	{
		// TODO Auto-generated method stub
		return null;
	}


	private String edgesToString()
	{
		// TODO Auto-generated method stub
		return null;
	}


	public Graph subgraph(String school)
	{
		// TODO
		return null;
	}


	public HashSet<Person> shortestPath(String start, String end)
	{
		// TODO
		return null;
	}


	public HashSet<Person> cliques(String school)
	{
		// TODO note: if clique is not in core graph, it will be empty
		return null;
	}


	public String connectors()
	{
		// TODO
		return null;
	}
}
