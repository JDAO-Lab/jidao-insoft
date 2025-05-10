package com.inSoft.utils;
import com.inSoft.pojo.SysAdmin;
import com.inSoft.pojo.SysConfig;
import com.inSoft.pojo.WebUsers;

import java.lang.reflect.Field;
import java.util.*;

public class TransUtils {

    /**
     * 将List<SysConfig>转换为Map<String, String>对象
     * @param sysConfigList List<SysConfig>
     * @return Map<String, String>
     */
    public static Map<String, String> convertToSysConfMap(List<SysConfig> sysConfigList) {
        Map<String, String> map = new HashMap<>();
        for (SysConfig config : sysConfigList) {
            try {
                Field nameField = SysConfig.class.getDeclaredField("name");
                Field valueField = SysConfig.class.getDeclaredField("value");
                nameField.setAccessible(true);
                valueField.setAccessible(true);
                String name = (String) nameField.get(config);
                String value = (String) valueField.get(config);
                map.put(name, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    /**
     * 将SysAdmin对象转换为Map<String, String>对象
     * @param sysAdmin SysAdmin对象
     * @return Map<String, String>
     */
    public static Map<String, String> convertSysAdminToMap(SysAdmin sysAdmin) {
        Map<String, String> map = new HashMap<>();
        Field[] fields = SysAdmin.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(sysAdmin);
                if (value != null) {
                    map.put(field.getName(), value.toString());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    /**
     * 将WebUsers对象转换为Map<String, String>对象
     * @param webUsers
     * @return
     */
    public static Map<String, String> convertWebUserToMap(WebUsers webUsers) {
        Map<String, String> map = new HashMap<>();
        Field[] fields = WebUsers.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(webUsers);
                if (value != null) {
                    map.put(field.getName(), value.toString());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

}
