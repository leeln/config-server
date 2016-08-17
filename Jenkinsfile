node {
    stage 'build'
    checkout scm
    docker.image('leeln/java:jdk-8').inside {
        sh './gradlew build'
        archive 'build/libs/*.war'
    }

    stage 'push'
    docker.withRegistry('https://index.docker.io/v1/', 'docker-registry-login') {
        docker.build('leeln/config-server').push()
    }

}