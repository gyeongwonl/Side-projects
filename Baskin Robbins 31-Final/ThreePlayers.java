import java.io.*;
import java.util.*;
public class ThreePlayers
{
    public String ThreePlayers()
    {
        int i=0;
        
        String replay;
        String numErrMessage = "\n@@@@@ Error! You can only count up by 1, 2, or 3! @@@@@";
        Scanner kbReader = new Scanner(System.in);
        Scanner player1 = new Scanner(System.in);
        Scanner player2 = new Scanner(System.in);
        Scanner player3 = new Scanner(System.in);
        Scanner kb = new Scanner(System.in);
        
        boolean replayErr=false;
        boolean finalMatch=false;   //Lets the computer know if this match is the ultimate match between two remaining players
        
        String ultimate1="n";   //Means no one has lost yet
        String ultimate2="n";   //ex. ultimate2="y" implies that Player 2 has lost initially and the game is being continued
        String ultimate3="n";   //between Players 1 & 3
        do
        {
            boolean player1Err=true;    //Allows the first of the two conditions for players' loops to be met
            boolean player2Err=true;    //The only time those loops won't run is if any of the ultimate values equal "n"
            boolean player3Err=true;
            while (player1Err==true&&ultimate1.equalsIgnoreCase("n"))   //Won't be iterated if ultimate1 is "y"...aka Player 1 has lost already
            {
                System.out.println("");
                System.out.println("***Current Number: "+i+"***");
                System.out.println("Player 1's turn");
                System.out.print("Please enter the amount of numbers you would like to count up by.(1, 2, or 3)_ ");
                String aString = player1.nextLine();
                char aChar = aString.charAt(0);
                int a = Character.getNumericValue(aChar);
                if (1<=a&&a<=3)
                {
                    player1Err = false;
                    i= i+a;
                    if (i>=31)  //if 31 is reached in Player 1's turn...Player 1 has lost
                    {
                        if (finalMatch==false)  //If this is the first time someone has reacehd 31...aka it's not the final match
                        {
                            System.out.println("");
                            System.out.println("*****************Player 1 has LOST*****************");
                            System.out.println("Maybe next time, Player 1...");
                            System.out.println();
                            System.out.println("Well, the unworthy Player 1 has lost...");
                            System.out.println("But the remaining winners shall determine the ultimate winner, shall we?");
                            do
                            {
                                System.out.println();
                                System.out.print("Do you wish to continue playing? (y/n)_ ");
                                ultimate1 = kb.next();  //Means that Player 1 has lost in the first round
                                System.out.print("\u000c");
                                if (ultimate1.equalsIgnoreCase("y"))    //The match should continue between two other Players
                                {
                                    i=0;    //Resets the value of the total so another round of the game can be run
                                    finalMatch=true;    //Now this is the final match between the remaining players
                                    break;
                                }
                                else if (ultimate1.equalsIgnoreCase("n"))
                                {
                                    ultimate2="y";  //Ensures that the loops for Players 2 & 3 won't be run but rather skipped 
                                    ultimate3="y";  //to go right to a part where the computer asks if the user wishes to play again
                                    break;
                                }
                                else
                                {
                                    System.out.println("Error! Please enter either \"y\" or \"n\"");
                                    replayErr=true;
                                }
                            }while(replayErr==true);
                            break;
                        }
                        else if (finalMatch==true&&ultimate2.equalsIgnoreCase("y")) //If this is the final match and Player 2 has lost already
                        {
                            System.out.println("");
                            System.out.println("*****************Player 3 is VICTORIOUS*****************");
                            System.out.println("Maybe next time, Player 1...");
                            ultimate1="y";  //Ensures that other turns are skipped
                            ultimate3="y";
                        }
                        else if (finalMatch==true&&ultimate3.equalsIgnoreCase("y")) //If this is the final match and Player 3 has lost already
                        {
                            System.out.println("");
                            System.out.println("*****************Player 2 is VICTORIOUS*****************");
                            System.out.println("Maybe next time, Player 1...");
                            ultimate1="y";  //Ensures that other turns are skipped
                            ultimate2="y";
                        }
                        break;
                    }
                }
                else
                {
                    System.out.println(numErrMessage);  //Error in input...Asks the Player to re-input the number
                    player1Err =true;
                }
            }
            while (player2Err==true&&ultimate2.equalsIgnoreCase("n"))   //Won't be iterated if ultimate2 is "y"...aka Player 2 has lost already
            {
                if (i>=31)
                {
                    break;  //Ensures that the computer doesn't request Player 2's play when the game is over
                }
                System.out.println("");
                System.out.println("***Current Number: "+i+"***");
                System.out.println("Player 2's turn");
                System.out.print("Please enter the amount of numbers you would like to count up by.(1, 2, or 3)_ ");
                String bString = player2.nextLine();
                char bChar = bString.charAt(0);
                int b = Character.getNumericValue(bChar);
                if (1<=b&&b<=3)
                {
                    if (ultimate3.equalsIgnoreCase("y"))
                    {
                        System.out.print("\u000c");
                    }
                    i= i+b;
                    player2Err =false;
                    if (i>=31)  //If 31 is reached in Player 2's turn...Player 2 has lost
                    {
                        if (finalMatch==false)  //If not an ultimate round
                        {
                            System.out.println("");
                            System.out.println("*****************Player 2 has LOST*****************");
                            System.out.println("Maybe next time, Player 2...");
                            System.out.println();
                            System.out.println("Well, the unworthy Player 2 has lost...");
                            System.out.println("But the remaining winners shall determine the ultimate winner, shall we?");
                            do
                            {
                                System.out.println();
                                System.out.print("Do you wish to continue playing? (y/n)_ ");
                                ultimate2 = kb.next();
                                System.out.print("\u000c");
                                if (ultimate2.equalsIgnoreCase("y"))    //Another match...ultimate...Player 2 won't get a turn
                                {
                                    i=0;
                                    finalMatch=true;
                                    break;
                                }
                                else if (ultimate2.equalsIgnoreCase("n"))
                                {
                                    ultimate1="y";  //Ensures that other turns are skipped
                                    ultimate3="y";
                                    break;
                                }
                                else
                                {
                                    System.out.println("Error! Please enter either \"y\" or \"n\"");
                                    replayErr=true;
                                }
                            }while(replayErr==true);
                            break;
                        }
                        else if (finalMatch==true&&ultimate1.equalsIgnoreCase("y")) //ultimate & Player 1 has lost already
                        {
                            System.out.println("");
                            System.out.println("*****************Player 3 is VICTORIOUS*****************");
                            System.out.println("Maybe next time, Player 2...");
                            ultimate2="y";  //Ensures that other turns are skipped
                            ultimate3="y";
                        }
                        else if (finalMatch==true&&ultimate3.equalsIgnoreCase("y")) //ultimate & Player 3 has lost already
                        {
                            System.out.println("");
                            System.out.println("*****************Player 1 is VICTORIOUS*****************");
                            System.out.println("Maybe next time, Player 2...");
                            ultimate1="y";  //Ensures that other turns are skipped
                            ultimate2="y";
                        }
                        break;
                    }
                }
                else
                {
                    System.out.println(numErrMessage);
                    player2Err= true;
                }
            }
            while (player3Err==true&&ultimate3.equalsIgnoreCase("n"))   //Won't be iterated if ultimate3 is "y"...aka Player 3 has lost already
            {
                if (i>=31)
                {
                    break;  //Player 3's turn won't be iterated if the game is over already
                }
                System.out.println("");
                System.out.println("***Current Number: "+i+"***");
                System.out.println("Player 3's turn");
                System.out.print("Please enter the amount of numbers you would like to count up by.(1, 2, or 3)_ ");
                String cString = player3.nextLine();
                char cChar = cString.charAt(0);
                int c = Character.getNumericValue(cChar);
                if (1<=c&&c<=3)
                {
                    System.out.print("\u000c");
                    i= i+c;
                    player3Err =false;
                    if (i>=31)  //Same basic structure as the others in the preceeding players' turn
                    {
                        if (finalMatch==false)
                        {
                            System.out.println("");
                            System.out.println("*****************Player 3 has LOST*****************");
                            System.out.println("Maybe next time, Player 3...");
                            System.out.println();
                            System.out.println("Well, the unworthy Player 3 has lost...");
                            System.out.println("But the remaining winners shall determine the ultimate winner, shall we?");
                            do
                            {
                                System.out.println();
                                System.out.print("Do you wish to continue playing? (y/n)_ ");
                                ultimate3 = kb.next();
                                System.out.print("\u000c");
                                if (ultimate3.equalsIgnoreCase("y"))
                                {
                                    i=0;
                                    finalMatch=true;
                                    break;
                                }
                                else if (ultimate3.equalsIgnoreCase("n"))
                                {
                                    ultimate1="y";
                                    ultimate2="y";
                                    break;
                                }
                                else
                                {
                                    System.out.println("Error! Please enter either \"y\" or \"n\"");
                                    replayErr=true;
                                }
                            }while(replayErr==true);
                            break;
                        }
                        else if (finalMatch==true&&ultimate1.equalsIgnoreCase("y"))
                        {
                            System.out.println("");
                            System.out.println("*****************Player 2 is VICTORIOUS*****************");
                            System.out.println("Maybe next time, Player 3...");
                            ultimate2="y";
                            ultimate3="y";
                        }
                        else if (finalMatch==true&&ultimate2.equalsIgnoreCase("y"))
                        {
                            System.out.println("");
                            System.out.println("*****************Player 1 is VICTORIOUS*****************");
                            System.out.println("Maybe next time, Player 3...");
                            ultimate1="y";
                            ultimate3="y";
                        }
                        break;
                    }
                }
                else
                {
                    System.out.println(numErrMessage);
                    player3Err= true;
                }
            }
        }while(i<31);
        do  //Asks if the user wishes to play again...Returns to the menu
        {
            System.out.println();
            System.out.println("Would you like to play again? (y/n)");
            replay = kbReader.next();
            System.out.println("\u000c");
            if (replay.equalsIgnoreCase("y")||replay.equalsIgnoreCase("n"))
            {
                break;
            }
            else
            {
                System.out.println("Error! Please enter either \"y\" or \"n\"");
                replayErr=true;
            }
        }while(replayErr==true);
        return replay;  //The input is stored in static String replay in class.Tester
    }
}