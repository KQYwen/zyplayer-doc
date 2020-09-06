//package com.zyplayer.doc.wiki.service.git;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.eclipse.jgit.api.Git;
//import org.eclipse.jgit.api.Status;
//import org.eclipse.jgit.api.errors.GitAPIException;
//import org.eclipse.jgit.lib.ObjectId;
//import org.eclipse.jgit.lib.ObjectLoader;
//import org.eclipse.jgit.lib.Repository;
//import org.eclipse.jgit.revwalk.RevCommit;
//import org.eclipse.jgit.revwalk.RevWalk;
//import org.eclipse.jgit.treewalk.TreeWalk;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.File;
//import java.util.Set;
//
//public class GitTest {
//	private static Logger logger = LoggerFactory.getLogger(GitTest.class);
//
//	private static final String LOCAL_PATH = "d:/zyplayerDoc/wikiGitFiles/.git";
//
//	/**
//	 * 提交
//	 */
//	private static String commitFiles() {
//		try {
//			Git git = Git.open(new File(LOCAL_PATH));
//			if (git.status().call().isClean()) {
//				logger.info("no changed");
//				return null;
//			}
//			git.add().addFilepattern(".").call();
//			Status status = git.status().call();
//			Set<String> missing = status.getMissing();
//			if (CollectionUtils.isNotEmpty(missing)) {
//				for (String f : missing) {
//					logger.info("missing file: {}", f);
//					git.rm().addFilepattern(f).call();
//				}
//			}
//			Set<String> removed = status.getRemoved();
//			if (CollectionUtils.isNotEmpty(removed)) {
//				for (String f : removed) {
//					logger.info("remove file: {}", f);
//					git.rm().addFilepattern(f).call();
//				}
//			}
//			RevCommit commit = git.commit().setMessage("commit").call();
//			logger.info("commit id: {}", commit.getName());
//			return commit.getName();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	private static void init() throws GitAPIException {
//		File dirFile = new File(LOCAL_PATH);
//		Git.init().setGitDir(dirFile).setDirectory(dirFile.getParentFile()).call();
//	}
//
//	private static void getFile(String objId, String entryPath) throws Exception {
//		Git git = Git.open(new File(LOCAL_PATH));
//		Repository repository = git.getRepository();
//		RevCommit revCommit = new RevWalk(repository).parseCommit(ObjectId.fromString(objId));
//		TreeWalk treeWalk = TreeWalk.forPath(repository, entryPath, revCommit.getTree());
//		treeWalk.setRecursive(false);
//		ObjectId blobId = treeWalk.getObjectId(0);
//		ObjectLoader loader = repository.open(blobId);
//		String fileContent = new String(loader.getBytes());
//		logger.info("fileContent: {}", fileContent);
//	}
//
//	public static void main(String[] args) throws Exception {
////		init();
////		commitFiles();
//		getFile("b47305c88251b7473928640b1f5346b40bb7387e", "14.txt");
//	}
//}
