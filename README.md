# 基于 Kubernetes 编排的 Spring Boot (Redis) 应用

## 环境准备
- Docker 19
- Kubernetes 1.19

## 本地可选步骤

如果要在本地编译并构建 Spring Boot Docker 镜像，需要环境：
- Maven 3
- JDK 8

```shell script
mvn clean package
mvn dockerfile:build
docker tag kubernetes-springboot-demo:0.0.2 registry.cn-shanghai.aliyuncs.com/dockerhub2019/spring:0.0.2   
```

上述步骤可直接跳过。

## 直接部署

创建 Redis 服务的 Deployment。 若本地无镜像则自动从仓库在线拉取：
```shell script
kubectl apply -f redis-deployment.yaml
```

查看 Pod 状态：
```shell script
kubectl get pods                                                                                                                                                                          
```

创建 Service，仅在集群内部暴露 Redis 服务：
```shell script
kubectl apply -f redis-service.yaml
```

创建 Spring Boot 服务的 Deployment。 若本地无镜像则自动从仓库在线拉取：
```shell script
kubectl apply -f spring-deployment.yaml
```

创建 Spring Boot 的 Service。且对外暴露 HTTP 服务：
```shell script
kubectl apply -f spring-service.yaml
```

查看 Service 状态：
```shell script
kubectl get svc
```

伸缩 Spring Boot 副本数量，Service 会自动反向代理以实现负载均衡：
```shell script
kubectl scale deployments/spring-boot --replicas=3  
```