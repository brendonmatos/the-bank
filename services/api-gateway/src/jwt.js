const jwt = require('jsonwebtoken')

module.exports = class {
    
    constructor(secret) {
        this.secret = secret
    }
    sign(content) {
        const token = jwt.sign(content, this.secret, {
            expiresIn: '3 days'
        })
        return token
    }
    async check(token) {
        return new Promise((resolve, reject) => {
            jwt.verify(token, this.secret, (err, userInfo) => {
                if (err) {
                    reject(err)
                    return
                }
                resolve(userInfo)
            })
        })
    }
}