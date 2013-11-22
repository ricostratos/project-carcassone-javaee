$(function() {
    $("#newTile").draggable();
});

var newTileRotation = 0;
var trueTileRotation, coordinates;

function checkNewTileLocation() {
    var match = false;  // Tehdään tarkistin jolla tarkistetaan onko palikka löytänyt paikkansa.
    var smallestX = $("#gameBoardTable").find("td").attr("id").split("_")[0]; // Haetaan pienimmän x-koordinaati
    var smallestY = $("#gameBoardTable").find("td").attr("id").split("_")[1]; // Haetaan pienimmän y-koordinaatin itseisarvo
    var temp_i = 0, temp_j = 0;
    
    for(var i=smallestX; i<$("#gameBoardTable").find("tr").length+parseInt(smallestX); i++) {
        temp_i++;
        for(var j=smallestY; j<parseInt($("#gameBoardRow"+i).find("td").length)+parseInt(smallestY); j++) {
            temp_j++;
            if($("#"+i+"_"+j).attr("class").split(" ")[0] === "newTilePlaceHolder") {
            if(Math.abs($("#newTile").position().left - $("#"+i+"_"+j).position().left) < 50 && Math.abs($("#newTile").position().top - $("#"+i+"_"+j).position().top) < 50) {
                $("#newTile").css("opacity","1");
                $("#newTile").css("left",$("#"+i+"_"+j).position().left);
                $("#newTile").css("top",$("#"+i+"_"+j).position().top);
                match = true;
                coordinates = i+"_"+j;
                rotatorVisible();
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
    alert("Palikka asetettu.\nRotation: "+newTileRotation+"\nmod(360): "+trueTileRotation+"\nKoordinaatit:\n X:"+coordinates.split("_")[0]+"\n Y:"+coordinates.split("_")[1]);
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