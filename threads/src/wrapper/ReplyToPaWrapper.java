package wrapper;

import implementation.Threads;
import org.nlogo.api.*;

public class ReplyToPaWrapper extends DefaultCommand {
    
    @Override
    public Syntax getSyntax() {
        return Syntax.commandSyntax(
                new int[]{Syntax.NumberType()});
    }
    
    @Override
    public void perform(Argument[] args, Context context) throws ExtensionException {
        
        Double powerValue = null;
        
        try {
            powerValue = args[0].getDoubleValue();
        } catch (LogoException e) {
            throw new ExtensionException(e.getMessage());
        }
        
        Threads.replyToPa(powerValue);
    }
}
