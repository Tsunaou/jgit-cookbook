package org.dstadler.jgit.project;

import org.apache.commons.io.FileUtils;
import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


//以JAVA-2018-homework为例测试JGit的API

public class openJavaHomework {
    
    public String remotePath = "https://github.com/centic9/jgit-cookbook";//远程库路径
    public static String localPath = "E:\\大三下\\创新项目\\JGit Test\\";//下载已有仓库到本地路径

    @Test
    //从github上clone代码
    public void testClone() throws IOException, GitAPIException {
        
        //设置远程服务器上的用户名和密码（若clone的是自己的private的仓库则需要此操作）
        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider =new
                UsernamePasswordCredentialsProvider("username","password");

        //clone代码库命令
        CloneCommand cloneCommand = Git.cloneRepository();

        Git git= cloneCommand.setURI(remotePath) //设置远程URI
                .setBranch("master") //设置clone下来的分支
                .setDirectory(new File(localPath)) //设置下载存放路径
                //.setCredentialsProvider(usernamePasswordCredentialsProvider) //设置权限验证
                .call();

        System.out.print(git.tag());
    }

    
    public static void main(String[] args) throws IOException, GitAPIException {
        // 设置文件路径
        File repoDir = new File(localPath+".git");
        // 用FileRepositoryBuilder打开一个仓库
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        // 获得对于一个仓库的引用
        try (Repository repository = builder.setGitDir(repoDir)
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build()) {
            System.out.println("Having repository: " + repository.getDirectory());

            // the Ref holds an ObjectId for any type of object (tree, commit, blob, tree)
            // 得到Ref后，可以做很多事情
            Ref head = repository.exactRef("refs/heads/master");
            System.out.println("Ref of refs/heads/master: " + head);
            
            //通过RevWalk获得git log的模拟(commit, Author, Date, Message,etc.)
            try (RevWalk revWalk = new RevWalk(repository)) {
                RevCommit lastCommit = revWalk.parseCommit(head.getObjectId());
                revWalk.markStart(lastCommit);
                revWalk.forEach(c -> {
                    System.out.println("commit " + c.getName());
                    System.out.printf("Author: %s <%s>\n", c.getAuthorIdent().getName(), c.getAuthorIdent().getEmailAddress());
                    System.out.println("Date: " + LocalDateTime.ofEpochSecond(c.getCommitTime(), 0, ZoneOffset.UTC));
                    System.out.println("\t" + c.getShortMessage() + "\n");
                });          
            }
            
        }

    }
}
