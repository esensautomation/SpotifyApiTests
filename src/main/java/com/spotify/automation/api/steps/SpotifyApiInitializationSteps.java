package com.spotify.automation.api.steps;

import com.esens.automationFwk.EsensLogger;
import com.esens.automationFwk.api.steps.ApiScenarioContext;
import com.esens.automationFwk.exceptions.OperatingSystemNotSupportedException;
import com.esens.automationFwk.utils.OSValidator;
import com.spotify.automation.api.exceptions.SpotifyAuthorizationTokenNotFound;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpotifyApiInitializationSteps {

    /**
     * Sample step
     */
    @Given("^sample Spotify API given step$")
    public void sampleSpotifyAPIGivenStep(){

    }

    @Given("I ask for Spotify Api token with scope {string}")
    public void iAskForSpotifyTokenWithScope(String scope) throws SpotifyAuthorizationTokenNotFound, OperatingSystemNotSupportedException {
        EsensLogger.logScenarioStep(scope);
        System.out.println("- Asking for Spotify authorization token with scope : " + scope + " ...");
        System.out.println("\n");
        System.out.println("- Request & Response :");
        System.out.println("\n");
        RequestSpecification rSpec = RestAssured
                .given()
                .param("client_id", "14ad74980cc24ecb9aff88ded35986e8")
                .param("response_type", "token")
                .param("redirect_uri", "http://www.example.com/postman/redirect")
                .param("state", "123")
                .param("scope", scope)
                .param("show_dialog", "false")
                .redirects().follow(false)
                .log().all();
        Response response = rSpec
                .when()
                .get("https://accounts.spotify.com/authorize")
                .then()
                .log().all()
                .and()
                .extract().response();
        String authRedirect = response
                .getHeader("Location");
        System.out.println("\n-> Found authentication redirect url in response header ! Url is " + authRedirect + ".");
        System.out.println("\n");
        SpotifyApiScenarioContext.getInstance().setAuthorizationRedirectionUrl(authRedirect);
        System.out.println("- Using chromedriver ...");
        System.out.println("\n");
        // select driver executable depending on the OS
        if(OSValidator.isMac()){
            System.setProperty("webdriver.chrome.driver", "src/test/resources/bin/mac/chromedriver");
        }
        else if(OSValidator.isLinux()){
            System.setProperty("webdriver.chrome.driver", "src/test/resources/bin/linux/chromedriver");
        }
        else if(OSValidator.isWindows()){
            System.setProperty("webdriver.chrome.driver", "src/test/resources/bin/chromedriver.exe");
        }
        else{
            throw new OperatingSystemNotSupportedException();
        }
        // open auth redirect url
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        WebDriver driver = new ChromeDriver(options);
        driver.get(SpotifyApiScenarioContext.getInstance().getAuthorizationRedirectionUrl());
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        System.out.println("\n-> Opened authorization redirection url in chrome headless client");
        System.out.println("\n");
        // input email
        System.out.println("- Input user email : esensqatraining@yopmail.com");
        System.out.println("\n");
        WebElement emailField = driver.findElement(By.id("login-username"));
        emailField.sendKeys("esensqatraining@yopmail.com");
        // input password
        System.out.println("- Input user password : Passwd1!");
        System.out.println("\n");
        WebElement passwordField = driver.findElement(By.id("login-password"));
        passwordField.sendKeys("Passwd1!");
        // click login
        System.out.println("- Submit the login form");
        System.out.println("\n");
        WebElement submitButton = driver.findElement(By.id("login-button"));
        submitButton.click();
        new WebDriverWait(driver, 30).until(ExpectedConditions.urlContains("#access_token="));
        // extract token
        Pattern p = Pattern.compile("#access_token=(.*)&token_type=Bearer");
        Matcher m = p.matcher(driver.getCurrentUrl());
        if(m.find()){
            String token = m.group(1);
            System.out.println("-> Found authentication token in url ! Token is " + token + ".");
            System.out.println("\n");
            // save token
            SpotifyApiScenarioContext.getInstance().setAuthorizationToken(token);
        }else{
            throw new SpotifyAuthorizationTokenNotFound(driver.getCurrentUrl());
        }
        driver.quit();
    }

    @And("I prepare Spotify Api request with token from scenario")
    public void iPrepareRequestWithSpotifyTokenFromScenario() {
        System.out.println("---------- STEP");
        System.out.println("'I prepare request with spotify token from scenario'");
        System.out.println("\n");
        System.out.println("- Preparing request with Spotify authorization token in Headers ...");
        System.out.println("\n");
        String token = SpotifyApiScenarioContext.getInstance().getAuthorizationToken();
        RequestSpecification scnReqSpec = ApiScenarioContext.getInstance().getRequestSpecification();
        RequestSpecification newReqSpec = (scnReqSpec != null) ? scnReqSpec : RestAssured.given();
        Header header = new Header("Authorization", "Bearer " + token);
        RequestSpecification preparedRequest = newReqSpec.header(header);
        ApiScenarioContext.getInstance().setRequestSpecification(preparedRequest);
        System.out.println("-> Request prepared with Authorization Header : " + header.toString() + " !");
        System.out.println("\n");    }
}
