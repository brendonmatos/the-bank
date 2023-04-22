package service

type GerenteBus interface {
	Created(gerente Gerente) error
	OnCreate(handler func(g Gerente) error) error
}
