package pageObjects;

import io.appium.java_client.AppiumBy;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;

import static stepdefinations.BaseClass.driver;
import static stepdefinations.BaseClass.wait;

public class CreatePost {


    // 1. Add pushFile method here
    public void pushImageToDevice() {
        try {
            String localImagePath = System.getProperty("user.dir") + "/src/test/java/images/motorcycle-4348015.jpg";
            String remotePath = "/data/local/tmp/test_image.jpg";

            System.out.println("📤 Pushing image to device...");
            System.out.println("Local: " + localImagePath);
            System.out.println("Remote: " + remotePath);

            // Push file to device
            driver.pushFile(remotePath, new File(localImagePath));

            System.out.println("✅ Image pushed successfully!");

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



            By addFromgallery = AppiumBy.xpath("//android.widget.Button[@content-desc=\'Add from Gallery\']");

            System.out.println("photo add from gallery button...");
            WebElement imagesfromgallery = wait.until(
                    ExpectedConditions.elementToBeClickable(addFromgallery)
            );

            System.out.println("✅ Sending an Image ...");
              imagesfromgallery.click();
            Thread.sleep(3000);

            // STEP 5: Select image (you need to implement this)
            selectImageFromGallery();

        } catch (Exception e) {
            System.out.println("❌ Error Moving to the My post : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    // 3. Add image selection method
    private void selectImageFromGallery() throws InterruptedException {
        System.out.println("Selecting image from gallery...");

        try {
            // Try to select first image
            By firstImage = AppiumBy.xpath("(//android.widget.ImageView)[1]");
            WebElement image = wait.until(
                    ExpectedConditions.elementToBeClickable(firstImage)
            );
            image.click();
            System.out.println("✅ Selected first image");
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("❌ Could not select image: " + e.getMessage());
            throw e;
        }
    }




}
