const toAlpha = (num) => {
    if(num < 1 || num > 26 || typeof num !== 'number'){
        return -1;
    }
    const leveller = 65;
    return String.fromCharCode(num + leveller);
};

function encrypt(){
    var text = document.getElementById("text").value.toUpperCase();
    var key = document.getElementById("key").value.toUpperCase();
    var result="";
    if(text.length != key.length){
        result = "Error";
    }else{
        for(let i=0;i<text.length;i++){
        result += toAlpha((text.charCodeAt(i)+ key.charCodeAt(i)) % 26);
    }
    }
    
    document.getElementById("hasil").value = result;
}

function decrypt(){
    var text = document.getElementById("text").value.toUpperCase()
    var key = document.getElementById("key").value.toUpperCase()
    var result="";
    if(text.length != key.length){
        result = "Error"
    }else{
        for(let i=0;i<text.length;i++){
            result += toAlpha((text.charCodeAt(i)- key.charCodeAt(i)) % 26)
        }
    }
    document.getElementById("hasil").value = result
}