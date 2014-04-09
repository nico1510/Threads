package wrapper;

import implementation.Distance;
import org.nlogo.api.*;

public class DistanceWrapper extends DefaultReporter {
 
  @Override
  public Syntax getSyntax() {
    return Syntax.reporterSyntax(
	new int[] {Syntax.NumberType(), Syntax.NumberType(), Syntax.NumberType(), Syntax.StringType()}, Syntax.NumberType());
  }
  
  @Override
  public Object report(Argument args[], Context context)
      throws ExtensionException {
      
    Integer forumID ;
    Integer startPercent ;
    Integer endPercent ;
    String simulationFile ;

    try {
      forumID = args[0].getIntValue();
      startPercent = args[1].getIntValue();
      endPercent = args[2].getIntValue();
      simulationFile = args[3].getString();
    }
    catch(LogoException e) {
      throw new ExtensionException( e.getMessage() ) ;
    }
    return Distance.getDistance(forumID, startPercent, endPercent, simulationFile);
  }
}