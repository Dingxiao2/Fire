package fun.juhua.library_springboot.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import fun.juhua.library_springboot.utils.DateUtils;

import java.util.Date;

/**
 * @program: library
 * @description: 火险实体类
 * @author:
 * @create: 2021-10-25 10:57
 **/
public class Fire {
    //林区ID
    @TableId(type = IdType.INPUT)
    private String fireID;
    //林区名
    private String fireName;
    //负责人
    private String fireAuthor;
    //相关部门
    private String firePublisher;
    //检测日期
    private Date publishTime;
    //燃点
    private float firePrice;
    //火险总数
    private int fireSum;
    //消除数量
    private int fireLend;
    //火险因子
    private String tag;
    //火险编号
    private String isbn;

    public Fire(String fireID, String fireName, String fireAuthor, String firePublisher, Date publishTime, float firePrice, int fireSum, int fireLend, String tag, String isbn) {
        this.fireID = fireID;
        this.fireName = fireName;
        this.fireAuthor = fireAuthor;
        this.firePublisher = firePublisher;
        this.publishTime = publishTime;
        this.firePrice = firePrice;
        this.fireSum = fireSum;
        this.fireLend = fireLend;
        this.tag = tag;
        this.isbn = isbn;
    }

    public Fire(String fireID, String fireName, String fireAuthor, String firePublisher, String publishTime, String firePrice, String fireSum, String fireLend, String tag, String isbn) {
        this.fireID = fireID;
        this.fireName = fireName;
        this.fireAuthor = fireAuthor;
        this.firePublisher = firePublisher;
        this.publishTime = new DateUtils().toDate(publishTime);
        this.firePrice = Float.parseFloat(firePrice);
        this.fireSum = Integer.parseInt(fireSum);
        this.fireLend = Integer.parseInt(fireLend);
        this.tag = tag;
        this.isbn = isbn;
    }

    public Fire() {
    }

    @Override
    public String toString() {
        return "Fire{" +
                "fireID='" + fireID + '\'' +
                ", fireName='" + fireName + '\'' +
                ", fireAuthor='" + fireAuthor + '\'' +
                ", firePublisher='" + firePublisher + '\'' +
                ", publishTime=" + publishTime +
                ", firePrice=" + firePrice +
                ", fireSum=" + fireSum +
                ", fireLend=" + fireLend +
                ", tag='" + tag + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }

    public String getFireID() {
        return fireID;
    }

    public void setFireID(String fireID) {
        this.fireID = fireID;
    }

    public String getFireName() {
        return fireName;
    }

    public void setFireName(String fireName) {
        this.fireName = fireName;
    }

    public String getFireAuthor() {
        return fireAuthor;
    }

    public void setFireAuthor(String fireAuthor) {
        this.fireAuthor = fireAuthor;
    }

    public String getFirePublisher() {
        return firePublisher;
    }

    public void setFirePublisher(String firePublisher) {
        this.firePublisher = firePublisher;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public float getFirePrice() {
        return firePrice;
    }

    public void setFirePrice(float firePrice) {
        this.firePrice = firePrice;
    }

    public int getFireSum() {
        return fireSum;
    }

    public void setFireSum(int fireSum) {
        this.fireSum = fireSum;
    }

    public int getFireLend() {
        return fireLend;
    }

    public void setFireLend(int fireLend) {
        this.fireLend = fireLend;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
