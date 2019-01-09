//Date Created: September 2/2017  
//Sep 21/2017
//Oct 6/2017

//Author: Gyeongwon Lee
//This program lets two users play a game of Omok (Connect Five). Click on the location you wish to place your stone to play that move. 
//The program will automatically tell you if one of you has won. Keep in mind that more than five stones in a row (i.e. six-in-a-row) doesn't count as a win.
//***IMPORTANT*** This program follows the most unrestrictive/simple Omok rule called "Gomoku rule" (Refer to http://blog.naver.com/tnrhgk1212/50186563296)
import java.util.Arrays;
static boolean blackTurn = true;
static int stoneSize = 30;
static ArrayList<int[]>blackList = new ArrayList<int[]>();
static ArrayList<int[]>whiteList = new ArrayList<int[]>();
static ArrayList<int[]>wholeList = new ArrayList<int[]>();
static int count=0;
static boolean veryFirstTime = true;
static boolean gameOver = false;
static boolean playerIsBlack;

void setup()
{
  size(700, 700);  //20 "blocks" * each 35 units in width
  background(#D18535);
  fill(0);
  for (int i=35; i<700; i+=35)
  {
    line(35, i, 665, i);  //Horizontal lines of the board
    line(i, 35, i, 665);  //Vertical lines of the board
  }
  for (int i=140; i<700; i+=210)
  {
    starPoint(i, 140);
    starPoint(i, 350);
    starPoint(i, 560);
  }
  blackStone();
  whiteStone();  
  if (blackTurn&&count==0)
    System.out.print("\nBlack's Turn");
  else if (count==0)
    System.out.print("\nWhite's Turn");
  count++;
}
void starPoint(int x, int y)
{
  ellipse(x, y, 5, 5);
}

void draw()
{
  if (gameOver==false)
  {
    setup();
    fill(#D1CACA);
    ellipse(mouseX, mouseY, stoneSize, stoneSize);
  }
}
void mouseClicked()
{
  count=0;
  for (int i=35; i<700; i+=35)
  {
    for (int j=35; j<700; j+=35)
    {
      if ( (mouseX>=(i-17)&&mouseX<=(i+17))&&(mouseY>=(j-17)&&mouseY<=(j+17)) )
      {
        if (placedAlready(i, j)==false)
        {
          System.out.print("...Completed");
          if (blackTurn)
          {
            blackStone(i, j);
          } else
          {
            whiteStone(i, j);
          }
        } else
        {
          System.out.println("\nOOPS! Someone placed his/her stone there already!");
        }
      }
    }
  }
}
void blackStone(int x, int y)
{
  int[] blackArray = new int[2];
  blackArray[0]=x;
  blackArray[1]=y;
  blackList.add(blackArray);
  wholeList.add(blackArray);
  fill(0);
  ellipse(x, y, stoneSize, stoneSize);
  blackTurn = false;
  GameOverChecker();
}
void blackStone()
{
  for (int i=0; i<blackList.size(); i++)
  {
    int[] temp = new int[2];
    temp = blackList.get(i);
    fill(0);
    ellipse(temp[0], temp[1], stoneSize, stoneSize);
  }
}

void whiteStone(int x, int y)
{
  int[] whiteArray = new int[2];
  whiteArray[0]=x;
  whiteArray[1]=y;
  whiteList.add(whiteArray);
  wholeList.add(whiteArray);
  fill(#FFFFFF);
  ellipse(x, y, stoneSize, stoneSize);
  blackTurn = true;
  GameOverChecker();
}
void whiteStone()
{
  for (int i=0; i<whiteList.size(); i++)
  {
    int[] temp = new int[2]; 
    temp = whiteList.get(i);
    fill(#FFFFFF);
    ellipse(temp[0], temp[1], stoneSize, stoneSize);
  }
}

boolean placedAlready(int x, int y)
{
  boolean placedAlready=false;
  int[] temp = new int[2];
  for (int i=0; i<wholeList.size(); i++)
  {
    temp = wholeList.get(i);
    if (x==temp[0]&&y==temp[1])
    {
      placedAlready=true;
      break;
    }
  }
  return placedAlready;
}