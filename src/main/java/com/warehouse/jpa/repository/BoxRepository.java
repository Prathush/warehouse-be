package com.warehouse.jpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.warehouse.jpa.entity.BoxEntity;

/**
 * Box repository interface for handling box transaction
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */
public interface BoxRepository extends JpaRepository<BoxEntity, String>
{
	BoxEntity findByBoxNameAndAndBoxAvailableCapacityNot(String boxName,int capacity);

	@Query("SELECT u.name FROM BoxEntity u WHERE u.availableCapacity not in :capacity")
	List<String> getAvailableCapacityBoxes(@Param("capacity")int boxMinimumAvailableCapacity);
}
