package translate;

import java.io.IOException;
import java.util.Scanner;

public class TranslateApplication {
    public static void main(String[] args) throws IOException {
        System.out.print("Введите фразу, которую необходимо перевести: ");
        Scanner in = new Scanner(System.in);
        String text = in.nextLine();

        String result;
        try {
            result = Translator.translate(text);
        } catch (TranslatorException ex) {
             System.out.println(ex.getMessage());
             return;
        }

        System.out.println("Перевод: " + result + " (Переведено сервисом Яндекс.Переводчик http://translate.yandex.ru/)");
    }
}
