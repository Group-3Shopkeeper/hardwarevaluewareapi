package com.hardwarevaluewareapi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.Query;
import com.hardwarevaluewareapi.bean.Favorite;
import com.hardwarevaluewareapi.exception.ResourceNotFoundException;
import com.hardwarevaluewareapi.service.FavoriteService;

@RestController
@RequestMapping("/favorite")

public class FavoriteController {
	@Autowired
	private FavoriteService favoriteService;

	@PostMapping("/")
	public Favorite saveFavourite(@RequestBody Favorite favorite) throws Exception {
		return favoriteService.saveFavorite(favorite);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<List<Favorite>> getFavourite(@PathVariable String userId)
			throws ResourceNotFoundException, InterruptedException, ExecutionException {
		List<Favorite> favouriteList = favoriteService.getFavourite(userId);
		if (favouriteList != null) {
			return new ResponseEntity<List<Favorite>>(favouriteList, HttpStatus.OK);
		} else {
			throw new ResourceNotFoundException("Product Not Found");
		}
	}

	@DeleteMapping("/{favouriteId}")
	public ResponseEntity<?> deleteFavorite(@PathVariable String favouriteId)
			throws InterruptedException, ExecutionException, ResourceNotFoundException {
		Favorite favorite = favoriteService.deleteFavorite(favouriteId);
		if (favorite == null) {
			throw new ResourceNotFoundException("Not found " + favouriteId);
		} else
			return new ResponseEntity<Favorite>(favorite, HttpStatus.OK);
	}
}
