package airfreights.console;

import airfreights.console.services.launcher.LauncherImpl;

public class ApplicationConsole {
    public static void main(String[] args) {
        LauncherImpl launcher = new LauncherImpl();
        launcher.launch();
    }
}
