### Build JAR and generate images
(Set variable JAVA_HOME to a Java17 distro before this)
```shell
# UserService image
.\build.bat user

# CommunicationService image
.\build.bat comm
```

### Building HAproxy image after updating configs
```shell
docker build -f Dockerfile.haproxy . -t balancer
```

### Bringing up services behind HAproxy
```shell
docker-compose up
```