package main

import (
	"os"

	"github.com/brendonmatos/the-bank/services/gerentes-service-go/infra"
	"github.com/brendonmatos/the-bank/services/gerentes-service-go/service"
)

func main() {
	grm := infra.NewGerenteRepositoryMysql()

	rabbitMqConnUri := os.Getenv("RABBITMQ_CONNECTION_URI")
	httpPort := os.Getenv("SERVER_PORT")
	bus := infra.NewGerenteBusRabbitMq(
		rabbitMqConnUri,
	)

	s := service.NewGerenteService(grm, bus)

	err := bus.OnCreate(func(g service.Gerente) error {
		_, err := s.AddGerente(g)

		if err != nil {
			return err
		}

		return nil
	})

	if err != nil {
		panic(err)
	}

	infra.MakeHttpServer(":"+httpPort, s)

}
