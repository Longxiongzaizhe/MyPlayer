package com.hjl.testmodule.network;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hzg on 16/12/6.
 */

public class User implements Serializable {
    public WyUser wyUser;
    public List<Project> projectList;
    public Status status;
    private boolean isCollectFeature;//是否采集了人脸

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isCollectFeature() {
        return isCollectFeature;
    }

    public void setCollectFeature(boolean collectFeature) {
        isCollectFeature = collectFeature;
    }

    public static class Status implements Serializable {
        public int id;
        public int startTime;
        public int endTime;
        public long lastTime;
        public String accountId;
        public boolean isMetting;
        public boolean isWorking;
        public WorkDayObj workDayObj;

        public Status(){

        }

        public class WorkDayObj implements Serializable{

            public boolean Wed;
            public boolean Fri;
            public boolean Tus;
            public boolean Sun;
            public boolean Sat;
            public boolean Mon;
            public boolean Thu;


            @Override
            public String toString() {
                return "WorkDayObj{" +
                        "Wed=" + Wed +
                        ", Fri=" + Fri +
                        ", Tus=" + Tus +
                        ", Sun=" + Sun +
                        ", Sat=" + Sat +
                        ", Mon=" + Mon +
                        ", Thu=" + Thu +
                        '}';
            }
        }


        @Override
        public String toString() {
            return "PostStatus{" +
                    "id=" + id +
                    ", startTime=" + startTime +
                    ", endTime=" + endTime +
                    ", lastTime=" + lastTime +
                    ", accountId='" + accountId + '\'' +
                    ", isMetting=" + isMetting +
                    ", isWorking=" + isWorking +
                    ", workDayObj=" + workDayObj +
                    '}';
        }
    }

    public static class Project implements Serializable {
        public String id;
        public String name;
        public String code;
        public String latitude;
        public String longitude;
        public String divisionName;
        public String ccProjectCode;
        public String projectCode;
        public String entranceCode;
        public String patrolCode;
        public String parkingCode;
        public String nodeType;
        public String nodeID;
        public boolean isWorkstation;//工作站是否是模块直接开门
    }

    public class WyUser implements Serializable {
        public String id;
        public String name;
        public String code;
        public String telephone;
        public String identity;
        public String sex;
        public String wyAccountName;
        public WyAccount wyAccount;

        public class WyAccount implements Serializable {
            public String id;
            public String token;
            public String imageUrl;
            public String loginTime;
            public String workCode;
            public WyRole wyRole;//角色
            public String isFirstLogin;
            public String faceId;
            public String isLogin;

            public void setWyRole(WyRole wyRole) {
                this.wyRole = wyRole;
            }

            public class WyRole implements Serializable {
                public String id;
                public String name;
                public String code;
                public String pid;
                public int dataPlatformCode;
            }
        }
    }
}
