import java.util.*;
public class Main_Tester
{
    public static void main(String args[])
    {
        testTools();
        testStudent();
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

        assert(Tools.applyFavouritismFunction(0) == 10);
        assert(Tools.applyFavouritismFunction(0.25) == 1/0.35);
        assert(Tools.applyFavouritismFunction(4.5) == 1/4.6);

        double[] e_input = {0.02, 1.548, 6.023156, 1.234};
        double e_expected = 0.02 + 1.548 + 6.023156 + 1.234;
        assert(Tools.getTotalCount(e_input) == e_expected);

        double[] f_input = e_input;
        double[] f_output = Tools.findMostLikelyChoice(f_input);
        double[] f_expected = {6.023156, 2};
        assert(Arrays.equals(f_output, f_expected));

        System.out.println("_Tools_ passed all the tests!");
    }

    private static void testStudent()
    {
        String[] practice = {"A", "B", "A", "D", "C"}; //0,1,0,3,2
        String[] test = {"B", "B", "C", "A", "D"};
        Student stdA = new Student("Ben", practice, test);
        
        int[] a_input_1 = {0, 1, 0, 3, 2};
        int[] a_input_2 = {0, 0, 0, 0, 0};
        int[] a_input_3 = {3, 3, 3, 3, 3};
        int[] a_input_4 = {2, 0, 1, 1, 3};
        int[] a_input_5 = {0, 1, 0, 3, 3};
        stdA.registerDistanceFrom(a_input_1);
        assert(stdA.distance == 0);
        stdA.registerDistanceFrom(a_input_2);
        assert(stdA.distance == Math.sqrt(14));
        stdA.registerDistanceFrom(a_input_3);
        assert(stdA.distance == Math.sqrt(23));
        stdA.registerDistanceFrom(a_input_4);
        assert(stdA.distance == Math.sqrt(11));
        stdA.registerDistanceFrom(a_input_5);
        assert(stdA.distance == 1);
        
        System.out.println("_Student_ passed all the tests!");
    }
}