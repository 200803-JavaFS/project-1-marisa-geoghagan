const url = "http://localhost:8080/Project1/"
let length = 0;

async function login() {
    let usern = document.getElementById("username").value;
    let userp = document.getElementById("password").value;

    let user = {
        username: usern,
        password: userp
    }

    let resp = await fetch(url + "login", {
        method: "POST",
        body: JSON.stringify(user),
        credentials: "include"
    })

    if (resp.status === 200) {
        document.getElementById("status").innerText = "Login Successful";
        console.log(resp);
        let resp2 = await fetch(url + "getUser", {
            method: "GET",
            credentials: "include"
        })
        let data = await resp2.json();
        sessionStorage.setItem("user", JSON.stringify(data));
        if (data.roleID === 1) {
            window.location.href = "Manager/ManagerDashboard.html";
        } else {
            window.location.href = "User/UserDashboard.html";
        }
    } else {
        document.getElementById("status").innerText = "Login Failed";
    }
}

async function logout() {
    let resp = await fetch(url + "logout", {
        credentials: "include"
    });

    if(resp.getStatus === 200) {
        window.location.href = "../SignIn.html";
    } else {
        sessionStorage.clear();
        window.location.href = "../SignIn.html";
    }
}

async function getPending() {
    document.getElementById("status").innerText = "";
    let resp = await fetch(url + "viewPending", {
        method: "GET",
        credentials: "include"
    });

    if (resp.status === 200) {
        let data = await resp.json();
        formatTable(data);
        length = data.length;
    }
}

async function getApproved() {
    document.getElementById("status").innerText = "";
    let resp = await fetch(url + "viewApproved", {
        method: "GET",
        credentials: "include"
    });

    if (resp.status === 200) {
        let data = await resp.json();
        formatTable(data);
        length = data.length;
    }
}

async function getDenied() {
    document.getElementById("status").innerText = "";
    let resp = await fetch(url + "viewDenied", {
        method: "GET",
        credentials: "include"
    });

    if (resp.status === 200) {
        let data = await resp.json();
        formatTable(data);
        length = data.length;
    }
}

async function getAll() {
    document.getElementById("status").innerText = "";
    let resp = await fetch(url + "viewAll", {
        method: "GET",
        credentials: "include"
    });

    if (resp.status === 200) {
        let data = await resp.json();
        formatTable(data);
        length = data.length;
    }
}

async function setApproved() {
    let checked = 0;
    for (let index = 0; index < length; index++) {
        if (document.getElementById("r" + index).checked) {
            checked = document.getElementById("r" + index).value;
            console.log("r" + index + " is checked.");
        } else {
            console.log("r" + index + " is not checked.");
        }
    }

    if(checked === 0) {
        document.getElementById("status").innerText = "Please select a request before attempting to approve or deny.";
    } else {
        console.log("The chosen reimbursement has an ID of " + checked + ".");

        let reimbursement = {
            reimID: checked
        }

        let resp = await fetch(url + "approve", {
            method: "POST",
            body: JSON.stringify(reimbursement),
            credentials: "include"
        });
        document.getElementById("status").innerText = "The Reimbursement Request with ID " + checked + " has been approved.";
    }
}

async function setDenied() {
    let checked = 0;
    for (let index = 0; index < length; index++) {
        if (document.getElementById("r" + index).checked) {
            checked = document.getElementById("r" + index).value;
            console.log("r" + index + " is checked.");
        } else {
            console.log("r" + index + " is not checked.");
        }
    }

    if(checked === 0) {
        document.getElementById("status").innerText = "Please select a request before attempting to approve or deny.";
    } else {
        console.log("The chosen reimbursement has an ID of " + checked + ".");

        let reimbursement = {
            reimID: checked
        }

        let resp = await fetch(url + "deny", {
            method: "POST",
            body: JSON.stringify(reimbursement),
            credentials: "include"
        });
        document.getElementById("status").innerText = "The Reimbursement Request with ID " + checked + " has been denied.";
    }
}

async function getUserHistory(id) {
    let user = {
        userID: id
    }

    let resp = await fetch(url + "viewHistory", {
        method: "POST",
        body: JSON.stringify(user),
        credentials: "include"
    });

    if (resp.status === 200) {
        let data = await resp.json();
        formatNoButtonTable(data);
    } else {
        console.log("Something went bad.");
    }
}

function formatTable(data) {
    document.getElementById("tableBody").innerText ="";
    let i = 0;
    for (let reimbursement of data) {
        console.log(reimbursement);
        let row = document.createElement("tr");
        let cell0 = document.createElement("td");
        cell0.innerHTML = '<input type="radio" name="radios" id="r' + i + '" value="' + reimbursement.id + '">';
        row.appendChild(cell0);
        let cell1 = document.createElement("td");
        cell1.innerHTML = reimbursement.id;
        row.appendChild(cell1);
        let cell2 = document.createElement("td");
        cell2.innerHTML = "$" + reimbursement.amount.toFixed(2);
        row.appendChild(cell2);
        let cell3 = document.createElement("td");
        cell3.innerHTML = reimbursement.description;
        row.appendChild(cell3);
        let cell4 = document.createElement("td");
        let submit = new Date(reimbursement.submitted)
        cell4.innerHTML = submit.format("mmmm d, yyyy, h:MM TT");
        row.appendChild(cell4);
        let cell5 = document.createElement("td");
        if (reimbursement.resolved === null) {
            cell5.innerHTML = "Unresolved";
        } else {
            let resolve = new Date(reimbursement.resolved)
            cell5.innerHTML = resolve.format("mmmm d, yyyy, h:MM TT");
        }
        row.appendChild(cell5);
        let cell6 = document.createElement("td");
        cell6.innerHTML = reimbursement.author.firstName + " " + reimbursement.author.lastName;
        row.appendChild(cell6);
        let cell7 = document.createElement("td");
        if (reimbursement.resolver === null) {
            cell7.innerHTML = "Unresolved";
        } else {
            cell7.innerHTML = reimbursement.resolver.firstName + " " + reimbursement.resolver.lastName;
        }
        row.appendChild(cell7);
        let cell8 = document.createElement("td");
        cell8.innerHTML = getStatus(reimbursement.statusID);
        row.appendChild(cell8);
        let cell9 = document.createElement("td");
        cell9.innerHTML = getType(reimbursement.typeID);
        row.appendChild(cell9);
        document.getElementById("tableBody").appendChild(row);
        i++;
    }
}

function formatNoButtonTable(data) {
    document.getElementById("tableBody").innerText ="";
    for (let reimbursement of data) {
        console.log(reimbursement);
        let row = document.createElement("tr");
        let cell1 = document.createElement("td");
        cell1.innerHTML = reimbursement.id;
        row.appendChild(cell1);
        let cell2 = document.createElement("td");
        cell2.innerHTML = "$" + reimbursement.amount.toFixed(2);
        row.appendChild(cell2);
        let cell3 = document.createElement("td");
        cell3.innerHTML = reimbursement.description;
        row.appendChild(cell3);
        let cell4 = document.createElement("td");
        let submit = new Date(reimbursement.submitted)
        cell4.innerHTML = submit.format("mmmm d, yyyy, h:MM TT");
        row.appendChild(cell4);
        let cell5 = document.createElement("td");
        if (reimbursement.resolved === null) {
            cell5.innerHTML = "Unresolved";
        } else {
            let resolve = new Date(reimbursement.resolved)
            cell5.innerHTML = resolve.format("mmmm d, yyyy, h:MM TT");
        }
        row.appendChild(cell5);
        let cell6 = document.createElement("td");
        cell6.innerHTML = reimbursement.author.firstName + " " + reimbursement.author.lastName;
        row.appendChild(cell6);
        let cell7 = document.createElement("td");
        if (reimbursement.resolver === null) {
            cell7.innerHTML = "Unresolved";
        } else {
            cell7.innerHTML = reimbursement.resolver.firstName + " " + reimbursement.resolver.lastName;
        }
        row.appendChild(cell7);
        let cell8 = document.createElement("td");
        cell8.innerHTML = getStatus(reimbursement.statusID);
        row.appendChild(cell8);
        let cell9 = document.createElement("td");
        cell9.innerHTML = getType(reimbursement.typeID);
        row.appendChild(cell9);
        document.getElementById("tableBody").appendChild(row);
    }
}

async function submit() {
    let rAmount = document.getElementById("amountField").value;
    let rDescription = document.getElementById("descriptionField").value;
    let rSubmitted = Date.now();
    let rAuthor = JSON.parse(sessionStorage.getItem("user"));
    let rTypeString = document.getElementById("typeField").value;
    let rType = 0;
    if(rTypeString === "Lodging") {
        rType = 1;
    } else if(rTypeString === "Travel") {
        rType = 2;
    } else if(rTypeString === "Food") {
        rType = 3;
    } else {
        rType = 4;
    }

    let reimbursement = {
        amount : rAmount,
        description : rDescription,
        submitted : rSubmitted,
        author : rAuthor,
        statusID : 1,
        typeID : rType 
    }

    console.log(reimbursement)
    let resp = await fetch(url + "addReimbursement", {
        method: 'POST',
        body: JSON.stringify(reimbursement),
        credentials: "include"
    })

    if(resp.status === 201){
        document.getElementById("status").innerText = "Submission successful. Please await resolution by a Financial Manager.";
    } else {
        document.getElementById("status").innerText = "Submission unsuccessful.";
    }
}

function getStatus(id) {
    if (id === 1) {
        return "Pending";
    } else if (id === 2) {
        return "Denied";
    } else if (id === 3) {
        return "Approved";
    } else {
        return "Null";
    }
}

function getType(id) {
    if (id === 1) {
        return "Lodging";
    } else if (id === 2) {
        return "Travel";
    } else if (id === 3) {
        return "Food";
    } else if (id === 4) {
        return "Other";
    } else {
        return "Null";
    }
}



/*
 * Date Format 1.2.3
 * (c) 2007-2009 Steven Levithan <stevenlevithan.com>
 * MIT license
 *
 * Includes enhancements by Scott Trenda <scott.trenda.net>
 * and Kris Kowal <cixar.com/~kris.kowal/>
 *
 * Accepts a date, a mask, or a date and a mask.
 * Returns a formatted version of the given date.
 * The date defaults to the current date/time.
 * The mask defaults to dateFormat.masks.default.
 */

var dateFormat = function () {
	var	token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
		timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
		timezoneClip = /[^-+\dA-Z]/g,
		pad = function (val, len) {
			val = String(val);
			len = len || 2;
			while (val.length < len) val = "0" + val;
			return val;
		};

	// Regexes and supporting functions are cached through closure
	return function (date, mask, utc) {
		var dF = dateFormat;

		// You can't provide utc if you skip other args (use the "UTC:" mask prefix)
		if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
			mask = date;
			date = undefined;
		}

		// Passing date through Date applies Date.parse, if necessary
		date = date ? new Date(date) : new Date;
		if (isNaN(date)) throw SyntaxError("invalid date");

		mask = String(dF.masks[mask] || mask || dF.masks["default"]);

		// Allow setting the utc argument via the mask
		if (mask.slice(0, 4) == "UTC:") {
			mask = mask.slice(4);
			utc = true;
		}

		var	_ = utc ? "getUTC" : "get",
			d = date[_ + "Date"](),
			D = date[_ + "Day"](),
			m = date[_ + "Month"](),
			y = date[_ + "FullYear"](),
			H = date[_ + "Hours"](),
			M = date[_ + "Minutes"](),
			s = date[_ + "Seconds"](),
			L = date[_ + "Milliseconds"](),
			o = utc ? 0 : date.getTimezoneOffset(),
			flags = {
				d:    d,
				dd:   pad(d),
				ddd:  dF.i18n.dayNames[D],
				dddd: dF.i18n.dayNames[D + 7],
				m:    m + 1,
				mm:   pad(m + 1),
				mmm:  dF.i18n.monthNames[m],
				mmmm: dF.i18n.monthNames[m + 12],
				yy:   String(y).slice(2),
				yyyy: y,
				h:    H % 12 || 12,
				hh:   pad(H % 12 || 12),
				H:    H,
				HH:   pad(H),
				M:    M,
				MM:   pad(M),
				s:    s,
				ss:   pad(s),
				l:    pad(L, 3),
				L:    pad(L > 99 ? Math.round(L / 10) : L),
				t:    H < 12 ? "a"  : "p",
				tt:   H < 12 ? "am" : "pm",
				T:    H < 12 ? "A"  : "P",
				TT:   H < 12 ? "AM" : "PM",
				Z:    utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
				o:    (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
				S:    ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
			};

		return mask.replace(token, function ($0) {
			return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
		});
	};
}();

// Some common format strings
dateFormat.masks = {
	"default":      "ddd mmm dd yyyy HH:MM:ss",
	shortDate:      "m/d/yy",
	mediumDate:     "mmm d, yyyy",
	longDate:       "mmmm d, yyyy",
	fullDate:       "dddd, mmmm d, yyyy",
	shortTime:      "h:MM TT",
	mediumTime:     "h:MM:ss TT",
	longTime:       "h:MM:ss TT Z",
	isoDate:        "yyyy-mm-dd",
	isoTime:        "HH:MM:ss",
	isoDateTime:    "yyyy-mm-dd'T'HH:MM:ss",
	isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
};

// Internationalization strings
dateFormat.i18n = {
	dayNames: [
		"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
		"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
	],
	monthNames: [
		"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
		"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
	]
};

// For convenience...
Date.prototype.format = function (mask, utc) {
	return dateFormat(this, mask, utc);
};