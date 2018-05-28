package com.cms.users.entity;

public class ResponseTransfer {
	private String fileName;
	private byte[] file;
	
	public String getFileName() {
		return fileName;
	}
	public void setFilename(String fileName) {
		this.fileName = fileName;
	}
	
	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}
}
