package com.mlc.file.parser;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BlockedIp {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String ip;
    private String reason;

    public BlockedIp(String ip, String reason) {
        this.ip = ip;
        this.reason = reason;
    }

    BlockedIp() {
        // just because jpa
    }

    public Integer getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "BlockedIp [id=" + id + ", ip=" + ip + ", reason=" + reason + "]";
    }

}
