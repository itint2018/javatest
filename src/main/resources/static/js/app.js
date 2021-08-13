async function doFetch(action, method, json1) {
    let response = await fetch(action, {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(json1),
        credentials: "same-origin"
    })
    let json = await response.json()
    return {response, json};
}

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
    let action = param.action;
    let method = param.method;
    let {response, json} = await doFetch(action, method, json1);
    if (response.ok) {
        if (json.hasOwnProperty("idSession")) {
            let urlSearchParams = new URLSearchParams(window.location.search);
            let uri = urlSearchParams.get("uri");
            if (uri !== null || uri === '/auth')
                location.href = uri
            else location.href = '/'
        } else if (json.hasOwnProperty('clazz')) {
            console.log("ok", json)
            location.href = `/${json.clazz}/${json.id}`
        }
    } else {
        console.log(json)
    }
    return false
}