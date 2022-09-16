import { expect, describe, it, beforeAll } from 'vitest'
import Axios from 'axios'
import crypto from 'crypto'

const rand = crypto.randomBytes(5).toString('hex')
const randomNumber = (min = 0, max = 10) => Math.floor(Math.random() * (max - min)) + min
const randomCpf = () => Array.from({ length: 11 }).map(() => randomNumber()).join('')
const clienteCpf = randomCpf()
const clienteRemoverCpf = randomCpf()
const gerenteCpf = randomCpf()
const adminCpf = randomCpf()
const emailCliente = `cliente-test-${clienteCpf}@gmail.com`
const emailClienteRejeitar = `cliente-test-remover-${clienteRemoverCpf}@gmail.com`
const emailGerente = `gerente-test-${gerenteCpf}@gmail.com`
const emailAdmin = `admin-test-${adminCpf}@gmail.com`
const password = '102030'
const contaDestinoId = "356197522"
let token = undefined
let contaID = undefined
let gerenteIdCpf = undefined

const wait = (ms) => new Promise((resolve) => setTimeout(resolve, ms))
const http = Axios.create({
    baseURL: 'http://api-gateway:3000',
    validateStatus: status => true
})

const sendEvent = (queueName, data) => {
    console.log("SENDING_EVENT", queueName, data)
    return http.post('/admin/queue/' + queueName, data)
}

describe('Healthcheck', async () => {
    it('should return 200', async () => {
        const response = await http.get('/api/v1/health')
        expect(response.status).toEqual(200)
        expect(response.data).toEqual('OK')
    })
})
describe('admin', () => {

    let token = undefined


    it('deve criar autenticacao do admin', async () => {
        await sendEvent('criar_autenticacao', {
            email: emailAdmin,
            senha: password,
            cpf: adminCpf,
            nivel: 2,
        })
        await wait(3000)
    })

    it('deve poder logar', async () => {
        const response = await http.post('/api/v1/logar', {
            "email": emailAdmin,
            "senha": password
        })
        console.log(response.data)
        expect(response.status).toEqual(200)
        expect(response.data.token).toBeDefined()

        token = response.data.token
    })

    it('deve poder criar gerente', async () => {
        const response = await http.post('/api/v1/gerentes', {
            nome: 'gerente',
            email: emailGerente,
            cpf: gerenteCpf,
            senha: password
        }, {
            headers: {
                'Authorization': token
            }
        })
        console.log(response.data)
        expect(response.status).toEqual(200)
        await wait(3000)
    })

    it('deve poder listar clientes por gerentes', async () => {
        const response = await http.get(`/api/v1/dashboard/gerentes`, {
            headers: {
                'Authorization': token
            }
        })
        console.log(response.data)
        expect(response.status).toEqual(200)
    })

    it('deve poder listar gerentes', async () => {
        const response = await http.get('/api/v1/gerentes', {
            headers: {
                'Authorization': token
            }
        })
        console.log(response.data)
        gerenteIdCpf = response.data?.items[0]?.cpf
        expect(response.data.items?.length).toBeGreaterThan(0)
        expect(response.status).toEqual(200)
    })

    it('deve poder consultar detalhe do gerente', async () => {
        const response = await http.get(`/api/v1/gerentes/${gerenteIdCpf}`, {
            headers: {
                'Authorization': token
            }
        })
        console.log(response.data)
        expect(response.status).toEqual(200)
    })

    it('deve poder atualizar gerente', async () => {
        const response = await http.put(`/api/v1/gerentes/${gerenteIdCpf}`, {
            nome: 'gerente atualizado',
            email: emailGerente,
            cpf: gerenteCpf,
            senha: password
        }, {
            headers: {
                'Authorization': token
            }
        })
        console.log(response.data)
        expect(response.status).toEqual(200)
    })

    it.skip('deve poder excluir gerente', async () => {
        const response = await http.delete(`/api/v1/gerentes/${gerenteIdCpf}`, {
            headers: {
                'Authorization': token
            }
        })
        console.log(response.data)
        expect(response.status).toEqual(200)
    })
})
describe('gerente', () => {

    it('deve poder logar como gerente', async () => {
        const response = await http.post('/api/v1/logar', {
            "email": emailGerente,
            "senha": password
        })
        console.log("LOGAR", response.data)
        expect(response.status).toEqual(200)
        expect(response.data.token).toBeDefined()
        token = response.data.token
    })

    it('deve poder consultar cliente', async () => {
        const response = await http.get('/api/v1/clientes/' + clienteCpf, {
            headers: {
                'Authorization': token
            }
        })
        console.log(response.data)
        expect(response.status).toEqual(200)
    })

    it('deve poder listar clientes', async () => {
        const response = await http.get('/api/v1/clientes/', {
            headers: {
                'Authorization': token
            }
        })
        console.log(response.data)
        expect(response.status).toEqual(200)
    })

    it('deve poder listar top 5 clientes', async () => {
        const response = await http.get('/api/v1/clientes/top5', {
            headers: {
                'Authorization': token
            }
        })
        console.log(response.data)
        expect(response.status).toEqual(200)
    })

    describe('autocadastro', () => {
        it('deve criar contas sem erros', async () => {
            let response = await http.post('/api/v1/autocadastro',
                {
                    "nome": "Pessoa Teste",
                    "email": emailCliente,
                    "cpf": clienteCpf,
                    "salario": 1000,
                    "senha": password,
                    "endereco": {
                        "tipo": "Residencial",
                        "logradouro": "Rua Teste",
                        "cep": "12345678",
                        "complemento": "",
                        "numero": "123",
                        "cidade": "Teste",
                        "estado": "SP"
                    }
                })

            response = await http.post('/api/v1/autocadastro',
                {
                    "nome": "Pessoa Teste",
                    "email": emailClienteRejeitar,
                    "cpf": clienteRemoverCpf,
                    "salario": 666666,
                    "senha": password,
                    "endereco": {
                        "tipo": "Residencial",
                        "logradouro": "Rua Teste",
                        "cep": "12345678",
                        "complemento": "",
                        "numero": "123",
                        "cidade": "Teste",
                        "estado": "SP"
                    }
                })
            expect(response.data.cpf).toBeDefined()
            expect(response.data.success).toBeTruthy()
            expect(response.data.message).include('sucesso')
            await wait(3000)
        })

    })


    it('deve poder listar contas pendentes de aprovação e REPROVAR', async () => {
        const response = await http.get('/api/v1/contas/pendentes', {
            headers: {
                'Authorization': token
            }
        })
        console.log(response.data)
        expect(response.status).toEqual(200)
        expect(response.data.items).toBeDefined()
        expect(response.data.items.length).toBeGreaterThan(0)

        const conta = response.data.items.find((item) => item.cpf === clienteRemoverCpf)

        const reprovarResponse = await http.post(`/api/v1/contas/pendentes/${conta.id}/reprovar`, {
            motivoRejeicao: 'ele é do kpt olha o salario :('
        }, {
            headers: {
                'Authorization': token
            }
        })
        expect(reprovarResponse.status).toEqual(200)
    })


    it('deve poder listar contas pendentes de aprovação e aprovar', async () => {
        const response = await http.get('/api/v1/contas/pendentes', {
            headers: {
                'Authorization': token
            }
        })
        console.log(response.data)
        expect(response.status).toEqual(200)
        expect(response.data.items).toBeDefined()
        expect(response.data.items.length).toBeGreaterThan(0)
        contaID = response.data.items[0].id
        const aprovarResponse = await http.post(`/api/v1/contas/pendentes/${contaID}/aprovar`, {}, {
            headers: {
                'Authorization': token
            }
        })
        expect(aprovarResponse.status).toEqual(200)
    })
})


describe('cliente processos logados', () => {

    let token = undefined
    it('deve poder logar', async () => {
        const response = await http.post('/api/v1/logar', {
            "email": emailCliente,
            "senha": password
        })
        console.log(response.data)
        expect(response.status).toEqual(200)
        expect(response.data.token).toBeDefined()
        token = response.data.token
    })

    it('deve poder depositar', async () => {
        const response = await http.post('/api/v1/conta/depositar', {
            "valor": 100
        }, {
            headers: {
                'Authorization': token
            }
        })
        console.log(response.data)
        expect(response.status).toEqual(200)
    })

    it('deve poder sacar', async () => {
        const response = await http.post('/api/v1/conta/sacar', {
            "valor": 100
        }, {
            headers: {
                'Authorization': token
            }
        })
        console.log(response.data)
        expect(response.status).toEqual(200)
    })

    it('deve poder transferir', async () => {
        const response = await http.post('/api/v1/conta/transferir', {
            "valor": 100,
            "contaDestinoId": contaDestinoId
        }, {
            headers: {
                'Authorization': token
            }
        })
        console.log(response.data)
        expect(response.status).toEqual(200)
    })

    it('deve poder ver extratos', async () => {
        const response = await http.get('/api/v1/conta/extrato', {
            params: {
                "dataInicial": "2022-01-01",
                "dataFinal": "2023-01-01"
            },
            headers: {
                'Authorization': token
            }
        })
        console.log(response.data)
        expect(response.status).toEqual(200)
    })

    it('deve poder ver limite e saldo', async () => {
        const response = await http.get('/api/v1/conta/limite-e-saldo', {
            headers: {
                'Authorization': token
            }
        })
        console.log(response.data)
        expect(response.status).toEqual(200)
    })

})



