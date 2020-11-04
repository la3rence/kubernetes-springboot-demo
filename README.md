# 基于 Kubernetes 编排的 Spring Boot 应用

## 环境准备
- Docker 19
- Kubernetes 1.19

## 本地可选步骤

编译并构建 Docker 镜像，需要环境：
- Maven 3
- JDK 8

```shell script
mvn clean package
mvn dockerfile:build
docker tag kubernetes-springboot-demo:0.0.1 registry.cn-shanghai.aliyuncs.com/dockerhub2019/spring:latest    
```

## 部署

创建 Deployment。 若本地无镜像则自动从仓库在线拉取：
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