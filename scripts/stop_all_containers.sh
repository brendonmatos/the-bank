docker kill $(docker ps -q)
docker stop $(docker ps -a -q)
