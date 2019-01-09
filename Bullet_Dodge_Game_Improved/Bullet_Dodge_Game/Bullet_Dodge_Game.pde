import java.util.Random;
static Random rand = new Random();
static int count=0;
void setup()
{
  size(1000, 1000);
}
void draw()
{ 
  background(128);
  ellipse(mouseX, mouseY, 20, 20);
  count++;

  int num = rand.nextInt(10);
  int cir[][] = new int[10][4];
  if (count%600==0||count==1)
  {

    for (int i=0; i<num; i++)
    {
      int x = newCircleX();
      int y = newCircleY(x);
      int xRate = rand.nextInt(100);
      int yRate = rand.nextInt(100);
      int neg = rand.nextInt(3); //<>//
      if (neg==0)
      {
      } else if (neg==1)
      {
        xRate = -xRate;
      } else if (neg==2)
      {
        yRate = -yRate;
      } else
      {
        xRate = -xRate;
        yRate = -yRate;
      }
      cir[i][0] =x;
      cir[i][1]=y;
      cir[i][2] = xRate;
      cir[i][3] = yRate;
    }
  }

  for (int i=0; i<10; i++)
  {
    drawEll(cir[i][0], cir[i][1]);
    //lipse(cir[i][0], cir[i][1], 50, 50);
    cir[i][0] += cir[i][2];
    cir[i][1] += cir[i][3];
  }
}
int newCircleX()
{
  int x = rand.nextInt(1000);
  return x;
}
int newCircleY(int x)
{
  int y;
  if (x<=50||x>=950)
  {
    y = rand.nextInt(1000);
  } else
  {
    y = rand.nextInt(1);
    if (y==1)
    {
      y = 1000;
    }
  }
  return y;
}
void drawEll(int x, int y)
{
 ellipse(x,y,50,50);
}