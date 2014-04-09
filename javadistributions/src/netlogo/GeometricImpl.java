package netlogo;

import distributions.Seed;
import org.nlogo.api.*;

public class GeometricImpl extends DefaultReporter {

  @Override
  public Syntax getSyntax() {
    return Syntax.reporterSyntax(
	new int[] {Syntax.NumberType()}, Syntax.NumberType());
  }
  
  @Override
  public Object report(Argument args[], Context context)
      throws ExtensionException {
      
    double p ;

    try {
      p = args[0].getDoubleValue(); 
    }
    catch(LogoException e) {
      throw new ExtensionException( e.getMessage() ) ;
    }
    if (p < 0 || p > 1) {
	throw new ExtensionException
	  ("Probability must be in interval [0..1]");
    }

    double result = Seed.getGeometricSample(p);
    
    return result;
  }
}