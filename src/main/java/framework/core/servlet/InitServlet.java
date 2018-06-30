package framework.core.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import framework.core.utils.ThreadUtils;



public class InitServlet extends HttpServlet {


    // ================================================================
    // Constants
    // ================================================================

    /**
     * UID
     */
    private static final long serialVersionUID = 1L;
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(InitServlet.class);

    // ================================================================
    // Fields
    // ================================================================

    // ================================================================
    // Constructors
    // ================================================================

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitServlet() {
        super();
    }

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    @Override
    public void init() throws ServletException {
        super.init();

        logger.info("InitServlet start, config infomation: mysql、mongodb、schedule、activemq、alarmconsole、flow、syslog and so on.");

        /**
         * 检测MySQL的运行启动状态
         */
        logger.debug("check mysql status.");
        checkMySQLStatus();

        /**
         * 检测MongoDB的运行启动状态
         */
        logger.debug("check mongodb status.");
        checkMongoDBStatus();

        // 考虑启动慢问题，加入新线程启动
        ThreadUtils.addExecuteTask(() -> {
            // 启动FTP服务器
            logger.debug("init ftp server.");
            ftpServerInit();

            /**
             * 初始化任务调度
             */
            logger.debug("init schedule quartz.");
            scheduleManagerInit();

            /**
             * ActiveMQ queue 和 subject 初始化
             */
            logger.debug("init active mq queue and subject session.");
            activeMQInit();

            /**
             * 邮件发送管理初始化
             */
            logger.debug("init send email manager.");
            mailSenderInit();

//            logger.debug("init convert manager.");
//            Converter.getInstance();
        });

        /**
         * <pre>
         * bean trace.
         * // 获取WebApplicationContext
         * ServletContext application = getServletContext();
         * WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application);
         * </pre>
         */

        logger.info("InitServlet start is ok, some child thread try starting...");
    }

    @Override
    public void destroy() {
        logger.info("Shutdown tomcat,some user create thread not closed, exception ignore.");
        // 尝试关闭所有线程和线程池
        ThreadUtils.serverShutdown();
        super.destroy();
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

    /**
     * 启动ftp服务器
     */
    private void ftpServerInit() {
        try {
            // project may not needed.
            logger.debug("apache ftp server init is ok.");
        } catch (Exception e) {
            logger.error("ftp server startup error: ", e);
        }
    }

    /**
     * 检测mysql的运行启动状态
     */
    private void checkMySQLStatus() {
        // 框架使用了Spring，为了保证验证在Spring的Servlet初始化前运行，改用Listener实现
    }

    /**
     * 检测MongoDB的运行启动状态
     */
    private void checkMongoDBStatus() {
        // 框架使用了Spring，为了保证验证在Spring的Servlet初始化前运行，改用Listener实现
    }

    /**
     * 初始化任务调度
     */
    private void scheduleManagerInit() {}

    /**
     * ActiveMQ queue 和 subject 初始化
     */
    private void activeMQInit() {
        // TODO
    }

    /**
     * 邮件发送管理初始化
     */
    private void mailSenderInit() {
        // TODO
    }

    // ================================================================
    // Inner or Anonymous Class
    // ================================================================

    // ================================================================
    // Test Methods
    // ================================================================


}
