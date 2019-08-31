import java.io.*;
import java.util.*;
public class Solutions
{
    private int[] solutions;
    public int number_of_solutions;
    public Solutions(File solution_File) // In the text file, solutions written as: A C D B A A B C D...
    {
        String solutions_raw = "";
        try 
        {
            Scanner sc = new Scanner (solution_File);
            solutions_raw = sc.nextLine();
        }
        catch (IOException e)
        {
            System.out.println("ERROR FOUND WHILE READING SOLUTION_FILE");
        }
        String[] solutions_raw_arr = solutions_raw.split("\\s+");
        solutions = Tools.rawToPlots(solutions_raw_arr);
        number_of_solutions = solutions.length;
    }

    public int getSolution(int index)   //A=0, B=1, C=2, D=3
    {
        return solutions[index];
    }
}