package xyz.ivyxjc.xml2Json;

import java.util.Optional;

public class XmlNode {


    private String nodeText;

    private Optional<NodeType> nodeType;

    private Optional<XmlNode> fatherNode;

    private int sameNodeCount;


    public XmlNode(String nodeText) {
        this.nodeText = nodeText;
        this.nodeType = Optional.ofNullable(null);
        this.fatherNode = Optional.ofNullable(null);
    }

    public XmlNode(String nodeText, NodeType type) {
        this.nodeText = nodeText;
        this.nodeType = Optional.of(type);
        this.fatherNode = Optional.ofNullable(null);
    }

    public XmlNode(String nodeText, NodeType type, XmlNode fatherNode) {
        this.nodeText = nodeText;
        this.nodeType = Optional.of(type);
        this.fatherNode = Optional.of(fatherNode);
    }


    @Override
    public boolean equals(Object obj) {
        XmlNode node = (XmlNode) obj;
        return nodeText.equals(node.getNodeText()) && nodeType.equals(node.getOptionalNodeType()) && fatherNode.equals(node.getOptionalFatherNode());
    }

    @Override
    public int hashCode() {
        int h = 0;
        char[] value = nodeText.toCharArray();
        if (value.length > 0) {
            char val[] = value;

            for (int i = 0; i < value.length; i++) {
                h = 31 * h + val[i];
            }
        }
        return h;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NodeText: " + nodeText);
        sb.append("  NodeType: " + getNodeType());
        sb.append("  FatherNode: " + getOptionalFatherNode().orElse(new XmlNode("null")).getNodeText());
        sb.append("  SameNodeCounts: " + sameNodeCount);
        return sb.toString();
    }

    public String getNodeText() {
        return nodeText;
    }

    public void setNodeText(String nodeText) {
        this.nodeText = nodeText;
    }

    public NodeType getNodeType() {
        return nodeType.orElse(null);
    }

    public Optional<NodeType> getOptionalNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = Optional.of(nodeType);
    }

    public XmlNode getFatherNode() {
        return fatherNode.orElse(null);
    }

    public Optional<XmlNode> getOptionalFatherNode() {
        return fatherNode;
    }

    public void setFatherNode(XmlNode fatherNode) {
        this.fatherNode = Optional.ofNullable(fatherNode);
    }

    public int getSameNodeCount() {
        return sameNodeCount;
    }

    public void setSameNodeCount(int sameNodeCount) {
        this.sameNodeCount = sameNodeCount;
    }
}


