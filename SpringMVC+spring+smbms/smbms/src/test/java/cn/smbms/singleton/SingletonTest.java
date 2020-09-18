package cn.smbms.singleton;

import cn.smbms.dao.Singleton;
import org.junit.Test;

public class SingletonTest {
    @Test
    public void test01(){
        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();
        System.out.println(singleton1==singleton2);
    }
}
