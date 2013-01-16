$().ready(function() {
	$('input[type!=hidden].autofocus').focus();
	$('.hidden-controls').hover(handleHiddenControls);
});

function handleHiddenControls(e){
	$(this).children('.controls').toggle(e.type === 'mouseenter');
	if (e.type === 'mouseleave') {
		$('#shelfSelector').fadeOut();
	}
};
