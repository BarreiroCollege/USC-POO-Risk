package gal.sdc.usc.risk.correccion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * A  Test takes a source string and extracts characters and tokens from
 * it. It is used by the JSONObject and JSONArray constructors to parse
 * JSON source strings.
 *
 * @author JSON.org
 * @version 2014-05-03
 */
public class Parseador {
    /**
     * current read character position on the current line.
     */
    private long character;
    /**
     * flag to indicate if the end of the input has been found.
     */
    private boolean eof;
    /**
     * current read index of the input.
     */
    private long index;
    /**
     * current line of the input.
     */
    private long line;
    /**
     * previous character read from the input.
     */
    private char previous;
    /**
     * Reader for the input.
     */
    private final Reader reader;
    /**
     * flag to indicate that a previous character was requested.
     */
    private boolean usePrevious;
    /**
     * the number of characters read in the previous line.
     */
    private long characterPreviousLine;


    /**
     * Construct a  Test from a string.
     *
     * @param s A source string.
     */
    public Parseador(String s) {
        s = s.replaceAll("(\\w+):", "");
        Reader reader = new StringReader(s);
        this.reader = reader.markSupported()
                ? reader
                : new BufferedReader(reader);
        this.eof = false;
        this.usePrevious = false;
        this.previous = 0;
        this.index = 0;
        this.character = 1;
        this.characterPreviousLine = 0;
        this.line = 1;
    }


    /**
     * Back up one character. This provides a sort of lookahead capability,
     * so that you can test for a digit or letter before attempting to parse
     * the next number or identifier.
     */
    public void back() {
        if (this.usePrevious || this.index <= 0) {
            return;
        }
        this.decrementIndexes();
        this.usePrevious = true;
        this.eof = false;
    }

    /**
     * Decrements the indexes for the {@link #back()} method based on the previous character read.
     */
    private void decrementIndexes() {
        this.index--;
        if (this.previous == '\r' || this.previous == '\n') {
            this.line--;
            this.character = this.characterPreviousLine;
        } else if (this.character > 0) {
            this.character--;
        }
    }

    /**
     * Checks if the end of the input has been reached.
     *
     * @return true if at the end of the file and we didn't step back
     */
    public boolean end() {
        return this.eof && !this.usePrevious;
    }


    /**
     * Get the next character in the source string.
     *
     * @return The next character, or 0 if past the end of the source string.
     */
    public char next() {
        int c;
        if (this.usePrevious) {
            this.usePrevious = false;
            c = this.previous;
        } else {
            try {
                c = this.reader.read();
            } catch (IOException exception) {
                return this.previous;
            }
        }
        if (c <= 0) { // End of stream
            this.eof = true;
            return 0;
        }
        this.incrementIndexes(c);
        this.previous = (char) c;
        return this.previous;
    }

    /**
     * Increments the internal indexes according to the previous character
     * read and the character passed as the current character.
     *
     * @param c the current character read.
     */
    private void incrementIndexes(int c) {
        if (c > 0) {
            this.index++;
            if (c == '\r') {
                this.line++;
                this.characterPreviousLine = this.character;
                this.character = 0;
            } else if (c == '\n') {
                if (this.previous != '\r') {
                    this.line++;
                    this.characterPreviousLine = this.character;
                }
                this.character = 0;
            } else {
                this.character++;
            }
        }
    }


    /**
     * Get the next n characters.
     *
     * @param n The number of characters to take.
     * @return A string of n characters.
     */
    public String next(int n) {
        if (n == 0) {
            return "";
        }

        char[] chars = new char[n];
        int pos = 0;

        while (pos < n) {
            chars[pos] = this.next();
            if (this.end()) {
                return "";
            }
            pos += 1;
        }
        return new String(chars);
    }


    /**
     * Get the next char in the string, skipping whitespace.
     *
     * @return A character, or 0 if there are no more characters.
     */
    public char nextClean() {
        for (; ; ) {
            char c = this.next();
            if (c == 0 || c > ' ') {
                return c;
            }
        }
    }


    /**
     * Return the characters up to the next close quote character.
     * Backslash processing is done. The formal JSON format does not
     * allow strings in single quotes, but an implementation is allowed to
     * accept them.
     *
     * @param quote The quoting character, either
     *              <code>"</code>&nbsp;<small>(double quote)</small> or
     *              <code>'</code>&nbsp;<small>(single quote)</small>.
     * @return A String.
     */
    public String nextString(char quote) {
        char c;
        StringBuilder sb = new StringBuilder();
        for (; ; ) {
            c = this.next();
            switch (c) {
                case 0:
                case '\n':
                case '\r':
                    break;
                case '\\':
                    c = this.next();
                    switch (c) {
                        case 'b':
                            sb.append('\b');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'f':
                            sb.append('\f');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 'u':
                            try {
                                sb.append((char) Integer.parseInt(this.next(4), 16));
                            } catch (NumberFormatException e) {
                                break;
                            }
                            break;
                        case '"':
                        case '\'':
                        case '\\':
                        case '/':
                            sb.append(c);
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    if (c == quote) {
                        System.out.println(sb.toString());
                        return sb.toString();
                    }
                    sb.append(c);
            }
        }
    }


    /**
     * Get the next value. The value can be a Boolean, Double, Integer,
     * JSONArray, JSONObject, Long, or String, or the JSONObject.NULL object.
     *
     * @return An object.
     */
    public Object nextValue() {
        char c = this.nextClean();
        String string;

        switch (c) {
            case '"':
            case '\'':
                return this.nextString(c);
            case '{':
                this.back();
                return new DatosDiccionario(this);
            case '[':
                this.back();
                return new DatosArray(this);
        }

        /*
         * Handle unquoted text. This could be the values true, false, or
         * null, or it can be a number. An implementation (such as this one)
         * is allowed to also accept non-standard forms.
         *
         * Accumulate characters until we reach the end of the text or a
         * formatting character.
         */

        StringBuilder sb = new StringBuilder();
        while (c >= ' ' && ":]}/\\\"[{;=#".indexOf(c) < 0) {
            sb.append(c);
            c = this.next();
        }
        if (!this.eof) {
            this.back();
        }

        string = sb.toString().trim();
        if ("".equals(string)) {
            return null;
        }
        return DatosDiccionario.stringToValue(string);
    }

    public String s() {
        try {
            int intValueOfChar;
            StringBuilder targetString = new StringBuilder();
            while ((intValueOfChar = reader.read()) != -1) {
                targetString.append((char) intValueOfChar);
            }
            return targetString.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Make a printable string of this  Test.
     *
     * @return " at {index} [character {character} line {line}]"
     */
    @Override
    public String toString() {
        return " at " + this.index + " [character " + this.character + " line " +
                this.line + "]";
    }
}