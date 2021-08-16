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
        } else {
            location.href = `/${location.pathname.split("/")[1]}`
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

async function onLoadTable() {

    console.log("ready")
    await replaceTable()
    setInterval(replaceTable, 10000)
}

async function replaceTable() {
    let doFetch1 = await doFetch("/api/reserve_room", "GET");
    let elementById = document.getElementById("table");
    elementById.innerHTML = ""
    if (doFetch1.response.ok) {
        doFetch1.json.forEach(reserveRoom => {
            let classCss
            if (reserveRoom.proof !== null) {
                classCss = "table-success"
            } else {
                classCss = "table-danger"
            }
            let start = new Date(reserveRoom.start)
            let end = new Date(reserveRoom.end)
            elementById.innerHTML += (`               
                <tr name="room1" class="${classCss}">
                    <td onclick="location.href='/reserve_room/${reserveRoom.id}'">${reserveRoom.room.name}</td>
                    <td onclick="location.href='/reserve_room/${reserveRoom.id}'">${start.toLocaleString('ru-ru', {
                year: 'numeric',
                month: '2-digit',
                day: '2-digit'
            })}<br> ${start.toLocaleTimeString('ru-ru', {
                hour: "2-digit",
                minute: "2-digit"
            })}-${end.toLocaleTimeString('ru-ru',
                {
                    hour: "2-digit",
                    minute: "2-digit"
                })}
                    </td>
                    <td onclick="location.href='/reserve_room/${reserveRoom.id}'">${reserveRoom.user.lastName} ${reserveRoom.user.firstName}</td>
                    <td>
                        <button class="btn btn-success btn-sm" onclick="doFetch('/api/reserve_room/${reserveRoom.id}/proof', 'GET');replaceTable()"><span
                                class="material-icons text-light"
                                style="font-size: .75rem" >done</span>
                        </button>
                    </td>
                    <td>
                        <button class="btn btn-danger btn-sm" onclick="doFetch('/api/reserve_room/${reserveRoom.id}', 'DELETE');replaceTable()"><span
                                class="material-icons text-light"
                                style="font-size: .75rem">close</span>
                        </button>
                    </td>
                </tr>`)
        })
    }

}