// Gyeongwon Lee
// 2018-12-29
// *Root of the idea: How Netflix recommends movies to its customers...not by category/genre, but by what other people
//                    SIMILAR to the customer also enjoyed!
// The Basis for a Customizable/Personal Tutoring Platform (AI). Riiid! EdTech!!!
// *Exciting thought: Why not make a platform where students/teachers can post cool/insightful/sharp questions they came up with?
//                    Everyone can tackle one another's problems, identifying misunderstandings and resolving them!
//                     -- Quizlet?
//                     -- Different curriculum, so many different sub-categories


// Can we predict (right/wrong, what answer, how likely) the student's performance on a test 
// by simply analyzing his/her performance on the diagnostic test ("practice")?
// * The two tests test different combinations of concepts given the same list of concepts 
// Espeically useful if a) there are too many questions in our bank to manually tag them according to the concepts it's testing
//                      b) the concepts the question is testing isn't too obvious or easy to identify
//                      c) the reason(s) why the student got it wrong isn't too obvious or easy to identify (ex. Literature,
//                         Physics (too many possible places that students could have messed up on)

// Sure, you can argue that a good teacher should be able to do this without the help of a program, given enough
// data, organization skills, and carefully crafted tests. For ex, in Math, you can easily draw the connection between
// getting 1+2*3 and 1/(2+3) wrong -- lack of an understanding of order of operations.
//     *(or what if a student got 1+2*3 wrong but 2*3+1 right? The student still doesn't have a solid understanding
//       -- can the program catch this?) 
// However, is that true for all subjects? Consider Literature, Physics. 
// Aren't the reasons behind a student getting a question wrong less obvious?
// Can this program be applied to subjects where the reason why a student got a certain question wrong is less obvious (vs. Math)?
// YES! Because we rely on the data gathered from other students who got similar results (The sets of questions they answered
// correctly/wrongly are similar) to make predictions! 

// Once we can do that, we should be able to identify the student's misunderstanding (or at the very least point to some resources
// that other students that got similar (types of) questions wrong found helpful).

import java.io.*;
import java.util.*;
public class Main
{
    public static void main(String args[]) throws IOException
    {
        String data_File_name = "C:\\djlfdkd";
        //String practice_Solutions_File_name = "C:\\djkjkjeeee";
        String test_Solutions_File_name = "C:\\dfjldkjlkjkljl";
        
        File student_data_File = new File(data_File_name);
        //File practice_Solutions_File = new File(practice_Solutions_File_name);
        File test_Solutions_File = new File(test_Solutions_File_name);
        // Read from text file to form Database
        
        Database DB = new Database();
        //Solutions practice_Sol = new Solutions(practice_Solutions_File);
        Solutions test_Sol = new Solutions(test_Solutions_File);
        
        // Prompt user input (or read text file) for practice answers from a student whose performance we are predicting
        
        // Do calculations
        // Print out the result
    }
}