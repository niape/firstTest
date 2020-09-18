package cn.smbms.tools;

import cn.smbms.dao.BaseDao;
import sun.security.krb5.Config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

//    private static ConfigManager configManager;
    private static Properties properties = new Properties();

    static {
        String configFile = "database.properties";
        InputStream is=BaseDao.class.getClassLoader().getResourceAsStream(configFile);
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String driver;
    private static String url;
    private static String user;
    private static String password;

    private ConfigManager(){}

    /**
     * 单例 -- 懒汉式
     * @param key
     * @return
     */
    /*public static ConfigManager getInstance(){
        if(configManager == null){
            configManager = new ConfigManager();
        }
        return configManager;
    }*/

    /**
     * 单例  --  静态内部类
     */
    private static class Instance{
        private static ConfigManager configManager = new ConfigManager();
    }
    public static ConfigManager getInstance(){
        return Instance.configManager;
    }


    public String getProperty(String key){
        return properties.getProperty(key);
    }
}
