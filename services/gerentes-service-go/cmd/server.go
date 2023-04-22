package main

import (
	"github.com/brendonmatos/the-bank/services/gerentes-service-go/infra"
	"github.com/brendonmatos/the-bank/services/gerentes-service-go/service"
)

func main() {
	grm := infra.NewGerenteRepositoryMysql()

	events := infra.NewGerenteEventsRabbitMq(
		infra.RABBIT_MQ_CONNECTION_URI,
	)

	s := service.NewGerenteService(grm, events)

	err := events.OnCreate(func(g service.Gerente) error {
		_, err := s.AddGerente(g)

		if err != nil {
			return err
		}

		return nil
	})

	if err != nil {
		panic(err)
	}

	infra.MakeHttpServer(":"+infra.SERVER_PORT, s)

}
