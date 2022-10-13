package com.warehouse.service.api;

import java.util.List;
import com.warehouse.model.request.Box;

public interface BoxService
{
	void saveBoxInformation(Box box);
	List<String> getAvailableBoxList();
}
