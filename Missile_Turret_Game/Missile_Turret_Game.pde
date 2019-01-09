//Author: Gyeongwon Lee
//Date created: August 4th/ 2017
//This program allows the user to play a simple Missile_Turret_Game (aka "Protect your planet from approaching warships!"). The user shoots lasers at approaching missiles
//(triangles) by clicking on the mouse. The aim can be adjusted with the movement of the mouse.
import java.util.Random;  //import to generate random numbers
import java.util.Arrays;  //import to utilize ArrayLists
static int count=0;  //Counts how many times the draw() method was iterated... important in the computer deciding when to shoot new missiles
static ArrayList<double[]> misList = new ArrayList<double[]>();  //ArrayList of the missiles shot by the computer (contains arrays that contain numerous info)
static ArrayList<double[]> defList = new ArrayList<double[]>();  //ArrayList of the defenses (lasers) shot by the user  (contains arrays that contain numerous info)
static boolean gameOver = false;  //Is the game over yet?
static double defLength = 250;  //Length of the defenses (lasers)
static double defSpeed = 20;  //Speed of the defenses (lasers)
void setup()  //Basic setup; only iterated once in the beginning
{
  size(1200,700);  //Sets the canvas size.
}
void draw()  //Iterated repetitively throughout the running of the program
{
  if (gameOver==false)  //If the game is not over yet...do this each iteration!
  {
      background(#8D81BF);  //Freshly paint the background (to get rid of the remnants from the last iteration)
    System.out.println(count);  //Just for the record...
    stroke(0);
    ellipse(1200,350,50,50);  //This is your "base" or the "cannon". The user's lasers are gonna be shot from here
    ellipse(mouseX,mouseY,50,50);  //This is your aim (circle part)...movable by your mouse.
    line(mouseX-50,mouseY,mouseX+50,mouseY);  //This is your aim (horizontal line part)
    line(mouseX,mouseY-50,mouseX,mouseY+50);  //This is your aim (Vertical line part)
    
    for (int i=0; i<defList.size(); i++)  //This is only iterated if the user has shot any lasers...If some have been shot already, execute code below for each shot of a laser.
    {                                     //Probably makes more sense to read this block of code AFTER taking a look at void defense() at the bottom
     boolean removed = false;
      double tempo[] = new double[7];  //Temporary array to store the array from the...(see the line below)
      tempo = defList.get(i);  //ArrayList of the lasers shot (list of arrays)
      float defx1 = (float)tempo[0];  //First element is the x1 point of the laser
      float defy1 = (float)tempo[1];  //Second element is the y1 point of the laser
      float defx2 = (float)tempo[2];  //Third element is the x2 point of the laser
      float defy2 = (float)tempo[3];  //Fourth element is the y2 point of the laser
      double spdMultiFactor = tempo[4];  //Fifth element is the multiplication factor (for deciding how much change in x and in y are needed to make the laser move at a specific speed
      //compared to our given run and rise...think of like triangles)
      double defRun = tempo[5];  //Sixth element is the original "run" (defined in void defense())
      double defRise = tempo[6];  //Seventh element is the original "rise" (defined in void defense())
      stroke (255,0,0);  //Sets the color of the laser
      line(defx1,defy1,defx2,defy2);  //Draw a line connecting points (x1,y1) and (x2,y2)
      
      if(defx2<=0)
      {
       defList.remove(i);
       removed = true;
      }
      
      if(removed==false)
      {
        defx1 -= defRun*spdMultiFactor;  //We want the laser to be moving at a speed that we set right?
      defy1 -= defRise*spdMultiFactor;  //Subtraction because I tried the operation for every direction possible (of the laser) and that's the one that worked (refer to notes)
      defx2 -= defRun*spdMultiFactor;
      defy2 -= defRise*spdMultiFactor;
      
      tempo[0] = defx1;  //Store the changed values in the temporary array
      tempo[1] = defy1;
      tempo[2] = defx2;
      tempo[3] = defy2;
      defList.set(i,tempo);  //And plug that changed array into the original spot
      }
    }
         // checkForCollision();
    
    if (count!=0&&count%200==0)  //Each 200th iteration, shoot a wave of missiles
    {
      int misNum;  //How many though?
       Random rand = new Random();  //A random amount
       misNum = rand.nextInt(6);  //Varying from 0~5
       missile(misNum);  //Go to void missile() //<>//
    }
    
    if (misList.size()!=0)  //If any missile has been shot...I don't think this if statement is necessary 
    {
      for (int i = 0; i<indx; i++)  //indx=misList.size...repeat this for each missile
      {
        double temp[] = new double[6];  //A temporary array to store the array from the list
        temp = misList.get(i);  //Retrieve the array from the list and put it in temp array
        float x1 = (float)temp[0]; 
        float y1 = (float)temp[1];
        float x2 = (float)temp[2];
        float y2 = (float)temp[3];
        float x3 = (float)temp[4];
        float y3 = (float)temp[5];
        stroke(0);
        triangle(x1,y1,x2,y2,x3,y3);  //Draw a triangle (the missile)
        if (x1 >= 1200-43.3)  //If the triangle has reached the end on the right
        {
          gameOver = true;
          break;
        }
        else  //If not, let's make the triangle advance towards the mothership (semi-circle on the right)
        {
          x1 += 1;
          x2 += 1;
          x3 += 1;
          temp[0] = x1;  //Plug changed values into the array
          temp[2] = x2;
          temp[4] = x3;
          misList.set(i,temp);  //Plug that changed array into the original spot
        }
      }
    }  
    count++;
  }
  else  //If boolean gameOver is true, the game is over;
  {
   gameOver();   //Go to void gameOver()
  }
}

static int indx = 0;
void missile(int num)  //Generates and stores info regarding the missiles to be shot in an array, which is then stored in an ArrayList misList
{
  Random rnd = new Random();
  double misAlt;  //Missiles Altitude...we know that the missiles are coming from the left, but from which height?
  for (int i=0; i<num; i++)  //New and different info for each missile we want
  {
     misAlt = rnd.nextInt(651);
    double misInfo[] = new double[6];
    misInfo[0] = 0;  //x1 of the missile
    misInfo[1] = misAlt;  //y1 of the missile
    misInfo[2] = 0;  //x2 of the missile
    misInfo[3] = misAlt+50;  //y2 of the missile
    misInfo[4] = 43.3;  //x3 of the missile
    misInfo[5] = misAlt+25;  //y3 of the missile
   //Missiles are triangles pointing right, so we need three plots
    misList.add(misInfo);   //All these info regarding a missile are stored in an array, which is then added to the ArrayList
    indx = misList.size();
  }
}
void gameOver()
{
  background(255,0,0);  //Color the background red
 System.out.println("Game Over");
 ellipse(1200,350,50,50);
   for (int i = 0; i<indx; i++)  //Show where all the missiles were when the game ended
    {
      double temp[] = new double[6];
      temp = misList.get(i);
      float x1 = (float)temp[0];
      float y1 = (float)temp[1];
      float x2 = (float)temp[2];
      float y2 = (float)temp[3];
      float x3 = (float)temp[4];
      float y3 = (float)temp[5];
      triangle(x1,y1,x2,y2,x3,y3);
    }
}

void mouseClicked()  //If mouse is clicked,
{
 defence();  //Go to void defence()
}

void defence()  //Generates and stores info about the defences (lasers) that the user shoots
{
  double defInfo[] = new double[7];  //An array that contains all the data will be stored in an ArrayList defList
  defInfo[0] = 1200;  //x1 of the laser
  defInfo[1] = 350;  //y1 of the laser...you shoot your laser from your mothership (semi-circle on the right side of the screen)
  double rise = 350 - mouseY;  //Figure out rise and run based on where you plan to shoot the laser towards (the point at which you clicked on the mouse)
  double run = 1200 - mouseX;
  defInfo[5] = run;
  defInfo[6] = rise;
  double hyp = Math.sqrt(Math.pow(rise,2)+Math.pow(run,2));  //Pythagorean theorem...figure out hypotenuse
  double multiFactor = defLength/hyp;  //Two like triangles... what's the multiplication factor if we want the hypotenuse to be a certain length (defLength)? 
  defInfo[2] = 1200+ run * multiFactor;  //x2 of the laser
  defInfo[3] = 350+ rise * multiFactor;  //y2 of the laser
  double speedMultiFactor = defSpeed/hyp;  //What's the multiplication factor if we want the laser to move at a certain speed (change in hypotenuse of defSpeed)
  defInfo[4] = speedMultiFactor;
  defList.add(defInfo);  //Store the array in the ArrayList
}

/*void checkForCollision()
{
  double missile[] = new double[6];
  double laser[] = new double[7];
  for (int i=0; i<misList.size(); i++)
  {
   missile = misList.get(i);
   missile[0] -= 1;
   missile[2] -= 1;
   missile[4] -= 1;
   for (int j=0; j<defList.size();j++)
   {
    laser = defList.get(j);
    double laserM = laser[6]/laser[5];
    double laserB = laser[1] - (laserM*laser[0]);
    for (double x=laser[0]; x<laser[2]; x++)
    {
     double y = laserM*x + laserB;
     boolean hit = inTheMissile(x,y,missile);
     if (hit==true)
     {
       misList.remove(i);
       defList.remove(j);
       ellipse((float)x,(float)y,100,100);
     }
     else
     {
      continue; 
     }
    }
   }
  }
}
boolean inTheMissile(double pointX, double pointY, double mis[])
{
  boolean in = false;
  double yMax = mis[3];
  double yMin = mis[1];
  double diff = pointX-mis[0];
  yMax -= diff*(50/43.3);
  yMin += diff*(50/43.3);
    if (yMin<=pointY && yMax>=pointY)
    {
     in = true;
    }
  return in;
}*/