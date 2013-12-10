package driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

public class Graph
{
	private int size; // number of vertices in graph
	private HashMap<String, Person> people; // name, person
	private String[] names;


	/**
	 * This class holds the representation of our graph.
	 * 
	 * @param scanner
	 *            The scanner containing the information about the graph.
	 * */
	public Graph()
	{
		people = new HashMap<String, Person>();
	}


	public void getFileGraph(Scanner scanner)
	{
		setVertices(scanner);
		setEdges(scanner);
		scanner.close();
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
			String line = scanner.nextLine().toLowerCase();
			String name1 = line.substring(0, line.indexOf('|'));
			String name2 = line.substring(name1.length() + 1);
			people.get(name1).addFriend(people.get(name2));
			people.get(name2).addFriend(people.get(name1));
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
		this.size = Integer.parseInt(scanner.nextLine());
		this.names = new String[size];
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
			Integer vertexNum = Integer.valueOf(i);
			people.put(name, new Person(name, school, vertexNum));
			names[vertexNum] = name;
		}
	}


	public String toString()
	{
		return size() + verticesToString() + edgesToString();
	}


	private String size()
	{
		String size = Integer.toString(this.size) + "\n";
		return size;
	}


	public int getSize()
	{
		return this.size;
	}


	private String verticesToString()
	{
		String vertices = "";
		for(Iterator<Entry<String, Person>> iterator = people.entrySet().iterator(); iterator.hasNext();)
		{
			Entry<String, Person> entry = iterator.next();
			vertices += entry.getKey() + "|";
			Person person = entry.getValue();
			if (person.getSchool() == null)
			{
				vertices += "n\n";
			}
			else
			{
				vertices += "y|" + person.getSchool() + "\n";
			}
		}
		return vertices;
	}


	private String edgesToString()
	{
		String edges = "";
		boolean[][] record = new boolean[size][size];
		for(int numP = 0; numP < size; numP++)
		{
			String name = names[numP];
			Person person = this.getPerson(name);
			for(Iterator<Person> fIterator = person.getFriends().iterator(); fIterator.hasNext();)
			{
				Person friend = fIterator.next();
				int numF = friend.getVertexNumber();
				if (!record[numF][numP])
				{
					edges += name + "|" + friend.getName() + "\n";
					record[numP][numF] = true;
				}
			}
		}
		edges.trim();
		return edges;
	}


	public Graph subgraph(String school)
	{
		Graph graph = new Graph();
		graph.names = new String[this.size];
		Integer newSize = 0;
		for(Integer i = 0; i < size; i++)
		{
			String name = this.getName(i);
			Person person = this.getPerson(name);
			if (person.getSchool() == null || !person.getSchool().equals(school))
			{
				continue;
			}
			HashSet<Person> friends = person.getFriends();
			Person newPerson = graph.getPerson(name);
			if (newPerson == null)
			{
				newPerson = new Person(name, school, newSize++);
				graph.addPerson(newPerson);
			}
			for(Iterator<Person> iterator = friends.iterator(); iterator.hasNext();)
			{
				Person friend = iterator.next();
				if (friend.getSchool() != null && friend.getSchool().equals(school))
				{
					Person existingFriend = graph.getPerson(friend.getName());
					if (existingFriend != null)
					{
						newPerson.addFriend(graph.getPerson(friend.getName()));
					}
					else
					{
						Person newFriend = new Person(friend.getName(), school, newSize++);
						newPerson.addFriend(newFriend);
						newFriend.addFriend(newPerson);
						graph.addPerson(newFriend);
					}
				}
			}
		}
		graph.setSize(newSize);
		String[] temp = new String[newSize];
		System.arraycopy(graph.names, 0, temp, 0, newSize);
		graph.names = temp;
		return graph;
	}


	public void setSize(int size)
	{
		this.size = size;
	}


	private Person getPerson(String name)
	{
		return people.get(name);
	}


	public Person getPerson(int vertexNum)
	{
		return people.get(names[vertexNum]);
	}


	private String getName(Integer numVertex)
	{
		return names[numVertex];
	}


	private void addPerson(Person newPerson)
	{
		people.put(newPerson.getName(), newPerson);
		names[newPerson.getVertexNumber()] = newPerson.getName();
	}


	public ArrayList<Person> shortestPath(String start, String end)
	{
		SPath path = new SPath(start.toLowerCase(), end.toLowerCase(), size, people, names);
		path.makePath();
		return path.getPath();
	}


	public HashSet<Graph> cliques(String school)
	{
		// Kevin's Proposed Algorithm:
		// ~ Need a 'visited' array
		// Iterate through the graph until a person with the desired school is found
		// Generate the sub-group from this person and add it to the Hashset
		// DFS and collect names
		// Continue to iterate after a sub-group is completed, searching for more people from the
		// school
		// If found, check if this person has already been 'visited' from the appropriate array
		// If not, repeat DFS add to this group to the "Cliques" list
		// TODO: Correctly display Output
		HashSet<Graph> cliques = new HashSet<Graph>();
		ArrayList<Person> group = new ArrayList<Person>();
		boolean[] visited = new boolean[size];
		for(int i = 0; i < size; i++)
		{
			String name = this.getName(i);
			Person person = this.getPerson(name);
			if (person.getSchool() == null || (visited[i]) || !person.getSchool().equals(school))
			{
				continue;
			}
			else
			{
				// Do a Depth First Search starting at this name
				ArrayList<Person> dfsGroup = new ArrayList<Person>();
				group = schoolDFS(person, visited, dfsGroup, school);
				// Get group's actual size;
				Graph graph = new Graph();
				graph.setSize(group.size());
				graph.names = new String[this.size];
				HashMap<String, Person> thisClique = new HashMap<String, Person>();
				for(int p = 0; p < group.size(); p++)
				{
					Person newPerson = new Person(group.get(p).getName(), school, p);
					thisClique.put(newPerson.getName(), newPerson);
				}
				for(int k = 0; k < group.size(); k++)
				{
					Person checkFriends = group.get(k);
					for(Iterator<Person> iterator = checkFriends.getFriends().iterator(); iterator.hasNext();)
					{
						Person checkPerson = iterator.next();
						if (thisClique.get(checkPerson.getName()) != null)
						{
							thisClique.get(checkFriends.getName()).addFriend(thisClique.get(checkPerson.getName()));
						}
					}
					graph.addPerson(thisClique.get(checkFriends.getName()));
				}
				cliques.add(graph);
			}
		}
		if (cliques.isEmpty())
		{
			return null;
		}
		return cliques;
	}


	private ArrayList<Person> schoolDFS(Person person, boolean[] visited, ArrayList<Person> group, String school)
	{
		int vert = person.getVertexNumber();
		visited[vert] = true;
		group.add(person);
		for(Iterator<Person> iterator = person.getFriends().iterator(); iterator.hasNext();)
		{
			Person neighbor = iterator.next();
			if (!visited[neighbor.getVertexNumber()] && neighbor.getSchool() != null && neighbor.getSchool().equals(school))
			{
				schoolDFS(neighbor, visited, group, school);
			}
		}
		return group;
	}


	public HashSet<Person> connectors()
	{
		Connector con = new Connector(this);
		con.findConnectors();
		return con.getConnectors();
	}
}
