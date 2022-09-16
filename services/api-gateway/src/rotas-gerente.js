const {isGerenteShield} = require('./shields')
const {contasService, clientesService} = require('./services')

module.exports = (app) => {
    app
        .get('/api/v1/contas/pendentes', isGerenteShield, async (req, res) => {

            const response = await contasService.listarContas({
                filter: {
                    status: 'PENDENTE_APROVACAO',
                    gerenteCpf: req.userInfo.cpf,
                }
            })

            return res.status(200).json({
                "items": response.data
            }).end()
        })
        .post('/api/v1/contas/pendentes/:id/aprovar', isGerenteShield, async (req, res) => {


            const response = await contasService.aprovarConta(req.params.id)
            return res.status(200).json(response.data).end()
        })
        .post('/api/v1/contas/pendentes/:id/reprovar', isGerenteShield, async (req, res) => {
            const response = await contasService.reprovarConta(req.params.id, req.body)
            return res.status(200).json(response.data).end()
        })
        .get('/api/v1/clientes/:cpf', isGerenteShield, async (req, res) => {
            const response = await clientesService.detalhePorCpf(req.params.cpf)
            return res.status(200).json(response.data).end()
        })
        .get('/api/v1/clientes/:cpf/conta', isGerenteShield, async (req, res) => {
            const response = await contasService.detalheContaCpf(req.params.cpf)
            return res.status(200).json(response.data).end()
        })
        .get('/api/v1/clientes/conta/top5', isGerenteShield, async (req, res) => {
            console.log("cpf:" + req.userInfo.cpf)
            const response = await contasService.top5({
                filter: {
                    gerenteCpf: req.userInfo.cpf,
                }
            })

            return res.status(200).json({
                "items": response.data
            }).end()
        })
        .get('/api/v1/clientes', isGerenteShield, async (req, res) => {
            const response = await contasService.listarContas({
                filter: {
                    gerenteCpf: req.userInfo.cpf,
                }
            })

            const clientes = []
            for (const conta of response.data) {
                clientes.push({
                    conta: conta.cliente
                })
            }
            return res.status(200).json({
                "items": clientes
            }).end()
        })
}