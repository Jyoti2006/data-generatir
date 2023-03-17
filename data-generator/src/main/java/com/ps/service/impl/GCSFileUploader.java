package com.ps.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.ps.services.FileUploader;
import com.ps.services.ReadProperties;

public class GCSFileUploader implements FileUploader {

	@SuppressWarnings("deprecation")
	@Override
	public void uploadFile() {
		ReadProperties properties = ReadProperties.getInstance();
		String projectId = properties.getValue("project_id");
		String bucketName = properties.getValue("bucket_name");
		String gcsSAKey = properties.getValue("gcs_service_account_key_path");
		String gcsBucketFolder = properties.getValue("gcs_bucket_directory");

		GoogleCredentials credentials;
		try {
			credentials = GoogleCredentials.fromStream(new FileInputStream(gcsSAKey));
			Storage storage = StorageOptions.newBuilder().setCredentials(credentials).setProjectId(projectId).build()
					.getService();

			// Create a bucket if it doesn't exist
			Bucket bucket = storage.get(bucketName);

			if (bucket == null) {
				bucket = storage.create(BucketInfo.newBuilder(bucketName).build());
			}
			String outputDir = properties.getValue("output_dir");
			File dir = new File(outputDir);
			String[] fileNames = dir.list();
			for (String fName : fileNames) {
				// Upload the file to the bucket
				BlobId blobId = BlobId.of(bucketName, gcsBucketFolder + Paths.get(fName).getFileName().toString());
				BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
				storage.create(blobInfo, new FileInputStream(outputDir+fName));
			}

			System.out.println("File uploaded to GCS bucket.");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
