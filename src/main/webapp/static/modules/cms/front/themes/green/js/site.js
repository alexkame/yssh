jQuery(document).ready(function($) {
	if ($('.news-slider').length) {
        $(".news-slider").slide({ mainCell:".pic", effect:"left", autoPlay:true, delayTime:300 });
	}

    jQuery(".mod").slide({ titCell:".tabs-hd li", mainCell:".tabs-bd" });
});
