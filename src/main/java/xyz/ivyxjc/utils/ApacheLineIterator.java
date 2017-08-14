package xyz.ivyxjc.utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class ApacheLineIterator {

    private static final String DEFAULT_CHARSET = "UTF-8";

    private ApacheLineIterator() {
    }

    public static final LineIterator getLineIterator(String path) throws IOException {
        File source = new File(path);
        return FileUtils.lineIterator(source, DEFAULT_CHARSET);
    }

    public static LineIterator getLineIterator(String path, String charset) throws IOException {
        File source = new File(path);
        return FileUtils.lineIterator(source, charset);
    }
}
