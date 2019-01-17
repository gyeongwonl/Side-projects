public class Tools
{
    public static int[] rawToPlots(String[] ans_raw)
    {
        int[] output = new int[ans_raw.length];
        int i=0;
        for (String s : ans_raw)
        {
            output[i++] = s.charAt(0) - 65; // A=0, B=1, C=2, D=3
        }
        return output;
    }
    public static char plotToRaw(int plot)
    {
        char output = (char)(plot+65);
        return output;
    }
    
    public static void replace_if_closer(Student std, Student[] nearest_so_far)
    {
        double[] max = Std_with_max_distance(nearest_so_far);
        if (std.distance < max[0])
        {
            nearest_so_far[(int)max[1]] = std;
        }
    }
     private static double[] Std_with_max_distance(Student[] nearest_so_far)   //[0] = distance, [1] = index in nearest_so_far
    {
        double[] output = {nearest_so_far[0].distance, 0};
        for (int i=1; i<nearest_so_far.length; i++)
        {
            Student s = nearest_so_far[i];
            if (s.distance > output[0])
            {
                output[0] = s.distance;
                output[1] = i;
            }    
        }
        return output;
    }
    
    // distance will always be >= 0
    public static double applyFavouritismFunction(double distance) //Favours close neighbours vs. kinda far neighbours
    {
        return 1/(distance + 0.1);
    }
    
    public static double getTotalCount(double[] counts)
    {
        double output = 0;
        for (double d : counts)
            output += d;
        return output;
    }
    
    // [0] = the count value, [1] = index number (0 = A, 1 = B, ...)
    public static double[] findMostLikelyChoice(double[] counts)
    {
        double[] output = {counts[0], 0};
        for (int i=1; i<counts.length; i++)
        {
            double d = counts[i];
            if (d > output[0])
            {
                output[0] = d;
                output[1] = i;
            }
        }
        return output;
    }
}