document.getElementById("loginForm").addEventListener("submit", validateLogin, true);

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