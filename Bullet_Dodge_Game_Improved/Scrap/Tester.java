public class Tester
{
    public static void main(String args[])
    {
        int x=2, y=5, userX=9, userY=10;
        double X = (double)(x-userX);
        double Y = (double)(y-userY);
        double a = Math.pow(X,2);
        double b = Math.pow(Y,2);
        double crit = Math.sqrt(a+b);
    }
    public void swerveExtra()
    {
        //use sin graph?
        //y = sin(x)
        int xRateExtra=6; //stays the same
        double q = Math.pow(xRateExtra,2);
        double w = Math.pow(yRateExtra,2);
        double e = Math.sqrt(q+w);
        double changeOverTiltedXPlain = Math.toRadians(e);
        double yRateExtra = Math.sin(changeOverTiltedXPlain);
    }
}
