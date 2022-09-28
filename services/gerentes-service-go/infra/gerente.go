package infra

type Gerente struct {
	Cpf   string `gorm:"primaryKey"`
	Nome  string
	Email string
}
