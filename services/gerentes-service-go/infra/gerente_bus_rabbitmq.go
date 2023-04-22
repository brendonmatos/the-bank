package infra

import (
	"encoding/json"
	"fmt"
	"log"

	"github.com/brendonmatos/the-bank/services/gerentes-service-go/service"
	"github.com/wagslane/go-rabbitmq"
)

type gerenteBusRabbitMq struct {
	conn      *rabbitmq.Conn
	publisher *rabbitmq.Publisher
}

// Created implements service.GerenteBus
func (bus *gerenteBusRabbitMq) Created(gerente service.Gerente) error {
	messageStr, err := json.Marshal(gerente)

	if err != nil {
		return fmt.Errorf("error marshalling message: %v", err)
	}

	err = bus.publisher.Publish(
		messageStr,
		[]string{"gerente_criado"},
	)

	if err != nil {
		return fmt.Errorf("error publishing message: %v", err)
	}

	return nil
}

func (bus *gerenteBusRabbitMq) OnCreate(handler func(g service.Gerente) error) error {
	_, err := rabbitmq.NewConsumer(
		bus.conn,
		func(d rabbitmq.Delivery) rabbitmq.Action {
			g := Gerente{}

			err := json.Unmarshal(d.Body, &g)

			if err != nil {
				log.Printf("error unmarshalling: %v", err)
				return rabbitmq.NackRequeue
			}

			err = handler(service.Gerente{
				Nome:  g.Nome,
				Cpf:   g.Cpf,
				Email: g.Email,
			})

			if err != nil {
				log.Printf("error handler: %v", err)
				return rabbitmq.NackRequeue
			}

			// rabbitmq.Ack, rabbitmq.NackDiscard, rabbitmq.NackRequeue
			return rabbitmq.Ack
		},
		"criar_gerente",
		rabbitmq.WithConsumerOptionsQueueDurable,
	)

	if err != nil {
		return fmt.Errorf("error creating consumer: %v", err)
	}

	// defer consumer.Close()

	return nil
}

func NewGerenteBusRabbitMq(url string) service.GerenteBus {

	conn, err := rabbitmq.NewConn(
		url,
		rabbitmq.WithConnectionOptionsLogging,
	)

	if err != nil {
		panic(err)
	}

	publisher, err := rabbitmq.NewPublisher(
		conn,
	)

	if err != nil {
		panic(err)
	}

	defer publisher.Close()

	return &gerenteBusRabbitMq{conn: conn, publisher: publisher}
}
