node {
    stage 'build'
    checkout scm
    docker.image('docker.repo.leeln.com/leeln/java:jdk-8').inside {
        sh './gradlew build'
        archive 'build/libs/*.war'
    }

    stage 'push'
    docker.withRegistry('https://docker.repo.leeln.com', 'leeln-admin') {
        docker.build('leeln/config-server').push()
    }

    stage 'deploy'
    sh 'rancher up -s leeln -p -d -u -c'
}