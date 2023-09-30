const services = require("./services");

const ACCESS_LEVELS = {
    cliente: 0,
    gerente: 1,
    admin: 2
}

const isAdminShield = (req, res, next) => {
    const isAdmin = nivelIsAdmin(req.userInfo.nivel)
    if (isAdmin) return next()
    return res.status(403).end();
}

const isClienteShield = (req, res, next) => {
    const isCliente = nivelIsCliente(req.userInfo.nivel)
    if (isCliente) return next()
    return res.status(403).end();
}

const fraudesShield = ({
    toleranciaMaxima = 0.8,
    operationType,
}) => async (req, res, next) => {
    
    const response = await fraudesService.fraudesCheck({
        sessionId: req.userInfo.sessionId,
        clienteId: req.userInfo.clienteId,
        operationType,
        amount: req.body.amount,
        status: req.body.status,
        contaOrigemId: req.body.contaOrigemId,
        contaDestinoId: req.body.contaDestinoId,
    })

    if (response.data.fraudesScore > toleranciaMaxima) {
        return res.status(403).end();
    }

    return next()
            
}

const nivelIsCliente = (nivel) => {
    return nivel === ACCESS_LEVELS.cliente
}

const nivelIsGerente = (nivel) => {
    console.log(nivel, ACCESS_LEVELS.gerente)
    return nivel === ACCESS_LEVELS.gerente
}

const nivelIsAdmin = (nivel) => {
    return nivel === ACCESS_LEVELS.admin
}

const isGerenteShield = (req, res, next) => {
    console.log(req.userInfo)
    const isGerente = nivelIsGerente(req.userInfo.nivel)
    if (isGerente) return next()
    return res.status(403).end();
}

module.exports = {
    isAdminShield,
    isClienteShield,
    isGerenteShield,
    nivelIsAdmin,
    nivelIsCliente,
    nivelIsGerente,
    fraudesShield
}