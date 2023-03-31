package org.example.inventoryservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {
    private final InventoryRepository inventoryRepository;

    @PostMapping("/check")
    public String getBySku(
            @RequestBody List<Map<String, Object>> details
    ) {
        log.info(details.toString());
        List<Inventory> inventories = inventoryRepository.findBySkuCodeIn(
                details.stream().map(x -> x.get("skuCode").toString()).collect(Collectors.toList())
        );
        log.info("inventories: {}",inventories);
        String res = "";
        for (Map<String, Object> detail : details) {
            Optional<Inventory> inventory = inventories.stream().filter(
                    x -> x.getSkuCode() != detail.get("skuCode")
            ).findFirst();
            if (inventory.isEmpty()) {
                res = "Empty: " + detail.get("skuCode").toString();
                break;
            }

            if (
                    inventory.get().getQuantity() < Integer.parseInt(
                            detail.get("quantity").toString()
                    )
            ) {
                res = detail.get("skuCode").toString();
                break;
            }
        }
        log.info("res: {}", res);
        return res;
    }

    @GetMapping("{sku_code}/{quantity}")
    public boolean getBySku(
            @PathVariable String sku_code,
            @PathVariable int quantity
    ) {
        Optional<Inventory> inventory = inventoryRepository.findBySkuCode(sku_code);
        return inventory.filter(value -> value.getQuantity() >= quantity).isPresent();
    }

    @GetMapping("all")
    public Page<Inventory> getAll(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return inventoryRepository.findAll(pageable);
    }

    @PostMapping("create")
    public Inventory getAll(@RequestBody Inventory inventory) {
        return inventoryRepository.save(inventory);
    }
}
