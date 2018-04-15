package com.pansoft.moneymanager.utils;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.beans.ConstructorProperties;
import java.io.Serializable;

/**
 * 作者：吕振鹏
 * E-mail:lvzhenpeng@pansoft.com
 * 创建时间：2018年03月21日
 * 时间：15:51
 * 版本：v1.0.0
 * 类描述：
 * 修改时间：
 */
public class Dump implements Serializable {
    private static final long serialVersionUID = 1L;
    private int status;
    private String message;
    private Long timestamp;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public static Dump success() {
        Dump dump = builder().status(200).message("execute successfully").data((Object) null).build();
        return dump;
    }

    public static Dump success(Object data) {
        Dump dump = builder().status(200).message("execute successfully").data(data).build();
        return dump;
    }

    public static Dump success(String msg) {
        Dump dump = builder().status(200).message(msg).data((Object) null).build();
        return dump;
    }

    public static Dump success(String msg, Object data) {
        Dump dump = builder().status(200).message(msg).data(data).build();
        return dump;
    }

    public static Dump success(int code, String msg) {
        Dump dump = builder().status(code).message(msg).data((Object) null).build();
        return dump;
    }

    public static Dump success(int code, String msg, Object data) {
        Dump dump = builder().status(code).message(msg).data(data).build();
        return dump;
    }

    public static Dump fail() {
        Dump dump = builder().status(500).message("execute failed").data((Object) null).build();
        return dump;
    }

    public static Dump fail(Object data) {
        Dump dump = builder().status(500).message("execute failed").data(data).build();
        return dump;
    }

    public static Dump fail(String msg) {
        Dump dump = builder().status(500).message(msg).data((Object) null).build();
        return dump;
    }

    public static Dump fail(String msg, Object data) {
        Dump dump = builder().status(500).message(msg).data(data).build();
        return dump;
    }

    public static Dump fail(int code, String msg) {
        Dump dump = builder().status(code).message(msg).data((Object) null).build();
        return dump;
    }

    public static Dump fail(int code, String msg, Object data) {
        Dump dump = builder().status(code).message(msg).data(data).build();
        return dump;
    }

    private static Long $default$timestamp() {
        return System.currentTimeMillis();
    }

    @ConstructorProperties({"status", "message", "timestamp", "data"})
    Dump(int status, String message, Long timestamp, Object data) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.data = data;
    }

    public static DumpBuilder builder() {
        return new DumpBuilder();
    }

    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public Object getData() {
        return this.data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Dump)) {
            return false;
        } else {
            Dump other = (Dump) o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getStatus() != other.getStatus()) {
                return false;
            } else {
                label49:
                {
                    Object this$message = this.getMessage();
                    Object other$message = other.getMessage();
                    if (this$message == null) {
                        if (other$message == null) {
                            break label49;
                        }
                    } else if (this$message.equals(other$message)) {
                        break label49;
                    }

                    return false;
                }

                Object this$timestamp = this.getTimestamp();
                Object other$timestamp = other.getTimestamp();
                if (this$timestamp == null) {
                    if (other$timestamp != null) {
                        return false;
                    }
                } else if (!this$timestamp.equals(other$timestamp)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Dump;
    }

    public int hashCode() {
        int result = 1;
        result = result * 59 + this.getStatus();
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : $timestamp.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "Dump(status=" + this.getStatus() + ", message=" + this.getMessage() + ", timestamp=" + this.getTimestamp() + ", data=" + this.getData() + ")";
    }

    public static class DumpBuilder {
        private int status;
        private String message;
        private Long timestamp;
        private boolean timestamp$set;
        private Object data;

        DumpBuilder() {
        }

        public DumpBuilder status(int status) {
            this.status = status;
            return this;
        }

        public DumpBuilder message(String message) {
            this.message = message;
            return this;
        }

        public DumpBuilder timestamp(Long timestamp) {
            this.timestamp = timestamp;
            this.timestamp$set = true;
            return this;
        }

        public DumpBuilder data(Object data) {
            this.data = data;
            return this;
        }

        public Dump build() {
            return new Dump(this.status, this.message, this.timestamp$set ? this.timestamp : Dump.$default$timestamp(), this.data);
        }

        public String toString() {
            return "Dump.DumpBuilder(status=" + this.status + ", message=" + this.message + ", timestamp=" + this.timestamp + ", data=" + this.data + ")";
        }
    }
}