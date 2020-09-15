const request = require("request-promise");

class StackExchangeChatClient {
    constructor(siteUrl, fkey, cookieJar) {
        this.siteUrl = siteUrl;
        this.fkey = fkey;
        this.cookieJar = cookieJar;
    }

    async send(roomId, messageText) {
        console.log("send", roomId, messageText, this.siteUrl);
        return request({
            method: 'POST',
            uri: `${this.siteUrl}/chats/${roomId}/messages/new`,
            jar: this.cookieJar,
            form: {
                text: messageText,
                fkey: this.fkey
            },
        });
    }

    async edit(messageId, messageText) {
        console.log("edit", messageId, messageText, this.siteUrl); // edit undefined undefined https://chat.stackoverflow.com
        return request({
            method: 'POST',
            uri: `${this.siteUrl}/messages/${messageId}`,
            jar: this.cookieJar,
            form: {
                text: messageText,
                fkey: this.fkey
            },
        });
    }
}

module.exports = {ChatClient: StackExchangeChatClient};
