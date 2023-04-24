package infra

import (
	"fmt"

	"github.com/brendonmatos/the-bank/services/gerentes-service-go/service"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
)

type GerenteRepositoryMysql struct {
	db *gorm.DB
}

func (r *GerenteRepositoryMysql) GetByCpf(cpf string) (*service.Gerente, error) {
	return nil, nil
}
func (r *GerenteRepositoryMysql) GetAll() (*[]service.Gerente, error) {
	gs := []GerenteInfra{}
	tx := r.db.Find(&gs)

	sgs := []service.Gerente{}
	for _, g := range gs {
		sgs = append(sgs, service.Gerente{
			Cpf:   g.Cpf,
			Nome:  g.Nome,
			Email: g.Email,
		})
	}

	return &sgs, tx.Error
}
func (r *GerenteRepositoryMysql) Insert(gerente *service.Gerente) (*service.Gerente, error) {
	g := GerenteInfra{
		Cpf:   gerente.Cpf,
		Nome:  gerente.Nome,
		Email: gerente.Email,
	}

	tx := r.db.Create(&g)

	return &service.Gerente{
		Cpf:   g.Cpf,
		Nome:  g.Nome,
		Email: g.Email,
	}, tx.Error
}
func (r *GerenteRepositoryMysql) Update(gerente *service.Gerente) (*service.Gerente, error) {
	return nil, nil
}
func (r *GerenteRepositoryMysql) Delete(cpf string) (bool, error) {
	return false, nil
}

func NewGerenteRepositoryMysql() service.GerenteRepository {

	dsn := GORM_DSN

	fmt.Println("GORM_DSN", dsn)

	db, err := gorm.Open(mysql.Open(dsn), &gorm.Config{})

	if err != nil {
		panic(err)
	}

	db.AutoMigrate(&GerenteInfra{})

	return &GerenteRepositoryMysql{
		db: db,
	}
}
