package factory;

import compositeNode.Container;
import compositeNode.JsonNode;
import compositeNode.Type;
import parser.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

/**
 * TreeStyle类继承自JsonStyle类，用于以树形结构展示JSON节点。
 */
public class TreeStyle extends JsonStyle {
    /**
     * 中间节点的图标。
     */
    String intermediateIcon;
    /**
     * 叶子节点的图标。
     */
    String leafIcon;
    
    /**
     * TreeStyle类的构造函数，初始化中间节点和叶子节点的图标。
     */
    public TreeStyle(){
        intermediateIcon = "";
        leafIcon = "";
    }
    
    /**
     * 以树形结构展示JSON所有节点的方法。
     * 
     * @param node JSON根节点
     */
    @Override
    public void show(JsonNode node){
        List<JsonNode> children = node.getChildren();
        for(int i = 0; i< children.size(); i++){
            boolean isLast = i==children.size() - 1;
            showNode(children.get(i), "", isLast);
        }
    }
    
    /**
     * 设置节点图标的方法。
     * 
     * @param configFilePath 图标的配置文件路径
     */
    @Override
    public void setStyle(String configFilePath){
        String result;
        try {
            File file = new File(configFilePath);
            StringBuilder sb = new StringBuilder();
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            br.close();
            fr.close();
            result = sb.toString();
            int start = result.indexOf(':');
            start = result.indexOf('"', start);
            int end = start + 1;
            while(result.charAt(end) != '"') end++;
            leafIcon = result.substring(start + 1, end);
            start = result.indexOf(':', end);
            start = result.indexOf('"', start);
            end = start + 1;
            while(result.charAt(end) != '"') end++;
            intermediateIcon = result.substring(start + 1, end);
        }catch (Exception e){
            //sSystem.out.println("请检查你的图标配置文件！");
            // e.printStackTrace();
        }
    }
    
    /**
     * 展示单个节点，用于辅助show()方法。
     * 
     * @param node   要展示的JSON节点
     * @param prefix 展示节点前需要打印的前缀字符串
     * @param isLast 是否为前一层节点的子节点中的最后一个节点
     */
    @Override
    public void showNode(JsonNode node, String prefix, boolean isLast){
        System.out.print(prefix);
        if(isLast) System.out.print("└─");
        else System.out.print("├─");

        if(node.getType() == Type.Leaf){
            System.out.println(leafIcon + node.getName()
                    + ":" + node.getValue());
            return;
        }
        System.out.println(intermediateIcon + node.getName());
        List<JsonNode> children = node.getChildren();
        for(int i = 0; i < children.size(); i++){
            boolean isLastChild = (i == children.size() - 1);

            String newPrefix = prefix;
            if(isLast) newPrefix += "  ";
            else newPrefix += "│ ";
            showNode(children.get(i), newPrefix, isLastChild);
        }

    }
    public static void main(String[] args){
        JsonParser ja = new JsonParser();
        ja.getContent("src/main/resources/test.json");
        JsonNode node = new Container("root", 0);
        ja.parser(node);
        TreeStyle t = new TreeStyle();
        t.setStyle("src/main/resources/iconConfig.json");
        t.show(node);
    }
}
