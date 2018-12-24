package com.invillia.acme.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.invillia.acme.model.Store;
import com.invillia.acme.repository.StoreRepository;

@Service
public class StoreService {

	@Autowired
	StoreRepository storeRepository;

	public List<Store> findByNameAndAddress(String name, String address) {
		return storeRepository.findByNameAndAddress(name, address);
	}

	public List<Store> findAll() {
		return storeRepository.findAll();
	}

	public Optional<Store> findById(Long id) {
		return storeRepository.findById(id);
	}

	public Store save(Store store) {
		return storeRepository.save(store);
	}

	public void deleteById(Long id) {
		storeRepository.deleteById(id);
	}
}
