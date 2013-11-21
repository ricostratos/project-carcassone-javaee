$(function() {
    $("#newTile").draggable();
});

var newTileRotation = 0;

function checkNewTileLocation() {
    if(Math.abs($("#newTile").position().left - $("#newTilePlaceHolder").position().left) >= 50 || Math.abs($("#newTile").position().top - $("#newTilePlaceHolder").position().top) >= 50) {
        
    } else {
        $("#newTile").css("opacity","1");
        $("#newTile").css("left",$("#newTilePlaceHolder").position().left);
        $("#newTile").css("top",$("#newTilePlaceHolder").position().top);
        rotatorVisible();
    }
}

function rotatorVisible() {
    $("#rotator").css("visibility","visible");
    $("#rotator").css("left",$("#newTile").position().left-25);
    $("#rotator").css("top",$("#newTile").position().top+80);
}

function rotatorHidden() {
    $("#rotator").css("visibility","hidden");
    $("#newTile").css("opacity","0.7");
}

function newTileReady() {
    rotatorHidden();
    alert("Palikka asetettu. Rotation: "+newTileRotation+"\nTahan sitten sovellamme modulolaskentaa. :)");
}

function rotateCW() {
    newTileRotation += 90;
    $("#newTileImage").css("transform","rotate("+newTileRotation+"deg)");
    $("#newTileImage").css("-ms-transform","rotate("+newTileRotation+"deg)");
    $("#newTileImage").css("-webkit-transform","rotate("+newTileRotation+"deg)");
    
    /*if(newTileRotation >= 360) {
        newTileRotation = 0;
    }*/
}
function rotateCCW() {
    newTileRotation -= 90;
    $("#newTileImage").css("transform","rotate("+newTileRotation+"deg)");
    $("#newTileImage").css("-ms-transform","rotate("+newTileRotation+"deg)");
    $("#newTileImage").css("-webkit-transform","rotate("+newTileRotation+"deg)");
    
    /*if(newTileRotation < 0) {
        newTileRotation = 270;
    }*/
}