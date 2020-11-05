import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.isChrome;

public class LoginTests {

    @DataProvider(name = "login-alerts")
    public Object[][] loginProvider() {
        return new Object[][]{
                {"papito@ninjaplus.com", "abc124", "Usuário e/ou senha inválidos"},
                {"404@gmail.com", "abc124", "Usuário e/ou senha inválidos"},
                {"", "abc124", "Opps. Cadê o email?"},
                {"papito@ninjaplus.com", "", "Opps. Cadê a senha?"}
        };
    }

    @Test
    public void shouldSeeLoggedUser() {
        isChrome();
        open("http://ninjaplus-web:5000");

        $("#emailId").setValue("papito@ninjaplus.com");
        $("#passId").setValue("pwd123");
        $(byText("Entrar")).click();

        $(".user .info span").shouldHave(text("Papito"));
    }

    @Test(dataProvider = "login-alerts")
    public void shouldSeeLoginAlerts(String email, String pass, String expectAlert) {
        isChrome();

        executeJavaScript("localStorage.clear();");
        open("http://ninjaplus-web:5000");

        $("#emailId").setValue(email);
        $("#passId").setValue(pass);
        $(byText("Entrar")).click();

        $(".user .info span").shouldHave(text(expectAlert));
    }

}
