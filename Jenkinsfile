pipeline {
    agent none

    stages {
        stage('Maven Build') {
            agent {
                docker {
                    image 'maven:latest'
                    args '-v /root/.m2:/root/.m2'
                }
            }
            steps {
                echo "1.Maven Build Stage"
                sh 'mvn -B clean package'
            }
        }

        stage('Image Build') {
            agent {
                label 'master'
            }
            steps {
                echo "2.Image Build Stage"
                sh 'docker build -t cloud-native-final:${BUILD_ID} . '
                sh 'docker tag cloud-native-final:${BUILD_ID} harbor.edu.cn/nju03/cloud-native-final:${BUILD_ID}'
            }
        }

        stage('Push') {
            agent {
                label 'master'
            }
            steps {
                echo "3.Push Docker Image Stage"
                sh 'docker login harbor.edu.cn -u nju14 -p nju142022'
                sh "docker push harbor.edu.cn/nju03/cloud-native-project:${BUILD_ID}"
            }
        }

    }
}

node('slave') {
    container('jnlp-kubectl') {

        stage('Clone YAML') {
             echo "4. Git Clone YAML To Slave"
             git branch: 'main', url: "https://github.com/shenyong2001/cloud-native-final.git"
        }

        stage('YAML') {
             echo "5. Change YAML File Stage"
             sh 'sed -i "s/{VERSION}/${BUILD_ID}/g" ./yaml/deploy-svc.yaml'
        }

        stage('Deploy') {
             echo "6. Deploy To K8s Stage"
             sh 'kubectl apply -f ./yaml/deploy-svc.yaml'
             sh 'kubectl apply -f ./yaml/redis-svc.yaml'
        }
    }
}
