package gal.sdc.usc.risk.correccion;

import java.util.ArrayList;
import java.util.Iterator;

public class DatosArray implements Iterable<Object> {
    private final ArrayList<Object> list = new ArrayList<>();

    public DatosArray(Parseador x) {
        if (x.nextClean() != '[') {
            System.err.println("A CorrectorArray text must start with '['");
            return;
        }

        char nextChar = x.nextClean();
        if (nextChar == 0) {
            System.out.println(x);
            // array is unclosed. No ']' found, instead EOF
            System.err.println("Expected a ',' or ']'");
            return;
        }
        if (nextChar != ']') {
            x.back();
            for (; ; ) {
                if (x.nextClean() == ',') {
                    x.back();
                    this.list.add(null);
                } else {
                    x.back();
                    this.list.add(x.nextValue());
                }
                switch (x.nextClean()) {// array is unclosed. No ']' found, instead EOF
                    case ',':
                        nextChar = x.nextClean();
                        if (nextChar == 0) {
                            // array is unclosed. No ']' found, instead EOF
                            System.err.println("Expected a ',' or ']'");
                            return;
                        }
                        if (nextChar == ']') {
                            return;
                        }
                        x.back();
                        break;
                    case ']':
                        return;
                    case 0:
                    default:
                        System.err.println("Expected a ',' or ']'");
                        return;
                }
            }
        }
    }

    @Override
    public Iterator<Object> iterator() {
        return this.list.iterator();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("[ ");
        for (int i = 0; i < list.size(); i++) {
            out.append(list.get(i).toString());
            if (i != (list.size() - 1)) {
                out.append(", ");
            }
        }
        out.append(" ]");
        return out.toString();
    }
}
