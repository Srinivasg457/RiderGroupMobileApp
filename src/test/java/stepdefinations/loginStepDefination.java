package stepdefinations;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class loginStepDefination {

    // Remove extends BaseClass and use static access
    @Given("the mobile app is launched")
    public void the_mobile_app_is_launched() {
        // App launch is handled in hooks, just verify driver is initialized
        if (BaseClass.driver != null) {
            System.out.println("Mobile app launched successfully - Driver session: " +
                    ((AndroidDriver) BaseClass.driver).getSessionId());
        } else {
            throw new RuntimeException("Driver is not initialized - app launch failed");
        }
    }

    // ... rest of your step definitions remain the same
    @When("the user completes onboarding screens")
    public void the_user_completes_onboarding_screens() {
        WebDriverWait wait = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(20));

        // Complete three onboarding screens
        for (int i = 0; i < 3; i++) {
            WebElement onboardingButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//android.widget.ImageView[2]")));
            onboardingButton.click();
            System.out.println("Completed onboarding screen " + (i + 1));

            // Small delay between screens
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @When("the user logs in with mobile number {string}")
    public void the_user_logs_in_with_mobile_number(String mobileNumber) {
        WebDriverWait wait = new WebDriverWait(BaseClass.driver, Duration.ofSeconds(20));

        // Wait and enter mobile number
        WebElement mobileInputField = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//android.widget.ImageView")));
        mobileInputField.click();
        mobileInputField.sendKeys(mobileNumber);

        // Click on "Continue"
        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.Button[@content-desc='Continue']")));
        continueButton.click();

        System.out.println("Entered mobile number: " + mobileNumber);
    }

}