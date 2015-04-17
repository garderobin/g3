package com.score.util.git;

public class FileInfo {
	private String rootpath;
	private boolean isfile; // whether it's a file or a directory
	private String name;
	private String path;
	private String version;
	
	public FileInfo(boolean isFile, String rootpath, String name, String path, String version) {
		// TODO Auto-generated constructor stub
		this.isfile = isFile;
		this.name = name;
		this.path = path;
		this.version = version;
		this.rootpath = rootpath;
	}
	
	public boolean getIsfile() {
		return isfile;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPath() {
		return path;
	}
	
	public String getRootpath() {
		return rootpath;
	}
	
	public String getVersion() {
		return version;
	}
}
