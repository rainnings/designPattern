package builder;

import factory.Factory;
import factory.JsonStyle;
import compositeNode.Container;
import compositeNode.JsonNode;
import parser.JsonParser;

/**
 * Builder类用于构建JSON节点树，并设置展示样式。
 */
public class Builder {
    /**
     * JSON节点树。
     */
    private JsonNode root;
    /**
     * 节点树展示样式。
     */
    private JsonStyle style;
    
    /**
     * 构建json树根节点的方法，根据指定文件路径构建JSON节点树。
     * 
     * @param filePath 文件路径
     * @return 当前Builder对象
     */
    public Builder buildNode(String filePath){
        root = new Container("root", 0);
        JsonParser parser = new JsonParser();
        parser.getContent(filePath);
        parser.parser(root);
        return this;
    }
    /**
     * 设置展示样式的方法，根据工厂创建样式对象。
     * @param factory 工厂对象
     * @return 当前Builder对象
     */
    public Builder setStyle(Factory factory){
        style = factory.createStyle();
        return this;
    }
    /**
     * 设置图标的方法，根据配置文件设置样式。
     * @param configFilePath 配置文件路径
     * @return 当前Builder对象
     */
    public Builder setIcon(String configFilePath){
        style.setStyle(configFilePath);
        return this;
    }
    /**
     * 展示JSON对象的方法，根据样式展示JSON节点。
     */
    public void showJson(){
        style.show(root);
    }

}
