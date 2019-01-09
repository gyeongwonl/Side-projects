public class Tester
{
    public static void main(String args[])
    {
        double missile[] = new double[6];
        double laser[] = new double[7];
        double eq[] = new double[4];
        for (int i=0; i<misList.size(); i++)
        {
            missile = misList.get(i);
            missile[0] -= 1;
            missile[2] -= 1;
            missile[4] -= 1;
            for (int j=0; j<defList.size();j++)
            {
                laser = defList.get(j);
                
                eq = calcEquation(missile,laser);
                if (missile[1]<350)
                {

                }
                else if (missile[1]>350)
                {

                }
                else
                {

                }
            }
        }
    }
    
    laserM = (laserRise)/(laserRun);
    laserB = laserY1 - laserM*laserX1
    for (int x=laserX1; x<laserX2; x++)
    {
        double y = laserM*x+laserB;
        boolean hit = inTheMissile(y);
        if (hit==true)
        {
            explode
        }
        else
        {
            continue;
        }
    }
    
    boolean inTheMissile(double point)
    {
        boolean in = false;
        double yMax = missileY2;
        double yMin = missileY1;
        for (int x=missileX1; x<missileX3; x++)
        {
            if (yMin<=point&&yMax>=point)
            {
                in = true;
                break;
            }
            else
            {
                yMax -= (50/43.3);
                yMin += (50/43.3);
            }
        }
        return in;
    }
    
    
}
