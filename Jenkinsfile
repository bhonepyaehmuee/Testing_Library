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
        //unit test
        stage('Build, Test & Coverage') {
            steps {
                // This generates JaCoCo HTML at target/site/jacoco
               sh 'mvn -B clean compile'
 //                  sh 'mvn test'
//                    sh 'mvn -B clean test jacoco:report'
            }
        }

        stage('JaCoCo Report') {
            steps {
                publishHTML([
                    reportDir: 'target/site/jacoco',
                    reportFiles: 'index.html',
                    reportName: 'JaCoCo Coverage',
                    allowMissing: false,   // Required parameter
                    alwaysLinkToLastBuild: true,   // Required parameter
                    keepAll: true   // Required parameter
                ])
            }
        }

       stage('Static Code Analysis (Checkstyle)') {
           steps {
               sh 'mvn checkstyle:checkstyle checkstyle:checkstyle-aggregate'
               // This ensures the target/site folder is created
               publishHTML(target: [
                   allowMissing: false,
                   alwaysLinkToLastBuild: true,
                   keepAll: true,
                   reportDir: 'target/site',
                   reportFiles: 'checkstyle.html', // Verify the exact filename in your target/site
                   reportName: 'Checkstyle Report'
               ])
           }
       }

        stage('Build Jar') {
            steps {
                // Jar build AFTER coverage
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
//        mvn verify     -Pacceptance
                sh 'mvn verify -Pcucumber'
//                 sh 'mvn verify -DskipUnitTests=true'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: '**/target/cucumber-reports/*.xml'

                    publishHTML(target: [
                        allowMissing: true,
                        keepAll: true,
                        alwaysLinkToLastBuild: true,
                        reportDir: 'target/cucumber-reports',
                        reportFiles: 'cucumber-report.html',
                        reportName: 'Acceptance Report'
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
                body: 'Pipeline Fail email sent successfully ✅'
            )
        }
        always {
            echo "🏁 Pipeline finished."
        }
    }
}