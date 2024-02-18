package com.momo.redisdemo.controller;

import com.momo.redisdemo.entity.Menu;
import com.momo.redisdemo.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @PostMapping("/save-menu")
    public ResponseEntity<String> save(@RequestBody Menu menu){
        log.info("Menu Saved");
        String message = menuService.save(menu);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/get-list-menu")
    public ResponseEntity<List<Menu>> getMenu(){
        log.info("Menu list shown");
        List<Menu> menuList = menuService.getListMenu();
        return ResponseEntity.ok(menuList);
    }

    @GetMapping("/get-menu/{menuId}")
    public ResponseEntity<Menu> getMenuById(@PathVariable("menuId") int menuId){
        log.info("Menu id shown");
        Menu menu = menuService.getMenuById(menuId);
        return ResponseEntity.ok(menu);
    }

    @PutMapping("/update-menu/{menuID}")
    public ResponseEntity<String> updateMenuById(@PathVariable("menuID") int menuID,@RequestBody Menu updateMenu){
        Boolean checkMenu = menuService.updateMenuById(menuID,updateMenu);
        if(checkMenu){
            return ResponseEntity.ok("Update Successful");
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Id doesn't exist");
        }
    }

    @DeleteMapping("/delete/{menuId}")
    public ResponseEntity<String> deleteMenuById(@PathVariable("menuId") int menuId){
        log.info("Menu removed");
        Boolean checkMenu = menuService.deleteMenu(menuId);
        if(checkMenu) {
            return ResponseEntity.ok("Menu deleted successfully !!");
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Menu id not found");
        }
    }


}
