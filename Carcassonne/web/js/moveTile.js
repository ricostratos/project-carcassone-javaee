$(function() {
    $("#newTile").draggable();
});

var newTileRotation = 0;
var trueTileRotation;

function checkNewTileLocation() {
    var match = false;
    for(var i=0; i<$("#gameBoardTable").find("tr").length; i++) {
        for(var j=0; j<$("#gameBoardRow"+i).find("td").length; j++) {
            if($("#"+i+"-"+j).attr("class").split(" ")[0] === "newTilePlaceHolder") {
            if(Math.abs($("#newTile").position().left - $("#"+i+"-"+j).position().left) >= 50 || Math.abs($("#newTile").position().top - $("#"+i+"-"+j).position().top) >= 50) {
                
            } else {
                $("#newTile").css("opacity","1");
                $("#newTile").css("left",$("#"+i+"-"+j).position().left);
                $("#newTile").css("top",$("#"+i+"-"+j).position().top);
                rotatorVisible();
                match = true;
            }
            }
        }
    }
    if(!match) {
        $("#newTile").css("opacity","1");
        $("#newTile").css("left",$("#gameInfo").position().left+1);
        $("#newTile").css("top",$("#gameInfo").position().top+179);
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
    trueTileRotation = newTileRotation % 360;
    if(trueTileRotation < 0) trueTileRotation += 360;
    alert("Palikka asetettu.\nRotation: "+newTileRotation+"\nmod(360): "+trueTileRotation);
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