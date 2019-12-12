function validateLogin(evt) {
    evt.preventDefault();
    let email = evt.target[0].value;
    let password = evt.target[1].value;

    function validEmail(email) {
        console.log(email);
        // Email validation regex from emailregex.com
        let rfc5322 = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/;
        return email.match(rfc5322);
    }

    function validPassword(password) {
        let results = zxcvbn(password);
        console.log(results.score);
        if (results.score < 3) {
            document.getElementById("passwordFeedback").innerHTML = "Weak password<br/>" + results.feedback.suggestions.join("<br/>");
            return false;
        } else {
            document.getElementById("passwordFeedback").innerHTML = "";
            return true;
        }
    }

    if (validEmail(email) && validPassword(password)) {
        return true;
    } else {
        return false;
    }
}

function showPasswordFeedback(evt) {
	let results = zxcvbn(evt.target.value);
	let strengthBar = document.getElementById("passwordMeter");
	
	let score = results.score;
	console.log(score);
	
	strengthBar.setAttribute("aria-valuenow", score);
	strengthBar.style.width = ((score + 1) * 20) + "%";
	switch (score) {
	case 0:
		strengthBar.textContent = "Extremely Weak";
		strengthBar.classList.remove("bg-danger", "bg-warning", "bg-success");
		strengthBar.classList.add("bg-danger");
		break;
	case 1:
		strengthBar.textContent = "Very Weak";
		strengthBar.classList.remove("bg-danger", "bg-warning", "bg-success");
		strengthBar.classList.add("bg-danger");
		break;
	case 2:
		strengthBar.classList.remove("bg-danger", "bg-warning", "bg-success");
		strengthBar.classList.add("bg-danger");
		strengthBar.textContent = "Weak";
		break;
	case 3:
		strengthBar.classList.remove("bg-danger", "bg-warning", "bg-success");
		strengthBar.classList.add("bg-warning");
		strengthBar.textContent = "Strong";
		break;
	case 4:
		strengthBar.classList.remove("bg-danger", "bg-warning", "bg-success");
		strengthBar.classList.add("bg-success");
		strengthBar.textContent = "Very Strong"
		break;
	}
	setFeedback(results.feedback);
	
	function setFeedback(feedback) {
		let passwordFeedback = document.getElementById("passwordFeedback");
		if (results == null) {
			passwordFeedback.innerHTML = "";
		}
		else {
			if (feedback.warning == "") {
				passwordFeedback.innerHTML = feedback.suggestions.join("<br/>");
			} else {
				passwordFeedback.innerHTML = feedback.warning + "<br/>" + feedback.suggestions.join("<br/>");
			}
		}
	}
}

document.getElementById("loginForm").addEventListener("submit", validateLogin, true);
document.getElementById("passwordInput").oninput = showPasswordFeedback;