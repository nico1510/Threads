package wrapper;

import implementation.Distance;
import implementation.Threads;
import java.util.ArrayList;
import org.nlogo.api.*;

public class ReadAnnealingResultsWrapper extends DefaultCommand {

    @Override
    public Syntax getSyntax() {
        return Syntax.reporterSyntax(
                new int[]{Syntax.StringType()}, Syntax.NumberType());
    }

    @Override
    public void perform(Argument[] args, Context context) throws ExtensionException {
        String annealingFile;

        try {
            annealingFile = args[0].getString();
        } catch (LogoException e) {
            throw new ExtensionException(e.getMessage());
        }
        
        Distance.readAnnealingResults(annealingFile);
    }
}