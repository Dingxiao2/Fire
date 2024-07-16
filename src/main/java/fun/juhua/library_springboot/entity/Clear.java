package fun.juhua.library_springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import fun.juhua.library_springboot.utils.DateUtils;

import java.util.Date;

/**
 * @program: library
 * @description: 预警实体类
 * @author:
 * @create: 2021-10-25 11:13
 **/
public class Clear {
    //读者id
    @TableId(type = IdType.INPUT)
    private String monitorID;
    //火险id
    private String fireID;
    //预警时间
    private Date clearTime;
    //消除时间
    private Date returnTime;

    public Clear(String monitorID, String fireID, Date clearTime, Date returnTime) {
        this.monitorID = monitorID;
        this.fireID = fireID;
        this.clearTime = clearTime;
        this.returnTime = returnTime;
    }

    public Clear(String monitorID, String fireID, String clearTime, Date returnTime) {
        this.monitorID = monitorID;
        this.fireID = fireID;
        this.clearTime = new DateUtils().toDate(clearTime);
        this.returnTime = returnTime;
    }

    public Clear(String monitorID, String fireID, String clearTime) {
        this.monitorID = monitorID;
        this.fireID = fireID;
        this.clearTime = new DateUtils().toDate(clearTime);
        this.returnTime = null;
    }

    public Clear(String monitorID, String fireID, Date clearTime) {
        this.monitorID = monitorID;
        this.fireID = fireID;
        this.clearTime = clearTime;
        this.returnTime = null;
    }

    public Clear() {
    }

    @Override
    public String toString() {
        return "clear{" +
                "monitorID='" + monitorID + '\'' +
                ", fireID='" + fireID + '\'' +
                ", clearTime=" + clearTime +
                ", returnTime=" + returnTime +
                '}';
    }

    public String getMonitorID() {
        return monitorID;
    }

    public void setMonitorID(String monitorID) {
        this.monitorID = monitorID;
    }

    public String getFireID() {
        return fireID;
    }

    public void setFireID(String fireID) {
        this.fireID = fireID;
    }

    public Date getClearTime() {
        return clearTime;
    }

    public void setClearTime(Date clearTime) {
        this.clearTime = clearTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }
}
