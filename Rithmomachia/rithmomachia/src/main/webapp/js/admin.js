"use strict";

function refresh() {
    $.ajax({
        url: 'api/games',
        type: 'GET',
        contentType: 'application/json',
        dataType: 'json',
        success: function (resp) {
            let str = '';
            for (let i = 0; i < resp.length; i++) {
                let g = resp[i];
                str += '<div>' 
                        + g.id 
                        + '<button class="delete" data-id="' 
                        + g.id 
                        + '">Delete</button></div>';
            }
            $('#games').html(str);
        },
        error: function () {
            alert('Network communication error');
        }
    });
}

$('#refresh').on('click', function () {
    $('#games').html('');
    refresh();
});

$(document).on('click', '.delete', function() {
    let id = $(this).data('id');
    $.ajax({
        url: 'api/game/' + id,
        type: 'DELETE',
        success: function () {
            refresh();
        },
        error: function () {
            alert('Network communication error');
        }
    });
});
