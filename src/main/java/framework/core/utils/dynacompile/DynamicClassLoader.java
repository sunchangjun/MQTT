package framework.core.utils.dynacompile;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * used to load classes and resources from a search path
 * Created by zeyuphoenix on 16/9/2.
 */
public class DynamicClassLoader extends URLClassLoader {

    // ================================================================
    // Constants
    // ================================================================

    // ================================================================
    // Fields
    // ================================================================

    // ================================================================
    // Constructors
    // ================================================================

    /**
     * Constructs a new URLClassLoader
     */
    public DynamicClassLoader(ClassLoader parent) {
        super(new URL[0], parent);
    }

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    // ================================================================
    // Public or Protected Methods
    // ================================================================

    public Class<?> findClassByClassName(String className) throws ClassNotFoundException {
        return super.findClass(className);
    }

    public Class<?> loadClass(String fullName, JavaClassObject jco) {
        byte[] classData = jco.getBytes();
        return super.defineClass(fullName, classData, 0, classData.length);
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
