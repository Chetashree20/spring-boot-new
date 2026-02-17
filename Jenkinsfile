pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    environment {
        DOCKER_IMAGE = "chetu20/spring-boot"
        DOCKER_TAG   = "${BUILD_NUMBER}"
        K8S_DEPLOYMENT = "spring-deploy"   // 🔥 Change if needed
        K8S_NAMESPACE  = "default"
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
                    sh '''
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                    '''
                }
            }
        }

        stage('Push Image') {
            steps {
                sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
            }
        }

        // 🔎 TROUBLESHOOT STAGE
        stage('Verify Kubernetes Deployment') {
            steps {
                sh '''
                    echo "Current Deployments:"
                    kubectl get deployment -n ${K8S_NAMESPACE}
                '''
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh """
                    kubectl set image deployment/${K8S_DEPLOYMENT} \
                    ${K8S_DEPLOYMENT}=${DOCKER_IMAGE}:${DOCKER_TAG} \
                    -n ${K8S_NAMESPACE}
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
