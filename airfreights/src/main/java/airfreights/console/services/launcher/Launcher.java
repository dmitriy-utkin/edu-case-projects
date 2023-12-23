package airfreights.console.services.launcher;

public interface Launcher {
    void launch();
    String input();
    void overApp() throws InterruptedException;
}
