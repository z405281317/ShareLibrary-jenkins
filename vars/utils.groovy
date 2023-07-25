

def PrintMsg(){
    print("groovy output")
}

def pull(Branch,Id,GitRepo){
    checkout scmGit(branches: [[name: BRANCH ]], extensions: [], userRemoteConfigs: [[credentialsId: '842ea056-6087-470a-9ca0-06bd1e9fa13c', url: gitRepo]])
}

//获取变更文件列表，返回HashSet，注意添加的影响文件路径不含仓库目录名
@NonCPS
List<String> getChangedFilesList(){
    def changedFiles = []
    for ( changeLogSet in currentBuild.changeSets){
        for (entry in changeLogSet.getItems()){
            changedFiles.addAll(entry.affectedPaths)
        }
    }
    return changedFiles
}

// 获取提交ID
@NonCPS
String getGitcommitID(){
    gitCommitID = " "
    for ( changeLogSet in currentBuild.changeSets){
        for (entry in changeLogSet.getItems()){
            gitCommitID = entry.commitId
        }
    }
    return gitCommitID
}

// 获取提交人
@NonCPS
String GetAuthorName(){
    gitAuthorName = " "
    for ( changeLogSet in currentBuild.changeSets){
        for (entry in changeLogSet.getItems()){
            gitAuthorName = entry.author.fullName
        }
    }
    return gitAuthorName
}

// 获取提交信息
@NonCPS
String GetCommitMessage(){
    commitMessage = " "
    for ( changeLogSet in currentBuild.changeSets){
        for (entry in changeLogSet.getItems()){
            commitMessage = entry.msg
        }
    }
    return commitMessage
}