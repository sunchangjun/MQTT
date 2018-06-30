package framework.core.web.listener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

import framework.core.common.Constant;

public class WebContextListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent>, Constant {

    // ================================================================
    // Constants
    // ================================================================

    /**
     * Logger
     */
    private static Logger logger = LoggerFactory.getLogger(WebContextListener.class);


    // ================================================================
    // Fields
    // ================================================================

    // ================================================================
    // Constructors
    // ================================================================

    // ================================================================
    // Methods from/for super Interfaces or SuperClass
    // ================================================================

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {

        // 打印版本信息
        if (!printKeyLoadMessage()) {
            // 加载失败
            throw new RuntimeException("版本信息错误,无法启动!");
        }

        Environment environment = event.getEnvironment();
        boolean distributed = false;
        if (environment != null) {
            distributed = Boolean.valueOf(environment.getProperty(CONFIGURATION_DEFINED_PREFIX + "distributed", "false"));
        }
        logger.info("系统初始化检测中,系统{}分布式", distributed ? "属于" : "不属于");

        /**
         * 检测MySQL数据库
         */
        // mysql属于业务数据库，不启动会导致业务失败
        // 获取读取延时参数，默认是等待一个小时
        CheckMysqlSatus.check(event.getEnvironment());
        /**
         * 检测MongoDB数据库
         */
        // mongodb属于日志数据库，不启动只会导致一些页面无日志
        // 获取读取延时参数，默认是日志打印告警5次
        CheckMongoDBSatus.check();
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
     * 获取并打印加载信息
     */
    private boolean printKeyLoadMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("\r\n======================================================================\r\n");
        sb.append("\r\n    欢迎使用 Framework - Powered By http://www.tiand99.com\r\n");
        sb.append("\r\n======================================================================\r\n");
        System.out.println(sb.toString());
        return true;
    }

    // ================================================================
    // Inner or Anonymous Class
    // ================================================================

    /**
     * 检查mysql的运行状态
     */
    private static class CheckMysqlSatus {

        /**
         * 验证状态
         */
        private static boolean check(ConfigurableEnvironment environment) {
            // mysql属于业务数据库，不启动会导致业务失败
            // 获取读取延时参数，默认是等待一个小时
            long now = System.currentTimeMillis();
            long start = now;
            // 60*1000;
            long timeout = -1;
            // 持续时间
            long contTime;
            boolean dbOk = false;

            // 等待时间,考虑以后加入配置文件
            String wait = "3600";
            long secTimeout = Long.parseLong(wait);

            if (secTimeout > 0)
                timeout = secTimeout * 1000;

            while (true) {
                if (checkDBState(environment)) {
                    dbOk = true;
                    break;
                }

                try {
                    logger.info("database connection is error, wait for mysql database start!");
                    Thread.sleep(10000);
                } catch (Exception ignored) {
                }

                now = System.currentTimeMillis();
                contTime = now - start;

                if (timeout <= 0)
                    continue;

                if (contTime >= timeout)
                    break;
            }

            if (!dbOk) {
                logger.info("database pool error ,please check mysql status...");
                return false;
            } else {
                logger.info("database pool ok, mysql is running..");
            }
            return true;
        }

        /**
         * 获取mysql数据库状态
         */
        private static boolean checkDBState(ConfigurableEnvironment environment) {
            boolean succ;

            // 验证sql
            String sql = environment.getProperty("spring.datasource.validationQuery", "select 1");
            try {
                succ = select(sql, environment);
            } catch (Throwable ex) {
                logger.error("mysql select error: " + ex);
                succ = false;
            }
            return succ;
        }

        /**
         * 查询
         */
        private static boolean select(String sql, ConfigurableEnvironment environment) throws Exception {

            Connection conn = null;
            PreparedStatement statement = null;
            ResultSet rs = null;
            try {

                conn = getConnection(environment);
                if (conn != null) {
                    statement = conn.prepareStatement(sql);
                    rs = statement.executeQuery();
                    if (rs != null) {
                        return true;
                    }
                }
                return false;
            } catch (Exception e) {
                String message = "database select error:" + e;
                throw new Exception(message, e);
            } finally {
                freeResource(rs, statement, conn);
            }
        }

        /**
         * 获取Connection
         */
        private static Connection getConnection(ConfigurableEnvironment environment) {
            // DB connection.
            Connection connection;

            try {

                String dbConnectUrl = environment.getProperty("spring.datasource.url")
                        + "&user=" + environment.getProperty("spring.datasource.username")
                        + "&password=" + environment.getProperty("spring.datasource.password");
                // database connection in single thread.
                Class.forName(environment.getProperty("spring.datasource.driver-class-name", "com.mysql.jdbc.Driver"));
                connection = DriverManager.getConnection(dbConnectUrl);
                logger.debug("connection is ok. ");
            } catch (Exception e) {
                logger.error("get connection is error: {} ", e.getMessage());
                return null;
            }
            return connection;
        }

        /**
         * 释放资源
         */
        private static void freeResource(ResultSet rs, Statement ps, Connection conn) {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ignored) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ignored) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

    /**
     * 检查mongodb的运行状态
     */
    private static class CheckMongoDBSatus {

        /**
         * 验证状态
         */
        private static boolean check() {
            // 目前系统不一定包含MongoDB数据库,暂时返回验证正常
            return true;
        }
    }
    // ================================================================
    // Test Methods
    // ================================================================
}
