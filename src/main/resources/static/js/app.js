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
    console.log(formData.serialize)
    let json1 = new Map([])
    let role = []
    let optionsRoom = []
    formData.forEach((value, key, parent) => {
        if (key === "pass") {
            if (value !== "") {
                json1[key] = md5(value)
            } else {
                json1[key] = null
            }
        } else if (key === "role") {
            role.push(value)
            json1[key] = role
        } else if (key === "optionsRoom") {
            optionsRoom.push(value)
            json1[key] = optionsRoom
        } else {
            json1[key] = value
        }
    })
    console.log(role)
    console.log(json1)
    let action = param.action;
    let method = param.method;
    let {response, json} = await doFetch(action, method, json1);
    console.log(json)
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
        if (json.hasOwnProperty("errors")) {
            document.getElementById('form').classList.remove("needs-validation")
            // document.getElementById('form').classList.add("was-validated")
            json.errors.forEach(error => {
                document.getElementById(error.field).classList.add("is-invalid")
                let elementId = error.field + "Validator"
                console.log(elementId)
                document.getElementById(elementId).innerHTML.replaceAll("", "ТЕСТ")
            })
        } else {
            document.getElementById("alert").style.visibility = "visible"
            document.getElementById("alertText").innerText = `${response.status} ${json.message()}`
        }


    }
    return false
}