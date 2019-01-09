//Author: Gyeongwon Lee
//Date Created: June 3rd/2017
//This program tells you the smallest prime number that is larger than the number that you've typed in.
import java.io.*;
import java.util.*;
public class PrimeNumberLoop
{
    public static void main(String args[])
    {
        Scanner kbReader = new Scanner(System.in);
        System.out.println("This program is intended for mathematical pursuit.");
        System.out.println("This program will print prime numbers larger than the number you entered.");
        System.out.print("Please enter a positive integer_ ");
        int a = kbReader.nextInt();
        int count=0;

        for (int i=(a+1); i>=0; i++)
        {
            for (int j=2; j<=i; j++)
            {
                if (i%j==0)
                {
                    if (i==j)
                    {
                        count++;
                        System.out.print(i+", ");
                        if (count%10==0)
                        {
                            System.out.println();
                        }
                    }

                    else
                    {
                        break;
                    }
                }
            }
        }

        
        /*
        if (a<2)
        {
            System.out.print("The next smallest prime number is 2");
        }
        else
        {
            int b, c;
            while (a>=2)
            {
                a++;
                c=2;
                do
                {
                    b=a%c;
                    c++;
                }while(b!=0);
                if ((c-1)==a)
                    break;
                else
                    continue;
            }
            System.out.println("The next smallest prime number is "+a);
        }*/
    }
}