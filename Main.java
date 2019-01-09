/**
 * !!!Check for duplicates (complete-2018-04-06)
 * Make separate output for NameTag-making (complete-2018-04-06)
 * Assign roomType for each Session Object (complete-2018-04-06)
 * !!!!!MAKE SURE NO DOUBLE ENROLLMENT (complete-2018-04-07)
 */
//Gyeongwon Lee
//2018-03-10
////2nd Version of the Program created for Scheduling committee of Grant Park High School Human Rights Conference.
//This program will import data from a text file (that contains data manually copy-and-pasted from google forms responses spreadsheet)
//and process it to assign students to their desired sessions (as many students as each session allows)
//Refer to README text file to understand what the data in different text files mean

//MAJOR IMPROVEMENT: This program takes into account that different types of sessions exist; some are offered only in the first slot (slot A)
//, some are offered only in the second slot (slot B), some are offered in both slots with different students (separated), while
//others are offered in both slots with the same group of students (continued)

//Another Improvement (2018-04-06): Assigning students to random sessions if the student either a) has registered but could not be enrolled
//within his/her top 5 choices, or b) has not registered.

import java.io.*;   //For IOException, FileWriter, PrintWriter
import java.util.*; //For Scanner
public class Main
{
    public static final boolean TELL_ME_NUM_OF_RANDOMLY_SLOTTED_STUDENTS_WHO_HAVE_REGISTERED = false;//Works only if the UnregisteredStudents.txt empty
    public static void main(String args[]) throws IOException
    {
        String sessionInput = "C:\\Users\\l6524\\Desktop\\HRC RegistrationVer2\\SessionList.txt";
        String responseInput = "C:\\Users\\l6524\\Desktop\\HRC RegistrationVer2\\Responses.txt";
        String unregisteredStudentsInput = "C:\\Users\\l6524\\Desktop\\HRC RegistrationVer2\\UnregisteredStudents.txt";
        Scanner sessionScanner = new Scanner(new File(sessionInput));
        Scanner responseScanner = new Scanner(new File(responseInput));
        Scanner unregisteredScanner = new Scanner(new File(unregisteredStudentsInput));

        String assignmentOutput = "C:\\Users\\l6524\\Desktop\\HRC RegistrationVer2\\Assignment.txt";
        String scheduleByNameOutput = "C:\\Users\\l6524\\Desktop\\HRC RegistrationVer2\\Schedule_ByName.txt";
        String scheduleByGradeOutput = "C:\\Users\\l6524\\Desktop\\HRC RegistrationVer2\\Schedule_ByGrade.txt";
        String scheduleNametagOutput = "C:\\Users\\l6524\\Desktop\\HRC RegistrationVer2\\Schedule_Nametag.txt";
        FileWriter fw = new FileWriter(assignmentOutput);
        PrintWriter output = new PrintWriter(fw);
        FileWriter fw2 = new FileWriter(scheduleByNameOutput);
        PrintWriter output2 = new PrintWriter(fw2);
        FileWriter fw3 = new FileWriter(scheduleByGradeOutput);
        PrintWriter output3 = new PrintWriter(fw3);
        FileWriter fw4 = new FileWriter(scheduleNametagOutput);
        PrintWriter output4 = new PrintWriter(fw4);

        /**
         * Make a Session object for each session with info provided on the SessionList.txt
         */
        String sessionInfo;
        while (sessionScanner.hasNext())
        {
            sessionInfo = sessionScanner.nextLine();
            Scanner sc = new Scanner(sessionInfo).useDelimiter("\t");
            int ID = sc.nextInt();
            int type = sc.nextInt();
            int max = sc.nextInt();
            String speaker = sc.next();
            int room = sc.nextInt();
            String roomType = sc.next();
            new Session(ID,type,max,speaker,room, roomType);
            sc.close();
        }
        sessionScanner.close();
        for(int i=0; i<Session.sessionCount; i++)   //Initial setup of nonMaxSessionsLst
        {
            Session.nonMaxSessionsLst.add(i+1);
        }
        
        /**
         * Make a Student object for each student with info provided on the Responses.txt
         */
        String studentInfo;
        String firstName;
        String lastName;
        int grade;
        int choice1;
        int choice2;
        int choice3;
        int choice4;
        int choice5;
        while (responseScanner.hasNext())
        {
            studentInfo = responseScanner.nextLine();
            Scanner sca = new Scanner(studentInfo);
            sca.useDelimiter("\\tSession\\s+|\\t"); //To account for the format of the data (Ignore "Session ")

            // firstName = sca.next().toLowerCase();
            // String upperTemp = firstName.substring(0,1).toUpperCase();
            // String lowerTemp = firstName.substring(1,firstName.length());
            //firstName = upperTemp+lowerTemp;
            firstName = toCommonNameFormat(sca.next());
            // lastName = sca.next().toLowerCase();
            // String upperTemp2 = lastName.substring(0,1).toUpperCase();
            // String lowerTemp2 = lastName.substring(1,lastName.length());
            // lastName = upperTemp2+lowerTemp2;
            lastName = toCommonNameFormat(sca.next());
            grade = sca.nextInt();

            if (Student.isReplicate(firstName, lastName, grade))
            {
                //System.out.println(firstName+" "+lastName+" is RUDE!!!");
                continue;
            }

            choice1 = sca.nextInt();
            choice2 = sca.nextInt();
            choice3 = sca.nextInt();
            choice4 = sca.nextInt();
            choice5 = sca.nextInt();
            Student stu = new Student(firstName,lastName,grade,choice1,choice2,choice3,choice4,choice5);
            sca.close();
        }
        responseScanner.close();

        /**
         * Make a Student object for each student who have not registerd with info from UnregisteredStudents.txt.
         * Use a different constructor
         */
        String unregStudentInfo;
        String unregFirstName;
        String unregLastName;
        int unregGrade=0;
        while (unregisteredScanner.hasNext()&&!Session.allSessionsFull)
        {
            unregStudentInfo = unregisteredScanner.nextLine();
            Scanner scan = new Scanner(unregStudentInfo).useDelimiter("\\s+");
            try
            {
                unregGrade = Integer.parseInt(unregStudentInfo);
                continue;
            }
            catch (NumberFormatException e)
            {
                unregLastName = toCommonNameFormat(scan.next());
                unregFirstName = toCommonNameFormat(scan.next());
            }
            //Do some editing with the given String format to make it usable by my program
            //! Make sure first and last name have Capital first letter and Lower rest of the letter
            if (Student.isReplicate(unregFirstName, unregLastName, unregGrade))
            {
                System.out.println(unregFirstName+" "+unregLastName+" is a duplicate!!!");
                continue;
            }
            Student stud = new Student(unregFirstName,unregLastName,unregGrade);
            scan.close();
        }
        unregisteredScanner.close();

        /**
         * Start storing data in text files
         */
        Session.printSessionEnrollment(output); //use the static method to print out results on Assignment.txt
        output.close();
        fw.close();
        Student.printAllStudentSchedule(output2, true, false);
        output2.close();
        fw2.close();
        Student.printAllStudentSchedule(output3, false, false);
        output3.close();
        fw3.close();
        Student.printAllStudentSchedule(output4, true, true);
        fw4.close();
        
        //For debugging
        System.out.println(Session.nonMaxSessionsLst.size());
        for (int i=0; i<Session.nonMaxSessionsLst.size(); i++)
        {
            System.out.print(Session.nonMaxSessionsLst.get(i)+" ");
        }
        if (TELL_ME_NUM_OF_RANDOMLY_SLOTTED_STUDENTS_WHO_HAVE_REGISTERED)
            System.out.println("TELL_ME_NUM_OF_RANDOMLY_SLOTTED_STUDENTS_WHO_HAVE_REGISTERED: "+Student.randCount);
    }
    
    /**
     * @param: Student's name (unprocessed) ex. mike, ANNA, jAmES, DrAke
     * @return: Processed student's name with first letter Capitalized ex. Mike, Anna, James, Drake
     */
    private static String toCommonNameFormat(String name)
    {
        name = name.toLowerCase();
        String upperTemp = name.substring(0,1).toUpperCase();
        String lowerTemp = name.substring(1,name.length());
        name = upperTemp+lowerTemp;
        return name;
    }
}