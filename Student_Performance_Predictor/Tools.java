// Contains many useful methods that are used by other classes
public class Tools
{
    // Converts an array of Strings (students' raw letter responses) into an array of corresponding integers.
    public static int[] rawToPlots(String[] ans_raw)
    {
        int[] output = new int[ans_raw.length];
        int i=0;
        for (String s : ans_raw)
        {
            output[i++] = s.charAt(0) - 65; // A -> 0, B -> 1, C -> 2, D -> 3
        }
        return output;
    }
    // Converts an integer into a corresponding String (raw letter response)
    public static char plotToRaw(int plot)
    {
        char output = (char)(plot+65);  // 0 -> A, 1 -> B, 2 -> C, 3 -> D
        return output;
    }

    // Identifies the Student with the greatest distance (an instance variable), checks if std has a smaller distance:
    // - if it does, swaps the std into the array, replacing the Student with the greatest distance;
    // - if it doesn't, leaves the array unchanged.
    public static void replace_if_closer(Student std, Student[] nearest_so_far)
    {
        int max_index = Std_with_max_distance(nearest_so_far);
        if (std.distance < nearest_so_far[max_index].distance)
        {
            nearest_so_far[max_index] = std;
        }
    }
    // Returns the index of the Student with the greatest distance (an instance variable) in the array
    private static int Std_with_max_distance(Student[] nearest_so_far)
    {
        double max_distance_so_far = nearest_so_far[0].distance;
        int max_index_so_far = 0;
        for (int i = 1; i < nearest_so_far.length; i++)
        {
            Student s = nearest_so_far[i];
            if (s.distance > max_distance_so_far)
            {
                max_distance_so_far = s.distance;
                max_index_so_far = i;
            }    
        }
        return max_index_so_far;
    }

    // Favours close neighbours slightly over kinda far neighbours
    // These "weights" are used to calculate probabilities by the Database Class
    public static double applyFavouritismFunction(double distance) // potential upgrade: linear regression to find the best favouritismfunction
    {
        assert(distance >= 0);
        return 1 / (distance / 10 + 1);
    }

    // Returns the sum of all the (double) elements in the array
    public static double getTotalCount(double[] counts)
    {
        double output = 0;
        for (double d : counts)
            output += d;
        return output;
    }

    // Returns the index of the greatest value in the double array 
    // (the index (0 ~ 3) represents the response (A ~ D) that is most likely the student's choice)
    public static int findMostLikelyChoice(double[] counts)
    {
        double max_so_far = counts[0];
        int max_index_so_far = 0;
        for (int i = 1; i < counts.length; i++)
        {
            double d = counts[i];
            if (d > max_so_far)
            {
                max_so_far = d;
                max_index_so_far = i;
            }
        }
        return max_index_so_far;
    }
}