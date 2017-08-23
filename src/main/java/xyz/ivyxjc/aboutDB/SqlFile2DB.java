package xyz.ivyxjc.aboutDB;

import org.apache.commons.io.LineIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import xyz.ivyxjc.utils.ApacheLineIterator;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class SqlFile2DB {
    private static Logger log = LogManager.getLogger(SqlFile2DB.class);

    private static String DRIVER_NAME;
    private static String DB_URL;
    private static String USERNAME;
    private static String PASSWORD;
    private static DataSource sDatasour;

    private SqlFile2DB() {
    }

    private static void initResources() {
        InputStream in = SqlFile2DB.class.getClassLoader().getResourceAsStream("secret.properties");
        Properties props = new Properties();
        try {
            props.load(in);
            DB_URL = props.getProperty("jdbc.url");
            DRIVER_NAME = props.getProperty("jdbc.driverClassName");
            USERNAME = props.getProperty("jdbc.username");
            PASSWORD = props.getProperty("jdbc.password");
        } catch (IOException e) {
            log.error(e.toString());
        }
    }

    public static void sqlToDB(String path) {
        log.info("-------------------" + path + "----------------------");
        initResources();
        LineIterator it = null;
        int count = 0;
        int nullCount = 0;
        try {
            it = ApacheLineIterator.getLineIterator(path);
            Class.forName(DRIVER_NAME);
            sDatasour = new DriverManagerDataSource(DB_URL, USERNAME, PASSWORD);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(sDatasour);

            while (it.hasNext()) {
                jdbcTemplate.getDataSource().getConnection().setAutoCommit(false);
                jdbcTemplate.execute("SET UNIQUE_CHECKS=0; ");
                jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS=0; ");
                String line = it.nextLine();
                if (line == null || line.length() == 0) {
                    nullCount += 1;
                    log.info("==================" + nullCount + "===========================");
                    continue;
                }
                long startTime = System.currentTimeMillis();
                jdbcTemplate.execute(line);
                jdbcTemplate.execute("commit;");
                count += 1;
                long endTime = System.currentTimeMillis();
                log.info(
                    "=================="
                        + count
                        + "========"
                        + (endTime - startTime)
                        + "===================");
            }
            log.info("Success");
        } catch (IOException | ClassNotFoundException | SQLException e) {
            log.error(e.toString());
        } finally {
            LineIterator.closeQuietly(it);
        }
    }

}
