pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh "./mvnw install -DskiptTest"
                sh "docker build -t danaxar/mueblesstgo ."
                sh "docker push danaxar/mueblesstgo"
                // Ya se tiene la aplicación en dockerhub
            }
        }
        stage('Test') {
            steps {
                //
            }
        }
        stage('Deploy') {
            steps {
                // Terraform
                sh "terraform init"
                sh ""
            }
        }
    }
}
