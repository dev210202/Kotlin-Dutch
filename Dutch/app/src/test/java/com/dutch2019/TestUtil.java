package com.dutch2019;

import android.graphics.Bitmap;
import android.graphics.Color;

import org.openqa.selenium.WebElement;

public class TestUtil {
    protected static int getElementColor(WebElement element, Bitmap screenshot) {
        int elementX = element.getLocation().x;
        int elementY = element.getLocation().y;
        return screenshot.getPixel(elementX + 10, elementY + 10);
    }

    protected static boolean isSameColor(int color, int expectedColor) {
        return Color.red(color) == Color.red(expectedColor) && Color.blue(color) == Color.blue(expectedColor) && Color.green(color) == Color.green(expectedColor);
    }

    protected static void waitUntil(int second){
        try {
            Thread.sleep(second * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
