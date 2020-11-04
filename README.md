# 基于 Kubernetes 编排的 Spring Boot 应用

## 环境准备
- Maven 3
- JDK 8
- Docker 19
- Kubernetes 1.19

## 部署步骤

编译并构建 Docker 镜像：
```shell script
mvn clean package
mvn dockerfile:build
```

创建 Deployment：
```shell script
kubectl apply -f deployment.yaml
```

查看 Pod 状态：
```shell script
kubectl get pods                                                                                                                                                                          
```

创建 Service：
```shell script
kubectl apply -f service.yaml
```

查看 Service 状态：
```shell script
kubectl get svc
```

伸缩 Spring Boot 副本数量，Service 会自动反向代理以实现负载均衡：
```shell script
kubectl scale deployments/spring-boot --replicas=5   
```