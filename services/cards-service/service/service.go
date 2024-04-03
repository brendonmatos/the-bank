package service

import (
	"fmt"
)

type Service interface {
	RequestCard(name string) (*Card, error)
	GetAllCards() ([]Card, error)
	GetCardByCpf(cpf string) (*Card, error)
}

func NewCardService(gr CardRepository, events CardEvents, cache CardCache) Service {
	return &service{
		cardPository: gr,
		cardCache:    cache,
		events:          events,
	}
}

type service struct {
	cardPository CardRepository
	events          CardEvents
	cardCache    CardCache
}

// AddCard implements Service
func (s *service) AddCard(card Card) (*Card, error) {

	g, err := s.cardPository.Insert(&card)

	if err != nil {
		return nil, fmt.Errorf("error inserting card: %w", err)
	}

	err = s.events.Created(Card{
		Nome:  g.Nome,
		Cpf:   g.Cpf,
		Email: g.Email,
	})

	if err != nil {
		return nil, fmt.Errorf("error sending event: %w", err)
	}

	return g, err
}

// GetAllCards implements Service
func (s *service) GetAllCards() ([]Card, error) {
	gs, e := s.cardPository.GetAll()
	return *gs, e
}

// GetCardByCpf implements Service
func (s *service) GetCardByCpf(cpf string) (*Card, error) {

	if g := s.cardCache.GetByCpf(cpf); g != nil {
		return g, nil
	}

	g, e := s.cardPository.GetByCpf(cpf)

	if e != nil {
		return nil, e
	}

	s.cardCache.SetByCpf(cpf, g)

	return g, e
}

// RemoveCardByCpf implements Service
func (s *service) RemoveCardByCpf(cpf string) error {
	_, e := s.cardPository.Delete(cpf)
	return e
}

// UpdateCard implements Service
func (s *service) UpdateCard(card Card) (*Card, error) {
	gs, e := s.cardPository.Update(&card)

	if e != nil {
		return nil, e
	}

	s.cardCache.SetByCpf(card.Cpf, gs)

	return gs, e
}
