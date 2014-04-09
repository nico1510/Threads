package wrapper;

import implementation.Threads;
import org.nlogo.api.*;

public class NewThreadWrapper extends DefaultCommand {

  @Override
  public void perform(Argument[] args, Context context){
      Threads.newThread();
  }
  
}