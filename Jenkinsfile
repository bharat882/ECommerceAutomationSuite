pipeline {
  agent any

  tools {
    jdk 'JDK17'
    maven 'Maven3'
  }

  environment {
    ALLURE_RESULTS = "${env.WORKSPACE}/target/allure-results"
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build & Test') {
      steps {
        // bind your credentials as env vars if needed
        withCredentials([usernamePassword(
          credentialsId: 'saucedemo-creds',
          usernameVariable: 'SAUCE_USER',
          passwordVariable: 'SAUCE_PASS'
        )]) {
          sh """
            mvn clean test \
              -Durl=https://www.saucedemo.com \
              -Dusername=$SAUCE_USER \
              -Dpassword=$SAUCE_PASS \
              -Dallure.results.directory=${ALLURE_RESULTS}
          """
        }
      }
    }

    stage('Generate Allure Report') {
      steps {
        sh "mvn allure:report"
      }
    }
  }

  post {
    always {
      // Archive results and report
      archiveArtifacts artifacts: 'target/allure-results/**', fingerprint: true
      archiveArtifacts artifacts: 'target/site/allure-maven-plugin/**', fingerprint: true

      // Publish interactive report
      allure([
        reportBuildPolicy: 'ALWAYS',
        results: [[path: 'target/allure-results']]
      ])
    }
  }
}