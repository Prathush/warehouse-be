package com.warehouse.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;

import com.warehouse.jpa.service.api.BoxDAOService;
import com.warehouse.model.request.Box;
import com.warehouse.service.impl.BoxServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class BoxServiceImplTest
{
	@InjectMocks
    BoxServiceImpl _boxService;

	@Mock
    BoxDAOService _boxDAOService;

	@Test
	public void shouldStoreBoxInformation()
	{
		_boxService.saveBoxInformation(new Box());

		verify(_boxDAOService).saveBoxInformation(any(Box.class));
	}

	@Test
	public void shouldFetchAvailableBoxInformation()
	{
		when(_boxDAOService.retrieveAvailableBoxes(0)).thenReturn(Arrays.asList("testBox1", "testBox2"));

		List<String> availableBoxList = _boxService.getAvailableBoxList();

		Assert.assertNotNull(availableBoxList);
		Assert.assertEquals("testBox1", availableBoxList.get(0));
	}
}
