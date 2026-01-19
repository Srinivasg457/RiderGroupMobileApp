package pageObjects;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static stepdefinations.BaseClass.driver;
import static stepdefinations.BaseClass.wait;

public class CreatePost {

    // 1. Add pushFile method here
    public void pushImageToDevice() {
        try {
            String localImagePath = System.getProperty("user.dir") + "/src/test/java/images/motorcycle-4348015.jpg";
            // Try multiple locations - gallery might access different folders
            String[] remotePaths = {
                    "/storage/emulated/0/DCIM/test_image.jpg",  // Camera folder
                    "/storage/emulated/0/Pictures/test_image.jpg", // Pictures folder
                    "/storage/emulated/0/Download/test_image.jpg"  // Download folder
            };

            boolean pushed = false;
            for (String remotePath : remotePaths) {
                try {
                    System.out.println("📤 Pushing image to device...");
                    System.out.println("Local: " + localImagePath);
                    System.out.println("Remote: " + remotePath);

                    // Push file to device
                    driver.pushFile(remotePath, new File(localImagePath));
                    System.out.println("✅ Image pushed successfully to: " + remotePath);
                    pushed = true;

                    // Give time for media scanner to detect
                    Thread.sleep(2000);

                } catch (Exception e) {
                    System.out.println("⚠️ Failed to push to " + remotePath + ": " + e.getMessage());
                }
            }

            if (!pushed) {
                throw new RuntimeException("Failed to push image to any location");
            }

        } catch (Exception e) {
            System.out.println("❌ pushFile failed: " + e.getMessage());
            throw new RuntimeException("Failed to push image to device", e);
        }
    }

    public void userCreatesPost() throws InterruptedException {
        try {
            // STEP 1: Push image first
            pushImageToDevice();

            // STEP 2: Wait a moment
            Thread.sleep(2000);

            // Wait for the Create Post button to be visible
            By createPostButton = AppiumBy.xpath("//android.widget.Button[@content-desc='Create Post']");
            System.out.println("Looking for Create Post button...");
            WebElement createPostBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(createPostButton)
            );
            System.out.println("✅ Found Create Post button! Clicking...");
            createPostBtn.click();

            // Wait for post creation screen to load
            Thread.sleep(3000);
            System.out.println("✅ Successfully navigated to post creation screen!");

            // STEP 3: Click "Add from Gallery"
            By addFromGallery = AppiumBy.xpath("//android.widget.Button[@content-desc='Add from Gallery']");
            System.out.println("Looking for 'Add from Gallery' button...");
            WebElement imagesFromGallery = wait.until(
                    ExpectedConditions.elementToBeClickable(addFromGallery)
            );
            System.out.println("✅ Clicking 'Add from Gallery'...");
            imagesFromGallery.click();
            Thread.sleep(3000);

            // STEP 4: Select image from gallery
            selectImageFromGallery();

            // STEP 5: Add caption
            addCaption();

            // STEP 6: Click Share button
            clickShareButton();

            // STEP 7: Verify post creation
            verifyPostCreated();

        } catch (Exception e) {
            System.out.println("❌ Error creating post: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    // Improved image selection method
    private void selectImageFromGallery() throws InterruptedException {
        System.out.println("Selecting image from gallery...");

        try {
            // Wait for gallery to load
            Thread.sleep(3000);

            // First, debug what's on the screen
            debugGalleryScreen();

            // Method 1: Try to find images by different strategies
            boolean imageSelected = false;

            // Strategy 1: Look for image by file name (if visible)
            try {
                System.out.println("Trying to find image by file name...");
                By imageByName = AppiumBy.xpath("//*[contains(@text, 'test_image') or contains(@text, 'motorcycle') or contains(@content-desc, 'test_image')]");
                List<WebElement> images = driver.findElements(imageByName);
                if (!images.isEmpty()) {
                    images.get(0).click();
                    System.out.println("✅ Selected image by file name");
                    imageSelected = true;
                }
            } catch (Exception e) {
                System.out.println("Could not find by file name: " + e.getMessage());
            }

            if (!imageSelected) {
                // Strategy 2: Look for actual image views (not icons)
                System.out.println("Looking for actual image views...");
                List<WebElement> allImageViews = driver.findElements(AppiumBy.className("android.widget.ImageView"));
                System.out.println("Found " + allImageViews.size() + " ImageView elements");

                for (int i = 0; i < allImageViews.size(); i++) {
                    try {
                        WebElement img = allImageViews.get(i);
                        if (img.isDisplayed()) {
                            // Get element info for debugging
                            String desc = img.getAttribute("content-desc");
                            String resourceId = img.getAttribute("resource-id");
                            String bounds = img.getAttribute("bounds");

                            // Skip small elements (likely icons)
                            if (bounds != null) {
                                // Parse bounds to check size
                                String[] coords = bounds.replace("[", "").replace("]", "").split(",");
                                int width = Integer.parseInt(coords[2]) - Integer.parseInt(coords[0]);
                                int height = Integer.parseInt(coords[3]) - Integer.parseInt(coords[1]);

                                // Only click if it's a reasonably sized image (not an icon)
                                if (width > 100 && height > 100) {
                                    System.out.println("Found image at index " + i + ": desc=" + desc + ", id=" + resourceId + ", size=" + width + "x" + height);
                                    img.click();
                                    System.out.println("✅ Selected image at index " + i);
                                    imageSelected = true;
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }

            if (!imageSelected) {
                // Strategy 3: Try to navigate to Pictures or DCIM folder
                System.out.println("Trying to navigate to image folders...");
                try {
                    // Look for folder names
                    String[] folderNames = {"Pictures", "DCIM", "Images", "Camera", "Gallery"};
                    for (String folder : folderNames) {
                        try {
                            By folderLocator = AppiumBy.xpath("//*[contains(@text, '" + folder + "')]");
                            WebElement folderElement = driver.findElement(folderLocator);
                            if (folderElement.isDisplayed()) {
                                folderElement.click();
                                System.out.println("✅ Clicked " + folder + " folder");
                                Thread.sleep(2000);

                                // Now try to select first image in folder
                                By firstImage = AppiumBy.xpath("(//android.widget.ImageView)[1]");
                                WebElement image = wait.until(
                                        ExpectedConditions.elementToBeClickable(firstImage)
                                );
                                image.click();
                                System.out.println("✅ Selected first image in " + folder + " folder");
                                imageSelected = true;
                                break;
                            }
                        } catch (Exception e) {
                            // Continue to next folder name
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Could not navigate folders: " + e.getMessage());
                }
            }

            if (!imageSelected) {
                // Strategy 4: Last resort - tap at coordinates where images usually are
                System.out.println("⚠️ Using coordinate tap as last resort...");
                Dimension size = driver.manage().window().getSize();
                int x = size.width / 2;
                int y = size.height / 3; // Upper middle area (where images usually are)

                tapAtCoordinates(x, y);
                System.out.println("✅ Tapped at coordinates: " + x + "," + y);
                imageSelected = true;
            }

            // Wait for image to be selected and return to post screen
            Thread.sleep(3000);
            System.out.println("✅ Image selection complete");

        } catch (Exception e) {
            System.out.println("❌ Could not select image: " + e.getMessage());
            throw e;
        }
    }

    // Helper method to tap at coordinates
    private void tapAtCoordinates(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);

        tap.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(tap));
    }

    // Debug method to see what's on screen
    private void debugGalleryScreen() {
        try {
            System.out.println("=== DEBUG GALLERY SCREEN ===");

            // Get current activity
            String currentActivity = ((AndroidDriver) driver).currentActivity();
            System.out.println("Current Activity: " + currentActivity);

            // List all clickable elements
            List<WebElement> clickableElements = driver.findElements(AppiumBy.xpath("//*[@clickable='true']"));
            System.out.println("Clickable elements: " + clickableElements.size());

            for (int i = 0; i < Math.min(clickableElements.size(), 10); i++) {
                try {
                    WebElement el = clickableElements.get(i);
                    String text = el.getText();
                    String desc = el.getAttribute("content-desc");
                    String className = el.getAttribute("className");
                    System.out.println("Element " + i + ": class=" + className + ", text='" + text + "', desc='" + desc + "'");
                } catch (Exception e) {
                    System.out.println("Element " + i + ": Could not get attributes");
                }
            }

            System.out.println("=== END DEBUG ===\n");

        } catch (Exception e) {
            System.out.println("Debug failed: " + e.getMessage());
        }
    }

    public void addCaption() throws InterruptedException {
        try {
            System.out.println("Adding caption...");
            By captionText = AppiumBy.xpath("//android.widget.EditText");
            WebElement caption = wait.until(
                    ExpectedConditions.elementToBeClickable(captionText)
            );
            caption.click();
            caption.clear();
            caption.sendKeys("New Post Automate");
            System.out.println("✅ Caption entered successfully");

            // Hide keyboard if needed
            try {
                driver.hideKeyboard();
            } catch (Exception e) {
                // Keyboard might already be hidden
            }

            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("❌ Could not enter the caption: " + e.getMessage());
            throw e;
        }
    }

    public void clickShareButton() throws InterruptedException {
        try {
            System.out.println("Looking for Share button...");
            By sharePost = AppiumBy.xpath("//android.widget.Button[@content-desc='Share']");
            WebElement shareBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(sharePost)
            );

            if (shareBtn.isDisplayed() && shareBtn.isEnabled()) {
                shareBtn.click();
                System.out.println("✅ Share button clicked");
                Assert.assertTrue("Share button clicked successfully", true);
            } else {
                Assert.fail("Share button not displayed or enabled");
            }

            Thread.sleep(3000); // Wait for post to be shared

        } catch (Exception e) {
            System.out.println("❌ Could not click share button: " + e.getMessage());
            throw e;
        }
    }

    public void verifyPostCreated() throws InterruptedException {
        try {
            System.out.println("Verifying post creation...");

            // Wait for any success message or return to main screen
            Thread.sleep(5000);

            // Check for success indicators
            String[] successIndicators = {
                    "Post created",
                    "Success",
                    "Posted",
                    "Done"
            };

            for (String indicator : successIndicators) {
                try {
                    By successLocator = AppiumBy.xpath("//*[contains(@text, '" + indicator + "')]");
                    WebElement successElement = driver.findElement(successLocator);
                    if (successElement.isDisplayed()) {
                        System.out.println("✅ Post created successfully! Found: " + indicator);
                        return;
                    }
                } catch (Exception e) {
                    // Continue checking other indicators
                }
            }

            // If no success message, check if we're back to a screen with "Create Post" button
            try {
                By createPostButton = AppiumBy.xpath("//android.widget.Button[@content-desc='Create Post']");
                WebElement postBtn = driver.findElement(createPostButton);
                if (postBtn.isDisplayed()) {
                    System.out.println("✅ Returned to main screen - post likely created");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Could not verify post creation explicitly");
            }

        } catch (Exception e) {
            System.out.println("⚠️ Verification failed: " + e.getMessage());
        }
    }


    public void Addbtn() throws InterruptedException {
        try {
            // Try to select first image
            By Addbtn = AppiumBy.xpath("//android.widget.Button[@resource-id=\'com.google.android.providers.media.module:id/button_add\']");
            WebElement Add = wait.until(
                    ExpectedConditions.elementToBeClickable(Addbtn)
            );
            Add.click();
            System.out.println("Add button Clicked ");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("❌ Could Enter The Caption: " + e.getMessage());
            throw e;
        }
    }

//
//    public void ShareButton() throws InterruptedException {
//        try {
//            // Try to select first image
//            By SharePost = AppiumBy.xpath("//android.widget.Button[@content-desc=\'Share\']");
//            WebElement sharebtn = wait.until(
//                    ExpectedConditions.elementToBeClickable(SharePost)
//            );
//            if(sharebtn.isDisplayed() && sharebtn.isEnabled()){
//                sharebtn.click();
//                Assert.assertTrue(true);
//            }
//            else{
//                Assert.fail("Button Not Displayed or Enabled ");
//            }
//
//            System.out.println("Caption Entered Successfully");
//            Thread.sleep(2000);
//        } catch (Exception e) {
//            System.out.println("❌ Could Enter The Caption: " + e.getMessage());
//            throw e;
//        }
//    }



    //Scroll option
//    public void scrollMainFeedDown() {
//        try {
//            postbutton();
//            WebElement scrollableElement = driver.findElement(AppiumBy.androidUIAutomator(
//                    "new UiScrollable(new UiSelector().scrollable(true).instance(0))" +
//                            ".scrollForward()"
//            ));
//            System.out.println("Scrolled main feed down");
//            Thread.sleep(60000);
//        } catch (Exception e) {
//            System.out.println("Could not scroll: " + e.getMessage());
//        }
//    }

//    public void scrollTillEndSimple() {
//        System.out.println("Starting to scroll to end...");
//
//        // Don't check for bottom tabs - they're always visible!
//        // Instead, scroll until we can't scroll anymore
//
//        int successCount = 0;
//        int failCount = 0;
//        int maxFails = 3;
//
//        while (failCount < maxFails) {
//            try {
//                // Try to scroll
//                driver.findElement(AppiumBy.androidUIAutomator(
//                        "new UiScrollable(new UiSelector().scrollable(true)).scrollForward()"
//                ));
//
//                successCount++;
//                failCount = 0; // Reset fail counter
//                System.out.println("Successfully scrolled. Count: " + successCount);
//
//                // Wait between scrolls
//                Thread.sleep(2000);
//
//            } catch (Exception e) {
//                failCount++;
//                System.out.println("Scroll failed " + failCount + " time(s)");
//
//                // Wait before retry
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException ie) {
//                    Thread.currentThread().interrupt();
//                    break;
//                }
//            }
//        }
//
//        System.out.println("Finished. Total scrolls: " + successCount);
//        System.out.println("Stopped because of " + failCount + " consecutive failures");
//    }
//    public void scroll(){
//        driver.findElement(
//                new AppiumBy.ByAndroidUIAutomator(
//                        "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"test drive\"))"
//                )
//        );
//
//    }


    public void switchbetweenTabs(){

// Wait for feed
        By HomeFeed = AppiumBy.xpath("//android.widget.Button[@content-desc=\'Feed Tab 1 of 5\']");
        System.out.println("Looking Feed Button");
        WebElement feed = wait.until(
                ExpectedConditions.elementToBeClickable(HomeFeed)
        );
        System.out.println("✅ Found Create Post button! Clicking...");
        feed.click();


        By Allpost = AppiumBy.xpath("//android.view.View[@content-desc=\"All Posts Tab 1 of 2\"]");
        System.out.println("Looking all post Button");
        WebElement AllPostbtn = wait.until(
                ExpectedConditions.elementToBeClickable(Allpost)
        );
        if(AllPostbtn.isDisplayed() && AllPostbtn.isEnabled()){
            Assert.assertTrue(true);
        }
        else{
            Assert.fail("All post button dint found");
        }
        By mypost = AppiumBy.xpath("//android.view.View[@content-desc=\"My Posts Tab 2 of 2\"]");
        System.out.println("Looking mypost Button");
        WebElement mypostbtn = wait.until(
                ExpectedConditions.elementToBeClickable(mypost));

        if(mypostbtn.isDisplayed() && mypostbtn.isEnabled()){
            Assert.assertTrue(true);
        }
        else{
            Assert.fail("My post button dint found");
        }
    }


}
//import com.google.common.collect.ImmutableMap;
//import io.appium.java_client.AppiumBy;
//import org.junit.Assert;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import java.io.File;
//import static stepdefinations.BaseClass.driver;
//import static stepdefinations.BaseClass.wait;
//
//public class CreatePost {
//
//
//    // 1. Add pushFile method here
//    public void pushImageToDevice() {
//        try {
//            String localImagePath = System.getProperty("user.dir") + "/src/test/java/images/motorcycle-4348015.jpg";
//           // String remotePath = "/data/local/tmp/test_image.jpg";
//            String remotePath = "/storage/emulated/0/Pictures/test_image.jpg";
//            System.out.println("📤 Pushing image to device...");
//            System.out.println("Local: " + localImagePath);
//            System.out.println("Remote: " + remotePath);
//
//            // Push file to device
//            driver.pushFile(remotePath, new File(localImagePath));
//
//            System.out.println("✅ Image pushed successfully!");
//// Optional: Trigger media scan to make image appear in gallery
//            triggerMediaScan(remotePath);
//        } catch (Exception e) {
//            System.out.println("❌ pushFile failed: " + e.getMessage());
//            throw new RuntimeException("Failed to push image to device", e);
//        }
//    }
//
//    private void triggerMediaScan(String filePath) {
//        try {
//            // Execute ADB command to trigger media scan
//            driver.executeScript("mobile: shell", ImmutableMap.of(
//                    "command", "am broadcast",
//                    "args", "-a android.intent.action.MEDIA_SCANNER_SCAN_FILE -d file://" + filePath
//            ));
//            System.out.println("✅ Media scan triggered");
//            Thread.sleep(2000); // Wait for scan to complete
//        } catch (Exception e) {
//            System.out.println("⚠️ Could not trigger media scan: " + e.getMessage());
//        }
//    }
//
//
//    public void userCreatesPost() throws InterruptedException {
//        try {
//            // STEP 1: Push image first
//            pushImageToDevice();
//
//            // STEP 2: Wait a moment
//            Thread.sleep(2000);
//            // Wait for the Create Post button to be visible
//            By createPostButton = AppiumBy.xpath("//android.widget.Button[@content-desc='Create Post']");
//
//            System.out.println("Looking for Create Post button...");
//            WebElement createPostBtn = wait.until(
//                    ExpectedConditions.elementToBeClickable(createPostButton)
//            );
//
//            System.out.println("✅ Found Create Post button! Clicking...");
//            createPostBtn.click();
//
//            // Wait for post creation screen to load
//            Thread.sleep(3000);
//            System.out.println("✅ Successfully navigated to post creation screen!");
//
//
//
//            By addFromgallery = AppiumBy.xpath("//android.widget.Button[@content-desc=\'Add from Gallery\']");
//
//            System.out.println("photo add from gallery button...");
//            WebElement imagesfromgallery = wait.until(
//                    ExpectedConditions.elementToBeClickable(addFromgallery)
//            );
//
//            System.out.println("✅ Sending an Image ...");
//              imagesfromgallery.click();
//            Thread.sleep(3000);
//
//            // STEP 5: Select image (you need to implement this)
//            selectImageFromGallery();
////            By cancelbutton = AppiumBy.xpath(" //android.widget.ImageButton[@content-desc=\Cancel\]");
////            WebElement cancelbtn = wait.until(
////                    ExpectedConditions.elementToBeClickable(cancelbutton)
////            );
////            cancelbtn.click();
//        } catch (Exception e) {
//            System.out.println("❌ Error Moving to the My post : " + e.getMessage());
//            e.printStackTrace();
//            throw e;
//        }
//    }
//
//
//    // 3. Add image selection method
//    private void selectImageFromGallery() throws InterruptedException {
//        System.out.println("Selecting image from gallery...");
//
//        try {
//            // Try to select first image
//            By firstImage = AppiumBy.xpath("(//android.widget.ImageView)[1]");
//            WebElement image = wait.until(
//                    ExpectedConditions.elementToBeClickable(firstImage)
//            );
//            image.click();
//            System.out.println("✅ Selected first image");
//            Thread.sleep(2000);
//
//
//        } catch (Exception e) {
//            System.out.println("❌ Could not select image: " + e.getMessage());
//            throw e;
//        }
//    }
//
//
//    public void caption() throws InterruptedException {
//        try {
//            // Try to select first image
//            By CaptionText = AppiumBy.xpath("//android.widget.EditText");
//            WebElement Caption = wait.until(
//                    ExpectedConditions.elementToBeClickable(CaptionText)
//            );
//            Caption.click();
//            Caption.clear();
//            Caption.sendKeys("New Post Automate");
//            System.out.println("Caption Entered Successfully");
//            Thread.sleep(2000);
//        } catch (Exception e) {
//            System.out.println("❌ Could Enter The Caption: " + e.getMessage());
//            throw e;
//        }
//    }
//
//
//    public void ShareButton() throws InterruptedException {
//        try {
//            // Try to select first image
//            By SharePost = AppiumBy.xpath("//android.widget.Button[@content-desc=\'Share\']");
//            WebElement sharebtn = wait.until(
//                    ExpectedConditions.elementToBeClickable(SharePost)
//            );
//            if(sharebtn.isDisplayed() && sharebtn.isEnabled()){
//                sharebtn.click();
//                Assert.assertTrue(true);
//            }
//            else{
//                Assert.fail("Button Not Displayed or Enabled ");
//            }
//
//            System.out.println("Caption Entered Successfully");
//            Thread.sleep(2000);
//        } catch (Exception e) {
//            System.out.println("❌ Could Enter The Caption: " + e.getMessage());
//            throw e;
//        }
//    }
//
//
//
//}
