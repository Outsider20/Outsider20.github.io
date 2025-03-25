"use strict";

function enableInputs() {
    $('#refresh').removeAttr('disabled');
    $('#moveInput').removeAttr('disabled');
    $('#move').removeAttr('disabled');
    $('#play').removeAttr('disabled');
}

function refreshBoard() {
    let id = $('#gameId').val();
    $.ajax({
        url: 'api/game/' + id,
        type: 'GET',
        dataType: 'json',
        success: function (resp) {
            $('#gameId').val(resp.id);
            let pp = resp.board.pieces;
            let str = '';
            for (let i = 0; i < pp.length; i++) {
                let p = pp[i];
                let s = p.color + p.kind + p.value + p.pos;
                str += s + ' ';
                if (i % 8 === 7) {
                    str += '<br>';
                }
            }
            $('#board').html(str);
        },
        error: function () {
            alert('Network communication error');
        }
    });
}

$('#newGame').on('click', function () {
    $.ajax({
        url: 'api/games',
        type: 'POST',
        contentType: 'application/json',
        dataType: 'text',
        success: function (resp) {
            $('#gameId').val(resp);
            enableInputs();
            refreshBoard();
        },
        error: function () {
            alert('Network communication error');
        }
    });
});

$('#newGameBoard').on('click', function () {
    let str = $('#inputBoard').val().trim();
    let pp = str.split(" ");
    let pieces = [];
    for (let i = 0; i < pp.length; i++) {
        let p = pp[i];
        let color = p[0];
        let kind = p[1];
        let j = 2;
        for (; j < p.length; j++) {
            if (p[j] < '0' || p[j] > '9') {
                break;
            }
        }
        let value = p.substring(2, j);
        let pos = p.substring(j);
        pieces.push({
            color: color,
            kind: kind,
            value: value,
            pos: pos
        });
    }
    let board = JSON.stringify({
        pieces: pieces
    });
    $.ajax({
        url: 'api/games',
        type: 'PUT',
        contentType: 'application/json',
        dataType: 'text',
        data: board,
        success: function (resp) {
            $('#gameId').val(resp);
            enableInputs();
            refreshBoard();
        },
        error: function () {
            alert('Network communication error');
        }
    });
});

$('#refresh').on('click', function () {
    refreshBoard();
});

$('#move').on('click', function () {
    let id = $('#gameId').val();
    let move = $('#moveInput').val().trim();
    let s = move.split(' ');
    let data = JSON.stringify({
        from: s[0],
        to: s[1]
    });
    $.ajax({
        url: 'api/game/' + id,
        type: 'PUT',
        contentType: 'application/json',
        data: data,
        success: function () {
            refreshBoard();
        },
        error: function () {
            alert('Network communication error');
        }
    });
});

$('#play').on('click', function () {
    let id = $('#gameId').val();
    $.ajax({
        url: 'api/game/' + id,
        type: 'POST',
        dataType: 'json',
        success: function (resp) {
            let str = resp.from + ' ' + resp.to;
            $('#lastMove').val(str);
            refreshBoard();
        },
        error: function () {
            alert('Network communication error');
        }
    });
});
