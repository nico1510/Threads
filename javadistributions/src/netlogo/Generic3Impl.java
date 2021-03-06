/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netlogo;

import distributions.Generic;
import distributions.Seed;
import org.nlogo.api.Argument;
import org.nlogo.api.Context;
import org.nlogo.api.DefaultReporter;
import org.nlogo.api.ExtensionException;
import org.nlogo.api.LogoException;
import org.nlogo.api.Syntax;

/**
 *
 * @author nico
 */
public class Generic3Impl extends DefaultReporter {

  @Override
  public Syntax getSyntax() {
    return Syntax.reporterSyntax(
	new int[] {Syntax.NumberType(), Syntax.NumberType(), Syntax.NumberType()}, Syntax.NumberType());
  }
  
  @Override
  public Object report(Argument args[], Context context)
      throws ExtensionException {
      
    double p1 ;
    double p2 ;
    double p3 ;

    try {
      p1 = args[0].getDoubleValue();
      p2 = args[1].getDoubleValue();
      p3 = args[2].getDoubleValue();
    }
    catch(LogoException e) {
      throw new ExtensionException( e.getMessage() ) ;
    }
    /** what are valid values for mu and lambda ?
    if (mu < 0 || lambda < 0) {
	throw new ExtensionException
	  ("not a valid value for mu or lambda");
    }
    */
    
    double[] probs = new double[3];
    probs[0] = p1;
    probs[1] = p2;
    probs[2] = p3;
    double result = Seed.getGeneric3Sample(probs);
    
    return result;
  }
}