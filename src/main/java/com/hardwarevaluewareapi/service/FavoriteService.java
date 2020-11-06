package com.hardwarevaluewareapi.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import com.hardwarevaluewareapi.SaveImage;
import com.hardwarevaluewareapi.bean.Favorite;
import com.hardwarevaluewareapi.bean.Product;
import com.hardwarevaluewareapi.exception.ResourceNotFoundException;

@Service
public class FavoriteService {
	
	Firestore fireStore = FirestoreClient.getFirestore();
	public Favorite saveFavorite(Favorite favorite) throws IOException {
		String favoriteId = fireStore.collection("Favourite").document().getId().toString();
		favorite.setFavoriteId(favoriteId);
        fireStore.collection("Favourite").document(favoriteId).set(favorite);
        return favorite;
	}
	public List<Favorite> getFavourite(String userId) throws InterruptedException, ExecutionException {
    	List<Favorite> list;
    	Firestore fireStore = FirestoreClient.getFirestore(); 
        CollectionReference collectionReference =  fireStore.collection("Favourite");
	    Query queryi = collectionReference.whereEqualTo("userId", userId);
        list = queryi.get().get().toObjects(Favorite.class);
    	return list;
    }
	public Favorite deleteFavorite(String favouriteId) throws InterruptedException, ExecutionException, ResourceNotFoundException {
		     
			DocumentReference documentReference = fireStore.collection("Favourite").document(favouriteId);
			Favorite favorite = documentReference.get().get().toObject(Favorite.class);
			if(favorite != null)
			documentReference.delete();
	    	return favorite;
	  }
}
