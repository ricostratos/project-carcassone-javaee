$(function() {
    $("#newTile").draggable();
});

var newTileRotation = 0;
var trueTileRotation, coordinates;
var match = false;  // Tehdään tarkistin jolla tarkistetaan onko palikka löytänyt paikkansa.

$(window).scroll(function() {
    if(window.location.hash === "#gameBoardAnchor") {
        $(window).scrollTop($(document).height());
    }
});

$("#gameBoard").scroll(function(){
    alert("scrolled!");
});

function checkNewTileLocation() {
    var smallestX = $("#gameBoardTable").find("td").attr("id").split("_")[0]; // Haetaan pienimmän x-koordinaati
    var smallestY = $("#gameBoardTable").find("td").attr("id").split("_")[1]; // Haetaan pienimmän y-koordinaati
    var temp_i = 0, temp_j = 0;
    
    for(var i=smallestY; i<$("#gameBoardTable").find("tr").length + parseInt(smallestY); i++) {
        for(var j=smallestX; j<$("#gameBoardRow"+i).find("td").length + parseInt(smallestX); j++) {
            if($("#"+j+"_"+i).attr("class").split(" ")[0] === "newTilePlaceHolder") {
            if(Math.abs($("#newTile").position().left - $("#"+j+"_"+i).position().left) < 51 &&
               Math.abs($("#newTile").position().top - $("#"+j+"_"+i).position().top) < 51) {
                $("#newTile").css("opacity","1");
                $("#newTile").css("left",$("#"+j+"_"+i).position().left);
                $("#newTile").css("top",$("#"+j+"_"+i).position().top);
                
                match = true;
                coordinates = j+"_"+i;
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
}
function rotateCCW() {
    newTileRotation -= 90;
    $("#newTileImage").css("transform","rotate("+newTileRotation+"deg)");
    $("#newTileImage").css("-ms-transform","rotate("+newTileRotation+"deg)");
    $("#newTileImage").css("-webkit-transform","rotate("+newTileRotation+"deg)");
}