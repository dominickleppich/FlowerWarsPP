package board;

import flowerwarspp.preset.*;

import java.lang.reflect.*;

public class TestBoardFactory {
    private static String boardClassName;

    // ------------------------------------------------------------

    static {
        // Init with my default value
        boardClassName = "flowerwarspp.board.BoardImpl";
    }

    // ------------------------------------------------------------

    public static void setBoardClassName(String boardClassName) throws Exception {
        Class boardClass = Class.forName(boardClassName);

        boolean foundCorrectConstructor = false;

        // Correct parameterList
        Class[] correctParameterTypes = new Class[]{int.class};

        // Check all constructors until one matches
        for (Constructor<Board> constructor : boardClass.getConstructors()) {
            Parameter[] parameters = constructor.getParameters();

            // Check number of arguments
            if (parameters.length != correctParameterTypes.length)
                continue;

            // Check all types
            for (int i = 0; i < parameters.length; i++)
                if (parameters[i].getType() != correctParameterTypes[i])
                    continue;

            foundCorrectConstructor = true;
            break;
        }

        if (foundCorrectConstructor)
            TestBoardFactory.boardClassName = boardClassName;
        else
            throw new Exception("Board class has no valid constructor");
    }

    public static Board createInstance(int boardSize) {
        try {
            Class boardClass = Class.forName(boardClassName);

            // Correct parameterList
            Class[] correctParameterTypes = new Class[]{int.class};

            // Check all constructors until one matches
            for (Constructor<Board> constructor : boardClass.getConstructors()) {
                Parameter[] parameters = constructor.getParameters();

                // Check number of arguments
                if (parameters.length != correctParameterTypes.length)
                    continue;

                // Check all types
                for (int i = 0; i < parameters.length; i++)
                    if (parameters[i].getType() != correctParameterTypes[i])
                        continue;

                return constructor.newInstance(boardSize);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Board class not found");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Board class not accessible");
        } catch (InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Board class cannot be instantiated");
        }

        throw new RuntimeException("Board class has no valid constructor");
    }
}
