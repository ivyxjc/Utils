package xyz.ivyxjc.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;

public class ApacheLineIterator {

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static File source = null;

    private ApacheLineIterator(String path) {
    }

    public static final LineIterator getLineIterator(String path) throws IOException {
        source = new File(path);
        return FileUtils.lineIterator(source, DEFAULT_CHARSET);
    }

    public static LineIterator getLineIterator(String path, String charset) throws IOException {
        source = new File(path);
        return FileUtils.lineIterator(source, charset);
    }
}
