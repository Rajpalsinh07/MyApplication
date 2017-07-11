package login1.myapplication;

/**
 * Created by Moksha on 5/9/2017.
 */
public class AppState {
    private static AppState singleInstance;

    private boolean isLoggingOut;

    private AppState() {
    }

    public static AppState getSingleInstance() {
        if (singleInstance == null) {
            singleInstance = new AppState();
        }
        return singleInstance;
    }

    public boolean isLoggingOut() {
        return isLoggingOut;
    }

    public void setLoggingOut(boolean isLoggingOut) {
        this.isLoggingOut = isLoggingOut;
    }
}
