package infra

import (
	"github.com/brendonmatos/the-bank/services/gerentes-service-go/service"
)

type GerenteRepositoryMysql struct {
}

func (r *GerenteRepositoryMysql) GetByCpf(cpf string) (*service.Gerente, error) {
	return nil, nil
}
func (r *GerenteRepositoryMysql) GetAll() (*[]service.Gerente, error) {
	gs := []service.Gerente{}
	return &gs, nil
}
func (r *GerenteRepositoryMysql) Insert(gerente *service.Gerente) (*service.Gerente, error) {
	return nil, nil
}
func (r *GerenteRepositoryMysql) Update(gerente *service.Gerente) (*service.Gerente, error) {
	return nil, nil
}
func (r *GerenteRepositoryMysql) Delete(cpf string) (bool, error) {
	return false, nil
}

func NewGerenteRepositoryMysql() service.GerenteRepository {
	return &GerenteRepositoryMysql{}
}
