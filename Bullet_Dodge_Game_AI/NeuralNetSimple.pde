//Inputs: proximity to obstacle from 8 different directions //<>// //<>// //<>//
//Outputs: how much to move in the horizontal direction (moveX), how much to move in the vertical direction (moveY)
import java.util.*;
import java.io.*;
public static class NeuralNetSimple
{
  private double[] layer1Weight = new double[8];  //Layer 1 contains 8 nodes
  private double[] layer1Bias = new double[8];
  private double[] layer1Output = new double[8];

  private double[] layer2Weight = new double[8*4];  //Layer 2 contains 4 nodes
  private double[] layer2Bias = new double[4];
  private double[] layer2Output = new double[4];

  private double[] layer3Weight = new double[4*2];  //Layer 3 contains 2 nodes
  private double[] layer3Bias = new double[2];
  private double[] layer3Output = new double[2];

  public final color colorCode;

  private static Random random = new Random();
  private static double[][][] lastBestModel = new double[5][6][32];
  private static int tempCount = 0;
  private static final double movementFactor = 0.0001;
  private static final int randomRange = 5;

  public NeuralNetSimple()  //void version -- used to ignore the randomly initialized balls when TESTING
  {
    Arrays.fill(layer1Weight, 0);
    Arrays.fill(layer1Bias, 0);
    Arrays.fill(layer2Weight, 0);
    Arrays.fill(layer2Bias, 0);
    Arrays.fill(layer3Weight, 0);
    Arrays.fill(layer3Bias, 0);
    colorCode = #FF7474;
  }
  public NeuralNetSimple(boolean anotherGeneration)  //If anotherGeneration is true, use the top players from the previous population. Otherwise, initialize randomly
  {  
    tempCount++;

    if (anotherGeneration)
    {
      int refNumBestModels = 5;
      //if (PREVENT_OVERFITTING_B)
        //refNumBestModels = 20;
      int refA=0;
      int refB=0;
      int refC=0;

      if (tempCount<=refNumBestModels)
      {
        refA = tempCount-1;
        refB = tempCount-1;
        refC = tempCount-1;
        colorCode = #FFFFFF;  //White
      } else
      {
        do
        {
          refA = random.nextInt(refNumBestModels);
          refB = random.nextInt(refNumBestModels);
          refC = random.nextInt(refNumBestModels);
        }
        while (refA==refB&&refB==refC);
        colorCode = #909090;  //Gray
      }

      System.arraycopy(lastBestModel[refA][0], 0, layer1Weight, 0, 8);
      System.arraycopy(lastBestModel[refB][1], 0, layer1Bias, 0, 8);
      System.arraycopy(lastBestModel[refC][2], 0, layer2Weight, 0, 32);
      System.arraycopy(lastBestModel[refA][3], 0, layer2Bias, 0, 4);
      System.arraycopy(lastBestModel[refB][4], 0, layer3Weight, 0, 8);
      System.arraycopy(lastBestModel[refC][5], 0, layer3Bias, 0, 2);

      if (30<tempCount)
      {
        for (int i=0; i<=random.nextInt(8); i++)
        {
          layer1Weight[i] *= getRandom(true); 
          layer1Bias[i] *= getRandom(true);
          layer3Weight[i] *= getRandom(true);
        }
        for (int i=0; i<=random.nextInt(32); i++)
          layer2Weight[i] *= getRandom(true);
        for (int i=0; i<=random.nextInt(4); i++)
          layer2Bias[i] *= getRandom(true);
      }
    } else
    {
      colorCode = #000000;  //Black
      for (int i=0; i<32; i++)
      {
        layer2Weight[i] = getRandom(anotherGeneration);
        if (i<2)
          layer3Bias[i] = getRandom(anotherGeneration);
        if (i<4)
          layer2Bias[i] = getRandom(anotherGeneration);
        if (i<8)
        {
          layer1Weight[i] = getRandom(anotherGeneration);
          layer1Bias[i] = getRandom(anotherGeneration);
          layer3Weight[i] = getRandom(anotherGeneration);
        }
      }
    }
  }

  private double getRandom(boolean anotherGeneration)  //If anotherGeneration is true, return a double with absolute value between 0.5 and 1.5. Otherwise, return a random double (+ or -)
  {
    double out=0;
    if (anotherGeneration)
    {
      do 
      {
        out = 2*random.nextDouble();
      }
      while (out<0.5||out>1.5);
    } else
      out = randomRange*random.nextDouble();

    if (random.nextInt()<0)
      out = -1*out;     

    return out;
  }

  public double[] run (double[] input)  //return the output of executing the entire neural net -- called by draw() method
  {
    executeLayer(false, input, layer1Weight, layer1Bias, layer1Output);
    executeLayer(true, layer1Output, layer2Weight, layer2Bias, layer2Output);
    executeLayer(true, layer2Output, layer3Weight, layer3Bias, layer3Output);
    layer3Output[0] *= movementFactor;
    layer3Output[1] *= movementFactor;
    return layer3Output;
  }

  private void executeLayer(boolean complex, double[] input, double[] weight, double[] bias, double[] to) //save result of executing each layer to double[] to
  {
    if (complex)
    {
      double[] sums = new double[to.length];
      for (int i=0; i<input.length; i++)
      {
        for (int j=0; j<to.length; j++)
        {
          sums[j] += input[i]*weight[to.length*i+j];
        }
      }

      for (int i=0; i<to.length; i++)
      {
        sums[i] += bias[i];
        to[i] = sums[i];
      }
    } else
    {
      for (int i=0; i<input.length; i++)
      {
        to[i] = input[i] * weight[i]+bias[i];
      }
    }
  }

  private void sigmoid(double[] to)
  {
    for (int i=0; i<to.length; i++)
    {
      to[i] = 1/(1+Math.pow(Math.E, -1*to[i]));
    }
  }

  public String getInfo()  //Get string representation of information related to the neural net
  {
    String out = "***Layer 1***\n";
    out += Arrays.toString(layer1Weight);
    out += "\n"+Arrays.toString(layer1Bias);
    out += "\n"+Arrays.toString(layer1Output);
    out += "\n***Layer 2***";
    out += "\n"+Arrays.toString(layer2Weight);
    out += "\n"+Arrays.toString(layer2Bias);
    out += "\n"+Arrays.toString(layer2Output);
    out += "\n***Layer 3***";
    out += "\n"+Arrays.toString(layer3Weight);
    out += "\n"+Arrays.toString(layer3Bias);
    out += "\n"+Arrays.toString(layer3Output);
    tempCount = 0;
    return out;
  }
  public static void storeLastBestModel()  //Store the weights and biases of the best neural nets in order to use them to produce the next population
  {
    int ref = 0;
    //if (PREVENT_OVERFITTING_B)
      //ref = trainSetCount*5;
    for (int i=0; i<5; i++)
    {
      System.arraycopy(neuralNets[top5NetIndex[i]].layer1Weight, 0, lastBestModel[ref+i][0], 0, 8);
      System.arraycopy(neuralNets[top5NetIndex[i]].layer1Bias, 0, lastBestModel[ref+i][1], 0, 8);
      System.arraycopy(neuralNets[top5NetIndex[i]].layer2Weight, 0, lastBestModel[ref+i][2], 0, 32);
      System.arraycopy(neuralNets[top5NetIndex[i]].layer2Bias, 0, lastBestModel[ref+i][3], 0, 4);
      System.arraycopy(neuralNets[top5NetIndex[i]].layer3Weight, 0, lastBestModel[ref+i][4], 0, 8);
      System.arraycopy(neuralNets[top5NetIndex[i]].layer3Bias, 0, lastBestModel[ref+i][5], 0, 2);
    }
  }
}