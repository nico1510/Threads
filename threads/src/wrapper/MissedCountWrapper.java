package wrapper;

import implementation.Threads;
import org.nlogo.api.*;

public class MissedCountWrapper extends DefaultReporter {
  
  @Override
  public Object report(Argument args[], Context context) throws ExtensionException {
      return Threads.getMissedCount();
  }
  
}