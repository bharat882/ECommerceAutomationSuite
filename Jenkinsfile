// Jenkinsfile (Windows‐only, no “sh” steps, no Groovy‐interpolated secrets)

pipeline {
  agent any

  tools {
    jdk    'JDK17'
    maven  'Maven3'
    allure 'Allure'               // must match your Allure CLI installation name
  }

  environment {
    // Windows uses %WORKSPACE% and backslashes
    ALLURE_RESULTS = '%WORKSPACE%\\target\\allure-results'
  }

  stages {
    stage('Clean Workspace') {
      steps {
        deleteDir()
      }
    }

    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build & Test') {
      steps {
        withCredentials([usernamePassword(
          credentialsId: 'saucedemo-creds',
          usernameVariable: 'SAUCE_USER',
          passwordVariable: 'SAUCE_PASS'
        )]) {
          // Use a literal string ('''…''') so Jenkins doesn’t perform Groovy interpolation
          bat '''
            mvn clean test ^
              -Durl=https://www.saucedemo.com ^
              -Dusername=%SAUCE_USER% ^
              -Dpassword=%SAUCE_PASS% ^
              -Dallure.results.directory=%ALLURE_RESULTS%
          '''
        }
      }
    }

    stage('Generate Allure Report') {
      steps {
        bat 'mvn allure:report'
      }
    }
  }

  post {
    always {
      archiveArtifacts artifacts: 'target/allure-results/**',          fingerprint: true
      archiveArtifacts artifacts: 'target/site/allure-maven-plugin/**', fingerprint: true

      allure(
        reportBuildPolicy: 'ALWAYS',
        results: [[path: 'target/allure-results']]
      )
    }
  }
}