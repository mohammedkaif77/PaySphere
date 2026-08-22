pipeline {
    agent any

    environment {
        ANSIBLE_INVENTORY = 'ansible/inventory.ini'
        ANSIBLE_PLAYBOOK  = 'ansible/deploy.yml'
    }

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out PaySphere source code...'

                git branch: 'main',
                    url: 'https://github.com/mohammedkaif77/PaySphere.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building PaySphere application...'

                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo 'Running PaySphere tests...'

                sh 'mvn test'
            }
        }

        stage('Deploy with Ansible') {
            steps {
                echo 'Deploying PaySphere to EC2 using Ansible + AWS SSM...'

                sh '''
                    ansible-playbook \
                        -i ${ANSIBLE_INVENTORY} \
                        ${ANSIBLE_PLAYBOOK}
                '''
            }
        }

        stage('Verify Deployment') {
            steps {
                echo 'Verifying PaySphere deployment...'

                sh '''
                    ansible \
                        -i ${ANSIBLE_INVENTORY} \
                        paysphere-ec2 \
                        -b \
                        -m shell \
                        -a 'curl -f http://localhost:8090/api/payments'
                '''
            }
        }
    }

    post {

        success {
            echo '''
            ======================================
            PaySphere deployment successful!
            ======================================
            '''
        }

        failure {
            echo '''
            ======================================
            PaySphere deployment failed!
            Check Jenkins console output.
            ======================================
            '''
        }
    }
}
