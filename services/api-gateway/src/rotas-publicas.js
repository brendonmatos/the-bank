const { authService } = require('./services')



module.exports = (app, producer, jwt) => {

    app.post('/admin/queue/:name', async (req, res, next) => {
        res.json({
            success: producer.publish(req.params.name, req.body),
            name: req.params.name
        }).end()
    })


    app.get('/api/v1/health', (req, res) => {
        return res.send('OK').status(200).end()
    })

    app.post('/api/v1/logar', async (req, res) => {
        try {
            const loginBody = req.body
            const response = await authService.login(loginBody)
            const loginResponse = response.data
            if (loginResponse.cpf) {
                const me = await authService.me(loginResponse.cpf, loginResponse.nivel)
                const payload = { ...loginResponse, ...me }
                const token = await jwt.sign(payload)
                return res.json({
                    "payload": payload,
                    "token": token
                })
            }
            res.status(403).json({
                message: 'Usuário ou senha inválidos'
            })
        } catch (e) {
            console.log(e)
            res.status(500).json(e)
        }
    })

    /** @typedef {object} autocadastroBody
     * @property {string} nome
     * @property {string} email
     * @property {string} cpf
     * @property {number} salario
     * @property {string} senha
     * @property {object} endereco
     * @property {string} endereco.tipo
     * @property {string} endereco.logradouro
     * @property {string} endereco.cep
     * @property {string} endereco.complemento
     * @property {string} endereco.numero
     * @property {string} endereco.cidade
     * @property {string} endereco.estado
     */

    app.post('/api/v1/autocadastro', async (req, res) => {

        /**
         * @type {autocadastroBody}
         */
        const body = req.body

        console.log(body);

        const success = [
            await producer.publish('criar_cliente', {
                cpf: body.cpf,
                nome: body.nome,
                email: body.email,
                salario: body.salario,
                enderecoTipo: body.endereco.tipo,
                enderecoLogradouro: body.endereco.logradouro,
                enderecoCep: body.endereco.cep,
                enderecoComplemento: body.endereco.complemento,
                enderecoNumero: body.endereco.numero,
                enderecoCidade: body.endereco.cidade,
                enderecoEstado: body.endereco.estado
            }),
            await producer.publish('criar_autenticacao', {
                cpf: body.cpf,
                email: body.email,
                senha: body.senha,
                nivel: 0,
            }),
            await producer.publish('criar_conta', {
                cpf: body.cpf,
                salarioCliente: body.salario,
                email: body.email,
                saldo: 0,
            })
        ].every(x => x)

        return res.json({
            cpf: body.cpf,
            success: success,
            message: 'Solicitação realizado com sucesso. Aguarde a aprovação pelo gerente.'
        })
    })
}