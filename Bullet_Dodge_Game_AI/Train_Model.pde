/*
public static class Train_Model
{
  private static final int trainXRate = 13;
  private static final int trainYRate = 10;
  public static int useXRate;
  public static int useYRate;
  private static int[][] startLocations = {{0,0,1,1}, {0,300,1,0},{0,600,1,-1},{400,600,0,-1},{800,600,-1,-1},{800,300,-1,0},{800,0,-1,1},{400,0,0,1},
                                           {0,150,1,0},{0,450,1,0},{200,600,0,-1},{600,600,0,-1},{800,450,-1,0},{800,150,-1,0}};
  private static int startLocCount = 0;
  private static boolean increment = true;
  public static void trainBullet()
  {
    int[] temp = new int[4];;
    if (x<=0-w||x>=800+w||y<=0-w||y>=600+w)
    {
      if(startLocCount==startLocations.length-1)
        increment = false;
      else if (increment==false&&startLocCount==0)
        increment = true;
        
      if(increment)
        temp = startLocations[startLocCount++];
      else
        temp = startLocations[startLocCount--];
      x = temp[0];
      y = temp[1];
      useXRate = temp[2]*trainXRate;
      useYRate = temp[3]*trainYRate;
    }
    x += useXRate;  //The BULLET will be moving obviously 
    y += useYRate;
  }
  public static void trainExtraBullet()
  {
    if (xExtra<-60||xExtra>860||yExtra<-60||yExtra>660)  //If the EXTRA leaves the screen/battlefield,
    {
      if (rand.nextInt(2)==0)
        xExtra = 0-wExtra/2;
      else
        xExtra = 800+wExtra/2;

      if (rand.nextInt(2)==0)
        yExtra = 0-wExtra/2;
      else
        yExtra = 600+wExtra/2;

      xRateExtra = 8;
      yRateExtra = 8;

      if (xExtra>400)  //If the EXTRA approaches from the right side, it should have a negative x-speed
      {
        xRateExtra = -xRateExtra;
      }
      if (yExtra>=300)  //If the EXTRA approaches from the bottom side, it should have a negative y-speed
      {
        yRateExtra = -yRateExtra;
      }
    }
    xExtra += xRateExtra;  //The EXTRA keeps moving
    yExtra += yRateExtra;
  }
  public static void trainSwervyBullet()
  {
    if (xSwerve<-30||xSwerve>830)  //If the SWERVY leaves the screen,
    {
      xSwerve = rand.nextInt(2);  //Reset its position
      ySwerve = 300;
      xRateSwerve = 4;
      yRateSwerve = 15;
    }

    if (xSwerve==1)  //We want some of the SWERVY bullets to come off from the right side (50/50 chance)
    {
      xSwerve = 800;  //The right side of the screen
      xRateSwerve = -xRateSwerve;  //Yup, move towards the left side of the screen
    }
    xSwerve += xRateSwerve;  //the SWERVY is constantly moving, obviously
    ySwerve += yRateSwerve;

    if (ySwerve>=midLine)
      yRateSwerve--;
    else
      yRateSwerve++;
  }
}
*/