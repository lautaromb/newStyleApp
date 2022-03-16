function reportInfo(vars, showType = false) {
    if (showType === true) console.log(typeof vars);
    console.log(vars);
}

function addImg(ele, content) {
    var myDIV = document.querySelector(ele);
    var newContent = document.createElement('div');
    newContent.innerHTML = content;

    while (newContent.firstChild) {
        myDIV.appendChild(newContent.firstChild);
    }
}

var feedback = function(res) {
    reportInfo(res, true);
    if (res.success === true) {
        var get_link = res.data.link.replace(/^http:\/\//i, 'https://');
        document.querySelector('.status').classList.add('white');
        var content =
            '' + '<br><input m-1 class="image-url"  " value=\"' + get_link + '\"/>'
        addImg('.status', content);
    }
};

new Imgur({
    clientid: '6a4d67df30c0957', //You can change this ClientID
    callback: feedback
});