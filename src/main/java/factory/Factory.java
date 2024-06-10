package factory;

/**
 * Factory类是一个抽象工厂类，用于创建JsonStyle对象。
 */
public abstract class Factory {
    /**
     * 创建JsonStyle对象的抽象方法，由子类实现具体创建逻辑。
     * 
     * @return 创建的JsonStyle对象
     */
    public abstract JsonStyle createStyle();
}
