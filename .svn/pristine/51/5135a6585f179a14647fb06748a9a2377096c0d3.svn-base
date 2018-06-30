package framework.core.utils;

import java.io.File;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 得到当前应用的系统路径
 * <pre>
 * new File("..\path\abc.txt") 中的三个方法获取路径的方法
 * 1： getPath() 获取相对路径，例如..\path\abc.txt
 * 2： getAbslutlyPath() 获取绝对路径，但可能包含 ".." 或 "." 字符，例如D:\otherPath\..\path\abc.txt
 * 3： getCanonicalPath() 获取绝对路径，但不包含 ".." 或 "."字符，例如 D:\path\abc.txt
 * </pre>
 */
public class PathUtils {

    // ================================================================
    // Constants
    // ================================================================
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(PathUtils.class);

    // ================================================================
    // Fields
    // ================================================================

    /**
     * The web root path.
     */
    private static String webRootPath;

    /**
     * The root class path.
     */
    private static String rootClassPath;

    // ================================================================
    // Constructors
    // ================================================================

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    // ================================================================
    // Public or Protected Methods
    // ================================================================

    /**
     * Gets the root class path.
     *
     * @return the root class path
     */
    public static String getRootClassPath() {
        if (rootClassPath == null) {
            URL url = PathUtils.class.getClassLoader().getResource("");
            if (url != null) {
                try {
                    String path = url.toURI().getPath();
                    rootClassPath = new File(path).getAbsolutePath();
                } catch (Exception e) {
                    String path = url.getPath();
                    rootClassPath = new File(path).getAbsolutePath();
                }
            }

        }
        return rootClassPath;
    }

    /**
     * Gets the package path.
     *
     * @param object the object
     * @return the package path
     */
    public static String getPackagePath(Object object) {
        Package p = object.getClass().getPackage();
        return p != null ? p.getName().replaceAll("\\.", "/") : "";
    }

    /**
     * Gets the file from jar.
     *
     * @param file the file
     * @return the file from jar
     */
    public static File getFileFromJar(String file) {
        logger.info("get {} file from jar", file);
        throw new RuntimeException("Not finish. Do not use this method.");
    }

    /**
     * Gets the web root path.
     *
     * @return the web root path
     */
    public static String getWebRootPath() {
        if (webRootPath == null)
            webRootPath = detectWebRootPath();
        return webRootPath;
    }

    /**
     * Sets the web root path.
     *
     * @param webRootPath the new web root path
     */
    public static void setWebRootPath(String webRootPath) {
        if (webRootPath == null)
            return;

        if (webRootPath.endsWith(File.separator))
            webRootPath = webRootPath.substring(0, webRootPath.length() - 1);
        PathUtils.webRootPath = webRootPath;
    }

    /**
     * 获取项目根路径
     */
    public static String getWebRoot() {
        URL url = PathUtils.class.getClassLoader().getResource("");
        String path;
        if (url != null) {
            path = url.getPath();
            path = path.replace("\\", "/");
            if (path.contains("/WEB-INF/")) {// web项目
                path = path.substring(0, path.indexOf("/WEB-INF/"));
            } else if (path.contains("/bin/")) {// java项目
                path = path.substring(0, path.indexOf("/bin/"));
            }
            path = path + "/";
//            if (OsCheck.getOperatingSystemType() == OsCheck.OSType.Windows) {
                if (path.startsWith("/")) {
                    path = path.substring(1);
                }
//            }
        } else {
            path = getWebRootPath();
        }

        return path;
    }

    /**
     * @return tomcat 根目录(war形式)
     */
    public static String getRoot() {
        String path = getWebRoot();
        path = path.substring(0, path.indexOf("/tomcat/")) + "/";
        return path;
    }

    // ================================================================
    // Getter & Setter
    // ================================================================

    // ================================================================
    // Private Methods
    // ================================================================

    /**
     * Detect web root path.
     *
     * @return the string
     */
    private static String detectWebRootPath() {
        try {
            String path = PathUtils.class.getResource("/").toURI().getPath();
            return new File(path).getParentFile().getParentFile().getCanonicalPath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ================================================================
    // Inner or Anonymous Class
    // ================================================================

    // ================================================================
    // Test Methods
    // ================================================================
}
