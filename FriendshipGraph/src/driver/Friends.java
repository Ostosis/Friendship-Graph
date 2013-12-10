package driver;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Final CS112 project. Friends.java is the main driver for this project.
 * 
 * @author Anton Shor
 * @author Kevin Wysoczynski
 * */
public class Friends
{
	private static final char CONNECTORS = 'o';
	private static final char CLIQUES = 'c';
	private static final char SHORTEST_PATH = 'h';
	private static final char SCHOOL = 's';
	private static final String OPTION_CHARS = "shcoq";
	private static final char QUIT_CHAR = 'q';
	private static final String CORRECTIVE_PROMPT = "You must enter one of the following characters: s, h, c, o, or q.";
	private static final String SECONDARY_PROMPT_2 = "(s)tudents at a school, s(h)ortest intro chain, (c)liques at school, c(o)nnectors, or (q)uit. \n";
	private static final String SECONDARY_PROMPT_1 = "\tChoose action: ";
	private static final String INITIAL_PROMPT = "Enter the name of the graph file.";
	private static Scanner scanner;


	public static void main(String[] args)
	{
		Graph graph = new Graph();
		graph.getFileGraph(initialization());
		char response = '0';
		while (response != QUIT_CHAR)
		{
			System.out.print(SECONDARY_PROMPT_1);
			System.out.print(SECONDARY_PROMPT_2);
			response = scanner.next().toLowerCase().charAt(0);
			while (OPTION_CHARS.indexOf(response) == -1)
			{
				System.out.print(CORRECTIVE_PROMPT);
				response = scanner.next().toLowerCase().charAt(0);
			}
			switch (response)
			{
				case SCHOOL:
					System.out.println("Enter the name of the school: ");
					String sch = scanner.next().toLowerCase();
					sch += scanner.nextLine().toLowerCase();
					System.out.println(graph.subgraph(sch));
					break;
				case SHORTEST_PATH: // TODO person existence check?
					System.out.println("Enter the starting person's name: ");
					String start = scanner.next().toLowerCase();
					start += scanner.nextLine().toLowerCase();
					System.out.println("Enter the ending person's name: ");
					String end = scanner.next().toLowerCase();
					end += scanner.nextLine().toLowerCase();
					ArrayList<Person> sp = graph.shortestPath(start, end);
					if (sp == null)
					{
						System.out.println("There is no path between these two people.\n\n");
					}
					else
					{
						System.out.println(shortestPathString(sp) + "\n\n");
					}
					break;
				case CLIQUES:
					System.out.println("Enter the name of the school in which to find cliques: ");
					String school = scanner.next().toLowerCase();
					school += scanner.nextLine().toLowerCase();
					HashSet<Graph> cliques = graph.cliques(school);
					printCliques(cliques);
					break;
				case CONNECTORS:
					HashSet<Person> connectors = graph.connectors();
					printConnectors(connectors);
					break;
				case QUIT_CHAR:
					break;
				default:
					break;
			}
		}
		scanner.close();
		System.exit(0);
	}


	private static String shortestPathString(ArrayList<Person> sp)
	{
		String string = "";
		boolean notFirst = false;
		for(int i = 0; i < sp.size(); i++)
		{
			if (notFirst)
			{
				string += "--";
			}
			notFirst = true;
			string += sp.get(i);
		}
		return string;
	}


	private static void printConnectors(HashSet<Person> connectors)
	{
		if (connectors.isEmpty())
		{
			System.out.println("This graph contains no connectors.\n\n");
			return;
		}
		boolean notFirst = false;
		for(Iterator<Person> iterator = connectors.iterator(); iterator.hasNext();)
		{
			if (notFirst)
			{
				System.out.print(", ");
			}
			notFirst = true;
			Person person = iterator.next();
			System.out.print(person);
		}
		System.out.println("\n\n");
	}


	private static void printCliques(HashSet<Graph> cliques)
	{
		int i = 1;
		if (cliques == null)
		{
			System.out.println("No students attend this school.\n\n");
			return;
		}
		for(Iterator<Graph> iterator = cliques.iterator(); iterator.hasNext();)
		{
			Graph clique = iterator.next();
			System.out.print("Clique " + i++ + ":\n\n");
			System.out.print(clique.toString() + "\n\n");
		}
	}


	private static Scanner initialization()
	{
		System.out.println(INITIAL_PROMPT);
		scanner = new Scanner(System.in);
		Scanner init = null;
		String filename = scanner.next();
		try
		{
			init = new Scanner(new File(filename));
		}catch (Exception e)
		{
			scanner.close();
			System.out.println("File not found.");
			scanner = initialization();
		}
		return init;
	}
}
