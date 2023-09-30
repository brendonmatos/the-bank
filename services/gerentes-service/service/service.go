package service

import (
	"fmt"
)

type Service interface {
	AddGerente(gerente Gerente) (*Gerente, error)
	GetAllGerentes() ([]Gerente, error)
	GetGerenteByCpf(cpf string) (*Gerente, error)
	RemoveGerenteByCpf(cpf string) error
	UpdateGerente(gerente Gerente) (*Gerente, error)
}

func NewGerenteService(gr GerenteRepository, events GerenteEvents, cache GerenteCache) Service {
	return &service{
		gerentePository: gr,
		gerenteCache:    cache,
		events:          events,

	}
}

type service struct {
	gerentePository GerenteRepository
	events          GerenteEvents
	gerenteCache    GerenteCache
}

// AddGerente implements Service
func (s *service) AddGerente(gerente Gerente) (*Gerente, error) {

	g, err := s.gerentePository.Insert(&gerente)

	if err != nil {
		return nil, fmt.Errorf("error inserting gerente: %w", err)
	}

	err = s.events.Created(Gerente{
		Nome:  g.Nome,
		Cpf:   g.Cpf,
		Email: g.Email,
	})

	if err != nil {
		return nil, fmt.Errorf("error sending event: %w", err)
	}

	return g, err
}

// GetAllGerentes implements Service
func (s *service) GetAllGerentes() ([]Gerente, error) {
	gs, e := s.gerentePository.GetAll()
	return *gs, e
}

// GetGerenteByCpf implements Service
func (s *service) GetGerenteByCpf(cpf string) (*Gerente, error) {

	if g := s.gerenteCache.GetByCpf(cpf); g != nil {
		return g, nil
	}

	g, e := s.gerentePository.GetByCpf(cpf)

	if e != nil {
		return nil, e
	}

	s.gerenteCache.SetByCpf(cpf, g)

	return g, e
}

// RemoveGerenteByCpf implements Service
func (s *service) RemoveGerenteByCpf(cpf string) error {
	_, e := s.gerentePository.Delete(cpf)
	return e
}

// UpdateGerente implements Service
func (s *service) UpdateGerente(gerente Gerente) (*Gerente, error) {
	gs, e := s.gerentePository.Update(&gerente)

	if e != nil {
		return nil, e
	}

	s.gerenteCache.SetByCpf(gerente.Cpf, gs)

	return gs, e
}
