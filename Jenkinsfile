pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven 3.9'
    }

    environment {
        SONAR_SCANNER = tool 'SonarScanner 5.5'
        JAVA_HOME = tool 'JDK21'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '=== Descargando codigo de Backend ==='
                checkout scm
            }
        }

        stage('Compilacion y Pruebas Unitarias') {
            steps {
                dir('admin-proyectos/Ejercicio5MyAPIGestor') {
                    echo '=== Compilando con Java 21 y ejecutando Surefire + JaCoCo ==='
                    sh 'mvn clean test'
                }
            }
            post {
                always {
                    junit 'admin-proyectos/Ejercicio5MyAPIGestor/target/surefire-reports/*.xml'
                    jacoco execFile: 'admin-proyectos/Ejercicio5MyAPIGestor/target/jacoco.exec',
                           classPattern: 'admin-proyectos/Ejercicio5MyAPIGestor/target/classes',
                           sourcePattern: 'admin-proyectos/Ejercicio5MyAPIGestor/src/main/java',
                           inclusionPattern: '**/*.class',
                           exclusionPattern: '**/target/**'
                }
            }
        }

        stage('Analisis de Codigo SonarQube (SAST)') {
            steps {
                dir('admin-proyectos/Ejercicio5MyAPIGestor') {
                    withSonarQubeEnv('SonarQubeServer') {
                        echo '=== Ejecutando SonarQube Scanner 5.5.0.6356 ==='
                        sh '''
                        mvn sonar:sonar                           -Dsonar.projectKey=admin-proyectos-backend                           -Dsonar.projectName="AdminProyectos - Backend REST API"                           -Dsonar.java.binaries=target/classes                           -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml                           -Dsonar.exclusions="**/target/**,**/*.class"
                        '''
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 3, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Empaquetado JAR') {
            steps {
                dir('admin-proyectos/Ejercicio5MyAPIGestor') {
                    sh 'mvn package -DskipTests'
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }
    }
}
