package stepdefinations;

import appTest.AppTest;
import io.cucumber.java.en.*;
import pageObjects.CreatePost;
import pageObjects.ridergroupLoginPage;

import java.io.IOException;
//import java.util.logging.Logger;
import org.apache.log4j.Logger; // ADD THIS
public class RiderGroupLoginPage {
    private static final Logger logger = Logger.getLogger(String.valueOf(RidergroupLaunchApp.class));
    private final BaseClass baseClass;
    private ridergroupLoginPage loginpage;
    public RiderGroupLoginPage(BaseClass baseClass) throws IOException {
        this.baseClass = baseClass;
        this.loginpage=new ridergroupLoginPage();
        System.out.println("Ridergroup Login Page  called. BaseClass injected.");
    }


    @Then("User Checks The Welcome Message on the Landing Page")
    public void userChecksTheWelcomeMessageOnTheLandingPage() {
        logger.info("***  The Presence of The Welcome message    ***");
        loginpage.WelcomeMessage();
    }
}
