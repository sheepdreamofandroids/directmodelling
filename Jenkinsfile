node {

	stage 'Stage checkout'
	
	checkout scm
	
	stage 'Stage Build'
	
	echo "Branch: ${env.BRANCH_NAME}"
	
	echo "Build nr: ${env.BUILD_NUMBER}"
	
	env.PATH = "${tool 'maven-3.0.5'}/bin:${env.PATH}"
	sh 'mvn clean package'
}
