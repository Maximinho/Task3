package translate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Translator {

    public static String translate(String text) throws TranslatorException, IOException {
        URL url = new URL("https://translate.yandex.net/api/v1.5/tr.json/translate");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        Map<String, String> params = new HashMap<>();
        params.put("key", "trnsl.1.1.20190528T190411Z.3f8682a0ad943185.ad99c8f6e8c3a5a4d3dacac5c0d213949588ecba");
        params.put("lang", "en-ru");
        params.put("text", text);

        writeURLParams(connection, params);

        int status = connection.getResponseCode();
        switch (status) {
            case 200:
                JsonNode jsonNode = readResponse(connection);
                return  jsonNode.get("text").get(0).asText();
            case 404:
                throw new TranslatorException("Превышено суточное ограничение на объем переведенного текста");
            case 413:
                throw new TranslatorException("Превышен максимально допустимый размер текста");
            case 422:
                throw new TranslatorException("Текст не может быть переведен");
            default:
                throw new TranslatorException("Непредвиденная ошибка");
        }
    }

    private static String getURLParamString(Map<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String,String> param : params.entrySet()) {
            result.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(param.getValue(), "UTF-8"));
            result.append("&");
        }

        return result.length() > 0 ? result.substring(0, result.length() - 1) : result.toString();
    }

    private static void writeURLParams(HttpURLConnection connection, Map<String, String> params) throws IOException {
        connection.setDoOutput(true);
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.writeBytes(getURLParamString(params));
        outputStream.flush();
        outputStream.close();
    }

    private static JsonNode readResponse(HttpURLConnection connection) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.readTree(connection.getInputStream());
        connection.disconnect();

        return jsonNode;
    }
}
