package com.invillia.acme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.invillia.acme.model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
	
}