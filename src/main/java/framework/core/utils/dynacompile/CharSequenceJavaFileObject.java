package framework.core.utils.dynacompile;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.net.URI;

/**
 * Provides simple implementations for most methods in JavaFileObject.
 * Created by zeyuphoenix on 16/9/2.
 */
public class CharSequenceJavaFileObject extends SimpleJavaFileObject {

    // ================================================================
    // Constants
    // ================================================================

    // ================================================================
    // Fields
    // ================================================================

    /**
     * content
     */
    private CharSequence content;

    // ================================================================
    // Constructors
    // ================================================================

    /**
     * Construct a SimpleJavaFileObject of the given kind and with the
     * given class and content.
     */
    protected CharSequenceJavaFileObject(String className, CharSequence content) {
        super(URI.create("string:///" + className.replace('.', '/') + JavaFileObject.Kind.SOURCE.extension),
                JavaFileObject.Kind.SOURCE);
        this.content = content;
    }

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return content;
    }

    // ================================================================
    // Public or Protected Methods
    // ================================================================

    // ================================================================
    // Getter & Setter
    // ================================================================

    // ================================================================
    // Private Methods
    // ================================================================

    // ================================================================
    // Inner or Anonymous Class
    // ================================================================

    // ================================================================
    // Test Methods
    // ================================================================

}
