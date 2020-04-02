
// program to find the maze solution using Depth First Search

import java.math.*;
import java.io.*;
import java.util.*;
import javafx.util.Pair; 
import java.util.ArrayList; 

public class depth_first_search_algorithm {


	//Depth-First Search Algorithm that takes a Maze as its parameter

	public static void Depth_first_search(Maze maze )
	{
		System.out.println("This is the implementation of the Depth First Search Algorithm");
		System.out.println();
		
		//stack data structure implemented to save the values of the traveresed nodes
		Stack stack = new Stack();
		pair [] solutions = new pair[maze.maze.length*maze.maze[0].length];
		int solution_index=0;
		int nodes_expanded=0;
		int depth =0;
		int maximum_value= 0;
		int temporary_max=0;

		//here we push the start position node on to the stack

		pair start = new pair(maze.startX, maze.startY, -1,-1, depth);
		stack.push( start );

		temporary_max=temporary_max+1;
		if(temporary_max > maximum_value)
			maximum_value = temporary_max;
		while( !stack.isEmpty() )
		{
			pair curr = stack.pop();
			solutions[solution_index]= curr;
			solution_index=solution_index+1;
			temporary_max=temporary_max-1;
			if(curr.first == maze.endX && curr.second == maze.endY)
			{
				
				System.out.println("End has been located");
				System.out.println("Total Number of Nodes Expanded: " + nodes_expanded);
				System.out.println("Max tree depth searched: " + depth);
				System.out.println("Max frontier size: "+ maximum_value);
				System.out.println();
				solution_path(maze, solutions, curr);
				
				return;
			}
			
				// adding the neighbours of the node
				if( !maze.visited[curr.first][curr.second] )
				{
				//update the visited nodes
				maze.visited[curr.first][curr.second]=true;
				nodes_expanded=nodes_expanded+1;

				//adding up the neighboring node which is above
				if( movement_predictor(maze, curr, "up") )
				{
					pair temp = new pair(curr.first, curr.second-1, curr.first, curr.second,  curr.ht+1);
					stack.push(temp);
					temporary_max=temporary_max+1;
					if(temporary_max> maximum_value)
						maximum_value=temporary_max;
					
					if(depth < curr.ht+1)
						depth = curr.ht+1;
				}

				//adding up the neighbouring node to the right
				if( movement_predictor(maze, curr, "right") )
				{
					pair temp = new pair(curr.first+1, curr.second, curr.first, curr.second,  curr.ht+1);
					stack.push(temp);
					temporary_max=temporary_max+1;
					if(temporary_max> maximum_value)
						maximum_value=temporary_max;
					
					if(depth < curr.ht+1)
						depth = curr.ht+1;

				}
				
				//adding up the neighbouring node to the right
				if( movement_predictor( maze,curr, "left") )
				{
					pair temp = new pair(curr.first-1, curr.second, curr.first, curr.second, curr.ht+1);
					stack.push(temp);
					temporary_max=temporary_max+1;
					if(temporary_max> maximum_value)
						maximum_value=temporary_max;
					
					if(depth < curr.ht+1)
						depth = curr.ht+1;
				}
				
				//adding up the neighbouring node which is on the downside
				if( movement_predictor(maze, curr, "down") )
				{
					pair temp = new pair(curr.first, curr.second+1, curr.first, curr.second,  curr.ht+1);
					stack.push(temp);
					temporary_max=temporary_max+1;
					if(temporary_max> maximum_value)
						maximum_value=temporary_max;
					
					if(depth < curr.ht+1)
						depth = curr.ht+1;
				}
			}	
		}
	}


	//this function will determine if the movement is possible from a certain direction of the node
	
	private static boolean movement_predictor(Maze m, pair p, String s)
	{
		if(s=="right")
		{
			if((p.first+1< m.Xlength-1) && ( m.maze[(p.first)+1][p.second]!= -1 ) )
			{
					// if there exists no wall to the right of the current position
					return true;
			}
			
			else return false;
		}
		if(s=="left")
		{
			if(p.first-1> 0 && ( m.maze[(p.first)-1][p.second]!= -1 ) )
			{
					//if there exists no wall to the right of the current position
					return true;
			}
			
			else return false;
		}
		if(s=="up")
		{
			if(p.second-1 > 0 && ( m.maze[p.first][p.second-1]!= -1 ) )
			{
					//if there exists no wall above the current position
					return true;
			}
			
			else return false;
		}
		if(s=="down")
		{
			if(p.second+1< m.Ylength-1 && ( m.maze[p.first][p.second+1]!= -1 ) )
			{
					//if there exists no wall below the current position
					return true;
			}
			else 
				return false;
		}
		
		else
			return false;
		
	}

	//this function prints out the solution path fromt the pair passed in to the last node

	private static void solution_path( Maze m, pair[] sol, pair p) 
	{
		if(p.parentF == m.startX && p.parentS == m.startY)
		{ 
			System.out.println("Start: "+  "("+p.parentF + ", " + p.parentS + ")"+ " moved to : "+ "("+ p.first +
					", "+ p.second + ")"+ "  Path Cost : " + m.maze[p.first][p.second] + "        Depth: " +p.ht);
			
			return;
		}
		//haven't finished printing out the solution
		else 
			System.out.println("Next: "+ "("+p.parentF + ", " + p.parentS + ")"+ " moved to : "+ "("+ p.first +
					", "+ p.second + ")"+ "  Path Cost : " + m.maze[p.first][p.second]+ "        Depth: " +p.ht);
	
		for(int i = 0; i< sol.length; i++)
		{
			if(sol[i].first ==p.parentF && sol[i].second == p.parentS)
			{
				solution_path(m, sol, sol[i]);
				return;
			}
		}
	}


// Class pair saves the current pair where X position is first and the y position is second
//height of the pair in the maze is relative to the root pair node

class pair {
	int first, second, parentF, parentS, ht, costFromStart;
	
	
	public pair(int f, int s, int pf, int ps, int height) {
		first=f;
		second = s;
		parentF= pf;
		parentS = ps;
		ht= height;
		costFromStart = 1;
		
	}
	
	public pair(int f, int s, int pf, int ps, int height, int c) {
		first=f;
		second = s;
		parentF= pf;
		parentS = ps;	
		ht= height;
		costFromStart = c;
	}
	
}

}