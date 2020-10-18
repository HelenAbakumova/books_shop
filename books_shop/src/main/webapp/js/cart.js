function increase_count(id) {
    $.ajax({
        url: "cart",
        type: "POST",
        data: {
            "id": id,
            "action": "+"
        },
        success: function(result) {
            var params = result.split(" ");
            $("#" + id).html(params[0]);
            $("#totalSum").html("Total:" + params[1]);
        }
    });
}

function decrease_count(id) {
    $.ajax({
        url: "cart",
        type: "POST",
        data: {
            "id": id,
            "action": "-"
        },
        success: function(result) {
            var params = result.split(" ");
            $("#" + id).html(params[0]);
            $("#totalSum").html("Total:" + params[1]);
        }
    });
}

function remove_element(id) {
    $.ajax({
        url: "cart?action=remove&id=" + id,
        type: "DELETE",
        success: function(result) {
            $("#total" + id).remove();
            if (result == 0) {
                $("#order_button").remove();
                $("#totalSum").html("Your basket is empty");
            } else {
                $("#totalSum").html("Total:" + result);
            }
        }
    });
}