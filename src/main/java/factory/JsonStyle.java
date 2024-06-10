package factory;

import compositeNode.JsonNode;

/**
 * JsonStyle是一个抽象类，定义了展示JSON节点和设置样式的方法。
 */
public abstract class JsonStyle {
    /**
     * 展示JSON所有节点的抽象方法。
     * 
     * @param node JSON根节点
     */
    public abstract void show(JsonNode node);
    
    /**
     * 设置节点图标的抽象方法。
     * 
     * @param configFilePath 图标的配置文件路径
     */
    public abstract void setStyle(String configFilePath);
    
    /**
     * 展示单个节点，用于辅助show()方法。
     * 
     * @param node   要展示的JSON节点
     * @param prefix 展示节点前需要打印的前缀字符串
     * @param isLast 是否为前一层节点的子节点中的最后一个节点
     */
    public abstract void showNode(JsonNode node, String prefix, boolean isLast);
}
