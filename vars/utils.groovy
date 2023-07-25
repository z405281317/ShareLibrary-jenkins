

def PrintMsg(){
    print("groovy output")
}

def pull(Branch,Id,GitRepo){
    checkout scmGit(branches: [[name: BRANCH ]], extensions: [], userRemoteConfigs: [[credentialsId: '842ea056-6087-470a-9ca0-06bd1e9fa13c', url: gitRepo]])
}