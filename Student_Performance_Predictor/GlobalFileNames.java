import java.io.*;
public interface GlobalFileNames
{
    final String common_address = "C:\\Users\\l6524\\Desktop\\Student_Performance_Predictor\\GlobalFiles\\";
    
    final String data_File_name = common_address + "data.txt";
    final String practice_Solutions_File_name = common_address + "practice_solutions.txt";
    final String test_Solutions_File_name = common_address + "test_solutions.txt";
    
    final File student_data_File = new File(data_File_name);    // contains the practice & test responses from other students... used to setup the database 
    final File practice_Solutions_File = new File(practice_Solutions_File_name);
    final File test_Solutions_File = new File(test_Solutions_File_name);
    
    final Database DB = new Database(student_data_File);
    
    // To be used by Main_Tester Class (for TESTING ONLY):
    final String d_name_TEST = common_address + "data_TEST.txt";
    final String p_sol_name_TEST = common_address + "practice_solutions_TEST.txt";
    final String t_sol_name_TEST = common_address + "test_solutions_TEST.txt";
    
    final File d_File_TEST = new File(d_name_TEST);
    final File p_sol_File_TEST = new File(p_sol_name_TEST);
    final File t_sol_File_TEST = new File(t_sol_name_TEST);
    
    final Database DB_TEST = new Database(d_File_TEST);
}