package ok.dwh.TamTamSender;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public class Main {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private static final Options OPTIONS = new Options();

    public static void main(String[] args) {
        BasicConfigurator.configure();
        System.out.println("started");
        OptionSet optionSet;
        try {
            for(int i=0; i<args.length; i++) {
                args[i] = args[i].replace("<br>","\n");
            }
            optionSet = OPTIONS.parse(args);
        } catch (OptionException e) {
            System.err.println(e.getMessage());
            System.exit(1);
            return;
        }

        String accessToken = OPTIONS.accessToken.value(optionSet);
        String message = OPTIONS.message.value(optionSet);

        TamTamSender tamTamSender = new TamTamSender(accessToken);
        int success =  tamTamSender.sendMessageToAllChats(message) ? 0:1;
        System.exit(success);
    }

    private static class Options extends OptionParser {
        OptionSpec<String> accessToken = accepts("token").withRequiredArg().required().ofType(String.class);
        OptionSpec<String> message = accepts("message").withRequiredArg().required().ofType(String.class);
    }
}