package org.csu.mypetstore.domain;

import java.util.Random;

public class MsgCode {
    String code;
    long createTime;
    int type;  //{0:登录，1-绑定号码}

    public MsgCode(int type){
        this.code = String.valueOf((new Random()).nextInt(999999) % 900000 + 100000);
        this.createTime = System.currentTimeMillis()/1000;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MsgCode{" +
                "code='" + code + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
