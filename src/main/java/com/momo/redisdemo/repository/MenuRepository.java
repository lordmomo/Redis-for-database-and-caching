package com.momo.redisdemo.repository;

import com.momo.redisdemo.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MenuRepository {

    public static final String HASH_KEY_NAME ="MENU";

    @Autowired
    private RedisTemplate redisTemplate;


    public Boolean save(Menu menu){
        // SETS menu object in MENU-ITEM hashmap at menuId key
        try {
            redisTemplate.opsForHash().put(HASH_KEY_NAME, menu.getId(), menu);
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public Boolean saveSet(Menu menu){
        // SETS menu object in MENU-ITEM hashmap at menuId key
        try {
            redisTemplate.opsForSet().add(HASH_KEY_NAME,menu.getId(),menu);
//                    put(HASH_KEY_NAME, menu.getId(), menu);
            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }

    public List<Menu> findAll(){
        // GET all Menu values

        return redisTemplate.opsForHash().values(HASH_KEY_NAME);
    }

    public Menu findById(int id){
        // GET menu object from MENU-ITEM hashmap by menuId key
        System.out.println("called findById() from redisDB");
        return (Menu)redisTemplate.opsForHash().get(HASH_KEY_NAME,id);
    }
    public Boolean deleteMenu(int id){
        // DELETE the hashkey by menuId from MENU-ITEM hashmap

        redisTemplate.opsForHash().delete(HASH_KEY_NAME,id);
        return true;
    }

}
