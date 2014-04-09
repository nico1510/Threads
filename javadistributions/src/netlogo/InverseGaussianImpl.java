/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package netlogo;

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
public class InverseGaussianImpl extends DefaultReporter {

  @Override
  public Syntax getSyntax() {
    return Syntax.reporterSyntax(
	new int[] {Syntax.NumberType(), Syntax.NumberType()}, Syntax.NumberType());
  }
  
  @Override
  public Object report(Argument args[], Context context)
      throws ExtensionException {
      
    double mu ;
    double lambda ;

    try {
      mu = args[0].getDoubleValue();
      lambda = args[1].getDoubleValue();
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
    
    double result = Seed.getInvGaussianSample(mu, lambda);
    
    return result;
  }
}