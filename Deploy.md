### build backend and frontend project
- create backend jar in backend folder

        ./gradlew bootjar
    
- build frontend in frontend folder

        npm run build
### download docker and test
    docker run -d -p 80:80 docker/getting-started
### add dockerfile for backend and frontend seperately
- for backend choose the jdk you'd like use in DcokerHub and edit in docker file
- for frontend choose the build parameter as well
### Register google cloud and build a project in console
### Create a k8s Autopilot cluster under Kubernetes Engine
- define name and region and set public
### Install google cloud CLI in local
- https://cloud.google.com/sdk/docs/install
- Test by input gcloud --version in terminal
### Install kubectl in local
- mac use brew install kubectl directly
### Create a Dockerfile repo under Artifact Registry
- set name and region
### copy repo address as the prefix of local docker image name
- us-west1-docker.pkg.dev/booking-system-cs5500/booking-repo
### Configure docker with gcloud to connect repo and docker(can also copy within repo page setup instruction)
        gcloud auth configure-docker \ us-west1-docker.pkg.dev
### Build docker image with repo name for backend and frontend
- must build within backend folder for backend with dockerfile
        docker build -t us-west1-docker.pkg.dev/booking-system-cs5500/booking-repo/booking-backend .
    
### Test docker image in local
        docker run --rm -p 18081:8081 us-west1-docker.pkg.dev/booking-system-cs5500/booking-repo/booking-backend
### Push docker file to remote
        docker push us-west1-docker.pkg.dev/booking-system-cs5500/booking-repo/booking-backend
### deploy with yaml file
- the order should be database, database service, backend, backend service, frontend, frontend service
- deploy code

        kubectl apply -f {yamlfilename}
- check deploy

        kubectl get deploy
- check pod

        kubectl get pods
- check detail

        kubectl describe pod/deploy {name}
- test local

        kubectl port-forward deploy/booking-backend 18080:8081
- when depploy database need to create secret in k8s cluster for database password https://kubernetes.io/docs/tasks/configmap-secret/managing-secret-using-kubectl/
- first time use database need to create database and create user and grant access
- test database with port-forward

        kubectl port-forward deploy/booking-backend 15432:5432
- use psql to viist database on port 15432(password is the value in secret)

        psql -h localhost -p 15432 -U postgres -W
- create new database the same as backend application.properties

        create database bookingdb
- create user and password and grant access

        create user bookingbackend with password 'bookingbackend'
        grant all privileges on database bookingdb to bookingbackend
- switch between database

        \c bookingdb
- check table

        \d
 ### deploy nginx through helm in k8s engine to do load banlance
 - https://cloud.google.com/community/tutorials/nginx-ingress-gke see Deploying the NGINX Ingress Controller with Helm part
 - get the external IP and replace the ip in booking-ingress yaml and deploy
 

![Screen Shot 2022-11-16 at 4 29 44 PM](https://media.github.khoury.northeastern.edu/user/10503/files/540e1174-263c-4898-adb9-7d211b88682a)
