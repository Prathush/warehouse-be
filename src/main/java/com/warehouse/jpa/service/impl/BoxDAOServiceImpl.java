package com.warehouse.jpa.service.impl;

import java.util.List;
import javax.annotation.Resource;
import javax.transaction.Transactional;

import com.warehouse.jpa.repository.BoxRepository;
import com.warehouse.model.request.Box;
import org.springframework.stereotype.Repository;
import com.warehouse.jpa.entity.BoxEntity;
import com.warehouse.jpa.service.api.BoxDAOService;

/**
 * BoxDAOServiceImpl class  for executing transaction
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */
@Repository
public class BoxDAOServiceImpl implements BoxDAOService
{
	@Resource
    BoxRepository _boxRepository;

	public void saveBoxInformation(Box box)
	{
		BoxEntity boxEntity = BoxEntity.builder()
			.name(box.getName())
			.capacity(box.getCapacity())
			.availableCapacity(box.getCapacity())
			.build();

		_boxRepository.save(boxEntity);
	}

	@Transactional
	public List<String> retrieveAvailableBoxes(int availableBoxCapacity)
	{
		return _boxRepository.getAvailableCapacityBoxes(availableBoxCapacity);
	}
}
