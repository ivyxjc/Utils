package xyz.ivyxjc.xml2Json;

import org.apache.commons.io.LineIterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.ivyxjc.utils.ApacheLineIterator;

import java.io.IOException;

public class XmlToJson {
    private static Logger log = LogManager.getLogger(XmlToJson.class);
    private static LineIterator it = null;

    public static void init(String xmlFile) {
        try {
            it = ApacheLineIterator.getLineIterator(xmlFile);
        } catch (IOException e) {
            log.info(e.toString());
        }
    }

    private XmlToJson() {
    }

}

