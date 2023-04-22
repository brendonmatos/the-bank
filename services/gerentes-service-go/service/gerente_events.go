package service

type GerenteEvents interface {
	Created(gerente Gerente) error
	OnCreate(handler func(g Gerente) error) error
}
