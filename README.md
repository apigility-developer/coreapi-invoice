
## Getting Started - coreapi-product API

### Run `coreapi-product` locally

	```
	$ git clone https://github.com/gravitant/coreapi-product.git
	$ cd coreapi-product/scripts/local
	$ ./run.sh
	```
	
* Deploy microservice

	```
	$ java -jar target/${type}-${identifier}-1.0.0-SNAPSHOT.jar --spring.profiles.active=developer
	```
	
* Connect to the API endpoint.

	```
	$ curl -s http://localhost:8080/${identifier}s/169af978-f8b3-4268-a4fb-86e29daa5095 | jq .
	
	      {
	        "name": "${identifier}1",
	        "state": "active",
	        "created_by": "testuser",
	        "created_at": "2014-09-06T00:00:00.000Z",
	        "last_modified_by": "testuser",
	        "last_modified_at": "2014-12-03T00:00:00.000Z",
	        "_links": {
	          "self": {
	            "href": "http://localhost:8080/${identifier}s/169af978-f8b3-4268-a4fb-86e29daa5095"
	          }
	        }
	      }
	```

* Preview API Documentation

	```
	$ apiary preview
	```
	
### Run `${type}-${identifier}` in Docker environment

* Download & Install [Docker Toolbox](https://www.docker.com/products/docker-toolbox) on your computer.
* Click Docker Quickstart Terminal icon to open command line console
* Set the docker hub credentials to the environment variables

	```
	$ export DOCKER_USERNAME=xxxxx
	$ export DOCKER_PASSWORD=xxxx
	$ export DOCKER_EMAIL=xxxxxx
	```

* Set database user credentials into the environment

	```
	$ export MYSQL_USER=xxxxx
	$ export MYSQL_PASSWORD=xxxxx
	```
* Start Docker Machine on virtual box

	```
	$ docker-machine create -d virtualbox --virtualbox-memory 4096 apiplatform-${identifier}-vb-node
	```

* Switch environment to point to docker engine running on the new virtual box machine

	```
	$ eval $(docker-machine env apiplatform-${identifier}-vb-node)
	```

* Run unit tests

	```
	$ cd ${type}-${identifier}
	$ mvn clean package -Dspring.profiles.active=developer
	```
	
* Build `${type}-${identifier}` docker image

	```
	$ docker build -t gravitant/${type}-${identifier} -f src/main/docker/Dockerfile .
	```

* Login to docker hub from command line if you are not currently logged in

	```
	$ docker login -e="$DOCKER_EMAIL" -u="$DOCKER_USERNAME" -p="$DOCKER_PASSWORD"
	```
	
* Run microservices in the virtual box

	```
	$ docker-compose up -d
	```
	
* Connect to the API endpoint to verify microservices are up and running

	```
	$ curl -s http://$(docker-machine ip apiplatform-${identifier}-vb-node):8765/${identifier}-api/${identifier}s/169af978-f8b3-4268-a4fb-86e29daa5095 | jq .
	
	      {
	        "name": "${identifier}1",
	        "state": "active",
	        "created_by": "testuser",
	        "created_at": "2014-09-06T00:00:00.000Z",
	        "last_modified_by": "testuser",
	        "last_modified_at": "2014-12-03T00:00:00.000Z",
	        "_links": {
	          "self": {
	            "href": "http://localhost:8080/${identifier}s/169af978-f8b3-4268-a4fb-86e29daa5095"
	          }
	        }
	      }
	```

* Run integration tests

	```
	$ dredd apiary.apib http://$(docker-machine ip apiplatform-${identifier}-vb-node):8765/${identifier}-api
	```

### Code `${type}-${identifier}` API

* Download [Spring Tool Suite (STS)](https://spring.io/tools/sts)
* Unpack the downloaded archive to install
* Open STS. Click through `File->Import->Maven`
* Select `Existing Maven Projects`
* Click `Browse` and navigate to the `${type}-${identifier}` folder
* Select `${type}-${identifier}` and click `Open`
