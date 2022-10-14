package com.warehouse.endpoint;

import com.warehouse.exception.WarehouseException;
import com.warehouse.model.WarehouseConstant;
import com.warehouse.model.request.Box;
import com.warehouse.model.response.SuccessResponse;
import com.warehouse.model.response.WarehouseServiceResponse;
import com.warehouse.service.api.BoxService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * Endpoint for creating and fetching boxes
 *
 * @author prathush j
 * @version 1.0.0
 * @since 12-Oct-2022
 */

@Slf4j
@AllArgsConstructor
@RequestMapping(path = "/warehouse/v1/box")
@Api(value = "Box Service", tags = {"Location"})
@RestController
public class BoxEndpoint {
    private final BoxService _boxService;

    /**
     * Create Box Operation to create box with given capacity
     *
     * @param box Box Information
     * @return Stored response of Box
     * @throws WarehouseException throw BoxName or Box Capacity is null or empty
     */
    @ApiOperation(value = "Create Box with user specified capacity")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created the box with specified capacity"),
            @ApiResponse(code = 401, message = "Not Authorized to view the resources"),
            @ApiResponse(code = 404, message = "The resource you where trying to reach is not found"),
            @ApiResponse(code = 500, message = "Internal Server error occurred")
    })
    @PostMapping(path = "/createBox", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBox(@Valid @RequestBody Box box) throws WarehouseException {
        try {
            _boxService.saveBoxInformation(box);

            SuccessResponse createBoxSuccessResponse = SuccessResponse.builder().status(WarehouseConstant.OK.getString())
                    .message(WarehouseConstant.BOX_CREATED.getString()).build();

            WarehouseServiceResponse warehouseServiceResponse = WarehouseServiceResponse.<SuccessResponse>builder()
                    .warehouseServiceResponse(createBoxSuccessResponse).build();

            return new ResponseEntity<>(warehouseServiceResponse, HttpStatus.CREATED);
        } catch (Exception exp) {
            log.error("Exception occurred while creating the Box {}", exp);
            throw new WarehouseException(WarehouseConstant.BOX_ALREADY_EXIT.getString());
        }
    }

    /**
     * Get Box with available capacity
     *
     * @return Boxes with available capacity are fetche
     * @throws WarehouseException throw box with available capacity not found
     */
    @ApiOperation(value = "Get Box with available capacity")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Boxes with available capacity are fetched"),
            @ApiResponse(code = 401, message = "Not Authorized to view the resources"),
            @ApiResponse(code = 404, message = "The resource you where trying to reach is not found"),
            @ApiResponse(code = 500, message = "Internal Server error occurred")
    })
    @GetMapping(path = "/getAvailableBox", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAvailableBoxes() throws WarehouseException {
        List<String> availableBoxInformationList = _boxService.getAvailableBoxList();

        if (Objects.isNull(availableBoxInformationList)) {
            throw new WarehouseException(WarehouseConstant.BOXES_WITH_AVAILABLE_CAPACITY_NOT_FOUND.getString());
        }

        WarehouseServiceResponse warehouseServiceResponse = WarehouseServiceResponse.<List<String>>builder()
                .warehouseAvailableBoxList(availableBoxInformationList).build();

        return new ResponseEntity<>(warehouseServiceResponse, HttpStatus.OK);
    }
}
