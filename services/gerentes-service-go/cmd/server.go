package main

import (
	"github.com/brendonmatos/the-bank/services/gerentes-service-go/infra"
	"github.com/brendonmatos/the-bank/services/gerentes-service-go/service"
)

func main() {
	grm := infra.NewGerenteRepositoryMysql()
	s := service.NewGerenteService(grm)
	infra.MakeHttpServer(":8080", s)
}
