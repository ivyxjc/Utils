package xyz.ivyxjc.xml2Json;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

public class TestXmlNode {

    private Random random = new Random();

    @Test
    public void testSameNodesHashCodeAndEquals() {

        XmlNode firstFatherNode = new XmlNode(random.nextDouble() + "", NodeType.ArrayNode);
        double same = random.nextDouble();
        XmlNode firstNode = new XmlNode(same + "", NodeType.ArrayNode, firstFatherNode);
        XmlNode secondNode = new XmlNode(same + "", NodeType.ArrayNode, firstFatherNode);
        Assert.assertEquals(firstFatherNode, firstFatherNode);
        Assert.assertTrue(firstNode.equals(secondNode));
        Assert.assertEquals(firstNode.hashCode(), secondNode.hashCode());
    }

    @Test
    public void testDifferentFatherNodes() {
        XmlNode firstFatherNode = new XmlNode(random.nextDouble() + "", NodeType.ArrayNode);
        XmlNode secondFatherNode = new XmlNode(random.nextDouble() + "", NodeType.ArrayNode);
        double same = random.nextDouble();
        XmlNode firstNode = new XmlNode(same + "", NodeType.ArrayNode, firstFatherNode);
        XmlNode secondNode = new XmlNode(same + "", NodeType.ArrayNode, secondFatherNode);
        Assert.assertNotEquals(firstNode, secondNode);
        Assert.assertEquals(firstNode.hashCode(), secondNode.hashCode());
    }

    @Test
    public void testSameFatherDifferentNodesType() {
        XmlNode firstFatherNode = new XmlNode(random.nextDouble() + "", NodeType.ArrayNode);
        double same = random.nextDouble();
        XmlNode firstNode = new XmlNode(same + "", NodeType.ArrayNode, firstFatherNode);
        XmlNode secondNode = new XmlNode(same + "", NodeType.ObjectNode, firstFatherNode);
        Assert.assertNotEquals(firstNode, secondNode);
        Assert.assertEquals(firstNode.hashCode(), secondNode.hashCode());
    }

    @Test
    public void testSameFatherDifferentNodesText() {
        XmlNode firstFatherNode = new XmlNode(random.nextDouble() + "", NodeType.ArrayNode);
        XmlNode firstNode = new XmlNode(random.nextDouble() + "", NodeType.ArrayNode, firstFatherNode);
        XmlNode secondNode = new XmlNode(random.nextDouble() + "", NodeType.ArrayNode, firstFatherNode);
        Assert.assertNotEquals(firstNode, secondNode);
        Assert.assertNotEquals(firstNode.hashCode(), secondNode.hashCode());
    }


    @Test
    public void testStackQueueObject() {
        XmlNode node = new XmlNode("hello");
        Stack<XmlNode> stack = new Stack<>();
        Queue<XmlNode> queue = new LinkedList<>();
        stack.push(node);
        queue.offer(node);
        System.out.println(queue.peek());
        stack.peek().setNodeType(NodeType.TextNode);
        System.out.println(queue.peek());
    }

}
