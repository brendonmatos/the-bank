const Axios = require('axios')
const AxiosLogger = require('axios-logger')
const { nivelIsGerente, nivelIsCliente, nivelIsAdmin } = require('./shields')

const authService = {
    http: Axios.create({
        baseURL: process.env.AUTH_SERVICE,
        validateStatus: status => true
    }),
    login(loginBody) {
        return this.http.post('/login', loginBody)
    },
    async me(cpf, nivel) {
        const isGerente = nivelIsGerente(nivel)
        const isCliente = nivelIsCliente(nivel)
        const isAdmin = nivelIsAdmin(nivel)

        if (isAdmin) {
            return undefined
        }

        if (isGerente) {
            return await gerentesService
                .detalhePorCpf(cpf)
                .then((result) => result.data)
        }

        if (isCliente) {
            return await clientesService
                .detalhePorCpf(cpf)
                .then((result) => result.data)
        }

        return undefined
    }
}

const contasService = {
    http: Axios.create({
        baseURL: process.env.CONTAS_SERVICE,
        validateStatus: status => true
    }),
    async listarContas(options) {
        const response = await this.http.get('/', {
            params: {
                status: options.filter?.status,
                gerenteCpf: options.filter?.gerenteCpf
            }
        })

        console.log("CONTAS", response.data)
        for (const conta of response.data) {
            const clienteResponse = await clientesService.detalhePorCpf(conta.cpf)
            conta.cliente = clienteResponse.data
        }


        return response
    },
    async top5(options) {
        const response = await this.http.get('/top5', {
            params: {
                status: options.filter?.status,
                gerenteCpf: options.filter?.gerenteCpf
            }
        })

        for (const conta of response.data) {
            const clienteResponse = await clientesService.detalhePorCpf(conta.cpf)
            conta.cliente = clienteResponse.data
            delete conta.cpf
            delete conta.id
            delete conta.limite
            delete conta.motivoRejeicao
            delete conta.saldo
            delete conta.status
            delete conta.numero
            delete conta.dataCriacao
        }

        return response
    },
    detalheConta(id) {
        return this.http.get(`/${id}`)
    },
    detalheContaCpf(id) {
        return this.http.get(`/cpf/${id}`, {})
    },
    detalheContaPendenteStatusCpf(id, options) {
        return this.http.get(`/cpf/${id}`, {
            params: {
                status: options.filter?.status,
                gerenteCpf: options.filter?.gerenteCpf
            }
        })
    },
    atualizarConta(id, data) {
        return this.http.put(`/${id}`, data)
    },
    aprovarConta(id) {
        return this.http.put(`/${id}/aprovar`)
    },
    reprovarConta(id, data) {
        return this.http.put(`/${id}/reprovar`, data)
    },
    sacarDeConta(id, data) {
        return this.http.put(`/${id}/sacar`, data)
    },
    depositarEmConta(id, data) {
        return this.http.put(`/${id}/depositar`, data)
    },
    transferirEntreContas(id, data) {
        return this.http.put(`/${id}/transferir`, data)
    },
    listarMovimentacoes(id, filters) {
        return this.http.get(`/${id}/movimentacoes`, {
            params: filters
        })
    }
}

const gerentesService = {
    http: Axios.create({
        baseURL: process.env.GERENTES_SERVICE,
        validateStatus: status => true
    }),
    criarGerente(data) {
        return this.http.post('/', data)
    },
    listarGerentes() {
        return this.http.get('/')
    },
    detalhePorCpf(cpf) {
        return this.http.get(`/${cpf}`)
    },
    atualizarGerente(cpf, data) {
        return this.http.put(`/${cpf}`, data)
    },
    removerGerente(cpf) {
        return this.http.delete(`/${cpf}`)
    },
}

const clientesService = {
    http: Axios.create({
        baseURL: process.env.CLIENTES_SERVICE,
        validateStatus: status => true
    }),
    async formatCliente(cliente, config = { populate: { conta: true } }) {

        if (config.populate.conta) {
            const contaResponse = await contasService.detalheContaCpf(cliente.cpf)
            cliente.conta = contaResponse.data;
        }

        return cliente
    },
    async detalhePorCpf(cpf) {
        const clienteResponse = await this.http.get(`/${cpf}`)
        await this.formatCliente(clienteResponse.data)
        return clienteResponse
    },
    async listarClientes() {
        const response = await this.http.get(`/`, {
            params: {
                gerenteCpf: options.filter?.gerenteCpf
            }
        })
        for (const cliente of response.data) {
            await this.formatCliente(cliente)
        }

        return response
    },

}

authService.http.interceptors.request.use(AxiosLogger.requestLogger);
authService.http.interceptors.response.use(AxiosLogger.responseLogger);
clientesService.http.interceptors.request.use(AxiosLogger.requestLogger);
clientesService.http.interceptors.response.use(AxiosLogger.responseLogger);
contasService.http.interceptors.request.use(AxiosLogger.requestLogger);
contasService.http.interceptors.response.use(AxiosLogger.responseLogger);
gerentesService.http.interceptors.request.use(AxiosLogger.requestLogger);
gerentesService.http.interceptors.response.use(AxiosLogger.responseLogger);

module.exports = {
    authService,
    clientesService,
    contasService,
    gerentesService,
}