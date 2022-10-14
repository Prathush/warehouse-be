package com.warehouse.service.api;

import com.warehouse.model.request.Box;

import java.util.List;

public interface BoxService {
    void saveBoxInformation(Box box);

    List<String> getAvailableBoxList();
}
