pipeline {
    agent any

    tools {
        maven 'mvn-3.9.9' // Ensure Maven is installed via Jenkins tools
    }

    stages {
        stage('Clone Latest Code') {
            steps {
                // Optional: Clean workspace before cloning
                deleteDir() // comment this if you want to retain workspace cache

                // Fresh clone from 'develop' branch
                git branch: 'master', url: 'https://github.com/danielimmanj/demo.git'
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
                dir('product-service') {
                    script {
                        withSonarQubeEnv('sonar-server') {
                            sh """
                            ${scannerHome}/bin/sonar-scanner \
                                -Dsonar.projectKey=e-commerce-inventory-service \
                                -Dsonar.projectName=e-commerce-inventory-service \
                                -Dsonar.projectVersion=1.0 \
                                -Dsonar.java.binaries=target/classes
                            """
                        }
                    }
                }
            }
        }
    }
}
