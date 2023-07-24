def hello(){
    print("this hello")
}

// git仓库地址
def gitRepo = "http://newgit.cg.xxx/goteam/activeServer.git"

// 环境列表
def sites = [
    "test": "测试环境",
    "dev": "开发环境",
    "try": "体验环境",
    "prod": "正式环境",
]

// 环境选项
def siteLabels = sites.values().join(',')
// 环境选项标签
def siteOptions = sites.keySet().join(',')
def siteCount = sites.size()

pipeline {
    agent any
    
    options {
        timestamps() //日志会有时间
        skipDefaultCheckout() //删除隐式checkout scm语句
        disableConcurrentBuilds() //禁止并行
        timeout(time: 1, unit: "HOURS") //流水线超时1小时
    }
    parameters {
        //install extended-choice-parameter
         extendedChoice(
            name: '环境',
            defaultValue: 'test',
            descriptionPropertyValue: siteLabels,
            multiSelectDelimiter: ',',
            type: 'PT_RADIO',
            value: siteOptions,
            visibleItemCount: siteCount,
        )
        gitParameter name: 'BRANCH',type: 'PT_BRANCH_TAG',defaultValue: 'release/test', branchFilter: 'origin/(.*)', useRepository: gitRepo,quickFilterEnabled: true
    }
    
    stages {
        stage("test"){
            steps{
                script{
                    println("test")
                    print(siteOptions)
                    println("${params.环境}")
                    utils.PrintMsg()
                    hello.Helloutils()
                }
            }
        }
    
        stage("build"){
            steps{
                script{
                    mvn = tool "mymvn"
                    println(mvn)
                    sh "${mvn}/bin/mvn --version"
                }
            }
        }
        
        stage("build go"){
            input {
                message "确定要构建吗？"
                ok "yes"
                submitter "admin,david"
            }
            steps {
                script{
                    mygo = tool "mygo"
                    sh "${mygo}/bin/go version"
                }
            }
        }
    }
    
       
    post {
        always {
            script {
                println("always")
            }
        }
        
        success {
            script {
                currentBuild.description = "\n 构建成功"
            }
        }
        
        failure {
            script {
                currentBuild.description = "\n 构建失败"
            }
        }
        
        aborted {
            script {
                currentBuild.description = "\n 构建取消"
            }
        }
    }
}