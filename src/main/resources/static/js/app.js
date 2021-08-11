function OnSubmit(param) {
    let message = ''
    let formData = new FormData(param)
    let json1 = new Map([])
    formData.forEach((value, key, parent) => {
        console.log(value.type)
        if (key === "password") {
            json1[key] = md5(value)
        } else {
            json1[key] = value
        }
    })
    console.log(JSON.stringify(json1))
    let data = Object.fromEntries(json1)
    fetch(param.action, {
        method: param.method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(json1),
        credentials: "same-origin"
    }).then(response => {
        if (response.status !== 200) {
            document.getElementById('alertText').innerHTML = `${response.status.toString()}`
            document.getElementById('alert').style.visibility = 'visible'
        } else {
            let urlSearchParams = new URLSearchParams(window.location.search);
            location.href = urlSearchParams.get("uri")

        }
        return response.json()
    }).then(json1 => {
        document.getElementById('alertText').innerHTML += ` ${json1.message}`

        console.log(json1)
    })
    return false
}