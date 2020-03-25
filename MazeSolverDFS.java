import java.io.*;
import java.util.*;
 
public class MazeSolverDFS
{
   private static String[] readLines (InputStream f) throws IOException
    {
        BufferedReader r =new BufferedReader (new InputStreamReader (f, "US-ASCII"));
        ArrayList<String> lines = new ArrayList<String>();
        String line;
        while ((line = r.readLine()) != null)
            lines.add (line);
        return lines.toArray(new String[0]);
    }
 

   private static char[][] horizonatalTraversal (String[] lines)
    {
        final int width = (lines[0].length() + 1) / 2;
        char[][] c = new char[lines.length][width];
        for (int i = 0  ;  i < lines.length  ;  i++)
            for (int j = 0  ;  j < width  ;  j++)
                c[i][j] = lines[i].charAt (j * 2);
        return c;
    }
 
    private static boolean mazeSolver (char[][] maze,
                                                 int x, int y, int d)
    {
        boolean ok = false;
        for (int i = 0  ;  i < 4  &&  !ok  ;  i++)
            if (i != d)
                switch (i)
                    {
                        // 0 = up, 1 = right, 2 = down, 3 = left
                    case 0:
                        if (maze[y-1][x] == ' ')
                            ok = mazeSolver (maze, x, y - 2, 2);
                        break;
                    case 1:
                        if (maze[y][x+1] == ' ')
                            ok = mazeSolver (maze, x + 2, y, 3);
                        break;
                    case 2:
                        if (maze[y+1][x] == ' ')
                            ok = mazeSolver (maze, x, y + 2, 0);
                        break;
                    case 3:
                        if (maze[y][x-1] == ' ')
                            ok = mazeSolver (maze, x - 2, y, 1);
                        break;
                    }
        if (x == 1  &&  y == 1)
            ok = true;
        if (ok)
            {
                maze[y][x] = '*';
                switch (d)
                    {
                    case 0:
                        maze[y-1][x] = '*';
                        break;
                    case 1:
                        maze[y][x+1] = '*';
                        break;
                    case 2:
                        maze[y+1][x] = '*';
                        break;
                    case 3:
                        maze[y][x-1] = '*';
                        break;
                    }
            }
        return ok;
    }
 
    private static void resolvingMaze (char[][] maze)
    {
        mazeSolver (maze, maze[0].length - 2, maze.length - 2, -1);
    }
 
    private static String[] horizontalExpansion (char[][] maze)
    {
        char[] tmp = new char[3];
        String[] lines = new String[maze.length];
        for (int i = 0  ;  i < maze.length  ;  i++)
            {
                StringBuilder sb = new StringBuilder(maze[i].length * 2);
                for (int j = 0  ;  j < maze[i].length  ;  j++)
                    if (j % 2 == 0)
                        sb.append (maze[i][j]);
                    else
                        {
                            tmp[0] = tmp[1] = tmp[2] = maze[i][j];
                            if (tmp[1] == '*')
                                tmp[0] = tmp[2] = ' ';
                            sb.append (tmp);
                        }
                lines[i] = sb.toString();
            }
        return lines;
    }
 
    public static void main (String[] args) throws IOException
    {
        InputStream fileA = (args.length > 0 ?  new FileInputStream (args[0]) :  System.in);
        String[] rows = readLines (fileA);
        char[][] maze = horizonatalTraversal(rows);  
        resolvingMaze (maze);
        String[] resolved = horizontalExpansion (maze);
        for (int i = 0  ;  i < solvedLines.length  ;  i++)
            System.out.println (resolved[i]);
    }
}