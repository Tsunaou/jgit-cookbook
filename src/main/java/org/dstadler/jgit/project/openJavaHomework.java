package org.dstadler.jgit.project;

import org.apache.commons.io.FileUtils;
import org.dstadler.jgit.helper.CookbookHelper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;


//以JAVA-2018-homework为例测试JGit的API

public class openJavaHomework {
    public static void main(String[] args) throws IOException, GitAPIException {

        
        // 设置文件路径
        File repoDir = new File("E:\\大三上\\JAVA程序设计\\java-2018f-homework\\.git");
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
            
            
            
        }

    }
}
