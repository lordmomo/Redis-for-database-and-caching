package com.momo.redisdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


// while using Redis concept,
// make sure that our model class
// should implement java.io.Serializable interface.

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Menu")
public class Menu implements Serializable {

    @Id
    private int id;
    private String itemName;
    private long price;
}
