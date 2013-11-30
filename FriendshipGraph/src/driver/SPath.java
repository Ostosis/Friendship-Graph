package driver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SPath
{
	String start , end;
	int size;
	HashMap<String, Person> people;
	String[] names;
	ArrayList<Person> path;
	boolean[] visited;
	int[] previous;


	public SPath(String start, String end, int size, HashMap<String, Person> people, String[] names)
	{
		this.start = start;
		this.end = end;
		this.size = size;
		this.people = people;
		this.names = names;
		this.visited = new boolean[size];
		this.previous = new int[size];
	}


	public void makePath()
	{
		if (!people.containsKey(start) || !people.containsKey(end))
		{
			path = null;
			return;
		}
		path = new ArrayList<Person>();
		int start = people.get(this.start).getVertexNumber();
		int end = people.get(this.end).getVertexNumber();
		Person curPer = null;
		ArrayDeque<Person> queue = new ArrayDeque<Person>();
		queue.add(people.get(this.end));
		visited[end] = true;
		previous[end] = -1;
		while (!queue.isEmpty())
		{
			curPer = queue.poll();
			int curNum = curPer.getVertexNumber();
			if (start == curNum)
			{
				populatePath(curNum);
				return;
			}
			else
			{
				for(Iterator<Person> iterator = curPer.getFriends().iterator(); iterator.hasNext();)
				{
					Person person = iterator.next();
					int num = person.getVertexNumber();
					if (visited[num])
					{
						continue;
					}
					visited[num] = true;
					queue.add(person);
					previous[person.getVertexNumber()] = curNum;
				}
			}
		}
		path = null;
	}


	private void populatePath(int curNum)
	{
		if (curNum == -1)
		{
			return;
		}
		else
		{
			path.add(people.get(names[curNum]));
			populatePath(previous[curNum]);
		}
	}


	public ArrayList<Person> getPath()
	{
		return this.path;
	}
}
