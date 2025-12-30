package utilities;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DriverManager {
    private static AndroidDriver driver;
    private static WebDriverWait wait;

    public static void setDriver(AndroidDriver driverInstance) {
        driver = driverInstance;
    }

    public static AndroidDriver getDriver() {
        return driver;
    }

    public static void setWait(WebDriverWait waitInstance) {
        wait = waitInstance;
    }

    public static WebDriverWait getWait() {
        return wait;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            wait = null;
        }
    }
}
