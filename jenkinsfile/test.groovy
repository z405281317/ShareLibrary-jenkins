def hello(){
    print("this hello")
}

pipeline {
    agent any
    
    options {
        timestamps() //日志会有时间
        skipDefaultCheckout() //删除隐式checkout scm语句
        disableConcurrentBuilds() //禁止并行
        timeout(time: 1, unit: "HOURS") //流水线超时1小时
    }
    parameters {
        string defaultValue: 'AMD-desktop', name: 'node_name'
    }
    
    stages {
        stage("test"){
            steps{
                script{
                    println("test")
                    println("${params.node_name}")
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
                currentBuild.description += "\n 构建成功"
            }
        }
        
        failure {
            script {
                currentBuild.description += "\n 构建失败"
            }
        }
        
        aborted {
            script {
                currentBuild.description += "\n 构建取消"
            }
        }
    }
}