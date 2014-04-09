package wrapper;

import implementation.Util;
import org.nlogo.api.*;

public class CopyFileWrapper extends DefaultCommand {

  @Override
  public Syntax getSyntax() {
    return Syntax.commandSyntax(
	new int[] {Syntax.StringType(), Syntax.StringType()});
  }
    
  @Override
  public void perform(Argument[] args, Context context) throws ExtensionException {

    String inputFilePath ;
    String outputFilePath ;

    try {
      inputFilePath = args[0].getString();
      outputFilePath = args[1].getString();
    }
    catch(LogoException e) {
      throw new ExtensionException( e.getMessage() ) ;
    }
    
    Util.copyFile(inputFilePath, outputFilePath);
  }
  
}