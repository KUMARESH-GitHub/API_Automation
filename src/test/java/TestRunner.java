import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/httpMethods",
        glue = {"BDD_Rest_Assured"},
        monochrome = true,
        strict = true
)
public class TestRunner {
}
