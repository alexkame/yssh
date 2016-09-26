jQuery(document).ready(function($) {
	if ($('#J_bxslider').length) {
	    $('#J_bxslider').bxSlider({
	        mode: 'fade',
	        auto: true,
	        speed: 1500,
	        controls: true,
	        pager: true
	    });
	}

	if ($('#logo_perspective_black').length) {
        $('#logo_perspective_black').logo_perspective({
            skin: 'black',
            width: 1200,
            imageWidth: 320,
            imageHeight: 424,
            showTooltip: false,
            elementsHorizontalSpacing: 280,
            elementsVerticalSpacing: 40,
            autoPlay: 20,
            numberOfVisibleItems: 6,
            showNavArrows: false,
            border: 3
        });
    }

    if($('.online-bar')[0]){
        $(".online-bar").hover(function(){
            $(this).animate({right: 0}, 400);
        },function(){
            $(this).animate({right: -160}, 400);
        });
    }
});
