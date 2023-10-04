docker kill $(docker ps -q)
docker stop $(docker ps -a -q)
docker rm -f $(docker ps -qa)
docker rmi -f $(docker images -aq)
docker image prune -f
docker network prune --force
docker volume prune -f
docker system prune -f
docker system prune --volumes -f

find . -name 'node_modules' -type d -prune -exec rm -rf '{}' +
find . -name '.angular' -type d -prune -exec rm -rf '{}' +
find . -name '.cache' -type d -prune -exec rm -rf '{}' +
find . -name '.gradle' -type d -prune -exec rm -rf '{}' +
find . -name '.idea' -type d -prune -exec rm -rf '{}' +

rm -rf .docker-conf
rm -rf logs

## Clear python caches
find . -name '__pycache__' -type d -prune -exec rm -rf '{}' +
find . -name '.pytest_cache' -type d -prune -exec rm -rf '{}' +
find . -name '.mypy_cache' -type d -prune -exec rm -rf '{}' +

## Clear java caches
for Folder in "auth-service" "contas-service" "clientes-service" "gerentes-service"; do
    rm -rf "$Folder"
    rm -rf "services/$Folder/target"
    rm -rf "services/$Folder/.classpath"
    rm -rf "services/$Folder/.settings"
done
