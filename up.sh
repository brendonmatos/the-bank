PS3='Digite o numero do script que deseja executar: '
options=(./scripts/*)
select opt in "${options[@]}"; do
    bash "$opt"
done
