package wrapper;

import implementation.Distance;
import org.nlogo.api.*;

public class DistanceFromFileWrapper extends DefaultReporter {
 
  @Override
  public Syntax getSyntax() {
    return Syntax.reporterSyntax(
	new int[] {Syntax.StringType(), Syntax.StringType()}, Syntax.NumberType());
  }
  
  @Override
  public Object report(Argument args[], Context context)
      throws ExtensionException {
      
    String realDataFile ;
    String simulationFile ;

    try {
      realDataFile = args[0].getString();
      simulationFile = args[1].getString();
    }
    catch(LogoException e) {
      throw new ExtensionException( e.getMessage() ) ;
    }
    return Distance.getDistanceFromFile(realDataFile, simulationFile);
  }
}