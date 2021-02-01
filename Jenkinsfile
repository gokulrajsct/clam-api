pipeline {
  agent {
    label 'maven'
  }

  stages {

    stage("Setup") {
      steps {
        echo "setting up"
      }
    }
  
    stage("Checks") {
      steps {
        sh "printenv"
        sh "mvn --version"
        sh "java -version"
      }
    }
    
    stage("Building") {
      steps {
        dir ("Match") {
          sh "mvn -B release:clean release:prepare -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true"
          sh "ls API/target"
        }
      }
    }
    
    stage("Publish"){
      steps {
        checkout([$class: 'GitSCM', 
            branches: [[name: '*/master']],
            extensions: [[$class: 'RelativeTargetDirectory',
              relativeTargetDir: 'destination_repo'
            ]],
            userRemoteConfigs: [[
                credentialsId: 'corneileb_pat', 
                url: 'https://gitlab.datacomdev.co.nz/mlol/matchapi-image.git'
            ]]
        ])
        
        archiveArtifacts artifacts: 'Match/API/target/mlc-match-api.war', onlyIfSuccessful: true
        
        sh "cp Match/API/target/mlc-match-api.war destination_repo/."

        dir("destination_repo"){
          withCredentials([usernamePassword(credentialsId: 'corneileb_pat', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
            sh "git config user.name '$GIT_USERNAME'"
            sh "git config user.email 'jenkins@datacom.co.nz'"
            sh "git remote set-url origin https://$GIT_USERNAME:$GIT_PASSWORD@gitlab.datacomdev.co.nz/mlol/matchapi-image.git"
            
            sh "ls"
            sh "git checkout master"
            sh "git add ."
            sh "git commit -m 'latest build'"
            sh "git status"
          
            sh "git push origin master"
          }
        }
        
      }
    }
    
  }
}
