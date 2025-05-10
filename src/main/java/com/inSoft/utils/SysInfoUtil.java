package com.inSoft.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;

/**
 * SysInfoUtil 提供了获取系统相关详细信息的方法，包括：
 *
 * - 系统名称及版本信息
 * - Java SDK 版本
 * - 内存大小
 * - 磁盘大小
 * - MySQL版本（需确保已连接MySQL数据库）
 *
 * 注意：获取磁盘大小和MySQL版本可能需要额外的权限或依赖，具体实现可能因环境而异。
 */
public class SysInfoUtil {

    // 初始化DecimalFormat，设置保留两位小数
    private static DecimalFormat df = new DecimalFormat("#.##");

    /**
     * 获取当前运行Java应用的系统名称及版本信息。
     *
     * @return 系统名称及版本信息字符串
     */
    public String getSystemNameAndVersion() {
        return System.getProperty("os.name") + " 版本：" + System.getProperty("os.version");
    }

    /**
     * 获取当前使用的Java SDK版本。
     *
     * @return Java SDK版本字符串
     */
    public String getJavaSDKVersion() {
        return System.getProperty("java.version");
    }

    /**
     * 获取当前Java应用的内存大小（堆内存）
     * @return 内存大小（以字节为单位）
     */
    public long getMemorySize() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    /**
     * 获取指定路径下的磁盘可用空间大小。
     *
     * @param path 要检查的文件或目录路径
     * @return 磁盘可用空间大小（以字节为单位）
     * @throws IllegalArgumentException 如果路径无效或无法访问
     */
    public long getDiskFreeSpace(String path) throws IllegalArgumentException {
        java.io.File file = new java.io.File(path);
        if (!file.exists()) {
            throw new IllegalArgumentException("Invalid or inaccessible path: " + path);
        }
        return file.getUsableSpace();
    }

    /**
     * 获取已连接MySQL数据库的版本信息。
     *
     * @param connection MySQL数据库连接对象
     * @return MySQL版本字符串
     * @throws SQLException 如果查询失败或未找到版本信息
     */
    public String getMySQLVersion(Connection connection) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        return metaData.getDatabaseProductVersion();
    }

    public double bytesToMb(long bytes) {
        double mb = bytes / (1024.0 * 1024.0);
        return Double.parseDouble(df.format(mb)); // 使用DecimalFormat进行格式化并转换回double
    }

    public double bytesToGb(long bytes) {
        double gb = bytes / (1024.0 * 1024.0 * 1024.0);
        return Double.parseDouble(df.format(gb)); // 使用DecimalFormat进行格式化并转换回double
    }


    /**
     * 获取系统核心数
     */
    public int getCpuNum() {
        return Runtime.getRuntime().availableProcessors();
    }

    /**
     * 获取当前活跃线程数
     */
    public int getActiveThreadNum() {
        return Thread.activeCount();
    }

}
