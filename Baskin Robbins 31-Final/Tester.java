//Author: Gyeongwon Lee
//Date Created: April 12/2017
//This program lets you to play the unique and exciting Korean game of "Baskin Robbins 31"
import java.io.*;   //import java.io
import java.util.*; //import java.util
public class Tester
{
    static String replay = "y"; //Allows class.ThreePlayers to assign a value to this String. 
    public static void main(String args[])
    {
        System.out.println("Welcome to Baskin Robbins 31 Game!\nThis program was created by Gyeongwon Lee\nAnd it's filled with excitement and joy!");
        System.out.println("Please enlarge the screen for optimal gaming experience."); //Introduction
        base(); //Tells the computer to go on to method.base
    }
    public static void base()   //Separate...to ensure that only this part is re-displayed if the player wishes to play again...not the whole intro
    {
        ThreePlayers obj1=new ThreePlayers();   //Connects class.Tester to class.ThreePlayers
        Random randNum = new Random();
        Scanner kb = new Scanner(System.in);
        Scanner kbReader = new Scanner(System.in);
        Scanner player1 = new Scanner(System.in);
        Scanner player2 = new Scanner(System.in);

        boolean case1Aerr = false;  //for easy single player mode
        boolean case1Berr = false;  //for hard single player mode
        boolean replayErr=false;
        boolean modeErr=false;
        String numErrMessage = "\n@@@@@ Error! You can only count up by 1, 2, or 3! @@@@@";
        numErrMessage = numErrMessage.toUpperCase();

        while(replay.equalsIgnoreCase("y"))
        {
            System.out.println("");
            System.out.println("\t***************MENU***************");
            System.out.println("\t1. Single Player (Play with the computer)");
            System.out.println("\t2. Multi Plyaer (Play with your friends)");
            System.out.println("\t3. Instructions (Help! I don't know how to play this game)");
            System.out.print("Please select your choice_ ");

            String menuString = kb.nextLine();
            char menuChar = menuString.charAt(0);   //Takes a String input and converts it to char...switch only works with char

            System.out.print("\u000c");
            switch (menuChar)
            {
                case '1':   //Single Player
                {
                    do
                    {
                        System.out.println("");
                        System.out.println("\t*****This premium version gives you an option to choose the difficulty level!*****");
                        System.out.println("\t1. Easy Mode (for beginners)\n\t2. Hard Mode (Good luck trying to beat it!)");
                        System.out.print("Please select your choice_ ");
                        String modeString = kb.nextLine();
                        char mode = modeString.charAt(0);   //This allows the computer to disply error message if the player enters String
                        //When int is expected...without this, if I were to take int mode straight from user, the program will crash if the user
                        //inputs string
                        System.out.print("\u000c");
                        if (mode=='1')
                        {
                            do  //boolean case1Aerr is used to re-print the message if the user inputs wrong information...mode 1 Loop
                            {
                                System.out.println("");
                                System.out.print("Would you like to go first? (y/n)_ ");
                                String turn = kbReader.next();
                                int num = 0;    //Resets num to zero for each new round of game
                                boolean run = true;
                                System.out.print("\u000c");
                                if (turn.equalsIgnoreCase("y")) //Player goes first
                                {
                                    case1Aerr = false;  //No error in input
                                    do  //Mode 1 Player First Loop
                                    {
                                        System.out.println("");
                                        System.out.println("***Current Number: "+num+"***");
                                        System.out.println("*Player's turn*");
                                        System.out.print("Please enter the amount of numbers you would like to count up by.(1,2, or 3)_ ");
                                        String upString = kb.nextLine();    //These sets of codes allow the computer to bear out-of-ordinary inputs
                                        char upChar= upString.charAt(0);    //Even if an unexpected variable type is input, this way, the program
                                        int up = Character.getNumericValue(upChar); //won't crash
                                        System.out.print("\u000c");
                                        if (1<=up&&up<=3)   //The count input is good
                                        {
                                            num = num+up;   //New total after player's turn
                                            if (num>=31)    //The game has ended since the num reached 31
                                            {
                                                System.out.println("");
                                                System.out.println("*****************Computer is VICTORIOUS*****************");
                                                //If 31 is reached right after the player's turn, the computer has won
                                                System.out.println("Maybe next time, Player...");
                                                do  //Basic structure of a replay questionnaire displayed at the end of each and every game
                                                {
                                                    System.out.println();
                                                    System.out.println("Would you like to play again? (y/n)");
                                                    replay = kbReader.next();
                                                    System.out.println("");
                                                    if (replay.equalsIgnoreCase("y")||replay.equalsIgnoreCase("n"))
                                                    {
                                                        System.out.print("\u000c");
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
                                            System.out.println("");
                                            System.out.println("Current Number: "+ num);
                                            System.out.println("Computer's turn");
                                            int easyCom = randNum.nextInt(3)+1; //In easy mode, the computer simply picks a random number from 1~3
                                            System.out.println("Computer's play...... " + easyCom);
                                            num = num + easyCom;    //New total after computer's turn
                                            if (num>=31)    //The game has ended after the computer's turn...Player has won
                                            {
                                                System.out.println("");
                                                System.out.println("*****************YOU ARE VICTORIOUS*****************");
                                                System.out.println("The computer is impressed and would like to offer a rematch.");
                                                do
                                                {
                                                    System.out.println();
                                                    System.out.println("Would you like to play again? (y/n)");
                                                    replay = kbReader.next();
                                                    System.out.println("");
                                                    if (replay.equalsIgnoreCase("y")||replay.equalsIgnoreCase("n"))
                                                    {
                                                        System.out.print("\u000c");
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
                                        }
                                        else
                                        {
                                            System.out.println(numErrMessage);  //Displays error message
                                        }
                                    }while(run==true);  //will run forever until the computer breaks out of it
                                    break;  //breaks out of the whole mode 1 loop
                                }
                                else if (turn.equalsIgnoreCase("n"))    //Computer goes first...Same basic structure as the one above
                                {
                                    case1Aerr = false;
                                    boolean numErr;
                                    do
                                    {
                                        System.out.println("");
                                        System.out.println("Current Number: "+ num);
                                        System.out.println("Computer's turn");
                                        int Easybot = randNum.nextInt(3)+1;
                                        System.out.println("Computer's play...... "+ Easybot);
                                        num += Easybot;
                                        System.out.println("");
                                        if (num>=31)
                                        {
                                            System.out.println("*****************You are VICTORIOUS*****************");
                                            System.out.println("The computer is impressed and would like to offer a rematch.");
                                            do
                                            {
                                                System.out.println();
                                                System.out.println("Would you like to play again? (y/n)");
                                                replay = kbReader.next();
                                                System.out.println("");
                                                if (replay.equalsIgnoreCase("y")||replay.equalsIgnoreCase("n"))
                                                {
                                                    System.out.print("\u000c");
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
                                        do
                                        {
                                            System.out.println("***Current Number: "+num+"***");
                                            System.out.println("*Player's turn*");
                                            System.out.print("Please enter the amount of numbers you would like to count up by.(1,2, or 3)_ ");
                                            String incrementString = kb.nextLine();
                                            char incrementChar= incrementString.charAt(0);
                                            int increment = Character.getNumericValue(incrementChar);
                                            System.out.print("\u000c");
                                            if (1<=increment&&increment<=3)
                                            {
                                                numErr = false;
                                                num += increment;
                                                if (num>=31)
                                                {
                                                    System.out.println("");
                                                    System.out.println("*****************Computer is VICTORIOUS*****************");
                                                    System.out.println("Maybe next time, Player...");
                                                    do
                                                    {
                                                        System.out.println("\nWould you like to play again? (y/n)");
                                                        replay = kbReader.next();
                                                        System.out.println("");
                                                        if (replay.equalsIgnoreCase("y")||replay.equalsIgnoreCase("n"))
                                                        {
                                                            System.out.print("\u000c");
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
                                            }
                                            else
                                            {
                                                numErr = true;
                                                System.out.println(numErrMessage);
                                                System.out.println();
                                            }
                                        }while(numErr == true);
                                    }while(num<31);
                                    break;
                                }
                                else    //Error in telling the computer whether or not the user wants to go first
                                {
                                    System.out.println("Please enter either \"y\" or \"n\"");
                                    case1Aerr = true;   //Repeat the mode 1 Loop
                                }
                            }while (case1Aerr == true);
                            break;
                        }
                        else if (mode=='2') //Difficult mode
                        {
                            do
                            {
                                System.out.println("");
                                System.out.print("Would you like to go first? (y/n)_ ");
                                String turn = kbReader.next();
                                int num = 0;
                                boolean run = true;
                                System.out.print("\u000c");
                                if (turn.equalsIgnoreCase("y"))
                                {
                                    case1Berr = false;
                                    int goodNum = 2;    //The number, with which the computer ends its turn, will let it win all the time
                                    do
                                    {
                                        System.out.println("");
                                        System.out.println("***Current Number: "+num+"***");
                                        System.out.println("*Player's turn*");
                                        System.out.print("Please enter the amount of numbers you would like to count up by.(1,2, or 3)_ ");
                                        String upString = kb.nextLine();
                                        char upChar= upString.charAt(0);
                                        int up = Character.getNumericValue(upChar);
                                        System.out.print("\u000c");
                                        if (1<=up&&up<=3)
                                        {
                                            num = num+up;
                                            if (num>=31)
                                            {
                                                System.out.println("");
                                                System.out.println("*****************Computer is VICTORIOUS*****************");
                                                System.out.println("Maybe next time, Player...");
                                                do
                                                {
                                                    System.out.println("\nWould you like to play again? (y/n)");
                                                    replay = kbReader.next();
                                                    System.out.println("");
                                                    if (replay.equalsIgnoreCase("y")||replay.equalsIgnoreCase("n"))
                                                    {
                                                        System.out.print("\u000c");
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
                                            System.out.println("");
                                            System.out.println("Current Number: "+ num);
                                            System.out.println("Computer's turn");
                                            if (goodNum<=num)   //If the goodNum is smaller than the current total
                                            {
                                                goodNum += 4;   //Underlying tactic
                                            }
                                            int compNum = goodNum-num;
                                            if (compNum==4) //Can't get to that number in one turn...
                                            {
                                                int waitNum = randNum.nextInt(3)+1;
                                                compNum -= waitNum; //Play a random number until it is possible to get back to the goodNum
                                            }
                                            System.out.println("Computer's play...... " + compNum);
                                            num = num + compNum;
                                            if (num>=31)
                                            {
                                                System.out.println("");
                                                System.out.println("*****************YOU ARE VICTORIOUS*****************");
                                                //Would only happen if the player already knows the underlying tactic to winning
                                                System.out.println("The computer is impressed and would like to offer a rematch.");
                                                //That's why it's pretty impressed
                                                do
                                                {
                                                    System.out.println("\nWould you like to play again? (y/n)");
                                                    replay = kbReader.next();
                                                    System.out.println("");
                                                    if (replay.equalsIgnoreCase("y")||replay.equalsIgnoreCase("n"))
                                                    {
                                                        System.out.print("\u000c");
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
                                        }
                                        else
                                        {
                                            System.out.println(numErrMessage);
                                        }
                                    }while(run==true);  //Loops forever until the computer breaks out of it
                                    break;
                                }
                                else if (turn.equalsIgnoreCase("n"))    //The player can never win
                                {
                                    case1Berr = false;
                                    boolean numErr;
                                    int comp = 2;   //The computer starts by playing 2
                                    do
                                    {
                                        System.out.println("");
                                        System.out.println("Current Number: "+ num);
                                        System.out.println("Computer's turn");
                                        System.out.println("Computer's play...... "+ comp);
                                        num += comp;    //New total
                                        do
                                        {
                                            System.out.println("");
                                            System.out.println("***Current Number: "+num+"***");
                                            System.out.println("*Player's turn*");
                                            System.out.print("Please enter the amount of numbers you would like to count up by.(1,2, or 3)_ ");
                                            String incrementString= kb.nextLine();
                                            char incrementChar = incrementString.charAt(0);
                                            int increment = Character.getNumericValue(incrementChar);
                                            System.out.print("\u000c");
                                            if (increment>=1&&increment<=3)
                                            {
                                                numErr = false;
                                                num += increment;
                                                comp = 4-increment; //Simply keep playing 4 minus the number that the user played...
                                                //ex. User plays 1, comp. plays 3; User plays 2, comp. plays 2; User plays 3, comp. plays 1
                                            }
                                            else
                                            {
                                                numErr = true;
                                                System.out.println(numErrMessage);
                                            }
                                        }while(numErr == true);
                                    }while(num<31); //Once 31 is reached, done with the loop
                                    System.out.println("");
                                    System.out.println("*****************Computer is VICTORIOUS*****************");
                                    System.out.println("Maybe next time, Player...");
                                    do
                                    {
                                        System.out.println("\nWould you like to play again? (y/n)");
                                        replay = kbReader.next();
                                        System.out.println("");
                                        if (replay.equalsIgnoreCase("y")||replay.equalsIgnoreCase("n"))
                                        {
                                            System.out.print("\u000c");
                                            break;
                                        }
                                        else
                                        {
                                            System.out.println("Error! Please enter either \"y\" or \"n\"");
                                            replayErr=true;
                                        }
                                    }while(replayErr==true);
                                }
                                else
                                {
                                    System.out.println("Please enter either \"y\" or \"n\"");
                                    case1Berr = true;
                                }
                            }while (case1Berr == true);
                            break;
                        }
                        else
                        {
                            modeErr=true;
                            System.out.println();
                            System.out.println("Error! Please enter either \"1\" or \"2\"");
                        }
                    }while(modeErr==true);
                }
                break;

                case '2':   //Multiplayer
                {
                    int people=0;
                    boolean peopleErr = false;  //Error in inputing the amount of people playing
                    boolean player1Err = false; //Error in player 1's input
                    boolean player2Err = false; //Error in player 2's input
                    for (int i=0; i<=31; i+=0)
                    {
                        if (i==0)   //Only runs the first time this multiplyaer mode is looped
                        {
                            do
                            {
                                System.out.println();
                                System.out.print("How many number of people are playing? (2~3)_ "); 
                                String peopleString = kb.nextLine();
                                char peopleChar = peopleString.charAt(0);
                                people = Character.getNumericValue(peopleChar);
                                System.out.print("\u000c");
                                if (people==2)
                                    break;
                                else if (people==3)
                                {
                                    replay =(obj1.ThreePlayers());  //Redirects to class.ThreePlayers...gets the output from class.ThreePlayers
                                    //and store it in static String replay
                                    break;
                                }
                                else
                                {
                                    peopleErr=true;
                                    System.out.println("ERROR! Please enter either 2 or 3!");
                                }
                            }while (peopleErr==true);
                        }
                        if (people==3)
                            break;  //Makes sure once class.ThreePlayers is run, two-player mode is not run
                        do
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
                                i= i+a;
                                if (i>=31)  //If player 1 reaches 31
                                {
                                    System.out.println("");
                                    System.out.println("*****************Player 2 is VICTORIOUS*****************");
                                    System.out.println("Maybe next time, Player 1...");
                                    do
                                    {
                                        System.out.println("\nWould you like to play again? (y/n)");
                                        replay = kbReader.next();
                                        System.out.println("");
                                        if (replay.equalsIgnoreCase("y")||replay.equalsIgnoreCase("n"))
                                        {
                                            System.out.print("\u000c");
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
                                player1Err = false;
                            }
                            else
                            {
                                System.out.println(numErrMessage);  //Error in typing in one of 1, 2, or 3
                                player1Err =true;
                            }
                        }while (player1Err==true);
                        do
                        {
                            if (i>=31)
                            {
                                break;  //Makes sure that the computer doesn't ask for player 2's play once the game is over
                            }
                            System.out.println("");
                            System.out.println("***Current Number: "+i+"***");
                            System.out.println("Player 2's turn");
                            System.out.print("Please enter the amount of numbers you would like to count up by.(1, 2, or 3)_ ");
                            String bString = player2.nextLine();
                            char bChar = bString.charAt(0);
                            int b = Character.getNumericValue(bChar);
                            System.out.print("\u000c");
                            if (1<=b&&b<=3)
                            {
                                i= i+b;
                                player2Err =false;
                                if (i>=31)  //If 31 is reached in Player 2's turn
                                {
                                    System.out.println("");
                                    System.out.println("*****************Player 1 is VICTORIOUS*****************");
                                    System.out.println("Maybe next time, Player 2...");
                                    do
                                    {
                                        System.out.println("\nWould you like to play again? (y/n)");
                                        replay = kbReader.next();
                                        System.out.println("");
                                        if (replay.equalsIgnoreCase("y")||replay.equalsIgnoreCase("n"))
                                        {
                                            System.out.print("\u000c");
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
                            }
                            else
                            {
                                System.out.println(numErrMessage);
                                player2Err= true;
                            }
                        }while (player2Err==true);
                        if (i>=31)
                        {
                            break;
                        }
                    }
                }
                break;
                case '3':   //Instructions
                {
                    System.out.println("");
                    System.out.println("Baskin Robbins 31 is a popular Korean game.");
                    System.out.println("Each of the players gets a turn in sequence (you after me, and me after you).");
                    System.out.println("Each player has the choice of calling out anything from 1-3 digits.");
                    System.out.println("*Meaning, if you start, you can say 1, 1 and 2 or, 1, 2 and 3.");
                    System.out.println("So, the next player can choose to count the number up by 1, 2, or 3.");
                    System.out.println("**Note: A player must count up by at least 1 in his/her turn.");
                    System.out.println("As the game continues, the number gets larger(The number is for all of the players to share).");
                    System.out.println("The goal of the game is to force the other player to count the number \"31\"");
                    System.out.println("***Yes, the player that has to count to 31 LOSES!");
                    System.out.println("Still not sure? Well, let's figure it out by playing!");
                    System.out.println("");
                    replay = "y";   //Allows the computer to go back to the top of the loop and disply menu
                }
                break;
                default:    //Error in input from menu screen
                {
                    System.out.println("\nERROR! Please select one of the given numbers.\n");
                    replay= "y";
                }
            }
        }
        System.out.println("Thank you for playing! Let's play again soon :)");  //Good-bye message...only displayed if the user doesn't wish to play again
    }
}