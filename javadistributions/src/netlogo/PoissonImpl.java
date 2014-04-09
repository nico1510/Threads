package netlogo;

import distributions.Seed;
import org.nlogo.api.*;

public class PoissonImpl extends DefaultReporter {

  @Override
  public Syntax getSyntax() {
    return Syntax.reporterSyntax(
	new int[] {Syntax.NumberType()}, Syntax.NumberType());
  }
  @Override
  public Object report(Argument args[], Context context)
      throws ExtensionException {
      
    double lambda ;

    try {
      lambda = args[0].getDoubleValue(); 
    }
    catch(LogoException e) {
      throw new ExtensionException( e.getMessage() ) ;
    }
    /* what are valid values for lambda ??
    if (lambda < 0) {
	throw new ExtensionException
	  ("lambda must be in interval ...");
    }
    */
    double result = Seed.getPoissonSample(lambda);
    
    return result;
  }
}