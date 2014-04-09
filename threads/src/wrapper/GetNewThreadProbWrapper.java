package wrapper;

import implementation.Distance;
import org.nlogo.api.*;

public class GetNewThreadProbWrapper extends DefaultReporter {

    @Override
    public Syntax getSyntax() {
        return Syntax.reporterSyntax(
                new int[]{Syntax.NumberType(), Syntax.NumberType(), Syntax.NumberType()}, Syntax.NumberType());
    }

    @Override
    public Object report(Argument args[], Context context)
            throws ExtensionException {

        Integer forumID;
        Integer startPercent;
        Integer endPercent;

        try {
            forumID = args[0].getIntValue();
            startPercent = args[1].getIntValue();
            endPercent = args[2].getIntValue();
        } catch (LogoException e) {
            throw new ExtensionException(e.getMessage());
        }
        
        return Distance.getNewThreadProb(forumID, startPercent, endPercent);
    }
}