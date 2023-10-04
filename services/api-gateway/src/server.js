const createServer = require('./create-server')
const JWTController = require('./jwt')
const { Producer, Consumer } = require('./events')

const PORT = process.env.SERVER_PORT || 3000
const JWT_SECRET = process.env.JWT_SECRET
const RABBITMQ_CONNECTION_URI = process.env.RABBITMQ_CONNECTION_URI

if (!JWT_SECRET) throw new Error('JWT_SECRET is required')

const jwt = new JWTController(JWT_SECRET)
const consumer = new Consumer(RABBITMQ_CONNECTION_URI)
const producer = new Producer(RABBITMQ_CONNECTION_URI)

createServer({
    consumer,
    producer,
    jwt
}).then((app) => {
    app.listen(PORT, () => {
        console.log('Server started!')
        console.log('Listening on http://localhost:' + (PORT))
    })
})