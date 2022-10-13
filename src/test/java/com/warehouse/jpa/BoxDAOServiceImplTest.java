package com.warehouse.jpa;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;

import com.warehouse.jpa.entity.BoxEntity;
import com.warehouse.jpa.repository.BoxRepository;
import com.warehouse.jpa.service.impl.BoxDAOServiceImpl;
import com.warehouse.model.request.Box;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class BoxDAOServiceImplTest
{
	@InjectMocks
    BoxDAOServiceImpl _boxDAOService;

	@Mock
    BoxRepository _boxRepository;

	@Test
	public void shouldSaveBoxInformation()
	{
		_boxDAOService.saveBoxInformation(Box.builder().id("1").name("testBoxName").capacity(3).build());

		verify(_boxRepository).save(any(BoxEntity.class));
	}

	@Test
	public void shouldReturnBoxesWhenAvailableCapacityOfBoxesIsNotZero()
	{
		when(_boxRepository.getAvailableCapacityBoxes(anyInt())).thenReturn(Arrays.asList("BoxName1,BoxName2"));
		List<String> availableBoxes = _boxDAOService.retrieveAvailableBoxes(0);

		Assert.assertNotNull(availableBoxes);
		Assert.assertNotNull("BoxName1", availableBoxes.get(0));
	}

	@Test
	public void shouldNotReturnBoxesWhenAvailableCapacityOfBoxesIsZero()
	{
		when(_boxRepository.getAvailableCapacityBoxes(anyInt())).thenReturn(Arrays.asList());
		List<String> availableBoxes = _boxDAOService.retrieveAvailableBoxes(0);

		Assert.assertEquals(0, availableBoxes.size());
	}
}
