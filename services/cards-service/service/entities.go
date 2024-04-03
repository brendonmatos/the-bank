package service

type CardStatus int 

const (
	Active CardStatus = iota
	Inactive
)

type Card struct {
	ID    int64  `json:"id"`
	Name string `json:"name"`
	Number string `json:"number"`
	ExpiryDate string `json:"expiry_date"`
	CVV string `json:"cvv"`
	Credit int64 `json:"credit"`
	Status CardStatus `json:"status"`
	Cliente Cliente `json:"cliente"`
}

type Cliente struct {
	ID    int64  `json:"id"`
}
