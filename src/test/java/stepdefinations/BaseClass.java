package stepdefinations;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass {
    // Keep these as instance variables for PicoContainer injection
    public static AndroidDriver driver;
    public static WebDriverWait wait;
}



//public class BaseClass {
////    public static AndroidDriver driver;
////    public static WebDriverWait wait;
//    public static AndroidDriver driver;
//    public static WebDriverWait wait;
//    // Add getters for better access
//    public AndroidDriver getDriver() {
//        return driver;
//    }
//
//    public WebDriverWait getWait() {
//        return wait;
//    }
//}
//import com.google.common.collect.ImmutableMap;
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.android.options.UiAutomator2Options;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.interactions.PointerInput;
//import org.openqa.selenium.interactions.Sequence;
//
//import java.net.URL;
//import java.time.Duration;
//import java.util.Arrays;
//
//public class BaseClass {
//    public static AndroidDriver driver;
//    public static WebDriverWait wait;
//
//    protected static void setupDriver(String appPackage, String appActivity, String deviceName,
//                                      String udid, String platformVersion) throws Exception {
//        UiAutomator2Options options = new UiAutomator2Options();
//        options.setAppPackage(appPackage);
//        options.setAppActivity(appActivity);
//        options.setAutoGrantPermissions(true);
//        options.setDeviceName(deviceName);
//        options.setUdid(udid);
//        options.setPlatformName("Android");
//        options.setPlatformVersion(platformVersion);
//        options.setIgnoreHiddenApiPolicyError(true);
//        options.setNoReset(true);
//        options.setCapability("newCommandTimeout", 300);
//        options.setCapability("adbExecTimeout", 60000);
//
//        URL url = new URL("http://127.0.0.1:4723/");
//        driver = new AndroidDriver(url, options);
//        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//    }
//
//    protected static void tapByCoordinates(int x, int y) {
//        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
//        Sequence tap = new Sequence(finger, 1);
//        tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
//        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
//        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
//        driver.perform(Arrays.asList(tap));
//    }
//
//    protected static void clearAppCache(String packageName) {
//        try {
//            if (driver != null) {
//                driver.executeScript("mobile: shell",
//                        ImmutableMap.of("command", "pm clear " + packageName));
//                System.out.println("Cache cleared for app: " + packageName);
//            }
//        } catch (Exception e) {
//            System.out.println("Error clearing app cache: " + e.getMessage());
//        }
//    }
//
//    protected static void closeApp() {
//        try {
//            if (driver != null) {
//                driver.executeScript("mobile: shell",
//                        ImmutableMap.of("command", "am force-stop com.chataak.app"));
//                System.out.println("Application Force Stopped Successfully");
//                driver.quit();
//            }
//        } catch (Exception e) {
//            System.out.println("Error stopping the app: " + e.getMessage());
//        }
//    }
//
//    protected static void sleep(long milliseconds) {
//        try {
//            Thread.sleep(milliseconds);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//}