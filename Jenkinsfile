pipeline {
    agent any

    tools {
        maven 'maven'
    }

    environment {
        DOCKER_IMAGE = "chetu20/spring-boot"
        DOCKER_TAG   = "${BUILD_NUMBER}"
        DEPLOYMENT   = "spring-deploy"
        CONTAINER    = "spring-container"
        NAMESPACE    = "default"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/Chetashree20/spring-boot-new.git'
            }
        }

        stage('Build Application') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-creds',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS'
                )]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                }
            }
        }

        stage('Push Image') {
            steps {
                sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh """
                    echo "Updating deployment image..."
                    kubectl set image deployment/${DEPLOYMENT} \
                    ${CONTAINER}=${DOCKER_IMAGE}:${DOCKER_TAG} \
                    -n ${NAMESPACE}

                    echo "Waiting for rollout..."
                    kubectl rollout status deployment/${DEPLOYMENT} -n ${NAMESPACE}
                """
            }
        }
    }

    post {
        success {
            echo 'Deployment Successful 🚀'
        }
        failure {
            echo 'Pipeline Failed ❌'
        }
    }
}
