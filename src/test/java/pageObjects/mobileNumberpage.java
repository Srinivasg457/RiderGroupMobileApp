package pageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import stepdefinations.BaseClass;

import java.util.List;
import java.util.Random;


public class mobileNumberpage extends BaseClass {

    // Onboarding Screen Elements
    public static WebElement getOnboardingButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.ImageView[2]")));
    }

    // Login Screen Elements
    public static WebElement getMobileInputField() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.ImageView")));
    }

    public static WebElement getContinueButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.Button[@content-desc='Continue']")));
    }

    public static WebElement getOtpInputField() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.EditText")));
    }

    public static WebElement getVerifyButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.Button[@content-desc='Verify']")));
    }

    // Store Selection Elements
    public static WebElement getShowStoresButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.Button[@content-desc='Show Stores']")));
    }

    public static WebElement getSearchStoreTextBox() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.EditText")));
    }

    public static WebElement getStoreOption(String storeName) {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.view.View[@content-desc='" + storeName + "']")));
    }

    // Scanner and Product Elements
    public static WebElement getScannerButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//android.widget.ImageView)[last()-1]")));
    }

    public static WebElement getQuantityIncreaseButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.ScrollView/android.view.View[12]")));
    }

    public static WebElement getAddToCartButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.view.View[@content-desc='Add to Cart']")));
    }

    public static WebElement getBuyNowButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.view.View[@content-desc='Buy Now']")));
    }

    // Payment Elements
    public static List<WebElement> getRadioButtons() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//android.widget.RadioButton")));
    }

    public static WebElement getProceedToPayButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.Button[@content-desc='Proceed to Pay']")));
    }

    public static WebElement getGoBackToHomeButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.Button[@content-desc='Go back to Home']")));
    }

    // Profile and Orders Elements
    public static WebElement getProfileIcon() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View")));
    }

    public static WebElement getMyOrdersSideMenu() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.ImageView[@content-desc='My Orders']")));
    }

    public static List<WebElement> getOrderList() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//android.view.View[contains(@content-desc, 'Bought on')]")));
    }

    public static WebElement getOrderDetailsBackButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View[1]/android.widget.ImageView")));
    }

    public static WebElement getMyOrdersBackButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]/android.view.View[1]/android.widget.ImageView")));
    }

    public static WebElement getLogoutButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.Button[@content-desc='Logout']")));
    }

    // Helper methods for interactions
    public static void clickRandomRadioButton() {
        List<WebElement> radioButtons = getRadioButtons();
        System.out.println("Radio buttons found: " + radioButtons.size());

        if (!radioButtons.isEmpty()) {
            int randomIndex = new Random().nextInt(radioButtons.size());
            radioButtons.get(randomIndex).click();
            System.out.println("Random radio button clicked.");
        } else {
            System.out.println("No radio buttons found.");
        }
    }

    public static void printOrderDetails() {
        List<WebElement> orderList = getOrderList();
        System.out.println("Total orders found: " + orderList.size());
        for (WebElement order : orderList) {
            System.out.println("Order Details: " + order.getAttribute("content-desc"));
        }
    }
}