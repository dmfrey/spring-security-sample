package io.pivotal.purchaseapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/purchases")
@Slf4j
public class PurchasesRestController {

    private PurchasesRepository purchasesRepository;

    public PurchasesRestController( PurchasesRepository purchasesRepository ) {
        this.purchasesRepository = purchasesRepository;
    }

    @GetMapping
    public List<Purchase> list() {
        log.info( "list : enter" );

        return purchasesRepository.findAll();
    }
}
