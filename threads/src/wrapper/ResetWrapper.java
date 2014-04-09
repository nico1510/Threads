package wrapper;

import implementation.Threads;
import java.util.ArrayList;
import org.nlogo.api.*;

public class ResetWrapper extends DefaultCommand {

  @Override
  public void perform(Argument[] args, Context context){
      Threads.threads = new ArrayList<Double>();
      Threads.threadFrequencies = new ArrayList<Double>();
      Threads.missedCount = 0;
  }
  
}