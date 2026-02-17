pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "chetu20/spring-boot"
        DOCKER_TAG = "1.${BUILD_NUMBER}"
        KUBE_DEPLOYMENT = "spring-deploy"
        CONTAINER_NAME = "spring-container"
    }

    stages {

        stage('Clone Code') {
            steps {
                git 'https://github.com/Chetashree20/spring-boot-new.git'
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE:$DOCKER_TAG .'
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh '''
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker push $DOCKER_IMAGE:$DOCKER_TAG
                    '''
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                    kubectl set image deployment/$KUBE_DEPLOYMENT \
                    $CONTAINER_NAME=$DOCKER_IMAGE:$DOCKER_TAG
                '''
            }
        }
    }
}
