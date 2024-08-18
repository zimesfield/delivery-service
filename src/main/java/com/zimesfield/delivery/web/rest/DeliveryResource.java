package com.zimesfield.delivery.web.rest;

import com.zimesfield.delivery.adapter.mapper.DeliveryRequestMapper;
import com.zimesfield.delivery.domain.model.DeliveryModel;
import com.zimesfield.delivery.domain.usecase.CreateDeliveryUseCase;
import com.zimesfield.delivery.domain.usecase.FindDeliveryRequestUseCase;
import com.zimesfield.delivery.model.DeliveryRequest;
import com.zimesfield.delivery.model.DeliveryResponse;
import com.zimesfield.delivery.web.api.DeliveriesApiDelegate;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

@Service
@RequiredArgsConstructor
public class DeliveryResource implements DeliveriesApiDelegate {

    private final CreateDeliveryUseCase createDeliveryUseCase;
    private final FindDeliveryRequestUseCase findDeliveryRequestUseCase;
    private final DeliveryRequestMapper deliveryRequestMapper;

    @Override
    public ResponseEntity<List<DeliveryResponse>> getDeliveries(Pageable pageable) {
        final Page<DeliveryResponse> page = findDeliveryRequestUseCase.apply(pageable).map(deliveryRequestMapper::toResponse);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DeliveryResponse> getDelivery(UUID deliveryId) {
        return ResponseEntity.ok(deliveryRequestMapper.toResponse(findDeliveryRequestUseCase.apply(deliveryId)));
    }

    @Override
    public ResponseEntity<DeliveryResponse> request(DeliveryRequest deliveryRequest) {
        DeliveryModel deliveryModel = createDeliveryUseCase.apply(deliveryRequestMapper.toModel(deliveryRequest));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("api/delivery/{deliveryId}")
            .buildAndExpand(deliveryModel.getDeliveryId())
            .toUri();
        return ResponseEntity.created(location).body(deliveryRequestMapper.toResponse(deliveryModel));
    }
}
