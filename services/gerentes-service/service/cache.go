package service

type GerenteCache interface {
	GetByCpf(cpf string) *Gerente
	SetByCpf(cpf string, gerente *Gerente) error
}
