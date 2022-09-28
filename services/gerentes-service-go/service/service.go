package service

type Service interface {
	AddGerente(gerente Gerente) (Gerente, error)
	GetAllGerentes() ([]Gerente, error)
	GetGerenteByCpf(cpf string) (Gerente, error)
	RemoveGerenteByCpf(cpf string) error
	UpdateGerente(gerente Gerente) (Gerente, error)
}

func NewGerenteService(gr GerenteRepository) Service {
	return &service{
		gerentePository: gr,
	}
}

type service struct {
	gerentePository GerenteRepository
}

// AddGerente implements Service
func (s *service) AddGerente(gerente Gerente) (Gerente, error) {
	g, e := s.gerentePository.Insert(&gerente)
	return *g, e
}

// GetAllGerentes implements Service
func (s *service) GetAllGerentes() ([]Gerente, error) {
	gs, e := s.gerentePository.GetAll()
	return *gs, e
}

// GetGerenteByCpf implements Service
func (s *service) GetGerenteByCpf(cpf string) (Gerente, error) {
	g, e := s.gerentePository.GetByCpf(cpf)
	return *g, e
}

// RemoveGerenteByCpf implements Service
func (s *service) RemoveGerenteByCpf(cpf string) error {
	_, e := s.gerentePository.Delete(cpf)
	return e
}

// UpdateGerente implements Service
func (s *service) UpdateGerente(gerente Gerente) (Gerente, error) {
	gs, e := s.gerentePository.Update(&gerente)
	return *gs, e
}
