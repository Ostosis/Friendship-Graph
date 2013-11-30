package driver;

import java.util.HashSet;

public class Person
{
	private String name , school; // name, school both lower-case
	private HashSet<Person> friends;
	private Integer numVert;


	public Person(String name, String school, Integer numVert)
	{
		this.name = name;
		this.school = school;
		this.numVert = numVert;
		friends = new HashSet<Person>();
	}


	public Integer getVertexNumber()
	{
		return numVert;
	}


	public void addFriend(Person person)
	{
		friends.add(person);
	}


	public String getName()
	{
		return name;
	}


	public String getSchool()
	{
		return school;
	}


	public HashSet<Person> getFriends()
	{
		return friends;
	}


	public String toString()
	{
		return this.getName();// + ", " + this.getVertexNumber();
	}
}
