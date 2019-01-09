//Oct 6/2017
//Creator: Gyeongwon Lee
//This program is a Two-Player Tic-Tac-Toe machine.
//Ues your mouse to play
import java.util.*;
static int lengthOfSide=702;
static int lengthOfBlock = lengthOfSide/3;
static int lengthOfHalfBlock = lengthOfBlock/2;
static boolean xTurn = true;
static ArrayList<int[]> xList = new ArrayList<int[]>();
static ArrayList<int[]> oList = new ArrayList<int[]>();
static ArrayList<int[]> wholeList = new ArrayList<int[]>();
static boolean blinkOn = true;
static int count=0;

void setup()
{
  background(#2199AD);  //Need some good colours... Suggestions? (0,255,200)***************************************************************************************************
  size(702, 702);  //3 blocks (horizontally and vertically) each 234 units in length
  strokeWeight(6);
  stroke(#177D8E);
  for (int i=lengthOfBlock; i<lengthOfSide; i+=lengthOfBlock)
  {
    line(0, i, lengthOfSide, i);
    line(i, 0, i, lengthOfSide);
  }
}
void draw()
{
  if (gameOver==true)
  {
    frameRate(1);
    if (blinkOn)
    {
      fill(255, 0, 0, 100);
      rect(0, 0, lengthOfSide, lengthOfSide); 
      blinkOn=false;
    } 
    else
    {
      setup();
      play();
      blinkOn=true;
    }
  }
  else if (draw)
  {
    frameRate(1);
    if (blinkOn)
    {
      fill(255, 0, 0, 100);
      rect(0, 0, lengthOfSide, lengthOfSide); 
      blinkOn=false;
    } 
    else
    {
      setup();
      play();
      blinkOn=true;
      count++;
    }
    
    if(count==3)
     {
         setup();
         gameOver=false;
         draw=false;
         xList.clear();
         oList.clear();
         wholeList.clear();
         count=0;
         frameRate(60);
     }
  }
}
void mouseClicked()
{
  for (int i=lengthOfHalfBlock; i<lengthOfSide; i+=lengthOfBlock)
  {
    for (int j=lengthOfHalfBlock; j<lengthOfSide; j+=lengthOfBlock)
    {
      if ((mouseX>i-lengthOfHalfBlock&&mouseX<i+lengthOfHalfBlock)&&(mouseY>j-lengthOfHalfBlock&&mouseY<j+lengthOfHalfBlock))
      {
        if (playedAlready(i, j)==false)
        {
          play(i, j);
        } else
        {
          System.out.println("!!!!!!!!!!ILLEGAL MOVE!!!!!!!!!!");
        }
      }
    }
  }
}
boolean playedAlready(int x, int y)
{
  boolean illegal=false;
  for (int i=0; i<wholeList.size(); i++)
  {
    int[] temp = new int[2];
    temp = wholeList.get(i);
    if (x==temp[0]&&y==temp[1])
    {
      illegal = true;
      break;
    }
  }
  return illegal;
}
static int withGap = lengthOfHalfBlock-20;
void play(int x, int y)
{
  int[] move = new int[2];
  move[0]=x;
  move[1]=y;
  wholeList.add(move);
  if (xTurn)
  {
    stroke(255, 0, 0);
    line(x-withGap, y-withGap, x+withGap, y+withGap);
    line(x-withGap, y+withGap, x+withGap, y-withGap);
    xTurn = false;
    xList.add(move);
    checkForGameOver();
  } else
  {
    stroke(120, 255, 0);  
    fill(#2199AD);
    ellipse(x, y, 2*withGap, 2*withGap);
    xTurn = true;
    oList.add(move);
    checkForGameOver();
  }
}
void play()
{
  int[] move = new int[2];
  int x,y;
  for (int i=0; i<xList.size(); i++)
  {
    move = xList.get(i);
    x=move[0];
    y=move[1];
    stroke(255, 0, 0);
    line(x-withGap, y-withGap, x+withGap, y+withGap);
    line(x-withGap, y+withGap, x+withGap, y-withGap);
  }
  for (int i=0; i<oList.size(); i++)
  {
    move = oList.get(i);
    x=move[0];
    y=move[1];
    stroke(120, 255, 0);  
    fill(#2199AD);
    ellipse(x, y, 2*withGap, 2*withGap);
  }
}