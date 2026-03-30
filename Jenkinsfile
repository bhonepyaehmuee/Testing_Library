pipeline {
    agent any

    tools {
        maven "maven3.9"
    }

    environment {
        DOCKER_REPO = "bph-library-image"
        DOCKER_HOST_PORT = "9096"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/bhonepyaehmuee/Testing_Library.git'
            }
        }

        stage('Build, Test & Coverage') {
            steps {
                sh 'mvn -B clean test'
                // sh 'mvn test'
                // sh 'mvn -B clean test jacoco:report'
            }
        }

        stage('JaCoCo Report') {
            steps {
                sh 'mvn jacoco:report'
            }
            post {
                always {
                    publishHTML(target: [
                                                    allowMissing: true,
                                                    keepAll: true,
                                                    alwaysLinkToLastBuild: true,
                                                    reportDir: 'target/site/jacoco',
                                                    reportFiles: 'index.html',
                                                    reportName: 'Coverage Report'
                                                ])
                }
            }
        }

        stage('Static Code Analysis (Checkstyle)') {
            steps {
                sh 'mvn checkstyle:checkstyle checkstyle:checkstyle-aggregate'
            }
            post {
                always {
                    publishHTML([
                        allowMissing: true,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/site',
                        reportFiles: 'checkstyle.html',
                        reportName: 'Checkstyle Report'
                    ])
                }
            }
        }

        stage('Build Jar') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    def imageTag = "${env.BUILD_NUMBER}"
                    sh "docker build -t ${DOCKER_REPO}:${imageTag} ."
                    sh "docker tag ${DOCKER_REPO}:${imageTag} ${DOCKER_REPO}:latest"
                    env.IMAGE_TAG = imageTag
                }
            }
        }

        stage('Run Docker Container') {
            steps {
                echo "Running container locally (port 8081)..."
                sh """
                    docker stop bph-library-container || true
                    docker rm bph-library-container || true
                    docker run -d --name bph-library-container -p 9096:8081 ${DOCKER_REPO}:${env.IMAGE_TAG}
                """
            }
        }

       stage('Acceptance Test') {
           steps {
               sh 'mvn verify'
           }
           post {
               always {
                   junit allowEmptyResults: true, testResults: 'target/cucumber.xml'
                   publishHTML([
                       allowMissing: true,
                       alwaysLinkToLastBuild: true,
                       keepAll: true,
                       reportDir: 'target/cucumber-reports',
                       reportFiles: 'cucumber-reports.html',
                       reportName: 'Acceptance Test Report'
                   ])
               }
           }
       }
    }

    post {
        success {
            echo "✅ Pipeline succeeded! App running at http://localhost:${DOCKER_HOST_PORT}/"
            emailext(
                to: 'bhshi75@gmail.com',
                subject: 'Pipeline Email Test',
                body: 'Pipeline Success email sent successfully ✅'
            )
        }
        failure {
            echo "❌ Pipeline failed."
            emailext(
                to: 'bhshi75@gmail.com',
                subject: 'Pipeline Email Test',
                body: 'Pipeline Fail email sent successfully ❌'
            )
        }
        always {
            echo "🏁 Pipeline finished."
        }
    }
}