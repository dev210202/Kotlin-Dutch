package com.dutch2019;

import static com.dutch2019.SimpleWebServer.serveScreenshotSubset;
import static com.dutch2019.TestUtil.getElementColor;
import static com.dutch2019.TestUtil.isSameColor;
import static com.dutch2019.TestUtil.waitUntil;

import io.appium.java_client.android.connection.ConnectionState;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.android.nativekey.AndroidKey;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.options.BaseOptions;

@RunWith(RobolectricTestRunner.class)
public class TestCase {
    String listFirstItemPath = "(//android.view.ViewGroup[@resource-id=\"com.dutch2019:id/layout_search\"])[1]";
    String listSecondItemPath = "(//android.view.ViewGroup[@resource-id=\"com.dutch2019:id/layout_search\"])[2]";

    String editTextPath = "//android.widget.EditText[@resource-id=\"com.dutch2019:id/et_search\"]";
    String searchListFirstItemPath = "(//android.view.ViewGroup[@resource-id=\"com.dutch2019:id/layout_search\"])[1]";
    String noHistoryTextPath = "//android.widget.TextView[@resource-id=\"com.dutch2019:id/tv_empty\"]";
    String editHistoryButtonPath = "//android.widget.Button[@resource-id=\"com.dutch2019:id/btn_edit\"]";
    String middleLocationButtonPath = "//android.widget.Button[@resource-id=\"com.dutch2019:id/btn_find_middlelocation\"]";
    String addLocationButtonPath = "//android.view.ViewGroup[@resource-id=\"com.dutch2019:id/layout_location_add\"]";
    String closeButtonPath = "(//android.widget.ImageView[@resource-id=\"com.dutch2019:id/btn_close\"])[1]";
    String listFirstMarkerPath = "(//android.widget.FrameLayout[@resource-id=\"com.dutch2019:id/layout_marker\"])[1]";

    String middleLocationInfoTextPath = "//android.widget.TextView[@resource-id=\"com.dutch2019:id/tv_info\"]";
    private AndroidDriver driver;

    @Before
    public void setUp() {
        BaseOptions options = new BaseOptions().amend("platformName", "Android").amend("appium:automationName", "UiAutomator2").amend("appium:app", "/Users/user/Desktop/app-release.apk").amend("appium:ensureWebviewsHavePages", true).amend("appium:nativeWebScreenshot", true).amend("appium:newCommandTimeout", 3600);

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
        test();
    }

    private void test() {

    }

    private void tc10_checkDisplayNoHistory() {
        boolean isTestSuccess = false;
        boolean isNoHistoryTextDisplayed = false;
        boolean isEditHistoryButtonDisplayed = false;

        driver.findElement(By.xpath(listFirstItemPath)).click();
        waitUntil(3);

        try {
            WebElement noHistoryTextElement = driver.findElement(By.xpath(noHistoryTextPath));
            if(noHistoryTextElement.isDisplayed()){
                isNoHistoryTextDisplayed = true;
            }
            WebElement editHistoryButton = driver.findElement(By.xpath(editHistoryButtonPath));
            if(editHistoryButton.isDisplayed()){
                isEditHistoryButtonDisplayed = true;
            }
        } catch (Exception ignored){

        }
        if(isNoHistoryTextDisplayed && !isEditHistoryButtonDisplayed){
            isTestSuccess = true;
        }
        Assert.assertEquals(true, isTestSuccess);
    }
    private void tc9_checkMiddleLocationButtonNoWiFi() {
        boolean isTestSuccess = false;

        setFirstLocation();
        setSecondLocation();

        ConnectionState state = driver.setConnection(new ConnectionStateBuilder()
                .withWiFiDisabled()
                .build());

        System.out.println("WiFi State: " + state.isWiFiEnabled());

        waitUntil(2);

        WebElement middleLocationButtonElement = driver.findElement(By.xpath(middleLocationButtonPath));
        middleLocationButtonElement.click();

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
            WebElement toastElement = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.Toast[@text=\"인터넷 연결을 확인해주세요!\"]"))
            );

            String toastText = toastElement.getAttribute("name");
            System.out.println("toastMessage: " + toastText);

            if (toastText.equals("인터넷 연결을 확인해주세요!")) {
                isTestSuccess = true;
            }
        } catch (Exception e) {
            System.out.println("테스트 실패, 오류 원인: \n\n" + e.getMessage() + "\n\n");
        }
        driver.setConnection(new ConnectionStateBuilder()
                .withWiFiEnabled().build());
        Assert.assertEquals(true, isTestSuccess);
    }

    private void tc8_clickMiddleLocationButton() {
        boolean isTestSuccess = false;

        setFirstLocation();
        setSecondLocation();

        WebElement middleLocationButtonElement = driver.findElement(By.xpath(middleLocationButtonPath));
        middleLocationButtonElement.click();

        waitUntil(3);
        try {
            WebElement middleLocationInfoTextElement = driver.findElement(By.xpath(middleLocationInfoTextPath));
            if (middleLocationInfoTextElement.isDisplayed()) {
                isTestSuccess = true;
            }
        } catch (Exception e) {
            System.out.println("테스트 실패, 오류 원인: \n\n" + e.getMessage() + "\n\n");
        }
        Assert.assertEquals(true, isTestSuccess);
    }

    private void tc7_checkMiddleLocationButtonEnabled() {
        boolean isTestSuccess = false;

        setFirstLocation();
        setSecondLocation();

        waitUntil(3);

        byte[] screenshotBytes = driver.getScreenshotAs(OutputType.BYTES);
        Bitmap screenshot = BitmapFactory.decodeByteArray(screenshotBytes, 0, screenshotBytes.length);

        WebElement middleLocationButtonElement = driver.findElement(By.xpath(middleLocationButtonPath));

        int middleLocationButtonColor = getElementColor(middleLocationButtonElement, screenshot);
        int expectedMiddleLocationButtonColor = Color.rgb(239, 75, 51);

        try {
            serveScreenshotSubset(screenshot, middleLocationButtonElement);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("MC:" + Color.red(middleLocationButtonColor));
        System.out.println("EMC:" + Color.red(expectedMiddleLocationButtonColor));

        if (isSameColor(middleLocationButtonColor, expectedMiddleLocationButtonColor)) {
            System.out.println("Color Check Pass");
            isTestSuccess = true;
        }

        Assert.assertEquals(true, isTestSuccess);
    }


    private void tc6_CheckMiddleLocationButtonDisabled() {
        boolean isTestSuccess = false;

        waitUntil(3);


        byte[] screenshotBytes = driver.getScreenshotAs(OutputType.BYTES);
        Bitmap screenshot = BitmapFactory.decodeByteArray(screenshotBytes, 0, screenshotBytes.length);

        WebElement middleLocationButtonElement = driver.findElement(By.xpath(middleLocationButtonPath));

        int middleLocationButtonColor = getElementColor(middleLocationButtonElement, screenshot);
        int expectedMiddleLocationButtonColor = Color.rgb(42, 42, 42);

        try {
            serveScreenshotSubset(screenshot, middleLocationButtonElement);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("MC:" + Color.red(middleLocationButtonColor));
        System.out.println("EMC:" + Color.red(expectedMiddleLocationButtonColor));

        if (isSameColor(middleLocationButtonColor, expectedMiddleLocationButtonColor)) {
            System.out.println("Color Check Pass");
            isTestSuccess = true;
        }

        Assert.assertEquals(true, isTestSuccess);
    }


    private void tc5_clickAddLocationButton() {
        boolean isTestSuccess = false;

        List<WebElement> elements = driver.findElements(By.id("com.dutch2019:id/layout_search"));
        int initCount = elements.size();

        driver.findElement(By.xpath(addLocationButtonPath)).click();

        List<WebElement> AfterElements = driver.findElements(By.id("com.dutch2019:id/layout_search"));
        int afterSize = AfterElements.size(); // 두 번째 요소 (인덱스 1)

        System.out.println("beforeSize: " + initCount);
        System.out.println("afterSize: " + afterSize);

        if (initCount == afterSize - 1) {
            isTestSuccess = true;
        }

        Assert.assertEquals(true, isTestSuccess);
    }

    private void tc4_clickListItem() {
        boolean isTestSuccess = false;

        driver.findElement(By.xpath(listFirstItemPath)).click();

        try {
            WebElement editTextElement = driver.findElement(By.xpath(editTextPath));
            if (editTextElement.isDisplayed()) {
                isTestSuccess = true;
            }
        } catch (Exception e) {
            System.out.println("테스트 실패, 오류 원인: \n\n" + e.getMessage() + "\n\n");
        }
        Assert.assertEquals(true, isTestSuccess);

    }

    private void tc3_clickListItemDeleteButton() {
        boolean isTestSuccess = false;

        List<WebElement> elements = driver.findElements(By.id("com.dutch2019:id/layout_search"));
        int initCount = elements.size();

        driver.findElement(By.xpath(closeButtonPath)).click();

        List<WebElement> AfterElements = driver.findElements(By.id("com.dutch2019:id/layout_search"));
        int afterSize = AfterElements.size(); // 두 번째 요소 (인덱스 1)

        System.out.println("beforeSize: " + initCount);
        System.out.println("afterSize: " + afterSize);

        if (initCount == afterSize + 1) {
            isTestSuccess = true;
        }

        Assert.assertEquals(true, isTestSuccess);
    }

    private void tc2_checkMarkerBlack() {
        boolean isTestSuccess = false;

        setFirstLocation();

        byte[] screenshotBytes = driver.getScreenshotAs(OutputType.BYTES);
        Bitmap screenshot = BitmapFactory.decodeByteArray(screenshotBytes, 0, screenshotBytes.length);

        WebElement markerElement = driver.findElement(By.xpath(listFirstMarkerPath));

        int markerColor = getElementColor(markerElement, screenshot);
        int expectedMarkerColor = Color.rgb(71, 71, 71); // Marker Color Gray

        try {
            serveScreenshotSubset(screenshot, markerElement);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("MC:" + Color.red(markerColor));
        System.out.println("EMC:" + Color.red(expectedMarkerColor));


        if (isSameColor(markerColor, expectedMarkerColor)) {
            System.out.println("Color Check Pass");
            isTestSuccess = true;
        }

        Assert.assertEquals(true, isTestSuccess);
    }

    private void tc1_checkMarkerGray() {
        boolean isTestSuccess = false;

        waitUntil(3);

        byte[] screenshotBytes = driver.getScreenshotAs(OutputType.BYTES);
        Bitmap screenshot = BitmapFactory.decodeByteArray(screenshotBytes, 0, screenshotBytes.length);

        WebElement markerElement = driver.findElement(By.xpath(listFirstMarkerPath));

        int markerColor = getElementColor(markerElement, screenshot);
        int expectedMarkerColor = Color.rgb(211, 211, 211); // Marker Color Light Gray

        try {
            serveScreenshotSubset(screenshot, markerElement);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("MC:" + Color.red(markerColor));
        System.out.println("EMC:" + Color.red(expectedMarkerColor));


        if (isSameColor(markerColor, expectedMarkerColor)) {
            System.out.println("Color Check Pass");
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

    private void setSecondLocation() {
        driver.findElement(By.xpath(listSecondItemPath)).click();
        waitUntil(3);

        WebElement editTextElement = driver.findElement(By.xpath(editTextPath));
        editTextElement.findElement(By.xpath(editTextPath)).click();
        waitUntil(3);

        editTextElement.sendKeys("2");
        waitUntil(3);

        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        waitUntil(3);

        driver.findElement(By.xpath(searchListFirstItemPath)).click();

        driver.findElement(By.id("com.dutch2019:id/btn_set_location")).click();
        waitUntil(3);
    }

    private void setFirstLocation() {
        driver.findElement(By.xpath(listFirstItemPath)).click();
        waitUntil(3);

        WebElement editTextElement = driver.findElement(By.xpath(editTextPath));
        editTextElement.click();
        waitUntil(3);

        editTextElement.sendKeys("1");
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
        waitUntil(3);

        driver.findElement(By.xpath(searchListFirstItemPath)).click();
        waitUntil(3);

        driver.findElement(By.id("com.dutch2019:id/btn_set_location")).click();
        waitUntil(3);
    }
}

