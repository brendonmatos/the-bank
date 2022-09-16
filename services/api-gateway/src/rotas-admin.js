const { isAdminShield } = require('./shields')
const { gerentesService, contasService } = require('./services')

module.exports = (app, producer) => {
    app
        .get('/api/v1/dashboard/gerentes', isAdminShield, async (req, res) => {
            const response = await contasService.listarContas({
                filter: {
                    status: 'ATIVA'
                }
            })

            return res.status(200).json(response.data).end()
        })
        .get('/api/v1/gerentes', isAdminShield, async (req, res) => {
            const response = await gerentesService.listarGerentes()

            return res.status(200).json({
                items: response.data
            }).end()
        })

        .post('/api/v1/gerentes', isAdminShield, async (req, res) => {
            await producer.publish('criar_gerente', {
                nome: req.body.nome,
                email: req.body.email,
                cpf: req.body.cpf,
            })
            await producer.publish('criar_autenticacao', {
                email: req.body.email,
                senha: req.body.senha,
                cpf: req.body.cpf,
                nivel: 1,
            })
            return res.status(200).json({
                success: true,
                message: 'Solicitação de criação de gerente enviada com sucesso'
            }).end()
        })

        .put('/api/v1/gerentes/:id', isAdminShield, async (req, res) => {
            const response = await gerentesService.atualizarGerente(req.params.id, req.body)

            return res.status(200).json(response.data).end()
        })

        .delete('/api/v1/gerentes/:id', isAdminShield, async (req, res) => {
            const response = await gerentesService.removerGerente(req.params.id)

            return res.status(200).json(response.data).end()
        })

        .get('/api/v1/gerentes/:id', isAdminShield, async (req, res) => {
            const response = await gerentesService.detalhePorCpf(req.params.id)

            return res.json(response.data).end()
        })

}