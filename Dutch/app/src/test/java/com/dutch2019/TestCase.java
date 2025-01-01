package com.dutch2019;

import static com.dutch2019.SimpleWebServer.serveScreenshotSubset;
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
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.options.BaseOptions;

@RunWith(RobolectricTestRunner.class)
public class TestCase {

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

        if (initCount == afterSize - 1) {
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
            if (element.isDisplayed()) {
                isTestSuccess = true;
            }
        } catch (Exception e) {
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

        if (initCount == afterSize + 1) {
            isTestSuccess = true;
        }

        Assert.assertEquals(true, isTestSuccess);
    }

    private void tc2_markerBlackCheck() {
        boolean isTestSuccess = false;


        String listFirstItemPath = "(//android.view.ViewGroup[@resource-id=\"com.dutch2019:id/layout_search\"])[1]";
        driver.findElement(By.xpath(listFirstItemPath)).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String editTextPath = "//android.widget.EditText[@resource-id=\"com.dutch2019:id/et_search\"]";
        WebElement editTextElement = driver.findElement(By.xpath(editTextPath));
        editTextElement.click();
        editTextElement.sendKeys("1");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));

        String path = "(//android.view.ViewGroup[@resource-id=\"com.dutch2019:id/layout_search\"])[1]";
        driver.findElement(By.xpath(path)).click();

        driver.findElement(By.id("com.dutch2019:id/btn_set_location")).click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        byte[] screenshotBytes = driver.getScreenshotAs(OutputType.BYTES);
        Bitmap screenshot = BitmapFactory.decodeByteArray(screenshotBytes, 0, screenshotBytes.length);

        String markerPath = "(//android.widget.FrameLayout[@resource-id=\"com.dutch2019:id/layout_marker\"])[1]";
        WebElement markerElement = driver.findElement(By.xpath(markerPath));
        int markerX = markerElement.getLocation().x;
        int markerY = markerElement.getLocation().y;
        System.out.println("markerX:" + markerX);
        System.out.println("markerY:" + markerY);
        int markerColor = screenshot.getPixel(markerX + 10, markerY + 10);
        int expectedMarkerColor = Color.rgb(71, 71, 71); // Marker Color Gray

        try {
            serveScreenshotSubset(screenshot, markerX, markerY, markerElement.getSize().width, markerElement.getSize().height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("MC:" + Color.red(markerColor));
        System.out.println("EMC:" + Color.red(expectedMarkerColor));


        if (Color.red(markerColor) == Color.red(expectedMarkerColor) && Color.blue(markerColor) == Color.blue(expectedMarkerColor) && Color.green(markerColor) == Color.green(expectedMarkerColor)) {
            System.out.println("Marker Color Pass");
            isTestSuccess = true;
        }

        Assert.assertEquals(true, isTestSuccess);
    }

    private void tc1_markerGrayCheck() {
        boolean isTestSuccess = false;

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        byte[] screenshotBytes = driver.getScreenshotAs(OutputType.BYTES);
        Bitmap screenshot = BitmapFactory.decodeByteArray(screenshotBytes, 0, screenshotBytes.length);

        String markerPath = "(//android.widget.FrameLayout[@resource-id=\"com.dutch2019:id/layout_marker\"])[1]";
        WebElement markerElement = driver.findElement(By.xpath(markerPath));
        int markerX = markerElement.getLocation().x;
        int markerY = markerElement.getLocation().y;

        int markerColor = screenshot.getPixel(markerX + 10, markerY + 10);
        int expectedMarkerColor = Color.rgb(211, 211, 211); // Marker Color Light Gray

        try {
            serveScreenshotSubset(screenshot, markerX, markerY, markerElement.getSize().width, markerElement.getSize().height);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("MC:" + Color.red(markerColor));
        System.out.println("EMC:" + Color.red(expectedMarkerColor));


        if (Color.red(markerColor) == Color.red(expectedMarkerColor) && Color.blue(markerColor) == Color.blue(expectedMarkerColor) && Color.green(markerColor) == Color.green(expectedMarkerColor)) {
            System.out.println("Marker Color Pass");
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

