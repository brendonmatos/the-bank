#!/bin/bash
for DatabaseName in "clientes" "auth" "gerentes" "contas"; 
do
    for Table in $(mysql -u "$MYSQL_ROOT_USER" -p"$MYSQL_ROOT_PASSWORD" -Nse 'show tables' "$DatabaseName"); 
    do 
        if [ "$Table" = "hibernate_sequence" ]; then
            echo "hibernate_sequence skipping";
            continue;
        fi

        mysql -u "$MYSQL_ROOT_USER" -p"$MYSQL_ROOT_PASSWORD" -e "truncate table $Table" "$DatabaseName"; 
    done
done
