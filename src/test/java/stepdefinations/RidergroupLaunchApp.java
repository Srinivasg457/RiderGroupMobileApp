package stepdefinations;
import io.cucumber.java.en.*;
import appTest.AppTest;
import pageObjects.CreatePost;

public class RidergroupLaunchApp {

    private final BaseClass baseClass;
    private AppTest appTest;
    private CreatePost post;
    public RidergroupLaunchApp(BaseClass baseClass) {
        this.baseClass = baseClass;
        this.appTest = new AppTest();
        this.post=new CreatePost();
        System.out.println("ChataakLaunchApp constructor called. BaseClass injected.");
    }

    @Given("uer navigates to chataak application")
    public void uer_navigates_to_chataak_application() throws InterruptedException {
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

        System.out.println("Calling navigateToLoginPage...");
        appTest.navigateToLoginPage(baseClass.driver, baseClass.wait);

        System.out.println("=== STEP EXECUTION COMPLETE ===");
    }


    @When("user navigates to post creation screen")
    public void user_navigates_to_post_creation_screen() throws InterruptedException {
        post.userCreatesPost();
    }
    @When("user selects an image from gallery")
    public void user_selects_an_image_from_gallery() {

    }
    @When("user adds a caption to the post")
    public void user_adds_a_caption_to_the_post() {

    }
    @When("user posts the image")
    public void user_posts_the_image() {

    }
    @Then("the post should be created successfully")
    public void the_post_should_be_created_successfully() {

    }




}










//import appTest.AppTest;
//import io.appium.java_client.android.AndroidDriver;
//import io.cucumber.java.en.*;
//
//import java.net.MalformedURLException;
//
//public class chataaklaunchApp extends AppTest {
//
//    public chataaklaunchApp(stepdefinations.BaseClass baseClass) {
//        super(baseClass);
//    }
//
//    public class BaseClass {
//        public AndroidDriver driver;
//    }
////    AppTest test = new AppTest();
//
//    @Given("uer navigates to chataak application")
//    public void uer_navigates_to_chataak_application() throws MalformedURLException, InterruptedException {
////       test.ridergroup();
////        // test.openmobileapp();
//////        test.clearAppCache();
////        test.closeApp();
////        test.ridergroupEmulatorLaunch();
//        AppTest test;
//        test.ridergroupEmulatorLoginPage();
//    }
//
////    @Then("Enter with the mobile number and the otp")
////    public void enter_with_the_mobile_number_and_the_otp() {
////
////    }
////
////    @Then("Performs an Operation to Order an product")
////    public void performs_an_operation_to_order_an_product() {
////
////    }
//
//
////        @Given("uer navigates to chataak application")
////        public void uer_navigates_to_chataak_application() {
////            try {
////                ChataakActions.launchApp();
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
////
////        @Then("Enter with the mobile number and the otp")
////        public void enter_with_the_mobile_number_and_the_otp() {
////            try {
////                ChataakActions.login("8105914136");
////            } catch (Exception e) {
////                e.printStackTrace();
////            }
////        }
////
////        @Then("Performs an Operation to Order an product")
////        public void performs_an_operation_to_order_an_product() {
////            try {
////                ChataakActions.orderProduct("pre");
////            } catch (Exception e) {
////                e.printStackTrace();
////            } finally {
////                ChataakActions.closeApp();
////            }
////        }
//
//
//}
