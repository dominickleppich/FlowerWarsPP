package flowerwarspp;

import flowerwarspp.game.*;
import flowerwarspp.player.*;
import flowerwarspp.player.ai.*;
import flowerwarspp.preset.*;
import flowerwarspp.ui.graphical.*;
import org.slf4j.*;
import sun.reflect.generics.reflectiveObjects.*;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    // ------------------------------------------------------------

    public static void main(String[] args) throws Exception {
        ArgumentParser ap = new ArgumentParser(args);

        // Create requestable object
        UIWindow window = new UIWindow();

        // Create players
        Player red = createPlayer(ap.getRed(), window);
        Player blue = createPlayer(ap.getBlue(), window);

        // Create match
        Match match = new Match(ap.getSize(), red, blue, window);

        // Start match
        match.play();

        // Close display
//        window.close();
    }

    // ------------------------------------------------------------

    private static Player createPlayer(PlayerType type, Requestable requestable) {
        switch (type) {
            case HUMAN:
                return new InteractivePlayer(requestable);
            case RANDOM_AI:
                return new RandomPlayer();
            default:
                throw new NotImplementedException();
        }
    }
}
