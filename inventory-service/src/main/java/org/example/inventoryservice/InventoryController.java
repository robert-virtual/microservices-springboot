package org.example.inventoryservice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryRepository inventoryRepository;
    @GetMapping("all")
    public Page<Inventory> getAll(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        Pageable pageable = PageRequest.of(page,size);
       return  inventoryRepository.findAll(pageable);
    }
    @PostMapping("create")
    public Inventory getAll(@RequestBody Inventory inventory){
        return  inventoryRepository.save(inventory);
    }
}
