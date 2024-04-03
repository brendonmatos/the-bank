package service

// GerenteRepository is the interface that wraps the basic gerente

type GerenteRepository interface {
	GetByCpf(cpf string) (*Gerente, error)
	GetAll() (*[]Gerente, error)
	Insert(gerente *Gerente) (*Gerente, error)
	Update(gerente *Gerente) (*Gerente, error)
	Delete(cpf string) (bool, error)
}
