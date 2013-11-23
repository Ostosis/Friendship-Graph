package junit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import junit.framework.Assert;

import org.junit.Test;

import driver.Graph;

public class test
{
	@Test
	public void test1() throws IOException
	{
		Graph graph = new Graph();
		graph.getFileGraph(new Scanner(new File("Graph1.txt")));
		System.out.println("test 1 ");
		byte[] encoded = Files.readAllBytes(Paths.get("Graph1.txt"));
		String expected = new String(encoded);
		String actual = graph.toString();
		System.out.println(expected.length());
		System.out.println(actual.length());
		System.out.println(expected);
		System.out.println(actual);
		Assert.assertEquals(expected.length(), actual.length());
	}
}
