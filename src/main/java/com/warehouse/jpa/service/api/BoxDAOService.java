package com.warehouse.jpa.service.api;

import com.warehouse.model.request.Box;

import java.util.List;

public interface BoxDAOService {
    void saveBoxInformation(Box box);

    List<String> retrieveAvailableBoxes(int availableBoxCapacity);
}