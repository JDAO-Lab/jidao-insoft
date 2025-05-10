package com.inSoft.pojo.vo;

import com.inSoft.enums.SysLogResultEnum;
import com.inSoft.pojo.SysLog;
import lombok.Data;

@Data
public class SysLogVo extends SysLog {
    //处理枚举信息
    private String resultText;

    /**
     * vo实体类处理实体类枚举参数方法
     * 使用说明：
     * 1.单一对象调用
     * SysLog sysLog = sysLogService.getSysLogById(logId);
     * SysLogVo sysLogVo = new SysLogVo(sysLog);
     * 2.页面列表数据调用
     * // 根据条件查询日志列表
     * List<SysLog> sysLogList = sysLogService.listByCondition(sysLog);
     * // 将查询到的SysLog对象转换为SysLogVO对象
     * List<SysLogVo> sysLogVoList = sysLogList.stream().map(SysLogVo::new).collect(Collectors.toList());
     * 3.延伸方法：保障复用性的基础上，可以自定义更多的参数输出到前端。
     * @param sysLog
     */
    public SysLogVo(SysLog sysLog) {
        super.setId(sysLog.getId());
        super.setIp(sysLog.getIp());
        super.setPath(sysLog.getPath());
        super.setRemarks(sysLog.getRemarks());
        super.setResult(sysLog.getResult());
        super.setCreatedAt(sysLog.getCreatedAt());
        super.setMethod(sysLog.getMethod());
        this.resultText = SysLogResultEnum.getByCode(sysLog.getResult()).getValue();
    }

}