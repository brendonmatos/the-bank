
const express = require('express')
const morgan = require('morgan')
const cors = require('cors')
const { clientesService, gerentesService, authService } = require('./services')
const rotasPublicas = require('./rotas-publicas')
const rotasAdmin = require('./rotas-admin')
const rotasCliente = require('./rotas-cliente')
const rotasGerente = require('./rotas-gerente')
const { nivelIsAdmin, nivelIsCliente, nivelIsGerente } = require('./shields')
const mail = require('./mail')
const crypto = require('crypto')

module.exports = async ({ consumer, producer, jwt }) => {

    const isJwtLoggedShield = async (req, res, next) => {
        const authorization = req.headers["authorization"]
        try {
            const content = await jwt.check(authorization)
            req.userInfo = content
            return next()
        } catch (err) {
            res.status(403).end()
            return
        }
    }

    await consumer.start()
    await producer.start()

    const app = express()
    app.use(cors(
        { host: "*" },
        { methods: ['GET', 'POST', 'DELETE', 'UPDATE', 'PUT', 'PATCH'] }
    ))
    app.use(express.json())
    app.use(morgan('dev'))


    rotasPublicas(app, producer, jwt)

    app.use(isJwtLoggedShield)

    rotasHibridas(app)
    rotasCliente(app)
    rotasGerente(app)
    rotasAdmin(app, producer)

    orquestrarSagaConta(producer, consumer)

    return app
}


const rotasHibridas = (app) => {

    app.get('/api/v1/me', async (req, res) => {
        if (req.userInfo) {
            const me = await authService.me(req.userInfo.cpf, req.userInfo.nivel)
            const payload = { ...req.userInfo, ...me }
            return res.status(200).json(payload)
        }

        return res.json({
            message: 'Estranho.'
        }).status(500).end()
    })
}

const orquestrarSagaConta = (producer, consumer) => {

    consumer.consume('conta_rejeitada', async (content) => {
        console.log('conta_rejeitada', content)
        const clienteResponse = await clientesService.detalhePorCpf(content.cpf)
        const { cpf, email } = clienteResponse.data
        await producer.publish('excluir_cliente', {
            cpf
        })
        // await producer.publish('excluir_autenticacao', {
        //     cpf
        // })

        if (!email) {
            console.log('--------------------------------------------------');
            console.log("Não foi possível enviar o email de conta rejeitada para o cliente: " + email)
            return true;
        }

        await mail.send(email, 'Conta rejeitada', 'Sua conta foi rejeitada. Motivo: ' + content.motivoRejeicao)
    })

    consumer.consume('conta_aprovada', async (content) => {
        console.log('conta_aprovada', content)

        const clienteResponse = await clientesService.detalhePorCpf(content.cpf)
        const { email } = clienteResponse.data


        if (!email) {
            console.log('--------------------------------------------------');
            console.log("Não foi possível enviar o email de conta aprovada para o cliente: " + email)
            console.log(content, clienteResponse.data)
            return true;
        }


        const novaSenha = crypto.randomBytes(4).toString('hex')
        await producer.publish('alterar_senha_autenticacao', {
            email,
            senha: novaSenha
        })
        await mail.send(email, 'Conta aprovada', 'Sua conta foi aprovada. Sua senha é: ' + novaSenha)
    })
}