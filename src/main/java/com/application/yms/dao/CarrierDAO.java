package com.application.yms.dao;

import com.application.yms.model.Carrier;

public interface CarrierDAO {
    Carrier save(Carrier carrier);
    Carrier findByScac(String scac);
    boolean existsByScac(String scac);
    Carrier update(Carrier carrier);
    Carrier softDeleteByScac(String scac);
}

