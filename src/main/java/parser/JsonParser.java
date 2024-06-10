package parser;

import compositeNode.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * JsonParser类用于解析JSON字符串并构建对应的JSON节点树。
 */
public class JsonParser {
    /**
     * 存放json内容的字符串。
     */
    private String json;
    /**
     * 解析器当前处理到的json字符串位置下标。
     */
    private int index;

    /**
     * JsonParser类的构造函数，指定初始处理位置为0。
     */
    public JsonParser(){
        index = 0;
    }

    /**
     * 从指定文件路径获取JSON内容并存储在json字段中。
     * 
     * @param filePath 文件路径
     */
    public void getContent(String filePath){
        try {
            String result;
            File file = new File(filePath);
            StringBuilder sb = new StringBuilder();
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                // sb.append(line).append("\n");
                sb.append(line);
            }
            br.close();
            fr.close();
            result = sb.toString();
            json = result;
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 解析JSON字符串并构建JSON节点树。
     * 
     * @param root 根节点
     */
    public void parser(JsonNode root){
        while(index < json.length()){
            // 匹配到键的开始
            if(json.charAt(index) == '"'){
                // 获取键的名称
                int end = json.indexOf('"', index + 1);
                String name = json.substring(index + 1, end);
                index = end + 1;

                while(index < json.length()){
                    // 如果键的值是一个对象，则当前节点为容器，子节点为对象里的节点
                    if(json.charAt(index) == '{'){
                        index++;
                        JsonNode node = new Container(name, root.getLevel() + 1);
                        // 递归解析子对象
                        parser(node);
                        root.add(node);
                        break;
                    } else if(json.charAt(index) == ' ' || json.charAt(index) == ':'){
                        index++;
                    } else if(json.charAt(index) == '['){
                        // 键的值是一个数组
                        index++;
                        JsonNode node = new Container(name, root.getLevel() + 1);
                        getArray(node);
                        root.add(node);
                        break;
                    }
                    else {
                        // 键的值为具体值，节点为叶子节点
                        switch (json.charAt(index)){
                            case '"':
                                end = json.indexOf('"', index + 1);
                                index++;
                                break;
                            case 't':
                            case 'n':
                                end = index + 4;
                                break;
                            case 'f':
                                end = index + 5;
                                break;
                            default:
                                if(Character.isDigit(json.charAt(index))) {
                                    end = index;
                                    while(Character.isDigit(json.charAt(end)) ||
                                            json.charAt(end) == '.'){
                                        end++;
                                    }
                                }
                                break;
                        }
                        String value = json.substring(index, end);
                        index = end + 1;

                        JsonNode node = new Leaf(name, value, root.getLevel() + 1);
                        root.add(node);
                        break;
                    }
                }
                // 当前对象解析结束，退出
            } else if (json.charAt(index) == '}'){
                index++;
                return;
            }
            index++;
        }
    }
    /**
     * 节点的值为数组时构造节点树。
     *
     * @param root 值为数组的节点
     */
    public void getArray(JsonNode root){
        int order = 0;
        while(index < json.length()){
            // 数组元素为对象
            if(json.charAt(index) == '{'){
                index++;
                JsonNode node = new Container(order + "", root.getLevel() + 1);
                parser(node);
                root.add(node);
                order++;
            } else if(json.charAt(index) == '['){
                // 数组元素为数组
                index++;
                JsonNode node = new Container(order + "", root.getLevel() + 1);
                getArray(node);
                root.add(node);
                order++;
            } else if(json.charAt(index) == ']'){
                index++;
                if(order == 0){
                    JsonNode leaf = new Leaf("-1", "emptyArray", root.getLevel() + 1);
                    root.add(leaf);
                }
                return;
            } else if(json.charAt(index) == '\n'
                    || json.charAt(index) == ' '
                    || json.charAt(index) == ','){
                index++;
            } else{
                // 数组元素是具体值
                int end = index;
                switch (json.charAt(index)){
                    case '"':
                        end = json.indexOf('"', index + 1);
                        index++;
                        break;
                    case 't':
                    case 'n':
                        end = index + 4;
                        break;
                    case 'f':
                        end = index + 5;
                        break;
                    default:
                        if(Character.isDigit(json.charAt(index))) {
                            end = index;
                            while(Character.isDigit(json.charAt(end)) ||
                                    json.charAt(end) == '.'){
                                end++;
                            }
                        }
                        break;
                }

                String value = json.substring(index, end);
                index = end + 1;

                JsonNode node = new Leaf(order + "", value, root.getLevel() + 1);
                root.add(node);
                order++;
            }
        }

    }

    /**
     * 用于调试。
     * @param args 命令行参数
     */
    public static void main(String[] args){
       JsonParser ja = new JsonParser();
        ja.getContent("src/main/resources/test.json");
        JsonNode node = new Container("", 0);
        ja.parser(node);
        System.out.println();
    }
}

