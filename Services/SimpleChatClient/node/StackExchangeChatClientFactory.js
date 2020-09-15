// const fetch = require('node-fetch');
const cheerio = require('cheerio');
const request = require("request-promise");
const {ChatClient} = require('./StackExchangeChatClient.js');


// const FormData = require('form-data');

// const FileCookieStore = require('tough-cookie-filestore');
// const fs = require('fs');
// const path = require('path');

class StackExchangeChatClientFactory {
    constructor(mainSiteUrl, chatSiteUrl) {
        this.mainSiteUrl = mainSiteUrl;
        this.chatSiteUrl = chatSiteUrl;
    }

    async createClient(username, password) {
        const cookieJar = request.jar();
        // const cookieJar = this.createJar();
        // const cookieJar = new CookieJar("jar.json");
        // const cookieJar = {};

        const mainFKey = await this.getMainFKey(cookieJar)
        console.log("mainFKey", mainFKey);

        await this.login(mainFKey, username, password, cookieJar)
        console.log("logged on", username);

        const accountFKey = await this.getAccountFKey(cookieJar)
        console.log("accountFKey", accountFKey);

        console.log(cookieJar);

        return new ChatClient(
            this.chatSiteUrl,
            accountFKey,
            cookieJar
        );
    }

    // createJar() {
    //     /* The following is quite possibly the worst code in the repository, but I have to do this because FileCookieStore has shitty code and will save invalid JSON files, crashing the app next time you run it. */
    //     const cookies_path = path.join(__dirname, '..', 'data', this.bot.saveFolder, 'cookies.json');
    //     fs.mkdirSync(path.join(__dirname, '..', 'data', this.bot.saveFolder), {recursive: true});
    //     if (!fs.existsSync(cookies_path)) {
    //         fs.writeFileSync(cookies_path, '{}');
    //     } else {
    //         let data = fs.readFileSync(cookies_path).toString();
    //         /* Or just clear out the file */
    //         if (!data.isJSON()) {
    //             fs.writeFileSync(cookies_path, '{}');
    //         }
    //     }
    //     return request.jar(new FileCookieStore(cookies_path));
    // }

    async getMainFKey(cookieJar) {
        // const response = await fetch(this.mainSiteUrl + '/users/login');
        // cookieJar.cookies = this.parseCookies(response)
        // return this.extractFKey(await response.text());

        const response = await request({
            method: 'GET',
            uri: this.mainSiteUrl + '/users/login',
            jar: cookieJar,
            resolveWithFullResponse: true
        });
        return this.extractFKey(response.body);
    }

    async getAccountFKey(cookieJar) {
        // const response = await fetch(this.chatSiteUrl, {
        //     headers: {
        //         'cookie': cookieJar.cookies
        //     }
        // });
        // return this.extractFKey(await response.text());

        const response = await request({
            method: 'GET',
            uri: this.chatSiteUrl,
            jar: cookieJar,
            resolveWithFullResponse: true
        });
        return this.extractFKey(response.body);
    }

    async login(fKey, username, password, cookieJar) {
        // const form = new FormData();
        // form.append('fKey', fKey);
        // form.append('email', username);
        // form.append('password', password);
        // const response = await fetch(this.mainSiteUrl + '/users/login', {
        //     method: 'POST',
        //     body: form,
        //     headers: {
        //         'cookie': cookieJar.cookies
        //     }
        // });
        //
        // console.log(cookieJar.cookies);
        // console.log(response);
        //
        // cookieJar.cookies = this.parseCookies(response);

        const response = await request({
            method: 'POST',
            uri: this.mainSiteUrl + '/users/login',
            jar: cookieJar,
            followAllRedirects: true,
            form: {
                fkey: fKey,
                email: username,
                password: password
            },
            headers: {
                'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/12.1.1 Safari/605.1.15'
            }
        });

        // console.log(respose.body);
    }

    extractFKey(html) {
        // noinspection JSUnresolvedFunction
        const $ = cheerio.load(html);
        return $('input[name="fkey"]').val();
    }

    // parseCookies(response) {
    //     const raw = response.headers.raw()['set-cookie'];
    //     if (raw === undefined)
    //         return '';
    //     return raw.map((entry) => {
    //         const parts = entry.split(';');
    //         return parts[0];
    //     }).join(';');
    // }
}

module.exports = {ChatClientFactory: StackExchangeChatClientFactory};
