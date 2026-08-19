pipeline {
    agent any

    environment {
        IMAGE_NAME = 'paysphere'
        IMAGE_TAG = '1.0'
        CONTAINER_NAME = 'paysphere'
        APP_PORT = '8090'
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
                echo 'Running tests...'

                sh 'mvn test'
            }
        }

        stage('Docker Build') {
            steps {
                echo 'Building Docker image...'

                sh '''
                    sudo docker build \
                        -t ${IMAGE_NAME}:${IMAGE_TAG} .
                '''
            }
        }

        stage('Stop Old Deployment') {
            steps {
                echo 'Stopping old PaySphere deployments...'

                sh '''
                    echo "Stopping old Docker container..."

                    sudo docker stop ${CONTAINER_NAME} 2>/dev/null || true
                    sudo docker rm ${CONTAINER_NAME} 2>/dev/null || true

                    echo "Checking port ${APP_PORT}..."

                    PID=$(sudo lsof -t -i:${APP_PORT} 2>/dev/null || true)

                    if [ -n "$PID" ]; then
                        echo "Found process using port ${APP_PORT}: $PID"
                        sudo kill $PID || true
                        sleep 3
                    else
                        echo "Port ${APP_PORT} is free."
                    fi

                    echo "Port check:"
                    sudo ss -lntp | grep ${APP_PORT} || true
                '''
            }
        }

        stage('Deploy Docker Container') {
            steps {
                echo 'Starting PaySphere Docker container...'

                sh '''
                    sudo docker run -d \
                        --name ${CONTAINER_NAME} \
                        -p ${APP_PORT}:${APP_PORT} \
                        ${IMAGE_NAME}:${IMAGE_TAG}
                '''
            }
        }

        stage('Verify Deployment') {
            steps {
                echo 'Waiting for PaySphere to start...'

                sh '''
                    i=1

                    while [ $i -le 30 ]
                    do
                        if curl -sf http://localhost:${APP_PORT}/api/payments > /tmp/paysphere-response.txt
                        then
                            echo "======================================"
                            echo "PaySphere is running successfully!"
                            echo "======================================"

                            echo "API Response:"
                            cat /tmp/paysphere-response.txt

                            echo ""
                            echo "Docker Container:"
                            sudo docker ps | grep ${CONTAINER_NAME}

                            echo ""
                            echo "Port ${APP_PORT}:"
                            sudo ss -lntp | grep ${APP_PORT} || true

                            exit 0
                        fi

                        echo "Waiting... attempt $i/30"

                        sleep 2

                        i=$((i + 1))
                    done

                    echo "======================================"
                    echo "PaySphere failed to start."
                    echo "======================================"

                    echo "Docker container status:"
                    sudo docker ps -a | grep ${CONTAINER_NAME} || true

                    echo ""
                    echo "Docker logs:"
                    sudo docker logs ${CONTAINER_NAME} || true

                    exit 1
                '''
            }
        }
    }

    post {

        success {
            echo '======================================'
            echo 'PaySphere deployment successful!'
            echo 'Docker container is running on port 8090.'
            echo '======================================'
        }

        failure {
            echo '======================================'
            echo 'PaySphere deployment failed.'
            echo 'Check the Jenkins console output and Docker logs.'
            echo '======================================'
        }
    }
}
