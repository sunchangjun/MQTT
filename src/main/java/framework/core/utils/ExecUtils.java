package framework.core.utils;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteStreamHandler;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.OS;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.exec.ShutdownHookProcessDestroyer;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 执行bat\shell脚本等命令
 */
public class ExecUtils {

    // ================================================================
    // Constants
    // ================================================================

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ExecUtils.class);

    // ================================================================
    // Fields
    // ================================================================

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
     * resolve file type by os
     *
     * @param script script path and name
     * @return full file
     */
    public static File resolveScriptForOS(final String script) {
        if (OS.isFamilyWindows()) {
            return new File(script + ".bat");
        } else if (OS.isFamilyUnix()) {
            return new File(script + ".sh");
        } else if (OS.isFamilyOpenVms()) {
            return new File(script + ".dcl");
        } else {
            throw new RuntimeException("script not supported for this OS");
        }
    }

    /**
     * execute command.
     *
     * @see ExecResultHandler
     */
    public static ExecResultHandler exec(final String command, final String... args) throws IOException {
        return exec(command, 0, false, args);
    }

    /**
     * execute command.
     *
     * @see ExecResultHandler
     */
    public static ExecResultHandler exec(final File commandFile, final String... args) throws IOException {
        return exec(commandFile, 0, false, args);
    }

    /**
     * execute command.
     *
     * @see ExecResultHandler
     */
    public static ExecResultHandler exec(final String command, final boolean inBackground, final String... args) throws IOException {
        return exec(command, 0, inBackground, args);
    }

    /**
     * execute command.
     *
     * @see ExecResultHandler
     */
    public static ExecResultHandler exec(final File commandFile, final boolean inBackground, final String... args) throws IOException {
        return exec(commandFile, 0, inBackground, args);
    }

    /**
     * execute command.
     *
     * @see ExecResultHandler
     */
    public static ExecResultHandler exec(final String command, final long timeout, final boolean inBackground, final String... args) throws IOException {
        return exec(CommandLine.parse(command), timeout, inBackground, args);
    }

    /**
     * execute command.
     *
     * @see ExecResultHandler
     */
    public static ExecResultHandler exec(final File commandFile, final long timeout, final boolean inBackground, final String... args) throws IOException {
        return exec(new CommandLine(commandFile), timeout, inBackground, args);
    }

    /**
     * execute command.
     *
     * @param timeout      the timeout (ms) before the watchdog terminates the process
     * @param inBackground done in the background or blocking
     * @return a result handler (implementing a future)
     * @throws IOException the execute failed
     */
    public static ExecResultHandler exec(final CommandLine commandLine, final long timeout, final boolean inBackground, final String... args) throws IOException {

        logger.info("command line is : {}", commandLine);
        logger.info(ArrayUtils.toString(args));

        // param
        int exitValue;
        ExecuteWatchdog watchdog = null;
        ExecResultHandler resultHandler;

        // create the executor and consider the exitValue '0' as success
        final Executor executor = new DefaultExecutor();
        executor.setExitValue(0);

        // can rewrite the out stream, now is system out it.
        ExecuteStreamHandler streamHandler = new PumpStreamHandler(new OutputStream() {

            @Override
            public void write(int b) throws IOException {
                // 暂时打印出来
                System.out.print((char) b);
            }
        });

        executor.setStreamHandler(streamHandler);

        // 添加默认关闭事件
        executor.setProcessDestroyer(new ShutdownHookProcessDestroyer());

        // put params
        if (args != null && args.length > 0) {
            for (String arg : args) {
                commandLine.addArgument(arg);
            }
        }

        // create a watchdog if requested
        if (timeout > 0) {
            watchdog = new ExecuteWatchdog(timeout);
            executor.setWatchdog(watchdog);
        }

        // pass a "ExecuteResultHandler" when doing background
        if (inBackground) {
            logger.info("[command] Executing non-blocking command ...");
            resultHandler = new ExecResultHandler(watchdog);
            executor.execute(commandLine, resultHandler);
        } else {
            logger.info("[command] Executing blocking command ...");
            exitValue = executor.execute(commandLine);
            resultHandler = new ExecResultHandler(exitValue);
        }

        return resultHandler;
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

    /**
     * execute result handler.
     */
    private static class ExecResultHandler extends DefaultExecuteResultHandler {

        // watch result.
        private ExecuteWatchdog watchdog;

        public ExecResultHandler(final ExecuteWatchdog watchdog) {
            this.watchdog = watchdog;
        }

        public ExecResultHandler(final int exitValue) {
            super.onProcessComplete(exitValue);
        }

        @Override
        public void onProcessComplete(final int exitValue) {
            super.onProcessComplete(exitValue);
            logger.info("[resultHandler] The command was successfully execute ...");
        }

        @Override
        public void onProcessFailed(final ExecuteException e) {
            super.onProcessFailed(e);
            if (watchdog != null && watchdog.killedProcess()) {
                logger.info("[resultHandler] The command process timed out");
            } else {
                logger.info("[resultHandler] The command process failed to do : " + e.getMessage());
            }
        }
    }

    // ================================================================
    // Test Methods
    // ================================================================
}
