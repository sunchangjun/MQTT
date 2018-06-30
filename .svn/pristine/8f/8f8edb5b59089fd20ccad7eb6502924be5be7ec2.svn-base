package framework.core.utils.dynacompile;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * Provides simple implementations for most methods in JavaFileObject.
 * Created by zeyuphoenix on 16/9/2.
 */
public class JavaClassObject extends SimpleJavaFileObject {

    // ================================================================
    // Constants
    // ================================================================

    // ================================================================
    // Fields
    // ================================================================

    // 输出流
    protected final ByteArrayOutputStream bos = new ByteArrayOutputStream();

    // ================================================================
    // Constructors
    // ================================================================

    /**
     * Construct a SimpleJavaFileObject of the given kind and with the
     * given URI.
     *  @param name  the name for this file object
     * @param kind the kind of this file object
     */
    public JavaClassObject(String name, JavaFileObject.Kind kind) {
        super(URI.create("string:///" + name.replace('.', '/') + kind.extension), kind);
    }

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    @Override
    public OutputStream openOutputStream() throws IOException {
        return bos;
    }

    // ================================================================
    // Public or Protected Methods
    // ================================================================

    public byte[] getBytes() {
        return bos.toByteArray();
    }

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
