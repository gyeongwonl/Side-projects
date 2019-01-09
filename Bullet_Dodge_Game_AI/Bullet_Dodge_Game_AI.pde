//Author: Gyeongwon Lee //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>//
//Date Created: May 17/2018 ~ (WORK IN PROGRESS)
//Remember the Bullet_Dodge_Game I created last summer? Let's make AI play that game!

//POSSIBLE IMPROVEMENT:
//Test different learning algorithms
//Get rid of the unnecessary stuff
private static Random rand = new Random();  //My random number generator
private static int w=140;  //For changing BULLET size
private static int x=0-w/2, y=0-w/2;  //These variables represent the x and y position of the BULLET
private static int xRate, yRate;  //Represent the speed of the BULLET

private static boolean increaseW = true;
private static final int gracePeriodCount = 30;
private static int red=255, green=0, blue=0;  //For changing the color of the BULLET when it bounces

private static int count=0;  //Counts how many iterations have been done (proportional to how much time has elapsed in the game)
private static boolean gameOver = false; 
private static int gameOverLoopCount = 0;
private static double userX = 400, userY = 300;  //Stores the x and y coordinates of the USER's character when the game ended
private static double loseX, loseY;  //Stores the x and y coordinates of the BULLET when the game ended

private static final int wExtra = 170;  //Size of EXTRA bullet
private static int xExtra = 0-wExtra/2, yExtra = 0-wExtra/2;  //Initial location of EXTRA bullet
private static final int[][] extraStartLocations = {{0-wExtra/2, 0-wExtra/2}, {0-wExtra/2, 600+wExtra/2}, {800+wExtra/2, 600+wExtra/2}, {800+wExtra/2, 0-wExtra/2}};
private static int extraStartLocCount = 0;
private static boolean extraIncrement = true;
private static int xRateExtra, yRateExtra;   //Speed of the EXTRA
private static final int beginExtraCount = 100;

private static int xSwerve, ySwerve = 300, xRateSwerve, yRateSwerve;
private static final int midLine = 300;
private static final int wSwerve = 150;
private static final int beginSwerveCount = 100;

private static final boolean NEURALNET_PLAYING = true;
private static final boolean LOGGING_RESULTS = false;
private static final boolean AUTOMATIC_LOGGING = true;
private static final boolean WANT_TO_TRAIN = true;  //If set to true, remember to set TRAIN to true too!
private static boolean TRAIN = true;
private static final boolean PREVENT_OVERFITTING_A = false;
private static final boolean PREVENT_OVERFITTING_B = true;
private static boolean justTested = false;
private static final int[][] trainSet = {{7, 7, 8, 6, 5, 20}, {8, 5, 8, 7, 4, 21}, {5, 9, 10, 7, 4, 20}, {9, 5, 10, 8, 5, 21}};
private static int trainSetCount = 0;
private static final int[] generationsToRecord = {1,3,5,7,9};  //TEST environment records the performnace of the geneartion exact same population of the designated generation
private static int recordCount = 0;
private static int[] records = new int[generationsToRecord.length];
private static final int neuralNetCount = 1000;

//private NeuralNet[] neuralNets = new NeuralNet[neuralNetCount];
private static NeuralNetSimple[] neuralNets = new NeuralNetSimple[neuralNetCount];
private static int[] neuralNetX = new int[neuralNetCount];
private static int[] neuralNetY = new int[neuralNetCount];
private static int[][] pastNeuralNetX = new int[neuralNetCount][100];
private static int[][] pastNeuralNetY = new int[neuralNetCount][100];
//private boolean doNotRecord = false;
private static boolean[] netGameOver = new boolean[neuralNetCount];
private static int[] top5NetIndex = new int[5];
private static boolean allGameOver = false;
private static int generationCount = 0;
private static boolean anotherGeneration = false;

void setup()  //Executed at the start of each game...initializes all the necessary variables and conditions
{
  size(800, 600);  //The size of our playground/battlefield
  Arrays.fill(neuralNetX, 400);
  Arrays.fill(neuralNetY, 300);
  Arrays.fill(netGameOver, false);
  if (!WANT_TO_TRAIN)
    Arrays.fill(top5NetIndex, -1);
  allGameOver = false;

  if (!NEURALNET_PLAYING)  //Assertions in place in order to ensure logical variable declarations
    assert(WANT_TO_TRAIN==false);
  if (!WANT_TO_TRAIN)
  {
    assert(TRAIN == false);
    assert(justTested == false);
    assert(PREVENT_OVERFITTING_A == false);
    assert(PREVENT_OVERFITTING_B == false);
  }
  if (PREVENT_OVERFITTING_A&&PREVENT_OVERFITTING_B)
    assert(false);

  if (generationCount-1==generationsToRecord[recordCount])
    TRAIN = false;
  else if (WANT_TO_TRAIN)
    TRAIN = true;

  w = 140;
  x = 0-w/2;
  y = 0-w/2;
  if (TRAIN||!WANT_TO_TRAIN)  //Set up the BULLET
  {
    if (PREVENT_OVERFITTING_A)
    {
      do
      {
        xRate = rand.nextInt(10);
        yRate = rand.nextInt(10);
      }
      while (xRate<5||yRate<5);
    } else if (PREVENT_OVERFITTING_B)
    {
      xRate = trainSet[trainSetCount][0];
      yRate = trainSet[trainSetCount][1];
    } else
    {
      xRate = 7;
      yRate = 7;
    }
  } else  //TEST Environment
  {
    xRate = 8;
    yRate = 9;
  }

  increaseW = true;
  red = 255;
  green = 0;
  blue = 0;
  count = 0;
  gameOver = false;
  gameOverLoopCount = 0;
  userX = 400;
  userY = 300;

  xExtra = 0-wExtra/2;
  yExtra = 0-wExtra/2;
  if (TRAIN||!WANT_TO_TRAIN)   //Set up the EXTRA BULLET
  {
    if (PREVENT_OVERFITTING_A)
    {
      do
      {
        xRateExtra = rand.nextInt(10);
        yRateExtra = rand.nextInt(10);
      }
      while (xRateExtra<5||yRateExtra<5);
    } else if (PREVENT_OVERFITTING_B)
    {
      xRateExtra = trainSet[trainSetCount][2];
      yRateExtra = trainSet[trainSetCount][3];
    } else
    {
      xRateExtra = 8;
      yRateExtra = 6;
    }
  } else
  {
    xRateExtra = 9;
    yRateExtra = 6;
  }
  extraStartLocCount = 0;
  extraIncrement = true;

  xSwerve = 0-wSwerve/2;
  ySwerve = 300;
  if (TRAIN||!WANT_TO_TRAIN)  //Set up the SWERVY BULLET
  {
    if (PREVENT_OVERFITTING_A)
    {
      do
      {
        xRateSwerve = rand.nextInt(8);
        yRateSwerve = rand.nextInt(23);
      }
      while (xRateSwerve<3||yRateSwerve<19);
    } else if (PREVENT_OVERFITTING_B)
    {
      xRateSwerve = trainSet[trainSetCount][4];
      yRateSwerve = trainSet[trainSetCount][5];
    } else
    {
      xRateSwerve = 5;
      yRateSwerve = 20;
    }
  } else
  {
    xRateSwerve = 6;
    yRateSwerve = 20;
  }

  if (!justTested&&WANT_TO_TRAIN&&!TRAIN)  //Set up the array of neural nets //<>//
  {
    //When we want to test THIS turn...going into the Testing, we should...
    for (int i=(neuralNetCount/2)+1; i<neuralNetCount; i++)  //Ignore random variants
    {
      neuralNets[i] = new NeuralNetSimple();
      //netGameOver[i] = true;
    }
  } else
  {
    if (justTested)
      justTested = false;

    //if (PREVENT_OVERFITTING_B&&(!anotherGeneration)&&trainSetCount>0)
    //{
    //} //keep current population
    //else
    //{
      for (int i=0; i<neuralNetCount; i++)
      {
        if (anotherGeneration&&i>((neuralNetCount)/2))
          neuralNets[i] = new NeuralNetSimple(!anotherGeneration);
        else
          neuralNets[i] = new NeuralNetSimple(anotherGeneration);
      }
    //}
  }
  
  System.out.println("SETUP"); //<>//
}

void draw()  //Iterated repeatedly (apparently at 60 times per second)
{
  if (gameOver==false)  //Executed until the game is over
  {
    System.out.println(count);
    background(#B5F2E0);  //Freshly paints the background each iteration.

    Bullet();
    if (count>=1000&&gameOver==false)  //Oooooh, the USER have survived for this long? Let's add some challenge!
      changeBulletSize();  //BULLET's size will change continuously from now on!
    if (count>=beginExtraCount&&gameOver==false)  //Holy, the USER managed to survive for this long? Let's add more challenge
      addExtraBullet();  //Add an EXTRA bullet
    if (count>=beginSwerveCount&&gameOver==false)  //Oh man, the USER survived for this long? Bring on some more challenge!
      addSwervyBullet();  //Add SWERVY bullet

    if (NEURALNET_PLAYING)                      //*************************************************************************************************************************************
    {
      for (int i=0; i<neuralNetCount; i++)  //For each neural net in the array, execute it and utilize the received output
      {
        if (netGameOver[i]==true)
          continue;

        //double[] tempInput = {(neuralNetX[i]-400)/10, (x-400)/10, xRate*5, w, (xExtra-400)/10, xRateExtra*5, (xSwerve-400)/10, xRateSwerve*5, (neuralNetY[i]-300)/10, (y-300)/10, yRate*5, w, (yExtra-300)/10, yRateExtra*5, (ySwerve-300)/10, yRateSwerve*5};
        double[] tempInput = getNeuralNetSimpleInput(neuralNetX[i], neuralNetY[i]);
        if (lastManStanding())
          showInput(neuralNetX[i], neuralNetY[i], tempInput);
        if (i==0)
          System.out.println(Arrays.toString(tempInput));

        double[] tempOutput = neuralNets[i].run(tempInput);
        neuralNetX[i] += tempOutput[0];
        neuralNetY[i] += tempOutput[1];
        if (neuralNetX[i]>800&tempOutput[0]>0)
          neuralNetX[i] = 800;
        else if (neuralNetX[i]<0&&tempOutput[0]<0)
          neuralNetX[i] = 0;

        if (neuralNetY[i]>600&&tempOutput[1]>0)
          neuralNetY[i] = 600;
        else if (neuralNetY[i]<0&&tempOutput[1]<0)
          neuralNetY[i] = 0;

        pastNeuralNetX[i][count%50] = neuralNetX[i];  //Kill the ones that stay at the same spot for a long time in order to minimize the affect of luck in survival 
        pastNeuralNetY[i][count%50] = neuralNetY[i];
        boolean sameX = true;
        boolean sameY = true;
        for (int pastX : pastNeuralNetX[i])
        {
          if (pastX!=neuralNetX[i])
            sameX = false;
        }
        for (int pastY : pastNeuralNetY[i])
        {
          if (pastY!=neuralNetY[i])
            sameY = false;
        }
        stroke(0);
        fill (neuralNets[i].colorCode);  //The color of the USER's character
        ellipse(neuralNetX[i], neuralNetY[i], 50, 50);  //USER's character controlled by mouse
        if (sameX&&sameY)
          deathByStandStill(neuralNetX[i], neuralNetY[i], i);

        if (count>=gracePeriodCount)
        {
          checkForCollision(neuralNetX[i], neuralNetY[i], x, y, w, i);
          checkForDeadEdges(neuralNetX[i], neuralNetY[i], i);
          //if (count>=5*gracePeriodCount)
          //checkForDeadCorners(neuralNetX[i], neuralNetY[i], i);
        }
        if (count>=beginExtraCount)
          checkForCollision(neuralNetX[i], neuralNetY[i], xExtra, yExtra, wExtra, i);
        if (count>=beginSwerveCount)
          checkForCollision(neuralNetX[i], neuralNetY[i], xSwerve, ySwerve, wSwerve, i);

        if (allGameOver)
          break;
      }
    } else  //If the human player is playing the game
    {
      userX = mouseX;  //This variable is made so we can refer back to it once the game is over
      userY = mouseY;  //and show how the game came to an end (show that the USER came in contact with the BULLET)
      fill (255);  //The color of the USER's character
      ellipse((float)userX, (float)userY, 50.0, 50.0);  //USER's character controlled by mouse

      if (count>=gracePeriodCount)
        checkForCollision(userX, userY, x, y, w, -1);
      if (count>=beginExtraCount)
        checkForCollision(userX, userY, xExtra, yExtra, wExtra, -1);
      if (count>=beginSwerveCount)
        checkForCollision(userX, userY, xSwerve, ySwerve, wSwerve, -1);
    }
    count++;  //Keep track of how many iterations were done
  } else if (gameOver==true)  //Only executed when the game is over
  {
    background(0);  //The background turns into black
    fill(255, 0, 0);  //The circles turn into red
    ellipse((float)loseX, (float)loseY, 50, 50);  //Shows where the USER was when the game ended
    ellipse(x, y, w, w);  //Shows where the BULLET was when the game ended
    if (count>=beginExtraCount)
    {
      ellipse(xExtra, yExtra, wExtra, wExtra);
    }
    if (count>=beginSwerveCount)
    {
      ellipse(xSwerve, ySwerve, wSwerve, wSwerve);
    }

    if (gameOverLoopCount==10)  //Show printout just once
    {
      if (TRAIN||!WANT_TO_TRAIN)
      {
        if (PREVENT_OVERFITTING_B)
          System.out.println("Generation "+generationCount+" - "+trainSetCount+" is over.");
        else
          System.out.println("Generation "+generationCount+" is over.");
      } else
        System.out.println("Generation "+(generationCount-1)+" TEST is over.");
      System.out.println("**********New Generation**********");
    } else if (gameOverLoopCount>=150)
    {
      if (LOGGING_RESULTS||(!NEURALNET_PLAYING))
      {
        if (gameOverLoopCount==150)
          System.out.print("Continue? (press any key)_ \n");
        if (keyPressed==false&&gameOverLoopCount==150)
        {
          System.out.print("Waiting for keypress...\n");
        } else if (keyPressed==true)
          reset();
      } else if (AUTOMATIC_LOGGING)
      {
        if (generationsToRecord[recordCount]==(generationCount-1)&&!TRAIN&&WANT_TO_TRAIN)
        {
          records[recordCount++] = count-1;
          justTested = true;
        } else if (generationsToRecord[recordCount]==generationCount&&!WANT_TO_TRAIN)
          records[recordCount++] = count-1;


        if (recordCount==generationsToRecord.length)
        {
          System.out.println("((((((((((((((((((((Recording Complete))))))))))))))))))))");
          System.out.println(Arrays.toString(records));
          do {
          } while (true);
        }
        reset();
      } else
        reset();
    }
    gameOverLoopCount++;
  }
}
void Bullet()  //Manages the BULLET -- bounces off the walls
{
  x += xRate;  //The BULLET will be moving obviously 
  y += yRate;
  if (TRAIN||!WANT_TO_TRAIN)
    fill (red, green, blue);  //The color of the BULLET
  else
    fill (#E9FF00);
  ellipse(x, y, w, w);  //The BULLET that the USER needs to dodge

  if ((x<=(w/2)&&xRate<0)||(x>=(800-(w/2))&&xRate>0))  //Approached the wall? Bounce off it
  {
    xRate = -xRate;
    red = rand.nextInt(256);  //To make things interesting, the BULLET switches colors each count it bounces off the walls.
    green = rand.nextInt(256);
    blue = rand.nextInt(256);
  }

  if ((y<=(w/2)&&yRate<0)||(y>=(600-(w/2))&&yRate>0))  //Approached the wall? Bounce off it
  {
    yRate = -yRate;
    red = rand.nextInt(256);  //To make things interesting, the BULLET switches colors each count it bounces off the walls.
    green = rand.nextInt(256);
    blue = rand.nextInt(256);
  }
}
void changeBulletSize()  //Extra function for the BULLET -- changes its size after a certain period of time has passed
{
  if (increaseW)
    w += 1;  //size of the BULLET increases 
  else //If the BULLET gets too big,
  w -= 1;  //Start again from a small BULLET

  if (increaseW&&w>=250)
    increaseW = false;
  else if (!increaseW&&w<=150)
    increaseW = true;
}
void addExtraBullet()  //Manages the EXTRA BULLET -- shot from each of the four corners in rotation...to ensure no one survives by just staying at a corner
{
  if (xExtra<0-wExtra/2||xExtra>800+wExtra/2||yExtra<0-wExtra/2||yExtra>600+wExtra/2)  //If the EXTRA leaves the screen/battlefield,
  {
    int[] temp = new int[2];

    if (extraStartLocCount==extraStartLocations.length-1)
      extraIncrement = false;
    else if (extraIncrement==false&&extraStartLocCount==0)
      extraIncrement = true;

    if (extraIncrement)
      temp = extraStartLocations[extraStartLocCount++];
    else
      temp = extraStartLocations[extraStartLocCount--];
    xExtra = temp[0];
    yExtra = temp[1];
    if (TRAIN||!WANT_TO_TRAIN)
    {
      if (PREVENT_OVERFITTING_A)
      {
        do
        {
          xRateExtra = rand.nextInt(10);
          yRateExtra = rand.nextInt(10);
        }
        while (xRateExtra<5||yRateExtra<5);
      } else if (PREVENT_OVERFITTING_B)
      {
        xRateExtra = trainSet[trainSetCount][2];
        yRateExtra = trainSet[trainSetCount][3];
      } else
      {
        xRateExtra = 8;
        yRateExtra = 6;
      }
    } else
    {
      xRateExtra = 9;
      yRateExtra = 6;
    }

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
  if (TRAIN||!WANT_TO_TRAIN)
    fill(#F50569);  //Just a nice purple-pink-ish color that I thought was nice
  else
    fill(#E9FF00);
  ellipse(xExtra, yExtra, wExtra, wExtra);  //Yup, this is the EXTRA
}
void addSwervyBullet()  //Manages the SWERVY BULLET -- swerves like a sin-curve across the horizontal screen. Reappears in the edge that it just left on
{
  if (xSwerve<0-wSwerve||xSwerve>800+wSwerve)  //If the SWERVY leaves the screen,
  {
    if (xSwerve<0-wSwerve)
      xSwerve = 0-wSwerve/2;
    else
      xSwerve = 800+wSwerve/2;
    ySwerve = 300;
    if (TRAIN||!WANT_TO_TRAIN)
    {
      if (PREVENT_OVERFITTING_A)
      {
        do
        {
          xRateSwerve = rand.nextInt(8);
          yRateSwerve = rand.nextInt(23);
        }
        while (xRateSwerve<3||yRateSwerve<19);
      } else if (PREVENT_OVERFITTING_B)
      {
        xRateSwerve = trainSet[trainSetCount][4];
        yRateSwerve = trainSet[trainSetCount][5];
      } else
      {
        xRateSwerve = 5;
        yRateSwerve = 20;
      }
    } else
    {
      xRateSwerve = 6;
      yRateSwerve = 20;
    }

    if (xSwerve>400)  //We want some of the SWERVY bullets to come off from the right side (50/50 chance)
      xRateSwerve = -xRateSwerve;  //Yup, move towards the left side of the screen
  }
  xSwerve += xRateSwerve;  //the SWERVY is constantly moving, obviously
  ySwerve += yRateSwerve;

  if (ySwerve>=midLine)
    yRateSwerve--;
  else
    yRateSwerve++;

  if (TRAIN||!WANT_TO_TRAIN)
    fill (#7AF00F);
  else
    fill (#E9FF00);
  ellipse(xSwerve, ySwerve, wSwerve, wSwerve);
}

private double[] getNeuralNetSimpleInput(double xLoc, double yLoc)  //Returns an array of inputs to be fed to the neural nets
{
  double[] output = new double[8];
  output = distToWall(xLoc, yLoc);
  distToBullet(xLoc, yLoc, x, y, w, output);
  if (count>=beginExtraCount)
    distToBullet(xLoc, yLoc, xExtra, yExtra, wExtra, output);
  if (count>=beginSwerveCount)
    distToBullet(xLoc, yLoc, xSwerve, ySwerve, wSwerve, output);
  return output;
}
private double[] distToWall(double xLoc, double yLoc)  //Returns the distance from the centre of the ball to the wall at 8 different angles
{
  double[] output = new double[8];
  output[0] = 800-xLoc;
  output[2] = yLoc;
  output[4] = xLoc;
  output[6] = 600-yLoc;

  if (output[0]<output[2])
    output[1] = output[0]/(Math.cos(Math.PI/4));
  else
    output[1] = output[2]/(Math.sin(Math.PI/4));

  if (output[4]<output[2])
    output[3] = output[4]/(Math.cos(Math.PI/4));
  else
    output[3] = output[2]/(Math.sin(Math.PI/4));

  if (output[4]<output[6])
    output[5] = output[4]/(Math.cos(Math.PI/4));
  else
    output[5] = output[6]/(Math.sin(Math.PI/4));

  if (output[0]<output[6])
    output[7] = output[0]/(Math.cos(Math.PI/4));
  else
    output[7] = output[6]/(Math.sin(Math.PI/4));

  return output;
}
private void distToBullet(double xLoc, double yLoc, int xBullet, int yBullet, int wBullet, double[] to)  //Stores the distance from the centre of the ball to the edge of the bullet (found in any of the 8 angles) in the to[] array received as a parameter IF the ball is closer to the bullet than to the wall.
{
  double dx = xBullet - xLoc;
  double dy = yLoc - yBullet;
  double absDx = Math.abs(dx);
  double absDy = Math.abs(dy);
  double theta = Math.atan(absDy/absDx);  //0<=theta<=PI/2
  int addIndex;
  double distance;

  for (int i=1; i<=3; i++)
  {
    double dtheta;
    if (i==1||i==2)
      dtheta = Math.abs(Math.PI/(Math.pow(2, i))-theta);
    else
      dtheta = Math.abs(0-theta);
    double c = Math.sqrt(Math.pow(dx, 2)+Math.pow(dy, 2));
    double perpenDist = c*Math.sin(dtheta);
    addIndex = 3-i;
    if (perpenDist<=(wBullet/2))  //It touches
    {
      distance = Math.sqrt(Math.pow(c, 2)-Math.pow(perpenDist, 2));
      distance -= Math.sqrt(Math.pow(wBullet/2, 2)-Math.pow(perpenDist, 2));
      if (dx>=0&&dy>=0&&(distance<to[0+addIndex]))  //Quadrant 1
        to[0+addIndex] = distance;
      else if (dx<0&&dy>=0&&(distance<to[4-addIndex]))  //Quadrant 2
        to[4-addIndex] = distance;
      else if (dx<0&&dy<0&&(distance<to[4+addIndex]))  //Quadrant 3
        to[4+addIndex] = distance;
      else  //Quadrant 4
      {
        if (addIndex==0&&(distance<to[0]))
          to[0] = distance;
        else if (addIndex!=0&&distance<to[8-addIndex])
          to[8-addIndex] = distance;
      }
    }
  }
}
private boolean lastManStanding()  //Returns a boolean value that represents if only one neural net is left surviving
{
  boolean lastman = false;
  int standingCount = 0;
  for (boolean dead : netGameOver)
  {
    if (!dead)
      standingCount++;
  }
  if (standingCount==1)
    lastman=true;
  return lastman;
}
private void showInput(double xLoc, double yLoc, double[] dist)  //Visually represents the inputs given to the neural net of the last surviving neural net
{
  drawX(xLoc+dist[0], yLoc);
  drawX(xLoc, yLoc-dist[2]);
  drawX(xLoc-dist[4], yLoc);
  drawX(xLoc, yLoc+dist[6]);

  double angle = Math.PI/4;
  drawX(xLoc+dist[1]*Math.cos(angle), yLoc-dist[1]*Math.sin(angle));
  drawX(xLoc-dist[3]*Math.cos(angle), yLoc-dist[3]*Math.sin(angle));
  drawX(xLoc-dist[5]*Math.cos(angle), yLoc+dist[5]*Math.sin(angle));
  drawX(xLoc+dist[7]*Math.cos(angle), yLoc+dist[7]*Math.sin(angle));
}
private void drawX(double centreX, double centreY)  //Function that draws a red x at given location
{
  stroke(255, 0, 0);
  line((float)centreX-15, (float)centreY-15, (float)centreX+15, (float)centreY+15);
  line((float)centreX-15, (float)centreY+15, (float)centreX+15, (float)centreY-15);
}

private void checkForCollision(double xLoc, double yLoc, int xBullet, int yBullet, int wBullet, int netIndex)  //Determines death of the ball if the distance between the centres of the ball and the bullet are less than the two radii combined (aka they are touching/overlapping) and "kills" the ball
{
  double X = (double)(xBullet-xLoc);
  double Y = (double)(yBullet-yLoc);
  double a = Math.pow(X, 2);
  double b = Math.pow(Y, 2);
  double criteria = Math.sqrt(a+b);
  if (criteria<=(25+wBullet/2))
  {
    if (NEURALNET_PLAYING&&!allGameOver)
    {
      netGameOver[netIndex] = true;
      allGameOver = true;
      int survivalCount = 0;
      for (boolean over : netGameOver)
      {
        if (over==false)
        {
          allGameOver = false;
          survivalCount++;
        }
      }
      if (survivalCount<=4&&(!WANT_TO_TRAIN||TRAIN))
        top5NetIndex[survivalCount] = netIndex;

      if (allGameOver)
      {
        gameOver = true;
        loseX = xLoc;
        loseY = yLoc;
        System.out.println("\nBEST MODEL: ");
        System.out.println(neuralNets[netIndex].getInfo());
      } else
      {
        fill(255, 0, 0);  //The circle turns into red
        ellipse((float)xLoc, (float)yLoc, 50, 50);  //Shows where the USER was when the game ended
      }
    } else if (!NEURALNET_PLAYING)
    {
      gameOver = true;
      loseX = xLoc;
      loseY = yLoc;
    }
  }
}
private void checkForDeadEdges(double xLoc, double yLoc, int netIndex)  //"kills" any balls whose centre touches any of the edges
{
  if (xLoc==0||xLoc==800||yLoc==0||yLoc==600)
  {
    netGameOver[netIndex] = true;
    allGameOver = true;
    int survivalCount = 0;
    for (boolean over : netGameOver)
    {
      if (over==false)
      {
        allGameOver = false;
        survivalCount++;
      }
    }
    if (survivalCount<=4&&(!WANT_TO_TRAIN||TRAIN))
      top5NetIndex[survivalCount] = netIndex;

    if (allGameOver)
    {
      gameOver = true;
      loseX = xLoc;
      loseY = yLoc;
      System.out.println("BEST MODEL: ");
      System.out.println(neuralNets[netIndex].getInfo());
    } else
    {
      fill(255, 0, 0);  //The circle turns into red
      ellipse((float)xLoc, (float)yLoc, 50, 50);  //Shows where the USER was when the game ended
    }
  }
}
private void checkForDeadCorners(double xLoc, double yLoc, int netIndex)  //"kills" any balls whose centre is located in any of the corners
{
  if ((xLoc==0&&(yLoc==0||yLoc==600))||(xLoc==800&&(yLoc==0||yLoc==600)))
  {
    netGameOver[netIndex] = true;
    boolean allGameOver = true;
    int survivalCount = 0;
    for (boolean over : netGameOver)
    {
      if (over==false)
      {
        allGameOver = false;
        survivalCount++;
      }
    }
    if (survivalCount<=4&&(!WANT_TO_TRAIN||TRAIN))
      top5NetIndex[survivalCount] = netIndex;


    if (allGameOver)
    {
      gameOver = true;
      loseX = xLoc;
      loseY = yLoc;
      System.out.println("BEST MODEL: ");
      System.out.println(neuralNets[netIndex].getInfo());
    } else
    {
      fill(255, 0, 0);  //The circle turns into red
      ellipse((float)xLoc, (float)yLoc, 50, 50);  //Shows where the USER was when the game ended
    }
  }
}
private void deathByStandStill(int xLoc, int yLoc, int netIndex)  //Called to "kill" the ball when it has stayed at the same location for too long (as determined in the draw() method
{
  netGameOver[netIndex] = true;
  boolean allGameOver = true;
  int survivalCount = 0;
  for (boolean over : netGameOver)
  {
    if (over==false)
    {
      allGameOver = false;
      survivalCount++;
    }
  }
  if (survivalCount<=4&&(!WANT_TO_TRAIN||TRAIN))
    top5NetIndex[survivalCount] = netIndex;


  if (allGameOver)
  {
    gameOver = true;
    loseX = xLoc;
    loseY = yLoc;
    System.out.println("Death By Stand Still :(");
    System.out.println(neuralNets[netIndex].getInfo());
  } else
  {
    fill(255, 0, 0);  //The circle turns into red
    ellipse((float)xLoc, (float)yLoc, 50, 50);  //Shows where the USER was when the game ended
  }
}

private void reset()  //Executed when one game is over. Resets variables and conditions for the next game
{
  if (TRAIN&&WANT_TO_TRAIN||(!WANT_TO_TRAIN))
    NeuralNetSimple.storeLastBestModel();

  if (WANT_TO_TRAIN)
    anotherGeneration = true;

  if (PREVENT_OVERFITTING_B)
    trainSetCount++;

  if (TRAIN||!WANT_TO_TRAIN)
  {
    if (PREVENT_OVERFITTING_B)
    {
      if (trainSetCount==trainSet.length)
      {
        trainSetCount = 0;
        generationCount++;
        //anotherGeneration = true;
      } //else
        //anotherGeneration = false;
    } else
      generationCount++;
  }

  setup();
}
void keyPressed()  //Used to wait for a key press when LOGGING_RESULTS is set to true
{
}