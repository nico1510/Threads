package wrapper;

import implementation.Distance;
import org.nlogo.api.*;

public class ReadAnnealingResultsWrapper extends DefaultCommand {

    @Override
    public Syntax getSyntax() {
        return Syntax.reporterSyntax(
                new int[]{Syntax.StringType(), Syntax.StringType()}, Syntax.NumberType());
    }

    @Override
    public void perform(Argument[] args, Context context) throws ExtensionException {
        String annealingFile;
        String mode;

        try {
            annealingFile = args[0].getString();
            mode = args[1].getString();
            
        } catch (LogoException e) {
            throw new ExtensionException(e.getMessage());
        }
        
        Distance.readAnnealingResults(annealingFile, mode);
    }
}