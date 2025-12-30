//package hooks;
package hooks;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import stepdefinations.BaseClass;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AppHooks {

    private final BaseClass baseClass;

    // PicoContainer injects BaseClass
    public AppHooks(BaseClass baseClass) {
        this.baseClass = baseClass;
    }

    @Before(order = 0)
    public void launchRiderGroupApp() throws MalformedURLException {
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("🚀 STARTING RIDER GROUP TRACKER APP FROM LOGIN PAGE");
        System.out.println("══════════════════════════════════════════════════");

        UiAutomator2Options options = new UiAutomator2Options();

        // ✅ Rider Group Tracker App details
        options.setAppPackage("com.example.rider_group_tracker");
        options.setAppActivity("com.example.rider_group_tracker.MainActivity");
        options.setAutoGrantPermissions(true);

        // ✅ Device configuration for Emulator
        options.setDeviceName("sdk_gphone64_x86_64");
        options.setUdid("emulator-5554");
        options.setPlatformVersion("14");

        // ✅ CRITICAL: Reset settings to start fresh from login
        options.setNoReset(false);           // FALSE = Clear app data (starts fresh)
        options.setFullReset(false);         // FALSE = Don't uninstall app
        options.setCapability("autoLaunch", true);  // Auto-launch the app

        // Timeout configurations
        options.setAvdLaunchTimeout(Duration.ofSeconds(180));
        options.setAvdReadyTimeout(Duration.ofSeconds(180));
        options.setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(180));

        // Other capabilities
        options.setIgnoreHiddenApiPolicyError(true);
        options.setCapability("newCommandTimeout", 300);
        options.setCapability("adbExecTimeout", 120000);

        URL url = new URL("http://127.0.0.1:4723/");

        // ✅ STEP 1: Force stop app and clear data BEFORE creating driver
        forceStopAndClearAppData("com.example.rider_group_tracker");

        // ✅ STEP 2: Initialize driver
        baseClass.driver = new AndroidDriver(url, options);
        System.out.println("✅ Driver created successfully");

        // ✅ STEP 3: Set implicit wait
        baseClass.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // ✅ STEP 4: Initialize WebDriverWait
        baseClass.wait = new WebDriverWait(baseClass.driver, Duration.ofSeconds(20));
        System.out.println("✅ WebDriverWait created successfully");

        // ✅ STEP 5: Wait for app to load and verify it's on login page
        verifyAndEnsureLoginPage(baseClass.driver);

        System.out.println("✅ App Launched Successfully from Login Page");
        System.out.println("📱 Session ID: " + baseClass.driver.getSessionId());
        System.out.println("📱 Current Activity: " + baseClass.driver.currentActivity());
        System.out.println("══════════════════════════════════════════════════");
    }

    @After
    public void tearDown() {
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("🔚 CLEANING UP AFTER TEST");
        System.out.println("══════════════════════════════════════════════════");

        if (baseClass.driver != null) {
            forceStopAndClearAppData("com.example.rider_group_tracker");
            closeDriver();
        }
    }

    // ---------- Utility Methods ----------

    private void forceStopAndClearAppData(String packageName) {
        try {
            System.out.println("🧹 Force stopping and clearing app data for: " + packageName);

            // 1. Force stop the app
            Process stopProcess = Runtime.getRuntime().exec(
                    new String[]{"adb", "-s", "emulator-5554", "shell", "am", "force-stop", packageName}
            );
            stopProcess.waitFor();
            System.out.println("✅ App force-stopped");

            // 2. Clear app data
            Process clearProcess = Runtime.getRuntime().exec(
                    new String[]{"adb", "-s", "emulator-5554", "shell", "pm", "clear", packageName}
            );
            clearProcess.waitFor();
            System.out.println("✅ App data cleared");

            // 3. Small delay to ensure cleanup is complete
            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("⚠️ Could not clear app data: " + e.getMessage());
            // Fallback: Try using driver if available
            if (baseClass.driver != null) {
                try {
                    baseClass.driver.executeScript("mobile: shell",
                            java.util.Map.of("command", "pm clear " + packageName));
                    System.out.println("✅ App data cleared using driver fallback");
                } catch (Exception ex) {
                    System.out.println("⚠️ Fallback clear also failed: " + ex.getMessage());
                }
            }
        }
    }

    private void verifyAndEnsureLoginPage(AndroidDriver driver) {
        try {
            System.out.println("🔍 Ensuring we're on login page...");

            // Wait for app to fully load
            Thread.sleep(5000);

            // Get current activity
            String currentActivity = driver.currentActivity();
            System.out.println("📱 Current Activity: " + currentActivity);

            // If not on MainActivity (login), try to reset
            if (!currentActivity.contains("MainActivity")) {
                System.out.println("⚠️ Not on login screen. Attempting to reset...");

                // You can uncomment this if you add the imports
                // driver.pressKey(new io.appium.java_client.android.nativekey.AndroidKey(
                //     io.appium.java_client.android.nativekey.AndroidKeyCode.BACK));

                Thread.sleep(2000);

                // Check again
                currentActivity = driver.currentActivity();
                System.out.println("📱 Activity after check: " + currentActivity);
            }

            if (currentActivity.contains("MainActivity")) {
                System.out.println("✅ Successfully on login screen");
            } else {
                System.out.println("⚠️ May not be on login screen. Current: " + currentActivity);
            }

        } catch (Exception e) {
            System.out.println("⚠️ Error verifying login page: " + e.getMessage());
        }
    }

    private void closeDriver() {
        try {
            System.out.println("🛑 Closing driver session...");

            if (baseClass.driver != null) {
                // Optional: Clear cache using driver before quitting
                try {
                    baseClass.driver.executeScript("mobile: shell",
                            java.util.Map.of("command", "pm clear com.example.rider_group_tracker"));
                    System.out.println("✅ Cache cleared before quitting");
                } catch (Exception e) {
                    System.out.println("⚠️ Could not clear cache: " + e.getMessage());
                }

                baseClass.driver.quit();
                System.out.println("🔚 Driver session closed");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Error closing driver: " + e.getMessage());
        }
    }

    // Original clearAppCache method (kept for compatibility if needed elsewhere)
    private void clearAppCache(String packageName) {
        forceStopAndClearAppData(packageName);
    }

    // Original closeApp method (kept for compatibility if needed elsewhere)
    private void closeApp() {
        if (baseClass.driver != null) {
            forceStopAndClearAppData("com.example.rider_group_tracker");
            closeDriver();
        }
    }
}


//package hooks;
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.android.options.UiAutomator2Options;
//import io.cucumber.java.After;
//import io.cucumber.java.Before;
//import utilities.DriverManager;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.time.Duration;
//
//public class AppHooks {
//
//    @Before(order = 0)
//    public void launchRiderGroupApp() throws MalformedURLException {
//        System.out.println("══════════════════════════════════════════════════");
//        System.out.println("🚀 STARTING RIDER GROUP TRACKER APP FROM LOGIN PAGE");
//        System.out.println("══════════════════════════════════════════════════");
//
//        UiAutomator2Options options = new UiAutomator2Options();
//
//        // ✅ Rider Group Tracker App details
//        options.setAppPackage("com.example.rider_group_tracker");
//        options.setAppActivity("com.example.rider_group_tracker.MainActivity");
//        options.setAutoGrantPermissions(true);
//
//        // ✅ Device configuration for Emulator
//        options.setDeviceName("sdk_gphone64_x86_64");
//        options.setUdid("emulator-5554");
//        options.setPlatformVersion("14");
//
//        // ✅ CRITICAL: Reset settings to start fresh from login
//        options.setNoReset(false);           // FALSE = Clear app data (starts fresh)
//        options.setFullReset(false);         // FALSE = Don't uninstall app
//        options.setCapability("autoLaunch", true);  // Auto-launch the app
//
//        // Timeout configurations
//        options.setAvdLaunchTimeout(Duration.ofSeconds(180));
//        options.setAvdReadyTimeout(Duration.ofSeconds(180));
//        options.setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(180));
//
//        // Other capabilities
//        options.setIgnoreHiddenApiPolicyError(true);
//        options.setCapability("newCommandTimeout", 300);
//        options.setCapability("adbExecTimeout", 120000);
//
//        URL url = new URL("http://127.0.0.1:4723/");
//
//        // ✅ STEP 1: Force stop app and clear data BEFORE creating driver
//        forceStopAndClearAppData("com.example.rider_group_tracker");
//
//        // ✅ STEP 2: Initialize driver
//        AndroidDriver driver = new AndroidDriver(url, options);
//        System.out.println("✅ Driver created successfully");
//
//        // ✅ STEP 3: Set implicit wait
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//
//        // ✅ STEP 4: Initialize WebDriverWait
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//        System.out.println("✅ WebDriverWait created successfully");
//
//        // ✅ STEP 5: Store in DriverManager
//        DriverManager.setDriver(driver);
//        DriverManager.setWait(wait);
//
//        // ✅ STEP 6: Wait for app to load and verify it's on login page
//        verifyAndEnsureLoginPage(driver);
//
//        System.out.println("✅ App Launched Successfully from Login Page");
//        System.out.println("📱 Session ID: " + driver.getSessionId());
//        System.out.println("📱 Current Activity: " + driver.currentActivity());
//        System.out.println("══════════════════════════════════════════════════");
//    }
//
//    @After
//    public void tearDown() {
//        System.out.println("══════════════════════════════════════════════════");
//        System.out.println("🔚 CLEANING UP AFTER TEST");
//        System.out.println("══════════════════════════════════════════════════");
//
//        AndroidDriver driver = DriverManager.getDriver();
//        if (driver != null) {
//            forceStopAndClearAppData("com.example.rider_group_tracker");
//            closeDriver();
//        }
//    }
//
//    // ---------- Utility Methods ----------
//
//    private void forceStopAndClearAppData(String packageName) {
//        try {
//            System.out.println("🧹 Force stopping and clearing app data for: " + packageName);
//
//            // 1. Force stop the app
//            Process stopProcess = Runtime.getRuntime().exec(
//                    new String[]{"adb", "-s", "emulator-5554", "shell", "am", "force-stop", packageName}
//            );
//            stopProcess.waitFor();
//            System.out.println("✅ App force-stopped");
//
//            // 2. Clear app data
//            Process clearProcess = Runtime.getRuntime().exec(
//                    new String[]{"adb", "-s", "emulator-5554", "shell", "pm", "clear", packageName}
//            );
//            clearProcess.waitFor();
//            System.out.println("✅ App data cleared");
//
//            // 3. Small delay to ensure cleanup is complete
//            Thread.sleep(2000);
//
//        } catch (Exception e) {
//            System.out.println("⚠️ Could not clear app data: " + e.getMessage());
//        }
//    }
//
//    private void verifyAndEnsureLoginPage(AndroidDriver driver) {
//        try {
//            System.out.println("🔍 Ensuring we're on login page...");
//
//            // Wait for app to fully load
//            Thread.sleep(5000);
//
//            // Get current activity
//            String currentActivity = driver.currentActivity();
//            System.out.println("📱 Current Activity: " + currentActivity);
//
//            // If not on MainActivity (login), try to reset
//            if (!currentActivity.contains("MainActivity")) {
//                System.out.println("⚠️ Not on login screen. Attempting to reset...");
//
//                // Press back button to go to login if possible
//                //driver.pressKey(new io.appium.java_client.android.nativekey.AndroidKey(io.appium.java_client.android.nativekey.AndroidKeyCode.BACK));
//                Thread.sleep(2000);
//
//                // Check again
//                currentActivity = driver.currentActivity();
//                System.out.println("📱 Activity after back press: " + currentActivity);
//            }
//
//            if (currentActivity.contains("MainActivity")) {
//                System.out.println("✅ Successfully on login screen");
//            } else {
//                System.out.println("⚠️ May not be on login screen. Current: " + currentActivity);
//            }
//
//        } catch (Exception e) {
//            System.out.println("⚠️ Error verifying login page: " + e.getMessage());
//        }
//    }
//
//    private void closeDriver() {
//        AndroidDriver driver = DriverManager.getDriver();
//        try {
//            System.out.println("🛑 Closing driver session...");
//
//            if (driver != null) {
//                driver.quit();
//                System.out.println("🔚 Driver session closed");
//            }
//
//        } catch (Exception e) {
//            System.out.println("⚠️ Error closing driver: " + e.getMessage());
//        }
//    }
//}










//import com.google.common.collect.ImmutableMap;
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.android.options.UiAutomator2Options;
//import io.cucumber.java.After;
//import io.cucumber.java.Before;
//import stepdefinations.BaseClass;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.time.Duration;
//
//public class AppHooks {
//
//    private final BaseClass baseClass;
//
//    // PicoContainer injects BaseClass
//    public AppHooks(BaseClass baseClass) {
//        this.baseClass = baseClass;
//    }
//
//    @Before(order = 0)
//    public void launchRiderGroupApp() throws MalformedURLException {
//        System.out.println("🚀 Starting Rider Group Tracker App...");
//
//        UiAutomator2Options options = new UiAutomator2Options();
//
//        // ✅ Rider Group Tracker App details
//        options.setAppPackage("com.example.rider_group_tracker");
//        options.setAppActivity("com.example.rider_group_tracker.MainActivity");
//        options.setAutoGrantPermissions(true);
//
//        // ✅ Device configuration
//        // For Emulator:
//        options.setDeviceName("sdk_gphone64_x86_64");
//        options.setUdid("emulator-5554");
//        options.setPlatformVersion("14");
//
//        // For Real device:
//        // options.setDeviceName("realme C3");
//        // options.setUdid("YSBEC689H6W8EM9H");
//        // options.setPlatformName("Android");
//        // options.setPlatformVersion("11");
//        // ✅ CRITICAL: Reset settings to start fresh from login
//        options.setNoReset(false);           // FALSE = Clear app data (starts fresh)
//        options.setFullReset(false);         // FALSE = Don't uninstall app
//        options.setCapability("autoLaunch", true);  // Auto-launch the app
//        // Capabilities
//        options.setIgnoreHiddenApiPolicyError(true);
//        options.setNoReset(true);
//        options.setCapability("newCommandTimeout", 300);
//        options.setCapability("adbExecTimeout", 60000);
//
//        URL url = new URL("http://127.0.0.1:4723/");
//
//        // ✅ Initialize driver
//        baseClass.driver = new AndroidDriver(url, options);
//
//        // ✅ Set implicit wait
//        baseClass.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//
//        // ✅ Initialize WebDriverWait
//        baseClass.wait = new WebDriverWait(baseClass.driver, Duration.ofSeconds(20));
//
//        // ✅ Clear cache for Rider Group Tracker app
//        clearAppCache("com.example.rider_group_tracker");
//
//        System.out.println("✅ App Launched Successfully");
//        System.out.println("Session ID: " + baseClass.driver.getSessionId());
//    }
//
//    @After
//    public void tearDown() {
//        if (baseClass.driver != null) {
//            closeApp();
//        }
//    }
//
//    // ---------- Utility Methods ----------
//    private void clearAppCache(String packageName) {
//        try {
//            baseClass.driver.executeScript(
//                    "mobile: shell",
//                    ImmutableMap.of("command", "pm clear " + packageName)
//            );
//            System.out.println("🧹 Cache cleared for: " + packageName);
//        } catch (Exception e) {
//            System.out.println("⚠️ Cache clear failed: " + e.getMessage());
//        }
//    }
//
//    private void closeApp() {
//        try {
//            baseClass.driver.executeScript(
//                    "mobile: shell",
//                    ImmutableMap.of("command", "am force-stop com.example.rider_group_tracker")
//            );
//            System.out.println("🛑 Application force-stopped");
//        } catch (Exception e) {
//            System.out.println("⚠️ Error stopping app: " + e.getMessage());
//        } finally {
//            if (baseClass.driver != null) {
//                baseClass.driver.quit();
//                System.out.println("🔚 Driver session closed");
//            }
//        }
//    }
//}