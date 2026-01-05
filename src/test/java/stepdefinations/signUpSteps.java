package stepdefinations;

import io.cucumber.java.en.*;
import pageObjects.signUpPage;
public class signUpSteps {
    private final BaseClass baseClass;
    private signUpPage sp;
    public signUpSteps(BaseClass baseClass) {
        this.baseClass = baseClass;
        this.sp=new signUpPage();
        System.out.println("RiderGroupApplication constructor called. BaseClass injected.");
    }
    @Given("user navigates to Rider Group application")
    public void user_navigates_to_rider_group_application() throws InterruptedException {
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

        System.out.println("SignUpPage is null: " + (sp == null));

        if (baseClass == null || baseClass.driver == null || baseClass.wait == null) {
            System.out.println("ERROR: Required objects are not properly initialized!");
            throw new RuntimeException("Initialization error. Check @Before hook.");
        }

        System.out.println("Calling navigateToLoginPage...");
        sp.LoginPage(baseClass.driver, baseClass.wait);
        System.out.println("=== STEP EXECUTION COMPLETE ===");
    }

    @Given("User Clicks The SignUp Link")
    public void userClicksTheSignUpLink() {

        sp.signuplink();

    }
    @Then("User Fill The Full Name Field")
    public void userFillTheFullNameField() {
      sp.signupFullName();
    }
    @Then("User Fill The Email Field")
    public void userFillTheEmailField() {
     sp.signupEmail();
    }
    @Then("User Fill The Password Field")
    public void userFillThePasswordField() {
     sp.signupPassword();
    }
    @Then("User Clicks The Sign up Button")
    public void userClicksTheSignUpButton() {
     sp.signupButton();
    }




}
