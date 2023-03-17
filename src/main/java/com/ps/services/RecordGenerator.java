package com.ps.services;

public interface RecordGenerator {
	public void init();

	public void genrateRecords();

	public void writeToFile();

	public void close();
}
