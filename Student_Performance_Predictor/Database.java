import java.util.*;
public class Database
{
    private ArrayList<Student> mainStack;

    public Database()
    {
        mainStack = new ArrayList<Student>();
    }

    public void addStudent(Student s)
    {
        mainStack.add(s);
    }

    public void guessPerformance(String[] practice_ans_raw, int test_question_num, int num_neighbours, Solutions test_Sol) //test_question_num starts from 0
    {
        int[] practice_ans = Tools.rawToPlots(practice_ans_raw);
        registerDistances(practice_ans);
        Student[] models = find_k_nearest_students(practice_ans, num_neighbours);
        int[] models_ans = find_models_ans(models, test_question_num);
        double[] weight_values = calc_weight_values(models);
        
        double[] models_ans_counts = calc_models_ans_counts(models_ans, weight_values);
        // calc counts by adding the weight_values for each instance (ex. A: 1.02, B: 0, C: 5.4, D: 0.05)
        int solution = test_Sol.getSolution(test_question_num);
        double correct_count = models_ans_counts[solution];
        double total_count = Tools.getTotalCount(models_ans_counts);
        double correct_prob = (correct_count/total_count) * 100;
        // Calculate the probability by taking (5.4/(1.02+0+5.4+0.05)) * 100
        
        double[] most_likely_choice_arr = Tools.findMostLikelyChoice(models_ans_counts);
        int most_likely_choice = (int)most_likely_choice_arr[1];
        char most_likely_choice_char = Tools.plotToRaw(most_likely_choice);
        double most_likely_count = most_likely_choice_arr[0];
        double most_likely_prob = (most_likely_count/total_count) * 100;
        
        System.out.println("The Student has " + correct_prob + "% chance of answering Question #" + test_question_num + " correctly.");
        System.out.println("The Student is most likely (" + most_likely_prob + "%) to answer " + most_likely_choice_char);
    }

    private void registerDistances(int[] practice_ans)
    {
        for (Student s : mainStack)
            s.registerDistanceFrom(practice_ans);
    }

    private Student[] find_k_nearest_students(int[] practice_ans, int k)
    {
        Student[] output = new Student[k];
        for (int i=0; i<mainStack.size(); i++)
        {
            Student s = mainStack.get(i);
            if (i<k)
                output[i] = s;
            else
                Tools.replace_if_closer(s, output);
        }
        return output;
    }

    private int[] find_models_ans(Student[] models, int index)
    {
        int[] output = new int[models.length];
        for (int i=0; i<models.length; i++)
        {
            Student s = models[i];
            output[i] = s.getTestAnsFor(index);
        }
        return output;
    }

    private double[] calc_weight_values(Student[] models)
    {
        double[] output = new double[models.length];
        for (int i=0; i<models.length; i++)
        {
            Student s = models[i];
            output[i] = Tools.applyFavouritismFunction(s.distance);
        }
        return output;
    }
    
    private double[] calc_models_ans_counts(int[] models_ans, double[] weight_values)
    {
        double[] output = new double[4];  // Counts for choice A, B, C, D
        for (int i=0; i<4; i++)
        {
            int choice = models_ans[i];
            output[choice] += weight_values[i];
        }
        return output;
    }
}