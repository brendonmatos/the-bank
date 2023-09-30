const { isClienteShield, fraudesShield } = require('./shields')
const { contasService } = require('./services')

module.exports = (app) => {
    app
        .post('/api/v1/conta/sacar', isClienteShield, fraudesShield({
            operationType: 'saque',
            toleranciaMaxima: 0.1
        }), async (req, res) => {
            const contaId = req.userInfo?.conta?.id
            const response = await contasService.sacarDeConta(contaId, req.body)
            return res.status(200).json(response.data).end()
        })
        .post('/api/v1/conta/depositar', isClienteShield, fraudesShield({
            operationType: 'deposito',
            toleranciaMaxima: 0.9
        }), async (req, res) => {
            const contaId = req.userInfo.conta.id
            const response = await contasService.depositarEmConta(contaId, req.body)
            return res.status(200).json(response.data).end()
        })
        .post('/api/v1/conta/transferir', isClienteShield, fraudesShield({
            operationType: 'transferencia',
            toleranciaMaxima: 0.15
        }), async (req, res) => {
            const contaId = req.userInfo.conta.id
            console.log("sending request to contas service", contaId, req.body)
            const response = await contasService.transferirEntreContas(contaId, req.body)
            return res.status(200).json(response.data).end()
        })
        .get('/api/v1/conta/extrato', isClienteShield, async (req, res) => {
            const contaId = req.userInfo.conta.id
            const response = await contasService.listarMovimentacoes(contaId, req.query)
            return res.status(200).json({
                items: response.data
            }).end()
        })
        .get('/api/v1/conta/limite-e-saldo', isClienteShield, async (req, res) => {
            return res.json(req.userInfo.conta).end()
        })

}