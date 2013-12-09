package driver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

public class Connector
{
	private HashSet<Person> connectors;
	private Graph graph;
	int size , dfsNum;
	private boolean[] isConnector , visitedP;


	public Connector(Graph graph)
	{
		connectors = new HashSet<Person>();
		this.size = graph.getSize();
		isConnector = new boolean[size];
		this.graph = graph;
	}


	public void findConnectors()
	{
		visitedP = new boolean[size];
		for(int v = 0; v < size; v++)
		{
			if (!visitedP[v])
			{
				Person person = graph.getPerson(v);
				this.dfsNum = 1;
				Vertex vertex = new Vertex(this.dfsNum, this.dfsNum, person);
				conDFS(vertex, v);
			}
		}
	}


	private void conDFS(Vertex v, int start)
	{
		visitedP[v.getDfsNum()] = true;
		visitNeighbors(v, start);
		if (v.getPerson().getVertexNumber() != start)
		{
			standardConnectorCheck(v);
		}
		else
		{
			startConnectorCheck(v);
		}
	}


	private void startConnectorCheck(Vertex v)
	{
		
	}


	private void standardConnectorCheck(Vertex v)
	{
		for(Iterator<Entry<Integer, Vertex>> iterator = v.getNeighbors().entrySet().iterator(); iterator.hasNext();)
		{
			Vertex w = iterator.next().getValue();
			if (v.getDfsNum() <= w.getBack() && !isConnector[v.getDfsNum() - 1])
			{
				connectors.add(v.person);
				isConnector[v.getDfsNum() - 1] = true;
				break;
			}
		}
	}


	private void visitNeighbors(Vertex v, int start)
	{
		for(Iterator<Person> iterator = v.getPerson().getFriends().iterator(); iterator.hasNext();)
		{
			Person person = iterator.next();
			int num = person.getVertexNumber();
			if (visitedP[num])
			{
				Vertex w = v.getVertex(num);
				v.setBack(Math.min(v.getBack(), w.getDfsNum()));
				continue;
			}
			visitedP[num] = true;
			Vertex w = new Vertex(dfsNum, dfsNum, person);
			dfsNum++;
			w.addNeighbor(v);
			v.addNeighbor(w);
			conDFS(w, start);
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
		private Person person;
		private HashMap<Integer, Vertex> neighbors;


		public Vertex(int dfsNum, int back, Person person)
		{
			this.dfsNum = dfsNum;
			this.back = back;
			this.person = person;
			this.neighbors = new HashMap<Integer, Vertex>();
		}


		public Vertex getVertex(int num)
		{
			return this.neighbors.get(Integer.valueOf(num));
		}


		public HashMap<Integer, Vertex> getNeighbors()
		{
			return this.neighbors;
		}


		public void addNeighbor(Vertex w)
		{
			this.neighbors.put(w.getPerson().getVertexNumber(), w);
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


		public Person getPerson()
		{
			return this.person;
		}
	}
}
