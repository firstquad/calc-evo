function clearResult() {
    document.getElementById("expression").value = "0";
    document.getElementById("result").value = "0";
}
function calculate(){
    var xhr = new XMLHttpRequest();
    var body = 'expression=' + encodeURIComponent(document.getElementById("expression").value);
    xhr.open("POST", '/calc', true)
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded')
    xhr.send(body);
}