update-version:
	mvn -N versions:update-child-modules
	@echo "Update success."
package:
	mvn clean package -DskipTests
install:
	mvn clean install -DskipTests
deploy:
	mvn clean deploy -P release -Darguments=xwc1125