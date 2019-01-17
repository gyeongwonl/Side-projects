public class Student
{
    private String name;
    private int[] practice_ans;
    private int[] test_ans;
    private int num_practice_q;
    private int num_test_q;

    public double distance;
    
    public Student(String stdName, String[] practice_ans_raw, String[] test_ans_raw)
    {
        name = stdName;
        practice_ans = Tools.rawToPlots(practice_ans_raw);
        test_ans = Tools.rawToPlots(test_ans_raw);
        num_practice_q = practice_ans.length;
        num_test_q = test_ans.length;
    }
    public Student(double distance)    //Used for Testing purposes
    {
        this.distance = distance;
    }

    public void registerDistanceFrom(int[] ans)
    {
        int sum_of_squares = 0;
        int[] diff = getDifference(ans);
        for (int n : diff)
            sum_of_squares += Math.pow(n, 2);
        distance = Math.sqrt(sum_of_squares);
    }
    
    private int[] getDifference(int[] ans)
    {
        int[] output = new int[num_practice_q];
        for (int i=0; i<num_practice_q; i++)
            output[i] = Math.abs(practice_ans[i] - ans[i]);
        return output;
    }
    
    public int getTestAnsFor(int test_question_num)
    {
        return test_ans[test_question_num];
    }
}