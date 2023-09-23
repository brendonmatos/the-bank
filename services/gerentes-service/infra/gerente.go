package infra

type GerenteInfra struct {
	Cpf   string `gorm:"primaryKey" json:"cpf"`
	Nome  string `json:"nome"`
	Email string `json:"email"`
}
