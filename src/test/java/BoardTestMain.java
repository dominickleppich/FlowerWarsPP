import board.*;
import org.junit.runner.*;
import org.junit.runner.notification.*;

public class BoardTestMain {
    private static int executedTestCounter = 0;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(
                    "No board class name provided!\nCorrect usage: java -jar BoardTestMain.jar <FULL_BOARD_CLASS_NAME>");
            System.exit(1);
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

            System.exit(printStatistics(result));
        } catch (ClassNotFoundException e) {
            System.err.println("Unable to locate board class: " + args[0]);
        }
    }

    private static int printStatistics(Result result) {
        System.out.println("\n\n" + result.getRunCount() + " tests executed in " + String.format("%.3f s",
                (double) result.getRunTime() / 1000));
        System.out.println(String.format("Success: %4d (%.1f", (result.getRunCount() - result.getFailureCount()),
                (double) (result.getRunCount() - result.getFailureCount()) * 100.0 / result.getRunCount()) + "%)");
        System.out.println(String.format("Failed: %5d (%.1f", result.getFailureCount(),
                (double) result.getFailureCount() * 100.0 / result.getRunCount()) + "%)");

        System.out.println("\n");

        int counter = 1;
        for (Failure f : result.getFailures()) {
            System.out.println(
                    String.format("------------------------------------------------------------\nFailure #%03d:",
                            counter++));
            System.out.println(f);
            System.out.println();
            System.out.println(f.getTrace());
            System.out.println("------------------------------------------------------------");
        }

        return result.getFailureCount();
    }
}
