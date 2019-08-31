public class Student
{
    private String name;
    private int[] practice_ans;
    private int[] practice_rating;   // "Rate the difficulty of this question from 1 (super easy) to 5 (super challenging)."
    private int[] test_ans;
    private int num_practice_q;
    private int num_test_q;

    public double distance;
    
    public Student(String stdName, String[] practice_ans_raw, int[] practice_rating_raw, String[] test_ans_raw)
    {
        name = stdName;
        practice_ans = Tools.rawToPlots(practice_ans_raw);
        practice_rating = new int[practice_rating_raw.length];
        System.arraycopy(practice_rating_raw, 0, this.practice_rating, 0, practice_rating_raw.length);
        test_ans = Tools.rawToPlots(test_ans_raw);
        num_practice_q = practice_ans.length;
        num_test_q = test_ans.length;
    }
    public Student(double distance)    //Used for Testing purposes
    {
        this.distance = distance;
    }
    
    public void registerDistanceFrom(int[] ans, int[] rating)
    {
        double sum_of_squares = 0;
        int[] diff = getDifference(ans, rating);
        for (int n : diff)
            sum_of_squares += Math.pow(n, 2);
        distance = Math.sqrt(sum_of_squares);
    }
    private int[] getDifference(int[] ans, int[] rating)
    {
        int[] output = new int[num_practice_q];
        for (int i = 0; i < num_practice_q; i++)
        {
            int ans_diff;
            if (practice_ans[i] == ans[i])
                ans_diff = 0;
            else
                ans_diff = 5;
            output[i] = ans_diff + Math.abs(practice_rating[i] - rating[i]);
        }
        return output;
    }
    
    public int getTestAnsFor(int test_question_num)
    {
        return test_ans[test_question_num];
    }
}