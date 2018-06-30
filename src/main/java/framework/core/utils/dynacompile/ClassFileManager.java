package framework.core.utils.dynacompile;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import java.io.IOException;

/**
 * Forwards calls to a given file manager.
 * Created by zeyuphoenix on 16/9/2.
 */
public class ClassFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    // ================================================================
    // Constants
    // ================================================================

    // ================================================================
    // Fields
    // ================================================================

    private JavaClassObject javaClassObject;

    // ================================================================
    // Constructors
    // ================================================================

    /**
     * Creates a new instance of ForwardingJavaFileManager.

     * @param standardManager delegate to this file manager
     */
    protected ClassFileManager(StandardJavaFileManager standardManager) {
        super(standardManager);
    }

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className,
                                               JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        javaClassObject = new JavaClassObject(className, kind);
        return javaClassObject;
    }

    // ================================================================
    // Public or Protected Methods
    // ================================================================

    public JavaClassObject getJavaClassObject() {
        return javaClassObject;
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
