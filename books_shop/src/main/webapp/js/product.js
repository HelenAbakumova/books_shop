  function add_to_cart(id) {
    $.ajax({
      url: "cart?id="+id,
        type: "PUT",
             success: function(result){
                $("#count").html(result);
             }
    });
  }