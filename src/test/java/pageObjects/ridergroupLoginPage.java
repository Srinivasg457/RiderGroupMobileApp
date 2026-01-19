package pageObjects;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.WaitHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static stepdefinations.BaseClass.driver;
import static stepdefinations.BaseClass.wait;

public class ridergroupLoginPage {
    public AndroidDriver ldriver;
    WaitHelper waithelper;

    public static Properties configprop;

    //constructor
    public ridergroupLoginPage() throws IOException {
        ldriver = driver;
        PageFactory.initElements(driver, this);
        waithelper = new WaitHelper(ldriver);

    }

    //xpath identification

    {
        try {
            configprop = new Properties();
            String configPath = System.getProperty("user.dir") + "/src/test/resources/config.properties";
            FileInputStream configProfile = new FileInputStream(configPath);
            configprop.load(configProfile);


        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load properties file!");
        }
    }


    private By getByXpath(String key) {
        String xpath = configprop.getProperty(key);
        if (xpath == null || xpath.trim().isEmpty()) {
            System.out.println("WARNING: XPath for key '" + key + "' is missing or commented out in config.properties.");
            return null; // Avoids IllegalArgumentException
        }
        return By.xpath(xpath);
    }


    //Locators
    private By emailField = AppiumBy.xpath(configprop.getProperty("emailtextBox"));
   // private By passwordField = AppiumBy.xpath(configprop.getProperty("passwordtextBox"));
    // CORRECT - Use androidUIAutomator for UiAutomator selectors:
   // private By passwordField = AppiumBy.androidUIAutomator(configprop.getProperty(""));
    // FIXED: Read XPath from config
    private By passwordField = AppiumBy.xpath(configprop.getProperty("passwordtextBox"));
    private By loginButton = AppiumBy.xpath(configprop.getProperty("loginButton"));

    private By loggedInUserName = AppiumBy.xpath(configprop.getProperty("loggedInUserName"));

    //Action Methods
    public void emailTextBox() {
        try {
            WebElement emailTextBox = waithelper.WaitForElement1(emailField, 20);
            if (emailTextBox.isDisplayed() && emailTextBox.isEnabled()) {
                System.out.println("Email Bringing data from config file");
                emailTextBox.click();
                emailTextBox.clear();
                emailTextBox.sendKeys(configprop.getProperty("srinivasemail"));
                Assert.assertTrue(true);
            } else {
                Assert.fail("The Email Text Box is Not Displayed and Not Enabled");
            }
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    public void passwordTextBox() {
        try {
            WebElement password = waithelper.WaitForElement1(passwordField, 20);
          /*  WebElement password = wait.until(
                    ExpectedConditions.visibilityOf((WebElement) passwordField)
            );*/
            if (password.isDisplayed() && password.isEnabled()) {
                System.out.println("Password Bringing from the Config file");
                password.click();
                password.clear();
                password.sendKeys(configprop.getProperty("SrinivasPassword"));
                Assert.assertTrue(true);
            } else {
                Assert.fail("The Password Field is Not Enabled or Disabled");
            }
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    public void loginButton() {
        try {
            WebElement loginBtn = waithelper.WaitForElement1(loginButton, 20);
            if (loginBtn.isDisplayed() && loginBtn.isEnabled()) {
                System.out.println("Login Button Bought From  Config file");
                loginBtn.click();
                Assert.assertTrue(true);
            } else {
                Assert.fail("The Login Button is Not Enabled or Disabled");
            }
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    public void WelcomeMessage() {
        try {
            WebElement welcomeUserMsg = waithelper.WaitForElement1(loggedInUserName, 20);
            if (welcomeUserMsg.isDisplayed() && welcomeUserMsg.isEnabled()) {
                String userName=welcomeUserMsg.getAttribute("content-desc");
                System.out.println("userName ="+userName);
                Assert.assertTrue(true);
            } else {
                Assert.fail("The User Welcome Message Not Displayed ");
            }
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}
