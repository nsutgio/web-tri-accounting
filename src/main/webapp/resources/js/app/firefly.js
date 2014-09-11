
function bindErrorsToElements(data, scopeErrors) {
    try {
        var messages = data.messages;
        for (var idx = 0; idx < messages.length; idx++) {
            var field = 'err_' + data.fields[idx];
            var message = data.messages[idx];
            scopeErrors[field] = message;
        }
    }catch (err) {}
    return scopeErrors;
}