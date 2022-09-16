const sgMail = require('@sendgrid/mail');
sgMail.setApiKey(process.env.SENDGRID_API_KEY);


module.exports = {
    async send(to, subject, body) {

        if (to.includes('test')) {
            console.log('--------------------------------------------------');
            console.log('------------------- TEST EMAIL -------------------');
            console.log('--------------------------------------------------');
            console.log('------------------- CONTENT -------------------');
            console.log('--------------------------------------------------');
            console.log('SUBJECT: ', subject);
            console.log('BODY: ', body);
            console.log('--------------------------------------------------');
            return true;
        }

        const msg = {
            to,
            from: process.env.NO_REPLY_MAIL, // Use the email address or domain you verified above
            subject,
            html: body,
        };

        return sgMail.send(msg)
    }
}