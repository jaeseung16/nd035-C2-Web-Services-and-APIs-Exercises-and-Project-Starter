package com.udacity.pricing.domain.price.repository;

import com.udacity.pricing.domain.price.entity.Price;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends CrudRepository<Price, Long> {

}
