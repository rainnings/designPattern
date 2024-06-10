package factory;

/**
 * RectangleFactory类是一个具体工厂类，用于创建RectangleStyle对象。
 */
public class RectangleFactory extends Factory{
    /**
     * 创建RectangleStyle对象的方法，实现了父类的抽象方法。
     * 
     * @return 创建的RectangleStyle对象
     */
    public JsonStyle createStyle(){
        return new RectangleStyle();
    }
}
