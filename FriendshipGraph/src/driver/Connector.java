package driver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Connector
{
	private HashSet<Person> connectors;
	private HashMap<Person, Vertex> neighbors;
	private Graph graph;
	int size , dfsNum;
	private boolean startConnector;
	private boolean[] isConnector , visitedP;


	public Connector(Graph graph)
	{
		connectors = new HashSet<Person>();
		this.size = graph.getSize();
		this.startConnector = false;
		this.neighbors = new HashMap<Person, Vertex>();
		this.graph = graph;
	}


	public void findConnectors()
	{
		visitedP = new boolean[size];
		for(int p = 0; p < size; p++)
		{
			if (!visitedP[p])
			{
				Person person = graph.getPerson(p);
				this.dfsNum = 1;
				Vertex vertex = new Vertex(this.dfsNum, this.dfsNum);
				neighbors.put(person, vertex);
				isConnector = new boolean[size];
				conDFS(person, p);
			}
		}
	}


	private void conDFS(Person p, int start)
	{
		boolean debug = p.toString().equals("tom");
		visitedP[p.getVertexNumber()] = true;
		visitNeighbors(p, start);
		if (p.getVertexNumber() != start)
		{
			standardConnectorCheck(p);
		}
		else
		{
			startConnectorCheck(p);
		}
	}


	private void startConnectorCheck(Person p)
	{
		Vertex v = neighbors.get(p);
		if (isConnector[v.getDfsNum() - 1] == true)
		{
			return;
		}
		if (startConnector && isConnector[v.getDfsNum() - 1] == false)
		{
			isConnector[v.getDfsNum() - 1] = true;
			connectors.add(p);
		}
		else
		{
			startConnector = true;
		}
	}


	private void standardConnectorCheck(Person p)
	{
		for(Iterator<Person> iterator = p.getFriends().iterator(); iterator.hasNext();)
		{
			Person p2 = iterator.next();
			Vertex v = neighbors.get(p);
			Vertex w = neighbors.get(p2);
			if (v.getDfsNum() <= w.getBack() && !isConnector[v.getDfsNum() - 1])
			{
				connectors.add(p);
				isConnector[v.getDfsNum() - 1] = true;
				break;
			}
		}
	}


	private void visitNeighbors(Person p, int start)
	{
		for(Iterator<Person> iterator = p.getFriends().iterator(); iterator.hasNext();)
		{
			Person person = iterator.next();
			Vertex v = neighbors.get(p);
			int num = person.getVertexNumber();
			if (visitedP[num])
			{
				Vertex w = neighbors.get(graph.getPerson(num));
				v.setBack(Math.min(v.getBack(), w.getDfsNum()));
				continue;
			}
			visitedP[num] = true;
			Vertex w = new Vertex(++dfsNum, dfsNum);
			neighbors.put(graph.getPerson(num), w);
			conDFS(graph.getPerson(num), start);
			if (v.getDfsNum() > w.getBack())
			{
				v.setBack(Math.min(v.getBack(), w.getBack()));
			}
		}
	}


	public HashSet<Person> getConnectors()
	{
		return this.connectors;
	}

	public class Vertex
	{
		private int dfsNum , back;


		public Vertex(int dfsNum, int back)
		{
			this.dfsNum = dfsNum;
			this.back = back;
		}


		public int getDfsNum()
		{
			return this.dfsNum;
		}


		public int getBack()
		{
			return this.back;
		}


		public void setBack(int back)
		{
			this.back = back;
		}


		public String toString()
		{
			return " " + this.dfsNum + " " + this.back;
		}
	}
}
