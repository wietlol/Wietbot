const username = process.env.username;
const password = process.env.password;

const mainSiteUrl = 'https://stackoverflow.com';
const chatSiteUrl = 'https://chat.stackoverflow.com';

const {ChatClientFactory} = require('./StackExchangeChatClientFactory.js');

// expect room id and message text
async function sendMessage(event) {
    const roomId = event.roomId;
    const messageText = event.messageText;

    await callStackOverflow(async function (client) {
        await client.send(roomId, messageText);
    });
}

async function editMessage(event) {
    const messageId = event.messageId;
    const messageText = event.messageText;

    await callStackOverflow(async function (client) {
        await client.edit(messageId, messageText);
    });
}

const clientFactory = new ChatClientFactory(mainSiteUrl, chatSiteUrl);
let client = null;
async function callStackOverflow(action) {
    if (client == null)
        client = await clientFactory.createClient(username, password);

    try {
        // try to do the action with an existing client
        await action(client);
    } catch (error) {
        console.log(error);
        // try again with a new client (if this fails, we just let the error go up)
        client = await clientFactory.createClient(username, password);
        await action(client);
    }
}

module.exports = {sendMessage: sendMessage, editMessage: editMessage};
