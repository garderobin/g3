package com.score.util.git;

import java.util.ArrayList;
import java.io.File;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.TreeWalk;

public class LogInfo {
	private String rootpath = "E:/git";
	private String name;
	private String commitMessage;
	private int commitTimeStamp;
	private String branchName;
	private String committerName;
	private String committerEmail;
	private ArrayList<String> files = new ArrayList<String>();

	public LogInfo(RevCommit commit, Repository repo) throws Exception {
		// TODO Auto-generated constructor stub
		name = commit.getName();
		commitMessage = commit.getFullMessage();
		commitTimeStamp = commit.getCommitTime();
		branchName = repo.getBranch();
		committerName = commit.getCommitterIdent().getName();
		committerEmail = commit.getCommitterIdent().getEmailAddress();
		
		TreeWalk tw = new TreeWalk(repo);
		tw.addTree(commit.getTree());
		tw.setRecursive(true);
		while (tw.next()) {
			files.add(tw.getPathString());
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getMessage() {
		return commitMessage;
	}
	
	public int getTimeStamp() {
		return commitTimeStamp;
	}
	
	public String getBranchName() {
		return branchName;
	}
	
	public String getCommitterName() {
		return committerName;
	}
	
	public String getCommitterEmail() {
		return committerEmail;
	}
	
	public String getRootpath() {
		return rootpath;
	}
	
	/*
	 * 得到当前版本的某个目录下的文件
	 * 如果currentPath=""表示path目录下的文件列表
	 *  @param currentPath 当前目录
	 */
	public ArrayList<String> getChildren(String path, String currentPath) {
		// 如果该路径不存在或者指向一个文件，则返回null
		File file = new File(path + "/" + currentPath);
		if ((!file.exists()) || file.isFile()) {
			return null;
		}
		
		ArrayList<String> children = new ArrayList<String>();
		int size = files.size();
		for (int i = 0; i < size; i++) {
			if (files.get(i).contains(currentPath)) {
				// eg. if currentPath = "a/b", files.get(i) = "a/b/c/d.x", then temp = "c/d.x", child = "c", children.add("c")
				// eg. if currentPath = "a/b", files.get(i) = "a/b/c.x", then temp = "c.x", children.add("c.x") 
				String temp = files.get(i).substring(currentPath.length() + 1);
				int index = temp.indexOf("/");
				if (index >= 0) { // 如果是个文件夹
					String child = temp.substring(0, index);
					if (!contains(children, child)) {
						children.add(child);
					}
				}
				else { // 如果是个文件
					children.add(temp);
				}
			}
		}
		return children;
	}
	
	private boolean contains(ArrayList<String> list, String a) {
		int size = list.size();
		boolean contains = false;
		for (int i = 0; i < size; i++) {
			if (list.get(i).equals(a)) {
				contains = true;
				break;
			}
		}
		return contains;
	}
	
	public String toString(){
		String file = "\n---------------------------\nFiles:\n";
		for (int i = 0; i < files.size(); i++) {
			file += files.get(i) + "\n";
		}
		file += "------------------------------\n";
		return "\nGitUtil============================\nName: " + name
				+ "\nMessage: " + commitMessage
				+ "\nTimeStamp: " + commitTimeStamp
				+ "\nCommitter: " + committerName + " with " + committerEmail
				+ "\nBranchName: "+ branchName
				+ file + "============================\n";
	}
}
