async function OnSubmit(param) {
    let message = ''
    let formData = new FormData(param)
    let json1 = new Map([])
    formData.forEach((value, key, parent) => {
        console.log(value.type)
        if (key === "pass") {
            json1[key] = md5(value)
        } else {
            json1[key] = value
        }
    })
    let response = await fetch(param.action, {
        method: param.method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(json1),
        credentials: "same-origin"
    })
    let json = await response.json()
    if (response.ok) {
        if (json.hasOwnProperty("idSession")) {
            let urlSearchParams = new URLSearchParams(window.location.search);
            let uri = urlSearchParams.get("uri");
            if (uri !== null || uri === '/auth')
                location.href = uri
            else location.href = '/'
        } else if (json.hasOwnProperty('id')) {
            //если это комната
            //если это резерв
            //если это пользователь
            console.log("ok", json)
        }
    } else {
        console.log(json)
    }
    return false
}