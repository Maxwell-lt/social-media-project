function populatePosts(posts) {
	console.log("Populating posts");
    var postContainer = document.getElementById("post-container");
    postContainer.innerHTML = "";
    for (var i in posts) {
        if (posts[i].title !== null && posts[i].text !== null && posts[i].pageLink !== null) {
            var card = document.createElement("div");
            card.classList.add("card", "col-sm-12", "col-md-6", "col-lg-4", "col-xl-3");
            var cardBody = document.createElement("div");
            cardBody.classList.add("card-body");
            var title = document.createElement("h5");
            title.classList.add("card-title");
            var titleLink = document.createElement("a");
            titleLink.href = posts[i].pageLink;
            titleLink.textContent = posts[i].title;
            title.appendChild(titleLink);
            var text = document.createElement("p");
            text.classList.add("card-text");
            text.textContent = posts[i].text;
            cardBody.appendChild(title);
            
            if (posts[i].image !== null) {
            	var img = document.createElement("img");
            	img.classList.add("card-img-top");
            	img.src = posts[i].image;
            	cardBody.appendChild(img)
            }
            
            cardBody.appendChild(text);
            card.appendChild(cardBody);
            postContainer.appendChild(card);
        }
    }
}

function reloadPosts() {
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var posts = JSON.parse(this.responseText);
			populatePosts(posts);
		}
	};
	xmlhttp.open("GET", "./posts.json", true);
	xmlhttp.send();
}