pipeline {
    agent {
    	node {
    		label 'ydzs-jnlp'
    	}
    }
    stages {
        stage('Build'){
            tools {
            	maven 'maven-3.6.3'
          	}
       		steps {
       		    sh 'env'
           	    sh 'mvn clean package dockerfile:build'
           	    sh 'docker tag kubernetes-springboot-demo:0.0.2 registry.cn-shanghai.aliyuncs.com/dockerhub2019/spring:0.0.2'
            }
    	}
    	stage('Deploy'){
    	    steps {
    	        sh 'kubectl apply -f redis-deployment.yaml'
    	        sh 'kubectl apply -f redis-service.yaml'
    	        sh 'kubectl apply -f spring-config.yaml'
    	        sh 'kubectl apply -f spring-deployment.yaml'
    	        sh 'kubectl apply -f spring-service.yaml'
    	    }
    	}
    }
}