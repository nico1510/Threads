package wrapper;

import implementation.Distance;
import implementation.Threads;
import java.util.ArrayList;
import org.nlogo.api.*;

public class BestParametersWrapper extends DefaultReporter {
  
    @Override
    public Syntax getSyntax() {
        return Syntax.reporterSyntax(
                new int[]{Syntax.NumberType()}, Syntax.ListType());
    }

    @Override
    public Object report(Argument args[], Context context)
            throws ExtensionException {

        int forumID;

        try {
            forumID = args[0].getIntValue();
        } catch (LogoException e) {
            throw new ExtensionException(e.getMessage());
        }
        
       ArrayList<Double> params = (ArrayList<Double>)Distance.bestParametersMap.get(String.valueOf(forumID)).clone();
       params.remove(0); // remove distance
       return LogoList.fromJava(params);
    }
  
}