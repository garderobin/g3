package com.score.util.git;

import java.io.File;
import java.util.ArrayList;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;

public class GitUtil {
	private static Git git;
	private String rootPath;
	
	public GitUtil(String rootPath) throws Exception {
		this.rootPath = rootPath;
		initGit();
	}
	
	public String getFileContent(String version, String path) throws Exception {
        ObjectId objId = git.getRepository().resolve(version); //revCommit.name是版本号
        RevWalk revWalk = new RevWalk(git.getRepository());
        TreeWalk tw = new TreeWalk(git.getRepository());
        
        if (objId != null) {
    		RevCommit commit = revWalk.parseCommit(objId);
    		tw.addTree(commit.getTree());
	        tw.setRecursive(true);
			while (tw.next()) {
				if (tw.getPathString().indexOf(path) != -1) {
					ObjectId objectId = tw.getObjectId(0);
					ObjectLoader loader = git.getRepository().open(objectId);
					return new String(loader.getBytes(), "UTF-8");
				}
			}
        }
        
		return "";
	}
	
	public String getUpperpath(String path) {
		int index = path.lastIndexOf('/');
		if (index == -1) {
			return "";
		}
		else {
			return path.substring(0, index);
		}
	}
	
	private void initGit() throws Exception {
        File root = new File(rootPath);
        if(!root.exists())
            root.mkdir();
        File gitF = new File("E:/StudentManage/.git");
        if(!gitF.exists()) {//如果已经初始化过,那肯定有.git文件夹
                        //初始化git库,相当于命令行的 git init
            Git.init().setDirectory(root).call();
        }
        git = Git.open(root); //打开git库
	}
	
	// 暂定显示20个log
	public ArrayList<LogInfo> getVersions() throws Exception {
		int count = 0;
		ArrayList<LogInfo> list = new ArrayList<LogInfo>();
        for(RevCommit revCommit : git.log().call()){
        	if (count > 20) {
				break;
			}
        	list.add(new LogInfo(revCommit, git.getRepository()));
        }
		
		return list;
	}
	
	// 得到特定版本的Log信息
	public LogInfo showLog(String version) throws Exception {
		LogInfo log = null;
        ObjectId objId = git.getRepository().resolve(version); //revCommit.name是版本号
        RevWalk revWalk = new RevWalk(git.getRepository());

        if (objId != null) {
    		RevCommit commit = revWalk.parseCommit(objId);
    		log = new LogInfo(commit, git.getRepository());
        }
		
		return log;
	}
	
	// 返回所有Log信息
	public ArrayList<LogInfo> showLogs() throws Exception {
		ArrayList<LogInfo> list = new ArrayList<LogInfo>();
        for(RevCommit revCommit : git.log().call()){
    		LogInfo log = new LogInfo(revCommit, git.getRepository());
    		list.add(log);
    		//System.out.println(log.toString());
    		/*System.out.println("============\n" + log.getName() + "\n");
    		ArrayList<String> children = log.getChildren("E:/git", "src/com/score");
    		if (children != null) {
    			for (int i = 0; i < children.size(); i++) {
    				System.out.println(children.get(i));
    			}
        		System.out.println("============");
    		}
    		else {
				System.out.println("Not exist.");
	    		System.out.println("============");
			}*/
        }
        return list;
	}
	
	public ArrayList<FileInfo> showFiles(String version, String rootPath, String path) throws Exception {
		ArrayList<FileInfo> list = new ArrayList<FileInfo>();
        ObjectId objId = git.getRepository().resolve(version); //revCommit.name是版本号
        RevWalk revWalk = new RevWalk(git.getRepository());
        TreeWalk tw = new TreeWalk(git.getRepository());

        if (objId != null) {
    		RevCommit commit = revWalk.parseCommit(objId);
    		File file = new File(rootPath + path);
    		if (!file.exists() && file.isFile()) {
				return null;
			}
    		else {
    	        tw.addTree(commit.getTree());
    	        tw.setRecursive(true);
    	        ArrayList<String> temp = new ArrayList<String>();
    			while (tw.next()) {
    				temp.add(tw.getPathString());
				}
    			list = ParseChildren(version, rootPath, path, temp);
			}
    		return list;
        }
        else {
			return null;
		}
	}
	
	private ArrayList<FileInfo> ParseChildren(String version, String rootpath, String path, ArrayList<String> temp) {
		ArrayList<FileInfo> result = new ArrayList<FileInfo>();
		ArrayList<String> list = new ArrayList<String>();
		int size = temp.size();
		
		for (int i = 0; i < size; i++) {
			if (temp.get(i).indexOf(path) == 0) {
				if (path.equals("")) {
					String buffer = temp.get(i);
					int index = buffer.indexOf('/');
					if (index == -1) {
						list.add(buffer);
						FileInfo fi = new FileInfo(true, rootpath, buffer, path, version);
						result.add(fi);
					}
					else {
						String child = buffer.substring(0, index);
						if (!containsInArraylist(child, list)) {
							list.add(child);
							File file = new File(path + child);
							FileInfo fi = new FileInfo(file.isFile(), rootpath, child, path, version);
							result.add(fi);
						}
					}
				}
				else {
					String buffer = temp.get(i).substring(path.length() + 1);
					int index = buffer.indexOf('/');
					if (index == -1) {
						list.add(buffer);
						FileInfo fi = new FileInfo(true, rootpath, buffer, path, version);
						result.add(fi);
					}
					else {
						String child = buffer.substring(0, index);
						if (!containsInArraylist(child, list)) {
							list.add(child);
							File file = new File(path + child);
							FileInfo fi = new FileInfo(file.isFile(), rootpath, child, path, version);
							result.add(fi);
						}
					}
				}
			}
		}
		
		return result;
	}
	
	private boolean containsInArraylist(String a, ArrayList<String> list) {
		boolean result = false;
		int size = list.size();
		for (int i = 0; i < size; i++) {
			if (list.get(i).equals(a)) {
				result = true;
				break;
			}
		}
		return result;
	}
}
