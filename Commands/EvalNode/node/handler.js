module.exports.evalNodePrivate = async function (event) {
    if (event.code === undefined || event.code === null || event.code === "")
        return {}

    const items = [];
    console.log = (obj) => {
        items.push(obj)
    };
    console.error = console.debug = console.warn = console.log;

    const resultObject = exec(event.code);
    const result = stringify(resultObject);

    const stdOut = items.join("\r\n");
    if (stdOut === "") {
        if (/\S/.test(result)) // check if not whitespace
            return {result};
        else
            return {result: "<empty result>"};
    } else
        return {result: `Result: ${result}\r\nOutput:\r\n${stdOut}`};
};

function exec(code) {
    try {
        return eval(code);
    } catch (err) {
        return err;
    }
}

function stringify(object) {
    if (object === undefined)
        return "undefined";
    if (object === null)
        return "null";

    if (object.toString !== Object.prototype.toString)
        return object.toString();

    return JSON.stringify(object);
}
