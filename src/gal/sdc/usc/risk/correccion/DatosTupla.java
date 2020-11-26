package gal.sdc.usc.risk.correccion;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DatosTupla {
    boolean dupla = false;
    Map<String, Object> map = new HashMap<>();

    public Object opt(String key) {
        return key == null ? null : this.map.get(key);
    }

    public DatosTupla put(String key, Object value) {
        if (key == null) {
            throw new NullPointerException("Null key.");
        }
        if (value != null) {
            this.map.put(key, value);
        } else {
            this.remove(key);
        }
        return this;
    }

    public Object remove(String key) {
        return this.map.remove(key);
    }

    public DatosTupla(Parseador x) {
        System.out.println(x.s());
        char c;
        String key;

        if (x.nextClean() != '{') {
            System.err.println("A JSONObject text must begin with '{'");
            return;
        }
        for (; ; ) {
            c = x.nextClean();
            switch (c) {
                case 0:
                    System.err.println("A JSONObject text must end with '}'");
                    return;
                case '}':
                    return;
                default:
                    x.back();
                    key = x.nextValue().toString();
            }

            // The key is followed by ':'.

            c = x.nextClean();
            if (c != ':') {
                System.err.println("Expected a ':' after a key");
                return;
            }

            // Use syntaxError(..) to include error location

            if (key != null) {
                // Check if key exists
                if (this.opt(key) != null) {
                    // key already exists
                    System.err.println("Duplicate key \"" + key + "\"");
                    return;
                }
                // Only add value if non-null
                Object value = x.nextValue();
                if (value != null) {
                    this.put(key, value);
                }
            }

            // Pairs are separated by ','.

            switch (x.nextClean()) {
                case ';':
                case ',':
                    if (x.nextClean() == '}') {
                        return;
                    }
                    x.back();
                    break;
                case '}':
                    return;
                default:
                    System.err.println(x.s());
                    System.err.println("> Expected a ',' or '}'");
                    return;
            }
        }
    }

    public static Object stringToValue(String string) {
        if ("".equals(string)) {
            return string;
        }

        // check JSON key words true/false/null
        if ("true".equalsIgnoreCase(string)) {
            return Boolean.TRUE;
        }
        if ("false".equalsIgnoreCase(string)) {
            return Boolean.FALSE;
        }
        if ("null".equalsIgnoreCase(string)) {
            return null;
        }

        /*
         * If it might be a number, try converting it. If a number cannot be
         * produced, then the value will just be a string.
         */

        char initial = string.charAt(0);
        if ((initial >= '0' && initial <= '9') || initial == '-') {
            try {
                return Integer.valueOf(string);
            } catch (Exception ignore) {
            }
        }
        return string;
    }

    @Override
    public boolean equals(Object object) {
        return object == null || object == this;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("{");
        out.append(" ");
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entrada = it.next();
            out.append("\"").append(entrada.getKey()).append("\"")
                    .append(dupla ? ", " : ": ")
                    .append(entrada.getValue().toString())
                    .append(it.hasNext() ? ", " : "");
        }
        out.append(" ");
        out.append("}");
        return out.toString();
    }
}
