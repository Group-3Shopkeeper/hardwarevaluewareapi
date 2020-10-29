package com.hardwarevaluewareapi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.hardwarevaluewareapi.bean.Category;
import com.hardwarevaluewareapi.exception.ResourceNotFoundException;
import com.hardwarevaluewareapi.image.SendImage;

@Service

public class CategoryService {
	Firestore fireStore = FirestoreClient.getFirestore();
	public ArrayList<Category> getCategoryList() throws InterruptedException, ExecutionException {
		
		ArrayList<Category> al = new ArrayList<>();
		ApiFuture<QuerySnapshot> apiFuture = fireStore.collection("Category").get();
		QuerySnapshot querySnapshot = apiFuture.get();
		List<QueryDocumentSnapshot> documentSnapshotList = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documentSnapshotList) {
			Category category = document.toObject(Category.class);
			al.add(category);
		}
		return al;
	}
    
	public Category saveCategory(MultipartFile file ,Category c) throws Exception {
		String categoryId=fireStore.collection("Category").document().getId().toString();
		c.setCategoryId(categoryId);
		SendImage sendImage=new SendImage();
		String imageUrl=sendImage.sendImageCode(file);
		c.setImageUrl(imageUrl);
		fireStore.collection("Category").document(categoryId).set(c);
		return c;
	}
	  public Category getCategoryById(String categoryId) throws InterruptedException, ExecutionException, ResourceNotFoundException {
		  
		  Category category = fireStore.collection("Category").document(categoryId).get().get().toObject(Category.class);
	      if (category!= null) 
	    	  return category;
	      else
	    	  throw new ResourceNotFoundException("category not found for this id "+categoryId);
	    	  
	  }
	
}

