package stepdefinations;
import io.cucumber.java.en.*;
import appTest.AppTest;
import pageObjects.CreatePost;
import pageObjects.ridergroupLoginPage;

import java.io.IOException;
//import java.util.logging.Logger;
import org.apache.log4j.Logger; // ADD THIS

public class RidergroupLaunchApp  {
    private static final Logger logger = Logger.getLogger(String.valueOf(RidergroupLaunchApp.class));
    private final BaseClass baseClass;
    private AppTest appTest;
    private CreatePost post;
    private ridergroupLoginPage loginpage;
    public RidergroupLaunchApp(BaseClass baseClass) throws IOException {
        this.baseClass = baseClass;
        this.appTest = new AppTest();
        this.post=new CreatePost();
        this.loginpage=new ridergroupLoginPage();
        System.out.println("Ridergroup launch App constructor called. BaseClass injected.");
    }

    @Given("user navigates to Rider Group Application")
    public void user_navigates_to_Rider_Group_Application() throws InterruptedException {
        logger.info("*** Starting The Emulator Android Device  ***");

        System.out.println("=== STEP EXECUTION START ===");

        // Debug the state
        System.out.println("Checking injection state:");
        System.out.println("baseClass is null: " + (baseClass == null));

        if (baseClass != null) {
            System.out.println("baseClass.driver is null: " + (baseClass.driver == null));
            System.out.println("baseClass.wait is null: " + (baseClass.wait == null));

            if (baseClass.driver != null) {
                System.out.println("Driver session ID: " + baseClass.driver.getSessionId());
                System.out.println("Current package: " + baseClass.driver.getCurrentPackage());
            }
        }

        System.out.println("appTest is null: " + (appTest == null));

        if (baseClass == null || baseClass.driver == null || baseClass.wait == null) {
            System.out.println("ERROR: Required objects are not properly initialized!");
            throw new RuntimeException("Initialization error. Check @Before hook.");
        }

        System.out.println("Calling RiderGroup Login Page...");
        logger.info("*** Entering The Email   ***");
        loginpage.emailTextBox();
        logger.info("*** Entering The Password  ***");
        loginpage.passwordTextBox();
        logger.info("*** Clicking The Login Button   ***");
        loginpage.loginButton();
        System.out.println("=== STEP EXECUTION COMPLETE ===");
    }


    @When("user navigates to post creation screen")
    public void user_navigates_to_post_creation_screen() throws InterruptedException {

        post.userCreatesPost();
        post.Addbtn();
    }


//    @When("user Scroll down till the end of the all posts")
//    public void userScrollDownTillTheEndOfTheAllPosts() {
//
//    }
    @When("user Scroll down till the end of the all posts")
    public void user_scroll_down_till_the_end_of_the_all_posts() {
        post.switchbetweenTabs();
    }

}

