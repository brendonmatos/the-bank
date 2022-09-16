for Path in "./services/auth-service" "./services/clientes-service" "./services/contas-service" "./services/gerentes-service";
do 
    cd $(dirname $0)/../${Path}
    ./mvnw clean install
done