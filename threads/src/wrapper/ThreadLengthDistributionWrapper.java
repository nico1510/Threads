package wrapper;

import implementation.Threads;
import org.nlogo.api.*;

public class ThreadLengthDistributionWrapper extends DefaultReporter {
  
  @Override
  public Object report(Argument args[], Context context) throws ExtensionException {
       return LogoList.fromJava(Threads.threadFrequencies);
  }
  
}