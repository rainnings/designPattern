package compositeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Container类是JSON数据的容器节点。
 */
public class Container extends JsonNode {
    /**
     * 容器节点的层级。
     */
    private final int level;
    /**
     * 容器节点的名称。
     */
    private final String name;
    /**
     * 容器节点的子节点列表。
     */
    private final List<JsonNode> children = new ArrayList<>();
    
    /**
     * 构造函数，用于创建Container实例。
     * 
     * @param name  容器节点的名称
     * @param level 容器节点的层级
     */
    public Container(String name, int level) {
        this.name = name;
        this.level = level;
    }
    
    /**
     * 向容器节点添加子节点。
     * 
     * @param node 要添加的子节点
     */
    @Override
    public void add(JsonNode node){
        children.add(node);
    }
    
    /**
     * 从容器节点中移除指定子节点。
     * 
     * @param node 要移除的子节点
     */
    @Override
    public void remove(JsonNode node){
        children.remove(node);
    }
    
    /**
     * 获取节点的类型。
     * 
     * @return 节点的类型为Container
     */
    @Override
    public Type getType(){
        return Type.Container;
    }
    
    /**
     * 获取节点的层级。
     * 
     * @return 节点的层级
     */
    @Override
    public int getLevel(){
        return level;
    }
    
    /**
     * 获取容器节点的子节点列表。
     * 
     * @return 子节点列表
     */
    @Override
    public List<JsonNode> getChildren(){
        return children;
    }
    
    /**
     * 获取容器节点的名称。
     * 
     * @return 容器节点的名称
     */
    @Override
    public String getName(){
        return name;
    }
    
    /**
     * 获取容器节点的值。
     * 
     * @return 空字符串，容器节点无值
     */
    @Override
    public String getValue(){
        return "";
    }

    /**
     * 用于调试。
     * @param args 命令行参数
     */
    public static void main(String[] args){
        
    }
}
