package com.warehouse.jpa.service.api;

import java.util.List;
import com.warehouse.model.request.Box;

public interface BoxDAOService
{
	void saveBoxInformation(Box box);
	List<String> retrieveAvailableBoxes(int availableBoxCapacity);
}