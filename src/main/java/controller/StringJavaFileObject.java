package controller;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

public class StringJavaFileObject extends SimpleJavaFileObject {
    private final CharSequence sourceCode;

    public StringJavaFileObject(String name, CharSequence sourceCode) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);

        this.sourceCode = sourceCode;
    }

    @Override
    public CharSequence getCharContent(boolean ignoredEncodingErrors) {
        return sourceCode;
    }
}
