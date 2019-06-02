package translate;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TranslatorTest {

    @Test
    void translateTest() throws IOException, TranslatorException {
        String expected = "Автомобиль";
        String actual = Translator.translate("Car");
        assertEquals(expected, actual);
    }
}