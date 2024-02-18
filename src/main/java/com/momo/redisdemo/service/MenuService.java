package com.momo.redisdemo.service;

import com.momo.redisdemo.entity.Menu;
import com.momo.redisdemo.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@EnableCaching
public class MenuService {
    @Autowired
    MenuRepository menuRepository;

    public String save(Menu menu) {

        Boolean menuAddBool =  menuRepository.save(menu);

        if(menuAddBool){
            return "Menu Added successfully";
        }
        else {
            return "Failed adding to menu";
        }
    }

    public List<Menu> getListMenu() {
        return menuRepository.findAll();
    }

    @Cacheable(key="#menuId",value="MENU",unless = "#result.price>100")
    public Menu getMenuById(int menuId) {
        return menuRepository.findById(menuId);
    }
    @CacheEvict(key ="#menuId",value = "MENU")
    public Boolean deleteMenu(int menuId) {
         return menuRepository.deleteMenu(menuId);

    }

    @CachePut(key = "#menuId",value = "MENU")
    public Boolean updateMenuById(int menuId,Menu updateMenu) {
        Optional<Menu> checkMenu = Optional.ofNullable(menuRepository.findById(menuId));
        if(checkMenu.isPresent()){
            Menu menu = checkMenu.get();

//            menu.setId(updateMenu.getId());
            menu.setItemName(updateMenu.getItemName());
            menu.setPrice(updateMenu.getPrice());
            menuRepository.save(menu);
            return true;
        }
        else{
            return false;
        }
    }
}
