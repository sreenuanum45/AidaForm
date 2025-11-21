package reporting;

import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class AllureHelper {
    public static void attachScreenshot(byte[] bytes, String name) {
        if (bytes == null) return;
        try (InputStream is = new ByteArrayInputStream(bytes)) {
            Allure.addAttachment(name, "image/png", is, "png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void attachText(String name, String text) {
        if (text == null) return;
        Allure.addAttachment(name, text);
    }
}

