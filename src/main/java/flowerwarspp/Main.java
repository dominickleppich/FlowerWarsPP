package flowerwarspp;

import flowerwarspp.game.*;
import flowerwarspp.net.*;
import flowerwarspp.player.*;
import flowerwarspp.player.ai.*;
import flowerwarspp.preset.*;
import flowerwarspp.ui.graphical.*;
import org.slf4j.*;
import sun.reflect.generics.reflectiveObjects.*;

import java.io.*;
import java.rmi.*;
import java.util.*;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static RMIHandler rmi;

    // ------------------------------------------------------------

    public static void main(String[] args) throws Exception {
        try {

            MyArgumentParser ap = new MyArgumentParser(args);

            if (ap.isHelp()) {
                StringBuilder sb = new StringBuilder();
                sb.append("You can the following parameters. Flags, starting with \"--\" are disabled by default\n\n");
                sb.append("\t-size\t\t\tSet the board size {4, 5, 6, ..., 26}\n");
                sb.append("\t-red\t\t\tSet the red player type {human, random, simple, remote}\n");
                sb.append("\t-blue\t\t\tSet the blue player type {human, random, simple, remote}\n");
                sb.append("\t-offer\t\t\tSet the player type to offer on a local rmi\n");
                sb.append("\t-name\t\t\tSet the name for an offered player\n");
                sb.append("\t-host\t\t\tHost of the rmi registry to find a player\t[Default: localhost]\n");
                sb.append("\t-port\t\t\tPort of the rmi registry to find a player\t[Default: 1099]\n");
                sb.append("\t-delay\t\t\tDelay between moves (in milliseconds)\t\t[Default: 0]\n");
            /*sb.append("\t--graphic\t\tActivate a graphical in- and output\n");
            sb.append("\t--debug\t\t\tPrint debug information\n");
            sb.append("\t--analyze\t\tPrint detailed information about every move\n");*/
                System.out.println(sb.toString());
                System.exit(0);
            }

            // Create requestable object
            UIWindow window = new UIWindow();

            // Check if network mode enabled
            if (ap.isSet("offer") || ap.getRed() == PlayerType.REMOTE || ap.getBlue() == PlayerType.REMOTE)
                rmi = new RMIHandler(ap.isSet("host") ? ap.getHost() : "localhost",
                        ap.isSet("port") ? ap.getPort() : 1099);

            // Local game
            if (!ap.isSet("offer")) {
                // Create players
                Player red, blue;
                red = createPlayer(PlayerColor.Red, ap.getRed(), window);
                blue = createPlayer(PlayerColor.Blue, ap.getBlue(), window);

                // Create match and start
                Match match = new Match(ap.getSize(), red, blue, window);
                match.play();
            }
            // Network offer
            else {
                rmi.offer(new RemotePlayer(createPlayer(null, ap.getOffer(), window), window), ap.getName());
                System.out.println("Player " + ap.getOffer() + " offered with name " + ap.getName());

                while (true) {
                    Thread.sleep(100000);
                }
            }

            Thread.sleep(5000);

            window.close();
        } catch (ArgumentParserException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Something went wrong with your parameters: " + e.getMessage() + "\n");
            sb.append("Type --help to get detailed information about possible parameters.\n");
            System.out.println(sb.toString());
            System.exit(1);
        } catch (InterruptedException e) {
            logger.error("Error waiting");
            e.printStackTrace(System.err);
        } catch (Exception e) {
            logger.error("Failed to init or start match! " + e.getMessage());
            e.printStackTrace(System.err);
            System.exit(2);
        }
    }

    // ------------------------------------------------------------

    private static Player createPlayer(PlayerColor color, PlayerType type, Requestable requestable) throws IOException, NotBoundException {
        switch (type) {
            case HUMAN:
                return new InteractivePlayer(requestable);
            case RANDOM_AI:
                return new RandomPlayer();
            case REMOTE:
                // List all Players
                Set<Map.Entry<String, Player>> players = rmi.list().entrySet();
                if (!players.isEmpty()) {
                    Player p = null;
                    for (Map.Entry<String, Player> e : players) {
                        p = e.getValue();
                        System.out.println("Found remote player: " + e.getKey());
                    }
                    if (players.size() > 1) {
                        System.out.print("Which player do you want to use as " + (color == PlayerColor.Red ? "red" : "blue") + " player? Type full name: ");
                        BufferedReader rd = new BufferedReader(new InputStreamReader(System.in));
                        return rmi.find(rd.readLine());
                    } else return p;
                } else
                    throw new NotBoundException("No remote players available");
            default:
                throw new NotImplementedException();
        }
    }
}
