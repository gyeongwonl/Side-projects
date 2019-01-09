/* //<>//
//Inputs: userX, x, xRate, w, xExtra, xRateExtra, xSwerve, xRateSwerve, userY, y, yRate, w, yExtra, yRateExtra, ySwerve, yRateSwerve (Total of 16 -- 7x & 1 size,7 y & 1 size) //<>//
//Outputs: moveX, moveY
import java.util.*;
import java.io.*;
public static class NeuralNet
{
  private double[] layer1Weight = new double[16];
  private double[] layer1Bias = new double[16];
  private double[] layer1Output = new double[16];

  private double[] layerIWeight = new double[16];
  private double[] layerIBias = new double[8];
  private double[] layerIOutput = new double[8];

  private double[] layer2NodeAWeight = new double[8];
  private double[] layer2NodeABias = new double[1];
  private double[] layer2NodeAOutput = new double[1]; //moveX

  private double[] layer2NodeBWeight = new double[8];
  private double[] layer2NodeBBias = new double[1];
  private double[] layer2NodeBOutput = new double[1]; //moveY

  private double[] output = new double[2];
  private static double[][] lastBestModel = new double[8][16];
  private static final double movementFactor = 0.001;
  private static final int randomRange = 5;
  private static int countFactor = 4;

  public NeuralNet(boolean anotherGeneration)
  {  
    if (anotherGeneration)
    {
      for (int i=0; i<16; i++)
      {
        layer1Weight[i] = getRandom(anotherGeneration)+lastBestModel[0][i];
        layer1Bias[i] = getRandom(anotherGeneration)+lastBestModel[1][i];
        layerIWeight[i] = getRandom(anotherGeneration)+lastBestModel[6][i];
        layerIBias[i] = getRandom(anotherGeneration)+lastBestModel[7][i];
        if (i==0)
        {
          layer2NodeABias[0] = getRandom(anotherGeneration)+lastBestModel[3][i];
          layer2NodeBBias[0] = getRandom(anotherGeneration)+lastBestModel[5][i];
        }

        if (i<8)
        {
          layer2NodeAWeight[i] = getRandom(anotherGeneration)+lastBestModel[2][i];
          layer2NodeBWeight[i] = getRandom(anotherGeneration)+lastBestModel[4][i];
        }
      }
    } else
    {
      for (int i=0; i<16; i++)
      {
        layer1Weight[i] = getRandom(anotherGeneration); 
        layer1Bias[i] = getRandom(anotherGeneration);
        layerIWeight[i] = getRandom(anotherGeneration);
        if (i==0)
        {
          layer2NodeABias[0] = getRandom(anotherGeneration);
          layer2NodeBBias[0] = getRandom(anotherGeneration);
        }

        if (i<8)
        {
          layer2NodeAWeight[i] = getRandom(anotherGeneration);
          layer2NodeBWeight[i] = getRandom(anotherGeneration);
          layerIBias[i] = getRandom(anotherGeneration);
        }
      }
    }
  }

  private double getRandom(boolean anotherGeneration)
  {
    double out=0;
    Random rand = new Random();
    if (anotherGeneration)
      out = rand.nextDouble();
    else
    {
      out = randomRange*rand.nextDouble();
    }

    if (rand.nextInt()<0)
      out = -1*out;            
    return out;
  }

  public double[] run (double[] input)
  {
    if (Bullet_Dodge_Game_AI.count>=1500)
      countFactor = 8;
    else if (Bullet_Dodge_Game_AI.count>=1000)
      countFactor = 6;

    //sigmoid(input);
    executeLayer(false, input, layer1Weight, layer1Bias, layer1Output);
    executeILayer(layer1Output, layerIWeight, layerIBias, layerIOutput);

    //sigmoid(layerIOutput);
    double[] layer2NodeAInput = new double[countFactor]; 
    System.arraycopy(layerIOutput, 0, layer2NodeAInput, 0, countFactor/2);
    double[] tempAW = new double[countFactor];
    System.arraycopy(layer2NodeAWeight, 0, tempAW, 0, countFactor/2);
    double[] layer2NodeBInput = new double[countFactor];
    System.arraycopy(layerIOutput, 4, layer2NodeBInput, 0, countFactor/2);
    double[] tempBW = new double[countFactor];
    System.arraycopy(layer2NodeBWeight, 0, tempBW, 0, countFactor/2);

    executeLayer(true, layer2NodeAInput, tempAW, layer2NodeABias, layer2NodeAOutput);
    executeLayer(true, layer2NodeBInput, tempBW, layer2NodeBBias, layer2NodeBOutput);

    output[0] = movementFactor*layer2NodeAOutput[0];
    output[1] = movementFactor*layer2NodeBOutput[0];
    return output; //<>// //<>//
  }

  private void executeLayer(boolean cumulative, double[] input, double[] weight, double[] bias, double[] to) //save result to int[] to
  {
    if (cumulative)
    {
      double sum=0;
      for (int i=0; i<input.length; i++)
      {
        sum += input[i]*weight[i];
      }
      sum += bias[0];
      to[0] = sum;
    } else
    {
      for (int i=0; i<input.length; i++)
      {
        to[i] = input[i] * weight[i]+bias[i];
      }
    }
    //sigmoid(to);
  }
  private void executeILayer(double[] input, double[] weight, double[] bias, double[] to)
  {
    double sum = 0;
    for (int i=0; i<input.length; i++)
    {
      if (i!=0&&i%2==0)
      {
        sum += bias[i/2-1];
        to[i/2-1] = sum;
        sum = 0;
      }
      sum += input[i]*weight[i];
    }
  }
  private void sigmoid(double[] to)
  {
    for (int i=0; i<to.length; i++)
    {
      to[i] = 1/(1+Math.pow(Math.E, -1*to[i]));
    }
  }

  public String getInfo(boolean success)
  {
    String out = "";
    /*out += "Layer 1 Weight: "+layer1Weight;
     out += "\nLayer 1 Bias: "+layer1Bias;
     out += "\nLayer 1 Output: "+layer1Output;
     out += "\nLayer 2A Weight: "+layer2NodeAWeight;
     out += "\nLayer 2A Bias: "+layer2NodeABias;
     out += "\nLayer 2A Output (x): "+layer2NodeAOutput;
     out += "\nLayer 2B Weight: "+layer2NodeBWeight;
     out += "\nLayer 2B Bias: "+layer2NodeBBias;
     out += "\nLayer 2B Output (y): "+layer2NodeBOutput;
    String[][] msg = new String[12][16];
    for (int i=0; i<16; i++)
    {
      msg[0][i] = ""+layer1Weight[i];
      msg[1][i] = ""+layer1Bias[i];
      msg[2][i] = ""+layer1Output[i];
      msg[9][i] = ""+layerIWeight[i];
      if (i<8)
      {
        msg[3][i] = ""+layer2NodeAWeight[i];
        msg[6][i] = ""+layer2NodeBWeight[i];
        msg[10][i] = ""+layerIBias[i];
        msg[11][i] = ""+layerIOutput[i];
      }
    }
    msg[4][0] = ""+layer2NodeABias[0];
    msg[5][0] = ""+layer2NodeAOutput[0];
    msg[7][0] = ""+layer2NodeBBias[0];
    msg[8][0] = ""+layer2NodeBOutput[0];

    for (String[] row : msg)
    {
      for (String s : row)
      {
        if (s==null)
          continue;
        out += s + "\t";
      }
      out += "\n";
    }

    if (success)
      storeLastBestModel();
    return out;
  }
  private void storeLastBestModel()
  {
    lastBestModel[0] = this.layer1Weight;
    lastBestModel[1] = this.layer1Bias;
    lastBestModel[2] = this.layer2NodeAWeight;
    lastBestModel[3] = this.layer2NodeABias;
    lastBestModel[4] = this.layer2NodeBWeight;
    lastBestModel[5] = this.layer2NodeBBias;
    lastBestModel[6] = this.layerIWeight;
    lastBestModel[7] = this.layerIBias;
  }
}
*/