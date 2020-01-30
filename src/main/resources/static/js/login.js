function getValidator(emailIndex, passwordIndex, passwordFeedbackElement, verifyPasswordIndex=-1) {
	return function (evt) {
		let email = evt.target[emailIndex].value;
		let password = evt.target[passwordIndex].value;

		function validEmail() {
			// Email validation regex from emailregex.com
			let rfc5322 = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)])/;
			return email.match(rfc5322);
		}

		function validPassword() {
			let results = zxcvbn(password);
			if (results.score < 3) {
				//passwordFeedbackElement.innerHTML = "Weak password<br/>" + results.feedback.suggestions.join("<br/>");
				return false;
			} else {
				//passwordFeedbackElement.innerHTML = "";
				return password.length <= 48;
			}
		}

		function passwordsMatch() {
			// If the sentinel value was not overridden, bypass the check
			if (verifyPasswordIndex < 0) {
				return true;
			}
			let password2 = evt.target[verifyPasswordIndex].value;
			return password === password2;
		}

		if (!validEmail() || !validPassword() || !passwordsMatch()) {
			evt.preventDefault();
		}
	}
}

function getFeedbackDisplay(strengthBar, passwordFeedback) {
	return function (evt) {
		let results = zxcvbn(evt.target.value);

		let score = results.score;

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
				strengthBar.textContent = "Very Strong";
				break;
		}
		setFeedback();

		function setFeedback() {
			if (results == null) {
				passwordFeedback.innerHTML = "";
			} else {
				if (results.feedback.warning === "") {
					passwordFeedback.innerHTML = results.feedback.suggestions.join("<br/>");
				} else {
					passwordFeedback.innerHTML = results.feedback.warning + "<br/>" + results.feedback.suggestions.join("<br/>");
				}
			}
		}
	}
}

function getPasswordMatchDisplay(passwordInput, passwordConfirm, passwordMatchFeedback) {
	return function () {
		if (passwordInput.value !== passwordConfirm.value) {
			passwordMatchFeedback.textContent = "Passwords must match!";
			passwordConfirm.classList.remove("is-valid");
			passwordConfirm.classList.add("is-invalid");
		} else {
			passwordMatchFeedback.textContent = "";
			passwordConfirm.classList.remove("is-invalid");
			passwordConfirm.classList.add("is-valid");
		}
	}
}