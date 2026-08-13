pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/mohammedkaif77/PaySphere.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    echo "======================================"
                    echo "Stopping existing PaySphere process..."
                    echo "======================================"

                    pkill -f 'paysphere-0.0.1-SNAPSHOT.jar' || true

                    sleep 3

                    echo "======================================"
                    echo "Starting PaySphere..."
                    echo "======================================"

                    JENKINS_NODE_COOKIE=dontKillMe \
                    nohup java -jar target/paysphere-0.0.1-SNAPSHOT.jar \
                        > /tmp/paysphere.log 2>&1 < /dev/null &

                    echo "PaySphere started in background."

                    echo "======================================"
                    echo "Waiting for PaySphere..."
                    echo "======================================"

                    i=1

                    while [ $i -le 30 ]
                    do
                        if curl -sf http://localhost:8090/api/payments > /tmp/paysphere-response.txt
                        then
                            echo "======================================"
                            echo "PaySphere is running!"
                            echo "======================================"

                            echo "API Response:"
                            cat /tmp/paysphere-response.txt

                            echo ""
                            echo "Port 8090:"
                            ss -lntp | grep 8090 || true

                            exit 0
                        fi

                        echo "Waiting... attempt $i/30"

                        sleep 2

                        i=$((i + 1))
                    done

                    echo "======================================"
                    echo "PaySphere failed to start."
                    echo "======================================"

                    echo "Application logs:"
                    echo "--------------------------------------"

                    cat /tmp/paysphere.log

                    echo "--------------------------------------"

                    echo "Port 8090:"
                    ss -lntp | grep 8090 || true

                    exit 1
                '''
            }
        }
    }

    post {
        success {
            echo 'PaySphere deployed successfully!'
            echo 'PaySphere is running on port 8090.'
        }

        failure {
            echo 'PaySphere deployment failed.'
        }
    }
}