package com.invillia.acme.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.invillia.acme.model.Store;
import com.invillia.acme.service.StoreService;

import io.swagger.annotations.ApiOperation;

@RestController
public class StoreController {

	@Autowired
	StoreService storeService;

	@ApiOperation(value = "Return all stores or filter by parameters")
	@RequestMapping(value = "/stores", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findStores(@RequestParam("name") Optional<String> name,
			@RequestParam("address") Optional<String> address) {
		List<Store> stores = null;
		if (name.isPresent() && address.isPresent()) {
			stores = storeService.findByNameAndAddress(name.get(), address.get());
		} else {
			stores = storeService.findAll();
		}
		if (stores.isEmpty()) {
			return new ResponseEntity<List<Store>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Store>>(stores, HttpStatus.OK);
	}

	@ApiOperation(value = "Return store by id")
	@RequestMapping(value = "/stores/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> findStore(@PathVariable("id") long id) {
		Optional<Store> store = storeService.findById(id);
		if (store == null) {
			return new ResponseEntity<Store>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Store>(store.get(), HttpStatus.OK);
	}

	@ApiOperation(value = "Create a new store")
	@RequestMapping(value = "/stores", method = RequestMethod.POST)
	public ResponseEntity<Void> newStore(@RequestBody Store store, UriComponentsBuilder ucBuilder) {
		store = storeService.save(store);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/stores/{id}").buildAndExpand(store.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@ApiOperation(value = "Update store all data")
	@RequestMapping(value = "/stores/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateStore(@PathVariable("id") long id, @RequestBody Store store) {
		Optional<Store> currentStore = storeService.findById(id);
		if (currentStore == null) {
			return new ResponseEntity<Store>(HttpStatus.NOT_FOUND);
		}
		currentStore.get().setName(store.getName());
		currentStore.get().setAddress(store.getAddress());
		storeService.save(currentStore.get());
		return new ResponseEntity<Store>(currentStore.get(), HttpStatus.OK);
	}

	@ApiOperation(value = "Delete a Store by id")
	@RequestMapping(value = "/stores/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteStore(@PathVariable("id") long id) {
		Optional<Store> store = storeService.findById(id);
		if (store == null) {
			return new ResponseEntity<Store>(HttpStatus.NOT_FOUND);
		}
		storeService.deleteById(id);
		return new ResponseEntity<Store>(HttpStatus.NO_CONTENT);
	}

}
