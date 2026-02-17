pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "chetu20/spring-boot"
        DOCKER_TAG = "${BUILD_NUMBER}"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                url: 'https://github.com/Chetashree20/spring-boot-new.git'
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE:$DOCKER_TAG .'
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-creds',
                usernameVariable: 'DOCKER_USER',
                passwordVariable: 'DOCKER_PASS')]) {

                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                }
            }
        }

        stage('Push Image to DockerHub') {
            steps {
                sh 'docker push $DOCKER_IMAGE:$DOCKER_TAG'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                kubectl set image deployment/springboot-app \
                springboot-app=$DOCKER_IMAGE:$DOCKER_TAG
                '''
            }
        }
    }

    post {
        success {
            echo "Deployment Successful 🚀"
        }
        failure {
            echo "Pipeline Failed ❌"
        }
    }
}
