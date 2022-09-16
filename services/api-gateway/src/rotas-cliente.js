const { isClienteShield } = require('./shields')

const { contasService } = require('./services')

module.exports = (app) => {
    app
        .post('/api/v1/conta/sacar', isClienteShield, async (req, res) => {
            const contaId = req.userInfo?.conta?.id
            const response = await contasService.sacarDeConta(contaId, req.body)

            console.log(222, response.data)

            // return res.status(200).json({
            //     "valor": 200_000_000,
            // }).end()
            return res.status(200).json(response.data).end()
        })
        .post('/api/v1/conta/depositar', isClienteShield, async (req, res) => {
            const contaId = req.userInfo.conta.id
            const response = await contasService.depositarEmConta(contaId, req.body)

            // return res.status(200).json({
            //     "valor": 2_00_0000,
            // }).end()

            return res.status(200).json(response.data).end()
        })
        .post('/api/v1/conta/transferir', isClienteShield, async (req, res) => {
            const contaId = req.userInfo.conta.id
            console.log("sending request to contas service", contaId, req.body)
            const response = await contasService.transferirEntreContas(contaId, req.body)
            // return res.status(200).json({
            //     "valor": 10_000_00,
            //     "conta_destino": "12345678901",
            // }).end()
            return res.status(200).json(response.data).end()
        })
        .get('/api/v1/conta/extrato', isClienteShield, async (req, res) => {
            const contaId = req.userInfo.conta.id
            const response = await contasService.listarMovimentacoes(contaId, req.query)
            return res.status(200).json({
                // "items": [
                //     {
                //         "data_hora": "2022-01-01",
                //         "valor": 5_000_00,
                //         "tipo": "depósito", //transferencia, depósito ou saque
                //         "destino_nome": "Ana"
                //     }
                // ]

                items: response.data
            }).end()
        })
        .get('/api/v1/conta/limite-e-saldo', isClienteShield, async (req, res) => {
            // return res.status(200).json({
            //     // "limite": 10_000_00,
            //     // "saldo": 5_000_00

            // }).end()

            return res.json(req.userInfo.conta).end()
        })

}