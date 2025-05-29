pipeline {
    agent any

    tools {
        maven 'mvn-3.9.9'
    }

    stages {
        stage('Checkout or Pull Latest Code') {
            steps {
                script {
                    if (fileExists('.git')) {
                        echo 'Git repo already exists. Pulling latest code...'
                        sh 'git reset --hard'
                        sh 'git clean -fd'
                        sh 'git pull origin master'
                    } else {
                        echo 'Cloning repository for the first time...'
                        sh 'git clone -b master https://github.com/danielimmanj/demo.git .'
                    }
                }
            }
        }

        stage('Verify JDK') {
            steps {
                sh 'java -version'
                sh 'javac -version'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            environment {
                scannerHome = tool 'sonar-scanner'
            }
            steps {
                script {
                    withSonarQubeEnv('sonar-server') {
                        sh """
                            ${scannerHome}/bin/sonar-scanner \
                                -Dsonar.projectKey=e-commerce-inventory-service \
                                -Dsonar.projectName=e-commerce-inventory-service \
                                -Dsonar.projectVersion=1.0 \
                                -Dsonar.sources=src/main/java \
                                -Dsonar.tests=src/test/java \
                                -Dsonar.java.binaries=target/classes \
                                -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                        """
                    }
                }
            }
        }
    }
}
