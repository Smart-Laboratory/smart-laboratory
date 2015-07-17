function changeTab(str) {
	if(str.indexOf("in") >=0) {
		$("#inpre").attr("class", "active");
		$("#outpre").removeClass("active");
	} else {
		$("#inpre").removeClass("active");
		$("#outpre").attr("class", "active");
	}
}