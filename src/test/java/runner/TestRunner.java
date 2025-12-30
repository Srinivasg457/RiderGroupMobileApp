package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/features/01_Launch.feature",
//        features="src/test/java/features/PhoneNumberPage.feature",
        glue = {"stepdefinations","hooks"},
        dryRun = false,
        // tags ="@sanity or @regression", // Ensure correct tag syntax,
        // tags= "@sanity",  // this will execute only sanity
        //tags= "@regression"  // this will execute only sanity
        //tags="@sanity and @regression" // Executes scenarios with both @sanity and @regression
        //tags="@sanity or @regression"   //Executess sceanrio either sanity or regression//tags="@sanity and not @regression"  // Executes scenarios with @sanity but not @regression
        plugin = {
                "pretty",
                "html:target/cucumber-reports.html", // HTML Report
                "json:target/cucumber-reports/cucumber.json" // JSON Report
//                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", // Extent Reports
//                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",  // <-- updated plugin name
        },
        monochrome = true

)
public class TestRunner {
}

