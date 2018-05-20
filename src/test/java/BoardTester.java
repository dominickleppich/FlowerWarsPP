import board.*;
import org.junit.runner.*;
import org.junit.runner.notification.*;

public class BoardTester {
    private static int executedTestCounter = 0;

    enum ResultInfo {
        NONE, MINI, FULL;
    }

    public static void main(String[] args) {
        ResultInfo info = ResultInfo.NONE;

        if (args.length < 1 || args.length > 2) {
            System.err.println(
                    "No board class name provided!\nCorrect usage: java -jar BoardTester.jar <FULL_BOARD_CLASS_NAME> [mini / full]");
            System.exit(1);
        } else if (args.length == 2) {
            if (args[1].equals("mini"))
                info = ResultInfo.MINI;
            else if (args[1].equals("full"))
                info = ResultInfo.FULL;
            else {
                System.err.println("Illegal option: " + args[1] + "! Possible values are \"mini\" and \"full\"");
                System.exit(1);
            }
        }

        try {
            TestBoardFactory.setBoardClassName(args[0]);

            JUnitCore junit = new JUnitCore();

            junit.addListener(new RunListener() {
                @Override
                public void testFinished(Description description) {
                    System.out.print(".");
                    executedTestCounter++;
                    if (executedTestCounter >= 50) {
                        executedTestCounter = 0;
                        System.out.println();
                    }
                }
            });
            System.out.println("Starting tests...\n");
            Result result = junit.run(BoardTests.class);

            System.exit(printStatistics(result, info));
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to locate board class: " + args[0]);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static int printStatistics(Result result, ResultInfo info) {
        System.out.println("\n\n" + result.getRunCount() + " tests executed in " + String.format("%5.3f s",
                (double) result.getRunTime() / 1000));
        System.out.println(String.format("Success: %4d (%5.1f", (result.getRunCount() - result.getFailureCount()),
                (double) (result.getRunCount() - result.getFailureCount()) * 100.0 / result.getRunCount()) + "%)");
        System.out.println(String.format("Failed: %5d (%5.1f", result.getFailureCount(),
                (double) result.getFailureCount() * 100.0 / result.getRunCount()) + "%)");

        if (info != ResultInfo.NONE) {
            System.out.println("\n");

            int counter = 1;
            for (Failure f : result.getFailures()) {
                System.out.println(String.format("------------------------------------------------------------\nFailure #%03d:",
                        counter++));
                System.out.println(f);
                if (info == ResultInfo.FULL) {
                    System.out.println();
                    System.out.println(f.getTrace());
                }
                System.out.println("------------------------------------------------------------");
            }
        }

        return result.getFailureCount();
    }
}
