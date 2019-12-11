function PostObject(image, title, text, pageLink) {
    this.image = image;
    this.title = title;
    this.text = text;
    this.pageLink = pageLink;
}

var posts = [];

function populatePosts() {
    var postContainer = document.getElementById("post-container");
    for (var i in posts) {
        if (posts[i].title !== null && posts[i].text !== null && posts[i].pageLink !== null) {
            var card = document.createElement("div");
            card.classList.add("card", "col-sm-12", "col-md-04", "col-lg-04", "col-xl-3");
            if (posts[i].image !== null) {
                var img = document.createElement("img");
                img.classList.add("card-img-top");
                img.src = posts[i].image;
                card.appendChild(img)
            }
            var cardBody = document.createElement("div");
            cardBody.classList.add("card-body");
            var title = document.createElement("h5");
            title.classList.add("card-title");
            var titleLink = document.createElement("a");
            titleLink.href = posts[i].pageLink;
            titleLink.innerText = posts[i].title;
            title.appendChild(titleLink);
            var text = document.createElement("p");
            text.classList.add("card-text");
            text.innerText = posts[i].text;

            cardBody.appendChild(title);
            cardBody.appendChild(text);
            card.appendChild(cardBody);
            postContainer.appendChild(card);
        }
    }
}

posts.push(new PostObject(null,
    "Lorem Ipsum",
    "Nunc mattis, odio sit amet feugiat finibus, dolor dui imperdiet sem, ut molestie enim mauris a\n" +
    "mi.\n" +
    "Maecenas sit amet lorem pretium, euismod enim ac, porta arcu. Cras placerat justo dolor, vitae\n" +
    "consequat tellus faucibus vitae. Suspendisse fermentum ante eu leo feugiat, vel fermentum enim\n" +
    "porta. Phasellus pulvinar neque tincidunt lacus lobortis, ac auctor velit mattis. Aliquam erat\n" +
    "volutpat. Quisque porttitor augue a nisi bibendum facilisis. Praesent tortor magna, hendrerit\n" +
    "non\n" +
    "scelerisque eu, vehicula non est. Aliquam nec rutrum nisi, eu placerat lorem. Phasellus\n" +
    "vestibulum\n" +
    "venenatis sagittis. Integer facilisis eu est nec ullamcorper.",
    "./post1.jsp"));

posts.push(new PostObject("https://images.unsplash.com/photo-1575686249941-1d5d9106b998",
    "Dolor sit amet",
    "This mountain was fun to climb on, even if the terrain wasn't perfect. Honestly, the mountain probably isn't as pretty as we thought it would be. As far as I know, we didn't climb anything on the last day or night because the whole mountain was covered in snow. One of the first hikes I did was on a snow covered hill to the top. It was pretty cool to see the horizon, the snow being fine and flowing just like it does in real life. If you're in the mood for mountains and snow, this is the right place.",
    "./post2.jsp"));

populatePosts();