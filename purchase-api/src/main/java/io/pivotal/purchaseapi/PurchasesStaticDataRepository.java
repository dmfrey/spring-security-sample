package io.pivotal.purchaseapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class PurchasesStaticDataRepository implements PurchasesRepository {

    private StaticDatabase staticDatabase;

    public PurchasesStaticDataRepository(StaticDatabase staticDatabase) {
        this.staticDatabase = staticDatabase;
    }

    @Override
    public List<Purchase> findAll() {
        log.info( "findAll : enter" );

        return new ArrayList<>(staticDatabase.getPurchases().values());
    }

    @Override
    public Purchase save(Purchase purchase) {
        return staticDatabase.save(purchase);
    }
}
