//package hooks;
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.android.options.UiAutomator2Options;
//import io.cucumber.java.After;
//import io.cucumber.java.Before;
//import io.cucumber.java.Scenario;
//import stepdefinations.BaseClass;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.time.Duration;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.io.File;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
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
//        // Add this to your driver capabilities
//        options.setCapability("allowInvisibleElements", true);
//        options.setCapability("adbExecTimeout", 60000);
//// Enable insecure features
//        options.setCapability("allowInsecure", "adb_shell");
//        options.setCapability("ensureWebviewsHavePages", true);
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
//        baseClass.driver = new AndroidDriver(url, options);
//        System.out.println("✅ Driver created successfully");
//
//        // ✅ STEP 3: Set implicit wait
//        baseClass.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//
//        // ✅ STEP 4: Initialize WebDriverWait
//        baseClass.wait = new WebDriverWait(baseClass.driver, Duration.ofSeconds(20));
//        System.out.println("✅ WebDriverWait created successfully");
//
//        // ✅ STEP 5: Wait for app to load and verify it's on login page
//        verifyAndEnsureLoginPage(baseClass.driver);
//
//        System.out.println("✅ App Launched Successfully from Login Page");
//        System.out.println("📱 Session ID: " + baseClass.driver.getSessionId());
//        System.out.println("📱 Current Activity: " + baseClass.driver.currentActivity());
//        System.out.println("══════════════════════════════════════════════════");
//    }
//
//    @After
//    public void tearDown(Scenario scenario) {
//        System.out.println("══════════════════════════════════════════════════");
//        System.out.println("🔚 CLEANING UP AFTER TEST - Scenario: " + scenario.getName());
//        System.out.println("══════════════════════════════════════════════════");
//
//        // Capture screenshot if scenario failed
//        captureScreenshotOnFailure(scenario);
//
//        if (baseClass.driver != null) {
//            forceStopAndClearAppData("com.example.rider_group_tracker");
//            closeDriver();
//        }
//
//        System.out.println("🔚 Scenario Status: " + (scenario.isFailed() ? "FAILED ❌" : "PASSED ✅"));
//        System.out.println("══════════════════════════════════════════════════");
//    }
//
//    // ---------- Utility Methods ----------
//
//    /**
//     * Capture screenshot when scenario fails
//     */
//    private void captureScreenshotOnFailure(Scenario scenario) {
//        if (scenario.isFailed() && baseClass.driver != null) {
//            try {
//                System.out.println("📸 Capturing screenshot for failed scenario: " + scenario.getName());
//
//                // 1. Take screenshot as bytes for Cucumber report attachment
//                byte[] screenshotBytes = ((TakesScreenshot) baseClass.driver).getScreenshotAs(OutputType.BYTES);
//
//                //added now
//                scenario.attach(screenshotBytes, "image/png", "Failed Step Screenshot");
//
//                // 2. Attach to Cucumber HTML report
//                scenario.attach(screenshotBytes, "image/png", "Failure Screenshot");
//
//                // 3. Save screenshot to file system (optional but useful)
//                saveScreenshotToFile(scenario.getName(), screenshotBytes);
//
//                // 4. Also capture page source for debugging
//                capturePageSource(scenario.getName());
//
//                System.out.println("✅ Screenshot captured successfully");
//
//            } catch (Exception e) {
//                System.out.println("⚠️ Failed to capture screenshot: " + e.getMessage());
//            }
//        } else if (baseClass.driver == null) {
//            System.out.println("⚠️ Driver is null, cannot capture screenshot");
//        } else {
//            System.out.println("✅ Scenario passed, no screenshot needed");
//        }
//    }
//
//    /**
//     * Save screenshot to file system with timestamp
//     */
//    private void saveScreenshotToFile(String scenarioName, byte[] screenshotBytes) {
//        try {
//            // Create directory if it doesn't exist
//            String screenshotsDir = "test-output1/screenshots/";
//            Path dirPath = Paths.get(screenshotsDir);
//            if (!Files.exists(dirPath)) {
//                Files.createDirectories(dirPath);
//            }
//
//            // Create filename with timestamp
//            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//            String safeScenarioName = scenarioName.replaceAll("[^a-zA-Z0-9.-]", "_");
//            String fileName = screenshotsDir + "FAILED_" + safeScenarioName + "_" + timestamp + ".png";
//
//            // Save the file
//            Files.write(Paths.get(fileName), screenshotBytes);
//            System.out.println("💾 Screenshot saved to: " + fileName);
//
//        } catch (Exception e) {
//            System.out.println("⚠️ Could not save screenshot to file: " + e.getMessage());
//        }
//    }
//
//    /**
//     * Capture page source for debugging
//     */
//    private void capturePageSource(String scenarioName) {
//        try {
//            String pageSource = baseClass.driver.getPageSource();
//            String safeScenarioName = scenarioName.replaceAll("[^a-zA-Z0-9.-]", "_");
//            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//
//            // Save page source to file
//            String fileName = "test-output/page_source/FAILED_" + safeScenarioName + "_" + timestamp + ".xml";
//            Path dirPath = Paths.get("test-output/page_source/");
//            if (!Files.exists(dirPath)) {
//                Files.createDirectories(dirPath);
//            }
//
//            Files.write(Paths.get(fileName), pageSource.getBytes());
//            System.out.println("📄 Page source saved to: " + fileName);
//
//        } catch (Exception e) {
//            System.out.println("⚠️ Could not capture page source: " + e.getMessage());
//        }
//    }
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
//            // Fallback: Try using driver if available
//            if (baseClass.driver != null) {
//                try {
//                    baseClass.driver.executeScript("mobile: shell",
//                            java.util.Map.of("command", "pm clear " + packageName));
//                    System.out.println("✅ App data cleared using driver fallback");
//                } catch (Exception ex) {
//                    System.out.println("⚠️ Fallback clear also failed: " + ex.getMessage());
//                }
//            }
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
//                // Try pressing back button
//                driver.navigate().back();
//                Thread.sleep(2000);
//
//                // Check again
//                currentActivity = driver.currentActivity();
//                System.out.println("📱 Activity after check: " + currentActivity);
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
//        try {
//            System.out.println("🛑 Closing driver session...");
//
//            if (baseClass.driver != null) {
//                // Optional: Clear cache using driver before quitting
//                try {
//                    baseClass.driver.executeScript("mobile: shell",
//                            java.util.Map.of("command", "pm clear com.example.rider_group_tracker"));
//                    System.out.println("✅ Cache cleared before quitting");
//                } catch (Exception e) {
//                    System.out.println("⚠️ Could not clear cache: " + e.getMessage());
//                }
//
//                baseClass.driver.quit();
//                System.out.println("🔚 Driver session closed");
//            }
//
//        } catch (Exception e) {
//            System.out.println("⚠️ Error closing driver: " + e.getMessage());
//        }
//    }
//
//    // Original clearAppCache method (kept for compatibility if needed elsewhere)
//    private void clearAppCache(String packageName) {
//        forceStopAndClearAppData(packageName);
//    }
//
//    // Original closeApp method (kept for compatibility if needed elsewhere)
//    private void closeApp() {
//        if (baseClass.driver != null) {
//            forceStopAndClearAppData("com.example.rider_group_tracker");
//            closeDriver();
//        }
//    }
//}


//// ************* For The Docker Images  ***********************************************
//package hooks;
//
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.android.options.UiAutomator2Options;
//import io.cucumber.java.After;
//import io.cucumber.java.Before;
//import io.cucumber.java.Scenario;
//import stepdefinations.BaseClass;
//import org.openqa.selenium.OutputType;
//import org.openqa.selenium.TakesScreenshot;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.time.Duration;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.io.File;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//public class AppHooks {
//
//    private final BaseClass baseClass;
//
//    // Docker configuration
//    private static final String APPIUM_DOCKER_URL = "http://localhost:4723/";  // Change to Docker IP if needed
//    private static final boolean USE_DOCKER = true;  // Set to false for local Appium
//
//    public AppHooks(BaseClass baseClass) {
//        this.baseClass = baseClass;
//    }
//
//    @Before(order = 0)
//    public void launchRiderGroupApp() throws MalformedURLException {
//        System.out.println("══════════════════════════════════════════════════");
//        System.out.println("🚀 STARTING RIDER GROUP TRACKER APP (Docker: " + USE_DOCKER + ")");
//        System.out.println("══════════════════════════════════════════════════");
//
//        UiAutomator2Options options = new UiAutomator2Options();
//
//        // ✅ Rider Group Tracker App details
//        options.setAppPackage("com.example.rider_group_tracker");
//        options.setAppActivity("com.example.rider_group_tracker.MainActivity");
//        options.setAutoGrantPermissions(true);
//
//        // ✅ Device configuration - IMPORTANT: Use Docker container's emulator
//        if (USE_DOCKER) {
//            // For Dockerized emulator
//            options.setDeviceName("sdk_gphone64_x86_64");  // Docker container name
//            options.setUdid("emulator-5554");
//            options.setPlatformVersion("14");// Match your Docker image version
//
//
//        } else {
//            // For local emulator
//            options.setDeviceName("sdk_gphone64_x86_64");
//            options.setUdid("emulator-5554");
//            options.setPlatformVersion("14");
//        }
//
//        // ✅ Reset settings to start fresh from login
//        options.setNoReset(false);
//        options.setFullReset(false);
//        options.setCapability("autoLaunch", true);
//        options.setCapability("allowInvisibleElements", true);
//        options.setCapability("adbExecTimeout", 120000);
//
//        // For Docker, you might need different adb connection
//        if (USE_DOCKER) {
//            options.setCapability("remoteAdbHost", "android-emulator");  // Docker service name
//            options.setCapability("adbPort", 5555);
//        }
//
//        options.setCapability("allowInsecure", "adb_shell");
//        options.setCapability("ensureWebviewsHavePages", true);
//
//        // Timeout configurations
//        options.setAvdLaunchTimeout(Duration.ofSeconds(180));
//        options.setAvdReadyTimeout(Duration.ofSeconds(180));
//        options.setUiautomator2ServerLaunchTimeout(Duration.ofSeconds(180));
//        options.setIgnoreHiddenApiPolicyError(true);
//        options.setCapability("newCommandTimeout", 300);
//
//        // ✅ Connect to Appium - Docker or local
//        URL url;
//        if (USE_DOCKER) {
//            url = new URL(APPIUM_DOCKER_URL);
//            System.out.println("🌐 Connecting to Docker Appium at: " + APPIUM_DOCKER_URL);
//        } else {
//            url = new URL("http://127.0.0.1:4723/");
//            System.out.println("🌐 Connecting to Local Appium at: http://127.0.0.1:4723/");
//        }
//
//        // ✅ Clean app data
//        if (!USE_DOCKER) {
//            // Only clear data for local emulator
//            forceStopAndClearAppData("com.example.rider_group_tracker");
//        }
//
//        // ✅ Initialize driver
//        baseClass.driver = new AndroidDriver(url, options);
//        System.out.println("✅ Driver created successfully");
//        System.out.println("📱 Session ID: " + baseClass.driver.getSessionId());
//
//        // ✅ Set waits
//        baseClass.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        baseClass.wait = new WebDriverWait(baseClass.driver, Duration.ofSeconds(20));
//        System.out.println("✅ WebDriverWait created successfully");
//
//        // ✅ Wait for app to load
//        waitForAppToLoad();
//
//        System.out.println("✅ App Launched Successfully");
//        System.out.println("📱 Current Activity: " + baseClass.driver.currentActivity());
//        System.out.println("══════════════════════════════════════════════════");
//    }
//
//    @After
//    public void tearDown(Scenario scenario) {
//        System.out.println("══════════════════════════════════════════════════");
//        System.out.println("🔚 CLEANING UP AFTER TEST - Scenario: " + scenario.getName());
//        System.out.println("══════════════════════════════════════════════════");
//
//        captureScreenshotOnFailure(scenario);
//
//        if (baseClass.driver != null) {
//            if (!USE_DOCKER) {
//                // Only clean app data for local emulator
//                forceStopAndClearAppData("com.example.rider_group_tracker");
//            }
//            closeDriver();
//        }
//
//        System.out.println("🔚 Scenario Status: " + (scenario.isFailed() ? "FAILED ❌" : "PASSED ✅"));
//        System.out.println("══════════════════════════════════════════════════");
//    }
//
//    // ---------- Utility Methods ----------
//
//    private void waitForAppToLoad() {
//        try {
//            System.out.println("⏳ Waiting for app to load...");
//            Thread.sleep(5000);
//
//            // Check if app is loaded
//            String currentActivity = baseClass.driver.currentActivity();
//            System.out.println("📱 Initial Activity: " + currentActivity);
//
//            // Additional wait for specific elements if needed
//            int attempts = 0;
//            while (attempts < 5 && (currentActivity == null || currentActivity.isEmpty())) {
//                Thread.sleep(1000);
//                currentActivity = baseClass.driver.currentActivity();
//                attempts++;
//                System.out.println("Attempt " + attempts + ": Activity = " + currentActivity);
//            }
//
//            if (currentActivity != null && currentActivity.contains("MainActivity")) {
//                System.out.println("✅ App loaded successfully on login screen");
//            } else {
//                System.out.println("⚠️ App loaded, but activity is: " + currentActivity);
//            }
//
//        } catch (Exception e) {
//            System.out.println("⚠️ Error waiting for app to load: " + e.getMessage());
//        }
//    }
//
//    private void captureScreenshotOnFailure(Scenario scenario) {
//        if (scenario.isFailed() && baseClass.driver != null) {
//            try {
//                System.out.println("📸 Capturing screenshot for failed scenario: " + scenario.getName());
//
//                byte[] screenshotBytes = ((TakesScreenshot) baseClass.driver).getScreenshotAs(OutputType.BYTES);
//                scenario.attach(screenshotBytes, "image/png", "Failed Step Screenshot");
//
//                saveScreenshotToFile(scenario.getName(), screenshotBytes);
//                capturePageSource(scenario.getName());
//
//                System.out.println("✅ Screenshot captured successfully");
//
//            } catch (Exception e) {
//                System.out.println("⚠️ Failed to capture screenshot: " + e.getMessage());
//            }
//        }
//    }
//
//    private void saveScreenshotToFile(String scenarioName, byte[] screenshotBytes) {
//        try {
//            String screenshotsDir = "test-output/screenshots/";
//            Path dirPath = Paths.get(screenshotsDir);
//            if (!Files.exists(dirPath)) {
//                Files.createDirectories(dirPath);
//            }
//
//            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//            String safeScenarioName = scenarioName.replaceAll("[^a-zA-Z0-9.-]", "_");
//            String fileName = screenshotsDir + "FAILED_" + safeScenarioName + "_" + timestamp + ".png";
//
//            Files.write(Paths.get(fileName), screenshotBytes);
//            System.out.println("💾 Screenshot saved to: " + fileName);
//
//        } catch (Exception e) {
//            System.out.println("⚠️ Could not save screenshot to file: " + e.getMessage());
//        }
//    }
//
//    private void capturePageSource(String scenarioName) {
//        try {
//            String pageSource = baseClass.driver.getPageSource();
//            String safeScenarioName = scenarioName.replaceAll("[^a-zA-Z0-9.-]", "_");
//            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//
//            String fileName = "test-output/page_source/FAILED_" + safeScenarioName + "_" + timestamp + ".xml";
//            Path dirPath = Paths.get("test-output/page_source/");
//            if (!Files.exists(dirPath)) {
//                Files.createDirectories(dirPath);
//            }
//
//            Files.write(Paths.get(fileName), pageSource.getBytes());
//            System.out.println("📄 Page source saved to: " + fileName);
//
//        } catch (Exception e) {
//            System.out.println("⚠️ Could not capture page source: " + e.getMessage());
//        }
//    }
//
//    private void forceStopAndClearAppData(String packageName) {
//        if (USE_DOCKER) {
//            System.out.println("⚠️ Skipping app data clear for Docker (handled by container)");
//            return;
//        }
//
//        try {
//            System.out.println("🧹 Force stopping and clearing app data for: " + packageName);
//
//            String[] adbCommands = {
//                    "adb", "-s", "emulator-5554", "shell", "am", "force-stop", packageName,
//                    "adb", "-s", "emulator-5554", "shell", "pm", "clear", packageName
//            };
//
//            for (String command : adbCommands) {
//                Process process = Runtime.getRuntime().exec(command.split(" "));
//                process.waitFor();
//            }
//
//            System.out.println("✅ App data cleared");
//            Thread.sleep(2000);
//
//        } catch (Exception e) {
//            System.out.println("⚠️ Could not clear app data: " + e.getMessage());
//        }
//    }
//
//    private void closeDriver() {
//        try {
//            System.out.println("🛑 Closing driver session...");
//
//            if (baseClass.driver != null) {
//                baseClass.driver.quit();
//                System.out.println("🔚 Driver session closed");
//            }
//
//        } catch (Exception e) {
//            System.out.println("⚠️ Error closing driver: " + e.getMessage());
//        }
//    }
//}

// *************  Trial 2 For The Docker Images  ***********************************************
package hooks;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import stepdefinations.BaseClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AppHooks {

    private final BaseClass baseClass;

    // Docker configuration
    private static final String APPIUM_DOCKER_URL = "http://localhost:4723/";
    private static final boolean USE_DOCKER = true;

    public AppHooks(BaseClass baseClass) {
        this.baseClass = baseClass;
    }

    @Before(order = 0)
    public void launchRiderGroupApp() throws MalformedURLException {
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("🚀 STARTING RIDER GROUP TRACKER APP (Docker: " + USE_DOCKER + ")");
        System.out.println("══════════════════════════════════════════════════");

        UiAutomator2Options options = new UiAutomator2Options();

        // ✅ Rider Group Tracker App details
        options.setAppPackage("com.example.rider_group_tracker");
        options.setAppActivity("com.example.rider_group_tracker.MainActivity");
        options.setAutoGrantPermissions(true);

        // ✅ Device configuration - SIMPLIFIED
        options.setDeviceName("sdk_gphone64_x86_64");
        options.setUdid("emulator-5554");
        options.setPlatformVersion("14");

        // ✅ Reset settings
        options.setNoReset(false);
        options.setFullReset(false);
        options.setCapability("autoLaunch", true);

        // ✅ Timeout configurations - SIMPLIFIED
        options.setAvdLaunchTimeout(Duration.ofSeconds(60));  // Reduced from 180
        options.setAvdReadyTimeout(Duration.ofSeconds(60));   // Reduced from 180
        options.setCapability("newCommandTimeout", 120);      // Reduced from 300

        // ✅ REMOVED: All problematic Docker-specific settings
        // DO NOT ADD: remoteAdbHost, adbPort, allowInsecure, etc.

        // ✅ Connect to Appium
        URL url = new URL(APPIUM_DOCKER_URL);
        System.out.println("🌐 Connecting to Docker Appium at: " + APPIUM_DOCKER_URL);

        // ✅ Initialize driver
        baseClass.driver = new AndroidDriver(url, options);
        System.out.println("✅ Driver created successfully");
        System.out.println("📱 Session ID: " + baseClass.driver.getSessionId());

        // ✅ Set waits
        baseClass.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        baseClass.wait = new WebDriverWait(baseClass.driver, Duration.ofSeconds(20));
        System.out.println("✅ WebDriverWait created successfully");

        // ✅ Wait for app to load
        waitForAppToLoad();

        System.out.println("✅ App Launched Successfully");
        System.out.println("📱 Current Activity: " + baseClass.driver.currentActivity());
        System.out.println("══════════════════════════════════════════════════");
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println("══════════════════════════════════════════════════");
        System.out.println("🔚 CLEANING UP AFTER TEST - Scenario: " + scenario.getName());
        System.out.println("══════════════════════════════════════════════════");

        captureScreenshotOnFailure(scenario);

        if (baseClass.driver != null) {
            closeDriver();
        }

        System.out.println("🔚 Scenario Status: " + (scenario.isFailed() ? "FAILED ❌" : "PASSED ✅"));
        System.out.println("══════════════════════════════════════════════════");
    }

    // ---------- Utility Methods ----------

    private void waitForAppToLoad() {
        try {
            System.out.println("⏳ Waiting for app to load...");
            Thread.sleep(5000);

            String currentActivity = baseClass.driver.currentActivity();
            System.out.println("📱 Initial Activity: " + currentActivity);

            int attempts = 0;
            while (attempts < 5 && (currentActivity == null || currentActivity.isEmpty())) {
                Thread.sleep(1000);
                currentActivity = baseClass.driver.currentActivity();
                attempts++;
                System.out.println("Attempt " + attempts + ": Activity = " + currentActivity);
            }

            if (currentActivity != null && currentActivity.contains("MainActivity")) {
                System.out.println("✅ App loaded successfully on login screen");
            } else {
                System.out.println("⚠️ App loaded, but activity is: " + currentActivity);
            }

        } catch (Exception e) {
            System.out.println("⚠️ Error waiting for app to load: " + e.getMessage());
        }
    }

    private void captureScreenshotOnFailure(Scenario scenario) {
        if (scenario.isFailed() && baseClass.driver != null) {
            try {
                System.out.println("📸 Capturing screenshot for failed scenario: " + scenario.getName());
                byte[] screenshotBytes = ((TakesScreenshot) baseClass.driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshotBytes, "image/png", "Failed Step Screenshot");
                System.out.println("✅ Screenshot captured successfully");
            } catch (Exception e) {
                System.out.println("⚠️ Failed to capture screenshot: " + e.getMessage());
            }
        }
    }

    private void closeDriver() {
        try {
            System.out.println("🛑 Closing driver session...");
            if (baseClass.driver != null) {
                baseClass.driver.quit();
                System.out.println("🔚 Driver session closed");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Error closing driver: " + e.getMessage());
        }
    }
}

