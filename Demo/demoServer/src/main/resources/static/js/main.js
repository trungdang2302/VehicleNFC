function switchContentFragment(url, title, button) {
    if (button != null) {
        deActiveAllMenuButton();
        button.className = "active pointer menu";
    }
    $('#content-title').text(title);
    $('#main-content').attr('src', url);
}

function deActiveAllMenuButton() {
    var buttons = document.getElementsByClassName("menu");
    for (var i = 0; i < buttons.length; i++) {
        buttons[i].className = "pointer menu";
    }
}

var show = true;

function shortenNavBar() {
    var items = document.getElementsByClassName("title");
    for (var i = 0; i < items.length; i++) {
        items[i].style.fontSize = (show) ? "0" : "16px";
        items[i].style.width = (show) ? "0" : "190px";
    }
    $("#content-title-holder").css("left", (show) ? "75px" : "280px");
    $("#main").width((show) ? "calc(100% - 70px)" : "calc(100% - 260px)");
    $("#shortenNavBarIcon").attr('class', (show) ? "lnr lnr-chevron-right" : "lnr lnr-chevron-left");
    show = !show;
}