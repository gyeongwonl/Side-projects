import java.io.*;   //For PrintWriter
import java.util.*; //For Scanner
public class Session
{
    /**
     * Universal state variables
     */
    public static int sessionCount=0;  //Total number of sessions offered in the conference
    public static Session[] sessionArr = new Session[35];  //Session array containing each and every session offered in the conference
    public static ArrayList<Integer> nonMaxSessionsLst = new ArrayList<Integer>();  //Used for random slotting
    //Contains "HUMAN REFERENCE #" of Sessions that are not full
    public static boolean allSessionsFull = false;
    
    /**
     * Session-specific state variables
     * Section 1 - Regarding basic initial setup of the Session object
     * Section 2 - Regarding enrollment in the Session object
     */
    private int sessionID;  //"Human Reference #"...sessionID-1=index at which it is found in sessionArr array
    private int sessionType;//MAJOR IMPROVEMENT:0-slot A only,1-slot B only,2-slot A&B separately (diff. students),3-slot A&B continuous (same students)
    private int maxNum; //Provided in the textfile...used to set session-speicific max # of students allowed to register for each slot
    private int maxNumA;    //Max # of students allowed to register during slot A
    private int maxNumB;    //Max # of students allowed to register during slot B
    private String speakerName; //Name of the speaker
    private int assignedRoom;   //Room # (where the session is taking place)
    private String roomType;
    //private String[] roomTypeArr = new String[100];

    private String[][] enrolledStudents = new String[2][];//Array of names of students enrolled for the session in each slot: row0-slot A,row1-slot B
    private int enrolledAlreadyA = 0;   // # of students enrolled already for the session in slot A
    private int enrolledAlreadyB = 0;   // # of students enrolled already for the session in slot B

    /**
     * Constructor...receives basic info from Main class to initialize each Session object appropriately...One Session object for each session
     */
    public Session(int ID, int type, int maxNum, String speaker, int room, String rType)
    {
        this.sessionID = ID;
        this.sessionType = type;
        this.maxNum = maxNum;
        this.setmaxNumEachSlot();
        this.speakerName = speaker;
        this.assignedRoom = room;
        this.roomType = rType;
        sessionArr[sessionCount++] = this;
    }

    /**
     * Used by the Constructor to set Max # of students allowed to register in each slot (A/B) according to the type of the Session
     */
    private void setmaxNumEachSlot()
    {
        if (this.sessionType==0){
            maxNumA = maxNum;
            maxNumB = 0;
        }
        else if (this.sessionType==1){
            maxNumA = 0;
            maxNumB = maxNum;
        }
        else if (this.sessionType==2||this.sessionType==3)
        {
            maxNumA = maxNum;
            maxNumB = maxNum;
        }
        enrolledStudents[0] = new String[maxNumA];
        enrolledStudents[1] = new String[maxNumB];
    }

    /**
     * @param: receives an integer index value of the Session (in sessionArr) that the Student class wishes to access
     * @return: Session object found at given index value in sessionArr
     */
    public static Session getSession(int index)
    {
        return sessionArr[index];
    }

    /**
     * @param: receives the name of the student who is attempting to enroll in the session(this), 
     * and the array that contains/will contain the Sessions in which the student is successfully enrolled,
     * and the boolean indicating whether the student was unsuccessful in fully enrolling the first time and thus is trying again
     * 
     * This method first checks if enrollment is possible with the help of findSlot() method,
     * then, if possible, enrolls the student in the session at the specific slot(s)
     */
    public void enroll(String name, Session[] sesEnrolled, int MANUALLY_ENROLL_SLOT)
    {
        boolean slotAOpen = false;
        boolean slotBOpen = false;
        if (sesEnrolled[0]==null) 
            slotAOpen = true;

        if (sesEnrolled[1]==null)
            slotBOpen = true;

        if ((this.equals(sesEnrolled[0])||this.equals(sesEnrolled[1]))&&this.sessionType!=3)
        {
            return;
        }
        
        int slotToEnroll;
        if (MANUALLY_ENROLL_SLOT == -1)
            slotToEnroll = findSlot(slotAOpen, slotBOpen);
        else
            slotToEnroll = MANUALLY_ENROLL_SLOT;

        if (slotToEnroll==0)
        {
            enrolledStudents[slotToEnroll][enrolledAlreadyA++] = name;
            sesEnrolled[0] = this;
            if (this.sessionType==3)    //Session takes two slots continous, so enroll the student in both slots
            {
                enrolledStudents[Math.abs(slotToEnroll-1)][enrolledAlreadyB++] = name;//If slotToEnroll was 0, also enroll in 1; if 1, also enroll in 0
                sesEnrolled[1] = this;
            }         
        }
        else if (slotToEnroll==1)
        {
            enrolledStudents[slotToEnroll][enrolledAlreadyB++] = name;
            sesEnrolled[1] = this;
        }
        else if (slotToEnroll==-1)
        {
        }
        updateNonMaxSessionsLst();
    }

    /**
     * @param: receives two boolean values indicating the available/empty time slots that the student has,
     * and the boolean indicating whether the student was unsuccessful in fully enrolling the first time and thus is trying again.
     * @return: returns an int value representing the time slot that the student should be attempting to enroll in for the given session(this)    
     * returns 0 for slot A, 1 for slot B, -1 for no slots available
     * 
     * This method looks for a slot in which student enrollment is possible. It does this by checking if 
     * a) the student is available (are not already registered in a session) during the slot(s) the session is offered in
     * and b) the session is available (below max capacity at that slot)
     * 
     * In specific circumstances (sesssion type is 3, student is available to enroll in either slots, retry==true),
     * try enrolling the student in the MORE filled slot
     */
    private int findSlot(boolean AOpen, boolean BOpen)
    {
        if (this.sessionType==0&&AOpen&&sessionSlotAOpen())
            return 0;
        else if (this.sessionType==1&&BOpen&&sessionSlotBOpen())
            return 1;
        else if (this.sessionType==2)   //If the session is offered in both slots separately,
        {
            if (AOpen&&(!BOpen)&&sessionSlotAOpen())    //and student is only available in slot A,
                return 0;
            else if (BOpen&&(!AOpen)&&sessionSlotBOpen())   //and student is only available in slot B,
                return 1;
            else if (AOpen&&BOpen)    //If student is available in both slot A and B (can enroll for either)
            {
                if (sessionSlotAOpen()&&(!sessionSlotBOpen()))
                    return 0;
                else if (sessionSlotBOpen()&&(!sessionSlotAOpen()))
                    return 1;
                else if (sessionSlotAOpen()&&sessionSlotBOpen())
                {
                    if (enrolledAlreadyA<enrolledAlreadyB)  //Prompt the student to enroll in less filled slot
                        return 0;
                    else
                        return 1;
                }
                else
                    return -1;
            }
            else
                return -1;
        }
        else if (this.sessionType==3&&AOpen&&BOpen&&sessionSlotAOpen())
            return 0;
        else
            return -1;
    }

    /**
     * @return true if slot A has room for one more student, false if already full
     */
    private boolean sessionSlotAOpen()
    {
        if (enrolledAlreadyA < maxNumA)
            return true;
        else
            return false;
    }

    /**
     * @return true if slot B has room for one more student, false if already full
     */
    private boolean sessionSlotBOpen()
    {
        if (enrolledAlreadyB < maxNumB)
            return true;
        else
            return false;
    }
    
    private void updateNonMaxSessionsLst()
    {
        for (int i=0; i<nonMaxSessionsLst.size(); i++)  //Remember! As I remove things from ArrLst, things get shifted over!
        {
            Integer j = nonMaxSessionsLst.get(i);
            Session s = sessionArr[j-1];    //Remember, index = "Human ref #"-1
            if (!s.sessionSlotAOpen()&&!s.sessionSlotBOpen())
            {
                nonMaxSessionsLst.remove(i);
                i--;
            }
        }
        if (nonMaxSessionsLst.size()==0)
        {
            System.out.println("ALL SESSIONS ARE FULL");
            allSessionsFull = true;
        }
    }

    /**
     * @param: receives a PrintWriter object instantiated in the Main class
     * 
     * Prints out lists of students enrolled in each slot of all sessions to the assignmentOutput.txt
     */
    public static void printSessionEnrollment(PrintWriter output) 
    {
        for (int i=0; i<sessionCount; i++)
        {
            Session s = sessionArr[i];
            output.println("Session "+s.sessionID+" ("+s.sessionType+") , Slot A : "+s.enrolledAlreadyA+" students enrolled out of "+s.maxNumA+". List: ");
            s.notifications(output,s.enrolledAlreadyA,s.maxNumA);
            for (int j=0; j<s.enrolledAlreadyA; j++)
            {
                if (j%5==0&&j!=0)
                    output.println();
                output.print(s.enrolledStudents[0][j]+"// ");
            }
            output.println();
            output.println();
            output.println("Session "+s.sessionID+", Slot B : "+s.enrolledAlreadyB+" students enrolled out of "+s.maxNumB+". List: ");
            s.notifications(output,s.enrolledAlreadyB,s.maxNumB);
            for (int j=0; j<s.enrolledAlreadyB; j++)
            {
                if (j%5==0&&j!=0)
                    output.println();
                output.print(s.enrolledStudents[1][j]+"// ");
            }   
            output.println();
            output.println();
            output.println("\n******************************************************************\n");
        }
    }

    private void notifications(PrintWriter output, int enrolled, int max)
    {
        if (enrolled==max)
            output.println("@@@@@@@@@@@@@@@@@@@@@@@SESSION IS FULL@@@@@@@@@@@@@@@@@@@@@@@");
        else if (enrolled>max)
            System.out.println("ERROR: Session "+this.sessionID+" is OVER MAX CAPACITY!!! (Enrolled: "+enrolled+", Max: "+max);
    }
    
    /**
     * @return String variable containing session Info
     * 
     * Called by printAllStudentSchedule() method
     */
    public String sessionInfo()
    {
        String s = this.sessionID+"("+this.sessionType+"), "+this.speakerName+", "+this.assignedRoom;
        return s;
    }
    public String sessionInfoForNametag()
    {
        String s = this.speakerName+"\t"+this.assignedRoom+"\t"+this.roomType;
        return s;
    }

    
    public void unEnroll(String studentName, int slot)
    {
        int ref=-1;
        if (slot==0)
            ref = enrolledAlreadyA-1;
        else if (slot==1)
            ref = enrolledAlreadyB-1;
        String s = enrolledStudents[slot][ref];
        if (s.equals(studentName))
        {
            enrolledStudents[slot][ref] = null;
            if (slot==0)
                enrolledAlreadyA--;
            else    //if slot==1
                enrolledAlreadyB--;
        }
        else
            System.out.println("ERROR: Student name does not match the last enrolled student of that session.");
    }
}
