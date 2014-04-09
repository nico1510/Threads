/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package distributions;

import java.util.Random;

/**
 *
 * @author nico
 */
public class Seed {
    
    private static Random generic2Seed = new Random();
    private static Random generic3Seed = new Random();
    private static Random uniformSeed = new Random();
    private static Random geometricSeed = new Random();
    private static Random invGaussianSeed = new Random();
    private static Random poissonSeed = new Random();
    private static Random randomDoubleSeed = new Random();

    public static double getGeneric2Sample(double[] probs) {
        return Generic.getSample(probs, generic2Seed);
    }

    public static double getGeneric3Sample(double[] probs) {
        return Generic.getSample(probs, generic3Seed);
    }
    
    public static double getUniformSample(int range) {
        return (double)uniformSeed.nextInt(range);
    }
    
    public static double getRandomDouble() {
        return randomDoubleSeed.nextDouble();
    }        
    public static double getGeometricSample(double p) {
        return Geometric.getSample(p, geometricSeed);
    }

    public static double getInvGaussianSample(double mu, double lambda) {
        return InverseGaussian.getSample(mu, lambda, invGaussianSeed);
    }

    public static double getPoissonSample(double lambda) {
        return Poisson.getSample(lambda, poissonSeed);
    }
    
}
