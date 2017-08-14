package xyz.ivyxjc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class SqlFile2DB {
    private static Logger log = LogManager.getLogger(SqlFile2DB.class);
    private static String DEFAULT_CHARSET = "UTF-8";

    private static String DRIVER_NAME;
    private static String DB_URL;

    private static DataSource mDatasour;

    private SqlFile2DB() {
    }

    private static void initResources() {
        InputStream in = SqlFile2DB.class.getClassLoader().getResourceAsStream("secret.properties");
        Properties props = new Properties();
        try {
            props.load(in);
            DB_URL = props.getProperty("jdbc.url");
            DRIVER_NAME = props.getProperty("jdbc.driverClassName");
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
            it = getLineIterator(path);
            Class.forName(DRIVER_NAME);
            mDatasour = new DriverManagerDataSource(DB_URL, "root", "qq123456");
            JdbcTemplate jdbcTemplate = new JdbcTemplate(mDatasour);

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

    private static LineIterator getLineIterator(String path) throws IOException {
        File source = new File(path);
        return FileUtils.lineIterator(source, DEFAULT_CHARSET);
    }

    private static LineIterator getLineIterator(String path, String charset) throws IOException {
        File source = new File(path);
        return FileUtils.lineIterator(source, charset);
    }
}
