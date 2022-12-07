package utils;

public final class OSValidateUtil {
    private static final String operationSystem = System.getProperty("os.name").toLowerCase();

    private OSValidateUtil() {
        //Utility class
    }

    public static boolean isWindows() {
        return (operationSystem.contains("win"));
    }

    public static boolean isUnix() {
        return (operationSystem.contains("nix") || operationSystem.contains("nux") || operationSystem.contains("aix"));
    }
}
