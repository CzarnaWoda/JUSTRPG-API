package pl.justrpg.api.util;

import java.util.Random;

import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;

public class RandomUtil
{
  public static int getRandInt(int min, int max)
    throws IllegalArgumentException
  {
    Validate.isTrue(max > min, "Max can't be smaller than min!");
    return rand.nextInt(max - min + 1) + min;
  }
  
  @NotNull
  public static Double getRandDouble(double min, double max)
    throws IllegalArgumentException
  {
    Validate.isTrue(max > min, "Max can't be smaller than min!");
    return Double.valueOf(rand.nextDouble() * (max - min) + min);
  }
  
  @NotNull
  public static Float getRandFloat(float min, float max)
    throws IllegalArgumentException
  {
    Validate.isTrue(max > min, "Max can't be smaller than min!");
    return Float.valueOf(rand.nextFloat() * (max - min) + min);
  }
  
  public static boolean getChance(double chance)
  {
    return (chance >= 100.0D) || (chance >= getRandDouble(0.0D, 100.0D).doubleValue());
  }
  
  private static Random rand = new Random();
  
  public static void main(String[] args) {}
}
