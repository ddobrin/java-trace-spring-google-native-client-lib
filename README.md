# java-trace-spring-google-native-client-lib
This sample intends to show how you can build a [Spring Native](https://docs.spring.io/spring-native/docs/0.12.1/reference/htmlsingle/#overview) application leveraging the [Google Cloud Client Libraries for Java](https://cloud.google.com/java/docs/compile-native-images) support for native images.

The sample is based on the [Cloud Trace Sample](https://github.com/googleapis/java-trace), with instructions to deploy to [Cloud Run](https://cloud.google.com/run) provided.

Configuration:
* Java 17
* Spring Native 0.12.2

## Runtimes
This project has been configured to let you generate either a lightweight container or a native executable.

## Native Executable with Native Build Tools
Use this option if you want to explore more options such as running your tests in a native image.

The GraalVM native-image compiler should be installed and configured on your machine, see [the Getting Started section of the reference guide](https://docs.spring.io/spring-native/docs/0.12.1/reference/htmlsingle/#getting-started-native-build-tools).

To create the executable, run the following goal:

```
$ ./mvnw package -Pnative
```

Then, you can run the app as follows:
```
$ ./target/google-cloud-trace-samples
```

Run native tests using:
```
$ ./target/native-tests
```

## Lightweight Container with Cloud Native Buildpacks

Docker should be installed and configured on your machine prior to creating the image, see [the Getting Started section of the reference guide](https://docs.spring.io/spring-native/docs/0.12.1/reference/htmlsingle/#getting-started-buildpacks).

To create the image, run the following goal:

```
$ ./mvnw spring-boot:build-image
```

Then, you can run the app like any other container

```
$ docker run --rm google-cloud-trace-samples:0.0.1-SNAPSHOT
```

You can observe a failure, due to missing Google Cloud Credentials, therefore deploy it to Cloud Run:
```
# authorize the user to GCP
gcloud auth list

# check if the project is set
gcloud config list

...
project = optimize-serverless-apps
...

# set project ID if not already set
gcloud config set project <project id>
# ex:
gcloud config set project optimize-serverless-apps

# tag the images and push them to the Container Registry 
docker tag google-cloud-trace-samples:0.0.1-SNAPSHOT gcr.io/optimize-serverless-apps/google-cloud-trace-samples

# push the image(s) to the Container Registry
docker push gcr.io/optimize-serverless-apps/google-cloud-trace-samples

# deploy the container to CloudRun
# Note that we are specifying:
#    app name - hello-function
#    region - us-central1
#    memory allocated for the process - 1Gi
#    simple apps allow all users, unauthenticated, best practice is to set service accounts up

# deploy the Native Image to Cloud Run
gcloud run deploy google-cloud-trace-samples \
  --image=gcr.io/optimize-serverless-apps/google-cloud-trace-samples \
  --region us-central1 \
  --memory 1Gi --allow-unauthenticated

# validate the deployment
gcloud run services list
   SERVICE                     REGION        URL                                                         LAST DEPLOYED BY    LAST DEPLOYED AT
✔  google-cloud-trace-samples  us-central1   https://google-cloud-trace-samples-...-uc.a.run.app         ......              2022-07-28T22:42:56.695734Z

# run a request  and wait for some traces to be generated
curl https://google-cloud-trace-samples-...-uc.a.run.app
```

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.2/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.2/maven-plugin/reference/html/#build-image)
* [Spring Native Reference Guide](https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/)

### Additional Links
These additional references should also help you:

* [Configure the Spring AOT Plugin](https://docs.spring.io/spring-native/docs/0.12.1/reference/htmlsingle/#spring-aot-maven)
