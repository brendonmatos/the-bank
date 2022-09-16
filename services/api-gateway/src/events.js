
const amqplib = require('amqplib')

class Producer {
    constructor(connectionUri) {
        this.connectionUri = connectionUri
    }

    async start() {
        const conn = await amqplib.connect(this.connectionUri);
        this.channel = await conn.createChannel();
    }

    async publish(queue, message) {
        console.log("Publishing", queue, message)
        await this.channel.assertQueue(queue, { durable: true })
        return await this.channel.sendToQueue(queue, Buffer.from(JSON.stringify(message)), {
            headers: {
                content_type: 'application/json'
            }
        });
    }
}

class Consumer {
    constructor(connectionUri) {
        this.connectionUri = connectionUri
    }

    async start() {
        const conn = await amqplib.connect(this.connectionUri);
        this.channel = await conn.createChannel();
    }

    async consume(queueName, callback) {
        await this.channel.consume(queueName, async (message) => {
            console.log("RECEIVING AT", queueName, message.content.toString())
            message.content = JSON.parse(message.content.toString())
            await callback(message.content)
            await this.channel.ack(message)
        });
    }
}

module.exports = {
    Producer,
    Consumer
}