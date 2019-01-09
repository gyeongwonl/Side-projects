//Author: Gyeongwon Lee
//Date Created: July 15th/2017
//This program lets the user play a generic Bullet Dodge Game with the user's mouse. The exciting part is that the game gets more challenging as time passes.
import java.util.Random;  //For random number
static Random rand = new Random();  //My random number generator
static int x=0, y=0;  //These variables represent the x and y position of the BULLET
static int xRate, yRate;  //Represent the speed of the BULLET
static int count=0;  //Counts how many iterations have been done (proportional to how much time has elapsed in the game)
static boolean gameOver = false; 
static int userX, userY;  //Stores the x and y coordinates of the USER's character when the game ended
static int loseX, loseY;  //Stores the x and y coordinates of the BULLET when the game ended
static int red=255, green=0, blue=0;  //For changing the color of the BULLET when it bounces
static int w=140;  //For changing BULLET size
static int xExtra = 0, yExtra = 0;  //Initial location of EXTRA bullet
static int xRateExtra, yRateExtra;   //Speed of the EXTRA

void setup()  //Only iterated once in the beginning
{
  size(800, 600);  //The size of our playground/battlefield
  do
  {
    xRate = rand.nextInt(8);  //Random Speeds for the BULLET
    yRate = rand.nextInt(8);
  }
  while (xRate<=3||yRate<=3);  //But slow speed would be quite boring, eh?

  do
  {
    xRateExtra = rand.nextInt(8);  //Random Speeds for EXTRA bullet
    yRateExtra = rand.nextInt(8);
  }
  while (xRateExtra<=2||yRateExtra<=2);  //But we don't want slow speeds...
  
  do
  {
   xRateSwerve = rand.nextInt(6);
  }while(xRateSwerve<=3);
}

void draw()  //Iterated repeatedly (apparently at 60 times per second)
{
  if (gameOver==false)  //Executed until the game is over
  {
    System.out.println(count);

    background(#74FFFE);  //Freshly paints the background each iteration.
    userX = mouseX;  //This variable is made so we can refer back to it once the game is over
    userY = mouseY;  //and show how the game came to an end (show that the USER came in contact with the BULLET)
    fill (255);  //The color of the USER's character
    ellipse(userX, userY, 50, 50);  //USER's character controlled by mouse
    x += xRate;  //The BULLET will be moving obviously 
    y += yRate;
    fill (red, green, blue);  //The color of the BULLET
    ellipse(x, y, w, w);  //The BULLET that the USER needs to dodge

    if (count>=60)  //Let's give some grace period before we get strict with the rules
    {
      double X = (double)(x-userX);  //negatives don't matter cuz it's gonna be squared anyways
      double Y = (double)(y-userY);
      double a = Math.pow(X, 2); 
      double b = Math.pow(Y, 2);
      double criteria = Math.sqrt(a+b);  //Find the length of hypotenuse with Pythagorean theorem...Hypotenuse represents the distance between the two centres of the two circles
      if (criteria<=(25+(w/2)))   //If the distance between the centres of the two circles are <= than their radii combined, the USER has collided into the BULLET.
      {
        gameOver=true;  //alright, the game is over
        loseX = userX;  //where was the USER when the game ended?
        loseY = userY;
      }
    }

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

    if (count>=500&&gameOver==false)  //Oooooh, the USER have survived for this long? Let's add some challenge!
    {
      changeBulletSize();  //BULLET's size will change continuously from now on!
    }

    if (count>=1000&&gameOver==false)  //Holy, the USER managed to survive for this long? Let's add more challenge
    {
      addExtraBullet();  //Add an EXTRA bullet
    }
    
    if (count>=1500&&gameOver==false)  //Oh man, the USER survived for this long? Bring on some more challenge!
    {
     addSwervyBullet();  //Add SWERVY bullet
    }
    count++;  //Keep track of how many iterations were done
  } else if (gameOver==true)  //Only executed when the game is over
  {
    background(0);  //The background turns into black
    fill(255, 0, 0);  //The circles turn into red
    ellipse(loseX, loseY, 50, 50);  //Shows where the USER was when the game ended
    ellipse(x, y, w, w);  //Shows where the BULLET was when the game ended
    if (count>=1000)
    {
      ellipse(xExtra, yExtra, 120, 120);
    }
    if (count>=1500)
    {
     ellipse(xSwerve,ySwerve,60,60); 
    }
  }
}

void changeBulletSize()
{
  w += 2;  //size of the BULLET increases 
  if (w>=300)  //If the BULLET gets too big,
  {
    w = 60;  //Start again from a small BULLET
  }
}

void addExtraBullet()
{
  fill(#F50569);  //Just a nice purple-pink-ish color that I thought was nice
  if (xExtra<-60||xExtra>860||yExtra<-60||yExtra>660)  //If the EXTRA leaves the screen/battlefield,
  {
    do 
    {
      xExtra = rand.nextInt(800);  //The EXTRA will pop up at a new location
      yExtra = rand.nextInt(600);
    }
    while ((xExtra>60&&xExtra<740)&&(yExtra>60&&yExtra<540));  //But we want the EXTRA bullet to approach from the edge of the screen...we don't want it popping up from anywhere in the screen.

    do
    {
      xRateExtra = rand.nextInt(8);  //With a different speed
      yRateExtra = rand.nextInt(8);  //Always positive... (so, we use the if statements below)
    }
    while (xRateExtra<=2||yRateExtra<=2);  //But we don't want slow speed

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
  ellipse(xExtra, yExtra, 120, 120);  //Yup, this is the EXTRA

  //We gotta check if the USER collides with the EXTRA bullets as well
  double XExtra = (double)(xExtra-userX);  //negatives don't matter cuz it's gonna be squared anyways
  double YExtra = (double)(yExtra-userY);
  double aExtra = Math.pow(XExtra, 2); 
  double bExtra = Math.pow(YExtra, 2);
  double criteriaExtra = Math.sqrt(aExtra+bExtra);  //Find the length of hypotenuse with Pythagorean theorem...Hypotenuse represents the distance between the two centres of the two circles
  if (criteriaExtra<=(25+(120/2)))   //If the distance between the centres of the two circles are <= than their radii combined, the USER has collided into the EXTRA bullet.
  {
    gameOver=true;  //alright, the game is over
    loseX = userX;  //where was the USER when the game ended?
    loseY = userY;
  }
}

static int xSwerve = rand.nextInt(2), ySwerve = rand.nextInt(600), xRateSwerve, yRateSwerve = 15;
static int midLine = ySwerve;
void addSwervyBullet()  //Adds another obstacle after count==1500...SWERVY bullet
{
  if(xSwerve<0||xSwerve>800)  //If the SWERVY leaves the screen,
  {
   xSwerve = rand.nextInt(2);  //Reset its position
   ySwerve = rand.nextInt(600);
   do
   {
    xRateSwerve = rand.nextInt(6);  //Also reset its speed
    yRateSwerve= 15;//rand.nextInt(15);
   }while(xRateSwerve<=3||yRateSwerve<=7);  //But not too slow...
  }
  
  if (xSwerve==1)  //We want some of the SWERVY bullets to come off from the right side (50/50 chance)
  {
   xSwerve = 800;  //The right side of the screen
   xRateSwerve = -xRateSwerve;  //Yup, move towards the left side of the screen
  }
  xSwerve += xRateSwerve;  //the SWERVY is constantly moving, obviously
  ySwerve += yRateSwerve;
  
  if ((midLine==ySwerve))  //If the SWERVY reaches the mid.-line (y position when it began)
  {
    yRateSwerve = -yRateSwerve;  //???  ohhhhhhh i see. So rather than drawing a nice swervy pattern (like a sin graph), it literally just bounces along the mid-line haha.
  }
  else
  {
   if (ySwerve>midLine)
   {
    yRateSwerve -= 1; 
   }
   /*else if (ySwerve<midLine)  //So this never gets executed
   {
    yRateSwerve += 1; 
   }*/
  }
  fill (#7AF00F);
  ellipse(xSwerve,ySwerve,60,60);
  
  double XSwerve = (double)(xSwerve-userX);  //negatives don't matter cuz it's gonna be squared anyways
  double YSwerve = (double)(ySwerve-userY);
  double aSwerve = Math.pow(XSwerve, 2); 
  double bSwerve = Math.pow(YSwerve, 2);
  double criteriaSwerve = Math.sqrt(aSwerve+bSwerve);  //Find the length of hypotenuse with Pythagorean theorem...Hypotenuse represents the distance between the two centres of the two circles
  if (criteriaSwerve<=(25+30))  //If the distance between the centres of the two circles are <= than their radii combined, the USER has collided into the SWERVY bullet.
  {
    gameOver=true;  //alright, the game is over
    loseX = userX;  //where was the USER when the game ended?
    loseY = userY;
  }
}