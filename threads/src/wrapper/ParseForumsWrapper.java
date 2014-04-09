package wrapper;

import implementation.Distance;
import org.nlogo.api.*;

public class ParseForumsWrapper extends DefaultReporter {

    @Override
    public Syntax getSyntax() {
        return Syntax.reporterSyntax(
                new int[]{Syntax.StringType(), Syntax.StringType()}, Syntax.NumberType());
    }

    @Override
    public Object report(Argument args[], Context context)
            throws ExtensionException {

        String forumsFile;
        String randomNumbersFile;

        try {
            forumsFile = args[0].getString();
            randomNumbersFile = args[1].getString();
        } catch (LogoException e) {
            throw new ExtensionException(e.getMessage());
        }
        Distance.createThreadsPerForumMap(forumsFile, randomNumbersFile);
        return (double) Distance.threadsPerForumMap.size() - 1.0;
    }
}