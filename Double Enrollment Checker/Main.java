import java.util.*;
import java.io.*;
public class Main
{
    public static void main(String args[]) throws IOException
    {
        String data = "C:\\Users\\l6524\\Desktop\\HRC RegistrationVer2\\Schedule_ByGrade.txt";
        Scanner sc = new Scanner(new File(data));
        int count=0;
        while(sc.hasNext())
        {
            String line = sc.nextLine();
            if (line.substring(0,1).equals(" "))
                break;
            Scanner sca = new Scanner(line).useDelimiter("\\t");
            sca.next();
            String name = sca.next();
            String sesA = sca.next();
            String sesB = sca.next();
            if (sesA.equals(sesB))
            {
                /*Scanner scan = new Scanner(sesA+sesB).useDelimiter("\\s+|\\t");
                String sesAType = scan.next().substring(0,2);
                scan.next();
                scan.next();
                String sesBType = scan.next().substring(0,2);
                scan.close();
                if (sesAType.equals("1(")&&sesBType.equals("1("))
                continue;
                else if (sesAType.equals("27")&&sesBType.equals("27"))
                continue;
                else if (sesAType.equals("29")&&sesBType.equals("29"))
                continue;
                else
                {
                System.out.println(name+" is double enrolled!");
                System.out.println("-- "+sesAType+", "+sesBType);
                }*/
                String sesAType = sesA.substring(sesA.length()-2,sesA.length());
                String sesBType = sesB.substring(sesB.length()-2,sesB.length());
                if (sesAType.equals(" 0")&&sesBType.equals(" 0"))
                    continue;
                else if (sesAType.equals("-1")&&sesBType.equals("-1"))
                    continue;
                else if (sesAType.equals(" 2")&&sesBType.equals(" 2"))
                    continue;
                else if (sesAType.equals("24")&&sesBType.equals("24"))
                    continue;
                else 
                {
                    System.out.println(name+" is double enrolled!");
                    System.out.println("-- "+sesAType+", "+sesBType);   
                    count++;
                }
            }
        }
        System.out.println("TOTAL: "+count);
    }
}