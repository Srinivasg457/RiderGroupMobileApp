package pageObjects;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static stepdefinations.BaseClass.wait;

public class signUpPage {


    public void LoginPage(AndroidDriver driver, WebDriverWait wait) throws InterruptedException {
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


        } catch (Exception e) {
            System.out.println("❌ Error navigating to login Page: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    public void signuplink() {
        try {
            By signUpLink = AppiumBy.xpath(
                    "//android.widget.Button[@content-desc=\"Don't have an account? Sign Up\"]"
            );

            WebElement SULink = wait.until(
                    ExpectedConditions.elementToBeClickable(signUpLink)
            );

            // CORRECTED: Use getAttribute("contentDescription") instead of getText()
            String ActualLinkName = SULink.getAttribute("contentDescription");
            String Expectedlinkname = "Don't have an account? Sign Up";

            // Optional: Print for debugging
            System.out.println("Actual link text: " + ActualLinkName);

            if(ActualLinkName != null && ActualLinkName.equals(Expectedlinkname)) {
                System.out.println("Link text verification passed");
                Assert.assertTrue(true);
            } else {
                System.err.println("Actual: " + ActualLinkName + " | Expected: " + Expectedlinkname);
                Assert.fail("The Actual Sign up link text not matching with the Expected linked text");
            }

            if(SULink.isDisplayed() && SULink.isEnabled()) {
                SULink.click();
                System.out.println("Sign up link clicked successfully");
                Assert.assertTrue(true);
            } else {
                System.err.println("Sign up Link Not Found - Displayed: " + SULink.isDisplayed() +
                        ", Enabled: " + SULink.isEnabled());
                Assert.fail("Sign up link not Displayed or not Enabled");
            }

        } catch (NoSuchElementException e) {
            System.err.println("Signup link not found: " + e.getMessage());
            throw e;
        } catch (TimeoutException e) {
            System.err.println("Timeout waiting for signup link: " + e.getMessage());
            Assert.fail("Timeout waiting for signup link to be clickable");
        }
    }

    public void signupFullName() {
        try {
            // Locate Full Name field
            By fullNameField = AppiumBy.xpath("//android.widget.EditText[@hint='Full Name']");

            WebElement fullNameElement = wait.until(
                    ExpectedConditions.elementToBeClickable(fullNameField)
            );

            // Validate it's displayed and enabled
            if(fullNameElement.isDisplayed() && fullNameElement.isEnabled()) {
                System.out.println("Full Name field is present and clickable");

                // Optional: Get placeholder text to verify
                String placeholder = fullNameElement.getAttribute("hint");
                if("Full Name".equals(placeholder)) {
                    System.out.println("Full Name placeholder verified: " + placeholder);
                    fullNameElement.click();
                    fullNameElement.clear();
                    fullNameElement.sendKeys("Srinivas G");
                    Assert.assertTrue(true);
                }
            } else {
                Assert.fail("Full Name field not displayed or not enabled");
            }

        } catch (TimeoutException e) {
            System.err.println("Timeout waiting for Full Name field: " + e.getMessage());
            Assert.fail("Full Name field not found within timeout");
        }
    }

    public void signupEmail() {
        try {
            // Locate Email field
            By emailField = AppiumBy.xpath("//android.widget.EditText[@hint='Email']");

            WebElement emailElement = wait.until(
                    ExpectedConditions.elementToBeClickable(emailField)
            );

            // Validate it's displayed and enabled
            if(emailElement.isDisplayed() && emailElement.isEnabled()) {
                System.out.println("Email field is present and clickable");

                // Optional: Get placeholder text to verify
                String placeholder = emailElement.getAttribute("hint");
                if("Email".equals(placeholder)) {
                    System.out.println("Email placeholder verified: " + placeholder);
                    emailElement.click();
                    emailElement.clear();
                    emailElement.sendKeys("srinivasg457@gmail.com");
                    Assert.assertTrue(true);
                }
            } else {
                Assert.fail("Email field not displayed or not enabled");
            }

        } catch (TimeoutException e) {
            System.err.println("Timeout waiting for Email field: " + e.getMessage());
            Assert.fail("Email field not found within timeout");
        }
    }

    public void signupPassword() {
        try {
            // Locate Password field
            By passwordField = AppiumBy.xpath("//android.widget.EditText[@hint='Password']");

            WebElement passwordElement = wait.until(
                    ExpectedConditions.elementToBeClickable(passwordField)
            );

            // Validate it's displayed and enabled
            if(passwordElement.isDisplayed() && passwordElement.isEnabled()) {
                System.out.println("Password field is present and clickable");

                // Verify it's a password field
                boolean isPasswordField = "true".equals(passwordElement.getAttribute("password"));
                if(isPasswordField) {
                    System.out.println("Confirmed: This is a password field");
                }

                // Optional: Get placeholder text to verify
                String placeholder = passwordElement.getAttribute("hint");
                if("Password".equals(placeholder)) {
                    System.out.println("Password placeholder verified: " + placeholder);
                    passwordElement.click();
                    passwordElement.clear();
                    passwordElement.sendKeys("Shree@1234");
                    Assert.assertTrue(true);
                }
            } else {
                Assert.fail("Password field not displayed or not enabled");
            }

        } catch (TimeoutException e) {
            System.err.println("Timeout waiting for Password field: " + e.getMessage());
            Assert.fail("Password field not found within timeout");
        }
    }

    public void signupButton() {
        try {
            // Locate SIGN UP button using content-desc
            By signupButton = AppiumBy.xpath(
                    "//android.widget.Button[@content-desc='SIGN UP']"
            );

            WebElement signupBtnElement = wait.until(
                    ExpectedConditions.elementToBeClickable(signupButton)
            );

            // Get the button text from content-desc
            String buttonText = signupBtnElement.getAttribute("contentDescription");
            String expectedText = "SIGN UP";

            // Validate button text
            if(expectedText.equals(buttonText)) {
                System.out.println("SIGN UP button text verified: " + buttonText);

                // Validate it's displayed and enabled
                if(signupBtnElement.isDisplayed() && signupBtnElement.isEnabled()) {
                    System.out.println("SIGN UP button is present and clickable");
                    Assert.assertTrue(true);
                    signupBtnElement.click();
                    verifyEmailAlreadyExistsError();
                    Thread.sleep(10000);
                } else {
                    Assert.fail("SIGN UP button not displayed or not enabled");
                }
            } else {
                System.err.println("Button text mismatch. Actual: " + buttonText + " | Expected: " + expectedText);
                Assert.fail("SIGN UP button text doesn't match expected");
            }

        } catch (TimeoutException | InterruptedException e) {
            System.err.println("Timeout waiting for SIGN UP button: " + e.getMessage());
            Assert.fail("SIGN UP button not found within timeout");
        }
    }


    //this method checks for the existed email
    public void verifyEmailAlreadyExistsError() {
        try {
            // Wait for error message to appear
            By errorMessage = AppiumBy.xpath(
                    "//android.view.View[contains(@content-desc, 'email address is already in use')]"
            );

            WebElement errorElement = wait.until(
                    ExpectedConditions.presenceOfElementLocated(errorMessage)
            );

            // Get the full error text
            String errorText = errorElement.getAttribute("contentDescription");
            String expectedText = "Exception: The email address is already in use by another account.";

            // Validate error message
            if(errorText != null && errorText.equals(expectedText)) {
                System.out.println("Email already exists error message verified: " + errorText);
                Assert.assertTrue("Email already exists error should be displayed", true);

                // Also verify it's displayed
                if(errorElement.isDisplayed()) {
                    System.out.println("Error message is visible on screen");
                    return;
                }
            } else {
                System.err.println("Error message mismatch. Actual: " + errorText);
                Assert.fail("Email already exists error message not as expected");
            }

        } catch (TimeoutException e) {
            System.err.println("Email already exists error message not found: " + e.getMessage());
            Assert.fail("Email already exists error message should appear but didn't");
        }
    }


}
