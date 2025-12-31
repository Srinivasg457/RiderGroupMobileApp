package pageObjects;

import io.appium.java_client.AppiumBy;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static stepdefinations.BaseClass.driver;
import static stepdefinations.BaseClass.wait;

public class CreatePost {
    public void userCreatesPost() throws InterruptedException {
        try {
            // Try to find the email field
            By tabmypost = AppiumBy.xpath(
                    "//android.view.View[@content-desc=\'My Posts Tab 2 of 2\']"
            );

            System.out.println("Looking for My postTab...");

            WebElement postTab = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(tabmypost)
            );

            System.out.println("Email field found!");

            postTab.click();

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
                Thread.sleep(1000000);
                System.out.println("Login button clicked!");
            }
            else{
                System.out.println("Login button not clickable!");
                Assert.assertTrue(false);
            }

        } catch (Exception e) {
            System.out.println("❌ Error Moving to the My post : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
