package xyz.ivyxjc.xml2Json;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class XmlHandler extends DefaultHandler {
    File out = null;
    FileChannel outChannel = null;
    ByteBuffer buffer = null;

//    public XmlHandler(String output) {
//        RandomAccessFile aFile = null;
//        try {
//            aFile = new RandomAccessFile(output, "rw");
//            outChannel = aFile.getChannel();
//            buffer = ByteBuffer.allocate(1000 * 1024 * 1024);
//            buffer.clear();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public XmlHandler() {

    }

    private Queue<XmlNode> queue;
    private Stack<XmlNode> stack;
    private int count = 0;

    @Override
    public void startDocument() throws SAXException {
        queue = new LinkedList<>();
        stack = new Stack<>();
    }

    @Override
    public void endDocument() throws SAXException {
        queue.forEach((node) -> node.setNodeType(node.getOptionalNodeType().orElse(NodeType.ObjectNode)));
        queue = null;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        XmlNode node = new XmlNode(qName);
        if (stack.empty()) {
            node.setFatherNode(null);
        } else {
            node.setFatherNode(stack.peek());
        }
        queue.offer(node);
        stack.push(node);
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        XmlNode node = new XmlNode(qName);
        if (node.getNodeText().equals(stack.peek().getNodeText())) {
            node.setNodeType(NodeType.TextNode);
            node.setFatherNode(stack.peek().getFatherNode());
            stack.pop().setNodeType(NodeType.TextNode);
        }
        queue.offer(node);
    }

    public int getCount() {
        return count;
    }


}
