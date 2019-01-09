//Gyeongwon Lee
//Sep.21/2017
//Oct.6/2017...coding to account for the fact that more than five-in-a-row (i.e. six-in-a-row) shall not be considered as a win.

//This section Checks if five stones are connected (either black or white, depending on who just went)
static boolean connected = false;
static public boolean checkWhiteStone;

static public boolean GameOverChecker()
{
  checkWhiteStone = blackTurn;
  ArrayList <int[]> listName;
  if (checkWhiteStone==true)
  {
    listName = whiteList;
  } else
  {
    listName = blackList;
  }
  for (int i=0; i<listName.size(); i++)
  {
    for (int j=0; j<listName.size(); j++)
    {
      if (j==i)
      {
        continue;
      }
      for (int k=0; k<listName.size(); k++)
      {
        if (k==j||k==i)
        {
          continue;
        }
        for (int l=0; l<listName.size(); l++)
        {
          if (l==k||l==j||l==i)
          {
            continue;
          }
          for (int m=0; m<listName.size(); m++)
          {
            if (m==l||m==k||m==j||m==i)
            {
              continue;
            }
            if (horizontal(listName, i, j, k, l, m)||vertical(listName, i, j, k, l, m)||positiveDiagonal(listName, i, j, k, l, m)||negativeDiagonal(listName, i, j, k, l, m))
            {
              gameOver=true;
              declareWinner();
              break;
            }
          }
          if (gameOver)
            break;
        }
        if (gameOver)
          break;
      }
      if (gameOver)
        break;
    }
    if (gameOver)
      break;
  } 
  return gameOver;
}

static private boolean horizontal(ArrayList<int[]> stones, int a, int b, int c, int d, int e)
{
  int[] aArr = stones.get(a);
  int[] bArr = stones.get(b);
  int[] cArr = stones.get(c);
  int[] dArr = stones.get(d);
  int[] eArr = stones.get(e);
  if (aArr[1]==bArr[1]&&bArr[1]==cArr[1]&&cArr[1]==dArr[1]&&dArr[1]==eArr[1])
  {
    if (aArr[0]==(bArr[0]-35)&&bArr[0]==(cArr[0]-35)&&cArr[0]==(dArr[0]-35)&dArr[0]==(eArr[0]-35))
    {
      connected = true;
      for (int i=0; i<stones.size(); i++)
      {
        if (i==a||i==b||i==c||i==d||i==e)
          continue;
        int[] fArr=stones.get(i);
        if (fArr[1]==aArr[1])
        {
          if (fArr[0]==(eArr[0]+35)||fArr[0]==(aArr[0]-35))
          {
            connected = false; 
            break;
          }
        }
      }
    }
  }
  return connected;
}
static private boolean vertical(ArrayList<int[]> stones, int a, int b, int c, int d, int e)
{
  int[] aArr = stones.get(a);
  int[] bArr = stones.get(b);
  int[] cArr = stones.get(c);
  int[] dArr = stones.get(d);
  int[] eArr = stones.get(e);
  if (aArr[0]==bArr[0]&&bArr[0]==cArr[0]&&cArr[0]==dArr[0]&&dArr[0]==eArr[0])
  {
    if (aArr[1]==(bArr[1]-35)&&bArr[1]==(cArr[1]-35)&&cArr[1]==(dArr[1]-35)&dArr[1]==(eArr[1]-35))
    {
      connected = true;
      for (int i=0; i<stones.size(); i++)
      {
        if (i==a||i==b||i==c||i==d||i==e)
          continue;
        int[] fArr=stones.get(i);
        if (fArr[0]==aArr[0])
        {
          if (fArr[1]==(eArr[1]+35)||fArr[1]==(aArr[1]-35))
          {
            connected = false; 
            break;
          }
        }
      }
    }
  }
  return connected;
}
static private boolean positiveDiagonal(ArrayList<int[]> stones, int a, int b, int c, int d, int e)
{
  int[] aArr = stones.get(a);
  int[] bArr = stones.get(b);
  int[] cArr = stones.get(c);
  int[] dArr = stones.get(d);
  int[] eArr = stones.get(e);
  if (aArr[0]==(bArr[0]-35)&&bArr[0]==(cArr[0]-35)&&cArr[0]==(dArr[0]-35)&dArr[0]==(eArr[0]-35))
  {
    if (aArr[1]==(bArr[1]+35)&&bArr[1]==(cArr[1]+35)&&cArr[1]==(dArr[1]+35)&dArr[1]==(eArr[1]+35))
    {
      connected = true;
      for (int i=0; i<stones.size(); i++)
      {
        if (i==a||i==b||i==c||i==d||i==e)
          continue;
        int[] fArr=stones.get(i);
        if (fArr[0]==(aArr[0]-35))
        {
          if (fArr[1]==(aArr[1]+35))
          {
            connected = false; 
            break;
          }
        }
        else if(fArr[0]==(eArr[0]+35))
        {
           if(fArr[1]==(eArr[1]-35))
           {
            connected = false;
            break;
           }
        }
      }
    }
  }
  return connected;
}
static private boolean negativeDiagonal(ArrayList<int[]> stones, int a, int b, int c, int d, int e)
{
  int[] aArr = stones.get(a);
  int[] bArr = stones.get(b);
  int[] cArr = stones.get(c);
  int[] dArr = stones.get(d);
  int[] eArr = stones.get(e);
  if (aArr[0]==(bArr[0]-35)&&bArr[0]==(cArr[0]-35)&&cArr[0]==(dArr[0]-35)&dArr[0]==(eArr[0]-35))
  {
    if (aArr[1]==(bArr[1]-35)&&bArr[1]==(cArr[1]-35)&&cArr[1]==(dArr[1]-35)&dArr[1]==(eArr[1]-35))
    {
      connected = true;
      for (int i=0; i<stones.size(); i++)
      {
        if (i==a||i==b||i==c||i==d||i==e)
          continue;
        int[] fArr=stones.get(i);
        if (fArr[0]==(aArr[0]-35))
        {
          if (fArr[1]==(aArr[1]-35))
          {
            connected = false; 
            break;
          }
        }
        else if(fArr[0]==(eArr[0]+35))
        {
           if(fArr[1]==(eArr[1]+35))
           {
            connected = false;
            break;
           }
        }
      }
    }
  }
  return connected;
}
static private void declareWinner()
{
  if (checkWhiteStone==true)
  {
    System.out.println("\n********WHITE WON!!!***********\n");
  } else
  {
    System.out.println("\n********BLACK WON!!!***********\n");
  }
}