package factory;

import compositeNode.Container;
import compositeNode.JsonNode;
import compositeNode.Type;
import parser.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RectangleStyle类继承自JsonStyle类，用于以矩形结构展示JSON节点。
 */
public class RectangleStyle extends JsonStyle {
    /**
     * 中间节点的图标。
     */
    private String intermediateIcon;
    /**
     * 叶子节点的图标。
     */
    private String leafIcon;
    /**
     * 是否为要展示的第一个节点。
     */
    private boolean isFirst;
    /**
     * 是否为最后一个叶子节点。
     */
    private boolean isLastLeaf;
    /**
     * 矩形长度。
     */
    private int rectangleLength;
    
    /**
     * RectangleStyle类的构造函数，初始化各属性。
     */
    public RectangleStyle(){
        intermediateIcon = "";
        leafIcon = "";
        isFirst = true;
        isLastLeaf = false;
        rectangleLength = 50;
    }

    @Override
    public void show(JsonNode node){
        isFirst = true;
        isLastLeaf = false;
        List<JsonNode> children = node.getChildren();
        if(children.size() == 1 && children.get(0).getType() == Type.Leaf){
            for(int i = 0; i < rectangleLength; i++){
                if(i == 0){
                    System.out.print("┌");
                } else if(i == rectangleLength - 1){
                    System.out.println("┘");
                } else {
                    System.out.print("─");
                }
            }
        }
        for(int i = 0; i< children.size(); i++){
            boolean isLast = (i ==children.size() - 1);
            isLastLeaf = isLast && (children.get(i).getType() == Type.Leaf);
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
            // 打开文件
            File file = new File(configFilePath);
            StringBuilder sb = new StringBuilder();
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                //sb.append(line).append("\n");
                sb.append(line);
            }
            br.close();
            fr.close();
            // 数据以字符串形式保存到result
            result = sb.toString();
            // 获取叶子图标
            int start = result.indexOf(':');
            start = result.indexOf('"', start);
            int end = start + 1;
            while(end < result.length() && result.charAt(end) != '"') end++;
            leafIcon = result.substring(start + 1, end);
            // 获取中间节点图标
            start = result.indexOf(':', end);
            start = result.indexOf('"', start);
            end = start + 1;
            while(end < result.length() && result.charAt(end) != '"') end++;
            intermediateIcon = result.substring(start + 1, end);
            // 获取矩形结构长度
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(result);
            if(matcher.find()){
                rectangleLength = Integer.parseInt(matcher.group());
            }
        }catch (Exception e){
            //System.out.println("请检查你的图标配置文件！");
        }
    }
    
    /**
     * 展示单个节点，用于辅助show()方法。
     * 
     * @param node   要展示的JSON节点
     * @param prefix 展示节点前需要打印的前缀字符串
     * @param isLast 是否为当前层的最后一个节点
     */
    @Override
    public void showNode(JsonNode node, String prefix, boolean isLast){
        int useLength;
        if(isLastLeaf){
            for(int i = 0; i < prefix.length(); i++){
                if(i == 0){
                    System.out.print("└");
                } else if(i % 2 == 0){
                    System.out.print("┴");
                } else {
                    System.out.print("─");
                }
            }
        } else {
            System.out.print(prefix);
        }

        if(isLastLeaf){
            if(node.getLevel() == 1){
                System.out.print("└─");
            } else {
                System.out.print("┴─");
            }
        } else if (isFirst){
            System.out.print("┌─");
        } else {
            System.out.print("├─");
        }
        useLength = prefix.length() + 2;
        if(node.getType() == Type.Leaf){
            String leaf = leafIcon + node.getName() + ":" + node.getValue();
            System.out.print(leaf);
            useLength += leaf.length();

        } else {
            String container = intermediateIcon + node.getName();
            System.out.print(container);
            useLength += container.length();
        }
        System.out.print(" ");
        int remainLength = rectangleLength - useLength - 1;
        if(remainLength <= 0) System.out.println();
        for(int i = 0;i < remainLength; i++){
            if(i == remainLength - 1){
                if(isLastLeaf){
                    System.out.println("┘");
                } else if (isFirst){
                    isFirst = false;
                    System.out.println("┐");
                } else {
                    System.out.println("┤");
                }
            } else {
                System.out.print("─");
            }
        }
        if(node.getType() == Type.Leaf) return;
        List<JsonNode> children = node.getChildren();
        for(int i = 0; i < children.size(); i++){
            boolean isLastChild = (i == children.size() - 1);

            String newPrefix = prefix + "│ ";
            if(isLastChild && isLast){
                isLastLeaf = children.get(i).getType() == Type.Leaf;
            }
            showNode(children.get(i), newPrefix, isLast && isLastChild);
        }

    }

    /**
     * 用于调试。
     * @param args 命令行参数
     */
    public static void main(String[] args){
        JsonParser ja = new JsonParser();
        ja.getContent("src/main/resources/test.json");
        JsonNode node = new Container("root", 0);
        ja.parser(node);

        RectangleStyle t = new RectangleStyle();
        t.setStyle("src/main/resources/iconConfig.json");
        t.show(node);
    }
}
