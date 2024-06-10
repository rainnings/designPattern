package factory;

/**
 * TreeFactory类是一个具体工厂类，用于创建TreeStyle对象。
 */
public class TreeFactory extends Factory{
    /**
     * 创建TreeStyle对象的方法，实现了父类的抽象方法。
     * 
     * @return 创建的TreeStyle对象
     */
    public JsonStyle createStyle(){
        return new TreeStyle();
    }
}
