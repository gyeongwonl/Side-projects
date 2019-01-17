import java.io.*;
import java.util.*;
public class Solutions
{
    int[] solutions;
    public Solutions(File test_text_File) throws IOException // In the text file, solutions written as: A C D B A A B C D...
    {
        Scanner sc = new Scanner (test_text_File);
        String solutions_raw = sc.nextLine();
        String[] solutions_raw_arr = solutions_raw.split("\\s+");
        solutions = Tools.rawToPlots(solutions_raw_arr);
    }
    
    public int getSolution(int index)   //A=0, B=1, C=2, D=3
    {
        return solutions[index];
    }
}