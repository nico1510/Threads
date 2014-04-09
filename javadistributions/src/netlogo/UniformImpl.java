package netlogo;

import distributions.Seed;
import org.nlogo.api.*;

public class UniformImpl extends DefaultReporter {

  @Override
  public Syntax getSyntax() {
    return Syntax.reporterSyntax(
	new int[] {Syntax.NumberType()}, Syntax.NumberType());
  }
  
  @Override
  public Object report(Argument args[], Context context)
      throws ExtensionException {
      
    int range ;

    try {
      range = args[0].getIntValue();
    }
    catch(LogoException e) {
      throw new ExtensionException( e.getMessage() ) ;
    }

    double result = Seed.getUniformSample(range);
    
    return result;
  }
}