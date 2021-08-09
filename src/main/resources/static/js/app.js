function OnSubmit(param) {
    let json = "{"
    for (let i = 0; i < param.length; i++) {
        let element = param.elements[i];
        let s = "\"" + element.name + "\":\"";
        if (element.type === "password") {
            json += s + md5(element.value) + "\","
        } else {
            json += s + element.value + "\","
        }
    }
    json = json.slice(0, -1) + "}"
    console.log(json)
    fetch(param.action, {
        method: param.method,
        headers: {
            'Content-Type': 'application/json'
        }, body: json
    }).then(response => {
        return response.json()
    }).then(json1 => {
        console.log(json1)
    })
}