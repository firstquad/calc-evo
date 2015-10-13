function clearResult() {
    document.getElementById("expression").value = "0";
    document.getElementById("result").value = "0";
}

function coaleasce(value) {
    if (value == null)
        document.getElementById("result").value = "0";
}