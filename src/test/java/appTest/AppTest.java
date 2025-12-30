package appTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppTest {

    public void navigateToLoginPage(AndroidDriver driver, WebDriverWait wait) throws InterruptedException {
        try {
            System.out.println("Starting navigation to login page...");
            System.out.println("Driver: " + (driver != null ? "Initialized" : "NULL"));
            System.out.println("Wait: " + (wait != null ? "Initialized" : "NULL"));

            // CRITICAL: Check if wait is null - if it is, DON'T create a new one with potentially null driver
            if (wait == null) {
                throw new RuntimeException("WebDriverWait is null! Cannot proceed.");
            }

            // REMOVE THIS LINE - it's causing the error:
            // wait = new WebDriverWait(driver, Duration.ofSeconds(60)); // DELETE THIS LINE

            // First, check if we're already on a login screen or need to navigate
            Thread.sleep(3000); // Small delay to let app load

            // Try to find the email field
            By txtEmail = AppiumBy.xpath(
                    "//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[3]/android.view.View/android.widget.EditText[1]"
            );

            System.out.println("Looking for email field...");

            WebElement email = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(txtEmail)
            );

            System.out.println("Email field found!");

            email.click();
            email.clear();
            email.sendKeys("srinivasg457@gmail.com");
            driver.hideKeyboard();

            // Find password field
            WebElement txtPassword = driver.findElement(
                    AppiumBy.androidUIAutomator(
                            "new UiScrollable(new UiSelector().scrollable(true))" +
                                    ".scrollIntoView(new UiSelector().className(\"android.widget.EditText\").instance(1))"
                    )
            );

            WebElement password = wait.until(
                    ExpectedConditions.visibilityOf(txtPassword)
            );

            password.click();
            password.clear();
            password.sendKeys("Shree@1234");

            By btnLogin = AppiumBy.xpath("//android.widget.Button[@content-desc='LOGIN']");

            WebElement loginButton = wait.until(
                    ExpectedConditions.elementToBeClickable(btnLogin)
            );

            if(loginButton.isDisplayed() && loginButton.isEnabled()){
                loginButton.click();
                Thread.sleep(10000);
                System.out.println("Login button clicked!");
            }
            else{
                System.out.println("Login button not clickable!");
                Assert.assertTrue(false);
            }

        } catch (Exception e) {
            System.out.println("❌ Error navigating to login: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}

//import com.google.common.collect.ImmutableMap;
//import io.appium.java_client.AppiumBy;
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.android.options.UiAutomator2Options;
//import org.junit.Assert;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.interactions.PointerInput;
//import org.openqa.selenium.interactions.Sequence;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.time.Duration;
//import java.util.*;
//
//import io.appium.java_client.android.nativekey.KeyEvent;
//import io.appium.java_client.android.nativekey.AndroidKey;
//import stepdefinations.BaseClass;
//
//
//public class AppTest {
//    //static AppiumDriver driver;
//    static AndroidDriver driver;
//    // static AndroidDriver<MobileElement> driver;
//
//    public static void main(String[] args) throws MalformedURLException, InterruptedException {
//        //openmobileapp();
//        System.out.println("Application Launched Successfulyy");
//    }
//    protected final BaseClass baseClass;
//
//    public AppTest() {
//        this.baseClass = baseClass;
//    }
//    //for travel app
////    public static void ridergroup() throws MalformedURLException, InterruptedException {
////        UiAutomator2Options options = new UiAutomator2Options();
////        options.setAppPackage("com.example.rider_group_tracker");
////        options.setAppActivity("com.example.rider_group_tracker.MainActivity");
////        options.setAutoGrantPermissions(true);
////        options.setAutoGrantPermissions(true);
////        options.setCapability("locationServicesEnabled", true);
////        options.setCapability("locationServicesAuthorized", true);
////        options.setCapability("gpsEnabled", true);
//////        //for the real device
////        options.setDeviceName("realme C3");
////        options.setUdid("YSBEC689H6W8EM9H");
////        options.setPlatformName("Android");
////        options.setPlatformVersion("11");
////        options.setIgnoreHiddenApiPolicyError(true);
////        options.setNoReset(true);
////        options.setCapability("newCommandtimeout", 300);
////// Set adbExecTimeout in milliseconds (60 seconds here)
////        options.setCapability("adbExecTimeout", 60000);
////        URL url = new URL("http://127.0.0.1:4723/");
////        // Clear app cache before launching
////        clearAppCache("com.example.rider_group_tracker");
////
////        // driver = new AndroidDriver(url, options);
////        driver = new AndroidDriver(url, options);
////
////        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
////
////        By routesTab = AppiumBy.xpath(
////                "//android.widget.Button[contains(@content-desc,'Routes')]"
////        );
////
////        WebElement routes = wait.until(
////                ExpectedConditions.visibilityOfElementLocated(routesTab)
////        );
////
////        routes.click();
////
////        //Click The Record Button
////
////        By recordBtn = AppiumBy.xpath(
////                "        //android.widget.FrameLayout[@resource-id=\'android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.widget.Button[2]"
////        );
////
////        WebElement record = wait.until(
////                ExpectedConditions.visibilityOfElementLocated(recordBtn)
////        );
////        record.click();
////
////        By startCnfbtn = AppiumBy.xpath(
////                "//android.widget.Button[@content-desc='Start\']"
////        );
////
////        WebElement start = wait.until(
////                ExpectedConditions.visibilityOfElementLocated(startCnfbtn)
////        );
////        start.click();
////
////        // Status element XPath
//////        String statusXPath = "//android.view.View[@content-desc=\'0 km/h Distance 0.02 km Duration 00:37 Max Speed 0.0 km/h Live RECORDING\']";
//////
//////        // Coordinates
//////        double latitude = 12.9716;
//////        double longitude = 77.5046;
//////
//////// Movement simulation
//////        for (int i = 0; i < 100; i++) {
//////            // Calculate new coordinates with incremental movement
//////            double newLatitude = latitude + (i * 0.0001);  // Small incremental change
//////            double newLongitude = longitude + (i * 0.0001);
//////
//////            // Alternative 1: Using Map (for newer Appium versions)
//////            Map<String, Object> args = new HashMap<>();
//////            args.put("latitude", newLatitude);
//////            args.put("longitude", newLongitude);
//////            args.put("altitude", 0.0);
//////
//////            try {
//////                driver.executeScript("mobile: setGeoLocation", args);
//////                System.out.println("Set location to: Lat=" + newLatitude + ", Long=" + newLongitude);
//////            } catch (Exception e) {
//////                // If mobile: setGeoLocation fails, try alternative
//////                System.out.println("mobile: setGeoLocation failed, trying alternative...");
//////
//////                // Alternative 2: Cast to AndroidDriver
//////                if (driver instanceof AndroidDriver) {
//////                    ((AndroidDriver) driver).setLocation(newLatitude, newLongitude, 0.0);
//////                    System.out.println("Used AndroidDriver.setLocation()");
//////                }
//////            }
//////
//////            // Check status every 10 iterations
//////            if (i % 10 == 0) {
//////                try {
//////                    WebElement statusElement = driver.findElement(By.xpath(statusXPath));
//////                    String status = statusElement.getAttribute("content-desc");
//////                    System.out.println("Iteration " + i + " - Status: " + status);
//////                } catch (Exception e) {
//////                    System.out.println("Could not find status element at iteration " + i);
//////                }
//////            }
//////
//////            Thread.sleep(1000);
//////        }
////    }
//
//    //Login to the app
////    public static void ridergroupEmulatorLaunch() throws MalformedURLException {
////        UiAutomator2Options options = new UiAutomator2Options();
////        options.setAppPackage("com.example.rider_group_tracker");
////        options.setAppActivity("com.example.rider_group_tracker.MainActivity");
////        options.setAutoGrantPermissions(true);
////        options.setAutoGrantPermissions(true);
////        options.setCapability("locationServicesEnabled", true);
////        options.setCapability("locationServicesAuthorized", true);
////        options.setCapability("gpsEnabled", true);
//////        //for the real device
////        options.setDeviceName("sdk_gphone64_x86_64");
////        options.setUdid("emulator-5554");
////        options.setPlatformName("Android");
////        options.setPlatformVersion("14");
////        options.setIgnoreHiddenApiPolicyError(true);
////        options.setNoReset(true);
////        options.setCapability("newCommandtimeout", 300);
////// Set adbExecTimeout in milliseconds (60 seconds here)
////        options.setCapability("adbExecTimeout", 60000);
////        URL url = new URL("http://127.0.0.1:4723/");
////        // Clear app cache before launching
////        clearAppCache("com.example.rider_group_tracker");
////
////        // driver = new AndroidDriver(url, options);
////        driver = new AndroidDriver(url, options);
////
////        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
////
////        By routesTab = AppiumBy.xpath(
////                "//android.widget.Button[contains(@content-desc,'Routes')]"
////        );
////    }
  //  public  void ridergroupEmulatorLoginPage(){
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
//
//        By txtEmail = AppiumBy.xpath(
//                "//android.widget.FrameLayout[@resource-id=\'android:id/content\']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[3]/android.view.View/android.widget.EditText[1]"
//        );
//
//        WebElement email = wait.until(
//                ExpectedConditions.visibilityOfElementLocated(txtEmail)
//        );
//
//        email.click();
//        email.clear();
//        email.sendKeys("srinivasg457@gmail.com");
//        driver.hideKeyboard();
//        WebElement txtPassword = driver.findElement(
//                AppiumBy.androidUIAutomator(
//                        "new UiScrollable(new UiSelector().scrollable(true))" +
//                                ".scrollIntoView(new UiSelector().className(\"android.widget.EditText\").instance(1))"
//                )
//        );
//
//        WebElement password = wait.until(
//                ExpectedConditions.visibilityOf(txtPassword)
//        );
//
//        password.click();
//        password.clear();
//        password.sendKeys("Shree@1234");
//
//        By btnLogin = AppiumBy.xpath("//android.widget.Button[@content-desc='LOGIN']");
//
//        WebElement loginButton = wait.until(
//                ExpectedConditions.elementToBeClickable(btnLogin)
//        );
//
//        if(loginButton.isDisplayed() && loginButton.isEnabled()){
//            loginButton.click();
//        }
//        else{
//            Assert.assertTrue(false);
//        }




//    }
//
//
//
//
////    private static void parseStatus(String status, int i) {
////        // Example parsing logic for the status string
////        // Format: "0 km/h Distance 0.02 km Duration 00:37 Max Speed 0.0 km/h Live RECORDING"
////
////        if (status.contains("km/h")) {
////            String[] parts = status.split(" ");
////            if (parts.length >= 9) {
////                String currentSpeed = parts[0] + " " + parts[1]; // "0 km/h"
////                String distance = parts[3] + " " + parts[4];    // "0.02 km"
////                String duration = parts[6];                      // "00:37"
////                String maxSpeed = parts[8] + " " + parts[9];    // "0.0 km/h"
////
////                System.out.println("Parsed - Speed: " + currentSpeed +
////                        ", Distance: " + distance +
////                        ", Duration: " + duration +
////                        ", Max Speed: " + maxSpeed);
////            }
////        }
////    }
//
//
//
//
//
//
//    public static void openmobileapp() throws MalformedURLException, InterruptedException {
//
//        UiAutomator2Options options = new UiAutomator2Options();
//
////         options.setAppPackage("org.simple.clinic.staging");
////        options.setAppActivity("org.simple.clinic.setup.SetupActivity");
//
//        //org.simple.clinic.main.SetupActivity
//        options.setAppPackage("com.chataak.app");
//        options.setAppActivity("com.chataak.app.MainActivity");
//
//        options.setAutoGrantPermissions(true);
////        options.setDeviceName("sdk_gphone64_x86_64");
////        options.setUdid("emulator-5554");
////        options.setPlatformName("Android");
////        options.setPlatformVersion("14");
//
//
////        //for the real device
//        options.setDeviceName("realme C3");
//        options.setUdid("YSBEC689H6W8EM9H");
//        options.setPlatformName("Android");
//        options.setPlatformVersion("11");
//        options.setIgnoreHiddenApiPolicyError(true);
//        //options.setNoReset(true);
//        options.setNoReset(true);
//        //capabilities.setCapability("newCommandTimeout", 300);
//        options.setCapability("newCommandtimeout", 300);
//
//
//// Set adbExecTimeout in milliseconds (60 seconds here)
//        options.setCapability("adbExecTimeout", 60000);
//        URL url = new URL("http://127.0.0.1:4723/");
//
//
//        // Clear app cache before launching
//        clearAppCache("com.chataak.app");
//
//        // driver = new AndroidDriver(url, options);
//        driver = new AndroidDriver(url, options);
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//
//        // Wait for first image/button and click (try only once, not three times)
//        WebElement firstActionButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//android.widget.ImageView[2]")));
//        firstActionButton.click();
//
//
//        // Wait for first image/button and click (try only once, not three times)
//        WebElement secondActionButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//android.widget.ImageView[2]")));
//        secondActionButton.click();
//
//        // Wait for first image/button and click (try only once, not three times)
//        WebElement thirdActionButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//android.widget.ImageView[2]")));
//        thirdActionButton.click();
//
//// Fetch the URL once navigation is complete
////        String currentUrl = driver.getCurrentUrl();
////        System.out.println(currentUrl);
////        assert currentUrl.equals("https://expected-url.com");
//        // Wait and enter mobile number
//        WebElement mobileInputField = wait.until(ExpectedConditions.presenceOfElementLocated(
//                By.xpath("//android.widget.ImageView"))); // Adjust if this is incorrect
//        mobileInputField.click();  // If necessary
//        mobileInputField.sendKeys("8105914136");
//
//        // Click on "Continue"
//        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//android.widget.Button[@content-desc='Continue']")));
//        continueButton.click();
//
//
//        Thread.sleep(5000);
//
//        WebElement otpPage = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
//                "//android.widget.EditText")));
//
//        if (otpPage.isDisplayed()) {
//            otpPage.click();
//        } else {
//            System.out.println("Element is Not Present");
//        }
//
//        // 141,741
//
//        // 1️⃣ Tap using coordinates (replace x and y with actual values)
////        int x = 205;  // your x-coordinate
////        int y = 1090;  // your y-coordinate
//
//        int x = 141;  // your x-coordinate
//        int y = 741;  // your y-coordinate
//
//        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
//        Sequence tap = new Sequence(finger, 1);
//        tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), x, y));
//        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
//        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
//
//        driver.perform(Arrays.asList(tap));
////
////        Thread.sleep(3000); // wait for UI transition
//
//// Cast driver to AndroidDriver
////        AndroidDriver driver = (AndroidDriver) this.driver;
//
//
//// Input 1 2 3 4 5 using AndroidKey
//        driver.pressKey(new KeyEvent(AndroidKey.DIGIT_1));
//        driver.pressKey(new KeyEvent(AndroidKey.DIGIT_2));
//        driver.pressKey(new KeyEvent(AndroidKey.DIGIT_3));
//        driver.pressKey(new KeyEvent(AndroidKey.DIGIT_4));
//        driver.pressKey(new KeyEvent(AndroidKey.DIGIT_5));
//
//
//// Click on "verify Button"
//        WebElement verifyButton = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//android.widget.Button[@content-desc=\'Verify\']")));
//        verifyButton.click();
//
//
//        WebElement showStores = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.Button[@content-desc=\'Show Stores\']")));
//        showStores.click();
//
//        WebElement searchStoreTextbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.EditText")));
//        searchStoreTextbox.click();
//        searchStoreTextbox.click();
//        searchStoreTextbox.sendKeys("pre");
//
//        WebElement PreranaStoreBallary = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.view.View[@content-desc=\'Prerana motors ballari (Bellary)\']")));
//        PreranaStoreBallary.click();
//
//
//        WebElement scanner = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("(//android.widget.ImageView)[last()-1]")));
//        scanner.click();
//
//        Thread.sleep(4000);
//    WebElement increase= wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.FrameLayout[@resource-id=\"android:id/content\"]/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View[2]/android.view.View[12]")));
////        WebElement increase = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.ScrollView/android.view.View[12]")));
//
//        for (int i = 1; i < 20; i++) { // starting from 1, click 19 times
//            increase.click();
//            Thread.sleep(300); // optional: small delay between clicks to avoid app lag
//        }
//
//        WebElement addtocartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.view.View[@content-desc=\'Add to Cart\']")));
//        addtocartButton.click();
//
//        WebElement buynowButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.view.View[@content-desc=\'Buy Now\']")));
//        buynowButton.click();
////
////        WebElement singleRadioButton = wait
////                .until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.RadioButton[1]")));
////        singleRadioButton.click();
//
//        // Wait for the radio buttons to be present
//        List<WebElement> radioButtons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
//                By.xpath("//android.widget.RadioButton"))
//        );
//
//// Debug: Print the size of the list to see if it's empty
//        System.out.println("Radio buttons found: " + radioButtons.size());
//
//        if (!radioButtons.isEmpty()) {
//            // Generate a random index between 0 and size-1
//            int randomIndex = new Random().nextInt(radioButtons.size());
//
//            // Click the randomly selected radio button
//            radioButtons.get(randomIndex).click();
//            System.out.println("Random radio button clicked.");
//        } else {
//            System.out.println("No radio buttons found.");
//        }
//
//
//        WebElement proceedtopayButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.Button[@content-desc=\'Proceed to Pay\']")));
//        proceedtopayButton.click();
//
//
//        WebElement gobacktohomeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.Button[@content-desc=\'Go back to Home\']")));
//        gobacktohomeButton.click();
//
////            Thread.sleep(3000);
////            WebElement profile=driver.findElement(By.xpath("//android.widget.FrameLayout[@resource-id=\'android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View"));
////            profile.click();
//
//        WebElement profileicon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.FrameLayout[@resource-id=\'android:id/content\']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View")));
//        profileicon.click();
//
//        WebElement myordersSideMenu = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.ImageView[@content-desc=\'My Orders\']")));
//        myordersSideMenu.click();
//
//        // Wait for all items with 'Bought on' in content-desc
//        List<WebElement> orderList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
//                By.xpath("//android.view.View[contains(@content-desc, 'Bought on')]"))
//        );
//
//// Print all order details
//        System.out.println("Total orders found: " + orderList.size());
//        for (WebElement order : orderList) {
//            System.out.println("Order Details: " + order.getAttribute("content-desc"));
//        }
//
//// Click the first item if the list is not empty
//        if (!orderList.isEmpty()) {
//            orderList.get(0).click();
//            System.out.println("Clicked the first order.");
//        } else {
//            System.out.println("No orders found.");
//        }
//
//        WebElement orderDetailspageBackButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.FrameLayout[@resource-id=\'android:id/content\']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View[1]/android.widget.ImageView")));
//        orderDetailspageBackButton.click();
//
//        WebElement myorderspagebackButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.FrameLayout[@resource-id=\'android:id/content\']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View[1]/android.widget.ImageView")));
//        myorderspagebackButton.click();
//
//        profileicon.click();
//
//
//
////        WebElement downloadInvoice = wait.until(ExpectedConditions.elementToBeClickable(
////                By.xpath("//android.widget.ImageView[@content-desc='Download Invoice  \']")
////        ));
////        downloadInvoice.click();
////
////        Thread.sleep(5000); // Adjust based on download time
////
////
////// Step 2: Wait for the file to download
////        Thread.sleep(5000); // Adjust if needed
////
////// Step 3: Get the latest .pdf file from device using adb
////        String latestPdf = null;
////        try {
////            Process listFiles = Runtime.getRuntime().exec("adb shell ls -t /sdcard/Download");
////            BufferedReader reader = new BufferedReader(new InputStreamReader(listFiles.getInputStream()));
////            String line;
////            while ((line = reader.readLine()) != null) {
////                if (line.trim().endsWith(".pdf")) {
////                    latestPdf = line.trim(); // First PDF in -t order is the latest
////                    break;
////                }
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
////// Step 4: Pull the latest PDF file to project directory
////        if (latestPdf != null) {
////            String deviceFilePath = "/sdcard/Download/" + latestPdf;
////            String localDirectory = System.getProperty("user.dir") + "/downloads/";
////            String localFilePath = localDirectory + latestPdf;
////
////            File folder = new File(localDirectory);
////            if (!folder.exists()) folder.mkdir();
////
////            try {
////                Process pullFile = Runtime.getRuntime().exec("adb pull " + deviceFilePath + " " + localFilePath);
////                pullFile.waitFor();
////                System.out.println("Downloaded invoice: " + localFilePath);
////            } catch (IOException | InterruptedException e) {
////                e.printStackTrace();
////            }
////        } else {
////            System.out.println("No PDF invoice found in device's Download folder.");
////        }
////
////        WebElement chrome = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//android.widget.ImageView[@resource-id=\'oplus:id/resolver_item_icon'])[1]")));
////        chrome.click();
////
////       WebElement download=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.Button[@resource-id=\'com.android.chrome:id/positive_button']")));
////        download.click();
//        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.Button[@content-desc=\'Logout\']")));
//        logoutButton.click();
////        // Your test steps...
////        Thread.sleep(3000);
//
//        // Close the app at the end
//        closeApp();
//
//
//    }
//
//
//
//    public static void clearAppCache(String packageName) {
//        try {
//            // Make sure the driver is initialized before executing ADB commands
//            if (driver != null) {
//                driver.executeScript("mobile: shell", ImmutableMap.of("command", "pm clear " + packageName));
//                System.out.println("Cache cleared for app: " + packageName);
//            } else {
//                System.out.println("Driver is not initialized, skipping cache clear.");
//            }
//        } catch (Exception e) {
//            System.out.println("Error clearing app cache: " + e.getMessage());
//        }
//    }
//
//
//    public static void closeApp() {
//        try {
//            // Execute ADB command to force-stop the app
//            driver.executeScript("mobile: shell", ImmutableMap.of("command", "am force-stop " + "com.chataak.app"));
//            System.out.println("Application Force Stopped Successfully");
//        } catch (Exception e) {
//            System.out.println("Error stopping the app: " + e.getMessage());
//        } finally {
//            // Quit the driver session
//            driver.quit();
//        }
//    }
//
//}
//
//
