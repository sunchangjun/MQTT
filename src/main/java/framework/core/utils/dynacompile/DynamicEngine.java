package framework.core.utils.dynacompile;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * 动态编译引擎
 * Created by zeyuphoenix on 16/9/2.
 */
public class DynamicEngine {

    // ================================================================
    // Constants
    // ================================================================

    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(DynamicEngine.class);

    // ================================================================
    // Fields
    // ================================================================

    /**
     * 提供唯一实例
     */
    private static final DynamicEngine INSTANCE = new DynamicEngine();

    // 父接口 loader
    private URLClassLoader parentClassLoader;
    // class 路径
    private String classpath;

    // ================================================================
    // Constructors
    // ================================================================

    /**
     * 私有构造函数
     */
    private DynamicEngine() {
        this.parentClassLoader = (URLClassLoader) this.getClass().getClassLoader();
        this.buildClassPath();
    }

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    // ================================================================
    // Public or Protected Methods
    // ================================================================

    /**
     * 单例模式
     */
    public static DynamicEngine getInstance() {
        return INSTANCE;
    }

    /**
     * 通过java code 动态编译 class
     * @param fullClassName 编译后的完整路径
     * @param javaCode 编译需要的java 代码
     * @return 编译好的class
     */
    public Object compile(String fullClassName, String javaCode)
            throws IllegalAccessException, InstantiationException, CompileException {
        long start = System.currentTimeMillis();
        Object instance = null;
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        ClassFileManager fileManager = new ClassFileManager(compiler.getStandardFileManager(diagnostics, null, null));

        List<JavaFileObject> jfiles = Lists.newArrayList();
        jfiles.add(new CharSequenceJavaFileObject(fullClassName, javaCode));

        List<String> options = Lists.newArrayList();
        options.add("-encoding");
        options.add("UTF-8");
        options.add("-classpath");
        options.add(this.classpath);

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, options, null, jfiles);
        boolean success = task.call();

        if (success) {
            JavaClassObject jco = fileManager.getJavaClassObject();
            DynamicClassLoader dynamicClassLoader = null;
            try {
                dynamicClassLoader = new DynamicClassLoader(this.parentClassLoader);
                Class<?> clazz = dynamicClassLoader.loadClass(fullClassName, jco);
                instance = clazz.newInstance();
            } finally {
                /*
                 * 只能工作在JDK1.7+
				 */
                if (javaVersion() > 6) {
                    if (dynamicClassLoader != null) {
                        try {
                            dynamicClassLoader.close();
                        } catch (Exception e) {
                            // ignore
                        }
                    }
                }
                // dynamicClassLoader = null;
            }
        } else {
            StringBuilder error = new StringBuilder();
            for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
                error.append(compilePrint(diagnostic));
            }
            throw new CompileException(error.toString());
        }
        long end = System.currentTimeMillis();
        logger.info("java code compile to Object used: {}ms", (end - start));
        return instance;
    }

    // ================================================================
    // Getter & Setter
    // ================================================================

    // ================================================================
    // Private Methods
    // ================================================================

    /**
     * 构造编译类路径
     */
    private void buildClassPath() {
        this.classpath = null;
        StringBuilder sb = new StringBuilder();
        for (URL url : this.parentClassLoader.getURLs()) {
            String p = url.getFile();
            sb.append(p).append(File.pathSeparator);
        }
        this.classpath = sb.toString();
    }

    /**
     * java version,低版本的jdk不支持动态编译
     *
     * @return java 版本
     */
    private static int javaVersion() {
        int javaVersion = -1;

        try {
            Class.forName("java.time.Clock", false, getClassLoader(Object.class));
            javaVersion = 8;
        } catch (Exception e) {
            // Ignore
        }

        try {
            Class.forName("java.util.concurrent.LinkedTransferQueue", false, getClassLoader(BlockingQueue.class));
            javaVersion = 7;
        } catch (Exception e) {
            // Ignore
        }
        if (javaVersion < 0) {
            javaVersion = 6;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Java version: {}", javaVersion);
        }
        return javaVersion;
    }

    /**
     * 根据给定class获取引导类
     */
    private static ClassLoader getClassLoader(final Class<?> clazz) {
        if (System.getSecurityManager() == null) {
            return clazz.getClassLoader();
        } else {
            return AccessController.doPrivileged((PrivilegedAction<ClassLoader>) clazz::getClassLoader);
        }
    }

    /**
     * 动态编译的输出信息
     */
    private StringBuilder compilePrint(Diagnostic<?> diagnostic) {
        logger.info("Code:{}", diagnostic.getCode());
        logger.info("Kind:{}", diagnostic.getKind());
        logger.info("Position:{}", diagnostic.getPosition());
        logger.info("Start Position:{}", diagnostic.getStartPosition());
        logger.info("End Position:{}", diagnostic.getEndPosition());
        logger.info("Source:{}", diagnostic.getSource());
        logger.info("Message:{}", diagnostic.getMessage(null));
        logger.info("LineNumber:{}", diagnostic.getLineNumber());
        logger.info("ColumnNumber:{}", diagnostic.getColumnNumber());
        StringBuilder res = new StringBuilder();
        res.append("Code:[").append(diagnostic.getCode()).append("]\n");
        res.append("Kind:[").append(diagnostic.getKind()).append("]\n");
        res.append("Position:[").append(diagnostic.getPosition()).append("]\n");
        res.append("Start Position:[").append(diagnostic.getStartPosition()).append("]\n");
        res.append("End Position:[").append(diagnostic.getEndPosition()).append("]\n");
        res.append("Source:[").append(diagnostic.getSource()).append("]\n");
        res.append("Message:[").append(diagnostic.getMessage(null)).append("]\n");
        res.append("LineNumber:[").append(diagnostic.getLineNumber()).append("]\n");
        res.append("ColumnNumber:[").append(diagnostic.getColumnNumber()).append("]\n");
        return res;
    }

    // ================================================================
    // Inner or Anonymous Class
    // ================================================================

    // ================================================================
    // Test Methods
    // ================================================================

}
