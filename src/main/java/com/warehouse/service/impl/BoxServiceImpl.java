package com.warehouse.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.warehouse.jpa.service.api.BoxDAOService;
import com.warehouse.model.request.Box;
import com.warehouse.service.api.BoxService;
import lombok.AllArgsConstructor;

/**
 * Box Service Impl class of warehouse service
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */
@AllArgsConstructor
@Service
public class BoxServiceImpl implements BoxService
{
	private final BoxDAOService _boxDAOService;

	@Override
	public void saveBoxInformation(Box box)
	{
		_boxDAOService.saveBoxInformation(box);
	}

	@Override
	public List<String> getAvailableBoxList()
	{
		return _boxDAOService.retrieveAvailableBoxes(0);
	}

}
