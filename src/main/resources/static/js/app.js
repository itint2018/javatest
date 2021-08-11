function OnSubmit(param) {
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
        }, body: JSON.stringify(json1)
    }).then(response => {
        return response.json()
    }).then(json1 => {
        console.log(json1)
    }).catch(e => console.log(e))
    return false
}