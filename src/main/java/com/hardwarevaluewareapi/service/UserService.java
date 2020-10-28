package com.hardwarevaluewareapi.service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;

import com.google.firebase.cloud.FirestoreClient;
import com.hardwarevaluewareapi.SaveImage;
import com.hardwarevaluewareapi.bean.Product;
import com.hardwarevaluewareapi.bean.User;
import com.hardwarevaluewareapi.exception.ResourceNotFoundException;

@Service
public class UserService {

	SaveImage saveImage = new SaveImage();

	public User getUser(String userId) throws InterruptedException, ExecutionException {
		Firestore fireStore = FirestoreClient.getFirestore();
		User user = fireStore.collection("User").document(userId).get().get().toObject(User.class);
		return user;
	}

	public User updateUser(MultipartFile file, User user) throws IOException, InterruptedException, Exception {
		Firestore firestoredatabase = FirestoreClient.getFirestore();
		String imageUrl = saveImage.sendImage(file);
		user.setImageUrl(imageUrl);
		user.setUserId(user.getUserId());
		firestoredatabase.collection("User").document(user.getUserId()).set(user);
		return user;
	}

	public User saveUser(MultipartFile file, User user) throws Exception {
		String imageUrl = saveImage.sendImage(file);
		user.setImageUrl(imageUrl);
		Firestore firestoredatabase = FirestoreClient.getFirestore();
		String userId = firestoredatabase.collection("User").document().getId().toString();
		user.setUserId(userId);
		firestoredatabase.collection("User").document(userId).set(user);
		return user;
	}

	public User deleteUser(String User) throws InterruptedException, ExecutionException, ResourceNotFoundException {
		Firestore fireStore = FirestoreClient.getFirestore();
		DocumentReference documentReference = fireStore.collection("User").document(User);
		User user = documentReference.get().get().toObject(User.class);
		documentReference.delete();
		return user;
	}
}
