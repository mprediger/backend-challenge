package com.invillia.acme.controller;

import com.invillia.acme.model.Store;
import com.invillia.acme.repository.StoreRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class StoreController {

	@Autowired
	StoreRepository storeRepository;

	// TODO IMPLEMENTAR A BUSCA POR MULTIPLOS PARAMETROS
	// Retrieve a Store by parameters
	@RequestMapping(value = "/stores", method = RequestMethod.GET)
	public ResponseEntity<?> findStores(@RequestParam("name") Optional<String> name,
			@RequestParam("address") Optional<String> address) {
		List<Store> stores = null;
		if (name.isPresent() && address.isPresent()) {
			stores = storeRepository.findByNameAndAddress(name.get(), address.get());
		} else {
			stores = storeRepository.findAll();
		}
		if (stores.isEmpty()) {
			return new ResponseEntity<List<Store>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);
	}

	@RequestMapping(value = "/stores/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> findStore(@PathVariable("id") long id) {
		Optional<Store> store = storeRepository.findById(id);
		if (store == null) {
			return new ResponseEntity<Store>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Store>(store.get(), HttpStatus.OK);
	}

	@RequestMapping(value = "/stores", method = RequestMethod.POST)
	public ResponseEntity<Void> newStore(@RequestBody Store store, UriComponentsBuilder ucBuilder) {
		store = storeRepository.save(store);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/stores/{id}").buildAndExpand(store.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/stores/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateStore(@PathVariable("id") long id, @RequestBody Store store) {
		Optional<Store> currentStore = storeRepository.findById(id);
		if (currentStore == null) {
			return new ResponseEntity<Store>(HttpStatus.NOT_FOUND);
		}
		currentStore.get().setName(store.getName());
		currentStore.get().setAddress(store.getAddress());
		storeRepository.save(currentStore.get());
		return new ResponseEntity<Store>(currentStore.get(), HttpStatus.OK);
	}

	@RequestMapping(value = "/stores/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteStore(@PathVariable("id") long id) {

		Optional<Store> store = storeRepository.findById(id);
		if (store == null) {
			return new ResponseEntity<Store>(HttpStatus.NOT_FOUND);
		}

		storeRepository.deleteById(id);
		return new ResponseEntity<Store>(HttpStatus.NO_CONTENT);
	}

}
