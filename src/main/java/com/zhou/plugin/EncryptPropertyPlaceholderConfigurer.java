package com.zhou.plugin;

import com.zhou.util.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 支持加密配置文件插件
 *
 * @author eli
 * @date 2017/9/18 22:08
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
    private Logger logger = LoggerFactory.getLogger(EncryptPropertyPlaceholderConfigurer.class);
    private String[] propertyNames = {
            "master.jdbc.password", "slave.jdbc.password"
    };

    /**
     * 解密指定propertyName的加密属性值
     * convertProperty方法会读取每一个properties参数和值,再本方法中要做改造properties
     *
     * @param propertyName
     * @param propertyValue
     * @return
     */
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        try {
            logger.info(propertyName+" :"+propertyValue);
            for (String p : propertyNames) {

                if (p.equalsIgnoreCase(propertyName)) {
                    logger.info(AESUtil.AESDecode(propertyValue));
                    return AESUtil.AESDecode(propertyValue);
                }
            }
        }catch (Exception e){
            logger.error("解密出错!",e);
        }
        return super.convertProperty(propertyName, propertyValue);
    }
}
