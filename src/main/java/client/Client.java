package client;

import builder.Builder;
import factory.Factory;
import factory.RectangleFactory;
import factory.TreeFactory;

import java.util.Scanner;

/**
 * Client类是程序的入口点，用于构建和以不同风格展示JSON节点树。
 * @author huangshx
 * @version v1.0
 */
public class Client {
    /**
     * 主方法，程序的入口。
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the json file path");
        String jsonFilePath = scanner.nextLine();
        System.out.println("Please enter render style(\"tree\" or \"rectangle\")");
        String style = scanner.nextLine();

        Factory factory;
        if(style.equals("tree")){
            factory = new TreeFactory();
        } else if(style.equals("rectangle")){
            factory = new RectangleFactory();
        } else {
            System.out.println("Incorrect command");
            return;
        }
        // src/main/resources/simpleTest.json
        Builder builder = new Builder()
                .buildNode(jsonFilePath)
                .setStyle(factory)
                .setIcon("../config/iconConfig.json");
        builder.setIcon("src/main/resources/iconConfig.json");
        builder.showJson();
    }
}
