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
            location.href = `/${json.clazz}/`
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

function formatDate(start) {
    let startDate = new Date(start)
    return startDate.toLocaleString('ru-ru', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit'
    });
}

function formatTime(start) {
    let startDate = new Date(start)
    return startDate.toLocaleTimeString('ru-ru', {
        hour: "2-digit",
        minute: "2-digit"
    });
}

async function replaceTable() {
    let doFetch1 = await doFetch("/api/reserve_room/unproofed", "GET");
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
                <tr name="room1" class="${classCss} " >
                    <td onclick="location.href='/reserve_room/${reserveRoom.id}'">${reserveRoom.room.name}</td>
                    <td onclick="location.href='/reserve_room/${reserveRoom.id}'">${(formatDate(start))}<br> ${(formatTime(start))}-${formatTime(end)}
                    </td>
                    <td onclick="location.href='/reserve_room/${reserveRoom.id}'">${reserveRoom.user.lastName} ${reserveRoom.user.firstName.charAt(0)}.</td>
                    <td>
                        <button class="btn btn-success btn-sm" onclick="proofButton(${reserveRoom.id})"><span
                                class="material-icons text-light"
                                style="font-size: .75rem" >done</span>
                        </button>
                    </td>
                    <td>
                        <button class="btn btn-danger btn-sm" onclick="deleteButton(${reserveRoom.id})"><span
                                class="material-icons text-light"
                                style="font-size: .75rem">close</span>
                        </button>
                    </td>
                </tr>`)
        })
    }

}

async function deleteButton(id) {
    await clickButton(`/api/reserve_room/${id}`, 'DELETE');
}

async function proofButton(id) {
    await clickButton(`/api/reserve_room/${id}/proof`, 'GET')
}

async function clickButton(action, method) {
    let fetchData = await doFetch(action, method);
    let elementById = document.getElementById("alertPlaceholder")

    elementById.innerHTML = ""
    let type = ""
    let message = ""
    let json = fetchData.json;
    let key = ""

    if (fetchData.response.ok) {
        type = "success"
        if (method !== "DELETE")
            message = `Резерв комнаты ${json.room.name} ${formatDate(json.start)} ${formatTime(json.start)}-${formatTime(json.end)} ${getMessage(method)}`
        else message = "Удалено"
    } else {
        type = "danger"
    }
    elementById.innerHTML += `
    <div class="alert alert-${type} alert-dismissible" role="alert">${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>`
}

function getMessage(method) {
    switch (method) {
        case "GET":
            return "зарезервирована";
        case "DELETE":
            return "удалена";
        default:
            return "не известно";
    }
}

async function deleteRoom(id) {
    let promise = await doFetch(`/api/rooms/${id}`, 'DELETE')
    if (promise.response.ok) {
        location.href = '/rooms'
    }
}

