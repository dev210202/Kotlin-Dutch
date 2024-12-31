package com.dutch2019;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.options.BaseOptions;

public class TestCase {

    private AndroidDriver driver;

    @Before
    public void setUp() {
        BaseOptions options = new BaseOptions()
                .amend("platformName", "Android")
                .amend("appium:automationName", "UiAutomator2")
                .amend("appium:app", "/Users/user/Desktop/app-release.apk")
                .amend("appium:ensureWebviewsHavePages", true)
                .amend("appium:nativeWebScreenshot", true)
                .amend("appium:newCommandTimeout", 3600);

        driver = new AndroidDriver(getUrl(), options);
    }

    private URL getUrl() {
        try {
            return new URL("http://127.0.0.1:4723");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid URL");
        }
    }

    @Test
    public void main() {
        tc5_clickAddLocationButton();
    }


    private void tc5_clickAddLocationButton() {
        boolean isTestSuccess = false;

        List<WebElement> elements = driver.findElements(By.id("com.dutch2019:id/layout_search"));
        int initCount = elements.size();

        String addLocationButtonPath = "//android.view.ViewGroup[@resource-id=\"com.dutch2019:id/layout_location_add\"]";
        driver.findElement(By.xpath(addLocationButtonPath)).click();

        List<WebElement> AfterElements = driver.findElements(By.id("com.dutch2019:id/layout_search"));
        int afterSize = AfterElements.size(); // 두 번째 요소 (인덱스 1)

        System.out.println("beforeSize: " + initCount);
        System.out.println("afterSize: " + afterSize);

        if(initCount == afterSize - 1){
            isTestSuccess = true;
        }

        Assert.assertEquals(true, isTestSuccess);
    }

    private void tc4_clickListItem() {
        boolean isTestSuccess = false;

        String listFirstItemPath = "(//android.view.ViewGroup[@resource-id=\"com.dutch2019:id/layout_search\"])[1]";
        driver.findElement(By.xpath(listFirstItemPath)).click();

        String editTextPath = "//android.widget.EditText[@resource-id=\"com.dutch2019:id/et_search\"]";

        try {
            WebElement element = driver.findElement(By.xpath(editTextPath));
            if(element.isDisplayed()){
                isTestSuccess = true;
            }
        } catch (Exception e){
            System.out.println("테스트 실패, 오류 원인: \n\n" + e.getMessage() + "\n\n");
        }
        Assert.assertEquals(true, isTestSuccess);

    }

    private void tc3_ClickListItemDeleteButton() {
        boolean isTestSuccess = false;

        List<WebElement> elements = driver.findElements(By.id("com.dutch2019:id/layout_search"));
        int initCount = elements.size();

        String closeButtonPath = "(//android.widget.ImageView[@resource-id=\"com.dutch2019:id/btn_close\"])[1]";
        driver.findElement(By.xpath(closeButtonPath)).click();

        List<WebElement> AfterElements = driver.findElements(By.id("com.dutch2019:id/layout_search"));
        int afterSize = AfterElements.size(); // 두 번째 요소 (인덱스 1)

        System.out.println("beforeSize: " + initCount);
        System.out.println("afterSize: " + afterSize);

        if(initCount == afterSize + 1){
            isTestSuccess = true;
        }

        Assert.assertEquals(true, isTestSuccess);
    }


    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
