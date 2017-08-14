package xyz.ivyxjc.aboutDB;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.ivyxjc.utils.ApacheLineIterator;

/**
 * All methods use Apache FileUtils to read data incrementally. Don't worry about memory.
 */
public class SqlFileSplitter {
    private static Logger log = LogManager.getLogger(SqlFileSplitter.class);
    private static String DEFAULT_CHARSET = "UTF-8";

    private SqlFileSplitter() {
    }

    /**
     * Split large sql file to small one using Apache FileUtils to read and process the data
     * incrementally, memory cost is small
     *
     * @throws IOException
     */
    public static void split(String sourcePath, String targetPath) throws IOException {
        int count = 0;
        LineIterator it = ApacheLineIterator.getLineIterator(sourcePath);
        File targetFile = new File(targetPath);
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                count += 1;
                FileUtils.writeStringToFile(targetFile, line + '\n', true);
                if (count % 1000 == 0) {
                    targetFile = new File(targetPath + count);
                }
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
    }

    /**
     * show one file's sub-content from start line to end line
     *
     * @throws IOException
     */
    public static void readLimitedLines(String path, int start, int end) throws IOException {
        File source = new File(path);
        LineIterator it = FileUtils.lineIterator(source, DEFAULT_CHARSET);
        int count = 0;
        try {
            while (it.hasNext()) {
                String line = it.nextLine();
                if (count > end) {
                    break;
                }
                if (count >= start) {
                    log.info(line);
                }
                count++;
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
    }

    /**
     * count one file's line number
     *
     * @throws IOException
     */
    public static int countLines(String path) throws IOException {
        File source = new File(path);
        LineIterator it = FileUtils.lineIterator(source, DEFAULT_CHARSET);
        int count = 0;
        try {
            while (it.hasNext()) {
                count += 1;
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
        return count;
    }
}
