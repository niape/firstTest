package cn.smbms.dao;

//懒汉式  -  单例
public class Singleton {
    //创建实例
    private static Singleton singleton;
    //私有构造器
    private Singleton(){}
    //静态公开方法
    public static Singleton getInstance(){
        if(singleton == null){
            singleton = new Singleton();
        }
        return singleton;
    }
}
