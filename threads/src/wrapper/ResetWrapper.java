package wrapper;

import implementation.Threads;
import org.nlogo.api.*;

public class ResetWrapper extends DefaultCommand {

  @Override
  public void perform(Argument[] args, Context context){
      Threads.reset();
  }
  
}