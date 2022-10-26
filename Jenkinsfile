pipeline {
    agent any
    tools{
        maven 'maven3.8.6'
    }
    stages {
        stage('Build JAR file') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Danaxar/mtisw_MueblesStgo']]])
                bat 'mvn clean install -DskipTests'
            }
        }
        stage('Testing') {
            steps {
                bat 'mvn test'
            }
        }
        stage('Build Docker Image'){
            steps {
                bat 'docker build -t danaxar/mueblesstgo .'
            }
        }
        stage('Push Docker Image'){
            steps {
                withCredentials([string(credentialsId: 'dckrhbpassword', variable: 'dockerpassword2')]) {
                    bat 'docker login -u danaxar -p %dockerpassword2%'
                }
                bat 'docker push danaxar/mueblesstgo'
            }
        }
    }
    post {
    		always {
    			bat 'docker logout'
    		}
    	}
}