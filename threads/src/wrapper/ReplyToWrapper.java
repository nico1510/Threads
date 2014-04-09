package wrapper;

import implementation.Threads;
import org.nlogo.api.*;

public class ReplyToWrapper extends DefaultCommand {
    
    @Override
    public Syntax getSyntax() {
        return Syntax.commandSyntax(
                new int[]{Syntax.NumberType(), Syntax.NumberType()});
    }
    
    @Override
    public void perform(Argument[] args, Context context) throws ExtensionException {
        
        Integer threadID = null;
        Integer filterID = null;
        
        try {
            threadID = args[0].getIntValue();
            filterID = args[1].getIntValue();
        } catch (LogoException e) {
            throw new ExtensionException(e.getMessage());
        }
        
        Threads.replyTo(threadID, filterID);
    }
}
