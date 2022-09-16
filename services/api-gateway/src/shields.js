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
    nivelIsGerente
}