/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrapper;

import org.nlogo.api.DefaultClassManager;
import org.nlogo.api.PrimitiveManager;

/**
 *
 * @author nico
 */
public class MainClass extends DefaultClassManager{
    
  @Override
  public void load(PrimitiveManager primitiveManager) {
    primitiveManager.addPrimitive("reset", new ResetWrapper());
    primitiveManager.addPrimitive("thread-dist", new ThreadLengthDistributionWrapper());
    primitiveManager.addPrimitive("new-thread", new NewThreadWrapper());
    primitiveManager.addPrimitive("reply-to", new ReplyToWrapper());
    primitiveManager.addPrimitive("list-size", new ListSizeWrapper()); 
    primitiveManager.addPrimitive("missed-count", new MissedCountWrapper());
    primitiveManager.addPrimitive("distance", new DistanceWrapper());
    primitiveManager.addPrimitive("distance-from-file", new DistanceFromFileWrapper());
    primitiveManager.addPrimitive("copy-file", new CopyFileWrapper());
    primitiveManager.addPrimitive("read-forums", new ParseForumsWrapper()); 
    primitiveManager.addPrimitive("draw-sample", new DrawSampleWrapper()); 
    primitiveManager.addPrimitive("get-newthreadprobability", new GetNewThreadProbWrapper()); 
  }

}
