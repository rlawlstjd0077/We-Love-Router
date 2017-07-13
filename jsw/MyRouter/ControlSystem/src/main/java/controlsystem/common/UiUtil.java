package controlsystem.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.text.Font;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

import java.io.IOException;

/**
 * Ui에 공통적으로 사용되는 기능을 제공
 */
public class UiUtil {
    private static GlyphFont fontAwesome;

    public static void initializeFont() {
        String[] fonts = new String[]{
                "Roboto-Black.ttf",
                "Roboto-BlackItalic.ttf",
                "Roboto-Bold.ttf",
                "Roboto-BoldItalic.ttf",
                "Roboto-Italic.ttf",
                "Roboto-Light.ttf",
                "Roboto-LightItalic.ttf",
                "Roboto-Medium.ttf",
                "Roboto-MediumItalic.ttf",
                "Roboto-Regular.ttf",
                "Roboto-Thin.ttf",
                "Roboto-ThinItalic.ttf",
                "RobotoCondensed-Bold.ttf",
                "RobotoCondensed-BoldItalic.ttf",
                "RobotoCondensed-Italic.ttf",
                "RobotoCondensed-Light.ttf",
                "RobotoCondensed-LightItalic.ttf",
                "RobotoCondensed-Regular.ttf",
        };
        for (String font : fonts) {
            Font.loadFont(UiUtil.class.getResourceAsStream("/commons/ui/fonts/" + font), 12);
        }
        fontAwesome = GlyphFontRegistry.font("FontAwesome");
    }

    public static FXMLLoader getFxmlLoader(Class<?> clazz) {
        FXMLLoader loader = new FXMLLoader();
        final String fxmlName = clazz.getSimpleName().replace("Controller", "") + ".fxml";
        loader.setLocation(clazz.getResource(fxmlName));
        return loader;
    }


    /**
     * FXML을 MyController 이름을 기반으로 추출하여 읽고 등록.
     *
     * @param controller MyController 인스턴스.
     * @throws IOException 파일이 없을 때 발생.
     */
    public static void loadFxml(Parent controller) throws IOException {
        final FXMLLoader loader = getFxmlLoader(controller.getClass());
        loader.setRoot(controller);
        loader.setController(controller);
        loader.load();
    }
}
