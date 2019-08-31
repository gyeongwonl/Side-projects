import java.util.*;
import java.io.*;
// Contains test methods to ensure accuracy of the methods defined in major classes
public class Main_Tester implements GlobalFileNames
{
    public static void main(String args[])
    {
        testTools();
        testStudent();
        try 
        {
            testSolutions();
        }
        catch (IOException e) 
        {
            System.out.println("I/O ERROR FOUND WHILE TESTING _SOLUTIONS_");
        }
        try
        {
            testDatabase();
        }
        catch (IOException e)
        {
            System.out.println("I/O ERROR FOUND WHILE TESTING _DATABASE_");
        }
    }

    private static void testTools()
    {
        String[] a_input = {"A","B","C","D","A","A","C","B","B","D","B"};
        int[] a_output = Tools.rawToPlots(a_input);
        int[] a_expected = {0,1,2,3,0,0,2,1,1,3,1};
        assert(Arrays.equals(a_output, a_expected));

        assert(Tools.plotToRaw(0) == 'A');
        assert(Tools.plotToRaw(1) == 'B');
        assert(Tools.plotToRaw(2) == 'C');
        assert(Tools.plotToRaw(3) == 'D');

        Student stdA = new Student(0.25);
        Student stdB = new Student(0.2);
        Student stdC = new Student(1.5);
        Student stdD = new Student(0);
        Student stdE = new Student(0.3);
        Student stdF = new Student(2.0001);
        Student[] c_input = {stdB, stdC, stdD, stdE};
        Tools.replace_if_closer(stdA, c_input);
        Student[] c_expected = {stdB, stdA, stdD, stdE};
        assert(Arrays.equals(c_input, c_expected));
        Tools.replace_if_closer(stdF, c_input);
        assert(Arrays.equals(c_input, c_expected));

        assert(Tools.applyFavouritismFunction(0) == 1);
        assert(Tools.applyFavouritismFunction(0.1) == 1/1.01);
        assert(Tools.applyFavouritismFunction(0.25) == 1/1.025);
        assert(Tools.applyFavouritismFunction(4.5) == 1/1.45);

        double[] e_input = {0.02, 1.548, 6.023156, 1.234};
        double e_expected = 0.02 + 1.548 + 6.023156 + 1.234;
        assert(Tools.getTotalCount(e_input) == e_expected);

        double[] f_input = e_input;
        int f_output = Tools.findMostLikelyChoice(f_input);
        int f_expected_index = 2;
        double f_expected_val = 6.023156;
        assert(f_output == f_expected_index);
        assert(f_input[f_output] == f_expected_val);

        System.out.println("_Tools_ passed all the tests!");
    }

    private static void testStudent()
    {
        String[] practice = {"A", "B", "C"}; //0,1,2
        int[] practice_rating = {1, 2, 3};
        String[] test = {"D", "B", "C"};
        Student stdA = new Student("Ben", practice, practice_rating, test);
        
        int[] a_p_1 = {0, 1, 2};
        int[] a_r_1 = {1, 2, 3};
        int[] a_p_2 = {0, 1, 4};
        int[] a_r_2 = {1, 2, 3};
        int[] a_p_3 = {0, 1, 2};
        int[] a_r_3 = {1, 4, 4};
        int[] a_p_4 = {1, 2, 3};
        int[] a_r_4 = {5, 5, 5};
        
        stdA.registerDistanceFrom(a_p_1, a_r_1);
        assert(stdA.distance == 0);
        stdA.registerDistanceFrom(a_p_2, a_r_2);
        assert(stdA.distance == Math.sqrt(5*5));    // diff ans = 5 distance unit, diff rating = actual difference
        stdA.registerDistanceFrom(a_p_3, a_r_3);
        assert(stdA.distance == Math.sqrt(2*2 + 1*1));
        stdA.registerDistanceFrom(a_p_4, a_r_4);
        assert(stdA.distance == Math.sqrt(9*9 + 8*8 + 7*7));
        
        assert(stdA.getTestAnsFor(0) == 3);
        assert(stdA.getTestAnsFor(1) == 1);
        assert(stdA.getTestAnsFor(2) == 2);
        
        System.out.println("_Student_ passed all the tests!");
    }
    
    private static void testSolutions() throws IOException
    {
        Solutions pSol = new Solutions(p_sol_File_TEST);
        Solutions tSol = new Solutions(t_sol_File_TEST);
        
        assert(pSol.getSolution(0) == 0);
        assert(pSol.getSolution(5) == 2);
        assert(pSol.getSolution(7) == 0);
        assert(tSol.getSolution(0) == 3);
        assert(tSol.getSolution(1) == 3);
        assert(tSol.getSolution(4) == 1);
        assert(tSol.getSolution(7) == 0);
        
        System.out.println("_Solutions_ passed all the tests!");
    }
    
    private static void testDatabase() throws IOException
    {
        String[] p_ans_raw = {"D"}; // Aidan, Nancy, Tom
        String[] p_ans_raw2 = {"B"}; // Justin, Louis, Daniel
        int[] p_rating = {1};
        int[] p_rating2 = {2};
        int test_q_num = 0;
        int k = 3;
        String data_test_sol_name = common_address + "data_test_sol_TEST.txt";
        File data_test_sol_file = new File(data_test_sol_name);
        Solutions data_test_sol = new Solutions(data_test_sol_file);
    
        double correct_prob = DB_TEST.guessPerformance(p_ans_raw, p_rating, test_q_num, k, data_test_sol); 
        assert(correct_prob == 100);
        correct_prob = DB_TEST.guessPerformance(p_ans_raw2, p_rating2, test_q_num, k, data_test_sol); 
        assert(correct_prob == 0);
        
        System.out.println("_Database_ passed all the tests!");
    }
}