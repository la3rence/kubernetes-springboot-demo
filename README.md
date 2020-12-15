# 基于 Kubernetes 编排的 Spring Boot (Redis) 应用

## 环境准备
- Docker 19
- Kubernetes 1.19

本分支使用 Ingress 对外暴露，所以得先在集群中部署 Ingress 控制器。
本例尝试使用 Nginx Ingress Controller 作为控制器，可以把它当作一个[七层负载均衡器](https://lawrenceli.me/blog/load-balancing):
```shell script
kubectl apply -f ingress.yaml
```

安装完成后尝试在 Pod 所在节点请求 `curl 127.0.0.1` ，应当返回 404 的 HTML 来验证.

## 本地可选步骤

如果要在本地编译并构建 Spring Boot Docker 镜像，需要环境：
- Maven 3
- JDK 8

```shell script
mvn clean package
mvn dockerfile:build
docker tag kubernetes-springboot-demo:0.0.2 registry.cn-shanghai.aliyuncs.com/dockerhub2019/spring-ingress:0.0.2   
```

上述步骤可直接跳过.

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

创建 Spring Boot 的 Service（集群内）, 并创建 Ingress 来暴露：
```shell script
kubectl apply -f spring-service.yaml
```

查看 Service 状态：
```shell script
kubectl get svc
```

查看 Ingress:
```shell script
kubectl get ingress
```

写 Hosts，或者在域名服务商做 DNS 解析后，尝试访问服务：
```shell script
curl spring.k8s.me/hello
```

## 滚动更新

参考官方指南[执行滚动更新](https://kubernetes.io/zh/docs/tutorials/kubernetes-basics/update/update-interactive/)。
将当前版本号首先定义成 0.0.2，由 `/v` 接口输出当前版本。观察此接口的返回。然后开启一个定时任务，不断循环请求此接口，并开始滚动更新：

```shell
kubectl set image deployments/spring-boot spring=registry.cn-shanghai.aliyuncs.com/dockerhub2019/spring-ingress:0.0.3
kubectl get pod
```

此刻，Kubernetes 收到了信号，开始先运行新的 Pod 来启动，准备就绪后，可以发现 `/v` 接口已经返回了新的版本号，且中间不会出现停机造成的接口返回失败。
新版本部署成功了，旧的 Pod 会自动关停自己。 这样就保证了服务的不间断升级。

```shell
kubectl rollout status deployment/spring-boot
```

## 回滚

```shell
kubectl rollout undo deployments/spring-boot
```

将旧版本 Deployments 恢复，滚动更新的逆过程。