package json.parser;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class NewJsonParser {
    public final static int GET = 1;
    public final static int POST = 2;
    static StringBuilder result;

    // constructor
    public NewJsonParser() {
    }

    public static String getNewJSONFromParams(HashMap<String, String> params) throws UnsupportedEncodingException {
        result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            try {
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}