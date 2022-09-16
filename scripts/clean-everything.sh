docker kill $(docker ps -q)
docker stop $(docker ps -a -q)

docker rm -f $(docker ps -qa)
docker rmi -f $(docker images -aq)
docker image prune -f
docker network prune --force

docker volume prune -f

rm -rf ./front/dist
find . -name 'node_modules' -type d -prune -exec rm -rf '{}' +
find . -name '.angular' -type d -prune -exec rm -rf '{}' +

rm -rf .docker-conf
rm -rf logs

for Folder in "auth-service" "contas-service" "clientes-service" "gerentes-service"; do
    rm -rf "$Folder"
    rm -rf "services/$Folder/target"
    rm -rf "services/$Folder/.classpath"
    rm -rf "services/$Folder/.settings"
done
