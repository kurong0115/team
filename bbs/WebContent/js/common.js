var xmlHttp;
function createXMLHttpRequest() {
    if (window.XMLHttpRequest) { // 如果可以取得XMLHttpRequest
        xmlHttp = new XMLHttpRequest();  // Mozilla、Firefox、Safari 
    }
    else if (window.ActiveXObject) { // 如果可以取得ActiveXObject
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP"); // Internet Explorer
    }
}


