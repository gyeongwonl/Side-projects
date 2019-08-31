import java.util.*;
import java.io.*;
import java.text.NumberFormat;
public class Database
{
    private ArrayList<Student> mainStack;

    public Database(File student_data_File)
    {
        mainStack = new ArrayList<Student>();
        try
        {
            loadData(student_data_File);
        }
        catch (IOException e)
        {
            System.out.println("I/O ERROR FOUND WHEN INITIALIZING DATABASE");
        }
    }

    // Read from text file to form Database
    private void loadData(File student_data_File) throws IOException
    {
        Scanner sc = new Scanner(student_data_File);
        while (sc.hasNext())
        {
            String line = sc.nextLine();
            Scanner sca = new Scanner(line);
            String name = sca.next();

            String p_ans_string = "";
            String p_rating_string = "";
            String t_ans_string = "";
            boolean record_test_ans = false;
            while (sca.hasNext())
            {
                String i = sca.next();
                if (i.equalsIgnoreCase("test"))
                {
                    record_test_ans = true;
                    continue;
                }

                if (record_test_ans)
                    t_ans_string += (i + " ");
                else
                {
                    p_ans_string += (i + " ");
                    int d = sca.nextInt();
                    p_rating_string += (d + " ");
                }
            }
            
            String[] practice_ans_raw = p_ans_string.split("\\s");
            int[] practice_rating;
            {
                String[] practice_rating_str_arr = p_rating_string.split("\\s");
                practice_rating = new int[practice_rating_str_arr.length];
                int c = 0;
                for (String s : practice_rating_str_arr)
                {
                    practice_rating[c] = Integer.parseInt(practice_rating_str_arr[c]);
                    c++;
                }
            }
            String[] test_ans_raw = t_ans_string.split("\\s");
            addStudent(new Student(name, practice_ans_raw, practice_rating, test_ans_raw));
            sca.close();
        }
        sc.close();
    }

    private void addStudent(Student s)
    {
        mainStack.add(s);
    }

    public double guessPerformance(String[] practice_ans_raw, int[] practice_rating, int test_question_num, int num_neighbours, Solutions test_Sol)
    //test_question_num starts from 0
    {
        if (num_neighbours > mainStack.size())
            System.out.println("ERROR: NOT ENOUGH DATA IN THE DATABASE");
        else if (num_neighbours > (mainStack.size() / 2))
            System.out.println("WARNING: NOT ENOUGH DATA IN THE DATABASE FOR ACCURATE PREDICTION");
        NumberFormat fmt = NumberFormat.getNumberInstance();
        fmt.setMaximumFractionDigits(3);
        fmt.setMinimumFractionDigits(0);

        int[] models_ans;
        double[] weight_values;
        {
            int[] practice_ans = Tools.rawToPlots(practice_ans_raw);
            registerDistances(practice_ans, practice_rating);
            Student[] models = find_k_nearest_students(num_neighbours);
            models_ans = find_models_ans(models, test_question_num);
            weight_values = calc_weight_values(models);
        }

        double[] models_ans_counts;
        double total_count;
        double correct_prob;
        {
            models_ans_counts = calc_models_ans_counts(models_ans, weight_values);
            // calc counts by adding the weight_values for each instance (ex. A: 1.02, B: 0, C: 5.4, D: 0.05)
            int solution = test_Sol.getSolution(test_question_num);
            double correct_count = models_ans_counts[solution];
            total_count = Tools.getTotalCount(models_ans_counts);
            correct_prob = (correct_count/total_count) * 100;    //*
            correct_prob = Double.parseDouble(fmt.format(correct_prob));
        }
        // Calculate the probability by taking (5.4/(1.02+0+5.4+0.05)) * 100

        char most_likely_choice_char;
        double most_likely_prob;
        {
            //double[] most_likely_choice_arr = Tools.findMostLikelyChoice(models_ans_counts);
            int most_likely_choice_index = Tools.findMostLikelyChoice(models_ans_counts);
            most_likely_choice_char = Tools.plotToRaw(most_likely_choice_index); //*
            double most_likely_count = models_ans_counts[most_likely_choice_index];
            most_likely_prob = (most_likely_count/total_count) * 100; 
            most_likely_prob = Double.parseDouble(fmt.format(most_likely_prob));    //*
        }

        System.out.println("The Student has " + correct_prob + "% chance of answering Question #" + (test_question_num+1) + " correctly.");
        System.out.println("The Student is most likely (" + most_likely_prob + "%) to answer " + most_likely_choice_char);
        // possible additional feature: "Will most likely rate this question to be of ... difficulty"
        return correct_prob;
    }

    private void registerDistances(int[] practice_ans, int[] practice_rating)
    {
        for (Student s : mainStack)
            s.registerDistanceFrom(practice_ans, practice_rating);
    }

    private Student[] find_k_nearest_students(int k)
    {
        Student[] output = new Student[k];
        for (int i = 0; i < mainStack.size(); i++)
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
        for (int i = 0; i < models.length; i++)
        {
            Student s = models[i];
            output[i] = s.getTestAnsFor(index);
        }
        return output;
    }

    private double[] calc_weight_values(Student[] models)
    {
        double[] output = new double[models.length];
        for (int i = 0; i < models.length; i++)
        {
            Student s = models[i];
            output[i] = Tools.applyFavouritismFunction(s.distance);
        }
        return output;
    }

    private double[] calc_models_ans_counts(int[] models_ans, double[] weight_values)
    {
        double[] output = new double[4];  // Counts for choice A, B, C, D
        for (int i = 0; i < models_ans.length; i++)
        {
            int choice = models_ans[i];
            output[choice] += weight_values[i];
        }
        return output;
    }
}