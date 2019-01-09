//Oct 6/2017
//Gyeongwon Lee
//This section checks if the game is over

static int[] a = new int[2];
static int[] b = new int[2];
static int[] c = new int[2];
static boolean gameOver = false;
static boolean draw = false;
void checkForGameOver()
{
  ArrayList<int[]> listName;
  if (xTurn==false) //x just Played
  {
    listName = xList;
  } else
  {
    listName = oList;
  }

  for (int i=0; i<listName.size(); i++)
  {
    for (int j=0; j<listName.size(); j++)
    {
      if (j==i)
        continue;
      for (int k=0; k<listName.size(); k++)
      {
        if (k==j||k==i)
          continue;
        
        a = listName.get(i);
        b = listName.get(j);
        c = listName.get(k);
        if(vertical()||horizontal()||posDiag()||negDiag())
        {
           if (xTurn==false)  //x just played
             System.out.println("********************Player X WON!!!********************");
           else
             System.out.println("********************Player O WON!!!********************");
           
           break;
        }
      }
      if(gameOver)
        break;
    }
    if(gameOver)
      break;
  }
  
  if(wholeList.size()==9&&gameOver==false)
  {
    draw = true;
    System.out.println("********************It's a DRAW!!!********************");
  }
}

private boolean vertical()
{
  gameOver = false;
  if (a[0]==b[0]&&b[0]==c[0])
  {
    gameOver = true;
  }
  return gameOver;
}
private boolean horizontal()
{
  gameOver = false;
  if (a[1]==b[1]&&b[1]==c[1])
  {
     gameOver = true; 
  }
  return gameOver;
}
private boolean posDiag()
{
  gameOver = false;
  if (a[0]==b[0]-lengthOfBlock&&b[0]==c[0]-lengthOfBlock)
  {
    if (a[1]==b[1]+lengthOfBlock&&b[1]==c[1]+lengthOfBlock)
     gameOver = true; 
  }
  return gameOver;
}
private boolean negDiag()
{
  gameOver = false;
   if (a[0]==b[0]-lengthOfBlock&&b[0]==c[0]-lengthOfBlock)
   {
     if (a[1]==b[1]-lengthOfBlock&&b[1]==c[1]-lengthOfBlock)
     gameOver = true;
   }
   return gameOver;
}

/*private void blink()
{
   PGraphics pg = createGraphics(100,200);
   pg.beginDraw();
   for(int count=0; count<=60000; count++)
   {
     if(count%5000==0&&count!=60000)
       background(100);
     else
       pg.clear();
   }
   System.out.println("DONE");
}*/