package com.miu.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.miu.domain.PaymentType;

import com.miu.repository.PaymentTypeRepository;
import com.miu.web.rest.util.HeaderUtil;
import com.miu.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PaymentType.
 */
@RestController
@RequestMapping("/api")
public class PaymentTypeResource {

    private final Logger log = LoggerFactory.getLogger(PaymentTypeResource.class);
        
    @Inject
    private PaymentTypeRepository paymentTypeRepository;

    /**
     * POST  /payment-types : Create a new paymentType.
     *
     * @param paymentType the paymentType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentType, or with status 400 (Bad Request) if the paymentType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-types")
    @Timed
    public ResponseEntity<PaymentType> createPaymentType(@Valid @RequestBody PaymentType paymentType) throws URISyntaxException {
        log.debug("REST request to save PaymentType : {}", paymentType);
        if (paymentType.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("paymentType", "idexists", "A new paymentType cannot already have an ID")).body(null);
        }
        PaymentType result = paymentTypeRepository.save(paymentType);
        return ResponseEntity.created(new URI("/api/payment-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("paymentType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-types : Updates an existing paymentType.
     *
     * @param paymentType the paymentType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentType,
     * or with status 400 (Bad Request) if the paymentType is not valid,
     * or with status 500 (Internal Server Error) if the paymentType couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-types")
    @Timed
    public ResponseEntity<PaymentType> updatePaymentType(@Valid @RequestBody PaymentType paymentType) throws URISyntaxException {
        log.debug("REST request to update PaymentType : {}", paymentType);
        if (paymentType.getId() == null) {
            return createPaymentType(paymentType);
        }
        PaymentType result = paymentTypeRepository.save(paymentType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("paymentType", paymentType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-types : get all the paymentTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of paymentTypes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/payment-types")
    @Timed
    public ResponseEntity<List<PaymentType>> getAllPaymentTypes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PaymentTypes");
        Page<PaymentType> page = paymentTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payment-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /payment-types/:id : get the "id" paymentType.
     *
     * @param id the id of the paymentType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentType, or with status 404 (Not Found)
     */
    @GetMapping("/payment-types/{id}")
    @Timed
    public ResponseEntity<PaymentType> getPaymentType(@PathVariable Long id) {
        log.debug("REST request to get PaymentType : {}", id);
        PaymentType paymentType = paymentTypeRepository.findOne(id);
        return Optional.ofNullable(paymentType)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /payment-types/:id : delete the "id" paymentType.
     *
     * @param id the id of the paymentType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payment-types/{id}")
    @Timed
    public ResponseEntity<Void> deletePaymentType(@PathVariable Long id) {
        log.debug("REST request to delete PaymentType : {}", id);
        paymentTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("paymentType", id.toString())).build();
    }

}
