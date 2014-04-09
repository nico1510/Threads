package netlogo;

import distributions.Seed;
import org.nlogo.api.*;

public class RandomDoubleImpl extends DefaultReporter {

  @Override
  public Syntax getSyntax() {
    return Syntax.reporterSyntax(
	new int[] {}, Syntax.NumberType());
  }
  
  @Override
  public Object report(Argument args[], Context context)
      throws ExtensionException {


    double result = Seed.getRandomDouble();
    
    return result;
  }
}