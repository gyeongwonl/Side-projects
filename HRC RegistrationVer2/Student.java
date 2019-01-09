import java.io.*;   //For PrintWriter
import java.util.*; //For Arrays and Scanner
public class Student
{
    private static int studentCount = 0;    //Number of all students
    private static Student[] studentArr = new Student[1000];    //Array containing each and every student
    private static Random rand = new Random();
    public static int randCount=0;

    private String firstName;
    private String lastName;
    private String name;
    private int grade;
    private int[] choiceArr = new int[5];   //Student's top 5 choices
    private Session[] sessionsEnrolled = new Session[2];    //Array to hold the indexes of the sessions the student is enrolled in for each slot
    //index1 - index of the session enrolled during slot A (as found on sessionsArr), index1 - slot B
    /**
     * Constructor...receives basic info from Main class to initialize each Student object appropriately...One Student object for each student
     */
    public Student(String first, String last, int gr,int ch1,int ch2,int ch3,int ch4,int ch5)
    {
        this.firstName = first;
        this.lastName = last;
        //this.name = firstName+" "+lastName;
        this.name = lastName+", "+firstName;
        this.grade = gr;
        this.choiceArr[0] = ch1;
        this.choiceArr[1] = ch2;
        this.choiceArr[2] = ch3;
        this.choiceArr[3] = ch4;
        this.choiceArr[4] = ch5;
        studentArr[studentCount++] = this;

        int count = 0;
        boolean random = false;
        while(!isFullyEnrolled()&&!Session.allSessionsFull) //While choices last, keep trying to enroll the student until the student is fully enrolled 
        {
            if (count<=4)
                attemptEnroll(choiceArr[count++],-1);
            else if (4<count&&count<=100)
            {
                if (Session.allSessionsFull)
                    break;

                if (count==100) //Probably means the student is stuck with no more slot B sessions to enroll in (due to some sessions that
                //are only offered in slot A)...So try enrolling in 2hour session (type 3)...Based on the MANUALLY_ENROLL() method. 
                {
                    for (int i=0; i<2; i++)
                    {
                        Session s = sessionsEnrolled[i];
                        if (s!=null)
                            s.unEnroll(name, i);
                    }
                    Arrays.fill(sessionsEnrolled,null);
                    int randTemp = rand.nextInt(2);
                    if (randTemp==0)
                        attemptEnroll(1,0); //Blanket Exercise
                    else
                        attemptEnroll(27,0);    //Mock Parliament
                }
                int randomLstIndex = rand.nextInt(Session.nonMaxSessionsLst.size());
                int randChoice = Session.nonMaxSessionsLst.get(randomLstIndex); //"Human Reference #"
                attemptEnroll(randChoice,-1);
                count++;
                random = true;
            }
            else
            {
                System.out.println("ERROR: "+name+" took too many random tries");
                break;
            }
        }
        if (Main.TELL_ME_NUM_OF_RANDOMLY_SLOTTED_STUDENTS_WHO_HAVE_REGISTERED&&random)
            randCount++;

        if (!isFullyEnrolled())
        {
            System.out.println("ERROR: "+name+" (Row "+(studentCount+1)+") is not fully enrolled!");
            //MANUALLY_ENROLL();
        }
    }

    //Constructor for unregistered students
    public Student(String first, String last, int gr)
    {
        int[] randChoices = new int[5];
        for (int i=0; i<5; i++)
        {
            int randomLstIndex = rand.nextInt(Session.nonMaxSessionsLst.size());
            randChoices[i] = Session.nonMaxSessionsLst.get(randomLstIndex);
        }
        new Student(first,last,gr,randChoices[0],randChoices[1],randChoices[2],randChoices[3],randChoices[4]);
    }

    /**
     * @param: receives an int value of the student's session choice ("Human reference #")
     * 
     * Calls enroll() method of the Session to attempt to enroll in the given session choice of the student
     */
    private void attemptEnroll(int choice, int MANUALLY_ENROLL_SLOT)
    {
        Session ses = Session.getSession(choice-1); //Remember, Index = Human reference # - 1
        ses.enroll(name, sessionsEnrolled, MANUALLY_ENROLL_SLOT);
    }

    /**
     * @return: true if student is enrolled in both slot A and B, false if not
     */
    private boolean isFullyEnrolled()
    {
        if (sessionsEnrolled[0]!=null&&sessionsEnrolled[1]!=null)
            return true;
        else
            return false;
    }

    public static boolean isReplicate(String first, String last, int gr)
    {
        boolean isReplicate=false;
        for (int i=0; i<studentCount; i++)
        {
            Student s = studentArr[i];
            if (first.equals(s.firstName)&&last.equals(s.lastName)&&(gr==s.grade))
            {
                isReplicate = true;
                break;
            }
        }
        return isReplicate;
    }

    /*
    private void MANUALLY_ENROLL()
    {
    Scanner sc = new Scanner(System.in);
    System.out.println("...Manually enroll? (y/n)_ ");
    String response = sc.next();
    if (response.equalsIgnoreCase("y"))
    {
    for (int i=0; i<2; i++)
    {
    Session s = sessionsEnrolled[i];
    if (s!=null)
    s.unEnroll(name, i);
    }
    Arrays.fill(sessionsEnrolled,null);
    System.out.println("Please enter the Session # for Slot A: ");
    int a = sc.nextInt();
    attemptEnroll(a,0);
    if (!isFullyEnrolled())
    {
    System.out.println("Please enter the Session # for Slot B: ");
    int b = sc.nextInt();
    attemptEnroll(b,1);
    }
    }
    }
     */
    /**
     * @param: receives a PrintWriter object instantiated in the Main class
     * 
     * Prints out lists of sessions each student is enrolled in to the scheduleOutput.txt
     */
    public static void printAllStudentSchedule(PrintWriter output, boolean byName, boolean forNametag)
    {
        int grade9 = 0;
        int grade10 = 0;
        int grade11 = 0;
        int grade12 = 0;
        String temp="";
        if (byName)
            temp = "Name\tGrade";
        else if (!byName)
            temp = "Grade\tName";

        if (!forNametag)
            output.println(temp+"\tSlot A (Session ID(Session Type), Speaker, Room#)\tSlot B (Session ID (Session Type), Speaker, Room#)");
        else
            output.println(temp+"\tAName\tARoom#\tARoomType\tBName\tBRoom#\tBRoomType");

        for (int i=0; i<studentCount; i++)
        {
            Student stu = studentArr[i];
            if (stu.grade==9)
                grade9++;
            else if (stu.grade==10)
                grade10++;
            else if (stu.grade==11)
                grade11++;
            else
                grade12++;
            Session slotA = stu.sessionsEnrolled[0];
            Session slotB = stu.sessionsEnrolled[1];
            if (byName)
                output.print(stu.name+"\t"+stu.grade);
            else if (!byName)
                output.print(stu.grade+"\t"+stu.name);

            if (slotA!=null&&!forNametag)
                output.print("\t"+slotA.sessionInfo());
            else if (slotA==null)
                output.print("\t     XXXXXXXXXX     \t");
            else
                output.print("\t"+slotA.sessionInfoForNametag());

            if (slotB!=null&&!forNametag)
                output.print("\t"+slotB.sessionInfo());
            else if (slotB==null)
                output.print("\t     XXXXXXXXXX     \t");
            else
                output.print("\t"+slotB.sessionInfoForNametag());
            output.println();
        }
        if (!forNametag)
        {
            output.println();
            output.println("Grade\t# of Students Enrolled");
            output.println("9\t"+grade9);
            output.println("10\t"+grade10);
            output.println("11\t"+grade11);
            output.println("12\t"+grade12);
        }
    }
}