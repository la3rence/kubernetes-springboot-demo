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
       		    sh 'pwd'
       		    sh 'ls -la'
           	    sh 'mvn -B -Dmaven.test.skip=true clean package dockerfile:build'
           	    sh 'docker tag kubernetes-springboot-demo:0.0.2 registry.cn-shanghai.aliyuncs.com/dockerhub2019/spring:0.0.2'
            }
            post {
                success {
                    githubPRComment comment: githubPRMessage('Build ${BUILD_NUMBER} successfully'),
                                    errorHandler: statusOnPublisherError('UNSTABLE'),
                                    statusVerifier: allowRunOnStatus('SUCCESS')
                }
            }
    	}
    	stage('Deploy'){
    	    steps {
    	        sh 'pwd'
    	        sh 'kubectl apply -f redis-deployment.yaml'
    	        sh 'kubectl apply -f redis-service.yaml'
    	        sh 'kubectl apply -f spring-config.yaml'
    	        sh 'kubectl apply -f spring-deployment.yaml'
    	        sh 'kubectl apply -f spring-service.yaml'
    	    }
    	    post {
    	        success {
    	            githubPRAddLabels errorHandler: statusOnPublisherError('UNSTABLE'),
    	                              labelProperty: labels('approve'),
    	                              statusVerifier: allowRunOnStatus('SUCCESS')
    	            githubPRStatusPublisher buildMessage: message(failureMsg: githubPRMessage('Build failed.  (Status set failed.)'),
    	                                    successMsg: githubPRMessage('Build succeeded. (Status set failed.)')),
    	                                    errorHandler: statusOnPublisherError('UNSTABLE'),
    	                                    statusMsg: githubPRMessage('${GITHUB_PR_COND_REF} run ended'),
    	                                    statusVerifier: allowRunOnStatus('SUCCESS'),
    	                                    unstableAs: 'FAILURE'
    	        }
    	        failure {
                    githubPRStatusPublisher buildMessage: message(failureMsg: githubPRMessage('Build failed. (Status set failed.)'),
                                            successMsg: githubPRMessage('Build succeeded. (Status set failed.)')),
                                            errorHandler: statusOnPublisherError('UNSTABLE'),
                                            statusMsg: githubPRMessage('${GITHUB_PR_COND_REF} run ended'),
                                            statusVerifier: allowRunOnStatus('FAILURE'),
                                            unstableAs: 'FAILURE'
    	       }
    	    }
    	}
    }
}