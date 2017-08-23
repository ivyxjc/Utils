package xyz.ivyxjc.xml2Json;

public enum NodeType {

    ArrayNode, ObjectNode, TextNode;


    @Override
    public String toString() {
        switch (this) {
            case ArrayNode:
                return "ArrayNode";
            case ObjectNode:
                return "ObjectNode";
            case TextNode:
                return "TextNode";
            default:
                return "null";
        }
    }
}

