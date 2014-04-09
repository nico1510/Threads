/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netlogo;

import org.nlogo.api.DefaultClassManager;
import org.nlogo.api.PrimitiveManager;

/**
 *
 * @author nico
 */
public class MainClass extends DefaultClassManager {
    
  @Override
  public void load(PrimitiveManager primitiveManager) {
    primitiveManager.addPrimitive("geometric-sample", new GeometricImpl());
    primitiveManager.addPrimitive("poisson-sample", new PoissonImpl());
    primitiveManager.addPrimitive("inv-gaussian-sample", new InverseGaussianImpl());
    primitiveManager.addPrimitive("generic-sample2", new Generic2Impl());
    primitiveManager.addPrimitive("generic-sample3", new Generic3Impl()); 
    primitiveManager.addPrimitive("uniform-sample", new UniformImpl()); 
    primitiveManager.addPrimitive("random-double", new RandomDoubleImpl()); 
  }

}
