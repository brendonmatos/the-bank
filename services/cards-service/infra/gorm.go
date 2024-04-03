package infra

type CardInfra struct {
	ID    int64  `gorm:"primaryKey" json:"id"`
	Name string `json:"name"`
	Number string `json:"number"`
	ExpiryDate string `json:"expiry_date"`
	CVV string `json:"cvv"`
	Credit int64 `json:"credit"`
	Status CardStatus `json:"status"`
	ClienteID int64 `json:"cliente_id"`
}


